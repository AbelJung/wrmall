package mall.web.service.common.impl;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import mall.common.util.SFTPUtil;
import mall.common.util.FTPUtil;
import mall.common.util.FileUtil;
import mall.web.domain.TB_COATFLXD;
import mall.web.domain.TB_COMCODXD;
import mall.web.domain.TB_OPTCODXD;
import mall.web.domain.TB_SYSMNUXM;
import mall.web.mapper.common.CommonMapper;
import mall.web.service.common.CommonService;

/**
 * ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
 * @Package	: mall.web.service
 * ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
 * @Desc	: 공통기능 Service
 * @Company	: YT Corp.
 * @Author	: Byeong-gwon Gang (multiple@nate.com)
 * @Date	: 2016-07-07 (오후 11:07:25)
 * @Version	: 1.0
 * ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
*/
@Service("commonService")
public class CommonServiceImpl implements CommonService {
	
	@Resource(name="commonMapper")
	CommonMapper commonMapper;
	
	@Autowired
	private Environment environment;
	
	/**
	 * ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
	 * @Class	: mall.web.service.CommonServiceImpl.java
	 * @Method	: selectCustomersList
	 * ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
	 * @Desc	: 공통코드 조회
	 * @Company	: YT Corp.
	 * @Author	: Byeong-gwon Gang (multiple@nate.com)
	 * @Date	: 2016-07-07 (오후 11:06:30)
	 * ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
	 * @see mall.web.service.CommonService#selectComCodList(mall.web.domain.TB_COMCODXD)
	 * ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
	*/
	@Override
	public List<TB_COMCODXD> selectComCodList(TB_COMCODXD comCod) throws Exception {
		return commonMapper.selectComCodList(comCod);
	}


	/**
	 * ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
	 * @Class	: mall.web.service.common.impl.CommonServiceImpl.java
	 * @Method	: selectComCodName
	 * ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
	 * @Desc	: 공통코드 코드명 조회
	 * @Company	: YT Corp.
	 * @Author	: Young-dae Kim (sjie1638@youngthink.co.kr)
	 * @Date	: 2016-07-18 (오후 8:45:49)
	 * ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
	 * @see mall.web.service.common.CommonService#selectComCodName(mall.web.domain.TB_COMCODXD)
	 * ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
	*/
	@Override
	public TB_COMCODXD selectComCodName(TB_COMCODXD comCod) throws Exception {
		return commonMapper.selectComCodName(comCod);
	}

	
	/**
	 * ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
	 * @Class	: mall.web.service.CommonServiceImpl.java
	 * @Method	: selectNowMenu
	 * ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
	 * @Desc	: 현재 접속 메뉴 조회
	 * @Company	: YT Corp.
	 * @Author	: Byeong-gwon Gang (multiple@nate.com)
	 * @Date	: 2016-07-07 (오후 11:06:30)
	 * ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
	 * @see mall.web.service.CommonService#selectNowMenu(mall.web.domain.TB_SYSMNUXM)
	 * ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
	*/
	@Override
	public TB_SYSMNUXM selectNowMenu(TB_SYSMNUXM sysMenu) throws Exception {
		return commonMapper.selectNowMenu(sysMenu);
	}

	/**
	 * ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
	 * @Class	: mall.web.service.CommonServiceImpl.java
	 * @Method	: selectCustomersList
	 * ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
	 * @Desc	: 고객목록 조회
	 * @Company	: YT Corp.
	 * @Author	: Byeong-gwon Gang (multiple@nate.com)
	 * @Date	: 2016-07-07 (오후 11:06:30)
	 * ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
	 * @see mall.web.service.CommonService#selectUserMenuList(mall.web.domain.TB_SYSMNUXM)
	 * ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
	*/
	@Override
	public List<TB_SYSMNUXM> selectUserMenuList(TB_SYSMNUXM sysMenu) throws Exception {
		return commonMapper.selectUserMenuList(sysMenu);
	}
	

	/**
	 * 파일저장
	 * bOriginalFilename - 원본 파일명 사용여부 true 원본파일명 사용
	 */
	@Override
	public String saveFile(TB_COATFLXD commonFile, HttpServletRequest request, MultipartHttpServletRequest multipartRequest, String objName, String filePath, Boolean bOriginalFilename) throws Exception {

		List<MultipartFile> fileList = multipartRequest.getFiles(objName);
		int fileOrdr = 0;

		String strRtnATFL_ID = commonFile.getATFL_ID();
		// 파일이 등록되어있지 않은 경우
		if ( !StringUtils.isNotEmpty(commonFile.getATFL_ID()) ) {
			// 파일첨부필드에 값이 있으면
			if ( StringUtils.isNotEmpty(fileList.get(0).getOriginalFilename()) ) {
				// 파일 MST 등록
				commonMapper.insertFileMaster(commonFile);
				strRtnATFL_ID = commonFile.getATFL_ID();
			}
		}
		
		if ( StringUtils.isNotEmpty(fileList.get(0).getOriginalFilename()) ) {
			// 파일 상세 등록
			for (MultipartFile multipartFile : fileList) {
				if ( StringUtils.isNotEmpty( multipartFile.getOriginalFilename()) ) {
					fileOrdr++;
					String saveFileName = FileUtil.saveFile2(request, multipartFile, filePath+"/"+strRtnATFL_ID, bOriginalFilename);
					/*
					SFTPUtil.init(environment.getRequiredProperty("sftp.IP")
							,environment.getRequiredProperty("sftp.USER")
							,environment.getRequiredProperty("sftp.PASSWORD")
							,Integer.parseInt(environment.getRequiredProperty("sftp.PORT")));
					String saveFileName = SFTPUtil.upload(environment.getRequiredProperty("sftp.MALLPATH"),multipartFile,filePath+"/"+strRtnATFL_ID, bOriginalFilename);
					*/
					commonFile.setORFL_NAME(multipartFile.getOriginalFilename());											// 원본파일명
					commonFile.setFILE_EXT(saveFileName.substring(saveFileName.lastIndexOf("."), saveFileName.length()));	// 파일 확장자
					commonFile.setSTFL_NAME(saveFileName);																	// 스토리지 파일 명
					commonFile.setSTFL_PATH(filePath+"/"+strRtnATFL_ID);														// 스토리지 파일 경로
					commonFile.setATFL_SEQ("" + fileOrdr);
					commonFile.setFILE_SIZE("" + multipartFile.getSize());
					//commonFile.setATFL_ID(strRtnATFL_ID);
					
					commonFile.setFILEPATH_FLAG("woori");	//우리마트 이미지 플래그(임시)
					commonMapper.insertFileDetail(commonFile);
				}
			}
		}
		
		return strRtnATFL_ID;
	}

	@Override
	public List<?> selectFileList(Object obj) throws Exception {
		return commonMapper.selectFileList(obj);
	}
	
	@Override
	public Object selectFile(Object obj) throws Exception {
		return commonMapper.selectFile(obj);
	}

	@Override
	public int deleteFile(Object obj) throws Exception {
		return commonMapper.deleteFile(obj);
	}


	@Override
	public List<TB_OPTCODXD> selectOptCodList(TB_OPTCODXD optCod) throws Exception {
		// TODO Auto-generated method stub
		return commonMapper.selectOptCodList(optCod);
	}
}
