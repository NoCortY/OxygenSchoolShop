package com.SchoolShop.o2o.service;



import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.SchoolShop.o2o.dto.ShopExecution;
import com.SchoolShop.o2o.entity.Shop;
import com.SchoolShop.o2o.exceptions.ShopOperationException;

public interface ShopService {
	//根据ShopCondition分页返回相应店铺列表
	public ShopExecution getShopList(Shop shopCondition,int pageIndex,int pageSize);
	ShopExecution addShop(Shop shop,CommonsMultipartFile shopImg);
	//通过店铺Id获取店铺信息
	Shop getByShopId(long shopId);
	//更新店铺信息，包括对图片的处理
	ShopExecution modifyShop(Shop shop,CommonsMultipartFile shopImgFile,String fileName) throws ShopOperationException;
	
}
