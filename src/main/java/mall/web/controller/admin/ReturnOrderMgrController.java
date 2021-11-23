package mall.web.controller.admin;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

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

import mall.common.util.StringUtil;
import mall.web.controller.DefaultController;
import mall.web.domain.TB_MBINFOXM;
import mall.web.domain.TB_ODDLAIXM;
import mall.web.domain.TB_ODINFOXD;
import mall.web.domain.TB_ODINFOXM;
import mall.web.domain.TB_RTODINFOXD;
import mall.web.domain.TB_RTODINFOXM;
import mall.web.service.admin.impl.OrderMgrService;
import mall.web.service.admin.impl.ReturnOrderMgrService;
import mall.web.service.common.CommonService;
import mall.web.service.mall.OrderService;

@Controller
@RequestMapping(value="/adm/returnOrderMgr")
public class ReturnOrderMgrController extends DefaultController{


	@Resource(name="commonService")
	CommonService commonService;
	
	@Resource(name="orderService")
	OrderService orderService;
	
	@Resource(name="returnOrderMgrService")
	ReturnOrderMgrService returnOrderMgrService;
	
	/**
	 * ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
	 * @Class	: mall.web.controller.admin.OrderMgrController.java
	 * @Method	: getList
	 * ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
	 * @Desc	: 주문내역 조회
	 * @Company	: YT Corp.
	 * @Author	: Young-dae Kim (sjie1638@youngthink.co.kr)
	 * @Date	: 2016-07-13 (오후 03:48:40)
	 * ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
	 * @param customers
	 * @param model
	 * @return ModelAndView
	 * @throws Exception
	 * ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
	*/
	@RequestMapping(method=RequestMethod.GET)
	public ModelAndView getList(@ModelAttribute TB_RTODINFOXM tb_rtodinfoxm, Model model,HttpServletRequest request) throws Exception {
		
		
		/*if(tb_rtodinfoxm.getORDER_CON_STATE()!="" && tb_rtodinfoxm.getORDER_CON_STATE()!=null){
			tb_rtodinfoxm.setMODP_ID("admin");
			orderMgrService.updateStateObject(tb_rtodinfoxm);
		}*/
		
		if(request.getParameter("pagerMaxPageItems")!=null){
			tb_rtodinfoxm.setRowCnt(Integer.parseInt(request.getParameter("pagerMaxPageItems")));	//페이징 단위 : 50
			tb_rtodinfoxm.setPagerMaxPageItems(Integer.parseInt(request.getParameter("pagerMaxPageItems")));
		}else {
			tb_rtodinfoxm.setRowCnt(20);	//페이징 단위 : 20
			tb_rtodinfoxm.setPagerMaxPageItems(20);
		}
		
		tb_rtodinfoxm.setCount(returnOrderMgrService.getObjectCount(tb_rtodinfoxm));
		tb_rtodinfoxm.setList(returnOrderMgrService.getPaginatedObjectList(tb_rtodinfoxm));
		
		model.addAttribute("obj", tb_rtodinfoxm);
		model.addAttribute("rowCnt", tb_rtodinfoxm.getRowCnt());
		model.addAttribute("totalCnt", tb_rtodinfoxm.getCount());
		
		
		String strLink = null;
		strLink = "&schGbn="+StringUtil.nullCheck(tb_rtodinfoxm.getSchGbn())
				+ "&schTxt="+StringUtil.nullCheck(tb_rtodinfoxm.getSchTxt()
				+ "&pagerMaxPageItems="+StringUtil.nullCheck(tb_rtodinfoxm.getPagerMaxPageItems())
				+ "&schNum="+StringUtil.nullCheck(tb_rtodinfoxm.getSchNum())
				+ "&datepickerStr=" + StringUtil.nullCheck(tb_rtodinfoxm.getDatepickerStr())
				+ "&datepickerEnd=" + StringUtil.nullCheck(tb_rtodinfoxm.getDatepickerEnd()));/*
				+ "&ORDER_CON=" + StringUtil.nullCheck(tb_rtodinfoxm.getORDER_CON())
				+ "&PAY_METD=" + StringUtil.nullCheck(tb_rtodinfoxm.getPAY_METD())
				+ "&ORDER_GUBUN=" + StringUtil.nullCheck(tb_rtodinfoxm.getORDER_GUBUN())
				+ "&DATE_ORDER=" + StringUtil.nullCheck(tb_rtodinfoxm.getDATE_ORDER())
				+ "&MEMB_NM_ORDER=" + StringUtil.nullCheck(tb_rtodinfoxm.getMEMB_NM_ORDER())
				+ "&COM_NAME_ORDER=" + StringUtil.nullCheck(tb_rtodinfoxm.getCOM_NAME_ORDER())
				+ "&PAY_DATE_ORDER=" + StringUtil.nullCheck(tb_rtodinfoxm.getPAY_DATE_ORDER()));*/
		
		
		model.addAttribute("link", strLink);
		
		return new ModelAndView("admin.layout", "jsp", "admin/returnOrderMgr/list");
	}

	
	/**
	 * ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
	 * @Class	: mall.web.controller.admin.OrderMgrController.java
	 * @Method	: edit
	 * ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
	 * @Desc	: 주문내역 상세 및 주문상태/결제정보(form)
	 * @Company	: YT Corp.
	 * @Author	: Young-dae Kim (sjie1638@youngthink.co.kr)
	 * @Date	: 2016-07-13 (오후 07:48:40)
	 * ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
	 * @param customers
	 * @param model
	 * @return ModelAndView
	 * @throws Exception
	 * ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
	*/
	@RequestMapping(value={ "/form/{RETURN_NUM}" }, method=RequestMethod.GET)
	public ModelAndView edit(@ModelAttribute TB_RTODINFOXM tb_rtodinfoxm, Model model) throws Exception {
		
		//주문정보 마스터 정보
		tb_rtodinfoxm = (TB_RTODINFOXM)returnOrderMgrService.getMasterInfo(tb_rtodinfoxm);
		//주문정보 상세  
		tb_rtodinfoxm.setList(returnOrderMgrService.getDetailsList(tb_rtodinfoxm));
		
		model.addAttribute("tb_rtodinfoxm", tb_rtodinfoxm);
		
		return new ModelAndView("admin.layout", "jsp", "admin/returnOrderMgr/form");
	}
	
	/**
	 * ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
	 * @Class	: mall.web.controller.admin.OrderMgrController.java
	 * @Method	: update
	 * ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
	 * @Desc	: 주문내역 상세 및 주문상태/결제정보 저장
	 * @Company	: YT Corp.
	 * @Author	: Young-dae Kim (sjie1638@youngthink.co.kr)
	 * @Date	: 2016-07-15 (오후 6:08:29)
	 * ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
	 * @param tb_odinfoxm
	 * @param pagerOffset
	 * @param model
	 * @return
	 * @throws Exception
	 * ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
	*/
	@RequestMapping(method=RequestMethod.POST)
	public ModelAndView update(@ModelAttribute TB_RTODINFOXM tb_rtodinfoxm, @ModelAttribute TB_RTODINFOXD tb_rtodinfoxd
			, BindingResult result, SessionStatus status, Model model,HttpServletRequest request) throws Exception {
		
		TB_MBINFOXM loginUser = (TB_MBINFOXM)request.getSession().getAttribute("ADMUSER");
		
		tb_rtodinfoxm.setMODP_ID(loginUser.getMEMB_ID());
		tb_rtodinfoxd.setMODP_ID(loginUser.getMEMB_ID());
		
		for(int i=0; i<tb_rtodinfoxd.getRETURN_DTNUMS().length; i++){
			
			tb_rtodinfoxd.setRETURN_DTNUM(tb_rtodinfoxd.getRETURN_DTNUMS()[i]);
			tb_rtodinfoxd.setPD_PRICE(tb_rtodinfoxd.getPD_PRICES()[i]);
			tb_rtodinfoxd.setPDDC_GUBN(tb_rtodinfoxd.getPDDC_GUBNS()[i]);
			tb_rtodinfoxd.setPDDC_VAL(tb_rtodinfoxd.getPDDC_VALS()[i]);
			tb_rtodinfoxd.setRETURN_QTY(tb_rtodinfoxd.getRETURN_QTYS()[i]);
			if(tb_rtodinfoxd.getPDDC_GUBN().equals("PDDC_GUBN_02")){
				int price = Integer.parseInt(tb_rtodinfoxd.getPD_PRICE());
				int pddcval = Integer.parseInt(tb_rtodinfoxd.getPDDC_VAL());
				int qty = Integer.parseInt(tb_rtodinfoxd.getRETURN_QTY());
				tb_rtodinfoxd.setRETURN_AMT(Integer.toString((price-pddcval)*qty));
			}else if(tb_rtodinfoxd.getPDDC_GUBN().equals("PDDC_GUBN_03")){
				int price = Integer.parseInt(tb_rtodinfoxd.getPD_PRICE());
				int pddcval = Integer.parseInt(tb_rtodinfoxd.getPDDC_VAL());
				int qty = Integer.parseInt(tb_rtodinfoxd.getRETURN_QTY());
				tb_rtodinfoxd.setRETURN_AMT(Integer.toString((price - (price*pddcval/100))*qty));
			}else{
				int price = Integer.parseInt(tb_rtodinfoxd.getPD_PRICE());
				int qty = Integer.parseInt(tb_rtodinfoxd.getRETURN_QTY());
				tb_rtodinfoxd.setRETURN_AMT(Integer.toString(price*qty));
			}
			//tb_rtodinfoxd.setRETURN_AMT(detail.getRETURN_AMTS()[i]);
			tb_rtodinfoxd.setRETURN_QTY(tb_rtodinfoxd.getRETURN_QTYS()[i]);
			tb_rtodinfoxd.setRETURN_GBN(tb_rtodinfoxd.getRETURN_GBNS()[i]);
			tb_rtodinfoxd.setREGP_ID(tb_rtodinfoxd.getREGP_ID());
			tb_rtodinfoxd.setMODP_ID(tb_rtodinfoxd.getMODP_ID());
			//tb_rtodinfoxd.setORDER_DTNUM(tb_rtodinfoxd.getORDER_DTNUMS()[i]);
			
			returnOrderMgrService.updateObject(tb_rtodinfoxd);
			returnOrderMgrService.updateMasterObject(tb_rtodinfoxm);
		}
		
		RedirectView redirectView = new RedirectView(servletContextPath+"/adm/returnOrderMgr/form/"+tb_rtodinfoxd.getRETURN_NUM());
		
		return new ModelAndView(redirectView);
	}
	
	/**
	 * ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
	 * @Class	: mall.web.controller.admin.ReturnOrderMgrController.java
	 * @Method	: insert
	 * ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
	 * @Desc	: 주문내역 상세 및 주문상태/결제정보 저장
	 * @Company	: YT Corp.
	 * @Author	: Hee-sun Lee(hslee@youngthink.co.kr)
	 * @Date	: 2018-10-17 (오후 6:08:29)
	 * ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
	 * @param tb_odinfoxm
	 * @param pagerOffset
	 * @param model
	 * @return
	 * @throws Exception
	 * ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
	*/
	@RequestMapping(value={ "/insert" }, method=RequestMethod.POST)
	public ModelAndView insert(@ModelAttribute TB_ODINFOXM tb_odinfoxm, @ModelAttribute TB_ODINFOXD tb_odinfoxd,@ModelAttribute TB_RTODINFOXM tb_rtodinfoxm,
			@ModelAttribute TB_RTODINFOXD tb_rtodinfoxd, BindingResult result, SessionStatus status, Model model,HttpServletRequest request) throws Exception {
		
		
		TB_MBINFOXM loginUser = (TB_MBINFOXM)request.getSession().getAttribute("ADMUSER");
		
		//반품신청 유저 셋팅
		tb_rtodinfoxm.setMODP_ID(loginUser.getMEMB_ID());
		tb_rtodinfoxm.setREGP_ID(loginUser.getMEMB_ID());
		tb_rtodinfoxd.setREGP_ID(loginUser.getMEMB_ID());
		tb_rtodinfoxd.setMODP_ID(loginUser.getMEMB_ID());
	
		// string[] to list
		//ArrayList<String> mNewList = new ArrayList<String>(Arrays.asList(tb_odinfoxd.getORDER_DTNUM().split(",")));
		//tb_rtodinfoxm.setList(mNewList);
		//반품마스터 insert
		returnOrderMgrService.insertRtOrdObject(tb_rtodinfoxm,tb_rtodinfoxd);
				
		
		//orderMgrService.getDetailsUpdate(tb_odinfoxm,tb_oddlaixm);
		/*//배송정보 수정
		orderMgrService.getDeliveryUpdate(tb_oddlaixm);*/
		
		RedirectView redirectView = new RedirectView(servletContextPath+"/adm/orderCmptMgr/form/"+tb_odinfoxm.getORDER_NUM());
		
		return new ModelAndView(redirectView);
	}
	
	

	/**
	 * ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
	 * @Class	: mall.web.controller.admin.OrderMgrController.java
	 * @Method	: edit
	 * ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
	 * @Desc	: 주문번호에 해당하는 반품내역 존재여부 확인
	 * @Company	: YT Corp.
	 * @Author	: Young-dae Kim (sjie1638@youngthink.co.kr)
	 * @Date	: 2016-07-13 (오후 07:48:40)
	 * ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
	 * @param customers
	 * @param model
	 * @return ModelAndView
	 * @throws Exception
	 * ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
	*/
	@RequestMapping(value={ "/ordCheck" }, method=RequestMethod.POST)
	public @ResponseBody String ordCheck(@ModelAttribute TB_RTODINFOXM tb_rtodinfoxm) throws Exception {
		//주문번호에 따른 반품내역 갯수
		System.out.println(tb_rtodinfoxm.getORDER_NUM());
		int ordCount = returnOrderMgrService.getOrdCount(tb_rtodinfoxm);
		System.out.println(ordCount+"");
		return ordCount+"";
	}
	
}
