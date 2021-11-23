<%@ page trimDirectiveWhitespaces="true"%>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/layout/inc/taglib.jsp" %>
<style>
.goodsImg {
    width: auto; height: auto;
    max-width: 228px;
    max-height: 228px;
}
</style>
<div id="aside">

<c:if test="${empty USER.MEMB_ID}">
<section id="ol_before" class="ol">
    <h2>회원로그인</h2>
    <!-- 로그인 전 외부로그인 시작 -->
    <form name="foutlogin" action="${contextPath }/user/login" onsubmit="return fhead_submit(this);" method="post" autocomplete="off">
    <input type="hidden" name="rtnUrl" value='<%= request.getHeader("referer") %>'>
    <fieldset>
        <input type="hidden" name="url" value="%2Fyoungcart5%2Fshop%2F">
        <label for="ol_id" id="ol_idlabel">회원아이디<strong class="sound_only"> 필수</strong></label>
        <input type="text" id="ol_id" name="MEMB_ID" required class="required" maxlength="20">
        <label for="ol_pw" id="ol_pwlabel">비밀번호<strong class="sound_only"> 필수</strong></label>
        <input type="password" name="MEMB_PW" id="ol_pw" required class="required" maxlength="20">
        <input type="submit" id="ol_submit" value="로그인">
        <div id="ol_svc">
            <a href="${contextPath}/memberJoinStep1"><b>회원가입</b></a>
            <a href="#" id="ol_password_lost">정보찾기</a>
        </div>
        <div id="ol_auto">
<!--             <input type="checkbox" name="auto_login" value="1" id="auto_login">
            <label for="auto_login" id="auto_login_label">자동로그인</label> -->
        </div>
    </fieldset>
    </form>
</section>
</c:if>
<c:if test="${!empty USER.MEMB_ID}">
${USER.MEMB_NAME }
    <!-- 로그인 전 외부로그인 시작 -->
    <form name="foutlogin" action="${contextPath }/user/logout" onsubmit="return fhead_submit(this);" method="post" autocomplete="off">
    <fieldset>
        <input type="submit" id="ol_submit" value="로그아웃">
    </fieldset>
    </form>
</c:if>

<script>
$omi = $('#ol_id');
$omp = $('#ol_pw');
$omi_label = $('#ol_idlabel');
$omi_label.addClass('ol_idlabel');
$omp_label = $('#ol_pwlabel');
$omp_label.addClass('ol_pwlabel');

$(function() {
    $omi.focus(function() {
        $omi_label.css('visibility','hidden');
    });
    $omp.focus(function() {
        $omp_label.css('visibility','hidden');
    });
    $omi.blur(function() {
        $this = $(this);
        if($this.attr('id') == "ol_id" && $this.attr('value') == "") $omi_label.css('visibility','visible');
    });
    $omp.blur(function() {
        $this = $(this);
        if($this.attr('id') == "ol_pw" && $this.attr('value') == "") $omp_label.css('visibility','visible');
    });

    $("#auto_login").click(function(){
        if ($(this).is(":checked")) {
            if(!confirm("자동로그인을 사용하시면 다음부터 회원아이디와 비밀번호를 입력하실 필요가 없습니다.\n\n공공장소에서는 개인정보가 유출될 수 있으니 사용을 자제하여 주십시오.\n\n자동로그인을 사용하시겠습니까?"))
                return false;
        }
    });
    
    $('.multiple-items').slick({
    	  infinite: true,
    	  slidesToShow: 4,
    	  slidesToScroll: 4
    	});
    
	$('.goodsImg').each(function(n){
		$(this).error(function(){
			$(this).attr('src', '${contextPath }/resources/images/mall/goods/noimage.png');
		});
	 });
});

function fhead_submit(f)
{
    return true;
}
</script>
<!-- 로그인 전 외부로그인 끝 -->
</div>
<h2 class="sound_only">최신글</h2>
<!-- 최신글 시작 { -->
<div class="lt_list">
	
    <div class="lt_free" style="width:250px">
        
		<div class="lat">
			<h2 class="lat_title"><a href="#">공지사항</a></h2>
			<ul>
			<c:forEach var="ent" items="${ resultNotice }" varStatus="status">
				<li><a href="#">${ent.BRD_SBJT}</a><span class="lt_date">${ent.WRT_DTM}</span></li>
			</c:forEach>
			</ul>
			<div class="lat_more"><a href="#"><span class="sound_only">공지사항</span>더보기</a></div>
		</div>
    </div>
    <div class="lt_notice">
    	<img  src="${contextPath }/resources/images/mall/main/mainvisual.gif" alt="메인비주얼"/>
    </div>
</div>
<!-- } 최신글 끝 -->

<!-- 쇼핑몰 이벤트 시작 { -->
<aside id="sev">
	<h2>쇼핑몰 이벤트</h2>
	<ul>
		<li><a href="#" class="sev_img"><img src="${contextPath }/resources/images/mall/event.png" alt="이벤트0"></a></li>
		<li><a href="#" class="sev_img"><img src="${contextPath }/resources/images/mall/event1.png" alt="이벤트0"></a></li>
		<li><a href="#" class="sev_img"><img src="${contextPath }/resources/images/mall/event.png" alt="이벤트0"></a></li>
		<li><a href="#" class="sev_img"><img src="${contextPath }/resources/images/mall/event1.png" alt="이벤트0"></a></li>
	</ul>
</aside>
<!-- } 쇼핑몰 이벤트 끝 -->

<!-- 베스트 시작 { -->
<section class="sct_wrap">
	<header>
		<h2>
			<a href="#">Top10</a>
		</h2>
	</header>

	<!-- 상품진열 10 시작 { -->
	<ul class="sct sct_10 multiple-items">
		<c:if test="${ !empty(resultTop) }">
			<c:forEach var="ent" items="${ resultTop }" varStatus="status">
				<c:set var="strClass" value="" />
				<c:if test="${status.count%4 eq 1 }"><c:set var="strClass" value="sct_clear" /></c:if>
				<c:if test="${status.count%4 eq 0 }"><c:set var="strClass" value="sct_last" /></c:if>
				<li class="sct_li ${strClass} }" style="width:220px">
					<div class="sct_img">
						<a href="${contextPath }/goods/view/${ ent.PD_CODE }" class="sct_a">
						<c:if test="${ !empty(ent.ATFL_ID) }">
							<img class="goodsImg" width="230" height="230" src="${contextPath }/common/commonFileDown?ATFL_ID=${ent.ATFL_ID }&ATFL_SEQ=${ent.RPIMG_SEQ}" alt="<c:out value="${ ent.PD_NAME }" escapeXml="true"/>"/>
						</c:if>
						<c:if test="${ empty(ent.ATFL_ID) }">
							<img class="goodsImg" width="230" height="230" src="${contextPath }/resources/images/mall/goods/noimage.png" alt="<c:out value="${ ent.PD_NAME }" escapeXml="true"/>"/>
						</c:if>
						</a>
					</div>
					<div class="sct_txt">
						<a href="${contextPath }/goods/view/${ ent.PD_CODE }" class="sct_a"><c:out value="${ ent.PD_NAME }" escapeXml="true"/></a>
					</div>
					<div class="sct_cost">
						<c:if test="${ ent.PDDC_GUBN ne 'PDDC_GUBN_01' }">
							<c:set var="nDiscount" value="0" />
							<c:if test="${ ent.PDDC_GUBN eq 'PDDC_GUBN_02' }">
								<c:set var="nDiscount" value="${ent.PDDC_VAL }" />
							</c:if>
							<c:if test="${ ent.PDDC_GUBN eq 'PDDC_GUBN_03' }">
								<c:set var="nDiscount" value="${ ent.PD_PRICE* ent.PDDC_VAL/100 }" />
							</c:if>
							<strike><fmt:formatNumber value="${ ent.PD_PRICE }" pattern="#,###"/>원</strike>
							<fmt:formatNumber value="${ ent.PD_PRICE - nDiscount }" pattern="#,###"/>원
						</c:if>
						<c:if test="${ ent.PDDC_GUBN eq 'PDDC_GUBN_01' }">
							<fmt:formatNumber value="${ ent.PD_PRICE }" pattern="#,###"/>원
						</c:if>
						
					</div>
				</li>
				
			</c:forEach>
		</c:if>
	</ul>
	<script>
$('.multiple-items').slick({
  infinite: true,
  slidesToShow: 4,
  slidesToScroll: 4
});
				
</script>
	<!-- } 상품진열 10 끝 -->
</section>
<!-- } 베스트 끝 -->


<!-- 최신상품  시작 { -->
<section class="sct_wrap">
    <header>
        <h2><a href="">최신상품</a></h2>
    </header>
	<!-- 상품진열 10 시작 { -->
	<ul class="sct sct_20">
		<c:if test="${ !empty(resultNew) }">
			<c:forEach var="ent" items="${ resultNew }" varStatus="status">
				<c:set var="strClass" value="" />
				<c:if test="${status.count%4 eq 1 }"><c:set var="strClass" value="sct_clear" /></c:if>
				<c:if test="${status.count%4 eq 0 }"><c:set var="strClass" value="sct_last" /></c:if>
				<li class="sct_li ${strClass} }" style="width:230px">
					<div class="sct_img">
						<a href="${contextPath }/goods/view/${ ent.PD_CODE }" class="sct_a">
						<c:if test="${ !empty(ent.ATFL_ID) }">
							<img class="goodsImg" width="230" height="230" src="${contextPath }/common/commonFileDown?ATFL_ID=${ent.ATFL_ID }&ATFL_SEQ=${ent.RPIMG_SEQ}" alt="<c:out value="${ ent.PD_NAME }" escapeXml="true"/>"/>
						</c:if>
						<c:if test="${ empty(ent.ATFL_ID) }">
							<img class="goodsImg" width="230" height="230" src="${contextPath }/resources/images/mall/goods/noimage.png" alt="<c:out value="${ ent.PD_NAME }" escapeXml="true"/>"/>
						</c:if>
						</a>
					</div>
					<div class="sct_txt">
						<a href="${contextPath }/goods/view/${ ent.PD_CODE }" class="sct_a"><c:out value="${ ent.PD_NAME }" escapeXml="true"/></a>
					</div>
					<div class="sct_basic">제조사:<c:out value="${ ent.MAKE_COM }" escapeXml="true"/></div>
					<div class="sct_cost">
						<c:if test="${ ent.PDDC_GUBN ne 'PDDC_GUBN_01' }">
							<c:set var="nDiscount" value="0" />
							<c:if test="${ ent.PDDC_GUBN eq 'PDDC_GUBN_02' }">
								<c:set var="nDiscount" value="${ent.PDDC_VAL }" />
							</c:if>
							<c:if test="${ ent.PDDC_GUBN eq 'PDDC_GUBN_03' }">
								<c:set var="nDiscount" value="${ ent.PD_PRICE* ent.PDDC_VAL/100 }" />
							</c:if>
							<strike><fmt:formatNumber value="${ ent.PD_PRICE }" pattern="#,###"/>원</strike>
							<fmt:formatNumber value="${ ent.PD_PRICE - nDiscount }" pattern="#,###"/>원
						</c:if>
						<c:if test="${ ent.PDDC_GUBN eq 'PDDC_GUBN_01' }">
							<fmt:formatNumber value="${ ent.PD_PRICE }" pattern="#,###"/>원
						</c:if>
					</div>
					
					
				</li>
				
			</c:forEach>
		</c:if>
	</ul>
	<!-- } 상품진열 10 끝 -->
</section>
<!-- } 최신상품 끝 -->