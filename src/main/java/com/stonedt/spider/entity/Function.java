package com.stonedt.spider.entity;



public class Function {

   
	private String id;

    private String name;

    private String parameter;

    private String script;

    private String createDate;
    
    private String explain_data;
    private int type;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getParameter() {
		return parameter;
	}
	public void setParameter(String parameter) {
		this.parameter = parameter;
	}
	public String getScript() {
		return script;
	}
	public void setScript(String script) {
		this.script = script;
	}
	public String getCreateDate() {
		return createDate;
	}
	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}
	public String getExplain_data() {
		return explain_data;
	}
	public void setExplain_data(String explain_data) {
		this.explain_data = explain_data;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public Function(String id, String name, String parameter, String script, String createDate, String explain_data,
			int type) {
		super();
		this.id = id;
		this.name = name;
		this.parameter = parameter;
		this.script = script;
		this.createDate = createDate;
		this.explain_data = explain_data;
		this.type = type;
	}
	public Function() {
		super();
	}
	
    

    
}
