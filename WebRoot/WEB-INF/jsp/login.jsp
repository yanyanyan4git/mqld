<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<title>曼奇利德</title>
  <head>
	<!----> <script type="text/javascript" src="js/jquery-3.2.1.js"></script>
	<script type="text/javascript" src="js/bootstrap.js"></script>
	<script type="text/javascript" src="js/vue.js"></script>  
	
	
	 <link rel="stylesheet" href="css/font-awesome.css">
	<link rel="stylesheet"   href="css/bootstrap.css">
	<link rel="stylesheet"   href="css/login.css">
	
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	
</head>
<body>
<button class="btn btn-primary btn-lg" data-toggle="modal" data-target="#myModal">开始演示模态框</button>

	<div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true" style="top: 20%;">
    <div class="modal-dialog">
           <div class="htmleaf-container">
		
		<div class="demo " style="padding: 20px 0;margin: auto auto;">
		        <div class="container" >
		                <div class=" col-md-6" >
		                    <form class="form-horizontal" action="${pageContext.request.contextPath}/login.action" method="post">
		                        <span class="heading">用户登录</span>
		                        <div class="form-group">
		                            <input type="text" class="form-control" name="username"  placeholder="用户名">
		                            <i class="fa fa-user"></i>
		                        </div>
		                        <div class="form-group help">
		                            <input type="password" class="form-control" name="password"  placeholder="密　码">
		                            <i class="fa fa-lock"></i>
		                        </div>
		                        <div class="form-group">
		                            <div class="main-checkbox">
		                                <input type="checkbox" value="None" id="checkbox1" name="check"/>
		                                <label for="checkbox1"></label>
		                            </div>
		                            <span class="text">记住用户名</span>
		                            <button type="submit" class="btn btn-default">登录</button>
		                            <a href="${pageContext.request.contextPath}/regist.action"><button type="button" class="btn btn-default">注册</button></a>
		                        </div>
		                    </form>
		        </div>
		    </div>
		
	</div>
        </div><!-- /.modal-content -->
    </div><!-- /.modal -->
</div>
	
	</body>
	<script type="text/javascript">
	
	</script>
</html>
