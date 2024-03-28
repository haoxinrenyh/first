package com.stonedt.spider.param;

import lombok.Data;

@Data
public class ParamES {

    private String type;
    private String name;
    private Object value;
    
    
    


    public String getType() {
		return type;
	}





	public void setType(String type) {
		this.type = type;
	}





	public String getName() {
		return name;
	}





	public void setName(String name) {
		this.name = name;
	}





	public Object getValue() {
		return value;
	}





	public void setValue(Object value) {
		this.value = value;
	}





	public boolean isEmpty(){
        if( getType()==null || getType().equals("") || getName()==null || getName().equals("") || getValue()==null || getValue().equals("") ){
            return false;
        }else {
            return true;
        }
    }
}
