package com.imooc.myo2o.dao;

import static org.junit.Assert.assertEquals;

import java.util.Date;
import java.util.List;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;

import com.imooc.myo2o.BaseTest;
import com.imooc.myo2o.dao.ShopCategoryDao;
import com.imooc.myo2o.entity.ShopCategory;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ShopCategoryDaoTest extends BaseTest {
	@Autowired
	private ShopCategoryDao shopCategoryDao;

	@Test
	public void testAInsertShopCategory() throws Exception {
		ShopCategory shopCategory = new ShopCategory();
		shopCategory.setShopCategoryName("店铺类别1");
		shopCategory.setShopCategoryDesc("测试商品类别");
		shopCategory.setPriority(1);
		shopCategory.setCreateTime(new Date());
		shopCategory.setLastEditTime(new Date());
		shopCategory.setParentId(1L);
		int effectedNum = shopCategoryDao.insertShopCategory(shopCategory);
		assertEquals(1, effectedNum);
	}

	@Test
	public void testBQueryShopCategory() throws Exception {
		ShopCategory sc = new ShopCategory();
		List<ShopCategory> shopCategoryList = shopCategoryDao
				.queryShopCategory(sc);
		assertEquals(3, shopCategoryList.size());
		sc.setParentId(1L);
		shopCategoryList = shopCategoryDao.queryShopCategory(sc);
		assertEquals(1, shopCategoryList.size());
		sc.setParentId(null);
		sc.setShopCategoryId(0L);
		shopCategoryList = shopCategoryDao.queryShopCategory(sc);
		assertEquals(2, shopCategoryList.size());

	}

	@Test
	public void testCUpdateShopCategory() throws Exception {
		ShopCategory shopCategory = new ShopCategory();
		shopCategory.setShopCategoryId(1L);
		shopCategory.setShopCategoryName("把妹");
		shopCategory.setShopCategoryDesc("把妹");
		shopCategory.setLastEditTime(new Date());
		int effectedNum = shopCategoryDao.updateShopCategory(shopCategory);
		assertEquals(1, effectedNum);
	}

	@Test
	public void testDDeleteShopCategory() throws Exception {
		ShopCategory sc = new ShopCategory();
		sc.setParentId(1L);
		List<ShopCategory> shopCategoryList = shopCategoryDao
				.queryShopCategory(sc);
		long shopCategoryId = shopCategoryList.get(0).getShopCategoryId();
		int effectedNum = shopCategoryDao.deleteShopCategory(shopCategoryId);
		assertEquals(1, effectedNum);
	}

}
