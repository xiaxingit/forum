package org.forum.service.impl;

import javax.annotation.Resource;

import org.forum.db.dao.UserPwdDaoMapper;
import org.forum.model.entity.UserPwd;
import org.forum.service.UserPwdService;
import org.springframework.stereotype.Service;

@Service("userPwdService")
public class UserPwdServiceImpl implements UserPwdService {
    
	@Resource(name="userPwdDaoMapper")
	UserPwdDaoMapper userPwdDaoMapper;
	
	public UserPwd getUserPwdByUserId(String userId) {
		return userPwdDaoMapper.getUserPwdByUserId(userId);
	}

	public int addUserPwd(UserPwd up) {
		return userPwdDaoMapper.addUserPwd(up);
	}

	public void delUserPwd(int user_id) {
		userPwdDaoMapper.delUserPwd(user_id);
	}

	public int updateUserPwd(UserPwd pwd) {
		return userPwdDaoMapper.updateUserPwd(pwd);
	}

}
