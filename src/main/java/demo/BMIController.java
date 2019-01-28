package demo;

import javax.servlet.http.HttpServletRequest;

import base.annotation.RequestMapping;

public class BMIController {

	@RequestMapping("/toBMI.do")
	public String toBMI() {
		System.out.println("toBMI");
		return "BMI";
	}
	@RequestMapping("/BMI.do")
	public String BMI(HttpServletRequest request) {
		System.out.println("BMI");
		double height = Double.parseDouble(request.getParameter("height"));
		double weight = Double.parseDouble(request.getParameter("weight"));
		String sex = request.getParameter("sex");
		System.out.println(sex+"---"+height+"---"+weight);
		return "welcome";
	}
}
