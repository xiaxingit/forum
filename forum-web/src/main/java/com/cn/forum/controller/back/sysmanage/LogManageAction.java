package com.cn.forum.controller.back.sysmanage;

import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.forum.model.entity.Log;
import org.forum.service.LogService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
/**
 * 日志管理
 * @author xiaxin
 *
 */
@Controller
public class LogManageAction {
	private static final Logger log = Logger.getLogger(LogManageAction.class);
	
	 @Resource(name="logService")
	 LogService logService;
	
	 @RequestMapping("/sysmanage/logManage.html")
	 public String toLogManagePage(){
		 return "redirect:/sysmanage/logList.html";
	 }
	 
	 @RequestMapping("/sysmanage/logList.html")
	 public ModelAndView toPage(String username,String startTime,String endTime,String currPage){
		 ModelAndView mav = new ModelAndView();
		 try {
			int page = 1;
			int size = 10;
			if(currPage !=null && currPage != ""){
				page = Integer.parseInt(currPage);
			}
			int LogTotal = logService.findLogTotal(username, startTime, endTime);
			int pageMax= LogTotal%size==0?LogTotal/size:(LogTotal/size)+1;
			if(page > pageMax && pageMax != 0){
				page = pageMax;
			}
			int min = (page-1) * size;
			List<Log> logList = logService.findLogByPage(username, startTime, endTime, min, size);
			mav.addObject("page",page);
			mav.addObject("size", size);
			mav.addObject("total",LogTotal);
			mav.addObject("pageMax",pageMax);
			mav.addObject("logList",logList);
			mav.addObject("username", username);
			mav.addObject("startTime", startTime);
			mav.addObject("endTime", endTime);
			mav.setViewName("/back/sysmanage/logManage");
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage());
		}
		 return mav;
	 }
}
