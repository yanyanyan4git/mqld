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
	
	
	 <link rel="stylesheet" href="css/font-awesome.css">
	<link rel="stylesheet"   href="css/bootstrap.css">
	<link rel="stylesheet" href="css/common.css">
<link rel="stylesheet" href="css/pagination.css"  />
	<link rel="stylesheet" href="css/jquery.raty.css">
</head>
<body>
	<div class="common-div">
			<div>
				<div class="page-header">
					<h1>
						排队进程 <small>曼奇立德</small>
					</h1>
				</div>
			</div>
			<div>
			<div class="left-div" id="pagination"  v-cloak>
			
				<table class="table table-hover">
					<thead>
						<tr>
							<th>排队编号</th>
							<th>创建时间</th>
							<th>助教姓名</th>
							<th>助教风格</th>
							<th>我的文件路径</th>
							<th>助教文件路径</th>
							<th>我的备注</th>
							<th>助教备注</th>
							<th>改图数量</th>
							<th>排队状态</th>
							<th></th>
						</tr>
					</thead>
					<tbody>
						<tr v-for="(item,index) in records" :class="index==recordIndex?'info':''" @click="recordIndex=index">
							<th scope="row">{{item.ID}}</th>
							<td>{{item.createTime}}</td>
							<td>{{item.teacherName}}</td>
							<td>{{item.teacherStyle}}</td>
							<td>{{item.studentPath}}</td>
							<td>{{item.teacherPath}}</td>
							<td>{{item.studentComment}}</td>
							<td>{{item.teacherComment}}</td>
							<td>{{item.pictureNum}}</td>
							<td>{{item.status}}</td>
							
						</tr>

					</tbody>
				</table>
			</div>

			<div class="right-div" id="queue"  v-cloak>
				<div >
					<h3>
						评价
					</h3>
				</div>
				<div>
				<form>
				
					<field   labelname="排队编号">
						<span class="right">{{queueID}}</span>
						</field>
					<field   labelname="改图水平">
						 <span class="ratyy right" name="profLevel"></span>
						 <div><span id="showProfLevel" class="right"></span></div>
					</field>
					<field   labelname="改图态度">
						 <span class="ratyy right" name="attitude"></span>
						 <div><span id="showAttitude" class="right"></span></div>
					</field>
					<field   labelname="备注">
						 <textarea class="form-control" name="comment" v-model="comment"></textarea>
					</field>
					<button type="button" class="btn btn-primary form-btn"
						 @click="evaluate" >提交评价</button>
					<feedback :success="success" :result="result"></feedback>
				</form>
				<div class="hidden">{{queue}}</div>
				</div>
			</div>
		</div>
		
	</div>
</body>
<script type="text/javascript" src=js/common-components.js></script>
<script type="text/javascript" src="js/pagination.js"></script>
<script type="text/javascript" src="js/queue-status.js"></script>
<script type="text/javascript">

		
	</script>
</html>