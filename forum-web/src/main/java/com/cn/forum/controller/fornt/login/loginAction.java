package com.cn.forum.controller.fornt.login;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class loginAction {
    
	 @RequestMapping("login.html")
	 public String toLoginPage(){
		 return "/fornt/login/login";
	 }
	 
	 @RequestMapping("error/404.html")
	 public String toErrorPage404(){
		 return "/fornt/error/404";
	 }
	 
	 @RequestMapping("error/500.html")
	 public String toErrorPage500(){
		 return "/fornt/error/500";
	 }
	 
}
