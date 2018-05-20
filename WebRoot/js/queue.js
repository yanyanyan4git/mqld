

		var queueVm = new Vue({
			el : '#queue',
			data : {
				maxPicNum:3,
				teacherID:'',
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
				showPanel:true,
				error:'',
				teacherOnWork:false,
				totalQueueNum:''
			},
			 components: {
				    'selector': Selector,
				    'field':FormField,
				    'feedback':Feedback
				  },
			computed: {
				DoC:function(){
					if (this.hasQueued) {
						return '取消排队';
					}else {
						return '排队';
					}
				},
			
				queue: function () {
					 if (this.hasQueued) {
							return null;
						  }else {
							  if (paginVm.recordIndex==null) {
									return null;
								}else {
									var item=paginVm.records[paginVm.recordIndex];
									this.teacherID=item.teacherID;
									this.teacherName=item.teacherName;
									this.currentQueueNum=item.currentQueueNum;
									this.totalQueueNum=item.totalQueueNum;
									this.maxStudentNum=item.maxStudentNum;
									this.teacherStyle=item.teacherStyle;
									this.startWorkTime=item.startWorkTime;
									this.endWorkTime=item.endWorkTime;
									this.teacherOnWork=item.teacherOnWork;
									return item;
								}
						}
					
				  },
				  canQueue:function(){
					  if (this.maxStudentNum==0||this.teacherOnWork==false) {
						  
						return false;
					  }
					  var flag=this.maxStudentNum>this.totalQueueNum;
					  return flag;
				  },
				  nullField:function(){
					  var flag=this.picNum=='---请选择---'||this.path==''||this.comment==''||null==this.queue;
					  return flag;
				  }
				
			},
			mounted:function(){
				paginVm.preUrl ="getQueues.action?";
				paginVm.go(1);
				var authority=parent.mainVm.ssUser.authority;
				if (authority=='管理员') {
					this.showPanel=false;
				}
				this.isQueued();
			},
			methods : {
				clearField:function(){
					this.picNum=='---请选择---';
					this.path=='';
					this.comment=='';
				},
				togglePicNum : function(picNum){
					this.picNum=picNum;
				},
				excuteDoC:function() {
					if (this.hasQueued) {
						if (window.confirm('确定取消排队？')) {
							this.cancelQueue();
						}
					}else {
						this.doQueue();
					}
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
								self.hasQueued=result.data.isQueued;
								if (self.hasQueued) {
									self.getCurrentInfo();
								}
							}
						} 
					});
				},
				getCurrentInfo:function(){
					var self = this;
					var src = "getCurrentInfo.action";
					$.ajax({
						url :src,
						method : 'GET',
						dataType:'json',
						success:function(result){
							if (result.error) {
								alert("获取具体信息失败");
							}else {
								var queue=result.data.queue;
								self.teacherName=queue.teacherName;
								self.path=queue.studentPath;
								self.comment=queue.studentComment;
								self.maxStudentNum=queue.maxStudentNum;
								self.picNum=queue.pictureNum;
								self.currentQueueNum=queue.currentQueueNum-1;
							}
						} 
					});
				},
				doQueue: function() {
					var self = this;
					var queue={
							pictureNum:this.picNum,
							studentPath:this.path,
							studentComment:this.comment,
							teacherID:this.teacherID
					};
					var src = "doQueue.action";
					$.ajax({
						url :src,
						method : 'POST',
						dataType:'json',
						data:JSON.stringify(queue),
						contentType:"application/json",
						success:function(result){
							if (result.error) {
								alert("排队失败");
								self.result="排队失败";
								self.success=false;
							}else {
								self.result="您已进入队列";
								self.success=true;
								self.hasQueued=true;
								paginVm.refresh();
							}
						} 
					});
				}
			}
		});

		