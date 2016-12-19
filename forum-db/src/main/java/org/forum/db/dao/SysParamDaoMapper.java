package org.forum.db.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.forum.model.entity.SysParam;
/**
 * 系统参数
 * @author xiaxin
 *
 */
public interface SysParamDaoMapper {

	/**
	 * 查询列表
	 */
	public List<SysParam> findParamList(@Param("pname")String param_name,@Param("min")int min,@Param("size")int size);
	/**
	 * 查询列表 总条数
	 */
	public int findParamListTotal(@Param("pname")String paran_name);
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
