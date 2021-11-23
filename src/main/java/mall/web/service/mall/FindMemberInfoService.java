package mall.web.service.mall;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import mall.web.mapper.mall.FindMemberInfoMapper;;


/**
 * ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
 * @Package	: mall.web.service.mall
 * ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
 * @Desc	: 전단 광고
 * @Company	: YT Corp.
 * @Author	: Young-dae Kim (sjie1638@youngthink.co.kr)
 * @Date	: 2016-07-27 (오후 2:08:37)
 * @Version	: 1.0
 * ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
*/
@Service("findMemberInfoService")
public class FindMemberInfoService{	
	
	@Resource(name="findMemberInfoMapper")
	FindMemberInfoMapper findMemberInfoMapper;

	
	public List<?> findID(Object obj) throws Exception {
		return findMemberInfoMapper.findID(obj);
	}
	
	public List<?> findPW(Object obj) throws Exception {
		return findMemberInfoMapper.findPW(obj);
	}
	
}
