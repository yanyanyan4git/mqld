<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
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
	<div class="common-div" >
		<div>
			<div class="page-header" id="toolbar">
				<h1>
					排队系统 <small>曼奇立德</small>
				</h1>
				
			</div>
		</div>
		<div>
			<div class="left-div" id="pagination"  v-cloak> 
			<div>
			<div align="right"> <span class="glyphicon glyphicon-repeat signal" title="刷新" @click="refresh" aria-hidden="true"></span> </div>
				<table class="table table-hover">
					<thead>
						<tr>

							<th>助教姓名</th>
							<th>正在排队/剩余名额</th>
							
							<th>助教风格</th>
							<th>上班时间</th>
							<th>是否在岗</th>
						</tr>
					</thead>
					<tbody>
						<tr v-for="(item,index) in records" :class="index==recordIndex?'info':''" @click="recordIndex=index">
						<th scope="row">{{item.teacherName}}</th>
						
						<td>{{item.currentQueueNum}}/{{item.maxStudentNum-item.totalQueueNum}}</td>
						<td>{{item.teacherStyle}}</td>
						<td>{{item.startWorkTime}}-{{item.endWorkTime}}</td>
						<td>{{item.teacherOnWork?'是':'否'}}</td>
					</tr>

					</tbody>
				</table>
				</div>
				
				<paginbar :data="$data"  v-on:pagin="go"  v-on:inputgo="inputGo"></paginbar>
			</div>
			<div class="right-div" id="queue" v-if="showPanel"  v-cloak>
				<div>
					<h3>信息</h3>
				</div>
				<div>
					<form>
					<field   labelname="助教">
						<span class="right">{{teacherName}}</span>
					</field>
					<field labelname="前方排队人数">
						 <span class="right">{{currentQueueNum===''?'':currentQueueNum}}</span>
					</field>
						
					<field   labelname="文件路径">
					 <input class="form-control"  v-model="path"
							placeholder="文件路径" :readonly="hasQueued" :readonly="hasQueued">
					</field>
					<field   labelname="改图数量">
						 <selector :selected="picNum" :disab="hasQueued">
								<li  v-for="n in maxPicNum" @click="togglePicNum(n)" ><a>{{n}}</a></li>
						</selector>
					</field>
					<field  :error="error" labelname="备注">
						 <textarea class="form-control" v-model="comment" :readonly="hasQueued"></textarea>
					</field>
					<feedback :success="success" :result="result"></feedback>
						<button  type="button" class="btn btn-primary form-btn" :disabled="!hasQueued&&(!canQueue||nullField)"
							 @click="excuteDoC">{{DoC}}</button>
					<div class="hidden">{{queue}}</div>
				</div>
					</form>
				</div>
			</div>
		</div>

	</div>
</body>
<script type="text/javascript">

	 
	</script>
<script type="text/javascript" src=js/common-components.js></script>
<script type="text/javascript" src="js/pagination.js"></script>
<script type="text/javascript" src="js/queue.js"></script>
</html>