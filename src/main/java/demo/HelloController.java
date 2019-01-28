package demo;

import base.annotation.RequestMapping;

public class HelloController {
	@RequestMapping("/hello.do")
	public String hello() {
		return "hello";
	}
}
