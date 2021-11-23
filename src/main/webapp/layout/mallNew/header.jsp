<%@ page trimDirectiveWhitespaces="true"%>
<%@ page contentType="text/html; charset=utf-8"%>
<%@ include file="/layout/inc/taglib.jsp" %>
<script>
$(document).ready(function(){

	var host = location.host.toLowerCase();
	var currentAddress = location.href;
	console.log('location.host', host);
	console.log('location.href', location.href);
	/* 
	if (host.indexOf("/m")== -1) {
		console.log('window.location.href', 'https://www.woorimarket.co.kr/m');
		//window.location.href='https://www.woorimarket.co.kr/m';
	}
	
	if (window.location.pathname != "/m") {
		console.log('pathname.replace', 'https://www.woorimarket.co.kr/m');
		window.location.href='https://www.woorimarket.co.kr/m';
	}
	 */
	if (document.location.protocol == 'http:') {	
        document.location.href = document.location.href.replace('http:', 'https:');
    }
	
	if (host.indexOf("www")== -1) {
		currentAddress = currentAddress.replace("//","//www.");
		console.log('location.replace', currentAddress);
		//location.href = currentAddress; 
	}
});	

/* 공백제거 */
function strtrimid(obj){	
	/* alert('아이디 공백제거 전 : '+$('#LOGIN_ID').val()); */
	var idtxt = $('#LOGIN_ID').val().replace(/ /gi, '');	
	/* alert('아이디 공백제거 후 : '+idtxt); */
	$('#LOGIN_ID').val(idtxt);
}

function strtrimpw(obj){
	/* alert('비번 공백제거 전 : '+$('#LOGIN_PW').val()); */
	var pwtxt = $('#LOGIN_PW').val().replace(/ /gi, '');	
	/* alert('비번 공백제거 후 : '+pwtxt); */
	$('#LOGIN_PW').val(pwtxt);
}
</script>

<!-- header -->
<header class="head">
    <div class="container">
        <div class="col-sm-3 left pd_n" >
        	<a href="${contextPath}/"><img style="width:150px;" src="${contextPath}/resources/img/common/woorilogo.png"></a>
        </div>
        <div class="col-sm-9 right">
            <div class="row">
                <div class="col-sm-6 pd_n">
               <c:if test="${empty USER.MEMB_ID}">
                    <form class="form-inline" action="${contextPath }/user/login" method="post">
                   <input type="hidden" name="rtnUrl" value=''>
                        <div class="form-group">
                            <label for="LOGIN_ID" class="mt_5">ID</label>
                            <input type="text" class="form-control" style="width:120px;" name="MEMB_ID"  id="LOGIN_ID" placeholder="아이디" onchange="strtrimid(this)">
                        </div>
                        <div class="form-group">
                            <label for="LOGIN_PW" class="mt_5">PW</label>
                            <input type="password" class="form-control" style="width:120px;"  name="MEMB_PW" id="LOGIN_PW" placeholder="비밀번호" onchange="strtrimpw(this)">
                        </div>
                        <button type="submit" class="btn btn-xs btn-success">로그인</button>
                    </form>
               </c:if>
                </div>
                <div class="col-sm-6 pd_n">
                    <ul class="nav nav-utill">
                  <c:if test="${empty USER.MEMB_ID}">
                  		   <li><a href="${contextPath}/user/loginForm" style="color:red">아이디/비밀번호찾기</a></li>
                           <li><a href="${contextPath}/memberJoinStep1">회원가입</a></li>
                           <li><a href="${contextPath}/user/loginForm">로그인</a></li>
                           <li><a href="${contextPath}/">첫화면이동</a></li>
                  </c:if>
                  <c:if test="${!empty USER.MEMB_ID}">
                           <li><a href="#">${USER.MEMB_NAME}</a></li>
                           <li><a href="${contextPath}/mypage">마이페이지</a></li>
                           <li><a href="${contextPath}/basket">장바구니</a></li>
                           <li><a href="${contextPath}/wishList">관심상품</a></li>
                           <li><a href="${contextPath}/order/wishList">배송/주문조회</a></li>
                           <li><a href="${contextPath}/mypage/buyBeforeInfo" style="color:red;">미결재내역(${NOTPAYCNT})</a></li>
                           <li><a href="${contextPath}/user/logout">로그아웃</a></li>
                           <li><a href="${contextPath}/">첫화면이동</a></li>
                  </c:if>
                    </ul>
                </div>
            </div>
        </div>
    </div>
</header>

<!-- 내용 -->
<nav class="navbar navbar-bg">
    <div class="container">
        <div class="navbar-header">        	
            <img src="${contextPath}/resources/img/common/gnb_img1.png">
            <button type="button" class="btn btn-xs btn-danger" onclick="javascript:window.open('http://www.woorimarket.co.kr/adWr1');"   >행사상품전단지 바로보기</button>
        </div>
        
        <div id="navbar">
            <ul class="nav navbar-nav" style="margin-left:50px"><!-- style="margin-left:300px"> -->
                <!-- <li class="active"><a href="#">베스트 상품</a></li>
                <li><a href="#">신규등록 상품</a></li>
                <li><a href="#">이달의 특가상품</a></li>  -->
                <li><a href="${contextPath}/community" style="font-size:14px;">고객센터</a></li>
                <li><a href="${contextPath}/intro" style="font-size:14px;">회사소개</a></li>
                <li><a href="${contextPath}/introPicture" style="font-size:14px;">매장소개</a></li>
            </ul>
            <spform:form name="searchFrm" id="searchFrm" method="get" action="${contextPath }/search" class="navbar-form navbar-gnb pd_n navbar-right">
                <div class="form-group">
                    <input name="schTxt" class="form-control" style="width:200px;" type="text" placeholder="검색어를 입력하세요" value="${param.schTxt }">
                </div>
                <button type="submit" class="btn btn-xs btn-black">검색</button>
           </spform:form>
        </div><!--/nav -->
    </div>
</nav>