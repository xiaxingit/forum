package org.forum.model.entity;

public class ResourceInfo {
	
	private int id;
	private String name;
	private String url;
	private String parent_id;
	private String resource_order;
	private String describe;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getParent_id() {
		return parent_id;
	}
	public void setParent_id(String parent_id) {
		this.parent_id = parent_id;
	}
	public String getResource_order() {
		return resource_order;
	}
	public void setResource_order(String resource_order) {
		this.resource_order = resource_order;
	}
	public String getDescribe() {
		return describe;
	}
	public void setDescribe(String describe) {
		this.describe = describe;
	}
	
	
}
