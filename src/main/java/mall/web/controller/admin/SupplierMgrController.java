package mall.web.controller.admin;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import mall.common.util.StringUtil;
import mall.web.controller.DefaultController;
import mall.web.domain.TB_MBINFOXM;
import mall.web.domain.TB_SPINFOXM;
import mall.web.service.admin.impl.MemberMgrService;
import mall.web.service.admin.impl.SupplierMgrService;

@Controller
@RequestMapping(value="/adm")
public class SupplierMgrController extends DefaultController{
	private static final Logger logger = LoggerFactory.getLogger(SupplierMgrController.class);

	@Resource(name="supplierMgrService")
	SupplierMgrService supplierMgrService;
	
	@Resource(name="memberMgrService")
	MemberMgrService memberMgrService;
	
	/**
	 * ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
	 * @Class	: mall.web.controller.admin.SupplierMgrController.java
	 * @Method	: getList
	 * ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
	 * @Desc	: 기업목록 조회
	 * @Company	: YT Corp.
	 * @Author	: Seok-min Jeon (smjeon@youngthink.co.kr)
	 * @Date	: 2016-07-12 (오후 11:04:40)
	 * ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
	 * @param customers
	 * @param model
	 * @return ModelAndView
	 * @throws Exception
	 * ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
	*/
	@RequestMapping(value="/supplierMgr", method=RequestMethod.GET)
	public ModelAndView getList(@ModelAttribute TB_SPINFOXM supplierInfo, TB_MBINFOXM memberInfo, Model model) throws Exception {

		supplierInfo.setSCSS_YN("N");					//Y-탈퇴기업, N-정상기업
		
		supplierInfo.setCount(supplierMgrService.getObjectCount(supplierInfo));
		supplierInfo.setList(supplierMgrService.getPaginatedObjectList(supplierInfo));
		
		model.addAttribute("obj", supplierInfo);
		model.addAttribute("rowCnt", supplierInfo.getRowCnt());
		model.addAttribute("totalCnt", supplierInfo.getCount());
		
		String strLink = null;
		strLink = "&schGbn="+StringUtil.nullCheck(supplierInfo.getSchGbn())
				+ "&schTxt="+StringUtil.nullCheck(supplierInfo.getSchTxt());
		
		model.addAttribute("link", strLink);

		return new ModelAndView("admin.layout", "jsp", "admin/supplierMgr/list");
	}
	
	/**
	 * ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
	 * @Class	: mall.web.controller.admin.MemberMgrController.java
	 * @Method	: edit
	 * ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
	 * @Desc	: 회원정보 등록/변경을 위한 정보 조회(form)
	 * @Company	: YT Corp.
	 * @Author	: Seok-min Jeon (smjeon@youngthink.co.kr)
	 * @Date	: 2016-07-12 (오후 11:04:40)
	 * ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
	 * @param customers
	 * @param model
	 * @return ModelAndView
	 * @throws Exception
	 * ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
	*/
	@RequestMapping(value={ "/supplierMgr/edit/{SUPR_ID}", "/supplierMgr/new" }, method=RequestMethod.GET)
	public ModelAndView edit(@ModelAttribute TB_SPINFOXM supplierInfo, Model model) throws Exception {
		
		if(StringUtils.isNotEmpty(supplierInfo.getSUPR_ID())){
			model.addAttribute("supplierInfo", (TB_SPINFOXM)supplierMgrService.getObject(supplierInfo));
		}
		
		String strLink = null;
		strLink = "&schGbn="+StringUtil.nullCheck(supplierInfo.getSchGbn())+
				  "&schTxt="+StringUtil.nullCheck(supplierInfo.getSchTxt());
		
		model.addAttribute("link", strLink);
		
		return new ModelAndView("admin.layout", "jsp", "admin/supplierMgr/form");
	}
	

	/**
	 * ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
	 * @Class	: mall.web.controller.admin.SuppierMgrController.java
	 * @Method	: insert
	 * ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
	 * @Desc	: 기업회원정보 등록
	 * @Company	: YT Corp.
	 * @Author	: Seok-min Jeon (smjeon@youngthink.co.kr)
	 * @Date	: 2016-07-12 (오후 11:04:40)
	 * ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
	 * @param supplierInfo
	 * @param result
	 * @param status
	 * @param model
	 * @return String
	 * @throws Exception
	 * ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
	*/
	
	@RequestMapping(value="/supplierMgr", method=RequestMethod.POST)
	public ModelAndView insert(@ModelAttribute TB_SPINFOXM supplierInfo, HttpServletRequest request, Model model) throws Exception {
		String strRtrUrl = "";
		
		TB_MBINFOXM loginUser = (TB_MBINFOXM)request.getSession().getAttribute("ADMUSER");
		supplierInfo.setREGP_ID(loginUser.getMEMB_ID());		

		//링크설정
		String strLink = null;
		strLink = "pageNum="+StringUtil.nullCheck(supplierInfo.getPageNum())+
				  "&rowCnt="+StringUtil.nullCheck(supplierInfo.getRowCnt())+
			  	  "&schGbn="+StringUtil.nullCheck(supplierInfo.getSchGbn())+
			  	  "&schTxt="+StringUtil.getURLEncoding(StringUtil.nullCheck(supplierInfo.getSchTxt()));
		
		int nRtn = 0;
		if( StringUtils.isNotEmpty(supplierInfo.getSUPR_ID())){		
			nRtn = supplierMgrService.updateObject(supplierInfo);
			strRtrUrl = "/adm/supplierMgr/edit/"+ supplierInfo.getSUPR_ID() + "?" +strLink;
		}else{
			nRtn = supplierMgrService.insertObject(supplierInfo);
			//strRtrUrl = "/adm/productMgr/edit/"+ productInfo.getPD_CODE() + "?" +strLink;
			strRtrUrl = "/adm/supplierMgr/";
		}
		
		RedirectView rv = new RedirectView(servletContextPath.concat(strRtrUrl));
		return new ModelAndView(rv);
	}	
	
	/**
	 * ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
	 * @Class	: mall.web.controller.admin.SupplierMgrController.java
	 * @Method	: getScssList
	 * ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
	 * @Desc	: 탈퇴기업목록 조회
	 * @Company	: YT Corp.
	 * @Author	: Seok-min Jeon (smjeon@youngthink.co.kr)
	 * @Date	: 2016-07-12 (오후 11:04:40)
	 * ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
	 * @param customers
	 * @param model
	 * @return ModelAndView
	 * @throws Exception
	 * ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
	*/
	@RequestMapping(value="/supplieScssrMgr", method=RequestMethod.GET)
	public ModelAndView getScssList(@ModelAttribute TB_SPINFOXM supplierInfo, Model model) throws Exception {

		supplierInfo.setSCSS_YN("Y");					//Y-탈퇴기업, N-정상기업
		
		supplierInfo.setCount(supplierMgrService.getObjectCount(supplierInfo));
		supplierInfo.setList(supplierMgrService.getPaginatedObjectList(supplierInfo));
		
		model.addAttribute("obj", supplierInfo);
		model.addAttribute("rowCnt", supplierInfo.getRowCnt());
		model.addAttribute("totalCnt", supplierInfo.getCount());
		
		String strLink = null;
		strLink = "&schGbn="+StringUtil.nullCheck(supplierInfo.getSchGbn())+
				  "&schTxt="+StringUtil.nullCheck(supplierInfo.getSchTxt());
		
		model.addAttribute("link", strLink);

		return new ModelAndView("admin.layout", "jsp", "admin/supplierMgr/listScss");
	}
	
	
	/**
	 * ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
	 * @Class	: mall.web.controller.admin.SupplierMgrController.java
	 * @Method	: getScssList
	 * ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
	 * @Desc	: 기업정보관리 상세(FORM)
	 * @Company	: YT Corp.
	 * @Author	: Seok-min Jeon (smjeon@youngthink.co.kr)
	 * @Date	: 2016-07-12 (오후 11:04:40)
	 * ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
	 * @param customers
	 * @param model
	 * @return ModelAndView
	 * @throws Exception
	 * ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
	*/
	@RequestMapping(value="/supplierInfoMgr", method=RequestMethod.GET)
	public ModelAndView editInfo(@ModelAttribute TB_SPINFOXM supplierInfo, HttpServletRequest request, Model model) throws Exception {
		
		
		TB_MBINFOXM loginUser = (TB_MBINFOXM)request.getSession().getAttribute("ADMUSER");
		supplierInfo.setSUPR_ID(loginUser.getSUPR_ID());
		
		
		model.addAttribute("supplierInfo", (TB_SPINFOXM)supplierMgrService.getObject(supplierInfo));
		
		return new ModelAndView("admin.layout", "jsp", "admin/supplierMgr/formInfo");
	}

	/**
	 * ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
	 * @Class	: mall.web.controller.admin. extends SuppierMgrController.java
	 * @Method	: update
	 * ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
	 * @Desc	: 기업회원정보 수정
	 * @Company	: YT Corp.
	 * @Author	: Seok-min Jeon (smjeon@youngthink.co.kr)
	 * @Date	: 2016-07-12 (오후 11:04:40)
	 * ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
	 * @param supplierInfo
	 * @param result
	 * @param status
	 * @param model
	 * @return String
	 * @throws Exception
	 * ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
	*/
	@RequestMapping(value="/supplierInfoMgr/edit", method=RequestMethod.PUT)
	public ModelAndView updateInfo(@ModelAttribute TB_SPINFOXM supplierInfo, HttpServletRequest request, Model model) throws Exception {

		TB_MBINFOXM loginUser = (TB_MBINFOXM)request.getSession().getAttribute("ADMUSER");
		supplierInfo.setSUPR_ID(loginUser.getSUPR_ID());	
		supplierInfo.setREGP_ID(loginUser.getMEMB_ID());	
		
		int updateCnt = supplierMgrService.updateObject(supplierInfo);
		
		RedirectView rv = new RedirectView(servletContextPath.concat("/adm/supplierInfoMgr"));
		return new ModelAndView(rv);
		
	}
	
	/* 탈퇴 (기업탈퇴) */
	
	@RequestMapping(value="/supplierMgr/delete", method=RequestMethod.POST)
	public ModelAndView delete(@ModelAttribute TB_SPINFOXM supplierInfo, HttpServletRequest request, Model model) throws Exception {
		
		TB_MBINFOXM loginUser = (TB_MBINFOXM)request.getSession().getAttribute("ADMUSER");
		supplierInfo.setREGP_ID(loginUser.getMEMB_ID());		
		
		int deleteCnt = supplierMgrService.deleteObject(supplierInfo);
		
		RedirectView rv = new RedirectView(servletContextPath.concat("/adm/supplierMgr"));
		return new ModelAndView(rv);
		
	}
	
}
