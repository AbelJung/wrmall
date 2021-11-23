package mall.web.controller.admin;

import java.io.File;
import java.io.IOException;
import java.net.URLEncoder;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import mall.common.util.ExcelDownload;
import mall.common.util.FileUtil;
import mall.common.util.StringUtil;
import mall.common.util.excel.ExcelRead;
import mall.common.util.excel.ExcelReadOption;
import mall.web.controller.DefaultController;
import mall.web.domain.GOODS_MASTER;
import mall.web.domain.TB_COATFLXD;
import mall.web.domain.TB_COMCODXD;
import mall.web.domain.TB_MBINFOXM;
import mall.web.domain.TB_PDCAGOXM;
import mall.web.domain.TB_PDCUTXM;
import mall.web.domain.TB_PDINFOXM;
import mall.web.domain.TB_PDRCMDXM;
import mall.web.domain.TB_SALEPRCGRP;
import mall.web.service.admin.impl.CategoryMgrService;
import mall.web.service.admin.impl.ProductMgrService;
import mall.web.service.common.CommonService;
import mall.web.service.mall.ProductService;

@Controller
@RequestMapping(value="/adm/productMgr")
public class ProductMgrController extends DefaultController{

	private static final Logger logger = LoggerFactory.getLogger(ProductMgrController.class);

	@Resource(name="commonService")
	CommonService commonService;

	@Resource(name="categoryMgrService")
	CategoryMgrService categoryMgrService;
	
	@Resource(name="productMgrService")
	ProductMgrService productMgrService;
	
	@Resource(name="productService")
	ProductService productService;

	/**
	 * ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
	 * @Class	: mall.web.controller.admin.ProductMgrController.java
	 * @Method	: getList
	 * ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
	 * @Desc	: 제품 조회
	 * @Company	: YT Corp.
	 * @Author	: Tae-seok Choi (tschoi@youngthink.co.kr)
	 * @Date	: 2016-07-07 (오후 11:04:40)
	 * ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
	 * @param customers
	 * @param model
	 * @return ModelAndView
	 * @throws Exception
	 * ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
	*/
	@RequestMapping(method=RequestMethod.GET)
	public ModelAndView getList(@ModelAttribute TB_PDINFOXM productInfo, Model model,HttpServletRequest request) throws Exception {
		
		//productInfo.setSUPR_ID();
		productInfo.setRowCnt(30);
		
		//정렬 초기화
		if(StringUtils.isEmpty(productInfo.getSortGubun())){
			productInfo.setSortGubun("REG_DTM");
			productInfo.setSortOdr("desc");
		}
		
		if(request.getParameter("pagerMaxPageItems")!=null){
			productInfo.setRowCnt(Integer.parseInt(request.getParameter("pagerMaxPageItems")));	//페이징 단위 : 50
			productInfo.setPagerMaxPageItems(Integer.parseInt(request.getParameter("pagerMaxPageItems")));
		}else {
			productInfo.setRowCnt(20);	//페이징 단위 : 20
			productInfo.setPagerMaxPageItems(20);
		}
				
		//공통코드 - 할인구분
		TB_COMCODXD comCod = new TB_COMCODXD();
		comCod.setCOMM_CODE("PDDC_GUBN");
		model.addAttribute("codPDDC_GUBN", commonService.selectComCodList(comCod));
		
		//공통코드 - 판매상태
		comCod.setCOMM_CODE("SALE_CON");
		model.addAttribute("codSALE_CON", commonService.selectComCodList(comCod));
				
		// 단독 검색
		if(productInfo.getReSearch() == null || productInfo.getReSearch().toString().equals("")) {
			List schTxt_befList = Arrays.asList(productInfo.getSchTxt());
			productInfo.setSchTxt_befList(schTxt_befList);	// 기본 검색어 만 List
			productInfo.setSchTxt_bef(productInfo.getSchTxt());	// 기본 검색어를 이전 검색어 hidden 데이터로 반영
			
		} else {	// 결과 내 재검색
			// 결과 내 재검색을 체크했으면
			if(!productInfo.getReSearch().toString().equals("")){
				
				StringBuffer str = new StringBuffer();
				str.append(productInfo.getSchTxt_bef());
				
				String schTxt_first ="";
				String schTxt_last ="";
				if(productInfo.getSchTxt_bef().length() > 0){
					List<String> schTxt_bef_list = Arrays.asList(str.toString().split(","));
					if(schTxt_bef_list.size()>0){
						schTxt_first = schTxt_bef_list.get(0);
						
						schTxt_last = schTxt_bef_list.get(schTxt_bef_list.size()-1);
					}
				}
				
				if(schTxt_first.equals(productInfo.getSchTxt().toString())){	// 최초 검색어가 같으면 재검색이므로 ...
					
					if(schTxt_last.equals(productInfo.getReSearch().toString())){
						
						List schTxt_befList = Arrays.asList(productInfo.getSchTxt_bef().toString().split(","));
						productInfo.setSchTxt_befList(schTxt_befList);
						
					}else{ // 결과 내 재검색 이 null 이 아닌대 on도 아니면 페이징으로 간주 기존 값 매칭
						
						if(productInfo.getSchTxt_bef().length() > 0) {
							str.append(",");
						}
						
						str.append(productInfo.getReSearch().toString());
						productInfo.setSchTxt_bef(str.toString());
						
						List schTxt_befList = Arrays.asList(str.toString().split(","));
						productInfo.setSchTxt_befList(schTxt_befList);
					}
				}else{
					List schTxt_befList = Arrays.asList(productInfo.getSchTxt());
					productInfo.setSchTxt_befList(schTxt_befList);	// 기본 검색어 만 List
					productInfo.setSchTxt_bef(productInfo.getSchTxt());	// 기본 검색어를 이전 검색어 hidden 데이터로 반영
					
					// 재검색정보초기화
					productInfo.setReSearch("");	
				}
			}				
		}
		
		productInfo.setCount(productMgrService.getObjectCount(productInfo));
		productInfo.setList(productMgrService.getPaginatedObjectList(productInfo));
		model.addAttribute("obj", productInfo);

		//페이징 정보
		model.addAttribute("rowCnt", productInfo.getRowCnt());			//페이지 목록수
		model.addAttribute("totalCnt", productInfo.getCount());			//전체 카운트

		//링크설정
		String strLink = null;
		strLink = "&schGbn="+StringUtil.nullCheck(productInfo.getSchGbn())+
				  "&schTxt_bef="+StringUtil.nullCheck(productInfo.getSchTxt_bef()) +
				  "&schTxt="+StringUtil.nullCheck(productInfo.getSchTxt())+
				  "&reSearch="+StringUtil.nullCheck(productInfo.getReSearch())+
			      "&sortGubun="+StringUtil.nullCheck(productInfo.getSortGubun())+
			      "&pagerMaxPageItems="+StringUtil.nullCheck(productInfo.getPagerMaxPageItems())+
			      "&ONDY_GUBN="+StringUtil.nullCheck(productInfo.getONDY_GUBN())+
			      "&SALE_CON="+StringUtil.nullCheck(productInfo.getSALE_CON())+
			      "&sortOdr="+StringUtil.nullCheck(productInfo.getSortOdr());
		
		model.addAttribute("link", strLink);
		
		return new ModelAndView("admin.layout", "jsp", "admin/productMgr/list");
		
	}
	
	
	/**
	 * ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
	 * @Class	: mall.web.controller.admin.ProductMgrController.java
	 * @Method	: edit
	 * ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
	 * @Desc	: 상품관리 상세(form)
	 * @Company	: YT Corp.
	 * @Author	: Tae-seok Choi (tschoi@youngthink.co.kr)
	 * @Date	: 2016-07-08 (오전 9:31:25)
	 * ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
	 * @param customers
	 * @param model
	 * @return ModelAndView
	 * @throws Exception
	 * ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
	*/
	@RequestMapping(value={ "/edit/{PD_CODE}", "/new"}, method=RequestMethod.GET)
	public ModelAndView view(@ModelAttribute TB_PDINFOXM productInfo, Model model) throws Exception {
		
		//카테고리 받아오기
		TB_PDCAGOXM category = new TB_PDCAGOXM();
		category.setUSE_YN("Y");
		model.addAttribute("categoryList", categoryMgrService.getObjectList(category));
			
		if(StringUtils.isNotEmpty(productInfo.getPD_CODE())){
			TB_PDINFOXM pdtInfo = (TB_PDINFOXM)productMgrService.getObject(productInfo);
			model.addAttribute("productInfo", pdtInfo);
			
			List<?> pdcut = productMgrService.getProCutList(productInfo);
			model.addAttribute("productCut", pdcut);
			
			/*ERP_MALL 행사가격 정보 검색*/ 
	        //TB_SALEPRCGRP saleprcgrp = (TB_SALEPRCGRP) productService.getSalePrcGrpInfo(productInfo);
			/*
	        if(saleprcgrp != null)
	            model.addAttribute("saleinfo", saleprcgrp);
			*/
			//파일 받아오기
			if(StringUtils.isNotEmpty(pdtInfo.getATFL_ID())){
				TB_COATFLXD commonFile = new TB_COATFLXD();
				commonFile.setATFL_ID(pdtInfo.getATFL_ID());
				model.addAttribute("fileList", commonService.selectFileList(commonFile));
			}
			
			//파일 받아오기 - 상세이미지
			if(StringUtils.isNotEmpty(pdtInfo.getDTL_ATFL_ID())){
				TB_COATFLXD commonDtlFile = new TB_COATFLXD();
				commonDtlFile.setATFL_ID(pdtInfo.getDTL_ATFL_ID());
				model.addAttribute("fileDtlList", commonService.selectFileList(commonDtlFile));
			}
		}
		
		//링크설정
		String strLink = null;
		strLink = "&schGbn="+StringUtil.nullCheck(productInfo.getSchGbn())+
				  "&schTxt="+StringUtil.nullCheck(productInfo.getSchTxt())+
			      "&sortGubun="+StringUtil.nullCheck(productInfo.getSortGubun())+
			      "&sortOdr="+StringUtil.nullCheck(productInfo.getSortOdr());
		
		model.addAttribute("link", strLink);
		
		return new ModelAndView("admin.layout", "jsp", "admin/productMgr/form");
	}
	
	
	@RequestMapping(value={ "/updateList"}, method=RequestMethod.POST)
	public ModelAndView updateList(@ModelAttribute TB_PDINFOXM productInfo, Model model,HttpServletRequest request,
			@RequestParam String[] saveValues) throws Exception {
				
		TB_MBINFOXM loginUser = (TB_MBINFOXM)request.getSession().getAttribute("ADMUSER");
		productInfo.setREGP_ID(loginUser.getMEMB_ID());		
		
		for(int i=0; i<saveValues.length; i++){
			String[] saveVal = saveValues[i].toString().split("!!@");
			
			productInfo.setPD_CODE(saveVal[0].toString());
			productInfo.setPDDC_GUBN(saveVal[1].toString());
			productInfo.setSALE_CON(saveVal[2].toString());
						 
			if(saveVal.length>3){
				System.out.println("saveVal[3]==="+saveVal[3].toString());
				productInfo.setPD_PRICE(saveVal[3].toString());
				productMgrService.updateList(productInfo);
			}else{
				//productInfo.setPD_PRICE();
				productMgrService.updateList(productInfo);
			}
		}
				
		//링크설정
		String strRtrUrl ="";
		
		//링크설정
		String strLink = null;
		strLink = "schGbn="+StringUtil.nullCheck(productInfo.getSchGbn())+
				  "&schTxt_bef="+StringUtil.nullCheck(URLEncoder.encode(productInfo.getSchTxt_bef(),"UTF-8")) +
				  "&schTxt="+StringUtil.nullCheck(URLEncoder.encode(productInfo.getSchTxt(),"UTF-8"))+
				  "&reSearch="+StringUtil.nullCheck(productInfo.getReSearch())+
			      "&sortGubun="+StringUtil.nullCheck(productInfo.getSortGubun())+
			      "&pagerMaxPageItems="+StringUtil.nullCheck(productInfo.getPagerMaxPageItems())+
			      "&ONDY_GUBN="+StringUtil.nullCheck(productInfo.getONDY_GUBN())+
			      "&sortOdr="+StringUtil.nullCheck(productInfo.getSortOdr());
		
		strRtrUrl = "/adm/productMgr?"+strLink;
		
		RedirectView rv = new RedirectView(servletContextPath.concat(strRtrUrl));
		return new ModelAndView(rv);
	}
	
	
	
	/**
	 * ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
	 * @Class	: mall.web.controller.admin.ProductMgrController.java
	 * @Method	: insert
	 * ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
	 * @Desc	: 제품정보 등록/수정
	 * @Company	: YT Corp.
	 * @Author	: Tae-seok Choi (tschoi@youngthink.co.kr)
	 * @Date	: 2016-07-07 (오후 11:05:15)
	 * ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
	 * @param productInfo
	 * @param request
	 * @param model
	 * @param multipartRequest
	 * @return ModelAndView
	 * @throws Exception
	 * ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
	*/
	@RequestMapping(method=RequestMethod.POST)
	public ModelAndView insert(@ModelAttribute TB_PDINFOXM productInfo, @ModelAttribute TB_COATFLXD comFile,@ModelAttribute TB_PDCUTXM  productcut,HttpServletRequest request, Model model, MultipartHttpServletRequest multipartRequest) throws Exception {
		String strRtrUrl = "";
		
		TB_MBINFOXM loginUser = (TB_MBINFOXM)request.getSession().getAttribute("ADMUSER");
		productInfo.setREGP_ID(loginUser.getMEMB_ID());		
		productInfo.setSUPR_ID("C00002");		
		comFile.setREGP_ID(loginUser.getMEMB_ID()); 

		//첨부파일이 두개 이상일 경우 객체하나씩 추가
		TB_COATFLXD comDtlFile = new TB_COATFLXD();
		comDtlFile.setATFL_ID(productInfo.getDTL_ATFL_ID()); 
		comDtlFile.setREGP_ID(loginUser.getMEMB_ID()); 
		
		//첨부파일 처리
		String strATFL_ID = commonService.saveFile(comFile, request, multipartRequest, "file", "product", false);
		String strDTL_ATFL_ID = commonService.saveFile(comDtlFile, request, multipartRequest, "fileDtl", "product", false);
		//파일첨부 할 경우
		//productInfo.setADC2_NAME("filepath");
		//productInfo.setADC2_VAL("woori");
				
		String[] splitSeq = productcut.getSEQ().split("!!@");
		String[] splitCut = productcut.getCUT_UNIT().split("!!@");
		String[] splitUse = productcut.getUSE_YN().split("!!@");
		
		if(splitSeq.length>0&&!productcut.getSEQ().equals("")){
			for(int i=0;i<splitSeq.length;i++){

				productcut.setPD_CODE_IN(productInfo.getPD_CODE_IN());
				productcut.setCAGO_ID(productInfo.getCAGO_ID());
				productcut.setCAGO_ID_LEN(productInfo.getCAGO_ID_LEN());
				
				productcut.setREGP_ID("admin");
				productcut.setSEQ(splitSeq[i]);
				productcut.setCUT_UNIT(splitCut[i]);
				productcut.setUSE_YN(splitUse[i]);
				
				if(splitCut[i].equals("")){
					
				}else{
					productMgrService.insertProCut(productcut);
				}
		    }	
		}

		//링크설정
		String strLink = null;
		strLink = "pageNum="+StringUtil.nullCheck(productInfo.getPageNum())+
				  "&rowCnt="+StringUtil.nullCheck(productInfo.getRowCnt())+
			  	  "&schGbn="+StringUtil.nullCheck(productInfo.getSchGbn())+
			  	  "&schTxt="+StringUtil.getURLEncoding(StringUtil.nullCheck(productInfo.getSchTxt()))+
			      "&sortGubun="+StringUtil.nullCheck(productInfo.getSortGubun())+
			      "&sortOdr="+StringUtil.nullCheck(productInfo.getSortOdr());
		
		int nRtn = 0;
		if( StringUtils.isNotEmpty(productInfo.getPD_CODE()) && !productInfo.getPD_CODE_IN().equals("INPUT")){		//상품코드가 없고, 수동입력이 아닐 경우
			productInfo.setATFL_ID(strATFL_ID);
			productInfo.setDTL_ATFL_ID(strDTL_ATFL_ID);
			nRtn = productMgrService.updateObject(productInfo);
			strRtrUrl = "/adm/productMgr/edit/"+ productInfo.getPD_CODE() + "?" +strLink;
		}else{
			productInfo.setATFL_ID(strATFL_ID);
			productInfo.setDTL_ATFL_ID(strDTL_ATFL_ID);

			productInfo.setCAGO_ID_LEN(productInfo.getCAGO_ID().length()+1);
			
			nRtn = productMgrService.insertObject(productInfo);
			//strRtrUrl = "/adm/productMgr/edit/"+ productInfo.getPD_CODE() + "?" +strLink;
			strRtrUrl = "/adm/productMgr/";
		}
		
		RedirectView rv = new RedirectView(servletContextPath.concat(strRtrUrl));
		return new ModelAndView(rv);
	}	

	
	@RequestMapping(value="/deleteChk", method=RequestMethod.POST)
	public @ResponseBody String deleteChk(@ModelAttribute TB_PDINFOXM productInfo) throws Exception {
		int nCateCnt = productMgrService.getObjectCount(productInfo);
		return nCateCnt+"";
	}
	
	@RequestMapping(value="/deletePrd", method=RequestMethod.POST)
	public ModelAndView deletePrd(@ModelAttribute TB_PDINFOXM productInfo, HttpServletRequest request, Model model) throws Exception {
		
		//########## 상품삭제 로직변경 : Temp로 백업 후 Delete ###########
		String strRtrUrl = "/adm/productMgr";
		
		TB_MBINFOXM loginUser = (TB_MBINFOXM)request.getSession().getAttribute("ADMUSER");
		productInfo.setREGP_ID(loginUser.getMEMB_ID());
		
		// 삭제상품 temp 테이블로 백업
		int tmpBackUp = 0;
		tmpBackUp = productMgrService.tmpInsertObject(productInfo);
		
		// 상품 삭제
		int deleteCnt = 0;
		if(tmpBackUp != 0){
			deleteCnt = productMgrService.deleteObject2(productInfo);			
		}
		
		if(deleteCnt ==0){
			//strRtrUrl = "/adm/productMgr/edit/"+ productInfo.getPD_CODE();
			ModelAndView mav = new ModelAndView();			
			mav.addObject("alertMessage", "상품이 삭제되지 않았습니다. 관리자에게 문의해주세요.");
			mav.addObject("returnUrl", "back");
			mav.setViewName("alertMessage");
			return mav;
		}
		
		RedirectView rv = new RedirectView(servletContextPath.concat(strRtrUrl));
		return new ModelAndView(rv);
		//############### 상품삭제 로직변경 End #################
		
		/*
		TB_MBINFOXM loginUser = (TB_MBINFOXM)request.getSession().getAttribute("ADMUSER");
		productInfo.setREGP_ID(loginUser.getMEMB_ID());
		
		int deleteCnt = productMgrService.deletePrdObject(productInfo);
		
		RedirectView rv = new RedirectView(servletContextPath.concat("/adm/productMgr"));
		return new ModelAndView(rv);
		*/
	}
	
	
	/**
	 * ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
	 * @Class	: mall.web.controller.admin.ProductMgrController.java
	 * @Method	: insert
	 * ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
	 * @Desc	: 카테고리 삭제
	 * @Company	: YT Corp.
	 * @Author	: Tae-seok Choi (tschoi@youngthink.co.kr)
	 * @Date	: 2016-07-07 (오후 11:05:15)
	 * ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
	 * @param productInfo
	 * @param request
	 * @param model
	 * @return ModelAndView
	 * @throws Exception
	 * ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
	*/
	@RequestMapping(value={ "/delete"}, method=RequestMethod.POST)
	public ModelAndView delete(@ModelAttribute TB_PDINFOXM productInfo, HttpServletRequest request, Model model) throws Exception {
		
		TB_MBINFOXM loginUser = (TB_MBINFOXM)request.getSession().getAttribute("ADMUSER");
		productInfo.setREGP_ID(loginUser.getMEMB_ID());		

		int nRtn = 0;
		nRtn = productMgrService.deleteObject(productInfo);

		RedirectView rv = new RedirectView(servletContextPath.concat("/adm/categoryMgr"));
		return new ModelAndView(rv);
	}
	
	/**
	 * ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
	 * @Class	: mall.web.controller.admin.ProductMgrController.java
	 * @Method	: popup
	 * ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
	 * @Desc	: 상품연계 테이블 조회
	 * @Company	: YT Corp.
	 * @Author	: Young-dae Kim (sjie1638@youngthink.co.kr)
	 * @Date	: 2016-07-20 (오후 4:42:49)
	 * ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
	 * @param tb_sysmnuxm
	 * @param model
	 * @return
	 * @throws Exception
	 * ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
	*/
	@RequestMapping(value={ "/popup"}, method=RequestMethod.GET)
	public ModelAndView popup(@ModelAttribute GOODS_MASTER goods_master, Model model) throws Exception {
		
		goods_master.setCount(productMgrService.getGoodsMasterCount(goods_master));
		goods_master.setList(productMgrService.getGoodsMasterList(goods_master));
		model.addAttribute("obj", goods_master);

		//페이징 정보
		model.addAttribute("rowCnt", goods_master.getRowCnt());			//페이지 목록수
		model.addAttribute("totalCnt", goods_master.getCount());		//전체 카운트
		
		
		return new ModelAndView("popup.layout", "jsp", "admin/productMgr/popup");
	}
	
	/**
	 * ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
	 * @Class	: mall.web.controller.admin.MemberMgrController.java
	 * @Method	: edit
	 * ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
	 * @Desc	: 회원정보 등록/변경을 위한 정보 조회(form)
	 * @Company	: YT Corp.
	 * @Author	: Tae-seok Choi (tschoi@youngthink.co.kr)
	 * @Date	: 2016-07-08 (오전 9:31:25)
	 * ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
	 * @param customers
	 * @param model
	 * @return ModelAndView
	 * @throws Exception
	 * ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
	*/
	@RequestMapping(value="/pdCodeChk", method=RequestMethod.POST)
	public @ResponseBody String pdCodeChk(@ModelAttribute TB_PDINFOXM productInfo) throws Exception {
		System.out.println(productInfo.getPD_CODE());
		
		int nCnt = productMgrService.pdCodeChk(productInfo);
		System.out.println(nCnt+"");
		return nCnt+"";
	}
	
	@RequestMapping(value="/pdBarCodeChk", method=RequestMethod.POST)
	public @ResponseBody String pdBarCodeChk(@ModelAttribute TB_PDINFOXM productInfo) throws Exception {
		System.out.println(productInfo.getPD_BARCD());
		
		int nCnt = productMgrService.pdBarCodeChk(productInfo);
		System.out.println(nCnt+"");
		return nCnt+"";
	}
	
	
	@RequestMapping(value={ "/excelDown" }, method=RequestMethod.GET)
	public void getExcelList(@ModelAttribute TB_PDINFOXM productInfo, Model model,HttpServletResponse response
			,@RequestParam(value="checkArray[]", defaultValue="") List<String> arrayParams) throws Exception {


//		String[] headerName = {"카테고리","상품명","판매가격","할인구분","할인값","판매상태"};
//		String[] columnName = {"CAGO_NM_PATH","PD_NAME","PD_PRICE","PDDC_GUBN_NAM","PDDC_VAL","SALE_CON_NAM"};
/*
		String[] headerName = {"제품코드","공급업체 ID","제품명","카테고리ID","제품가격","제품할인 구분","제품할인 값","적립금 구분"
				,"적립 값","재고수량","POS명","제품바코드","보관기준","제품규격","유통기한","제조회사","원산지","판매상태","상품 상세정보"
				,"배송/환불 안내","파일ID","대표이미지 순번","추가컬럼1 명","추가컬럼2 명","추가컬럼3 명","추가컬럼4 명","추가컬럼5 명"
				,"추가컬럼1 값","추가컬럼2 값","추가컬럼3 값","추가컬럼4 값","추가컬럼5 값","상품아이콘","규격단위","등록자 ID","등록 일시"
				,"수정자 ID","수정 일시","면세과세구분","상세첨부파일","상세첨부파일사용여부","개별배송 여부(INDIVIDUAL)","보관기준","팩킹기준"
				,"보관위치","한정수량","입수량"};
		String[] columnName = {
				"PD_CODE","SUPR_ID","PD_NAME","CAGO_ID","PD_PRICE","PDDC_GUBN","PDDC_VAL","SVMN_GUBN","SVMN_VAL","INVEN_QTY"
				,"POS_NAME","PD_BARCD","STORE_CTR","PD_STD","DTB_DLINE","MAKE_COM","ORG_CT","SALE_CON","PD_DINFO","DLVREF_GUIDE"
				,"ATFL_ID","RPIMG_SEQ","ADC1_NAME","ADC2_NAME","ADC3_NAME","ADC4_NAME","ADC5_NAME","ADC1_VAL","ADC2_VAL","ADC3_VAL"
				,"ADC4_VAL","ADC5_VAL","PD_ICON","STD_UNIT","REGP_ID","REG_DTM","MODP_ID","MOD_DTM","TAX_GUBN","DTL_ATFL_ID","DTL_USE_YN"
				,"DLVR_INDI_YN","KEEP_GUBN","PACKING_GUBN","KEEP_LOCATION","LIMIT_QTY","INPUT_CNT"
				*/

		String[] headerName = {"제품코드","제품명","공급업체 ID","카테고리ID","카테고리명","제품가격","제품할인 구분","제품할인 값"
				,"재고수량","제품바코드","제품규격","유통기한","제조회사","원산지","판매상태","파일ID","대표이미지 순번"
				,"규격단위","면세과세구분","일배상품구분","박스할인여부","박스할인금액","비닐봉투색상","세절방식"
				,"상세첨부파일","상세첨부파일사용여부","개별배송 여부(INDIVIDUAL)","보관기준","팩킹기준"
				,"보관위치","한정수량","입수량"};
		String[] columnName = {
				"PD_CODE","PD_NAME","SUPR_ID","CAGO_ID","CAGO_NM","PD_PRICE","PDDC_GUBN","PDDC_VAL"
				,"INVEN_QTY","PD_BARCD","PD_STD","DTB_DLINE","MAKE_COM","ORG_CT","SALE_CON","ATFL_ID","RPIMG_SEQ"
				,"STD_UNIT","TAX_GUBN","ONDY_GUBN","BOX_PDDC_GUBN","BOX_PDDC_VAL","OPTION_GUBN","CUT_UNIT"
				,"DTL_ATFL_ID","DTL_USE_YN","DLVR_INDI_YN","KEEP_GUBN","PACKING_GUBN"
				,"KEEP_LOCATION","LIMIT_QTY","INPUT_CNT"
				};
				
		
		String sheetName = "상품목록";
		

		List<HashMap<String, String>> list = (List<HashMap<String, String>>) productMgrService.getExcelList(productInfo);
		
		ExcelDownload.excelDownloadOrder(response, list, headerName, columnName, sheetName);
		
	}
	
	/* 등록/수정   */
	@RequestMapping(value="/excelUpload",method=RequestMethod.POST)
	public ModelAndView excelUpload(@ModelAttribute TB_PDINFOXM tb_pdinfoxm, HttpServletRequest request, Model model, MultipartHttpServletRequest multipartRequest) throws Exception{
		
		TB_MBINFOXM loginUser = (TB_MBINFOXM)request.getSession().getAttribute("ADMUSER");
		List<TB_PDINFOXM> list = null;
		tb_pdinfoxm.setREGP_ID(loginUser.getMEMB_ID()); 
		tb_pdinfoxm.setMODP_ID(loginUser.getMEMB_ID()); 
		
		MultipartFile excelFile =multipartRequest.getFile("EXCEL_FILE");
		
        if(excelFile==null || excelFile.isEmpty()){
            throw new RuntimeException("엑셀파일을 선택 해 주세요.");
        }
        
		if ( StringUtils.isNotEmpty(excelFile.getOriginalFilename()) ) {
			String saveFileName = FileUtil.saveFile2(request, excelFile, "jundan/tmp", false);
			String savePath = (request.getSession().getServletContext().getRealPath("/")).replace("\\", "/") + "upload/jundan/tmp" + File.separator;

	        File destFile = new File(savePath+saveFileName);
	        try{
	            excelFile.transferTo(destFile);
	        }catch(IllegalStateException | IOException e){
	            throw new RuntimeException(e.getMessage(),e);
	        }
	        
	        //Service 단에서 가져온 코드 
	        ExcelReadOption excelReadOption = new ExcelReadOption();
	        excelReadOption.setFilePath(destFile.getAbsolutePath());
	        excelReadOption.setOutputColumns("A","B", "C");
	        excelReadOption.setStartRow(3);
	        	        
	        List<Map<String, String>>excelContent =ExcelRead.read(excelReadOption);
	        logger.info("getNumCellCnt>>" + excelReadOption.getNumCellCnt());

			if (excelReadOption.getNumCellCnt() < 3) {
				ModelAndView mav = new ModelAndView();
				
				mav.addObject("alertMessage", "엑셀 업로드 양식이 변경 되었거나 일치하지 않습니다. 양식 다운로드 후 다시 저장 하세요.");
				mav.addObject("returnUrl", "back");
				mav.setViewName("alertMessage");
				return mav;
			}
			
	        try{
	        	list = productMgrService.excelUpload(tb_pdinfoxm, excelContent);
	        	
	        }catch(SQLException e){
	        	ModelAndView mav = new ModelAndView();			
				mav.addObject("alertMessage", e.getStackTrace()+"\n엑셀 업로드 양식이 변경 되었거나 일치하지 않습니다. 양식 다운로드 후 다시 저장 하세요.");
				mav.addObject("returnUrl", "back");
				mav.setViewName("alertMessage");
				return mav;
	        }
		}
		
		
		List<TB_PDINFOXM> successlist = new ArrayList<>();
		List<TB_PDINFOXM> errorlist = new ArrayList<>();
		
		// -1 : 실패 / 0 : 성공
		for(TB_PDINFOXM tb : list)
		{	
			if (tb.getADC1_NAME().equals("0"))
				successlist.add(tb);
			else if(tb.getADC1_NAME().equals("-1"))
				errorlist.add(tb);
		}
		
		model.addAttribute("errlist", errorlist);
		model.addAttribute("scslist", successlist);
		
		return new ModelAndView("admin.layout", "jsp", "admin/productMgr/excelResult");
	}

	
	
}
