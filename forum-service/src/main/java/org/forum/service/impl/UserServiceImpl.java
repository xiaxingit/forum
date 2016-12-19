package org.forum.service.impl;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;

import javax.annotation.Resource;

import org.forum.db.dao.UserDaoMapper;
import org.forum.model.entity.UserInfo;
import org.forum.service.UserService;
import org.springframework.stereotype.Service;
          
@Service("userService")
public class UserServiceImpl implements UserService {
    
	@Resource(name="userDaoMapper")
	UserDaoMapper userDaoMapper;
	
	public int findUserTotal() {
		return userDaoMapper.findUserTotal();
	}

	public UserInfo getUserInfoByName(String name) {
		return userDaoMapper.getUserInfoByName(name);
	}


	public int updateUserInfo(UserInfo userInfo) {
		return userDaoMapper.updateUserInfo(userInfo);
	}
	 /**
     * 查询用户列表  （分页）
     * min 从多少条开始
     * size 获得多少条
     */
	public List<UserInfo> findUserList(int page, int size) {
		int min = (page-1) * size;
		return userDaoMapper.findUserList(min, size);
	}
	/**
	 * 新增用户
	 */
	public int addUserInfo(UserInfo info) {
		return userDaoMapper.addUserInfo(info);
	}
	/**
	 * 删除用户
	 */
	public int delUserInfo(int user_id) {
		return userDaoMapper.delUserInfo(user_id);
	}
	/**
	 * 查询最大的用户ID
	 */
	public int selectMaxUserId() {
		return userDaoMapper.selectMaxUserId();
	}
    /**
     * 修改用户状态
     */
	public int updateUserStatus(int user_id, int status) {
		return userDaoMapper.updateUserStatus(user_id, status);
	}

}
