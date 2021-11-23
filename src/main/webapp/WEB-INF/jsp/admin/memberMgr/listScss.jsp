<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/layout/inc/taglib.jsp" %>

<section class="content-header">
	<h1>
		탈퇴회원 	
	</h1>
	<ol class="breadcrumb">
		<li><a href="#"><i class="fa fa-dashboard"></i> Level</a></li>
		<li class="active">Here</li>
	</ol>
</section>

<section class="content">
	<!-- 검색 박스 -->
	<div class="box box-primary">
		<div class="box-header with-border">
			<h3 class="box-title">검색 조건</h3>

			<div class="box-tools pull-right">
				<button type="button" class="btn btn-box-tool"
					data-widget="collapse">
					<i class="fa fa-minus"></i>
				</button>
				<button type="button" class="btn btn-box-tool" data-widget="remove">
					<i class="fa fa-remove"></i>
				</button>
			</div>
		</div>
		<!-- /.box-header -->
		<div class="box-body">
			<form class="form-horizontal">
				<div class="box-body">
					<div class="form-group">
						<label for="inputEmail3" class="col-sm-1 control-label">검색어</label>

						<div class="col-sm-2">
							<select name="schGbn" class="form-control select2" style="width: 100%;">
								<option value="MEMB_NAME" selected="selected">회원명</option>
								<option value="MEMB_ID">회원ID</option>
							</select>
						</div>
						<div class="col-sm-8">
							<input name="schTxt" type="text" class="form-control" placeholder="검색어 입력" value="${param.schTxt }">
						</div>
						<div class="col-sm-1">
							<button type="submit" class="btn btn-success pull-right">검색</button>
						</div>
					</div>
				</div>
				<!-- /.row -->
		</div>
		<!-- /.box-body -->
	</div>
	<!-- /.box -->
	<!-- 검색 박스 -->

	<div class="box">
		<div class="box-header with-border">
			<h3 class="box-title">탈퇴회원 목록(${totalCnt})</h3>
		</div>
		<!-- /.box-header -->
		<div class="box-body">
			<table class="table table-bordered">
				<colgroup>
					<col style="with:10px" />
					<col />
					<col style="with:50px" />
				</colgroup>
				<thead>
					<tr>
						<th>번호</th>
						<th>아이디</th>
						<th>성명</th>
						<th>탈퇴일자</th>						
					</tr>
				</thead>
				<tbody>
				<c:forEach items="${obj.list }" var="ent" varStatus="loop">
					<tr>
						<td>${ent.RNUM }</td>
						<td><a href="${contextPath}/adm/memberMgr/edit?MEMB_ID=${ent.MEMB_ID }&pageNum=${obj.pageNum }&rowCnt=${obj.rowCnt }${link}"><c:out value="${ent.MEMB_ID }" escapeXml="true"/></a></td>						
						<td>${ent.MEMB_NAME }</td>
						<td>${ent.STOP_DTM }</td>						
					</tr>
				</c:forEach>
				<c:if test="${obj.count == 0}">
					<tr>
						<td colspan="3">조회된 내용이 없습니다.</td>
					</tr>
				</c:if>
				</tbody>
			</table>
		</div>
		<paging:PageFooter totalCount="${ totalCnt }" rowCount="${ rowCnt }">
				<First><Previous><AllPageLink><Next><Last>
		</paging:PageFooter>
	</div>
</section>
