package com.endless.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.endless.entity.JsonResult;
import com.endless.entity.User;
import com.endless.entity.note;
import com.endless.entity.noteBook;
import com.endless.service.UserService;

@RestController
public class WebAppController {
    @Autowired
    public UserService userService;

    @RequestMapping(value = "/notechange.do")
    public JsonResult noteChange(String notebody, String noteid, String notetitle, Integer statusid) {

	return userService.noteChange(notebody, noteid, notetitle, statusid);
    }

    @RequestMapping(value = "/rebookname.do")
    public JsonResult reBookName(String cn_notebook_id, String newBookName) {
	return userService.reBookName(cn_notebook_id, newBookName);
    }

    @RequestMapping(value = "/notedel.do")
    public JsonResult notedel(String noteid) {
	return userService.noteDel(noteid);
    }

    @RequestMapping(value = "/notetorecycle.do")
    public JsonResult noteToRecycle(String cn_note_id) {
	return userService.noteToRecycle(cn_note_id);
    }

    @RequestMapping(value = "/recycleToNoteList.do")
    public JsonResult recycleToNoteList(String cn_notebook_id, String cn_note_id) {
	return userService.recycleToNoteList(cn_notebook_id, cn_note_id);
    }

   @RequestMapping(value = "/getrecyclelist.do")
    public List<note> getRecycleList(String cn_user_id) {
	return userService.getRecycleList(cn_user_id);
    }

    @RequestMapping(value = "/notetocollection.do")
    public JsonResult noteToCollection(String cn_note_id) {
	return userService.noteToCollection(cn_note_id);
    }

    @RequestMapping(value = "/createNoteBook.do")
    public JsonResult createNoteBook(String cn_notebook_name, String cn_user_id) {
	return userService.createNoteBook(cn_notebook_name, cn_user_id);
    }

    @RequestMapping(value = "/createNote.do")
    public JsonResult createNote(String cn_note_title, String cn_user_id, String cn_notebook_id) {
	return userService.createNote(cn_note_title, cn_user_id, cn_notebook_id);
    }

    @RequestMapping(value = "/login.do")
    public JsonResult login(String username, String password) {
	return userService.login(username, password);
    }

    @RequestMapping(value = "/loginstate.do")
    public User loginstate(String username, String userid) {
	return userService.loginstate(username, userid);
    }

    @RequestMapping(value = "/update.do")
    public JsonResult update(String userid, String lpassword, String npassword) {
	return userService.update(userid, lpassword, npassword);
    }

    @RequestMapping(value = "/getnotebook.do")
    public List<noteBook> getnotebook(String userid) {
	return userService.getbooklist(userid);
    }

    @RequestMapping(value = "/register.do")
    public User register(String username, String password, String nick) {
	return userService.register(username, password, nick);
    }

    @RequestMapping(value = "/getnotelist.do")
    public List<note> getnotelist(String notebookid) {
	return userService.getnotelist(notebookid);
    }
}
