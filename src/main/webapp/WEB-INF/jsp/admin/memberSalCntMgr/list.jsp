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
		회원 관리	
		<small>회원 목록</small>
	</h1>
	<ol class="breadcrumb">
		<li><a href="#"><i class="fa fa-dashboard"></i> Level</a></li>
		<li class="active">Here</li>
	</ol>
</section>

<c:set var="strActionUrl" value="${contextPath }/adm/memberSalCntMgr" />
<c:set var="strMethod" value="get" />


<c:choose>
<c:when test="${empty obj.MEMB_NM_ORDER or  obj.MEMB_NM_ORDER =='desc' }"><c:set var="MEMB_NM_ORDER" value="asc" /></c:when>
<c:otherwise><c:set var="MEMB_NM_ORDER" value="desc" /></c:otherwise>
</c:choose>
<c:choose>
<c:when test="${empty obj.COM_NM_ORDER or  obj.COM_NM_ORDER =='desc' }"><c:set var="COM_NM_ORDER" value="asc" /></c:when>
<c:otherwise><c:set var="COM_NM_ORDER" value="desc" /></c:otherwise>
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
			<spform:form name="memberSalCntMgrForm" id="memberSalCntMgrForm" method="${strMethod }" action="${strActionUrl }" class="form-horizontal">
			<!-- <form class="form-horizontal"> -->
				<div class="box-body">
					<div class="form-group">
						<label for="inputEmail3" class="col-sm-1 control-label">검색어</label>

						<div class="col-sm-2">
							<select name="schGbn" class="form-control select2" style="width: 100%;">
								<option value="MEMB_NAME" ${ param.schGbn eq 'MEMB_NAME' ? "selected='selected'":''}>회원명</option>
								<option value="MEMB_ID" ${ param.schGbn eq 'MEMB_ID' ? "selected='selected'":''}>회원ID</option>
								<option value="COM_NAME" ${ param.schGbn eq 'COM_NAME' ? "selected='selected'":''}>사업자상호</option>
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
						<label for="inputEmail3" class="col-sm-1 control-label">결제방법</label>
						<div class="col-sm-2">
							<select name="PAY_GUBN" class="form-control select2" style="width: 100%;">
								<option value="">전체</option>
								<option value="PAY_METD_01" ${ param.PAY_GUBN eq 'PAY_METD_01' ? "selected='selected'":''}>신용카드</option>
								<option value="SC0040" ${ param.PAY_GUBN eq 'SC0040' ? "selected='selected'":''}>무통장입금</option>
							</select>
						</div>
						<label for="inputEmail3" class="col-sm-1 control-label">기간별 조회</label>
						<div class="col-sm-2">
							<div class="form-group">
								<div class="col-sm-10">
									<input type="text" class="form-control pull-left" name="datepickerStr" id="datepickerStr" value="${obj.datepickerStr }">
								</div>
							</div>
						</div>
						<!-- 조회구분자 추가_20190424 -->
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
						<label for="inputEmail3" class="col-sm-1 control-label">세금계산서<br>발행구분</label>
						<div class="col-sm-2">
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
		<div class="box-body">
			<spform:form name="sortingForm" id="sortingForm" method="${strMethod }" action="${strActionUrl }" class="form-horizontal">
				<input type="hidden" id="MEMB_ORD_GUBUN" name="MEMB_ORD_GUBUN" value="${obj.MEMB_ORD_GUBUN }" /><!-- 주문일 정렬 -->
				<input type="hidden" id="MEMB_NM_ORDER" name="MEMB_NM_ORDER" value="${MEMB_NM_ORDER }" /><!-- 주문자명 정렬 -->
				<input type="hidden" id="COM_NM_ORDER" name="COM_NM_ORDER" value="${COM_NM_ORDER }" /><!-- 주문자아이디 정렬 -->
				<!-- 검색조건 유지 -->
				<input type="hidden" name="schGbn" value="${param.schGbn }" />
				<input type="hidden" name="schTxt" value="${param.schTxt }" />
				<input type="hidden" name="PAY_GUBN" value="${ param.PAY_GUBN }" />
				<input type="hidden" name="datepickerStr" value="${obj.datepickerStr }" />
				<input type="hidden" name="MEMB_GUBN" value="${ param.MEMB_GUBN }" />
				<input type="hidden" name="TAXCAL_YN" value="${ param.TAXCAL_YN }" />
			</spform:form>
			<div class="box-body" style="white-space:nowrap;overflow-x:scroll;">
			<table class="table table-bordered">
				<!-- <colgroup>
					<col style="with:10px" />
					<col />
					<col style="with:50px" />
				</colgroup> -->
				<thead>
					<tr>
						<th rowspan="3">번호</th>
						<th rowspan="3">회원구분</th>
						<th rowspan="3">아이디</th>
						<th rowspan="3">고객명<br>(대표자명)</th>
						<th rowspan="3">사업자상호</th>
						<th rowspan="3">사업자번호</th>
						<!-- <th rowspan="3">구매완료<br>건수</th> -->
						<th rowspan="3">누적금액</th>
						<th rowspan="3">배송비<br>누적금액</th>
						<th rowspan="3">과세매출</th>
						<th rowspan="3">면세매출</th>
						<!-- 반품금액 -->
						<th rowspan="3">반송금액</th>
						<th rowspan="3">반송배송비</th>
						<th rowspan="3">반송과세매출</th>
						<th rowspan="3">반송면세매출</th>
						<th rowspan="3">총 매출금액</th>
						<!-- 반품금액 -->
						<!-- <th rowspan="3">카드결제</th>
						<th rowspan="3">무통장결제</th> -->
						
						<th colspan="4" style="text-align:center">결제구분</th>
						<th rowspan="3">사업자 주소</th>
						<th rowspan="3">사업자 이메일</th>
					</tr>
					<tr>
						<th colspan="2">카드</th>
						<th colspan="2">무통장</th>
						<!-- <th colspan="2">현금영수증</th> -->
					</tr>
					<tr>
						<th>과세</th>
						<th>면세</th>
						<th>과세</th>
						<th>면세</th>
						<!-- <th>과세</th>
						<th>면세</th> -->
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${obj.list }" var="ent" varStatus="loop">
					<tr>
						<td>${ent.RNUM }</td>
						<td>${ent.MEMB_GUBN_NM}</td>
						<td>${ent.MEMB_ID}</td>
						<td>${ent.MEMB_NAME}</td>
						<td>${ent.COM_NAME}</td>
						<td>${ent.COM_BUNB}</td>
						<%-- <td>${ent.ORDER_CNT}</td> --%>
						<td style="text-align:right;"><fmt:formatNumber value="${ent.ORDER_AMT_SUM }"/></td>
						<td style="text-align:right;"><fmt:formatNumber value="${ent.DLVY_AMT_SUM }"/></td>
						<td style="text-align:right;"><fmt:formatNumber value="${ent.TAX_GUBN_01_SUM }"/></td>
						<td style="text-align:right;"><fmt:formatNumber value="${ent.TAX_GUBN_02_SUM }"/></td>
						<!-- 반송매출 -->
						<td style="text-align:right;"><fmt:formatNumber value="${ent.ORDER_AMT_SUM_BEFORE_RT> 0 ? ent.ORDER_AMT_SUM_BEFORE_RT : 0}"/></td>
						<td style="text-align:right;"><fmt:formatNumber value="${ent.DLVY_AMT_SUM_RT >0 ? ent.DLVY_AMT_SUM_RT : 0}"/></td>
						<td style="text-align:right;"><fmt:formatNumber value="${ent.TAX_GUBN_01_SUM_RT > 0 ? ent.TAX_GUBN_01_SUM_RT : 0}"/></td>
						<td style="text-align:right;"><fmt:formatNumber value="${ent.TAX_GUBN_02_SUM_RT > 0 ? ent.TAX_GUBN_02_SUM_RT : 0}"/></td>
						<td style="text-align:right;"><fmt:formatNumber value="${ent.ORDER_AMT_SUM - (ent.ORDER_AMT_SUM_BEFORE_RT )}"/></td>
						<!-- 반송 -->
						<td style="text-align:right;"><fmt:formatNumber value="${ent.PAY_METD_01_TAX_GUBN_01_SUM >0 ? ent.PAY_METD_01_TAX_GUBN_01_SUM : 0}"/></td>
						<td style="text-align:right;"><fmt:formatNumber value="${ent.PAY_METD_01_TAX_GUBN_02_SUM >0 ? ent.PAY_METD_01_TAX_GUBN_02_SUM : 0}"/></td>
						<td style="text-align:right;"><fmt:formatNumber value="${ent.PAY_METD_02_TAX_GUBN_01_SUM >0 ? ent.PAY_METD_02_TAX_GUBN_01_SUM : 0}"/></td>
						<td style="text-align:right;"><fmt:formatNumber value="${ent.PAY_METD_02_TAX_GUBN_02_SUM >0 ? ent.PAY_METD_02_TAX_GUBN_02_SUM : 0}"/></td>
						<td>${ent.COM_BADR }</td>
						<td>${ent.MEMB_MAIL}</td>
					</tr>
				</c:forEach>
				<c:if test="${obj.count == 0}">
					<tr>
						<td colspan="15">조회된 내용이 없습니다.</td>
					</tr>
				</c:if>
				</tbody>
			</table>
		</div>
		</div>
		<paging:PageFooter totalCount="${ totalCnt }" rowCount="${ rowCnt }">
				<First><Previous><AllPageLink><Next><Last>
		</paging:PageFooter>
	</div>
</section>
<form id="excelFrm" method="get" action="${contextPath }/adm/memberSalCntMgr/excelDown">

</form>
<script type="text/javascript">
$(function() {

	//Date picker
	$('#datepickerStr').datepicker({
		format: "yyyy-mm",
		minViewMode: 1
	});
	
	// 엑셀
    $("#btnExcel").click(function() {
    	// form
    	/*var form = document.createElement("form");     

    	form.setAttribute("method","get");                    
    	form.setAttribute("action","${strActionUrl }"+"/excelDown");        
    	
    	alert("<c:out value='${strActionUrl}'/>"+"/excelDown");

    	document.body.appendChild(form);
    	form.submit;
    	*/
    	//$("#excelFrm").submit();
    	
    	var strAction = "${contextPath }/adm/memberSalCntMgr/excelDown";
    	var realAction =  "${strActionUrl }";
    	
        $("#memberSalCntMgrForm").attr("action", strAction);
        $("#memberSalCntMgrForm").submit();
        
        //원래대로
        $("#memberSalCntMgrForm").attr("action",realAction);
        
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
