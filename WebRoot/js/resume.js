var pagevue = new Vue({
	el : '#registPage',
	data : {
		pageData : [],
		size : 10,
		pageNum : 1,
		totalPage : 0,
		startPage : 0,
		endPage : 8,
		pageInputNum : '',
		totalRecord : 0,
		url : '',
		namespace : '',
		filterparam:'',
		keyword:'',
		minworkingyear:'',
		maxworkingyear:'',
		interviewStatus:-1,
		interviewType:-1,
		historys:[],
		trhistorys:[],
		historysinterview:[],
		currentinterview:[],
		historyinterview:[],
		starttime:"",
		endtime:""
	},
	mounted : function() {
		var url = document.getElementById("url");
		if (url != null) {
			this.url = url.value;
		}
		var namespace = document.getElementById("namespace");
		if (namespace != null) {
			this.namespace = namespace.value;
		}
		/*this.go(1);*/
	},
	methods : {
		dealWithResponse:function(data){
			this.totalRecord = data.totalRecord;
			this.pageNum = data.pageNum;
			this.totalPage = data.totalPage;
			this.startPage = data.startPage;
			this.endPage = data.endPage;
		},
		go : function(number) {
			var self = this;
			var parameter='{"'+this.namespace+'pageNum":"'+number+'","'+this.namespace+'pageSize":"'+this.size+'"'+this.filterparam+',"'+this.namespace+'keyword":"'+this.keyword+'","'+this.namespace+'starttime":"'+this.starttime+'","'+this.namespace+'endtime":"'+this.endtime+'","'+this.namespace+'minworkingyear":"'+this.minworkingyear+'","'+this.namespace+'maxworkingyear":"'+this.maxworkingyear+'"}';
			parameter = JSON.parse(parameter);
			$.ajax({
				url : this.url,
				method : 'GET',
				data : parameter,
				async :false,
				dataType:'json',
				success:function(data){
					self.dealWithResponse(data);
				} 
			});
			this.showPageInfo(this.pageNum, "pageNumSpan");
		},
		allSelectedType:function(){
			var namespace=this.namespace;
			var types=this.getTypes();
			var filters=',';
			$.each(types,function(index,value){
				var values='[';
				$("input[name="+value+"]:checked").each(function(){
					var selectedval = $(this).val();
					values+='"'+selectedval+'",';
				});
				values=values.substring(0,values.length-1);
				values+=']';
				filters+='"'+namespace+value+'":'+values+',';
			});
			filters=filters.substring(0,filters.length-1);
			this.filterparam=filters;
			this.go(1);
		},
		getTypes:function(){
			var types=[];
			$("input:checked").each(function(){
				var name=$(this).attr("name");						
				if($.inArray(name,types)===-1){
					types.push(name);
				}
	  		});
			return types;
		},
		inputValidate : function() {
			var input = document.getElementById('pageInputNum');
			this.inputClass(input);
		},
		inputGo : function() {
			if (!(this.pageInputNum > 0 && this.pageInputNum <= this.totalPage))
				return;
			else
				this.go(this.pageInputNum);
		},
		inputClass : function(input) {
			if (!(this.pageInputNum >= 0 && this.pageInputNum <= this.totalPage)) {
				input.className = 'red';
			} else {
				input.className = '';
			}
		},
		setSpanClassName : function (span,num){
			var className='';
			if (span === num+"") 
				className = 'show';
			return className;
		},
		showPageInfo : function(num, elementsName) {
			var spans = document.getElementsByName(elementsName);
			for (var i = 0; i < spans.length; i++) {
				spans[i].className=this.setSpanClassName(spans[i].innerHTML,num);
			}
		},
		changeSize : function(size) {
			this.size = size;
			this.go(1);
		},
		changeInterviewType:function(interviewType){
			var map=["New","Screen","Phone","Technical","PP","PM","Director","Offer"];
			return map[interviewType];
		},
		changeInterviewStatus:function(interviewStatus){
			var map=["Save","Pass","Fail","Next","Skip"];
			return map[interviewStatus];
		},
		changeDateFormat:function(date){
			if(date!=null){
				var dat = new Date(date.time);
				var dateN = (dat.getFullYear()) + "-" + (dat.getMonth() + 1) + "-"
						+ (dat.getDate());
				return dateN;
			}	
		}
	}
});