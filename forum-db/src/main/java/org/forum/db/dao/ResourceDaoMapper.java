package org.forum.db.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.forum.model.entity.ResourceInfo;

/**
 * 资源管理
 * @author xx
 *
 */
public interface ResourceDaoMapper {
	/**
	 * 查询用户的资源列表
	 * @param userId
	 * @return
	 */
	public List<ResourceInfo> getAllResource(String userId);
	/**
	 * 新增资源
	 */
	public int addResource(ResourceInfo info);
	/**
	 * 修改资源
	 */
	public int editResource(ResourceInfo info);
	/**
	 * 删除资源
	 */
	public int deleResource(ResourceInfo info);
	/**
	 * 根据资源的路径 查询资源信息
	 */
	public String findResourceByUrl(String url);
}
