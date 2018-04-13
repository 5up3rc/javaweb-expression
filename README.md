这是一个专门Hook并记录Java表达式语言执行的Agent小程序。逻辑非常简单，适合初学者使用。所有的实现代码逻辑都在:`org.javaweb.expression.Agent`类里面。导入javaweb-expression并构建expression-agent模块。然后在程序运行之前加入以下jvm参数:

`-noverify -javaagent:/目标路径/expression-agent.jar -Dfile.encoding=UTF-8`

如果是Tomcat需要修改`catalina.sh`或者`catalina.bat`文件,添加如下配置参数:`JAVA_OPTS="-noverify -javaagent:/目标路径/expression-agent.jar`
成功插入后控制台(可以根据具体的情况输出到文件或者数据库)会打印出拦截到的表达式和程序调用链。

如:

```
---------------------------------EXP-----------------------------------------
3-1
---------------------------------调用链---------------------------------------
java.lang.Thread.getStackTrace(Thread.java:1559)
org.javaweb.expression.Agent.expression(Agent.java:179)
org.springframework.expression.spel.standard.SpelExpression.<init>(SpelExpression.java)
org.springframework.expression.spel.standard.InternalSpelExpressionParser.doParseExpression(InternalSpelExpressionParser.java:139)
org.springframework.expression.spel.standard.SpelExpressionParser.doParseExpression(SpelExpressionParser.java:61)
org.springframework.expression.spel.standard.SpelExpressionParser.doParseExpression(SpelExpressionParser.java:33)
org.springframework.expression.common.TemplateAwareExpressionParser.parseExpression(TemplateAwareExpressionParser.java:52)
org.springframework.expression.common.TemplateAwareExpressionParser.parseExpression(TemplateAwareExpressionParser.java:43)
org.javaweb.exp.controller.IndexController.spel(IndexController.java:37)
sun.reflect.NativeMethodAccessorImpl.invoke0(Native Method)
sun.reflect.NativeMethodAccessorImpl.invoke(NativeMethodAccessorImpl.java:62)
sun.reflect.DelegatingMethodAccessorImpl.invoke(DelegatingMethodAccessorImpl.java:43)
java.lang.reflect.Method.invoke(Method.java:498)
org.springframework.web.method.support.InvocableHandlerMethod.doInvoke(InvocableHandlerMethod.java:209)
org.springframework.web.method.support.InvocableHandlerMethod.invokeForRequest(InvocableHandlerMethod.java:136)
org.springframework.web.servlet.mvc.method.annotation.ServletInvocableHandlerMethod.invokeAndHandle(ServletInvocableHandlerMethod.java:102)
org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter.invokeHandlerMethod(RequestMappingHandlerAdapter.java:877)
org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter.handleInternal(RequestMappingHandlerAdapter.java:783)
org.springframework.web.servlet.mvc.method.AbstractHandlerMethodAdapter.handle(AbstractHandlerMethodAdapter.java:87)
org.springframework.web.servlet.DispatcherServlet.doDispatch(DispatcherServlet.java:991)
org.springframework.web.servlet.DispatcherServlet.doService(DispatcherServlet.java:925)
org.springframework.web.servlet.FrameworkServlet.processRequest(FrameworkServlet.java:974)
org.springframework.web.servlet.FrameworkServlet.doGet(FrameworkServlet.java:866)
javax.servlet.http.HttpServlet.service(HttpServlet.java:635)
org.springframework.web.servlet.FrameworkServlet.service(FrameworkServlet.java:851)
javax.servlet.http.HttpServlet.service(HttpServlet.java:742)
org.apache.catalina.core.ApplicationFilterChain.internalDoFilter(ApplicationFilterChain.java:231)
org.apache.catalina.core.ApplicationFilterChain.doFilter(ApplicationFilterChain.java:166)
org.apache.tomcat.websocket.server.WsFilter.doFilter(WsFilter.java:52)
org.apache.catalina.core.ApplicationFilterChain.internalDoFilter(ApplicationFilterChain.java:193)
org.apache.catalina.core.ApplicationFilterChain.doFilter(ApplicationFilterChain.java:166)
org.springframework.web.filter.RequestContextFilter.doFilterInternal(RequestContextFilter.java:99)
org.springframework.web.filter.OncePerRequestFilter.doFilter(OncePerRequestFilter.java:107)
org.apache.catalina.core.ApplicationFilterChain.internalDoFilter(ApplicationFilterChain.java:193)
org.apache.catalina.core.ApplicationFilterChain.doFilter(ApplicationFilterChain.java:166)
org.springframework.web.filter.HttpPutFormContentFilter.doFilterInternal(HttpPutFormContentFilter.java:109)
org.springframework.web.filter.OncePerRequestFilter.doFilter(OncePerRequestFilter.java:107)
org.apache.catalina.core.ApplicationFilterChain.internalDoFilter(ApplicationFilterChain.java:193)
org.apache.catalina.core.ApplicationFilterChain.doFilter(ApplicationFilterChain.java:166)
org.springframework.web.filter.HiddenHttpMethodFilter.doFilterInternal(HiddenHttpMethodFilter.java:81)
org.springframework.web.filter.OncePerRequestFilter.doFilter(OncePerRequestFilter.java:107)
org.apache.catalina.core.ApplicationFilterChain.internalDoFilter(ApplicationFilterChain.java:193)
org.apache.catalina.core.ApplicationFilterChain.doFilter(ApplicationFilterChain.java:166)
org.springframework.boot.web.servlet.support.ErrorPageFilter.doFilter(ErrorPageFilter.java:117)
org.springframework.boot.web.servlet.support.ErrorPageFilter.access$000(ErrorPageFilter.java:61)
org.springframework.boot.web.servlet.support.ErrorPageFilter$1.doFilterInternal(ErrorPageFilter.java:92)
org.springframework.web.filter.OncePerRequestFilter.doFilter(OncePerRequestFilter.java:107)
org.springframework.boot.web.servlet.support.ErrorPageFilter.doFilter(ErrorPageFilter.java:110)
org.apache.catalina.core.ApplicationFilterChain.internalDoFilter(ApplicationFilterChain.java:193)
org.apache.catalina.core.ApplicationFilterChain.doFilter(ApplicationFilterChain.java:166)
org.springframework.web.filter.CharacterEncodingFilter.doFilterInternal(CharacterEncodingFilter.java:200)
org.springframework.web.filter.OncePerRequestFilter.doFilter(OncePerRequestFilter.java:107)
org.apache.catalina.core.ApplicationFilterChain.internalDoFilter(ApplicationFilterChain.java:193)
org.apache.catalina.core.ApplicationFilterChain.doFilter(ApplicationFilterChain.java:166)
org.apache.catalina.core.StandardWrapperValve.invoke(StandardWrapperValve.java:199)
org.apache.catalina.core.StandardContextValve.invoke(StandardContextValve.java:96)
org.apache.catalina.authenticator.AuthenticatorBase.invoke(AuthenticatorBase.java:478)
org.apache.catalina.core.StandardHostValve.invoke(StandardHostValve.java:140)
org.apache.catalina.valves.ErrorReportValve.invoke(ErrorReportValve.java:81)
org.apache.catalina.valves.AbstractAccessLogValve.invoke(AbstractAccessLogValve.java:650)
org.apache.catalina.core.StandardEngineValve.invoke(StandardEngineValve.java:87)
org.apache.catalina.connector.CoyoteAdapter.service(CoyoteAdapter.java:342)
org.apache.coyote.http11.Http11Processor.service(Http11Processor.java:803)
org.apache.coyote.AbstractProcessorLight.process(AbstractProcessorLight.java:66)
org.apache.coyote.AbstractProtocol$ConnectionHandler.process(AbstractProtocol.java:868)
org.apache.tomcat.util.net.NioEndpoint$SocketProcessor.doRun(NioEndpoint.java:1459)
org.apache.tomcat.util.net.SocketProcessorBase.run(SocketProcessorBase.java:49)
java.util.concurrent.ThreadPoolExecutor.runWorker(ThreadPoolExecutor.java:1149)
java.util.concurrent.ThreadPoolExecutor$Worker.run(ThreadPoolExecutor.java:624)
org.apache.tomcat.util.threads.TaskThread$WrappingRunnable.run(TaskThread.java:61)
java.lang.Thread.run(Thread.java:748)
--------------------------------------------------------------------------
```

`javaweb-exp`是一个包含集中表达式执行点的测试项目,可以直接部署到tomcat就行测试。