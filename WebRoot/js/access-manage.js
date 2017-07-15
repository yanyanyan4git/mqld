
		var managerVm = new Vue({
			el : '#manager',
			data : {
				userType:'',
				authorities:['学生','助教','管理员'],
				ID:'',
				name:'',
				authority:'---请选择权限--- ',
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
						if (item==null) {
							return null;
						}
						this.ID=item.ID;
						this.name=item.name;
						this.authority=item.authority;
						return item;
					}
				  },
				  nullField:function(){
					  return this.ID==''||this.name==''||this.authority=='';
				  }
				
			},
			mounted:function(){
				paginVm.preUrl ="getUsers.action?&userType="+this.userType;
				paginVm.preDelUrl ="delUser.action?";
				paginVm.go(1);
			},
			methods : {
				toggleAuthor : function(authority){
					this.authority=authority;
				},
				submit: function() {
					var self = this;
					var src = "doManagement.action?ID="+this.ID+"&name="+this.name+"&authority="+this.authority;
					$.ajax({
						url :src,
						method : 'GET',
						dataType:'json',
						success:function(result){
							if (result.error) {
								alert("修改失败");
								self.result=self.ID+"修改失败";
								self.success=false;
							}else {
								self.result=self.ID+"修改成功";
								self.success=true;
								paginVm.refresh();
							}
						} 
					});
				}
			}
		});
