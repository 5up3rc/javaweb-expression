/*
 * Copyright yz 2018-01-20 Email:admin@javaweb.org.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.javaweb.expression;

import org.objectweb.asm.*;

import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;
import java.lang.instrument.Instrumentation;
import java.security.ProtectionDomain;
import java.util.ArrayList;
import java.util.List;

public class Agent implements Opcodes {

	private static List<MethodHookDesc> expClassList = new ArrayList<MethodHookDesc>();

	static {
		expClassList.add(
				new MethodHookDesc(
						"org.mvel2.MVELInterpretedRuntime", "parse", "()Ljava/lang/Object;"
				)
		);

		expClassList.add(
				new MethodHookDesc(
						"ognl.Ognl", "parseExpression",
						"(Ljava/lang/String;)Ljava/lang/Object;"
				)

		);
		expClassList.add(
				new MethodHookDesc(
						"org.springframework.expression.spel.standard.SpelExpression", "<init>",
						"(Ljava/lang/String;Lorg/springframework/expression/spel/ast/SpelNodeImpl;" +
								"Lorg/springframework/expression/spel/SpelParserConfiguration;)V"
				)
		);
	}

	public static void premain(String args, Instrumentation inst) {
		inst.addTransformer(new ClassFileTransformer() {
			@Override
			public byte[] transform(ClassLoader loader, String name, Class<?> classBeingRedefined, ProtectionDomain protectionDomain, byte[] classfileBuffer) throws IllegalClassFormatException {
				final String className = name.replace("/", ".");

				for (final MethodHookDesc methodHookDesc : expClassList) {
					if (methodHookDesc.getHookClassName().equals(className)) {
						final ClassReader cr = new ClassReader(classfileBuffer);

						// 忽略接口类
						ClassWriter cw  = new ClassWriter(cr, ClassWriter.COMPUTE_MAXS);
						final int   api = ASM5;

						try {
							ClassVisitor classVisitor = new ClassVisitor(api, cw) {

								@Override
								public void visit(int version, int access, String name, String signature, String superName, String[] interfaces) {
									super.visit(version, access, name, signature, superName, interfaces);
								}

								@Override
								public MethodVisitor visitMethod(final int access, final String methodName, final String argTypeDesc, final String signature, final String[] exceptions) {
									final MethodVisitor methodVisitor = super.visitMethod(access, methodName, argTypeDesc, signature, exceptions);

									if (methodHookDesc.getHookMethodName().equals(methodName) && methodHookDesc.hookMethodArgTypeDesc.equals(argTypeDesc)) {
										return new MethodVisitor(api, methodVisitor) {

											@Override
											public void visitCode() {
												if ("ognl.Ognl".equals(className)) {
													mv.visitVarInsn(Opcodes.ALOAD, 0);// 传入参数名
												} else {
													mv.visitVarInsn(Opcodes.ALOAD, 1);// 传入参数名
												}

												// 调用对应的处理类处理
												mv.visitMethodInsn(
														Opcodes.INVOKESTATIC, Agent.class.getName().replace(".", "/"),
														"expression", "(Ljava/lang/String;)V", false
												);
											}

										};
									}

									return methodVisitor;
								}
							};
							cr.accept(classVisitor, ClassReader.EXPAND_FRAMES);
							classfileBuffer = cw.toByteArray();
						} catch (Throwable t) {
							t.printStackTrace();
						}
					}
				}
				return classfileBuffer;
			}
		});
	}

	public static class MethodHookDesc {

		/**
		 * Hook类名
		 */
		private String hookClassName;

		/**
		 * Hook方法名
		 */
		private String hookMethodName;

		/**
		 * Hook方法参数描述符
		 */
		private String hookMethodArgTypeDesc;

		/**
		 * ASM方法Hook,通过类名、父类名、方法名和参数确定一个Hook点。Hook的类名可以为空,
		 * 但是hookSuperClassName不允许为空,如果没有直接继承的类就写java.lang.Object。
		 *
		 * @param hookClassName         hook类名
		 * @param hookMethodName        hook方法名
		 * @param hookMethodArgTypeDesc hook方法描述符
		 */
		public MethodHookDesc(String hookClassName, String hookMethodName, String hookMethodArgTypeDesc) {

			this.hookClassName = hookClassName;
			this.hookMethodName = hookMethodName;
			this.hookMethodArgTypeDesc = hookMethodArgTypeDesc;
		}

		public String getHookClassName() {
			return hookClassName;
		}

		public void setHookClassName(String hookClassName) {
			this.hookClassName = hookClassName;
		}

		public String getHookMethodName() {
			return hookMethodName;
		}

		public void setHookMethodName(String hookMethodName) {
			this.hookMethodName = hookMethodName;
		}

		public String getHookMethodArgTypeDesc() {
			return hookMethodArgTypeDesc;
		}

		public void setHookMethodArgTypeDesc(String hookMethodArgTypeDesc) {
			this.hookMethodArgTypeDesc = hookMethodArgTypeDesc;
		}

	}

	public static void expression(String exp) {
		System.err.println("---------------------------------EXP-----------------------------------------");
		System.err.println(exp);
		System.err.println("---------------------------------调用链---------------------------------------");

		StackTraceElement[] elements = Thread.currentThread().getStackTrace();

		for (StackTraceElement element : elements) {
			System.err.println(element);
		}

		System.err.println("--------------------------------------------------------------------------");
	}

}
