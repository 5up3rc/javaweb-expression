/*
 * Copyright yz 2018-04-13 Email:admin@javaweb.org.
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
package org.javaweb.exp.controller;

import ognl.Ognl;
import ognl.OgnlContext;
import ognl.OgnlException;
import org.mvel2.MVEL;
import org.springframework.expression.Expression;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;

@Controller
public class IndexController {

	@RequestMapping(value = {"/spel", "/spel.php"})
	@ResponseBody
	public String spel(String exp, HttpServletResponse response) {
		SpelExpressionParser parser     = new SpelExpressionParser();
		Expression           expression = parser.parseExpression(exp);
		return expression.getValue().toString();
	}

	@RequestMapping(value = {"/mvel", "/mvel.php"})
	@ResponseBody
	public String mvel(String exp, HttpServletResponse response) {
		return MVEL.eval(exp).toString();
	}

	@RequestMapping(value = {"/ognl", "/ognl.php"})
	@ResponseBody
	public String ognl(String exp, HttpServletResponse response) throws OgnlException {
		OgnlContext context = new OgnlContext();
		return Ognl.getValue(exp, context, context.getRoot()).toString();
	}

}
