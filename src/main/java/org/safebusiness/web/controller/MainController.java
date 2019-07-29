package org.safebusiness.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {

	@GetMapping("safebusiness/index")
	public String index() {
		return "index";
	}
	
	@GetMapping("safebusiness")
	public String baseUrl() {
		return "redirect:safebusiness/index";
	}
	
}
