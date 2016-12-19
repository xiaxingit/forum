package org.forum.service;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.forum.model.entity.Log;

public interface LogService {
	/**
	 * 新增日志
	 */
	public int addLog(Log log);
	/**
	 * 查询日志列表
	 * 
	 */
	public List<Log> findLogByPage(String userName,String startTime,String endTime,int min,int size);
	/**
	 * 查询日志总条数
	 */
	public int findLogTotal(String userName,String startTime,String endTime);
}
