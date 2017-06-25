
var registVm = new Vue({
	el : '#main',
	data : {
		user:{
			username:'',
			password:'',
			remember:false
		},
		success:false,
		name:''
	},
	computed:{
		logNull:function(){
			var user=this.user;
			return user.username==''||user.password=='';
		},
		
	},
	methods : {
		show4Athor:function(activePage){
			if (''==this.authority) {
				return false;
			}
			var list=this.page4Author[this.authority];
			for (let page of list) {
				if (activePage==page) {
					return true;
				}
			}
			return false;
		},
		login:function(){
			var self = this;
			var src = "login.action";
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
		
	}
});