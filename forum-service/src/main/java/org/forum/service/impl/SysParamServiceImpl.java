package org.forum.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.forum.db.dao.SysParamDaoMapper;
import org.forum.model.entity.SysParam;
import org.forum.service.SysParamService;
import org.springframework.stereotype.Service;

@Service("sysParamService")
public class SysParamServiceImpl implements SysParamService {
    @Resource(name="sysParamDaoMapper")
    SysParamDaoMapper sysParamDaoMapper;
    
	public List<SysParam> findParamList(String param_name, int min, int size) {
		return sysParamDaoMapper.findParamList(param_name, min, size);
	}

	public int findParamListTotal(String paran_name) {
		return sysParamDaoMapper.findParamListTotal(paran_name);
	}

	public int addSysParam(SysParam param) {
		return sysParamDaoMapper.addSysParam(param);
	}

	public int editSysParam(SysParam param) {
		return sysParamDaoMapper.editSysParam(param);
	}

	public int delSysParam(int param_id) {
		return sysParamDaoMapper.delSysParam(param_id);
	}

}
