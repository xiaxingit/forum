package org.forum.model.entity;

public class Log {
	 
	 private int id;
	 private String log_name;
	 private String log_url;
	 private String user_id;
	 private String ip;
	 private String create_time;
	 
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getLog_name() {
		return log_name;
	}
	public void setLog_name(String log_name) {
		this.log_name = log_name;
	}
	public String getLog_url() {
		return log_url;
	}
	public void setLog_url(String log_url) {
		this.log_url = log_url;
	}
	public String getUser_id() {
		return user_id;
	}
	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	public String getCreate_time() {
		return create_time;
	}
	public void setCreate_time(String create_time) {
		this.create_time = create_time;
	}
	 

}
