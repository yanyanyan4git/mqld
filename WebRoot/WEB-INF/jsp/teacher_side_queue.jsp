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
<script type="text/javascript" src="js/jquery.raty.js"></script>  
<script type="text/javascript" src="js/bootstrap-datetimepicker.js"></script>  
<script type="text/javascript" src="js/datetimepicker.js"></script>

<link rel="stylesheet" href="css/font-awesome.css">
<link rel="stylesheet" href="css/bootstrap.css">
<link rel="stylesheet" href="css/common.css">
<link rel="stylesheet" href="css/pagination.css"  />
<link rel="stylesheet" href="css/teacher-side-queue.css">
<link rel="stylesheet" href="css/bootstrap-datetimepicker.css">
<style type="text/css">

</style>
</head>
<body>
	<div class="common-div" >
		<div>
			<div class="page-header" id="beginWork"  v-cloak>
				<h1>
					排队系统（助教端）<small>曼奇立德</small> 
					<button class="btn btn-primary right"  type="button" @click="toggleWork">{{workBtn}}</button> 
				</h1>
			</div>
		</div>
		<div>
		<div style="display: inline;" >
		
        </div>
		<div class="left-div"  id="pagination" v-cloak>
		<div align="right"> <span class="glyphicon glyphicon-repeat signal" title="刷新" @click="refresh" aria-hidden="true"></span> </div>
			<table class="table table-hover">
				<thead>
					<tr>
						<th>排队编号</th>
						<th>学生姓名</th>
						<th>改图数量</th>
						<th>文件路径</th>
						<th>备注</th>
					</tr>
				</thead>
				<tbody>
					<tr v-for="(item,index) in records" :class="index==recordIndex?'info':''" @click="recordIndex=index">
						<th scope="row">{{index+1+(currentPage-1)*pageSize}}</th>
						<td>{{item.studentName}}</td>
						<td>{{item.pictureNum}}</td>
						<td>{{item.studentPath}}</td>
						<td>{{item.studentComment}}</td>
					</tr>

				</tbody>
			</table>
			<paginbar :data="$data"  v-on:pagin="go"  v-on:inputgo="inputGo"></paginbar>
			
		</div>
		<div class="right-div" id="tQueue" v-cloak>
				<div >
					<h3>
						信息
					</h3>
				</div>
				<div>
				<ul class="nav nav-pills">
			<li v-for="actionItem in actions" role="presentation"
				:class="{'active':actionItem==action}" @click="action=actionItem"><a>{{actionItem}}</a></li>

		</ul></div>
				<div v-show="action=='排队处理'">
				<form>
					<field   labelname="排队编号">
						<span class="right">{{queueID}}</span>
					</field>
					<field   labelname="学生姓名">
						 <span class="right">{{studentName}}</span>
					</field>
					<field   labelname="文件路径">
					 <input class="form-control"  v-model="path"
							placeholder="文件路径">
					</field>
					<field  labelname="备注">
						 <textarea class="form-control" v-model="comment"></textarea>
					</field>
					<feedback :success="success" :result="result"></feedback>
					<button type="button" class="btn btn-primary form-btn" :disabled="nullField||user==null"
						@click="resolveQueue">解决</button>

				</form>
				<div class="hidden">{{user}}</div>
				</div>
				<div v-show="action=='工作时间'">
					<form>
						 <field   labelname="上班时间">
						<input type="text" class="form-control short-input right" name="start" id="startTime" readonly="" v-model="startWorkTime"/> 
						</field>
						
						<field   labelname="下班时间">
							   <input type="text" class="form-control short-input right" name="end" id="endTime" readonly="" v-model="endWorkTime"/> 
						</field> 
						<feedback :success="success" :result="result"></feedback>
						<button type="button" class="btn btn-primary form-btn" @click="setWorkTime" 
					>设置</button>
					</form>
				</div>
			</div ></div>
		
	</div>
</body>
<script type="text/javascript" src=js/common-components.js></script>
<script type="text/javascript" src="js/pagination.js"></script>
<script type="text/javascript" src="js/bg-work.js"></script>
<script type="text/javascript" src="js/teacher-side-queue.js"></script>
<script type="text/javascript">


</script>
	
</html>