package com.cn.forum.controller.back.filter;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.UnknownHostException;

import javax.annotation.Resource;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.forum.model.entity.Log;
import org.forum.model.entity.ResourceInfo;
import org.forum.model.entity.UserInfo;
import org.forum.service.LogService;
import org.forum.service.ResourceService;
import org.forum.service.UserService;

public class MyShiroFilter implements Filter {
    
	 @Resource(name="logService")
	 LogService logService;
	
	 @Resource(name="resourceService")
	 ResourceService resourceService;
	 
	 @Resource(name="userService")
	 UserService userService;
	public void init(FilterConfig filterConfig) throws ServletException {
		// TODO Auto-generated method stub

	}

	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		HttpServletRequest httpRequest = (HttpServletRequest)request;
		HttpServletResponse httpresponse = (HttpServletResponse)response;
		httpRequest.setCharacterEncoding("utf-8");
		httpresponse.setCharacterEncoding("utf-8");
		String request_url = httpRequest.getServletPath();
		Subject subject = SecurityUtils.getSubject();
		if(subject.isAuthenticated()){   //��֤ͨ��
			UserInfo currUser = userService.getUserInfoByName(subject.getPrincipal().toString());
			//ajax ����
			if (httpRequest.getHeader("x-requested-with") != null 
                    && "XMLHttpRequest".equalsIgnoreCase(httpRequest.getHeader("x-requested-with"))) {  
				//�û��ѱ�����  ��ת����¼ҳ
			    if("0".equals(currUser.getStatus()) || !"1".equals(currUser.getStatus())){
			    	PrintWriter out = response.getWriter();  
			        out.print("<script>window.parent.location.href='"+httpRequest.getContextPath()+"/back/login.html';</script>");  
			        out.flush(); 
				}else{
				    chain.doFilter(request, response); 
				}
		    //��¼ҳ������ҳ
			}else if(request_url.contains("login.html") || request_url.contains("index.html")){
				chain.doFilter(request, response); 
			//������Դ
			}else{
				//�û��ѱ�����  ��ת����¼ҳ
			    if("0".equals(currUser.getStatus()) || !"1".equals(currUser.getStatus())){
			    	PrintWriter out = response.getWriter();  
			        out.print("<script>window.parent.location.href='"+httpRequest.getContextPath()+"/back/login.html';</script>");  
			        out.flush(); 
			    }else{
					addLog(httpRequest,currUser.getUser_id());
					chain.doFilter(httpRequest, response); 
			    }
			}
		}else{    //δ��֤ ��ת����¼ҳ
			if(request_url.contains("login.html") || request_url.contains("index.html")){
				httpresponse.sendRedirect(httpRequest.getContextPath() + "/back/login.html");
			}else{
				PrintWriter out = response.getWriter();  
		        out.print("<script>window.parent.location.href='"+httpRequest.getContextPath()+"/back/login.html';</script>");  
		        out.flush(); 
			}
		}
	}
    
	public void destroy() {
		// TODO Auto-generated method stub

	}
	/**
	 * ����������־
	 */
	public void addLog(HttpServletRequest request,int user_id){
		
		try{
		String ip = getIpAddr(request);
		String url = request.getServletPath();
		String resource_name = resourceService.findResourceByUrl(url);
		if(resource_name!=null && resource_name!=""){
			Log log = new Log();
			log.setLog_name(resource_name);
			log.setLog_url(url);
			log.setUser_id(user_id+"");
			log.setIp(ip);
			logService.addLog(log);
		}
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	/**
	 * ��ÿͻ���IP
	 * @return
	 */
	private String getIpAddr(HttpServletRequest request) {   
	     String ipAddress = null;   
	     ipAddress = request.getHeader("x-forwarded-for");   
	     if(ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {   
	      ipAddress = request.getHeader("Proxy-Client-IP");   
	     }   
	     if(ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {   
	         ipAddress = request.getHeader("WL-Proxy-Client-IP");   
	     }   
	     if(ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {   
	      ipAddress = request.getRemoteAddr();   
	      if(ipAddress.equals("127.0.0.1")){   
	        //��������ȡ�������õ�IP   
	        InetAddress inet=null;   
		    try {   
		     inet = InetAddress.getLocalHost();   
		    } catch (UnknownHostException e) {   
		     e.printStackTrace();   
		    }   
		    ipAddress= inet.getHostAddress();   
		    }   
	    }   
	     //����ͨ�����������������һ��IPΪ�ͻ�����ʵIP,���IP����','�ָ�   
        if(ipAddress!=null && ipAddress.length()>15){ //"***.***.***.***".length() = 15   
	         if(ipAddress.indexOf(",")>0){   
	             ipAddress = ipAddress.substring(0,ipAddress.indexOf(","));   
	         }   
	    }   
	     return ipAddress;    
	  }   
}
