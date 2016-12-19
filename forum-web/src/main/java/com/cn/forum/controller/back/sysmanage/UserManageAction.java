package com.cn.forum.controller.back.sysmanage;

import java.io.File;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.forum.model.entity.RoleInfo;
import org.forum.model.entity.UserInfo;
import org.forum.model.entity.UserPwd;
import org.forum.model.entity.UserRole;
import org.forum.service.RoleService;
import org.forum.service.UserPwdService;
import org.forum.service.UserService;
import org.forum.util.Base64Util;
import org.forum.util.MyDesUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class UserManageAction {
	private static final Logger log = Logger.getLogger(UserManageAction.class);
	
	@Resource(name="userService")
	UserService userService;
	@Resource(name="userPwdService")
	UserPwdService userPwdService;
	@Resource(name="roleService")
	RoleService roleService;
	
	@RequestMapping("/sysmanage/userManage.html")
	public ModelAndView toPage(String currPage,String msg,HttpServletRequest request,HttpServletResponse response){
		try{
			request.setCharacterEncoding("utf-8");
			response.setCharacterEncoding("utf-8");
		int page = 1;
		int size = 10;
		if(currPage != null && currPage != ""){
			page = Integer.parseInt(currPage); 
		}
		int userListTotal = userService.findUserTotal();
		int pageMax= userListTotal%size==0?userListTotal/size:(userListTotal/size)+1;
		if(page > pageMax){
			page = pageMax;
		}
		ModelAndView mav = new ModelAndView();
		List<UserInfo> userList = userService.findUserList(page, size);
		
		mav.addObject("userList", userList);
		mav.addObject("pageMax", pageMax);
		mav.addObject("page", page);
		mav.addObject("size", size);
		mav.addObject("total", userListTotal);
		if(msg!=null && msg.equals("1")){
		mav.addObject("msg", "新增用户成功！");
		}else if(msg!=null && msg.equals("2")){
		mav.addObject("msg", "删除用户成功！");	
		}
		mav.setViewName("/back/sysmanage/userManage");
		return mav;
		}catch(Exception e){
			log.error(e.getMessage());
			return null;
		}
		
	}
	/**
	 * 新增用户
	 */
	@RequestMapping("/sysmanage/addUserInfo.html")
	public ModelAndView addUserInfo(UserInfo user){
		ModelAndView mav = new ModelAndView();
		MyDesUtils desUtil;
		String base64Img = "";
		try {
		desUtil = new MyDesUtils();
		user.setStatus("1");
		if(user.getHead_image().indexOf("default") == -1){  //不是系统头像
			base64Img = user.getHead_image();
			user.setHead_image("");
		}
		int result = userService.addUserInfo(user);
		UserPwd userPwd = new UserPwd();
		//返回新增的用户ID
		int insert_userId = userService.selectMaxUserId();
		userPwd.setUser_id(insert_userId);
		userPwd.setPwd(desUtil.encrypt("123456"));
		int result2 = userPwdService.addUserPwd(userPwd);
		if(base64Img!="" && base64Img != null){  //不是系统头像
			//上传个人头像
			String pic_name = "image"+insert_userId+".png";
			uploadHeadImage(base64Img,pic_name);
			user.setUser_id(insert_userId);
			user.setHead_image(pic_name);
			userService.updateUserInfo(user);
		}
		mav.addObject("msg","1");
		mav.setViewName("redirect:/sysmanage/userManage.html");
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage());
		}
		return mav;
	}
	public void uploadHeadImage(String base64code,String pic_name){
		base64code = base64code.substring(base64code.indexOf(",")+1,base64code.length());
		Base64Util.GenerateImage(base64code, "D:/headImageUpload/"+pic_name);
	}
	
	/**
	 * 删除用户
	 */
	@RequestMapping("/sysmanage/delUserById.html")
	@ResponseBody
	public String delUserInfo(int del_user_id){
		//删除用户信息
		int result = userService.delUserInfo(del_user_id);
		//删除用户密码
		userPwdService.delUserPwd(del_user_id);
		//删除用户头像
		delUserHeadImage(del_user_id);
		//删除用户角色信息
		roleService.delUserRole(del_user_id);
		return result+"";
	}
	/**
	 * 删除用户头像文件
	 * @param del_user_id
	 */
	public void delUserHeadImage(int del_user_id){
		File file = new File("D:/headImageUpload/image"+del_user_id+".png");
		if(file.exists()){
			file.delete();
		}
	}
	/**
	 * 初始化密码
	 */
	@RequestMapping("/sysmanage/initialPwd.html")
	@ResponseBody
	public String initialUserPwd(int user_id){
		MyDesUtils desUtil;
		try {
			desUtil = new MyDesUtils();
			UserPwd userPwd = new UserPwd();
			userPwd.setUser_id(user_id);
			userPwd.setPwd(desUtil.encrypt("123456"));
			userPwdService.updateUserPwd(userPwd);
		} catch (Exception e) {
			log.error(e.getMessage());
			return "-1";
		}
		return "1";
	}
    /**
     * 启用/禁用   用户
     */
	@RequestMapping("/sysmanage/changeUserStatus.html")
	@ResponseBody
	public String changeUserStatus(int user_id,int status){
		return userService.updateUserStatus(user_id, status)+"";
	}
	/**
	 * 修改用户
	 */
	@RequestMapping("/sysmanage/updateUser.html")
	@ResponseBody
	public String updateUserInfo(UserInfo user){
		int result = 0;
		try{
			if(user.getHead_image().indexOf("default")==-1){
				String pic_name = "image"+user.getUser_id()+".png";
				//覆盖原有头像
				uploadHeadImage(user.getHead_image(),pic_name);
				user.setHead_image(pic_name);
			}
			result = userService.updateUserInfo(user);
	    }catch(Exception e){
	    	log.error(e.getMessage());
	    }
		return result+"";
	}
	/**
	 * 获得所有角色列表
	 */
	@RequestMapping("/sysmanage/getRoleList.html")
	@ResponseBody
	public List<RoleInfo> getRoleList(){
		return roleService.getAllRole();
	}
	/**
	 * 获得用户的角色列表
	 */
	@RequestMapping("/sysmanage/getUserRoleList.html")
	@ResponseBody
	public List<RoleInfo> getUserRoleList(int user_id){
		return roleService.getRoleListByUserId(user_id+"");
	}
	/**
	 * 修改角色信息
	 */
	@RequestMapping("/sysmanage/updateUserRole.html")
	@ResponseBody
	public String updateUserRole(int user_id,String roleids){
		try{
			//删除该用户原有角色
			roleService.delUserRole(user_id);
			String[] arr = roleids.split(",");
			for(int i=0;i<arr.length;i++){
				UserRole ur = new UserRole();
				ur.setUser_id(user_id);
				ur.setRole_id(Integer.parseInt(arr[i]));
				roleService.addUserRole(ur);
			}
			return "1";
		}catch(Exception e){
			log.error(e.getMessage());
			return "";
		}
		
	}
}
