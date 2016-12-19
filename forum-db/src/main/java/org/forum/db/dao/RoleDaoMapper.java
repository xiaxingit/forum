package org.forum.db.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.forum.model.entity.ResourceInfo;
import org.forum.model.entity.RoleInfo;
import org.forum.model.entity.UserRole;

public interface RoleDaoMapper {
	/**
	 * 获得角色列表 根据用户ID
	 * @param userId
	 * @return
	 */
	public List<RoleInfo> getRoleListByUserId(String userId);
	/**
	 * 获得全部角色列表(分页查询)
	 */
	public List<RoleInfo> getAllRoleList(@Param("min")int min,@Param("size")int size);
	/**
	 * 获得全部角色列表
	 */
	public List<RoleInfo> getAllRole();
	/**
	 * 查询全部角色条数
	 */
	public int getAllRoleListTotal();
    /**
     * 添加角色
     */
	public int addRole(RoleInfo info);
	/**
	 * 修改角色
	 */
	public int updateRole(RoleInfo info);
	/**
	 * 删除角色
	 */
	public int deleteRole(String role_id);
	/**
	 * 获得全部资源列表  根据角色ID
	 */
	public List<String> getResourceInfoByRoleId(@Param("role_id")int role_id);
	/**
	 * 为角色新增资源权限
	 */
	public  int addRoleResource(@Param("role_id")int role_id,@Param("resource_id")int resource_id);
	/**
	 * 删除角色资源权限
	 */
	public int delRoleResource(@Param("role_id")int role_id,@Param("resource_id")int resource_id);
	/**
	 * 删除用户的角色
	 */
	public int delUserRole(@Param("user_id")int user_id);
	/**
	 * 新增用户的角色
	 */
	public int addUserRole(UserRole ur);
}
