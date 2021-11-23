package mall.web.controller.admin;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import mall.web.controller.DefaultController;
import mall.web.domain.TB_COMCODXD;
import mall.web.domain.TB_MBINFOXM;
import mall.web.domain.TB_SYSMNUXM;
import mall.web.service.admin.impl.ComCodMgrService;
import mall.web.service.admin.impl.MenuMgrService;

@Controller
@RequestMapping(value="/adm/comCodMgr")
public class ComCodMgrController extends DefaultController{

	@Resource(name="comCodMgrService")
	ComCodMgrService comCodMgrService;
	
	/**
	 * ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
	 * @Class	: mall.web.controller.admin.MenuMgrController.java
	 * @Method	: getList
	 * ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
	 * @Desc	: 메뉴 목록 조회
	 * @Company	: YT Corp.
	 * @Author	: Young-dae Kim (sjie1638@youngthink.co.kr)
	 * @Date	: 2016-07-20 (오후 3:07:41)
	 * ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
	 * @param tb_sysmnuxm
	 * @param model
	 * @return
	 * @throws Exception
	 * ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
	*/
	@RequestMapping(method=RequestMethod.GET)
	public ModelAndView getList(@ModelAttribute TB_COMCODXD tb_comcodxd, Model model) throws Exception {
		
		tb_comcodxd.setList(comCodMgrService.getObjectList(tb_comcodxd));
		model.addAttribute("obj", tb_comcodxd);
		
		return new ModelAndView("admin.layout", "jsp", "admin/comCodMgr/list");
	}
	
	/**
	 * ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
	 * @Class	: mall.web.controller.admin.MenuMgrController.java
	 * @Method	: edit04
	 * ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
	 * @Desc	: 메뉴 등록 폼 조회
	 * @Company	: YT Corp.
	 * @Author	: Young-dae Kim (sjie1638@youngthink.co.kr)
	 * @Date	: 2016-07-20 (오후 3:32:03)
	 * ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
	 * @param tb_sysmnuxm
	 * @param model
	 * @return
	 * @throws Exception
	 * ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
	*/
	@RequestMapping(value={ "/{COMM_CODE}" , "/new"}, method=RequestMethod.GET)
	public ModelAndView edit(@ModelAttribute TB_COMCODXD tb_comcodxd, Model model, HttpServletRequest request) throws Exception {
		
		if(StringUtils.isNotEmpty(tb_comcodxd.getCOMM_CODE())){
			//기존등록
			model.addAttribute("NewYN", "N");
			tb_comcodxd = (TB_COMCODXD) comCodMgrService.getObject(tb_comcodxd);			
			tb_comcodxd.setList(comCodMgrService.getSelectComCodList(tb_comcodxd));
			
			System.out.println("tb_comcodxd.getList().size()==="+tb_comcodxd.getList().size());
			
			model.addAttribute("tb_comcodxm", tb_comcodxd);
			model.addAttribute("tb_comcodxd", tb_comcodxd.getList());
		}else{
			//신규등록
			model.addAttribute("NewYN", "Y");
		}		
		
		return new ModelAndView("admin.layout", "jsp", "admin/comCodMgr/form");
	}
	

	@ResponseBody
	@RequestMapping(value={"/save"}, method=RequestMethod.POST)
	public void save(@ModelAttribute TB_COMCODXD tb_comcodxd, Model model, HttpServletRequest request) throws Exception {
		
 		TB_MBINFOXM loginUser = (TB_MBINFOXM)request.getSession().getAttribute("ADMUSER");		
		tb_comcodxd.setREGP_ID(loginUser.getMEMB_ID());
		tb_comcodxd.setMODP_ID(loginUser.getMEMB_ID());
		
		System.out.println(request.getParameter("CodFlag"));
		System.out.println(tb_comcodxd.getCodFlag());
		//마스터 저장
		if(tb_comcodxd.getCodFlag().equals("CommInsert") || tb_comcodxd.getCodFlag().equals("CommUpdate") || tb_comcodxd.getCodFlag().equals("CommDelete") )
		{
			comCodMgrService.MatserInsert(tb_comcodxd);
		}
		
		//디테일 저장
		else if(tb_comcodxd.getCodFlag().equals("Read") || tb_comcodxd.getCodFlag().equals("Insert") || tb_comcodxd.getCodFlag().equals("Delete"))
		{
			//업데이트할 데이터가 기존 데이터와 일치하지 않을 경우
			if( !( tb_comcodxd.getCodFlag().equals("Read") && comCodMgrService.ComdUpdateChk(tb_comcodxd) == 1) )
				comCodMgrService.DtlInsert(tb_comcodxd);
		}
		
	}
	
	/**
	 * ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
	 * @Class	: mall.web.controller.admin.MenuMgrController.java
	 * @Method	: insert04
	 * ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
	 * @Desc	: 메뉴 등록
	 * @Company	: YT Corp.
	 * @Author	: Young-dae Kim (sjie1638@youngthink.co.kr)
	 * @Date	: 2016-07-20 (오후 3:32:11)
	 * ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
	 * @param tb_sysmnuxm
	 * @param result
	 * @param status
	 * @param model
	 * @return
	 * @throws Exception
	 * ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
	*/
	@RequestMapping(method=RequestMethod.POST)
	public ModelAndView insert(@ModelAttribute TB_SYSMNUXM tb_sysmnuxm, BindingResult result, SessionStatus status, Model model) throws Exception {
		
		tb_sysmnuxm.setREGP_ID("admin");
		tb_sysmnuxm.setMODP_ID("admin");
		comCodMgrService.insertObject(tb_sysmnuxm);
		
		
		RedirectView redirectView = new RedirectView(servletContextPath+"/adm/menuMgr");
		
		return new ModelAndView(redirectView);
	}
	
	/**
	 * ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
	 * @Class	: mall.web.controller.admin.MenuMgrController.java
	 * @Method	: update04
	 * ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
	 * @Desc	: 메뉴수정
	 * @Company	: YT Corp.
	 * @Author	: Young-dae Kim (sjie1638@youngthink.co.kr)
	 * @Date	: 2016-07-20 (오후 3:32:18)
	 * ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
	 * @param tb_sysmnuxm
	 * @param result
	 * @param status
	 * @param model
	 * @return
	 * @throws Exception
	 * ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
	*/
	@RequestMapping(value={ "/{COMM_CODE}" }, method=RequestMethod.PUT)
	public ModelAndView update(@ModelAttribute TB_SYSMNUXM tb_sysmnuxm, BindingResult result, SessionStatus status, Model model) throws Exception {
		
		tb_sysmnuxm.setMODP_ID("admin");
		comCodMgrService.updateObject(tb_sysmnuxm);
		
		RedirectView redirectView = new RedirectView(servletContextPath+"/adm/menuMgr/"+tb_sysmnuxm.getMENU_CD());
		
		return new ModelAndView(redirectView);
	}
	
	/**
	 * ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
	 * @Class	: mall.web.controller.admin.MenuMgrController.java
	 * @Method	: delete
	 * ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
	 * @Desc	: 메뉴삭제
	 * @Company	: YT Corp.
	 * @Author	: Young-dae Kim (sjie1638@youngthink.co.kr)
	 * @Date	: 2016-07-20 (오후 3:33:45)
	 * ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
	 * @param tb_sysmnuxm
	 * @param result
	 * @param status
	 * @param model
	 * @return
	 * @throws Exception
	 * ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
	*/
	@RequestMapping(value={ "/{MENU_CD}" }, method=RequestMethod.DELETE)
	public ModelAndView delete(@ModelAttribute TB_SYSMNUXM tb_sysmnuxm, BindingResult result, SessionStatus status, Model model) throws Exception {
		
		comCodMgrService.deleteObject(tb_sysmnuxm);
		
		RedirectView redirectView = new RedirectView(servletContextPath+"/adm/menuMgr");
		
		return new ModelAndView(redirectView);
	}
	
	/**
	 * ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
	 * @Class	: mall.web.controller.admin.MenuMgrController.java
	 * @Method	: popup
	 * ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
	 * @Desc	: 상위메뉴 선택
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
	public ModelAndView popup(@ModelAttribute TB_SYSMNUXM tb_sysmnuxm, Model model) throws Exception {
		
		tb_sysmnuxm.setList(comCodMgrService.getObjectList(tb_sysmnuxm));
		model.addAttribute("obj", tb_sysmnuxm);
		
		return new ModelAndView("popup.layout", "jsp", "admin/menuMgr/popup");
	}
	
	/**
	 * ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
	 * @Class	: mall.web.controller.admin.MenuMgrController.java
	 * @Method	: insert04
	 * ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
	 * @Desc	: 메뉴코드 중복 검사
	 * @Company	: YT Corp.
	 * @Author	: Young-dae Kim (sjie1638@youngthink.co.kr)
	 * @Date	: 2016-07-20 (오후 3:32:11)
	 * ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
	 * @param tb_sysmnuxm
	 * @param result
	 * @param status
	 * @param model
	 * @return
	 * @throws Exception
	 * ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
	*/
	@RequestMapping(value={ "/chk"}, method=RequestMethod.POST)
	public @ResponseBody String chk(@ModelAttribute TB_COMCODXD tb_comcodxd, BindingResult result, SessionStatus status, Model model) throws Exception {
		
		int updateCnt = comCodMgrService.getCodeSameCnt(tb_comcodxd);
		/*return updateCnt > 0 ? SUCCESS : FAILURE;*/
		return updateCnt == 0 ? "true" : "false";
	}
}
