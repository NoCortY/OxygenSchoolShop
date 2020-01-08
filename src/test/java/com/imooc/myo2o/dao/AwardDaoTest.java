package com.imooc.myo2o.dao;

import static org.junit.Assert.assertEquals;

import java.util.Date;
import java.util.List;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;

import com.imooc.myo2o.BaseTest;
import com.imooc.myo2o.dao.AwardDao;
import com.imooc.myo2o.entity.Award;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class AwardDaoTest extends BaseTest {

	@Autowired
	private AwardDao awardDao;

	@Test
	public void testAInsertAward() throws Exception {
		long shopId = 2;
		Award award = new Award();
		award.setAwardName("测试一");
		award.setAwardImg("test1");
		award.setPoint(5);
		award.setPriority(1);
		award.setEnableStatus(1);
		award.setCreateTime(new Date());
		award.setLastEditTime(new Date());
		award.setExpireTime(new Date());
		award.setShopId(shopId);
		int effectedNum = awardDao.insertAward(award);
		assertEquals(1, effectedNum);
	}

	@Test
	public void testBQueryAwardList() throws Exception {
		Award award = new Award();
		List<Award> awardList = awardDao.queryAwardList(award, 0, 3);
		assertEquals(2, awardList.size());
		int count = awardDao.queryAwardCount(award);
		assertEquals(2, count);
		award.setAwardName("测试");
		awardList = awardDao.queryAwardList(award, 0, 3);
		assertEquals(1, awardList.size());
		count = awardDao.queryAwardCount(award);
		assertEquals(1, count);
	}

	@Test
	public void testCQueryAwardByAwardId() throws Exception {
		Award awardCondition = new Award();
		awardCondition.setAwardName("测试");
		List<Award> awardList = awardDao.queryAwardList(awardCondition, 0, 1);
		assertEquals(1, awardList.size());
		Award award = awardDao.queryAwardByAwardId(awardList.get(0)
				.getAwardId());
		assertEquals("测试一", award.getAwardName());
	}

	@Test
	public void testDUpdateAward() throws Exception {
		Award award = new Award();
		award.setAwardId(1L);
		award.setAwardName("第一个奖品");
		int effectedNum = awardDao.updateAward(award);
		assertEquals(1, effectedNum);
	}

	@Test
	public void testEDeleteAward() throws Exception {
		Award awardCondition = new Award();
		awardCondition.setAwardName("测试");
		List<Award> awardList = awardDao.queryAwardList(awardCondition, 0, 1);
		assertEquals(1, awardList.size());
		int effectedNum = awardDao.deleteAward(awardList.get(0).getAwardId());
		assertEquals(1, effectedNum);
	}
}
