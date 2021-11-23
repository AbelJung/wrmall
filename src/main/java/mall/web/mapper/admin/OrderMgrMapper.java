package mall.web.mapper.admin;

import java.util.List;

/**
 * ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
 * @Package	: mall.web.mapper
 * ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
 * @Desc	: 주문내역 Mapper
 * @Company	: YT Corp.
 * @Author	: Young-dae Kim (sjie1638@youngthink.co.kr)
 * @Date	: 2016-07-13 (오후 03:49:22)
 * @Version	: 1.0
 * ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
*/
public interface OrderMgrMapper {
	public int count(Object obj) throws Exception;
	public List<?> list(Object obj) throws Exception;
	public List<?> paginatedList(Object obj) throws Exception;
	public Object find(Object obj) throws Exception;
	public int insert(Object obj) throws Exception;
	public int update(Object obj) throws Exception;
	public int delete(Object obj) throws Exception;
	
	public int updateState(Object obj) throws Exception;
	public Object getMasterInfo(Object obj) throws Exception;
	public List<?> getDetailsList(Object obj) throws Exception;
	public Object getDeliveryInfo(Object obj) throws Exception;
	
	public int getDetailsUpdate(Object obj) throws Exception;
	public int getDeliveryUpdate(Object obj) throws Exception;
	
	public List<?> pickingList(Object obj) throws Exception;
	public List<?> pickingGoodsList(Object obj) throws Exception;
	public List<?> pickingComList(Object obj) throws Exception;
	public List<?> pickingCarList(Object obj) throws Exception;
	public int updatePickingList(String num) throws Exception;
	
	public List<?> excelList(Object obj) throws Exception;
	public List<?> excelAllList(Object obj) throws Exception;
	public List<?> detailExcelList(Object obj) throws Exception;
	
	
	public int deleteOrdList(String num) throws Exception;
	public int deleteOrdDetail(String num) throws Exception;
	
	public int updateDatailQty(Object obj) throws Exception;
	public int updateMasterQty(Object obj) throws Exception;
	
	
	
	public List<?> pickingGoodsExcel(Object obj) throws Exception;
	public List<?> pickingComExcel(Object obj) throws Exception;
	public List<?> pickingCarExcel(Object obj) throws Exception;
	
	public int deleteCount(Object obj) throws Exception;
	public List<?> paginatedDeleteList(Object obj) throws Exception;
	
	public int deleteReturnOrdList(String num) throws Exception;
	public int deleteReturnOrdDtlList(String num) throws Exception;
	
}
