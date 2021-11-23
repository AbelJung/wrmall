<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/layout/inc/taglib.jsp" %>  

<script language="javascript" src="https://xpay.uplus.co.kr/xpay/js/xpay_crossplatform.js" type="text/javascript"></script>
<script type="text/javascript">
	/*
	* 수정불가.
	*/
	var LGD_window_type = '<c:out value="${LGD_WINDOW_TYPE}" />';
		
	/*
	* 수정불가
	*/
	function launchCrossPlatform(){
		lgdwin = openXpay(document.getElementById('LGD_PAYINFO'), '<c:out value="${CST_PLATFORM}" />', LGD_window_type, null, "", "");
	}
	
	/*
	* FORM 명만  수정 가능
	*/
	function getFormObject() {
		return document.getElementById("LGD_PAYINFO");
	}
	
	/*
	 * 인증결과 처리
	 */
	function payment_return() {
		var fDoc;
		fDoc = lgdwin.contentWindow || lgdwin.contentDocument;
		if (fDoc.document.getElementById('LGD_RESPCODE').value == "0000") {
				document.getElementById("LGD_PAYKEY").value = fDoc.document.getElementById('LGD_PAYKEY').value;
				document.getElementById("LGD_PAYINFO").target = "_self";
				document.getElementById("LGD_PAYINFO").action = "${contextPath}/order/payRes";
				document.getElementById("LGD_PAYINFO").submit();
		} else {
			alert("LGD_RESPCODE (결과코드) : " + fDoc.document.getElementById('LGD_RESPCODE').value + "\n" + "LGD_RESPMSG (결과메시지): " + fDoc.document.getElementById('LGD_RESPMSG').value);
			closeIframe();
		}
	}
</script>

	<!-- Main Container  -->
	<div class="main-container container">
		<ul class="breadcrumb">
			<li><a href="${contextPath }/m"><i class="fa fa-home"></i></a></li>
			<li><a href="${contextPath }/m/order/wishList">배송/주문조회</a></li>
		</ul>
	
		<div class="row">
			<!--Middle Part Start-->
			<form name="LGD_PAYINFO" id="LGD_PAYINFO"  action="${contextPath}/order/payRes" method="POST">
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
													<c:forEach items="${tb_odinfoxm.list }" var="list"
														varStatus="loop">
														<!-- 박스할인율 계산  -->
														<c:if test="${list.BOX_PDDC_GUBN eq 'PDDC_GUBN_01'}">
															<c:set var="boxsaleval" value="0" />
														</c:if>
														<c:if test="${list.BOX_PDDC_GUBN eq 'PDDC_GUBN_02'}">
															<fmt:parseNumber var="boxsalequt" value="${list.ORDER_QTY/list.INPUT_CNT}" integerOnly="true" />
															<c:set var="boxsaleval" value="${list.BOX_PDDC_VAL*boxsalequt}" />
														</c:if>
														<c:if test="${list.BOX_PDDC_GUBN eq 'PDDC_GUBN_03'}">
															<fmt:parseNumber var="boxsalequt" value="${list.ORDER_QTY/list.INPUT_CNT}" integerOnly="true" />
															<fmt:parseNumber var="boxpddcval" value="${list.REAL_PRICE*list.BOX_PDDC_VAL/100}" integerOnly="true" />
															<c:set var="boxsaleval" value="${boxpddcval*boxsalequt*list.INPUT_CNT}" />
														</c:if>
														<!-- .//박스할인율계산 -->
														<tr>
															<td class="text-left">
																<input type="hidden" id="ORDER_NUM" name="ORDER_NUM" value="${list.ORDER_NUM }"> 
																<input type="hidden" id="BSKT_REGNO" name="BSKT_REGNO" value="${list.BSKT_REGNO }"> 
																<input type="hidden" id="PD_CODES" name="PD_CODES" value="${list.PD_CODE }">
																<input type="hidden" id="PD_NAMES" name="PD_NAMES" value="${list.PD_NAME}" /> 
																<input type="hidden" id="PD_PRICES" name="PD_PRICES" value="${list.PD_PRICE}" />
																<input type="hidden" id="PDDC_GUBNS" name="PDDC_GUBNS" value="${list.PDDC_GUBN}" /> 
																<input type="hidden" id="PDDC_VALS" name="PDDC_VALS" value="${list.PDDC_VAL}" />
																<input type="hidden" id="ORDER_QTYS" name="ORDER_QTYS" value="${list.ORDER_QTY}" /> 
																<input type="hidden" id="ORDER_AMTS" name="ORDER_AMTS" value="${list.ORDER_AMT}" /> 
																<input type="hidden" id="PD_CUT_SEQS" name="PD_CUT_SEQS" value="${ list.PD_CUT_SEQ }" /> 
																<input type="hidden" id="OPTION_CODES" name="OPTION_CODES" value="${ list.OPTION_CODE }" /> <!-- 박스할인율 --> 
																<input type="hidden" id="BOX_PDDC_GUBNS" name="BOX_PDDC_GUBNS" value="${list.BOX_PDDC_GUBN}" /> 
																<input type="hidden" id="BOX_PDDC_VALS" name="BOX_PDDC_VALS" value="${list.BOX_PDDC_VAL}" /> 
																<input type="hidden" id="INPUT_CNTS" name="INPUT_CNTS" value="${list.INPUT_CNT}" /> 
																<b><a
																	href="${contextPath }/m/product/view/${ list.PD_CODE }"
																	target="_blank">${list.PD_NAME }</a></b><br> 
																	<c:if test="${ list.PD_CUT_SEQ ne null }">(세절방식 : ${ list.PD_CUT_SEQ_UNIT})</c:if>
																	<c:if test="${ list.OPTION_CODE ne null }">(색상 : ${ list.OPTION_NAME})</c:if>
															</td>
															<td class="text-right"><fmt:formatNumber value="${list.ORDER_QTY }" /></td>
															<td class="text-right"><fmt:formatNumber value="${list.REAL_PRICE }" /> 원</td>
															<td class="text-right">
																<fmt:formatNumber value="${(list.ORDER_QTY * list.REAL_PRICE)-boxsaleval }" /> 원
															</td>
														</tr>
														<c:set var="tot_amt" value="${tot_amt + ((list.ORDER_QTY * list.REAL_PRICE)-boxsaleval) }" />
													</c:forEach>
												</tbody>
												<tfoot>
													<tr>
														<td class="text-right" colspan="3"><strong>상품 구매액:</strong></td>
														<td class="text-right"><fmt:formatNumber value="${tot_amt }" /> 원</td>
													</tr>
													<tr>
														<td class="text-right" colspan="3"><strong>배송비:</strong></td>
														<td class="text-right">
															<fmt:formatNumber value="${tb_odinfoxm.DLVY_AMT }" pattern="#,###" /> 원 
															<c:if test="${tb_odinfoxm.DLVY_AMT != 0 }">
																<c:if test="${tb_odinfoxm.DAP_YN eq 'Y' }">
																	<br />배송비 결제
																</c:if>
																<c:if test="${tb_odinfoxm.DAP_YN eq 'N' }">
																	<br />배송비 착불
																</c:if>																
															</c:if>
														</td>
													</tr>
													<c:if test="${tb_odinfoxm.CPON_YN eq 'Y' }">
													<tr>
														<td class="text-right" colspan="3"><strong>쿠폰사용:</strong></td>
														<td class="text-right">배송비쿠폰</td>
													</tr>
													</c:if>
													<tr>
														<td class="text-right" colspan="3"><strong>Total :</strong></td>
														<td class="text-right"><fmt:formatNumber value="${tot_amt + tb_odinfoxm.DLVY_AMT }" />원</td>
													</tr>
												</tfoot>
											</table>
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
												<i class="fa fa-credit-card"></i> 결제 정보
											</h4>
										</div>
										<div class="panel-body">
											<div class="table-responsive">
												<table class="table table-bordered">
													<tbody>
														<tr>
															<th scope="row">결제금액</th>
															<td><fmt:formatNumber value="${tb_odinfoxm.ORDER_AMT}" pattern="#,###" /> 원</td>
															<th scope="row">결제수단</th>
															<td>
																<input type="hidden" id="payType" value="${tb_odinfoxm.PAY_METD_NM}" />
																${tb_odinfoxm.PAY_METD_NM}
															</td>
														</tr>
														<tr>
															<th scope="row">주문상태</th>
															<td colspan="3" id="ORDER_CON_NM">${tb_odinfoxm.ORDER_CON_NM}</td>
														</tr>
														<c:if test="${tb_odinfoxm.ORDER_CON eq 'ORDER_CON_04'}">
															<tr>
																<th>취소사유</th>
																<td colspan="3">${tb_odinfoxm.CNCL_MSG}</td>
															</tr>
															<tr>
																<th>취소상태</th>
																<td colspan="3">${tb_odinfoxm.CNCL_CON_NM}</td>
															</tr>
														</c:if>
												</table>
											</div>
										</div>
									</div>
								</div>
	
								<div class="table-responsive form-group">
									<div class="panel panel-default">
										<div class="panel-heading">
											<h4 class="panel-title">
												<i class="fa fa-truck"></i> 배송지 정보
											</h4>
										</div>
										<div class="panel-body">
											<div class="table-responsive">
												<table class="table table-bordered">
													<tbody>
														<tr>
															<th scope="row">배송지정보</th>
															<td colspan="3">${tb_oddlaixm.DLAR_GUBN_NM}</td>
														</tr>
														<tr>
															<th scope="row">받으시는분</th>
															<td colspan="3">${tb_oddlaixm.RECV_PERS}</td>
														</tr>
														<tr>
															<th scope="row">주소</th>
															<td colspan="3"><label for="POST_NUM"
																class="sound_only">우편번호</label> (${tb_oddlaixm.POST_NUM})
																${tb_oddlaixm.BASC_ADDR} ${tb_oddlaixm.DTL_ADDR}</td>
														</tr>
														<tr>
															<th scope="row">전화번호</th>
															<td>${tb_oddlaixm.RECV_TELN}</td>
															<th scope="row">휴대폰번호</th>
															<td>${tb_oddlaixm.RECV_CPON}</td>
														</tr>
														<tr>
															<th scope="row">배송 메세지</th>
															<td colspan="6">${tb_oddlaixm.DLVY_MSG}</td>
														</tr>
														<tr class="tb_line">
															<th>배송시간</th>
															<td colspan="6">
																<div class="form-inline">
																	<fmt:parseDate value="${ tb_oddlaixm.DLAR_DATE }"
																		var="noticePostDate" pattern="yyyyMMdd" />
																	<fmt:formatDate value="${noticePostDate}"
																		pattern="yyyy-MM-dd" />
																	&nbsp;&nbsp;${ tb_oddlaixm.DLAR_TIME }
																</div>
															</td>
														</tr>
												</table>
											</div>
											<div class="buttons">
												<a href="${contextPath }/m/order/wishList"
													class="btn btn-sm btn-default pull-right"
													style="height: 48px; padding-top: 14px;">목록</a>
												<c:if test="${tb_odinfoxm.ORDER_CON eq 'ORDER_CON_01'}">
													<a onclick="javascript:;" id="btnAuth"
														class="btn btn-sm btn-primary pull-right"
														style="margin-right: 3px; height: 48px; padding-top: 14px;">신용카드</a>
													<a onclick="javascript:;" id="btnPay2Call"
														class="btn btn-sm btn-primary pull-right"
														style="margin-right: 3px; height: 48px;">실시간 계좌이체</br>무통장입금
													</a>
													<!-- <a onclick="javascript:;" id="btnPay2Call" class="btn btn-sm btn-success pull-right" style="margin-right:5px;">무통장 입금(가상계좌)</a> -->
												</c:if>
												<c:if test="${tb_odinfoxm.ORDER_CON eq 'ORDER_CON_02'}">
													<a onclick="javascript:cancel_popup();"
														class="btn btn-sm btn-danger pull-right"
														style="margin-right: 3px; height: 48px; padding-top: 14px;">주문취소</a>
												</c:if>
												<a type="button" class="btn btn-sm btn-warning pull-right"
													onclick="fn_inst_bskt();"
													style="margin-right: 3px; height: 48px; padding-top: 14px;">장바구니 담기<!-- 장바구니에 저장하고</br>상품추가 주문하기 -->
												</a>
											</div>
										</div>
									</div>
									<div>
										<img src="${contextpath }/resources/images/mall/deleveryinfo.png">
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
	
	<!-- 결제 기능 추가 -->
	<div class="modal fade" id="divPayModal" tabindex="-1" role="dialog" aria-labelledby="myLargeModalLabel">
		<div class="modal-dialog modal-lg" role="document">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal" aria-label="Close" onClick="window.location.reload()">
						<span aria-hidden="true">&times;</span>
					</button>
					<!-- <h4 class="modal-title" id="myModalLabel">결제창</h4> -->
				</div>
				<div class="modal-body" style="background-color: #ECF0F5;">
					<iframe id="ifrmPayModal" src="" > </iframe>	<!-- style="border:0; width:100%; height:600px;" -->
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" onclick="window.location.reload()">닫기</button>
				</div>
			</div>
		</div>
	</div>
	
	<!-- Modal -->
	<div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
		<div class="modal-dialog" role="document">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal" aria-label="Close" onClick="window.location.reload()">
						<span aria-hidden="true">&times;</span>
					</button>
					<h4 class="modal-title" id="myModalLabel">무통장 입금방법</h4>
				</div>
				<div class="modal-body">
	            <form role="form">            	
	            	<img src="${contextPath}/resources/img/order/bank_info.jpg" style="width:100%;height:auto;">
	            </form>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal"  onclick="fnPayCall2OkDiv()">확인</button>
				</div>
			</div>
		</div>  	
	</div>
	
	<!-- Modal -->
	<div class="modal fade" id="fnPayCall2OkDiv" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
		<div class="modal-dialog" role="document">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal" aria-label="Close" onClick="window.location.reload()">
						<span aria-hidden="true">&times;</span>
					</button>
					<h4 class="modal-title" id="myModalLabel">무통장 입금확인</h4>
				</div>
				<div class="modal-body">
	            <form role="form">            	
	            	<img src="${contextPath}/resources/img/order/bank_info2.png" style="width:100%;height:auto;">
	            </form>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal">확인</button>
				</div>
			</div>
		</div>  	
	</div>
	

<script type="text/javascript">

$(function() {
	$("#btnAuth").click(function(){
		//launchCrossPlatform();
		var url = '${contextPath}/order/orderPay?ORDER_NUM=${tb_odinfoxm.ORDER_NUM }';
		$('#ifrmPayModal').attr('src', url);
		$("#divPayModal").modal('show');
	});
	$("#btnPay2Call").click(function(){
		//launchCrossPlatform();
		var url = '${contextPath}/order/orderPayCash?ORDER_NUM=${tb_odinfoxm.ORDER_NUM }';
		$('#myModal').modal('show');
	});
	
	//launchCrossPlatform();
});

function fnPayClose() {
	$("#divPayModal").modal('hide');
	location.reload();
}

function cancel_popup(){
	if($("#payType").val()=='계좌이체'){
		window.open("${contextPath }/m/order/cancel/popup2?ORDER_NUM=${tb_odinfoxm.ORDER_NUM}&PAY_MDKY=${tb_odinfoxm.PAY_MDKY}", "_blank", "width=500, height=230");
	}else{
	 	window.open("${contextPath }/m/order/cancel/popup?ORDER_NUM=${tb_odinfoxm.ORDER_NUM}&PAY_MDKY=${tb_odinfoxm.PAY_MDKY}", "_blank", "width=500, height=230");
	}
	
 }
function fnPayCall2OkDiv(){
	$('#fnPayCall2OkDiv').modal('show');
 }
 
function fn_inst_bskt(){
	
	$.ajax({
		type: "POST",
	    dataType: 'json',
		url: '${contextPath}/order/insertBsktAjax.json',
		data: $("#LGD_PAYINFO").serialize(),
		success: function (data) {
			alert("장바구니에 등록되었습니다.")
		}, error: function (jqXHR, textStatus, errorThrown) {
			alert("처리중 에러가 발생했습니다. 관리자에게 문의 하세요.(error:"+textStatus+")");
		}
	});
}
</script>
	
