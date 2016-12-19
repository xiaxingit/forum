package org.forum.db.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.forum.model.entity.Log;

/**
 * 日志
 * @author xiaxin
 *
 */
public interface LogDaoMapper {

	/**
	 * 新增日志
	 */
	public int addLog(Log log);
	/**
	 * 查询日志列表
	 * 
	 */
	public List<Log> findLogByPage(@Param("username")String userName,@Param("startT")String startTime,@Param("endT")String endTime,
			@Param("min")int min,@Param("size")int size);
	/**
	 * 查询日志总条数
	 */
	public int findLogTotal(@Param("username")String userName,@Param("startT")String startTime,@Param("endT")String endTime);
}
