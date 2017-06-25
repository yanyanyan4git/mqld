	var date;
	var range ;
	var gRange ;
	var year;
	var reportType;
	$(function() {
		 date=$("#prDate_h").val();
		 pageNum=parseInt($("#pageNum_h").val());
		 totalRecord=parseInt($("#totalRecord_h").val());
		 currentPage=parseInt($("#currentPage_h").val());
		 range = "Month";
		 gRange = "Month";
		 reportType = "";
		$("#datatable").css("padding-top", $("#datediv").outerHeight());
		initDatePicker($('#logDatePicker'));
		initMonthPicker($('#logMonthPicker'));
		initYearPicker($('#logYearPicker'));
		initMonthPeriod($('#gMonthBegin'), $('#gMonthEnd'));
		initMonthPeriod($('#gDateBegin'), $('#gDateEnd'));
		$('#logDatePicker').hide();
		$('#logYearPicker').hide();
		$('#gDateSpan input').hide();
		$('#gDateSpan span').hide();
		paginBarSize=9;
		initPaginationView($("li[name='paginBtn']"));
		setPageInfo($("#pageInfo"));
		initPaginationEvent($("li[name='paginBtn']"));
		initComponentEvent();
		initQueryEvent();
		initGenerationEvent();
	});
	function initComponentEvent(){
		$("#range ul li").click(function() {
			range = $(this).text();
			$("#logDate input").hide();
			switch (range) {
			case "Year":
				$('#logYearPicker').show();
				break;
			case "Month":
				$('#logMonthPicker').show();
				break;
			case "Date":
				$('#logDatePicker').show();
				break;
			default:
				break;
			}
		});
		
		$("#gRange ul li").click(function() {
			gRange = $(this).text();

			$("#gLogDate input").hide();
			$('#gDateSpan span').hide();
			$('#gMonthSpan span').hide();
			switch (gRange) {
			case "Date":
				$('#gDateSpan input').show();
				$('#gDateSpan span').show();
				break;
			case "Month":
				$('#gMonthSpan input').show();
				$('#gMonthSpan span').show();
				break;
			default:
				break;
			}
		});
		
		$("span.btn-group ul li").click(function() {
			$(this).parent().prev().html($(this).text() + "<span class='caret'></span>");
		});
		
		$('#itdgm-Tab a').click(function(e) {
			e.preventDefault();
			$(this).tab('show');
		});
		
		$("#reportType button").click(function() {
			reportType = $(this).val();
			var btn = $(this).attr('name');
			$("[name='" + btn + "']").removeClass("btn-primary");
			$(this).addClass("btn-primary");
		});
	}
	
	function initQueryEvent(){
		$("#go").click(function() {
			currentPage = 1;
			goByRange(currentPage, false);
		});
	}
	
	function initGenerationEvent(){
		$("#generate").click(function() {
			switch (gRange) {
			case "Month":
				var endMonth = $('#gMonthEnd').val();
				var beginMonth = $('#gMonthBegin').val();
				if (beginMonth == "" || endMonth == "") {
					alert("Please select month!");
				} else if (reportType == "") {
					alert("Please select report type!");
				} else {
					generate(beginMonth, endMonth, reportType);
				}

				break;
			case "Date":
				var endDate = $('#gDateEnd').val();
				var beginDate = $('#gDateBegin').val();
				if (endDate == "" || beginDate == "") {
					alert("Please select date!");
				} else if (reportType == "") {
					alert("Please select report type!");
				} else {
					generate(beginDate, endDate, reportType);
				}
				break;
			default:
				break;
			}
		});
	}
	

	function goByRange(currentPage, queried) {
		switch (range) {
		case "Year":
			date = $('#logYearPicker').val();
			if (date !== "") {
				year = date;
			} else {
				alert("Please select year!");
				return;
			}
			break;
		case "Month":
			date = $('#logMonthPicker').val();
			if (date !== "") {
				year = date.split("/")[1];
			} else {
				alert("Please select month!");
				return;
			}

			break;
		case "Date":
			date = $('#logDatePicker').val();
			if (date !== "") {
				year = date.split("/")[2];
			} else {
				alert("Please select date!");
				return;
			}
			break;
		default:
			break;
		}

		if (null != date) {
			go(date, currentPage, queried);
		}

	}

	function go(date, currentPage, queried) {
		var src = "getITDMsg.do?timestamp=" + new Date().getTime() + "&date="
				+ date + "&range=" + range + "&currentPage=" + currentPage
				+ "&queried=" + queried;
		$.ajax({
					url : src,
					dataType : "json",
					success : function(result) {
						data = [];
						if (result.error) {
							consule.log("Error: " + result.error);

						} else if (result.data.page != null) {
							$("#databody tr").remove();
							var page = result.data.page;
							var json = page.records;
							if (!queried) {
								pageNum = page.pageNum;
								totalRecord = page.totalRecord;
								console.log("pageNum"+pageNum+"totalRecord"+totalRecord);
								initPaginationView($("li[name='paginBtn']"));
							}
							if (year > 2016) {
								$("#changeField").text("RootCause Category");
								for ( var i = 0; i < json.length; i++) {

									$("#databody")
											.append(
													"<tr><td><a href='itdDetail.do?ISM_Nbr="
															+ json[i].ISM_Nbr
															+ "&incident_Date="
															+ json[i].incident_Date
															+ "' target='_blank'>Detail</a></td><td>"
															+ json[i].incident_Date
															+ "</td><td>"
															+ json[i].ISM_Nbr
															+ "</td><td>"
															+ json[i].division
															+ "</td><td>"
															+ json[i].root_Cause
															+ "</td><td>"
															+ json[i].rootCause_Category
															+ "</td><td>"
															+ json[i].reportable
															+ "</td></tr>");
								}
							} else {
								$("#changeField").text("ResiliencyTheme");
								for ( var i = 0; i < json.length; i++) {
									$("#databody")
											.append(
													"<tr><td><a href='itdDetail.do?ISM_Nbr="
															+ json[i].ISM_Nbr
															+ "&incident_Date="
															+ json[i].incident_Date
															+ "' target='_blank'>Detail</a></td><td>"
															+ json[i].incident_Date
															+ "</td><td>"
															+ json[i].ISM_Nbr
															+ "</td><td>"
															+ json[i].division
															+ "</td><td>"
															+ json[i].root_Cause
															+ "</td><td>"
															+ json[i].res_Theme_Initial
															+ "</td><td>"
															+ json[i].reportable
															+ "</td></tr>");
								}
							}
							setPageInfo($("#pageInfo"));
							
						} else {
							console.log("Error: no page");
						}
					},
					error : function(request, textStatus, errorThrown) {
						console.log("Error: " + textStatus);
					}
				});
	}

	function generate(startDate, endDate) {
		var src = "generateITDMsg.do?timestamp=" + new Date().getTime()
				+ "&startDate=" + startDate + "&endDate=" + endDate + "&reportType="
				+ reportType + "&range=" + gRange;
		$("#hiddenA").attr("href",src);
		$("#autoClick").click();
		/*$.ajax({
			url : src,
			dataType : "json",
			success : function(result) {
				data = [];
				if (result.error) {
					consule.log("Error: " + result.error);

				} else if (result.data.generateResult == "success") {
					
					} else if(result.data.generateResult == "failed"){
						alert("Fail toGenerate Excel");
				} else {
					console.log("Error: no result");
				}
			},
			error : function(request, textStatus, errorThrown) {
				console.log("Error: " + textStatus);
			}
		});*/
		
	}