<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/layout/inc/taglib.jsp" %>  

<script src="${contextPath }/resources/js/jquery-barcode-2.0.2.min.js"></script>

<section class="content-header">
	<h1>
		반품내역 목록	
		<small>반품내역 목록</small>
	</h1>
	<ol class="breadcrumb">
		<li><a href="#"><i class="fa fa-dashboard"></i> Level</a></li>
		<li class="active">Here</li>
	</ol>
</section>

<c:set var="strActionUrl" value="${contextPath }/adm/returnOrderMgr" />
<c:set var="strMethod" value="get" />

<section class="content">
	<!-- 검색 박스 -->
	<div class="box box-primary">
		<div class="box-header with-border">
			<h3 class="box-title">주문반품내역검색 조건</h3>

			<div class="box-tools pull-right">
				<button type="button" class="btn btn-box-tool"
					data-widget="collapse">
					<i class="fa fa-minus"></i>
				</button>
				<button type="button" class="btn btn-box-tool" data-widget="remove">
					<i class="fa fa-remove"></i>
				</button>
			</div>
		</div>
		<!-- /.box-header -->
		<div class="box-body">
			
			<spform:form name="orderFrm" id="orderFrm" method="${strMethod }" action="${strActionUrl }" class="form-horizontal">
				<%-- <input type="hidden" id="DATE_ORDER" name="DATE_ORDER" value="${DATE_ORDER }" /><!-- 주문일 정렬 -->
				<input type="hidden" id="MEMB_NM_ORDER" name="MEMB_NM_ORDER" value="${MEMB_NM_ORDER }" /><!-- 주문자명 정렬 -->
				<input type="hidden" id="COM_NAME_ORDER" name="COM_NAME_ORDER" value="${COM_NAME_ORDER }" /><!-- 사업사상호 정렬 -->
				 --%>
				<input type="hidden" id="pagerMaxPageItems" name="pagerMaxPageItems" value="${obj.pagerMaxPageItems }" /><!-- 목록수 -->
				
				<div class="box-body">
					<div class="form-group">
						<label class="col-sm-1 control-label">주문번호</label>
						<div class="col-sm-5">
							<input name="schNum" id="schNum" type="text" class="form-control" placeholder="검색어 입력" <c:if test = '${obj.schNum !=null && obj.schNum != "null" }'>value="${obj.schNum }"</c:if>>
						</div>
					</div>
					<div class="form-group">
						<label for="inputEmail3" class="col-sm-1 control-label">주문일자</label>
						
						<div class="col-sm-5">
							<div class="form-group">
								<div class="col-sm-5">
									<input type="text" class="form-control pull-left" readonly="readonly"  name="datepickerStr" id="datepickerStr" value="${obj.datepickerStr }" >
								</div>
								<div class="col-sm-1" style="height: 34px;padding-top: 8px ">
									~
								</div>
								<div class="col-sm-6">
									<input type="text" class="form-control pull-right" readonly="readonly" name="datepickerEnd" id="datepickerEnd" value="${obj.datepickerEnd }">
								</div>
							</div>
						</div>
						<label for="inputEmail3" class="col-sm-1 control-label">검색조건</label>
						<div class="col-sm-2">
							<select name="schGbn" id="schGbn" class="form-control select2" style="width: 100%;">
								<option value="MEMB_NM" ${obj.schGbn == 'MEMB_NM' ? 'selected=selected':''}>주문자명</option>
								<%-- <option value="RECV_PERS" ${obj.schGbn == 'RECV_PERS' ? 'selected=selected':''}>수령자명</option> --%>
								<option value="COM_NAME" ${ obj.schGbn eq 'COM_NAME' ? "selected='selected'":''}>사업자상호</option>
							</select>
						</div>
						<div class="col-sm-2">
							<input name="schTxt" id="schTxt" type="text" class="form-control" placeholder="검색어 입력" <c:if test = '${obj.schTxt !=null && obj.schTxt != "null" }'>value="${obj.schTxt }"</c:if>>
						</div>
						<div class="col-sm-1">
							<button type="submit" class="btn btn-success pull-right">검색</button>
						</div>
					</div>
				</div>
			<!-- /.box-body -->
			</spform:form>
			
		</div>
		<!-- /.box-body -->
	</div>
	<!-- /.box -->
	<div class="box-header with-border">
		<h3 class="box-title"></h3>
		<div class="box-tools">
			<!-- <a href="" class="btn btn-sm btn-primary pull-right" >엑셀</a> -->
			<!-- <button type="button" class="btn btn-primary btn-sm pull-right" id="btnExcel">엑셀</button> -->
		</div>
	</div>
	<!-- 검색 박스 -->
	<div class="box box-primary">
		<!-- /.box-header -->
		<div class="box-body">
			<form class="form-horizontal">
				<div class="box-body">
					<div class="form-group">
						<!-- <label for="inputEmail3" class="col-sm-1 control-label">정렬</label>
						<div class="col-sm-6">
							<button type="button" class="btn btn-primary btn-sm" onclick="javascript:fn_order_by('DATE_ORDER');">주문일▼▲</button>
							<button type="button" class="btn btn-primary btn-sm" onclick="javascript:fn_order_by('MEMB_NM_ORDER');">주문자명▼▲</button>
							<button type="button" class="btn btn-primary btn-sm" onclick="javascript:fn_order_by('COM_NAME_ORDER');">사업사상호▼▲</button>
							<button type="button" class="btn btn-primary btn-sm" onclick="javascript:fn_order_by('PAY_DATE_ORDER');">결제시간▼▲</button>
						</div> -->
						<div class="col-sm-8"></div>
						<div class="col-sm-4">
							<label for="inputEmail3" class="col-sm-6 control-label">목록수</label>
							<div class="col-sm-3">
				                <select name="cnt" id="cnt" class="form-control select2" style="width: 100%;" onchange="javascript:fn_order_by('');">
									<option value="10" ${obj.pagerMaxPageItems == '10' ? 'selected=selected':''}>10</option>
									<option value="20" ${obj.pagerMaxPageItems == '20' ? 'selected=selected':''}>20</option>
									<option value="30" ${obj.pagerMaxPageItems == '30' ? 'selected=selected':''}>30</option>
									<option value="40" ${obj.pagerMaxPageItems == '40' ? 'selected=selected':''}>40</option>
									<option value="50" ${obj.pagerMaxPageItems == '50' ? 'selected=selected':''}>50</option>
									<option value="100" ${obj.pagerMaxPageItems == '100' ? 'selected=selected':''}>100</option>
									<option value="200" ${obj.pagerMaxPageItems == '200' ? 'selected=selected':''}>200</option>
								</select>
							</div>
						</div>
					</div>
				</div>
				<!-- /.row -->
		</div>
		<!-- /.box-body -->
	</div>
	<!-- /.box -->
	<!-- 검색 박스 -->
	<div class="box">
		<!-- /.box-header -->
		<div class="box-body" style="white-space:nowrap;overflow-x:scroll;">
			<table class="table table-bordered">
				<colgroup>
					<col style="with:10px" />
					<col />
					<col style="with:50px" />
				</colgroup>
				<thead>
					<tr>
						<th><input type="checkbox" id="CHK_ALL" name="CHK_ALL" onclick="javascript:fn_all_chk();" /></th>
						<th>번호</th>
						<!-- <th>주문일시</th>
						<th>주문시간</th>
						<th>주문번호</th>
						<th>주문자명<br/>(수령자명)</th>
						<th>주문상품</th>
						<th>결제완료시간</th>
						<th>총 주문금액</th>
						<th>주문상태<br/>(결제수단)</th>
						<th>기능</th> -->
						
						<!-- <th style="text-align: center;vertical-align : middle;">주문일자<br>결제시간</th> -->
						<th style="text-align: center;vertical-align : middle;">반품번호</th>
						<th style="text-align: center;vertical-align : middle;">주문번호</th>
						<th style="text-align: center;vertical-align : middle;">주문자명<br/>(사업사상호)</th>
						<th style="text-align: center;vertical-align : middle;">주문상품</th>
						<th style="text-align: center;vertical-align : middle;">총결제금액</th>
						<th style="text-align: center;vertical-align : middle;">배송비</th>
					</tr>
				</thead>
				<tbody>
				<c:if test="${obj.count == 0}">
					<tr>
						<td colspan="13">조회된 주문내역이 없습니다.</td>
					</tr>
				</c:if>
				<c:forEach items="${obj.list }" var="list" varStatus="loop">
					<c:if test="${list.ORDER_CON ne 'ORDER_CON_04' && list.ORDER_CON ne 'ORDER_CON_08' }">
					</c:if>
					<tr>
						<td style="text-align: center;vertical-align : middle;">
							<input type="checkbox" id="CHK${loop.count }" name="chkOrdList" value="${list.ORDER_NUM }"/>
						</td>
						<td>${list.RNUM }</td>
						<td style="text-align: center;vertical-align : middle;">
							<a href="${contextPath}/adm/returnOrderMgr/form/${list.RETURN_NUM }">${list.RETURN_NUM }</a>
						</td>
						<td style="text-align: center;vertical-align : middle;">
							${list.ORDER_NUM }
						</td>
						<td style="text-align: center;vertical-align : middle;">
							${list.MEMB_NM }<br/>
							<c:if test='${list.COM_NAME==null || list.COM_NAME=="" }'>( ${list.MEMB_NM })</c:if>
							<c:if test='${list.COM_NAME!=null && list.COM_NAME!="" }'>( ${list.COM_NAME } )</c:if>
						</td>
						<td style="text-align: center;vertical-align : middle;">
							${list.PD_NAME }
							<c:if test="${list.count > 0 }">
								&nbsp; 외 ${list.count}&nbsp;종
							</c:if>
						</td>
						<td style="text-align: center;vertical-align : middle;">
							<fmt:formatNumber value="${list.RETURN_AMT }" />
						</td>
						<td style="text-align: center;vertical-align : middle;">
							<fmt:formatNumber value="${list.DLVY_AMT ==''? 0 : list.DLVY_AMT}" />
						</td>
						<%-- <td style="text-align: center;vertical-align : middle;">
							<fmt:formatNumber value="${list.RETURN_AMT - list.DLVY_AMT }" />
						</td> --%>						
					</tr>
				</c:forEach>
				</tbody>
			</table>
		</div>
		<paging:PageFooter totalCount="${ totalCnt }" rowCount="${ rowCnt }">
			<First><Previous><AllPageLink><Next><Last>
		</paging:PageFooter>
	</div>
	
</section>

<script>
$(function() {

	//Date picker
	$('#datepickerStr').datepicker({
		autoclose: true,
		format: 'yyyy-mm-dd'
	});
	$('#datepickerEnd').datepicker({
		autoclose: true,
		format: 'yyyy-mm-dd'
	});
	
	
	$('#myModal').on('show.bs.modal', function (event) {

		var modal = $(this);
        $(".btnSave").text("저장");
	});
		
	$(".viewPopup").click(function(){
		var strGubun = $(this).attr("gubun");
		$('#pickingGbun').val(strGubun);

		var checkboxValues = [];

		//each로 loop를 돌면서 checkbox의 check된 값을 가져와 담아준다.
		$("input:checkbox[name=chkOrdList]:checked").each(function(){
			checkboxValues.push($(this).val());
		});
		if(checkboxValues==''||checkboxValues==null){
			checkboxValues.push("");
			
			alert("피킹리스트를 작성 할 주문을 선택하세요.");
			return false;
		}
	    var allData = { "checkArray": checkboxValues, "ORDER_GUBUN" : strGubun };
		var addTotalRow = "";
		
		$.ajax({
		    type: 'GET',
		    dataType: 'json',
		    data: allData,
		    url: '${contextPath }/adm/orderMgr/picking.json',
		    success: function (data) {
		    	var objList = data.objList;
		    	//alert(JSON.stringify(data));
		    	if(objList.length>0){
		    		for(i=0;i<objList.length;i++){
			    		var addRow = "";
			    		addRow = '<tr>'
			    				+	'<td>'+(i+1)+'</td>'
			    				if(objList[i].atfl_ID != null){
			    					addRow = addRow +	'<td>'+//objList[i].atfl_ID+'</td>'
			    					'<img width="50" height="50" src="${contextPath }/common/commonFileDown?ATFL_ID='+objList[i].atfl_ID+'&ATFL_SEQ='+objList[i].rpimg_SEQ+'" />'
			    					+'</td>';
			    				}else{
			    					addRow = addRow +	'<td>'+//objList[i].atfl_ID+'</td>'
			    					'<img width="50" height="50" src="${contextPath }/resources/images/mall/goods/noimage.png"/>'
			    					+'</td>';
			    				}
			    				//+	'<td>'+objList[i].atfl_ID+'</td>'
			    				addRow = addRow +	'<td class="tdCar">'+((objList[i].car_NUM_NM==null) ? "미등록" : objList[i].car_NUM_NM)+'</td>';
			    				
			    		if(objList[i].com_NAME==null){
			    			addRow = addRow +	'<td class="tdCom">'+objList[i].memb_NM+'</td>';
			    		}else{
			    			addRow = addRow +	'<td class="tdCom">'+objList[i].com_NAME+"("+objList[i].memb_NM+")"+'</td>';
			    		}
			    		
			    		addRow = addRow +	'<td>'+objList[i].pd_NAME+'</td>'
			    				+	'<td>'+((objList[i].pd_STD==null) ? "" : objList[i].pd_STD)+'</td>'
			    				+	'<td>'+((objList[i].input_CNT==null) ? "" : objList[i].input_CNT)+'</td>'
			    				+	'<td>'+((objList[i].order_QTY==null) ? "" : objList[i].order_QTY)+'</td>'
			    				+	'<td>'+((objList[i].pd_BARCD==null) ? "" : objList[i].pd_BARCD)+'</td>'
			    				+	'<td>'+'<p id="barcodeTarget'+i+'" class="barcodeTarget"></p></td>'
			    				//+	'<input type="text" name="test" value="'+((objList[i].pd_CODE==null) ? "" : objList[i].pd_CODE)+'"</>'
			    				+'</tr>'
			    		addTotalRow += addRow;
		    			
			    	}
		    	}else{
		    		var addRow = "";
		    		
		    		addRow = '<tr>'
		    				+	'<td colspan="6" style="text-align:center">조회된 데이터가 없습니다.</td>'
		    				+'</tr>';
		    		addTotalRow += addRow;
		    	}
		    	
		    	//$("#barcodeTarget").barcode(objList[0].pd_BARCD, "ean13");
		    	//$("#barcodeTarget").barcode((objList[1].pd_BARCD==null)?"11111":objList[1].pd_BARCD, "ean13");
		    	
		    	$("#tBody").html(addTotalRow);
		    	
		    	for(j=0;j<objList.length;j++){
		    		if(objList[j].pd_BARCD!=null){
			    		$("#barcodeTarget"+j).barcode(objList[j].pd_BARCD, "ean13");
			    	}else{
			    		$("#barcodeTarget"+j).text("데이터없음")
			    	}
		    	}
		    	
		    	$("#today").text("주문일자 : "+data.today);
		    	
		    	if(strGubun == "goods"){
		    		$(".tdCar").hide();
		    		$(".tdCom").hide();
		    		
		    	}else if(strGubun == "com"){
		    		$(".tdCar").hide();
		    		$(".tdCom").show();
		    		
		    	}else if(strGubun == "car"){
		    		$(".tdCar").show();
		    		$(".tdCom").show();
		    		
		    	}
		   
		    },
		    error:function(request,status,error){
		         console.log('error : '+error+"\n"+"code : "+request.status+"\n");
		    }
		});
		
		$('#myModal').modal('show');
		return false;
	 }); 
	
	
	// 엑셀
    $("#btnExcel").click(function() {
    	
    	var checkboxValues = [];

		//each로 loop를 돌면서 checkbox의 check된 값을 가져와 담아준다.
		$("input:checkbox[name=chkOrdList]:checked").each(function(){
			checkboxValues.push($(this).val());
		});
		if(checkboxValues==''||checkboxValues==null){
			checkboxValues.push("");
			
			alert("엑셀을 출력할 주문내역을 선택하세요.");
			return false;
		} 
		$("#CHK_ORD_LIST").val(checkboxValues);
    	var strAction = "${contextPath }/adm/orderMgr/excelDown";
    	var realAction =  "${strActionUrl }";
    	
        $("#orderFrm").attr("action", strAction);
        $("#orderFrm").submit();
        
        //원래대로
        $("#orderFrm").attr("action",realAction);
    });
	
	
	
});

//정렬 주문일 & 주문자명 클릭시 , 목록수 변경시
function fn_order_by(str){
	
	var frm=document.getElementById("orderFrm");
	if(str != ""){
		frm.ORDER_GUBUN.value=str;
	}
	
	frm.pagerMaxPageItems.value=$("#cnt").val();
	console.log(frm.pagerMaxPageItems.value);
	frm.submit();
}

//전체 체크 및 해제 시
function fn_all_chk(){
	var check_yn = false;		
	if($("#CHK_ALL").is(":checked")){
		check_yn = true;
	}
	for(var i=1;i<= ${fn:length(obj.list) };i++){
		$("#CHK"+i).prop("checked",check_yn);
	}
}

//일괄 주문상태 변경(배송준비중 변경 버튼 클릭시)
function fn_state(str){
	
	var cnt = 0;
	var order_num_list = "";
	for(var i=1;i<= ${fn:length(obj.list) };i++){
		if($("#CHK"+i).is(":checked")){
			cnt++;
			if(order_num_list != "") {
				order_num_list+="$";
			}
			order_num_list+=$("#CHK"+i).val();
		}
	}
	if(cnt == 0){
		alert("일괄 변경 할 주문을 선택하세요.");
		return;
	}
	
	if(!confirm("일괄 변경 하시겠습니까?"))return;

	
	
 	var frm=document.getElementById("orderFrm");
		
	frm.ORDER_CON_STATE.value=str;
	frm.ORDER_CON_ORDER_NUM.value=order_num_list;
	frm.submit();
}

function pickingListPrint(){

	//결제완료 > 배송 준비중으로  update
	
	//var checkboxValues = new Array();
	var checkboxValues = [];

	//each로 loop를 돌면서 checkbox의 check된 값을 가져와 담아준다.
	$("input:checkbox[name=chkOrdList]:checked").each(function(){
		checkboxValues.push($(this).val());
	});
	if(checkboxValues==''||checkboxValues==null){
		checkboxValues.push("");
	}
    var allData = { "checkArray": checkboxValues };
	var addTotalRow = "";
	
	
	$.ajax({
	    type: 'GET',
	    dataType: 'json',
	    data: allData,
	    url: '${contextPath }/adm/orderMgr/pickingUpdate',
	    success: function (data) {   	
	    	
	   
	    },
	    error:function(request,status,error){
	         console.log('error : '+error+"\n"+"code : "+request.status+"\n");
	    }
	});
	
	
	var initBody = document.body.innnerHTML;
	
	
	/* var mediaQueryList = window.matchMedia('print');
    mediaQueryList.addListener(function(mql) {
        if (mql.matches) {
        	document.body.innerHTML = document.getElementById("printPickingList").innerHTML;
             //console.log('이 Function은 프린트 이전에 호출됩니다.');
        } else {
        	document.body.innerHTML = initBody;
             //console.log('이 Function은 프린트 이후에 호출됩니다.');
        }
    }); */
	
	
	
	window.onbeforeprint = function(){
		document.body.innerHTML = document.getElementById("printPickingList").innerHTML;
		
	}
	window.onafterprint = function(){
		//document.body.innerHTML = initBody;
		
		window.location.reload(true);
		
		
	} 
	
	window.print();
	
}

function delivery_slip(){
	
	var checkboxValues ="";
	
	//체크값들 넣기
	$("input:checkbox[name=chkOrdList]:checked").each(function(){
		//checkboxValues.push($(this).val());
		checkboxValues += $(this).val()+":";
	});
	if(checkboxValues==''||checkboxValues==null){
		alert("주문을 하나이상 선택해 주세요.")
		return;
	}
	window.open('http://121.183.206.41/cjflsRptWeb/Clickonce/cjflsRpt.application?'
			+'qr='+checkboxValues+'');
}
function pickingListExcel(){
	
	//엑셀정보가져오기
	var strGubun = $('#pickingGbun').val();

	var checkboxValues = [];

	//each로 loop를 돌면서 checkbox의 check된 값을 가져와 담아준다.
	$("input:checkbox[name=chkOrdList]:checked").each(function(){
		checkboxValues.push($(this).val());
	});
	if(checkboxValues==''||checkboxValues==null){
		checkboxValues.push("");
		
		alert("출력할 정보가 없습니다.");
		return false;
	}
    var allData = { "checkArray": checkboxValues, "ORDER_GUBUN" : strGubun };
    var form = "<form action='${contextPath }/adm/orderMgr/pickingExcelDown' method='get'>"; 
    form += "<input type='hidden' name='checkArray' value='"+checkboxValues+"' />"; 
    form += "<input type='hidden' name='ORDER_GUBUN' value='"+strGubun+"' />"; 
    form += "</form>"; 
    jQuery(form).appendTo("body").submit().remove(); 
	
}
//주문내역 삭제
function deleteOrd(){
	
	var checkboxValues = [];

	//each로 loop를 돌면서 checkbox의 check된 값을 가져와 담아준다.
	$("input:checkbox[name=chkOrdList]:checked").each(function(){
		checkboxValues.push($(this).val());
	});
	if(checkboxValues==''||checkboxValues==null){
		checkboxValues.push("");
		
		alert("삭제할 주문을 선택하세요.");
		return false;
	}
	
	var frm=document.getElementById("deleteFrm");
	//frm.method="post";
	//frm.action="${contextPath }/adm/orderMgr/deleteOrdList";
	frm.ORD_DELETE_LIST.value=checkboxValues;
	if(confirm("결제전,주문취소 상태의 주문내역만 삭제됩니다.\n 삭제하시겠습니까?")){
		frm.submit();	
	}
	
}
</script>