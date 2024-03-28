
package com.stonedt.spider.expression.interpreter;

import com.stonedt.spider.expression.ExpressionError;
import com.stonedt.spider.expression.ExpressionError.TemplateException;
import com.stonedt.spider.expression.ExpressionTemplate;
import com.stonedt.spider.expression.ExpressionTemplateContext;
import com.stonedt.spider.expression.parsing.Ast;
import com.stonedt.spider.expression.parsing.Ast.Node;
import com.stonedt.spider.expression.parsing.Ast.Text;
//import org.spiderflow.core.utils.MyExceptionUtils;
//import org.spiderflow.utils.SenderMsgUtil;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * Interprets a Template given a TemplateContext to lookup variable values in and writes the evaluation results to an output
 * stream. Uses the global {@link Reflection} instance as returned by {@link Reflection#getInstance()} to access members and call
 * methods.
 * </p>
 *
 * <p>
 * The interpeter traverses the AST as stored in {@link ExpressionTemplate#getNodes()}. the interpeter has a method for each AST node type
 * (see {@link Ast} that evaluates that node. A node may return a value, to be used in the interpretation of a parent node or to
 * be written to the output stream.
 * </p>
 **/
public class AstInterpreter {
	public static Object interpret (ExpressionTemplate template, ExpressionTemplateContext context, Map<String, Object> variables) {
		try {
			return interpretNodeList(template.getNodes(), template, context);
		} catch (Throwable t) {
			if (t instanceof TemplateException)
				throw (TemplateException)t;
			else {
				ExpressionError.error("执行表达式出错 " + t.getMessage(), template.getNodes().get(0).getSpan(),t);
				//发送日志
				//SenderMsgUtil.sendLog(variables,"执行表达式出错",t.getMessage(), MyExceptionUtils.getString(t),"ERROR","org.spiderflow.core.expression.interpreter.AstInterpreter","");

				return null; // never reached
			}
		} 
	}

	public static Object interpretNodeList (List<Node> nodes, ExpressionTemplate template, ExpressionTemplateContext context) throws IOException {
		String result = "";
		for (int i = 0, n = nodes.size(); i < n; i++) {
			Node node = nodes.get(i);
			Object value = node.evaluate(template, context);
			if(node instanceof Text){
				result += node.getSpan().getText();
			}else if(value == null){
				if(i ==	 0 && i + 1 == n){
					return null;
				}
				result += "null";
			}else if(value instanceof String || value instanceof Number || value instanceof Boolean){
				if(i ==0 && i + 1 ==n){
					return value;
				}
				result += value;
			}else if(i + 1 < n){
				ExpressionError.error("表达式执行错误", node.getSpan());
			}else{
				return value;
			}
		}
		return result;
	}
}
