<%@ page trimDirectiveWhitespaces="true"%>
<%@ page contentType="text/html; charset=utf-8"%>
<%@ include file="/layout/inc/taglib.jsp" %>


	<!-- Main Container  -->
	<div class="main-container container">
		<ul class="breadcrumb">
			<li><a href="${contextPath }/m"><i class="fa fa-home"></i></a></li>
			<li><a href="${contextPath }/m/community/faqList">FAQ</a></li>
		</ul>
		
		<div class="row">
			<div id="content" class="col-sm-12">
				<h3>FAQ - 자주하는 질문</h3>
				<p><br></p>
				<div class="row">
					<div class="col-sm-12">
						<ul class="yt-accordion">
							<c:forEach items="${obj.list }" var="list" varStatus="loop">
								<li class="accordion-group">
									<h3 class="accordion-heading"><i class="fa fa-plus-square"></i><span>${ list.BRD_SBJT }</span></h3>
									<div class="accordion-inner">
										${list.BRD_CONT }
									</div>
								</li>
							</c:forEach>
							<c:if test="${fn:length(obj.list) == 0 }">
							자주묻는질문이 없습니다.</td>
							</c:if>
						</ul>
					</div>
				</div>
			
				
			</div>
		</div>
	</div>
	<!-- //Main Container -->