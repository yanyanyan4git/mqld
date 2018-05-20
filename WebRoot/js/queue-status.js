

		var queueVm = new Vue({
			el : '#queue',
			data : {
				comment:'',
				success:false,
				result:'',
				queueID:'',
				status:'',
				error:'',
				profLevel:0,
				attitude:0,
				profLevelShow:'',
				attitudeShow:'',
				perfLevel:['','差','一般','满意','赞']
				
			},
			 components: {
				    'field':FormField,
				    'feedback':Feedback
				  },
			computed: {
				canEvaluate:function(){
					if (this.queue==null) {
						return null
					}else {
						var emptyField=this.profLevel==0||this.attitude==0||this.comment=='';
						console.log(this.comment);
						return this.queue.status=='已解决'&&!emptyField;
					}
			},
				
				
				queue: function () {
					 if (this.hasQueued) {
						 console.log('has queued');
							return null;
						  }else {
							  if (paginVm.recordIndex==null) {
								  console.log('null recordIndex');
									return null;
								}else {
									var item=paginVm.records[paginVm.recordIndex];
									this.queueID=item.ID;
									this.status=item.status;
									console.log('has queue');
									return item;
								}
						}
					
				  },
			},
			mounted:function(){
				var self=this;
				$("[name='profLevel']").raty({
					number: 4,
					  click: function(score, evt) {
						  self.profLevel=score;
						  self.profLevelShow=self.perfLevel[score];
						  }
				});
				$("[name='attitude']").raty({
					number: 4,
					  click: function(score, evt) {
						  self.attitude=score;
						  self.attitudeShow=self.perfLevel[score];
						  }
				});
				$(".raty").css("margin-right","50px");
				paginVm.preUrl ="getQueueHistory.action?";
				paginVm.go(1);
				
			},
			methods : {
				evaluate:function(){
					var conf=confirm("您的评价是：改图态度为"+this.attitudeShow+"，改图水平为"+this.profLevelShow+',确定提交？');
					if (!conf) {
						return;
					}
					var self = this;
					var src = "doEvaluate.action?attitude="+this.attitude+"&profLevel="+this.profLevel+"&comment="+this.comment+"&queueID="+this.queueID;
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
								self.clearField();
								paginVm.refresh();
							}
						} 
					});
				},
				clearField:function(){
					this.comment=null;
					this.profLevelShow='';
					this.attitudeShow='';
					this.profLevel=0;
					this.attitude=0;
					$("[name='profLevel']").raty('setScore',0);
					$("[name='attitude']").raty('setScore',0);
				}
			}
		});

		