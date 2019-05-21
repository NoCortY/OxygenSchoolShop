package com.SchoolShop.o2o.service;

import java.util.List;

import com.SchoolShop.o2o.entity.ShopCategory;

public interface ShopCategoryService {
	List<ShopCategory> getShopCatgoryList(ShopCategory shopCategoryCondition); 
}
