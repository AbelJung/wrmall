package mall.web.service.admin.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import mall.web.domain.TB_ODINFOXD;
import mall.web.domain.TB_ODINFOXM;
import mall.web.domain.TB_RTODINFOXD;
import mall.web.domain.TB_RTODINFOXM;
import mall.web.mapper.admin.ReturnOrderMgrMapper;
import mall.web.service.DefaultService;


/**
 * ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
 * @Package	: mall.web.service
 * ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
 * @Desc	: 주문내역 Service
 * @Company	: YT Corp.
 * @Author	: Young-dae Kim (sjie1638@youngthink.co.kr)
 * @Date	: 2016-07-13 (오후 03:49:40)
 * @Version	: 1.0
 * ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
*/
@Service("returnOrderMgrService")
public class ReturnOrderMgrService implements DefaultService {

	@Resource(name="returnOrderMgrMapper")
	ReturnOrderMgrMapper returnOrderMgrMapper;
	
	@Override
	public int getObjectCount(Object obj) throws Exception {
		return returnOrderMgrMapper.count(obj);
	}
	
	@Override
	public List<?> getObjectList(Object obj) throws Exception {
		return returnOrderMgrMapper.list(obj);
	}

	@Override
	public List<?> getPaginatedObjectList(Object obj) throws Exception {
		return returnOrderMgrMapper.paginatedList(obj);
	}

	@Override
	public Object getObject(Object obj) throws Exception {
		return returnOrderMgrMapper.find(obj);
	}
	@Override
	public int insertObject(Object obj) throws Exception {
		// TODO Auto-generated method stub
		return returnOrderMgrMapper.insert(obj);
	}

	public int insertRtOrdObject(TB_RTODINFOXM master, TB_RTODINFOXD detail) throws Exception {
		int updateCnt = 0; 
		returnOrderMgrMapper.rtOdInfoXmInsert(master);	
		
		for(int i=0; i<detail.getPD_CODES().length; i++){
			
			TB_RTODINFOXD tb_rtodinfoxd = new TB_RTODINFOXD();
			
			tb_rtodinfoxd.setRETURN_NUM(master.getRETURN_NUM());
			tb_rtodinfoxd.setPD_CODE(detail.getPD_CODES()[i]);
			tb_rtodinfoxd.setPD_NAME(detail.getPD_NAMES()[i]);
			tb_rtodinfoxd.setPD_PRICE(detail.getPD_PRICES()[i]);
			tb_rtodinfoxd.setPDDC_GUBN(detail.getPDDC_GUBNS()[i]);
			tb_rtodinfoxd.setPDDC_VAL(detail.getPDDC_VALS()[i]);
			tb_rtodinfoxd.setRETURN_QTY(detail.getRETURN_QTYS()[i]);
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
			tb_rtodinfoxd.setRETURN_GBN(detail.getRETURN_GBNS()[i]);
			tb_rtodinfoxd.setREGP_ID(detail.getREGP_ID());
			tb_rtodinfoxd.setMODP_ID(detail.getMODP_ID());
			tb_rtodinfoxd.setORDER_DTNUM(detail.getORDER_DTNUMS()[i]);
			//세절방식 잠시 보류....ㅠ.ㅠ..
			/*if(obj2.getPD_CUT_SEQS().length>0){
				tb_odinfoxd.setPD_CUT_SEQ(obj2.getPD_CUT_SEQS()[i]);
			}else{
				tb_odinfoxd.setPD_CUT_SEQ("");
			}*/
	
			updateCnt += returnOrderMgrMapper.rtOdInfoXdInsert(tb_rtodinfoxd);
		}
		
		
		return updateCnt;
	}

	@Override
	public int updateObject(Object obj) throws Exception {
		return returnOrderMgrMapper.update(obj);
	}

	@Override
	public int deleteObject(Object obj) throws Exception {
		return returnOrderMgrMapper.delete(obj);
	}
	
	//주문정보 마스터 정보 - 상세화면
	public Object getMasterInfo(TB_RTODINFOXM tb_odinfoxm) throws Exception {
		return returnOrderMgrMapper.getMasterInfo(tb_odinfoxm);
	}
	
	//주문정보 상세 - 상세화면  
	public List<?> getDetailsList(TB_RTODINFOXM tb_odinfoxm) throws Exception {
		return returnOrderMgrMapper.getDetailsList(tb_odinfoxm);
	}
	
	public int updateMasterObject(Object obj) throws Exception {
		return returnOrderMgrMapper.updateMaster(obj);
	}
	
	// 주문번호에 해당하는 반품내역 Count
	public int getOrdCount(Object obj) throws Exception {
		return returnOrderMgrMapper.ordCheck(obj);
	}
	
}