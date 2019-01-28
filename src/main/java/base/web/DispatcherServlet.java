package base.web;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import base.common.Handler;
import base.common.HandlerMapping;

public class DispatcherServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	HandlerMapping handlerMapping;
       
	@Override
	public void init() throws ServletException {
		String config = getServletConfig().getInitParameter("ConfigLoad");
		System.out.println(config);
		InputStream in = getClass().
				getClassLoader().getResourceAsStream(config);
		SAXReader saxReader = new SAXReader();
		try {
			Document document = saxReader.read(in);
			Element root = document.getRootElement();
			List<Element> elements = root.elements();
			List<Object> beans = new ArrayList<Object>();
			for(Element element:elements) {
				String className = element.attributeValue("class");
				System.out.println(className);
				Object obj = Class.forName(className).newInstance();
				beans.add(obj);
			}
			handlerMapping = new HandlerMapping();
			handlerMapping.process(beans);
			System.out.println(beans);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String uri = request.getRequestURI();
		System.out.println("uri:"+uri);
		String contextPath = request.getContextPath();
		System.out.println("应用名长度："+contextPath.length());
		String path = uri.substring(contextPath.length());
		System.out.println("path:"+path);
		Handler hander = handlerMapping.getHandlel(path);
		if(hander!=null) {
			Method mh = hander.getMh();
			Object obj = hander.getObj();
			Object rv = null;
			try {
				Object [] types = mh.getParameterTypes();
				if(types.length>0) {
					Object [] params = new Object[types.length];
					for(int i=0;i<types.length;i++) {
						if(types[i].equals(HttpServletRequest.class)) {
							params[i] = request;
						}
						if(types[i].equals(HttpServletResponse.class)) {
							params[i] = response;
						}
					}
					rv = mh.invoke(obj, params);
				}else {
					rv = mh.invoke(obj);
				}
				System.out.println("rv:"+rv);
				String viewName = rv.toString();
				if(viewName.startsWith("redirect:")) {
					System.out.println("contextPath"+contextPath);
					String jspName = contextPath+"/"+viewName.substring("redirect:".length());
					response.sendRedirect(jspName);
				}else {
					String jspPath = "/WEB-INF/"+viewName+".jsp";
					System.out.println("JspPath:"+jspPath);
					request.getRequestDispatcher(jspPath).forward(request, response);
				}
			} catch (Exception e) {
				e.printStackTrace();
			} 
		}else {
			response.sendError(404);
		}
	}
}
