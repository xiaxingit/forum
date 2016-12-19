package org.forum.model.entity;

public class SysParam {

	
	private int id;
	private String param_name;
	private String param_value;
	private String description;
	private String create_time;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getParam_name() {
		return param_name;
	}
	public void setParam_name(String param_name) {
		this.param_name = param_name;
	}
	public String getParam_value() {
		return param_value;
	}
	public void setParam_value(String param_value) {
		this.param_value = param_value;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getCreate_time() {
		return create_time;
	}
	public void setCreate_time(String create_time) {
		this.create_time = create_time;
	}
	
	
}
