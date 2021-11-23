<%@ page trimDirectiveWhitespaces="true"%>
<%@ page contentType="text/html; charset=utf-8"%>
<%@ include file="/layout/inc/taglib.jsp" %>


<script type="text/javascript">
<!--

/* Side Menu */
$(document).ready(function(){

	//탑 2차 지정
    <c:forEach items="${cagoList }" var="subMenu">
	    <c:if test="${subMenu.LVL eq '2'}">

	    $("#subMenuUl_${subMenu.UPCAGO_ID}").append("<li id='subMenuLi_${subMenu.CAGO_ID }' class='subLi'> <p class='title'><a style='color:rgb(121, 121, 121)' href='${contextPath }/product?CAGO_ID=${ subMenu.CAGO_ID }'>${subMenu.CAGO_NAME}</a></p></li>");

		</c:if>
		
	    <c:if test="${subMenu.LVL eq '3'}">
	
			if($("#subMenuLi_${subMenu.UPCAGO_ID} > dl").length < 1){
		    	$("#subMenuLi_${subMenu.UPCAGO_ID}").append("<dl></dl>");
			}
			$("#subMenuLi_${subMenu.UPCAGO_ID} > dl").append("<dd><a href='${contextPath }/product?CAGO_ID=${ subMenu.CAGO_ID }'>${subMenu.CAGO_NAME}</a></dd>");
		</c:if>
		
    </c:forEach>
    
    
	$(".subLi").each(function() {
		$(this).append("<hr>");

	});
	$(".subMenu").each(function() {
		var strId = $(this).attr("id");

		if($("#" + strId + " > div > ul > li").length < 1){
			var strCagoId = $(this).attr("cagoId");
			$("#link_"+strCagoId).attr("onmouseover", "");
		}


	});
});
//-->
</script>


<div style="display:none;">
	<c:forEach var="ent" items="${ cagoList }" varStatus="status">
		<c:if test="${ent.LVL eq '1' }">
		<div id="subMenu_${ent.CAGO_ID }" class="subMenu" cagoId="${ent.CAGO_ID }">
			<div class="category">
	            <ul id="subMenuUl_${ent.CAGO_ID }" class="category_list">
				</ul>
			</div>
		</div>
		</c:if>
</c:forEach>
</div>
					
<div class="container main_con">
	<div class="row_01 mb_25">
		<div class="col-1 lnb">

			<div class="category">
				<img src="${contextPath}/resources/img/common/cate_title.png">
				<hr>
				<ul class="category_list">
					<c:forEach var="ent" items="${ cagoList }" varStatus="status">
						<c:if test="${ent.LVL eq '1' }">
						<li id="liCago_${ent.CAGO_ID }">
							<p class="title"><a id="link_${ent.CAGO_ID }" onmouseover="tooltip.pop(this, '#subMenu_${ent.CAGO_ID }');" href="${contextPath }/product?CAGO_ID=${ ent.CAGO_ID }">${ ent.CAGO_NAME }</a></p>
							<dl></dl>
							<hr>
						</li>
						</c:if>
					</c:forEach>
				</ul>
			</div>

		</div>

		<div class="col-2">
			<div class="col-sm-12 slide">
				<div id="carousel-example-generic" class="carousel slide"
					data-ride="carousel">
					<!-- Indicators -->
					<ol class="carousel-indicators">
						<li data-target="#carousel-example-generic" data-slide-to="0"
							class="active"></li>
						<li data-target="#carousel-example-generic" data-slide-to="1"></li>
						<li data-target="#carousel-example-generic" data-slide-to="2"></li>
					</ol>

					<!-- Wrapper for slides -->
					<div class="carousel-inner" role="listbox">
						<div class="item active">
							<img src="${contextPath}/resources/img/main/slide_img.jpg" alt="...">
							<div class="carousel-caption">캡션테스트</div>
						</div>
						<div class="item">
							<img src="${contextPath}/resources/img/main/slide_img.jpg" alt="...">
							<div class="carousel-caption">...</div>
						</div>
						<div class="item">
							<img src="${contextPath}/resources/img/main/slide_img.jpg" alt="...">
							<div class="carousel-caption">...</div>
						</div>
					</div>

					<!-- Controls -->
					<a class="left carousel-control" href="#carousel-example-generic"
						role="button" data-slide="prev"> <span
						class="glyphicon glyphicon-chevron-left" aria-hidden="true"></span>
						<span class="sr-only">Previous</span>
					</a> <a class="right carousel-control" href="#carousel-example-generic"
						role="button" data-slide="next"> <span
						class="glyphicon glyphicon-chevron-right" aria-hidden="true"></span>
						<span class="sr-only">Next</span>
					</a>
				</div>
			</div>
		</div>
		<!--col-2 end-->
		<div class="col-3">
			<div class="main-tab">
				<!-- Nav tabs -->
				<ul class="nav nav-main" role="tablist">
					<li role="presentation" class="active"><a href="#home"
						aria-controls="home" role="tab" data-toggle="tab">아사특전</a></li>
					<li role="presentation"><a href="#profile"
						aria-controls="profile" role="tab" data-toggle="tab">추천상품</a></li>
					<li role="presentation"><a href="#messages"
						aria-controls="messages" role="tab" data-toggle="tab">상품후기</a></li>
				</ul>

				<!-- Tab panes -->
				<div class="tab-content tab-con-main">
					<div role="tabpanel" class="tab-pane ta_c active" id="home">
						<img src="${contextPath}/resources/img/main/event.jpg">
					</div>
					<div role="tabpanel" class="tab-pane ta_c" id="profile">...2</div>
					<div role="tabpanel" class="tab-pane ta_c" id="messages">3...</div>
				</div>
			</div>
			<div class="banner-box">
				<a><img src="${contextPath}/resources/img/main/banner_1.jpg"> </a>
				<hr>
				<a><img src="${contextPath}/resources/img/main/banner_2.jpg"> </a>
			</div>
		</div>
		<!--col-3end-->

		<div class="clearfix"></div>
	</div>


	<div class="row_02 mb_10">
		<div class="col-1">
			<div class="hit-box">
				<div class="row_02_title">
					<p>
						<span class="blue">HIT</span> 상품전
					</p>
					<ul class="controller">
						<li><a><img src="${contextPath}/resources/img/main/hit_arrow_left.gif"></a></li>
						<li><a><img src="${contextPath}/resources/img/main/hit_arrow_right.gif"></a></li>
					</ul>
				</div>

				<ul class="hit-list">
					<c:forEach var="ent" items="${ hitList }" varStatus="status">
						<c:if test="${status.index < 4 }">
						<li>
							<a href="./product/view/${ ent.PD_CODE }"> 
								<c:if test="${ !empty(ent.ATFL_ID) }">
									<img class="goodsImg" width="60" height="53" src="${contextPath }/common/commonFileDown?ATFL_ID=${ent.ATFL_ID }&ATFL_SEQ=${ent.RPIMG_SEQ}" alt="<c:out value="${ ent.PD_NAME }" escapeXml="true"/>"/>
								</c:if>
								<c:if test="${ empty(ent.ATFL_ID) }">
									<img class="goodsImg" width="60" height="53" src="${contextPath }/resources/images/mall/goods/noimage.png" alt="<c:out value="${ ent.PD_NAME }" escapeXml="true"/>"/>
								</c:if>
								<div class="text-box">
									<small class="label label-danger">HIT</small>
									<p class="txt_wg">${ ent.PD_NAME }</p>
									<p class="gray">${ ent.PD_NAME }</p>
									<p class="price">\ <fmt:formatNumber value="${ ent.REAL_PRICE }" pattern="#,###"/></p>
								</div>
							</a>
						</li>
						</c:if>
					</c:forEach>
				</ul>
			</div>
		</div>
		<!--col-1 end-->
		<div class="col-2">
			<div class="col-sm-12">
				<div class="row_02_title">
					<p>
						<span class="blue">신규</span> 상품전
					</p>
				</div>

				<div class="new-box">
					<ul class="new-list">
						<c:forEach var="ent" items="${ newList }" varStatus="status">
							<c:if test="${status.index < 3 }">
							<li>
								<a href="./product/view/${ ent.PD_CODE }">
									<c:if test="${ !empty(ent.ATFL_ID) }">
										<img class="goodsImg" width="132" height="88" src="${contextPath }/common/commonFileDown?ATFL_ID=${ent.ATFL_ID }&ATFL_SEQ=${ent.RPIMG_SEQ}" alt="<c:out value="${ ent.PD_NAME }" escapeXml="true"/>"/>
									</c:if>
									<c:if test="${ empty(ent.ATFL_ID) }">
										<img class="goodsImg" width="132" height="88" src="${contextPath }/resources/images/mall/goods/noimage.png" alt="<c:out value="${ ent.PD_NAME }" escapeXml="true"/>"/>
									</c:if>
									<p>${ ent.PD_NAME }</p>
									<p class="price">\ <fmt:formatNumber value="${ ent.REAL_PRICE }" pattern="#,###"/></p> 
								</a>
							</li>
							</c:if>
						</c:forEach>
					</ul>
					<a class="pre"><img src="${contextPath}/resources/img/main/new_arrow_left.gif"> </a> <a
						class="next"><img src="${contextPath}/resources/img/main/new_arrow_right.gif"> </a>
				</div>

				<div class="sale-box">
					<img src="${contextPath}/resources/img/main/onlysale.jpg">
				</div>

			</div>
		</div>
		<!--col-2 end-->
		<div class="col-3">
			<div class="row_02_title">
				<p>
					<span class="blue">Best of Best</span> 인기상품
				</p>
			</div>
			<div class="best-box">
				<a href="#" class="mb_15"><img src="${contextPath}/resources/img/main/best_img.jpg">
				</a> <a href="#"><img src="${contextPath}/resources/img/main/best_img2.jpg"> </a>
			</div>
		</div>
		<!--col-3end-->

		<div class="clearfix"></div>
	</div>

	<div class="row_03 mb_25">
		<div class="center-box center-green mb_20">
<!-- 
			<div class="col-xs-3 title">
				<h4 class="eng mb_5">
					<span class="blue">NEW</span> PRODUCT
				</h4>
				<p class="sm_ft gray">언제나 좋은 상품으로 함께하겠습니다</p>
			</div>
			<div class="col-xs-9 product">
				<div class="product_head">
					<ul>
						<li><a>농산물코너</a></li>
						<li><a>수산/건어물코너</a></li>
						<li><a>농산물코너</a></li>
						<li><a>수산/건어물코너</a></li>
					</ul>
				</div>
				<div class="product_con">
					<ul>
						<li><a> <img src="${contextPath}/resources/img/main/center_box_img.jpg">
								<p>종합과일세트</p>
								<p class="red txt_wg">\30,000</p> <span
								class="label sm_ft label-warning">NEW</span> <span
								class="label sm_ft label-success">BEST</span>
						</a></li>
						<li><a> <img src="${contextPath}/resources/img/main/center_box_img.jpg">
								<p>종합과일세트</p>
								<p class="red txt_wg">\30,000</p> <span
								class="label sm_ft label-warning">NEW</span> <span
								class="label sm_ft label-success">BEST</span>
						</a></li>
						<li><a> <img src="${contextPath}/resources/img/main/center_box_img.jpg">
								<p>종합과일세트</p>
								<p class="red txt_wg">\30,000</p> <span
								class="label sm_ft label-warning">NEW</span> <span
								class="label sm_ft label-success">BEST</span>
						</a></li>
						<li><a> <img src="${contextPath}/resources/img/main/center_box_img.jpg">
								<p>종합과일세트</p>
								<p class="red txt_wg">\30,000</p> <span
								class="label sm_ft label-warning">NEW</span> <span
								class="label sm_ft label-success">BEST</span>
						</a></li>
					</ul>
				</div>
			</div>
		</div>

		<div class="center-box center-orange">
			<div class="col-xs-3 title">
				<h4 class="eng mb_5">
					<span class="red">BEST</span> PRODUCT
				</h4>
				<p class="sm_ft gray">언제나 좋은 상품으로 함께하겠습니다</p>
			</div>
			<div class="col-xs-9 product">
				<div class="product_head">
					<ul>
						<li><a>농산물코너</a></li>
						<li><a>수산/건어물코너</a></li>
						<li><a>농산물코너</a></li>
						<li><a>수산/건어물코너</a></li>
					</ul>
				</div>
				<div class="product_con">
					<ul>
						<li><a> <img src="${contextPath}/resources/img/main/center_box_img.jpg">
								<p>종합과일세트</p>
								<p class="red txt_wg">\30,000</p> <span
								class="label sm_ft label-warning">NEW</span> <span
								class="label sm_ft label-success">BEST</span>
						</a></li>
						<li><a> <img src="${contextPath}/resources/img/main/center_box_img.jpg">
								<p>종합과일세트</p>
								<p class="red txt_wg">\30,000</p> <span
								class="label sm_ft label-warning">NEW</span> <span
								class="label sm_ft label-success">BEST</span>
						</a></li>
						<li><a> <img src="${contextPath}/resources/img/main/center_box_img.jpg">
								<p>종합과일세트</p>
								<p class="red txt_wg">\30,000</p> <span
								class="label sm_ft label-warning">NEW</span> <span
								class="label sm_ft label-success">BEST</span>
						</a></li>
						<li><a> <img src="${contextPath}/resources/img/main/center_box_img.jpg">
								<p>종합과일세트</p>
								<p class="red txt_wg">\30,000</p> <span
								class="label sm_ft label-warning">NEW</span> <span
								class="label sm_ft label-success">BEST</span>
						</a></li>
					</ul>
				</div>
			</div>
		</div>

		<div class="clearfix"></div>
	</div>
 -->
	<div class="row_04">
		<div class="panel panel-default">
			<div class="panel-body pos_r">
				<h4 class="eng mg_n">
					<span class="blue">RECOMMEND</span> PRODUCT <small
						class="sm_ft wgray">엄선된 상품만을 제공합니다</small>
				</h4>
				<a class="pos_a" style="right: 20px; top: 10px">more +</a>
			</div>
		</div>

		<c:if test="${ !empty(recommendList) }">
			<ul class="product_list">
				<c:forEach var="ent" items="${ recommendList }" varStatus="status">
			
					<li>
						<a href="./product/view/${ ent.PD_CODE }">
							<c:if test="${ !empty(ent.ATFL_ID) }">
								<img class="goodsImg" width="132" height="93" src="${contextPath }/common/commonFileDown?ATFL_ID=${ent.ATFL_ID }&ATFL_SEQ=${ent.RPIMG_SEQ}" alt="<c:out value="${ ent.PD_NAME }" escapeXml="true"/>"/>
							</c:if>
							<c:if test="${ empty(ent.ATFL_ID) }">
								<img class="goodsImg" width="132" height="93" src="${contextPath }/resources/images/mall/goods/noimage.png" alt="<c:out value="${ ent.PD_NAME }" escapeXml="true"/>"/>
							</c:if>
							<p>${ ent.PD_NAME }</p>
							<p class="red txt_wg">\ <fmt:formatNumber value="${ ent.REAL_PRICE }" pattern="#,###"/></p>
						</a>
					</li>
				</c:forEach>
			</ul>
		</c:if>
	</div>

	<div class="custom-box mt_30">
		<div class="customer">
			<h4 class="txt_wg eng">
				<span class="blue">CUSTOMER</span> CENTER
			</h4>
			<img src="${contextPath}/resources/img/common/customer.png">
			<div class="row mt_10 mb_10">
				<div class="col-sm-6">
					<a class="fl_l" href="#"><img src="${contextPath}/resources/img/common/customer_01.png"></a>
				</div>
				<div class="col-sm-6">
					<a class="fl_l" href="#"><img src="${contextPath}/resources/img/common/customer_02.png"></a>
				</div>
				<div class="clearfix"></div>
			</div>
		</div>
		<div class="notice">
			<h4 class="txt_wg eng gray">NOTICE & NEWS</h4>
			<ul>
				<li><a>정기세일 이벤트 당첨자 발표</a></li>
				<li><a>신규 브랜드 오픈기념 이벤트 당첨..</a></li>
				<li><a>사용후기 당첨자 안내</a></li>
				<li><a>아사농사물에서는 상품후기를..</a></li>
				<li><a>12월 상품정보 당첨자 공지</a></li>
				<li><a>신규 브랜드 오픈기념 이벤트 당첨자 발표합니다</a></li>
			</ul>
		</div>
		<div class="review">
			<h4 class="txt_wg eng gray">PRODUCT REVIEW</h4>
			<ul>
				<li><a><span class="blue">[상품평]</span>정기세일 이벤트 당첨자 발표</a></li>
				<li class="reply"><a class="text-danger">고객님 물건 잘받으셨죠?</a></li>
				<li><a><span class="blue">[상품평]</span>사용후기 당첨자 안내</a></li>
				<li class="reply"><a class="text-danger">아사농사물에서는 상품후기를 이용해</a></li>
				<li><a><span class="blue">[상품평]</span>12월 상품정보 당첨자 공지</a></li>
				<li class="reply"><a class="text-danger">고객님 물건 잘받으셨나요</a></li>
			</ul>
		</div>
		<div class="clearfix"></div>
	</div>

</div>
<!-- /container -->
