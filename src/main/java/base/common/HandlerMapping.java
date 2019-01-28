package base.common;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import base.annotation.RequestMapping;

public class HandlerMapping {

	Map<String, Handler> handlerMap = new HashMap<String, Handler>();
	
	public Handler getHandlel(String path) {
		return handlerMap.get(path);
	}
	
	public void process(List<Object> beans) {
		for(Object bean:beans) {
			Class clazz = bean.getClass();
			Method [] methods = clazz.getDeclaredMethods();
			for(Method method:methods) {
				RequestMapping rm = method.getDeclaredAnnotation(RequestMapping.class);
				String path = rm.value();
				System.out.println("path:"+path);
				handlerMap.put(path, new Handler(method, bean));
			}
		}
		System.out.println("handlerMap:"+handlerMap);
	}
}
