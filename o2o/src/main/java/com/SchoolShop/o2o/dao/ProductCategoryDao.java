package com.SchoolShop.o2o.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.SchoolShop.o2o.entity.ProductCategory;

public interface ProductCategoryDao {
	//通过ShopId查询店铺类别
	List<ProductCategory> queryProductCategoryList(long shopId);
	//批量新增商品类别
	int batchInsertProductCategory(List<ProductCategory> productCategoryList);
	//删除指定商品类别
	int deleteProductCategory(@Param("productCategoryId") long productCategoryId,@Param("shopId") long shopId);
	//@Param是为了让Mybatis辨认   mapper中用#{productCategoryId}
}
