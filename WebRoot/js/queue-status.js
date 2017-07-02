
var profLevel=0;
var attitude=0;
var profLevelShow='';
var attitudeShow='';
var perfLevel=['','差','一般','满意','赞'];
		var queueVm = new Vue({
			el : '#queue',
			data : {
				comment:'',
				success:false,
				result:'',
				queueID:'',
				status:'',
				error:''
				
			},
			 components: {
				    'field':FormField,
				    'feedback':Feedback
				  },
			computed: {
				
				
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
			},
			mounted:function(){
				
				$("[name='profLevel']").raty({
					number: 4,
					  click: function(score, evt) {
						  profLevel=score;
						  profLevelShow= perfLevel[score];
						  $("#showProfLevel").text(profLevelShow);
						  }
				});
				$("[name='attitude']").raty({
					number: 4,
					  click: function(score, evt) {
						  attitude=score;
						  attitudeShow=perfLevel[score];
						  $("#showAttitude").text(attitudeShow);
						  }
				});
				$(".raty").css("margin-right","50px");
				paginVm.preUrl ="getQueueHistory.action?";
				paginVm.go(1);
				
			},
			methods : {
				evaluate:function(){
					var conf=confirm("您的评价是：改图态度为"+attitudeShow+"，改图水平为"+profLevelShow+',确定提交？');
					if (!conf) {
						return;
					}
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
								self.clearField();
								paginVm.refresh();
							}
						} 
					});
				},
				clearField:function(){
					this.comment=null;
					profLevelShow='';
					attitudeShow='';
					profLevel=0;
					attitude=0;
					$("[name='profLevel']").raty('setScore',0);
					$("[name='attitude']").raty('setScore',0);
					$("#showProfLevel").text(profLevelShow);
					$("#showAttitude").text(attitudeShow);
				}
			}
		});

		