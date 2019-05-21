package com.SchoolShop.o2o.service;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Date;

import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.SchoolShop.o2o.BaseTest;
import com.SchoolShop.o2o.dto.ShopExecution;
import com.SchoolShop.o2o.entity.Area;
import com.SchoolShop.o2o.entity.PersonInfo;
import com.SchoolShop.o2o.entity.Shop;
import com.SchoolShop.o2o.entity.ShopCategory;
import com.SchoolShop.o2o.enums.ShopStateEnum;
import com.SchoolShop.o2o.exceptions.ShopOperationException;

public class ShopServiceTest extends BaseTest{
	@Autowired
	private ShopService shopService;
	@Test
	public void testGetShopList() {
		Shop shopCondition = new Shop();
		ShopCategory sc = new ShopCategory();
		sc.setShopCategoryId(2L);
		shopCondition.setShopCategory(sc);
		ShopExecution se = shopService.getShopList(shopCondition, 0, 2);
		System.out.println("店铺列表数为:"+se.getShopList().size());
		System.out.println("店铺总数:"+se.getCount());
	}
	@Test
	@Ignore
	public void testModifyShop() throws ShopOperationException,FileNotFoundException{
		Shop shop = new Shop();
		shop.setShopId(1L);
		shop.setShopName("修改后的店铺名称");
		File shopImg = new File("G:/图片/2.jpg");
	//	ShopExecution shopExecution = shopService.modifyShop(shop, shopImg, "2.jpg");
		//System.out.println("新的图片地址为"+shopExecution.getShop().getShopImg());
	}
	@Test
	@Ignore
	public void testAddShop() {
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
		shop.setShopDesc("test1");
		shop.setShopAddr("test1");
		shop.setCreateTime(new Date());
		shop.setEnableStatus(ShopStateEnum.CHECK.getState());
		shop.setAdvice("审核中");
		//File shopImg = new File("G:/程序/Java程序/o2o/src/main/resources/doctornews.png");
		//ShopExecution se = shopService.addShop(shop, shopImg);
		//assertEquals(ShopStateEnum.CHECK.getState(),se.getState());
	}

}
