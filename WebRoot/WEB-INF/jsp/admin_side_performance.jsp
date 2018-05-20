<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<title>曼奇立德</title>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

<script type="text/javascript" src="js/jquery-3.2.1.js"></script>
<script type="text/javascript" src="js/bootstrap.js"></script>
<script type="text/javascript" src="js/bootstrap-datetimepicker.js"></script>  
<script type="text/javascript" src="js/datetimepicker.js"></script>
<script type="text/javascript" src="js/vue.js"></script>
<script type="text/javascript" src="js/string-utils.js"></script>

<link rel="stylesheet" href="css/bootstrap.css">
<link rel="stylesheet" href="css/common.css">
<link rel="stylesheet" href="css/pagination.css"  />
<link rel="stylesheet" href="css/bootstrap-datetimepicker.css">
<style>
	
	#searchBar>li>span{
	cursor:auto;
	color: #505254;
	}
	#searchBar>li>span:hover{ background:#fff;}
	#searchBar>li>input{
	outline:none;
	}
	.highlight{
	background-color: #505254 !important;
	color: white !important;
	}
	.pointer{
		cursor:pointer !important;
	}
	
	
</style>
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
		
		<div id="paginToolBar">
		
			<ul id="searchBar" class="pagination">
								<li ><span>时间范围</span></li>
								<li ><input type="text" style="width: 150px;" id="startTime" readonly="" v-model="condition.startCreateTime"></li>
								<li ><span>-</span></li>
								<li ><input type="text" style="width: 150px;" id="endTime" readonly="" v-model="condition.endCreateTime"></li>
								<li ><span>分数范围</span></li>
								<li ><input type="text" style="width: 50px;" v-model="condition.lowTotalScore"></li>
								<li ><span>-</span></li>
								<li ><input type="text" style="width: 50px;" v-model="condition.highTotalScore"></li>
								<li ><span>助教ID</span></li>
								<li ><input type="text" style="width: 150px;" v-model="condition.ID"></li>
								<li ><span :class="{'highlight':condition.scoreAsc}" class="pointer" v-on:click="condition.scoreAsc=true">升序</span></li>
								<li ><span :class="{'highlight':!condition.scoreAsc}" class="pointer" v-on:click="condition.scoreAsc=false">降序</span></li>
								<li ><input type="text" style="width: 20px;" readonly="" ></li>
								<li><input id="searchBtn" class="highlight" type="button" v-on:click="submit" value="搜索"></li>
								
							</ul>
							</div>
			<div   id="pagination" v-cloak>
		
		
			<table class="table table-hover">
				<thead>
					<tr>
						<th>创建时间</th>
						<th>助教ID</th>
						<th>助教姓名</th>
						<th>学生姓名</th>
						<th>学生备注</th>
						<th>助教备注</th>
						<th>改图水平</th>
						<th>改图态度</th>
						<th>评价备注</th>
					</tr>
				</thead>
				<tbody>
					<tr v-for="(item,index) in records" :class="index==recordIndex?'info':''" @click="recordIndex=index">
						<th scope="row">{{item.createTime}}</th>
						<th scope="row">{{item.teacherID}}</th>
						<td>{{item.teacherName}}</td>
						<td>{{item.studentName}}</td>
						<td>{{item.studentComment}}</td>
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