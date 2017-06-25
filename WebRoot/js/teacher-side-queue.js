
		var studengQueueVm = new Vue({
			el : '#queue',
			data : {
				queueID:'',
				studentName:'',
				currentQueueNum:'',
				maxStudentNum:'',
				teacherStyle:'',
				startWorkTime:'',
				endWorkTime:'',
				path:'',
				comment:'',
				showWorkTime:false,
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
							studentName:'',
							queueID:''
						};
					}else {
						var item=paginVm.records[paginVm.recordIndex];
						this.studentName=item.studentName;
						this.queueID=item.queueID;
						return item;
					}
				  },
				  nullField:function(){
					  return this.path==''||this.comment=='';
				  }
				
			},
			mounted:function(){
				paginVm.preUrl ="getQueuedStudent.action?";
				paginVm.go(1);
			},
			methods : {
				setWorkTime:function(){
					var self = this;
					var src = "setWorkTime.action?startWorkTime="+this.startWorkTime+"&endWorkTime="+this.endWorkTime;
					$.ajax({
						url :src,
						method : 'GET',
						dataType:'json',
						success:function(result){
							if (result.error) {
								alert("修改工作时间失败");
							}else {
								alert("修改工作时间成功");
							}
						} 
					});
				},
				resolveQueue: function() {
					var self = this;
					var src = "resolveQueue.action?path="+this.path+"&comment="+this.comment;
					$.ajax({
						url :src,
						method : 'GET',
						dataType:'json',
						success:function(result){
							if (result.error) {
								alert(self.studentName+"解决失败");
								self.result=self.studentName+"解决失败";
								self.success=false;
							}else {
								self.result=self.studentNam+"解决成功";
								self.success=true;
							}
						} 
					});
				}
			}
		});
