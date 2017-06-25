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


<link rel="stylesheet" href="css/font-awesome.css"/>
<link rel="stylesheet" href="css/bootstrap.css"/>
<link rel="stylesheet" href="css/common.css"/>
<link rel="stylesheet" href="css/pagination.css"  />
</head>
<body>
	<div class="common-div" id="queue">
		<div>
			<div class="page-header">
				<h1>
					排队系统 <small>曼奇立德</small>
					<button class="btn btn-primary right"  type="button" @click="cancelQueue">取消当前排队</button>
				</h1>
				
			</div>
		</div>
		<div>
			<div class="left-div" id="pagination">
			<div>
				<table class="table table-hover">
					<thead>
						<tr>

							<th>助教姓名</th>
							<th>排队人数</th>
							<th>助教风格</th>
							<th>上班时间</th>
						</tr>
					</thead>
					<tbody>
						<tr v-for="(item,index) in records" @click="recordIndex=index">
						<th scope="row">{{item.teacherName}}</th>
						<td>{{item.currentQueueNum}}/{{item.maxStudentNum}}</td>
						<td>{{item.teacherStyle}}</td>
						<td>{{item.startWorkTime}}-{{item.endWorkTime}}</td>
					</tr>

					</tbody>
				</table>
				</div>
				
				<paginbar :data="$data"  v-on:pagin="go"  v-on:inputgo="inputGo"></paginbar>
			</div>
			<div class="right-div">
				<div>
					<h3>信息</h3>
				</div>
				<div>
					<form>

						
					<field  :error="error" labelname="文件路径">
					 <input class="form-control"  v-model="path"
							placeholder="文件路径">
					</field>
					<field  :error="error" labelname="改图数量">
						 <selector :selected="picNum">
								<li  v-for="n in maxPicNum" @click="togglePicNum(n)"><a>{{n}}</a></li>
						</selector>
					</field>
					<field  :error="error" labelname="备注">
						 <textarea class="form-control" v-model="comment"></textarea>
					</field>
					<feedback :success="success" :result="result"></feedback>
						<button  type="button" class="btn btn-primary form-btn" :disabled="nullField||hasQueued"
							 @click="doQueue">排队</button>

					</form>
				</div>
			</div>
		</div>

	</div>
</body>
<script type="text/javascript" src=js/common-components.js></script>
<script type="text/javascript" src="js/pagination.js"></script>
<script type="text/javascript" src="js/queue.js"></script>
</html>