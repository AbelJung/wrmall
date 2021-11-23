package mall.web.controller.admin;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import mall.common.util.ExcelDownload;
import mall.common.util.StringUtil;
import mall.web.controller.DefaultController;
import mall.web.domain.TB_MBINFOXM;
import mall.web.domain.TB_ODDLAIXM;
import mall.web.domain.TB_ODINFOXD;
import mall.web.domain.TB_ODINFOXM;
import mall.web.domain.XTB_ODINFOXM;
import mall.web.service.admin.impl.OrderMgrService;
import mall.web.service.common.CommonService;

@Controller
@RequestMapping(value="/adm/orderDltMgr")
public class OrderDltMgrController extends DefaultController{


	@Resource(name="orderMgrService")
	OrderMgrService orderMgrService;
	
	@Resource(name="commonService")
	CommonService commonService;
	
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
	public ModelAndView getList(@ModelAttribute TB_ODINFOXM tb_odinfoxm, Model model,HttpServletRequest request) throws Exception {
		
		tb_odinfoxm.setORDER_CON("ORDER_CON_04");
		if(tb_odinfoxm.getORDER_CON_STATE()!="" && tb_odinfoxm.getORDER_CON_STATE()!=null){
			tb_odinfoxm.setMODP_ID("admin");
			orderMgrService.updateStateObject(tb_odinfoxm);
		}
		
		if(request.getParameter("pagerMaxPageItems")!=null){
			tb_odinfoxm.setRowCnt(Integer.parseInt(request.getParameter("pagerMaxPageItems")));	//페이징 단위 : 50
			tb_odinfoxm.setPagerMaxPageItems(Integer.parseInt(request.getParameter("pagerMaxPageItems")));
		}else {
			tb_odinfoxm.setRowCnt(20);	//페이징 단위 : 20
			tb_odinfoxm.setPagerMaxPageItems(20);
		}
		
		//tb_odinfoxm.setCount(orderMgrService.getObjectDeleteCount(tb_odinfoxm));
		//tb_odinfoxm.setList(orderMgrService.getPaginatedDeleteObjectList(tb_odinfoxm));
		tb_odinfoxm.setCount(orderMgrService.getObjectCount(tb_odinfoxm));
		tb_odinfoxm.setList(orderMgrService.getPaginatedObjectList(tb_odinfoxm));
		
		model.addAttribute("obj", tb_odinfoxm);
		model.addAttribute("rowCnt", tb_odinfoxm.getRowCnt());
		model.addAttribute("totalCnt", tb_odinfoxm.getCount());
		
		
		String strLink = null;
		strLink = "&schGbn="+StringUtil.nullCheck(tb_odinfoxm.getSchGbn())
				+ "&schTxt="+StringUtil.nullCheck(tb_odinfoxm.getSchTxt()
				+ "&PAY_METD="+StringUtil.nullCheck(tb_odinfoxm.getPAY_METD())
				+ "&pagerMaxPageItems="+StringUtil.nullCheck(tb_odinfoxm.getPagerMaxPageItems())
				+ "&datepickerStr=" + StringUtil.nullCheck(tb_odinfoxm.getDatepickerStr())
				+ "&datepickerEnd=" + StringUtil.nullCheck(tb_odinfoxm.getDatepickerEnd()));
		
		model.addAttribute("link", strLink);
		
		return new ModelAndView("admin.layout", "jsp", "admin/orderDltMgr/list");
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
	@RequestMapping(value={ "/form/{ORDER_NUM}" }, method=RequestMethod.GET)
	public ModelAndView edit(@ModelAttribute TB_ODINFOXM tb_odinfoxm, Model model) throws Exception {
		
		//주문정보 마스터 정보
		tb_odinfoxm = (TB_ODINFOXM)orderMgrService.getMasterInfo(tb_odinfoxm);
		//주문정보 상세  
		tb_odinfoxm.setList(orderMgrService.getDetailsList(tb_odinfoxm));
		//배송지 정보
		TB_ODDLAIXM tb_oddlaixm = new TB_ODDLAIXM();
		tb_oddlaixm = (TB_ODDLAIXM)orderMgrService.getDeliveryInfo(tb_odinfoxm);
		
		model.addAttribute("tb_odinfoxm", tb_odinfoxm);
		model.addAttribute("tb_oddlaixm", tb_oddlaixm);
		
		return new ModelAndView("admin.layout", "jsp", "admin/orderDltMgr/form");
	}
	
	@RequestMapping(value={ "/deleteReturnOrdList" }, method=RequestMethod.POST)
	public ModelAndView deleteOrdList(@ModelAttribute XTB_ODINFOXM tb_odinfoxm, Model model,HttpServletResponse response
			,HttpServletRequest request) throws Exception {
		tb_odinfoxm.setMODP_ID("admin");
		
		String str1 = request.getParameter("ORD_DELETE_LIST");
		if(str1.contains(",")){
			String[] words = str1.split(",");
			for (String wo : words ){
	    		orderMgrService.getDeleteReturnOrdList(wo);
	    		orderMgrService.getDeleteReturnOrdDtlList(wo);
	        }
		}else{
			orderMgrService.getDeleteReturnOrdList(str1);
    		orderMgrService.getDeleteReturnOrdDtlList(str1);
		}
         
        			
        
        RedirectView rv = new RedirectView(servletContextPath.concat("/adm/orderDltMgr"));
		return new ModelAndView(rv);
		
	}
	
	/**
	 * ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
	 * @Class	: mall.web.controller.admin.OrderMgrController.java
	 * @Method	: orderQtyUpdate
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
	@RequestMapping(value="/orderQtyUpdate",method=RequestMethod.POST)
	public ModelAndView updateQty(TB_ODINFOXM tb_odinfoxm, TB_ODINFOXD tb_odinfoxd,Model model) throws Exception{
		
		tb_odinfoxm.setMODP_ID("admin");
		tb_odinfoxd.setMODP_ID("admin");
		
		orderMgrService.updateDatailQty(tb_odinfoxd);
		orderMgrService.updateMasterQty(tb_odinfoxm);
		
		
		RedirectView redirectView = new RedirectView(servletContextPath+"/adm/orderDltMgr/form/"+tb_odinfoxm.getORDER_NUM());
		
		return new ModelAndView(redirectView);

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
	public ModelAndView update(@ModelAttribute TB_ODINFOXM tb_odinfoxm, @ModelAttribute TB_ODDLAIXM tb_oddlaixm
			, BindingResult result, SessionStatus status, Model model) throws Exception {
		
		//주문상태 및 결제 정보 수정
		tb_odinfoxm.setMODP_ID("admin");
		tb_oddlaixm.setMODP_ID("admin");
		orderMgrService.getDetailsUpdate(tb_odinfoxm,tb_oddlaixm);
		/*//배송정보 수정
		orderMgrService.getDeliveryUpdate(tb_oddlaixm);*/
		
		RedirectView redirectView = new RedirectView(servletContextPath+"/adm/orderDltMgr/form/"+tb_odinfoxm.getORDER_NUM());
		
		return new ModelAndView(redirectView);
	}
	/**
	 * ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
	 * @Class	: mall.web.controller.admin.OrderDtlMgrController.java
	 * @Method	: getExcelList
	 * ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
	 * @Desc	: 주문내역 상세 및 주문상태/결제정보 저장
	 * @Company	: YT Corp.
	 * @Author	: Hee-Sun Lee (hslee@youngthink.co.kr)
	 * @Date	: 2016-07-15 (오후 6:08:29)
	 * ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
	 * @param tb_odinfoxm
	 * @param pagerOffset
	 * @param model
	 * @return
	 * @throws Exception
	 * ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
	*/
	@RequestMapping(value={ "/excelDown" }, method=RequestMethod.GET)
	public void getExcelList(@ModelAttribute XTB_ODINFOXM tb_odinfoxm, Model model,HttpServletResponse response,HttpServletRequest request
			,@RequestParam(value="checkArray[]", defaultValue="") List<String> arrayParams) throws Exception {

		tb_odinfoxm.setORDER_CON("ORDER_CON_04");

		String[] headerName = {"주문일자","결제시간", "주문번호","주문자명","사업자상호","주문상품","총 결제금액","배송료","상품주문금액","주문상태 결제수단","출고방식","고객배송 요청사항","배송참조사항(관리자기록)"};
		String[] columnName = {"ORDER_DATE","PAY_DTM","ORDER_NUM","MEMB_NM","COM_NAME","PD_NAME","ORDER_AMT","DLVY_AMT","REAL_AMT","ORDER_CON_NM","DLAR_GUBN","DLVY_MSG","ADM_REF"};

		String sheetName = "주문취소내역";
		
		String[] chkArray = request.getParameter("CHK_ORD_LIST").split(",");
		// String[] -> List
        List<String> stringList = Arrays.asList(chkArray);
		tb_odinfoxm.setList(stringList);
		
		if(request.getParameter("pagerMaxPageItems")!=null){
			tb_odinfoxm.setRowCnt(Integer.parseInt(request.getParameter("pagerMaxPageItems")));	//페이징 단위 : 50
			tb_odinfoxm.setPagerMaxPageItems(Integer.parseInt(request.getParameter("pagerMaxPageItems")));
		}else {
			tb_odinfoxm.setRowCnt(20);	//페이징 단위 : 20
			tb_odinfoxm.setPagerMaxPageItems(20);
		}
		
		//tb_odinfoxm.setList(arrayParams);

		List<HashMap<String, String>> list = (List<HashMap<String, String>>) orderMgrService.getExcelList(tb_odinfoxm);
		
		ExcelDownload.excelDownloadOrder(response, list, headerName, columnName, sheetName);
		
	}
	
	@RequestMapping(value={ "/detailExcelDown" }, method=RequestMethod.GET)
	public void getDetailExcelList(@ModelAttribute XTB_ODINFOXM tb_odinfoxm, Model model,HttpServletResponse response
			,@RequestParam(value="checkArray[]", defaultValue="") List<String> arrayParams) throws Exception {

		
		String[] headerName = {"주문번호","주문일자", "사업자상호","주문자","배송비결재여부","바코드","상품명","세절방식","색상","과면세구분","수량","정상가","할인금액","단가","가격"};
		String[] columnName = {"ORDER_NUM","ORDER_DATE", "COM_NAME","MEMB_NM","DAP_YN","PD_BARCD","PD_NAME","PD_CUT_SEQ","OPTION_NAME","TAX_GUBN","ORDER_QTY","PD_PRICE"
											,"PDDC_VAL","ORDER_PREICE","ORDER_AMT"};

		String sheetName = "주문취소내역상세";
		
		tb_odinfoxm.setList(arrayParams);

		List<HashMap<String, String>> list = (List<HashMap<String, String>>) orderMgrService.getDetailExcelList(tb_odinfoxm);
		
		ExcelDownload.excelDownloadOrder(response, list, headerName, columnName, sheetName);
		
	}
}
