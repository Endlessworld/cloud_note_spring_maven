package com.endless.entity;

import com.fasterxml.jackson.databind.ser.std.SerializableSerializer;

public class JsonResult  extends SerializableSerializer{
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
