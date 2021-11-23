package mall.web.mapper.mall;

import java.util.List;

/**
 * ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
 * @Package	: mall.web.mapper.mall
 * ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
 * @Desc	: 관심상품
 * @Company	: YT Corp.
 * @Author	: Young-dae Kim (sjie1638@youngthink.co.kr)
 * @Date	: 2016-07-25 (오후 3:15:16)
 * @Version	: 1.0
 * ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
*/
public interface ProductMapper {

	public int count(Object obj) throws Exception;
	public List<?> list(Object obj) throws Exception;
	public List<?> paginatedList(Object obj) throws Exception;
	public Object find(Object obj) throws Exception;
	public int insert(Object obj) throws Exception;
	public int update(Object obj) throws Exception;
	public int delete(Object obj) throws Exception;
	
	public List<?> cagoList(Object obj) throws Exception;
	public List<?> cagoDownList(Object obj) throws Exception;
	public Object cagoFind(Object obj) throws Exception;
	public List<?> cagoAllList(Object obj) throws Exception;
	
	public List<?> boardList(Object obj) throws Exception;
	public int basketInst(Object obj) throws Exception;
	public int basketSelect(Object obj) throws Exception;
	public int wishInst(Object obj) throws Exception;
	public int wishSelect(Object obj) throws Exception;
	
	public List<?> mainProductList(Object obj) throws Exception;
	public List<?> mainNewProductList(Object obj) throws Exception;
	public List<?> mainRecommandProductList(Object obj) throws Exception;
	public List<?> mainRecommandProductList3(Object obj) throws Exception;
	public List<?> mainRecommandProductList4(Object obj) throws Exception;
	public List<?> mainRecommandProductList5(Object obj) throws Exception;
	public List<?> mainRecommandProductList6(Object obj) throws Exception;
	public List<?> mainRecommandProductList7(Object obj) throws Exception;
	public List<?> mainRecommandProductList8(Object obj) throws Exception;
	
	
	
	public List<?> proCutList(Object obj) throws Exception;
	public List<?> proCutFind(List list) throws Exception;
	
	public List<?> tabRecommandProductList(Object obj) throws Exception;
	
	public int retailCount(Object obj) throws Exception;
	public List<?> paginatedRetailList(Object obj) throws Exception;
	
	public Object salePrcGrpInfo(Object obj) throws Exception;
}
