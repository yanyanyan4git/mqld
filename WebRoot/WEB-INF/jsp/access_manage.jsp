<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<title>曼奇利德</title>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">



<link rel="stylesheet" href="css/font-awesome.css">
<link rel="stylesheet" href="css/bootstrap.css">
<link rel="stylesheet" href="css/common.css">
<link rel="stylesheet" href="css/pagination.css"  />

<script type="text/javascript" src="js/jquery-3.2.1.js"></script>
<script type="text/javascript" src="js/bootstrap.js"></script>
<script type="text/javascript" src="js/vue.js"></script>
</head>
<body>
	<div class="common-div">
		<div>
			<div class="page-header">
				<h1>
					权限管理 <small>曼奇立德</small>
				</h1>
			</div>
		</div>
		<div class="left-div" id="pagination"  v-cloak>
		<div>
			<div align="right"> <span class="glyphicon glyphicon-repeat signal" title="刷新" @click="refresh" aria-hidden="true"></span> </div>
			<table class="table table-hover">
				<thead>
					<tr>
						<th>ID</th>
						<th>姓名</th>
						<th>权限</th>
					</tr>
				</thead>
				<tbody>
					<tr v-for="(item,index) in records" :class="index==recordIndex?'info':''" @click="recordIndex=index">
						<th scope="row">{{item.ID}}</th>
						<td>{{item.name}}</td>
						<td>{{item.authority}}</td>
					</tr>
				</tbody>
			</table>
		</div>
		<paginbar :data="$data"  v-on:pagin="go"  v-on:inputgo="inputGo"></paginbar>
		</div>
		<div class="right-div" id="manager"  v-cloak>
				<div >
					<h3>
						信息
					</h3>
				</div>
				<div>
				<form>
					<field  :error="error" labelname="ID">
						<input class="form-control" readonly="readonly" name="userID"  v-model="ID"></input>
					</field>
					<field  :error="error" labelname="姓名">
						<input class="form-control" name="name" v-model="name"></input>
					</field>
					<field  :error="error" labelname="权限">
						<selector :selected="authority">
								<li  v-for="item in authorities" @click="toggleAuthor(item)"><a>{{item}}</a></li>
						</selector>
					</field>
					<feedback :success="success" :result="result"></feedback>
					<button  type="button" class="btn btn-primary form-btn" :disabled="nullField"
						 @click="submit">修改</button>
					
				</form>
				<div class="hidden">{{user}}</div>
				</div>
			</div>
	</div>
</body>
<script type="text/javascript" src="js/common-components.js"></script>
<script type="text/javascript" src="js/pagination.js"></script>
<script type="text/javascript" src="js/access-manage.js"></script>

</html>