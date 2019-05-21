package com.SchoolShop.o2o.dao;

import java.util.Date;
import java.util.List;

import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.SchoolShop.o2o.BaseTest;
import com.SchoolShop.o2o.entity.Area;
import com.SchoolShop.o2o.entity.PersonInfo;
import com.SchoolShop.o2o.entity.Shop;
import com.SchoolShop.o2o.entity.ShopCategory;
import static org.junit.Assert.assertEquals;
/*
 * @Test 是Junit中的注解，表示测试本函数
 * @Ignore表示不测试本函数
 * */
public class ShopDaoTest extends BaseTest{
	@Autowired
	private ShopDao shopDao;
	@Test
	public void testQueryShopListAnd() {
		Shop shopCondition = new Shop();
		PersonInfo owner = new PersonInfo();
		owner.setUserId(1L);
		shopCondition.setOwner(owner);
		List<Shop> shopList = shopDao.queryShopList(shopCondition, 0, 5);
		int count = shopDao.queryShopCount(shopCondition);
		System.out.println("店铺列表的大小" + shopList.size());
		System.out.println("店铺总数" + count);
		ShopCategory sc = new ShopCategory();
		sc.setShopCategoryId(2L);
		shopCondition.setShopCategory(sc);
		shopList = shopDao.queryShopList(shopCondition, 0, 5);
		count = shopDao.queryShopCount(shopCondition);
		System.out.println("店铺列表的大小" + shopList.size());
		System.out.println("店铺总数" + count);
	}
	@Test
	@Ignore
	public void testQueryByShopId(){
		long shopId = 1;
		Shop shop = shopDao.queryByShopId(shopId);
		System.out.println("areaId:"+shop.getArea().getAreaId());
		System.out.println("areaName"+shop.getArea().getAreaName());
	}
	@Test
	@Ignore
	public void testInsertShop() {
		Shop shop = new Shop();
		PersonInfo owner = new PersonInfo();
		Area area = new Area();
		ShopCategory shopCategory = new ShopCategory();
		owner.setUserId(1L);
		area.setAreaId(2);
		shopCategory.setShopCategoryId(1L);
		shop.setOwner(owner);
		shop.setShopCategory(shopCategory);
		shop.setShopName("测试的店铺");
		shop.setShopDesc("test");
		shop.setShopAddr("test");
		shop.setShopImg("test");
		shop.setCreateTime(new Date());
		shop.setEnableStatus(1);
		shop.setAdvice("审核中");
		int effectedNum = shopDao.insertShop(shop);
		assertEquals(1,effectedNum);
	}
	@Test
	@Ignore
	public void testUpdateShop() {
		Shop shop = new Shop();
		shop.setShopId(1L);
		shop.setShopDesc("测试描述");
		shop.setShopAddr("测试地址");
		shop.setPhone("123123123");
		shop.setLastEditTime(new Date());
		int effectedNum = shopDao.updateShop(shop);
		assertEquals(1,effectedNum);
	}

}
