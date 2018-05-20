	function initTimePicker(input){
		input.datetimepicker({
			bootcssVer:3,
			autoclose : true,
			clearBtn : true,
			startView:1,
			maxView:1,
			minView:0,
			
			forceParse:false,
			format:"hh:ii"
		});
	}
	
	function initDatePicker(input){
		input.datetimepicker({
			bootcssVer:3,
			autoclose : true,
			todayBtn : "linked",
			clearBtn : true,
			todayHighlight : true,
			startView:2,
			minView: 2,
			format:"yyyy-mm-dd",
			forceParce:false
		});
	}
	
	function initMonthPicker(input){
		input.datetimepicker({
			bootcssVer:3,
			autoclose : true,
			todayBtn : "linked",
			clearBtn : true,
			todayHighlight : true,
			startView:3,
			minView: 3,
			format:"yyyy-mm",
			forceParce:false
		});
	}
	
	function initYearPicker(input){
		input.datetimepicker({
			bootcssVer:3,
			autoclose : true,
			todayBtn : "linked",
			clearBtn : true,
			todayHighlight : true,
			startView:4,
			minView: 4,
			format:"yyyy",
			forceParce:false
		});
	}
	
	function initDatePeriod(startInput,endInput){
		initDatePicker(startInput);
		startInput.on('changeDate', function(e) {
			var startTime = e.date;
			endInput.datetimepicker('setStartDate', startTime);
		});
		
		initDatePicker(endInput);
		endInput.on('changeDate', function(e) {
			var endTime = e.date;
			startInput.datetimepicker('setEndDate', endTime);
		});
		if(null!=startInput.val()&&""!=startInput.val)
			endInput.datetimepicker('setStartDate', startInput.val());
		if(null!=endInput.val()&&""!=endInput.val)
			startInput.datetimepicker('setEndDate',endInput.val());
	}
	
	function initMonthPeriod(startInput,endInput){
		initMonthPicker(startInput);
		startInput.on('changeDate', function(e) {
			var startTime = e.date;
			endInput.datetimepicker('setStartDate', startTime);
		});
		
		initMonthPicker(endInput);
		endInput.on('changeDate', function(e) {
			var endTime = e.date;
			startInput.datetimepicker('setEndDate', endTime);
		});
		if(null!=startInput.val()&&""!=startInput.val)
			endInput.datetimepicker('setStartDate', startInput.val());
		if(null!=endInput.val()&&""!=endInput.val)
			startInput.datetimepicker('setEndDate',endInput.val());
	}
	
	function initYearPeriod(startInput,endInput){
		initYearPicker(startInput);
		startInput.on('changeDate', function(e) {
			var startTime = e.date;
			endInput.datetimepicker('setStartDate', startTime);
		});
		
		initYearPicker(endInput);
		endInput.on('changeDate', function(e) {
			var endTime = e.date;
			startInput.datetimepicker('setEndDate', endTime);
		});
		if(null!=startInput.val()&&""!=startInput.val)
			endInput.datetimepicker('setStartDate', startInput.val());
		if(null!=endInput.val()&&""!=endInput.val)
			startInput.datetimepicker('setEndDate',endInput.val());
	}
	function initTimePeriod(startInput,endInput){
		initTimePicker(startInput);
		startInput.on('changeDate', function(e) {
			var startTime = e.date;
			endInput.datetimepicker('setStartDate', startTime);
		});
		
		initTimePicker(endInput);
		endInput.on('changeDate', function(e) {
			var endTime = e.date;
			startInput.datetimepicker('setEndDate', endTime);
		});
		if(null!=startInput.val()&&""!=startInput.val)
			endInput.datetimepicker('setStartDate', startInput.val());
		if(null!=endInput.val()&&""!=endInput.val)
			startInput.datetimepicker('setEndDate',endInput.val());
	}
	