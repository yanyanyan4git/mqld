var PaginToolBarVm = new Vue({
	el : '#paginToolBar',
	data : {
		condition:{
			ID:null,
			startCreateTime:null,
			endCreateTime:null,
			lowTotalScore:null,
			highTotalScore:null,
			scoreAsc:true
		}
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
		initDatePeriod($('#startTime'),$('#endTime'));
		paginVm.paramData=JSON.stringify(this.condition);
		paginVm.preUrl ="getTeachersPerf.action?";
		paginVm.go(1);
	},
	methods : {
		submit: function() {
			this.condition.startCreateTime=$('#startTime').val();
			this.condition.endCreateTime=$('#endTime').val();
			paginVm.paramData=JSON.stringify(this.condition);
			paginVm.preUrl ="getTeachersPerf.action?";
			paginVm.go(1);
		}
	}
});