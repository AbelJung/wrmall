<%@ page trimDirectiveWhitespaces="true"%>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/layout/inc/taglib.jsp" %>

<style>
/* 폼 테이블 */
.tbl_pdt_detail {margin:0 0 20px}
.tbl_pdt_detail table {width:100%;border-collapse:collapse;border-spacing:0;border-top:2px solid #000}
.tbl_pdt_detail th {width:100px;padding:7px 13px;border:1px solid #e9e9e9;border-left:0;background:#f7f7f7;text-align:left}
.tbl_pdt_detail td {padding:7px 10px;border-top:1px solid #e9e9e9;border-bottom:1px solid #e9e9e9;background:transparent}
.tbl_pdt_detail textarea, .frm_input {border:1px solid #e4eaec;background:#f7f7f7;color:#000;vertical-align:middle;line-height:2em}
.tbl_pdt_detail textarea {padding:2px 2px 3px}
.tbl_pdt_detail textarea {width:98%;height:100px}
.tbl_pdt_detail a {text-decoration:none}
.tbl_pdt_detail .frm_address {margin-top:5px}
.tbl_pdt_detail .frm_file {display:block;margin-bottom:5px}
.tbl_pdt_detail .frm_info {display:block;padding:0 0 5px;line-height:1.4em}
</style>

<section class="content-header">
	<h1>
		상품관리
		<small>상품 등록/수정</small>
	</h1>
	<ol class="breadcrumb">
		<li><a href="#"><i class="fa fa-dashboard"></i> Level</a></li>
		<li class="active">Here</li>
	</ol>
</section>

<section class="content">
	<!-- Horizontal Form -->
	<!-- form start -->
	<spform:form name="saveFrm" id="saveFrm" method="post" action="${contextPath }/adm/productMgr" class="form-horizontal" enctype="multipart/form-data">
		<input type="hidden" id="ATFL_ID" name="ATFL_ID" value="${productInfo.ATFL_ID }">	
		<input type="hidden" id="DTL_ATFL_ID" name="DTL_ATFL_ID" value="${productInfo.DTL_ATFL_ID }">	
		<input type="hidden" id="RPIMG_SEQ" name="RPIMG_SEQ" value="${ productInfo.RPIMG_SEQ }">
		<input type="hidden" id="PD_CODE_CHK_YN" name="PD_CODE_CHK_YN" value="N"/>
		<input type="hidden" id="PD_BARCD_CHK_YN" name="PD_BARCD_CHK_YN" value="N"/>
		<input type="hidden" id="DLVR_INDI_YN" name="DLVR_INDI_YN" value="N"/><!-- 개별배송 여부 -->
		
		<input type="hidden" id="pageNum" name="pageNum" value="${param.pageNum }">
		<input type="hidden" id="rowCnt" name="rowCnt" value="${param.rowCnt }">
		<input type="hidden" id="schTxt" name="schTxt" value="${param.schTxt }">
		<input type="hidden" id="schGbn" name="schGbn" value="${param.schGbn }">
		
		<input type="hidden" id="sortGubun" name="sortGubun" value="${param.sortGubun }">
		<input type="hidden" id="sortOdr" name="sortOdr" value="${param.sortOdr }">
		
		<input type="hidden" id="SEQ" name="SEQ" value="">
		<input type="hidden" id="USE_YN" name="USE_YN" value="">
		<input type="hidden" id="CUT_UNIT" name="CUT_UNIT" value="">
		
		<div class="box box-info">
			<div class="box-header with-border">
				<h3 class="box-title">상품정보</h3>
			</div>
			<!-- /.box-header -->
	
				<!-- box-body -->
				<div class="box-body">
					<div class="form-group">
						<label for="MEMB_NAME" class="col-sm-2 control-label">상품명</label>
						
						<c:if test="${ empty productInfo.PD_CODE }">
							<div class="col-sm-6">
								<input type="text" class="form-control input-sm" id="PD_NAME" name="PD_NAME" value="${productInfo.PD_NAME }" placeholder="상품명" />
							</div>
							<button type="button" class="btn btn-default" id="btnGoodsPopup">상품검색</button>
						</c:if>
						<c:if test="${ !empty productInfo.PD_CODE }">
						<div class="col-sm-10">
							<input type="text" class="form-control input-sm" id="PD_NAME" name="PD_NAME" value="${productInfo.PD_NAME }" placeholder="상품명" />
						</div>
						</c:if>
					</div>
					<c:choose>
						<c:when test="${ empty productInfo.PD_CODE }">
							<div class="form-group">
								<label class="col-md-2 control-label"> 상품코드</label>
								<div class="col-md-6">
									<div class="input-group">
										<label class="check" style="margin-right:10px">
											<input type="radio" class="iradio" name="PD_CODE_IN" value="AUTO" checked />
											자동발급
										</label>
										<label class="check" style="margin-right:10px">
											<input type="radio" class="iradio" name="PD_CODE_IN" value="INPUT" />
											수동입력
										</label>

										<div class="input-group input-group-sm divInputCode">
											<input name="PD_CODE" id="PD_CODE" value="${ productInfo.PD_CODE }" type="text" class="form-control input-sm" maxlength="13" /> 
											<span class="input-group-btn">
												<button type="button" class="btn btn-info btn-flat" id="btnDupChk">중복확인</button>
											</span>
										</div>
										<div class="text-info divInputCode" id="chkTxt"></div>
									</div> 
								</div>
							</div>
						</c:when>
						<c:otherwise>
							<div class="form-group">
								<label class="col-md-2 control-label"> 상품코드</label>
								<div class="col-md-4">
									<p class="form-control-static">
										<c:out value="${productInfo.PD_CODE }" escapeXml="true" />
									</p>
									<input name="PD_CODE" value="${ productInfo.PD_CODE }" type="hidden" />
								</div>
							</div>
						</c:otherwise>
					</c:choose>
					<c:choose>	
						<c:when test="${ empty productInfo.PD_BARCD }">
							<div class="form-group">
								<label class="col-md-2 control-label"> 상품바코드</label>
								<div class="col-md-6">
									<div class="input-group">
										<!-- <label class="check" style="margin-right:10px">
											<input type="radio" class="iradio" name="PD_BARCD_IN" value="AUTO" checked />
											자동발급
										</label>
										<label class="check" style="margin-right:10px">
											<input type="radio" class="iradio" name="PD_BARCD_IN" value="INPUT" />
											수동입력
										</label> -->
										<input type="hidden" name="PD_BARCD_IN" value="INPUT" />
										<div class="input-group input-group-sm divInputBarCode">
											<input name="PD_BARCD" id="PD_BARCD" value="${ productInfo.PD_BARCD }" type="text" class="form-control input-sm" maxlength="13" /> 
											<span class="input-group-btn">
												<button type="button" class="btn btn-info btn-flat" id="btnBarcdDupChk">중복확인</button>
											</span>
										</div>
										<div class="text-info divInputBarCode" id="chkBarcdTxt"></div>
									</div> 
								</div>
							</div>
						</c:when>
						<c:otherwise>
							<div class="form-group">
								<label class="col-md-2 control-label"> 상품바코드</label>
								<div class="col-md-4">
									<%-- <p class="form-control-static">
										<c:out value="${productInfo.PD_BARCD }" escapeXml="true" />
									</p> --%>
									<div class="input-group input-group-sm">
										<input name="PD_BARCD" id="PD_BARCD" value="${ productInfo.PD_BARCD }" type="text" class="form-control input-sm" maxlength="20" /> 
										<input type="hidden" id="ORG_BARCD" value="${ productInfo.PD_BARCD }"/>
										<span class="input-group-btn">
											<button type="button" class="btn btn-info btn-flat" id="btnBarcdDupChk">중복확인</button>
										</span>
									</div>
									<div class="text-info" id="chkBarcdTxt"></div>
								</div>
							</div>
						</c:otherwise>
					</c:choose>
					
				    <div class="form-group">
						<label for="CAGO_NAME" class="col-md-2 control-label">상품분류</label>
						<div class="col-md-4">
							<input name="CAGO_ID" id="CAGO_ID" value="${ productInfo.CAGO_ID }" type="hidden" /> 
							<div class="input-group input-group-sm" id="divInputCode">
								<input name="CAGO_NAME" id="CAGO_NAME" value="${ productInfo.CAGO_NAME }" type="text" class="form-control" title="상품분류"/> 
								<span class="input-group-btn">
									<button type="button" class="btn btn-info btn-flat" id="btnCateSel">카테고리선택</button>
								</span>
							</div>
						</div>
					</div>
					<div class="form-group">
						<label for="ORG_CT" class="col-md-2 control-label">리테일카테고리 사용여부</label>
						<div class="col-md-2">
							<p class="form-control-static">
								<label class="check" style="margin-right:10px">
									<input type="radio" class="iradio" name="RETAIL_YN" value="Y" 
										<c:if test='${productInfo.RETAIL_YN=="Y" }'>checked</c:if>
										onclick="fn_chk_rta_yn()" 
									/>
									사용
								</label>
								<label class="check" style="margin-right:10px">
									<input type="radio" class="iradio" name="RETAIL_YN" value="N" 
										<c:if test='${productInfo.RETAIL_YN=="N" || productInfo.RETAIL_YN==null }'>checked</c:if>
										onclick="fn_chk_rta_yn()"
									/>
									미사용
								</label>
							</p>
						</div>
						
						
						<label for="KEEP_GUBN" class="col-md-2 control-label retail">리테일 카테고리</label>
						<div class="col-md-5 retail">
							<p class="form-control-static">
				                <jsp:include page="/common/comCodForm">
									<jsp:param name="COMM_CODE" value="RETAIL_GUBN" />
									<jsp:param name="name" value="RETAIL_GUBN" />
									<jsp:param name="value" value="${ productInfo.RETAIL_GUBN }" />
									<jsp:param name="type" value="select" />
									<jsp:param name="top" value="선택" />
								</jsp:include>
							</p>
						</div>
						
					</div>
					<div class="form-group">
						<label for="PD_PRICE" class="col-md-2 control-label"> 상품가격</label>
						<div class="col-md-2">
							<div class="input-group">
								<input name="PD_PRICE" id="PD_PRICE" value="<fmt:formatNumber value="${ productInfo.PD_PRICE }" pattern="#,###"/>" type="text" class="form-control input-sm number" style="text-align:right;" maxlength="15" title="상품가격">
							</div>
						</div>
					</div>	               
	               
					<div class="form-group">
						<label for="PDDC_GUBN" class="col-md-2 control-label"> 상품할인</label>
						<div class="col-md-4">
			                <jsp:include page="/common/comCodForm">
								<jsp:param name="COMM_CODE" value="PDDC_GUBN" />
								<jsp:param name="name" value="PDDC_GUBN" />
								<jsp:param name="value" value="${ productInfo.PDDC_GUBN }" />
								<jsp:param name="type" value="radio" />
								<jsp:param name="top" value="선택" />
							</jsp:include>
						</div>
						<div class="col-md-2">
							<input name="PDDC_VAL" id="PDDC_VAL" value="<fmt:formatNumber value="${ productInfo.PDDC_VAL }" pattern="#,###"/>" 
								type="text" class="form-control input-sm number" 
								style="text-align:right;" maxlength="15" title="상품할인 값"
								onblur="calculPrice()">
						</div>
						<label for="PDDC_GUBN" class="col-md-1 control-label"> 행사가</label>
						<div class="col-md-2">
							<input id="PDDC_PRICE" type="text" class="form-control input-sm number" style="text-align:right;" maxlength="15" title="상품할인 값" readonly="readonly">
						</div>
					</div>
					<div class="form-group">
						<label for="INVEN_QTY" class="col-md-2 control-label"> 재고 수량</label>
						<div class="col-md-2">
							<input name="INVEN_QTY" id="INVEN_QTY" value="<fmt:formatNumber value="${ productInfo.INVEN_QTY }" pattern="#,###"/>" type="text" class="form-control input-sm number" style="text-align:right;"  maxlength="5" title="재고 수량">
						</div>
						<label for="INVEN_QTY" class="col-md-2 control-label"> 한정 수량</label>
						<div class="col-md-2">
							<input name="LIMIT_QTY" id="LIMIT_QTY" value="<fmt:formatNumber value="${ productInfo.LIMIT_QTY }" pattern="#,###"/>" type="text" class="form-control input-sm number" style="text-align:right;"  maxlength="4" title="한정 수량">
						</div>
					</div>
					<div class="form-group">
						<label for="BUNDLE_CNT" class="col-md-2 control-label"> 묶음배송</label>
						<div class="col-md-2">
							<input name="BUNDLE_CNT" id="BUNDLE_CNT" value="<fmt:formatNumber value="${ productInfo.BUNDLE_CNT }" pattern="#,###"/>" type="text" class="form-control input-sm number" style="text-align:right;"  maxlength="15" title="묶음배송 수량">
						</div>
					</div>
					<div class="form-group">
						<label for="MAKE_COM" class="col-md-2 control-label"> 제조사(브랜드)</label>
						<div class="col-md-4">
							<input name="MAKE_COM" value="${ productInfo.MAKE_COM }" type="text" class="form-control input-sm" maxlength="50" title="제조사(브랜드)">
						</div>
						<label for="ORG_CT" class="col-md-2 control-label"> 원산지</label>
						<div class="col-md-4">
							<input name="ORG_CT" value="${ productInfo.ORG_CT }" type="text" class="form-control input-sm" maxlength="50" title="원산지">
						</div>
					</div>
					<div class="form-group">
						<label for="SALE_CON" class="col-md-2 control-label"> 추가 컬럼</label>
						<div class="col-md-10">
							<button type="button" class="btn btn-primary" id="btnAddColumns" data-toggle="button" aria-pressed="false" autocomplete="off">추가 컬럼 열기</button>
						</div>
					</div>
					<div class="form-group divAdd">
						<label for="SALE_CON" class="col-md-2 control-label"> 추가 컬럼 입력</label>
						<div class="col-md-5">
							<div class="input-group">
								<span class="input-group-addon">컬럼명1</span>
								<input name="ADC1_NAME" value="${ productInfo.ADC1_NAME }" type="text" class="form-control input-sm">
							</div>
						</div>
						<div class="col-md-5">
							<div class="input-group">
								<span class="input-group-addon">컬럼값1</span>
								<input name="ADC1_VAL" value="${ productInfo.ADC1_VAL }" type="text" class="form-control input-sm">
							</div>
						</div>
					</div>
					<div class="form-group divAdd">
						<div class="col-md-5 col-md-offset-2">
							<div class="input-group">
								<span class="input-group-addon">컬럼명2</span>
								<input name="ADC2_NAME" value="${ productInfo.ADC2_NAME }" type="text" class="form-control input-sm">
							</div>
						</div>
						<div class="col-md-5">
							<div class="input-group">
								<span class="input-group-addon">컬럼값2</span>
								<input name="ADC2_VAL" value="${ productInfo.ADC2_VAL }" type="text" class="form-control input-sm">
							</div>
						</div>
					</div>
					<div class="form-group divAdd">
						<div class="col-md-5 col-md-offset-2">
							<div class="input-group">
								<span class="input-group-addon">컬럼명3</span>
								<input name="ADC3_NAME" value="${ productInfo.ADC3_NAME }" type="text" class="form-control input-sm">
							</div>
						</div>
						<div class="col-md-5">
							<div class="input-group">
								<span class="input-group-addon">컬럼값3</span>
								<input name="ADC3_VAL" value="${ productInfo.ADC3_VAL }" type="text" class="form-control input-sm">
							</div>
						</div>
					</div>
					<div class="form-group divAdd">
						<div class="col-md-5 col-md-offset-2">
							<div class="input-group">
								<span class="input-group-addon">컬럼명4</span>
								<input name="ADC4_NAME" value="${ productInfo.ADC4_NAME }" type="text" class="form-control input-sm">
							</div>
						</div>
						<div class="col-md-5">
							<div class="input-group">
								<span class="input-group-addon">컬럼값2</span>
								<input name="ADC4_VAL" value="${ productInfo.ADC4_VAL }" type="text" class="form-control input-sm">
							</div>
						</div>
					</div>
					<div class="form-group divAdd">
						<div class="col-md-5 col-md-offset-2">
							<div class="input-group">
								<span class="input-group-addon">컬럼명5</span>
								<input name="ADC5_NAME" value="${ productInfo.ADC5_NAME }" type="text" class="form-control input-sm">
							</div>
						</div>
						<div class="col-md-5">
							<div class="input-group">
								<span class="input-group-addon">컬럼값5</span>
								<input name="ADC5_VAL" value="${ productInfo.ADC5_VAL }" type="text" class="form-control input-sm">
							</div>
						</div>
					</div>
					<div class="form-group">
						<label for="SALE_CON" class="col-md-2 control-label"> 판매상태</label>
						<div class="col-md-4">
							<p class="form-control-static">
				                <jsp:include page="/common/comCodForm">
									<jsp:param name="COMM_CODE" value="SALE_CON" />
									<jsp:param name="name" value="SALE_CON" />
									<jsp:param name="value" value="${ productInfo.SALE_CON }" />
									<jsp:param name="type" value="radio" />
									<jsp:param name="top" value="선택" />
								</jsp:include>
							</p>
						</div>
						
						<label for="ORG_CT" class="col-md-2 control-label">수량제한 표시여부</label>
						<div class="col-md-4">
							<p class="form-control-static">
								<label class="check" style="margin-right:10px">
									<input type="radio" class="iradio" name="QNT_LIMT_USE" value="Y" <c:if test='${productInfo.QNT_LIMT_USE=="Y" }'>checked</c:if> />
									사용
								</label>
								<label class="check" style="margin-right:10px">
									<input type="radio" class="iradio" name="QNT_LIMT_USE" value="N" <c:if test='${productInfo.QNT_LIMT_USE=="N" || productInfo.QNT_LIMT_USE==null }'>checked</c:if>/>
									미사용
								</label>
							</p>
						</div>
					</div>
					<div class="form-group">
						<label for="TAX_GUBN" class="col-md-2 control-label"> 면세과세 구분</label>
						<div class="col-md-4">
							<p class="form-control-static">
				                <jsp:include page="/common/comCodForm">
									<jsp:param name="COMM_CODE" value="TAX_GUBN" />
									<jsp:param name="name" value="TAX_GUBN" />
									<jsp:param name="value" value="${ productInfo.TAX_GUBN }" />
									<jsp:param name="type" value="radio" />
									<jsp:param name="top" value="" />
								</jsp:include>
							</p>
						</div>
					
						
						<%-- <div class="col-md-4">
				                <jsp:include page="/common/comCodForm">
									<jsp:param name="COMM_CODE" value="OPTION_GUBN" />
									<jsp:param name="name" value="OPTION_GUBN" />
									<jsp:param name="value" value="${ productInfo.OPTION_GUBN }" />
									<jsp:param name="type" value="radioOption" />
									<jsp:param name="top" value="선택" />
								</jsp:include>
						</div> --%>
						
						<label for="TAX_GUBN" class="col-md-2 control-label">세절방식 등록</label>
						<div class="col-md-4">
							<button type="button" class="btn btn-primary" id="productCutBtn" data-toggle="button" aria-pressed="false" autocomplete="off">세절방식 등록</button>
												
						</div>
						
						<!-- 상품 옵션 설정 -->
						<!-- 현재 비닐봉투 단일 옵션 가능 -->
						<br><br>
						<label for="OPTION_GUBN" class="col-md-2 control-label">비닐봉투 색깔여부</label>
						<p class="form-control-static col-md-2">
							<label class="check" style="margin-right:10px">
								<input type="radio" class="" name="OPTION_GUBN" value="OPTION_GUBN_02" 
									<c:if test='${productInfo.OPTION_GUBN =="OPTION_GUBN_02" }'>checked</c:if> 
								/>
								흰색/검정색
							</label>
							<label class="check" style="margin-right:10px">
								<input type="radio" class="" name="OPTION_GUBN" value="PLSBAG_GUBN" 
									<c:if test='${productInfo.OPTION_GUBN =="PLSBAG_GUBN" }'>checked</c:if> 
								/>
								흰색/검정색/파란색/빨간색
							</label>
							
							<!-- 2019.02.11 추가 chjw -->
							<label class="check" style="margin-right:10px">
								<input type="radio" class="" name="OPTION_GUBN" value="PLSBAG_GUBN_2" 
									<c:if test='${productInfo.OPTION_GUBN =="PLSBAG_GUBN_2" }'>checked</c:if> 
								/>
								흰색/파란색
							</label>
							<label class="check" style="margin-right:10px">
								<input type="radio" class="" name="OPTION_GUBN" value="PLSBAG_GUBN_3" 
									<c:if test='${productInfo.OPTION_GUBN =="PLSBAG_GUBN_3" }'>checked</c:if> 
								/>
								흰색/검정색/빨간색
							</label>
							<label class="check" style="margin-right:10px">
								<input type="radio" class="" name="OPTION_GUBN" value="PLSBAG_GUBN_4" 
									<c:if test='${productInfo.OPTION_GUBN =="PLSBAG_GUBN_4" }'>checked</c:if> 
								/>
								흰색/검정색/파란색 
							</label>
							<!-- 2019.02.11 추가 chjw -->
							
							<label class="check" style="margin-right:10px">
								<input type="radio" class="" name="OPTION_GUBN" value="" 
									<c:if test='${ productInfo.OPTION_GUBN == null || productInfo.OPTION_GUBN == "" }'>checked</c:if>
								/>
								미사용
							</label>
						</p>
						<!-- END 상품 옵션 설정 -->
						
					</div>
					<div class="form-group">
						<label for="KEEP_GUBN" class="col-md-2 control-label">입수량</label>
						<div class="col-md-4">
							<input name="INPUT_CNT" id="INPUT_CNT" value="<fmt:formatNumber value="${ productInfo.INPUT_CNT }" pattern="#,###"/>" 
								type="text" class="form-control input-sm number" 
								style="text-align:right;" maxlength="15" title="입수량">
						</div>
					</div>
					<div class="form-group">
						<label for="KEEP_GUBN" class="col-md-2 control-label"> 박스할인여부</label>
						<div class="col-md-4">
							<p class="form-control-static">
								<label class="check" style="margin-right:10px">
									<input type="radio" class="iradio" name="BOX_PDDC_GUBN" value="PDDC_GUBN_02" 
										<c:if test='${productInfo.BOX_PDDC_GUBN=="PDDC_GUBN_02" }'>checked</c:if> 
										onclick="fn_boxpddc_disabled()"/>
									할인금액
								</label>
								<label class="check" style="margin-right:10px">
									<input type="radio" class="iradio" name="BOX_PDDC_GUBN" value="PDDC_GUBN_03" 
										<c:if test='${productInfo.BOX_PDDC_GUBN=="PDDC_GUBN_03" }'>checked</c:if>
										onclick="fn_boxpddc_disabled()"/>
									할인율
								</label>
								<label class="check" style="margin-right:10px">
									<input type="radio" class="iradio" name="BOX_PDDC_GUBN" value="PDDC_GUBN_01" 
										<c:if test='${productInfo.BOX_PDDC_GUBN=="PDDC_GUBN_01"||productInfo.BOX_PDDC_GUBN==null}'>checked</c:if>
										onclick="fn_boxpddc_disabled()"/>
									미사용
								</label>
							</p>
						</div>
						<div class="col-md-2">
							<div class="input-group">
								<input name="BOX_PDDC_VAL" id="BOX_PDDC_VAL" value="<fmt:formatNumber value="${ productInfo.BOX_PDDC_VAL }" pattern="#,###"/>" 
									type="text" class="form-control input-sm number" style="text-align:right;" maxlength="15" title="박스할인 값">
							</div>
						</div>
					</div>
					<div class="form-group">
						<label for="KEEP_GUBN" class="col-md-2 control-label"> 보관기준</label>
						<div class="col-md-5">
							<p class="form-control-static">
				                <jsp:include page="/common/comCodForm">
									<jsp:param name="COMM_CODE" value="KEEP_GUBN" />
									<jsp:param name="name" value="KEEP_GUBN" />
									<jsp:param name="value" value="${ productInfo.KEEP_GUBN }" />
									<jsp:param name="type" value="select" />
									<jsp:param name="top" value="선택" />
								</jsp:include>
							</p>
						</div>
					</div>
					<div class="form-group">
						<label for="KEEP_GUBN" class="col-md-2 control-label"> 팩킹기준</label>
						<div class="col-md-5">
							<p class="form-control-static">
				                <jsp:include page="/common/comCodForm">
									<jsp:param name="COMM_CODE" value="PACKING_GUBN" />
									<jsp:param name="name" value="PACKING_GUBN" />
									<jsp:param name="value" value="${ productInfo.PACKING_GUBN }" />
									<jsp:param name="type" value="select" />
									<jsp:param name="top" value="선택" />
								</jsp:include>
							</p>
						</div>
					</div>
					
					<div class="form-group">
						<label for="ORG_CT" class="col-md-2 control-label">일배상품구분</label>
						<div class="col-md-4">
							<p class="form-control-static">
				                <jsp:include page="/common/comCodForm">
									<jsp:param name="COMM_CODE" value="ONDY_GUBN" />
									<jsp:param name="name" value="ONDY_GUBN" />
									<jsp:param name="value" value="${ productInfo.ONDY_GUBN }" />
									<jsp:param name="type" value="radio" />
									<jsp:param name="top" value="" />
								</jsp:include>
							</p>
						</div>
					</div>

					<div class="form-group">
						<label for="KEEP_LOCATION" class="col-md-2 control-label"> 보관위치</label>
						<div class="col-md-10">
							<input name="KEEP_LOCATION" value="${ productInfo.KEEP_LOCATION }" type="text" class="form-control input-sm" maxlength="50" title="제조사(브랜드)">
						</div>
					</div>
					
					<div class="form-group">
						<label for="PD_DINFO" class="col-md-2 control-label">상품 상세정보</label>
						<div class="col-md-10">
		                    <textarea id="txtaPD_DINFO" rows="15">${productInfo.PD_DINFO } </textarea>
		                    <input name="PD_DINFO" id="PD_DINFO" type="hidden" />
						</div>
					</div>
					<div class="form-group">
						<label for="DLVREF_GUIDE" class="col-md-2 control-label">배송/환불 안내</label>
						<div class="col-md-10">
		                    <textarea id="txtaDLVREF_GUIDE" rows="15">${productInfo.DLVREF_GUIDE } </textarea>
		                    <input name="DLVREF_GUIDE" id="DLVREF_GUIDE" type="hidden" />
						</div>
					</div>
					<c:if test="${ empty(productInfo.PD_CODE) || empty(fileDtlList) }">
		                    <input name="DTL_USE_YN" id="DTL_USE_YN" type="hidden" value="Y" />
					</c:if>
					<c:if test="${ !empty(productInfo.PD_CODE) && !empty(fileDtlList) }">
						<div class="form-group">
							<label for="TAX_GUBN" class="col-md-2 control-label"> 상세 첨부 사용여부</label>
							<div class="col-md-10">
								<p class="form-control-static">
					                <jsp:include page="/common/comCodForm">
										<jsp:param name="COMM_CODE" value="COMM_YN" />
										<jsp:param name="name" value="DTL_USE_YN" />
										<jsp:param name="value" value="${ productInfo.DTL_USE_YN }" />
										<jsp:param name="type" value="radio" />
										<jsp:param name="top" value="" />
									</jsp:include>
								</p>
							</div>
						</div>
						<div class="form-group">                                        
					        <label class="col-md-2 control-label">상세 첨부파일 목록</label>
					        <div class="col-md-10">	                
				                <div class="col-md-10">
						        	<c:forEach var="var" items="${ fileDtlList }" varStatus="status">
										<label class="file_${var.ATFL_ID}_${var.ATFL_SEQ}" for="seq${var.ATFL_SEQ}">${var.ORFL_NAME }(경로 : ${contextPath }/common/commonFileDown?ATFL_ID=${var.ATFL_ID }&ATFL_SEQ=${var.ATFL_SEQ})</label>
										<button type="button" class="btn btn-xs btn-primary file_${var.ATFL_ID}_${var.ATFL_SEQ}" onclick="javascript:doImgPop('${contextPath }/common/commonFileDown?ATFL_ID=${var.ATFL_ID }&ATFL_SEQ=${var.ATFL_SEQ}')" >이미지 보기</button>
										<button type="button" class="btn btn-xs btn-danger file_${var.ATFL_ID}_${var.ATFL_SEQ}" onclick="javascript:fn_fileDelete(this, '${var.ATFL_ID}', '${var.ATFL_SEQ}')">삭제</button>
						        		<br>
					                </c:forEach>
								</div>    
							</div>
						</div>
				    </c:if>
				    <div class="form-group">                                        
				        <label class="col-md-2 control-label">상세이미지 첨부</label>
				        <div class="col-md-3">
			                <input name="fileDtl" type="file" multiple id="file-simple">
				        </div>
				    </div>
					<c:if test="${ !empty(productInfo.PD_CODE) && !empty(fileList)  }">
						<div class="form-group">                                        
					        <label class="col-md-2 control-label">대표이미지 목록</label>
					        <div class="col-md-10">	                
				                <div class="col-md-10">
						        	<c:forEach var="var" items="${ fileList }" varStatus="status">
										<input class="file_${var.ATFL_ID}_${var.ATFL_SEQ}" name="mainFileOrdrIn" type="radio" id="seq${var.ATFL_SEQ}" value="${var.ATFL_SEQ}" ${productInfo.RPIMG_SEQ eq var.ATFL_SEQ ? "checked" : ""} />
										<label class="file_${var.ATFL_ID}_${var.ATFL_SEQ}" for="seq${var.ATFL_SEQ}">${var.ORFL_NAME }</label>
										<button type="button" class="btn btn-xs btn-primary file_${var.ATFL_ID}_${var.ATFL_SEQ}" onclick="javascript:doImgPop('${contextPath }/common/commonFileDown?ATFL_ID=${var.ATFL_ID }&ATFL_SEQ=${var.ATFL_SEQ}')" >이미지 보기</button>
										<button type="button" class="btn btn-xs btn-danger file_${var.ATFL_ID}_${var.ATFL_SEQ}" onclick="javascript:fn_fileDelete(this, '${var.ATFL_ID}', '${var.ATFL_SEQ}')">삭제</button>
						        		<br>
					                </c:forEach>
								</div>             
				                <div class="col-md-10">
				                * 대표이미지를 지정
				                </div>
							</div>
						</div>
				    </c:if>
				    <div class="form-group">                                        
				        <label class="col-md-2 control-label">대표이미지 첨부</label>
				        <div class="col-md-3">
			                <input name="file" type="file" multiple id="file-simple">
				        </div>
				    </div>
				    
					<div class="form-group">
						<label for="INVEN_QTY" class="col-md-2 control-label"> 연계여부</label>
						<div class="col-md-2">
			                <jsp:include page="/common/comCodForm">
								<jsp:param name="COMM_CODE" value="COMM_YN" />
								<jsp:param name="name" value="LINK_YN" />
								<jsp:param name="value" value="${ productInfo.LINK_YN }" />
								<jsp:param name="type" value="radio" />
								<jsp:param name="top" value="" />
							</jsp:include>
						</div>
					</div>
				</div>
				<!-- /.box-body -->
			
			</div>

			<!-- this row will not appear when printing -->
			<div class="row">
				<div class="col-xs-12">
					<a href="${contextPath}/adm/productMgr?pageNum=${param.pageNum }&rowCnt=${param.rowCnt }${link}" class="btn btn-success pull-right"><i class="fa fa-list"></i> 목록</a>
					<button type="button" id="btnSave" class="btn btn-primary pull-right" style="margin-right: 5px;">
						<i class="fa fa-save"></i> 저장
					</button>
					<c:if test="${ !empty productInfo.PD_CODE }">
						<button type="button" class="btn btn-danger pull-right btnDel" style="margin-right: 5px;">
							<i class="fa fa-remove"></i> 상품삭제 </button>
					</c:if>
				</div>
			</div>

	</spform:form>
	<!-- /.box -->
</section>

<form id="delFrm" name="delFrm" class="form-horizontal" method="post">
	<input type="hidden" id="delATFL_ID" name="ATFL_ID" value=""/>
	<input type="hidden" id="delATFL_SEQ" name="ATFL_SEQ">
</form>

<!-- Modal : 카테고리선택용 -->
<div class="modal fade" id="divCategory" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
	<div class="modal-dialog" role="document">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal" aria-label="Close">
					<span aria-hidden="true">&times;</span>
				</button>
				<h4 class="modal-title" id="myModalLabel">카테고리 선택</h4>
			</div>
			<div class="modal-body">

				<div class="box box-primary">
					<div class="box-body" style="height: 280px;">
						<a href="javascript: cateTree.openAll();"><img src="${contextPath}/resources/adminlte/plugins/tree/img/openall.gif" width="15px" /></a> | <a href="javascript: cateTree.closeAll();"><img src="${contextPath}/resources/adminlte/plugins/tree/img/closeall.gif" width="15px"/></a>
						<div class="tree_nav" style="height: 240px; overflow-y: scroll;">
							
							<script type="text/javascript">

								cateTree = new dTree('cateTree', '${contextPath }'); //dTree 생성
	
								cateTree.add(0,-1,"상품 카테고리");
								<c:forEach var="ent" items="${ categoryList }" varStatus="status">
									cateTree.add("${ent.CAGO_ID}", "${empty ent.UPCAGO_ID ? '0' : ent.UPCAGO_ID}", "${ent.CAGO_NAME}${ent.USE_YN eq 'N' ? '(미사용)' : ''}" , "javascript: fnSeleteCategory(\'${ent.CAGO_ID}\', \'${ent.CAGO_NAME}\', \'${ent.CAGO_ID_PATH}\');", "${ent.CAGO_NAME}", "","","");
								</c:forEach>
								document.write(cateTree);
							</script>
						</div>

					</div>
				</div>
				<!-- blockquote>
				  <p id="pSelectCategory">카테고리를 선택하세요.</p>
				</blockquote -->
			</div>
			<div class="modal-footer">
				<button type="button" class="btn btn-default" data-dismiss="modal">닫기</button>
			</div>
		</div>
	</div>
</div>


<!-- Modal : 세절방식 -->
<div class="modal fade" id="productCut" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" style="max-height: 100%;overflow-y: scroll;">
	<div class="modal-dialog" role="document">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal" aria-label="Close">
					<span aria-hidden="true">&times;</span>
				</button>
				<h4 class="modal-title" id="myModalLabel">세절방식</h4>
			</div>
			<div class="modal-body">
			
				<div class="box box-primary">
					<div class="box-body" >
						<table class="table table-bordered">
							<thead>
								<tr>
									<th width="80%">세절방식</th>
									<th class="tdCar">사용여부</th>
								</tr>
							</thead>
							<tbody id="tBody" >
							
								<c:if test='${ productCut==null || productCut.size()==0 }'>
									<tr>
										<input type="hidden" name = "SEQ_LST" value="0">
										<td><input type="text" class = "form-control" name = "CUT_UNIT_LST" maxlength="15"></td>
										<td>
											<select class="form-control select2" style="width: 100%;" name = "USE_YN_LST">
												<option value="Y">Y</option>
												<option value="N">N</option>
											</select>
										</td>								
									</tr>
								</c:if>
								<c:forEach var="proCut" items="${productCut }" varStatus="sta">
								<tr>
									<input type="hidden" name = "SEQ_LST" value="${proCut.SEQ }">
									<td><input type="text" class = "form-control"  value="${proCut.CUT_UNIT }" name = "CUT_UNIT_LST" readonly="readonly"></td>
									<td>
										<select class="form-control select2" style="width: 100%;" name = "USE_YN_LST">
											<option value="Y" <c:if test='${proCut.USE_YN=="Y" }'>selected="selected"</c:if>>Y</option>
											<option value="N"<c:if test='${proCut.USE_YN=="N" }'>selected="selected"</c:if>>N</option>
										</select>
									</td>	
								</tr>
								</c:forEach>
								
							</tbody>
						</table>
						<div align="right" style="margin-top:5px">
							<button type="button" class="btn btn-primary btn-sm"  onclick="javascript:proCutAdd();" >추가</button>
						</div>
					</div>
				</div>
				
			</div>
			<div class="modal-footer">
				<button type="button" class="btn btn-default" data-dismiss="modal">닫기</button>
			</div>
		</div>
	</div>
</div>

<form id="pdCodeChkFrm" name="pdCodeChkFrm" action="${contextPath }/adm/productMgr/pdCodeChk" method="post" autocomplete="off">
	<input type="hidden" id="CHK_PD_CODE" name="PD_CODE" />
</form>
<form id="pdBarCodeChkFrm" name="pdBarCodeChkFrm" action="${contextPath }/adm/productMgr/pdBarCodeChk" method="post" autocomplete="off">
	<input type="hidden" id="CHK_PD_BARCD" name="PD_BARCD" />
</form>



<script>
var strSelectCagoId = "";
var strSelectCagoName = "";

var numProCut = 0//"${productCut.size()}";

function proCutAdd(){
	//numProCut++;		
	$('#tBody').append('<tr><input type="hidden" name = "SEQ_LST" value="'+numProCut+'"><td><input type="text" class = "form-control" name = "CUT_UNIT_LST" maxlength="15"></td><td><select class="form-control select2" style="width: 100%;" name = "USE_YN_LST"><option value="Y">Y</option><option value="N">N</option></select></td></tr>');
	
}

$(function() {
	
	$('#productCutBtn').on('click',function(){
		$('#productCut').modal('show');
	})

	CKEDITOR.replace('txtaPD_DINFO');
	CKEDITOR.replace('txtaDLVREF_GUIDE');

    //숫자만 입력토록 함.
    $(document).on("keyup", ".number", function() {
    	$(this).number(true);
    });

	//레이어 초기화
	$(".divAdd").hide();			//추가컬럼 숨김
	$(".divInputCode").hide();		//수동입력 숨김
	//$(".divInputBarCode").hide();		//수동입력 숨김
	calculPrice();						//행사가표시
	fn_chk_rta_yn();					//리테일상품사용여부 초기값
	
	var bToggle = true;
	var strToggle = "";
	$("#btnAddColumns").click(function(){
		 
		if(bToggle){
			strToggle = "추가 컬럼 닫기";
			$(".divAdd").show();
		}else{
			strToggle = "추가 컬럼 열기";
			$(".divAdd").hide();
		}
		
		$(this).text(strToggle);
		
		bToggle = !bToggle;
	});

	//상품코드 입력 변경시
	$("input:radio[name='PD_CODE_IN']").change(function(){
		var strChkVal = $("input:radio[name='PD_CODE_IN']:checked").val();
		
		if(strChkVal == "INPUT"){
			$(".divInputCode").show();
		}else{
			$(".divInputCode").hide();
		}
	});
	
	//상품바코드 입력 변경시
	$("input:radio[name='PD_BARCD_IN']").change(function(){
		var strChkVal = $("input:radio[name='PD_BARCD_IN']:checked").val();
		
		if(strChkVal == "INPUT"){
			$(".divInputBarCode").show();
		}else{
			$(".divInputBarCode").hide();
		}
	});

	
	//상품할인 변경시
	$("input:radio[name='PDDC_GUBN']").change(function(){
		var strChkVal = $("input:radio[name='PDDC_GUBN']:checked").val();
		
		if(strChkVal == "PDDC_GUBN_01"){
			$("#PDDC_VAL").val("0");
		}
		
		calculPrice();
	});

	$("#btnCateSel").click(function(){
		$('#divCategory').modal('show');
	});
	
	$("#btnSetCategory").click(function(){
		if(strSelectCagoId == ""){
			alert("적용할 카테고리를 선택하세요.");
			return;
		}
		
		$("#CAGO_NAME").val(strSelectCagoName);
		$("#CAGO_ID").val(strSelectCagoId);

		//초기화
		strSelectCagoId = "";
		strSelectCagoName = "";
		cateTree.closeAll();
		
		$('#divCategory').modal('hide');
	});
	
	//재고수량 변경시_20190306 chjw
	$("#INVEN_QTY").change(function(){
		/* alert($("#INVEN_QTY").val()); */
		
		if($("#INVEN_QTY").val() == 0){
			/* alert($("input:radio[name='SALE_CON']:checked").val()); */
			$('input:radio[name=SALE_CON]:input[value="SALE_CON_02"]').prop("checked", true);	/* attr()과 prop() 차이 */
			/* alert("ffff============="+$('input:radio[name=SALE_CON]:checked').val()); */
		}
	});
	
	//판매상태 변경시_20190306 chjw
	$("input:radio[name='SALE_CON']").change(function(){		
		/* alert($("input:radio[name='SALE_CON']:checked").val()); */
		
		if($("input:radio[name='SALE_CON']:checked").val() != "SALE_CON_02"){
			if($("#INVEN_QTY").val() == 0){
				alert("재고수량을 확인해주세요.");
				/* $('input:radio[name=SALE_CON]:input[value="SALE_CON_02"]').attr("checked", true); */
			}	
		}
	});
	
	
	$("#btnSave").click(function(){

		// 재고수량 0일경우 품절표시
		if($("#INVEN_QTY").val() == 0){
			/* alert($("#INVEN_QTY").val());  */
			$("input:radio[name='SALE_CON']").val("SALE_CON_02");
		}
		
		var strChkVal = $("input:radio[name='PD_CODE_IN']:checked").val();
		
		if(strChkVal == "INPUT"){
			if($('#PD_CODE_CHK_YN').val() == "N"){
				alert("상품코드 중복확인 후 저장하세요.");
				$("#MEMB_ID").focus();
				return false;	
			}
			
		}
		
		//var strChkVal2 = $("input:radio[name='PD_BARCD_IN']:checked").val();
		var strChkVal2 = $("input[name='PD_BARCD_IN']").val();
		
		if(strChkVal2 == "INPUT"){
			if($('#PD_BARCD_CHK_YN').val() == "N"){
				alert("상품바코드 중복확인 후 저장하세요.");	
				return false;	
			}
			
		}


		
		var strPD_DINFO = CKEDITOR.instances.txtaPD_DINFO.getData();
		var strDLVREF_GUIDE = CKEDITOR.instances.txtaDLVREF_GUIDE.getData();
		
		$("#PD_DINFO").val(strPD_DINFO);
		$("#DLVREF_GUIDE").val(strDLVREF_GUIDE);

		$("#RPIMG_SEQ").val( $(':radio[name="mainFileOrdrIn"]:checked').val());
		if($(':radio[name="mainFileOrdrIn"]:checked').val() == null || $(':radio[name="mainFileOrdrIn"]:checked').val() == undefined || $(':radio[name="mainFileOrdrIn"]:checked').val() == ''){
			$("#RPIMG_SEQ").val("1");
		}
		
		if($('input:radio[name="PDDC_GUBN"]:checked').val() == "PDDC_GUBN_01"){
			$("#PDDC_VAL").val("0");
		}
		if($("#PDDC_VAL").val()==null ||$("#PDDC_VAL").val()==""){	//할인구분 값 null이면 0으로 셋팅
			$("#PDDC_VAL").val("0");
		}
		

		var seq_lst = document.getElementsByName("SEQ_LST");
		var use_yn_lst = document.getElementsByName('USE_YN_LST'); 
		var cut_unit_lst = document.getElementsByName('CUT_UNIT_LST');
		
		var seq_str ="";
		var use_yn_str = "";
		var cut_unit_str = "";

		for (var i = 0; i < seq_lst.length; i++) {
			if(cut_unit_lst[i].value !=""){
				if(seq_str==""){
					seq_str +=""+seq_lst[i].value;
				}else{
					seq_str +="!!@"+seq_lst[i].value;
				}
			}
	    }
		for (var i = 0; i < use_yn_lst.length; i++) {
			if(cut_unit_lst[i].value !=""){
				if(use_yn_str==""){
					use_yn_str +=""+use_yn_lst[i].value;					
				}else{
					use_yn_str +="!!@"+use_yn_lst[i].value;
				}
			}
	    }
		for (var i = 0; i < cut_unit_lst.length; i++) {
			if(cut_unit_lst[i].value !=""){
				if(cut_unit_str==""){
					cut_unit_str +=""+cut_unit_lst[i].value;
				}else{
					cut_unit_str +="!!@"+cut_unit_lst[i].value;					
				}
			}
	    }
		
		
		$("#SEQ").val(seq_str)
		$("#USE_YN").val(use_yn_str)
		$("#CUT_UNIT").val(cut_unit_str)
		
		$("#saveFrm").submit();
		
	});
	
	
	$(".btnDel").click(function(){
		if(!confirm("삭제하시겠습니까?")) return;
		$("#saveFrm").attr("action","${contextPath }/adm/productMgr/deletePrd");
		$("#saveFrm").submit();
	});
	
	
    $('#saveFrm').validate({
        debug: false,
        onfocusout: false,
        rules: {
        	PD_NAME: {
                required: true
            },
            CAGO_NAME: {
                required: true
            },
            PD_PRICE: {
                required: true
            },
            INVEN_QTY: {
                required: true
            }
        }, messages: {
        	PD_NAME: {
                required: '상품명을 입력하세요.'
            },
            CAGO_ID: {
                required: '카테고리를 선택하세요.'
            },
            PD_PRICE: {
                required: '상품가격을 입력하세요.'
            },
            INVEN_QTY: {
                required: '제고수량을 입력하세요.'
            }
        }, errorPlacement: function(error, element) {
            // do nothing
        }, invalidHandler: function(form, validator) {
             var errors = validator.numberOfInvalids();
             if (errors) {
                 alert("error=="+validator.errorList[0].message);
                 validator.errorList[0].element.focus();
             }          
        }
    });
    
  //상품검색 팝업 호출
	$("#btnGoodsPopup").click(function(){
		window.open("${contextPath }/adm/productMgr/popup", "_blank", "width=900,height=800"); 
	});
    
	//상품검색 팝업 호출
	$("#btnDupChk").click(function(){

		var strPdCode = $("#PD_CODE").val();
		$("#CHK_PD_CODE").val(strPdCode);
		
		if(strPdCode == ""){
			alert("상품코드를 입력해 주세요");
			return false;
		}
		
		$.ajax({
			type: "POST",
			url: "${contextPath}/adm/productMgr/pdCodeChk",
			data: $("#pdCodeChkFrm").serialize(),
			success: function (data) {

				// 제품코드 중복 여부
				if (data == '0') {
					$('#chkTxt').html("<span><font color='blue'>사용할 수 있는 제품코드입니다.</font></span>");
					$('#PD_CODE_CHK_YN').val("Y");
				}else{
					$('#chkTxt').html("<span><font color='red'>사용할 수 없는 제품코드입니다.</font></span>");
					$('#PD_CODE_CHK_YN').val("N");
				}
			}, error: function (jqXHR, textStatus, errorThrown) {
				alert("처리중 에러가 발생했습니다. 관리자에게 문의 하세요.(error:"+textStatus+")");
			}
		});
		
	});
	
	//상품바코드 중복확인
	$("#btnBarcdDupChk").click(function(){

		var strPdCode = $("#PD_BARCD").val();
		$("#CHK_PD_BARCD").val(strPdCode);
		
		if(strPdCode == ""){
			alert("상품바코드를 입력해 주세요");
			return false;
		}
		if($('#ORG_BARCD').val()==$('#PD_BARCD').val()){
			$('#PD_BARCD_CHK_YN').val("Y");
			$('#chkBarcdTxt').html("<span><font color='blue'>기존에 사용하던 제품바코드입니다.</font></span>");
		}else{
			$.ajax({
				type: "POST",
				url: "${contextPath}/adm/productMgr/pdBarCodeChk",
				data: $("#pdBarCodeChkFrm").serialize(),
				success: function (data) {
	
					// 제품코드 중복 여부
					if (data == '0') {
						$('#chkBarcdTxt').html("<span><font color='blue'>사용할 수 있는 제품바코드입니다.</font></span>");
						$('#PD_BARCD_CHK_YN').val("Y");
					}else{
						$('#chkBarcdTxt').html("<span><font color='red'>사용할 수 없는 제품바코드입니다.</font></span>");
						$('#PD_BARCD_CHK_YN').val("N");
					}
				}, error: function (jqXHR, textStatus, errorThrown) {
					alert("처리중 에러가 발생했습니다. 관리자에게 문의 하세요.(error:"+textStatus+")");
				}
			});
		}
		
	});
	

	//배송/환불안내 초기값 
	if($('#txtaDLVREF_GUIDE').text()==''){
		var dlvTxt = '';
		dlvTxt += '교환/반품 안내 &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; <br />';
		dlvTxt += '1.교환/반품은 상품수령후 7일이내로 가능합니다 &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; <br />';
		dlvTxt += '2.상품의 불량/하자인 경우 해당 상품회수 비용은 무료입니다. &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; <br />';
		dlvTxt += '&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;<br />';
		dlvTxt += '교환 및 반품 불가능한 경우 &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; <br />';
		dlvTxt += '1. 냉장,냉동식품의 경우(반송시재판매불가) &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; <br />';
		dlvTxt += '2. 고객님의 책임사유있는이유로 포장된 상품이 훼손된 경우 &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; <br />';
		dlvTxt += '3. 시간이 경과되어 재판매가 곤란할 정도로 상품의 가치가 상실된경우 &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; <br />';
		dlvTxt += '4. 고객센터와협의없이 임의로 반송한경우 &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; <br />';
		dlvTxt += '5. 기타전자상거래 등에서의 소비자보호에 관한 법률이 정하는 소비자청양철회 제한 내용에 해당되는 경우 &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; <br />';
		dlvTxt += '<br />';
		
		$('#txtaDLVREF_GUIDE').text(dlvTxt)
	}
	
	//한정수량 999개 기본
	if($('#LIMIT_QTY').val()==''){
		$('#LIMIT_QTY').val('999');
	}
	
	//박스할인부분 적용
	fn_boxpddc_disabled();
    
});


var cagoIdPath = '${ productInfo.CAGO_ID_PATH }';
var preCagoRootId = "";

if(cagoIdPath != "" && cagoIdPath != "null"){
	preCagoRootId = cagoIdPath.split( '>' )[0];
}
	
//카테고리 선택
function fnSeleteCategory(strCagoId, strCagoName, strCagoIdPath){
	/* 
	바로 선택으로 변경
	strSelectCagoId = strCagoId;
	strSelectCagoName = strCagoName;
	
	$("#pSelectCategory").text("선택 카테고리 : " + strSelectCagoName);
	 */
	 
	$("#CAGO_NAME").val(strCagoName);
	$("#CAGO_ID").val(strCagoId);

	//초기화
	cateTree.closeAll();

	var arrCagoId = strCagoIdPath.split( '>' );
	var strHtml = "";
	
	if(preCagoRootId != "" && preCagoRootId != "null" && preCagoRootId != arrCagoId[0]){
		if(confirm("상품상세 설명이 초기화 됩니다. 적용하시겠습니까?")){
			strHtml = fnMakeHtml(arrCagoId[0]);

			if ( CKEDITOR.instances.txtaPD_DINFO.mode == 'wysiwyg' )    
			{        
				//CKEDITOR.instances.txtaPD_DINFO.insertHtml( strHtml );    
				CKEDITOR.instances.txtaPD_DINFO.setData(strHtml);
			}else{
				alert( '소스보기 모드에선 초기화 할수 없습니다.' );
			}        
		}			
	}
	
	if(preCagoRootId == "" || preCagoRootId == "null"){

		strHtml = fnMakeHtml(arrCagoId[0]);
		if ( CKEDITOR.instances.txtaPD_DINFO.mode == 'wysiwyg' ){  
			CKEDITOR.instances.txtaPD_DINFO.setData(strHtml);
		}else{
			alert( '소스보기 모드에선 초기화 할수 없습니다.' );
		}
		
	}


	preCagoRootId = arrCagoId[0];

	
	$('#divCategory').modal('hide'); 
}

function fnMakeHtml(topCagoId)
{
	var rtnHtml = "";
	var strPdName = $("#PD_NAME").val();
	
	if(strPdName == "" || strPdName == "null"){
		strPdName = "상세설명참조";
	}
	
	//가공식품
	if(topCagoId == '100000000'){		//010000000000
		rtnHtml += "<p>전자상거래 등에서의 상품정보제공고시(가공식품)</p>";
		rtnHtml += "<table>";
		rtnHtml += "	<tbody>";
		rtnHtml += "		<tr>";
		rtnHtml += "			<th style='width:170px'>상품명</th>";
		rtnHtml += "			<td>"+strPdName+"</td>";
		rtnHtml += "			<th style='width:170px'>식품유형</th>";
		rtnHtml += "			<td>상세설명참조</td>";
		rtnHtml += "		</tr>";
		rtnHtml += "		<tr>";
		rtnHtml += "			<th>생산자및소재지(수입품의경우 생산자,수입자 및 제조국)</th>";
		rtnHtml += "			<td>상세설명참조</td>";
		rtnHtml += "			<th style='color:red'>제조연월일,유통기한또는품질유지기한</th>";
		rtnHtml += "			<td style='color:red'>상세설명참조</td>";
		rtnHtml += "		</tr>";
		rtnHtml += "		<tr>";
		rtnHtml += "			<th>포장단위별 용량(중량),수량</th>";
		rtnHtml += "			<td>상세설명참조</td>";
		rtnHtml += "			<th>원재료명 및 함량</th>";
		rtnHtml += "			<td>상세설명참조</td>";
		rtnHtml += "		</tr>";
		rtnHtml += "		<tr>";
		rtnHtml += "			<th>영양성분(식품위생법에 따른 영양성분 표시대상 식품에 한함)</th>";
		rtnHtml += "			<td>상세설명참조</td>";
		rtnHtml += "			<th>유전자재조합식품에 해당하는 경우의 표시</th>";
		rtnHtml += "			<td>해당없음</td>";
		rtnHtml += "		</tr>";
		rtnHtml += "		<tr>";
		rtnHtml += "			<th>영유아식 또는 체중조절식품 등에 해당하는 경우 표시광고 사전 심의필 유무 및 부작용발생 가능성</th>";
		rtnHtml += "			<td>해당없음</td>";
		rtnHtml += "			<th>수입식품에 해당하는 겨우 &quot;식품위생법에 따른 수입신고를 필함&quot;의 문구</th>";
		rtnHtml += "			<td>상세설명참조</td>";
		rtnHtml += "		</tr>";
		rtnHtml += "		<tr>";
		rtnHtml += "			<th>섭취량,섭취방법,섭취시주의사항</th>";
		rtnHtml += "			<td>상세설명참조</td>";
		rtnHtml += "			<th>보관방법/취급방법</th>";
		rtnHtml += "			<td>상세설명참조</td>";
		rtnHtml += "		</tr>";
		rtnHtml += "		<tr>";
		rtnHtml += "			<th>소비자상담관련전화번호</th>";
		rtnHtml += "			<td>상세설명참조</td>";
		rtnHtml += "			<th>&nbsp;</th>";
		rtnHtml += "			<td>&nbsp;</td>";
		rtnHtml += "		</tr>";
		rtnHtml += "	</tbody>";
		rtnHtml += "</table>";
	}	
	//양곡
	else if(topCagoId == '140000000'){	//020000000000
		rtnHtml += "<p>전자상거래 등에서의 상품정보제공고시(양곡)</p>";
		rtnHtml += "<table>";
		rtnHtml += "	<tbody>";
		rtnHtml += "		<tr>";
		rtnHtml += "			<th style='width:170px'>상품명</th>";
		rtnHtml += "			<td>"+strPdName+"</td>";
		rtnHtml += "			<th style='width:170px'>식품유형</th>";
		rtnHtml += "			<td>상세설명참조</td>";
		rtnHtml += "		</tr>";
		rtnHtml += "		<tr>";
		rtnHtml += "			<th>생산자및소재지(수입품의경우 생산자,수입자 및 제조국)</th>";
		rtnHtml += "			<td>상세설명참조</td>";
		rtnHtml += "			<th>제조연월일,유통기한또는품질유지기한</th>";
		rtnHtml += "			<td>상품입고시 마다 제조일이 상이하여 유통기한이 다릅니다.배송되는 상품은 선입선출에 의해 출고되오니 안심하고 구입하시기 바랍니다. 유통기한에 대한 궁금하신사항은 문의글이나 전화를 주시면 상세히 안내해 드리겠습니다.</td>";
		rtnHtml += "		</tr>";
		rtnHtml += "		<tr>";
		rtnHtml += "			<th>농수축산물품질관리법상 유전자변형농산물 표시,지리적 표시</th>";
		rtnHtml += "			<td>상세설명참조</td>";
		rtnHtml += "			<th>포장단위별 용량(중량),수량</th>";
		rtnHtml += "			<td>상세설명참조</td>";
		rtnHtml += "		</tr>";
		rtnHtml += "		<tr>";
		rtnHtml += "			<th>원재료명 및 함량</th>";
		rtnHtml += "			<td>상세설명참조</td>";
		rtnHtml += "			<th>영양성분(식품위생법에 따른 영양성분 표시대상 식품에 한함)</th>";
		rtnHtml += "			<td>상세설명참조</td>";
		rtnHtml += "		</tr>";
		rtnHtml += "		<tr>";
		rtnHtml += "			<th>유전자재조합식품에 해당하는 경우의 표시</th>";
		rtnHtml += "			<td>해당없음</td>";
		rtnHtml += "			<th>영유아식 또는 체중조절식품 등에 해당하는 경우 표시광고 사전 심의필 유무 및 부작용발생 가능성</th>";
		rtnHtml += "			<td>해당없음</td>";
		rtnHtml += "		</tr>";
		rtnHtml += "		<tr>";
		rtnHtml += "			<th>수입식품에 해당하는 겨우 &quot;식품위생법에 따른 수입신고를 필함&quot;의 문구</th>";
		rtnHtml += "			<td>상세설명참조</td>";
		rtnHtml += "			<th>섭취량,섭취방법,섭취시주의사항</th>";
		rtnHtml += "			<td>상세설명참조</td>";
		rtnHtml += "		</tr>";
		rtnHtml += "		<tr>";
		rtnHtml += "			<th>보관방법/취급방법</th>";
		rtnHtml += "			<td>상세설명참조</td>";
		rtnHtml += "			<th>소비자상담관련전화번호</th>";
		rtnHtml += "			<td>상세설명참조</td>";
		rtnHtml += "		</tr>";
		rtnHtml += "	</tbody>";
		rtnHtml += "</table>";

	}
	//농산물
	else if(topCagoId == '110000000'){		//030000000000
		rtnHtml += "<p>전자상거래 등에서의 상품정보제공고시(농산물)</p>";
		rtnHtml += "<table>";
		rtnHtml += "	<tbody>";
		rtnHtml += "		<tr>";
		rtnHtml += "			<th style='width:170px'>상품명</th>";
		rtnHtml += "			<td>"+strPdName+"</td>";
		rtnHtml += "			<th style='width:170px'>식품유형</th>";
		rtnHtml += "			<td>상세설명참조</td>";
		rtnHtml += "		</tr>";
		rtnHtml += "		<tr>";
		rtnHtml += "			<th>생산자및소재지(수입품의경우 생산자,수입자 및 제조국)</th>";
		rtnHtml += "			<td>상세설명참조</td>";
		rtnHtml += "			<th>제조연월일,유통기한또는품질유지기한</th>";
		rtnHtml += "			<td>상품입고시 마다 제조일이 상이하여 유통기한이 다릅니다.배송되는 상품은 선입선출에 의해 출고되오니 안심하고 구입하시기 바랍니다. 유통기한에 대한 궁금하신사항은 문의글이나 전화를 주시면 상세히 안내해 드리겠습니다.</td>";
		rtnHtml += "		</tr>";
		rtnHtml += "		<tr>";
		rtnHtml += "			<th>농수축산물품질관리법상 유전자변형농산물 표시,지리적 표시</th>";
		rtnHtml += "			<td>상세설명참조</td>";
		rtnHtml += "			<th>포장단위별 용량(중량),수량</th>";
		rtnHtml += "			<td>상세설명참조</td>";
		rtnHtml += "		</tr>";
		rtnHtml += "		<tr>";
		rtnHtml += "			<th>영양성분(식품위생법에 따른 영양성분 표시대상 식품에 한함)</th>";
		rtnHtml += "			<td>상세설명참조</td>";
		rtnHtml += "			<th>유전자재조합식품에 해당하는 경우의 표시</th>";
		rtnHtml += "			<td>해당없음</td>";
		rtnHtml += "		</tr>";
		rtnHtml += "		<tr>";
		rtnHtml += "			<th>영유아식 또는 체중조절식품 등에 해당하는 경우 표시광고 사전 심의필 유무 및 부작용발생 가능성</th>";
		rtnHtml += "			<td>해당없음</td>";
		rtnHtml += "			<th>수입식품에 해당하는 겨우 &quot;식품위생법에 따른 수입신고를 필함&quot;의 문구</th>";
		rtnHtml += "			<td>상세설명참조</td>";
		rtnHtml += "		</tr>";
		rtnHtml += "		<tr>";
		rtnHtml += "			<th>섭취량,섭취방법,섭취시주의사항</th>";
		rtnHtml += "			<td>상세설명참조</td>";
		rtnHtml += "			<th>보관방법/취급방법</th>";
		rtnHtml += "			<td>상세설명참조</td>";
		rtnHtml += "		</tr>";
		rtnHtml += "		<tr>";
		rtnHtml += "			<th>소비자상담관련전화번호</th>";
		rtnHtml += "			<td>상세설명참조</td>";
		rtnHtml += "			<th>&nbsp;</th>";
		rtnHtml += "			<td>&nbsp;</td>";
		rtnHtml += "		</tr>";
		rtnHtml += "	</tbody>";
		rtnHtml += "</table>";

	}
	//수산물
	else if(topCagoId == '130000000'){		//040000000000
		rtnHtml += "<p>전자상거래 등에서의 상품정보제공고시(수산물)</p>";
		rtnHtml += "<table>";
		rtnHtml += "	<tbody>";
		rtnHtml += "		<tr>";
		rtnHtml += "			<th style='width:170px'>상품명</th>";
		rtnHtml += "			<td>"+strPdName+"</td>";
		rtnHtml += "			<th style='width:170px'>식품유형</th>";
		rtnHtml += "			<td>상세설명참조</td>";
		rtnHtml += "		</tr>";
		rtnHtml += "		<tr>";
		rtnHtml += "			<th>생산자및소재지(수입품의경우 생산자,수입자 및 제조국)</th>";
		rtnHtml += "			<td>상세설명참조</td>";
		rtnHtml += "			<th>제조연월일,유통기한또는품질유지기한</th>";
		rtnHtml += "			<td>상품입고시 마다 제조일이 상이하여 유통기한이 다릅니다.배송되는 상품은 선입선출에 의해 출고되오니 안심하고 구입하시기 바랍니다. 유통기한에 대한 궁금하신사항은 문의글이나 전화를 주시면 상세히 안내해 드리겠습니다.</td>";
		rtnHtml += "		</tr>";
		rtnHtml += "		<tr>";
		rtnHtml += "			<th>농수축산물품질관리법상 유전자변형농산물 표시,지리적 표시</th>";
		rtnHtml += "			<td>상세설명참조</td>";
		rtnHtml += "			<th>포장단위별 용량(중량),수량</th>";
		rtnHtml += "			<td>상세설명참조</td>";
		rtnHtml += "		</tr>";
		rtnHtml += "		<tr>";
		rtnHtml += "			<th>영양성분(식품위생법에 따른 영양성분 표시대상 식품에 한함)</th>";
		rtnHtml += "			<td>상세설명참조</td>";
		rtnHtml += "			<th>유전자재조합식품에 해당하는 경우의 표시</th>";
		rtnHtml += "			<td>해당없음</td>";
		rtnHtml += "		</tr>";
		rtnHtml += "		<tr>";
		rtnHtml += "			<th>영유아식 또는 체중조절식품 등에 해당하는 경우 표시광고 사전 심의필 유무 및 부작용발생 가능성</th>";
		rtnHtml += "			<td>해당없음</td>";
		rtnHtml += "			<th>수입식품에 해당하는 겨우 &quot;식품위생법에 따른 수입신고를 필함&quot;의 문구</th>";
		rtnHtml += "			<td>상세설명참조</td>";
		rtnHtml += "		</tr>";
		rtnHtml += "		<tr>";
		rtnHtml += "			<th>섭취량,섭취방법,섭취시주의사항</th>";
		rtnHtml += "			<td>상세설명참조</td>";
		rtnHtml += "			<th>보관방법/취급방법</th>";
		rtnHtml += "			<td>상세설명참조</td>";
		rtnHtml += "		</tr>";
		rtnHtml += "		<tr>";
		rtnHtml += "			<th>소비자상담관련전화번호</th>";
		rtnHtml += "			<td>상세설명참조</td>";
		rtnHtml += "			<th>&nbsp;</th>";
		rtnHtml += "			<td>&nbsp;</td>";
		rtnHtml += "		</tr>";
		rtnHtml += "	</tbody>";
		rtnHtml += "</table>";

	}
	//축산물
	else if(topCagoId == '160000000'){		//050000000000
		rtnHtml += "<p>전자상거래 등에서의 상품정보제공고시(축산물)</p>";
		rtnHtml += "<table>";
		rtnHtml += "	<tbody>";
		rtnHtml += "		<tr>";
		rtnHtml += "			<th style='width:170px'>상품명</th>";
		rtnHtml += "			<td>"+strPdName+"</td>";
		rtnHtml += "			<th style='width:170px'>식품유형</th>";
		rtnHtml += "			<td>상세설명참조</td>";
		rtnHtml += "		</tr>";
		rtnHtml += "		<tr>";
		rtnHtml += "			<th>생산자및소재지(수입품의경우 생산자,수입자 및 제조국)</th>";
		rtnHtml += "			<td>상세설명참조</td>";
		rtnHtml += "			<th>제조연월일,유통기한또는품질유지기한</th>";
		rtnHtml += "			<td>상품입고시 마다 제조일이 상이하여 유통기한이 다릅니다.배송되는 상품은 선입선출에 의해 출고되오니 안심하고 구입하시기 바랍니다. 유통기한에 대한 궁금하신사항은 문의글이나 전화를 주시면 상세히 안내해 드리겠습니다.</td>";
		rtnHtml += "		</tr>";
		rtnHtml += "		<tr>";
		rtnHtml += "			<th>농수축산물품질관리법상 유전자변형농산물 표시,지리적 표시</th>";
		rtnHtml += "			<td>상세설명참조</td>";
		rtnHtml += "			<th>축산물법에 따른등급표시,쇠고기의 경우 이력관리에 따른 표시유무</th>";
		rtnHtml += "			<td>상세설명참조</td>";
		rtnHtml += "		</tr>";
		rtnHtml += "		<tr>";
		rtnHtml += "			<th>포장단위별 용량(중량),수량</th>";
		rtnHtml += "			<td>상세설명참조</td>";
		rtnHtml += "			<th>원재료명 및 함량</th>";
		rtnHtml += "			<td>상세설명참조</td>";
		rtnHtml += "		</tr>";
		rtnHtml += "		<tr>";
		rtnHtml += "			<th>영양성분(식품위생법에 따른 영양성분 표시대상 식품에 한함)</th>";
		rtnHtml += "			<td>상세설명참조</td>";
		rtnHtml += "			<th>유전자재조합식품에 해당하는 경우의 표시</th>";
		rtnHtml += "			<td>해당없음</td>";
		rtnHtml += "		</tr>";
		rtnHtml += "		<tr>";
		rtnHtml += "			<th>영유아식 또는 체중조절식품 등에 해당하는 경우 표시광고 사전 심의필 유무 및 부작용발생 가능성</th>";
		rtnHtml += "			<td>해당없음</td>";
		rtnHtml += "			<th>수입식품에 해당하는 겨우 &quot;식품위생법에 따른 수입신고를 필함&quot;의 문구</th>";
		rtnHtml += "			<td>상세설명참조</td>";
		rtnHtml += "		</tr>";
		rtnHtml += "		<tr>";
		rtnHtml += "			<th>섭취량,섭취방법,섭취시주의사항</th>";
		rtnHtml += "			<td>상세설명참조</td>";
		rtnHtml += "			<th>보관방법/취급방법</th>";
		rtnHtml += "			<td>냉동보관하시기 바랍니다</td>";
		rtnHtml += "		</tr>";
		rtnHtml += "		<tr>";
		rtnHtml += "			<th>소비자상담관련전화번호</th>";
		rtnHtml += "			<td>상세설명참조</td>";
		rtnHtml += "			<th>&nbsp;</th>";
		rtnHtml += "			<td>&nbsp;</td>";
		rtnHtml += "		</tr>";
		rtnHtml += "	</tbody>";
		rtnHtml += "</table>";

	}
	//비식품
	else if(topCagoId == '120000000'){		//060000000000
		rtnHtml += "<p>전자상거래 등에서의 상품정보제공고시(비식품)</p>";
		rtnHtml += "<table>";
		rtnHtml += "	<tbody>";
		rtnHtml += "		<tr>";
		rtnHtml += "			<th style='width:170px'>상품명</th>";
		rtnHtml += "			<td>"+strPdName+"</td>";
		rtnHtml += "			<th style='width:170px'>재질</th>";
		rtnHtml += "			<td>상세설명참조</td>";
		rtnHtml += "		</tr>";
		rtnHtml += "		<tr>";
		rtnHtml += "			<th>구성품</th>";
		rtnHtml += "			<td>상세설명참조</td>";
		rtnHtml += "			<th>크기</th>";
		rtnHtml += "			<td>상세설명참조</td>";
		rtnHtml += "		</tr>";
		rtnHtml += "		<tr>";
		rtnHtml += "			<th>동일모델의출시년월</th>";
		rtnHtml += "			<td>상세설명참조</td>";
		rtnHtml += "			<th>제조자,수입자</th>";
		rtnHtml += "			<td>상세설명참조</td>";
		rtnHtml += "		</tr>";
		rtnHtml += "		<tr>";
		rtnHtml += "			<th>품질보증기준</th>";
		rtnHtml += "			<td>상세설명참조</td>";
		rtnHtml += "			<th>제조국</th>";
		rtnHtml += "			<td>상세설명참조</td>";
		rtnHtml += "		</tr>";
		rtnHtml += "		<tr>";
		rtnHtml += "			<th>생산자및소재지(수입품의경우 생산자,수입자 및 제조국)</th>";
		rtnHtml += "			<td>상세설명참조</td>";
		rtnHtml += "			<th>보관방법/취급방법</th>";
		rtnHtml += "			<td>상세설명참조</td>";
		rtnHtml += "		</tr>";
		rtnHtml += "		<tr>";
		rtnHtml += "			<th>소비자상담관련전화번호</th>";
		rtnHtml += "			<td>상세설명참조</td>";
		rtnHtml += "			<th>&nbsp;</th>";
		rtnHtml += "			<td>&nbsp;</td>";
		rtnHtml += "		</tr>";
		rtnHtml += "	</tbody>";
		rtnHtml += "</table>";
	//기타
	}else{
		rtnHtml += "<p>전자상거래 등에서의 상품정보제공고시</p>";
		rtnHtml += "<table>";
		rtnHtml += "	<tbody>";
		rtnHtml += "		<tr>";
		rtnHtml += "			<th style='width:170px'>상품명</th>";
		rtnHtml += "			<td>"+strPdName+"</td>";
		rtnHtml += "			<th style='width:170px'>재질</th>";
		rtnHtml += "			<td>상세설명참조</td>";
		rtnHtml += "		</tr>";
		rtnHtml += "		<tr>";
		rtnHtml += "			<th>구성품</th>";
		rtnHtml += "			<td>상세설명참조</td>";
		rtnHtml += "			<th>크기</th>";
		rtnHtml += "			<td>상세설명참조</td>";
		rtnHtml += "		</tr>";
		rtnHtml += "		<tr>";
		rtnHtml += "			<th>동일모델의출시년월</th>";
		rtnHtml += "			<td>상세설명참조</td>";
		rtnHtml += "			<th>제조자,수입자</th>";
		rtnHtml += "			<td>상세설명참조</td>";
		rtnHtml += "		</tr>";
		rtnHtml += "		<tr>";
		rtnHtml += "			<th>품질보증기준</th>";
		rtnHtml += "			<td>상세설명참조</td>";
		rtnHtml += "			<th>제조국</th>";
		rtnHtml += "			<td>상세설명참조</td>";
		rtnHtml += "		</tr>";
		rtnHtml += "		<tr>";
		rtnHtml += "			<th>생산자및소재지(수입품의경우 생산자,수입자 및 제조국)</th>";
		rtnHtml += "			<td>상세설명참조</td>";
		rtnHtml += "			<th>보관방법/취급방법</th>";
		rtnHtml += "			<td>상세설명참조</td>";
		rtnHtml += "		</tr>";
		rtnHtml += "		<tr>";
		rtnHtml += "			<th>소비자상담관련전화번호</th>";
		rtnHtml += "			<td>상세설명참조</td>";
		rtnHtml += "			<th>&nbsp;</th>";
		rtnHtml += "			<td>&nbsp;</td>";
		rtnHtml += "		</tr>";
		rtnHtml += "	</tbody>";
		rtnHtml += "</table>";
	}
	
	return rtnHtml;
	
}

function fn_fileDelete(obj, ATFL_ID, ATFL_SEQ)
{
	if( "${productInfo.ATFL_ID }" == ATFL_ID && $(':radio[name="mainFileOrdrIn"]:checked').val() == ATFL_SEQ){
		alert("대표이미지는 삭제할 수 없습니다.");
		return;
	}
	
	if(!confirm("삭제하시겠습니까?")){
		return;
	}
	
	$("#delATFL_ID").val(ATFL_ID);
	$("#delATFL_SEQ").val(ATFL_SEQ);
	
	$.ajax({
         type:"POST",
         url:"${contextPath}/common/commonFileDelete",
         data: $("form[name='delFrm']").serialize(),
         success : function(data) {
        	 var result = data.result;
        	 var contents = "";	
	   			
	       		$('.file_'+ATFL_ID+'_'+ATFL_SEQ).remove();
	       		obj.remove();
         },
         error : function(xhr, status, error) {
               alert(error);
         }
   });
}

	
	
//상품검색 조회 결과
function fn_popup_return(GOODS_CODE,POS_NAME,CAGO_ID,CAGO_NAME){
	//var frm=document.getElementById("orderFrm");
	//frm.UPPER_MENU_CD.value=GOODS_CODE;
	//frm.UPPER_MENU_NAME.value=POS_NAME;
	$("#PD_NAME").val(POS_NAME);
	if(CAGO_ID != ""){
		$("#CAGO_NAME").val(CAGO_NAME);
		$("#CAGO_ID").val(CAGO_ID);
	}
}


var img1= new Image(); 
function doImgPop(img){ 
	img1= new Image(); 
	img1.src=(img); 
	imgControll(img); 
} 
	  
function imgControll(img){ 
	if((img1.width!=0)&&(img1.height!=0)){ 
		viewImage(img); 
	} 
	else{ 
		controller="imgControll('"+img+"')"; 
		intervalID=setTimeout(controller,20); 
	} 
}
function viewImage(img){ 
	W=img1.width+20; 
	H=img1.height+20; 
	O="width="+W+",height="+H+",scrollbars=yes"; 
	imgWin=window.open("","",O); 
	imgWin.document.write("<html><head><title>:: 이미지상세보기 ::</title></head>");
	imgWin.document.write("<body topmargin=0 leftmargin=0>");
	imgWin.document.write("<img src="+img+" onclick='self.close()' style='cursor:pointer;' title ='클릭하시면 창이 닫힙니다.'>");
	imgWin.document.close();
}
function calculPrice(){
	
	var pddcGubn =$(':radio[name="PDDC_GUBN"]:checked').val();	//할인구분
	var pddcVal = Number($('#PDDC_VAL').val().replace(/[^\d]+/g, ''));				//할인값
	var pdPrice = Number($('#PD_PRICE').val().replace(/[^\d]+/g, ''));				//제품값
	if(pddcGubn == 'PDDC_GUBN_02'){
		$('#PDDC_PRICE').val((pdPrice-pddcVal).toLocaleString());	
	}else if(pddcGubn == 'PDDC_GUBN_03'){
		$('#PDDC_PRICE').val((pdPrice-(pdPrice*pddcVal/100)).toLocaleString());
	}else{
		$('#PDDC_PRICE').val(pdPrice.toLocaleString());
	}
	
	if(pddcGubn == 'PDDC_GUBN_01'||pddcGubn == 'PDDC_GUBN_05'){
		$('#PDDC_PRICE').val(0);
	}
	
}
function fn_chk_rta_yn(){
	var strChkVal = $("input:radio[name='RETAIL_YN']:checked").val();
	
	if(strChkVal == 'Y'){
		$('.retail').show();
	}else{
		$('.retail').hide();
	}
}
function fn_boxpddc_disabled(){
	var strChkVal = $("input:radio[name='BOX_PDDC_GUBN']:checked").val();
	
	if(strChkVal == "PDDC_GUBN_01"){
		$("#BOX_PDDC_VAL").val("0");
		$("#BOX_PDDC_VAL").attr("readonly",true);
		
	}else{
		$("#BOX_PDDC_VAL").attr("readonly",false);
	}
}
</script>
