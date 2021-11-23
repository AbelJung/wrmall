<%@ page trimDirectiveWhitespaces="true"%>
<%@ page contentType="text/html; charset=utf-8"%>
<%@ include file="/layout/inc/taglib.jsp" %>
                                                                                                    
<script type="text/javascript">
	/* Side Menu */
	$(document).ready(function(){

		console.log("===========================================");		
		console.log('document.protocol', document.location.protocol);
		console.log('document.location', document.location.href);
		console.log('location.pathname',  window.location.pathname); // Returns path only
		console.log('location.href', window.location.href); // Returns full URL
		console.log("===========================================");
		
		var host = location.host.toLowerCase();
		var currentAddress = location.href;
		console.log('location.host', host);
		console.log('location.href', location.href);
		
		if (document.location.protocol == 'http:') {	
	        document.location.href = document.location.href.replace('http:', 'https:');
	    }
		
		if (host.indexOf("www")== -1) {
			currentAddress = currentAddress.replace("//","//www.");
			console.log('location.replace', currentAddress);
			//location.href = currentAddress;
		}

		// 카테고리 탑 2차 지정
	    <c:forEach items="${cagoList }" var="subMenu">
		    <c:if test="${subMenu.LVL eq '3'}">
				if($("#link_${subMenu.UPCAGO_ID} > b").length < 1){
			    	$("#link_${subMenu.UPCAGO_ID}").append("<b class='fa-angle-right'></b>");
				}
		    	$("#subMenuUl_${subMenu.UPCAGO_ID}").append("<li id='subMenuLi_${subMenu.CAGO_ID }'><a href='${contextPath }/m/product?CAGO_ID=${ subMenu.CAGO_ID }' class='main-menu'>${subMenu.CAGO_NAME}</a></li>");	
			</c:if>
	    </c:forEach>
	    
	    // 하위카테고리 없을경우 div 삭제
		$(".cagoMenu").each(function() {
			var strId = $(this).attr('id');
			if($(this).find('.fa-angle-right').length < 1){
				$(this).find('.sub-menu').remove();
			}
		});
		
		// 모바일 돋보기 검색 >> 바로 상세검색으로...
			$('.icon-search').click(function(){
			$(this).removeClass('active');
			$('#search0').css('display', 'none');
			$('#searchform').submit();
		}) 
	});	

	// Cart add remove functions
	var cart = {
		'add': function(product_id, product_name, product_cut, product_option) {

	       	<c:if test="${empty USER.MEMB_ID}">
				alert("로그인이 필요합니다.");
				return false;
			</c:if>

			if(product_cut > 0){
				alert("세절방식을 선택해야하는 상품입니다.\n상품상세페이지에서 세절방식을 선택 후 장바구니에 담아주세요.");
				return false;
			}
			if(product_option != ""){
				alert("색상을 선택해야하는 상품입니다.\n상품상세페이지에서 색상을 선택 후 장바구니에 담아주세요.");
				return false;
			}
		
	  		//장바구니 등록 여부 확인
	    	$.ajax({
	    		type: "GET",
	    		url: '${contextPath}/goods/basketInst?PD_CODE='+product_id,
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
		}
	}

	var wishlist = {
		'add': function(product_id, product_name, product_cut, product_option) {
	       	<c:if test="${empty USER.MEMB_ID}">
				alert("로그인이 필요합니다.");
				return false;
			</c:if>

			if(product_cut > 0){
				alert("세절방식을 선택해야하는 상품입니다.\n상품상세페이지에서 세절방식을 선택 후 장바구니에 담아주세요.");
				return false;
			}
			if(product_option != ""){
				alert("색상을 선택해야하는 상품입니다.\n상품상세페이지에서 색상을 선택 후 장바구니에 담아주세요.");
				return false;
			}
			
			// 관심상품 등록 여부 확인
	    	$.ajax({
				type: "GET",
	    		url: '${contextPath}/goods/wishInst?PD_CODE='+product_id+'&PD_QTY=1&PD_CUT_SEQ=&OPTION_CODE=',
	    		success: function (data) {

	    			// 관심상품 등록 여부
	    			if (data == '0') {
	    				//alert("관심상품에 등록되었습니다.");
						addProductNotice('관심상품 추가', '', '<h3>[' + product_name + '] 관심상품에 등록되었습니다.</h3>', 'success');
	    			}else{
						addProductNotice('관심상품 추가', '', '<h3 style="color:red;">관심상품에 이미 등록되어있습니다.</h3>', 'success');
	    			}
	    		}, error: function (jqXHR, textStatus, errorThrown) {
	    			alert("처리중 에러가 발생했습니다. 관리자에게 문의 하세요.(error:"+textStatus+")");
	    		}
	    	});
		}
	}	
</script>  

    <!-- Header Container  -->
    <header id="header" class=" typeheader-2">
    <!-- Header Top -->
    <div class="header-top hidden-compact">
        <div class="container">
            <div class="row">
                <div class="header-top-left col-lg-8 col-md-8 col-sm-6 col-xs-3">
                    <div class="hidden-md hidden-sm hidden-xs welcome-msg"><b>[공지사항]</b> 내용 <b>없음</b> </div>
                    <div class="telephone hidden-xs hidden-sm hidden-md" >
                        <ul><li><i class="fa fa-phone"></i><a href="tel:042-822-8444">고객센터 : 042-822-8444 (AM 10 ~ PM 6)</a></li></ul>
                    </div>
                    <c:if test="${empty USER.MEMB_ID}">
                    <ul class="account hidden-lg ">
                        <li class="account" id="my_account">
                            <a href="#" title="My Account " class="btn-xs dropdown-toggle" data-toggle="dropdown"> <span class="hidden-xs">회원서비스 </span>  <span class="fa fa-caret-down"></span>
                            </a>
                            <ul class="dropdown-menu ">
	                            <li><a href="${contextPath}/m/memberJoinStep1"><i class="fa fa-user"></i> 회원가입</a></li>
	                            <li><a href="${contextPath}/m/user/loginForm"><i class="fa fa-pencil-square-o"></i> 로그인</a></li>
                            </ul>
                        </li>
                    </ul> 
                    </c:if> 
                </div>
                <div class="header-top-right collapsed-block col-lg-4 col-md-4 col-sm-6 col-xs-9">
                    <div class="inner"> 
                        <ul class="top-link list-inline lang-curr">
                            <!-- li class="currency">
                                <div class="btn-group currencies-block">
                                    <form action="index.html" method="post" enctype="multipart/form-data" id="currency">
                                        <a class="btn btn-link dropdown-toggle" data-toggle="dropdown">
                                            <span class="icon icon-credit "></span> $ US Dollar  <span class="fa fa-angle-down"></span>
                                        </a>
                                        <ul class="dropdown-menu btn-xs">
                                            <li> <a href="#">(€)&nbsp;Euro</a></li>
                                            <li> <a href="#">(£)&nbsp;Pounds    </a></li>
                                            <li> <a href="#">($)&nbsp;US Dollar </a></li>
                                        </ul>
                                    </form>
                                </div>
                            </li -->   
							<c:if test="${!empty USER.MEMB_ID}">
                            <li class="language">
                                <div class="btn-group languages-block ">
                                    <form action="index.html" method="post" enctype="multipart/form-data" id="bt-language">
                                        <a class="btn btn-link dropdown-toggle" data-toggle="dropdown">
                                            <span class=""><i class="fa fa-user"></i>회원서비스 (${USER.MEMB_NAME})</span>
                                            <span class="fa fa-angle-down"></span>
                                        </a>
                                        <ul class="dropdown-menu">
									        <li><a href="${contextPath}/m/mypage">MyPage</a></li>
									        <li><a href="${contextPath}/m/basket">장바구니</a></li>
									        <li><a href="${contextPath}/m/wishList">관심상품</a></li>
									        <li><a href="${contextPath}/m/order/wishList">배송/주문조회</a></li>
									        <li><a href="${contextPath}/m/mypage/buyBeforeInfo" style="color:red;">미결재내역(${NOTPAYCNT})</a></li>
									        <li><a>배송비쿠폰(${DELICUPON})</a></li>
									        <li><a href="${contextPath}/m/user/logout">로그아웃</a></li>
                                        </ul>
                                    </form>
                                </div>
                            </li>
							</c:if>
                            
                        </ul>
                    </div>
                    
                </div>
            </div>
        </div>
    </div>
    <!-- //Header Top -->

    <!-- Header center -->
    <div class="header-middle">
        <div class="container">
            <div class="row">
                <!-- Logo -->
                <div class="navbar-logo col-lg-3 col-md-3 col-sm-12 col-xs-12">
                    <div class="logo">
                    	<a href="${contextPath}/m"><img src="${contextPath}/resources/img/common/woorilogo2.png" title="WOORIMARKET" alt="WOORIMARKET" /></a>
                    </div>
                </div>
                <!-- //end Logo -->
                <!-- Search -->
                <div class="middle2 col-lg-6 col-md-7 col-sm-6 col-xs-6">
                    <div class="search-header-w">
                        <div class="icon-search hidden-lg hidden-md"><i class="fa fa-search"></i></div> 
                        <div id="sosearchpro" class="sosearchpro-wrapper so-search ">
                            <form method="GET" name="searchform" id="searchform" action="${contextPath}/m/search">
                                <div id="search0" class="search input-group form-group">
                                    <div class="select_category filter_type  icon-select hidden-sm hidden-xs">
										<select class="no-border" name="schGbn">
											<option value="PD_NAME" ${param.schGbn eq 'PD_NAME' ? 'selected=selected' : '' }>상품명</option>
											<option value="PD_DINFO" ${param.schGbn eq 'PD_DINFO' ? 'selected=selected' : '' }>상품상세</option>
											<option value="MAKE_COM" ${param.schGbn eq 'MAKE_COM' ? 'selected=selected' : '' }>제조사</option>
										</select>
                                    </div>

                                    <input class="autosearch-input form-control" type="text" value="${param.schTxt }" style="ime-mode:active;" size="50" autocomplete="off" placeholder="검색어 입력" name="schTxt">
                                    <span class="input-group-btn">
                                    <button type="submit" class="button-search btn btn-primary" name="submit_search"><i class="fa fa-search"></i><span>Search</span></button>
                                    </span>
                                </div>
                                <input type="hidden" name="route" value="product/search" />
                            </form>
                        </div>
                    </div>
                </div>
                <!-- //end Search -->
                
                <div class="middle3 col-lg-3 col-md-2 col-sm-6 col-xs-6">       
                    <c:if test="${!empty USER.MEMB_ID}">             
                    <!--cart-->
                    <div class="shopping_cart">
                        <div id="cart" class="btn-shopping-cart">

                            <a data-loading-text="Loading... " class="btn-group top_cart dropdown-toggle" data-toggle="dropdown" aria-expanded="true">
                                <div class="shopcart">
                                    <span class="icon-c">
                                    <i class="fa fa-shopping-bag"></i>
                                  </span>
                                    <div class="shopcart-inner">
                                        <p class="text-shopping-cart">
                                            My cart
                                        </p>

                                        <span class="total-shopping-cart cart-total-full">
                                            <span class="items_cart">${fn:length(myCartList)}</span><span class="items_cart2"> item(s)</span><!-- span class="items_carts"> ($162.00) </span -->
                                        </span>
                                    </div>
                                </div>
                            </a>

                            <ul class="dropdown-menu pull-right shoppingcart-box" role="menu">
                                <li>
                                    <table class="table table-striped">
                                        <tbody>
											<c:forEach items="${myCartList }" var="list" varStatus="loop">
												<tr>
													<td class="text-center" style="width: 70px">
														<a href="${contextPath }/m/product/view/${ list.PD_CODE }"> 
														<c:if test="${ !empty(list.ATFL_ID)  }" >
															<c:if test="${list.FILEPATH_FLAG eq 'woori' }">		
																<c:set var="imgPath" value="${contextPath }/common/commonFileDown?ATFL_ID=${list.ATFL_ID}&ATFL_SEQ=${list.RPIMG_SEQ}" />							
																<%-- <c:set var="imgPath" value="${contextPath }/upload/${list.STFL_PATH }/${list.STFL_NAME }" /> --%>
															</c:if>
															<c:if test="${list.FILEPATH_FLAG ne 'woori' }">
																<c:set var="imgPath" value="https://www.cjfls.co.kr/common/commonFileDown?ATFL_ID=${list.ATFL_ID}&ATFL_SEQ=${list.RPIMG_SEQ}" />
																<%-- <c:set var="imgPath" value="https://www.cjfls.co.kr/upload/${list.STFL_PATH }/${list.STFL_NAME }" /> --%>
															</c:if>
														</c:if>
														<c:if test="${ empty(list.ATFL_ID)  }">
															<c:set var="imgPath" value="${contextPath }/resources/images/mall/goods/noimage_270.png" />
														</c:if>
														<img src="${imgPath}" style="width:50px; height:50px;" alt="${ list.PD_NAME }" title="${ list.PD_NAME }" class="preview">														
														</a>
													</td>
													<td class="text-left">
														<a class="cart_product_name"href="${contextPath }/m/product/view/${list.PD_CODE }">${list.PD_NAME }</a>
													</td>
													<td class="text-center">
														<c:if test="${list.SALE_CON=='SALE_CON_01'  && list.DEL_YN == 'N'   }">
														${list.PD_QTY }
														</c:if>
														<c:if test="${list.SALE_CON != 'SALE_CON_01' || list.DEL_YN != 'N'   }">
															판매하지<br>않는 상품
														</c:if>
														<c:if test="${list.SALE_CON == 'SALE_CON_01' && list.DEL_YN == 'N'   }">
															<!-- 박스할인율 적용  -->
															<c:if test="${list.BOX_PDDC_GUBN eq 'PDDC_GUBN_01'}">
																<c:set var="boxsaleval" value="0" />
															</c:if>
															<c:if test="${list.BOX_PDDC_GUBN eq 'PDDC_GUBN_02'}">
																<fmt:parseNumber var="boxsalequt" value="${list.PD_QTY/list.INPUT_CNT}" integerOnly="true" />
																<c:set var="boxsaleval" value="${list.BOX_PDDC_VAL*boxsalequt}" />
															</c:if>
															<c:if test="${list.BOX_PDDC_GUBN eq 'PDDC_GUBN_03'}">
																<fmt:parseNumber var="boxsalequt" value="${list.PD_QTY/list.INPUT_CNT}" integerOnly="true" />
																<fmt:parseNumber var="boxpddcval" value="${list.REAL_PRICE*list.BOX_PDDC_VAL/100}" integerOnly="true" />
																<c:set var="boxsaleval" value="${boxpddcval*boxsalequt*list.INPUT_CNT}" />
															</c:if>
														</c:if>
													</td>
													<td class="text-center"><fmt:formatNumber value="${(list.PD_QTY * list.REAL_PRICE)-boxsaleval }" /></td>
												</tr>
											</c:forEach>
											<c:if test="${fn:length(myCartList) == 0 }">
												<tr>
													<td>등록된 장바구니가 없습니다.</td>
												</tr>
											</c:if>  
                                        </tbody>
                                    </table>
                                </li>
                                <li>
                                    <div>
                                        <!-- table class="table table-bordered">
                                            <tbody>
                                                <tr>
                                                    <td class="text-left"><strong>Sub-Total</strong>
                                                    </td>
                                                    <td class="text-right">$140.00</td>
                                                </tr>
                                                <tr>
                                                    <td class="text-left"><strong>Eco Tax (-2.00)</strong>
                                                    </td>
                                                    <td class="text-right">$2.00</td>
                                                </tr>
                                                <tr>
                                                    <td class="text-left"><strong>VAT (20%)</strong>
                                                    </td>
                                                    <td class="text-right">$20.00</td>
                                                </tr>
                                                <tr>
                                                    <td class="text-left"><strong>Total</strong>
                                                    </td>
                                                    <td class="text-right">$162.00</td>
                                                </tr>
                                            </tbody>
                                        </table -->
										<p class="text-right">
											<a class="btn view-cart" href="${contextPath }/m/basket"><i
												class="fa fa-shopping-cart"></i>&nbsp;View Cart</a>
										</p>
                                    </div>
                                </li>
                            </ul>
                        </div>

                    </div>
                    <!--//cart-->
                    </c:if>
                    <c:if test="${empty USER.MEMB_ID}">
                    <ul class="login-w hidden-md hidden-sm hidden-xs">
                        <li>회원서비스</li>
                        <li class="logout"><a href="${contextPath}/m/user/loginForm">로그인</a> / <a href="${contextPath}/m/memberJoinStep1">회원가입</a></li>
                    </ul>            
                    </c:if>
                    
                </div>
                
                
            </div>

        </div>
    </div>
    <!-- //Header center -->

    <!-- Header Bottom -->
    <div class="header-bottom hidden-compact">
        <div class="container">
            <div class="row">
                
                <div class="bottom1 menu-vertical col-lg-2 col-md-3">
                    <!-- Secondary menu -->
                    <div class="responsive so-megamenu megamenu-style-dev">
                        <div class="so-vertical-menu ">
                            <nav class="navbar-default">    
                                
                                <div class="container-megamenu vertical">
                                    <div id="menuHeading">
                                        <div class="megamenuToogle-wrapper">
                                            <div class="megamenuToogle-pattern">
                                                <div class="container">
                                                    <div>
                                                        <span></span>
                                                        <span></span>
                                                        <span></span>
                                                    </div>
                                                    <b>All Categories</b>                          
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="navbar-header">
                                        <button type="button" id="show-verticalmenu" data-toggle="collapse" class="navbar-toggle">      
                                            <i class="fa fa-bars"></i>
                                            <span><b>  All Categories     </b></span>
                                        </button>
                                    </div>
                                    <div class="vertical-wrapper" >
                                        <span id="remove-verticalmenu" class="fa fa-times"></span>
                                        <div class="megamenu-pattern">
                                            <div class="container-mega">
                                                <ul class="megamenu">
													<c:set var="nCnt" value="0" />
													<c:forEach var="ent" items="${ cagoList }" varStatus="status">
														<c:if test="${ent.LVL eq '2' }">
														<c:set var="nCnt" value="${nCnt+1 }" />
	                                                       <li class="item-vertical cagoMenu css-menu with-sub-menu hover" id="liCago_${ent.CAGO_ID }" ${nCnt > 10 ? 'style="display: none;"' : '' }>
	                                                           <p class="close-menu"></p>
	                                                           <a class="clearfix" id="link_${ent.CAGO_ID }" href="${contextPath }/m/product?CAGO_ID=${ ent.CAGO_ID }">
	                                                               <img src="${contextPath}/resources/images/responsive/catalog/menu/icons/ico0.png" alt="icon">
	                                                               <span>${ ent.CAGO_NAME }</span>
	                                                           </a>
	                                                           <div class="sub-menu" data-subwidth="20" id='subMenuDiv_${ent.CAGO_ID }'>
	                                                               <div class="content" >
	                                                                   <div class="row">
	                                                                       <div class="col-sm-12">
	                                                                           <div class="row">
	                                                                               <div class="col-sm-12 hover-menu">
	                                                                                   <div class="menu">
	                                                                                       <ul id='subMenuUl_${ent.CAGO_ID }'>
	                                                                                       </ul>
	                                                                                   </div>
	                                                                               </div>
	                                                                           </div>
	                                                                       </div>
	                                                                   </div>
	                                                               </div>
	                                                           </div>
	                                                       </li>
														</c:if>
													</c:forEach>
                                                    <li class="loadmore">
                                                        <i class="fa fa-plus-square-o"></i>
                                                        <span class="more-view">카테고리 더보기</span>
                                                    </li>                                                        
                                                </ul>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                             </nav>
                        </div>
                    </div>
                    <!-- // end Secondary menu -->
                </div>
                <!-- Main menu -->
                <div class="main-menu-w col-lg-10 col-md-9">
                    <div class="responsive so-megamenu megamenu-style-dev">
                        <nav class="navbar-default">
                            <div class=" container-megamenu  horizontal open ">
                                <div class="navbar-header">
                                    <button type="button" id="show-megamenu" data-toggle="collapse" class="navbar-toggle">
                                        <span class="icon-bar"></span>
                                        <span class="icon-bar"></span>
                                        <span class="icon-bar"></span>
                                    </button>
                                </div>
                                
                                <div class="megamenu-wrapper">
                                    <span id="remove-megamenu" class="fa fa-times"></span>
                                    <div class="megamenu-pattern">
                                        <div class="container-mega">
                                            <ul class="megamenu" data-transition="slide" data-animationtime="250">
                                                <li class="">
                                                    <!-- <p class="close-menu"></p> -->
                                                    <a href="javascript:window.open('${contextPath}/adWr1');" class="clearfix" title="행사상품전단지 바로보기">
                                                        <strong>행사상품전단지</strong>
                                                        <img class="label-hot" src="${contextPath}/resources/images/responsive/catalog/menu/hot-icon.png" alt="icon items">
                                                    </a>
                                                </li>
                                                <li class="">
                                                    <!-- <p class="close-menu"></p> -->
                                                    <a href="${contextPath }/m/intro" class="clearfix">
                                                        <strong>회사소개</strong>
                                                    </a>
                                                </li>
                                                <li class="">
                                                    <!-- <p class="close-menu"></p> -->
                                                    <a href="${contextPath }/m/introPicture" class="clearfix">
                                                        <strong>매장소개</strong>
                                                    </a>
                                                </li>
                                                <li class="">
                                                    <!-- <p class="close-menu"></p> -->
                                                    <a href="${contextPath }/m/community/faqList" class="clearfix">
                                                        <strong>FAQ</strong>
                                                    </a>
                                                </li>
                                            </ul>
                                            
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </nav>
                    </div>
                </div>
                <!-- //end Main menu -->
                
            </div>
        </div>

    </div>

    </header>
    <!-- //Header Container  -->