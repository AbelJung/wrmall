package mall.web.service.admin.impl;

import java.util.List;
import java.util.StringTokenizer;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import mall.web.domain.TB_ODDLAIXM;
import mall.web.domain.TB_ODINFOXM;
import mall.web.mapper.admin.OrderMgrMapper;
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
@Service("orderMgrService")
public class OrderMgrService implements DefaultService {

	@Resource(name="orderMgrMapper")
	OrderMgrMapper orderMgrMapper;
	
	@Override
	public int getObjectCount(Object obj) throws Exception {
		return orderMgrMapper.count(obj);
	}
	
	@Override
	public List<?> getObjectList(Object obj) throws Exception {
		return orderMgrMapper.list(obj);
	}

	@Override
	public List<?> getPaginatedObjectList(Object obj) throws Exception {
		return orderMgrMapper.paginatedList(obj);
	}

	@Override
	public Object getObject(Object obj) throws Exception {
		return orderMgrMapper.find(obj);
	}

	@Override
	public int insertObject(Object obj) throws Exception {
		return orderMgrMapper.insert(obj);
	}

	@Override
	public int updateObject(Object obj) throws Exception {
		return orderMgrMapper.update(obj);
	}

	@Override
	public int deleteObject(Object obj) throws Exception {
		return orderMgrMapper.delete(obj);
	}
	
	//일괄 주문 상태 변경
	public void updateStateObject(TB_ODINFOXM tb_odinfoxm) throws Exception {
		StringTokenizer stok = new StringTokenizer(tb_odinfoxm.getORDER_CON_ORDER_NUM(),"$");
		while(stok.hasMoreTokens()){
			String str = stok.nextToken();
			TB_ODINFOXM obj = new TB_ODINFOXM();
			obj.setORDER_NUM(str);
			obj.setORDER_CON_STATE(tb_odinfoxm.getORDER_CON_STATE());
			obj.setMODP_ID(tb_odinfoxm.getMODP_ID());
			orderMgrMapper.updateState(obj);
		}
	}
	
	//주문정보 마스터 정보 - 상세화면
	public Object getMasterInfo(TB_ODINFOXM tb_odinfoxm) throws Exception {
		return orderMgrMapper.getMasterInfo(tb_odinfoxm);
	}
	
	//주문정보 상세 - 상세화면  
	public List<?> getDetailsList(TB_ODINFOXM tb_odinfoxm) throws Exception {
		return orderMgrMapper.getDetailsList(tb_odinfoxm);
	}
	
	//배송지 정보 - 상세화면
	public Object getDeliveryInfo(TB_ODINFOXM tb_odinfoxm) throws Exception {
		return orderMgrMapper.getDeliveryInfo(tb_odinfoxm);
	}
	
	/*//주문상태 및 결제 정보 수정
	public void getDetailsUpdate(Object obj) throws Exception {
		orderMgrMapper.getDetailsUpdate(obj);
	}*/
	public void getDetailsUpdate(TB_ODINFOXM tb_odinfoxm, TB_ODDLAIXM tb_oddlaixm) throws Exception {
		//주문상태 및 결제 정보 수정
		orderMgrMapper.getDetailsUpdate(tb_odinfoxm);
		//배송정보 수정
		orderMgrMapper.getDeliveryUpdate(tb_oddlaixm);
	}
	
	/*//배송정보 수정
	public void getDeliveryUpdate(Object obj) throws Exception {
		orderMgrMapper.getDeliveryUpdate(obj);
	}*/
	
	//피킹리스트
	public List<?> getPickingList(TB_ODINFOXM tb_odinfoxm) throws Exception{
		return orderMgrMapper.pickingList(tb_odinfoxm);
		
	}
	//피킹리스트 - 상품별
	public List<?> getPickingGoodsList(TB_ODINFOXM tb_odinfoxm) throws Exception{
		return orderMgrMapper.pickingGoodsList(tb_odinfoxm);
		
	}
	//피킹리스트 - 매출처별
	public List<?> getPickingComList(TB_ODINFOXM tb_odinfoxm) throws Exception{
		return orderMgrMapper.pickingComList(tb_odinfoxm);
		
	}
	//피킹리스트 - 차량별
	public List<?> getPickingCarList(TB_ODINFOXM tb_odinfoxm) throws Exception{
		return orderMgrMapper.pickingCarList(tb_odinfoxm);
		
	}
	//피킹리스트 주문상태 업데이트
	public void updatePickingList(String num) throws Exception{
		orderMgrMapper.updatePickingList(num);
	}
	
	public List<?> getExcelList(Object obj) throws Exception {
		return orderMgrMapper.excelList(obj);
	}
	
	public List<?> getExcelAllList(Object obj) throws Exception {
		return orderMgrMapper.excelAllList(obj);
	}
	
	public List<?> getDetailExcelList(Object obj) throws Exception {
		return orderMgrMapper.detailExcelList(obj);
	}

	//주문내역삭제
	public void deleteOrdList(String num) throws Exception{
		orderMgrMapper.deleteOrdList(num);
	}
	//주문내역상세삭제
	public void deleteOrdDetail(String num) throws Exception{
		orderMgrMapper.deleteOrdDetail(num);
	}
	
	//주문내역삭제
	public void updateDatailQty(Object obj) throws Exception{
		orderMgrMapper.updateDatailQty(obj);
	}
	//주문내역상세삭제
	public void updateMasterQty(Object obj) throws Exception{
		orderMgrMapper.updateMasterQty(obj);
	}
	
	//테스트ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ
	//피킹리스트 - 상품별
	public List<?> getPickingGoodsExcel(TB_ODINFOXM tb_odinfoxm) throws Exception{
		return orderMgrMapper.pickingGoodsExcel(tb_odinfoxm);
		
	}
	//피킹리스트 - 매출처별
	public List<?> getPickingComExcel(TB_ODINFOXM tb_odinfoxm) throws Exception{
		return orderMgrMapper.pickingComExcel(tb_odinfoxm);
		
	}
	//피킹리스트 - 차량별
	public List<?> getPickingCarExcel(TB_ODINFOXM tb_odinfoxm) throws Exception{
		return orderMgrMapper.pickingCarExcel(tb_odinfoxm);
		
	}
	//결제전 내역 삭제
	public int getObjectDeleteCount(Object obj) throws Exception {
		return orderMgrMapper.deleteCount(obj);
	}

	public List<?> getPaginatedDeleteObjectList(Object obj) throws Exception {
		return orderMgrMapper.paginatedDeleteList(obj);
	}
	
	//주문내역삭제복원
	public void getDeleteReturnOrdList(String num) throws Exception{
		orderMgrMapper.deleteReturnOrdList(num);
	}
	public void getDeleteReturnOrdDtlList(String num) throws Exception{
		orderMgrMapper.deleteReturnOrdDtlList(num);
	}
}