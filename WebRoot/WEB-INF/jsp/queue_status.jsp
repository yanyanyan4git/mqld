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
			<div class="left-div">
			
				<table class="table table-hover">
					<thead>
						<tr>

							<th>时间</th>
							<th>助教姓名</th>
							<th>助教风格</th>
							<th>排队状态</th>
							<th></th>
						</tr>
					</thead>
					<tbody>
						<tr>
							<th scope="row">05/2017</th>
							<td>张三</td>
							<td></td>
							<td>排队中</td>
							<td style="width: 70px;">
								<button name="performance" type="button" class="btn btn-primary">评价</button>
							</td>
						</tr>

					</tbody>
				</table>
			</div>

			<div class="right-div">
				<div style="margin-bottom: 50px;">
					<h3>
						评价
					</h3>
				</div>
				<div>
				<form>

					<div class="form-group help-block">
						<label>改图水平：</label> <span name="profLevel"></span>一般
					</div>
					<div class="form-group help-block">
						<label>改图态度：</label> <span name="attitude"></span>一般
					</div>
					<div class="form-group">
						<label>备注</label> <textarea class="form-control" name="comment"></textarea>
					</div>
					<button type="button" class="btn btn-primary"
						style="width: 300px; margin-top: 10px;" id="evaluation">提交评价</button>

				</form>
				</div>
			</div>
		</div>
		
	</div>
</body>
<script type="text/javascript">
		
		$("span[name='performance']").raty();
		$("span[name='performance']").css("margin-right","50px");
		
	</script>
</html>