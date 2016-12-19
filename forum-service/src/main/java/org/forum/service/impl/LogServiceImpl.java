package org.forum.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.forum.db.dao.LogDaoMapper;
import org.forum.model.entity.Log;
import org.forum.service.LogService;
import org.springframework.stereotype.Service;

@Service("logService")
public class LogServiceImpl implements LogService {
	
    @Resource(name="logDaoMapper")
    LogDaoMapper logDaoMapper;
    
	public int addLog(Log log) {
		return logDaoMapper.addLog(log);
	}

	public List<Log> findLogByPage(String userName, String startTime,
			String endTime, int min, int size) {
		return logDaoMapper.findLogByPage(userName, startTime, endTime, min, size);
	}

	public int findLogTotal(String userName, String startTime, String endTime) {
		return logDaoMapper.findLogTotal(userName, startTime, endTime);
	}

}
