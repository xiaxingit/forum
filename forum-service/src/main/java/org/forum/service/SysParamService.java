package org.forum.service;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.forum.model.entity.SysParam;

public interface SysParamService {
	/**
	 * 查询列表
	 */
	public List<SysParam> findParamList(String param_name,int min,int size);
	/**
	 * 查询列表 总条数
	 */
	public int findParamListTotal(String paran_name);
	/**
	 * 新增参数
	 */
	public int addSysParam(SysParam param);
	/**
	 * 编辑参数
	 */
	public int editSysParam(SysParam param);
	/**
	 * 删除参数
	 */
	public int delSysParam(int param_id);
}
