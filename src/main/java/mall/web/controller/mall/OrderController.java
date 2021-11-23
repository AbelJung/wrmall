package mall.web.controller.mall;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
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
import mall.common.util.DanalFunction;
import mall.common.util.DanalFunction2;
import mall.common.util.StringUtil;
import mall.web.controller.DefaultController;
import mall.web.controller.admin.CategoryMgrController;
import mall.web.domain.TB_BKINFOXM;
import mall.web.domain.TB_MBINFOXM;
import mall.web.domain.TB_ODDLAIXM;
import mall.web.domain.TB_ODDNLGXM;
import mall.web.domain.TB_ODINFOXD;
import mall.web.domain.TB_ODINFOXM;
import mall.web.domain.TB_SPINFOXM;
import mall.web.service.admin.impl.SupplierMgrService;
import mall.web.service.mall.OrderService;
import mall.web.service.mall.ProductService;

@Controller
//@RequestMapping(value="/order")
@RequestMapping(value="/orderDanal")
public class OrderController extends DefaultController{
	
	private static final Logger logger = LoggerFactory.getLogger(CategoryMgrController.class);

	@Autowired
	private Environment environment;
	
	@Resource(name="orderService")
	OrderService orderService;

	@Resource(name="supplierMgrService")
	SupplierMgrService supplierMgrService;
	
	@Resource(name="productService")
	ProductService productService;
	
	/**
	 * �봺�봺�봺�봺�봺�봺�봺�봺�봺�봺�봺�봺�봺�봺�봺�봺�봺�봺�봺�봺�봺�봺�봺�봺�봺�봺�봺�봺�봺�봺�봺�봺�봺�봺�봺�봺�봺�봺�봺�봺�봺�봺�봺�봺�봺
	 * @Class	: mall.web.controller.mall.OrderController.java
	 * @Method	: index
	 * �봺�봺�봺�봺�봺�봺�봺�봺�봺�봺�봺�봺�봺�봺�봺�봺�봺�봺�봺�봺�봺�봺�봺�봺�봺�봺�봺�봺�봺�봺�봺�봺�봺�봺�봺�봺�봺�봺�봺�봺�봺�봺�봺�봺�봺
	 * @Desc	: 二쇰Ц �궡�뿭 紐⑸줉
	 * @Company	: YT Corp.
	 * @Author	: Young-dae Kim (sjie1638@youngthink.co.kr)
	 * @Date	: 2016-07-27 (�삤�썑 2:07:41)
	 * �봺�봺�봺�봺�봺�봺�봺�봺�봺�봺�봺�봺�봺�봺�봺�봺�봺�봺�봺�봺�봺�봺�봺�봺�봺�봺�봺�봺�봺�봺�봺�봺�봺�봺�봺�봺�봺�봺�봺�봺�봺�봺�봺�봺�봺
	 * @param tb_odinfoxm
	 * @param model
	 * @return
	 * @throws Exception
	 * �봺�봺�봺�봺�봺�봺�봺�봺�봺�봺�봺�봺�봺�봺�봺�봺�봺�봺�봺�봺�봺�봺�봺�봺�봺�봺�봺�봺�봺�봺�봺�봺�봺�봺�봺�봺�봺�봺�봺�봺�봺�봺�봺�봺�봺
	*/
	@RequestMapping(value="/wishList", method=RequestMethod.GET)
	public ModelAndView index(@ModelAttribute TB_ODINFOXM tb_odinfoxm, HttpServletRequest request, Model model) throws Exception {

		TB_MBINFOXM loginUser = (TB_MBINFOXM)request.getSession().getAttribute("USER");
		if(loginUser==null){
			
			ModelAndView mav = new ModelAndView();
			mav.addObject("alertMessage", "濡쒓렇�씤 �썑 �씠�슜�빐二쇱꽭�슂.");
			mav.addObject("returnUrl", "back");
			mav.setViewName("alertMessage");
			return mav;
			
		}else{
			
			if(request.getParameter("pagerMaxPageItems")!=null){
				tb_odinfoxm.setRowCnt(Integer.parseInt(request.getParameter("pagerMaxPageItems")));	//�럹�씠吏� �떒�쐞 : 50
				tb_odinfoxm.setPagerMaxPageItems(Integer.parseInt(request.getParameter("pagerMaxPageItems")));
			}else {
				tb_odinfoxm.setRowCnt(20);	//�럹�씠吏� �떒�쐞 : 20
				tb_odinfoxm.setPagerMaxPageItems(20);	
			}
			
			
			tb_odinfoxm.setREGP_ID(loginUser.getMEMB_ID());
			tb_odinfoxm.setCount(orderService.getObjectCount(tb_odinfoxm));
			
			//tb_odinfoxm.setMEMB_ID("user");
			tb_odinfoxm.setList(orderService.getObjectList(tb_odinfoxm));
			model.addAttribute("obj", tb_odinfoxm);
			
			model.addAttribute("rowCnt", tb_odinfoxm.getRowCnt());
			model.addAttribute("totalCnt", tb_odinfoxm.getCount());
			
			/*String strLink = null;
			strLink = "&pagerMaxPageItems="+StringUtil.nullCheck(tb_odinfoxm.getPagerMaxPageItems());
	
			model.addAttribute("link", strLink);

			request.getSession().setAttribute("NOTPAYCNT", refreshOrder(loginUser.getMEMB_ID()));*/
			//미결제내역세션
			/*TB_ODINFOXM resultNotPayCnt= new TB_ODINFOXM();
			resultNotPayCnt.setREGP_ID(loginUser.getMEMB_ID());	
			List<TB_ODINFOXM> notPayCnt =(List<TB_ODINFOXM>) orderService.getObjectList(resultNotPayCnt);
			int cnt = 0;
			for(int i=0; i<notPayCnt.size();i++){
				if(notPayCnt.get(i).getORDER_CON().equals("ORDER_CON_01"))
					cnt++;
			}
			request.getSession().setAttribute("NOTPAYCNT",  cnt);*/
	
			
			return new ModelAndView("mallNew.layout", "jsp", "mall/order/list");
		}
	}
	
	@RequestMapping(value="/new", method=RequestMethod.POST)
	public ModelAndView newForm(@ModelAttribute TB_ODINFOXM tb_odinfoxm, Model model) throws Exception {
		
		StringTokenizer stok = new StringTokenizer(tb_odinfoxm.getBSKT_REGNO_LIST(),"$");
		List<String> list = new ArrayList<String>();
		while(stok.hasMoreTokens()){
			String str = stok.nextToken();
			list.add(str);
		}
		
//		StringTokenizer stok2 = new StringTokenizer(tb_odinfoxm.getPD_CUT_SEQ_LIST(),"$");
		
//		List<String> list2 = new ArrayList<String>();
//		while(stok2.hasMoreTokens()){
//			String str2 = stok2.nextToken();
//			list2.add(str2);
//		}
		
		String[] cut_seq_name = tb_odinfoxm.getPD_CUT_SEQ_LIST().split("@@!");
		String[] option_name = tb_odinfoxm.getOPTION_CODE_LIST().split("@@#");
		
		tb_odinfoxm.setList(orderService.getNewObjectList(list));
		model.addAttribute("obj", tb_odinfoxm);
		model.addAttribute("PD_CUT_SEQ", cut_seq_name);
		model.addAttribute("OPTION_CODE", option_name);
		
		
		//諛곗넚鍮� 議곌굔
		TB_SPINFOXM supplierInfo = new TB_SPINFOXM();
		supplierInfo.setSUPR_ID("C00002");
		model.addAttribute("supplierInfo", (TB_SPINFOXM)supplierMgrService.getObject(supplierInfo));
		
		return new ModelAndView("mallNew.layout", "jsp", "mall/order/new");
	}
	
	@RequestMapping(value="/buy")
	public ModelAndView buy(@ModelAttribute TB_ODINFOXD tb_odinfoxm, Model model) throws Exception {

		StringTokenizer stok = new StringTokenizer(tb_odinfoxm.getPD_CODE(),"$");
		List<String> list = new ArrayList<String>();
		while(stok.hasMoreTokens()){
			String str = stok.nextToken();
			list.add(str);
		}
		
		tb_odinfoxm.setList(orderService.getBuyObjectList(list));
		model.addAttribute("obj", tb_odinfoxm);
		
		
		
		//諛곗넚鍮� 議곌굔
		TB_SPINFOXM supplierInfo = new TB_SPINFOXM();
		supplierInfo.setSUPR_ID("C00002");
		model.addAttribute("supplierInfo", (TB_SPINFOXM)supplierMgrService.getObject(supplierInfo));
		
		return new ModelAndView("mallNew.layout", "jsp", "mall/order/new");

	}
	
	@RequestMapping(value="/insert", method=RequestMethod.POST)
	public ModelAndView insert(@ModelAttribute TB_ODINFOXM tb_odinfoxm, @ModelAttribute TB_ODINFOXD tb_odinfoxd, @ModelAttribute TB_ODDLAIXM tb_oddlaixm, Model model, HttpServletRequest request) throws Exception {

		TB_MBINFOXM loginUser = (TB_MBINFOXM)request.getSession().getAttribute("USER");
		tb_odinfoxm.setREGP_ID(loginUser.getMEMB_ID());
		tb_odinfoxd.setREGP_ID(loginUser.getMEMB_ID());
		tb_oddlaixm.setREGP_ID(loginUser.getMEMB_ID());
		
		if(tb_oddlaixm.getDLAR_DATE()!=null&&!tb_oddlaixm.getDLAR_DATE().equals("")){
			if(tb_oddlaixm.getDLAR_DATE().equals("DLAR_DATE_01")){
				
				SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");

		        Calendar c1 = Calendar.getInstance();
		        c1.add(Calendar.DATE, 1);
		        if(c1.get(Calendar.DAY_OF_WEEK)==1){
		        	c1.add(Calendar.DATE, 1);
		        }
		        String strToday = sdf.format(c1.getTime());

		        tb_oddlaixm.setDLAR_DATE(strToday);

			}else{
				SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");

		        Calendar c1 = Calendar.getInstance();
		        if(c1.get(Calendar.DAY_OF_WEEK)==1){
		        	c1.add(Calendar.DATE, 1);
		        }
		        String strToday = sdf.format(c1.getTime());

		        tb_oddlaixm.setDLAR_DATE(strToday);
			}
		}
		
		orderService.insertOrderObject(tb_odinfoxm, tb_odinfoxd, tb_oddlaixm);		
		
		//二쇰Ц�젙蹂� 留덉뒪�꽣 �젙蹂�
		TB_ODINFOXM rtnOdinfoxm = (TB_ODINFOXM)orderService.getMasterInfo(tb_odinfoxm);
		//二쇰Ц�젙蹂� �긽�꽭  
		rtnOdinfoxm.setList(orderService.getDetailsList(rtnOdinfoxm));
		//諛곗넚吏� �젙蹂�
		TB_ODDLAIXM rtnOddlaixm = new TB_ODDLAIXM();
		rtnOddlaixm = (TB_ODDLAIXM)orderService.getDeliveryInfo(rtnOdinfoxm);
		
		model.addAttribute("tb_odinfoxm", rtnOdinfoxm);
		model.addAttribute("tb_oddlaixm", rtnOddlaixm);
		
		//�꽭�뀡蹂�寃�
		TB_ODINFOXM resultNotPayCnt= new TB_ODINFOXM();
		resultNotPayCnt.setREGP_ID(loginUser.getMEMB_ID());	
		List<TB_ODINFOXM> notPayCnt =(List<TB_ODINFOXM>) orderService.getObjectList(resultNotPayCnt);
		int cnt = 0;
		for(int i=0; i<notPayCnt.size();i++){
			if(notPayCnt.get(i).getORDER_CON().equals("ORDER_CON_01"))
				cnt++;
		}
		request.getSession().setAttribute("NOTPAYCNT",  cnt);
		
		//return new ModelAndView("mallNew.layout", "jsp", "mall/order/payReq");
		RedirectView redirectView = new RedirectView(servletContextPath+"/order/view/"+rtnOdinfoxm.getORDER_NUM());
		return new ModelAndView(redirectView);
	}

	@RequestMapping(value="/orderReady")
	public ModelAndView orderPay(@ModelAttribute TB_ODINFOXM tb_odinfoxm, Model model, HttpServletRequest request) throws Exception {
		
		TB_MBINFOXM loginUser = (TB_MBINFOXM)request.getSession().getAttribute("USER");
		//二쇰Ц�젙蹂� 留덉뒪�꽣 �젙蹂�
		TB_ODINFOXM rtnOdinfoxm = (TB_ODINFOXM)orderService.getMasterInfo(tb_odinfoxm);
		//二쇰Ц�젙蹂� �긽�꽭  
		rtnOdinfoxm.setList(orderService.getDetailsList(rtnOdinfoxm));
		//諛곗넚吏� �젙蹂�
		TB_ODDLAIXM rtnOddlaixm = new TB_ODDLAIXM();
		rtnOddlaixm = (TB_ODDLAIXM)orderService.getDeliveryInfo(rtnOdinfoxm);
		
		//�뀒�뒪�듃�뀫�뀫�뀫�뀫�뀫�뀫�뀫�뀫�뀫�뀫�뀫�뀫�뀫�뀫�뀫�뀫�뀫�뀫�뀫�뀫�뀫�뀫�뀫�뀫�뀫�뀫�뀫�뀫�뀫�뀫�뀫�뀫�뀫�뀫�뀫�뀫�뀫�뀫�뀫�뀫�뀫�뀫�뀫�뀫�뀫�뀫�뀫�뀫�뀫�뀫�뀫�뀫�뀫�뀫
		orderService.orderDlvyUpdate(tb_odinfoxm);

		String strItemName = "";
		
		List<?> list = rtnOdinfoxm.getList();
		if(list.size() > 0){
			TB_ODINFOXD odinfoxd = (TB_ODINFOXD)list.get(0);
			if(list.size() == 1){
				strItemName = odinfoxd.getPD_NAME();
			}else{
				strItemName = odinfoxd.getPD_NAME() + " �쇅 " + (list.size()-1) + "嫄�";			
			}
		}
		
		//model.addAttribute("tb_odinfoxm", rtnOdinfoxm);
		//model.addAttribute("tb_oddlaixm", rtnOddlaixm);
		

		String MODE = environment.getProperty("danal.MODE"); 						// service, test
		String DN_CREDIT_URL = environment.getProperty("danal.DN_CREDIT_URL"); 		// 寃곗젣 �꽌踰� �젙�쓽
		String CPID = environment.getProperty("danal.CPID"); 						// CPID
		String CRYPTOKEY = environment.getProperty("danal.CRYPTOKEY");				// �븫�샇�솕Key

		
		DanalFunction danalFun = new DanalFunction(MODE, DN_CREDIT_URL, CPID, CRYPTOKEY);
		
		/*[ �븘�닔 �뜲�씠�꽣 ]***************************************/
		Map REQ_DATA = new HashMap();
		Map RES_DATA = null;

		/******************************************************
		 *  RETURNURL 	: CPCGI�럹�씠吏��쓽 Full URL�쓣 �꽔�뼱二쇱꽭�슂
		 *  CANCELURL 	: BackURL�럹�씠吏��쓽 Full URL�쓣 �꽔�뼱二쇱꽭�슂
		 ******************************************************/
		String RETURNURL = environment.getProperty("danal.RETURNURL");
		String CANCELURL = environment.getProperty("danal.CANCELURL");

		/**************************************************
		 * SubCP �젙蹂�
		 **************************************************/
		REQ_DATA.put("SUBCPID", "");

		/**************************************************
		 * 寃곗젣 �젙蹂�
		 **************************************************/
		REQ_DATA.put("ORDERID", rtnOdinfoxm.getORDER_NUM());
		REQ_DATA.put("AMOUNT", rtnOdinfoxm.getORDER_AMT());
		REQ_DATA.put("CURRENCY", "410");
		REQ_DATA.put("ITEMNAME", strItemName);			//�긽�뭹紐�
		
		//�궗�슜�옄 �솚寃�
		String userAgent = request.getHeader("user-agent");
		boolean mobile1 = userAgent.matches(".*(iPhone|iPad|Android|Windows CE|BlackBerry|Symbian|Windows Phone|webOS|Opera Mini|POLATIS|IEMobile|lgtelecom|mokia|SonyEricsson).*");
		boolean mobile2 = userAgent.matches(".*(LG|SAMSUNG|Samsung).*");
		if(mobile1 || mobile2){
			REQ_DATA.put("USERAGENT", "WM");
		}else{
			REQ_DATA.put("USERAGENT", "PC");
		}
		REQ_DATA.put("OFFERPERIOD", "2015102920151129");

		/**************************************************
		 * 怨좉컼 �젙蹂�
		 **************************************************/
		REQ_DATA.put("USERNAME", (String) loginUser.getMEMB_NAME()); // 援щℓ�옄 �씠由�
		REQ_DATA.put("USERID", (String) loginUser.getMEMB_ID()); // �궗�슜�옄 ID
		REQ_DATA.put("USEREMAIL", (String) loginUser.getMEMB_MAIL()); // �냼蹂대쾿 email�닔�떊泥�

		/**************************************************
		 * URL �젙蹂�
		 **************************************************/
		REQ_DATA.put("CANCELURL", CANCELURL);
		REQ_DATA.put("RETURNURL", RETURNURL);

		/**************************************************
		 * 湲곕낯 �젙蹂�
		 **************************************************/
		REQ_DATA.put("TXTYPE", "AUTH");
		REQ_DATA.put("SERVICETYPE", "DANALCARD");
		REQ_DATA.put("ISNOTI", "N");
		//REQ_DATA.put("BYPASSVALUE", "this=is;a=test;bypass=value"); // BILL�쓳�떟 �삉�뒗 Noti�뿉�꽌 �룎�젮諛쏆쓣 媛�. '&'瑜� �궗�슜�븷 寃쎌슦 媛믪씠 �옒由ш쾶�릺誘�濡� �쑀�쓽.

		System.out.println(REQ_DATA);
		
		RES_DATA = danalFun.CallCredit(REQ_DATA, true);
		
		//if ("0000".equals(RES_DATA.get("RETURNCODE"))) {
		//	
		//}else{
		//
		//}

		String STARTURL = (String) RES_DATA.get("STARTURL");
		String STARTPARAMS = (String) RES_DATA.get("STARTPARAMS");
		
		String RETURNCODE = (String) RES_DATA.get("RETURNCODE");
		String RETURNMSG = (String) RES_DATA.get("RETURNMSG");

		model.addAttribute("STARTURL", STARTURL);
		model.addAttribute("STARTPARAMS", STARTPARAMS);
		
		model.addAttribute("RETURNCODE", RETURNCODE);
		model.addAttribute("RETURNMSG", RETURNMSG);
		
		
		return new ModelAndView("popup.layout", "jsp", "mall/order/orderReady");
		
	}
	
	/**
	 * �씤利앹슂泥� �쓳�떟 �솕硫� (
	 * @param resv
	 * @param request
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/orderReturn")
	public  ModelAndView payReturn(@ModelAttribute TB_ODINFOXM tb_odinfoxm, HttpServletRequest request, HttpSession session, Model model) throws Exception {
			
		String MODE = environment.getProperty("danal.MODE"); 						// service, test
		String DN_CREDIT_URL = environment.getProperty("danal.DN_CREDIT_URL"); 		// 寃곗젣 �꽌踰� �젙�쓽
		String CPID = environment.getProperty("danal.CPID"); 						// CPID
		String CRYPTOKEY = environment.getProperty("danal.CRYPTOKEY");				// �븫�샇�솕Key

		DanalFunction danalFun = new DanalFunction(MODE, DN_CREDIT_URL, CPID, CRYPTOKEY);
		
		
		String RES_STR = danalFun.toDecrypt((String) request.getParameter("RETURNPARAMS"));
		Map retMap = danalFun.str2data(RES_STR);

		String returnCode = (String) retMap.get("RETURNCODE");
		String returnMsg = (String) retMap.get("RETURNMSG");
		
		//*****  �떊�슜移대뱶 �씤利앷껐怨� �솗�씤 *****************
		if (returnCode == null || !"0000".equals(returnCode)) {
			// returnCode媛� �뾾嫄곕굹 �삉�뒗 洹� 寃곌낵媛� �꽦怨듭씠 �븘�땲�씪硫� �떎�뙣 泥섎━
			logger.info("Authentication failed. " + returnMsg + "[" + returnCode + "]");

			ModelAndView mav = new ModelAndView();
			mav.addObject("alertMessage", "�옒紐삳맂 �젒洹� �엯�땲�떎. 愿�由ъ옄�뿉寃� 臾몄쓽�븯�꽭�슂.\n"
							+"Authentication failed. " + returnMsg + "[" + returnCode + "]");
			mav.addObject("returnUrl", "close");
			mav.setViewName("alertMessage");
			return mav;
			
			
		}
		//二쇰Ц�젙蹂� 留덉뒪�꽣 �젙蹂�
		tb_odinfoxm.setORDER_NUM((String) retMap.get("ORDERID"));
		TB_ODINFOXM rtnOdinfoxm = (TB_ODINFOXM)orderService.getMasterInfo(tb_odinfoxm);
		
		/*[ �븘�닔 �뜲�씠�꽣 ]***************************************/
		Map REQ_DATA = new HashMap();
		Map RES_DATA = new HashMap();

		/**************************************************
		 * 寃곗젣 �젙蹂�
		 **************************************************/
		REQ_DATA.put("TID", (String) retMap.get("TID"));
		REQ_DATA.put("AMOUNT", rtnOdinfoxm.getORDER_AMT()); 		// 理쒖큹 寃곗젣�슂泥�(AUTH)�떆�뿉 蹂대깉�뜕 湲덉븸怨� �룞�씪�븳 湲덉븸�쓣 �쟾�넚

		/**************************************************
		 * 湲곕낯 �젙蹂�
		 **************************************************/
		REQ_DATA.put("TXTYPE", "BILL");
		REQ_DATA.put("SERVICETYPE", "DANALCARD");

		RES_DATA = danalFun.CallCredit(REQ_DATA, false);

		String RETURNCODE = (String) RES_DATA.get("RETURNCODE");
		String RETURNMSG = (String) RES_DATA.get("RETURNMSG");
		
		model.addAttribute("RETURNCODE", RETURNCODE);
		model.addAttribute("RETURNMSG", RETURNMSG);


//		logger.info("TID : " + (String) RES_DATA.get("TID"));
//		logger.info("ORDERID : " + (String) RES_DATA.get("ORDERID"));
//		logger.info("AMOUNT : " + (String) RES_DATA.get("AMOUNT"));
//		logger.info("RETURNCODE : " + (String) RES_DATA.get("RETURNCODE"));
//		logger.info("RETURNMSG : " + (String) RES_DATA.get("RETURNMSG"));
//		logger.info("TRANDATE : " + (String) RES_DATA.get("TRANDATE"));
//		logger.info("TRANTIME : " + (String) RES_DATA.get("TRANTIME"));
//		logger.info("CARDCODE : " + (String) RES_DATA.get("CARDCODE"));
//		logger.info("CARDNAME : " + (String) RES_DATA.get("CARDNAME"));
//		logger.info("CARDAUTHNO : " + (String) RES_DATA.get("CARDAUTHNO"));
//		logger.info("BYPASSVALUE : " + (String) RES_DATA.get("BYPASSVALUE"));
		
		//寃곗젣 �긽�깭 蹂�寃�
		if ("0000".equals(RES_DATA.get("RETURNCODE"))) {
	    	TB_ODINFOXM odinfoxm = new TB_ODINFOXM();
	    	
	    	odinfoxm.setORDER_NUM((String) RES_DATA.get("ORDERID"));
	    	odinfoxm.setORDER_CON("ORDER_CON_02");					//寃곗젣�셿猷�
	    	odinfoxm.setPAY_METD("PAY_METD_01");					//移대뱶寃곗젣
	    	odinfoxm.setPAY_MDKY((String) RES_DATA.get("TID"));
	    	odinfoxm.setPAY_AMT((String) RES_DATA.get("AMOUNT"));
	    	
			orderService.orderPayUpdate(odinfoxm);
			
		}
		
		model.addAttribute("ORDER_NUM", (String) RES_DATA.get("ORDERID"));
		
		//寃곗젣 濡쒓렇 泥섎━
		try {
			
			TB_ODDNLGXM oddnlgxm = new TB_ODDNLGXM();

			oddnlgxm.setTID((String) RES_DATA.get("TID"));
			oddnlgxm.setORDER_NUM((String) RES_DATA.get("ORDERID"));
			oddnlgxm.setGUBUN("ORDER");
			oddnlgxm.setRETURNCODE((String) RES_DATA.get("RETURNCODE"));
			oddnlgxm.setRETURNMSG((String) RES_DATA.get("RETURNMSG"));
			oddnlgxm.setAMOUNT((String) RES_DATA.get("AMOUNT"));
			oddnlgxm.setTRANDATE((String) RES_DATA.get("TRANDATE"));
			oddnlgxm.setTRANTIME((String) RES_DATA.get("TRANTIME"));
			oddnlgxm.setCARDCODE((String) RES_DATA.get("CARDCODE"));
			oddnlgxm.setCARDNAME((String) RES_DATA.get("CARDNAME"));
			oddnlgxm.setCARDAUTHNO((String) RES_DATA.get("CARDAUTHNO"));
			oddnlgxm.setBYPASSVALUE((String) RES_DATA.get("BYPASSVALUE"));
	    	
			orderService.danalLogInsert(oddnlgxm);
			
		}catch(Exception e){
			logger.info("Danal Logging Error : " + e.toString());
		}		
		
		return new ModelAndView("popup.layout", "jsp", "mall/order/orderReturn");
	}
	

	/**
	 * �봺�봺�봺�봺�봺�봺�봺�봺�봺�봺�봺�봺�봺�봺�봺�봺�봺�봺�봺�봺�봺�봺�봺�봺�봺�봺�봺�봺�봺�봺�봺�봺�봺�봺�봺�봺�봺�봺�봺�봺�봺�봺�봺�봺�봺
	 * @Class	: mall.web.controller.mall.OrderController.java
	 * @Method	: edit
	 * �봺�봺�봺�봺�봺�봺�봺�봺�봺�봺�봺�봺�봺�봺�봺�봺�봺�봺�봺�봺�봺�봺�봺�봺�봺�봺�봺�봺�봺�봺�봺�봺�봺�봺�봺�봺�봺�봺�봺�봺�봺�봺�봺�봺�봺
	 * @Desc	: 二쇰Ц�궡�뿭 �긽�꽭
	 * @Company	: YT Corp.
	 * @Author	: Seok-min Jeon (smjeon@youngthink.co.kr)
	 * @Date	: 2016-07-12 (�삤�썑 11:04:40)
	 * �봺�봺�봺�봺�봺�봺�봺�봺�봺�봺�봺�봺�봺�봺�봺�봺�봺�봺�봺�봺�봺�봺�봺�봺�봺�봺�봺�봺�봺�봺�봺�봺�봺�봺�봺�봺�봺�봺�봺�봺�봺�봺�봺�봺�봺
	 * @param customers
	 * @param model
	 * @return ModelAndView
	 * @throws Exception
	 * �봺�봺�봺�봺�봺�봺�봺�봺�봺�봺�봺�봺�봺�봺�봺�봺�봺�봺�봺�봺�봺�봺�봺�봺�봺�봺�봺�봺�봺�봺�봺�봺�봺�봺�봺�봺�봺�봺�봺�봺�봺�봺�봺�봺�봺
	*/
	@RequestMapping(value={ "/view/{ORDER_NUM}" }, method=RequestMethod.GET)
	public ModelAndView view(@ModelAttribute TB_ODINFOXM tb_odinfoxm, HttpServletRequest request, Model model) throws Exception {
		
		//�꽭�뀡蹂�寃�
		TB_MBINFOXM loginUser = (TB_MBINFOXM)request.getSession().getAttribute("USER");
		TB_ODINFOXM resultNotPayCnt= new TB_ODINFOXM();
		resultNotPayCnt.setREGP_ID(loginUser.getMEMB_ID());
		List<TB_ODINFOXM> notPayCnt =(List<TB_ODINFOXM>) orderService.getObjectList(resultNotPayCnt);
		int cnt = 0;
		for(int i=0; i<notPayCnt.size();i++){
			if(notPayCnt.get(i).getORDER_CON().equals("ORDER_CON_01"))
				cnt++;
		}
		request.getSession().setAttribute("NOTPAYCNT",  cnt);	
		
		TB_ODDLAIXM tb_oddlaixm = new TB_ODDLAIXM();
		//諛곗넚吏��젙蹂�
		tb_oddlaixm = (TB_ODDLAIXM)orderService.getDeliveryInfo(tb_odinfoxm);
		//二쇰Ц�젙蹂� 留덉뒪�꽣 �젙蹂�
		tb_odinfoxm = (TB_ODINFOXM)orderService.getMasterInfo(tb_odinfoxm);
		//二쇰Ц�젙蹂� �긽�꽭  
		tb_odinfoxm.setList(orderService.getDetailsList(tb_odinfoxm));
		
		/*
		if(tb_odinfoxm.getORDER_CON().equals("ORDER_CON_02")
				&&(tb_oddlaixm.getDLAR_DATE()==null||tb_oddlaixm.getDLAR_DATE().equals(""))
				&&(tb_odinfoxm.getPAY_DTM()!=null&&!tb_odinfoxm.getPAY_DTM().equals(""))){
			tb_odinfoxm.setMODP_ID(loginUser.getMEMB_ID());
			//諛곗넚�궇吏�, 諛곗넚�떆媛� ���옣
			orderService.orderDlvyDateUpdate(tb_odinfoxm);	
		}
		*/
		
		//諛곗넚吏� �젙蹂� �떎�떆 遺덈윭�삤湲�
		tb_oddlaixm = (TB_ODDLAIXM)orderService.getDeliveryInfo(tb_odinfoxm);

		if(!loginUser.getMEMB_ID().equals(tb_odinfoxm.getMEMB_ID())){
			ModelAndView mav = new ModelAndView();
			mav.addObject("alertMessage", "二쇰Ц�옄�� 濡쒓렇�씤 �젙蹂닿� �씪移섑븯吏� �븡�뒿�땲�떎.");
			mav.addObject("returnUrl", "back");
			mav.setViewName("alertMessage");
			return mav;
		}
		
		model.addAttribute("tb_odinfoxm", tb_odinfoxm);
		model.addAttribute("tb_oddlaixm", tb_oddlaixm);
		
		return new ModelAndView("mallNew.layout", "jsp", "mall/order/view");
	}

	@RequestMapping(value={ "/orderCancel" }, method=RequestMethod.GET)
	public ModelAndView cancel(@ModelAttribute TB_ODINFOXM tb_odinfoxm, HttpServletRequest request, Model model) throws Exception {
		
		return new ModelAndView("popup.layout", "jsp", "mall/order/orderCancel");
	}
	
	/**
	 * �봺�봺�봺�봺�봺�봺�봺�봺�봺�봺�봺�봺�봺�봺�봺�봺�봺�봺�봺�봺�봺�봺�봺�봺�봺�봺�봺�봺�봺�봺�봺�봺�봺�봺�봺�봺�봺�봺�봺�봺�봺�봺�봺�봺�봺
	 * @Class	: mall.web.controller.mall.OrderController.java
	 * @Method	: edit
	 * �봺�봺�봺�봺�봺�봺�봺�봺�봺�봺�봺�봺�봺�봺�봺�봺�봺�봺�봺�봺�봺�봺�봺�봺�봺�봺�봺�봺�봺�봺�봺�봺�봺�봺�봺�봺�봺�봺�봺�봺�봺�봺�봺�봺�봺
	 * @Desc	: 二쇰Ц�궡�뿭 �긽�꽭
	 * @Company	: YT Corp.
	 * @Author	: Seok-min Jeon (smjeon@youngthink.co.kr)
	 * @Date	: 2016-07-12 (�삤�썑 11:04:40)
	 * �봺�봺�봺�봺�봺�봺�봺�봺�봺�봺�봺�봺�봺�봺�봺�봺�봺�봺�봺�봺�봺�봺�봺�봺�봺�봺�봺�봺�봺�봺�봺�봺�봺�봺�봺�봺�봺�봺�봺�봺�봺�봺�봺�봺�봺
	 * @param customers
	 * @param model
	 * @return ModelAndView
	 * @throws Exception
	 * �봺�봺�봺�봺�봺�봺�봺�봺�봺�봺�봺�봺�봺�봺�봺�봺�봺�봺�봺�봺�봺�봺�봺�봺�봺�봺�봺�봺�봺�봺�봺�봺�봺�봺�봺�봺�봺�봺�봺�봺�봺�봺�봺�봺�봺
	*/
	@RequestMapping(value={ "/deliveryAddr" }, method=RequestMethod.POST)
	public @ResponseBody TB_ODDLAIXM deliveryAddr(@ModelAttribute TB_ODDLAIXM tb_oddlaixm, Model model, HttpServletRequest request) throws Exception {
		
		String dlarGubn = tb_oddlaixm.getDLAR_GUBN();

		TB_MBINFOXM loginUser = (TB_MBINFOXM)request.getSession().getAttribute("USER");
		tb_oddlaixm.setREGP_ID(loginUser.getMEMB_ID());
		
		/*
		  DLAR_GUBN_01 �옄�깮
		  DLAR_GUBN_02 �쉶�궗
		  DLAR_GUBN_03 理쒓렐諛곗넚吏�
		  DLAR_GUBN_04 �떊洹�
		  DLAR_GUBN_05 吏곸젒異쒓퀬
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
	
	
	
	/* 諛섑뭹臾멸뎄 �뙘�뾽 */
	@RequestMapping(value={ "/returnInfo/popup" }, method=RequestMethod.GET)
	public ModelAndView returnInfoPop(@ModelAttribute TB_ODINFOXM tb_odinfoxm, HttpServletRequest request, Model model) throws Exception {
				
		return new ModelAndView("popup.layout", "jsp", "mall/order/popup3");
	}
	
	
	/* 二쇰Ц痍⑥냼 �뙘�뾽 */
	@RequestMapping(value={ "/cancel/popup" }, method=RequestMethod.GET)
	public ModelAndView cancelPop(@ModelAttribute TB_ODINFOXM tb_odinfoxm, HttpServletRequest request, Model model) throws Exception {
		
		TB_MBINFOXM loginUser = (TB_MBINFOXM)request.getSession().getAttribute("USER");

		TB_ODINFOXM rtnOdinfoxm = (TB_ODINFOXM)orderService.getMasterInfo(tb_odinfoxm);
		
		if(!loginUser.getMEMB_ID().equals(rtnOdinfoxm.getMEMB_ID())){
			ModelAndView mav = new ModelAndView();
			mav.addObject("alertMessage", "二쇰Ц�옄�� 濡쒓렇�씤 �젙蹂닿� �씪移섑븯吏� �븡�뒿�땲�떎.");
			mav.addObject("returnUrl", "close");
			mav.setViewName("alertMessage");
			return mav;
		}
		
		
		return new ModelAndView("popup.layout", "jsp", "mall/order/popup");
	}
	
	
	/* 二쇰Ц痍⑥냼 */
	@RequestMapping(value={ "/cancel/popup" }, method=RequestMethod.POST)
	public ModelAndView orderCancel(@ModelAttribute TB_ODINFOXM tb_odinfoxm, HttpServletRequest request, Model model) throws Exception {

		ModelAndView mav = new ModelAndView();
		
		TB_MBINFOXM loginUser = (TB_MBINFOXM)request.getSession().getAttribute("USER");
		tb_odinfoxm.setREGP_ID(loginUser.getMEMB_ID());

		TB_ODINFOXM rtnOdinfoxm = (TB_ODINFOXM)orderService.getMasterInfo(tb_odinfoxm);
		
		if(!loginUser.getMEMB_ID().equals(rtnOdinfoxm.getMEMB_ID())){
			mav.addObject("alertMessage", "二쇰Ц�옄�� 濡쒓렇�씤 �젙蹂닿� �씪移섑븯吏� �븡�뒿�땲�떎.");
			mav.addObject("returnUrl", "close");
			mav.setViewName("alertMessage");
			return mav;
		}
		
		
		//寃곗젣 �젙蹂닿� �엳�쓣寃쎌슦
		if(StringUtils.isNotEmpty(rtnOdinfoxm.getPAY_MDKY())){
			String MODE = environment.getProperty("danal.MODE"); 						// service, test
			String DN_CREDIT_URL = environment.getProperty("danal.DN_CREDIT_URL"); 		// 寃곗젣 �꽌踰� �젙�쓽
			String CPID = environment.getProperty("danal.CPID"); 						// CPID
			String CRYPTOKEY = environment.getProperty("danal.CRYPTOKEY");				// �븫�샇�솕Key

			DanalFunction danalFun = new DanalFunction(MODE, DN_CREDIT_URL, CPID, CRYPTOKEY);
			
			Map REQ_DATA = new HashMap();
			Map RES_DATA = new HashMap();
			
			/**************************************************
			 * 寃곗젣 �젙蹂�
			 **************************************************/
			REQ_DATA.put("TID", rtnOdinfoxm.getPAY_MDKY());
			
			/**************************************************
			 * 湲곕낯 �젙蹂�
			 **************************************************/
			REQ_DATA.put("CANCELTYPE", "C");
			REQ_DATA.put("AMOUNT", rtnOdinfoxm.getORDER_AMT());

			/**************************************************
			 * 痍⑥냼 �젙蹂�
			 **************************************************/
			REQ_DATA.put("CANCELREQUESTER", "CP_CS_PERSON");
			REQ_DATA.put("CANCELDESC", "Item not delivered");


			REQ_DATA.put("TXTYPE", "CANCEL");
			REQ_DATA.put("SERVICETYPE", "DANALCARD");

			
			RES_DATA = danalFun.CallCredit(REQ_DATA, false);
			
			if( RES_DATA.get("RETURNCODE").equals("0000")){
				// 寃곗젣 �꽦怨� �떆 �옉�뾽 吏꾪뻾
		    	mav.addObject("alertMessage", "寃곗젣 痍⑥냼�슂泥��씠 �셿猷뚮릺�뿀�뒿�땲�떎.");
		    	
				tb_odinfoxm.setORDER_CON("ORDER_CON_04");
				orderService.cancelObject(tb_odinfoxm);
			} else {
				// 寃곗젣 �떎�뙣 �떆 �옉�뾽 吏꾪뻾
		    	mav.addObject("alertMessage", "寃곗젣 痍⑥냼�슂泥��씠 �떎�뙣�븯���뒿�땲�떎.\n  - RETURNCODE = " + (String) RES_DATA.get("RETURNCODE") + " - \nTX Response_msg = " + (String) RES_DATA.get("RETURNMSG"));
			}

			//寃곗젣 濡쒓렇 泥섎━
			try {
				
				TB_ODDNLGXM oddnlgxm = new TB_ODDNLGXM();

				oddnlgxm.setTID((String) RES_DATA.get("O_TID"));
				oddnlgxm.setORDER_NUM(tb_odinfoxm.getORDER_NUM());
				oddnlgxm.setGUBUN("CANCEL");
				oddnlgxm.setRETURNCODE((String) RES_DATA.get("RETURNCODE"));
				oddnlgxm.setRETURNMSG((String) RES_DATA.get("RETURNMSG"));
				oddnlgxm.setAMOUNT((String) RES_DATA.get("AMOUNT"));
				oddnlgxm.setTRANDATE((String) RES_DATA.get("TRANDATE"));
				oddnlgxm.setTRANTIME((String) RES_DATA.get("TRANTIME"));
				oddnlgxm.setCARDCODE("");
				oddnlgxm.setCARDNAME("");
				oddnlgxm.setCARDAUTHNO("");
				oddnlgxm.setBYPASSVALUE((String) RES_DATA.get("TID"));
		    	
				orderService.danalLogInsert(oddnlgxm);
				
			}catch(Exception e){
				logger.info("Danal Logging Error : " + e.toString());
			}
		    
		}else{
	    	mav.addObject("alertMessage", "寃곗젣 痍⑥냼�슂泥��씠 �셿猷뚮릺�뿀�뒿�땲�떎.");

			tb_odinfoxm.setORDER_CON("ORDER_CON_04");
			orderService.cancelObject(tb_odinfoxm);
		}


		mav.addObject("gubun", "popup");
		mav.addObject("returnUrl", servletContextPath.concat("/order/view/" + tb_odinfoxm.getORDER_NUM()));
		mav.setViewName("alertMessage");

		return mav;
		
		//RedirectView rv = new RedirectView(servletContextPath.concat("/order/view/" + tb_odinfoxm.getORDER_NUM()));
		//return new ModelAndView(rv);
	}
	
	
	
	//臾댄넻�옣�엯湲�
	@RequestMapping(value="/orderReady2")
	public ModelAndView orderPay2(@ModelAttribute TB_ODINFOXM tb_odinfoxm, Model model, HttpServletRequest request) throws Exception {

		TB_MBINFOXM loginUser = (TB_MBINFOXM)request.getSession().getAttribute("USER");
		//二쇰Ц�젙蹂� 留덉뒪�꽣 �젙蹂�
		TB_ODINFOXM rtnOdinfoxm = (TB_ODINFOXM)orderService.getMasterInfo(tb_odinfoxm);
		//二쇰Ц�젙蹂� �긽�꽭  
		rtnOdinfoxm.setList(orderService.getDetailsList(rtnOdinfoxm));
		//諛곗넚吏� �젙蹂�
		TB_ODDLAIXM rtnOddlaixm = new TB_ODDLAIXM();
		rtnOddlaixm = (TB_ODDLAIXM)orderService.getDeliveryInfo(rtnOdinfoxm);
		
		orderService.orderDlvyUpdate(tb_odinfoxm);

		String strItemName = "";
		
		List<?> list = rtnOdinfoxm.getList();
		if(list.size() > 0){
			TB_ODINFOXD odinfoxd = (TB_ODINFOXD)list.get(0);
			if(list.size() == 1){
				strItemName = odinfoxd.getPD_NAME();
			}else{
				strItemName = odinfoxd.getPD_NAME() + " �쇅 " + (list.size()-1) + "嫄�";			
			}
		}
		
		//model.addAttribute("tb_odinfoxm", rtnOdinfoxm);
		//model.addAttribute("tb_oddlaixm", rtnOddlaixm);
		

		String MODE = environment.getProperty("danal.MODE"); 						// service, test
		String DN_CREDIT_URL = environment.getProperty("danal.DN_BANK_URL"); 		// 寃곗젣 �꽌踰� �젙�쓽
		String CPID = environment.getProperty("danal.BANK_CPID"); 					// CPID
		String CRYPTOKEY = environment.getProperty("danal.BANK_CRYPTOKEY");				// �븫�샇�솕Key
		
		DanalFunction2 danalFun = new DanalFunction2(MODE, DN_CREDIT_URL, CPID, CRYPTOKEY);
		
		/*[ �븘�닔 �뜲�씠�꽣 ]***************************************/
		Map REQ_DATA = new HashMap();
		Map RES_DATA = null;

		/******************************************************
		 *  RETURNURL 	: CPCGI�럹�씠吏��쓽 Full URL�쓣 �꽔�뼱二쇱꽭�슂
		 *  CANCELURL 	: BackURL�럹�씠吏��쓽 Full URL�쓣 �꽔�뼱二쇱꽭�슂
		 ******************************************************/
		String RETURNURL = environment.getProperty("danal.BANK_RETURNURL");
		String CANCELURL = environment.getProperty("danal.BANK_CANCELURL");

		/**************************************************
		 * SubCP �젙蹂�
		 **************************************************/
		REQ_DATA.put("SUBCPID", "");

		/**************************************************
		 * 寃곗젣 �젙蹂�
		 **************************************************/
		REQ_DATA.put("ORDERID", rtnOdinfoxm.getORDER_NUM());
		REQ_DATA.put("AMOUNT", rtnOdinfoxm.getORDER_AMT());
		//REQ_DATA.put("ISCASHRECEIPTUI", "Y");			//�쁽湲덉쁺�닔利�
		REQ_DATA.put("ITEMNAME", strItemName);			//�긽�뭹紐�
		REQ_DATA.put("USERAGENT", "PC");
		//REQ_DATA.put("BYPASSVALUE", "a=b;c=d;");

		/**************************************************
		 * 怨좉컼 �젙蹂�
		 **************************************************/
		REQ_DATA.put("USERNAME", (String) loginUser.getMEMB_NAME()); // 援щℓ�옄 �씠由�
		REQ_DATA.put("USERID", (String) loginUser.getMEMB_ID()); // �궗�슜�옄 ID
		REQ_DATA.put("USEREMAIL", (String) loginUser.getMEMB_MAIL()); // �냼蹂대쾿 email�닔�떊泥�
		//REQ_DATA.put("USEREMAIL", "test@test.com"); // �냼蹂대쾿 email�닔�떊泥�

		/**************************************************
		 * URL �젙蹂�
		 **************************************************/
		REQ_DATA.put("CANCELURL", CANCELURL);
		REQ_DATA.put("RETURNURL", RETURNURL);

		/**************************************************
		 * 湲곕낯 �젙蹂�
		 **************************************************/
		REQ_DATA.put("TXTYPE", "AUTH");
		REQ_DATA.put("SERVICETYPE", "WIRETRANSFER");
		REQ_DATA.put("ISNOTI", "N");
		//REQ_DATA.put("BYPASSVALUE", "this=is;a=test;bypass=value"); // BILL�쓳�떟 �삉�뒗 Noti�뿉�꽌 �룎�젮諛쏆쓣 媛�. '&'瑜� �궗�슜�븷 寃쎌슦 媛믪씠 �옒由ш쾶�릺誘�濡� �쑀�쓽.

		System.out.println(REQ_DATA);
		
		//RES_DATA = danalFun.CallCredit(REQ_DATA, false);
		RES_DATA = danalFun.CallDanalBank(REQ_DATA, true);

		String STARTURL = (String) RES_DATA.get("STARTURL");
		String STARTPARAMS = (String) RES_DATA.get("STARTPARAMS");
		
		String RETURNCODE = (String) RES_DATA.get("RETURNCODE");
		String RETURNMSG = (String) RES_DATA.get("RETURNMSG");

		model.addAttribute("STARTURL", STARTURL);
		model.addAttribute("STARTPARAMS", STARTPARAMS);
		
		model.addAttribute("RETURNCODE", RETURNCODE);
		model.addAttribute("RETURNMSG", RETURNMSG);
		
		
		return new ModelAndView("popup.layout", "jsp", "mall/order/orderReady");
		
	}
	
	/**
	 * �씤利앹슂泥� �쓳�떟 �솕硫� - 怨꾩쥖�씠泥�
	 * @param resv
	 * @param request
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/orderReturn2")	
	public  ModelAndView payReturn2(@ModelAttribute TB_ODINFOXM tb_odinfoxm, HttpServletRequest request, HttpSession session, Model model) throws Exception {
	
		String MODE = environment.getProperty("danal.MODE"); 						// service, test
		String DN_CREDIT_URL = environment.getProperty("danal.DN_BANK_URL"); 		// 寃곗젣 �꽌踰� �젙�쓽
		String CPID = environment.getProperty("danal.BANK_CPID"); 						// CPID
		String CRYPTOKEY = environment.getProperty("danal.BANK_CRYPTOKEY");				// �븫�샇�솕Key

		DanalFunction2 danalFun = new DanalFunction2(MODE, DN_CREDIT_URL, CPID, CRYPTOKEY);
		
		
		String RES_STR = danalFun.toDecrypt((String) request.getParameter("RETURNPARAMS"));
		Map retMap = danalFun.str2data(RES_STR);

		String returnCode = (String) retMap.get("RETURNCODE");
		String returnMsg = (String) retMap.get("RETURNMSG");

		//*****  �떊�슜移대뱶 �씤利앷껐怨� �솗�씤 *****************
		if (returnCode == null || !"0000".equals(returnCode)) {
			// returnCode媛� �뾾嫄곕굹 �삉�뒗 洹� 寃곌낵媛� �꽦怨듭씠 �븘�땲�씪硫� �떎�뙣 泥섎━
			logger.info("Authentication failed. " + returnMsg + "[" + returnCode + "]");
		
			ModelAndView mav = new ModelAndView();
			mav.addObject("alertMessage", "�옒紐삳맂 �젒洹� �엯�땲�떎. 愿�由ъ옄�뿉寃� 臾몄쓽�븯�꽭�슂.\n"
					+"Authentication failed. " + returnMsg + "[" + returnCode + "]");
			mav.addObject("returnUrl", "close");
			mav.setViewName("alertMessage");
			return mav;
			
			
		}
		//二쇰Ц�젙蹂� 留덉뒪�꽣 �젙蹂�
		tb_odinfoxm.setORDER_NUM((String) retMap.get("ORDERID"));
		TB_ODINFOXM rtnOdinfoxm = (TB_ODINFOXM)orderService.getMasterInfo(tb_odinfoxm);
		
		/*[ �븘�닔 �뜲�씠�꽣 ]***************************************/
		Map REQ_DATA = new HashMap();
		Map RES_DATA = new HashMap();

		/**************************************************
		 * 寃곗젣 �젙蹂�
		 **************************************************/
		REQ_DATA.put("TID", (String) retMap.get("TID"));
		REQ_DATA.put("AMOUNT", rtnOdinfoxm.getORDER_AMT()); 		// 理쒖큹 寃곗젣�슂泥�(AUTH)�떆�뿉 蹂대깉�뜕 湲덉븸怨� �룞�씪�븳 湲덉븸�쓣 �쟾�넚

		/**************************************************
		 * 湲곕낯 �젙蹂�
		 **************************************************/
		REQ_DATA.put("TXTYPE", "BILL");
		REQ_DATA.put("SERVICETYPE", "WIRETRANSFER");

		RES_DATA = danalFun.CallDanalBank(REQ_DATA, false);

		String RETURNCODE = (String) RES_DATA.get("RETURNCODE");
		String RETURNMSG = (String) RES_DATA.get("RETURNMSG");
		
		model.addAttribute("RETURNCODE", RETURNCODE);
		model.addAttribute("RETURNMSG", RETURNMSG);


//		logger.info("TID : " + (String) RES_DATA.get("TID"));
//		logger.info("ORDERID : " + (String) RES_DATA.get("ORDERID"));
//		logger.info("AMOUNT : " + (String) RES_DATA.get("AMOUNT"));
//		logger.info("RETURNCODE : " + (String) RES_DATA.get("RETURNCODE"));
//		logger.info("RETURNMSG : " + (String) RES_DATA.get("RETURNMSG"));
//		logger.info("TRANDATE : " + (String) RES_DATA.get("TRANDATE"));
//		logger.info("TRANTIME : " + (String) RES_DATA.get("TRANTIME"));
//		logger.info("CARDCODE : " + (String) RES_DATA.get("CARDCODE"));
//		logger.info("CARDNAME : " + (String) RES_DATA.get("CARDNAME"));
//		logger.info("CARDAUTHNO : " + (String) RES_DATA.get("CARDAUTHNO"));
//		logger.info("BYPASSVALUE : " + (String) RES_DATA.get("BYPASSVALUE"));
		
		//寃곗젣 �긽�깭 蹂�寃�
		if ("0000".equals(RES_DATA.get("RETURNCODE"))) {
	    	TB_ODINFOXM odinfoxm = new TB_ODINFOXM();
	    	
	    	odinfoxm.setORDER_NUM((String) RES_DATA.get("ORDERID"));
	    	odinfoxm.setORDER_CON("ORDER_CON_02");					//寃곗젣�셿猷�
	    	odinfoxm.setPAY_METD("SC0030");					//移대뱶寃곗젣
	    	odinfoxm.setPAY_MDKY((String) RES_DATA.get("TID"));
	    	odinfoxm.setPAY_AMT((String) RES_DATA.get("AMOUNT"));
	    	
			orderService.orderPayUpdate(odinfoxm);
		}
		
		model.addAttribute("ORDER_NUM", (String) RES_DATA.get("ORDERID"));
		
		//寃곗젣 濡쒓렇 泥섎━
		try {
			
			TB_ODDNLGXM oddnlgxm = new TB_ODDNLGXM();

			oddnlgxm.setTID((String) RES_DATA.get("TID"));
			oddnlgxm.setORDER_NUM((String) RES_DATA.get("ORDERID"));
			oddnlgxm.setGUBUN("ORDER");
			oddnlgxm.setRETURNCODE((String) RES_DATA.get("RETURNCODE"));
			oddnlgxm.setRETURNMSG((String) RES_DATA.get("RETURNMSG"));
			oddnlgxm.setAMOUNT((String) RES_DATA.get("AMOUNT"));
			oddnlgxm.setTRANDATE((String) RES_DATA.get("TRANDATE"));
			oddnlgxm.setTRANTIME((String) RES_DATA.get("TRANTIME"));
			oddnlgxm.setCARDCODE("怨꾩쥖�씠泥�");
			oddnlgxm.setCARDNAME("怨꾩쥖�씠泥�");
			//oddnlgxm.setCARDAUTHNO((String) RES_DATA.get("CARDAUTHNO"));
			//oddnlgxm.setBYPASSVALUE((String) RES_DATA.get("BYPASSVALUE"));
	    	
			orderService.danalLogInsert(oddnlgxm);
			
		}catch(Exception e){
			logger.info("Danal Logging Error : " + e.toString());
		}
		
		return new ModelAndView("popup.layout", "jsp", "mall/order/orderReturn2");
	}
	
	/* 二쇰Ц痍⑥냼 �뙘�뾽 */
	@RequestMapping(value={ "/cancel/popup2" }, method=RequestMethod.GET)
	public ModelAndView cancelPop2(@ModelAttribute TB_ODINFOXM tb_odinfoxm, HttpServletRequest request, Model model) throws Exception {
		
		TB_MBINFOXM loginUser = (TB_MBINFOXM)request.getSession().getAttribute("USER");

		TB_ODINFOXM rtnOdinfoxm = (TB_ODINFOXM)orderService.getMasterInfo(tb_odinfoxm);
		
		if(!loginUser.getMEMB_ID().equals(rtnOdinfoxm.getMEMB_ID())){
			ModelAndView mav = new ModelAndView();
			mav.addObject("alertMessage", "二쇰Ц�옄�� 濡쒓렇�씤 �젙蹂닿� �씪移섑븯吏� �븡�뒿�땲�떎.");
			mav.addObject("returnUrl", "close");
			mav.setViewName("alertMessage");
			return mav;
		}
		
		
		return new ModelAndView("popup.layout", "jsp", "mall/order/popup2");
	}
	
	/* 二쇰Ц痍⑥냼 */
	@RequestMapping(value={ "/cancel/popup2" }, method=RequestMethod.POST)
	public ModelAndView orderCancel2(@ModelAttribute TB_ODINFOXM tb_odinfoxm, HttpServletRequest request, Model model) throws Exception {

		ModelAndView mav = new ModelAndView();
		
		TB_MBINFOXM loginUser = (TB_MBINFOXM)request.getSession().getAttribute("USER");
		tb_odinfoxm.setREGP_ID(loginUser.getMEMB_ID());

		TB_ODINFOXM rtnOdinfoxm = (TB_ODINFOXM)orderService.getMasterInfo(tb_odinfoxm);
		
		if(!loginUser.getMEMB_ID().equals(rtnOdinfoxm.getMEMB_ID())){
			mav.addObject("alertMessage", "二쇰Ц�옄�� 濡쒓렇�씤 �젙蹂닿� �씪移섑븯吏� �븡�뒿�땲�떎.");
			mav.addObject("returnUrl", "close");
			mav.setViewName("alertMessage");
			return mav;
		}
	
		//寃곗젣 �젙蹂닿� �엳�쓣寃쎌슦
		if(StringUtils.isNotEmpty(rtnOdinfoxm.getPAY_MDKY())){
			String MODE = environment.getProperty("danal.MODE"); 						// service, test
			String DN_CREDIT_URL = environment.getProperty("danal.DN_BANK_URL"); 		// 寃곗젣 �꽌踰� �젙�쓽
			String CPID = environment.getProperty("danal.BANK_CPID"); 						// CPID
			String CRYPTOKEY = environment.getProperty("danal.BANK_CRYPTOKEY");				// �븫�샇�솕Key

			DanalFunction2 danalFun = new DanalFunction2(MODE, DN_CREDIT_URL, CPID, CRYPTOKEY);
			
			Map REQ_DATA = new HashMap();
			Map RES_DATA = new HashMap();
			
			/**************************************************
			 * 寃곗젣 �젙蹂�
			 **************************************************/
			REQ_DATA.put("TID", rtnOdinfoxm.getPAY_MDKY());
			
			/**************************************************
			 * 湲곕낯 �젙蹂�
			 **************************************************/
			REQ_DATA.put("CANCELTYPE", "C");
			REQ_DATA.put("AMOUNT", rtnOdinfoxm.getORDER_AMT());

			/**************************************************
			 * 痍⑥냼 �젙蹂�
			 **************************************************/
			REQ_DATA.put("CANCELREQUESTER", "CP_CS_PERSON");
			REQ_DATA.put("CANCELDESC", "Item not delivered");


			REQ_DATA.put("TXTYPE", "CANCEL");
			REQ_DATA.put("SERVICETYPE", "WIRETRANSFER");

			
			RES_DATA = danalFun.CallDanalBank(REQ_DATA, false);
			
			if( RES_DATA.get("RETURNCODE").equals("0000")){
				// 寃곗젣 �꽦怨� �떆 �옉�뾽 吏꾪뻾
		    	mav.addObject("alertMessage", "寃곗젣 痍⑥냼�슂泥��씠 �셿猷뚮릺�뿀�뒿�땲�떎.");
		    	
				tb_odinfoxm.setORDER_CON("ORDER_CON_04");
				orderService.cancelObject(tb_odinfoxm);
			} else {
				// 寃곗젣 �떎�뙣 �떆 �옉�뾽 吏꾪뻾
		    	mav.addObject("alertMessage", "寃곗젣 痍⑥냼�슂泥��씠 �떎�뙣�븯���뒿�땲�떎.\n  - RETURNCODE = " + (String) RES_DATA.get("RETURNCODE") + " - \nTX Response_msg = " + (String) RES_DATA.get("RETURNMSG"));
			}

			//寃곗젣 濡쒓렇 泥섎━
			try {
				
				TB_ODDNLGXM oddnlgxm = new TB_ODDNLGXM();

				oddnlgxm.setTID((String) RES_DATA.get("O_TID"));
				oddnlgxm.setORDER_NUM(tb_odinfoxm.getORDER_NUM());
				oddnlgxm.setGUBUN("CANCEL");
				oddnlgxm.setRETURNCODE((String) RES_DATA.get("RETURNCODE"));
				oddnlgxm.setRETURNMSG((String) RES_DATA.get("RETURNMSG"));
				oddnlgxm.setAMOUNT((String) RES_DATA.get("AMOUNT"));
				oddnlgxm.setTRANDATE((String) RES_DATA.get("TRANDATE"));
				oddnlgxm.setTRANTIME((String) RES_DATA.get("TRANTIME"));
				oddnlgxm.setCARDCODE("");
				oddnlgxm.setCARDNAME("");
				oddnlgxm.setCARDAUTHNO("");
				oddnlgxm.setBYPASSVALUE((String) RES_DATA.get("TID"));
		    	
				orderService.danalLogInsert(oddnlgxm);
				
			}catch(Exception e){
				logger.info("Danal Logging Error : " + e.toString());
			}
		    
		}else{
	    	mav.addObject("alertMessage", "寃곗젣 痍⑥냼�슂泥��씠 �셿猷뚮릺�뿀�뒿�땲�떎.");

			tb_odinfoxm.setORDER_CON("ORDER_CON_04");
			orderService.cancelObject(tb_odinfoxm);
		}


		mav.addObject("gubun", "popup");
		mav.addObject("returnUrl", servletContextPath.concat("/order/view/" + tb_odinfoxm.getORDER_NUM()));
		mav.setViewName("alertMessage");

		return mav;
		
		//RedirectView rv = new RedirectView(servletContextPath.concat("/order/view/" + tb_odinfoxm.getORDER_NUM()));
		//return new ModelAndView(rv);
	}
	
	@RequestMapping(value={ "/orderCancel2" }, method=RequestMethod.GET)
	public ModelAndView cancel2(@ModelAttribute TB_ODINFOXM tb_odinfoxm, HttpServletRequest request, Model model) throws Exception {
		return new ModelAndView("popup.layout", "jsp", "mall/order/orderCancel2");
	}
	
	@RequestMapping(value={ "/updateDelete" }, method=RequestMethod.GET)
	public ModelAndView updateDelete(@ModelAttribute TB_ODINFOXM tb_odinfoxm, HttpServletRequest request, Model model
			,@RequestParam(value="checkArray[]", defaultValue="") List<String> arrayParams) throws Exception {
		
		
		for(int i=0;i<arrayParams.size();i++){	//�뿬湲곗꽌 �뾽�뜲�씠�듃
			String num = arrayParams.get(i);
			orderService.orderUpdateDelete(num);
		
		}
		return new ModelAndView("mallNew.layout", "jsp", "mall/order/list");
	}
	
	//�옣諛붽뎄�땲�뿉 ���옣
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
				if(tb_odinfoxd.getPD_CUT_SEQS().length==0||tb_odinfoxd.getPD_CUT_SEQS()[i]==null||tb_odinfoxd.getPD_CUT_SEQS()[i].equals("")){
					tb_bkinfoxm.setPD_CUT_SEQ("");
				}else{
					tb_bkinfoxm.setPD_CUT_SEQ(tb_odinfoxd.getPD_CUT_SEQS()[i]);
				}
				productService.basketInst(tb_bkinfoxm);
				cnt = 0;
			}
		}
	}

		
	
	
}

