function getToday() {
	var today= new Date();
	var year=today.getFullYear();
	var month=today.getMonth()+1;
	var date=today.getDate();
	if (date<10) {
		date="0"+date;
	}
	if (month<10) {
		month="0"+month;
	}
	var todayStr=year+"-"+month+"-"+date;
	return todayStr;
}