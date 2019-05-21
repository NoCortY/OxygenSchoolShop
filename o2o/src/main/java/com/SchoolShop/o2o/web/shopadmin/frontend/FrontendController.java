package com.SchoolShop.o2o.web.shopadmin.frontend;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/frontend")
public class FrontendController {
	@RequestMapping(value = "/index",method = RequestMethod.GET)
	private String index() {
		return "frontend/index";
	}
	@RequestMapping(value = "/shoplist",method = RequestMethod.GET)
	private String shoplist() {
		return "frontend/shoplist";
	}
	@RequestMapping(value="/shopdetail",method = RequestMethod.GET)
	private String shopdetail() {
		return "frontend/shopdetail";
	}
}
