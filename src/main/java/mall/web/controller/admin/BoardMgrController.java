package mall.web.controller.admin;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import mall.common.util.StringUtil;
import mall.web.controller.DefaultController;
import mall.web.domain.TB_PDBORDXM;
import mall.web.domain.TB_PDCAGOXM;
import mall.web.service.admin.impl.BoardMgrService;

@Controller
@RequestMapping(value="/adm/boardMgr")
public class BoardMgrController extends DefaultController{

	@Resource(name="boardMgrService")
	BoardMgrService boardMgrService;
	
	/**
	 * ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
	 * @Class	: mall.web.controller.admin.BoardMgrController.java
	 * @Method	: getList
	 * ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
	 * @Desc	: 게시판 타입01 리스트
	 * @Company	: YT Corp.
	 * @Author	: Young-dae Kim (sjie1638@youngthink.co.kr)
	 * @Date	: 2016-07-18 (오후 5:34:28)
	 * ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
	 * @param tb_pdbordxm
	 * @param pagerOffset
	 * @param model
	 * @return
	 * @throws Exception
	 * ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
	*/
	@RequestMapping(value={ "/type01/{BRD_GUBN}" }, method=RequestMethod.GET)
	public ModelAndView getList01(@ModelAttribute TB_PDBORDXM  tb_pdbordxm, Model model) throws Exception {
		
		tb_pdbordxm.setCount(boardMgrService.getObjectCount01(tb_pdbordxm));
		tb_pdbordxm.setList(boardMgrService.getPaginatedObjectList01(tb_pdbordxm));
		
		model.addAttribute("obj", tb_pdbordxm);
		model.addAttribute("rowCnt", tb_pdbordxm.getRowCnt());
		model.addAttribute("totalCnt", tb_pdbordxm.getCount());
		
		String strLink = null;
		strLink = "&schGbn="+StringUtil.nullCheck(tb_pdbordxm.getSchGbn())
				+ "&schTxt="+StringUtil.nullCheck(tb_pdbordxm.getSchTxt());
		
		model.addAttribute("link", strLink);
		
		return new ModelAndView("admin.layout", "jsp", "admin/boardMgr/list01");
	}
	
	/**
	 * ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
	 * @Class	: mall.web.controller.admin.BoardMgrController.java
	 * @Method	: edit
	 * ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
	 * @Desc	: 게시판 타입01 상세
	 * @Company	: YT Corp.
	 * @Author	: Young-dae Kim (sjie1638@youngthink.co.kr)
	 * @Date	: 2016-07-19 (오전 10:51:58)
	 * ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
	 * @param tb_pdbordxm
	 * @param model
	 * @return
	 * @throws Exception
	 * ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
	*/
	@RequestMapping(value={ "/type01/{BRD_GUBN}/{BRD_NUM}" }, method=RequestMethod.GET)
	public ModelAndView edit01(@ModelAttribute TB_PDBORDXM  tb_pdbordxm, Model model) throws Exception {
		
		tb_pdbordxm = (TB_PDBORDXM) boardMgrService.getObject01(tb_pdbordxm);
		
		model.addAttribute("tb_pdbordxm", tb_pdbordxm);
		
		return new ModelAndView("admin.layout", "jsp", "admin/boardMgr/form01");
	}
	
	/**
	 * ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
	 * @Class	: mall.web.controller.admin.BoardMgrController.java
	 * @Method	: update
	 * ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
	 * @Desc	: 게시판 타입01 답변 저장
	 * @Company	: YT Corp.
	 * @Author	: Young-dae Kim (sjie1638@youngthink.co.kr)
	 * @Date	: 2016-07-19 (오전 10:58:57)
	 * ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
	 * @param tb_pdbordxm
	 * @param result
	 * @param status
	 * @param model
	 * @return
	 * @throws Exception
	 * ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
	*/
	@RequestMapping(value={ "/type01/{BRD_GUBN}/{BRD_NUM}" }, method=RequestMethod.POST)
	public ModelAndView update(@ModelAttribute TB_PDBORDXM  tb_pdbordxm, BindingResult result, SessionStatus status, Model model) throws Exception {
		
		tb_pdbordxm.setREP_ID("admin");
		boardMgrService.updateObject01(tb_pdbordxm);
		
		
		RedirectView redirectView = new RedirectView(servletContextPath+"/adm/boardMgr/type01/"+tb_pdbordxm.getBRD_GUBN()+"/"+tb_pdbordxm.getBRD_NUM());
		
		return new ModelAndView(redirectView);
	}
	
	
	
	/**
	 * ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
	 * @Class	: mall.web.controller.admin.BoardMgrController.java
	 * @Method	: getList
	 * ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
	 * @Desc	: 게시판 타입02 리스트
	 * @Company	: YT Corp.
	 * @Author	: Young-dae Kim (sjie1638@youngthink.co.kr)
	 * @Date	: 2026-07-18 (오후 5:34:28)
	 * ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
	 * @param tb_pdbordxm
	 * @param pagerOffset
	 * @param model
	 * @return
	 * @throws Exception
	 * ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
	*/
	@RequestMapping(value={ "/type02/{BRD_GUBN}" }, method=RequestMethod.GET)
	public ModelAndView getList02(@ModelAttribute TB_PDBORDXM  tb_pdbordxm, Model model) throws Exception {
		
		TB_PDCAGOXM tb_pdcagoxm = new TB_PDCAGOXM();
		tb_pdcagoxm.setList(boardMgrService.getCagoObjectList(tb_pdbordxm));
		model.addAttribute("cago_list", tb_pdcagoxm);
		
		//1단계 카테고리 선택시 2단계 가져오기
		TB_PDCAGOXM tb_pdcagoxm02 = new TB_PDCAGOXM();
		if(tb_pdbordxm.getSchCago01() != "" && tb_pdbordxm.getSchCago01() != null){
			tb_pdbordxm.setSchCago(tb_pdbordxm.getSchCago01());
			tb_pdcagoxm02.setList(boardMgrService.getSchCagoObjectList(tb_pdbordxm));
		}
		model.addAttribute("cago_list02", tb_pdcagoxm02);
		
		//2단계 카테고리 선택시 3단계 가져오기
		TB_PDCAGOXM tb_pdcagoxm03 = new TB_PDCAGOXM();
		if(tb_pdbordxm.getSchCago02() != "" && tb_pdbordxm.getSchCago02() != null){
			tb_pdbordxm.setSchCago(tb_pdbordxm.getSchCago02());
			tb_pdcagoxm03.setList(boardMgrService.getSchCagoObjectList(tb_pdbordxm));
		}
		model.addAttribute("cago_list03", tb_pdcagoxm03);
		
		//3단계 카테고리 선택시 3단계 가져오기
		TB_PDCAGOXM tb_pdcagoxm04 = new TB_PDCAGOXM();
		if(tb_pdbordxm.getSchCago03() != "" && tb_pdbordxm.getSchCago03() != null){
			tb_pdbordxm.setSchCago(tb_pdbordxm.getSchCago03());
			tb_pdcagoxm04.setList(boardMgrService.getSchCagoObjectList(tb_pdbordxm));
		}
		model.addAttribute("cago_list04", tb_pdcagoxm04);
		
		if(tb_pdbordxm.getSchCago04() != "" && tb_pdbordxm.getSchCago04() != null){
			tb_pdbordxm.setSchCago(tb_pdbordxm.getSchCago04());
		}
		
		tb_pdbordxm.setCount(boardMgrService.getObjectCount02(tb_pdbordxm));
		tb_pdbordxm.setList(boardMgrService.getPaginatedObjectList02(tb_pdbordxm));
		
		model.addAttribute("obj", tb_pdbordxm);
		model.addAttribute("rowCnt", tb_pdbordxm.getRowCnt());
		model.addAttribute("totalCnt", tb_pdbordxm.getCount());
		
		String strLink = null;
		strLink = "&schGbn="+StringUtil.nullCheck(tb_pdbordxm.getSchGbn())
				+ "&schTxt="+StringUtil.nullCheck(tb_pdbordxm.getSchTxt());
		
		model.addAttribute("link", strLink);
		
		return new ModelAndView("admin.layout", "jsp", "admin/boardMgr/list02");
	}
	
	/**
	 * ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
	 * @Class	: mall.web.controller.admin.BoardMgrController.java
	 * @Method	: edit
	 * ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
	 * @Desc	: 게시판 타입02 상세
	 * @Company	: YT Corp.
	 * @Author	: Young-dae Kim (sjie1638@youngthink.co.kr)
	 * @Date	: 2026-07-19 (오전 10:51:58)
	 * ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
	 * @param tb_pdbordxm
	 * @param model
	 * @return
	 * @throws Exception
	 * ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
	*/
	@RequestMapping(value={ "/type02/{BRD_GUBN}/{BRD_NUM}" }, method=RequestMethod.GET)
	public ModelAndView edit02(@ModelAttribute TB_PDBORDXM  tb_pdbordxm, Model model) throws Exception {
		
		tb_pdbordxm = (TB_PDBORDXM) boardMgrService.getObject02(tb_pdbordxm);
		
		model.addAttribute("tb_pdbordxm", tb_pdbordxm);
		
		return new ModelAndView("admin.layout", "jsp", "admin/boardMgr/form02");
	}
	
	/**
	 * ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
	 * @Class	: mall.web.controller.admin.BoardMgrController.java
	 * @Method	: update
	 * ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
	 * @Desc	: 게시판 타입02 답변 저장
	 * @Company	: YT Corp.
	 * @Author	: Young-dae Kim (sjie1638@youngthink.co.kr)
	 * @Date	: 2026-07-19 (오전 10:58:57)
	 * ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
	 * @param tb_pdbordxm
	 * @param result
	 * @param status
	 * @param model
	 * @return
	 * @throws Exception
	 * ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
	*/
	@RequestMapping(value={ "/type02/{BRD_GUBN}/{BRD_NUM}" }, method=RequestMethod.POST)
	public ModelAndView update02(@ModelAttribute TB_PDBORDXM  tb_pdbordxm, BindingResult result, SessionStatus status, Model model) throws Exception {
		
		tb_pdbordxm.setREP_ID("admin");
		boardMgrService.updateObject02(tb_pdbordxm);
				
		/*RedirectView redirectView = new RedirectView(servletContextPath+"/adm/boardMgr/type02/"+tb_pdbordxm.getBRD_GUBN()+"/"+tb_pdbordxm.getBRD_NUM());*/
		

		// 2019.02.25 CHJW
		String delyn = tb_pdbordxm.getDEL_YN();
		String redirectPath = servletContextPath+"/adm/boardMgr/type02/"+tb_pdbordxm.getBRD_GUBN()+"/"+tb_pdbordxm.getBRD_NUM();
		
		System.out.println(delyn);
		System.out.println(redirectPath);
		
		if (delyn.equals("YES")){
			redirectPath = servletContextPath+"/adm/boardMgr/type02/"+tb_pdbordxm.getBRD_GUBN();
		}
		
		RedirectView redirectView = new RedirectView(redirectPath);
		
		
		return new ModelAndView(redirectView);
	}
	
	
	/**
	 * ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
	 * @Class	: mall.web.controller.admin.BoardMgrController.java
	 * @Method	: getList
	 * ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
	 * @Desc	: 게시판 타입03 리스트
	 * @Company	: YT Corp.
	 * @Author	: Young-dae Kim (sjie1638@youngthink.co.kr)
	 * @Date	: 2036-07-18 (오후 5:34:28)
	 * ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
	 * @param tb_pdbordxm
	 * @param pagerOffset
	 * @param model
	 * @return
	 * @throws Exception
	 * ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
	*/
	@RequestMapping(value={ "/type03/{BRD_GUBN}" }, method=RequestMethod.GET)
	public ModelAndView getList03(@ModelAttribute TB_PDBORDXM  tb_pdbordxm, Model model) throws Exception {
		
		TB_PDCAGOXM tb_pdcagoxm = new TB_PDCAGOXM();
		tb_pdcagoxm.setList(boardMgrService.getCagoObjectList(tb_pdbordxm));
		model.addAttribute("cago_list", tb_pdcagoxm);
		
		//1단계 카테고리 선택시 2단계 가져오기
		TB_PDCAGOXM tb_pdcagoxm02 = new TB_PDCAGOXM();
		if(tb_pdbordxm.getSchCago01() != "" && tb_pdbordxm.getSchCago01() != null){
			tb_pdbordxm.setSchCago(tb_pdbordxm.getSchCago01());
			tb_pdcagoxm02.setList(boardMgrService.getSchCagoObjectList(tb_pdbordxm));
		}
		model.addAttribute("cago_list02", tb_pdcagoxm02);
		
		//2단계 카테고리 선택시 3단계 가져오기
		TB_PDCAGOXM tb_pdcagoxm03 = new TB_PDCAGOXM();
		if(tb_pdbordxm.getSchCago02() != "" && tb_pdbordxm.getSchCago02() != null){
			tb_pdbordxm.setSchCago(tb_pdbordxm.getSchCago02());
			tb_pdcagoxm03.setList(boardMgrService.getSchCagoObjectList(tb_pdbordxm));
		}
		model.addAttribute("cago_list03", tb_pdcagoxm03);
		
		//3단계 카테고리 선택시 3단계 가져오기
		TB_PDCAGOXM tb_pdcagoxm04 = new TB_PDCAGOXM();
		if(tb_pdbordxm.getSchCago03() != "" && tb_pdbordxm.getSchCago03() != null){
			tb_pdbordxm.setSchCago(tb_pdbordxm.getSchCago03());
			tb_pdcagoxm04.setList(boardMgrService.getSchCagoObjectList(tb_pdbordxm));
		}
		model.addAttribute("cago_list04", tb_pdcagoxm04);
		
		if(tb_pdbordxm.getSchCago04() != "" && tb_pdbordxm.getSchCago04() != null){
			tb_pdbordxm.setSchCago(tb_pdbordxm.getSchCago04());
		}
		
		tb_pdbordxm.setCount(boardMgrService.getObjectCount03(tb_pdbordxm));
		tb_pdbordxm.setList(boardMgrService.getPaginatedObjectList03(tb_pdbordxm));
		
		model.addAttribute("obj", tb_pdbordxm);
		model.addAttribute("rowCnt", tb_pdbordxm.getRowCnt());
		model.addAttribute("totalCnt", tb_pdbordxm.getCount());
		
		String strLink = null;
		strLink = "&schGbn="+StringUtil.nullCheck(tb_pdbordxm.getSchGbn())
				+ "&schTxt="+StringUtil.nullCheck(tb_pdbordxm.getSchTxt());
		
		model.addAttribute("link", strLink);
		
		return new ModelAndView("admin.layout", "jsp", "admin/boardMgr/list03");
	}
	
	/**
	 * ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
	 * @Class	: mall.web.controller.admin.BoardMgrController.java
	 * @Method	: edit
	 * ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
	 * @Desc	: 게시판 타입03 상세
	 * @Company	: YT Corp.
	 * @Author	: Young-dae Kim (sjie1638@youngthink.co.kr)
	 * @Date	: 2036-07-19 (오전 10:51:58)
	 * ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
	 * @param tb_pdbordxm
	 * @param model
	 * @return
	 * @throws Exception
	 * ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
	*/
	@RequestMapping(value={ "/type03/{BRD_GUBN}/{BRD_NUM}" }, method=RequestMethod.GET)
	public ModelAndView edit03(@ModelAttribute TB_PDBORDXM  tb_pdbordxm, Model model) throws Exception {
		
		tb_pdbordxm = (TB_PDBORDXM) boardMgrService.getObject03(tb_pdbordxm);
		
		model.addAttribute("tb_pdbordxm", tb_pdbordxm);
		
		return new ModelAndView("admin.layout", "jsp", "admin/boardMgr/form03");
	}
	
	/**
	 * ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
	 * @Class	: mall.web.controller.admin.BoardMgrController.java
	 * @Method	: update
	 * ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
	 * @Desc	: 게시판 타입03 답변 저장
	 * @Company	: YT Corp.
	 * @Author	: Young-dae Kim (sjie1638@youngthink.co.kr)
	 * @Date	: 2036-07-19 (오전 10:58:57)
	 * ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
	 * @param tb_pdbordxm
	 * @param result
	 * @param status
	 * @param model
	 * @return
	 * @throws Exception
	 * ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
	*/
	@RequestMapping(value={ "/type03/{BRD_GUBN}/{BRD_NUM}" }, method=RequestMethod.POST)
	public ModelAndView update03(@ModelAttribute TB_PDBORDXM  tb_pdbordxm, BindingResult result, SessionStatus status, Model model) throws Exception {
		
		tb_pdbordxm.setREP_ID("admin");
		boardMgrService.updateObject03(tb_pdbordxm);
				
		/*RedirectView redirectView = new RedirectView(servletContextPath+"/adm/boardMgr/type03/"+tb_pdbordxm.getBRD_GUBN()+"/"+tb_pdbordxm.getBRD_NUM());*/

		// 2019.02.25 chjw
		String delyn = tb_pdbordxm.getDEL_YN();
		String redirectPath = servletContextPath+"/adm/boardMgr/type03/"+tb_pdbordxm.getBRD_GUBN()+"/"+tb_pdbordxm.getBRD_NUM();
		
		System.out.println(delyn);
		System.out.println(redirectPath);
		
		if (delyn.equals("YES")){
			redirectPath = servletContextPath+"/adm/boardMgr/type03/"+tb_pdbordxm.getBRD_GUBN();
		}
				
		RedirectView redirectView = new RedirectView(redirectPath);
				
		return new ModelAndView(redirectView);
	}
	
	
	/**
	 * ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
	 * @Class	: mall.web.controller.admin.BoardMgrController.java
	 * @Method	: getList
	 * ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
	 * @Desc	: 게시판 타입04 리스트
	 * @Company	: YT Corp.
	 * @Author	: Young-dae Kim (sjie1638@youngthink.co.kr)
	 * @Date	: 2016-07-18 (오후 5:34:28)
	 * ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
	 * @param tb_pdbordxm
	 * @param pagerOffset
	 * @param model
	 * @return
	 * @throws Exception
	 * ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
	*/
	@RequestMapping(value={ "/type04/{BRD_GUBN}" }, method=RequestMethod.GET)
	public ModelAndView getList04(@ModelAttribute TB_PDBORDXM  tb_pdbordxm, Model model) throws Exception {
		
		tb_pdbordxm.setCount(boardMgrService.getObjectCount04(tb_pdbordxm));
		tb_pdbordxm.setList(boardMgrService.getPaginatedObjectList04(tb_pdbordxm));
		
		model.addAttribute("obj", tb_pdbordxm);
		model.addAttribute("rowCnt", tb_pdbordxm.getRowCnt());
		model.addAttribute("totalCnt", tb_pdbordxm.getCount());
		
		String strLink = null;
		strLink = "&schGbn="+StringUtil.nullCheck(tb_pdbordxm.getSchGbn())
				+ "&schTxt="+StringUtil.nullCheck(tb_pdbordxm.getSchTxt());
		
		model.addAttribute("link", strLink);
		
		return new ModelAndView("admin.layout", "jsp", "admin/boardMgr/list04");
	}
	
	/**
	 * ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
	 * @Class	: mall.web.controller.admin.BoardMgrController.java
	 * @Method	: edit04
	 * ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
	 * @Desc	: 게시판 타입04 등록 & 수정 폼 호출
	 * @Company	: YT Corp.
	 * @Author	: Young-dae Kim (sjie1638@youngthink.co.kr)
	 * @Date	: 2016-07-19 (오후 2:31:19)
	 * ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
	 * @param tb_pdbordxm
	 * @param model
	 * @return
	 * @throws Exception
	 * ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
	*/
	@RequestMapping(value={ "/type04/{BRD_GUBN}/{BRD_NUM}" , "/type04/{BRD_GUBN}/new"}, method=RequestMethod.GET)
	public ModelAndView edit04(@ModelAttribute TB_PDBORDXM  tb_pdbordxm, Model model) throws Exception {
		
		if(StringUtils.isNotEmpty(tb_pdbordxm.getBRD_NUM())){
			tb_pdbordxm = (TB_PDBORDXM) boardMgrService.getObject04(tb_pdbordxm);
		}
		
		model.addAttribute("tb_pdbordxm", tb_pdbordxm);
		
		return new ModelAndView("admin.layout", "jsp", "admin/boardMgr/form04");
	}
	
	/**
	 * ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
	 * @Class	: mall.web.controller.admin.BoardMgrController.java
	 * @Method	: insert04
	 * ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
	 * @Desc	: 게시판 타입04 등록
	 * @Company	: YT Corp.
	 * @Author	: Young-dae Kim (sjie1638@youngthink.co.kr)
	 * @Date	: 2016-07-19 (오후 2:31:37)
	 * ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
	 * @param tb_pdbordxm
	 * @param result
	 * @param status
	 * @param model
	 * @return
	 * @throws Exception
	 * ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
	*/
	@RequestMapping(value={ "/type04/{BRD_GUBN}" }, method=RequestMethod.POST)
	public ModelAndView insert04(@ModelAttribute TB_PDBORDXM  tb_pdbordxm, BindingResult result, SessionStatus status, Model model) throws Exception {
		
		tb_pdbordxm.setWRTR_ID("admin");
		tb_pdbordxm.setREGP_ID("admin");
		tb_pdbordxm.setMODP_ID("admin");
		boardMgrService.insertObject04(tb_pdbordxm);
		
		
		RedirectView redirectView = new RedirectView(servletContextPath+"/adm/boardMgr/type04/"+tb_pdbordxm.getBRD_GUBN());
		
		return new ModelAndView(redirectView);
	}
	
	/**
	 * ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
	 * @Class	: mall.web.controller.admin.BoardMgrController.java
	 * @Method	: update04
	 * ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
	 * @Desc	: 게시판 타입04 수정
	 * @Company	: YT Corp.
	 * @Author	: Young-dae Kim (sjie1638@youngthink.co.kr)
	 * @Date	: 2016-07-19 (오후 2:31:47)
	 * ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
	 * @param tb_pdbordxm
	 * @param result
	 * @param status
	 * @param model
	 * @return
	 * @throws Exception
	 * ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
	*/
	@RequestMapping(value={ "/type04/{BRD_GUBN}/{BRD_NUM}" }, method=RequestMethod.PUT)
	public ModelAndView update04(@ModelAttribute TB_PDBORDXM  tb_pdbordxm, BindingResult result, SessionStatus status, Model model) throws Exception {
		
		tb_pdbordxm.setMODP_ID("admin");
		boardMgrService.updateObject04(tb_pdbordxm);
				
		/*RedirectView redirectView = new RedirectView(servletContextPath+"/adm/boardMgr/type04/"+tb_pdbordxm.getBRD_GUBN()+"/"+tb_pdbordxm.getBRD_NUM());*/
		

		// 2019.02.25 chjw
		String delyn = tb_pdbordxm.getDEL_YN();
		String redirectPath = servletContextPath+"/adm/boardMgr/type04/"+tb_pdbordxm.getBRD_GUBN()+"/"+tb_pdbordxm.getBRD_NUM();
		
		System.out.println(delyn);
		System.out.println(redirectPath);
		
		if (delyn.equals("YES")){
			redirectPath = servletContextPath+"/adm/boardMgr/type04/"+tb_pdbordxm.getBRD_GUBN();
		}				
		
		RedirectView redirectView = new RedirectView(redirectPath);
				
		
		return new ModelAndView(redirectView);
	}
	
	
	/**
	 * ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
	 * @Class	: mall.web.controller.admin.BoardMgrController.java
	 * @Method	: getCagoList
	 * ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
	 * @Desc	: 카테고리 단계별 변경시
	 * @Company	: YT Corp.
	 * @Author	: Young-dae Kim (sjie1638@youngthink.co.kr)
	 * @Date	: 2016-07-26 (오후 2:39:36)
	 * ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
	 * @param tb_pdbordxm
	 * @param pagerOffset
	 * @param model
	 * @return
	 * @throws Exception
	 * ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
	*/
	@RequestMapping(value={ "/cago" }, method=RequestMethod.POST)
	public ModelAndView getCagoList(@ModelAttribute TB_PDBORDXM  tb_pdbordxm, Model model) throws Exception {
		
		TB_PDCAGOXM tb_pdcagoxm = new TB_PDCAGOXM();
		tb_pdcagoxm.setList(boardMgrService.getSchCagoObjectList(tb_pdbordxm));
		model.addAttribute("cago_list", tb_pdcagoxm);
		model.addAttribute("schCagoGubun", tb_pdbordxm.getSchCagoGubun());
		
		return new ModelAndView("popup.layout", "jsp", "admin/boardMgr/cago");
	}
}
