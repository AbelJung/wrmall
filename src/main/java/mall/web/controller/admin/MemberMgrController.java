package mall.web.controller.admin;

import java.net.URLEncoder;
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

@Controller
@RequestMapping(value="/adm")
public class MemberMgrController extends DefaultController{

	private static final Logger logger = LoggerFactory.getLogger(MemberMgrController.class);

	@Resource(name="memberMgrService")
	MemberMgrService memberMgrService;
	
	/**
	 * ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
	 * @Class	: mall.web.controller.admin.MemberMgrController.java
	 * @Method	: getList
	 * ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
	 * @Desc	: 회원목록 조회
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
	@RequestMapping(value="/memberMgr", method=RequestMethod.GET)
	public ModelAndView getList(@ModelAttribute TB_MBINFOXM memberInfo, Model model) throws Exception {
			
		//memberInfo.setMEMB_GUBN("MEMB_GUBN_01");	//MEMB_GUBN_01-일반회원, MEMB_GUBN_02-기업회원,  MEMB_GUBN_03-시스템관리자
		memberInfo.setSCSS_YN("N");					//Y-탈퇴회원, N-정상회원

		memberInfo.setCount(memberMgrService.getObjectCount(memberInfo));
		memberInfo.setList(memberMgrService.getPaginatedObjectList(memberInfo));
		
		model.addAttribute("obj", memberInfo);
		model.addAttribute("rowCnt", memberInfo.getRowCnt());
		model.addAttribute("totalCnt", memberInfo.getCount());
		
		String strLink = null;
		strLink = "&schGbn="+StringUtil.nullCheck(memberInfo.getSchGbn())
				+  "&schTxt="+StringUtil.nullCheck(memberInfo.getSchTxt())
				+ "&MEMB_NM_ORDER=" + StringUtil.nullCheck(memberInfo.getMEMB_NM_ORDER())
				+ "&COM_NAME_ORDER=" + StringUtil.nullCheck(memberInfo.getCOM_NM_ORDER())
				+ "&MEMB_ORD_GUBUN=" + StringUtil.nullCheck(memberInfo.getMEMB_ORD_GUBUN());
		
		model.addAttribute("link", strLink);

		return new ModelAndView("admin.layout", "jsp", "admin/memberMgr/list");
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
	@RequestMapping(value={ "/memberMgr/edit/{MEMB_ID}", "/memberMgr/new" }, method=RequestMethod.GET)
	public ModelAndView edit(@ModelAttribute TB_MBINFOXM memberInfo, Model model) throws Exception {
		
		//전체관리자이면 전체, 기업회원이면 소속회사직원만 조회, 일반회원은 전체관리자만 조회 - 권한 체크 필요
		System.out.println("memberInfo.getMEMB_ID()======="+memberInfo.getMEMB_ID());
		
		if(StringUtils.isNotEmpty(memberInfo.getMEMB_ID())){
			model.addAttribute("memberInfo", (TB_MBINFOXM)memberMgrService.getObject(memberInfo));
		}
		model.addAttribute("areaList", memberMgrService.getComAreaGubn());
		
		String strLink = null;
		strLink = "&schGbn="+StringUtil.nullCheck(memberInfo.getSchGbn())+
				  	 "&schTxt="+StringUtil.nullCheck(memberInfo.getSchTxt());
		
		model.addAttribute("link", strLink);
		
		return new ModelAndView("admin.layout", "jsp", "admin/memberMgr/form");
	}
	
	@RequestMapping(value={ "/memberMgr/edit"}, method=RequestMethod.GET)
	public ModelAndView edit2(@ModelAttribute TB_MBINFOXM memberInfo, Model model) throws Exception {
		
		//전체관리자이면 전체, 기업회원이면 소속회사직원만 조회, 일반회원은 전체관리자만 조회 - 권한 체크 필요
		System.out.println("memberInfo.getMEMB_ID()======="+memberInfo.getMEMB_ID());
		
		if(StringUtils.isNotEmpty(memberInfo.getMEMB_ID())){
			model.addAttribute("memberInfo", (TB_MBINFOXM)memberMgrService.getObject(memberInfo));
		}
		model.addAttribute("areaList", memberMgrService.getComAreaGubn());
		
		String strLink = null;
		strLink = "&schGbn="+StringUtil.nullCheck(memberInfo.getSchGbn())+
				  	 "&schTxt="+StringUtil.nullCheck(memberInfo.getSchTxt());
		
		model.addAttribute("link", strLink);
		
		return new ModelAndView("admin.layout", "jsp", "admin/memberMgr/form");
	}
	

	/**
	 * ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
	 * @Class	: mall.web.controller.admin.MemberMgrController.java
	 * @Method	: insert
	 * ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
	 * @Desc	: 회원정보 등록
	 * @Company	: YT Corp.
	 * @Author	: Tae-seok Choi (tschoi@youngthink.co.kr)
	 * @Date	: 2016-07-07 (오후 11:05:15)
	 * ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
	 * @param memberInfo
	 * @param request
	 * @param model
	 * @return String
	 * @throws Exception
	 * ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
	*/	
	@RequestMapping(value="/memberMgr", method=RequestMethod.POST)
	public ModelAndView insert(@ModelAttribute TB_MBINFOXM memberInfo, HttpServletRequest request, Model model) throws Exception {
		String strRtrUrl = "";
		
		TB_MBINFOXM loginUser = (TB_MBINFOXM)request.getSession().getAttribute("ADMUSER");
		memberInfo.setREGP_ID(loginUser.getMEMB_ID());		

		//링크설정
		String strLink = null;
		strLink = "pageNum="+StringUtil.nullCheck(memberInfo.getPageNum())+
				  "&rowCnt="+StringUtil.nullCheck(memberInfo.getRowCnt())+
			  	  "&schGbn="+StringUtil.nullCheck(memberInfo.getSchGbn())+
			  	  "&schTxt="+StringUtil.getURLEncoding(StringUtil.nullCheck(memberInfo.getSchTxt()));

		//System.out.println(memberInfo.getMEMB_ID());
		//System.out.println(memberInfo.getMEMB_GUBN());
		//System.out.println(memberInfo.getSLCAL_GUBN());
		//System.out.println(memberInfo.getSCSS_YN());
		
		int nRtn = 0;
		
		//기존 정보가 존재하는지_20190226CHJW
		TB_MBINFOXM dupple;
		if(StringUtils.isNotEmpty(memberInfo.getMEMB_ID()));{
			dupple = (TB_MBINFOXM)memberMgrService.getObject(memberInfo);
		}
		
		if( dupple != null){
			nRtn = memberMgrService.updateObject(memberInfo);
			strRtrUrl = "/adm/memberMgr/edit/"+ memberInfo.getMEMB_ID() + "?" +strLink;
		}else{
			nRtn = memberMgrService.insertObject(memberInfo);
			strRtrUrl = "/adm/memberMgr/";
		}
				
		/*
		if( StringUtils.isNotEmpty(memberInfo.getMEMB_ID())){
			nRtn = memberMgrService.updateObject(memberInfo);
			strRtrUrl = "/adm/memberMgr/edit/"+ memberInfo.getMEMB_ID() + "?" +strLink;
		}else{
			nRtn = memberMgrService.insertObject(memberInfo);
			//strRtrUrl = "/adm/productMgr/edit/"+ productInfo.getPD_CODE() + "?" +strLink;
			strRtrUrl = "/adm/memberMgr/";
		}
		*/		
		RedirectView rv = new RedirectView(servletContextPath.concat(strRtrUrl));
		return new ModelAndView(rv);
	}	
	
	
	/**
	 * ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
	 * @Class	: mall.web.controller.admin.MemberMgrController.java
	 * @Method	: getList
	 * ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
	 * @Desc	: 탈퇴회원목록 조회
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
	@RequestMapping(value="/memberScssMgr", method=RequestMethod.GET)
	public ModelAndView getScssList(@ModelAttribute TB_MBINFOXM memberInfo, Model model) throws Exception {
			
		memberInfo.setMEMB_GUBN("MEMB_GUBN_01");	//MEMB_GUBN_01-일반회원, MEMB_GUBN_02-기업회원,  MEMB_GUBN_03-시스템관리자
		memberInfo.setSCSS_YN("Y");					//Y-탈퇴회원, N-정상회원

		memberInfo.setCount(memberMgrService.getObjectCount(memberInfo));
		memberInfo.setList(memberMgrService.getPaginatedObjectList(memberInfo));
		
		model.addAttribute("obj", memberInfo);
		model.addAttribute("rowCnt", memberInfo.getRowCnt());
		model.addAttribute("totalCnt", memberInfo.getCount());
		
		String strLink = null;
		strLink = "&schGbn="+StringUtil.nullCheck(memberInfo.getSchGbn())+
				  "&schTxt="+StringUtil.nullCheck(memberInfo.getSchTxt());
		
		model.addAttribute("link", strLink);

		return new ModelAndView("admin.layout", "jsp", "admin/memberMgr/listScss");
	}
	
	/* 회원탈퇴 (정보삭제) */	
	@RequestMapping(value="/memberMgr/delete", method=RequestMethod.POST)//, method=RequestMethod.PUT
	public ModelAndView delete(@ModelAttribute TB_MBINFOXM memberInfo, HttpServletRequest request, Model model) throws Exception {
		
		TB_MBINFOXM loginUser = (TB_MBINFOXM)request.getSession().getAttribute("ADMUSER");
		memberInfo.setREGP_ID(loginUser.getMEMB_ID());	
		
		memberMgrService.deleteObject(memberInfo);
		
		RedirectView rv = new RedirectView(servletContextPath.concat("/adm/memberMgr"));
		return new ModelAndView(rv);
	}
	
	/* 회원 엑셀다운로드 */
	@RequestMapping(value={ "/memberMgr/excelDown" }, method=RequestMethod.GET)
	public void getExcelList(@ModelAttribute TB_MBINFOXM memberInfo, Model model,HttpServletResponse response
			,@RequestParam(value="checkArray[]", defaultValue="") List<String> arrayParams) throws Exception {

		String[] headerName = {"회원아이디", "회원구분", "회원성명", "회원휴대폰번호", "사업자상호", "회사전화번호","회사주소","회원주소","가입일"};
	
		String[] columnName = {"MEMB_ID", "MEMB_GUBN_NM", "MEMB_NAME", "MEMB_CPON", "COM_NAME","COM_TELN","COM_ADR","MEMB_ADR","REG_DTM"};
		String sheetName = "회원정보";
		
		List<HashMap<String, String>> list = (List<HashMap<String, String>>) memberMgrService.getExcelList(memberInfo);
		
		ExcelDownload.excelDownloadOrder(response, list, headerName, columnName, sheetName);		
	}

	/* 회원 영구삭제 (백업삭제) */	
	@RequestMapping(value="/memberMgr/delete2", method=RequestMethod.POST)//, method=RequestMethod.PUT
	public ModelAndView delete2(@ModelAttribute TB_MBINFOXM memberInfo, HttpServletRequest request, Model model) throws Exception {
		
		TB_MBINFOXM loginUser = (TB_MBINFOXM)request.getSession().getAttribute("ADMUSER");
		memberInfo.setREGP_ID(loginUser.getMEMB_ID());	
		
		// 탈퇴회원 temp 테이블로 백업
		int tmpBackUp = 0;
		tmpBackUp = memberMgrService.backupObject(memberInfo);
		
		// 상품 삭제
		int deleteCnt = 0;
		if(tmpBackUp != 0){
			try{
				deleteCnt = memberMgrService.PermdeleteObject(memberInfo);
			}
			catch(Exception e){
				deleteCnt = 0;
			}
		}

		if(deleteCnt ==0){
			ModelAndView mav = new ModelAndView();			
			mav.addObject("alertMessage", "회원정보가 삭제되지 않았습니다. 관리자에게 문의해주세요.");
			mav.addObject("returnUrl", "back");
			mav.setViewName("alertMessage");
			return mav;
		}
		
		RedirectView rv = new RedirectView(servletContextPath.concat("/adm/memberScssMgr"));
		return new ModelAndView(rv);
	}
	
}
