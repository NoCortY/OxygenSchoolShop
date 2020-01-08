package com.imooc.myo2o.dao;

import static org.junit.Assert.assertEquals;

import java.util.Date;
import java.util.List;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;

import com.imooc.myo2o.BaseTest;

import cn.objectspace.schoolshop.dao.ShopAuthMapDao;
import cn.objectspace.schoolshop.entity.ShopAuthMap;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ShopAuthMapDaoTest extends BaseTest {
	@Autowired
	private ShopAuthMapDao shopAuthMapDao;

	@Test
	public void testAInsertShopAuthMap() throws Exception {
		ShopAuthMap shopAuthMap = new ShopAuthMap();
		shopAuthMap.setEmployeeId(2L);
		shopAuthMap.setShopId(2L);
		shopAuthMap.setName("test1");
		shopAuthMap.setTitle("CEO");
		shopAuthMap.setTitleFlag(1);
		shopAuthMap.setCreateTime(new Date());
		shopAuthMap.setLastEditTime(new Date());
		shopAuthMap.setEnableStatus(1);
		int effectedNum = shopAuthMapDao.insertShopAuthMap(shopAuthMap);
		assertEquals(1, effectedNum);
	}

	@Test
	public void testBQueryShopAuthMapListByShopId() throws Exception {
       List<ShopAuthMap> shopAuthMapList = shopAuthMapDao.queryShopAuthMapListByShopId(1, 1, 1);
       assertEquals(1, shopAuthMapList.size());
       shopAuthMapList = shopAuthMapDao.queryShopAuthMapListByShopId(1, 0, 2);
       assertEquals(2, shopAuthMapList.size());
       int count = shopAuthMapDao.queryShopAuthCountByShopId(1);
       assertEquals(2, count);
	}

	@Test
	public void testCUpdateShopAuthMap() throws Exception {
		ShopAuthMap shopAuthMap = new ShopAuthMap();
		shopAuthMap.setShopAuthId(1L);
		shopAuthMap.setTitle("CCO");
		shopAuthMap.setTitleFlag(2);
		int effectedNum = shopAuthMapDao.updateShopAuthMap(shopAuthMap);
		assertEquals(1, effectedNum);
	}

	@Test
	public void testDeleteShopAuthMap() throws Exception {
		long employeeId = 2;
		long shopId = 2;
		int effectedNum = shopAuthMapDao.deleteShopAuthMap(employeeId, shopId);
		assertEquals(1, effectedNum);
	}
}
