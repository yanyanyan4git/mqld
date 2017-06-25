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

<link rel="stylesheet" href="css/font-awesome.css">
<link rel="stylesheet" href="css/bootstrap.css">
<link rel="stylesheet" href="css/common.css">
<link rel="stylesheet" href="css/pagination.css"  />
<link rel="stylesheet" href="css/teacher-side-queue.css">
<link rel="stylesheet" href="css/bootstrap-datetimepicker.css">
</head>
<body>
	<div class="common-div" id="queue">
		<div>
			<div class="page-header">
				<h1>
					排队系统（助教端）<small>曼奇立德</small> 
					<button class="btn btn-primary right"  type="button" @click="showWorkTime=true">设置上班时间</button>
				</h1>
			</div>
		</div>
		<div>
		<div style="display: inline;"  v-if="showWorkTime">
		<div class="input-group work-time-bar" id="datepicker" >  
            	 <span class="input-group-addon">上班时间</span>  <input type="text" class="form-control" name="start" id="startTime" readonly="" v-model="startWorkTime"/>  
            <span class="input-group-addon">至</span>  
            <input type="text" class="form-control" name="end" id="endTime" readonly="" v-model="endWorkTime"/>  
           <span class="input-group-btn">
        <button class="btn btn-default"  type="button" id="workTimeConfirm" @click="setWorkTime">确定</button>
      </span>
        </div>  
        </div>
		<div class="left-div"  id="pagination">
		
			<table class="table table-hover">
				<thead>
					<tr>
						<th>排队编号</th>
						<th>学生姓名</th>
						<th>助教风格</th>
						<th>改图数量</th>
						<th>文件路径</th>
					</tr>
				</thead>
				<tbody>
					<tr v-for="(item,index) in records" @click="recordIndex=index">
						<th scope="row">{{index+currenPage*pageSize}}</th>
						<td>{{item.studentName}}</td>
						<td>{{item.teacherStyle}}</td>
						<td>{{item.pictureNum}}</td>
						<td>{{item.studentPath}}</td>
					</tr>

				</tbody>
			</table>
			<paginbar :data="$data"  v-on:pagin="go"  v-on:inputgo="inputGo"></paginbar>
		</div>
		<div class="right-div">
				<div >
					<h3>
						信息
					</h3>
				</div>
				<div>
				<form>
					<field  :error="error" labelname="文件路径">
					 <input class="form-control"  v-model="path"
							placeholder="文件路径">
					</field>
					<field  :error="error" labelname="备注">
						 <textarea class="form-control" v-model="comment"></textarea>
					</field>
					<feedback :success="success" :result="result"></feedback>
					<button type="button" class="btn btn-primary form-btn" :disabled="nullField"
						@click="resolveQueue">解决</button>

				</form>
				</div>
			</div></div>
		
	</div>
</body>
<script type="text/javascript" src=js/common-components.js></script>
<script type="text/javascript" src="js/pagination.js"></script>
<script type="text/javascript" src="js/teacher-side-queue.js"></script>
<script type="text/javascript">
		$('#startTime').datetimepicker({
	        todayBtn:  1,
			autoclose: 1,
			todayHighlight: 1,
			startView: 1,
			minView: 0,
			maxView: 1,
			format:'hh:ii',
			 endDate : new Date()  
	    }).on('changeDate',function(e){
	    	var startTime = e.date;  
	    	$('#endTime').datetimepicker('setStartDate',startTime);  
	    	$('#startTime').datetimepicker('hide');
	    });
		$('#endTime').datetimepicker({
	        todayBtn:  1,
			autoclose: 1,
			todayHighlight: 1,
			startView: 1,
			minView: 0,
			maxView: 1,
			format:'hh:ii',
			 endDate : new Date()  
	    }).on('changeDate',function(e){
	    	var endTime = e.date;  
	    	$('#startTime').datetimepicker('setEndDate',endTime);  
	    	$('#endTime').datetimepicker('hide');
	    });
		
	</script>
</html>