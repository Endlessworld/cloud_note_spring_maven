package com.endless.entity;

public class noteBook {
    private String cn_notebook_id, cn_user_id, cn_notebook_name, cn_notebook_desc, cn_notebook_createtime;
    private Integer cn_notebook_type_id;

    public String getCn_user_id() {
	return cn_user_id;
    }

    public void setCn_user_id(String cn_user_id) {
	this.cn_user_id = cn_user_id;
    }

    public String getCn_notebook_name() {
	return cn_notebook_name;
    }

    public void setCn_notebook_name(String cn_notebook_name) {
	this.cn_notebook_name = cn_notebook_name;
    }

    public String getCn_notebook_desc() {
	return cn_notebook_desc;
    }

    public void setCn_notebook_desc(String cn_notebook_desc) {
	this.cn_notebook_desc = cn_notebook_desc;
    }

    public String getCn_notebook_createtime() {
	return cn_notebook_createtime;
    }

    public void setCn_notebook_createtime(String cn_notebook_createtime) {
	this.cn_notebook_createtime = cn_notebook_createtime;
    }

    public String getCn_notebook_id() {
	return cn_notebook_id;
    }

    public void setCn_notebook_id(String cn_notebook_id) {
	this.cn_notebook_id = cn_notebook_id;
    }

    public Integer getCn_notebook_type_id() {
	return cn_notebook_type_id;
    }

    public void setCn_notebook_type_id(Integer cn_notebook_type_id) {
	this.cn_notebook_type_id = cn_notebook_type_id;
    }

    @Override
    public String toString() {
	return cn_notebook_name + "\n" + cn_user_id + "\n" + cn_notebook_id + "\n" + cn_notebook_type_id + "\n"
		+ cn_notebook_createtime;

    }
}
