<%@ page trimDirectiveWhitespaces="true"%>
<%@ page contentType="text/html; charset=utf-8"%>
<%@ include file="/layout/inc/taglib.jsp" %>  

<c:set var="arrCagoIdPath" value="${fn:split(rtnCagoInfo.CAGO_ID_PATH, '>') }" /> 
<c:set var="arrCagoNmPath" value="${fn:split(rtnCagoInfo.CAGO_NM_PATH, '>') }" /> 

<script>
	$(function() {	
		$('.product-image-zoom').each(function(n){
			$(this).error(function(){
				$(this).attr('src', '${contextPath }/resources/images/mall/goods/noimage.png');
				$(this).attr('style', 'width:100%;');
			});
		});
		$('.thumb-slider-img').each(function(n){
			$(this).error(function(){
				$(this).attr('src', '${contextPath }/resources/images/mall/goods/noimage.png');
				$(this).attr('style', 'width:100%;');
			});
		 });
		$('.goodsImg').each(function() {
			$(this).error(function(){
				$(this).attr('src', 'https://www.cjfls.co.kr/resources/images/mall/goods/noimage.png');
				$(this).attr('style', 'width:100%;');
			});
		 });
		
	    $(document).on("change", "#CT_QTY", function() {
	    	price_calculate();
	    });
	    
	    $('#').val()
	    // 수량변경 및 삭제
		$('.btnQty').click(function(){
	        var mode = $(this).text();
	        var this_qty, max_qty = 9999, min_qty = 1;
	        var $el_qty = $("input[name^=CT_QTY]");
	        var stock = 100;

	        switch(mode) {
	            case "+":
	                this_qty = parseInt($el_qty.val().replace(/[^0-9]/, "")) + 1;

	                if(this_qty > max_qty) {
	                    this_qty = max_qty;
	                    alert("최대 구매수량은 "+number_format(String(max_qty))+" 입니다.");
	                }

	                $el_qty.val(this_qty);
	                price_calculate();
	                break;

	            case "-":
	                this_qty = parseInt($el_qty.val().replace(/[^0-9]/, "")) - 1;
	                if(this_qty < min_qty) {
	                    this_qty = min_qty;
	                    alert("최소 구매수량은 "+number_format(String(min_qty))+" 입니다.");
	                }
	                $el_qty.val(this_qty);
	                price_calculate();
	                break;

	            default:
	                alert("올바른 방법으로 이용해 주십시오.");
	                break;
	        }
	    });
	    
		// 장바구니 이벤트
		$('#btnCart').click(function(){

	       	<c:if test="${empty USER.MEMB_ID}">
				alert("로그인이 필요합니다.");
				location.href = "${contextPath}/m/user/loginForm";
				return false;
			</c:if>
			
			<c:if test="${result.SALE_CON eq 'SALE_CON_02' }">
				alert("품절된 상품은 장바구니에 담을 수 없습니다.");
				return false;
			</c:if>
			
			if($('#PD_CUT_SEL').val()=='nothing'){
				alert("세절방식을 선택해 주세요.");
				return false;
			}
			
			if($('.PD_OPTION_SEL').val()=='nothing'){
				alert("옵션을 선택해 주세요.");
				return false;
			}
			
	    	$('#PD_QTY').val($('#CT_QTY').val());
	    	
	    	<c:if test='${pdcut_cnt >0 }'>
			 	$('#PD_CUT_SEQ').val($('#PD_CUT_SEL').val());
			</c:if>
			
			<c:if test='${option_cnt >0 }'>
		 	$('#OPTION_CODE').val($('.PD_OPTION_SEL').val());
			</c:if>

	        <c:if test="${!empty USER.MEMB_ID}">
	      		//회원
		    	$.ajax({
		    		type: "POST",
		    		url: '${contextPath}/goods/basketInst',
		    		data: $("#bkInstFrm").serialize(),
		    		success: function (data) {

		    			// 장바구니 등록 여부
		    			if (data == '0') {
		    				//alert("장바구니에 등록되었습니다.");
		    				addProductNotice('장바구니 추가', '', '<h3>[' + product_name + '] 장바구니에 등록되었습니다.</h3>', 'success');
		    			}else{
		    				//alert("장바구니에 이미 등록된 상품 입니다.");
							addProductNotice('장바구니 추가', '', '<h3 style="color:red;">장바구니에 이미 등록된 상품 입니다.</h3>', 'success');
		    			}
		    		}, error: function (jqXHR, textStatus, errorThrown) {
		    			alert("처리중 에러가 발생했습니다. 관리자에게 문의 하세요.(error:"+textStatus+")");
		    		}
		    	});
	        </c:if>	    	
		});
		
		// 구매하기 이벤트
		$('#btnBuy').click(function(){
	       	<c:if test="${empty USER.MEMB_ID}">
				alert("로그인이 필요합니다.");
				location.href = "${contextPath}/m/user/loginForm";
				return false;
			</c:if>

			<c:if test="${result.SALE_CON eq 'SALE_CON_02' }">
				alert("품절된 상품은 구매 할 수 없습니다.");
				return false;
			</c:if>
			
			if($('#PD_CUT_SEL').val()=='nothing'){
				alert("세절방식을 선택해 주세요.");
				return false;
			}
			
			if($('.PD_OPTION_SEL').val()=='nothing'){
				alert("옵션을 선택해 주세요.");
				return false;
			}
			
			<c:if test='${result.QNT_LIMT_USE=="Y"}'>
				var pd_qty_val = $("#CT_QTY  option:selected").text();
				$('#PD_QTY').val(pd_qty_val);    //CT_QTY : 신청수량
				$('#ORDER_QTY').val(pd_qty_val);
			</c:if>
			
			<c:if test='${result.QNT_LIMT_USE!="Y"}'>
				$('#PD_QTY').val($('#CT_QTY').val());    //CT_QTY : 신청수량
				$('#ORDER_QTY').val($('#CT_QTY').val());
			</c:if>

			<c:if test='${pdcut_cnt >0 }'>
			 	$('#PD_CUT_SEQ').val($('#PD_CUT_SEL').val());
			 	$('#PD_CUT_SEQ_UNIT').val($('#PD_CUT_SEL option:selected').text());
			</c:if>
			
			/* 여기 */
			<c:if test='${option_cnt >0 }'>
			 	$('#OPTION_CODE').val($('.PD_OPTION_SEL').val());
			 	$('#OPTION_NAME').val($('.PD_OPTION_SEL option:selected').text());
			</c:if>			
			
			$('#bkInstFrm').attr('action', '${contextPath}/m/order/buy').submit();
		});

	 	// 관심상품 이벤트
		$('#btnWish').click(function(){
			<c:if test="${empty USER.MEMB_ID}">
				alert("로그인이 필요합니다.");
				location.href = "${contextPath}/m/user/loginForm";
				return false;
			</c:if>

			<c:if test="${result.SALE_CON eq 'SALE_CON_02' }">
				alert("품절된 상품은 관심상품에 등록 할 수 없습니다.");
				return false;
			</c:if>
			
			if($('#PD_CUT_SEL').val()=='nothing'){
				alert("세절방식을 선택해 주세요.");
				return false;
			}
			
			if($('.PD_OPTION_SEL').val()=='nothing'){
				alert("옵션을 선택해 주세요.");
				return false;
			}
			
	    	$('#PD_QTY').val($('#CT_QTY').val());
	    	
	    	<c:if test='${pdcut_cnt >0 }'>
			 	$('#PD_CUT_SEQ').val($('#PD_CUT_SEL').val());
			</c:if>
			
			<c:if test='${option_cnt >0 }'>
			 	$('#OPTION_CODE').val($('.PD_OPTION_SEL').val());
			</c:if>
	    	
	    	$.ajax({
	    		type: "POST",
	    		url: '${contextPath}/goods/wishInst',
	    		data: $("#bkInstFrm").serialize(),
	    		success: function (data) {

	    			// 관심상품 등록 여부
	    			if (data == '0') {
	    				//alert("관심상품에 등록되었습니다.");
						addProductNotice('관심상품 추가', '', '<h3>관심상품에 등록되었습니다.</h3>', 'success');
	    			}else{
	    				//alert("관심상품에 이미 등록되어있습니다.");
						addProductNotice('관심상품 추가', '', '<h3 style="color:red;">관심상품에 이미 등록되어있습니다.</h3>', 'success');
	    			}
	    		}, error: function (jqXHR, textStatus, errorThrown) {
	    			alert("처리중 에러가 발생했습니다. 관리자에게 문의 하세요.(error:"+textStatus+")");
	    		}
	    	});
		});
	});
	 
	// 가격계산
	function price_calculate()
	{
	    var PD_PRICE = parseInt($("input#REAL_PRICE").val());
	    if(isNaN(PD_PRICE))
	        return;

	    if(parseInt($('#CT_QTY').val())<1||$('#CT_QTY').val()==''){
	    	alert("최소 구매수량은 1 입니다.");
	    	$('#CT_QTY').val(1);	    	
	    }
	    
	    if(parseInt($('#CT_QTY').val())>parseInt($('#LIMIT_QTY').val())){
	    	alert("한정 구매수량은 "+ $('#LIMIT_QTY').val() +" 입니다.");
	    	$('#CT_QTY').val($('#LIMIT_QTY').val());	    	
	    }

	  //var final_qty = 0;
		var final_total = 0;
	    <c:if test='${result.QNT_LIMT_USE=="Y"}'>
		    var qty = $("#CT_QTY  option:selected").text()
		    var total = PD_PRICE*qty;
			final_total = total;
	    </c:if>
	    
	    <c:if test='${result.QNT_LIMT_USE==null||result.QNT_LIMT_USE==""||result.QNT_LIMT_USE=="N"}'>
		    var qty = parseInt($("#CT_QTY").val());			//신청수량
		    var total = parseInt(PD_PRICE * qty);
			final_total = total;
	    </c:if>
	    
	    //박스입수량
	    var inputcnt = parseInt('${result.INPUT_CNT}');			//입수량
	    var boxpddcval = parseInt('${result.BOX_PDDC_VAL}');	//박스할인값
	    var boxsalequt = parseInt(qty/inputcnt);				//몫
	    var boxsaleval = parseInt(boxsalequt*boxpddcval);
	    var realtotal = 0;
	    
	    if('${result.BOX_PDDC_GUBN}'=='PDDC_GUBN_02'){
	    	realtotal = total-boxsaleval;	    	
	    }else if('${result.BOX_PDDC_GUBN}'=='PDDC_GUBN_03'){    	
	    	boxpddcval = PD_PRICE* boxpddcval/100;	//할인율
	    	boxsaleval = parseInt(boxsalequt*boxpddcval*inputcnt);	    	
	    	realtotal = total-boxsaleval;
	    }else{
	    	realtotal = final_total;
	    }
	    
	    $("#sit_tot_price").html("<span>총 금액</span> "+number_format(String(realtotal))+"원");
	    $('#ORDER_AMT').val(realtotal);
	}
</script>

	<form id="bkInstFrm" name="bkInstFrm" action="${contextPath }/goods/basketInst" method="post" autocomplete="off">
		<input type="hidden" name="PD_CODE" id="PD_CODE" value="${result.PD_CODE }"> 
		<input type="hidden" name="PD_QTY" id="PD_QTY" value=""> 
		<input type="hidden" name="ORDER_QTY" id="ORDER_QTY" value=""> 
		<input type="hidden" id="BSKT_REGNO" name="BSKT_REGNO" value=""> 
		<input type="hidden" id="PD_NAME" name="PD_NAME" value="${result.PD_NAME}" /> 
		<input type="hidden" id="PD_PRICE" name="PD_PRICE" value="${result.PD_PRICE}" /> 
		<input type="hidden" id="PDDC_GUBN" name="PDDC_GUBN" value="${result.PDDC_GUBN}" /> 
		<input type="hidden" id="PDDC_VAL" name="PDDC_VAL" value="${result.PDDC_VAL}" /> 
		<input type="hidden" id="ORDER_AMT" name="ORDER_AMT" value="${ result.PD_PRICE }" /> 
		<input type="hidden" id="DLVY_AMT" name="DLVY_AMT" value="0" /> 
		<input type="hidden" id="DAP_YN" name="DAP_YN" value="N" />
		<input type="hidden" id="PD_CUT_SEQ" name="PD_CUT_SEQ" value="" />
		<input type="hidden" id="PD_CUT_SEQ_UNIT" name="PD_CUT_SEQ_UNIT" value="" />
		
		<input type="hidden" id="OPTION_CODE" name="OPTION_CODE" value="" />
		<input type="hidden" id="OPTION_NAME" name="OPTION_NAME" value="" />
	</form>

	<!-- Main Container  -->
	<div class="main-container container">
		<ul class="breadcrumb">
			<li><a href="${contextPath }/m"><i class="fa fa-home"></i></a></li>
			<c:forEach var="entPath" items="${ arrCagoNmPath }" varStatus="status">
				<li><a href="${contextPath }/m/product?CAGO_ID=${arrCagoIdPath[status.index] }" class=" ">${entPath }</a></li>
			</c:forEach>
		</ul>
		
		<div class="row">
			<!--Left Part Start -->
			<aside class="col-sm-4 col-md-3 content-aside" id="column-left">
				<c:if test="${ !empty rtnCagoList }">
				<div class="module category-style">
                	<h3 class="modtitle">Categories</h3>
                	<div class="modcontent">
                		<div class="box-category">
                			<ul id="cat_accordion" class="list-group">
								<c:forEach var="ent" items="${ rtnCagoList }" varStatus="status">
									<li class=""><a href="${contextPath }/m/product?CAGO_ID=${ ent.CAGO_ID }" class="cutom-parent"><c:out value="${ ent.CAGO_NAME }" escapeXml="true"/> (${ ent.PRD_CNT })</a>  <span class="dcjq-icon"></span></li>
								</c:forEach>
                			</ul>
                		</div>
                	</div>
                </div>
				</c:if>
				<%-- 
            	<div class="module banner-left hidden-xs ">
                	<div class="banner-sidebar banners">
                      <div>
                        <a title="Banner Image" href="${contextPath}/product/m/retailListIcon"> 
                          <img src="${contextPath }/resources/img/retail/retail_button.png" alt="retail"> 
                        </a>
                      </div>
                    </div>
                </div>
                 --%>
            </aside>
            <!--Left Part End -->

			<!--Middle Part Start-->
			<div id="content" class="col-md-9 col-sm-8">
				
				<div class="product-view row">
					<div class="left-content-product">						
						<div class="content-product-left class-honizol col-md-5 col-sm-12 col-xs-12">
							<%-- <c:set var="imgPath" value="${contextPath }/common/commonImgFileDown?ATFL_ID=${result.ATFL_ID }&ATFL_SEQ=${result.RPIMG_SEQ}&IMG_GUBUN=productView1" /> --%>
							<c:if test="${ !empty(result.ATFL_ID)  }" >
								<c:if test="${result.FILEPATH_FLAG eq 'woori' }">													
									<c:set var="imgPath" value="${contextPath }/upload/${result.STFL_PATH }/${result.STFL_NAME }" />									
								</c:if>
								<c:if test="${result.FILEPATH_FLAG ne 'woori' }">
									<c:set var="imgPath" value="https://www.cjfls.co.kr/upload/${result.STFL_PATH }/${result.STFL_NAME }" />
								</c:if>
							</c:if>
							<c:if test="${ empty(result.ATFL_ID)  }">
								<c:set var="imgPath" value="${contextPath }/resources/images/mall/goods/noimage.png" />
							</c:if>
							<div class="large-image  ">
								<img itemprop="image" class="product-image-zoom" src="${imgPath }" title="${ result.PD_NAME }" alt="${ result.PD_NAME }">
							</div>
							<c:if test="${ent.SALE_CON eq 'SALE_CON_02' }">
								<div class="box-label">
									<span class="label-product label-sale">품절</span>
								</div>
							</c:if>
							
							<div id="thumb-slider" class="yt-content-slider full_slider owl-drag" data-rtl="yes" data-autoplay="no" data-autoheight="no" data-delay="4" data-speed="0.6" data-margin="10" data-items_column00="4" data-items_column0="4" data-items_column1="3" data-items_column2="4"  data-items_column3="4" data-items_column4="4" data-arrows="yes" data-pagination="no" data-lazyload="yes" data-loop="no" data-hoverpause="yes">
								<c:if test="${ !empty(fileList) }">
									<c:forEach var="var" items="${ fileList }" varStatus="status">
										<c:if test="${ !empty(var.ATFL_ID)  }" >
											<c:if test="${var.FILEPATH_FLAG eq 'woori' }">													
												<c:set var="imgPathSm" value="${contextPath }/upload/${var.STFL_PATH }/${var.STFL_NAME }" />
											</c:if>
											<c:if test="${var.FILEPATH_FLAG ne 'woori' }">
												<c:set var="imgPathSm" value="https://www.cjfls.co.kr/upload/${var.STFL_PATH }/${var.STFL_NAME }" />
											</c:if>
										</c:if>
										<c:if test="${ empty(var.ATFL_ID)  }">
											<c:set var="imgPathSm" value="${contextPath }/resources/images/mall/goods/noimage.png" />
										</c:if>
										<a data-index="${status.index }" class="img thumbnail " data-image="${imgPathSm }" title="${ result.PD_NAME }">
											<img class="thumb-slider-img" src="${imgPathSm }" title="${ result.PD_NAME }" alt="${ result.PD_NAME }">
										</a>
										<%-- <a data-index="${status.index }" class="img thumbnail " data-image="${contextPath }/common/commonImgFileDown?ATFL_ID=${var.ATFL_ID }&ATFL_SEQ=${var.ATFL_SEQ}&IMG_GUBUN=productView1" title="${ result.PD_NAME }">
											<img class="thumb-slider-img" src="${contextPath }/common/commonImgFileDown?ATFL_ID=${var.ATFL_ID }&ATFL_SEQ=${var.ATFL_SEQ}&IMG_GUBUN=productView1" title="${ result.PD_NAME }" alt="${ result.PD_NAME }">
										</a> --%>
									</c:forEach>
								</c:if>
								<c:if test="${ empty(fileList) }">
									<a data-index="0" class="img thumbnail " data-image="${contextPath }/resources/images/mall/goods/noimage.png" title="${ result.PD_NAME } 상품이미지 없음">
										<img src="${contextPath }/resources/images/mall/goods/noimage.png" title="${ result.PD_NAME } 상품이미지 없음" alt="${ result.PD_NAME } 상품이미지 없음">
									</a>
								</c:if>
							</div>
							
						</div>
						 
						<div class="content-product-right col-md-7 col-sm-12 col-xs-12">
							<div class="title-product">
								<h1><c:out value="${ result.PD_NAME }" escapeXml="true" /></h1>
							</div>
							<div class="product-label form-group">
								<div class="product_page_price price" itemprop="offerDetails" itemscope="" itemtype="http://data-vocabulary.org/Offer">
									<span class="price-new" itemprop="price"><fmt:formatNumber value="${ result.REAL_PRICE }" pattern="#,###" />원</span>
									<input type="hidden" id="REAL_PRICE" value="${ result.REAL_PRICE }">
									<c:if test="${ result.REAL_PRICE ne result.PD_PRICE }">
										<span class="price-old"><fmt:formatNumber value="${ result.PD_PRICE }" pattern="#,###" />원</span>
									</c:if>
								</div>
								<div class="stock">
									<span>재고상태 :</span> 
									<c:if test="${result.SALE_CON eq 'SALE_CON_02' }">
										<span class="status-stock-out">품절</span>
									</c:if>
									<c:if test="${result.SALE_CON ne 'SALE_CON_02' }">
										<span class="status-stock">구매가능</span>
									</c:if>									
								</div>
							</div>

							<div class="product-box-desc">
								<div class="inner-box-desc">
									<div class="price-tax"><span>제조사:</span> <c:out value="${ result.MAKE_COM == '' ? '상세설명참조 ' : result.MAKE_COM}" escapeXml="true" /></div>
									<div class="reward"><span>원산지:</span> <c:out value="${ result.ORG_CT == '' ? '상세설명참조 ' : result.ORG_CT}" escapeXml="true" /></div>
									<div class="brand"><span>박스입수량:</span><c:if test='${result.INPUT_CNT != null && result.INPUT_CNT>0 }'>한 박스에 ${result.INPUT_CNT}개 입수량 입니다.</c:if></div>
									<c:if test="${result.BOX_PDDC_GUBN !='PDDC_GUBN_01' }">
									<div class="model">박스로 구매시 ( ${result.BOX_PDDC_VAL} )${ result.BOX_PDDC_GUBN == 'PDDC_GUBN_02'? '원':'%'} 할인되는 상품입니다.</div>
									</c:if>
								</div>
							</div>

							<div id="product">
								<c:if test='${pdcut_cnt >0 || !empty result.OPTION_GUBN}'>
									<h4>상품 옵션</h4>
									<c:if test='${pdcut_cnt >0 }'>
									<div class="box-checkbox form-group required">
										<label class="control-label">세절방식</label>
										<div id="input-option232">
											<select class="form-control select"  id="PD_CUT_SEL">
												<option value='nothing'>선택</option>
												<c:forEach var="pdcut" items="${pdcutList }" varStatus="sta">
													<option value='${pdcut.SEQ }'>${pdcut.CUT_UNIT }</option>
												</c:forEach>
											</select>
										</div>
										<p style="font-size:12px;margin:3px">*축산물은 세절작업을 진행한 후에는 <b style="color:red">반품 빛 교환이 되지않습니다.</b> 이점을 반드시 유의하시어 주문부탁드립니다.</p>	
									</div>
									</c:if>	
	
									<!-- 옵션 선택 -->
									<c:if test='${ result.OPTION_GUBN == "OPTION_GUBN_02" }'>
										<div class="image_option_type form-group required">
											<label class="control-label">색상</label>
											<div id="input-option234">
												<select class="form-control select PD_OPTION_SEL"  id="PD_OPTION_SEL">
													<option value='nothing'>선택</option>
													<c:forEach var="pdoption" items="${optionList }" varStatus="sta">
														<option value='${pdoption.COMD_CODE }'>${pdoption.COMDCD_NAME }</option>
													</c:forEach>
												</select>
											</div>
										</div>
									</c:if> 
									
									<!-- 옵션 선택 -->
									<c:if test='${ result.OPTION_GUBN == "PLSBAG_GUBN" }'>
										<div class="image_option_type form-group required">
											<label class="control-label">색상</label>
											<div id="input-option234">
												<jsp:include page="/common/optCodForm">
													<jsp:param name="OPTM_CODE" value="PLSBAG_GUBN" />
													<jsp:param name="name" value="PLSBAG_GUBN" />
													<jsp:param name="value" value="" />
													<jsp:param name="type" value="select" />
													<jsp:param name="top" value="" />
													<jsp:param name="test" value="PD_OPTION_SEL" />
												</jsp:include>
											</div>
										</div>
									</c:if>				
									<c:if test='${ result.OPTION_GUBN == "PLSBAG_GUBN_2" }'>
										<div class="image_option_type form-group required">
											<label class="control-label">색상</label>
											<div id="input-option234">
												<jsp:include page="/common/optCodForm">
													<jsp:param name="OPTM_CODE" value="PLSBAG_GUBN_2" />
													<jsp:param name="name" value="PLSBAG_GUBN_2" />
													<jsp:param name="value" value="" />
													<jsp:param name="type" value="select" />
													<jsp:param name="top" value="" />
													<jsp:param name="test" value="PD_OPTION_SEL" />
												</jsp:include>
											</div>
										</div>
									</c:if>
									<c:if test='${ result.OPTION_GUBN == "PLSBAG_GUBN_3" }'>
										<div class="image_option_type form-group required">
											<label class="control-label">색상</label>
											<div id="input-option234">
												<jsp:include page="/common/optCodForm">
													<jsp:param name="OPTM_CODE" value="PLSBAG_GUBN_3" />
													<jsp:param name="name" value="PLSBAG_GUBN_3" />
													<jsp:param name="value" value="" />
													<jsp:param name="type" value="select" />
													<jsp:param name="top" value="" />
													<jsp:param name="test" value="PD_OPTION_SEL" />
												</jsp:include>
											</div>
										</div>
									</c:if>
									<c:if test='${ result.OPTION_GUBN == "PLSBAG_GUBN_4" }'>
										<div class="image_option_type form-group required">
											<label class="control-label">색상</label>
											<div id="input-option234">
												<jsp:include page="/common/optCodForm">
													<jsp:param name="OPTM_CODE" value="PLSBAG_GUBN_4" />
													<jsp:param name="name" value="PLSBAG_GUBN_4" />
													<jsp:param name="value" value="" />
													<jsp:param name="type" value="select" />
													<jsp:param name="top" value="" />
													<jsp:param name="test" value="PD_OPTION_SEL" />
												</jsp:include>
											</div>
										</div>
									</c:if>
								</c:if>
								<div class="form-group box-info-product">
									<div class="option quantity">
										<c:if test='${result.QNT_LIMT_USE=="Y" }'>				
											<div class="input-group quantity-control" unselectable="on" style="-webkit-user-select: none;">
												<jsp:include page="/common/comCodForm">
													<jsp:param name="COMM_CODE" value="QNT_LIMT" />
													<jsp:param name="name" value="CT_QTY" />
													<jsp:param name="value" value="QNT_LIMT_01" />
													<jsp:param name="type" value="select" />
													<jsp:param name="top" value="" />
												</jsp:include>
											</div>
										</c:if>
										<c:if test = '${result.QNT_LIMT_USE==null||result.QNT_LIMT_USE==""||result.QNT_LIMT_USE=="N" }'>								
											<div class="input-group quantity-control" unselectable="on" style="-webkit-user-select: none;">
												<label>신청수량</label>
												<input class="form-control number" type="text" id="CT_QTY" name="CT_QTY" maxlength="4" value="1">
												<input type="hidden" id="LIMIT_QTY" name="LIMIT_QTY" value="${ result.LIMIT_QTY }"/>
												<span class="input-group-addon product_quantity_down btnQty">-</span>
												<span class="input-group-addon product_quantity_up btnQty">+</span>
											</div>
										</c:if>	
									</div>
									<div class="cart">
										<input type="button" data-toggle="tooltip" title="" value="구매하기" data-loading-text="Loading..." id="btnBuy" class="btn btn-mega btn-lg" data-original-title="바로 구매하기">
										<input type="button" data-toggle="tooltip" title="" value="장바구니담기" data-loading-text="Loading..." id="btnCart" class="btn btn-mega btn-lg" data-original-title="장바구니 담기">
									</div>
									<div class="add-to-links wish_comp">
										<ul class="blank list-inline">
											<li class="wishlist">
												<a class="icon" data-toggle="tooltip" id="btnWish" title="Add to Wish List" data-original-title="Add to Wish List"><i class="fa fa-heart"></i>
												</a>
											</li>
										</ul>
									</div>
								</div>

							</div>
							<!-- end box info product -->
							<div id="product">
								<div class="box-checkbox form-group required">
									<c:if test='${result.QNT_LIMT_USE=="Y" }'>
										<h4 id="sit_tot_price">총 금액 <fmt:formatNumber value="${ result.REAL_PRICE*3 }" pattern="#,###" />원</h4>
									</c:if>
									<c:if test='${result.QNT_LIMT_USE!="Y" }'>
										<h4 id="sit_tot_price">총 금액 <fmt:formatNumber value="${ result.REAL_PRICE }" pattern="#,###" />원</h4>
									</c:if>
								</div>
							</div>
													
						</div>
				
					</div>
				</div>
				<!-- Product Tabs -->
				<div class="producttab ">
					<div class="tabsslider  vertical-tabs col-xs-12">
						<ul class="nav nav-tabs col-lg-2">
							<li class="active"><a data-toggle="tab" href="#tab-1">상품 상세정보</a></li>
							<li class="item_nonactive"><a data-toggle="tab" href="#tab-2">배송/환불 안내</a></li>
						</ul>
						<div class="tab-content col-lg-10 col-sm-12 col-xs-12">
							<div id="tab-1" class="tab-pane fade active in tbl_pdt_detail">
								<div class="table-responsive">
								${ result.PD_DINFO }
								<c:if test="${ !empty(fileDtlList) && result.DTL_USE_YN eq 'Y' }">
									<br>
									<p>상품 상세정보</p>
									<table class="table table-bordered">
										<tbody>
											<c:forEach var="var" items="${ fileDtlList }" varStatus="status">
												<c:if test="${var.FILEPATH_FLAG eq 'woori' }">													
													<c:set var="imgDtlPath" value="${contextPath }/upload/${var.STFL_PATH }/${var.STFL_NAME }" />
												</c:if>
												<c:if test="${var.FILEPATH_FLAG ne 'woori' }">
													<c:set var="imgDtlPath" value="https://www.cjfls.co.kr/upload/${var.STFL_PATH }/${var.STFL_NAME }" />
												</c:if>
												<tr>
													<td><img src="${imgDtlPath }" class="goodsImg" />&nbsp;</td>
												</tr>
											</c:forEach>
										</tbody>
									</table>
								</c:if>
								</div>
							</div>
							<div id="tab-2" class="tab-pane fade">
								${ result.DLVREF_GUIDE }
								<c:if test="${ empty result.DLVREF_GUIDE }">
									<p class="sit_empty">배송/환불 안내 정보가 없습니다.</p>
								</c:if>
							</div>
						</div>
					</div>
				</div>
				<!-- //Product Tabs -->

			</div>

        </div>
    </div>
	<!-- //Main Container -->
	