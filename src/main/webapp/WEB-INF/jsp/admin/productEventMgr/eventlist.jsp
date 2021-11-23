<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/layout/inc/taglib.jsp" %>

<section class="content-header">
	<h1>
		행사상품관리	
		<small>할인상품 목록</small>
	</h1>
	<ol class="breadcrumb">
		<li><a href="#"><i class="fa fa-dashboard"></i> Level</a></li>
		<li class="active">Here</li>
	</ol>
</section>

<section class="content">
	<!-- 검색 박스 -->
	<div class="box box-primary">
		<div class="box-header with-border">
			<h3 class="box-title">검색 조건</h3>			
			<div class="box-tools pull-right">
				<button type="button" class="btn btn-box-tool" data-widget="collapse">
					<i class="fa fa-minus"></i>
				</button>
				<button type="button" class="btn btn-box-tool" data-widget="remove">
					<i class="fa fa-remove"></i>
				</button>
			</div>
		</div>
		<!-- search box-body -->
		<div class="box-body">
			<form class="form-horizontal" id="frm" method="get" action="${contextPath }/adm/productEventMgr">
				<!-- 검색조건 -->
				<input type="hidden" name="sortGubun" id="sortGubun" />
				<input type="hidden" name="sortOdr" id="sortOdr" />
				<input type="hidden" id="pagerMaxPageItems" name="pagerMaxPageItems" value="${obj.pagerMaxPageItems }" /><!-- 목록수 -->
				
				<div class="box-body">
					<div class="form-group">
						<label for="inputEmail3" class="col-sm-1 control-label">수정기간</label>						
						<div class="col-sm-4">
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
						<label for="inputEmail3" class="col-sm-1 control-label">검색어</label>
						<div class="col-sm-2">
							<select name="schGbn" class="form-control select2" style="width: 100%;">
								<option value="PD_NAME" ${obj.schGbn == 'PD_NAME' ? 'selected=selected':''}>상품명</option>
								<option value="PD_CODE" ${obj.schGbn == 'PD_CODE' ? 'selected=selected':''}>상품코드</option>
								<option value="PD_BARCD" ${obj.schGbn == 'PD_BARCD' ? 'selected=selected':''}>상품바코드</option>
							</select>
						</div>
						<div class="col-sm-3">
							<input name="schTxt" id="schTxt" type="text" class="form-control" placeholder="검색어 입력" <c:if test = '${obj.schTxt !=null && obj.schTxt != "null" }'>value="${obj.schTxt }"</c:if>>							
						</div>
						<div class="col-sm-1">
							<button type="submit" class="btn btn-success pull-right">검색</button>
						</div>
					</div>
				</div>
			</form>
		</div>
	</div>
	
	<!-- 정렬박스 -->	
	<div class="row" style="padding-bottom:10px;">
		<div class="col-xs-12">
			<button type="button" class="btn btn-primary btn-sm pull-left btnSort" style="margin-left: 5px" id="ordREG_DTM" sortGubun="REG_DTM" sortOdr="asc" sortName="등록일">등록일▼▲</button>
			<button type="button" class="btn btn-primary btn-sm pull-left btnSort" style="margin-left: 5px" id="ordMOD_DTM" sortGubun="MOD_DTM" sortOdr="asc" sortName="수정일">수정일▼▲</button>
			<button type="button" class="btn btn-primary btn-sm pull-left btnSort" style="margin-left: 5px" id="ordPD_NAME" sortGubun="PD_NAME" sortOdr="asc" sortName="상품명">상품명▼▲</button>
					
			<div class="col-xs-1 pull-right">				
                <select name="cnt" id="cnt" class="form-control select2" style="width: 100%;" onchange="javascript:fn_order_by('');">									
					<option value="10" ${obj.pagerMaxPageItems == '10' ? 'selected=selected':''}>10</option>
					<option value="20" ${obj.pagerMaxPageItems == '20' ? 'selected=selected':''}>20</option>
					<option value="30" ${obj.pagerMaxPageItems == '30' ? 'selected=selected':''}>30</option>
					<option value="40" ${obj.pagerMaxPageItems == '40' ? 'selected=selected':''}>40</option>
					<option value="50" ${obj.pagerMaxPageItems == '50' ? 'selected=selected':''}>50</option>									
				</select>
			</div>
			<label for="inputEmail3" class="pull-right" style="margin-left: 5px">목록수</label>			
		</div>
	</div>
	
	<div class="box">
		<!-- box-header -->
		<div class="box-header with-border">
			<h3 class="box-title">행사상품 목록</h3>
			<div class="box-tools">
				<button type="button" class="btn btn-sm btn-primary pull-right" id="btnExcel" style="margin-left:5px;">엑셀</button>
				<a href="${contextPath}/adm/productEventMgr/event" class="btn btn-sm btn-primary pull-right" style="margin-left:5px;">등록</a>
			</div>
		</div>
		<!-- /.box-header -->
		<!-- box-body -->
		<div class="box-body">
			<table class="table table-bordered">
				<colgroup>
					<col style="with:10px" />
					<col />
					<col />
					<col />
					<col />
					<col />
					<col />
					<col />
				</colgroup>
				<thead>
					<tr>
						<th >번호</th>						
						<th>상품코드</th>
						<th>상품바코드</th>
						<th>상품명</th>
						<th>판매가격</th>						
						<th>할인금액</th>
						<th>할인구분</th>
						<th>행사가격</th>
						<th>판매상태</th>
						<th>수정자</th>
						<th>수정일</th>
					</tr>
				</thead>
				<tbody>
				<c:if test="${fn:length(obj.list) == 0}">
					<tr>
						<td colspan="8">조회된 리스트가 없습니다.</td>
					</tr>
				</c:if>
				<c:forEach items="${obj.list }" var="list" varStatus="loop">
					<tr>
						<td>${loop.count }</td>						
						<td>${list.PD_CODE }</td>
						<td>${list.PD_BARCD }</td>
						<td>${list.PD_NAME }</td>
						<td align="right" ><fmt:formatNumber value="${list.PD_PRICE }" pattern="#,###"/></td>						
						<td align="right" ><fmt:formatNumber value="${list.PDDC_VAL }" pattern="#,###"/></td>
						<td>
							<select name="PDDC_GUBN" class="form-control select2" disabled>								
								<option value="PDDC_GUBN_01" <c:if test='${list.PDDC_GUBN=="PDDC_GUBN_01" }'>selected="selected"</c:if> >할인안함</option>
								<option value="PDDC_GUBN_02" <c:if test='${list.PDDC_GUBN=="PDDC_GUBN_02" }'>selected="selected"</c:if> >할인금액</option>
								<option value="PDDC_GUBN_03" <c:if test='${list.PDDC_GUBN=="PDDC_GUBN_03" }'>selected="selected"</c:if> >할인율</option>
								<option value="PDDC_GUBN_04" <c:if test='${list.PDDC_GUBN=="PDDC_GUBN_04" }'>selected="selected"</c:if> >특가상품</option>
								<option value="PDDC_GUBN_05" <c:if test='${list.PDDC_GUBN=="PDDC_GUBN_05" }'>selected="selected"</c:if> >추석선물세트</option>
							</select>
						</td>
						<td align="right" ><fmt:formatNumber value="${list.REAL_PRICE }" pattern="#,###"/></td>
						<td>${list.SALE_CON }</td>
						<td>${list.MODP_ID }</td>
						<td>${list.MOD_DTM }</td>
					</tr>
				</c:forEach>
				</tbody>
			</table>
		</div>		
		<!-- 페이징 -->
		<paging:PageFooter totalCount="${ totalCnt }" rowCount="${ rowCnt }">
			<First><Previous><AllPageLink><Next><Last>
		</paging:PageFooter>			
	</div>
	
</section>


<script type="text/javascript">
$(function() {

	<c:if test="${!empty param.schGbn}">
		$('#schGbn').val('${param.schGbn}');
	</c:if>
		
	<c:if test="${!empty param.sortGubun}">
		$('#ord${param.sortGubun}').attr("sortOdr", "${param.sortOdr eq 'desc' ? 'asc' : 'desc'}");
		$('#ord${param.sortGubun}').text( $('#ord${param.sortGubun}').attr("sortName") + " ${param.sortOdr eq 'desc' ? '△▼' : '▲▽'}");
		$("#sortGubun").val('${param.sortGubun}');
		$("#sortOdr").val('${param.sortOdr}');
	</c:if>

	
	//Date picker
	$('#datepickerStr').datepicker({
		autoclose: true,
		format: 'yyyy-mm-dd'
	});
	$('#datepickerEnd').datepicker({
		autoclose: true,
		format: 'yyyy-mm-dd'
	});	
	
	// 정렬
	$('.btnSort').click(function(){
		var strSortGubun = $(this).attr("sortGubun");
		var strSortOdr = $(this).attr("sortOdr");
		
		/* alert(strSortGubun+'/'+strSortOdr); */
		
		$("#sortGubun").val(strSortGubun);
		$("#sortOdr").val(strSortOdr);
		
		$("#frm").submit();		
	});

	// 엑셀
	$("#btnExcel").click(function() {
	    $("#excelFrm").submit();
	});		
});


//정렬 주문일 & 주문자명 클릭시 , 목록수 변경시
function fn_order_by(str){		
	var frm=document.getElementById("frm");

	frm.pagerMaxPageItems.value=$("#cnt").val();
	console.log(frm.pagerMaxPageItems.value);
	frm.submit();
}
</script>

<form id="excelFrm" method="get" action="${contextPath }/adm/productEventMgr/excelDownload"></form>

