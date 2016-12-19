package com.cn.forum.controller.back.sysmanage;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.forum.model.entity.ResourceInfo;
import org.forum.service.ResourceService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class ResourceManageAction {
	private static final Logger log = Logger.getLogger(ResourceManageAction.class);
	
	@Resource(name="resourceService")
	ResourceService resourceService;
	 
	@RequestMapping("/sysmanage/resourceManage.html")
	public String toPage(){
		return "/back/sysmanage/resourceManage";
	}
	
	/**
	 * 获取全部资源信息
	 * @return
	 */
	@RequestMapping("/sysmanage/getResourceList.html")
	@ResponseBody
	public List<ResourceInfo> getResourceList(){
		try{
		List<ResourceInfo> list = resourceService.getAllResource(null);
		return list;
		}catch(Exception e){
			log.error(e.getMessage());
			return null;
		}
	}
	/*
	 * 添加资源
	 */
	@RequestMapping("/sysmanage/addResource.html")
	@ResponseBody
	public String addResource(HttpServletRequest request,HttpServletResponse response){
		ResourceInfo info = new ResourceInfo();
		try{
		info.setName(request.getParameter("name"));
		info.setParent_id(request.getParameter("parent_id"));
		info.setUrl(request.getParameter("url"));
		info.setResource_order(request.getParameter("resource_order"));
		info.setDescribe(request.getParameter("describe"));
		resourceService.addResource(info);
		return "1";
		}catch(Exception e){
			log.error(e.getMessage());
			return "-1";
		}
	}
	/**
	 * 编辑资源
	 */
	@RequestMapping("/sysmanage/editResource.html")
	@ResponseBody
	public String editResource(HttpServletRequest request,HttpServletResponse response){
		ResourceInfo info = new ResourceInfo();
		try{
		info.setId(Integer.parseInt(request.getParameter("id")));
		info.setName(request.getParameter("name").trim());
		info.setParent_id(request.getParameter("parent_id"));
		info.setUrl(request.getParameter("url").trim());
		info.setResource_order(request.getParameter("resource_order"));
		info.setDescribe(request.getParameter("describe").trim());
		resourceService.editResource(info);
		return "1";
		}catch(Exception e){
			log.error(e.getMessage());
			return "-1";
		}
	}
	/**
	 * 删除资源
	 */
	@RequestMapping("/sysmanage/deleResource.html")
	@ResponseBody
	public String deleteResource(HttpServletRequest request,HttpServletResponse response){
		ResourceInfo info = new ResourceInfo();
		try{
		info.setId(Integer.parseInt(request.getParameter("id")));
		resourceService.deleResource(info);
		return "1";
		}catch(Exception e){
			log.error(e.getMessage());
			return "-1";
		}
	}
}
