package com.cn.forum.controller.back.realm;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.MemoryConstrainedCacheManager;
import org.apache.shiro.cache.ehcache.EhCacheManager;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.forum.model.entity.ResourceInfo;
import org.forum.model.entity.RoleInfo;
import org.forum.model.entity.UserInfo;
import org.forum.model.entity.UserPwd;
import org.forum.service.ResourceService;
import org.forum.service.RoleService;
import org.forum.service.UserPwdService;
import org.forum.service.UserService;
import org.forum.util.MyDesUtils;
import org.springframework.stereotype.Component;

@Component
public class MyShiroRealm extends AuthorizingRealm {
	
	@Resource(name="roleService")
    RoleService roleService;
	
	@Resource(name="resourceService")
    ResourceService resourceService;
	
	@Resource(name="userService")
	UserService userService;
	
	@Resource(name="userPwdService")
	UserPwdService userPwdService;
	
	@Resource
	private MemoryConstrainedCacheManager cacheManager;
	/* 
	 * 授权
	 */
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) { 
		try {
	        String loginName = (String) principals.fromRealm(getName()).iterator().next();  
	        UserInfo userInfo = userService.getUserInfoByName(loginName);
	        if(userInfo!=null){
		        Set<String> roleNames = new HashSet<String>();  
		        Set<String> permissions = new HashSet<String>();  
		        List<RoleInfo> roleList = roleService.getRoleListByUserId(userInfo.getUser_id()+"");
		        List<ResourceInfo> resourceList = resourceService.getAllResource(userInfo.getUser_id()+"");
		        for(RoleInfo role:roleList){
		        	  roleNames.add(role.getRole_name()); 
		        }
		        for(ResourceInfo resource:resourceList){
		        	  permissions.add(resource.getName());   
		        }
		        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo(roleNames);  
		        info.setStringPermissions(permissions);  
		        return info;  
	        }else{
	        	return null;
	        }
		} catch (Exception e) {
			throw new UnknownAccountException();
		}
	}
	
	
	/* --/0
	 * 
	 * 
	 * 登录
	 */
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authcToken) throws AuthenticationException {
		MyDesUtils desUtil = null;
		UsernamePasswordToken token = (UsernamePasswordToken) authcToken;
		//用户名
        String token_username = token.getUsername();
        //密码
        String token_password = new String(token.getPassword());
        UserInfo userInfo = null;
        UserPwd userPwd = null;
        try {
        	desUtil = new MyDesUtils();
        	userInfo = userService.getUserInfoByName(token_username);
		    if(userInfo!=null){
		    	userPwd = userPwdService.getUserPwdByUserId(userInfo.getUser_id()+"");
		    	if(desUtil.encrypt(token_password)==userPwd.getPwd() || desUtil.encrypt(token_password).equals(userPwd.getPwd())){
		    		//若存在，将此用户存放到登录认证info中  
		    		AuthenticationInfo info = new SimpleAuthenticationInfo(token_username, token_password, getName());
		    		clearCache(info.getPrincipals());
		    		Cache<Object, Object> cache  = cacheManager.getCache("sessionCache");
		    		cache.put(token_username, SecurityUtils.getSubject().getSession().getId().toString());
		    		return info; 
		    	}else{
		    		throw new IncorrectCredentialsException();// 密码错误
		    	}
		    }
	    } catch (Exception e) {
			throw new AuthenticationException();  
		}
        return null;
	}

}
