package mall.web.controller.common;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.URLEncoder;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import mall.common.util.SFTPUtil;
import mall.web.controller.DefaultController;
import mall.web.domain.DefaultDomain;
import mall.web.domain.TB_COATFLXD;
import mall.web.domain.TB_COMCODXD;
import mall.web.domain.TB_OPTCODXD;
import mall.web.domain.TB_PDINFOXM;
import mall.web.service.common.CommonService;
import mall.web.service.mall.ProductService;

@Controller
@RequestMapping(value="/common")
public class CommonController extends DefaultController{

	private static final Logger logger = LoggerFactory.getLogger(CommonController.class);

	@Resource(name="commonService")
	CommonService commonService;
	

	@Resource(name="productService")
	ProductService productService;
	
	@Autowired
	private Environment environment;
	
	/**
	 * ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
	 * @Class	: mall.web.controller.admin.CommonController.java
	 * @Method	: getList
	 * ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
	 * @Desc	: 공통코드(form 용) 조회
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
	@RequestMapping(value="/comCodForm")
	public ModelAndView getComCodForm(@ModelAttribute TB_COMCODXD comCod,  Model model) throws Exception {
		
		List<TB_COMCODXD> comCodList = null;
		comCodList = commonService.selectComCodList(comCod);
		
		comCod.setList(comCodList);
		model.addAttribute("obj", comCod);

		return new ModelAndView("blankPage", "jsp", "common/comCodForm");
	}
	
	/**
	 * ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
	 * @Class	: mall.web.controller.common.CommonController.java
	 * @Method	: getComCodForm
	 * ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
	 * @Desc	: 공통코드 코드명 조회
	 * @Company	: YT Corp.
	 * @Author	: Young-dae Kim (sjie1638@youngthink.co.kr)
	 * @Date	: 2016-07-18 (오후 8:43:46)
	 * ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
	 * @param comCod
	 * @param model
	 * @return
	 * @throws Exception
	 * ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
	*/
	@RequestMapping(value="/comCodName", method=RequestMethod.GET)
	public ModelAndView getComCodName(@ModelAttribute TB_COMCODXD comCod,  Model model) throws Exception {
		
		comCod = commonService.selectComCodName(comCod);
		model.addAttribute("obj", comCod);

		return new ModelAndView("blankPage", "jsp", "common/comCodForm");
	}
	
	

	/**
	 * 첨부파일 다운로드
	 */	
	@RequestMapping("/commonFileDown")
	public void fileDown(TB_COATFLXD commonFile, HttpServletRequest request, HttpServletResponse response) throws Exception {	

		String userAgent = request.getHeader("User-Agent");
		FileInputStream fileInputStream = null;

		logger.info("commonFileDown >> start");
		try {
			
			if( StringUtils.isNotEmpty(commonFile.getATFL_ID()) ){
				TB_COATFLXD model = (TB_COATFLXD) commonService.selectFile(commonFile);										
				
				String strSTFL_NAME = model.getSTFL_NAME().trim();		
				String originalFileName = model.getORFL_NAME().trim();
				String strSTFL_PATH = model.getSTFL_PATH().trim();
				String strFILE_EXT = model.getFILE_EXT().trim().toLowerCase();
				String savePath = (request.getSession().getServletContext().getRealPath("/")).replace("\\", "/") + "upload/" +strSTFL_PATH;
	
				if( StringUtils.isNotEmpty(commonFile.getIMG_GUBUN()) ){
					savePath = (request.getSession().getServletContext().getRealPath("/")).replace("\\", "/") + "upload/" + strSTFL_PATH  + "/" + commonFile.getIMG_GUBUN();
					File file = new File(savePath+"/"+strSTFL_NAME);
					if (file.isFile() == false) {
						savePath = (request.getSession().getServletContext().getRealPath("/")).replace("\\", "/") + "upload/" +strSTFL_PATH;
					}
					System.out.println("savePath======"+savePath);
				}
				
				//logger.info("downloadFile >> " + model.get("downloadFile").toString());
				File downloadFile = new File(savePath+"/"+strSTFL_NAME);
				/*
				File existFolder = new File(downloadFile.getParent());	
				if(!existFolder.exists()){
					existFolder.mkdirs();
				}
				//SFTP 파일 다운로드 처리
				SFTPUtil.init(environment.getRequiredProperty("sftp.IP")
						,environment.getRequiredProperty("sftp.USER")
						,environment.getRequiredProperty("sftp.PASSWORD")
						,Integer.parseInt(environment.getRequiredProperty("sftp.PORT")));
				SFTPUtil.download(environment.getRequiredProperty("sftp.FILEPATH")
						+ "/"+environment.getRequiredProperty("sftp.MALLPATH")
						+ "/upload/"+strSTFL_PATH
						//, strSTFL_NAME, savePath);
						, strSTFL_NAME, downloadFile.getCanonicalPath());
				*/
				
				// 파일다운로드를 위한 변수 설정
				String contentLength = downloadFile.length() > -1 ? String.valueOf(downloadFile.length()) : "0";
				String contentType = "application/octet-stream";
				String contentTransferEncoding = "binary";
				String contentDisposition = originalFileName != null ? originalFileName : downloadFile.getName();
	
				// 파일다운로드 처리
				if(downloadFile.isFile() && downloadFile.exists()) {
					
					response.setContentType(contentType);
					response.setBufferSize(1024 * 4);
					response.setHeader("Content-Length", contentLength);
					response.setHeader("Content-Transfer-Encoding", contentTransferEncoding);
					
					if (userAgent.indexOf("MSIE 5.5") > -1) {
						response.setContentType("doesn/matter");
						response.setHeader("Content-Disposition", "attachment; filename=\"" + URLEncoder.encode(contentDisposition, "UTF-8").replace("+", "%20") + "\";");
					} else if (userAgent.indexOf("MSIE") > -1) {
						response.setHeader("Content-Disposition", "attachment; filename=\"" + URLEncoder.encode(contentDisposition, "UTF-8").replace("+", "%20") + "\";");
					} else {
						response.setHeader("Content-Disposition", "attachment; filename=\"" + URLEncoder.encode(contentDisposition, "UTF-8").replace("+", "%20") + "\";");
					}
					
					fileInputStream = new FileInputStream(downloadFile.getCanonicalPath());					
					
					if(fileInputStream != null) {
						FileCopyUtils.copy(fileInputStream,  response.getOutputStream());
						//FileCopyUtils.copy(inputStream,  response.getOutputStream());
						response.flushBuffer();
						response.getOutputStream().flush();
						fileInputStream.close();
						//inputStream.close();
					}
					
				} else {
					//logger.info("fileNotFoundMessage >> 1");
					logger.info("fileNotFoundMessage1 >> " + originalFileName);
				}
				
			}else{
				//logger.info("fileNotFoundMessage >> 2");
				logger.info("fileNotFoundMessage >> ATFL_ID가 없습니다.");
				
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			fileInputStream = null;
		}
	}
	
	

	/**
	 * 첨부파일 다운로드
	 */	
	@RequestMapping("/commonFileDownBak")
	public void fileDownBak(TB_COATFLXD commonFile, HttpServletRequest request, HttpServletResponse response) throws Exception {	

		if( StringUtils.isNotEmpty(commonFile.getATFL_ID()) ){	
			TB_COATFLXD model = (TB_COATFLXD) commonService.selectFile(commonFile);										
			
			String strSTFL_NAME = model.getSTFL_NAME().trim();		
			String originalFileName = model.getORFL_NAME().trim();
			String strSTFL_PATH = model.getSTFL_PATH().trim();
			String strFILE_EXT = model.getFILE_EXT().trim().toLowerCase();
			String savePath = (request.getSession().getServletContext().getRealPath("/")).replace("\\", "/") + "upload/" +strSTFL_PATH;

			if( StringUtils.isNotEmpty(commonFile.getIMG_GUBUN()) ){
				savePath = (request.getSession().getServletContext().getRealPath("/")).replace("\\", "/") + "upload/" + strSTFL_PATH  + "/" + commonFile.getIMG_GUBUN();
				File file = new File(savePath+"/"+strSTFL_NAME);
				if (file.isFile() == false) {
					savePath = (request.getSession().getServletContext().getRealPath("/")).replace("\\", "/") + "upload/" +strSTFL_PATH;
				}
			}

			/*
			if(strFILE_EXT.equals(".jpg") || strFILE_EXT.equals(".gif") || strFILE_EXT.equals(".bmp") || strFILE_EXT.equals(".png")){
				File file = new File(savePath+"/"+strSTFL_NAME);
				if (file.isFile() == false) {
					
				}
				
			}
			*/

			byte fileByte[] = FileUtils.readFileToByteArray(new File(savePath+"/"+strSTFL_NAME));

			response.setContentType("application/octet-stream");
			response.setContentLength(fileByte.length);
			response.setHeader("Content-Disposition", "attachment; fileName=\"" + URLEncoder.encode(originalFileName,"UTF-8")+"\";");
			response.setHeader("Content-Transfer-Encoding", "binary");
			response.getOutputStream().write(fileByte);
			
			response.getOutputStream().flush();
			response.getOutputStream().close();
		}
	}
	
	

	/**
	 * 파일삭제
	 */
	@RequestMapping("/commonFileDelete")
	public @ResponseBody String commonFileDelete(TB_COATFLXD commonFile, HttpServletRequest request) throws Exception {

		// 조회
		int nCnt = commonService.deleteFile(commonFile);
		
		return nCnt+"";
	}
	

	@RequestMapping(value="/helpDesk", method=RequestMethod.GET)
	public ModelAndView helpDesk(@ModelAttribute DefaultDomain defaultdomain,  Model model) throws Exception {

		return new ModelAndView("popup.layout", "jsp", "mall/helpDesk/view");
	}
	

	@RequestMapping(value="/hitList", method=RequestMethod.GET)
	public ModelAndView hitList(@ModelAttribute TB_PDINFOXM productInfo,  Model model) throws Exception {

		//상품목록
		productInfo.setSUPR_ID("C00002");
		productInfo.setSALE_CON("SALE_CON_01");
		
		//베스트
		model.addAttribute("hitList", productService.getMainProductList(productInfo));
		
		return new ModelAndView("blankPage", "jsp", "mall/common/hitList");
	}
	
	/**
	 * ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
	 * @Class	: mall.web.controller.admin.CommonController.java
	 * @Method	: getList
	 * ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
	 * @Desc	: 공통코드(form 용) 조회
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
	@RequestMapping(value="/optCodForm")
	public ModelAndView getOptCodForm(@ModelAttribute TB_OPTCODXD optCod,  Model model) throws Exception {
		
		List<TB_OPTCODXD> optCodList = null;
		optCodList = commonService.selectOptCodList(optCod);
		
		optCod.setList(optCodList);
		model.addAttribute("obj", optCod);

		return new ModelAndView("blankPage", "jsp", "common/optCodForm");
	}
}
