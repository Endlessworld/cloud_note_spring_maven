package com.endless.entity;

import com.fasterxml.jackson.databind.ser.std.SerializableSerializer;

public class User  extends SerializableSerializer{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String id, username, password, token, nick;

    public String getId() {
	return id;
    }

    public User() {

    }

    public User(String id, String password) {
	this.id = id;
	this.password = password;
    }

    public User(String id) {
	this.id = id;
    }

    public void setId(String id) {
	this.id = id;
    }

    public String getUsername() {
	return username;
    }

    public void setUsername(String username) {
	this.username = username;
    }

    public String getPassword() {
	return password;
    }

    public void setPassword(String password) {
	this.password = password;
    }

    public String getToken() {
	return token;
    }

    public void setToken(String token) {
	this.token = token;
    }

    public String getNick() {
	return nick;
    }

    public void setNick(String nick) {
	this.nick = nick;
    }

    @Override
    public String toString() {
	return id + "\n" + username + "\n" + password + "\n" + token + "\n" + nick;
    }
}
