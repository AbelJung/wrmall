package mall.web.mapper.admin;

import java.util.List;

import mall.web.domain.Customers;

/**
 * ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
 * @Package	: mall.web.mapper
 * ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
 * @Desc	: 제품정보 Mapper
 * @Company	: YT Corp.
 * @Author	: Tae-seok Choi (tschoi@youngthink.co.kr)
 * @Date	: 2016-07-07 (오후 11:09:58)
 * @Version	: 1.0
 * ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
*/
public interface ProductMgrMapper {
	public int count(Object obj) throws Exception;
	public List<?> list(Object obj) throws Exception;
	public List<?> paginatedList(Object obj) throws Exception;
	public Object find(Object obj) throws Exception;
	public int insert(Object obj) throws Exception;
	public int update(Object obj) throws Exception;
	public int delete(Object obj) throws Exception;
	
	public int deletePrd(Object obj) throws Exception;
	
	public int goodsMasterCount(Object obj) throws Exception;
	public List<?> goodsMasterList(Object obj) throws Exception;
	public int pdCodeChk(Object obj) throws Exception;
	public int pdBarCodeChk(Object obj) throws Exception;

	public List<?> excelList(Object obj) throws Exception;
	
	public List<?> proCutList(Object obj) throws Exception;
	public int proCutInsert(Object obj) throws Exception;
	
	public int listUpdate(Object obj) throws Exception;
	
	public int excelUpload(Object obj) throws Exception;
	public int excelUpdate_pdPrice(Object obj) throws Exception;

	// temp backup
	public int tmpBackUp(Object obj) throws Exception;
	public int delete2(Object obj) throws Exception;
}
