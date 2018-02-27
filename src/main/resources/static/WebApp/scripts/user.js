function get(nnn){
		return document.getElementById(nnn);
		 
	}
	window.onload=function(){
		get("regist_button").onclick=function(){
			console.log(this.id);
			var  username=$("#regist_username").val();
			var  nick=$("#nickname").val();
			var  regist_password=$("#regist_password").val();
			var  password=$("#final_password").val();
			if(regist_password==password||password.length>5){
				 console.log("注册提交达成");
			 
			 $.get("register.do",{"username":username,"password":password,"nick":nick},
						function (ress){
				 if(ress.id==null){
					 alert("注册失败");
				 }else if(ress.id=='111111'){
					 var nextSib=get('regist_username').nextSibling.nextSibling;
					 nextSib.innerHTML="用户已存在";
					 nextSib.style.display='block';
				 }
				 if(ress.username==username){
					 alert('注册成功！');
					 console.log( ress.id);   
					 get('zc').className='sig sig_out';
					 get('dl').className='log log_in';
					 $('#loginusername').val(ress.username);
				 }
				  });
			}
		}
		
		get('login').onclick=function(){
			var username=$('#loginusername').val().trim();
			var password=$('#loginpassword').val().trim();
			console.log(username+password)
			$('#loginwa1').html(username==''?"用户名为空":"");
			$('#loginwa2').html(password==''?"密码为空":"")
			$.post("login.do",{"username":username,"password":password},
				function (res){
					var state=res.state;
					console.log(state);
					console.log(res.msg);
					if(state==1){
						$("#loginwa1").html(res.msg);
					}
					if(state==2){
						$("#loginwa2").html(res.msg);
					}
					if(state==0){ 
						var reusername =$('#loginusername').val().trim();
						$.cookie("username",username=reusername,{path: "/", expiress: 1});
						$.cookie("userid",res.obj.id, {path:"/",expiress: 1});
						location.href="edit.html";
					}
				});
		}
		get('sig_in').onclick=function(){
			get('dl').className='log log_out';
			get('zc').className='sig sig_in';
		}
		get('back').onclick=function(){
			get('zc').className='sig sig_out';
			get('dl').className='log log_in';
		}
		var t =setTimeout("get('zc').style.visibility='visible'",800);
		get('final_password').onblur=function(){
			var npassword=get('regist_password').value;
			var fpassword=get('final_password').value;
			if(npassword!=fpassword){
				get('warning_3').style.display='block';
			}
		}
		get('regist_username').onblur=function(){
			if(this.value.length<1){
			   this.nextSibling.nextSibling.style.display='block';
			}
		}
		get('regist_password').onblur=function(){
			var npassword=get('regist_password').value.length;
			if(npassword<6&&npassword>0){
			get('warning_2').style.display='block';
			}
		}
		get('regist_password').onfocus=function(){
			get('warning_2').style.display='none';
		}
		get('final_password').onfocus=function(){
			get('warning_3').style.display='none';
			}
		get('regist_username').onfocus=function(){
			   this.nextSibling.nextSibling.style.display='none';
		}
	}