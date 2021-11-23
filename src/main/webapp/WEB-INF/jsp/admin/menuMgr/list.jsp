<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/layout/inc/taglib.jsp" %>

<section class="content-header">
	<h1>
		메뉴 목록	
		<small>메뉴 목록</small>
	</h1>
	<ol class="breadcrumb">
		<li><a href="#"><i class="fa fa-dashboard"></i> Level</a></li>
		<li class="active">Here</li>
	</ol>
</section>

<section class="content">
	<div class="box-footer">
		<a href="${contextPath}/adm/menuMgr/new" class="btn btn-default pull-right">등록</a>
	</div>
	
	<div class="box">
		<!-- /.box-header -->
		<div class="box-body">
			<table class="table table-bordered">
				<colgroup>
					<col style="with:10px" />
					<col />
					<col style="with:200px" />
					<col />
					<col />
					<col />
					<col />
					<col />
				</colgroup>
				<thead>
					<tr>
						<th>번호</th>
						<th>코드</th>
						<th>메뉴명</th>
						<th>URL</th>
						<th>출력여부</th>
						<th>정렬순서</th>
						<th>수정일</th>
						<th>수정자</th>
					</tr>
				</thead>
				<tbody>
				<c:if test="${fn:length(obj.list) == 0}">
					<tr>
						<td colspan="8">조회된 메뉴가 없습니다.</td>
					</tr>
				</c:if>
				<c:forEach items="${obj.list }" var="list" varStatus="loop">
					<tr>
						<td>${loop.count }</td>
						<td>${list.MENU_CD }</td>
						<td><a href="${contextPath}/adm/menuMgr/${list.MENU_CD }">${list.MENU_NAME }</a></td>
						<td>${list.MENU_URL }</td>
						<td>${list.OUTPT_FG }</td>
						<td>${list.SORT_ORDR }</td>
						<td>${list.MODP_ID }</td>
						<td>${list.MOD_DTM }</td>
					</tr>
				</c:forEach>
				</tbody>
			</table>
		</div>
		
	</div>
	
</section>
