package com.SchoolShop.o2o.service;

import java.util.List;

import com.SchoolShop.o2o.dto.ProductCategoryExecution;
import com.SchoolShop.o2o.entity.ProductCategory;
import com.SchoolShop.o2o.exceptions.ProductCategoryOperationException;

public interface ProductCategoryService {
	//查询指定某个店铺下所有商品类别信息
	List<ProductCategory> getProductCategoryList(long shopId);
	//批量添加商品类别
	ProductCategoryExecution batchAddProductCategory(List<ProductCategory> productCategoryList) throws ProductCategoryOperationException;
	//将此类别下的商品类别id置为空，再删除掉该商品类别
	ProductCategoryExecution deleteProductCategory(long productCategoryId,long shopId) throws ProductCategoryOperationException;
}
