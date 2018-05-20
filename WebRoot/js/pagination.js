var Paginbar={
		
	 props: ['data'],
     template: '<div align="right" ><p >记录条数:{{data.totalRecord}}&nbsp;</p><ul class="pagination"><li v-if="data.currentPage>1"><span v-on:click="pagin(data.currentPage-1)">&laquo;</span></li><li v-for="num in data.endPage-data.startPage+1"><span  v-on:click="pagin(num+data.startPage-1)" :class="num==data.currentPage?\'show\':\'\'">{{num+data.startPage-1}}</span></li><li v-if="data.currentPage<data.pageNum"><span v-on:click="pagin(data.currentPage+1)">&raquo;</span></li><li><p>{{data.currentPage}}/{{data.pageNum}}</p></li><li><input id="pageinInput" type="text" v-model="data.inputNum" :class="[data.inputVal?\'\':\'red\',\'pagin-input\']"></li><li><input type="button" v-on:click="inputgo" value="GO!"></li></ul></div>',
     
     methods: {
      	 	pagin: function (num) {
    	      this.$emit('pagin',num);
    	    },
    	    inputgo: function () {
      	      this.$emit('inputgo');
      	    },
    	  }
     
}

var paginVm = new Vue({
			el : '#pagination',
			data : {
				IDs:[],
				records : [],
				recordIndex:null,
				pageSize : 10,
				currentPage:1,
				pageNum : 0,
				startPage : 0,
				endPage : 0,
				pageInputNum : '',
				paramData:null,
				totalRecord : 0,
				inputNum:'',
				userType:'',
				preUrl:'',
				preDelUrl:'',
				inputVal:true,
				allChecked:false
			}, 
			components: {
			    'paginbar': Paginbar
			},
			computed: {
				  inputValidate: function () {
					if (this.inputNum=='') {
						this.inputVal=true;
						return true;
					}
					var inputNum=parseInt(this.inputNum); 
					this.inputVal=inputNum >= 0 && inputNum <= this.pageNum;
					return this.inputVal;
				  },
				  sufUrl: function () {
					    return "&currentPage="+this.currentPage+"&pageSize="+this.pageSize;
					  },
				  url: function () {
					    return this.preUrl+this.sufUrl;
					  },
				  sufDelUrl: function () {
				    return "&IDs="+this.IDs;
				  },
				  delUrl: function () {
					    return this.preDelUrl+this.sufDelUrl;
				  }  
			},
			methods : {
				checkAll: function() {
					var self=this;
					    if (this.allChecked) {
					      this.IDs = [];
					     
					    }else{
					      this.IDs = [];
					      this.records.forEach(function(item) {
					    	  self.IDs.push(item.ID);
					      });
					      
					    }
					    this.allChecked=!this.allChecked;
					  }	,
				del:function(){
					if (this.IDs==[]) {
						return;
					}
					console.log(this.IDs);
					var self = this;
					var sure=window.confirm("确定删除所选用户？");
					if (!sure) {
						return;
					}
					$.ajax({
						url :self.delUrl,
						method : 'GET',
						dataType:'json',
						success:function(result){
							if (result.error) {
								alert(result.error);
								self.go(1);
							}else {
								alert("删除成功");
								self.go(1);
							}
							self.IDs=[];
						} ,
						error:function(request, textStatus, errorThrown){
							self.IDs=[];
							console.log("Error: " + textStatus);
						}
						
					});
				},
				
				dealWithResponse:function(data){
					 page=data.page;
					this.totalRecord = page.totalRecord;
					this.pageNum = page.pageNum;
					this.currentPage=page.currentPage;
					this.startPage = page.startPage;
					this.endPage = page.endPage;
					this.records=page.records;
				},
				go : function(number) {
					this.currentPage=number;
					var self = this;
					$.ajax({
						url :self.url,
						method : 'POST',
						dataType:'json',
						data:self.paramData,
						contentType:"application/json",
						success:function(result){
							self.dealWithResponse(result.data);
							self.IDs=[];
						} 
					});
				},
				inputGo : function() {
					if (this.inputValidate)
						this.go(parseInt(this.inputNum));
					else
						return;
						
				},
				refresh: function() {
					this.go(this.currentPage);
				}
				
				
			}
		});