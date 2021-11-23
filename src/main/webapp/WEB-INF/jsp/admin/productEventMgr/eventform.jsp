<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/layout/inc/taglib.jsp" %>

<script type="text/javascript">

$(function(){
	//숫자만 입력토록 함.
    $(document).on("keyup", ".number", function() {
    	$(this).number(true);
    });
	
  	//엑셀 업로드 버튼
    $(".btnExcelUpload").click(function(){	
		$('#myModal').modal('show');
    });
  	
  	//엑셀 양식 받기
    $("#btnExcelDown").click(function(){	
		document.location.href = "${contextPath}/upload/jundan/excel/event_excel_form.xls";
    });
  	
  	//엑셀 저장
    $("#btnExcelSave").click(function(){	
		$('#excelFrm').submit();
    });    

  	//리스트 삭제 버튼
    $(".btnListDelete").click(function(){	
		$('#eventPdList').empty();
    });
});

//저장버튼 클릭시
function fn_save(){
	var frm=document.getElementById("eventFrm");	
	if(!confirm("저장 하시겠습니까?")) return;
	frm.submit();
}

//삭제버큰 클릭시
function fn_delete(){
	if(!confirm("삭제 하시겠습니까?")) return;
	var frm=document.getElementById("delFrm");
	frm.submit();
}

//상품선택 팝업 호출
function fn_pd_popup(){
	window.open("${contextPath }/adm/productEventMgr/eventpopup", "_blank", "width=900,height=900"); 
}

//상품선택 팝업 리턴값
function fn_pd_event_return(SEL_PD_CODE, SEL_PD_NAME, SEL_PD_BARCD, SEL_PD_PRICE, SEL_PDDC_VAL){
	var frm=document.getElementById("eventFrm");
	
	var row_id		= "code_"+SEL_PD_CODE;		//상품코드
	var bar_id		= "barcd_"+SEL_PD_CODE;		//상품바코드
	var nm_id		= "name_"+SEL_PD_CODE;		//상품명
	var btn_id		= "btn_"+SEL_PD_CODE;			//삭제버튼
	var num_id		= "num_"+SEL_PD_CODE;			//정렬순서
	var price_id	= "price_"+SEL_PD_CODE;		//판매가
	var pddc_id 	= "pddc_"+SEL_PD_CODE;		//행사가
	var sale_id 	= "sale_"+SEL_PD_CODE;			//할인금액
	var gubn_id 	= "gubn_"+SEL_PD_CODE;		//할인구분
	var list_id 		= "list_"+SEL_PD_CODE;			//행
	
	//그룹내 상품 있는지 확인
	if($("#"+row_id).length > 0){
		alert("현재 그룹에 상품( "+SEL_PD_CODE+" | "+SEL_PD_NAME+" )이 포함되어 있습니다.");
		return;
	}
	
	var addNum = "";
	addNum = '<tr id="'+list_id+'">'
				+'<td style="padding-right:5px;padding-bottom:5px;width:60px;">'
					+'<input id="'+num_id+'" class="form-control" style="margin-bottom:5px" type="text" name="PD_SORT" value="'+($('input[name="PD_SORT"]').length+1)+'" title="정렬순서" readonly="readonly"/>'
				+'</td>'
				+'<td style="padding-right:5px;padding-bottom:5px;width:200px;">'
					+'<input id="'+row_id+'" class="form-control" style="margin-bottom:5px" type="text" name="PD_CODE" value="'+SEL_PD_CODE+'" title="상품코드" readonly="readonly"/>'
				+'</td>'
				+'<td style="padding-right:5px;padding-bottom:5px;width:200px;">'
					+'<input id="'+bar_id+'" class="form-control" style="margin-bottom:5px" type="text" name="PD_BARCD" value="'+SEL_PD_BARCD+'" title="상품바코드" readonly="readonly"/>'
				+'</td>'
				+'<td style="padding-right:5px;padding-bottom:5px;width:200px;">'
					+'<input id="'+nm_id+'"class="form-control" style="margin-bottom:5px" type="text" name="PD_NAME" value="'+SEL_PD_NAME+'" title="상품명" readonly="readonly"/>'
				+'</td>'
				+'<td style="padding-right:5px;padding-bottom:5px;width:140px;">'
					+'<input id="'+price_id+'"class="form-control input-sm number" style="margin-bottom:5px; text-align:right;" type="text" name="PD_PRICE" value="'+SEL_PD_PRICE+'" placeholder="판매가" title="판매가" readonly="readonly" />'	/* onchange="javascript:fn_calculPrice(\''+SEL_PD_CODE+'\')" */
				+'</td>'
				+'<td style="padding-right:5px;padding-bottom:5px;width:140px;">'
					+'<input id="'+pddc_id+'"class="form-control input-sm number" style="margin-bottom:5px; text-align:right;" type="text" name="PDDC_VAL" value="'+SEL_PDDC_VAL+'" placeholder="할인금액" title="할인금액" onchange="javascript:fn_calculPrice(\''+SEL_PD_CODE+'\')"/>'
				+'</td>'						
				+'<td style="padding-right:5px;padding-bottom:5px;width:120px;">'
 					+'<select id="'+gubn_id+'" name="PDDC_GUBN" class="form-control select2" >'
						+'<option value="PDDC_GUBN_01" >할인안함</option>'
						+'<option value="PDDC_GUBN_02" selected="selected">할인금액</option>'
					+'</select>' 
				+'</td>'	
				+'<td style="padding-right:5px;padding-bottom:5px;width:140px;">'
					+'<input id="'+sale_id+'"class="form-control input-sm number" style="margin-bottom:5px; text-align:right;" type="text" name="PD_SALE" value=0 placeholder="행사가" title="행사가" readonly="readonly"/>'
				+'</td>'		
				+'<td style="padding-right:5px;padding-bottom:5px;">'
					+'<a id="'+btn_id+'"style="margin-bottom:5px;height:33px;width:100%" class="btn btn-info pull-right" onclick="javascript:fn_pd_delete(\''+SEL_PD_CODE+'\');">삭제</a>'
				+'</td>'
			+ '</tr>';
			
			//console.log("1==="+typeof(SEL_PD_CODE));
	$("#eventPdList").append(addNum);
}

//상품추가 멀티리턴값
function fn_pd_multi_return(checkboxValues){
	var errormsg = "";
	
	for(var i=0; i<checkboxValues.length; i++){
		var pd_add = checkboxValues[i];
		//alert(pd_add);	
		var pdSplit = pd_add.split('@!');	
		var pd_code = pdSplit[0];
		var pd_name = pdSplit[1];
		var pd_barcode = pdSplit[2];
		var pd_price = pdSplit[3];
		var pddc_val = pdSplit[4];
		
		fn_pd_event_return(pd_code, pd_name, pd_barcode, pd_price, pddc_val);
	}  
}

// 상품삭제
function fn_pd_delete(pd_code){	
	//16자리 코드 가져올때 값이 다름 >> type문제, number인데 string으로 가져와서....
	//console.log("2==="+pd_code);
	//console.log("typeof==="+typeof(pd_code));
	
	$('#num_'+pd_code).remove();
	$('#code_'+pd_code).remove();
	$('#barcd_'+pd_code).remove();	
	$('#name_'+pd_code).remove();
	$('#price_'+pd_code).remove();
	$('#pddc_'+pd_code).remove();
	$('#gubn_'+pd_code).remove();	
	$('#sale_'+pd_code).remove();	
	$('#btn_'+pd_code).remove();
	$('#list_'+pd_code).remove();
}

// 할인금액 계산
function fn_calculPrice(pdCode){
	//alert(pdCode);
	var pdPrice = Number($('#price_'+pdCode).val().replace(/[^\d]+/g, ''));				//제품값
	var pddcVal = Number($('#pddc_'+pdCode).val().replace(/[^\d]+/g, ''));				//할인값	
	
	$('#sale_'+pdCode).val((pdPrice-pddcVal).toLocaleString());
}
</script>

<c:set var="strActionUrl" value="${contextPath }/adm/productEventMgr" />
<c:set var="strMethod" value="post" />

<section class="content-header">
	<h1>
		행사상품관리
		<small></small>
	</h1>
	<ol class="breadcrumb">
		<li><a href="#"><i class="fa fa-dashboard"></i> Level</a></li>
		<li class="active">Here</li>
	</ol>
</section>

<section class="content">
	<!-- Horizontal Form -->
	<spform:form name="eventFrm" id="eventFrm" method="${strMethod }" action="${strActionUrl }" class="form-horizontal">
		<!-- 내용 시작 -->
		<div class="box box-info">
			<div class="box-header with-border">
				<h3 class="box-title">내용</h3>
			</div>
			<!-- /.box-header -->
	
			<!-- box-body -->
			<div class="box-body">
				<div class="form-group">
					<div class="form-group">
						<label for="inputEmail3" class="col-sm-2 control-label">행사</label>
						<div class="col-sm-8">
							할인상품
						</div>
					</div><br/>
					<div class="form-group">
						<label for="inputEmail3" class="col-sm-2 control-label">등록 상품</label>
						<div class="col-sm-8">
							<button type="button" class="btn btn-primary btnAdd" onclick="javascript:fn_pd_popup();">
								<i class="fa fa-plus"></i> 상품 추가 
							</button>
							<button type="button" class="btn btn-primary btnListDelete">
								<i class="fa fa-file-excel-o"></i> 상품 리스트 삭제
							</button>
							<button type="button" class="btn btn-primary btnExcelUpload">
								<i class="fa fa-file-excel-o"></i> 상품 엑셀 업로드 
							</button>							
							<!-- <button type="button" class="btn btn-primary btnExcelDownload">
								<i class="fa fa-file-excel-o"></i> 상품 엑셀 다운로드
							</button> -->							
						</div>
					</div>
						
						<!-- 추가실패목록 START -->
						<c:if test = "${errlist ne null}">						
							<!-- 변경실패 -->
							<div class="box">
								<div class="box-header with-border">
									<h3 class="box-title">추가 실패 : <font color = "red"><b>${errlist.size()}</b></font> </h3>	
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
									<table class="table table-bordered">
										<thead>
											<tr>
												<th width="70px">순번</th>
												<th width="20%">바코드</th> 
												<th width="20%">상품명</th>
												<th width="150px">에러코드</th>
												<th>메시지</th>
											</tr>					
										</thead>
										<tbody>
										<c:forEach items="${errlist}" var="list" varStatus="loop">
											<tr>
												<td align="center">
													<label>${loop.count}</label>
												</td>
												<td align="left">
													<label>${list.PD_BARCD}</label>
												</td>
												<td align="left">
													<label>${list.PD_NAME}</label>
												</td>
												<td align="left">
													<label>${list.ERROR_CODE}</label>
												</td>
												<td align="left">
													<label>${list.ERROR_TEXT}</label>
												</td>
											</tr>
										</c:forEach>
										<c:if test="${errlist.size() == 0}">
											<tr>
												<td colspan="4"><label>업로드에 성공하였습니다.</label></td>
											</tr>
										</c:if>
										</tbody>
									</table>
								</div>
							</div>
						</c:if>
						<!-- 추가실패목록 END-->
						
						<!-- 추가상품목록 START -->
						<label for="inputEmail3" class="col-sm-2 control-label"></label>
						<table id="eventPdList">
							<c:forEach items="${tb_pdinfoxm.list }" var="list" varStatus="loop">
								<tr>
									<td style="padding-right:5px;padding-bottom:5px;width:60px;">
										<input id="num_${list.PD_CODE }" class="form-control"
											type="text" name="PD_SORT" value="${loop.count}" title="순번" />
									</td>
									<td style="padding-right:5px;padding-bottom:5px;width:200px;">
										<input id="code_${list.PD_CODE }" class="form-control"  
											type="text" name="PD_CODE" value="${list.PD_CODE }" title="상품코드" readonly="readonly"/>
									</td>
									<td style="padding-right:5px;padding-bottom:5px;width:200px;">
										<input id="barcd_${list.PD_CODE }" class="form-control" 
											type="text" name="PD_BARCD" value="${list.PD_BARCD }" title="상품바코드" readonly="readonly"/>
									</td>
									<td style="padding-right:5px;padding-bottom:5px;width:200px;">
										<input id="name_${list.PD_CODE }" class="form-control"
											type="text" name="PD_NAME" value="${list.PD_NAME }" title="상품명" readonly="readonly"/>
									</td>
									<td style="padding-right:5px;padding-bottom:5px;width:140px;">
										<input id="price_${list.PD_CODE }" class="form-control"
											type="text" name="PD_PRICE" value="<fmt:formatNumber value="${list.PD_PRICE }" pattern="#,###"/>" placeholder="판매가" title="판매가" readonly="readonly"/>
									</td>
									<td style="padding-right:5px;padding-bottom:5px;width:140px;">
										<input id="pddc_${list.PD_CODE }" class="form-control"
											type="text" name="PDDC_VAL" value="<fmt:formatNumber value="${list.PDDC_VAL }" pattern="#,###"/>" placeholder="할인가" title="할인가" readonly="readonly"/>
									</td>									
									<td style="padding-right:5px;padding-bottom:5px;width:120px;">
										<select id="gubn_${list.PD_CODE }" name="PDDC_GUBN" class="form-control select2" >								
											<option value="PDDC_GUBN_01" <c:if test='${list.PDDC_GUBN=="PDDC_GUBN_01" }'>selected="selected"</c:if> >할인안함</option>
											<option value="PDDC_GUBN_02" <c:if test='${list.PDDC_GUBN=="PDDC_GUBN_02" }'>selected="selected"</c:if> >할인금액</option>
										</select>
									</td>
									<td style="padding-right:5px;padding-bottom:5px;width:140px;">
										<input id="sale_${list.PD_CODE }" class="form-control"
											type="text" name="PD_SALE" value="<fmt:formatNumber value="" pattern="#,###"/>" placeholder="할인금액" title="할인금액" readonly="readonly"/>
									</td>
									<td style="padding-right:5px;padding-bottom:5px;">
										<a id="btn_${list.PD_CODE }" style="height:33px;width:100%" 
											onclick="javascript:fn_pd_delete('${list.PD_CODE}');" class="btn btn-info pull-right">삭제</a>
									</td>
								</tr>
							</c:forEach>						
						</table>
					</div>
				</div>
				<!-- /.box-body -->
			</div>
			<!-- 내용 끝 -->			
			</spform:form>
			
			<div class="box-footer" >
				<a href="${contextPath}/adm/productEventMgr" class="btn btn-default pull-right">리스트</a>
				<a onclick="javascript:fn_save();" class="btn btn-info pull-right">저장</a>
			</div>			
			<!-- /.box-footer -->
	<!-- /.box -->
</section>
 
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
				<!-- 행사상품정보 박스 -->
					<div class="box box-info">
						<!-- box-header -->
						<div class="box-header with-border">
							<h3 class="box-title">업로드</h3>
						</div>
						<!-- /.box-header -->
					
						<!-- box-body -->
						<spform:form name="excelFrm" id="excelFrm" method="post" action="${contextPath }/adm/productEventMgr/excelUpload" role="form" class="form-horizontal" enctype="multipart/form-data">						
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
							<div class="form-group">
								<label for="GUIDE" class="col-sm-2 control-label">이용안내</label>
								<div class="col-sm-10">
									<textarea name="CONTENT" class="form-control" rows="2" cols="100" 
									  readonly="readonly" style="color:red"> 할인금액 0으로 업로드시, 할인안함으로 적용됩니다. </textarea>
								</div>
							</div>
						</div>
						</spform:form>
						<!-- /.box-body -->
					</div>
				<!-- 행사상품정보 박스 -->
			</div>
			<div class="modal-footer">
				<button type="button" class="btn btn-default" data-dismiss="modal">닫기</button>
				<button type="button" class="btn btn-primary" id="btnExcelSave">업로드</button>
			</div>
		</div>
	</div>
</div>  
 
 <spform:form name="delFrm" id="delFrm" method="DELETE" action="${contextPath }/adm/productEventMgr/${tb_pdrcmdxm.GRP_CD }" class="form-horizontal">
 </spform:form>
 
 <spform:form name="delProductFrm" id="delProductFrm" method="GET" action="${contextPath }/adm/productEventMgr/deleteProduct">
 	<input type="hidden" name="PD_CODE" value=""/>
 	<input type="hidden" name="GRP_CD" value=""/>
 </spform:form>
