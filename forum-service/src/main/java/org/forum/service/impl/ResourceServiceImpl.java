package org.forum.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.forum.db.dao.ResourceDaoMapper;
import org.forum.model.entity.ResourceInfo;
import org.forum.service.ResourceService;
import org.springframework.stereotype.Service;

@Service("resourceService")
public class ResourceServiceImpl implements ResourceService {

	@Resource(name="resourceDaoMapper")
	ResourceDaoMapper resourceDaoMapper;
	
	public List<ResourceInfo> getAllResource(String userId) {
		return resourceDaoMapper.getAllResource(userId);
	}
    /**
     * 新增资源
     */
	public int addResource(ResourceInfo info) {
		return resourceDaoMapper.addResource(info);
	}
    /**
     * 修改资源
     */
	public int editResource(ResourceInfo info) {
		return resourceDaoMapper.editResource(info);
	}
    /**
     * 删除资源
     */
	public int deleResource(ResourceInfo info) {
		return resourceDaoMapper.deleResource(info);
	}
	/**
	 * 根据资源的路径 查询资源信息
	 */
	public String findResourceByUrl(String url) {
		return resourceDaoMapper.findResourceByUrl(url);
	}

}
