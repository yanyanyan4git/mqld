<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>

</body>
</html>
<!-- selector -->
<div v-if="type=='助教'"
	:class="[styleError?'has-error':'','form-group','form-dorpdown']">
	<label>风格</label>
	<selector :selected="style">
	<li v-for="style in styles" @click="toggleStyle(style)"><a>{{style}}</a></li>
	</selector>
	<span class="help-block">{{styleError}}</span>
</div>

<!-- Pagination -->
<div align="right" >
					<p >Records:{{totalRecord}}&nbsp;</p>
					<ul class="pagination">
						<li v-if="currentPage>1"><span @click="go(currentPage-1)">&laquo;</span>
						</li>
						<li v-for="num in endPage-startPage+1"><span
							 @click="go(num+startPage-1)" :class="num==currentPage?'show':''">{{num+startPage-1}}</span></li>
						<li v-if="currentPage<pageNum"><span @click="go(currentPage+1)">&raquo;</span>
						</li>
						<li><p>{{currentPage}}/{{pageNum}}</p></li>
						<li><input type="text" v-model="inputNum" :class="[inputValidate?'':'red','pagin-input']"></li>
						<li><input type="button" @click="inputGo" value="GO!"></li>
					</ul>
				</div>