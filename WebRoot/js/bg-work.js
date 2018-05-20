var timer;
var mainVm=parent.mainVm;
var bgWorkVm = new Vue({
			el : '#beginWork',
			data : {
				workBtns:['开始工作','结束工作'],
				onWork:false
			},
			computed: {
				workBtn: function () {
					if (this.onWork) {
						return this.workBtns[1];
					}else{
						return this.workBtns[0];
					}
				}
			},
			mounted:function(){
				this.setUserStatus();
				
			},
			methods : {
				setUserStatus:function(){
					var mainVm=parent.mainVm;
					mainVm.getUser();
					var self=this;
					timer=setInterval(function(){
					if (mainVm.refreshed) {
						console.log(22);
							var user=mainVm.$data.ssUser;
							
							var startWork=user.startWorkTime;
							var endWork=user.endWorkTime;
							console.log(startWork);
							console.log(endWork);
							console.log(user.onWork);
							if (undefined!=startWork) {
								
								teacherQueueVm.startWorkTime=startWork;
							}
							if (undefined!=endWork) {
								teacherQueueVm.endWorkTime=endWork;
							}
							
							self.onWork=user.onWork;
							window.clearInterval(timer);
						}
					} , 200);
				},
				setFunc:function(){
					var mainVm=parent.mainVm;
						console.log(11);
						if (mainVm.refreshed) {
						console.log(22);
							var user=mainVm.$data.ssUser;
							
							var startWork=user.startWorkTime;
							var endWork=user.endWorkTime;
							console.log(startWork);
							console.log(endWork);
							console.log(user.onWork);
							if (undefined!=startWork) {
								
								teacherQueueVm.startWorkTime=startWork;
							}
							if (undefined!=endWork) {
								teacherQueueVm.endWorkTime=endWork;
							}
							
							this.onWork=user.onWork;
							window.clearInterval(timer);
						}
					
				},
				toggleWork:function(){
					var startTime=$('#startTime').val();
					var endTime=$('#endTime').val();
					if (startTime==''||endTime=='') {
						alert("请先设置上班或下班时间");
						return;
					}
					var self = this;
					console.log(this.onWork);
					var src = "setOnWork.action?onWork="+!this.onWork;
					$.ajax({
						url :src,
						method : 'GET',
						dataType:'json',
						success:function(result){
							if (result.error) {
								
								alert("修改工作状态失败");
							}else {
								self.setUserStatus();
								alert("修改工作状态成功");
							}
						} 
					});
				}
			}
});