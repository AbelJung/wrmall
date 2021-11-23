<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ include file="/layout/inc/taglib.jsp" %>  
	
	<script src="${contextPath }/resources/js/addr_check.js"></script>
	
	<script type="text/javascript">
	$(function() {
		// 로그인 체크	
		if("${USER.MEMB_ID}" == ""){
			alert("로그인이 필요합니다.");
			location.href = "${contextPath}/user/loginForm";
			return false;
		}else{
			fn_dlarGubn();
		}
		
		// 선불&착불
		$("#DAP_YN").change(function(){
		
			var orderSum = parseInt($("#ORDER_SUM").val());		//주문금액
			var dlvyAmt = ${supplierInfo.DLVY_AMT };			//배송비
		    
			if($(this).val() == 'N'){
				dlvyAmt = 0;
			}
			
			var totAmt = orderSum + dlvyAmt;
			
			$("#tdTotAmt").text(number_format(String(totAmt)) + "원");
			$("#ORDER_AMT").val(totAmt);
		});
		
		// 주문하기
	    $("#btnSave").click(function() {
	
	    	//필수입력 체크
			if($("#RECV_PERS").val() == "") {
	            alert("받으시는분은 필수 입력 항목입니다.");
	            $("#RECV_PERS").focus();
	            return false;
			}
	
			if($("#POST_NUM").val() == "") {
	            alert("우편번호는 필수 입력 항목입니다.\n주소검색을 클릭하여 우편번호를 입력하세요.");
	            $("#POST_NUM").focus();
	            return false;
			}
	
			if($("#RECV_TELN1").val() == "" || $("#RECV_TELN2").val() == "" || $("#RECV_TELN3").val() == "") {
	            alert("전화번호는 필수 입력 항목입니다.");
	            $("#RECV_TELN1").focus();
	            return false;
			}
			
			if($("#RECV_CPON1").val() == "" || $("#RECV_CPON2").val() == "" || $("#RECV_CPON3").val() == "") {
	            alert("휴대폰번호는 필수 입력 항목입니다.");
	            $("#RECV_CPON1").focus();
	            return false;
			}
			
			if($('input[name="DLAR_TIME"]').val()=='nothing'){
				alert("배송시간/출고시간은 필수항목입니다.");
				return false;
			}
	
			var orderAmt = $("#ORDER_AMT").val();
			var dlvyAmt = $("#DLVY_AMT").val();
			var dapYn = 'Y';//$("#DAP_YN").val();
			
			if(dapYn == 'Y'){
			}
			
			//쿠폰사용체크
			var cpon = $("#CPON_YN").val();
			var dlvyCnt = $("#DLVY_CPON").val();
			
			if(cpon == "Y" && dlvyCnt>0){
				$("#DLVY_AMT").val(0);					//배송비 0원				
			}else{
				$("#CPON_YN").val("N");				
			}
			
			if(orderAmt > 5000000){
	            alert("총 구매 금액이 5,000,000원을 넘을 수 없습니다.");
	            //$("#agree").focus();
	            return false;
			}
			
	    	if(!confirm('결제를 하시겠습니까?')) {
	            return false;
	    	}
	    	
	    	$('#RECV_TELN').val($('#RECV_TELN1').val()+"-"+$('#RECV_TELN2').val()+"-"+$('#RECV_TELN3').val());
	    	$('#RECV_CPON').val($('#RECV_CPON1').val()+"-"+$('#RECV_CPON2').val()+"-"+$('#RECV_CPON3').val());
	    	
	    	// OID, TIMESTA
	    	
	    	// 결제창 호출
	    	document.getElementById("fregisterform").submit();
	    });
	    
	 	// 주문취소
	    $("#btnCancel").click(function() {
	    	window.history.back();
	    });
	    
	    //배송시간 defualt
	    //fn_dlarGubn();
	    fn_dlarTime();
	    fn_dlarDate();
	});
	
	function LPad(digit, size, attatch) {
	    var add = "";
	    digit = digit.toString();
	
	    if (digit.length < size) {
	        var len = size - digit.length;
	        for (i = 0; i < len; i++) {
	            add += attatch;
	        }
	    }
	    return add + digit;
	}
	
	function makeoid() {
		var now = new Date();
		var years = now.getFullYear();
		var months = LPad(now.getMonth() + 1, 2, "0");
		var dates = LPad(now.getDate(), 2, "0");
		var hours = LPad(now.getHours(), 2, "0");
		var minutes = LPad(now.getMinutes(), 2, "0");
		var seconds = LPad(now.getSeconds(), 2, "0");
		var timeValue = years + months + dates + hours + minutes + seconds; 
		document.getElementById("LGD_OID").value = "test_" + timeValue;
		document.getElementById("LGD_TIMESTAMP").value = timeValue;
	}
	
	// 배송지정보 구분
	function fn_dlarGubn(){
		//현장출고
		if($("input:radio[name='DLAR_GUBN']:checked").val()=='DLAR_GUBN_05'){
			$('#dlarGugn5').show();
			$('#dlarGugn1234').hide();
			
			$('input[name="DLAR_DATE"]').val($('#DLAR_DATE_CHK').val());
			
		}else{
			$('#dlarGugn5').hide();
			$('#dlarGugn1234').show();
			
			$('input[name="DLAR_DATE"]').val('');			
		}
		
		//배송지 구분별 배송지정보
		$.ajax({
			type: "POST",
		    dataType: 'json',
			url: '${contextPath}/order/deliveryAddr.json',
			data: $("#fregisterform").serialize(),
			success: function (data) {
				
				//최근배송지 또는 받는사람이 없을경우 
				if(data.recv_PERS == "" && $("input:radio[name='DLAR_GUBN']:checked").val() == "DLAR_GUBN_03"){
					alert("주문내역이 없습니다.");
				}
				
				//초기화
				$('#RECV_TELN1').val("");
				$('#RECV_TELN2').val("");
				$('#RECV_TELN3').val("");
				$('#RECV_CPON1').val("");
				$('#RECV_CPON2').val("");
				$('#RECV_CPON3').val("");
				
				// 배송지 정보				
				$('#RECV_PERS').val(data.recv_PERS);
				$('#POST_NUM').val(data.post_NUM);
				$('#BASC_ADDR').val(data.basc_ADDR);
				$('#DTL_ADDR').val(data.dtl_ADDR);
				$('#RECV_TELN').val(data.recv_TELN);
				$('#RECV_CPON').val(data.recv_CPON);
				
				//전화번호
				var recvTeln = data.recv_TELN;
				if(recvTeln){
					var slRecvTeln = recvTeln.split("-");
		
					$('#RECV_TELN1').val(slRecvTeln[0]);
					$('#RECV_TELN2').val(slRecvTeln[1]);
					$('#RECV_TELN3').val(slRecvTeln[2]);
				}
				
				//핸드폰번호
				var recvCpon = data.recv_CPON;
				if(recvCpon){
					var slRecvCpon = recvCpon.split("-");
					
					$('#RECV_CPON1').val(slRecvCpon[0]);
					$('#RECV_CPON2').val(slRecvCpon[1]);
					$('#RECV_CPON3').val(slRecvCpon[2]);	
				}
				
			}, error: function (jqXHR, textStatus, errorThrown) {
				alert("처리중 에러가 발생했습니다. 관리자에게 문의 하세요.(error:"+textStatus+")");
			}
		});
	}
	
	// 출고&배송시간
	function fn_dlarTime(){
		if($("input:radio[name='DLAR_GUBN']:checked").val()=='DLAR_GUBN_05'){
			$('input[name="DLAR_TIME"]').val($('#DLAR_TIME_CHK_01').val());
		}else{
			$('input[name="DLAR_TIME"]').val($('#DLAR_TIME_CHK_02').val());
		}
	}
	
	// 출고일자 (명일/당일)
	function fn_dlarDate(){
		if($("input:radio[name='DLAR_GUBN']:checked").val()=='DLAR_GUBN_05'){
			$('input[name="DLAR_DATE"]').val($(':radio[name="DLAR_DATE_CHK"]:checked').val());
		}else{		
			$('input[name="DLAR_DATE"]').val('');
		}
	}
	
	
	// 배송비쿠폰 체크
	function fn_deli_calculate(){	
		var orderSum = parseInt($("#ORDER_SUM").val());			//주문금액
		var dlvyAmt = ${supplierInfo.DLVY_AMT };						//배송비
		var dlva_fcon = ${supplierInfo.DLVA_FCON };					//무료배송금액
		var cponCnt = parseInt($("#DLVY_CPON").val());			//배송비쿠폰 갯수
		
		//주문금액별 무료배송 체크
		if(orderSum > dlva_fcon){
			alert("이미 무료배송 상태입니다.");
			$("input:checkbox[name='CPON_YN']").prop("checked", false);
			return;
		}else{
			//체크박스선택여부
			if($("input:checkbox[name='CPON_YN']").is(":checked")){
				$("#CPON_YN").val("Y");
				
				if(cponCnt > 0){
					dlvyAmt = 0;
				}else{
					alert("사용가능한 배송쿠폰이 없습니다."+cponCnt);
					return;
				}
			}else{
				$("input:checkbox[name='CPON_YN']").prop("checked", false);
				$("#CPON_YN").val("N");
			}	
		}
		
		var totAmt = orderSum + dlvyAmt;							// 총금액
		
		$("#tdTotAmt").text(number_format(String(totAmt)) + "원");
		$("#devy_amt").text(number_format(String(dlvyAmt)) + "원");
		$("#ORDER_AMT").val(totAmt);
		//$("#DLVY_CPON").val(cponCnt);	
		$("#DLVY_AMT").val(dlvyAmt);
	}
	
</script>
	
	
	<c:set var="strActionUrl" value="${contextPath }/order" />
	<c:set var="strMethod" value="post" />

	<!-- Main Container  -->
	<div class="main-container container">
		<ul class="breadcrumb">
			<li><a href="${contextPath }/m"><i class="fa fa-home"></i></a></li>
			<li><a href="${contextPath }/m/order">주문하기</a></li>
		</ul>
	
		<div class="row">
			<!--Middle Part Start-->
			<form method="post" name="fregisterform" id="fregisterform" action="${contextPath }/m/order/insert">
				<input type="hidden" name="DLAR_DATE" value=""/>
				<input type="hidden" name="DLAR_TIME" value=""/>
				<div id="content" class="col-sm-12">
	
					<div class="row">
						<div class="col-left col-sm-6">
	
							<div class="table-responsive form-group">
								<div class="panel panel-default">
									<div class="panel-heading">
										<h4 class="panel-title">
											<i class="fa fa-shopping-cart"></i> 주문상세 정보
										</h4>
									</div>
									<div class="panel-body">
										<div class="table-responsive">
											<table class="table table-bordered">
												<thead>
													<tr>
														<td class="text-left">상품명</td>
														<td class="text-right">수량</td>
														<td class="text-right">판매가</td>
														<td class="text-right">상품구매금액</td>
													</tr>
												</thead>
												<tbody>
													<c:set var="tot_amt" value="0" />
													<c:forEach items="${obj.list }" var="list" varStatus="loop">
														<c:set var="order_qty" value="${list.ORDER_QTY}" />
														<c:set var="pd_cut_seq" value="${list.PD_CUT_SEQ}" />
														<c:set var="option_code" value="${list.OPTION_CODE}" />
														<c:if test="${!empty param.ORDER_QTY }">
															<c:set var="order_qty" value="${ param.ORDER_QTY}" />
														</c:if>
														<c:if test="${!empty param.PD_CUT_SEQ }">
															<c:set var="pd_cut_seq" value="${ param.PD_CUT_SEQ}" />
														</c:if>
														<c:if test="${!empty param.OPTION_CODE }">
															<c:set var="option_code" value="${ param.OPTION_CODE}" />
														</c:if>
														<!-- 박스할인 적용 -->
														<c:if test="${list.BOX_PDDC_GUBN eq 'PDDC_GUBN_01'}">
																<c:set var="boxsaleval" value="0" />
															</c:if>
														<c:if test="${list.BOX_PDDC_GUBN eq 'PDDC_GUBN_02'}">
															<fmt:parseNumber var="boxsalequt" value="${order_qty/list.INPUT_CNT}" integerOnly="true" />
															<c:set var="boxsaleval" value="${list.BOX_PDDC_VAL*boxsalequt}" />
														</c:if>
														<c:if test="${list.BOX_PDDC_GUBN eq 'PDDC_GUBN_03'}">
															<fmt:parseNumber var="boxsalequt" value="${order_qty/list.INPUT_CNT}" integerOnly="true" />
															<fmt:parseNumber var="boxpddcval" value="${list.REAL_PRICE*list.BOX_PDDC_VAL/100}" integerOnly="true" />
															<c:set var="boxsaleval" value="${boxpddcval*boxsalequt*list.INPUT_CNT}" />
														</c:if>
														<tr>
															<td class="text-left">
																<input type="hidden" id="BSKT_REGNO" name="BSKT_REGNO" value="${list.BSKT_REGNO }">
																<input type="hidden" id="PD_CODES" name="PD_CODES" value="${list.PD_CODE }">
																<input type="hidden" id="PD_NAMES" name="PD_NAMES" value="${list.PD_NAME}" />
																<input type="hidden" id="PD_PRICES" name="PD_PRICES" value="${list.PD_PRICE}" />
																<input type="hidden" id="PDDC_GUBNS" name="PDDC_GUBNS" value="${list.PDDC_GUBN}" />
																<input type="hidden" id="PDDC_VALS" name="PDDC_VALS" value="${list.PDDC_VAL}" />
																<input type="hidden" id="ORDER_QTYS" name="ORDER_QTYS" value="${order_qty}" />
																<input type="hidden" id="ORDER_AMTS" name="ORDER_AMTS" value="${order_qty * list.REAL_PRICE}" />
																<%-- <input type="hidden" id="PD_CUT_SEQS" name="PD_CUT_SEQS" value="${list.PD_CUT_SEQ}" /> --%>
																<input type="hidden" id="PD_CUT_SEQS" name="PD_CUT_SEQS" value="${pd_cut_seq}" />
																<input type="hidden" id="OPTION_CODES" name="OPTION_CODES" value="${option_code}" />
																<input type="hidden" id="BUNDLE_CNTS" name="BUNDLE_CNTS" value="${list.BUNDLE_CNT}" />
																<input type="hidden" id="BOX_PDDC_GUBNS" name="BOX_PDDC_GUBNS" value="${list.BOX_PDDC_GUBN}" />
																<input type="hidden" id="BOX_PDDC_VALS" name="BOX_PDDC_VALS" value="${list.BOX_PDDC_VAL}" />
																<input type="hidden" id="INPUT_CNTS" name="INPUT_CNTS" value="${list.INPUT_CNT}" />
																<b>
																<a href="${contextPath }/m/product/view/${ list.PD_CODE }" target="_blank">${list.PD_NAME }</a></b><br> 
																<c:if test="${list.PD_CUT_SEQ_UNIT ne null }"><br/>( 세절방식 : ${list.PD_CUT_SEQ_UNIT } )</c:if>
																<c:if test="${param.PD_CUT_SEQ_UNIT ne null && fn:length(param.PD_CUT_SEQ_UNIT)>0}"><br/>( 세절방식 : ${param.PD_CUT_SEQ_UNIT } )</c:if>
																
																<c:if test="${list.OPTION_NAME ne null }"><br/>( 색상 : ${list.OPTION_NAME } )</c:if>
																<c:if test="${param.OPTION_NAME ne null && fn:length(param.OPTION_NAME)>0}"><br/>( 색상 : ${param.OPTION_NAME } )</c:if>
															</td>
															<td class="text-right"><fmt:formatNumber value="${order_qty }" /></td>
															<td class="text-right"><fmt:formatNumber value="${list.REAL_PRICE }" /> 원</td>
															<td class="text-right">
																<fmt:formatNumber value="${(order_qty * list.REAL_PRICE)-boxsaleval }" /> 원
															</td>
														</tr>
														<c:set var="tot_amt" value="${tot_amt + ((order_qty * list.REAL_PRICE)-boxsaleval) }" />
													</c:forEach>
												</tbody>
												<tfoot>
													<tr>
														<td class="text-right" colspan="3"><strong>상품 구매액:</strong></td>
														<td class="text-right"><fmt:formatNumber value="${tot_amt }" /> 원</td>
													</tr>
													<tr>
														<td class="text-right" colspan="3"><strong>배송비(<fmt:formatNumber value="${supplierInfo.DLVA_FCON }" />원 이상 무료):</strong></td>
														<td class="text-right">
															<c:set var="devy_amt" value="0" />
															<c:if test="${ tot_amt < supplierInfo.DLVA_FCON }">
																<c:set var="devy_amt" value="${supplierInfo.DLVY_AMT }" />
															</c:if>															
															<c:if test="${ tot_amt >= supplierInfo.DLVA_FCON }">
																<c:set var="devy_amt" value="0" />
																<!-- <input type="text" id="DAP_YN" name="DAP_YN" value="N" /> -->
															</c:if>
															<p id="devy_amt"><fmt:formatNumber value="${devy_amt }" />원<br/></p>
															<input type="hidden" id="DAP_YN" name="DAP_YN" value="Y"/><!-- 모두 선불로 통일 20180516 -->
														</td>
													</tr>
													<tr>
														<td class="text-right" colspan="3"><strong>Total:</strong></td>
														<td class="text-right" id="tdTotAmt"><fmt:formatNumber value="${tot_amt + devy_amt }" />원</td>
													</tr>
												</tfoot>
											</table>			
											<input type="hidden" id="ORDER_SUM" name="ORDER_SUM" value="${tot_amt}"/>				<!-- 주문금액 함 -->
											<input type="hidden" id="DLVY_AMT" name="DLVY_AMT" value="${devy_amt}"/>					<!-- 배송비 -->											
											<input type="hidden" id="ORDER_AMT" name="ORDER_AMT" value="${tot_amt + devy_amt}"/>	<!-- 결제금액 -->
										</div>
									</div>
								</div>
							</div>
	
						</div>
	
						<div class="col-right col-sm-6">
							<div class="row">
	
								<div class="table-responsive form-group">
									<div class="panel panel-default">
										<div class="panel-heading">
											<h4 class="panel-title">
												<i class="fa fa-truck"></i> 배송지 정보
											</h4>
										</div>
										<div class="panel-body">
											<div class="table-responsive">
												<fieldset id="address" class="required">
												  <div class="form-group">
													<div>
														<label class="radio-inline">
															<input type="radio" id="DLAR_GUBN" name="DLAR_GUBN" value="DLAR_GUBN_01" onchange="javascript:fn_dlarGubn();" checked="checked"/>자택 
														</label>
														<label class="radio-inline">
															<input type="radio" id="DLAR_GUBN" name="DLAR_GUBN" value="DLAR_GUBN_02" onchange="javascript:fn_dlarGubn();"/>회사
														</label>
														<label class="radio-inline">
															<input type="radio" id="DLAR_GUBN" name="DLAR_GUBN" value="DLAR_GUBN_03" onchange="javascript:fn_dlarGubn();"/>최근배송지
														</label>
														<label class="radio-inline">
															<input type="radio" id="DLAR_GUBN" name="DLAR_GUBN" value="DLAR_GUBN_04" onchange="javascript:fn_dlarGubn();"/>신규
														</label>
														
														<!-- 배송비쿠폰 -->
														<c:if test="${mbinfo.DLVY_CPON ne 0 && tot_amt < supplierInfo.DLVA_FCON}">
															<label class="radio-inline" style="float: right" >
																<input type="checkbox" name="CPON_YN" value="N" id="CPON_YN" onclick="fn_deli_calculate()"> <b>배송비쿠폰사용</b>
																<input type="hidden" id="DLVY_CPON" name="DLVY_CPON" value="${mbinfo.DLVY_CPON}"/>
															</label>
														</c:if>
													</div>
												  </div>
												  <div class="form-group required">
													<label for="RECV_PERS" class="control-label">받으시는분</label>
													<input type="text" name="RECV_PERS" value="" id="RECV_PERS" required placeholder="받으시는분" class="form-control" minlength="3" maxlength="20">
												  </div>
												  <div class="form-group required">
													<label for="POST_NUM" class="control-label">우편번호</label>
													<input type="hidden" name="COM_EXTRA_ADDR" value="" id="COM_EXTRA_ADDR">
													<input type="hidden" name="COM_ALL_ADDR" value="" id="COM_ALL_ADDR">
														<div class="form-inline">
															<div class="input-group">
																<input type="text" name="POST_NUM" value="" id="POST_NUM" placeholder="우편번호" required readonly="readonly" style="width: 70px;" size="5" class="form-control" maxlength="6">								
																<span class="input-group-btn">
																	<input type="button" class="btn btn-primary" value="주소검색" onclick="win_zip('fregisterform', 'POST_NUM', 'BASC_ADDR', 'DTL_ADDR', 'COM_EXTRA_ADDR', 'COM_ALL_ADDR');">
																</span>
															</div>
														</div> 
												  </div>
												  <div class="form-group required">
													<label for="BASC_ADDR" class="control-label">기본주소</label>
													<input type="text" name="BASC_ADDR" value="" id="BASC_ADDR" required readonly="readonly" class="form-control" placeholder="기본주소">
												  </div>
												  <div class="form-group required">
													<label for="DTL_ADDR" class="control-label">상세주소</label>
													<input type="text" name="DTL_ADDR" value="" id="DTL_ADDR" required class="form-control" placeholder="상세주소">
												  </div>
												  <div class="form-group required">
													<label for="RECV_TELN1" class="control-label">전화번호</label>
													<div class="form-inline">
											         	<input type="hidden" name="RECV_TELN" value="" id="RECV_TELN" required class="form-control input-sm"  maxlength="20">
											         	<input type="text" name="RECV_TELN1" value="" id="RECV_TELN1" required class="form-control input-sm"  style="width:50px; display: inline-block;" maxlength="4">&nbsp;-&nbsp;
											         	<input type="text" name="RECV_TELN2" value="" id="RECV_TELN2" required class="form-control input-sm"  style="width:50px; display: inline-block;" maxlength="4">&nbsp;-&nbsp;
											         	<input type="text" name="RECV_TELN3" value="" id="RECV_TELN3" required class="form-control input-sm"  style="width:50px; display: inline-block;" maxlength="4">
												    </div>
												  </div>
												  <div class="form-group required">
													<label for="RECV_CPON1" class="control-label">휴대폰번호</label>
													<div class="form-inline">
											             <input type="hidden" name="RECV_CPON" value="" id="RECV_CPON" required class="form-control input-sm" maxlength="20">
											             <input type="text" name="RECV_CPON1" value="" id="RECV_CPON1" required class="form-control input-sm" style="width:50px; display: inline-block;"  maxlength="4">&nbsp;-&nbsp;
											             <input type="text" name="RECV_CPON2" value="" id="RECV_CPON2" required class="form-control input-sm" style="width:50px; display: inline-block;"  maxlength="4">&nbsp;-&nbsp;
											             <input type="text" name="RECV_CPON3" value="" id="RECV_CPON3" required class="form-control input-sm" style="width:50px; display: inline-block;"  maxlength="4">
												    </div>
												  </div>
												  <div class="form-group">
													<label for="DTL_ADDR" class="control-label">배송 메세지</label>
													<textarea id="DLVY_MSG" name="DLVY_MSG" rows="2" class="form-control"></textarea>
												  </div>
												  <div class="form-group" id="dlarGugn5">
													<label for="DLAR_TIME_CHK_01" class="control-label">출고시간</label>
													<div class="form-inline">
														<label class="radio-inline">
															<input type="radio" id="DLAR_DATE_CHK" name="DLAR_DATE_CHK" value="DLAR_DATE_01" checked="checked" onchange="javascript:fn_dlarDate();"/>명일
														</label>
														<label class="radio-inline">
															<input type="radio" id="DLAR_DATE_CHK" name="DLAR_DATE_CHK" value="DLAR_DATE_02" onchange="javascript:fn_dlarDate();"/>당일
														</label>
														&nbsp;&nbsp;&nbsp;
														<select id="DLAR_TIME_CHK_01" class="form-control" onchange="javascript:fn_dlarTime();">
															<option value="nothing">선택</option>
															<option value="PM 1시~2시">PM 1시~2시</option>
															<option value="PM 2시~3시">PM 2시~3시</option>
															<option value="PM 3시~4시">PM 3시~4시</option>
															<option value="PM 4시~6시">PM 4시~6시</option>
											        	</select>
										        	</div>
												  </div>
												  <div class="form-group required" id="dlarGugn1234">
													<label for="input-payment-zone" class="control-label">배송시간</label>
													<div class="form-inline">
														<select name="DLAR_TIME_CHK_02" id="DLAR_TIME_CHK_02" class="form-control" onchange="javascript:fn_dlarTime();">
															<option value="nothing">선택</option>
															<option value="오전">오전</option>
															<option value="오후">오후</option>
															<option value="5시~6시">5시~6시</option>
														</select>
													</div>
										        	<p style="font-size:12px;margin:3px;margin-top:10px">
										        		고객님의 상품도착시간이 당일 배송수량과 교통상황에 따라 요청하신 시간에 도착하지 못할수 있습니다. 
										        		양해부탁드립니다.
										        	</p>
												  </div>
												</fieldset>
											</div>
											<div class="buttons">
												<div class="pull-right">
											        <input type="button" id="btnSave" class="btn btn btn-primary" value="결제하기"> &nbsp;
											        <a href="#" id="btnCancel" class="btn btn-default">주문취소</a> &nbsp;
												</div>
											</div>
										</div>
									</div>
									<div>								
										<!-- <img src="http://www.cjfls.co.kr/resources/img/order/dlvy_info_201905.png"> -->
									</div>
								</div>
	
							</div>
						</div>
					</div>
				</div>
				<!--Middle Part End -->
			</form>
		</div>
	</div>
	<!-- //Main Container -->
	