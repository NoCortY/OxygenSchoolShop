package com.SchoolShop.o2o.service;

import static org.junit.Assert.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.fileupload.FileItem;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.SchoolShop.o2o.BaseTest;
import com.SchoolShop.o2o.dto.ProductExecution;
import com.SchoolShop.o2o.entity.Product;
import com.SchoolShop.o2o.entity.ProductCategory;
import com.SchoolShop.o2o.entity.Shop;
import com.SchoolShop.o2o.enums.ProductStateEnum;
import com.SchoolShop.o2o.exceptions.ShopOperationException;

public class ProductServiceTest extends BaseTest{
	@Autowired
	private ProductService productService;
	@Test
	@Ignore
	public void testAddProduct()throws ShopOperationException,FileNotFoundException{
		//创建shopId为1且productCategoryId为1的商品实例并给其成员变量赋值
		Product product = new Product();
		Shop shop = new Shop();
		shop.setShopId(1L);
		ProductCategory pc = new ProductCategory();
		pc.setProductCategoryId(1L);
		product.setShop(shop);
		product.setProductCategory(pc);
		product.setProductName("测试商品1");
		product.setProductDesc("测试商品1");
		product.setPriority(20);
		product.setCreateTime(new Date());
		product.setEnableStatus(ProductStateEnum.SUCCESS.getState());
		
		File imageFile = new File("G:/程序/Java程序/o2o/src/main/resources/doctor.png");
		File productImg1 = new File("G:/程序/Java程序/o2o/src/main/resources/doctornews.png");
		File productImg2 = new File("G:/程序/Java程序/o2o/src/main/resources/doctornews.png");
		List<File> productImgList = new ArrayList<File>();
		productImgList.add(productImg1);
		productImgList.add(productImg2);
		//ProductExecution pe = productService.addProduct(product, imageFile, productImgList);
		//assertEquals(ProductStateEnum.SUCCESS.getState(),pe.getState());
	}
	@Test
	public void testModifyProduct() throws ShopOperationException,FileNotFoundException{
		//创建shopId为1且productCategory为1的商品实例并给其成员变量赋值
		Product product = new Product();
		Shop shop = new Shop();
		shop.setShopId(1L);
		ProductCategory pc = new ProductCategory();
		pc.setProductCategoryId(1L);
		product.setProductId(1L);
		product.setShop(shop);
		product.setProductCategory(pc);
		product.setProductName("正式的商品");
		product.setProductDesc("正式的商品");
		//创建缩略图文件流
		CommonsMultipartFile img = new CommonsMultipartFile((FileItem)new File("G:/程序/Java程序/image/upload/item/shop/1/2019041522142972214.jpg"));
		File thumbnailFile = new File("G:/程序/Java程序/image/upload/item/shop/1/2019041522142972214.jpg");
		InputStream is = new FileInputStream(thumbnailFile);
		
		
		//创建两个商品详情图文件流并将他们添加到详情图列表中
		CommonsMultipartFile img1 = new CommonsMultipartFile((FileItem)new File("G:/程序/Java程序/image/upload/item/shop/1/2019041522142972214.jpg"));
		File productImg1 = new File("G:/程序/Java程序/image/upload/item/shop/1/2019041522142972214.jpg");
		InputStream is1 = new FileInputStream(productImg1);
		CommonsMultipartFile img2 = new CommonsMultipartFile((FileItem)new File("G:/程序/Java程序/image/upload/item/shop/1/2019041522142972214.jpg"));
		File productImg2 = new File("G:/程序/Java程序/image/upload/item/shop/1/2019041522142972214.jpg");
		InputStream is2 = new FileInputStream(productImg1);
		List<CommonsMultipartFile> imageList = new ArrayList<CommonsMultipartFile>();
		imageList.add(img1);
		imageList.add(img2);
		//添加商品并验证
		ProductExecution pe = productService.modifyProduct(product, img, imageList);
		assertEquals(ProductStateEnum.SUCCESS.getState(),pe.getState());
		
	}
}
