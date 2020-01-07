package com.imooc.myo2o.dao;

import static org.junit.Assert.assertEquals;

import java.util.Date;
import java.util.List;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;

import com.imooc.myo2o.BaseTest;
import com.imooc.myo2o.entity.UserProductMap;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class UserProductMapDaoTest extends BaseTest {
	@Autowired
	private UserProductMapDao userProductMapDao;

	@Test
	public void testAInsertUserProductMap() throws Exception {
		UserProductMap userProductMap = new UserProductMap();
		userProductMap.setUserId(1L);
		userProductMap.setProductId(1L);
		userProductMap.setShopId(1L);
		userProductMap.setUserName("test");
		userProductMap.setProductName("优乐美");
		userProductMap.setCreateTime(new Date());
		int effectedNum = userProductMapDao
				.insertUserProductMap(userProductMap);
		assertEquals(1, effectedNum);
		userProductMap.setUserId(2L);
		userProductMap.setProductId(1L);
		userProductMap.setShopId(1L);
		userProductMap.setUserName("test2");
		userProductMap.setProductName("冰淇淋");
		userProductMap.setCreateTime(new Date());
		effectedNum = userProductMapDao.insertUserProductMap(userProductMap);
		assertEquals(1, effectedNum);
	}

	@Test
	public void testBQueryUserProductMapList() throws Exception {
		UserProductMap userProductMap = new UserProductMap();

		List<UserProductMap> userProductMapList = userProductMapDao
				.queryUserProductMapList(userProductMap, 0, 3);
		assertEquals(2, userProductMapList.size());
		int count = userProductMapDao.queryUserProductMapCount(userProductMap);
		assertEquals(2, count);
		userProductMap.setUserName("test");
		userProductMapList = userProductMapDao.queryUserProductMapList(
				userProductMap, 0, 3);
		assertEquals(2, userProductMapList.size());
		count = userProductMapDao.queryUserProductMapCount(userProductMap);
		assertEquals(2, count);
		userProductMap.setUserId(1L);
		userProductMapList = userProductMapDao.queryUserProductMapList(
				userProductMap, 0, 3);
		assertEquals(1, userProductMapList.size());
		count = userProductMapDao.queryUserProductMapCount(userProductMap);
		assertEquals(1, count);
	}
}
