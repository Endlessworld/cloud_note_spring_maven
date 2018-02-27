package com.endless.dao;

import java.util.List;

import org.apache.ibatis.annotations.CacheNamespace;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.endless.entity.User;
import com.endless.entity.note;
import com.endless.entity.noteBook;
/**
 * 
 * cn_note_status_id： 0 : 回收站 ,1 ： 正常 ,2 ： 收藏
 * 
 * @author endless_z
 *
 */
// @isMappers
@CacheNamespace(size=512)
public interface UserMapper {
    @Select("select * from users")
    List<User> getAll();

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class, timeout = 1, isolation = Isolation.DEFAULT)
    @Select("select * from cn_user where id=#{userid} ")
    User loginstate(String userid);

    @Select("SELECT * FROM cn_user WHERE username = #{username}")
    User getOnebyName(String name);

    @Select("SELECT * FROM cn_user WHERE password = #{0} and  id=#{1}")
    User getOnebyPassword(String password, String cn_user_id);

    @Select("select * from cn_notebook where cn_user_id=#{userid}")
    List<noteBook> getbooklist(String userid);

    @Select("select * from cn_note where cn_user_id= #{cn_user_id} and cn_note_status_id=0 ")
    List<note> getRecycleList(String cn_user_id);

    @Select("select * from cn_note where cn_notebook_id= #{notebookid} and cn_note_status_id!=0 ")
    List<note> getnotelist(String notebookid);

    @Insert("insert into cn_user values(#{id},#{username},#{password},'',#{nick})")
    void register(User user);

    @Insert("insert into cn_notebook values(#{cn_notebook_id},#{cn_user_id},#{cn_notebook_type_id},#{cn_notebook_name},#{cn_notebook_desc},#{cn_notebook_createtime})")
    int createNoteBook(noteBook notebook);
   
    @Insert("insert into cn_note values(#{cn_note_id},#{ cn_notebook_id}, #{cn_user_id} ,#{cn_note_status_id}, #{cn_note_type_id}, #{cn_note_title},#{cn_note_body},#{cn_note_create_time},#{cn_note_last_modify_time})")
    int createNote(note note);

    @Update("update cn_note set cn_note_body=#{cn_note_body},cn_note_title=#{cn_note_title},cn_note_status_id=#{cn_note_status_id} where cn_note_id=#{cn_note_id}")
    int noteChange(note note);

    @Delete("delete from cn_note where cn_note_id=#{noteid}")
    int noteDel(String noteid);

    @Update("update cn_notebook set cn_notebook_name=#{1} where cn_notebook_id=#{0}")
    int reBookName(String cn_notebook_id, String newBookName);

    @Update("update cn_note set cn_note_status_id=0 where cn_note_id=#{cn_note_id}")
    int noteToRecycle(String cn_note_id);

    @Update("update cn_note set cn_note_status_id=2 where cn_note_id=#{cn_note_id}")
    int noteToCollection(String cn_note_id);

    @Update("update cn_user set password=#{password} where id=#{id}")
    int update(User user);

    @Update("update cn_note set cn_note_status_id=1,cn_notebook_id=#{0}where cn_note_id=#{1}")
    int recycleToNoteList(String cn_notebook_id, String cn_note_id);
}
