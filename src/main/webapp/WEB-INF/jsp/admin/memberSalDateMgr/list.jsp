<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/layout/inc/taglib.jsp" %>

<style type="text/css">
table.table>thead>tr>th {
    text-align: center;
    vertical-align: middle;
}
</style>




<section class="content-header">
	<h1>
		회원일별매출집계관리	
		<small>매출 목록</small>
	</h1>
	<ol class="breadcrumb">
		<li><a href="#"><i class="fa fa-dashboard"></i> Level</a></li>
		<li class="active">Here</li>
	</ol>
</section>

<c:set var="strActionUrl" value="${contextPath }/adm/memberSalDateMgr" />
<c:set var="strMethod" value="get" />

<c:choose>
<c:when test="${empty obj.COM_NM_ORDER or obj.COM_NM_ORDER =='desc' }"><c:set var="COM_NM_ORDER" value="asc" /></c:when>
<c:otherwise><c:set var="COM_NM_ORDER" value="desc" /></c:otherwise>
</c:choose>
<c:choose>
<c:when test="${empty obj.MEMB_NM_ORDER or  obj.MEMB_NM_ORDER =='desc' }"><c:set var="MEMB_NM_ORDER" value="asc" /></c:when>
<c:otherwise><c:set var="MEMB_NM_ORDER" value="desc" /></c:otherwise>
</c:choose>

<section class="content">
	<!-- 검색 박스 -->
	<div class="box box-primary">
		<div class="box-header with-border">
			<h3 class="box-title">회원검색 조건</h3>

			<div class="box-tools pull-right">
				<button type="button" class="btn btn-box-tool" data-widget="collapse">
					<i class="fa fa-minus"></i>
				</button>
				<button type="button" class="btn btn-box-tool" data-widget="remove">
					<i class="fa fa-remove"></i>
				</button>
			</div>
		</div>
		<!-- /.box-header -->
		<div class="box-body">
			<spform:form name="memberSalCntDateMgrForm" id="memberSalCntDateMgrForm" method="${strMethod }" action="${strActionUrl }" class="form-horizontal">
			<!-- <form class="form-horizontal"> -->
				<div class="box-body">
					<div class="form-group">
						<label for="inputEmail3" class="col-sm-1 control-label">검색어</label>

						<div class="col-sm-2">
							<select name="schGbn" class="form-control select2" style="width: 100%;">
								<option value="MEMB_NAME" ${ param.schGbn eq 'MEMB_NAME' ? "selected='selected'":''}>고객명</option>
								<option value="COM_NAME" ${ param.schGbn eq 'COM_NAME' ? "selected='selected'":''}>사업사상호</option>
							</select>
						</div>
						<div class="col-sm-8">
							<input name="schTxt" type="text" class="form-control" placeholder="검색어 입력" value="${param.schTxt }">
						</div>
						<div class="col-sm-1">
							<button type="submit" class="btn btn-success pull-right">검색</button>
						</div>
					</div>
					<div class="form-group">
						<label for="inputEmail3" class="col-sm-1 control-label">결제방식</label>
						<div class="col-sm-2">
							<select name="PAY_METD" class="form-control select2" style="width: 100%;">
								<option value="" ${ param.PAY_METD eq '' ? "selected='selected'":''}>전체</option>
								<option value="SC0040" ${ param.PAY_METD eq 'SC0040' ? "selected='selected'":''}>무통장입금</option>
								<option value="PAY_METD_01" ${ param.PAY_METD eq 'PAY_METD_01' ? "selected='selected'":''}>신용카드</option>
							</select>
						</div>
						<label for="inputEmail3" class="col-sm-1 control-label">기간설정</label>
						<div class="col-sm-3">
							<div class="form-group">
								<div class="col-sm-5">
									<input type="text" class="form-control pull-left" readonly="readonly"  name="datepickerStr" id="datepickerStr" value="${obj.datepickerStr }" >
								</div>
								<div class="col-sm-1" style="height: 34px;padding-top: 8px "> 
									~
								</div>
								<div class="col-sm-5">
									<input type="text" class="form-control pull-right" readonly="readonly" name="datepickerEnd" id="datepickerEnd" value="${obj.datepickerEnd }">
								</div>										
							</div>
						</div>
						<!--  -->
						<label for="inputEmail3" class="col-sm-1 control-label">회원구분</label>
						<div class="col-sm-2">
							<select name="MEMB_GUBN" class="form-control select2" style="width: 100%;">
								<option value="">전체</option>
								<option value="MEMB_GUBN_01" ${ param.MEMB_GUBN eq 'MEMB_GUBN_01' ? "selected='selected'":''}>일반회원</option>
								<option value="MEMB_GUBN_02" ${ param.MEMB_GUBN eq 'MEMB_GUBN_02' ? "selected='selected'":''}>사업자회원</option>
								<%-- <option value="MEMB_GUBN_03" ${ param.MEMB_GUBN eq 'MEMB_GUBN_03' ? "selected='selected'":''}>시스템관리자</option> --%>
								<%-- <option value="MEMB_GUBN_04" ${ param.MEMB_GUBN eq 'MEMB_GUBN_04' ? "selected='selected'":''}>도매유통회원</option> --%>
							</select>
						</div>
						<label for="inputEmail3" class="col-sm-1 control-label">계산서발행구분</label>
						<div class="col-sm-1">
							<select name="TAXCAL_YN" class="form-control select2" style="width: 100%;">
								<option value="">전체</option>
								<option value="Y" ${ param.TAXCAL_YN eq 'Y' ? "selected='selected'":''}>발행</option>
								<option value="N" ${ param.TAXCAL_YN eq 'N' ? "selected='selected'":''}>미발행</option>								
							</select>
						</div>
					</div>
				</div>
				<!-- /.row -->
			<!-- </form> -->
			</spform:form>
		</div>
		<!-- /.box-body -->
	</div>
	<!-- /.box -->
	<!-- 검색 박스 -->

	<div class="box">
		<div class="box-header with-border">
			<h3 class="box-title">회원 매출 집계 목록</h3>
			<div class="box-tools">
				<button type="button" class="btn btn-sm" onclick="javascript:fn_order_by('MEMB_NM_ORDER');">성명▼▲</button>
				<button type="button" class="btn btn-sm" onclick="javascript:fn_order_by('COM_NM_ORDER');">사업자상호▼▲</button>
				<button type="button" class="btn btn-primary btn-sm pull-right" id="btnExcel" style="margin-left:5px;">엑셀</button>
			</div>
		</div>
		<!-- /.box-header -->
		<div class="box-body"  style="white-space:nowrap;overflow-x:scroll;">
			<spform:form name="sortingForm" id="sortingForm" method="${strMethod }" action="${strActionUrl }" class="form-horizontal">
				<input type="hidden" id="MEMB_ORD_GUBUN" name="MEMB_ORD_GUBUN" value="${obj.MEMB_ORD_GUBUN }" /><!-- 주문일 정렬 -->
				<input type="hidden" id="MEMB_NM_ORDER" name="MEMB_NM_ORDER" value="${MEMB_NM_ORDER }" /><!-- 주문자명 정렬 -->
				<input type="hidden" id="COM_NM_ORDER" name="COM_NM_ORDER" value="${COM_NM_ORDER }" /><!-- 주문자아이디 정렬 -->
				<!-- 검색조건 유지 -->
				<input type="hidden" name="schGbn" value="${param.schGbn }" />
				<input type="hidden" name="schTxt" value="${param.schTxt }" />
				<input type="hidden" name="PAY_METD" value="${ param.PAY_METD }" />
				<input type="hidden" name="datepickerStr" value="${obj.datepickerStr }" />
				<input type="hidden" name="datepickerEnd" value="${obj.datepickerEnd }" />				
				<input type="hidden" name="MEMB_GUBN" value="${ param.MEMB_GUBN }" />
				<input type="hidden" name="TAXCAL_YN" value="${ param.TAXCAL_YN }" />
			</spform:form>
			<table class="table table-bordered">
				<!-- <colgroup>
					<col style="with:10px" />
					<col />
					<col style="with:50px" />
				</colgroup> -->
				<thead>
					<tr>
						<th>고객아이디</th>
						<th>고객명</th>
						<th>상호</th>
						<th>사업자번호</th>
						<th>일자</th>
						<th>과세</th>
						<th>면세</th>
						<th>배송비</th>
						<th>반품과세</th>
						<th>반품면세</th>
						<th>반품배송비</th>						
						<th>합계</th>
						<th>주소</th>						
						<th>이메일</th>						
					</tr> 
				</thead>
				<tbody>
					<c:forEach items="${obj.list }" var="ent" varStatus="loop">
					<tr>
						<td>${ent.MEMB_ID}</td>
						<td>${ent.MEMB_NAME}</td>
						<td>${ent.COM_NAME}</td>
						<td>${ent.COM_BUNB}</td>
						<td>${ent.ORDER_DATE}</td>
						<td style="text-align:right;"><fmt:formatNumber value="${ent.TAX_GUBN_01_SUM }"/></td>
						<td style="text-align:right;"><fmt:formatNumber value="${ent.TAX_GUBN_02_SUM }"/></td>
						<td style="text-align:right;"><fmt:formatNumber value="${ent.DLVY_AMT_SUM }"/></td>
						<td style="text-align:right;"><fmt:formatNumber value="${ent.TAX_GUBN_01_SUM_RT > 0 ? ent.TAX_GUBN_01_SUM_RT : 0}"/></td>
						<td style="text-align:right;"><fmt:formatNumber value="${ent.TAX_GUBN_02_SUM_RT > 0 ? ent.TAX_GUBN_02_SUM_RT : 0}"/></td>
						<td style="text-align:right;"><fmt:formatNumber value="${ent.DLVY_AMT_SUM_RT > 0 ? ent.DLVY_AMT_SUM_RT : 0}"/></td>
						<td><fmt:formatNumber value="${ent.ORDER_AMT_SUM - ent.ORDER_AMT_SUM_BEFORE_RT}"/></td>
						<td>${ent.MEMB_BADR}</td>
						<td>${ent.MEMB_MAIL}</td>
					</tr>
				</c:forEach>
				<c:if test="${obj.count == 0}">
					<tr>
						<td colspan="11">조회된 내용이 없습니다.</td>
					</tr>
				</c:if>
				</tbody>
			</table>
		</div>
		<paging:PageFooter totalCount="${ totalCnt }" rowCount="${ rowCnt }">
				<First><Previous><AllPageLink><Next><Last>
		</paging:PageFooter>
	</div>
</section>

<script type="text/javascript">
/* 
$(document).ready( function() {
	var date = new Date();
	date.setDate(date.getDate()-7);
	
    $('#datepickerStr').val(date.toDateInputValue());
    $('#datepickerEnd').val(new Date().toDateInputValue());
});​
 */
$(function() {

	//Date picker
/* 	$('#datepickerStr').datepicker({
		format: "yyyy-mm",
		minViewMode: 1
	}); */
	
	//일주일 전 날짜 
	var Str = $('#datepickerStr').val();
	var End = $('#datepickerEnd').val();
	
	//Date picker
	$('#datepickerStr').datepicker({
		autoclose: true,
		format: 'yyyy-mm-dd'
	});
	$('#datepickerEnd').datepicker({
		autoclose: true,
		format: 'yyyy-mm-dd'
	});
	
	// 엑셀
    $("#btnExcel").click(function() {
    	
    	var strAction = "${contextPath }/adm/memberSalDateMgr/excelDown";
    	var realAction =  "${strActionUrl }";
    	
        $("#memberSalCntDateMgrForm").attr("action", strAction);
        $("#memberSalCntDateMgrForm").submit();
        
        //원래대로
        $("#memberSalCntDateMgrForm").attr("action",realAction);
        
    });
});


//정렬 주문일 & 주문자명 클릭시 , 목록수 변경시
function fn_order_by(str){
	
	var frm=document.getElementById("sortingForm");
	if(str != ""){
		frm.MEMB_ORD_GUBUN.value=str;
	}
	frm.submit();
}
</script>
