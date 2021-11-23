<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/layout/inc/taglib.jsp" %>

<!-- bootstrap datepicker -->
<link rel="stylesheet" href="${contextPath }/resources/adminlte/plugins/timepicker/bootstrap-timepicker.css">
<link rel="stylesheet" href="${contextPath }/resources/adminlte/plugins/timepicker/bootstrap-timepicker.min.css">

<!-- bootstrap datepicker -->
<script src="${contextPath }/resources/adminlte/plugins/timepicker/bootstrap-timepicker.js"></script>
<script src="${contextPath }/resources/adminlte/plugins/timepicker/bootstrap-timepicker.min.js"></script>

<section class="content-header">
	<h1>
		기업회원 관리	
		<small>회원 목록</small>
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
			<form class="form-horizontal">
				<div class="box-body">
					<div class="form-group">
						<label for="inputEmail3" class="col-sm-1 control-label">검색어</label>

						<div class="col-sm-2">
							<select name="schGbn" id="schGbn" class="form-control select2" style="width: 100%;">
								<option value="SUPR_NAME" selected="selected">업체명</option>
								<option value="MEMB_ID">회원ID</option>
								<option value="MEMB_NAME">회원명</option>
							</select>
						</div>
						<div class="col-sm-8">
							<input name="schTxt" type="text" class="form-control" placeholder="검색어 입력" value="${param.schTxt }">
						</div>
						<div class="col-sm-1">
							<button type="submit" class="btn btn-success pull-right">검색</button>
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

	<div class="box">
		<div class="box-header with-border">
			<h3 class="box-title">기업회원 목록(${totalCnt})</h3>
			<div class="box-tools">
				<!-- <a href="javascirpt:;" class="btn btn-sm btn-primary pull-right" data-toggle="modal" data-target="#myModal">등록</a> -->
				<a href="${contextPath}/adm/memberMgr/new?pageNum=${obj.pageNum }&rowCnt=${obj.rowCnt }${link}" class="btn btn-sm btn-primary pull-right">등록</a>
			</div>
		</div>
		<!-- /.box-header -->
		<div class="box-body">
			<table class="table table-bordered">
				<colgroup>
					<col style="with:10px" />
					<col />
					<col style="with:50px" />
				</colgroup>
				<thead>
					<tr>
						<th>번호</th>
						<th>업체명</th>
						<th>아이디</th>
						<th>성명</th>
						<th>등록일</th>
						<th>승인상태</th>
					</tr>
				</thead>
				<tbody>
				<c:forEach items="${obj.list }" var="ent" varStatus="loop">
					<tr>
						<td>${ent.RNUM }</td>
						<td>${ent.SUPR_NAME }</td>
						<td><%-- <a href="javascirpt:;" class="viewPopup" membId="${ent.MEMB_ID }">${ent.MEMB_ID }</a> --%>
							<c:choose>
							    <c:when test="${ent.SUPR_ID eq null || ent.SUPR_ID eq ''}">
							        <a href="javascirpt:;" class="editPopup" membId="${ent.MEMB_ID }">${ent.MEMB_ID }</a>
							    </c:when>
							    <c:otherwise>
							        <a href="javascirpt:;" class="viewPopup" membId="${ent.MEMB_ID }">${ent.MEMB_ID }</a>
							    </c:otherwise>
							</c:choose>
						</td>
						<td>${ent.MEMB_NAME }</td>
						<td>${ent.REG_DTM }</td>
						<td>${ent.SUPMEM_APST_NM }</td>
					</tr>
				</c:forEach>
				<c:if test="${obj.count == 0}">
					<tr>
						<td colspan="6">조회된 내용이 없습니다.</td>
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

<!-- Modal -->
<div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
	<div class="modal-dialog" role="document">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal" aria-label="Close">
					<span aria-hidden="true">&times;</span>
				</button>
				<h4 class="modal-title" id="myModalLabel">기업회원 등록/수정</h4>
			</div>
			<div class="modal-body">
            <form role="form">
            </form>
				<!-- 기업정보 박스 -->
					<div class="box box-info">
						<div class="box-header with-border">
							<h3 class="box-title">기본정보</h3>
						</div>
						<!-- /.box-header -->
					
						<!-- box-body -->
						<spform:form name="customerFrm" id="customerFrm" method="post" action="${contextPath}/adm/supplierMbrMgr" role="form" class="form-horizontal">
						<input type="hidden" name="SUPR_FLAG" id="SUPR_FLAG" value="I" />
						
						<div class="box-body">
							<div class="form-group">
								<label for="SUPR_ID" class="col-sm-2 control-label">업체명</label>
								<div class="col-sm-10">
									<select name="SUPR_ID" id="SUPR_ID" class="form-control" value="" placeholder="업체명" required="required" />
										<option value="">------------- 선택 ------------</option>
										<c:forEach items="${splist }" var="spList" varStatus="loop">
												<option value="${spList.SUPR_ID }" ${memberInfo.SUPR_ID eq spList.SUPR_ID ? 'selected=selected' : ''}>${spList.SUPR_NAME }</option>
										</c:forEach>
									</select>
								</div>
							</div>
							<div class="form-group">
								<label for="MEMB_ID" class="col-sm-2 control-label">아이디</label>
								<div class="col-sm-10">
									<input type="text" class="form-control" id="MEMB_ID" name="MEMB_ID" value="" placeholder="아이디" required="required" />
								</div>
							</div>
							<div class="form-group">
								<label for="MEMB_NAME" class="col-sm-2 control-label">이름</label>
								<div class="col-sm-10">
									<input type="text" class="form-control" id="MEMB_NAME" name="MEMB_NAME" value="" placeholder="이름" required="required" />
								</div>
							</div>			
							<div class="form-group">
								<label for="MEMB_PW" class="col-sm-2 control-label">비밀번호</label>
								<div class="col-sm-10">
										<input type="password" class="form-control" id="MEMB_PW" name="MEMB_PW" value="" placeholder="비밀번호" required="required" />
								</div>
							</div>
							<div class="form-group">
								<label for="MEMB_MAIL" class="col-sm-2 control-label">이메일</label>
								<div class="col-sm-10">
									<input type="email" class="form-control" id="MEMB_MAIL" name="MEMB_MAIL" value="" placeholder="email" required="required" />
								</div>
							</div>
							<div class="form-group">
								<label for="MEMB_CPON" class="col-sm-2 control-label">휴대전화</label>
								<div class="col-sm-10">
									<!-- <input type="text" class="form-control" name="MEMB_CPON" data-inputmask='"mask": "999-9999-9999"'> -->
									<input type="text" class="form-control" id="MEMB_CPON" name="MEMB_CPON" value="" placeholder="휴대폰번호" required="required" >
								</div>
							</div>
							<div class="form-group">
								<label for="MEMB_TELN" class="col-sm-2 control-label">승인상태</label>
								<div class="col-sm-10">
					                <jsp:include page="/common/comCodForm">
										<jsp:param name="COMM_CODE" value="SUPMEM_APST" />
										<jsp:param name="name" value="SUPMEM_APST" />
										<jsp:param name="value" value="" />
										<jsp:param name="type" value="select" />
										<jsp:param name="top" value="선택" />
									</jsp:include>
								</div>
							</div>
							<div class="form-group" id="divAPRF_RESN" id="divAPRF_RESN">
								<label for="APRF_RESN" class="col-sm-2 control-label">거부사유</label>
								<div class="col-sm-10">
									<input type="input" class="form-control" id="APRF_RESN" name="APRF_RESN" value="" />
								</div>
							</div>
							
							<!-- <div class="form-group" id="divAPRF_RESN" id="divAPRF_RESN">
								<label for="APRF_RESN" class="col-sm-2 control-label">개점/폐점 시간</label>
								<div class="col-sm-10">
									 <div class="input-group bootstrap-timepicker timepicker col-sm-5" style="float: inherit;">
							            <input id="timepickerStr" type="text" class="form-control input-small form-control" readonly="readonly"  value="02:45 PM">
							            <span class="input-group-addon"><i class="glyphicon glyphicon-time"></i></span>
							        </div>
							        <div class="col-sm-2" style="text-align:center">
										~
									</div>
							        <div class="input-group bootstrap-timepicker timepicker col-sm-5">
							            <input id="timeickerEnd" type="text" class="form-control input-small form-control" readonly="readonly">
							            <span class="input-group-addon"><i class="glyphicon glyphicon-time"></i></span>
							        </div>
								</div>
							</div> -->
							
						</div>
						</spform:form>
						<!-- /.box-body -->
					</div>
				<!-- 기업정보 박스 -->
			</div>
			<div class="modal-footer">
				<button type="button" class="btn btn-default" data-dismiss="modal">닫기</button>
				<button type="button" class="btn btn-primary btnSave"></button>
			</div>
		</div>
	</div>  	
</div>

<script type="text/javascript">
$(function() {
	//$('#timepickerStr').timepicker();
	//$('#timeickerEnd').timepicker();
	
	<c:if test="${!empty param.schGbn}">
		$('#schGbn').val('${param.schGbn}');
	</c:if>
	
	$('#myModal').on('show.bs.modal', function (event) {

		var modal = $(this);
		$("#SUPR_ID").val("");
	    $("#SUPR_ID").attr("disabled",false); //입력가능
        $("#MEMB_ID").val("");
        $("#MEMB_ID").attr("readonly",false).attr("disabled",false); //입력가능
        $("#MEMB_NAME").val("");
        $("#MEMB_MAIL").val("");
        $("#MEMB_CPON").val("");
        $("#SUPMEM_APST").val("");
        $("#APRF_RESN").val("");
        $("#divAPRF_RESN").hide();

        $("#SUPR_FLAG").val("I");
        $(".btnSave").text("저장");
	});
		
	// SUPR_ID 존재할때
	$(".viewPopup").click(function(){
		var strMembId = $(this).attr("membId");
		$.ajax({
		    type: 'get',
		    dataType: 'json',
		    url: '${contextPath }/adm/memberMgr/edit/'+strMembId+'.json',
		    success: function (data) {
			    var objMbr = data.memberInfo; 

		        $("#SUPR_ID").val(objMbr.supr_ID);
		        $("#SUPR_ID").attr("disabled", "disabled");
		        $("#MEMB_ID").val(objMbr.memb_ID);
		        $("#MEMB_ID").attr("readonly",true).attr("disabled",false); //입력불가
		        $("#MEMB_NAME").val(objMbr.memb_NAME);
		        $("#MEMB_MAIL").val(objMbr.memb_MAIL);
		        $("#MEMB_CPON").val(objMbr.memb_CPON);
		        $("#SUPMEM_APST").val(objMbr.supmem_APST);
		        $("#APRF_RESN").val(objMbr.aprf_RESN);
		        
		        $(".btnSave").text("수정");
		        $("#SUPR_FLAG").val("U");
		    },
		    error: function () {
		         console.log('error');
		    }
		});

		$('#myModal').modal('show');
	 });  

	// SUPR_ID 없을때
	$(".editPopup").click(function(){
		var strMembId = $(this).attr("membId");
		$.ajax({
		    type: 'get',
		    dataType: 'json',
		    url: '${contextPath }/adm/memberMgr/edit/'+strMembId+'.json',
		    success: function (data) {
			    var objMbr = data.memberInfo; 

		        $("#SUPR_ID").val(objMbr.supr_ID);
		        $("#SUPR_ID").attr("disabled", false);
		        $("#MEMB_ID").val(objMbr.memb_ID);
		        $("#MEMB_ID").attr("readonly",true).attr("disabled",false); //입력불가
		        $("#MEMB_NAME").val(objMbr.memb_NAME);
		        $("#MEMB_MAIL").val(objMbr.memb_MAIL);
		        $("#MEMB_CPON").val(objMbr.memb_CPON);
		        $("#SUPMEM_APST").val(objMbr.supmem_APST);
		        $("#APRF_RESN").val(objMbr.aprf_RESN);
		        
		        $(".btnSave").text("수정");
		        $("#SUPR_FLAG").val("U");
		    },
		    error: function () {
		         console.log('error');
		    }
		});
	
	 $(".btnSave").click(function() {
		$("#customerFrm").submit();
	 }); 
	 
});
</script>
