package mall.web.controller.responsiveMall;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import mall.web.domain.TB_COMCODXD;
import mall.web.domain.TB_MBINFOXM;
import mall.web.domain.TB_ODINFOXM;
import mall.web.service.admin.impl.MemberMgrService;
import mall.web.service.common.CommonService;
import mall.web.service.mall.OrderService;

@Controller
@RequestMapping(value="/m")
public class CommonRespController {
	@Resource(name="commonService")
	CommonService commonService;
	
	//회원관리 서비스 이용
	@Resource(name="memberMgrService")
	MemberMgrService memberMgrService;

	@Resource(name="orderService")
	OrderService orderService;
	
	@RequestMapping(value="/user/loginForm", method=RequestMethod.GET)
	public ModelAndView userLoginForm(@ModelAttribute TB_COMCODXD comCod,  Model model) throws Exception {

		return new ModelAndView("blankPage", "jsp", "responsiveMall/login/loginForm");
	}
	
	/**
	 * ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
	 * @Class	: mall.web.controller.admin.CommonController.java
	 * @Method	: getList
	 * ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
	 * @Desc	: 로그인 세션처리
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
	@RequestMapping(value="/user/login", method=RequestMethod.POST)
	public ModelAndView getUserLogin(@ModelAttribute TB_MBINFOXM memberInfo, Model model, HttpServletRequest request, HttpSession session) throws Exception {
		
		ModelAndView mav = new ModelAndView();
		TB_MBINFOXM loginMember = (TB_MBINFOXM)memberMgrService.getObject(memberInfo);

		//String strEncrpytPasswd = digestUtils.kisaSha256(memberInfo.getMEMB_PW());
		String strEncrpytPasswd = memberInfo.getMEMB_PW();
		String trimEncrpytPasswd = strEncrpytPasswd.trim();	//공백제거
		
		String strRtnUrl = memberInfo.getRtnUrl();
		System.out.println("request.getHeader('referer'); >> " + strRtnUrl);
		
		if(loginMember==null){
			mav.addObject("alertMessage", "아이디와 비밀번호를 입력해 주시기 바랍니다.");
			mav.addObject("returnUrl", "/m/user/loginForm");
			mav.setViewName("alertMessage");
		}else if(loginMember.getMEMB_ID()=="" || loginMember.getMEMB_PW()==""
					|| loginMember.getMEMB_ID()==null || loginMember.getMEMB_PW()==null) {
			
			mav.addObject("alertMessage", "아이디와 비밀번호를 입력해 주시기 바랍니다.");
			mav.addObject("returnUrl", "/m/user/loginForm");
			mav.setViewName("alertMessage");
			
		}else if (!loginMember.getMEMB_PW().equals( trimEncrpytPasswd )) {
			mav.addObject("alertMessage", "아이디와 비밀번호를 확인하시기 바랍니다.");
			mav.addObject("returnUrl", "/m/user/loginForm");
			mav.setViewName("alertMessage");

		} else if(loginMember.getSCSS_YN().equals("Y")){
			mav.addObject("alertMessage", "탈퇴된 회원계정입니다.고객센터로 문의바랍니다.");
			mav.addObject("returnUrl", "/m/user/loginForm");
			mav.setViewName("alertMessage");

		} else if(loginMember.getSTOP_YN().equals("Y")){
			mav.addObject("alertMessage", "일시중지된 회원계정입니다. 고객센터로 문의바랍니다.");
			mav.addObject("returnUrl", "/m/user/loginForm");
			mav.setViewName("alertMessage");
			
		}else {

			if(StringUtils.isEmpty(strRtnUrl)){
				strRtnUrl = request.getHeader("referer");
			}else{
				if(strRtnUrl.indexOf("findmemberinfo/result") > 0 || strRtnUrl.indexOf("user/login") > 0 || strRtnUrl.indexOf("user/loginForm") > 0){
					strRtnUrl = "/m";
				}
			}
			
			// 유저 세션 생성
			session.setAttribute("USER",  loginMember);
			
			// 미결재내역  세션 생성
			TB_ODINFOXM resultNotPayCnt= new TB_ODINFOXM();
			resultNotPayCnt.setREGP_ID(loginMember.getMEMB_ID());	
			resultNotPayCnt.setORDER_CON("ORDER_CON_01");
			
			session.setAttribute("NOTPAYCNT", orderService.getObjectCount(resultNotPayCnt));
			session.setAttribute("DELICUPON", loginMember.getDLVY_CPON());
			
			RedirectView rv = new RedirectView(strRtnUrl);			
			return new ModelAndView(rv);
		}
		
		return mav;
	}
		
	
	// logout
	@RequestMapping(value="/user/logout")
	public ModelAndView userLogout(HttpServletRequest request, Model model) {
		ModelAndView mav = new ModelAndView();
		request.getSession().invalidate();

		mav.setViewName("redirect:" + "/m");
		return mav;

	}
}
