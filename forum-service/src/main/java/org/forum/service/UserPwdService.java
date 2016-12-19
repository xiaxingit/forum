package org.forum.service;

import org.apache.ibatis.annotations.Param;
import org.forum.model.entity.UserPwd;

/**
 * @author xiaxin
 *
 */
public interface UserPwdService {
	/**
	 * 查询用户密码
	 * @param userId
	 * @return
	 */
	public UserPwd  getUserPwdByUserId(String userId);
	/**
	 * 新增用户密码
	 */
	public int addUserPwd(UserPwd up);
	/**
	 * 删除该用户的密码
	 */
	public void delUserPwd(int user_id);
	/**
	 * 修改密码
	 */
	public int  updateUserPwd(UserPwd pwd);
}
