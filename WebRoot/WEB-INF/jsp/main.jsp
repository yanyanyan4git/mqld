<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<title>曼奇立德</title>
  <head>
	<!----> <script type="text/javascript" src="js/jquery-3.2.1.js"></script>
	<script type="text/javascript" src="js/bootstrap.js"></script>
	<script type="text/javascript" src="js/vue.js"></script>  
	
	<link rel="stylesheet"   href="css/main.css">
	 <link rel="stylesheet" href="css/font-awesome.css">
	<link rel="stylesheet"   href="css/bootstrap.css">
	<link rel="stylesheet"   href="css/login.css">
	<link rel="stylesheet"   href="css/common.css">
	
	<style>
	
	</style>
	
</head>
<body>

	<div id="main"  v-cloak>
		<div class="modal fade" id="myModal" tabindex="-1" role="dialog"
			aria-labelledby="myModalLabel" aria-hidden="true" style="top: 20%;" >
			<div class="modal-dialog">
				<div class="htmleaf-container">

					<div class="demo " style="padding: 20px 0; margin: auto auto;">
						<div class="container">
							<div class=" col-md-6">
								<form class="form-horizontal"
									action="${pageContext.request.contextPath}/login.action"
									method="post">
									<span class="heading">用户登录</span>
									<div class="form-group">
										<input type="text" class="form-control" name="username" v-model="user.username"
											placeholder="ID"> <i class="fa fa-user"></i>
									</div>
									<div class="form-group help">
										<input type="password" class="form-control" name="password" v-model="user.password"  v-on:keyup.enter='login'
											placeholder="密　码"> <i class="fa fa-lock"></i>
									</div>
									<div class="form-group">
										<!--<div class="main-checkbox">
											<input type="checkbox" value="None" id="checkbox1" v-model="user.remember"
												name="check" /> <label for="checkbox1"></label>
										</div>
										 <span class="text">记住用户名</span> -->
										<button id="login" type="button" class="btn btn-default" @click="login">登录</button>
										
									</div>
								</form>
							</div>
						</div>

					</div>
				</div>
			</div>
		</div>
		
		
		<div class="modal fade" id="setPsw" tabindex="-1" role="dialog" aria-labelledby="pswModalLabel" aria-hidden="true" style="top: 20%;">
    <div class="modal-dialog" style="width:300px;">
        <div class="modal-content" >
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true" @click="closeSetPsw">&times;</button>
                <h4 class="modal-title" id="pswModalLabel">更改密码</h4>
            </div>
            <div class="modal-body" align="center">
            	<div class="form-group">
						<input  class="form-control psw"  v-model="psw" type="password"
											placeholder="旧密码"> 
				</div>
				
				<div class="form-group" align="center">
						<input  class="form-control psw"  v-model="newPsw" type="password" 
											placeholder="新密码,至少6位"> 
				</div>
				
				<div class="form-group" align="center">
						<input  class="form-control psw"  v-model="confirmPsw" type="password" 
											placeholder="确认密码"> 
						<span v-if="wrongPsw" class="help-block" style="color: red;float: left;margin-left: 34px;font-size: 12px;">{{wrongPsw}}</span>
				</div>
				<div class="form-group" align="center">
						 <button type="button" class="btn btn-primary" @click="doUpdatePsw" style="width:200px;">确认更改</button>
				</div>
            </div>
        </div><!-- /.modal-content -->
    </div><!-- /.modal -->
</div>


		<div id="topBar">
			<nav class="navbar navbar-default navbar-fixed-top">
				<div class="navbar-header">
					<a class="navbar-brand" href="#">曼奇立德</a>
				</div>
				<ul class="nav navbar-nav navbar-right">
	
					<li class="dropdown"><a href="#" class="dropdown-toggle"
						data-toggle="dropdown" role="button" aria-haspopup="true"
						aria-expanded="false" >${user.name} <span class="caret"></span></a>
						<ul class="dropdown-menu">
							<li @click="openSetPsw" ><a href="#">更改密码</a></li>
							<li @click="logout"><a href="#">登出</a></li>
						</ul></li>
				</ul>
			</nav>
		</div>
		<div id="sideBar">
			<ul class="nav nav-pills nav-stacked">
				<li role="presentation" class="active" name="welcome"><a
					href="#">主页</a></li>
				<li role="presentation" name="accessManage"><a href="#">权限管理</a></li>
				<li role="presentation" name="regist"><a href="#">注册学员/助教</a></li>
				<li role="presentation" name="queue"><a href="#">排队</a></li>
				<li role="presentation" name="teacherSideQueue"><a href="#">排队（助教端）</a></li>
				<li role="presentation" name="performance"><a href="#">评价中心</a></li>
				<li role="presentation" name="adminSidePerformance"><a href="#">评价中心(管理员端)</a></li>
				<li role="presentation" name="queueStatus"><a href="#">排队进程</a></li>
				
			</ul>
		</div>
		<div id="view" class="view">
		       <iframe id="iframe" src="${pageContext.request.contextPath}/welcome.action"   frameborder='0'> 
				</iframe> 
		    </div>
		
	</div>
	</body>
	<script type="text/javascript" src="js/main.js"></script>  
	<script type="text/javascript">
		 $("#sideBar ul li").click(function(){
			 $("#iframe").attr("src","${pageContext.request.contextPath}/"+$(this).attr('name')+".action?timestamp="+new Date()); 
			 $("#sideBar ul li").removeClass("active");
			 $(this).addClass("active");
		});
		var student=["queue","queueStatus"];
		var teacher=["teacherSideQueue","performance"];
		var admin=["accessManage","regist","queue","adminSidePerformance"];
		$(document).ready(function(){
			 if(top.location!=self.location)
				     {
				         top.location=self.location;
				     }
			 $("#sideBar ul li").hide();
			 var authority="${user.authority}";
			 $("#sideBar ul li[name='welcome']").show();
			 if (authority=="管理员") {
				  for (var i = 0; i < admin.length; i++) {
					 $("#sideBar ul li[name='"+admin[i]+"'] ").show();
				} 
			}else if (authority=="学生") {
				for (var i = 0; i < student.length; i++) {
					 $("#sideBar ul li[name='"+student[i]+"']").show();
				}
			}else if (authority=="助教") {
				for (var i = 0; i < student.length; i++) {
					$("#sideBar ul li[name='"+teacher[i]+"']").show();
				}
			}
			$('#myModal').modal({backdrop: 'static', keyboard: false , show:false});
			$('#setPsw').modal({backdrop: 'static', keyboard: false , show:false});
			
			  if ("${user}"=='') {
				$('#myModal').modal('show'); 
			} 
		}); 
	</script>
</html>
