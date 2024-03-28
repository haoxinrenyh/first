package com.stonedt.spider.expression;

import org.apache.commons.lang3.StringUtils;
import com.stonedt.spider.ExpressionEngine;
import com.stonedt.spider.expression.interpreter.Reflection;
import com.stonedt.spider.executor.FunctionExecutor;
import com.stonedt.spider.executor.FunctionExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Map;

//@Component
public class DefaultExpressionEngine implements ExpressionEngine{
	
	@Autowired
	private List<FunctionExecutor> functionExecutors;
	
	@Autowired
	private List<FunctionExtension> functionExtensions;
	
	@PostConstruct
	private void init(){
		for (FunctionExtension extension : functionExtensions) {
			Reflection.getInstance().registerExtensionClass(extension.support(), extension.getClass());
		}
	}
	
	@Override
	public Object execute(String expression, Map<String, Object> variables) {

		if(StringUtils.isBlank(expression)){
			return expression;
		}
		//抽象语法树技术   还没看明白   解析java的 antlr
		ExpressionTemplateContext context = new ExpressionTemplateContext(variables);
		for (FunctionExecutor executor : functionExecutors) {
			context.set(executor.getFunctionPrefix(), executor);
		}

		ExpressionGlobalVariables.getVariables().entrySet().forEach(entry->{
			context.set(entry.getKey(),ExpressionTemplate.create(entry.getValue()).render(context,variables));
		});
		try {
			ExpressionTemplateContext.set(context);
			return ExpressionTemplate.create(expression).render(context,variables);
		} finally {
			ExpressionTemplateContext.remove();
		}
		
	}
	
}
