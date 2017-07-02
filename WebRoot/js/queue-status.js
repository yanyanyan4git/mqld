
var profLevel='';
var attitude='';
var perfLevel=['','差','一般','满意','赞'];
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
				queueID:'',
				status:'',
				error:''
				
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
									this.queueID=item.ID;
									this.status=item.status;
									return item;
								}
						}
					
				  },
				  canQueued:function(){
					  if (this.maxStudentNum==0||this.currentQueueNum==0) {
						return true;
					  }
					  return maxStudentNum>currentQueueNum;
				  },
				  nullField:function(){
					  if (this.hasQueued) {
						return false;
					  }
					  return this.picNum=='---请选择---'||this.path==''||this.comment==''||null==this.queue;
				  }
				
			},
			mounted:function(){
				
				$("[name='profLevel']").raty({
					number: 4,
					  click: function(score, evt) {
						  profLevel= perfLevel[score];
						  $("#showProfLevel").text(profLevel);
						  }
				});
				$("[name='attitude']").raty({
					number: 4,
					  click: function(score, evt) {
						  attitude=perfLevel[score];
						  $("#showAttitude").text(attitude);
						  }
				});
				$(".raty").css("margin-right","50px");
				
				paginVm.preUrl ="getQueueHistory.action?";
				paginVm.go(1);
				
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
				evaluate:function(){
					alert(attitude+""+profLevel+this.comment);
					var self = this;
					var src = "doEvaluate.action?attitude="+attitude+"&profLevel="+profLevel+"&comment="+this.comment+"&queueID="+this.queueID;
					$.ajax({
						url :src,
						method : 'GET',
						dataType:'json',
						success:function(result){
							if (result.error) {
								self.success=false;
								self.result="评价提交失败";
								alert("评价提交失败，请重试");
							}else {
								self.success=true;
								self.result="评价提交成功";
								paginVm.refresh();
							}
						} 
					});
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
								self.currentQueueNum=queue.currentQueueNum;
							}
						} 
					});
				},
				doQueue: function() {
					var self = this;
					var src = "doQueue.action?picNum="+this.picNum
							+"&path="+this.path+"&comment="+this.comment
							+"&teacherName="+this.teacherName+"&teacherID="+this.teacherID;
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

		