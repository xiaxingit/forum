package com.cn.forum.controller.back.sysmanage;

import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.forum.model.entity.RoleInfo;
import org.forum.service.ResourceService;
import org.forum.service.RoleService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class AuthManageAction {
	private static final Logger log = Logger.getLogger(AuthManageAction.class);
	
	@Resource(name="roleService")
	RoleService roleService;
	
	@RequestMapping("/sysmanage/authManage.html")
	public String toPage(){
		return "/back/sysmanage/authManage";
	}
    
	/**
	 * 查询获得所有角色  
	 * @param currPage
	 * @param Size
	 * @return
	 */
	@RequestMapping("/sysmanage/getAllRoleList.html")
	@ResponseBody
	public List<RoleInfo> getAllRoleList(int currPage,int Size){
		List<RoleInfo> list = null;
		list = roleService.getAllRoleList(currPage, Size);
		return list;
	}
	/**
	 * 获得所有角色列表 的总条数
	 * @return
	 */
	@RequestMapping("/sysmanage/getAllRoleListTotal.html")
	@ResponseBody
	public String getAllRoleListTotal(){
		return roleService.getAllRoleListTotal()+"";
	}
	/**
	 * 新增角色
	 */
	@RequestMapping("/sysmanage/addRole.html")
	@ResponseBody
	public String addRoleInfo(String role_name,String descr){
		try {
			RoleInfo roleInfo = new RoleInfo();
			roleInfo.setRole_name(role_name);
			roleInfo.setDescription(descr);
			int result = roleService.addRole(roleInfo);
			return result+"";
		} catch (Exception e) {
			log.error(e.getMessage());
			return "0";
		}
	}
	/**
	 * 禁用或启用角色
	 */
	@RequestMapping("/sysmanage/changeEnable.html")
	@ResponseBody
	public String changeEnable(int role_id,int en){
		RoleInfo info = new RoleInfo();
		info.setRole_id(role_id);
		info.setStatus(en+"");
		int result = roleService.updateRole(info);
		return result+"";
	}
	/**
	 * 修改角色
	 */
	@RequestMapping("/sysmanage/updateRole.html")
	@ResponseBody
	public String updateRole(int role_id,String role_name,String description,String status){
		RoleInfo info = new RoleInfo();
		info.setRole_id(role_id);
		info.setRole_name(role_name);
		info.setDescription(description);
		info.setStatus(status);
		int result  =roleService.updateRole(info);
		return result+"";
	}
	/**
	 * 删除角色
	 */
	@RequestMapping("/sysmanage/deleteRole.html")
	@ResponseBody
	public String deleteRole(String role_id){
		int result = roleService.deleteRole(role_id);
		return result+"";
	}
	/**
	 * 获得该角色的资源列表
	 */
	@RequestMapping("/sysmanage/getResourceByRoleId.html")
	@ResponseBody
	public List<String> getResourceByRoleId(int role_id){
		List<String> list = null;
		try{
			list =	roleService.getResourceInfoByRoleId(role_id);
		}catch(Exception e){
			log.error(e.getMessage());
		}
	    return list;
	}
	/**
	 * 新增角色资源权限
	 */
	@RequestMapping("/sysmanage/addRoleResource.html")
	@ResponseBody
	public void addRoleResource(int role_id,int r_id){
		try{
			roleService.addRoleResource(role_id, r_id);
		}catch(Exception e){
			log.error(e.getMessage());
		}
	}
	/**
	 * 删除角色资源权限
	 */
	@RequestMapping("/sysmanage/delRoleResource.html")
	@ResponseBody
	public void delRoleResource(int role_id,int r_id){
		try{
			roleService.delRoleResource(role_id, r_id);
		}catch(Exception e){
			log.error(e.getMessage());
		}
	}
}
