package mall.web.controller.admin;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
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
import mall.web.service.admin.impl.MemberSalDateMgrService;

@Controller
@RequestMapping(value="/adm")
public class MemberSalDateMgrController extends DefaultController{

	private static final Logger logger = LoggerFactory.getLogger(MemberSalDateMgrController.class);

	@Resource(name="memberSalDateMgrService")
	MemberSalDateMgrService memberSalDateMgrService;
	
	/**
	 * ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
	 * @Class	: mall.web.controller.admin.MemberMgrController.java
	 * @Method	: getList
	 * ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
	 * @Desc	: 회원목록 조회
	 * @Company	: YT Corp.
	 * @Author	: Tae-seok Choi (tschoi@youngthink.co.kr)
	 * @Date	: 2017-07-06 (오후 11:04:40)
	 * ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
	 * @param customers
	 * @param model6
	 * @return ModelAndView
	 * @throws Exception
	 * ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
	*/
	@RequestMapping(value="/memberSalDateMgr", method=RequestMethod.GET)
	public ModelAndView getList(@ModelAttribute TB_MBINFOXM memberInfo, HttpServletRequest request, Model model) throws Exception {
		
		//Default date
		String dateStr = request.getParameter("datepickerStr");
		String dateEnd = request.getParameter("datepickerEnd");
		
		Calendar cal = Calendar.getInstance ( );
		cal.add(Calendar.DATE, -7);
		Date weekago = cal.getTime();
		Date today = new Date ( );
		SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd");
		
		if (dateStr == null){
			dateStr = dateformat.format(weekago).toString();
			memberInfo.setDatepickerStr(dateStr);
		}
		if(dateEnd == null){
			dateEnd = dateformat.format(today).toString();
			memberInfo.setDatepickerEnd(dateEnd);
		}
		
		//System.out.println(dateStr);
		//System.out.println(dateEnd);
		//System.out.println(memberInfo.getMEMB_ORD_GUBUN());
		//System.out.println(memberInfo.getMEMB_NM_ORDER());
		//System.out.println(memberInfo.getCOM_NM_ORDER());
		
		//memberInfo.setMEMB_GUBN("MEMB_GUBN_01");	//MEMB_GUBN_01-일반회원, MEMB_GUBN_02-기업회원,  MEMB_GUBN_03-시스템관리자
		memberInfo.setSCSS_YN("N");					//Y-탈퇴회원, N-정상회원
		memberInfo.setMEMB_GUBN(request.getParameter("MEMB_GUBN"));
		memberInfo.setTAXCAL_YN(request.getParameter("TAXCAL_YN"));
		memberInfo.setCount(memberSalDateMgrService.getObjectCount(memberInfo));
		memberInfo.setList(memberSalDateMgrService.getPaginatedObjectList(memberInfo));

		model.addAttribute("obj", memberInfo);
		model.addAttribute("rowCnt", memberInfo.getRowCnt());
		model.addAttribute("totalCnt", memberInfo.getCount());
		
		String strLink = null;
		strLink = "&schGbn="+StringUtil.nullCheck(memberInfo.getSchGbn())
				  + "&schTxt="+StringUtil.nullCheck(memberInfo.getSchTxt())
				  + "&datepickerStr="+StringUtil.nullCheck(dateStr)	
				  + "&datepickerEnd="+StringUtil.nullCheck(dateEnd)
				  + "&PAY_METD="+StringUtil.nullCheck(request.getParameter("PAY_METD"))
				  + "&MEMB_GUBN="+StringUtil.nullCheck(request.getParameter("MEMB_GUBN"))
				  + "&TAXCAL_YN="+StringUtil.nullCheck(request.getParameter("TAXCAL_YN"))
				  + "&MEMB_NM_ORDER=" + StringUtil.nullCheck(memberInfo.getMEMB_NM_ORDER())
				  + "&COM_NAME_ORDER=" + StringUtil.nullCheck(memberInfo.getCOM_NM_ORDER())
				  + "&MEMB_ORD_GUBUN=" + StringUtil.nullCheck(memberInfo.getMEMB_ORD_GUBUN());
		
		System.out.println(strLink);
		
		model.addAttribute("link", strLink);

		return new ModelAndView("admin.layout", "jsp", "admin/memberSalDateMgr/list");
	}
	
	
	
	@RequestMapping(value={ "/memberSalDateMgr/excelDown" }, method=RequestMethod.GET)
	public void getExcelList(@ModelAttribute TB_MBINFOXM memberInfo, Model model,HttpServletResponse response
			,@RequestParam(value="checkArray[]", defaultValue="") List<String> arrayParams) throws Exception {


		String[] headerName = {"고객아이디", "고객명", "상호", "사업자번호", "일자", "과세","면세","배송비"
								,"반품과세","반품면세","반품배송비","합계","주소","이메일"};
	
		String[] columnName = {"MEMB_ID", "MEMB_NAME", "COM_NAME", "COM_BUNB", "ORDER_DATE", "TAX_GUBN_01_SUM","TAX_GUBN_02_SUM"
				,"DLVY_AMT_SUM","TAX_GUBN_01_SUM_RT","TAX_GUBN_02_SUM_RT","DLVY_AMT_SUM_RT","AMT_TOTAL","MEMB_BADR","MEMB_MAIL"};
		String sheetName = "일별매출집계";
		

		List<HashMap<String, String>> list = (List<HashMap<String, String>>) memberSalDateMgrService.getExcelList(memberInfo);
		
		ExcelDownload.excelDownloadOrder(response, list, headerName, columnName, sheetName);
		
	}
	
	@RequestMapping(value={ "/memberSalMnthMgr" }, method=RequestMethod.GET)
	public ModelAndView gettest1(@ModelAttribute TB_MBINFOXM memberInfo, HttpServletRequest request, Model model) throws Exception {
		
		if(memberInfo.getDatepickerStr()==null ||memberInfo.getDatepickerStr().equals("")){
			String curTime = new SimpleDateFormat("yyyyMM").format(new Date());			
			memberInfo.setDatepickerStr(curTime);
		}
		
		int year = Integer.parseInt(memberInfo.getDatepickerStr().substring(0, 4));
        int month = Integer.parseInt(memberInfo.getDatepickerStr().substring(4, 6))-1;
        int day = 1;
        Calendar cal = new GregorianCalendar(year, month, day);
        int daysOfMonth = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
       
        System. out.println(year + "년 " + (month + 1) + "월의 일수: " + daysOfMonth);
		
		memberInfo.setCount(memberSalDateMgrService.getDateCnt(memberInfo));
		memberInfo.setList(memberSalDateMgrService.getDateList(memberInfo));
		
		model.addAttribute("obj", memberInfo);
		model.addAttribute("rowCnt", memberInfo.getRowCnt());
		model.addAttribute("totalCnt", memberInfo.getCount());
		model.addAttribute("dateCnt",daysOfMonth);
		model.addAttribute("month",month+1);
		
		String strLink = null;
		strLink = "&schGbn="+StringUtil.nullCheck(memberInfo.getSchGbn())+
				  "&schTxt="+StringUtil.nullCheck(memberInfo.getSchTxt())+
				  "&datepickerStr="+StringUtil.nullCheck(request.getParameter("datepickerStr"))+
				  "&PAY_METD="+StringUtil.nullCheck(request.getParameter("PAY_METD"))
				  + "&MEMB_GUBN="+StringUtil.nullCheck(request.getParameter("MEMB_GUBN"))
				  + "&TAXCAL_YN="+StringUtil.nullCheck(request.getParameter("TAXCAL_YN"))
				  + "&MEMB_NM_ORDER=" + StringUtil.nullCheck(memberInfo.getMEMB_NM_ORDER())
				  + "&COM_NAME_ORDER=" + StringUtil.nullCheck(memberInfo.getCOM_NM_ORDER())
				  + "&MEMB_ORD_GUBUN=" + StringUtil.nullCheck(memberInfo.getMEMB_ORD_GUBUN());
		
		
		model.addAttribute("link", strLink);
		
		return new ModelAndView("admin.layout", "jsp", "admin/memberSalDateMgr2/list");
	}
	
	@RequestMapping(value={ "/memberSalDateMgr/dateExcelDown" }, method=RequestMethod.GET)
	public void getDateExcelList(@ModelAttribute TB_MBINFOXM memberInfo, Model model,HttpServletResponse response
			,@RequestParam(value="checkArray[]", defaultValue="") List<String> arrayParams) throws Exception {

		int year = Integer.parseInt(memberInfo.getDatepickerStr().substring(0, 4));
        int month = Integer.parseInt(memberInfo.getDatepickerStr().substring(4, 6))-1;
        int day = 1;
        Calendar cal = new GregorianCalendar(year, month, day);
        int daysOfMonth = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
		
		String[] headerName = new String[daysOfMonth+4];
		headerName[0] = "고객아이디";
		headerName[1] = "고객명";
		headerName[2] = "사업자상호";
		headerName[3] = "사업자번호";
		String[] columnName = new String[daysOfMonth+4];
		columnName[0] = "MEMB_ID";
		columnName[1] = "MEMB_NAME";
		columnName[2] = "COM_NAME";
		columnName[3] = "COM_BUNB";
		for(int i=4; i<daysOfMonth+4; i++){
			headerName[i] = (i-3)+"일";
			columnName[i] = "DAY"+(i-3);
		}
		String sheetName = "회원월별매출집계관리";
		

		List<HashMap<String, String>> list = (List<HashMap<String, String>>) memberSalDateMgrService.getDateExcelList(memberInfo);
		
		ExcelDownload.excelDownloadOrder(response, list, headerName, columnName, sheetName);
		
	}
	
}
