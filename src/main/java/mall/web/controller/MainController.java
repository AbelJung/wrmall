package mall.web.controller;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import mall.common.util.JunDanUtil;
import mall.web.controller.admin.CategoryMgrController;
import mall.web.domain.Customers;
import mall.web.domain.TB_BKINFOXM;
import mall.web.domain.TB_JDINFOXM;
import mall.web.domain.TB_MBINFOXM;
import mall.web.domain.TB_ODINFOXM;
import mall.web.domain.TB_PDBORDXM;
import mall.web.domain.TB_PDINFOXM;
import mall.web.domain.TB_PDRCMDXM;
import mall.web.service.admin.impl.BoardMgrService;
import mall.web.service.common.CommonService;
import mall.web.service.mall.BoardService;
import mall.web.service.mall.OrderService;
import mall.web.service.mall.ProductService;

/**
 * ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
 * @Package	: mall.web.controller
 * ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
 * @Desc	: 초기 페이지 접속 관리용 Controller
 * @Company	: YT Corp.
 * @Author	: Byeong-gwon Gang (multiple@nate.com)
 * @Date	: 2016-07-07 (오후 11:13:33)
 * @Version	: 1.0
 * ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
*/
@Controller
@RequestMapping(value="/")
public class MainController {

	private static final Logger logger = LoggerFactory.getLogger(MainController.class);
	
	@Value("#{servletContext.contextPath}")
	private String servletContextPath;

	@Resource(name="commonService")
	CommonService commonService;
	
	@Resource(name="productService")
	ProductService productService;

	@Resource(name="boardMgrService")
	BoardMgrService boardMgrService;
	
	@Resource(name="boardService")
	BoardService boardService;
	
	@Resource(name="orderService")
	OrderService orderService;
	
	/**
	 * ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
	 * @Class	: mall.web.controller.MainController.java
	 * @Method	: index
	 * ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
	 * @Desc	: 초기 페이지 접속시 /customers 페이지로 Redirect
	 * @Company	: YT Corp.
	 * @Author	: Byeong-gwon Gang (multiple@nate.com)
	 * @Date	: 2016-07-07 (오후 11:13:45)
	 * ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
	 * @param customers
	 * @param model
	 * @return ModelAndView
	 * @throws Exception
	 * ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
	*/
	//@RequestMapping(value={"/main1"}, method=RequestMethod.GET)
	@RequestMapping(method=RequestMethod.GET)
	public ModelAndView index(@ModelAttribute TB_PDINFOXM productInfo, HttpServletRequest request, Model model) throws Exception {
		RedirectView redirectView = new RedirectView("/m");
		return new ModelAndView(redirectView);
		//return new ModelAndView("blankPage", "jsp", "mall/main");
	}


	/**
	 * ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
	 * @Class	: mall.web.controller.MainController.java
	 * @Method	: index
	 * ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
	 * @Desc	: 초기 페이지 접속시 /customers 페이지로 Redirect
	 * @Company	: YT Corp.
	 * @Author	: Byeong-gwon Gang (multiple@nate.com)
	 * @Date	: 2016-07-07 (오후 11:13:45)
	 * ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
	 * @param customers
	 * @param model
	 * @return ModelAndView
	 * @throws Exception
	 * ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
	*/
	//@RequestMapping(method=RequestMethod.GET)
	@RequestMapping(value={"/main1"}, method=RequestMethod.GET)
	public ModelAndView index1(@ModelAttribute TB_PDINFOXM productInfo, @ModelAttribute TB_JDINFOXM jundanInfo, HttpServletRequest request, Model model) throws Exception {
	//public ModelAndView index1(@ModelAttribute TB_PDINFOXM productInfo, HttpServletRequest request, Model model) throws Exception {

		//상품목록
		productInfo.setSUPR_ID("C00002");
		productInfo.setSALE_CON("SALE_CON_01");
		
		//베스트
		//model.addAttribute("hitList", productService.getMainProductList(productInfo));
		//당일 농산물 시세
		model.addAttribute("newList", productService.getMainNewProductList(productInfo));
		//오늘만 번개세일
		model.addAttribute("recommendList8", productService.getMainRecommandProductList8(productInfo));
		//추천상품
		model.addAttribute("recommendList", productService.getMainRecommandProductList(productInfo));
		//추가할인상품
		model.addAttribute("recommendList3", productService.getMainRecommandProductList3(productInfo));
		//주점안주류
		model.addAttribute("recommendList4", productService.getMainRecommandProductList4(productInfo));
		//중식일식재료
		model.addAttribute("recommendList5", productService.getMainRecommandProductList5(productInfo));
		//재과점재료
		model.addAttribute("recommendList6", productService.getMainRecommandProductList6(productInfo));
		//신상품입점
		model.addAttribute("recommendList7", productService.getMainRecommandProductList7(productInfo));
				
		//공지사항
		TB_PDBORDXM resultNotice = new TB_PDBORDXM();
		resultNotice.setBRD_GUBN("BRD_GUBN_05");
		model.addAttribute("resultNotice", productService.getBoardList(resultNotice));
		
		// FAQ 받아오기
		TB_PDBORDXM faq = new TB_PDBORDXM();
		faq.setBRD_GUBN("BRD_GUBN_04");
		List<TB_PDBORDXM> faqs = (List<TB_PDBORDXM>) boardService.getBoardObjectList(faq);
		model.addAttribute("faq", faqs);
		
		// 공지사항 받아오기
		TB_PDBORDXM notice = new TB_PDBORDXM();
		notice.setBRD_GUBN("BRD_GUBN_05");
		List<TB_PDBORDXM> notices = (List<TB_PDBORDXM>) boardService.getBoardObjectList(notice);
		model.addAttribute("notice", notices);
		
		/*
		TB_ODINFOXM resultNotPayCnt= new TB_ODINFOXM();
		TB_MBINFOXM loginUser = (TB_MBINFOXM)request.getSession().getAttribute("USER");
		if(loginUser !=null){
			resultNotPayCnt.setREGP_ID(loginUser.getMEMB_ID());
	
			List<TB_ODINFOXM> notPayCnt =(List<TB_ODINFOXM>) orderService.getObjectList(resultNotPayCnt);
			int cnt = 0;
			for(int i=0; i<notPayCnt.size();i++){
				if(notPayCnt.get(i).getORDER_CON().equals("ORDER_CON_01"))
					cnt++;
			}
			model.addAttribute("notPayCnt", cnt);
		}
		*/

		//############ 롤링이미지 읽어오기 (시작)_20190625 #############
		//파일 경로
		String vewInf = "jundan/main/";
		String path= (request.getSession().getServletContext().getRealPath("/")).replace("\\", "/") + "upload/" + vewInf;

		//파일 읽어오기
		File dirFile=new File(path);
		File[] fileList=dirFile.listFiles();
		
		//viewImgTest2 (foreach용)
		List<TB_JDINFOXM> jdfileinfo = new ArrayList<>();
		
		if(fileList != null){
			for(File tempFile : fileList) {				
				//file Check
				if(tempFile.isFile()) {
					//System.out.println("FilePath="+tempFile.getPath());
					
					//filePath add
					TB_JDINFOXM jdpath = new TB_JDINFOXM();		//초기화
					jdpath.setJD_LIST(tempFile.getName());
					jdfileinfo.add(jdpath);
				}
			}
			
			jundanInfo.setList(jdfileinfo);				//Rolling Image List Add
		}
		
		// 해당 경로에 파일 Count
		int fileCnt = JunDanUtil.jundanFileCnt(request, vewInf);
		
		jundanInfo.setCount(fileCnt);
		jundanInfo.setJD_PATH(vewInf);
		model.addAttribute("obj", jundanInfo);
		//############ 롤링이미지 읽어오기 (끝)_20190625 ##############	
		
		return new ModelAndView("main.layout", "jsp", "mall/main1");
	}
	

	/**
	 * ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
	 * @Class	: mall.web.controller.MainController.java
	 * @Method	: index
	 * ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
	 * @Desc	: 초기 페이지 접속시 /customers 페이지로 Redirect
	 * @Company	: YT Corp.
	 * @Author	: Byeong-gwon Gang (multiple@nate.com)
	 * @Date	: 2016-07-07 (오후 11:13:45)
	 * ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
	 * @param customers
	 * @param model
	 * @return ModelAndView
	 * @throws Exception
	 * ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
	*/
	@RequestMapping(value={"/m"}, method=RequestMethod.GET)//@RequestMapping(method=RequestMethod.GET)
	public ModelAndView responsiveMall(@ModelAttribute TB_PDINFOXM productInfo, @ModelAttribute TB_JDINFOXM jundanInfo, HttpServletRequest request, Model model) throws Exception {

		//상품목록
		productInfo.setSUPR_ID("C00002");
		productInfo.setSALE_CON("SALE_CON_01");
		
		//베스트
		//model.addAttribute("hitList", productService.getMainProductList(productInfo));
		//당일 농산물 시세
		model.addAttribute("newList", productService.getMainNewProductList(productInfo));
		//오늘만 번개세일
		model.addAttribute("recommendList8", productService.getMainRecommandProductList8(productInfo));
		//추천상품
		model.addAttribute("recommendList", productService.getMainRecommandProductList(productInfo));
		//추가할인상품
		model.addAttribute("recommendList3", productService.getMainRecommandProductList3(productInfo));
		//주점안주류
		model.addAttribute("recommendList4", productService.getMainRecommandProductList4(productInfo));
		//중식일식재료
		model.addAttribute("recommendList5", productService.getMainRecommandProductList5(productInfo));
		//재과점재료
		model.addAttribute("recommendList6", productService.getMainRecommandProductList6(productInfo));
		//신상품입점
		model.addAttribute("recommendList7", productService.getMainRecommandProductList7(productInfo));
		
		//############ 롤링이미지 읽어오기 (시작)_20190613 #############
		//파일 경로
		String vewInf = "jundan/main/";
		String path= (request.getSession().getServletContext().getRealPath("/")).replace("\\", "/") + "upload/" + vewInf;

		//파일 읽어오기
		File dirFile=new File(path);
		File[] fileList=dirFile.listFiles();
		
		//viewImgTest2 (foreach용)
		List<TB_JDINFOXM> jdfileinfo = new ArrayList<>();
		
		if(fileList != null){
			for(File tempFile : fileList) {				
				//file Check
				if(tempFile.isFile()) {
					//System.out.println("FilePath="+tempFile.getPath());
					
					//filePath add
					TB_JDINFOXM jdpath = new TB_JDINFOXM();		//초기화
					jdpath.setJD_LIST(tempFile.getName());
					jdfileinfo.add(jdpath);
				}
			}
			
			jundanInfo.setList(jdfileinfo);				//Rolling Image List Add
		}
		
		// 해당 경로에 파일 Count
		int fileCnt = JunDanUtil.jundanFileCnt(request, vewInf);
		
		jundanInfo.setCount(fileCnt);
		jundanInfo.setJD_PATH(vewInf);
		model.addAttribute("obj", jundanInfo);
		//############ 롤링이미지 읽어오기 (끝)_20190613 ##############	
				
		return new ModelAndView("main.responsive1.layout", "jsp", "main");
	}
	
	
	/**
	 * ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
	 * @Class	: mall.web.controller.MainController.java
	 * @Method	: main
	 * ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
	 * @Desc	: 관리자 메인 페이지
	 * @Company	: YT Corp.
	 * @Author	: Byeong-gwon Gang (multiple@nate.com)
	 * @Date	: 2016-07-07 (오후 11:13:45)
	 * ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
	 * @param customers
	 * @param model
	 * @return ModelAndView
	 * @throws Exception 
	 * ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
	*/
	@RequestMapping(value={"/adm", "/adm/main"}, method=RequestMethod.GET)
	public ModelAndView main(@ModelAttribute Customers customers, Model model) throws Exception {

		return new ModelAndView("admin.layout", "jsp", "admin/main");
	}		
	
	
	/**
	 * ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
	 * @Class	: mall.web.controller.mall.BasketController.java
	 * @Method	: rcmndListAjax
	 * ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
	 * @Desc	: 장바구니 수량변경
	 * @Company	: YT Corp.
	 * @Author	: HEESUN-LEE (hslee@youngthink.co.kr)
	 * @Date	: 2016-07-26 (오후 2:57:09)
	 * ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
	 * @param tb_bkinfoxm
	 * @param result
	 * @param status
	 * @param model
	 * @return
	 * @throws Exception
	 * ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
	*/
	@RequestMapping(value="/main1/rcmndListAjax",method=RequestMethod.GET)
	public ModelAndView rcmndListAjax(@ModelAttribute TB_PDRCMDXM prodRcmdInfo, Model model
			,@RequestParam(value="rcmdGubn") String rcmdGubn) throws Exception{
		//주문상태 및 결제 정보 수정
		prodRcmdInfo.setRCMD_GUBN(rcmdGubn);		
		model.addAttribute("pdList", productService.getTabRecommandProductList(prodRcmdInfo));
		
		return new ModelAndView("admin.layout", "jsp", "mall/main1");
	}
}
