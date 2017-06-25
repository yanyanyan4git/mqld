var Paginbar={
		
	 props: ['data'],
     template: '<div align="right" ><p >Records:{{data.totalRecord}}&nbsp;</p><ul class="pagination"><li v-if="data.currentPage>1"><span v-on:click="pagin(data.currentPage-1)">&laquo;</span></li><li v-for="num in data.endPage-data.startPage+1"><span  v-on:click="pagin(num+data.startPage-1)" :class="num==data.currentPage?\'show\':\'\'">{{num+data.startPage-1}}</span></li><li v-if="data.currentPage<data.pageNum"><span v-on:click="pagin(data.currentPage+1)">&raquo;</span></li><li><p>{{data.currentPage}}/{{data.pageNum}}</p></li><li><input type="text" v-model="data.inputNum" :class="[data.inputVal?\'\':\'red\',\'pagin-input\']"></li><li><input type="button" v-on:click="inputgo" value="GO!"></li></ul></div>',
     
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
				records : [],
				recordIndex:null,
				pageSize : 10,
				currentPage:1,
				pageNum : 0,
				startPage : 0,
				endPage : 0,
				pageInputNum : '',
				totalRecord : 0,
				inputNum:'',
				userType:'',
				preUrl:'',
				inputVal:true	
			}, 
			components: {
			    'paginbar': Paginbar,
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
					  }
			},
			methods : {
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
						method : 'GET',
						dataType:'json',
						success:function(result){
							self.dealWithResponse(result.data);
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