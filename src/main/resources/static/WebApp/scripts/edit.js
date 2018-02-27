/**
 * edit.html
 */
//加载DOM之后处理页面高度
 			var userid=$.cookie('userid');
			function closeMsg(){
				 console.log('关闭提示窗口');
				 $('.opacity_bg').hide(600);
				 $('#can').hide(300);
			 }
			function get_dom(e){
				return document.getElementById(e);
			}
			
			function set_height(){
				var pc_height=window.innerHeight;
				pc_height=pc_height-132;
				get_dom('first_side_right').style.height=(pc_height-31)+'px';
				get_dom('second_side_right').style.height=pc_height+'px';
				get_dom('four_side_right').style.height=pc_height+'px';
				get_dom('sixth_side_right').style.height=pc_height+'px';
				get_dom('seventh_side_right').style.height=pc_height+'px';
				get_dom('eighth_side_right').style.height=pc_height+'px';
				get_dom('third_side_right').style.height=(pc_height-15)+'px';
				get_dom('fifth_side_right').style.height=(pc_height-15)+'px';
			}
			function myEditorWidth(){
				var dom=get_dom('third_side_right');
				var style=dom.currentStyle||window.getComputedStyle(dom, null);
				get_dom('myEditor').style.width=style.width;
			}
			set_height();
			//改变窗口大小时调整页面尺寸
			window.onresize=function(){
				set_height();
				var width=$('#third_side_right').width()-35;
				$('.edui-container,.edui-editor-body').width(width);
				$('#myEditor').width(width-20);
			};
			function get(str){
			     return document.getElementById(str);
			 }
			$(".profile-username").html($.cookie("username"));
			var userid=$.cookie('userid');
			console.log("获取笔记本列表userid"+userid);
			var noteBookList;
			function shownotebook(){
				for(var i=0;i<300;i++){
					$("#booklists").remove();
			 	}
				$.post("getnotebook.do",{"userid":userid},
					function (res){
							noteBookList=res;
							console.log("返回"+res.length+"个笔记本目录");
							for(var i=0;i<res.length;i++){
								 var str='<li class="online"><a  ondblclick="reBookName(this)"  onclick="shownote(this)" class="checked" id="booklists" name="'+res[i].cn_notebook_id+'"><i class="fa fa-book" title="online" rel="tooltip-bottom"></i>'+res[i].cn_notebook_name+'</a></li>';
								 $("#booklist").after(str);
							}
					 });
			}shownotebook();
			var	bookid;
			var bookNode;
			var notemenu= '<div class="note_menu" tabindex="-1"> <dl>'+
				'<dt><button type="button" onclick="noteMove()" class="btn btn-default btn-xs btn_move" title="移动至..."><i class="fa fa-random"></i></button></dt>'+
				'<dt><button type="button" onclick="noteShare()" class="btn btn-default btn-xs btn_share" title="分享"><i class="fa fa-sitemap"></i></button></dt>'+
				'<dt><button type="button" onclick="noteToRecycle()" class="btn btn-default btn-xs btn_delete" title="删除"><i class="fa fa-times"></i></button></dt></dl></div></li>'
			
				function shownote(book){
				console.log("显示笔记列表");
				bookid=book.name;
				bookNode=book;
			 	for(var i=0;i<300;i++){
			 		$("#notelist").remove();
			 	}
				console.log("当前bookid:"+bookid);
				$.post("getnotelist.do",{"notebookid":bookid},
						function (res){
							console.log("返回"+res.length+"个笔记目录");
							 for(var i=0;i<res.length;i++){
								 var noteStr=JSON.stringify(res[i]);
								 var str= '<li class="online" id="notelist">'+
									 '<a title="'+res[i].cn_note_title+'"  onclick="noteEditor(this)" class="checked" id="note'+i+'">'+
									 '<i class="fa fa-file-text-o" title="online" rel="tooltip-bottom"></i><span>'+res[i].cn_note_title+'</span>'+
									 '<button type="button" onclick="noteSelectDown()" class="btn btn-default btn-xs btn_position btn_slide_down">'+
									 '<i class="fa fa-chevron-down"></i></button></a>';
								 $("#notelists").append(str+notemenu);
								 var note=$("#note"+i);
								 note.data("note_id",res[i].cn_note_id);
								 note.data("note_body",res[i].cn_note_body);
								 note.data("cn_note_title",res[i].cn_note_title);
							}
						 });
			}
//			document.querySelector(),
// 			res[i].cn_note_id,
// 			res[i].cn_notebook_id,
// 			res[i].cn_user_id, 
// 			res[i].cn_note_status_id, 
// 			res[i].cn_note_type_id, 
// 			res[i].cn_note_title,
// 			res[i].cn_note_body, 
// 			res[i].cn_note_create_time,
// 			res[i].cn_note_last_modify_time;
			var newBookName;
			function reBookName(booka) {
				console.log("笔记本重命名");
				window.alert=function(){
					$('#can').load('./alert/alert_rename.html',function(){
						$('.opacity_bg').show(10);
						$('#can').show(500); 
						$('#input_notebook_rename').val(booka.innerText);
						$(".close,#close").click(function() {
						  closeMsg();
						});
						get("input_notebook_rename").onblur=function (){
							 newBookName=this.value;
							 console.log(newBookName);
						}  
					});
				}
			 alert();
			 alertChange();
			}
			$("#rollback_button").click(function() {
				var nowNode=get("pc_part_4");
				if(nowNode.style.display=="none"){
				   console.log("显示回收站");
				   $(nowNode).show(500);
				   showRecycleList();
				   nowNode.style.display="block";
				   $("#pc_part_3").hide(500);
				}else{
					$(nowNode).hide(500);
				   $("#pc_part_3").show(500);
				}
			})
			function showRecycleList(){
				console.log("显示回收站列表");
				 $.post("getrecyclelist.do",{"cn_user_id":userid},
					   function (res){
					 	for(var i=0;i<300;i++){
					 		$(".disable").remove();
					 	}
						 for(var i=0;i<res.length;i++){
							 var noteStr=JSON.stringify(res[i]);
							 var recycleList= '<li class="disable"><a ><i class="fa fa-file-text-o" title="online" rel="tooltip-bottom"></i>'+res[i].cn_note_title+'<button type="button" class="btn btn-default btn-xs btn_position btn_delete" id='+res[i].cn_note_id+' onclick="noteRemove(this)"><i class="fa fa-times"></i></button><button type="button" class="btn btn-default btn-xs btn_position_2 btn_replay"  id='+res[i].cn_note_id+' onclick="noteRecovery(this)"><i class="fa fa-reply"></i></button></a></li>';						 
							 var recButton=$(recycleList).find("button");
							 console.log(recButton.eq(0).data("cn_note_id"))
							 $("#recyclelist").append(recycleList);
						}
					   });
			}
			function noteRecovery(recButton) {
				
				console.log("从回收站恢复");
				var cn_note_id=recButton.id;
				console.log(cn_note_id);
				window.alert=function(){
					$('#can').load('./alert/alert_replay.html',function(){
						var selected_notebook_id;
						var selectText;
						$('.opacity_bg').show(10);
						$('#can').show(500);
						$('.close,.cancle').click(function() {
							closeMsg();
						});
						for(var i=0;i<noteBookList.length;i++){
							 $("#replaySelect").prepend("<option value='"+noteBookList[i].cn_notebook_id+"'>"+noteBookList[i].cn_notebook_name+"</option>");
						}
						$("#replaySelect").change(function() {  
							selected_notebook_id = $("#replaySelect").val();
							selectText = $("#replaySelect").find("option:selected").text();
						});
						$(".btn-primary").click(function() {
							console.log("恢复到"+selectText+"\r     "+selected_notebook_id);
							recycleToNoteList(selected_notebook_id,cn_note_id);
						})
			        });
					}  
				alert();
				alertChange();	 
				}
			function recycleToNoteList(cn_notebook_id,cn_note_id) {
				console.log("恢复到"+cn_notebook_id);
				$.post("recycleToNoteList.do",{"cn_notebook_id":cn_notebook_id,"cn_note_id":cn_note_id},
						function (res){
						 console.log(res.msg);
						 alert('笔记恢复提示',res.msg,1000);
						 $("#replaySelect option[value="+cn_notebook_id+"]").remove();
						 $("#rollback_button").click();
						 $("#booklists [name="+cn_notebook_id+"]").click();
						});
				}
			var recycleNoteid;
			function noteRemove(delButton) {
				console.log("彻底删除笔记");
				recycleNoteid=delButton.id;
				window.alert=function(){
					$('#can').load('./alert/alert_delete_rollback.html',function(){
						$('.opacity_bg').show(10);
						$('#can').show(500);
					});
				}
				 alert();
				 alertChange();	  
			}
			function noteDelPost() {
				console.log("删除笔记");
				$.post("notedel.do",{"noteid":recycleNoteid},
					function (res){
					console.log(res.msg);
					 showRecycleList();
					 alert('彻底删除笔记',res.msg,1000);
					});
			}
			function rebookbamepost(enter) {
			 console.log("笔记本重命名"+newBookName+"\r"+bookid);  
			 $.post("rebookname.do",{"cn_notebook_id":bookid,"newBookName":newBookName},
					function (res){
				 	alertChange();
				 	console.log(res.msg);
				 	alert("笔记本重命名提示",res.msg,1200);
				    var booknode=$("[name="+bookid+"]");
				    console.log(booknode.html());
				    shownotebook();
					});
			}
			var nowNoteNode;
			var note_id;
			function noteEditor(nowNote){
				console.log("单击笔记");
				nowNoteNode=event.target||nowNote;
				note_id=$(nowNoteNode).data("note_id");
				console.log(note_id);
				get("save_note").name=note_id;
				$("#myEditor").html($(nowNoteNode).data("note_body"));
				$("#input_note_title").val(nowNoteNode.title);
			}
			var note;
			function noteSelectDown(){
				console.log("下拉");
				event.cancelBubble=true;
				$(event.target.parentNode.parentNode).nextAll().get(0).style.display="block";
			}

			function noteMove(){
				console.log("移动笔记")
				window.alert=function(){
					$('#can').load('./alert/alert_move.html',function(){
						$('.opacity_bg').show(10);
						$('#can').show(500);
					});
				}
			 alert();
			 $.post("getnotebook.do",{"userid":userid},
						function (res){
							console.log("返回"+res.length+"个笔记本目录");
							 for(var i=0;i<res.length;i++){
								 var bookval=res[i].cn_notebook_name;
								 var option='<option value="'+bookval+'">'+bookval+'</option>';
								 $("moveSelect").append(bookval);
							 }
						 });
			 alertChange();
			} 
			function noteMovePost (){
				console.log("移动笔记")
			} 
			function noteShare() {
				console.log("分享笔记")
			}
			function noteToRecycle(){
				console.log("移动到回收站x");
				window.alert=function(){
					$('#can').load('./alert/alert_delete_note.html',function(){
						$('.opacity_bg').show(10);
						$('#can').show(500);
					});
				}
				 alert();
				 alertChange();	  
			}
			function noteToRecyclePost(){
				console.log("移动到回收站"+note_id);
				$.post("notetorecycle.do",{"cn_note_id":note_id},
						function (res){
							console.log(res.msg);
							alertChange();
							alert('移动到回收站提示',res.msg,500);
							$(bookNode).click();
						 } );
			}
			
			
			document.onclick=function(){
				$(".note_menu").hide();
				 
			}
			function saveNote(event_target){
				console.log("修改笔记");
				var noteBody =$("#myEditor").html();
				var noteId=event_target.name;
				var noteTitle=$("#input_note_title").val();
				//修改本地节点属性
				nowNoteNode.name=noteBody;
				nowNoteNode.title=noteTitle;
				$(nowNoteNode).find("span").html(noteTitle);
				console.log(noteId);
				console.log(noteBody);
				//修改数据库属性
				var statusid=1;
				$.post("notechange.do",{"noteid":noteId,"notebody":noteBody,"notetitle":noteTitle,"statusid":statusid},
						function (res){
					        console.log("修改结果信息");
							console.log(res.state);
							console.log(res.msg);
							alertChange();
							alert('修改提示',res.msg,500);
							 
						 } );
			} 
			 function addNoteBook() {
				 console.log('添加笔记本');
				 window.alert=function(){
						$('#can').load('./alert/alert_notebook.html',function(){
							$('.opacity_bg').show(10);
							$('#can').show(500) ;
						});
					}
				 alert();
				 alertChange()
			}
			 function addNote(){
				 console.log('添加笔记');
				 window.alert=function(){
						$('#can').load('./alert/alert_note.html',function(){
							$('.opacity_bg').show(10);
							$('#can').show(500) ;
						});
					}
				 alert();
				 alertChange()
			 }
			 
			
			 function createNote(){ 
				 var cn_note_title=$("#input_note").val();
				 console.log('当前笔记本ID:'+bookid);
				 console.log('创建笔记'+cn_note_title);
				 $.post("createNote.do",{"cn_note_title":cn_note_title,"cn_user_id":userid,"cn_notebook_id":bookid},
							function (res){
					 				closeMsg();
									alertChange();
									alert("笔记创建结果",res.msg,1000);
									location.href="edit.html";
							});
			 }
			 function createNoteBook() {
				console.log('创建笔记本')
				var NoteBookName=$('#input_notebook').val();
				console.log('笔记本名称'+NoteBookName+"\r用户ID"+userid);
				$.post("createNoteBook.do",{"cn_notebook_name":NoteBookName,"cn_user_id":userid},
					function (res){
							closeMsg();
							alertChange();
							alert("笔记本创建结果",res.msg,1000);
							location.href="edit.html";
					});
			}
			//重写JS原生alert函数
			function alertChange() {
				window.alert=function(msgtitle,msgBody,timeout){
					$('#can').load('./alert/alert_error.html',function(){
						$('#error_info').text(" "+msgBody);
						$("#modalBasicLabel_8").text(msgtitle);
						$('.opacity_bg').show(10).hide(timeout+500) ;
						$('#can').show(500).hide(timeout);
					});
				}
			}alertChange();
			//实例化Ueditor编辑器
			var um = UM.getEditor('myEditor');
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			