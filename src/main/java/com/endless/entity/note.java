package com.endless.entity;

import com.fasterxml.jackson.databind.ser.std.SerializableSerializer;

public class note  extends SerializableSerializer{
    private String cn_note_id, cn_notebook_id, cn_user_id, cn_note_title, cn_note_body, cn_note_create_time,
	    cn_note_last_modify_time;
    private Integer cn_note_status_id, cn_note_type_id;

    public String getCn_note_id() {
	return cn_note_id;
    }

    public void setCn_note_id(String cn_note_id) {
	this.cn_note_id = cn_note_id;
    }

    public String getCn_notebook_id() {
	return cn_notebook_id;
    }

    public void setCn_notebook_id(String cn_notebook_id) {
	this.cn_notebook_id = cn_notebook_id;
    }

    public String getCn_user_id() {
	return cn_user_id;
    }

    public void setCn_user_id(String cn_user_id) {
	this.cn_user_id = cn_user_id;
    }

    public Integer getCn_note_type_id() {
	return cn_note_type_id;
    }

    public void setCn_note_type_id(Integer cn_note_type_id) {
	this.cn_note_type_id = cn_note_type_id;
    }

    public String getCn_note_title() {
	return cn_note_title;
    }

    public void setCn_note_title(String cn_note_title) {
	this.cn_note_title = cn_note_title;
    }

    public String getCn_note_body() {
	return cn_note_body;
    }

    public void setCn_note_body(String cn_note_body) {
	this.cn_note_body = cn_note_body;
    }

    public String getCn_note_create_time() {
	return cn_note_create_time;
    }

    public void setCn_note_create_time(String cn_note_create_time) {
	this.cn_note_create_time = cn_note_create_time;
    }

    public String getCn_note_last_modify_time() {
	return cn_note_last_modify_time;
    }

    public void setCn_note_last_modify_time(String cn_note_last_modify_time) {
	this.cn_note_last_modify_time = cn_note_last_modify_time;
    }

    @Override
    public String toString() {
	return cn_note_body + cn_note_id + cn_notebook_id + cn_user_id + cn_note_type_id + cn_note_create_time
		+ cn_note_last_modify_time + cn_note_title + cn_note_status_id;

    }

    public Integer getCn_note_status_id() {
	return cn_note_status_id;
    }

    public void setCn_note_status_id(Integer cn_note_status_id) {
	this.cn_note_status_id = cn_note_status_id;
    }
}
