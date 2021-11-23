<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/layout/inc/taglib.jsp" %>

<style>
.goodsImg {
    width: auto; height: auto;
    max-width: 50px;
    max-height: 50px;
    min-width: 25px;
    min-height: 25px;
}
</style>

<section class="content-header">
	<h1>상품관리
		<small>상품 목록</small>
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
		<!-- /.box-header -->
		<div class="box-body">
			<form class="form-horizontal" id="frm" >
				<input type="hidden" name="sortGubun" id="sortGubun" />
				<input type="hidden" name="sortOdr" id="sortOdr" />
				<input type="hidden" name="schTxt_bef" value="${ obj.schTxt_bef }" />
				<input type="hidden" id="pagerMaxPageItems" name="pagerMaxPageItems" value="${obj.pagerMaxPageItems }" /><!-- 목록수 -->
				<div class="box-body">
					<div class="form-group">
						<label for="inputEmail3" class="col-sm-1 control-label">검색어</label>

						<div class="col-sm-2">
							<select name="schGbn" class="form-control select2" style="width: 100%;">
								<option value="PD_NAME" selected="selected">상품명</option>
								<option value="PD_CODE">상품코드</option>
								<option value="PD_BARCD">상품바코드</option>
							</select>
						</div>
						<div class="col-sm-8">
							<input name="schTxt" type="text" class="form-control" placeholder="검색어 입력" value="${param.schTxt }" onclick="javascript:cleanResearch()">
						</div>
						<div class="col-sm-1">
							<button type="submit" class="btn btn-success pull-right">검색</button>
						</div>
					</div>
				</div>
				
				<div class="box-body">
					<div class="form-group">
						<div class="col-sm-3"></div>
						<div class="col-sm-8">
							<input type = "text" class="form-control"  name="reSearch" id ="reSearch"  placeholder="결과내 재검색" value="${param.reSearch }">
						</div>
					</div>
				</div>
					
				<div class="box-body">
					<div class="form-group">
						<label for="inputEmail3" class="col-sm-1 control-label">일배상품</label>
						<div class="col-sm-2">
							<jsp:include page="/common/comCodForm">
								<jsp:param name="COMM_CODE" value="ONDY_GUBN" />
								<jsp:param name="name" value="ONDY_GUBN" />
								<jsp:param name="value" value="${obj.ONDY_GUBN }" />
								<jsp:param name="type" value="select" />
								<jsp:param name="top" value="선택" />
							</jsp:include>
							<%-- <select name="ONDY_GUBN" class="form-control select2" style="width: 100%;">
								<option value="" <c:if test='${obj.ONDY_GUBN=="" }'>selected="selected"</c:if>>전체</option>
								<option value="Y" <c:if test='${obj.ONDY_GUBN=="Y" }'>selected="selected"</c:if> >사용</option>
								<option value="N" <c:if test='${obj.ONDY_GUBN=="N" }'>selected="selected"</c:if> >미사용</option>
							</select> --%>
						</div>
						<label for="inputEmail3" class="col-sm-1 control-label">판매상태</label>
						<div class="col-sm-2">
							<jsp:include page="/common/comCodForm">
								<jsp:param name="COMM_CODE" value="SALE_CON" />
								<jsp:param name="name" value="SALE_CON" />
								<jsp:param name="value" value="${obj.SALE_CON }" />
								<jsp:param name="type" value="select" />
								<jsp:param name="top" value="선택" />
							</jsp:include>
						</div>
					</div>
				</div>
				<!-- /.row -->
			</form>
		</div>
		<!-- /.box-body -->
	</div>
	<!-- /.box -->
	<!-- 검색 박스 -->

	<!-- 검색 박스 -->
	<div class="box box-primary">
		<!-- /.box-header -->
		<div class="box-body">
				<div class="box-body">
					<div class="form-group">
						<label for="inputEmail3" class="col-sm-1 control-label">정렬</label>
						<div class="col-sm-6">
							<button type="button" class="btn btn-primary btn-sm btnSort" id="ordREG_DTM" sortGubun="REG_DTM" sortOdr="asc" sortName="등록일">등록일 △▽</button>
							<button type="button" class="btn btn-primary btn-sm btnSort" id="ordMOD_DTM" sortGubun="MOD_DTM" sortOdr="asc" sortName="수정일">수정일 △▽</button>
							<button type="button" class="btn btn-primary btn-sm btnSort" id="ordPD_NAME" sortGubun="PD_NAME" sortOdr="asc" sortName="상품명">상품명 △▽</button>
						</div>
						<div class="col-sm-5"></div>
						<div class="col-sm-5">
							<label for="inputEmail3" class="col-sm-2 control-label">목록수</label>
							<div class="col-sm-3">
				                <select name="cnt" id="cnt" class="form-control select2" style="width: 100%;" onchange="javascript:fn_order_by('');">
									<option value="10" ${obj.pagerMaxPageItems == '10' ? 'selected=selected':''}>10</option>
									<option value="20" ${obj.pagerMaxPageItems == '20' ? 'selected=selected':''}>20</option>
									<option value="30" ${obj.pagerMaxPageItems == '30' ? 'selected=selected':''}>30</option>
									<option value="40" ${obj.pagerMaxPageItems == '40' ? 'selected=selected':''}>40</option>
									<option value="50" ${obj.pagerMaxPageItems == '50' ? 'selected=selected':''}>50</option>
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
		<div class="box-header with-border">
			<h3 class="box-title">상품 목록</h3>
			
			<div class="box-tools">
				<button type="button" class="btn btn-primary btn-sm pull-right" id="btnExcel" style="margin-left:5px;">엑셀</button>
				<a href="${contextPath}/adm/productMgr/new?pageNum=${obj.pageNum }&rowCnt=${obj.rowCnt }${link}" class="btn btn-sm btn-primary pull-right" style="margin-left:5px;">등록</a>
				<button type="button" class="btn btn-primary btn-sm pull-right" id="btnSave" style="margin-left:5px;">저장</button>
				<!-- 상품액셀 업로드 벗튼 -->
				<button type="button" class="btn btn-primary btn-sm btnExcelUpload">
					<i class="fa fa-file-excel-o"></i> 상품 엑셀 업로드 
				</button>
			</div>
		</div>
		<!-- /.box-header -->
		<div class="box-body">
			<table class="table table-bordered">
				<colgroup>
					<col style="with:20px" />
					<col style="with:100px" />
					<col />
					<col style="with:50px" />
					<col style="with:50px" />
					<col style="with:50px" />
					<col style="with:50px" />
					<col style="with:50px" />
				</colgroup>
				<thead>
					<tr>
                       	<th><input id="allCheck" name="toggleChk" type="checkbox" onclick="javascript:fnCheckAll('pdtIdArr')"></th>
						<th>대표이미지</th>
						<th>카테고리/상품명</th>
						<th>변경판가</th>
						<th>판매가격</th>
						<th>할인구분</th>
						<th>할인값</th>
						<th>판매상태</th>
						<!-- th>기능</th -->
					</tr>
				</thead>
				<tbody>
				<c:forEach items="${obj.list }" var="ent" varStatus="loop">
					<tr>
                        <td>
                        	<input name="chkPdtId" value="${ent.PD_CODE }" type="checkbox"/>
                        	<input name="pdtIdYn" value="N" type="hidden"/>
                        </td>
						<td align="center">
							<c:if test="${ !empty(ent.ATFL_ID) }">
								<c:if test="${ent.FILEPATH_FLAG eq 'woori' }">
									<img class="goodsImg" src="${contextPath }/common/commonFileDown?IMG_GUBUN=thumbnail&ATFL_ID=${ent.ATFL_ID }&ATFL_SEQ=${ent.RPIMG_SEQ}" onclick="doImgPop('${contextPath }/common/commonFileDown?ATFL_ID=${ent.ATFL_ID }&ATFL_SEQ=${ent.RPIMG_SEQ}')"  title="클릭하시면 원본크기로 보실 수 있습니다." style="cursor: pointer;"/>
								</c:if>
								<c:if test="${ent.FILEPATH_FLAG ne 'woori' }">
									<img class="goodsImg" src="https://www.cjfls.co.kr/common/commonFileDown?IMG_GUBUN=thumbnail&ATFL_ID=${ent.ATFL_ID }&ATFL_SEQ=${ent.RPIMG_SEQ}" onclick="doImgPop('${contextPath }/common/commonFileDown?ATFL_ID=${ent.ATFL_ID }&ATFL_SEQ=${ent.RPIMG_SEQ}')"  title="클릭하시면 원본크기로 보실 수 있습니다." style="cursor: pointer;"/>
								</c:if>
							</c:if>
							<c:if test="${ empty(ent.ATFL_ID) }">
								<img class="goodsImg" src="${contextPath }/resources/images/mall/goods/noimage.png" />
							</c:if>
						</td>
						<td align="left">
							<c:out value="${ ent.CAGO_NM_PATH }" escapeXml="true"/><br>
							<a href="${contextPath }/adm/productMgr/edit/${ ent.PD_CODE }?pageNum=${obj.pageNum }&rowCnt=${obj.rowCnt }${link}">
								<c:out value="${ ent.PD_NAME }" escapeXml="true"/>
							</a>
						</td>
						<td align="right" style="padding-left: 20px">
							<%-- <c:if test='${ent.ONDY_GUBN != null && ent.ONDY_GUBN=="Y" }'> 일배상품만 보이게 하려면 주석해제 --%>
								<input type="number" class="form-control" id="PRICE_CHANGE_${ent.PD_CODE }">
							<%-- </c:if> --%>
						</td>
						<td id="PRICE_BEFORE_${ent.PD_CODE }"align="right" style="padding-left: 20px;<c:if test = '${ent.TODAY_CHANGE_YN=="Y"}'>color: red;</c:if>"><fmt:formatNumber value="${ ent.PD_PRICE }" pattern="#,###"/></td>							
						<td>
							<select name="PDDC_GUBN" class="form-control select" id = "PDDC_GUBN_${ent.PD_CODE }">
								<c:forEach var="entCode" items="${ codPDDC_GUBN }" varStatus="status">
									<option value="${ entCode.COMD_CODE }" <c:if test="${ ent.PDDC_GUBN eq entCode.COMD_CODE }">selected</c:if>>${ entCode.COMDCD_NAME }</option>
								</c:forEach>
							</select>
						</td>
						<td>${ ent.PDDC_VAL }</td>						
						<td>		
							<select name="SALE_CON" class="form-control select" id = "SALE_CON_${ent.PD_CODE }">
								<c:forEach var="entCode" items="${ codSALE_CON }" varStatus="status">
									<option value="${ entCode.COMD_CODE }" <c:if test="${ ent.SALE_CON eq entCode.COMD_CODE }">selected</c:if>>${ entCode.COMDCD_NAME }</option>
								</c:forEach>
							</select>
						
						</td>							
						<!-- td>---</td -->	
					</tr>
				</c:forEach>
				<c:if test="${obj.count == 0}">
					<tr>
						<td colspan="8">조회된 내용이 없습니다.</td>
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


<form id="excelFrm" method="get" action="${contextPath }/adm/productMgr/excelDown">

</form>

<script type="text/javascript">
$(function() {
	
	$('#btnSave').on('click',function(){

		var checkboxValues =[];
		// 체크항목
		$("input:checkbox[name=chkPdtId]:checked").each(function(){
			checkboxValues.push($(this).val());
		});
		
		if(checkboxValues==''||checkboxValues==null){
			checkboxValues.push("");
			
			alert("저장할 항목을 체크해주세요.");
			return false;
		}
	
		var saveValues =[];
		
		for(var i=0; i<checkboxValues.length;i++){
			
			var price_change = "#PRICE_CHANGE_"+checkboxValues[i];
			var pddc_gubn = "#PDDC_GUBN_"+checkboxValues[i];
			var sale_con = "#SALE_CON_"+checkboxValues[i];
			var price_before = "#PRICE_BEFORE_"+checkboxValues[i];
			
			if($(price_change).val() !=null){
				var price_change_val = $(price_change).val().trim();
				var pddc_gubn_val =$(pddc_gubn).val(); 
				var sale_con_val = $(sale_con).val();
				var price_before = parseInt($(price_before).text().replace(/[^\d]+/g, ''));
				if(price_change_val!=""){	
					var save_val = checkboxValues[i]+"!!@"+pddc_gubn_val+"!!@"+sale_con_val+"!!@"+price_change_val
					saveValues.push(save_val);
				}else{
					var save_val = checkboxValues[i]+"!!@"+pddc_gubn_val+"!!@"+sale_con_val+"!!@"+price_before
					saveValues.push(save_val);
				}
			}else{
				var save_val = checkboxValues[i]+"!!@"+pddc_gubn_val+"!!@"+sale_con_val+"!!@"+price_before
				saveValues.push(save_val);
			}
		}
		
       //var formMap = {"saveValues":saveValues };
       var formMap = {"saveValues":saveValues
				,"sortGubun": $('#sortGubun').val()
				,"sortOdr": $('#sortOdr').val()
				,"schTxt_bef": $('input[name=schTxt_bef]').val()
				,"pagerMaxPageItems": $('#pagerMaxPageItems').val()
				,"schGbn": $('select[name=schGbn]').val()
				,"schTxt": $('input[name=schTxt]').val()
				,"reSearch": $('#reSearch').val()
				,"ONDY_GUBN": $('select[name=ONDY_GUBN]').val()
			  };
       cusFormNew("${contextPath }/adm/productMgr/updateList", formMap);
	})
	
	
	// 엑셀
    $("#btnExcel").click(function() {
    	
		
        $("#excelFrm").submit();
        
    });
	

	
	<c:if test="${!empty param.schGbn}">
		$('#schGbn').val('${param.schGbn}');
	</c:if>
		
	<c:if test="${!empty param.sortGubun}">
		$('#ord${param.sortGubun}').attr("sortOdr", "${param.sortOdr eq 'desc' ? 'asc' : 'desc'}");
		$('#ord${param.sortGubun}').text( $('#ord${param.sortGubun}').attr("sortName") + " ${param.sortOdr eq 'desc' ? '△▼' : '▲▽'}");
		$("#sortGubun").val('${param.sortGubun}');
		$("#sortOdr").val('${param.sortOdr}');
	</c:if>
	
	$('.goodsImg').each(function() {
		$(this).error(function(){
			$(this).attr('src', '${contextPath }/resources/images/mall/goods/noimage.png');
		});
	});
	
	$("#allCheck").click(function(){
		if($("#allCheck").prop("checked")) {
			$("input[type=checkbox]").prop("checked",true);
		} else {
			$("input[type=checkbox]").prop("checked",false);
		}
	});

	$('.btnSort').click(function(){
		var strSortGubun = $(this).attr("sortGubun");
		var strSortOdr = $(this).attr("sortOdr");
		
		$("#sortGubun").val(strSortGubun);
		$("#sortOdr").val(strSortOdr);
		
		$("#frm").submit();
		
	});
	
	//-- 상품 엑셀 업로드 관련 --//
	$(".btnExcelUpload").click(function(){	//엑셀 업로드 버튼
		$('#myModal').modal('show');
    });
	
	$("#btnExcelDown").click(function(){	//엑셀 양식 받기
		document.location.href = "${contextPath }/upload/jundan/excel/pdprice_excel_form.xls";
    });
	
	$("#btnExcelSave").click(function(){	//엑셀저장
		$('#excelFrm2').submit();
    });
	
	/*
	$('.btnTest').click(function(){

		$("input[name=chkPdtId]").each(function(n){
		
			if($(this).is(":checked")){
				$("input[name='pdtIdYn']").eq(n).val("Y");
			}
			
		});
		
	});
	*/
});

function fn_order_by(str){
	
	var frm=document.getElementById("frm");
	
	frm.pagerMaxPageItems.value=$("#cnt").val();
	console.log(frm.pagerMaxPageItems.value);
		
	frm.submit();

}

var img1= new Image(); 
function doImgPop(img){ 
	img1= new Image(); 
	img1.src=(img); 
	imgControll(img); 
} 

function cleanResearch(){
	$('#reSearch').val("");
}
	  
function imgControll(img){ 
	if((img1.width!=0)&&(img1.height!=0)){ 
		viewImage(img); 
	} 
	else{ 
		controller="imgControll('"+img+"')"; 
		intervalID=setTimeout(controller,20); 
	} 
}
function viewImage(img){ 
	W=img1.width+20; 
	H=img1.height+20; 
	O="width="+W+",height="+H+",scrollbars=yes"; 
	imgWin=window.open("","",O); 
	imgWin.document.write("<html><head><title>:: 이미지상세보기 ::</title></head>");
	imgWin.document.write("<body topmargin=0 leftmargin=0>");
	imgWin.document.write("<img src="+img+" onclick='self.close()' style='cursor:pointer;' title ='클릭하시면 창이 닫힙니다.'>");
	imgWin.document.close();
}
</script>

<!-- 상품엑셀 업로드 모달 -->
<!-- Modal -->
<div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
	<div class="modal-dialog" role="document">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal" aria-label="Close">
					<span aria-hidden="true">&times;</span>
				</button>
				<h4 class="modal-title" id="myModalLabel">상품 엑셀 업로드</h4>
			</div>
			<div class="modal-body">
            <form role="form">
            </form>
				<!-- 기업정보 박스 -->
					<div class="box box-info">
						<div class="box-header with-border">
							<h3 class="box-title">업로드</h3>
						</div>
						<!-- /.box-header -->
					
						<!-- box-body -->
						<spform:form name="excelFrm2" id="excelFrm2" method="post" action="${contextPath }/adm/productMgr/excelUpload" role="form" class="form-horizontal" enctype="multipart/form-data">
						<div class="box-body">

							<div class="form-group">
								<label for="MEMB_ID" class="col-sm-2 control-label">양식파일</label>
								<div class="col-sm-10">
									<button type="button" class="btn btn-primary pull-left" id="btnExcelDown"><i class="fa fa-download"></i> 양식 받기 </button>
								</div>
							</div>
							
							<div class="form-group">
								<label for="MEMB_NAME" class="col-sm-2 control-label">엑셀파일</label>
								<div class="col-sm-10">
									<input type="file" id="EXCEL_FILE" name="EXCEL_FILE" id="file-simple" value=""> 
								</div>
							</div>

						</div>
						</spform:form>
						<!-- /.box-body -->
					</div>
				<!-- 기업정보 박스 -->
			</div>
			<div class="modal-footer">
				<button type="button" class="btn btn-default" data-dismiss="modal">닫기</button>
				<button type="button" class="btn btn-primary" id="btnExcelSave">업로드</button>
			</div>
		</div>
	</div>
</div> 