package com.cn.forum.controller.back.login;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.forum.model.entity.ResourceInfo;
import org.forum.model.entity.UserInfo;
import org.forum.model.entity.UserPwd;
import org.forum.service.ResourceService;
import org.forum.service.UserPwdService;
import org.forum.service.UserService;
import org.forum.util.MyDesUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class BackLoginAction {
	
	 @Resource(name="resourceService")
	 ResourceService resourceService;
	 
	 @Resource(name="userService")
	 UserService userService;
	 
	 @Resource(name="userPwdService")
	 UserPwdService userPwdService;
	 /**
	  * 跳转到后台登录页
	  * @return
	  */
	 @RequestMapping("back/login.html")
	 public String toLoginPage2(){
		 return "/back/login/login";
	 }
	 @RequestMapping("error/noPermission.html")
	 public String toNoPermission(){
		 return "/fornt/error/noPermission";
	 }
	 /**
	  * 进行登录验证
	  * @return
	  */
	 @RequestMapping("back/loginsubmit.html")
	 public ModelAndView toLoginPage3(String username,String password,HttpServletRequest request){
		 ModelAndView mav = new ModelAndView();
		 try {
			Subject subject = SecurityUtils.getSubject();
			UserInfo userInfo = userService.getUserInfoByName(username);
			if(userInfo == null){
				//用户名不存在
				setMAV(mav,username,password,"/back/login/login","该用户名不存在！");
			}else{
		       if("0".equals(userInfo.getStatus()) || !"1".equals(userInfo.getStatus())){
		    	   //用户已被禁用
		    	   setMAV(mav,username,password,"/back/login/login","账号已被禁用！");
		       }else{
		    	   //用户正常登录
				UsernamePasswordToken token = new UsernamePasswordToken(username,password);
				subject.login(token);
				updateLastLoginTime(userInfo); //修改最后登录时间
				subject.getSession().setAttribute("resourceList", resourceService.getAllResource(userInfo.getUser_id()+""));
				subject.getSession().setAttribute("curr_user", userInfo);
				mav.setViewName("redirect:/back/index.html");
		       }
			}
		} catch (AuthenticationException e) {
			//登录失败
			setMAV(mav,username,password,"/back/login/login","登录失败！请检查您的用户名和密码");
		} 
		 return mav;
	 }
	 public void setMAV(ModelAndView mav,String uname,String upwd,String vname,String msg){
			mav.addObject("user_name", uname);
			mav.addObject("user_pwd", upwd);
			mav.setViewName(vname);
			mav.addObject("msg", msg);
	 }
	 /**
	  * 跳转到后台主页
	  * @return
	  */
	 @RequestMapping("back/index.html")
	 public ModelAndView toMainPage(){
		 ModelAndView mav = new ModelAndView();
		 try {
			mav.setViewName("/back/main/index");
		} catch (Exception e) {
		    e.printStackTrace();
		} 
		 return mav;
	 }
	 /**
	  * 修改最后登录时间
	  */
	 public void updateLastLoginTime(UserInfo userInfo){
		 userInfo.setLast_login_time(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
		 userService.updateUserInfo(userInfo);
	 }
	 /**
	  * 登出
	  */
	 @RequestMapping("back/logout.html")
	 public String logout(){
		 Subject subject = SecurityUtils.getSubject();
		 subject.logout();
		 return "back/login/login";
	 }
     /**
      * 修改密码
      */
	@RequestMapping("back/resetPwd.html")
	@ResponseBody
	public String resetPwd(String oldPwd,String newPwd){
		String result = "";
		try {
			MyDesUtils desUtil = new MyDesUtils();
			Subject subject = SecurityUtils.getSubject();
			UserInfo userInfo = userService.getUserInfoByName(subject.getPrincipal().toString());
			UserPwd userPwd = userPwdService.getUserPwdByUserId(userInfo.getUser_id()+"");
			String de_pwd = desUtil.decrypt(userPwd.getPwd());
			if(de_pwd == oldPwd || de_pwd.equals(oldPwd)){
				//原密码正确
				userPwd.setPwd(desUtil.encrypt(newPwd));
				userPwdService.updateUserPwd(userPwd);
				result = "1";
			}else{
				//原密码错误
				result = "-1";
			}
		} catch (Exception e) {
             e.printStackTrace();
		}
		return result;
	}
}
