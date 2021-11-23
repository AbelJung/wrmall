<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/layout/inc/taglib.jsp" %>

<c:set var="strActionUrl" value="${contextPath }/adm/returnOrderMgr/" />
<c:set var="strMethod" value="post" />

<section class="content-header">
	<h1>
		반품내역	
		<small>반품내역상세</small>
	</h1>
	<ol class="breadcrumb">
		<li><a href="#"><i class="fa fa-dashboard"></i> Level</a></li>
		<li class="active">Here</li>
	</ol>
</section>

<section class="content">
	<!-- Horizontal Form -->
		<!-- 주문 정보 시작 -->
		<div class="box box-info">
			<div class="box-header with-border">
				<h3 class="box-title">주문정보</h3>
			</div>
			<!-- /.box-header -->
		
				<!-- box-body -->
				<div class="box-body">
					<div class="form-group">
						<label for="inputEmail3" class="col-sm-2 control-label">반품번호</label>
						<div class="col-sm-4">${tb_rtodinfoxm.RETURN_NUM }</div>
						<label for="inputEmail3" class="col-sm-2 control-label">주문자</label>
						<div class="col-sm-4">${tb_rtodinfoxm.MEMB_NM }</div>
						<!-- <button type="button" class="btn btn-primary btn-sm pull-right" id="btnExcel" style="margin-left:5px;">엑셀</button> -->
					</div><br/>
					<div class="form-group">
						<label for="inputEmail3" class="col-sm-2 control-label">반품일자</label>
						<div class="col-sm-4">${tb_rtodinfoxm.RETURN_DATE }</div>
						<label for="inputEmail3" class="col-sm-2 control-label">주문번호</label>
						<div class="col-sm-4">${tb_rtodinfoxm.ORDER_NUM }</div> 
					</div><br/>
					<div class="form-group">
						<label for="inputEmail3" class="col-sm-2 control-label">주문결제수단</label>
						<div class="col-sm-4">${tb_rtodinfoxm.PAY_METD_NM }</div>
					</div>
				</div>
				<!-- /.box-body -->
			</div>
			<!-- 주문 정보 끝 -->
			
		<!-- 주문상품 정보 시작 -->
		<spform:form name="orderFrm" id="orderFrm" method="${strMethod }" action="${strActionUrl }" class="form-horizontal">
			<input type="hidden" id="RETURN_NUM" name="RETURN_NUM" value="${tb_rtodinfoxm.RETURN_NUM }" /><!-- 주문번호 -->			
			<div class="box box-info">
				<div class="box-header with-border">
					<h3 class="box-title">주문 정보</h3>
				</div>
					<!-- /.box-header -->
					<div class="box-body">
						<c:set var="totalAmt" value="0"/>
						<table class="table table-bordered">
							<colgroup>
								<col style="with:10px" />
								<col />
								<col style="with:50px" />
							</colgroup>
							<thead>
								<tr>
									<th>번호</th>
									<th>상품명</th>
									<th>판매단가</th>
									<th>할인금액</th>
									<th>판매금액</th>
									<th>주문수량</th>
									<th style="width:8%">반품수량</th>
									<th>반품단가</th>
									<th>반품금액</th>
									<th style="width:32%">반품사유</th>
								</tr>
							</thead>
							<c:set var="totalAmt" value="0"/> 
							<c:forEach items="${tb_rtodinfoxm.list }" var="list" varStatus="loop">
								<c:set var="totalAmt" value="${totalAmt + list.REAL_PRICE*list.RETURN_QTY }"/>
								<input type="hidden" name="RETURN_DTNUMS" value="${list.RETURN_DTNUM }"/>
								<input type="hidden" name="PD_PRICES" value="${list.PD_PRICE }"/>
								<input type="hidden" name="PDDC_GUBNS" value="${list.PDDC_GUBN }"/>
								<input type="hidden" name="PDDC_VALS" value="${list.PDDC_VAL }"/>
								
								
								<%-- <input type="text" name="RETURN_AMTS" value="${list.RETURN_AMT }"/> --%>
								<tr>
									<td>${loop.count }</td>
									<td>${list.PD_NAME }</td>
									<td style="text-align:right"><fmt:formatNumber>${list.ORDER_PRICE }</fmt:formatNumber></td>
									<td style="text-align:right"><fmt:formatNumber>${list.ORDER_VAL }</fmt:formatNumber></td>
									<td style="text-align:right"><fmt:formatNumber>${list.ORDER_REAL_PRICE }</fmt:formatNumber></td>
									<td><input type="hidden" id="ORDER_QTY_${list.RETURN_DTNUM }" value="${list.ORDER_QTY }"/>${list.ORDER_QTY }</td>
									<td><input class="form-control number" type="text" name="RETURN_QTYS" value="${list.RETURN_QTY }" 
											id="RETURN_QTY_${list.RETURN_DTNUM }" onblur="javascript:fn_qtychk('${list.RETURN_DTNUM}');"/></td>
									<td style="text-align:right"><fmt:formatNumber>${list.PD_PRICE }</fmt:formatNumber></td>
									<td style="text-align:right"><fmt:formatNumber>${list.REAL_PRICE }</fmt:formatNumber></td>
									<td><input class="form-control" type="text" name="RETURN_GBNS" value="${list.RETURN_GBN }"/></td>
									<%-- <td><fmt:formatNumber>${list.PDDC_VAL }</fmt:formatNumber></td>
									<td class="orderAmt"><fmt:formatNumber>${list.RETURN_AMT }</fmt:formatNumber></td>
									<c:set var= "totalAmt" value="${totalAmt + list.RETURN_AMT}"/> --%>
								</tr>
							</c:forEach>
							<tr>
								<th colspan="9" style="text-align:right;">상품 합계</th>
								<td id="totalPrdAmt" colspan="4" style="text-align:right"> <fmt:formatNumber><c:out value="${totalAmt}"/></fmt:formatNumber> </td>
							</tr>
							<tr>
								<th colspan="9" style="text-align:right;">배송비 </th>
								<td id="dlvyAmt" colspan="4" style="text-align:right"> 
									<input type="text" class="form-control number" name="DLVY_AMT" value="${tb_rtodinfoxm.DLVY_AMT }"/>
								</td>
							</tr>
							</tbody>
						</table>
					</div>
				</div>
			<!-- 주문상품 정보 끝 -->			
			</spform:form>
			
			<div class="box-footer">
				<a href="${contextPath}/adm/returnOrderMgr" class="btn btn-default pull-right" style="margin-left: 5px">리스트</a>
				<!-- <button type="submit" class="btn btn-info pull-right">저장</button> -->
				<a onclick="javascript:fn_save();" class="btn btn-info pull-right" style="margin-left: 5px">저장</a>
			</div>
			
			
			<!-- /.box-footer -->
	
	<!-- /.box -->
</section>

<script type="text/javascript">
$(document).ready(function() {

	$("#ORDER_CON").change(function(e) {
		fn_disabled();
	});
	
	//Date picker
	/*
	$('#datepicker_dlar').datepicker({
		autoclose: true,
		format: 'yyyy-mm-dd'
	}); 
	$('#datepicker').datepicker({
		autoclose: true,
		format: 'yyyy-mm-dd'
	}); 
	$('#timepicker').timepicker({
		timeFormat : "H:i:s"
	});
	*/
	
});

//엑셀
$("#btnExcel").click(function() {
	
	var form = document.createElement("form");

	form.target = "_blank";

	form.method = "get";
	form.action = "${contextPath }/adm/orderMgr/detailExcelDown";

	document.body.appendChild(form);

	var input_id = document.createElement("input");
	input_id.setAttribute("type", "hidden");
	input_id.setAttribute("name", "ORDER_NUM");
	input_id.setAttribute("value", "${tb_odinfoxm.ORDER_NUM }");

	form.appendChild(input_id);
	form.submit();
    
});
function fn_save(){

	var frm=document.getElementById("orderFrm");
	var count = 0;
	//$("input[name='RETURN_QTYS'][value='']").size()
	
	$("input[name='RETURN_QTYS']").each(function (i) {
		 console.log(i+"============"+$("input[name='RETURN_QTYS']").eq(i).val());
        if($("input[name='RETURN_QTYS']").eq(i).val()==null||$("input[name='RETURN_QTYS']").eq(i).val()==''){
        	count ++;
        }
        if($("input[name='RETURN_GBNS']").eq(i).val()==null||$("input[name='RETURN_GBNS']").eq(i).val()==''){
        	count++;
        }
    });
	
	if($("input[name='DLVY_AMT']").val()==null || $("input[name='DLVY_AMT']").val()==''){
		alert("배송비는 필수값입니다.") 
		return;
	}
	
	if(count>0){
		alert("반품수량과 반품사유는 필수값입니다.") 
		return;
	}
	
   
    if(!confirm("저장 하시겠습니까?")) return;
	
	frm.submit();
}

//pg사 결제 취소
function fn_pg_cancle(){
	alert("추후개발");
}

function addComma(num) { //숫자콤마만드는 함수
	  var regexp = /\B(?=(\d{3})+(?!\d))/g;
	  return num.toString().replace(regexp, ',');
}

function fn_qtychk(dtnum){
	var orderqty = parseInt($("#ORDER_QTY_"+dtnum).val());
	var returnqty = parseInt($("#RETURN_QTY_"+dtnum).val());
	
	if(orderqty < returnqty){
		alert("주문수량보다 반품수량이 더 많을 수 없습니다.");
		setTimeout(function(){
			$("#RETURN_QTY_"+dtnum).focus();
		})
		return false;
	}
	
	return true;
	
}
</script>