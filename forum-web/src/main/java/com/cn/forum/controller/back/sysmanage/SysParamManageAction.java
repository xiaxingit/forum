package com.cn.forum.controller.back.sysmanage;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.forum.model.entity.Log;
import org.forum.model.entity.SysParam;
import org.forum.service.SysParamService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class SysParamManageAction {
	private static final Logger log = Logger.getLogger(SysParamManageAction.class);
	
	@Resource(name="sysParamService")
	SysParamService sysParamService;
	
	/**
	 * 跳转到系统参数管理
	 */
	@RequestMapping("/sysmanage/sysParamManage.html")
	public String toPage(){
		return "redirect:/sysmanage/getSysParamList.html";
	}
	
	/**
	 * 查询参数列表
	 */
	@RequestMapping("/sysmanage/getSysParamList.html")
	public ModelAndView getSysParamList(String pname,String currPage){
		ModelAndView mav = new ModelAndView();
		try{
			int page = 1;
			int size = 10;
			if(currPage !=null && currPage != ""){
				page = Integer.parseInt(currPage);
			}
			int Total = sysParamService.findParamListTotal(pname);
			int pageMax= Total%size==0?Total/size:(Total/size)+1;
			if(page > pageMax && pageMax != 0){
				page = pageMax;
			}
			int min = (page-1) * size;
			List<SysParam> List = sysParamService.findParamList(pname, min, size);
			mav.addObject("page",page);
			mav.addObject("size", size);
			mav.addObject("total",Total);
			mav.addObject("pageMax",pageMax);
			mav.addObject("paramList",List);
			mav.addObject("pname", pname);
			mav.setViewName("/back/sysmanage/paramManage");
		}catch(Exception e){
			log.error(e.getMessage());
		}
		return mav;
	}
	/**
	 * 删除系统参数
	 */
	@RequestMapping("/sysmanage/delSysParam.html")
	@ResponseBody
	public String delSysParam(int param_id){
		try{
			sysParamService.delSysParam(param_id);
			return "1";
		}catch(Exception e){
			log.error(e.getMessage());
			return null;
		}
		
	}
	/**
	 * 新增系统参数
	 */
	@RequestMapping("/sysmanage/addSysParam.html")
	@ResponseBody
	public String addSysParam(SysParam param){
		try {
			int result = sysParamService.addSysParam(param);
			return result+"";
		} catch (Exception e) {
			log.error(e.getMessage());
			return null;
		}
	}
	/**
	 * 修改系统参数
	 */
	@RequestMapping("/sysmanage/editSysParam.html")
	@ResponseBody
	public String editSysParam(SysParam param){
		try {
			int result = sysParamService.editSysParam(param);
			return result+"";
		} catch (Exception e) {
			log.error(e.getMessage());
			return null;
		}
	}
}
