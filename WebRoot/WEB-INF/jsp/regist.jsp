<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<title>曼奇立德</title>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">


<link rel="stylesheet" href="css/font-awesome.css">
<link rel="stylesheet" href="css/bootstrap.css">
<link rel="stylesheet" href="css/common.css">
<link rel="stylesheet" href="css/regist.css">
<link rel="stylesheet" href="css/bootstrap-select.min.css">

<script type="text/javascript" src="js/vue.js"></script>
<script type="text/javascript" src="js/jquery-3.2.1.js"></script>
<script type="text/javascript" src="js/bootstrap.js"></script>
<script type="text/javascript" src="js/bootstrap-select.js"></script>  
<style type="text/css">
</style>
</head>
<body>
	<div id="registPage" class="common-div" v-cloak>

		<div>
			<div class="page-header">
				<h1>
					教务注册 <small>曼奇立德</small>
				</h1>
			</div>
		</div>
		<ul class="nav nav-pills">
			<li v-for="typeItem in types" role="presentation"
				:class="{'active':typeItem==type}" @click="toggleTab(typeItem)"><a>注册{{typeItem}}</a></li>

		</ul>
		<div id="formDiv">

				<form id="form">
				
					<field  :error="IDError" labelname="ID">
						<div class="input-group">
							<input type="text" name="userID" v-model="ID"
								class="form-control" :placeholder="IDHelpler" :readonly="fixID">
							<span class="input-group-btn">
								<button class="btn btn-primary" type="button"
									@click="autoIncre()">ID自增</button>
							</span>
						</div>
					</field>
					
					<field  :error="nameError" labelname="姓名">
						<input class="form-control" name="username"	v-model="name" placeholder="不为空">
					</field>
					
					<field  :error="pwError" labelname="密码">
						<div class="input-group">
							<input type="text" class="form-control" v-model="password"
								name="password" placeholder="6位以上" :readonly="fixPw"> <span
								class="input-group-btn">
								<button class="btn btn-primary" type="button"
									@click="fixPw=!fixPw">固定密码</button>
							</span>
						</div>
					</field>
					
					<field  v-show="type=='助教'" :error="styleError" labelname="风格">
                                <select id="teacherStyle" name="teacherStyle" class="selectpicker show-tick form-control"  multiple data-live-search="false" >
                                        <option value="日系设计">日系设计</option>
                                        <option value="韩式写实设计">韩式写实设计</option>
                                        <option value="Q版设计">Q版设计</option>
                                        <option value="机甲设计">机甲设计</option>
                                        <option value="欧美设计">欧美设计</option>
                                        <option value="插图">插图</option>
                                        <option value="结构">结构</option>
                                        <option value="色彩">色彩</option>
                                        <option value="怪物">怪物</option>
                                </select>
                           
						
					</field>
					
					
					 <feedback :success="success" :result="result"></feedback> 
					<button type="button" class="btn btn-primary form-btn"
						@click="regist" id="regist">注册</button>
						
				</form>
			</div>
	</div>
</body>




<script type="text/javascript" src="js/common-components.js"></script>
<script type="text/javascript" src="js/regist.js"></script>
</html>
