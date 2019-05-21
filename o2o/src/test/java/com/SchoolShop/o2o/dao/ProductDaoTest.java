package com.SchoolShop.o2o.dao;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.FixMethodOrder;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;

import com.SchoolShop.o2o.BaseTest;
import com.SchoolShop.o2o.entity.Product;
import com.SchoolShop.o2o.entity.ProductCategory;
import com.SchoolShop.o2o.entity.ProductImg;
import com.SchoolShop.o2o.entity.Shop;
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ProductDaoTest extends BaseTest {
	@Autowired
	private ProductDao productDao;
	@Autowired
	private ProductImgDao productImgDao;
	@Test
	public void testAInsertProduct() throws Exception{
		Shop shop1 = new Shop();
		shop1.setShopId(1L);
		ProductCategory pc1 = new ProductCategory();
		pc1.setProductCategoryId(1L);
		//初始化三个商品实力并添加进shopId为1的店铺里，同时商品类别id也为1
		Product product1 = new Product();
		product1.setProductName("测试1");
		product1.setProductDesc("测试Desc1");
		product1.setImgAddr("test1");
		product1.setPriority(1);
		product1.setEnableStatus(1);
		product1.setCreateTime(new Date());
		product1.setLastEditTime(new Date());
		product1.setShop(shop1);
		product1.setProductCategory(pc1);
		Product product2 = new Product();
		product2.setProductName("测试2");
		product2.setProductDesc("测试Desc2");
		product2.setImgAddr("test2");
		product2.setPriority(2);
		product2.setEnableStatus(2);
		product2.setCreateTime(new Date());
		product2.setLastEditTime(new Date());
		product2.setShop(shop1);
		product2.setProductCategory(pc1);
		int effectedNum = productDao.insertProduct(product1);
		assertEquals(1,effectedNum);
		effectedNum = productDao.insertProduct(product2);
		assertEquals(1,effectedNum);
	}
	@Test
	public void testCQueryProductByProductId() throws Exception{
		long productId = 1L;
		//初始化两个商品详情图实例作为productId为1的商品下的详情图片
		//批量插入到商品详情图表中
		ProductImg productImg1 = new ProductImg();
		productImg1.setImgAddr("图片1");
		productImg1.setImgDesc("测试图片1");
		productImg1.setPriority(1);
		productImg1.setCreateTime(new Date());
		productImg1.setProductId(productId);
		
		
		ProductImg productImg2 = new ProductImg();
		productImg2.setImgAddr("图片2");
		productImg2.setImgDesc("测试图片2");
		productImg2.setPriority(1);
		productImg2.setCreateTime(new Date());
		productImg2.setProductId(productId);
		
		
		List<ProductImg> productImgList = new ArrayList<ProductImg>();
		productImgList.add(productImg1);
		productImgList.add(productImg2);
		int effectedNum = productImgDao.batchInsertProductImg(productImgList);
		assertEquals(2,effectedNum);
		Product product = productDao.queryProductById(productId);
		assertEquals(2,product.getProductImgList().size());
		effectedNum = productImgDao.deleteProductImgByProductId(productId);
		assertEquals(2,effectedNum);
	}
	@Test
	public void testDUpdateProduct() throws Exception{
		Product product = new Product();
		ProductCategory pc = new ProductCategory();
		Shop shop = new Shop();
		shop.setShopId(1L);
		pc.setShopId(1L);
		pc.setProductCategoryId(2L);
		product.setProductId(1L);
		product.setShop(shop);
		product.setProductName("第1个产品");
		product.setProductCategory(pc);
		//修改productId为1的商品的名称
		//以及商品类别并校验影响的行数是否为
		int effectedNum = productDao.updateProduct(product);
		assertEquals(1,effectedNum);
	}
}
