
		var queueVm = new Vue({
			el : '#queue',
			data : {
				maxPicNum:3,
				teacherName:'',
				currentQueueNum:'',
				maxStudentNum:'',
				teacherStyle:'',
				startWorkTime:'',
				endWorkTime:'',
				picNum:'---请选择---',
				path:'',
				comment:'',
				hasQueued:false,
				success:false,
				result:'',
				error:''
			},
			 components: {
				    'selector': Selector,
				    'field':FormField,
				    'feedback':Feedback
				  },
			computed: {
				user: function () {
					if (paginVm.recordIndex==null) {
						return {
							teacherName:'',
							currentQueueNum:'',
							maxStudentNum:'',
							teacherStyle:'',
							startWorkTime:'',
							endWorkTime:''
						};
					}else {
						var item=paginVm.records[paginVm.recordIndex];
						this.teacherName=item.teacherName;
						this.currentQueueNum=item.currentQueueNum;
						this.maxStudentNum=item.maxStudentNum;
						this.teacherStyle=item.teacherStyle;
						this.startWorkTime=item.startWorkTime;
						this.endWorkTime=item.endWorkTime;
						return item;
					}
				  },
				  canQueued:function(){
					  if (this.maxStudentNum==0||this.currentQueueNum==0) {
						return true;
					  }
					  return maxStudentNum>currentQueueNum;
				  },
				  nullField:function(){
					  return this.picNum=='---请选择---'||this.path==''||this.comment=='';
				  }
				
			},
			mounted:function(){
				paginVm.preUrl ="getQueues.action?";
				paginVm.go(1);
				isQueued();
			},
			methods : {
				togglePicNum : function(picNum){
					this.picNum=picNum;
				},
				cancelQueue: function() {
					var self = this;
					var src = "cancelQueue.action";
					$.ajax({
						url :src,
						method : 'GET',
						dataType:'json',
						success:function(result){
							if (result.error) {
								alert("取消排队失败，请确定排队状态");
							}else {
								alert("取消排队成功");
								self.hasQueued=false;
							}
						} 
					});
				},
				isQueued:function(){
					var self = this;
					var src = "isQueued.action";
					$.ajax({
						url :src,
						method : 'GET',
						dataType:'json',
						success:function(result){
							if (result.error) {
								alert("获取排队信息失败");
							}else {
								
								self.hasQueued=false;
							}
						} 
					});
				},
				doQueue: function() {
					var self = this;
					var src = "doQueue.action?picNum="+this.picNum+"&path="+this.path+"&comment="+this.comment;
					$.ajax({
						url :src,
						method : 'GET',
						dataType:'json',
						success:function(result){
							if (result.error) {
								alert("排队失败");
								self.result="排队失败";
								self.success=false;
							}else {
								self.result="您已进入队列";
								self.success=true;
								self.hasQueued=true;
							}
						} 
					});
				}
			}
		});
