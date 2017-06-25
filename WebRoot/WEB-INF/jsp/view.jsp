<%@ taglib uri="http://java.sun.com/portlet_2_0" prefix="portlet"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<portlet:defineObjects />
<portlet:resourceURL var="uploadFile" id="uploadFile"></portlet:resourceURL>

<link rel="stylesheet" href="http://cdn.bootcss.com/bootstrap/3.3.0/css/bootstrap.min.css">
<link href="<%=request.getContextPath()%>/css/pagination.css" rel="stylesheet" />
<link rel="stylesheet" href="<%=request.getContextPath()%>/css/scrollbar.css">
<link href="<%=request.getContextPath()%>/css/file-upload.css" rel="stylesheet" >
<link href="<%=request.getContextPath()%>/css/allresume.css" rel="stylesheet"/>

<portlet:resourceURL id="allPage" var="pageURL" />

<portlet:actionURL name="showResume" var="showURL">
	<portlet:param name="page" value="detail"></portlet:param>
</portlet:actionURL>

<input type="hidden" value="${pageURL}" id="url">
<input type="hidden" value="<portlet:namespace/>" id="namespace">
<input type="hidden" value="" id="date">
<div id="app">
	<div class="container">
		<div id="searchdiv">
			<input type="text" id="searchcontext"/>
			<button id="searchbtn" @click="searchResume">Search</button>
		</div>
        <div class="impo">
        	<button @click="impEx" id="importBtn" class="btn btn-default btn-primary importbtn">Import</button>
        </div>
    </div>
 </div>

<div id="page" v-cloak>
	<div align="right">
		<ul class="pagination">
			<li><p>pageSize</p></li>
			<li><span name="pageSizeSpan" @click="changeSize(10)">10</span></li>
			<li><span name="pageSizeSpan" @click="changeSize(20)">20</span></li>
			<li><span name="pageSizeSpan" @click="changeSize(50)">50</span></li>
		</ul>
	</div>
	<div class="scrollbar-external_wrapper">
		<div class="table-responsive">
				<tablecomponent url="${showURL}" channelfilter="${filters.channel}" namespace="<portlet:namespace/>" type="all" :tabledata="pageData" columns="name,interviewType,interviewStatus,receivedDate,applyPosition,locus,serviceYear,telephone,email,channel,createDate"></tablecomponent>
				<div class="external-scroll_x">
					<div class="scroll-element_outer">
						<div class="scroll-element_size"></div>
						<div class="scroll-element_track"></div>
						<div id="scrollbar" class="scroll-bar"></div>
					</div>
				</div>
		</div>
	</div>
	<div align="right" id="selectpage">
	<p id="totalrecord">Records:{{totalRecord}}&nbsp;</p>
		<ul class="pagination">
			<li v-if="pageNum>1"><span @click="go(pageNum-1)">&laquo;</span>
			</li>
			<li v-for="num in endPage-startPage+1"><span name="pageNumSpan"
				@click="go(num+startPage-1)">{{num+startPage-1}}</span></li>
			<li v-if="pageNum<totalPage"><span @click="go(pageNum+1)">&raquo;</span>
			</li>
			<li><p>{{pageNum}}/{{totalPage}}</p></li>
			<li><input type="text" v-model="pageInputNum"
				@input="inputValidate" id="pageInputNum"></li>
			<li><input type="button" @click="inputGo" value="GO!"
				id="goButton"></li>
		</ul>
	</div>
	<historypopup :trhistorys="trhistorys" class='historystr' style="background-color:rgba(80,82,84,.9);color:#fff;padding:10px;border-radius:4px;display:none;position:absolute;top:100px;left:100px;"></historypopup>
</div>

<script src="<%=request.getContextPath()%>/js/vue.js"></script>
<script src="<%=request.getContextPath()%>/js/vue-resource.js"></script>
<script src="<%=request.getContextPath()%>/js/jquery-2.0.3.js"></script>
<script src="<%=request.getContextPath()%>/js/jquery.scrollbar.min.js"></script>
<script src="<%=request.getContextPath()%>/js/bootstrap.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/bootbox.js/4.4.0/bootbox.min.js"></script>
<script src="<%=request.getContextPath()%>/js/bootstrap-datetimepicker.js"></script>
<script src="<%=request.getContextPath()%>/js/file-upload.js"></script>
<script src="<%=request.getContextPath()%>/js/constant.js"></script>
<script src="<%=request.getContextPath()%>/js/listcommon.js"></script>
<script src="<%=request.getContextPath()%>/js/listdom.js"></script>
<script src="<%=request.getContextPath()%>/js/pagejs/resume.js"></script>




open: index === currentIndex && item.level === currentLevel
hide: item.level === currentLevel && index !== currentIndex

<li v-for="(item,index) in items" class="has-sub" :class="{'open': index === currentIndex && item.level === currentLevel, 'hide': item.level === currentLevel && index !== currentIndex }" >
        <a @click="expandList(item, index)" href="#">{{item.title}}<span v-if="item.children" class="i caret"></span></a>
            <ul v-if="item.children" class="dropdown-menu level-2">
                <li class="visible-xs"><a @click="backUp" href="javascript:;"><i class="i"></i>Back</a></li>
                <li class="has-sub" v-for="(child, i ) of item.children" :class="{'open': i === currentIndex && child.level === currentLevel, 'hide': child.level === currentLevel && i !== currentIndex}">
                    <i v-if="child.children" @click.prevent="expandList(child,i)" class="i"></i><span>{{child.title}}</span>
                    <ul class="dropdown-menu level-3">
                                            ......
                    </ul>
                </li>
            </ul>
        </li>

items: [
        {
            title:"Trade fair ",
            level: 1,
            children:[
                {
                    title: 'Exhibition profile',
                    level: 2,
                    children:[

                    ]
                },
                {
                    title: 'At the fair',
                    level: 2
                }
            ]
        },
        {
            ......    
        }
        ],
        currentLevel: '',
        currentIndex: '',
        hideFalg: false,
        backFlag: false
       
 expandList: function (item,index) {
          this.currentIndex = index
          this.currentLevel = item.level
}                    
