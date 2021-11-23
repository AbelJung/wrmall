<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ include file="/layout/inc/taglib.jsp" %>  

<c:set var="strActionUrl" value="${contextPath }/m/wishList" />
<c:set var="strMethod" value="post" />

	<script type="text/javascript">
	$(function() {		
		$(".btnDeliveryView").click(function(){
			alert("배송 조회 준비중입니다.");
		});
		
		//전체선택 체크박스 클릭 
		$("#CHK_ALL").click(function(){ //만약 전체 선택 체크박스가 체크된상태일경우 
			if($("#CHK_ALL").prop("checked")) { //해당화면에 전체 checkbox들을 체크해준다 
				$("input[type=checkbox]").prop("checked",true); // 전체선택 체크박스가 해제된 경우 
			} else { //해당화면에 모든 checkbox들의 체크를해제시킨다. 
				$("input[type=checkbox]").prop("checked",false); 
			} 
		});
	
	});
	
	function chk_delete(){
		var checkboxValues = [];
	
		//each로 loop를 돌면서 checkbox의 check된 값을 가져와 담아준다.
		$("input:checkbox[name=CHK_WISH]:checked").each(function(){
			checkboxValues.push($(this).val());
			//checkboxValues += $(this).val()+":";
		});
		if(checkboxValues==''||checkboxValues==null){
			checkboxValues.push("");
			
			alert("삭제할 주문내역을 선택해 주세요.");
			return;
		}
		var allData = { "checkArray": checkboxValues };
		
		if (confirm('선택한 결제전 상태의 주문내역이 사라집니다.\n삭제하시겠습니까?')) {
	        // Yes click
			$.ajax({
			    type: 'GET',
			    data: allData,
			    url: '${contextPath }/order/updateDelete',
			    success: function (data) {   	
			    	 location.reload();
			   
			    },
			    error:function(request,status,error){
			         console.log('error : '+error+"\n"+"code : "+request.status+"\n");
			    }
			});
	   } else {
	       // no click
	       return;
		}
		//post_to_url("${contextPath }/order/wishList",allData,"get" );
		
		//post_to_url("https://logins.daum.net/accounts/login.do", {"id":"gmltjs2808", "pw":"my10182756"});
	}
	
	function post_to_url(path, params, method) {
		method = method || "post"; // Set method to post by default, if not specified.
		// The rest of this code assumes you are not using a library.
		// It can be made less wordy if you use one.
		var form = document.createElement("form");
		form.setAttribute("method", method);
		form.setAttribute("action", path);
		for(var key in params) {
			var hiddenField = document.createElement("input");
			hiddenField.setAttribute("type", "hidden");
			
			hiddenField.setAttribute("name", key);
			hiddenField.setAttribute("value", params[key]);
			alert(params[key]);
			form.appendChild(hiddenField);
		}
		document.body.appendChild(form);
		form.submit();
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
			<div id="content" class="col-sm-12">
				<h2 class="title">주문내역<small class="ml_5"> | 주문 상태, 배송상태 등 주문 내역을 조회합니다.</small></h2>
				<div class="table-responsive form-group">
					<table class="table table-bordered">
						<thead>
							<tr>
								<td class="text-center"><input type="checkbox" id="CHK_ALL" name="CHK_ALL"/></td>
								<td class="text-center">번호</td>
								<td class="text-center">주문일자</td>
								<td class="text-center">상품명</td>
								<td class="text-center">결제금액</td>
								<td class="text-center">주문상태</td>
								<td class="text-center">배송조회</td>
							</tr>
						</thead>
						<tbody>
							<c:forEach items="${obj.list }" var="list" varStatus="loop">
							<tr>
								<td class="text-center">
									<c:if test="${list.ORDER_CON eq 'ORDER_CON_01'}">
										<input type="checkbox" id="CHK${loop.count }" name="CHK_WISH" value="${list.ORDER_NUM }"/>
									</c:if>
								</td>
								<td class="text-center">
									<input type="hidden" name="PD_CODE" value="${list.ORDER_NUM }">
									<a href="${contextPath}/m/order/view/${list.ORDER_NUM }"><b>${list.ORDER_NUM }</b></a>
								</td>
								<td class="text-center">${list.ORDER_DATE }</td>
								<td class="text-left">
									<a href="${contextPath}/m/order/view/${list.ORDER_NUM }"><b>${list.PD_NAME }
									<c:if test="${list.count > 0 }">
										&nbsp; 외 ${list.count}&nbsp;종
									</c:if>
									</b></a>
								</td>
								<td class=text-right><fmt:formatNumber value="${list.ORDER_AMT }" /></td>
								<td class="text-center">${list.ORDER_CON_NM }</td>
								<td class="text-center">
									<c:if test="${list.ORDER_CON ne 'ORDER_CON_01' && list.ORDER_CON ne 'ORDER_CON_04' && list.ORDER_CON ne 'ORDER_CON_07'}">
										<button type="button" class="btn btn-xs btn-default btnDeliveryView">조회</button>
									</c:if>
								</td>
							</tr>
							</c:forEach>
							<c:if test="${fn:length(obj.list) == 0 }">
								<tr>
									<td colspan="7">조회된 주문내역이 없습니다.</td>
								</tr>
							</c:if>
						</tbody>
					</table>
				</div>

				<div class="buttons">
					<div class="pull-right">
						<button type="button" onclick="chk_delete();" class="btn btn-danger pull-right" style="margin-right:5px;">선택 삭제</button>
					</div>
				</div>
			</div>
			<!--Middle Part End -->
	
		</div>
	</div>
	</form>
	<!-- //Main Container -->

	<spform:form name="orderFrm" id="orderFrm" method="${strMethod }" action="${strActionUrl }" class="form-horizontal">
	<input type="hidden" id="BSKT_REGNO_LIST" name="BSKT_REGNO_LIST" value="" /><!-- 상태 변경 체크 항목 -->
	<input type="hidden" id="BSKT_REGNO" name="BSKT_REGNO" value="" /><!-- 선택 장바구니 등록번호-->
	<input type="hidden" id="STATE_GUBUN" name="STATE_GUBUN" value="" /><!-- 산태 변경 구분 - 장바구니이동인지 삭제인지 -->
	<input type="hidden" id="PD_CUT_SEQ_LIST" name="PD_CUT_SEQ_LIST" value="" /><!-- 산태 변경 구분 - 장바구니이동인지 삭제인지 -->
	<input type="hidden" id="OPTION_CODE_LIST" name="OPTION_CODE_LIST" value="" /><!-- 산태 변경 구분 - 장바구니이동인지 삭제인지 -->
	</spform:form>


