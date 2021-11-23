<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/layout/inc/taglib.jsp" %>

<c:set var="strActionUrl" value="${contextPath }/adm/menuMgr" />
<c:set var="strMethod" value="post" />
<c:if test="${ !empty tb_sysmnuxm.MENU_CD }"><!-- 수정시 사용 -->
	<c:set var="strActionUrl" value="${contextPath }/adm/menuMgr/${tb_sysmnuxm.MENU_CD }" />
	<c:set var="strMethod" value="put" />
</c:if>


<section class="content-header">
	<h1>
		메뉴관리
		<small></small>
	</h1>
	<ol class="breadcrumb">
		<li><a href="#"><i class="fa fa-dashboard"></i> Level</a></li>
		<li class="active">Here</li>
	</ol>
</section>

<section class="content">
	<!-- Horizontal Form -->
			
			<spform:form name="orderFrm" id="orderFrm" method="${strMethod }" action="${strActionUrl }" class="form-horizontal">
			
			<!-- 내용 시작 -->
			<div class="box box-info">
			<div class="box-header with-border">
				<h3 class="box-title">내용</h3>
			</div>
			<!-- /.box-header -->
	
				<!-- box-body -->
				<div class="box-body">
					<div class="form-group">
						<label for="inputEmail3" class="col-sm-2 control-label">메뉴코드</label>
						<div class="col-sm-10">
							<c:choose>
							<c:when test="${ !empty tb_sysmnuxm.MENU_CD }">
								${tb_sysmnuxm.MENU_CD }
								<input type="hidden" id="MENU_CD" name="MENU_CD" value="${tb_sysmnuxm.MENU_CD }" />
								<input type="hidden" id="MENU_CD_CHK" name="MENU_CD_CHK" value="Y" />
							</c:when>
							<c:otherwise>
								<input type="text" id="MENU_CD" name="MENU_CD" value="${tb_sysmnuxm.MENU_CD }" onchange="javascript:fn_change();"/>
								<a onclick="javascript:fn_menu_cd_chk();" class="btn btn-info pull-right">중복검사</a>
								<input type="hidden" id="MENU_CD_CHK" name="MENU_CD_CHK" value="N" />
							</c:otherwise>
							</c:choose>
						</div>
					</div><br/>
					<div class="form-group">
						<label for="inputEmail3" class="col-sm-2 control-label">메뉴명</label>
						<div class="col-sm-10"><input type="text" id="MENU_NAME" name="MENU_NAME" value="${tb_sysmnuxm.MENU_NAME }" /></div>
					</div><br/>
					<div class="form-group">
						<label for="inputEmail3" class="col-sm-2 control-label">메뉴URL</label>
						<div class="col-sm-10"><input type="text" id="MENU_URL" name="MENU_URL" value="${tb_sysmnuxm.MENU_URL }" /></div>
					</div><br/>
					<div class="form-group">
						<label for="inputEmail3" class="col-sm-2 control-label">상위메뉴코드</label>
						<div class="col-sm-10">
							<input type="text" id="UPPER_MENU_NAME" name="UPPER_MENU_NAME" value="${tb_sysmnuxm.UPPER_MENU_NAME }" />
							<input type="text" id="UPPER_MENU_CD" name="UPPER_MENU_CD" value="${tb_sysmnuxm.UPPER_MENU_CD }" />
							<a onclick="javascript:fn_clean();" class="btn btn-info pull-right">상위메뉴삭제</a>
							<a onclick="javascript:fn_menu_popup();" class="btn btn-info pull-right">상위메뉴선택</a>
						</div>
					</div><br/>
					<div class="form-group">
						<label for="inputEmail3" class="col-sm-2 control-label">정렬순서</label>
						<div class="col-sm-10"><input type="text" id="SORT_ORDR" name="SORT_ORDR" value="${tb_sysmnuxm.SORT_ORDR }" /></div>
					</div><br/>
					<div class="form-group">
						<label for="inputEmail3" class="col-sm-2 control-label">메뉴구분</label>
						<%-- <div class="col-sm-10"><input type="text" id="MENU_GUBN" name="MENU_GUBN" value="${tb_sysmnuxm.MENU_GUBN }" /></div> --%>
						<div class="col-sm-10"><input type="text" id="MENU_GUBN" name="MENU_GUBN" value="${tb_sysmnuxm.MENU_GUBN == null ? 'MENU_SE_01':tb_sysmnuxm.MENU_GUBN}" /></div>
					</div><br/>
					<div class="form-group">
						<label for="inputEmail3" class="col-sm-2 control-label">출력여부</label>
						<div class="col-sm-10">
							<select name="OUTPT_FG" id="OUTPT_FG" class="form-control select2" style="width: 100%;">
								<option value="Y" ${tb_sysmnuxm.OUTPT_FG == 'Y' ? 'selected=selected':''}>출력</option>
								<option value="N" ${tb_sysmnuxm.OUTPT_FG == 'N' ? 'selected=selected':''}>미출력</option>
							</select>
							<%-- <input type="text" id="OUTPT_FG" name="OUTPT_FG" value="${tb_sysmnuxm.OUTPT_FG }" /> --%>
						</div>
					</div><br/>
					<div class="form-group">
						<label for="inputEmail3" class="col-sm-2 control-label">메뉴CSS</label>
						<div class="col-sm-10"><input type="text" id="MENU_CSS" name="MENU_CSS" value="${tb_sysmnuxm.MENU_CSS }" /></div>
					</div>
				</div>
				<!-- /.box-body -->
			</div>
			<!-- 내용 끝 -->
			
			</spform:form>
			
			<div class="box-footer" >
				<a href="${contextPath}/adm/menuMgr" class="btn btn-default pull-right">리스트</a>
				<a onclick="javascript:fn_save();" class="btn btn-info pull-right">저장</a>
				<c:if test="${ !empty tb_sysmnuxm.MENU_CD }"><!-- 수정시 사용 -->
				<a onclick="javascript:fn_delete();" class="btn btn-info pull-right">삭제</a>
				</c:if>
			</div>
			
			<!-- /.box-footer -->
	<!-- /.box -->
</section>
 
 <spform:form name="delFrm" id="delFrm" method="DELETE" action="${contextPath }/adm/menuMgr/${tb_sysmnuxm.MENU_CD }" class="form-horizontal">
 </spform:form>
<script type="text/javascript">
//등록시 메뉴코드 중복 검사
function fn_menu_cd_chk(){
	var frm=document.getElementById("orderFrm");
	$.ajax({
	    url: "${contextPath }/adm/menuMgr/chk",
	    type: 'post',
	    data: 'MENU_CD='+ frm.MENU_CD.value,
	    success: function(data){                               
	    	 if(data=="success"){
	    		 alert("이미 존재하는 메뉴코드 입니다.");
	    		 frm.MENU_CD.focus();
	    	 }else{
	    		 alert("메뉴코드를 사용해도 좋습니다.");
	    		 frm.MENU_CD_CHK.value="Y";
	    	 }
	    },
	    error: function(e){
	       alert("에러:");
	    }
	});
}

//메뉴코드 수정시 중복검사 안한걸로 플레그 변경
function fn_change(){
	var frm=document.getElementById("orderFrm");
	frm.MENU_CD_CHK.value="N";
}
//저장버튼 클릭시
function fn_save(){
	var frm=document.getElementById("orderFrm");
	if(frm.MENU_CD.value==""){
		alert("메뉴코드를 입력하세요");
		frm.MENU_CD.focus();
		return;
	}
	if(frm.MENU_CD_CHK.value=="N"){
		alert("메뉴코드 중복검사를 먼저 하시기 바랍니다.");
		return;
	}
	if(frm.MENU_NAME.value==""){
		alert("메뉴명을 입력하세요");
		frm.MENU_NAME.focus();
		return;
	}
	if(frm.UPPER_MENU_NAME.value==""){
		alert("상위메뉴코드를 선택하세요");
		frm.UPPER_MENU_NAME.focus();
		return;
	}
	if(!confirm("저장 하시겠습니까?")) return;
	frm.submit();
}
//삭제버큰 클릭시
function fn_delete(){
	if(!confirm("삭제 하시겠습니까?")) return;
	var frm=document.getElementById("delFrm");
	frm.submit();
}
//상위메뉴선택 팝업 호출
function fn_menu_popup(){
	window.open("${contextPath }/adm/menuMgr/popup", "_blank", "width=800,height=800"); 
}
//상위메뉴선택 리턴값
function fn_menu_popup_return(UPPER_MENU_CD,UPPER_MENU_NAME){
	var frm=document.getElementById("orderFrm");
	frm.UPPER_MENU_CD.value=UPPER_MENU_CD;
	frm.UPPER_MENU_NAME.value=UPPER_MENU_NAME;
}

//상위메뉴삭제
function fn_clean(){
	var frm=document.getElementById("orderFrm");
	frm.UPPER_MENU_NAME.value="";
	frm.UPPER_MENU_CD.value="";
}

</script>