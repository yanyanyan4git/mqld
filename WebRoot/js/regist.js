


var registVm = new Vue({
	el : '#registPage',
	data : {
		
		types:['学生','助教'],
		type:'学生',
		ID:'',
		name:'',
		password:'',
		IDError:'',
		nameError:'',
		pwError:'',
		styleError:'',
		success:false,
		result:'',
		fixPw:false,
		fixID:false,
		successName:''
	},
	 components: {
			    'selector': Selector,
			    'field':FormField,
			    'feedback':Feedback
		  },
	computed : {
		IDHelpler: function () {
			if (this.type=='学生') {
				return 's+6位数字';
			}else {
				return 't+6位数字';
			}
		}
	},
	mounted:function(){
		 $('#usertype').selectpicker({
			 noneSelectedText:"请选择助教风格",
			 actionsBox:true,
			 deselectAllText:'全不选',
			 selectAllText:'全选'
		    });
	},
	methods : {
		toggleTab:function(type){
			this.type=type;
			this.fixID=false;
			this.fixPw=false;
			this.clearField();
			this.clearResult();
			this.clearError();
		},
		autoIncre:function(){
			var regex=/^[Ss]\d{6}$/;
			if (this.type=='助教') {
				regex=/^[Tt]\d{6}$/;
			}
			if (null==this.ID||''==this.ID||!(regex.test(this.ID))) {
				this.IDError='ID不符合规则';
				return;
			}else {
				this.IDError='';
				this.fixID=!this.fixID;
				
			}
			
		},
		
		regist:function(){
			if (!this.validate()) {
				this.clearResult();
				return;
			}
			var self = this;
			var serialData=$( "#form").serialize();
			var src = "doRegist.action?" +serialData+"&userType="+this.type+"&style="+ $('#usertype').val();
			 $.ajax({
					url: src,
					dataType:"json",
					success:function(result){
						data=[];
						if(result.error){
							alert(result.error+"注册失败！");
							self.success=false;
							self.result=self.ID+"注册失败";
						}else if(result.data.result){
							self.successName=registVm.name;
							self.success=true;
							self.result=self.ID+"注册成功";
							if (self.fixID) {
								var num=parseInt(self.ID.substr(1, 6))+1;
								self.ID=self.ID.charAt(0)+num;
							}
							
							self.clearField();
						}else{
							alert("注册失败！");
							self.success=false;
							self.result=self.ID+"注册失败";
						}	
					},
					error:function(request, textStatus, errorThrown){
						console.log("Error: " + textStatus);
					}
			 }); 
		},
		validate:function(){
			var flag=true;
			var regex=/^[Ss]\d{6}$/;
			if (this.type=='助教') {
				regex=/^[Tt]\d{6}$/;
				if ($('#usertype').val().length==0) {
					this.styleError='未选择风格';
					flag=false;
				}else {
					this.styleError='';
				}
			}
			if (this.ID=='') {
				this.IDError='ID为空';
				flag=false;
			}else if(!(regex.test(this.ID))) {
				this.IDError='ID不符合规则';
				flag=false;
			}else {
				this.IDError='';
			}
			if (this.name=='') {
				this.nameError='姓名为空';
				flag=false;
			}else {
				this.nameError='';
			}
			if (this.password.length<6) {
				this.pwError='密码小于6位';
				flag=false;
			}else {
				this.pwError='';
			}
			return flag;
		},
		clearError:function(){
			this.IDError='';
			this.nameError='';
			this.pwError='';
			this.styleError='';
		},
		clearField:function(){
			if (!this.fixID) {
				this.ID='';
			}
			this.name='';
			if (!this.fixPw) {
				this.password='';
			}
			this.style='---请选择助教风格---';
		},
		clearResult:function(){
			this.success=false;
			this.result='';
		}
		
	}
});