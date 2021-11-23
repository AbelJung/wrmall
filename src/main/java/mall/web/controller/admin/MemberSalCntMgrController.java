package mall.web.controller.admin;

import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import mall.common.util.ExcelDownload;
import mall.common.util.StringUtil;
import mall.web.controller.DefaultController;
import mall.web.domain.TB_MBINFOXM;
import mall.web.domain.TB_SPINFOXM;
import mall.web.domain.XTB_ODINFOXM;
import mall.web.service.admin.impl.MemberMgrService;
import mall.web.service.admin.impl.MemberSalCntMgrService;

@Controller
@RequestMapping(value="/adm")
public class MemberSalCntMgrController extends DefaultController{

	private static final Logger logger = LoggerFactory.getLogger(MemberSalCntMgrController.class);

	@Resource(name="memberSalCntMgrService")
	MemberSalCntMgrService memberSalCntMgrService;
	
	
	/**
	 * ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
	 * @Class	: mall.web.controller.admin.MemberMgrController.java
	 * @Method	: getList
	 * ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
	 * @Desc	: 회원매출집계관리
	 * @Company	: YT Corp.
	 * @Author	: HEESUN LEE (hslee@youngthink.co.kr)
	 * @Date	: 2018-06-05 (오후 5:00:00)
	 * ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
	 * @param customers
	 * @param model
	 * @return ModelAndView
	 * @throws Exception
	 * ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
	*/
	@RequestMapping(value="/memberSalCntMgr", method=RequestMethod.GET)
	public ModelAndView getSalCntList(@ModelAttribute TB_MBINFOXM memberInfo,  HttpServletRequest request, Model model) throws Exception {
		
		//memberInfo.setMEMB_GUBN("MEMB_GUBN_01");	//MEMB_GUBN_01-일반회원, MEMB_GUBN_02-기업회원,  MEMB_GUBN_03-시스템관리자
		memberInfo.setSCSS_YN("N");					//Y-탈퇴회원, N-정상회원

		memberInfo.setCount(memberSalCntMgrService.getObjectCount(memberInfo));
		memberInfo.setList(memberSalCntMgrService.getPaginatedObjectList(memberInfo));
		
		model.addAttribute("obj", memberInfo);
		model.addAttribute("rowCnt", memberInfo.getRowCnt());
		model.addAttribute("totalCnt", memberInfo.getCount());
		
		String strLink = null;
		strLink = "&schGbn="+StringUtil.nullCheck(memberInfo.getSchGbn())
					+ "&schTxt="+StringUtil.nullCheck(memberInfo.getSchTxt())
					+ "&datepickerStr="+StringUtil.nullCheck(request.getParameter("datepickerStr"))
					  //"&TAX_GUBN="+StringUtil.nullCheck(request.getParameter("TAX_GUBN")
					+ "&PAY_GUBN="+StringUtil.nullCheck(request.getParameter("PAY_GUBN"))
					+ "&MEMB_GUBN="+StringUtil.nullCheck(request.getParameter("MEMB_GUBN"))
					+ "&TAXCAL_YN="+StringUtil.nullCheck(request.getParameter("TAXCAL_YN"))
					+ "&MEMB_NM_ORDER=" + StringUtil.nullCheck(memberInfo.getMEMB_NM_ORDER())
					+ "&COM_NAME_ORDER=" + StringUtil.nullCheck(memberInfo.getCOM_NM_ORDER())
					+ "&MEMB_ORD_GUBUN=" + StringUtil.nullCheck(memberInfo.getMEMB_ORD_GUBUN());
		
		model.addAttribute("link", strLink);

		return new ModelAndView("admin.layout", "jsp", "admin/memberSalCntMgr/list");
	}
	
	@RequestMapping(value={ "/memberSalCntMgr/excelDown" }, method=RequestMethod.GET)
	public void getExcelList(@ModelAttribute TB_MBINFOXM memberInfo, Model model,HttpServletResponse response) throws Exception {

		String[] headerName = {"고객ID", "고객구분", "고객명(대표자명)", "사업자상호", "사업자번호","누적금액","배송비 누적금액"
				,"과세매출","면세매출","반품금액","반품배송비","반품과세금액","반품면세금액"
				,"카드과세","카드면세","무통장과세","무통장면세" , "총 매출금액"
				,"사업자 기본주소","사업자 상세주소","회원 이메일"};
	
		String[] columnName = {"MEMB_ID", "MEMB_GUBN_NM", "MEMB_NAME", "COM_NAME", "COM_BUNB","ORDER_AMT_SUM", "DLVY_AMT_SUM"
				,"TAX_GUBN_01_SUM","TAX_GUBN_02_SUM","ORDER_AMT_SUM_BEFORE_RT","DLVY_AMT_SUM_RT","TAX_GUBN_01_SUM_RT","TAX_GUBN_02_SUM_RT"
				,"PAY_METD_01_TAX_GUBN_01_SUM","PAY_METD_01_TAX_GUBN_02_SUM","PAY_METD_02_TAX_GUBN_01_SUM","PAY_METD_02_TAX_GUBN_02_SUM","AMT_TOTAL"
				,"COM_BADR","COM_DADR","MEMB_MAIL"};
		String sheetName = "회원매출집계";
		

		List<HashMap<String, String>> list = (List<HashMap<String, String>>) memberSalCntMgrService.getExcelList(memberInfo);
		
		ExcelDownload.excelDownloadOrder(response, list, headerName, columnName, sheetName);
		
	}
	
	
}
