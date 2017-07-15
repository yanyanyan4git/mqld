
var mainVm = new Vue({
	el : '#main',
	data : {
		user:{
			username:'',
			password:'',
			remember:false
		},
		success:false,
		name:'',
		ssUser:'',
		psw:'',
		newPsw:'',
		confirmPsw:'',
		wrongPsw:'',
		refreshed:false
	},
	computed:{
		logNull:function(){
			var user=this.user;
			return user.username==''||user.password=='';
		},
		
	},
	mounted:function(){
		this.getUser();
	},
	methods : {
		closeSetPsw:function(){
			$('#setPsw').modal('hide'); 
		},
		openSetPsw:function(){
			$('#setPsw').modal('show'); 
		},
		getUser:function(){
			var self = this;
			var src = "getUser.action";
			this.refreshed=false;
			$.ajax({
					url: src,
					type : "GET",  
					dataType: 'json',  
					success:function(result){
						if(result.error){
							
						}else if(result.data.user){
							self.ssUser=result.data.user;
							self.refreshed=true;
						}else{
						
							
						}	
					},
					error:function(request, textStatus, errorThrown){
						console.log("Error: " + textStatus);
					}
			 }); 
		},
		login:function(){
			var self = this;
			var src = "login.action";
			console.log(JSON.stringify(self.user));
			$.ajax({
					url: src,
					type : "POST",  
					data : JSON.stringify(self.user),  
					dataType: 'json',  
					contentType:'application/json;charset=UTF-8',     
					success:function(result){
						if(result.error){
							alert("登录失败，用户名或密码错误");
							self.success=false;
						}else if(result.data.user){
							
							self.success=true;
							location.reload();
							
						}else{
							alert("登录失败，用户名或密码错误");
							self.success=false;
							
						}	
					},
					error:function(request, textStatus, errorThrown){
						alert("登录失败");
						console.log("Error: " + textStatus);
					}
			 }); 
		},
		logout:function(){
			var self = this;
			var src = "logout.action";
			$.ajax({
					url: src,
					type : "GET",  
					dataType: 'json',  
					success:function(result){
						if(result.error){
							alert("登出失败，请重试");
						}else{
							alert("已登出");
							location.reload();
						}
					},
					error:function(request, textStatus, errorThrown){
						alert("登出失败，请重试");
						console.log("Error: " + textStatus);
					}
			 }); 
		},
		doUpdatePsw:function(){
			if (this.newPsw.length<6) {
				this.wrongPsw='密码长度需大于6';
				return;
			}
			if (this.newPsw!=this.confirmPsw) {
				this.wrongPsw='密码不一致';
				return;
			}
			
			
			var self = this;
			var src = "updatePsw.action";
			var msg={password:this.psw,newPassword:this.newPsw};
			$.ajax({
					url: src,
					type : "POST",  
					data : JSON.stringify(msg),  
					dataType: 'json',  
					contentType:'application/json;charset=UTF-8',     
					success:function(result){
						if(result.error){
							alert("修改失败，密码错误");
							self.success=false;
						}else{
							alert("修改密码成功,请重新登录");
							location.reload();
						}
					},
					error:function(request, textStatus, errorThrown){
						alert("登录失败");
						console.log("Error: " + textStatus);
					}
			 }); 
		},
		
	}
});