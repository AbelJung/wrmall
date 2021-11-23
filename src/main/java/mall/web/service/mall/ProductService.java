package mall.web.service.mall;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import mall.web.mapper.mall.ProductMapper;
import mall.web.service.DefaultService;

/**
 * ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
 * @Package	: mall.web.service.mall
 * ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
 * @Desc	: 사용자 상품
 * @Company	: YT Corp.
 * @Author	: Young-dae Kim (sjie1638@youngthink.co.kr)
 * @Date	: 2016-07-27 (오후 2:08:37)
 * @Version	: 1.0
 * ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
*/
@Service("productService")
public class ProductService implements DefaultService{	
	
	@Resource(name="productMapper")
	ProductMapper productMapper;

	@Override
	public int getObjectCount(Object obj) throws Exception {
		return productMapper.count(obj);
	}
	
	@Override
	public List<?> getObjectList(Object obj) throws Exception {
		return productMapper.list(obj);
	}

	@Override
	public List<?> getPaginatedObjectList(Object obj) throws Exception {
		return productMapper.paginatedList(obj);
	}

	@Override
	public Object getObject(Object obj) throws Exception {
		return productMapper.find(obj);
	}

	@Override
	public int insertObject(Object obj) throws Exception {
		return productMapper.insert(obj);
	}

	@Override
	public int updateObject(Object obj) throws Exception {
		return productMapper.update(obj);
	}

	@Override
	public int deleteObject(Object obj) throws Exception {
		return productMapper.delete(obj);
	}
	
	public List<?> getCagoList(Object obj) throws Exception {
		return productMapper.cagoList(obj);
	}
	
	public List<?> getCagoDownList(Object obj) throws Exception {
		return productMapper.cagoDownList(obj);
	}
	
	public Object getCagoObject(Object obj) throws Exception {
		return productMapper.cagoFind(obj);
	}
	
	public List<?> getCagoAllList(Object obj) throws Exception {
		return productMapper.cagoAllList(obj);
	}
	
	public List<?> getBoardList(Object obj) throws Exception {
		return productMapper.boardList(obj);
	}

	public int basketInst(Object obj) throws Exception {
		return productMapper.basketInst(obj);
	}
	
	public int basketSelect(Object obj) throws Exception {
		return productMapper.basketSelect(obj);
	}
	
	public int wishInst(Object obj) throws Exception {
		return productMapper.wishInst(obj);
	}
	
	public int wishSelect(Object obj) throws Exception {
		return productMapper.wishSelect(obj);
	}
	
	public List<?> getMainProductList(Object obj) throws Exception {
		return productMapper.mainProductList(obj);
	}
	
	public List<?> getMainNewProductList(Object obj) throws Exception {
		return productMapper.mainNewProductList(obj);
	}
	public List<?> getMainRecommandProductList(Object obj) throws Exception {
		return productMapper.mainRecommandProductList(obj);
	}
	public List<?> getMainRecommandProductList3(Object obj) throws Exception {
		return productMapper.mainRecommandProductList3(obj);
	}
	public List<?> getMainRecommandProductList4(Object obj) throws Exception {
		return productMapper.mainRecommandProductList4(obj);
	}
	public List<?> getMainRecommandProductList5(Object obj) throws Exception {
		return productMapper.mainRecommandProductList5(obj);
	}
	public List<?> getMainRecommandProductList6(Object obj) throws Exception {
		return productMapper.mainRecommandProductList6(obj);
	}
	public List<?> getMainRecommandProductList7(Object obj) throws Exception {
		return productMapper.mainRecommandProductList7(obj);
	}
	public List<?> getMainRecommandProductList8(Object obj) throws Exception {
		return productMapper.mainRecommandProductList8(obj);
	}
	public List<?> getProCutList(Object obj) throws Exception {
		return productMapper.proCutList(obj);
	}
	public List<?> getProCutFind(List list) throws Exception {
		return productMapper.proCutFind(list);
	}
	public List<?> getTabRecommandProductList(Object obj) throws Exception {
		return productMapper.tabRecommandProductList(obj);
	}
	
	public int getRetailCount(Object obj) throws Exception {
		return productMapper.retailCount(obj);
	}
	public List<?> getRetailProductList(Object obj) throws Exception {
		return productMapper.paginatedRetailList(obj);
	}
	
	public Object getSalePrcGrpInfo(Object obj) throws Exception {
		return productMapper.salePrcGrpInfo(obj);
	}
	
	
}
