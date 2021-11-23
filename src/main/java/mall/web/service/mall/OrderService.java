package mall.web.service.mall;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import mall.web.domain.TB_BKINFOXM;
import mall.web.domain.TB_ODDLAIXM;
import mall.web.domain.TB_ODINFOXD;
import mall.web.domain.TB_ODINFOXM;
import mall.web.mapper.mall.OrderMapper;
import mall.web.service.DefaultService;

/**
 * ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
 * @Package	: mall.web.service.mall
 * ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
 * @Desc	: 주문 내역
 * @Company	: YT Corp.
 * @Author	: Young-dae Kim (sjie1638@youngthink.co.kr)
 * @Date	: 2016-07-27 (오후 2:08:37)
 * @Version	: 1.0
 * ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
*/
@Service("orderService")
public class OrderService implements DefaultService{	
	
	@Resource(name="orderMapper")
	OrderMapper orderMapper;

	@Override
	public int getObjectCount(Object obj) throws Exception {
		return orderMapper.count(obj);
	}
	
	@Override
	public List<?> getObjectList(Object obj) throws Exception {
		return orderMapper.list(obj);
	}

	@Override
	public List<?> getPaginatedObjectList(Object obj) throws Exception {
		return orderMapper.paginatedList(obj);
	}

	@Override
	public Object getObject(Object obj) throws Exception {
		return orderMapper.find(obj);
	}

	@Override
	public int insertObject(Object obj) throws Exception {
		return orderMapper.insert(obj);
	}

	@Override
	public int updateObject(Object obj) throws Exception {
		return orderMapper.update(obj);
	}

	@Override
	public int deleteObject(Object obj) throws Exception {
		return orderMapper.delete(obj);
	}
	
	@Transactional
	public int insertOrderObject(TB_ODINFOXM obj, TB_ODINFOXD obj2, TB_ODDLAIXM obj3) throws Exception {
		int updateCnt = 0; 
		orderMapper.odInfoXmInsert(obj);	//결제전
		
		for(int i=0; i<obj2.getPD_CODES().length; i++){
		
			TB_ODINFOXD tb_odinfoxd = new TB_ODINFOXD();

			tb_odinfoxd.setORDER_NUM(obj.getORDER_NUM());
			tb_odinfoxd.setPD_CODE(obj2.getPD_CODES()[i]);
			tb_odinfoxd.setPD_NAME(obj2.getPD_NAMES()[i]);
			tb_odinfoxd.setPD_PRICE(obj2.getPD_PRICES()[i]);
			tb_odinfoxd.setPDDC_GUBN(obj2.getPDDC_GUBNS()[i]);
			tb_odinfoxd.setPDDC_VAL(obj2.getPDDC_VALS()[i]);
			tb_odinfoxd.setORDER_QTY(obj2.getORDER_QTYS()[i]);
			tb_odinfoxd.setORDER_AMT(obj2.getORDER_AMTS()[i]);
			tb_odinfoxd.setREGP_ID(obj.getREGP_ID());
			if(obj2.getPD_CUT_SEQS().length>0){
				tb_odinfoxd.setPD_CUT_SEQ(obj2.getPD_CUT_SEQS()[i]);
			}else{
				tb_odinfoxd.setPD_CUT_SEQ("");
			}
			
			if(obj2.getOPTION_CODES().length>0){
				tb_odinfoxd.setOPTION_CODE(obj2.getOPTION_CODES()[i]);
			}else{
				tb_odinfoxd.setOPTION_CODE("");
			}
			
			/*박스할인율*/
			if(obj2.getBOX_PDDC_GUBNS().length>0){
				tb_odinfoxd.setBOX_PDDC_GUBN(obj2.getBOX_PDDC_GUBNS()[i]);
			}else{
				tb_odinfoxd.setBOX_PDDC_GUBN("");
			}
			if(obj2.getBOX_PDDC_VALS().length>0){
				tb_odinfoxd.setBOX_PDDC_VAL(obj2.getBOX_PDDC_VALS()[i]);
			}else{
				tb_odinfoxd.setBOX_PDDC_VAL("");
			}
			if(obj2.getINPUT_CNTS().length>0){
				tb_odinfoxd.setINPUT_CNT(obj2.getINPUT_CNTS()[i]);
			}else{
				tb_odinfoxd.setINPUT_CNT("");
			}

			updateCnt += orderMapper.odInfoXdInsert(tb_odinfoxd); //결제완료
		
		}

		obj3.setORDER_NUM(obj.getORDER_NUM());
		
		orderMapper.odDlaxiXmInsert(obj3); //배송지 정소 insert
		
		for(int i=0; i<obj2.getPD_CODES().length; i++){
			TB_BKINFOXM tb_bkinfoxm = new TB_BKINFOXM();

			tb_bkinfoxm.setPD_CODE(obj2.getPD_CODES()[i]);
			tb_bkinfoxm.setREGP_ID(obj.getREGP_ID());
			
			orderMapper.bkInfoXmDelete(tb_bkinfoxm);
		}
		
		return updateCnt;
	}
	
	public List<?> getNewObjectList(List list) throws Exception {
		return orderMapper.newList(list);
	}
	
	public List<?> getBuyObjectList(List list) throws Exception {
		return orderMapper.buyList(list);
	}
	
	//주문정보 마스터 정보 - 상세화면
	public Object getMasterInfo(TB_ODINFOXM tb_odinfoxm) throws Exception {
		return orderMapper.getMasterInfo(tb_odinfoxm);
	}
	
	//주문정보 상세 - 상세화면  
	public List<?> getDetailsList(TB_ODINFOXM tb_odinfoxm) throws Exception {
		return orderMapper.getDetailsList(tb_odinfoxm);
	}
	
	//배송지 정보 - 상세화면
	public Object getDeliveryInfo(TB_ODINFOXM tb_odinfoxm) throws Exception {
		return orderMapper.getDeliveryInfo(tb_odinfoxm);
	}
	
	public Object getMbDlvyInfo(Object obj) throws Exception {
		return orderMapper.getMbDlvyInfo(obj);
	}
	public Object getSpDlvyInfo(Object obj) throws Exception {
		return orderMapper.getSpDlvyInfo(obj);
	}
	public Object getDlvyInfo(Object obj) throws Exception {
		return orderMapper.getDlvyInfo(obj);
	}
	public Object getSfDlvyInfo(Object obj) throws Exception {
		return orderMapper.getSfDlvyInfo(obj);
	}
	public Object getCtDlvyInfo(Object obj) throws Exception {
		return orderMapper.getCtDlvyInfo(obj);
	}
	
	public int cancelObject(Object obj) throws Exception {
		return orderMapper.orderCancel(obj);
	}
	public int orderCnt(Object obj) throws Exception {
		return orderMapper.orderCnt(obj);
	}
	

	public int orderPayUpdate(Object obj) throws Exception {
		return orderMapper.orderPayUpdate(obj);
	}
	
	public int danalLogInsert(Object obj) throws Exception {
		return orderMapper.danalLogInsert(obj);
	}
	
	public void orderDlvyUpdate(Object obj) throws Exception {
		orderMapper.orderDlvyUpdate(obj);
	}
	
	public void orderUpdateDelete(String num) throws Exception{
		orderMapper.orderUpdateDelete(num);
	}
	
	public void orderDlvyDateUpdate(Object obj) throws Exception{
		orderMapper.orderDlvyDateUpdate(obj);
	}
}
