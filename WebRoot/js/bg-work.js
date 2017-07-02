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
					var timer=setInterval(() => {
						if (mainVm.refreshed) {
							var user=mainVm.ssUser;
							teacherQueueVm.startWorkTime=user.startWorkTime;
							teacherQueueVm.endWorkTime=user.endWorkTime;
							this.onWork=user.onWork;
							console.log(11);
							window.clearInterval(timer);
						}
					}, 200);
				},
				toggleWork:function(){
					var self = this;
					
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