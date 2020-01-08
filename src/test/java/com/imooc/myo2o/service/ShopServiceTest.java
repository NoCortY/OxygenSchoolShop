package com.imooc.myo2o.service;

import java.util.List;

import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.imooc.myo2o.BaseTest;
import com.imooc.myo2o.dto.ShopExecution;
import com.imooc.myo2o.entity.Shop;
import com.imooc.myo2o.service.ShopService;

public class ShopServiceTest extends BaseTest {
	@Autowired
	private ShopService shopService;

//	@Test
//	public void testAddShop() throws Exception {
//		Shop shop = new Shop();
//		shop.setOwnerId(1L);
//		Area area = new Area();
//		area.setAreaId(1L);
//		ShopCategory sc = new ShopCategory();
//		sc.setShopCategoryId(1L);
//		shop.setShopName("mytest1");
//		shop.setShopDesc("mytest1");
//		shop.setShopAddr("testaddr1");
//		shop.setPhone("13810524526");
//		shop.setShopImg("test1");
//		shop.setLongitude(1D);
//		shop.setLatitude(1D);
//		shop.setCreateTime(new Date());
//		shop.setLastEditTime(new Date());
//		shop.setEnableStatus(0);
//		shop.setAdvice("审核中");
//		shop.setArea(area);
//		shop.setShopCategory(sc);
//		ShopExecution se = shopService.addShop(shop);
//		assertEquals("mytest1", se.getShop().getShopName());
//	}


	@Test
	public void testGetByEmployeeId() throws Exception {
		long employeeId = 1;
		ShopExecution shopExecution = shopService.getByEmployeeId(employeeId);
		List<Shop> shopList = shopExecution.getShopList();
		for (Shop shop : shopList) {
			System.out.println(shop);
		}
	}

	@Ignore
	@Test
	public void testGetByShopId() throws Exception {
		long shopId = 1;
		Shop shop = shopService.getByShopId(shopId);
		System.out.println(shop);
	}

}
