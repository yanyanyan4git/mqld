	$(function(){
		$("#mid tr td:even").css("text-align","right") ;
		$("#mid tr td:odd").css("width","500px") ;
		$("#mid tr td:even").css("width","250px") ;
		$("#mid tr td:odd").css("text-align","left") ; 
		$("#mid tr td:odd").css("padding-top","3px") ;
		$("td").css("border-top","0px") ;
		$("#head thead tr th").css("text-align","center") ;
		var flag=false;
		if($("#added").val()=="true")
			flag=true;
		 if(flag){
			 $('#add').attr('disabled',true);
			 $('#update').attr('disabled',false);
			 $('#del').attr('disabled',false);
		 }else{
			 $('#add').attr('disabled',false);
			 $('#update').attr('disabled',true);
			 $('#del').attr('disabled',true);
		 }
		 $("[name='desc_Incident']").val($("#desc_Incident_h").val());
		 $("[name='remimpact']").val($("#remimpact_h").val());
		  $("[name='rootCauseDescription']").val($("#rootCauseDescription_h").val()); 
		 $("[name='source']").val($("#source_h").val());
		
		 initDatePeriod($("#rcaDate"),$("#compeletionDate"));
		 initDatePeriod($("#startDate"),$("#endDate"));
		 initDatePicker($("[name='actionTargetDate']"));
		 initDatePicker($("[name='actionDateComplete']"));
		 /* initTimePicker($("[name='startTime']"));
		 initTimePicker($("[name='endTime']")); */
		
		 $.each($("#hiddenField input"), function(index) { 
			var name=$(this).attr("name");
			var val=$(this).val();
			 setData2BtnGroup(name,val);
			 setData2DropDown(name,val);
			 }); 
		 	initComponentEvent();
		 	initActionsEvent();
		 	initSubmitEvent();
	 });
	
	function initComponentEvent(){
		 $("div.btn-group button").click(function(){
				console.log("clicktoggleBtn");
				var val=toggleBtn($(this));
				var name=$(this).attr("name");
				var input=$("#hiddenField input[name='"+name+"']");
				setData(input,val);
			});
			
			$("span.btn-group ul li").click(function() {
				console.log("clickdropdown");
				var val=toggleDropDown($(this));
				var name=$(this).attr("name");
				var input=$("#hiddenField input[name='"+name+"']");
				setData(input,val);
			});
			
			$("#calculate").click(function() {
				var duration=dateTimeMinus($('#startDate').val(),$('#startTime').val(),$('#endDate').val(),$('#endTime').val());
				if(duration=="")
					return;
				$("[name='duration']").val(duration);
			});
	}
	
	function initActionsEvent(){
		$("#addrow").click(function() {
			
			$('#actionTb').append("<tr name='morerow'><td></td><td><input  type='text' name='actionItem'></td><td><input  type='text'  name='actionClass'></td><td><input  type='text'   name='actionTargetDate'  readonly=''></td><td><input  type='text' name='actionDateComplete' readonly=''></td><td><input  type='text' name='actionOwner'></td><td><button name='delrow' type='button' class='btn btn-default' title='Delete This Action'>Delete</button></td></tr>");
			 $("tr[name='morerow'] td").css("border-top","0px") ; 
			 initDatePicker($("[name='actionTargetDate']"));
			 initDatePicker($("[name='actionDateComplete']"));
			 refreshIndex();
		});
		
		$(document).on("click","button[name='delrow']",function() {
			$(this).parent().parent().remove();	
			refreshIndex();
		});
	}
	
	function initSubmitEvent(){
		$("#add").click(function() {
			var add=confirm("Sure to Add Incident Detail?");
			if(add){
				go("add");
			}
		});
		
		$("#update").click(function() {
			var update=confirm("Sure to Update Incident Detail?");
			if(update){
				go("update");
			}
		});
		
		$("#del").click(function() {
			var del=confirm("Sure to Delete Incident Detail?");
			if(del){
				deleteDetail();
			}
		});
	}
	
	function refreshIndex(){
		$.each($('#actionTb tr'), function(index) { 
			$(this).children('td:first-child').text(index+1);
		}); 
	}
	
	
	function go(status) {
		$("#myModal").modal('show');
		var detailData=$( "#detail_form").serialize();
		var src = status+"Detail.do?timestamp=" + new Date().getTime()+"&"+detailData+"&actionNum="+$('#actionTb tr').length;
		 $.ajax({
			url: src,
			dataType:"json",
			success:function(result){
				$("#myModal").modal('hide');
				data=[];
				if(result.error){
					consule.log("Error: "+result.error);
				}else if(result.data.result==true){
					alert(status+" success!");
					window.close();
				}else if(result.data.result==false){
					alert(status+" failed!");
				}else{
					console.log("no result");
				}			
			},
			error:function(request, textStatus, errorThrown){
				$("#myModal").modal('hide');
				console.log("Error: " + textStatus);
			}
		}); 
	}
	
	function deleteDetail() {
		var src = status+"delDetail.do?timestamp=" + new Date().getTime()+"&ISM_Nbr="+$("[name='ISM_Nbr']").val()+"&incident_Date="+$("[name='incident_Date']").val();
		$("#myModal").modal('show');
		 $.ajax({
			url: src,
			dataType:"json",
			success:function(result){
				data=[];
				$("#myModal").modal('hide');
				if(result.error){
					console.log("Error: "+result.error);
				}else if(result.data.delResult==true){
					alert("delete success!");
					window.close();
				}else if(result.data.delResult==false){
					alert("delete failed!");
				}else{
					console.log("no result");
				}			
			},
			error:function(request, textStatus, errorThrown){
				$("#myModal").modal('hide');
				console.log("Error: " + textStatus);
			}
		}); 
	}