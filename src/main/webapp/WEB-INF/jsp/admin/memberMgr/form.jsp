<%@ page trimDirectiveWhitespaces="true"%>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/layout/inc/taglib.jsp" %>

<section class="content-header">
	<h1>
		회원 관리
		<small>회원정보 등록</small>
	</h1>
	<ol class="breadcrumb">
		<li><a href="#"><i class="fa fa-dashboard"></i> Level</a></li>
		<li class="active">Here</li>
	</ol>
</section>

<section class="content">
	<!-- Horizontal Form -->
	<!-- form start -->
	<spform:form name="customerFrm" id="customerFrm" method="post" action="${contextPath }/adm/memberMgr" class="form-horizontal" onsubmit="return validatrion_submit(this);" >
		<input type="hidden" id="pageNum" name="pageNum" value="${param.pageNum }" /> 
		<input type="hidden" id="rowCnt" name="rowCnt" value="${param.rowCnt }" /> 
		<input type="hidden" id="schGbn" name="schGbn" value="${param.schGbn }" />
		<input type="hidden" id="schTxt" name="schTxt" value="${param.schTxt }" /> 
		<%-- <input type="hidden" name="MEMB_GUBN" value="${memberInfo.MEMB_GUBN }" /> --%>
		<c:if test="${ !empty memberInfo.MEMB_ID }">
			<input type="hidden" id="MEMB_CHK_YN" name="MEMB_CHK_YN" value="Y"/>
		</c:if>
		<c:if test="${ empty memberInfo.MEMB_ID }">
			<input type="hidden" id="MEMB_CHK_YN" name="MEMB_CHK_YN" value="N"/>
		</c:if>		
   		<input type="hidden" id="COM_CHK_YN" name="COM_CHK_YN" value="Y"/>   		
		
		<div class="box box-info">
			<div class="box-header with-border">
				<h3 class="box-title">개인정보 - 필수 입력</h3>
			</div>
			<!-- /.box-header -->
	
				<!-- box-body -->
				<div class="box-body">
					<div class="form-group">
						<label for="MEMB_GUBN" class="col-sm-2 control-label">회원구분</label>
						<div class="col-sm-10">						
			                <jsp:include page="/common/comCodForm">
								<jsp:param name="COMM_CODE" value="MEMB_GUBN" />
								<jsp:param name="name" value="MEMB_GUBN" />
								<jsp:param name="value" value="${ memberInfo.MEMB_GUBN }" />
								<jsp:param name="type" value="radio" />
								<jsp:param name="top" value="선택" />
							</jsp:include>
						</div>
					</div>
					<div class="form-group">
						<label for="MEMB_ID" class="col-sm-2 control-label">아이디</label>
						<div class="col-sm-4">
							<c:if test="${ !empty memberInfo.MEMB_ID }">
									${memberInfo.MEMB_ID }
									<input type="hidden" name="MEMB_ID" value="${memberInfo.MEMB_ID }" />
							</c:if>
							<c:if test="${ empty memberInfo.MEMB_ID }">
								<input type="text" class="form-control" id="MEMB_ID" name="MEMB_ID" value="" placeholder="아이디" required="required" onblur="javascript:fn_idChk();" />
							</c:if>
						</div>
						<div class="col-sm-4">
							<div id="ID_CHK_MSG"></div>
						</div>	
					</div>
					<div class="form-group">
						<label for="MEMB_NAME" class="col-sm-2 control-label">이름</label>
						<div class="col-sm-10">
							<input type="text" class="form-control" id="MEMB_NAME" name="MEMB_NAME" value="${memberInfo.MEMB_NAME }" placeholder="이름" required="required" />
						</div>
					</div>
					<c:if test="${ empty memberInfo.MEMB_ID }">					
					<div class="form-group">
						<label for="MEMB_PW" class="col-sm-2 control-label">비밀번호</label>
						<div class="col-sm-4">
							<input type="password" class="form-control" id="MEMB_PW" name="MEMB_PW" value="" placeholder="비밀번호" required="required" 
								onblur="javascript:fnCheckPassword($('#MEMB_ID').val(),$('#MEMB_PW').val());" />
						</div>
						<label for="MEMB_PW_CON" class="col-sm-2 control-label">비밀번호 확인</label>
						<div class="col-sm-4">
							<input type="password" class="form-control" id="MEMB_PW_CON" name="MEMB_PW_CON" value="" placeholder="비밀번호 확인" required="required" 
								onchange="javascript:fnCfmPassword($('#MEMB_PW').val());" />
						</div>
					</div>
					</c:if>
					<c:if test="${ !empty memberInfo.MEMB_ID }">					
					<div class="form-group">
						<label for="MEMB_PW" class="col-sm-2 control-label">비밀번호</label>
						<div class="col-sm-4">
							<input type="password" class="form-control" id="MEMB_PW" name="MEMB_PW" value="" placeholder="비밀번호 변경시 입력하세요." />
						</div>
					</div>
					</c:if>
					<div class="form-group">
						<label for="MEMB_SEX" class="col-sm-2 control-label">성별</label>
						<div class="col-sm-10">
			                <jsp:include page="/common/comCodForm">
								<jsp:param name="COMM_CODE" value="MEMB_SEX" />
								<jsp:param name="name" value="MEMB_SEX" />
								<jsp:param name="value" value="${ memberInfo.MEMB_SEX }" />
								<jsp:param name="type" value="radio" />
								<jsp:param name="top" value="선택" />
							</jsp:include>
						</div>
					</div>
					<div class="form-group">
						<label for="MEMB_BTDY" class="col-sm-2 control-label">생년월일</label>
						<div class="col-sm-4">
							<div class="input-group date">
								<div class="input-group-addon">
									<i class="fa fa-calendar"></i>
								</div>
								<input type="text" name="MEMB_BTDY" class="form-control pull-right" id="datepicker" value="${memberInfo.MEMB_BTDY }" required="required" >
							</div>
						</div>
						<div class="col-sm-6">
			                <jsp:include page="/common/comCodForm">
								<jsp:param name="COMM_CODE" value="SLCAL_GUBN" />
								<jsp:param name="name" value="SLCAL_GUBN" />
								<jsp:param name="value" value="${ memberInfo.SLCAL_GUBN }" />
								<jsp:param name="type" value="radio" />
								<jsp:param name="top" value="선택" />
							</jsp:include>
						</div>
					</div>
					<div class="form-group">
						<label for="MEMB_MAIL" class="col-sm-2 control-label">이메일</label>
						<div class="col-sm-10">
							<input type="email" class="form-control" id="MEMB_MAIL" name="MEMB_MAIL" value="${memberInfo.MEMB_MAIL }" placeholder="email" required="required"
								onchange="chk_email($('#MEMB_MAIL').val())" />
						</div>
					</div>
					<div class="form-group">
						<label for="MEMB_PN" class="col-sm-2 control-label">주소</label>
						<div class="col-sm-2">
							<input type="text" class="form-control" id="MEMB_PN" name="MEMB_PN" value="${memberInfo.MEMB_PN }" placeholder="우편번호" required="required" />
						</div>
					</div>
					<div class="form-group">
						<label for="MEMB_BADR" class="col-sm-2 control-label"></label>
						<div class="col-sm-10">
							<input type="text" class="form-control" id="MEMB_BADR" name="MEMB_BADR" value="${memberInfo.MEMB_BADR }" placeholder="기본주소" required="required" />
						</div>
					</div>
					<div class="form-group">
						<label for="MEMB_DADR" class="col-sm-2 control-label"></label>
						<div class="col-sm-10">
							<input type="text" class="form-control" id="MEMB_DADR" name="MEMB_DADR" value="${memberInfo.MEMB_DADR }" placeholder="상세주소"  required="required" >
						</div>
					</div>
					<div class="form-group">
						<label for="MEMB_CPON" class="col-sm-2 control-label">휴대전화</label>
						<div class="col-sm-10">
							<input type="text" class="form-control" id="MEMB_CPON" name="MEMB_CPON" data-inputmask='"mask": "999-9999-9999"' data-mask value="${memberInfo.MEMB_CPON }" required="required" >
						</div>
					</div>
					<div class="form-group">
						<label for="MEMB_TELN" class="col-sm-2 control-label">전화번호</label>
						<div class="col-sm-10">
							<input type="text" class="form-control" name="MEMB_TELN" data-inputmask='"mask": "999-9999-9999"' data-mask value="${memberInfo.MEMB_TELN }">
						</div>
					</div>
					<div class="form-group">
						<label for="MEMB_TELN" class="col-sm-2 control-label">은행명</label>
						<div class="col-sm-4">
							<input type="text" class="form-control" name="BANK_NAME" value="${memberInfo.BANK_NAME }">
						</div>
						<label for="MEMB_TELN" class="col-sm-2 control-label">통장번호</label>
						<div class="col-sm-4">
							<input type="text" class="form-control" name="BANK_BUNB" value="${memberInfo.BANK_BUNB }"  placeholder="-를 제외하고 적어주세요" >
						</div>
					</div>
					<div class="form-group">
						<label for="DLVY_CPON" class="col-sm-2 control-label" >배송쿠폰</label>
						<div class="col-sm-4">
							<button type="button" class="btn btn-primary btn-sm btnQty">-</button>
							<input type="text" class="input-sm number" name="DLVY_CPON" value="<fmt:formatNumber value="${ memberInfo.DLVY_CPON }" pattern="###"/>" id="DLVY_CPON" style="width:60px;text-align:center;color:red;" maxlength="3">
							<button type="button" class="btn btn-primary btn-sm btnQty">+</button>
						</div>
					</div>
					<div class="form-group">						
						<label for="MEMB_STAT" class="col-sm-2 control-label">회원상태</label>
						<c:if test="${ !empty memberInfo.MEMB_ID && memberInfo.SCSS_YN == 'N' }">
							<div class="col-sm-4">
								<label class="radio-inline">
			                  		<input type="radio" name="STOP_YN" value="N" required ${ memberInfo.STOP_YN == 'N' ? 'checked' : '' } onclick="fn_memb_state()"/>활동
				               	</label>
				               	<label class="radio-inline">
				                  	<input type="radio" name="STOP_YN" value="Y" required ${ memberInfo.STOP_YN == 'Y' ? 'checked' : '' } onclick="fn_memb_state()">일시중지
				               	</label>			               	
			               	</div>
		               	</c:if>
		               	<c:if test="${ !empty memberInfo.MEMB_ID && memberInfo.SCSS_YN == 'Y' }">		               		
		               		<div class="col-sm-4">
		               			<label class="radio-inline">
		                  			<input type="radio" name="SCSS_YN" value="N" required ${ memberInfo.SCSS_YN == 'N' ? 'checked' : '' } onclick="fn_memb_state()"/>활동
			               		</label>
				               	<label class="radio-inline">
				                  	<input type="radio" name="SCSS_YN" value="Y" required ${ memberInfo.SCSS_YN == 'Y' ? 'checked' : '' } onclick="fn_memb_state()">탈퇴
				               	</label>
				            </div>
			            </c:if>
		               	<label for="MEMB_STAT" class="col-sm-2 control-label">활동중지일자</label>
		               	<div class="col-sm-4" id="STDT_SHOW">
							<div class="input-group date">
								<div class="input-group-addon">
									<i class="fa fa-calendar"></i>
								</div>
								<input type="text" id="STOP_DTM" name="STOP_DTM" class="form-control pull-right" value="${memberInfo.STOP_DTM }" required="required" readonly>
							</div>
						</div>
					</div>
				</div>
				<!-- /.box-body -->
			</div>
			<div class="box box-info">
				<div class="box-header with-border">
					<h3 class="box-title">회사정보 - 사업자회원입력</h3>
				</div>
				
				<!-- box-body -->
				<div class="box-body">
					<div class="form-group">
						<label for="COM_NAME" class="col-sm-2 control-label">상호명</label>
						<div class="col-sm-4">
							<input type="text" class="form-control" id="COM_NAME" name="COM_NAME" value="${memberInfo.COM_NAME }" />
						</div>		
					</div>
					<div class="form-group">
						<label for="COM_BUNB" class="col-sm-2 control-label">사업자등록번호</label>
						<div class="col-sm-4">
							<input type="text" class="form-control" id="COM_BUNB" name="COM_BUNB" value="${memberInfo.COM_BUNB }"
							 onblur="javascript:fn_bunbChk();" onkeydown="return onlyNumber(this)" placeholder="-를 제외하고 적어주세요" />
						</div>
						<div class="col-sm-4">
                     		<div id="COM_BUNB_CHK_MSG"></div>
                     	</div>
                  	 </div>					
					<div class="form-group">
						<label for="COM_PN" class="col-sm-2 control-label">주소</label>
						<div class="col-sm-2">
							<input type="text" class="form-control" id="COM_PN" name="COM_PN" value="${memberInfo.COM_PN }" placeholder="우편번호" />
						</div>
						<input type="checkbox" id="sameChk" onclick="same_chk()"/>개인정보와 동일
					</div>
					<div class="form-group">
						<label for="COM_BADR" class="col-sm-2 control-label"></label>
						<div class="col-sm-10">
							<input type="text" class="form-control" id="COM_BADR" name="COM_BADR" value="${memberInfo.COM_BADR }" placeholder="기본주소" />
						</div>
					</div>
					<div class="form-group">
						<label for="COM_DADR" class="col-sm-2 control-label"></label>
						<div class="col-sm-10">
							<input type="text" class="form-control" id="COM_DADR" name="COM_DADR" value="${memberInfo.COM_DADR }" placeholder="상세주소">
						</div>
					</div>
					<div class="form-group">
						<label for="COM_TELN" class="col-sm-2 control-label">전화번호</label>
						<div class="col-sm-10">
							<input type="text" class="form-control" name="COM_TELN" data-inputmask='"mask": "999-[9]999-9999"' data-mask value="${memberInfo.COM_TELN }">
						</div>
					</div>
					<div class="form-group">
						<label for="COM_OPEN" class="col-sm-2 control-label">매장 개점시간</label>
						<div class="col-sm-10">
							<input type="text" class="form-control" name="COM_OPEN" data-inputmask='"mask": "99:99"' data-mask value="${memberInfo.COM_OPEN }">
						</div>
					</div>
					<div class="form-group">
						<label for="COM_CLOSE" class="col-sm-2 control-label">매장 폐점시간</label>
						<div class="col-sm-10">
							<input type="text" class="form-control" name="COM_CLOSE" data-inputmask='"mask": "99:99"' data-mask value="${memberInfo.COM_CLOSE }">
						</div>
					</div>
					<div class="form-group">
						<label for="KEEP_LOCATION" class="col-sm-2 control-label">상품보관장소</label>
						<div class="col-sm-10">
							<input type="text" class="form-control" name="KEEP_LOCATION" value="${memberInfo.KEEP_LOCATION }">
						</div>
					</div>
					<div class="form-group">
						<label for="CAR_NUM" class="col-sm-2 control-label">차량번호</label>
						<div class="col-sm-5">
							<p class="form-control-static">
				                <jsp:include page="/common/comCodForm">
									<jsp:param name="COMM_CODE" value="CAR_NUM" />
									<jsp:param name="name" value="CAR_NUM" />
									<jsp:param name="value" value="${ memberInfo.CAR_NUM }" />
									<jsp:param name="type" value="select" />
									<jsp:param name="top" value="선택" />
								</jsp:include>
							</p>
						</div>
					</div>
					<div class="form-group">
						<label for="ADM_REF" class="col-sm-2 control-label">관리자 참조설명기능</label>
						<div class="col-sm-10">
							<%-- <input type="text" class="form-control" name="ADM_REF" value="${memberInfo.ADM_REF }"> --%>
							<textarea name="ADM_REF" class="form-control" rows="6" cols="100">${memberInfo.ADM_REF }</textarea>
						</div>
					</div>
					
					<div class="form-group">
						<label for="ADM_REF" class="col-sm-2 control-label">지역코드</label>
						<div class="col-sm-5">
							<select name="AREA_GUBN" class="form-control select2" style="width: 100%;">
								<option value="" <c:if test='${memberInfo.AREA_GUBN ==null || memberInfo.AREA_GUBN ==""  }'>selected="selected"</c:if>>선택</option>
								
								<c:forEach items="${areaList }" var="area" varStatus="sta">
									<option value="${area.COMD_CODE }" <c:if test='${memberInfo.AREA_GUBN ==area.COMD_CODE }'> selected="selected"</c:if>>
										${area.COMDCD_NAME }
									</option>
								</c:forEach>
							</select>
						</div>
					</div>
					<div class="form-group">
						<label for="ORG_CT" class="col-md-2 control-label">세금계산서 발행여부</label>
						<div class="col-md-2">
							<p class="form-control-static">
								<label class="check" style="margin-right:10px">
									<input type="radio" class="iradio" name="TAXCAL_YN" value="Y" 
										<c:if test='${memberInfo.TAXCAL_YN=="Y" }'>checked</c:if>
									/>
									발행
								</label>
								<label class="check" style="margin-right:10px">
									<input type="radio" class="iradio" name="TAXCAL_YN" value="N" 
										<c:if test='${memberInfo.TAXCAL_YN=="N" || memberInfo.TAXCAL_YN==null }'>checked</c:if>
									/>
									미발행
								</label>
							</p>
						</div>					
					</div>					
				</div>
				<!-- /.box-body -->			
			</div>

			<!-- this row will not appear when printing -->
			<div class="row">
				<div class="col-xs-12">
					<a href="${contextPath}/adm/memberScssMgr?&pageNum=${param.pageNum }&rowCnt=${param.rowCnt }${link}" class="btn btn-success pull-right"><i class="fa fa-list"></i> 목록</a>
					<c:if test="${ !empty memberInfo.MEMB_ID && memberInfo.SCSS_YN != 'Y' }">						
						<button type="submit" class="btn btn-primary pull-right" style="margin-right: 5px;">
							<i class="fa fa-save"></i> 저장
						</button>
						<button type="button" class="btn btn-danger pull-right btnSec" style="margin-right: 5px;">
							<i class="fa fa-sign-out"></i> 탈퇴
						</button>
					</c:if>
					<c:if test="${ !empty memberInfo.MEMB_ID && memberInfo.SCSS_YN == 'Y' }">						
						<!-- <button type="submit" class="btn btn-primary pull-right" style="margin-right: 5px;">
							<i class="fa fa-save"></i> 저장
						</button> -->
						<!-- <button type="button" class="btn btn-danger pull-right btnDel" style="margin-right: 5px;">
							<i class="fa fa-sign-out"></i> 영구삭제
						</button> -->
					</c:if>
				</div>
			</div>
	</spform:form>
	<!-- /.box -->
</section>

<script>
	$(function() {		
		//회원상태		
		<c:if test = "{!empty memberInfo.STOP_YN || memberInfo.STOP_YN != ''}" >	//if($('input:radio[name=STOP_YN]').is(':checked') == false){		
			/* alert("탔다!"+$('input:radio[name="STOP_YN"]').val()); */
			$('input:radio[name=STOP_YN]:input[value="N"]').prop("checked", true);	/* attr()과 prop() 차이 */
			/* alert($('input:radio[name="STOP_YN"]').val());		 */
		</c:if>
		
		//fn_memb_state();
		
		//개점, 폐점
		<c:if test="${!empty memberInfo.COM_OPEN }">
			$('#COM_OPEN_HH').val('${memberInfo.COM_OPEN_HH }');
			$('#COM_OPEN_MM').val('${memberInfo.COM_OPEN_MM }');
		</c:if>
		<c:if test="${!empty memberInfo.COM_CLOSE }">
			$('#COM_CLOSE_HH').val('${memberInfo.COM_CLOSE_HH }');
			$('#COM_CLOSE_MM').val('${memberInfo.COM_CLOSE_MM }');
		</c:if>
		
		//Date picker
		$('#datepicker').datepicker({
			autoclose: true,
			format: 'yyyymmdd'
		});
		$('#datepicker_stdt').datepicker({
			autoclose: true,
			format: 'yyyymmdd'
		});
		
	    //Money Euro
	    $("[data-mask]").inputmask();
	    
	    $('.btnSec').click(function(){
	    	if(!confirm("탈퇴하시면 7일이내 재가입이 불가능합니다. 탈퇴하시겠습니까?")) return;
			$("#customerFrm").attr("action","${contextPath }/adm/memberMgr/delete");
			$("#customerFrm").submit();
	    });
	    

	    $('.btnDel').click(function(){
	    	if(!confirm("영구삭제하시면 복구가 불가능합니다. 진행하시겠습니까?")) return;
			$("#customerFrm").attr("action","${contextPath }/adm/memberMgr/delete2");
			$("#customerFrm").submit();
	    });
	});
		
	
	// ===2019.02.26 chjw===
	
	// 비밀번호 체크
	function fnCheckPassword(uid, upw){
	    if(!/^[a-zA-Z0-9]{5,20}$/.test(upw)&&!(upw=="")){ 
	        alert('비밀번호는 숫자와 영문자 조합으로 5~12자리를 사용해야 합니다.'); 
	        $('#MEMB_PW').val('');
	        $('#MEMB_PW').focus();
	       
	        return false;
	    }
	  
	    var chk_num = upw.search(/[0-9]/g); 
	    var chk_eng = upw.search(/[a-z]/ig);

	    if((chk_num < 0 || chk_eng < 0) &&!(upw=="")){ 
	        alert('비밀번호는 숫자와 영문자를 혼용하여야 합니다.'); 
	        $('#MEMB_PW').val('');
	        $('#MEMB_PW').focus();
	        
	        return false;
	    }	    
	    if(/(\w)\1\1\1/.test(upw) &&!(upw=="")){
	        alert('비밀번호에 같은 문자를 4번 이상 사용하실 수 없습니다.'); 
	        $('#MEMB_PW').val('');
	        $('#MEMB_PW').focus();
	        
	        return false;
	    }
	    if(upw.search(uid)>-1 &&!(upw=="")){
	        alert('ID가 포함된 비밀번호는 사용하실 수 없습니다.'); 
	        $('#MEMB_PW').val('');
	        $('#MEMB_PW').focus();
	        
	        return false;
	    }

	    return true;
	}
	
	// 비밀번호 확인_CHJW
	function fnCfmPassword(upw){
		if (upw != $('#MEMB_PW_CON').val() && $('#MEMB_PW_CON').val() != ''){
			alert('비밀번호가 일치하지 않습니다.'); 
	        $('#MEMB_PW_CON').val('');
	        
	        //focus시 chrome에서 무한루프 해결
	        setTimeout(function(){
	        	$('#MEMB_PW_CON').focus();
	        }, 10)
	        return false;
		}		
		return true;
	}
	
	// email형식 체크
	function chk_email(strEmail) {
	   var em = strEmail; //이메일의 값은 단순히 문자이다 이것을 객체화시켜서 비교한다.

	   //^는 반드시 이런형식으로 시작하라 $는 반드시 요런형식으로 종료
	   // {3,20} 3~20글자만쓸수있다..
	   var epattern = new RegExp("^([a-zA-Z0-9\-\_]{3,20})" + "@" + "([a-zA-Z0-9\-\_]{3,20})" + "." + "([a-zA-Z0-9\-\_\.]{3,5})$");
	   
	   if (!epattern.test(em)) {
		   alert("e-mail 형식이 아닙니다.");
		   
		   //focus시 chrome에서 무한루프 해결
	       setTimeout(function(){
	       		$("#MEMB_MAIL").focus();
	       }, 10)	      
	      
	      return false;
	   }
	   return true;
	}
	
	//아이디 중복확인	
	function fn_idChk(){
		
	   var strMembId = $("#MEMB_ID").val();
	   $("#MEMB_ID_TMP").val(strMembId);
	   
	   if(strMembId == ""){
	      alert("아이디를 입력해 주세요");
	      return false;
	   }
	   
	   $.ajax({
	      type: "POST",
	      url: "${contextPath}/idChk",
	      data: $("#idChkFrm").serialize(),
	      success: function (data) {
	
	         var strMembIdTrim = strMembId.trim();
	         var re = /\s/g;
	         var special_pattern = /[,.`~!@#$%^&*|\\\'\";:\/?]/gi;
	         
	      	 if(strMembId != strMembIdTrim || re.test(strMembId)){
	            $('#ID_CHK_MSG').html("<span><font color='red'>아이디에 공백이 들어갈 수 없습니다</font></span>");
	            $('#MEMB_CHK_YN').val("N");
	         } else if(special_pattern.test(strMembId)) {
	        	 $('#ID_CHK_MSG').html("<span><font color='red'>아이디에 특수문자가 들어갈 수 없습니다</font></span>");
	             $('#MEMB_CHK_YN').val("N");
	         }else{
	            // 아이디 중복 여부
	            if (data == '0') {
	               $('#ID_CHK_MSG').html("<span><font color='blue'>사용할 수 있는 아이디</font></span>");
	               $('#MEMB_CHK_YN').val("Y");
	            }else{
	               $('#ID_CHK_MSG').html("<span><font color='red'>중복된 아이디</font></span>");
	               $('#MEMB_CHK_YN').val("N");
	            }
	         }
	      }, error: function (jqXHR, textStatus, errorThrown) {
	         alert("처리중 에러가 발생했습니다. 관리자에게 문의 하세요.(error:"+textStatus+")");
	      }
	   });	   
	}
	
	//사업자번호 중복		
	function fn_bunbChk(){	   
	   var strComBunb = $("#COM_BUNB").val().replace(/-/gi, "");
	   
	   if(strComBunb.length==0){
	      $('#COM_CHK_YN').val("N");
	      return;
	   }	      
	   
	   if(strComBunb.length <10){
	      $('#COM_BUNB_CHK_MSG').html("<span><font color='red'>사업자번호 자릿수를 확인해 주세요</font></span>");
	      $('#COM_CHK_YN').val("N");
	   }else{	      
	      var strComBunbSub = strComBunb.substring(0,3)+"-"+strComBunb.substring(3,5)+"-"+strComBunb.substring(5,10);
	     
	      $("#COM_BUNB_TMP").val(strComBunbSub);
	      
	       $.ajax({
	          type:"POST",
	           url:"${contextPath }/comBunbChk",
	         data: $("#bunbChkFrm").serialize(),
	         success: function (data) {
	   
	            // 사업자번호 중복 여부
	            if (data == '0') {
	               $('#COM_BUNB_CHK_MSG').html("<span><font color='blue'></font></span>");
	               $('#COM_CHK_YN').val("Y");
	            }else{
	               $('#COM_BUNB_CHK_MSG').html("<span><font color='red'>중복된 사업자번호입니다</font></span>");
	               $('#COM_CHK_YN').val("N");
	            }
	         }, error: function (jqXHR, textStatus, errorThrown) {
	            alert("처리중 에러가 발생했습니다. 관리자에게 문의 하세요.(error:"+textStatus+")");
	         }
	      });
	   }
	}
	
	//사업자회원 선택시 사업자정보 필수입력
	//radio 버튼 클릭 이벤트 
	$("input:radio[name=MEMB_GUBN]").click(function(){	    
	    if($("input:radio[name=MEMB_GUBN]:checked").val()=='MEMB_GUBN_02' 
	    		||$("input:radio[name=MEMB_GUBN]:checked").val()=='MEMB_GUBN_04'){
	    	$('#COM_NAME').attr("required","");
			$('#COM_BUNB').attr("required","");
	    }else{
	    	$('#COM_NAME').removeAttr("required");
			$('#COM_BUNB').removeAttr("required");
	    }
	});
 
	// 숫자만
	function onlyNumber(obj){
	   $(obj).keyup(function(){
	        $(this).val($(this).val().replace(/[^0-9]/g,""));
	   }); 
	}
	
	// 개인정보와 동일 체크
	function same_chk(){
	   if($('#sameChk').prop('checked')==true){
	      $('#COM_PN').val($('#MEMB_PN').val());
	      $('#COM_BADR').val($('#MEMB_BADR').val());
	      $('#COM_DADR').val($('#MEMB_DADR').val());
	   }else{ //개인정보와 다름
	      $('#COM_PN').val('');
	      $('#COM_BADR').val('');
	      $('#COM_DADR').val('');
	   }
	}	

	//SaveValidation	
	function validatrion_submit(){
	   //아이디 체크
	   if($('#MEMB_CHK_YN').val() == "N"){
	      alert("아이디를 다시 입력해 주세요");
	      $("#MEMB_ID").focus();
	      return false;   
	   }
	   //비밀번호 체크
	   if($("#MEMB_PW").val() != $("#MEMB_PW_CON").val() && $("#MEMB_PW_CON").val()!=null){
	      alert("비밀번호가 일치하지 않습니다.");
	      $("#MEMB_PW_CON").focus();
	      return false;
	   }
	   //이메일체크
	   if(!chk_email($("#MEMB_MAIL").val())){
	      alert("e-mail 형식이 아닙니다.");
	      $("#MEMB_MAIL").focus();
	      return false;
	   }
	   //주소 체크
	   if($('#MEMB_PN').val()==null||$('#MEMB_PN').val()==''){
		   alert("우편번호와 주소는 필수값입니다.");
		   $("#MEMB_PN").focus();
		   return false;
	   }	 
	   //휴대폰번호 체크
	   if($('#MEMB_CPON').val()==null || $('#MEMB_CPON').val()==''){
		   alert("휴대폰번호를 입력해주세요." + $('#MEMB_CPON').val());
		   $("#MEMB_CPON").focus();
		   return false;
	   }	  
	   //사업자등록번호 체크
	   if($('#COM_CHK_YN').val() == "N"){
	      alert("사업자등록번호를 다시 입력해 주세요");
	      $("#COM_BUNB").focus();
	      return false;   
	   }  	   
	   /* 
	   //사업자일 경우 필수값 체크
	   if($('#MEMB_GUBN').val()=="MEMB_GUBN_02"){
		   if ($('#COM_TELN').val()==null || $('#COM_TELN').val()==""){
			   alert("사업자 연락처를 입력해 주세요");
			   $("#COM_TELN").focus();
			   return false;   
		   }		   		
	   }
	    */
	   var strComBunb = $("#COM_BUNB").val().replace(/-/gi, "");
	   var strComBunbSub = strComBunb.substring(0,3)+"-"+strComBunb.substring(3,5)+"-"+strComBunb.substring(5,10);
	   $("#COM_BUNB").val(strComBunbSub);
	   
	 	// 일반회원 선택시 사업자정보 초기화
		if($('input:radio[name="MEMB_GUBN"]:checked').val()=='MEMB_GUBN_01'){
			$('#COM_NAME').val('');
			$('#COM_BUNB').val('');
			$('#COM_TELN').val('');
			$('#COM_EXTRA_ADDR').val('');
			$('#COM_ALL_ADDR').val('');
			$('#COM_PN').val('');
			$('#sameChk').val('');
			$('#COM_BADR').val('');
			$('#COM_DADR').val('');
			$('#COM_OPEN_HH').val('');
			$('#COM_OPEN_MM').val('');
			$('#COM_OPEN').val('');
			$('#COM_CLOSE_HH').val('');
			$('#COM_CLOSE_MM').val('');
			$('#COM_CLOSE').val('');
			$('#KEEP_LOCATION').val('');	
		}  
	}

	// 회원상태
	function fn_memb_state(){	
		var d = new Date();			 
		var date = d.yyyymmddhhmmss();	//현재일자
		//alert(date);
		
		if($('input:radio[name="STOP_YN"]:checked').val()=='Y'){
			$('#STDT_SHOW').show();
			$('#STOP_YN').attr("required","");
			$('#STOP_DTM').val(date);			
		}else{
			$('#STDT_SHOW').hide();
			$('#STOP_YN').removeAttr("required");
			$('#STOP_DTM').val('');
		}
		
		if($('input:radio[name="SCSS_YN"]:checked').val()=='Y'){
			$('#STDT_SHOW').show();
			$('#STOP_YN').attr("required","");
			$('#STOP_DTM').val(date);
		}
	}

	// 현재일자 format
	Date.prototype.yyyymmddhhmmss = function(){
		
	    var yyyy = this.getFullYear().toString();
	    var MM = (this.getMonth() + 1).toString();
	    var dd = this.getDate().toString();
	    /* 
	    var HH = this.getHours();
	    var mm = this.getMinutes();
	    var ss = this.getSeconds();
	     */
	    return yyyy + (MM[1] ? MM : '0'+MM[0]) + (dd[1] ? dd : '0'+dd[0]);
	    		/* + [(HH>9 ? '' : '0') + HH, (mm>9 ? '' : '0') + mm, (ss>9 ? '' : '0') + ss, ].join(''); */
	}

    // 쿠폰 수량변경 및 삭제
	$('.btnQty').click(function(){
        var mode = $(this).text();
        var this_qty, max_qty = 999, min_qty = 0;
        var $el_qty = $('#DLVY_CPON').val();	//.replace(/[^0-9]/, ""));
        var stock = 100;

        switch(mode) {
            case "+":
                this_qty = parseInt($el_qty) + 1;

                if(this_qty > max_qty) {
                    this_qty = max_qty;
                    alert("최대수량은 "+number_format(String(max_qty))+" 입니다.");
                }
                
                $('#DLVY_CPON').val(this_qty);
                break;

            case "-":
                this_qty = parseInt($el_qty) - 1;
                
                if(this_qty < min_qty) {
                    this_qty = min_qty;
                    alert("최소수량은 "+number_format(String(min_qty))+" 입니다.");
                }
                
                $('#DLVY_CPON').val(this_qty);
                break;

            default:
                alert("올바른 방법으로 이용해 주십시오.");
                break;
		}
    });

</script>

<!-- 아이디 중복체크 -->
<form id="idChkFrm" name="idChkFrm" action="${contextPath }/idChk" method="post" autocomplete="off">
   <input type="hidden" id="MEMB_ID_TMP" name="MEMB_ID_TMP" />
</form>

<!-- 사업자번호 중복체크 -->
<form id="bunbChkFrm" name="bunbChkFrm" action="${contextPath }/comBunbChk" method="post" autocomplete="off">
   <input type="hidden" id="COM_BUNB_TMP" name="COM_BUNB_TMP" />
</form>

