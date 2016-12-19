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
		mav.addObject("msg", "�����û��ɹ���");
		}else if(msg!=null && msg.equals("2")){
		mav.addObject("msg", "ɾ���û��ɹ���");	
		}
		mav.setViewName("/back/sysmanage/userManage");
		return mav;
		}catch(Exception e){
			log.error(e.getMessage());
			return null;
		}
		
	}
	/**
	 * �����û�
	 */
	@RequestMapping("/sysmanage/addUserInfo.html")
	public ModelAndView addUserInfo(UserInfo user){
		ModelAndView mav = new ModelAndView();
		MyDesUtils desUtil;
		String base64Img = "";
		try {
		desUtil = new MyDesUtils();
		user.setStatus("1");
		if(user.getHead_image().indexOf("default") == -1){  //����ϵͳͷ��
			base64Img = user.getHead_image();
			user.setHead_image("");
		}
		int result = userService.addUserInfo(user);
		UserPwd userPwd = new UserPwd();
		//�����������û�ID
		int insert_userId = userService.selectMaxUserId();
		userPwd.setUser_id(insert_userId);
		userPwd.setPwd(desUtil.encrypt("123456"));
		int result2 = userPwdService.addUserPwd(userPwd);
		if(base64Img!="" && base64Img != null){  //����ϵͳͷ��
			//�ϴ�����ͷ��
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
	 * ɾ���û�
	 */
	@RequestMapping("/sysmanage/delUserById.html")
	@ResponseBody
	public String delUserInfo(int del_user_id){
		//ɾ���û���Ϣ
		int result = userService.delUserInfo(del_user_id);
		//ɾ���û�����
		userPwdService.delUserPwd(del_user_id);
		//ɾ���û�ͷ��
		delUserHeadImage(del_user_id);
		//ɾ���û���ɫ��Ϣ
		roleService.delUserRole(del_user_id);
		return result+"";
	}
	/**
	 * ɾ���û�ͷ���ļ�
	 * @param del_user_id
	 */
	public void delUserHeadImage(int del_user_id){
		File file = new File("D:/headImageUpload/image"+del_user_id+".png");
		if(file.exists()){
			file.delete();
		}
	}
	/**
	 * ��ʼ������
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
     * ����/����   �û�
     */
	@RequestMapping("/sysmanage/changeUserStatus.html")
	@ResponseBody
	public String changeUserStatus(int user_id,int status){
		return userService.updateUserStatus(user_id, status)+"";
	}
	/**
	 * �޸��û�
	 */
	@RequestMapping("/sysmanage/updateUser.html")
	@ResponseBody
	public String updateUserInfo(UserInfo user){
		int result = 0;
		try{
			if(user.getHead_image().indexOf("default")==-1){
				String pic_name = "image"+user.getUser_id()+".png";
				//����ԭ��ͷ��
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
	 * ������н�ɫ�б�
	 */
	@RequestMapping("/sysmanage/getRoleList.html")
	@ResponseBody
	public List<RoleInfo> getRoleList(){
		return roleService.getAllRole();
	}
	/**
	 * ����û��Ľ�ɫ�б�
	 */
	@RequestMapping("/sysmanage/getUserRoleList.html")
	@ResponseBody
	public List<RoleInfo> getUserRoleList(int user_id){
		return roleService.getRoleListByUserId(user_id+"");
	}
	/**
	 * �޸Ľ�ɫ��Ϣ
	 */
	@RequestMapping("/sysmanage/updateUserRole.html")
	@ResponseBody
	public String updateUserRole(int user_id,String roleids){
		try{
			//ɾ�����û�ԭ�н�ɫ
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
