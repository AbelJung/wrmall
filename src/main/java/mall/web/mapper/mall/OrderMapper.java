package mall.web.mapper.mall;

import java.util.List;

/**
 * ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
 * @Package	: mall.web.mapper.mall
 * ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
 * @Desc	: 주문 내역
 * @Company	: YT Corp.
 * @Author	: Young-dae Kim (sjie1638@youngthink.co.kr)
 * @Date	: 2016-07-27 (오후 2:08:09)
 * @Version	: 1.0
 * ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
*/
public interface OrderMapper {
	public int count(Object obj) throws Exception;
	public List<?> list(Object obj) throws Exception;
	public List<?> paginatedList(Object obj) throws Exception;
	public Object find(Object obj) throws Exception;
	public int insert(Object obj) throws Exception;
	public int update(Object obj) throws Exception;
	public int delete(Object obj) throws Exception;
	
	public int odInfoXmInsert(Object obj) throws Exception;
	public int odInfoXdInsert(Object obj) throws Exception;
	public int odDlaxiXmInsert(Object obj) throws Exception;
	public int bkInfoXmDelete(Object obj) throws Exception;
	
	public List<?> newList(List list) throws Exception;
	public List<?> buyList(Object obj) throws Exception;
	public Object getMasterInfo(Object obj) throws Exception;
	public List<?> getDetailsList(Object obj) throws Exception;
	public Object getDeliveryInfo(Object obj) throws Exception;

	public Object getMbDlvyInfo(Object obj) throws Exception;
	public Object getSpDlvyInfo(Object obj) throws Exception;
	public Object getDlvyInfo(Object obj) throws Exception;
	public Object getSfDlvyInfo(Object obj) throws Exception;
	public Object getCtDlvyInfo(Object obj) throws Exception;
	
	public int orderCancel(Object obj) throws Exception;
	public int orderCnt(Object obj) throws Exception;

	public int orderPayUpdate(Object obj) throws Exception;
	public int danalLogInsert(Object obj) throws Exception;
	
	public void orderDlvyUpdate(Object obj) throws Exception;
	
	public void orderUpdateDelete(String num) throws Exception;
	
	public void orderDlvyDateUpdate(Object obj) throws Exception;
	
}
