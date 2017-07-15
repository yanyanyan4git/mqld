<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<title>曼奇立德</title>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

<script type="text/javascript" src="js/jquery-3.2.1.js"></script>
<script type="text/javascript" src="js/bootstrap.js"></script>
<script type="text/javascript" src="js/vue.js"></script>


<link rel="stylesheet" href="css/bootstrap.css">
<link rel="stylesheet" href="css/common.css">
<link rel="stylesheet" href="css/pagination.css"  />
</head>
<body>
	<div class="common-div">
		<div>
			<div class="page-header">
				<h1>
					教务评价 <small>曼奇立德</small>
				</h1>
			</div>
		</div>
		<div>
			<div   id="pagination" v-cloak>
		<div align="right"> <span class="glyphicon glyphicon-repeat signal" title="刷新" @click="refresh" aria-hidden="true"></span> </div>
			<table class="table table-hover">
				<thead>
					<tr>
						<th>排队编号</th>
						<th>助教姓名</th>
						<th>学生姓名</th>
						<th>学生路径</th>
						<th>学生备注</th>
						<th>助教路径</th>
						<th>助教备注</th>
						<th>改图水平</th>
						<th>改图态度</th>
						<th>评价备注</th>
					</tr>
				</thead>
				<tbody>
					<tr v-for="(item,index) in records" :class="index==recordIndex?'info':''" @click="recordIndex=index">
						<th scope="row">{{item.ID}}</th>
						<td>{{item.teacherName}}</td>
						<td>{{item.studentName}}</td>
						<td>{{item.studentPath}}</td>
						<td>{{item.studentComment}}</td>
						<td>{{item.teacherPath}}</td>
						<td>{{item.teacherComment}}</td>
						<td>{{item.profLevel}}</td>
						<td>{{item.attitude}}</td>
						<td>{{item.perfComment}}</td>
					</tr>

				</tbody>
			</table>
			<paginbar :data="$data"  v-on:pagin="go"  v-on:inputgo="inputGo"></paginbar>
			
		</div>
		</div>
	</div>
</body>
<script type="text/javascript" src="js/pagination.js"></script>
<script type="text/javascript" src="js/admin-side-performance.js"></script>
</html>