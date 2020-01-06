package com.SchoolShop.o2o.dao;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.FixMethodOrder;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;

import com.SchoolShop.o2o.BaseTest;
import com.SchoolShop.o2o.entity.ProductImg;
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ProductImgDaoTest extends BaseTest{
	@Autowired
	private ProductImgDao productImgDao;
	//Autowired注入Spring中 Mybatis实现类(Spring整合Mybatis)
	@Test
	public void testABatchInsertProductImg() throws Exception{
		ProductImg productImg1 = new ProductImg();
		productImg1.setImgAddr("11");
		productImg1.setImgDesc("111");
		productImg1.setPriority(1);
		productImg1.setCreateTime(new Date());
		productImg1.setProductId(1L);
		
		
		ProductImg productImg2 = new ProductImg();
		productImg2.setImgAddr("22");
		productImg2.setImgDesc("222");
		productImg2.setPriority(2);
		productImg2.setCreateTime(new Date());
		productImg2.setProductId(1L);
		
		
		List<ProductImg> productImgList = new ArrayList<ProductImg>();
		productImgList.add(productImg1);
		productImgList.add(productImg2);
		
		int effectedNum = productImgDao.batchInsertProductImg(productImgList);
		assertEquals(2,effectedNum);
	}
	@Test
	public void testCDeleteProductImgByProductId() throws Exception{
		//删除新增的两条商品详情图片记录
		long productId = 1;
		int effectedNum = productImgDao.deleteProductImgByProductId(productId);
		assertEquals(2,effectedNum);
	}
	
}
