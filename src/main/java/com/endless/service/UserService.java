package com.endless.service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.UUID;

import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.defaults.DefaultSqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import com.endless.dao.UserMapper;
import com.endless.entity.JsonResult;
import com.endless.entity.User;
import com.endless.entity.note;
import com.endless.entity.noteBook;

@Service
public class UserService {
    @Autowired
    UserMapper mapper;
    @Autowired
    DataSourceTransactionManager transactionManager;
    @Autowired
    DefaultSqlSession  SqlSession;
    @Autowired
    SqlSessionFactory sqlSessionFactory;
    public JsonResult login(String name, String password) {
    	 
    	
	System.err.println("当前密码加密\t" + DigestUtils.md5DigestAsHex(password.getBytes()).toString());
	User user = mapper.getOnebyName(name);
	if (user == null) {
	    return new JsonResult(1, "没有此用户");
	} else {
	    System.out.println("登录" + name + "\t" + password + "\n" + user.toString());
	    boolean login = user.getPassword().equals(DigestUtils.md5DigestAsHex(password.getBytes()).toString());
	    return login ? new JsonResult(user, "欢迎登录") : new JsonResult(2, "密码错误");
	}
    }

    public JsonResult update(String cn_user_id, String password, String newPassword) {
	newPassword = DigestUtils.md5DigestAsHex(newPassword.getBytes()).toString();
	password = DigestUtils.md5DigestAsHex(password.getBytes()).toString();
	
	User userCheck = mapper.getOnebyPassword(password, cn_user_id);
	int x = userCheck != null ? mapper.update(new User(cn_user_id, newPassword)) : 0;
	System.out.println("修改密码" + (x == 1 ? "成功" : "失败"));
	return x > 0 ? new JsonResult(userCheck, "修改成功") : new JsonResult(1, "修改失败");
    }

    public List<noteBook> getbooklist(String userid) {
	System.out.println("获取笔记本列表" + userid);
	return mapper.getbooklist(userid);
    }

    public List<note> getnotelist(String notebookid) {
	System.out.println("获取笔记列表 notebookid:" + notebookid);
	return mapper.getnotelist(notebookid);
    }

    public User loginstate(String username, String userid) {
	System.out.println("登录状态检测,userid:" + userid);
	User user = mapper.loginstate(userid);
	User field = new User("999999999");
	return user == null ? field : user;
    }

    public User register(String username, String password, String nick) {
	if (mapper.getOnebyName(username) == null) {
	    System.out.println("用户注册");
	    User newUser = new User(UUID.randomUUID().toString());
	    newUser.setPassword(DigestUtils.md5DigestAsHex(password.getBytes()).toString());
	    newUser.setUsername(username);
	    newUser.setNick(nick);
	    mapper.register(newUser);
	    User user = mapper.getOnebyName(username);
	    System.out.println(user != null ? "注册成功！" : "注册失败");
	    return user != null ? user : new User(null);
	} else {
	    return new User("111111");
	}
    }

    public JsonResult noteChange(String cn_note_body, String cn_note_id, String cn_note_title,
	    Integer cn_note_status_id) {
	System.out.println(cn_note_body + "\n" + cn_note_id);
	note note = new note();
	note.setCn_note_body(cn_note_body);
	note.setCn_note_id(cn_note_id);
	note.setCn_note_title(cn_note_title);
	note.setCn_note_status_id(cn_note_status_id);
	note.setCn_note_last_modify_time(now);
	int x = mapper.noteChange(note);
	JsonResult res = new JsonResult(x, x == 1 ? "修改成功" : "修改失败");
	System.out.println("修改笔记：\n" + note);
	System.out.println(res);
	return res;
    }

    private String now = new SimpleDateFormat("yyyy-M-d hh:mm:ss").format(new Date().getTime());

    public JsonResult createNoteBook(String cn_notebook_name, String cn_user_id) {
	System.out.println(cn_notebook_name + cn_user_id);
	noteBook notebook = new noteBook();
	notebook.setCn_notebook_name(cn_notebook_name);
	notebook.setCn_notebook_id(UUID.randomUUID().toString());
	notebook.setCn_user_id(cn_user_id);
	notebook.setCn_notebook_createtime(now);
	notebook.setCn_notebook_type_id(new Random().nextInt(4 + 1));
	notebook.setCn_notebook_desc("创建");
	System.out.println(notebook);
	int x = mapper.createNoteBook(notebook);
	return new JsonResult(x, x == 1 ? notebook.getCn_notebook_name() + "创建成功" : "创建失败");
    }

    public JsonResult createNote(String cn_note_title, String cn_user_id, String cn_notebook_id) {
	System.out.println("创建笔记");
	note note = new note();
	note.setCn_note_status_id(1);
	note.setCn_note_create_time(now);
	note.setCn_user_id(cn_user_id);
	note.setCn_note_title(cn_note_title);
	note.setCn_notebook_id(cn_notebook_id);
	note.setCn_note_id(UUID.randomUUID().toString());
	note.setCn_note_status_id(1);
	note.setCn_note_type_id(new Random().nextInt(4 + 1));
	note.setCn_note_body("");
	System.out.println(note);
	int x = mapper.createNote(note);
	return new JsonResult(x, x == 1 ? cn_note_title + "创建成功" : "创建失败");
    }

    public JsonResult noteDel(String noteid) {
	System.out.println("删除笔记" + noteid);
	int x = mapper.noteDel(noteid);
	return new JsonResult(x, x == 1 ? "删除成功" : "删除失败");
    }

    public JsonResult reBookName(String cn_notebook_id, String newBookName) {
	System.out.println("修改笔记本儿 \t" + cn_notebook_id + newBookName);
	int x = mapper.reBookName(cn_notebook_id, newBookName);
	return new JsonResult(x, x == 1 ? "修改成功" : "修改失败");
    }

    public JsonResult noteToRecycle(String cn_note_id) {
	System.out.println("移动笔记到回收站" + cn_note_id);
	int x = mapper.noteToRecycle(cn_note_id);
	return new JsonResult(x, x == 1 ? "移动到回收站成功" : "移动到回收站失败");
    }

    public JsonResult noteToCollection(String cn_note_id) {
	System.out.println("移动笔记到收藏夹" + cn_note_id);
	int x = mapper.noteToCollection(cn_note_id);
	return new JsonResult(x, x == 1 ? "收藏成功" : "收藏失败");
    }

    public List<note> getRecycleList(String cn_user_id) {
	System.out.println("获取回收站列表" + cn_user_id);
	return mapper.getRecycleList(cn_user_id);
    }

    public JsonResult recycleToNoteList(String cn_notebook_id, String cn_note_id) {
	int x = mapper.recycleToNoteList(cn_notebook_id, cn_note_id);
	return new JsonResult(x, x == 1 ? "恢复成功" : "恢复失败");

    }

}
