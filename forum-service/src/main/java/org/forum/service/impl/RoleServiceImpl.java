package org.forum.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.forum.db.dao.RoleDaoMapper;
import org.forum.model.entity.RoleInfo;
import org.forum.model.entity.UserRole;
import org.forum.service.RoleService;
import org.springframework.stereotype.Service;

@Service("roleService")
public class RoleServiceImpl implements RoleService {

	@Resource(name="roleDaoMapper")
	RoleDaoMapper roleDaoMapper;
	/**
	 * 查询角色列表   根据用户ID
	 */
	public List<RoleInfo> getRoleListByUserId(String userId) {
		return roleDaoMapper.getRoleListByUserId(userId);
	}
    /**
     * 查询角色列表  全部
     */
	public List<RoleInfo> getAllRoleList(int page,int size) {
		int min = (page-1) * size;
		int max = page * size;
		return roleDaoMapper.getAllRoleList(min,size);
	}
	/**
     * 查询角色列表数量
     */
	public int getAllRoleListTotal() {
		return roleDaoMapper.getAllRoleListTotal();
	}
	/**
	 * 新增角色
	 */
	public int addRole(RoleInfo info) {
		return roleDaoMapper.addRole(info);
	}
	/**
	 * 修改角色
	 */
	public int updateRole(RoleInfo info) {
		return roleDaoMapper.updateRole(info);
	}
	/**
	 * 删除角色
	 */
	public int deleteRole(String role_id) {
		return roleDaoMapper.deleteRole(role_id);
	}
	/**
	 * 获得全部资源列表  根据角色ID
	 */
	public List<String> getResourceInfoByRoleId(int role_id) {
		return roleDaoMapper.getResourceInfoByRoleId(role_id);
	}
	/**
	 * 添加角色资源
	 */
	public int addRoleResource(int role_id, int resource_id) {
		return roleDaoMapper.addRoleResource(role_id, resource_id);
	}
	/**
	 * 删除角色的资源
	 */
	public int delRoleResource(int role_id, int resource_id) {
		return roleDaoMapper.delRoleResource(role_id, resource_id);
	}
	/**
	 * 获得全部角色列表
	 */
	public List<RoleInfo> getAllRole() {
		return roleDaoMapper.getAllRole();
	}
	
	/**
	 * 删除用户的角色
	 */
	public int delUserRole(int user_id) {
		return roleDaoMapper.delUserRole(user_id);
	}
	/**
	 * 新增用户的角色
	 */
	public int addUserRole(UserRole ur) {
		return roleDaoMapper.addUserRole(ur);
	}
	

}
