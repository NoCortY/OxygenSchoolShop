package com.imooc.myo2o.dao;

import static org.junit.Assert.assertEquals;

import java.util.Date;
import java.util.List;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;

import com.imooc.myo2o.BaseTest;

import cn.objectspace.schoolshop.dao.UserAwardMapDao;
import cn.objectspace.schoolshop.entity.UserAwardMap;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class UserAwardMapDaoTest extends BaseTest {
	@Autowired
	private UserAwardMapDao userAwardMapDao;

	@Test
	public void testAInsertUserAwardMap() throws Exception {
		UserAwardMap userAwardMap = new UserAwardMap();
		userAwardMap.setUserId(1L);
		userAwardMap.setAwardId(1L);
		userAwardMap.setShopId(1L);
		userAwardMap.setUserName("test");
		userAwardMap.setAwardName("第一个奖品");
		userAwardMap.setCreateTime(new Date());
		userAwardMap.setUsedStatus(1);
		int effectedNum = userAwardMapDao.insertUserAwardMap(userAwardMap);
		assertEquals(1, effectedNum);
		userAwardMap.setUserId(2L);
		userAwardMap.setAwardId(1L);
		userAwardMap.setShopId(1L);
		userAwardMap.setUserName("test2");
		userAwardMap.setAwardName("第二个奖品");
		userAwardMap.setCreateTime(new Date());
		userAwardMap.setUsedStatus(0);
		effectedNum = userAwardMapDao.insertUserAwardMap(userAwardMap);
		assertEquals(1, effectedNum);
	}

	@Test
	public void testBQueryUserAwardMapList() throws Exception {
		UserAwardMap userAwardMap = new UserAwardMap();

		List<UserAwardMap> userAwardMapList = userAwardMapDao
				.queryUserAwardMapList(userAwardMap, 0, 3);
		assertEquals(2, userAwardMapList.size());
		int count = userAwardMapDao.queryUserAwardMapCount(userAwardMap);
		assertEquals(2, count);
		userAwardMap.setUserName("test");
		userAwardMapList = userAwardMapDao.queryUserAwardMapList(userAwardMap,
				0, 3);
		assertEquals(2, userAwardMapList.size());
		count = userAwardMapDao.queryUserAwardMapCount(userAwardMap);
		assertEquals(2, count);
		userAwardMap.setUserId(1L);
		userAwardMapList = userAwardMapDao.queryUserAwardMapList(userAwardMap,
				0, 3);
		assertEquals(1, userAwardMapList.size());
		count = userAwardMapDao.queryUserAwardMapCount(userAwardMap);
		assertEquals(1, count);
	}
}
