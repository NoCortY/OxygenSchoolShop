package com.imooc.myo2o.service;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;

import com.imooc.myo2o.BaseTest;
import com.imooc.myo2o.dto.ProductCategoryExecution;
import com.imooc.myo2o.entity.ProductCategory;
import com.imooc.myo2o.enums.ProductCategoryStateEnum;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ProductCategoryServiceTest extends BaseTest {
	@Autowired
	private ProductCategoryService productCategoryService;

	@Test
	public void testAAddProductCategory() throws Exception {
		ProductCategory productCategory = new ProductCategory();
		productCategory.setProductCategoryName("商品类别1");
		productCategory.setProductCategoryDesc("测试商品类别");
		productCategory.setPriority(1);
		productCategory.setCreateTime(new Date());
		productCategory.setLastEditTime(new Date());
		productCategory.setShopId(1L);
		ProductCategory productCategory2 = new ProductCategory();
		productCategory2.setProductCategoryName("商品类别2");
		productCategory2.setProductCategoryDesc("测试商品类别2");
		productCategory2.setPriority(2);
		productCategory2.setCreateTime(new Date());
		productCategory2.setLastEditTime(new Date());
		productCategory2.setShopId(1L);
		List<ProductCategory> productCategoryList = new ArrayList<ProductCategory>();
		productCategoryList.add(productCategory);
		productCategoryList.add(productCategory2);
		ProductCategoryExecution productCategoryExecution = productCategoryService
				.batchAddProductCategory(productCategoryList);
		assertEquals(ProductCategoryStateEnum.SUCCESS.getStateInfo(),
				productCategoryExecution.getStateInfo());
	}

	@Test
	public void testBGetByShopId() throws Exception {
		long shopId = 1;
		List<ProductCategory> productCategoryList = productCategoryService
				.getByShopId(shopId);
		assertEquals(2, productCategoryList.size());
		System.out.println(productCategoryList.get(0).toString());

	}

	@Test
	public void testCDeleteProductCategory() throws Exception {
		long shopId = 1;
		List<ProductCategory> productCategoryList = productCategoryService
				.getByShopId(shopId);
		ProductCategoryExecution productCategoryExecution = productCategoryService
				.deleteProductCategory(productCategoryList.get(0)
						.getProductCategoryId(), shopId);
		assertEquals(ProductCategoryStateEnum.SUCCESS.getStateInfo(),
				productCategoryExecution.getStateInfo());
		productCategoryExecution = productCategoryService
				.deleteProductCategory(productCategoryList.get(1)
						.getProductCategoryId(), shopId);
		assertEquals(ProductCategoryStateEnum.SUCCESS.getStateInfo(),
				productCategoryExecution.getStateInfo());
	}

}
