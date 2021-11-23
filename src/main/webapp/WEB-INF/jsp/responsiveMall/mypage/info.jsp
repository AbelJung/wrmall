<%@ page trimDirectiveWhitespaces="true"%>
<%@ page contentType="text/html; charset=utf-8"%>
<%@ include file="/layout/inc/taglib.jsp" %>  

<c:set var="strActionUrl" value="${contextPath }/m/wishList" />
<c:set var="strMethod" value="post" />

	<script type="text/javascript">
	$(document).ready(function() {

		//개점, 폐점
		<c:if test="${!empty obj.COM_OPEN }">
			$('#COM_OPEN_HH').val('${obj.COM_OPEN_HH }');
			$('#COM_OPEN_MM').val('${obj.COM_OPEN_MM }');
		</c:if>
		<c:if test="${!empty obj.COM_CLOSE }">
			$('#COM_CLOSE_HH').val('${obj.COM_CLOSE_HH }');
			$('#COM_CLOSE_MM').val('${obj.COM_CLOSE_MM }');
		</c:if>
		
		//이메일 한글입력 안되게 처리_20190409
		$("input[name=MEMB_MAIL]").keyup(function(event){
			$(this).val( $(this).val().replace( /[^a-z0-9]/gi, '' ) );
		});
	});
	
	//사업자구분체크
	$(function() {
		fn_memb_gbn();	
	});
	
	function fregisterform_submit(){

	   if($("#MEMB_PW").val() != $("#MEMB_PW_RE").val()){
	      alert("비밀번호가 다릅니다.");
	      return false;
	   }	   
	   if(!chk_email($("#MEMB_MAIL").val())){
	      alert("e-mail 형식이 아닙니다.");
	      $("#MEMB_MAIL").focus();
	      return false;
	   }
	   if($('#MEMB_PN').val()==null||$('#MEMB_PN').val()==''){
		   alert("우편번호와 주소는 필수값입니다.");
		   return false;
	   }	   
	   if($('#COM_CHK_YN').val() == "N"){
	      alert("사업자등록번호를 다시 입력해 주세요");
	      $("#COM_BUNB").focus();
	      return false;   
	   } 	   
	   
	   var membBtdyYear = $('#MEMB_BTDY_YEAR').val();
	   var membBtdyMonth = $('#MEMB_BTDY_MONTH').val();
	   var membBtdyDay = $('#MEMB_BTDY_DAY').val();
	   
	   // 생일, 연락처, 전화번호
	   $('#MEMB_BTDY').val(membBtdyYear+(LPAD(membBtdyMonth,'0',2))+(LPAD(membBtdyDay,'0',2)));
	   $('#MEMB_TELN').val($('#MEMB_TELN1').val()+"-"+$('#MEMB_TELN2').val()+"-"+$('#MEMB_TELN3').val());
	   $('#MEMB_CPON').val($('#MEMB_CPON1').val()+"-"+$('#MEMB_CPON2').val()+"-"+$('#MEMB_CPON3').val());
	   
	   if(!($('#ERP_LIST').css("display")=="none")){//사업자일 경우 필수값 체크
	      $('#COM_TELN').val($('#COM_TELN1').val()+"-"+$('#COM_TELN2').val()+"-"+$('#COM_TELN3').val());
	      //$('#COM_TELN').val($('#COM_TELN1').val()+""+$('#COM_TELN2').val()+""+$('#COM_TELN3').val());
	   }	   
	   
	   //매장 개점시간, 폐점시간
	   $('#COM_OPEN').val($('#COM_OPEN_HH').val()+":"+$('#COM_OPEN_MM').val());
	   $('#COM_CLOSE').val($('#COM_CLOSE_HH').val()+":"+$('#COM_CLOSE_MM').val());
	   
	   var strComBunb = $("#COM_BUNB").val().replace(/-/gi, "");
	   var strComBunbSub = strComBunb.substring(0,3)+"-"+strComBunb.substring(3,5)+"-"+strComBunb.substring(5,10);
	   $("#COM_BUNB").val(strComBunbSub);
	   
	 	//사업자 구분
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
		/*
		var open_hours = parseInt($('#COM_OPEN_HH').val());
		var AMPM = $('input:radio[name="COM_OPEN_TM"]:checked').val();
		if(AMPM == "PM" && open_hours<12) open_hours = open_hours+12;
		if(AMPM == "AM" && open_hours==12) open_hours = open_hours-12;	
		$('#COM_OPEN').val(open_hours+" : "+$('#COM_OPEN_MM').val());
		
		var close_hours = parseInt($('#COM_CLOSE_HH').val());
		var AMPM2 = $('input:radio[name="COM_CLOSE_TM"]:checked').val();
		if(AMPM2 == "PM" && close_hours<12) close_hours = close_hours+12;
		if(AMPM2 == "AM" && close_hours==12) close_hours = close_hours-12;
		$('#COM_CLOSE').val(close_hours+" : "+$('#COM_CLOSE_MM').val());
		*/		
	}
	
	//왼쪽에서부터 채운다는 의미
	function LPAD(s, c, n) {
	    if (! s || ! c || s.length >= n) {
	        return s;
	    }
	    var max = (n - s.length)/c.length;
	    for (var i = 0; i < max; i++) {
	        s = c + s;
	    }
	    return s;
	}
		
	// email체크 함수
	function chk_email(strEmail) {	
	   /*
	    * 이메일의 값은 단순히 문자이다 이것을 객체화시켜서 비교한다.
	    * ^는 반드시 이런형식으로 시작하라 $는 반드시 요런형식으로 종료
	    * {3,20} 3~20글자만쓸수있다.
	    */	   
	   var em = strEmail;
	   var epattern = new RegExp("^([a-zA-Z0-9\-\_]{3,20})" + "@" + "([a-zA-Z0-9\-\_]{3,20})" + "." + "([a-zA-Z0-9\-\_\.]{3,5})$");
	   if (!epattern.test(em)) {
	      return false;
	   }
	   return true;
	}
	
	//비밀번호 확인_CHJW
	function fnCfmPassword(upw){
		if (upw != $('#MEMB_PW_RE').val() && $('#MEMB_PW_RE').val() != ''){
			alert('비밀번호가 일치하지 않습니다.'); 
	     $('#MEMB_PW_RE').val('');
	     
	     //focus시 chrome에서 무한루프 해결
	     setTimeout(function(){
	     	$('#MEMB_PW_RE').focus();
	     }, 10)
	     return false;
		}		
		return true;
	}
	
	function fnCheckPassword(uid, upw) {
		
	    if(!/^[a-zA-Z0-9]{5,20}$/.test(upw)&&!(upw=="")) { 
	        alert('비밀번호는 숫자와 영문자 조합으로 5~12자리를 사용해야 합니다.'); 
	        $('#MEMB_PW').val('');
	        $('#MEMB_PW').focus();
	       
	        return false;
	    }
	  
	    var chk_num = upw.search(/[0-9]/g); 
	    var chk_eng = upw.search(/[a-z]/ig);
	
	    if((chk_num < 0 || chk_eng < 0) &&!(upw=="")) { 
	        alert('비밀번호는 숫자와 영문자를 혼용하여야 합니다.'); 
	        $('#MEMB_PW').val('');
	        $('#MEMB_PW').focus();	        
	        return false;
	    }	    
	    if(/(\w)\1\1\1/.test(upw) &&!(upw=="")) {
	        alert('비밀번호에 같은 문자를 4번 이상 사용하실 수 없습니다.'); 
	        $('#MEMB_PW').val('');
	        $('#MEMB_PW').focus();	        
	        return false;
	    }
	    if(upw.search(uid)>-1 &&!(upw=="")) {
	        alert('ID가 포함된 비밀번호는 사용하실 수 없습니다.'); 
	        $('#MEMB_PW').val('');
	        $('#MEMB_PW').focus();	        
	        return false;
	    }
	
	    return true;
	}
	
	//사업자,개인 선택
	function fn_memb_gbn(){
		if($('input:radio[name="MEMB_GUBN"]:checked').val()=='MEMB_GUBN_02'
				||$('input:radio[name="MEMB_GUBN"]:checked').val()=='MEMB_GUBN_04'){
			$('#ERP_LIST').show();
			$('#COM_NAME').attr("required","");
			$('#COM_BUNB').attr("required","");
			
			var strcombunb = $('#COM_BUNB').val().replace(/-/gi, "");
			/* alert(strcombunb); */
			
			if(strcombunb != null || strcombunb != ''){
				if(strcombunb.length == 10){
					$('#COM_NAME').attr("readonly",true);
					$('#COM_BUNB').attr("readonly",true);
					$('#COM_BUNB').removeAttr("onblur");
				}	
			}		
		}else{
			$('#ERP_LIST').hide();
			$('#COM_NAME').removeAttr("required");
			$('#COM_BUNB').removeAttr("required");
		}
	}
	
	function same_chk(){
	   if($('#sameChk').prop('checked')==true){ //개인정보와 동일
	      $('#COM_PN').val($('#MEMB_PN').val());
	      $('#COM_BADR').val($('#MEMB_BADR').val());
	      $('#COM_DADR').val($('#MEMB_DADR').val());
	   }else{ //개인정보와 다름
	      $('#COM_PN').val('');
	      $('#COM_BADR').val('');
	      $('#COM_DADR').val('');
	   }
	}
	
	// 숫자만 입력
	function onlyNumber(obj){
		$(obj).keyup(function(){
	        $(this).val($(this).val().replace(/[^0-9]/g,""));
	   }); 
	}
	
	function fn_bunbChk(){
	   var strComBunb = $("#COM_BUNB").val().replace(/-/gi,"");
	   
	   if(strComBunb.length==0){
	      $('#COM_CHK_YN').val("N");
	      return;
	   }
	   
	   if(strComBunb.length <10 || strComBunb.length > 10){
	      $('#COM_BUNB_CHK_MSG').html("<span><font color='red'>사업자번호 자릿수를 확인해 주세요</font></span>");
	      $('#COM_CHK_YN').val("N");
	      
	   }else{	      
	      var strComBunbSub = strComBunb.substring(0,3)+"-"+strComBunb.substring(3,5)+"-"+strComBunb.substring(5,10);
	      
	      if($("#BEF_COM_BUNB").val()==strComBunbSub){
	    	  $('#COM_BUNB_CHK_MSG').html("<span><font color='blue'>기존에 사용한 사업자번호 입니다.</font></span>");
	          $('#COM_CHK_YN').val("Y");
	      }else{
	    	  $("#COM_BUNB_TMP").val(strComBunbSub);
	    	  
		       $.ajax({
		          type:"POST",
		           url:"${contextPath }/comBunbChk",
		         data: $("#bunbChkFrm").serialize(),
		         success: function (data) {
		   		
		            // 사업자번호 중복 여부
		            if (data == '0') {
		               $('#COM_BUNB_CHK_MSG').html("<span><font color='blue'>사용할 수 있는 사업자번호입니다.</font></span>");
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
	}
	</script>
	
	<form id="bunbChkFrm" name="bunbChkFrm" action="${contextPath }/comBunbChk" method="post" autocomplete="off">
	   <input type="hidden" id="COM_BUNB_TMP" name="COM_BUNB_TMP" />
	</form>
	
	<!-- Main Container  -->
	<div class="main-container container">
		<ul class="breadcrumb">
			<li><a href="${contextPath }/m"><i class="fa fa-home"></i></a></li>
			<li><a href="${contextPath }/m/mypage">마이페이지</a></li>
		</ul>
		
		<div class="row">
			<div id="content" class="col-sm-12">
				<h2 class="title">마이페이지</h2>
			    <form id="fregisterform" name="fregisterform" class="form-horizontal account-register clearfix" action="${contextPath }/m/mypage/memberUpdate" onsubmit="return fregisterform_submit(this);" method="post" autocomplete="off">
				<input type="hidden" id="MEMB_CHK_YN" name="MEMB_CHK_YN" value="N"/>
				<input type="hidden" id="COM_CHK_YN" name="COM_CHK_YN" value="Y"/>
           		<input type="hidden" name="BANK_NAME" value="${obj.BANK_NAME }" id="BANK_NAME">
				<input type="hidden" name="BANK_BUNB" value="${obj.BANK_BUNB }" id="BANK_BUNB">
					<fieldset id="account">
						<legend>개인정보 - 필수 입력</legend>
						<div class="form-group required">
							<label class="col-sm-2 control-label" for="MEMB_ID">아이디</label>
							<div class="col-sm-10">
								${obj.MEMB_ID }
							</div>
						</div>
						<div class="form-group required">
							<label class="col-sm-2 control-label" for="MEMB_PW">비밀번호</label>
							<div class="col-sm-10">
								<input type="password" name="MEMB_PW" placeholder="숫자와 영문자 조합으로 5~12자리" value="" id="MEMB_PW" class="form-control" required onblur="javascript:fnCheckPassword('${obj.MEMB_ID }',$('#MEMB_PW').val());">
							</div>
						</div>
						<div class="form-group required">
							<label class="col-sm-2 control-label" for="MEMB_PW_RE">비밀번호 확인</label>
							<div class="col-sm-10">
								<input type="password" name="MEMB_PW_RE" placeholder="숫자와 영문자 조합으로 5~12자리" value="" id="MEMB_PW_RE" class="form-control" required onchange="javascript:fnCfmPassword($('#MEMB_PW').val());">
							</div>
						</div>
						<div class="form-group required">
							<label class="col-sm-2 control-label" for="MEMB_GUBN">사업자 구분</label>
							<div class="col-sm-10">
								<label class="radio-inline">
									<input type="radio" name="MEMB_GUBN" value="MEMB_GUBN_02" required ${ obj.MEMB_GUBN == 'MEMB_GUBN_02' ? 'checked' : '' } onclick="return(false)"> 사업자
								</label>
								<label class="radio-inline">
									<input type="radio" name="MEMB_GUBN" value="MEMB_GUBN_01" required ${ obj.MEMB_GUBN == 'MEMB_GUBN_01' ? 'checked' : '' } onclick="return(false)"> 개인
								</label>
								<label class="radio-inline">
									<input type="radio" name="MEMB_GUBN" value="MEMB_GUBN_04" required ${ obj.MEMB_GUBN == 'MEMB_GUBN_04' ? 'checked' : '' } onclick="return(false)"> 도매유통사업자	<!-- 사업자 수정 불가 = fn_memb_gbn() -->
								</label>
	               				<h5 style="color:red">* 도매유통사업자 회원가입시 쇼핑몰주문시 배송불가능할수도 있습니다. (사전 협의 필수)</h5>
							</div>
						</div>
						<div class="form-group required">
							<label class="col-sm-2 control-label" for="MEMB_NAME">이름(대표자성명)</label>
							<div class="col-sm-10">
								<input type="text" name="MEMB_NAME" value="${obj.MEMB_NAME }" placeholder=이름(대표자성명) id="MEMB_NAME" class="form-control" required>
							</div>
						</div>
						<div class="form-group required">
							<label class="col-sm-2 control-label" for="MEMB_SEX">성별</label>
							<div class="col-sm-10">
								<label class="radio-inline">
									<input type="radio" name="MEMB_SEX" value="MEMB_SEX_01" required ${ obj.MEMB_SEX == 'MEMB_SEX_01' ? 'checked' : '' }> 남
								</label>
								<label class="radio-inline">
									<input type="radio" name="MEMB_SEX" value="MEMB_SEX_02" required ${ obj.MEMB_SEX == 'MEMB_SEX_02' ? 'checked' : '' }> 여
								</label>
							</div>
						</div>
						<div class="form-group required">
							<label class="col-sm-2 control-label" for="MEMB_BTDY_YEAR">생년월일</label>
							<div class="col-sm-10">
								<div class="form-inline">
									<select name="MEMB_BTDY_YEAR" id="MEMB_BTDY_YEAR" class="form-control">
										<c:forEach var="i" begin="0" end="${TODAY_YEAR - 1950 }" step="1">
											<option value="${i + 1950}" ${fn:substring(obj.MEMB_BTDY,0,4) == i+1950 ? 'selected' : ''}>${i + 1950}년</option>
										</c:forEach>  
									</select>&nbsp;
									<select name="MEMB_BTDY_MONTH" id="MEMB_BTDY_MONTH" class="form-control">
										<c:forEach var="i" begin="1" end="12" step="1">
											<option value="${i}" ${fn:substring(obj.MEMB_BTDY,4,6) == i ? 'selected' : ''}>${i}월</option>
										</c:forEach>  
									</select>&nbsp;
									<select name="MEMB_BTDY_DAY" id="MEMB_BTDY_DAY" class="form-control">
										<c:forEach var="i" begin="1" end="31" step="1">
											<option value="${i}" ${fn:substring(obj.MEMB_BTDY,6,8) == i ? 'selected' : ''}>${i}일</option>
										</c:forEach>  
									</select>
									&nbsp;&nbsp;&nbsp;
									<label class="radio-inline">
										<input type="radio" name="SLCAL_GUBN" value="SLCAL_GUBN_01" required ${ obj.SLCAL_GUBN == 'SLCAL_GUBN_01' ? 'checked' : '' } />양력
									</label>
									<label class="radio-inline">
										<input type="radio" name="SLCAL_GUBN" value="SLCAL_GUBN_02" required ${ obj.SLCAL_GUBN == 'SLCAL_GUBN_02' ? 'checked' : '' } />음력
									</label>
									<input type="hidden" name="MEMB_BTDY" value="" id="MEMB_BTDY">
								</div>
							</div>
						</div>
						<div class="form-group required">
							<label class="col-sm-2 control-label" for="MEMB_MAIL">E-mail</label>
							<div class="col-sm-10">
								<input type="email" name="MEMB_MAIL" value="${obj.MEMB_MAIL }" placeholder="E-mail" id="MEMB_MAIL" class="form-control">
							</div>
						</div>
						<div class="form-group required">
							<label class="col-sm-2 control-label" for="MEMB_PN">주소</label>
							<div class="col-sm-10">
								<input type="hidden" name="EXTRA_ADDR" value="" id="EXTRA_ADDR">
								<input type="hidden" name="ALL_ADDR" value="" id="ALL_ADDR">
								<div class="form-inline">
									<div class="input-group">
										<input type="text" name="MEMB_PN" value="${obj.MEMB_PN }" id="MEMB_PN" placeholder="우편번호" required readonly="readonly" class="form-control" maxlength="6">
										<span class="input-group-btn">
											<input type="button" class="btn btn-primary" value="주소검색" onclick="win_zip('fregisterform', 'MEMB_PN', 'MEMB_BADR', 'MEMB_DADR', 'EXTRA_ADDR', 'ALL_ADDR');">
										</span>
									</div>
								</div> 
							</div>
						</div>
						<div class="form-group required">
							<label class="col-sm-2 control-label" for="MEMB_BADR">기본주소</label>
							<div class="col-sm-10">
								<input type="text" name="MEMB_BADR" value="${obj.MEMB_BADR }" id="MEMB_BADR" placeholder="기본주소" required readonly="readonly" class="form-control">
							</div>
						</div>
						<div class="form-group required">
							<label class="col-sm-2 control-label" for="MEMB_DADR">상세주소</label>
							<div class="col-sm-10">
								<input type="text" name="MEMB_DADR" value="${obj.MEMB_DADR }" id="MEMB_DADR" placeholder="상세주소" required class="form-control">
							</div>
						</div>
						
						<div class="form-group">
							<label class="col-sm-2 control-label" for="MEMB_TELN1">전화번호</label>
							<div class="col-sm-10">
								<div class="form-inline">
									<input type="hidden" name="MEMB_TELN" value="" id="MEMB_TELN" >
									<input type="text" name="MEMB_TELN1" value="${fn:split(obj.MEMB_TELN,'-')[0]}" id="MEMB_TELN1" class="form-control"  style="width:60px; display: inline-block;" maxlength="3" onkeydown="return onlyNumber(this)">&nbsp;-&nbsp;
									<input type="text" name="MEMB_TELN2" value="${fn:split(obj.MEMB_TELN,'-')[1]}" id="MEMB_TELN2" class="form-control"  style="width:60px; display: inline-block;" maxlength="4" onkeydown="return onlyNumber(this)">&nbsp;-&nbsp;
									<input type="text" name="MEMB_TELN3" value="${fn:split(obj.MEMB_TELN,'-')[2]}" id="MEMB_TELN3" class="form-control"  style="width:60px; display: inline-block;" maxlength="4" onkeydown="return onlyNumber(this)">
								</div>
							</div>
						</div>
						<div class="form-group required">
							<label class="col-sm-2 control-label" for="MEMB_CPON1">휴대폰번호</label>
							<div class="col-sm-10">
								<div class="form-inline">
									<input type="hidden" name="MEMB_CPON" value="" id="MEMB_CPON" >
									<input type="text" name="MEMB_CPON1" value="${fn:split(obj.MEMB_CPON,'-')[0]}" id="MEMB_CPON1" required class="form-control"  style="width:60px; display: inline-block;" maxlength="3" onkeydown="return onlyNumber(this)">&nbsp;-&nbsp;
									<input type="text" name="MEMB_CPON2" value="${fn:split(obj.MEMB_CPON,'-')[1]}" id="MEMB_CPON2" required class="form-control"  style="width:60px; display: inline-block;" maxlength="4" onkeydown="return onlyNumber(this)">&nbsp;-&nbsp;
									<input type="text" name="MEMB_CPON3" value="${fn:split(obj.MEMB_CPON,'-')[2]}" id="MEMB_CPON3" required class="form-control"  style="width:60px; display: inline-block;" maxlength="4" onkeydown="return onlyNumber(this)">
								</div>
							</div>
						</div>
					</fieldset>
					<fieldset id="ERP_LIST">
						<legend>추가정보(사업자) - 사업자 필수 입력항목</legend>
						<div class="form-group required">
							<label class="col-sm-2 control-label" for="COM_NAME">상호명</label>
							<div class="col-sm-10">
								<input type="text" name="COM_NAME" value="${obj.COM_NAME }" placeholder="상호명" id="COM_NAME" class="form-control">
							</div>
						</div>
						<div class="form-group required">
							<label class="col-sm-2 control-label" for="COM_BUNB">사업자등록번호</label>
							<div class="col-sm-10">
								<input type="hidden" id="BEF_COM_BUNB" value="${obj.COM_BUNB }"/>
								<input type="text" name="COM_BUNB" value="${obj.COM_BUNB }" placeholder="-를 제외하고 적어주세요" id="COM_BUNB" class="form-control" onblur="javascript:fn_bunbChk();" onkeydown="return onlyNumber(this)" maxlength="10">
								<div id="COM_BUNB_CHK_MSG"></div>
							</div>
						</div>
						<div class="form-group">
							<label class="col-sm-2 control-label" for="COM_TELN1">전화번호</label>
							<div class="col-sm-10">
								<div class="form-inline">
									<input type="hidden" name="COM_TELN" value="" id="COM_TELN" >
									<input type="text" name="COM_TELN1" value="${fn:split(obj.COM_TELN,'-')[0]}" id="COM_TELN1" class="form-control"  style="width:60px; display: inline-block;" maxlength="3" onkeydown="return onlyNumber(this)">&nbsp;-&nbsp;
									<input type="text" name="COM_TELN2" value="${fn:split(obj.COM_TELN,'-')[1]}" id="COM_TELN2" class="form-control"  style="width:60px; display: inline-block;" maxlength="4" onkeydown="return onlyNumber(this)">&nbsp;-&nbsp;
									<input type="text" name="COM_TELN3" value="${fn:split(obj.COM_TELN,'-')[2]}" id="COM_TELN3" class="form-control"  style="width:60px; display: inline-block;" maxlength="4" onkeydown="return onlyNumber(this)">
								</div>
							</div>
						</div>
						<div class="form-group">
							<label class="col-sm-2 control-label" for="COM_PN">회사 주소</label>
							<div class="col-sm-10">
								<input type="hidden" name="COM_EXTRA_ADDR" value="" id="COM_EXTRA_ADDR">
								<input type="hidden" name="COM_ALL_ADDR" value="" id="COM_ALL_ADDR">
								<input type="checkbox" id="sameChk" onclick="same_chk()"/>개인정보와 동일
								<div class="form-inline">
									<div class="input-group">
										<input type="text" name="COM_PN" value="${obj.COM_PN }" id="COM_PN" placeholder="우편번호" readonly="readonly" class="form-control" maxlength="6">
										<span class="input-group-btn">
											<input type="button" class="btn btn-primary" value="주소검색" onclick="win_zip('fregisterform', 'COM_PN', 'COM_BADR', 'COM_DADR', 'COM_EXTRA_ADDR', 'COM_ALL_ADDR');">
										</span>
									</div>
								</div> 
							</div>
						</div>
						<div class="form-group">
							<label class="col-sm-2 control-label" for="COM_BADR">회사 기본주소</label>
							<div class="col-sm-10">
								<input type="text" name="COM_BADR" value="${obj.COM_BADR }" id="COM_BADR" placeholder="회사 기본주소" readonly="readonly" class="form-control">
							</div>
						</div>
						<div class="form-group">
							<label class="col-sm-2 control-label" for="COM_DADR">회사 상세주소</label>
							<div class="col-sm-10">
								<input type="text" name="COM_DADR" value="${obj.COM_DADR }" id="COM_DADR" placeholder="회사 상세주소" class="form-control">
							</div>
						</div>
						<div class="form-group">
							<label class="col-sm-2 control-label" for="input-address-2">매장 개점시간</label>
							<div class="col-sm-10">
								<div class="form-inline">
									<select name="COM_OPEN_HH" id="COM_OPEN_HH" class="form-control">
										<c:forEach var="i" begin="0" end="23" step="1">
											<c:set var="data">${i}</c:set>
											<c:if test="${i < 10}">
												<c:set var="data">0${i}</c:set>
											</c:if>
											<option value="${data}">${data}시</option>
										</c:forEach>  
									</select>&nbsp;
									<select name="COM_OPEN_MM" id="COM_OPEN_MM" class="form-control">
										<c:forEach var="i" begin="0" end="59" step="1">
											<c:set var="data">${i}</c:set>
											<c:if test="${i < 10}">
												<c:set var="data">0${i}</c:set>
											</c:if>
											<option value="${data}">${data}분</option>
										</c:forEach>  
									</select>
									<input type="hidden" name="COM_OPEN" value="" id="COM_OPEN">
								</div>
							</div>
						</div>
						<div class="form-group">
							<label class="col-sm-2 control-label" for="input-address-2">매장 폐점시간</label>
							<div class="col-sm-10">
								<div class="form-inline">
									<select name="COM_CLOSE_HH" id="COM_CLOSE_HH" class="form-control">
										<c:forEach var="i" begin="0" end="23" step="1">
											<c:set var="data">${i}</c:set>
											<c:if test="${i < 10}">
												<c:set var="data">0${i}</c:set>
											</c:if>
											<option value="${data}">${data}시</option>
										</c:forEach>  
									</select> &nbsp;
									<select name="COM_CLOSE_MM" id="COM_CLOSE_MM" class="form-control">
										<c:forEach var="i" begin="0" end="59" step="1">
										<c:set var="data">${i}</c:set>
										<c:if test="${i < 10}">
											<c:set var="data">0${i}</c:set>
										</c:if>
										<option value="${data}">${data}분</option>
									</c:forEach>  
									</select>
									<input type="hidden" name="COM_CLOSE" value="" id="COM_CLOSE">
									
								</div>
								<span style="color:red">* 개점시간과 폐점시간이 00시 00분일 경우 24시 운영입니다.</span>
							</div>
						</div>
						<div class="form-group">
							<label class="col-sm-2 control-label" for="input-city">상품보관장소</label>
							<div class="col-sm-10">
								<input type="text" name="KEEP_LOCATION" value="${obj.KEEP_LOCATION }" placeholder="상품보관장소" id="KEEP_LOCATION" class="form-control">
								<span style="color:red">* 오후 6시 이후 개점 사업장 상품보관장소를 입력하세요.</span>
							</div>
						</div>
					</fieldset>
					
					<div class="buttons">
						<div class="pull-right">
							<input type="submit" id="btn_submit" value="개인정보 수정" class="btn btn-primary"> &nbsp;
							<a href="${contextPath }/m" class="btn_cancel btn btn-default ">취소</a>
						</div>
					</div>
				</form>
			</div>
		</div>
	</div>
	<!-- //Main Container -->

