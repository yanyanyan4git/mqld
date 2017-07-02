

		var teacherQueueVm = new Vue({
			el : '#queue',
			data : {
				action:'排队处理',
				actions:['排队处理','工作时间'],
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
						return null;
					}else {
						var item=paginVm.records[paginVm.recordIndex];
						this.studentName=item.studentName;
						console.log(item.ID);
						this.queueID=item.ID;
						return item;
					}
				  },
				  nullField:function(){
					  return this.path==''||this.comment=='';
				  }
				
			},
			mounted:function(){
				initTimePeriod($('#startTime'),$('#endTime'));
				paginVm.preUrl ="getQueuedStudent.action?";
				paginVm.go(1);
			},
			methods : {
				setWorkTime:function(){
					var self = this;
					var startTime=$('#startTime').val();
					var endTime=$('#endTime').val();
					if (startTime==''||endTime=='') {
						alert("上班或下班时间未填写");
						return;
					}
					var src = "setWorkTime.action?startWorkTime="+startTime+"&endWorkTime="+endTime;
					$.ajax({
						url :src,
						method : 'GET',
						dataType:'json',
						success:function(result){
							if (result.error) {
								alert("修改工作时间失败");
							}else {
								bgWorkVm.setUserStatus();
								alert("修改工作时间成功");
							}
						} 
					});
				},
				resolveQueue: function() {
					var self = this;
					var src = "resolveQueue.action?path="+this.path+"&comment="+this.comment+"&queueID="+this.queueID;
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
								self.result=self.studentName+"解决成功";
								self.success=true;
							}
						} 
					});
				}
			}
		});
		
		
		
		