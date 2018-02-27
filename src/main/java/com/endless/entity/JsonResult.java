package com.endless.entity;

public class JsonResult {
    public int state = 0;
    public String msg;
    public Object obj;

    public JsonResult() {
    }

    public JsonResult(Object obj, String msg) {
	this.state = 0;
	this.msg = msg;
	this.obj = obj;
    }

    public JsonResult(int state, String msg) {
	super();
	this.state = state;
	this.msg = msg;
    }

    @Override
    public String toString() {
	return msg + state;
    }
}
