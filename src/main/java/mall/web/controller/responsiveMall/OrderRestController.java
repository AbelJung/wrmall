package mall.web.controller.responsiveMall;

import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import lgdacom.XPayClient.XPayClient;
import mall.web.controller.DefaultController;
import mall.web.controller.admin.CategoryMgrController;
import mall.web.domain.TB_BKINFOXM;
import mall.web.domain.TB_MBINFOXM;
import mall.web.domain.TB_ODDLAIXM;
import mall.web.domain.TB_ODINFOXD;
import mall.web.domain.TB_ODINFOXM;
import mall.web.domain.TB_SPINFOXM;
import mall.web.service.admin.impl.SupplierMgrService;
import mall.web.service.mall.MemberService;
import mall.web.service.mall.OrderService;
import mall.web.service.mall.ProductService;

@Controller
//@RequestMapping(value="/orderLgUplus")
@RequestMapping(value="/m/order")
public class OrderRestController extends DefaultController{
	
	private static final Logger logger = LoggerFactory.getLogger(CategoryMgrController.class);

	@Autowired
	private Environment environment;
	
	@Resource(name="orderService")
	OrderService orderService;

	@Resource(name="supplierMgrService")
	SupplierMgrService supplierMgrService;

	@Resource(name="productService")
	ProductService productService;

	@Resource(name="memberService")
	MemberService memberService;
	
	/**
	 * ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
	 * @Class	: mall.web.controller.mall.OrderController.java
	 * @Method	: index
	 * ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
	 * @Desc	: 관심상품
	 * @Company	: YT Corp.
	 * @Author	: Young-dae Kim (sjie1638@youngthink.co.kr)
	 * @Date	: 2016-07-27 (오후 2:07:41)
	 * ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
	 * @param tb_odinfoxm
	 * @param model
	 * @return
	 * @throws Exception
	 * ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
	*/
	@RequestMapping(value="/wishList", method=RequestMethod.GET)
	public ModelAndView index(@ModelAttribute TB_ODINFOXM tb_odinfoxm, HttpServletRequest request, Model model) throws Exception {

		TB_MBINFOXM loginUser = (TB_MBINFOXM)request.getSession().getAttribute("USER");
		
		if(loginUser==null){			
			ModelAndView mav = new ModelAndView();
			mav.addObject("alertMessage", "로그인 후 이용해주세요.");
			mav.addObject("returnUrl", "/m/user/loginForm");
			mav.setViewName("alertMessage");
			return mav;
			
		}else{			
			if(request.getParameter("pagerMaxPageItems")!=null){
				tb_odinfoxm.setRowCnt(Integer.parseInt(request.getParameter("pagerMaxPageItems")));	//페이징 단위 : 50
				tb_odinfoxm.setPagerMaxPageItems(Integer.parseInt(request.getParameter("pagerMaxPageItems")));
			}else {
				tb_odinfoxm.setRowCnt(20);	//페이징 단위 : 20
				tb_odinfoxm.setPagerMaxPageItems(20);	
			}			
			
			tb_odinfoxm.setREGP_ID(loginUser.getMEMB_ID());
			tb_odinfoxm.setCount(orderService.getObjectCount(tb_odinfoxm));			
			tb_odinfoxm.setList(orderService.getObjectList(tb_odinfoxm));
			
			model.addAttribute("obj", tb_odinfoxm);			
			model.addAttribute("rowCnt", tb_odinfoxm.getRowCnt());
			model.addAttribute("totalCnt", tb_odinfoxm.getCount());			
		}
		
		return new ModelAndView("mall.responsive.layout", "jsp", "order/list");
	}
	
	/**
	 * 주문
	 * @param tb_odinfoxm
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/new", method=RequestMethod.POST)
	public ModelAndView newForm(@ModelAttribute TB_ODINFOXM tb_odinfoxm, Model model, HttpServletRequest request) throws Exception {
		
		StringTokenizer stok = new StringTokenizer(tb_odinfoxm.getBSKT_REGNO_LIST(),"$");
		List<String> list = new ArrayList<String>();
		while(stok.hasMoreTokens()){
			String str = stok.nextToken();
			list.add(str);
		}
		tb_odinfoxm.setList(orderService.getNewObjectList(list));
		model.addAttribute("obj", tb_odinfoxm);
		
		//배송비 조건
		TB_SPINFOXM supplierInfo = new TB_SPINFOXM();
		supplierInfo.setSUPR_ID("C00002");
		model.addAttribute("supplierInfo", (TB_SPINFOXM)supplierMgrService.getObject(supplierInfo));

		//배송비쿠폰 select
		TB_MBINFOXM tb_mbinfoxm = (TB_MBINFOXM)request.getSession().getAttribute("USER");
		tb_mbinfoxm = (TB_MBINFOXM) memberService.getObject(tb_mbinfoxm);
		model.addAttribute("mbinfo", tb_mbinfoxm);		
		
		return new ModelAndView("mall.responsive.layout", "jsp", "order/new");
	}
	
	@RequestMapping(value="/buy")
	public ModelAndView buy(@ModelAttribute TB_ODINFOXD tb_odinfoxm, Model model, HttpServletRequest request) throws Exception {

		StringTokenizer stok = new StringTokenizer(tb_odinfoxm.getPD_CODE(),"$");
		List<String> list = new ArrayList<String>();
		while(stok.hasMoreTokens()){
			String str = stok.nextToken();
			list.add(str);
		}
		
		tb_odinfoxm.setList(orderService.getBuyObjectList(list));
		model.addAttribute("obj", tb_odinfoxm);
		
		//배송비 조건
		TB_SPINFOXM supplierInfo = new TB_SPINFOXM();
		supplierInfo.setSUPR_ID("C00002");
		model.addAttribute("supplierInfo", (TB_SPINFOXM)supplierMgrService.getObject(supplierInfo));

		//배송비쿠폰 select
		TB_MBINFOXM tb_mbinfoxm = (TB_MBINFOXM)request.getSession().getAttribute("USER");
		tb_mbinfoxm = (TB_MBINFOXM) memberService.getObject(tb_mbinfoxm);
		model.addAttribute("mbinfo", tb_mbinfoxm);		
		
		return new ModelAndView("mall.responsive.layout", "jsp", "order/new");
	}
	
	@RequestMapping(value="/insert", method=RequestMethod.POST)
	public ModelAndView insert(@ModelAttribute TB_ODINFOXM tb_odinfoxm, @ModelAttribute TB_ODINFOXD tb_odinfoxd, @ModelAttribute TB_ODDLAIXM tb_oddlaixm, Model model, HttpServletRequest request) throws Exception {

		TB_MBINFOXM loginUser = (TB_MBINFOXM)request.getSession().getAttribute("USER");
		tb_odinfoxm.setREGP_ID(loginUser.getMEMB_ID());
		tb_odinfoxd.setREGP_ID(loginUser.getMEMB_ID());
		tb_oddlaixm.setREGP_ID(loginUser.getMEMB_ID());
		
		orderService.insertOrderObject(tb_odinfoxm, tb_odinfoxd, tb_oddlaixm);

		System.out.println(tb_odinfoxm.getCPON_YN());
		//회원정보 배송비쿠폰갯수 update
		if(tb_odinfoxm.getCPON_YN() != null && tb_odinfoxm.getCPON_YN().equals("Y")){
			TB_MBINFOXM mbinfoxm = new TB_MBINFOXM();
			mbinfoxm.setMEMB_ID(loginUser.getMEMB_ID());
			mbinfoxm.setREGP_ID(loginUser.getMEMB_ID());
			mbinfoxm.setDLVY_CPON(tb_odinfoxm.getDLVY_CPON());
			
			memberService.updateCponCnt(mbinfoxm);
		}
		
		RedirectView redirectView = new RedirectView(servletContextPath+"/m/order/view/"+tb_odinfoxm.getORDER_NUM());
		return new ModelAndView(redirectView);
	}
	
	/**
	 * 신용카드 결제
	 * @param tb_odinfoxm
	 * @param tb_odinfoxd
	 * @param tb_oddlaixm
	 * @param model
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/orderPay")//, method=RequestMethod.POST)
	public ModelAndView orderPay(@ModelAttribute TB_ODINFOXM tb_odinfoxm, @ModelAttribute TB_ODINFOXD tb_odinfoxd, @ModelAttribute TB_ODDLAIXM tb_oddlaixm, Model model, HttpServletRequest request) throws Exception {

		TB_MBINFOXM loginUser = (TB_MBINFOXM)request.getSession().getAttribute("USER");
		tb_odinfoxm.setREGP_ID(loginUser.getMEMB_ID());
		tb_odinfoxd.setREGP_ID(loginUser.getMEMB_ID());
		tb_oddlaixm.setREGP_ID(loginUser.getMEMB_ID());
		//orderService.insertOrderObject(tb_odinfoxm, tb_odinfoxd, tb_oddlaixm);
		
		/*
		logger.info("lguplus.cst_platform >> " + environment.getProperty("lguplus.cst_platform"));
		logger.info("lguplus.cst_mid >> " + environment.getProperty("lguplus.cst_mid"));
		logger.info("lguplus.lgd_martkey >> " + environment.getRequiredProperty("lguplus.lgd_martkey"));
		logger.info("lguplus.lgd_custom_skin >> " + environment.getRequiredProperty("lguplus.lgd_custom_skin"));
		logger.info("lguplus.lgd_window_ver >> " + environment.getRequiredProperty("lguplus.lgd_window_ver"));
		logger.info("lguplus.lgd_window_type >> " + environment.getRequiredProperty("lguplus.lgd_window_type"));
		logger.info("lguplus.lgd_ostype_check >> " + environment.getRequiredProperty("lguplus.lgd_ostype_check"));
		logger.info("lguplus.lgd_casnoteurl >> " + environment.getRequiredProperty("lguplus.lgd_casnoteurl"));
		logger.info("lguplus.lgd_returnurl >> " + environment.getRequiredProperty("lguplus.lgd_returnurl"));
		*/
		
	    /*
	     * [결제 인증요청 페이지(STEP2-1)]
	     * 샘플페이지에서는 기본 파라미터만 예시되어 있으며, 별도로 필요하신 파라미터는 연동메뉴얼을 참고하시어 추가 하시기 바랍니다.
	     */

	    /*
	     * 1. 기본결제 인증요청 정보 변경
	     * 기본정보를 변경하여 주시기 바랍니다.(파라미터 전달시 POST를 사용하세요)
	     */
		//농수축산물센터 - cjh3474    f521e1be913b23de7a66b793b90069ec  http://www.amsmall.co.kr/order/payReturn
		
		//주문정보 마스터 정보
		TB_ODINFOXM rtnOdinfoxm = (TB_ODINFOXM)orderService.getMasterInfo(tb_odinfoxm);
		//주문정보 상세  
		rtnOdinfoxm.setList(orderService.getDetailsList(rtnOdinfoxm));
		//배송지 정보
		TB_ODDLAIXM rtnOddlaixm = new TB_ODDLAIXM();
		rtnOddlaixm = (TB_ODDLAIXM)orderService.getDeliveryInfo(rtnOdinfoxm);
		
		//..주문정보 끝
		
		long timestamp = (System.currentTimeMillis() / 1000L) * 1000L;
		
	    String CST_PLATFORM         	= environment.getRequiredProperty("lguplus.cst_platform");			//LG유플러스 결제서비스 선택(test:테스트, service:서비스)
	    String CST_MID              		= environment.getRequiredProperty("lguplus.cst_mid");				//LG유플러스로 부터 발급받으신 상점아이디를 입력하세요.
	    String LGD_MID              		= ("test".equals(CST_PLATFORM.trim())?"t":"")+CST_MID;				//테스트 아이디는 't'를 제외하고 입력하세요.
	    																																	//상점아이디(자동생성)
	    String LGD_OID              		= rtnOdinfoxm.getORDER_NUM();												//주문번호(상점정의 유니크한 주문번호를 입력하세요)	tb_odinfoxm.getORDER_NUM();
	    String LGD_AMOUNT           	= rtnOdinfoxm.getORDER_AMT();												//테스트결제금액("," 를 제외한 결제금액을 입력하세요)
	    
	    String LGD_MERTKEY          	= environment.getRequiredProperty("lguplus.lgd_martkey");			//상점MertKey(mertkey는 상점관리자 -> 계약정보 -> 상점정보관리에서 확인하실수 있습니다)
	    String LGD_BUYER           	= loginUser.getMEMB_ID();														//구매자명
	    String LGD_PRODUCTINFO    = "쇼핑몰 상품 주문";																		//상품명
	    String LGD_BUYEREMAIL		= loginUser.getMEMB_MAIL();													//구매자 이메일
	    String LGD_TIMESTAMP			= ""+timestamp;																		//타임스탬프
	    String LGD_CUSTOM_USABLEPAY = "SC0010";																		//상점정의 초기결제수단	//"PAY_METD_01"
	    String LGD_CUSTOM_SKIN	= environment.getRequiredProperty("lguplus.lgd_custom_skin");	//상점정의 결제창 스킨(red)
	    String LGD_CUSTOM_SWITCHINGTYPE = "IFRAME"; 																//신용카드 카드사 인증 페이지 연동 방식 (수정불가)
	    String LGD_WINDOW_VER	= environment.getRequiredProperty("lguplus.lgd_window_ver");		//결제창 버젼정보
	    String LGD_WINDOW_TYPE  = environment.getRequiredProperty("lguplus.lgd_window_type");	//결제창 호출 방식 (수정불가)
		String LGD_OSTYPE_CHECK  = environment.getRequiredProperty("lguplus.lgd_ostype_check");	//값 P: XPay 실행(PC 결제 모듈): PC용과 모바일용 모듈은 파라미터 및 프로세스가 다르므로 PC용은 PC 웹브라우저에서 실행 필요. 
																																			//"P", "M" 외의 문자(Null, "" 포함)는 모바일 또는 PC 여부를 체크하지 않음
		//String LGD_ACTIVEXYN		= "N";																					//계좌이체 결제시 사용, ActiveX 사용 여부로 "N" 이외의 값: ActiveX 환경에서 계좌이체 결제 진행(IE)
		
		//사용자 환경
		String userAgent = request.getHeader("user-agent");
		boolean mobile1 = userAgent.matches(".*(iPhone|iPad|Android|Windows CE|BlackBerry|Symbian|Windows Phone|webOS|Opera Mini|POLATIS|IEMobile|lgtelecom|mokia|SonyEricsson).*");
		boolean mobile2 = userAgent.matches(".*(LG|SAMSUNG|Samsung).*");
		if(mobile1 || mobile2){
			LGD_OSTYPE_CHECK = "M";
		}else{
			LGD_OSTYPE_CHECK = "P";
		}
		
	    
	    /*
	     * 가상계좌(무통장) 결제 연동을 하시는 경우 아래 LGD_CASNOTEURL 을 설정하여 주시기 바랍니다.
	     */
	    String LGD_CASNOTEURL		= environment.getRequiredProperty("lguplus.lgd_casnoteurl");

	    /*
	     * LGD_RETURNURL 을 설정하여 주시기 바랍니다. 반드시 현재 페이지와 동일한 프로트콜 및  호스트이어야 합니다. 아래 부분을 반드시 수정하십시요.
	     */
	    String LGD_RETURNURL		= environment.getRequiredProperty("lguplus.lgd_returnurl");			// FOR MANUAL

	    
	    /*
	     *************************************************
	     * 2. MD5 해쉬암호화 (수정하지 마세요) - BEGIN
	     *
	     * MD5 해쉬암호화는 거래 위변조를 막기위한 방법입니다.
	     *************************************************
	     *
	     * 해쉬 암호화 적용( LGD_MID + LGD_OID + LGD_AMOUNT + LGD_TIMESTAMP + LGD_MERTKEY )
	     * LGD_MID          : 상점아이디
	     * LGD_OID          : 주문번호
	     * LGD_AMOUNT       : 금액
	     * LGD_TIMESTAMP    : 타임스탬프
	     * LGD_MERTKEY      : 상점MertKey (mertkey는 상점관리자 -> 계약정보 -> 상점정보관리에서 확인하실수 있습니다)
	     *
	     * MD5 해쉬데이터 암호화 검증을 위해
	     * LG유플러스에서 발급한 상점키(MertKey)를 환경설정 파일(lgdacom/conf/mall.conf)에 반드시 입력하여 주시기 바랍니다.
	     */
	    StringBuffer sb = new StringBuffer();
	    sb.append(LGD_MID);
	    sb.append(LGD_OID);
	    sb.append(LGD_AMOUNT);
	    sb.append(LGD_TIMESTAMP);
	    sb.append(LGD_MERTKEY);

	    byte[] bNoti = sb.toString().getBytes();
	    MessageDigest md = MessageDigest.getInstance("MD5");
	    byte[] digest = md.digest(bNoti);

	    StringBuffer strBuf = new StringBuffer();
	    for (int i=0 ; i < digest.length ; i++) {
	        int c = digest[i] & 0xff;
	        if (c <= 15){
	            strBuf.append("0");
	        }
	        strBuf.append(Integer.toHexString(c));
	    }

	    String LGD_HASHDATA = strBuf.toString();
	    String LGD_CUSTOM_PROCESSTYPE = "TWOTR";
	    /*
	     *************************************************
	     * 2. MD5 해쉬암호화 (수정하지 마세요) - END
	     *************************************************
	     */
	  	
	    Map payReqMap = new HashMap();
	    payReqMap.put("CST_PLATFORM"							, CST_PLATFORM);                   		// 테스트, 서비스 구분
	    payReqMap.put("CST_MID"                     				, CST_MID );                        			// 상점아이디
	    payReqMap.put("LGD_WINDOW_TYPE"           		, LGD_WINDOW_TYPE );              	// 결제창호출 방식(수정불가)
	    payReqMap.put("LGD_MID"                     				, LGD_MID );                        			// 상점아이디
	    payReqMap.put("LGD_OID"                     				, LGD_OID );                        			// 주문번호
	    payReqMap.put("LGD_BUYER"                   				, LGD_BUYER );                      			// 구매자
	    payReqMap.put("LGD_PRODUCTINFO"            			, LGD_PRODUCTINFO );                	// 상품정보
	    payReqMap.put("LGD_AMOUNT"                  			, LGD_AMOUNT );                     		// 결제금액
	    payReqMap.put("LGD_BUYEREMAIL"						, LGD_BUYEREMAIL );                 		// 구매자 이메일
	    payReqMap.put("LGD_CUSTOM_SKIN"             		, LGD_CUSTOM_SKIN );                	// 결제창 SKIN
	    payReqMap.put("LGD_CUSTOM_PROCESSTYPE"		, LGD_CUSTOM_PROCESSTYPE );   	// 트랜잭션 처리방식
	    payReqMap.put("LGD_TIMESTAMP"               			, LGD_TIMESTAMP );                  		// 타임스탬프
	    payReqMap.put("LGD_HASHDATA"                			, LGD_HASHDATA );      	           		// MD5 해쉬암호값
	    payReqMap.put("LGD_RETURNURL"   						, LGD_RETURNURL );      					// 응답수신페이지
	    payReqMap.put("LGD_CUSTOM_USABLEPAY"  			, LGD_CUSTOM_USABLEPAY );			// 디폴트 결제수단 (해당 필드를 보내지 않으면 결제수단 선택 UI 가 보이게 됩니다.)
	    payReqMap.put("LGD_CUSTOM_SWITCHINGTYPE"  	, LGD_CUSTOM_SWITCHINGTYPE );	// 신용카드 카드사 인증 페이지 연동 방식
	    payReqMap.put("LGD_WINDOW_VER"  					, LGD_WINDOW_VER );					// 결제창 버젼정보 
	    payReqMap.put("LGD_OSTYPE_CHECK"            		, LGD_OSTYPE_CHECK);               	// 값 P: XPay 실행(PC용 결제 모듈), PC, 모바일 에서 선택적으로 결제가능 
		//payReqMap.put("LGD_ACTIVEXYN"			    		, LGD_ACTIVEXYN);							// 계좌이체 결제시 사용, ActiveX 사용 여부
		payReqMap.put("LGD_VERSION"         					, "JSP_Non-ActiveX_Standard");			// 사용타입 정보(수정 및 삭제 금지): 이 정보를 근거로 어떤 서비스를 사용하는지 판단할 수 있습니다.

	    // 가상계좌(무통장) 결제연동을 하시는 경우  할당/입금 결과를 통보받기 위해 반드시 LGD_CASNOTEURL 정보를 LG 유플러스에 전송해야 합니다 .
	    payReqMap.put("LGD_CASNOTEURL"          				, LGD_CASNOTEURL );               		// 가상계좌 NOTEURL

	    /*Return URL에서 인증 결과 수신 시 셋팅될 파라미터 입니다.*/
		payReqMap.put("LGD_RESPCODE"		, "" );
		payReqMap.put("LGD_RESPMSG"  		, "" );
		payReqMap.put("LGD_PAYKEY"  		, "" );

		payReqMap.put("LGD_ENCODING"  		 			, "UTF-8" );
		payReqMap.put("LGD_ENCODING_RETURNURL"  	, "UTF-8" );
		

		//session.setAttribute("PAYREQ_MAP", payReqMap);
		model.addAttribute("PAYREQ_MAP"							, payReqMap);
		model.addAttribute("CST_MID"									, CST_MID);
		model.addAttribute("CST_PLATFORM"							, CST_PLATFORM);
		model.addAttribute("LGD_OID"									, LGD_OID);
		model.addAttribute("LGD_MERTKEY"							, LGD_MERTKEY);
		model.addAttribute("LGD_AMOUNT"							, LGD_AMOUNT);
		model.addAttribute("LGD_BUYER"								, LGD_BUYER);
		model.addAttribute("LGD_PRODUCTINFO"					, LGD_PRODUCTINFO);
		model.addAttribute("LGD_BUYEREMAIL"						, LGD_BUYEREMAIL);
		model.addAttribute("LGD_TIMESTAMP"						, LGD_TIMESTAMP);
		model.addAttribute("LGD_CUSTOM_USABLEPAY"			, LGD_CUSTOM_USABLEPAY);
		model.addAttribute("LGD_CUSTOM_SWITCHINGTYPE"	, LGD_CUSTOM_SWITCHINGTYPE);
		model.addAttribute("LGD_WINDOW_TYPE"					, LGD_WINDOW_TYPE);
		
		model.addAttribute("tb_odinfoxm", rtnOdinfoxm);
		model.addAttribute("tb_oddlaixm", rtnOddlaixm);
		
		return new ModelAndView("popup.layout", "jsp", "mall/order/payReq");		
	}
	
	/**
	 * 인증요청 응답 화면 (
	 * @param resv
	 * @param request
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/payReturn", method=RequestMethod.POST)
	public  ModelAndView payReturn(@ModelAttribute TB_ODINFOXM tb_odinfoxm, HttpServletRequest request, HttpSession session, Model model) throws Exception {
	
		logger.info("LGD_RESPCODE>>"+tb_odinfoxm.getLGD_RESPCODE());
		logger.info("LGD_RESPMSG>>"+tb_odinfoxm.getLGD_RESPMSG());
		logger.info("LGD_RESPCODE>>"+request.getParameter("LGD_RESPCODE"));
		logger.info("LGD_RESPMSG>>"+request.getParameter("LGD_RESPMSG"));
		
		request.setCharacterEncoding("euc-kr");

		logger.info("LGD_RESPCODE11>>"+request.getParameter("LGD_RESPCODE"));
		logger.info("LGD_RESPMSG11>>"+request.getParameter("LGD_RESPMSG"));
				
		String LGD_RESPCODE	= request.getParameter("LGD_RESPCODE");
		String LGD_RESPMSG	= request.getParameter("LGD_RESPMSG");
		Map payReqMap = request.getParameterMap();

		model.addAttribute("LGD_RESPCODE"	, LGD_RESPCODE);
		model.addAttribute("LGD_RESPMSG"	,  LGD_RESPMSG);
		model.addAttribute("payReqMap"		, payReqMap);
		
		request.setAttribute("payReqMap", payReqMap);

		return new ModelAndView("blankPage", "jsp", "mall/order/payReturn");
	}
	

	/**
	 * 최종결제요청 및 결제결과 처리
	 * @param resv
	 * @param request
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/payRes", method=RequestMethod.POST)
	public  ModelAndView payRes( HttpServletRequest request, HttpSession session, Model model) throws Exception {
		boolean bSatus = false;
		
		/*
		 * LG유플러스에서 제공한 환경파일("/conf/lgdacom.conf,/conf/mall.conf") 위치 지정.  
		 */
		String configPath = environment.getRequiredProperty("lguplus.configPath");	//"C:/lgdacom";
				
		String CST_PLATFORM				= request.getParameter("CST_PLATFORM");
	    String CST_MID						= request.getParameter("CST_MID");
	    String LGD_MID						= ("test".equals(CST_PLATFORM.trim())?"t":"")+CST_MID;
	    String LGD_PAYKEY					= request.getParameter("LGD_PAYKEY");
	    
	    /*
	     * 해당 API를 사용하기 위해 WEB-INF/lib/XPayClient.jar 를 Classpath 로 등록하셔야 합니다.
	     * (1) XpayClient의 사용을 위한 xpay 객체 생성
	     */
	    XPayClient xpay = new XPayClient();

		/*
		 *  (2) Init: XPayClient 초기화(환경설정 파일 로드)
		 *  configPath: 설정파일 
		 *  CST_PLATFORM: - test, service 값에 따라 lgdacom.conf의 test_url(test) 또는 url(srvice) 사용
		 *  						- test, service 값에 따라 테스트용 또는 서비스용 아이디 생성
		 */
	   	boolean isInitOK = xpay.Init(configPath, CST_PLATFORM);
	   	
	   	Map<String,String> payResMap = new HashMap<String,String>();
	   	
	   	if( !isInitOK ) {
	    	//API 초기화 실패 화면처리
	        payResMap.put("msg01" , "결제요청을 초기화 하는데 실패하였습니다.<br>"
	        		+ "LG유플러스에서 제공한 환경파일이 정상적으로 설치 되었는지 확인하시기 바랍니다.<br>"
	        		+ "mall.conf에는 Mert ID = Mert Key 가 반드시 등록되어 있어야 합니다.<br><br>"
	        		+ "문의전화 LG유플러스 1544-7772<br>");
	        model.addAttribute("payResMap", payResMap);
	        return new ModelAndView("mallNew.layout", "jsp", "mall/order/payRes");
	   	
	   	}else{      
	   		try{	   			
				// (3) Init_TX: 메모리에 mall.conf, lgdacom.conf 할당 및 트랜잭션의 고유한 키 TXID 생성
		    	xpay.Init_TX(LGD_MID);
		    	xpay.Set("LGD_TXNAME", "PaymentByKey");
		    	xpay.Set("LGD_PAYKEY", LGD_PAYKEY);
		    
		    	//금액을 체크하시기 원하는 경우 아래 주석을 풀어서 이용하십시요.
		    	//String DB_AMOUNT = "DB나 세션에서 가져온 금액"; //반드시 위변조가 불가능한 곳(DB나 세션)에서 금액을 가져오십시요.
		    	//xpay.Set("LGD_AMOUNTCHECKYN", "Y");
		    	//xpay.Set("LGD_AMOUNT", DB_AMOUNT);
		    
	    	}catch(Exception e) {
	    		payResMap.put("msg01" , "LG유플러스 제공 API를 사용할 수 없습니다. 환경파일 설정을 확인해 주시기 바랍니다.");
	    		payResMap.put("msg02" , ""+e.getMessage());
		        model.addAttribute("payResMap", payResMap);
		        return new ModelAndView("mallNew.layout", "jsp", "mall/order/payRes");
	    	}
	   	}
	   	
	   	/*
		 *************************************************
		 * 1.최종결제 요청(수정하지 마세요) - END
		 *************************************************
		 */

	    /*
	     * 2. 최종결제 요청 결과처리
	     *
	     * 최종 결제요청 결과 리턴 파라미터는 연동메뉴얼을 참고하시기 바랍니다.
	     */
		
        //(4) TX: lgdacom.conf에 설정된 URL로 소켓 통신하여 최종 인증요청, 결과값으로 true, false 리턴
	    if ( xpay.TX() ) {
	        //1)결제결과 화면처리(성공,실패 결과 처리를 하시기 바랍니다.)
	        payResMap.put("msg01" , "결제요청이 완료되었습니다.  <br>"
	        		+ "TX 결제요청 통신 응답코드 = " 	+ xpay.m_szResCode + "<br>"    //통신 응답코드("0000" 일 때 통신 성공)
	        		+ "TX 결제요청 통신 응답메시지 = " + xpay.m_szResMsg + "<p>");  //통신 응답메시지
	        
    		payResMap.put("msg02" , 
    				  "거래번호 : " 		+ xpay.Response("LGD_TID",0) + "<br>"
    				+ "상점아이디 : " 	+ xpay.Response("LGD_MID",0) + "<br>"
    				+ "상점주문번호 : " 	+ xpay.Response("LGD_OID",0) + "<br>"
    				+ "결제금액 : " 		+ xpay.Response("LGD_AMOUNT",0) + "<br>"
    				+ "결과코드 : " 		+ xpay.Response("LGD_RESPCODE",0) + "<br>"   //LGD_RESPCODE 가 반드시 "0000" 일때만 결제 성공, 그 외는 모두 실패
    				+ "결과메세지 : " 	+ xpay.Response("LGD_RESPMSG",0) + "<p>");
	            		
    		StringBuffer sb = new StringBuffer();    		
	        for (int i = 0; i < xpay.ResponseNameCount(); i++) {	        	
	        	sb.append(xpay.ResponseName(i) + " = ");
	            
	            for (int j = 0; j < xpay.ResponseCount(); j++) {
	            	sb.append("\t" + xpay.Response(xpay.ResponseName(i), j) + "<br>");
	            }
	        }
	        sb.append("<p>");
	        payResMap.put("msg03" , sb.toString());
	        
			// (5) DB에 인증요청 결과 처리
	        if( "0000".equals( xpay.m_szResCode ) ) {
	        	// 통신상의 문제가 없을시
	        	// 최종결제요청 결과 성공 DB처리(LGD_RESPCODE 값에 따라 결제가 성공인지, 실패인지 DB처리)	        	
	        	payResMap.put("msg04" , "최종결제요청 성공, DB처리하시기 바랍니다.<br>");
	        	
	    		String LGD_PAYTYPE = xpay.Response("LGD_PAYTYPE",0);
	    		
	        	//1. resv 업데이트 및 MIS 송부 처리
//	        	resv.setResvState("5001");
//	        	resv.setPayYn("Y");
//	        	resv.setInDMLUserId(myInfo.getUserId());
	        	
	        	TB_ODINFOXM tb_odinfoxm = new TB_ODINFOXM();
	    		
	    		tb_odinfoxm.setORDER_NUM(xpay.Response("LGD_OID",0));
	    		tb_odinfoxm.setORDER_CON("ORDER_CON_02");				//결제완료
	    		tb_odinfoxm.setPAY_METD(LGD_PAYTYPE);
	    		tb_odinfoxm.setPAY_MDKY(xpay.Response("LGD_TID",0));
	    		tb_odinfoxm.setPAY_AMT(xpay.Response("LGD_AMOUNT",0));

	        	boolean isDBOK = false;
	        	
	        	if( orderService.orderPayUpdate(tb_odinfoxm) > 0 ) {
	        		isDBOK = true;
	        	}
	         	            	
	         	//최종결제요청 결과를 DB처리합니다. (결제성공 또는 실패 모두 DB처리 가능)
				//상점내 DB에 어떠한 이유로 처리를 하지 못한경우 false로 변경해 주세요.
	         	 
	         	if( !isDBOK ) {
					 
	         		xpay.Rollback("상점 DB처리 실패로 인하여 Rollback 처리 [TID:" +xpay.Response("LGD_TID",0)+",MID:" + xpay.Response("LGD_MID",0)+",OID:"+xpay.Response("LGD_OID",0)+"]");
	         		
	         		payResMap.put("msg05" , "TX Rollback Response_code = " + xpay.Response("LGD_RESPCODE",0) + "<br>");
	         		payResMap.put("msg06" , "TX Rollback Response_msg = " + xpay.Response("LGD_RESPMSG",0) + "<p>");
	         		
					if( "0000".equals( xpay.m_szResCode ) ) { 
						payResMap.put("msg07" , "자동취소가 정상적으로 완료 되었습니다.<br>");
					}else{
						payResMap.put("msg07" , "자동취소가 정상적으로 처리되지 않았습니다.<br>");
					}
	         	}else{
	         		bSatus = true;
	         	}
	         	
	        }else{
				//통신상의 문제 발생(최종결제요청 결과 실패 DB처리)
	        	payResMap.put("msg08" , "최종결제요청 결과 실패, DB처리하시기 바랍니다.<br>");           	
	        }
	    }else {
	         //2)API 요청실패 화면처리
	    	payResMap.put("msg09" , "결제요청이 실패하였습니다.  <br>");
	    	payResMap.put("msg10" , "TX 결제요청 Response_code = " + xpay.m_szResCode + "<br>");      
	    	payResMap.put("msg11" , "TX 결제요청 Response_msg = " + xpay.m_szResMsg + "<p>");      
	    	    
	     	//최종결제요청 결과 실패 DB처리
	    	payResMap.put("msg12" , "최종결제요청 결과 실패 DB처리하시기 바랍니다.<br>");      	            
	    }

	    model.addAttribute("payResMap", payResMap);

	    
		ModelAndView mav = new ModelAndView();
		mav.addObject("payResMap", payResMap);
		
		if(bSatus){
			mav.addObject("alertMessage", "결제가 정상적으로 처리 되었습니다. 배송/주문조회 페이지로 이동합니다.");
		}else{
			mav.addObject("alertMessage", "결제 처리중 에러가 발생했습니다. 같은 에러가 계속 발생시 관리자에게 문의하세요.");	
		}
		
		mav.addObject("returnUrl", servletContextPath.concat("/m/order/payRes"));
		mav.setViewName("alertMessage");
		
		return mav;
		
		//mav.addObject("returnUrl", "parentclose");
		//mav.setViewName("alertMessage");
		//return new ModelAndView("mallNew.layout", "jsp", "mall/order/payRes");		
	}
	
	/**
	 * 인증요청 응답 화면 (
	 * @param resv
	 * @param request
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/payRes", method=RequestMethod.GET)
	public  ModelAndView payReturnGet(@ModelAttribute TB_ODINFOXM tb_odinfoxm, HttpServletRequest request, HttpSession session, Model model) throws Exception {
		//팝업창 닫기위한 함수.....
		request.setCharacterEncoding("euc-kr");
		model.addAttribute("payReturnFlag","justget");

		return new ModelAndView("mallNew.layout", "jsp", "mall/order/payReq");
	}
	
	/**
	 * ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
	 * @Class	: mall.web.controller.mall.OrderController.java
	 * @Method	: edit
	 * ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
	 * @Desc	: 주문내역 상세
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
	@RequestMapping(value={ "/view/{ORDER_NUM}" }, method=RequestMethod.GET)
	public ModelAndView view(@ModelAttribute TB_ODINFOXM tb_odinfoxm, HttpServletRequest request, Model model) throws Exception {
		//주문정보 마스터 정보
		tb_odinfoxm = (TB_ODINFOXM)orderService.getMasterInfo(tb_odinfoxm);
		//주문정보 상세  
		tb_odinfoxm.setList(orderService.getDetailsList(tb_odinfoxm));
		//배송지 정보
		TB_ODDLAIXM tb_oddlaixm = new TB_ODDLAIXM();
		tb_oddlaixm = (TB_ODDLAIXM)orderService.getDeliveryInfo(tb_odinfoxm);
		
		model.addAttribute("tb_odinfoxm", tb_odinfoxm);
		model.addAttribute("tb_oddlaixm", tb_oddlaixm);
		
		return new ModelAndView("mall.responsive.layout", "jsp", "order/view");
	}

	/**
	 * ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
	 * @Class	: mall.web.controller.mall.OrderController.java
	 * @Method	: edit
	 * ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
	 * @Desc	: 주소검색
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
	@RequestMapping(value={ "/deliveryAddr" }, method=RequestMethod.POST)
	public @ResponseBody TB_ODDLAIXM deliveryAddr(@ModelAttribute TB_ODDLAIXM tb_oddlaixm, Model model, HttpServletRequest request) throws Exception {		
		//System.out.println(tb_oddlaixm.getDLAR_GUBN());		
		String dlarGubn = tb_oddlaixm.getDLAR_GUBN();

		TB_MBINFOXM loginUser = (TB_MBINFOXM)request.getSession().getAttribute("USER");
		tb_oddlaixm.setREGP_ID(loginUser.getMEMB_ID());
		
		/*
		  DLAR_GUBN_01 자택
		  DLAR_GUBN_02 회사
		  DLAR_GUBN_03 최근배송지
		  DLAR_GUBN_04 신규
		*/
		
		TB_ODDLAIXM obj = new TB_ODDLAIXM();
		
		if(dlarGubn.equals("DLAR_GUBN_01")){
			obj = (TB_ODDLAIXM)orderService.getMbDlvyInfo(tb_oddlaixm);
		}else if(dlarGubn.equals("DLAR_GUBN_02")){
			obj = (TB_ODDLAIXM)orderService.getSpDlvyInfo(tb_oddlaixm);
		}else if(dlarGubn.equals("DLAR_GUBN_03")){
			obj = (TB_ODDLAIXM)orderService.getDlvyInfo(tb_oddlaixm);
		}else if(dlarGubn.equals("DLAR_GUBN_05")){
			obj = (TB_ODDLAIXM)orderService.getSfDlvyInfo(tb_oddlaixm);
		}else if(dlarGubn.equals("DLAR_GUBN_06")){
			obj = (TB_ODDLAIXM)orderService.getCtDlvyInfo(tb_oddlaixm);
		}else{
			
		}
		
		if(obj == null){
			obj = new TB_ODDLAIXM();
		}
		
		obj.setDLAR_GUBN(dlarGubn);
		
		return obj;
	}
	
	/**
	 * 주문취소 팝업 
	 * @param tb_odinfoxm
	 * @param request
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value={ "/cancel/popup" }, method=RequestMethod.GET)
	public ModelAndView cancelPop(@ModelAttribute TB_ODINFOXM tb_odinfoxm, HttpServletRequest request, Model model) throws Exception {
		
		TB_MBINFOXM loginUser = (TB_MBINFOXM)request.getSession().getAttribute("USER");
		tb_odinfoxm.setREGP_ID(loginUser.getMEMB_ID());
		
		request.getParameter("ORDER_NUM");
		
		return new ModelAndView("popup.layout", "jsp", "responsiveMall/order/popup");
	}
	
	
	/**
	 * 주문취소
	 * @param tb_odinfoxm
	 * @param request
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value={ "/cancel/popup" }, method=RequestMethod.POST)
	public ModelAndView orderCancel(@ModelAttribute TB_ODINFOXM tb_odinfoxm, HttpServletRequest request, Model model) throws Exception {

		ModelAndView mav = new ModelAndView();
		
		TB_MBINFOXM loginUser = (TB_MBINFOXM)request.getSession().getAttribute("USER");
		tb_odinfoxm.setREGP_ID(loginUser.getMEMB_ID());

		//결제 정보가 있을경우
		if(StringUtils.isNotEmpty(tb_odinfoxm.getPAY_MDKY())){

		    /*
		     * [결제취소 요청 페이지]
		     *
		     * LG유플러스으로 부터 내려받은 거래번호(LGD_TID)를 가지고 취소 요청을 합니다.(파라미터 전달시 POST를 사용하세요)
		     * (승인시 LG유플러스으로 부터 내려받은 PAYKEY와 혼동하지 마세요.)
		     */
		    String CST_PLATFORM		= environment.getRequiredProperty("lguplus.cst_platform");		//LG유플러스 결제서비스 선택(test:테스트, service:서비스)
		    String CST_MID				= environment.getRequiredProperty("lguplus.cst_mid");			//LG유플러스으로 부터 발급받으신 상점아이디를 입력하세요.
		    String LGD_MID				= ("test".equals(CST_PLATFORM.trim())?"t":"")+CST_MID;  		//테스트 아이디는 't'를 제외하고 입력하세요.
		    																															//상점아이디(자동생성)
		    String LGD_TID				= tb_odinfoxm.getPAY_MDKY();                      						//LG유플러스으로 부터 내려받은 거래번호(LGD_TID)

			/* 
			 * ※ 중요
			 * 환경설정 파일의 경우 반드시 외부에서 접근이 가능한 경로에 두시면 안됩니다.
			 * 해당 환경파일이 외부에 노출이 되는 경우 해킹의 위험이 존재하므로 반드시 외부에서 접근이 불가능한 경로에 두시기 바랍니다. 
			 * 예) [Window 계열] C:\inetpub\wwwroot\lgdacom ==> 절대불가(웹 디렉토리)
			 */
		    String configPath 			= environment.getRequiredProperty("lguplus.configPath");	//LG유플러스에서 제공한 환경파일("/conf/lgdacom.conf") 위치 지정.
		        
		    LGD_TID     					= ( LGD_TID == null )?"":LGD_TID; 
		    
		    XPayClient xpay = new XPayClient();
		    xpay.Init(configPath, CST_PLATFORM);
		    xpay.Init_TX(LGD_MID);
		    xpay.Set("LGD_TXNAME", "Cancel");
		    xpay.Set("LGD_TID", LGD_TID);
		 
		    /*
		     * 1. 결제취소 요청 결과처리
		     *
		     * 취소결과 리턴 파라미터는 연동메뉴얼을 참고하시기 바랍니다.
			 *
			 * [[[중요]]] 고객사에서 정상취소 처리해야할 응답코드
			 * 1. 신용카드 : 0000, AV11  
			 * 2. 계좌이체 : 0000, RF00, RF10, RF09, RF15, RF19, RF23, RF25 (환불진행중 응답건-> 환불결과코드.xls 참고)
			 * 3. 나머지 결제수단의 경우 0000(성공) 만 취소성공 처리
			 *
		     */
		    if (xpay.TX()) {
		        //1)결제취소결과 화면처리(성공,실패 결과 처리를 하시기 바랍니다.)
		    	mav.addObject("alertMessage", "결제 취소요청이 완료되었습니다.");	 
				tb_odinfoxm.setORDER_CON("ORDER_CON_04");
				
				orderService.cancelObject(tb_odinfoxm);
				
		    }else {
		        //2)API 요청 실패 화면처리
		    	mav.addObject("alertMessage", "결제 취소요청이 실패하였습니다.\n  - TX Response_code = " + xpay.m_szResCode + " - \nTX Response_msg = " + xpay.m_szResMsg);
		    }		    
		}else{
	    	mav.addObject("alertMessage", "결제 취소요청이 완료되었습니다.");

			tb_odinfoxm.setORDER_CON("ORDER_CON_04");
			orderService.cancelObject(tb_odinfoxm);
		}
		
		mav.addObject("gubun", "popup");
		mav.addObject("returnUrl", servletContextPath.concat("/m/order/view/" + tb_odinfoxm.getORDER_NUM()));
		mav.setViewName("alertMessage");

		return mav;		
	}
	
	/**
	 * 주문내역 삭제
	 * @param tb_odinfoxm
	 * @param request
	 * @param model
	 * @param arrayParams
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value={ "/updateDelete" }, method=RequestMethod.GET)
	public ModelAndView updateDelete(@ModelAttribute TB_ODINFOXM tb_odinfoxm, HttpServletRequest request, Model model
			,@RequestParam(value="checkArray[]", defaultValue="") List<String> arrayParams) throws Exception {
				
		for(int i=0;i<arrayParams.size();i++){	//여기서 업데이트
			String num = arrayParams.get(i);
			orderService.orderUpdateDelete(num);		
		}
		
		return new ModelAndView("mallNew.layout", "jsp", "mall/order/list");
	}
	

	/**
	 * 장바구니에 저장
	 * @param tb_odinfoxd
	 * @param model
	 * @param request
	 * @throws Exception
	 */
	@RequestMapping(value="/insertBsktAjax", method=RequestMethod.POST)
	public void insertBsktAjax(@ModelAttribute TB_ODINFOXD tb_odinfoxd, Model model, HttpServletRequest request) throws Exception {

		TB_MBINFOXM loginUser = (TB_MBINFOXM)request.getSession().getAttribute("USER");

		for(int i=0; i<tb_odinfoxd.getPD_CODES().length; i++){
			
			TB_BKINFOXM tb_bkinfoxm = new TB_BKINFOXM();
			tb_bkinfoxm.setREGP_ID(loginUser.getMEMB_ID());
			tb_bkinfoxm.setPD_CODE(tb_odinfoxd.getPD_CODES()[i]);
			tb_bkinfoxm.setPD_QTY(tb_odinfoxd.getORDER_QTYS()[i]);
			
			int cnt = productService.basketSelect(tb_bkinfoxm);
			
			if(cnt <= 0 ){
				//if(tb_odinfoxd.getPD_CUT_SEQS().length==0||tb_odinfoxd.getPD_CUT_SEQS()[i]==null||tb_odinfoxd.getPD_CUT_SEQS()[i].equals("")){
					tb_bkinfoxm.setPD_CUT_SEQ("");
				//}else{
				//	tb_bkinfoxm.setPD_CUT_SEQ(tb_odinfoxd.getPD_CUT_SEQS()[i]);
				//}
				productService.basketInst(tb_bkinfoxm);
				cnt = 0;
			}
		}
	}
		
	/**
	 * 무통장 결제
	 * @param tb_odinfoxm
	 * @param tb_odinfoxd
	 * @param tb_oddlaixm
	 * @param model
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/orderPayCash")//, method=RequestMethod.POST)
	public ModelAndView orderPayCash(@ModelAttribute TB_ODINFOXM tb_odinfoxm, @ModelAttribute TB_ODINFOXD tb_odinfoxd, @ModelAttribute TB_ODDLAIXM tb_oddlaixm, Model model, HttpServletRequest request) throws Exception {

		TB_MBINFOXM loginUser = (TB_MBINFOXM)request.getSession().getAttribute("USER");
		tb_odinfoxm.setREGP_ID(loginUser.getMEMB_ID());
		tb_odinfoxd.setREGP_ID(loginUser.getMEMB_ID());
		tb_oddlaixm.setREGP_ID(loginUser.getMEMB_ID());
		//orderService.insertOrderObject(tb_odinfoxm, tb_odinfoxd, tb_oddlaixm);
		
		/*
		logger.info("lguplus.cst_platform >> " + environment.getProperty("lguplus.cst_platform"));
		logger.info("lguplus.cst_mid >> " + environment.getProperty("lguplus.cst_mid"));
		logger.info("lguplus.lgd_martkey >> " + environment.getRequiredProperty("lguplus.lgd_martkey"));
		logger.info("lguplus.lgd_custom_skin >> " + environment.getRequiredProperty("lguplus.lgd_custom_skin"));
		logger.info("lguplus.lgd_window_ver >> " + environment.getRequiredProperty("lguplus.lgd_window_ver"));
		logger.info("lguplus.lgd_window_type >> " + environment.getRequiredProperty("lguplus.lgd_window_type"));
		logger.info("lguplus.lgd_ostype_check >> " + environment.getRequiredProperty("lguplus.lgd_ostype_check"));
		logger.info("lguplus.lgd_casnoteurl >> " + environment.getRequiredProperty("lguplus.lgd_casnoteurl"));
		logger.info("lguplus.lgd_returnurl >> " + environment.getRequiredProperty("lguplus.lgd_returnurl"));
		*/
		
	    /*
	     * [결제 인증요청 페이지(STEP2-1)]
	     * 샘플페이지에서는 기본 파라미터만 예시되어 있으며, 별도로 필요하신 파라미터는 연동메뉴얼을 참고하시어 추가 하시기 바랍니다.
	     */

	    /*
	     * 1. 기본결제 인증요청 정보 변경
	     * 기본정보를 변경하여 주시기 바랍니다.(파라미터 전달시 POST를 사용하세요)
	     */
		//농수축산물센터 - cjh3474    f521e1be913b23de7a66b793b90069ec  http://www.amsmall.co.kr/order/payReturn
		
		//주문정보 마스터 정보
		TB_ODINFOXM rtnOdinfoxm = (TB_ODINFOXM)orderService.getMasterInfo(tb_odinfoxm);
		//주문정보 상세  
		rtnOdinfoxm.setList(orderService.getDetailsList(rtnOdinfoxm));
		//배송지 정보
		TB_ODDLAIXM rtnOddlaixm = new TB_ODDLAIXM();
		rtnOddlaixm = (TB_ODDLAIXM)orderService.getDeliveryInfo(rtnOdinfoxm);
		
		//..주문정보 끝
		
		long timestamp = (System.currentTimeMillis() / 1000L) * 1000L;
		
	    String CST_PLATFORM					= environment.getRequiredProperty("lguplus.cst_platform");			//LG유플러스 결제서비스 선택(test:테스트, service:서비스)
	    String CST_MID              				= environment.getRequiredProperty("lguplus.cst_mid");				//LG유플러스로 부터 발급받으신 상점아이디를 입력하세요.
	    String LGD_MID              				= ("test".equals(CST_PLATFORM.trim())?"t":"")+CST_MID;				//테스트 아이디는 't'를 제외하고 입력하세요.
	    																																			//상점아이디(자동생성)
	    String LGD_OID              				= rtnOdinfoxm.getORDER_NUM();												//주문번호(상점정의 유니크한 주문번호를 입력하세요)	//tb_odinfoxm.getORDER_NUM();
	    String LGD_AMOUNT           			= rtnOdinfoxm.getORDER_AMT();												//테스트결제금액("," 를 제외한 결제금액을 입력하세요)	//tb_odinfoxm.getORDER_AMT();
	    
	    String LGD_MERTKEY          			= environment.getRequiredProperty("lguplus.lgd_martkey");			//상점MertKey(mertkey는 상점관리자 -> 계약정보 -> 상점정보관리에서 확인하실수 있습니다)
	    String LGD_BUYER            			= loginUser.getMEMB_ID();														//구매자명
	    String LGD_PRODUCTINFO      		= "쇼핑몰 상품 주문";																		//상품명
	    String LGD_BUYEREMAIL       		= loginUser.getMEMB_MAIL();													//구매자 이메일
	    String LGD_TIMESTAMP        			= ""+timestamp;																		//타임스탬프
	    String LGD_CUSTOM_USABLEPAY 	= "SC0030";																			//상점정의 초기결제수단 - 실시간 계좌이체
	    String LGD_CUSTOM_SKIN      		= environment.getRequiredProperty("lguplus.lgd_custom_skin");	//상점정의 결제창 스킨(red)
	    String LGD_CUSTOM_SWITCHINGTYPE = "IFRAME"; 																		//신용카드 카드사 인증 페이지 연동 방식 (수정불가)
	    String LGD_WINDOW_VER			= environment.getRequiredProperty("lguplus.lgd_window_ver");		//결제창 버젼정보
	    String LGD_WINDOW_TYPE      		= environment.getRequiredProperty("lguplus.lgd_window_type");	//결제창 호출 방식 (수정불가)
		String LGD_OSTYPE_CHECK    	 	= environment.getRequiredProperty("lguplus.lgd_ostype_check");	//값 P: XPay 실행(PC 결제 모듈): PC용과 모바일용 모듈은 파라미터 및 프로세스가 다르므로 PC용은 PC 웹브라우저에서 실행 필요. 
																																					//"P", "M" 외의 문자(Null, "" 포함)는 모바일 또는 PC 여부를 체크하지 않음
		//String LGD_ACTIVEXYN				= "N";																					//계좌이체 결제시 사용, ActiveX 사용 여부로 "N" 이외의 값: ActiveX 환경에서 계좌이체 결제 진행(IE)

	    
	    /*
	     * 가상계좌(무통장) 결제 연동을 하시는 경우 아래 LGD_CASNOTEURL 을 설정하여 주시기 바랍니다.
	     */
	    String LGD_CASNOTEURL		= environment.getRequiredProperty("lguplus.lgd_casnoteurl");

	    /*
	     * LGD_RETURNURL 을 설정하여 주시기 바랍니다. 반드시 현재 페이지와 동일한 프로트콜 및  호스트이어야 합니다. 아래 부분을 반드시 수정하십시요.
	     */
	    String LGD_RETURNURL		= environment.getRequiredProperty("lguplus.lgd_returnurl");			// FOR MANUAL

	    
	    /*
	     *************************************************
	     * 2. MD5 해쉬암호화 (수정하지 마세요) - BEGIN
	     *
	     * MD5 해쉬암호화는 거래 위변조를 막기위한 방법입니다.
	     *************************************************
	     *
	     * 해쉬 암호화 적용( LGD_MID + LGD_OID + LGD_AMOUNT + LGD_TIMESTAMP + LGD_MERTKEY )
	     * LGD_MID          : 상점아이디
	     * LGD_OID          : 주문번호
	     * LGD_AMOUNT       : 금액
	     * LGD_TIMESTAMP    : 타임스탬프
	     * LGD_MERTKEY      : 상점MertKey (mertkey는 상점관리자 -> 계약정보 -> 상점정보관리에서 확인하실수 있습니다)
	     *
	     * MD5 해쉬데이터 암호화 검증을 위해
	     * LG유플러스에서 발급한 상점키(MertKey)를 환경설정 파일(lgdacom/conf/mall.conf)에 반드시 입력하여 주시기 바랍니다.
	     */
	    StringBuffer sb = new StringBuffer();
	    sb.append(LGD_MID);
	    sb.append(LGD_OID);
	    sb.append(LGD_AMOUNT);
	    sb.append(LGD_TIMESTAMP);
	    sb.append(LGD_MERTKEY);

	    byte[] bNoti = sb.toString().getBytes();
	    MessageDigest md = MessageDigest.getInstance("MD5");
	    byte[] digest = md.digest(bNoti);

	    StringBuffer strBuf = new StringBuffer();
	    for (int i=0 ; i < digest.length ; i++) {
	        int c = digest[i] & 0xff;
	        if (c <= 15){
	            strBuf.append("0");
	        }
	        strBuf.append(Integer.toHexString(c));
	    }

	    String LGD_HASHDATA = strBuf.toString();
	    String LGD_CUSTOM_PROCESSTYPE = "TWOTR";
	    /*
	     *************************************************
	     * 2. MD5 해쉬암호화 (수정하지 마세요) - END
	     *************************************************
	     */
	  	
	    Map payReqMap = new HashMap();
	    payReqMap.put("CST_PLATFORM"                			, CST_PLATFORM);                   	// 테스트, 서비스 구분
	    payReqMap.put("CST_MID"                     				, CST_MID );                        		// 상점아이디
	    payReqMap.put("LGD_WINDOW_TYPE"             		, LGD_WINDOW_TYPE );              // 결제창호출 방식(수정불가)
	    payReqMap.put("LGD_MID"                     				, LGD_MID );                        		// 상점아이디
	    payReqMap.put("LGD_OID"                     				, LGD_OID );                        		// 주문번호
	    payReqMap.put("LGD_BUYER"                   				, LGD_BUYER );                      		// 구매자
	    payReqMap.put("LGD_PRODUCTINFO"             		, LGD_PRODUCTINFO );                // 상품정보
	    payReqMap.put("LGD_AMOUNT"                  			, LGD_AMOUNT );                     	// 결제금액
	    payReqMap.put("LGD_BUYEREMAIL"              			, LGD_BUYEREMAIL );                 	// 구매자 이메일
	    payReqMap.put("LGD_CUSTOM_SKIN"             		, LGD_CUSTOM_SKIN );                // 결제창 SKIN
	    payReqMap.put("LGD_CUSTOM_PROCESSTYPE"      	, LGD_CUSTOM_PROCESSTYPE );   // 트랜잭션 처리방식
	    payReqMap.put("LGD_TIMESTAMP"               			, LGD_TIMESTAMP );                  	// 타임스탬프
	    payReqMap.put("LGD_HASHDATA"                			, LGD_HASHDATA );      	           	// MD5 해쉬암호값
	    payReqMap.put("LGD_RETURNURL"   						, LGD_RETURNURL );      				// 응답수신페이지
	    payReqMap.put("LGD_CUSTOM_USABLEPAY"  			, LGD_CUSTOM_USABLEPAY );		// 디폴트 결제수단 (해당 필드를 보내지 않으면 결제수단 선택 UI 가 보이게 됩니다.)
	    payReqMap.put("LGD_CUSTOM_SWITCHINGTYPE"	, LGD_CUSTOM_SWITCHINGTYPE );	// 신용카드 카드사 인증 페이지 연동 방식
	    payReqMap.put("LGD_WINDOW_VER"  					, LGD_WINDOW_VER );				// 결제창 버젼정보 
	    payReqMap.put("LGD_OSTYPE_CHECK"            		, LGD_OSTYPE_CHECK);               // 값 P: XPay 실행(PC용 결제 모듈), PC, 모바일 에서 선택적으로 결제가능 
		//payReqMap.put("LGD_ACTIVEXYN"			    		, LGD_ACTIVEXYN);						// 계좌이체 결제시 사용, ActiveX 사용 여부
		payReqMap.put("LGD_VERSION"         					, "JSP_Non-ActiveX_Standard");		// 사용타입 정보(수정 및 삭제 금지): 이 정보를 근거로 어떤 서비스를 사용하는지 판단할 수 있습니다.

	    // 가상계좌(무통장) 결제연동을 하시는 경우  할당/입금 결과를 통보받기 위해 반드시 LGD_CASNOTEURL 정보를 LG 유플러스에 전송해야 합니다 .
	    payReqMap.put("LGD_CASNOTEURL"          				, LGD_CASNOTEURL );               	// 가상계좌 NOTEURL

	    /* Return URL에서 인증 결과 수신 시 셋팅될 파라미터 입니다. */
		payReqMap.put("LGD_RESPCODE"		, "" );
		payReqMap.put("LGD_RESPMSG"  		, "" );
		payReqMap.put("LGD_PAYKEY"  		, "" );

		payReqMap.put("LGD_ENCODING"  		 			, "UTF-8" );
		payReqMap.put("LGD_ENCODING_RETURNURL"  	, "UTF-8" );
		

		//session.setAttribute("PAYREQ_MAP", payReqMap);
		model.addAttribute("PAYREQ_MAP"						, payReqMap);
		model.addAttribute("CST_MID"								, CST_MID);
		model.addAttribute("CST_PLATFORM"						, CST_PLATFORM);
		model.addAttribute("LGD_OID"								,  LGD_OID);
		model.addAttribute("LGD_MERTKEY"						, LGD_MERTKEY);
		model.addAttribute("LGD_AMOUNT"						, LGD_AMOUNT);
		model.addAttribute("LGD_BUYER"							, LGD_BUYER);
		model.addAttribute("LGD_PRODUCTINFO"				, LGD_PRODUCTINFO);
		model.addAttribute("LGD_BUYEREMAIL"					, LGD_BUYEREMAIL);
		model.addAttribute("LGD_TIMESTAMP"					, LGD_TIMESTAMP);
		model.addAttribute("LGD_CUSTOM_USABLEPAY"		, LGD_CUSTOM_USABLEPAY);
		model.addAttribute("LGD_CUSTOM_SWITCHINGTYPE"	, LGD_CUSTOM_SWITCHINGTYPE);
		model.addAttribute("LGD_WINDOW_TYPE"				, LGD_WINDOW_TYPE);

		model.addAttribute("tb_odinfoxm", rtnOdinfoxm);
		model.addAttribute("tb_oddlaixm", rtnOddlaixm);
		
		return new ModelAndView("popup.layout", "jsp", "mall/order/payReq");		
	}
		
}
