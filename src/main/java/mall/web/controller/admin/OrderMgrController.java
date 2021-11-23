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
import mall.web.service.mall.OrderService;

@Controller
@RequestMapping(value="/adm/orderMgr")
public class OrderMgrController extends DefaultController{


	@Resource(name="orderMgrService")
	OrderMgrService orderMgrService;
	
	@Resource(name="commonService")
	CommonService commonService;
	
	@Resource(name="orderService")
	OrderService orderService;
	
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
		
		tb_odinfoxm.setCount(orderMgrService.getObjectCount(tb_odinfoxm));
		tb_odinfoxm.setList(orderMgrService.getPaginatedObjectList(tb_odinfoxm));
		
		model.addAttribute("obj", tb_odinfoxm);
		model.addAttribute("rowCnt", tb_odinfoxm.getRowCnt());
		model.addAttribute("totalCnt", tb_odinfoxm.getCount());
		
		
		String strLink = null;
		strLink = "&schGbn="+StringUtil.nullCheck(tb_odinfoxm.getSchGbn())
				+ "&schTxt="+StringUtil.nullCheck(tb_odinfoxm.getSchTxt()
				+ "&pagerMaxPageItems="+StringUtil.nullCheck(tb_odinfoxm.getPagerMaxPageItems())
				+ "&datepickerStr=" + StringUtil.nullCheck(tb_odinfoxm.getDatepickerStr())
				+ "&datepickerEnd=" + StringUtil.nullCheck(tb_odinfoxm.getDatepickerEnd())
				+ "&ORDER_CON=" + StringUtil.nullCheck(tb_odinfoxm.getORDER_CON())
				+ "&PAY_METD=" + StringUtil.nullCheck(tb_odinfoxm.getPAY_METD())
				+ "&ORDER_GUBUN=" + StringUtil.nullCheck(tb_odinfoxm.getORDER_GUBUN())
				+ "&DATE_ORDER=" + StringUtil.nullCheck(tb_odinfoxm.getDATE_ORDER())
				+ "&MEMB_NM_ORDER=" + StringUtil.nullCheck(tb_odinfoxm.getMEMB_NM_ORDER())
				+ "&COM_NAME_ORDER=" + StringUtil.nullCheck(tb_odinfoxm.getCOM_NAME_ORDER())
				+ "&PAY_DATE_ORDER=" + StringUtil.nullCheck(tb_odinfoxm.getPAY_DATE_ORDER())
				+ "&DLAR_DATE_ORDER=" + StringUtil.nullCheck(tb_odinfoxm.getDLAR_DATE_ORDER()));
		
		
		model.addAttribute("link", strLink);
		
		return new ModelAndView("admin.layout", "jsp", "admin/orderMgr/list");
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
		
		return new ModelAndView("admin.layout", "jsp", "admin/orderMgr/form");
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
	public ModelAndView update(@ModelAttribute TB_ODINFOXM tb_odinfoxm, @ModelAttribute TB_ODDLAIXM tb_oddlaixm, @ModelAttribute TB_ODINFOXD tb_odinfoxd
			, BindingResult result, SessionStatus status, Model model) throws Exception {
		
		//주문상태 및 결제 정보 수정
		tb_odinfoxm.setMODP_ID("admin");
		tb_oddlaixm.setMODP_ID("admin");
		
		String[] ordsplit = tb_odinfoxd.getORDER_DTNUM().split(",");
		String[] ordqtysplit =  tb_odinfoxm.getORDER_QTY().split(",");
		for(int i= 0; i<ordsplit.length;i++){
			tb_odinfoxd.setORDER_DTNUM(ordsplit[i]);
			tb_odinfoxd.setORDER_QTY(ordqtysplit[i]);
			
			orderMgrService.updateDatailQty(tb_odinfoxd);
			orderMgrService.updateMasterQty(tb_odinfoxm);
		}
		
		
		orderMgrService.getDetailsUpdate(tb_odinfoxm,tb_oddlaixm);
		/*//배송정보 수정
		orderMgrService.getDeliveryUpdate(tb_oddlaixm);*/
		
		RedirectView redirectView = new RedirectView(servletContextPath+"/adm/orderMgr/form/"+tb_odinfoxm.getORDER_NUM());
		
		return new ModelAndView(redirectView);
	}
	
	/**
	 * ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
	 * @Class	: mall.web.controller.admin.OrderMgrController.java
	 * @Method	: getPickingList
	 * ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
	 * @Desc	: 피킹리스트
	 * @Company	: YT Corp.
	 * @Author	: LEE HEE SUN (hslee@youngthink.co.kr)
	 * @Date	: 2018-04-12 (오후 6:08:29)
	 * ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
	 * @param tb_odinfoxm
	 * @param pagerOffset
	 * @param model
	 * @return
	 * @throws Exception
	 * ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
	*/
	@RequestMapping(value="/picking",method=RequestMethod.GET)
	public ModelAndView getPickingList(TB_ODINFOXM tb_odinfoxm, Model model
			,@RequestParam(value="checkArray[]", defaultValue="") List<String> arrayParams) throws Exception{
		ModelAndView mav = new ModelAndView();
		
		
		tb_odinfoxm.setList(arrayParams);
		
		if(tb_odinfoxm.getORDER_GUBUN()!=null && tb_odinfoxm.getORDER_GUBUN().equals("goods")){
			model.addAttribute("objList", orderMgrService.getPickingGoodsList(tb_odinfoxm));
		}else if (tb_odinfoxm.getORDER_GUBUN()!=null && tb_odinfoxm.getORDER_GUBUN().equals("com")){
			model.addAttribute("objList", orderMgrService.getPickingComList(tb_odinfoxm));
		}else if(tb_odinfoxm.getORDER_GUBUN()!=null && tb_odinfoxm.getORDER_GUBUN().equals("car")){
			model.addAttribute("objList", orderMgrService.getPickingCarList(tb_odinfoxm));
		}else{
			model.addAttribute("objList", orderMgrService.getPickingList(tb_odinfoxm));
		}
		model.addAttribute("today",new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
		//mav.addObject("obj",orderMgrService.getPickingList(tb_odinfoxm));
		//mav.addObject("test","test");
		
		return new ModelAndView("admin.layout", "jsp", "admin/orderMgr/list");

	}
	
	/**
	 * ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
	 * @Class	: mall.web.controller.admin.OrderMgrController.java
	 * @Method	: getPickingList
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
	@RequestMapping(value="/pickingUpdate",method=RequestMethod.GET)
	public ModelAndView pickingUpdate(TB_ODINFOXM tb_odinfoxm, Model model
			,@RequestParam(value="checkArray[]", defaultValue="") List<String> arrayParams) throws Exception{
		ModelAndView mav = new ModelAndView();
		//주문상태 및 결제 정보 수정

		tb_odinfoxm.setList(arrayParams);
		
		List<?> pickingList = orderMgrService.getPickingList(tb_odinfoxm);
		for(int i=0;i<pickingList.size();i++){	//여기서 업데이트
			String num = ((TB_ODINFOXM)pickingList.get(i)).getORDER_NUM();
		
			orderMgrService.updatePickingList(num);
		}
		
		//orderMgrService.updatePickingList(pickingList); foreach쓸때..
		
		
		return new ModelAndView("admin.layout", "jsp", "admin/orderMgr/list");

	}
	
	
	@RequestMapping(value={ "/excelDown" }, method=RequestMethod.GET)
	public void getExcelList(@ModelAttribute XTB_ODINFOXM tb_odinfoxm, Model model,HttpServletRequest request,HttpServletResponse response
			,@RequestParam(value="checkArray[]", defaultValue="") List<String> arrayParams) throws Exception {

		
		String[] headerName = {"주문일자","결제시간", "주문번호","주문자명","사업자상호","주문상품","총 결제금액","배송료","상품주문금액","주문상태 결제수단","출고방식","고객배송 요청사항","배송참조사항(관리자기록)"};
		String[] columnName = {"ORDER_DATE","PAY_DTM","ORDER_NUM","MEMB_NM","COM_NAME","PD_NAME","ORDER_AMT","DLVY_AMT","REAL_AMT","ORDER_CON_NM","DLAR_GUBN","DLVY_MSG","ADM_REF"};
//
//		String[] headerName = {"주문일시", "주문번호", "주문자명(수령자명)", "주문상품", "총 주문금액", "주문상태(결제수단)"};
//		String[] columnName = {"ORDER_DATE", "ORDER_NUM", "MEMB_NM", "PD_NAME", "ORDER_AMT","ORDER_CON_NM"};
		String sheetName = "주문내역";
		
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

		List<HashMap<String, String>> list = (List<HashMap<String, String>>) orderMgrService.getExcelList(tb_odinfoxm);
		
		ExcelDownload.excelDownloadOrder(response, list, headerName, columnName, sheetName);
		
	}
	
	@RequestMapping(value={ "/detailExcelDown" }, method=RequestMethod.GET)
	public void getDetailExcelList(@ModelAttribute XTB_ODINFOXM tb_odinfoxm, Model model,HttpServletResponse response
			,@RequestParam(value="checkArray[]", defaultValue="") List<String> arrayParams) throws Exception {

		
		String[] headerName = {"주문번호","주문일자", "사업자상호","주문자","배송비결재여부","바코드","상품명","세절방식","색상","과면세구분","수량","정상가","할인금액","단가","가격"};
		String[] columnName = {"ORDER_NUM","ORDER_DATE", "COM_NAME","MEMB_NM","DAP_YN","PD_BARCD","PD_NAME","PD_CUT_SEQ","OPTION_NAME","TAX_GUBN","ORDER_QTY","PD_PRICE"
											,"PDDC_VAL","ORDER_PREICE","ORDER_AMT"};

		String sheetName = "주문내역상세";
		
		tb_odinfoxm.setList(arrayParams);

		List<HashMap<String, String>> list = (List<HashMap<String, String>>) orderMgrService.getDetailExcelList(tb_odinfoxm);
		
		ExcelDownload.excelDownloadOrder(response, list, headerName, columnName, sheetName);
		
	}
	
	@RequestMapping(value={ "/pickingExcelDown" }, method=RequestMethod.GET)
	public void getpickingExcelList(@ModelAttribute TB_ODINFOXM tb_odinfoxm, Model model,HttpServletResponse response
			,@RequestParam(value="checkArray", defaultValue="") List<String> arrayParams) throws Exception{
		
		tb_odinfoxm.setList(arrayParams);
		
		List<HashMap<String, String>> list =null;
		String[] headerName = null;
		String[] columnName = null;
		String sheetName = "피킹리스트";
		
		
		if(tb_odinfoxm.getORDER_GUBUN()!=null && tb_odinfoxm.getORDER_GUBUN().equals("goods")){
			list= (List<HashMap<String, String>>)orderMgrService.getPickingGoodsExcel(tb_odinfoxm);
			headerName = new String[]{"상품코드", "상품명", "규격", "입수", "주문수량","바코드"};
			columnName = new String[]{"PD_CODE","PD_NAME", "PD_STD", "INPUT_CNT","ORDER_QTY", "PD_BARCD"};
			sheetName = "피킹리스트(상품별)";
		}else if (tb_odinfoxm.getORDER_GUBUN()!=null && tb_odinfoxm.getORDER_GUBUN().equals("com")){
			list= (List<HashMap<String, String>>)orderMgrService.getPickingComExcel(tb_odinfoxm);
			headerName = new String[]{"상품코드", "매출처명", "상품명", "규격", "입수", "주문수량","바코드"};
			columnName = new String[]{"PD_CODE","COM_ORD","PD_NAME", "PD_STD", "INPUT_CNT","ORDER_QTY", "PD_BARCD"};
			sheetName = "피킹리스트(매출처별)";
		}else if(tb_odinfoxm.getORDER_GUBUN()!=null && tb_odinfoxm.getORDER_GUBUN().equals("car")){
			list= (List<HashMap<String, String>>)orderMgrService.getPickingCarExcel(tb_odinfoxm);
			headerName = new String[]{"상품코드","차량별" ,"매출처명", "상품명", "규격", "입수", "주문수량","바코드"};
			columnName = new String[]{"PD_CODE","CAR_NUM_NM","COM_ORD","PD_NAME", "PD_STD", "INPUT_CNT","ORDER_QTY", "PD_BARCD"};
			sheetName = "피킹리스트(차량별)";
		}else{
			//list= (List<HashMap<String, String>>)orderMgrService.getPickingExcel(tb_odinfoxm);
			headerName = new String[]{"순번", "상품명", "규격", "입수", "주문수량","바코드"};
			columnName = new String[]{"PD_CODE","PD_NAME", "PD_STD", "INPUT_CNT","ORDER_QTY", "PD_BARCD"};
		}
		
		ExcelDownload.excelDownloadOrder(response, list, headerName, columnName, sheetName);
		
	}
	
	
	@RequestMapping(value={ "/deleteOrdList" }, method=RequestMethod.POST)
	public ModelAndView deleteOrdList(@ModelAttribute XTB_ODINFOXM tb_odinfoxm, Model model,HttpServletResponse response
			,HttpServletRequest request) throws Exception {
		
		String str1 = request.getParameter("ORD_DELETE_LIST");
        String[] words = str1.split(",");
         
        for (String wo : words ){
    		orderMgrService.deleteOrdList(wo);
    		orderMgrService.deleteOrdDetail(wo);
        }			
        
        RedirectView rv = new RedirectView(servletContextPath.concat("/adm/orderMgr"));
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
		
		
		RedirectView redirectView = new RedirectView(servletContextPath+"/adm/orderMgr/form/"+tb_odinfoxm.getORDER_NUM());
		
		return new ModelAndView(redirectView);

	}
	
}
