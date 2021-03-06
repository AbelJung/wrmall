package mall.web.service.admin.impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.util.SystemOutLogger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import mall.web.domain.TB_ADPDIFXM;
import mall.web.domain.TB_PDINFOXM;
import mall.web.domain.TB_PDRCMDXM;
import mall.web.mapper.admin.ProductEventMgrMapper;
import mall.web.mapper.admin.ProductMgrMapper;
import mall.web.service.DefaultService;

/**
 * ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
 * @Package	: mall.web.service
 * ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
 * @Desc	: 제품정보 관리 Service
 * @Company	: YT Corp.
 * @Author	: Byeong-gwon Gang (multiple@nate.com)
 * @Date	: 2016-07-07 (오후 11:07:25)
 * @Version	: 1.0
 * ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
*/
@Service("productEventMgrService")
public class ProductEventMgrService implements DefaultService {

	@Resource(name="productEventMgrMapper")
	ProductEventMgrMapper productEventMgrMapper;
	
	@Override
	public int getObjectCount(Object obj) throws Exception {
		return productEventMgrMapper.count(obj);
	}
	
	@Override
	public List<?> getObjectList(Object obj) throws Exception {	//행사상품목록
		return productEventMgrMapper.list(obj);
	}

	@Override
	public List<?> getPaginatedObjectList(Object obj) throws Exception {
		return productEventMgrMapper.paginatedList(obj);
	}

	@Override
	public Object getObject(Object obj) throws Exception {
		return productEventMgrMapper.find(obj);
	}

	@Override
	public int insertObject(Object obj) throws Exception {
		return productEventMgrMapper.insert(obj);
	}

	@Override
	public int updateObject(Object obj) throws Exception {	//행사상품등록
		return productEventMgrMapper.update(obj);
	}

	@Override
	public int deleteObject(Object obj) throws Exception {
		return productEventMgrMapper.delete(obj);
		
	}
	
	// 엑셀 다운로드
	public List<?> getDetailExcelList(Object obj) throws Exception {
		return productEventMgrMapper.detailExcelList(obj);
	}
	
	// 엑셀 업로드
	public List<TB_PDINFOXM> excelUpload(Object obj,  List<Map<String, String>>excelContent) throws Exception {
		
		TB_PDINFOXM tb_pdinfoxm = (TB_PDINFOXM) obj;				
		List<TB_PDINFOXM> listPD = new ArrayList<TB_PDINFOXM>();	// 예외 반환용 리스트
		
        for(Map<String, String> article: excelContent){
        	
        	if(StringUtils.isNotEmpty(article.get("A"))) {
        		TB_PDINFOXM paramObj = (TB_PDINFOXM) tb_pdinfoxm;
        		paramObj.setPD_BARCD(article.get("A"));
				paramObj.setPDDC_VAL(article.get("C"));
				
				//할인구분값
				int pdgb_ck = Integer.parseInt(article.get("C").toString());
				if(pdgb_ck == 0){	//할인금액이 0일경우
					paramObj.setPDDC_GUBN("PDDC_GUBN_01");		//할인안함
				}else{
					paramObj.setPDDC_GUBN("PDDC_GUBN_02");		//할인금액
				}
				
				try{					
					//중복되어 있는 바코드 수 반환 : 조건 - 바코드, 사용여부 Y
					int bcdCnt = productEventMgrMapper.excelUploadChk_BarcodeCount(paramObj) ;
					// 바코드가 일치하는 상품이 없음, 바코드가 일치하는 상품 2개이상
					if( bcdCnt != 1) {
						TB_PDINFOXM pd = new TB_PDINFOXM();
						pd.setPD_BARCD(article.get("A"));
						pd.setPD_NAME(article.get("B"));
						
						if(bcdCnt == 0) {
							pd.setERROR_CODE("-101");
							pd.setERROR_TEXT("상품이 존재하지 않습니다.");
						} else {
							pd.setERROR_CODE("-102");
							pd.setERROR_TEXT("바코드가 중복된 상품입니다. ( " + bcdCnt + " 개 )");
						}
						
						listPD.add(pd);
						continue;
					}
					// 행사가 업데이트
					productEventMgrMapper.excelUpload(paramObj);	
					
				}catch(SQLException e){				
				}catch(Exception ex){					
				}
        	}
        }
        // errorList 반환
		return listPD;
	}
	
	public int updateProduct(Object obj) throws Exception {
		return productEventMgrMapper.updateProduct(obj);		
	}
	
	public int deleteProduct(Object obj) throws Exception{
		return productEventMgrMapper.deleteProduct(obj);
	}
	
	public int getOrdrSameCnt(Object obj) throws Exception {
		return productEventMgrMapper.ordrSameCnt(obj);
	}
	
	public int getGrpPdCnt(Object obj) throws Exception {
		return productEventMgrMapper.grpPdCnt(obj);
	}

}
