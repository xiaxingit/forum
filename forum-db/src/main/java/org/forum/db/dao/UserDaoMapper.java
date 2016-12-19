package org.forum.db.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.forum.model.entity.UserInfo;
import org.forum.model.entity.UserPwd;

public interface UserDaoMapper {
	/**
	 * 根据用户名 查询用户信息
	 * @param name
	 * @return
	 */
	public UserInfo getUserInfoByName(String name);

	/**
	 * 修改用户信息
	 * @param userInfo
	 * @return
	 */
	public int      updateUserInfo(UserInfo userInfo);
	/**
	 * 查询用户总条数
	 * @return
	 */
	public int findUserTotal();
    /**
     * 查询用户列表  （分页）
     * min 从多少条开始
     * size 获得多少条
     */
	public List<UserInfo> findUserList(@Param("min")int min,@Param("size")int size);
	/**
	 * 新增用户
	 */
	public int addUserInfo(UserInfo info);
	/**
	 * 删除用户
	 */
	public int delUserInfo(@Param("user_id")int user_id);
	
	/**
	 * 查询最大的用户ID
	 */
	public int selectMaxUserId();
	/**
	 * 修改用户状态
	 */
	public int updateUserStatus(@Param("user_id")int user_id,@Param("status")int status);
}
