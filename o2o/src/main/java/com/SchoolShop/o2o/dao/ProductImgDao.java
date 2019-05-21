package com.SchoolShop.o2o.dao;

import java.util.List;

import com.SchoolShop.o2o.entity.ProductImg;

public interface ProductImgDao {
	//批量插入图片Dao
	int batchInsertProductImg(List<ProductImg> productImgList); 
	//删除商品下的所有详情图
	int deleteProductImgByProductId(long productId);
	
	List<ProductImg> queryProductImgList(long productId);
}
