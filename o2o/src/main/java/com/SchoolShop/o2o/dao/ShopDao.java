package com.SchoolShop.o2o.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.SchoolShop.o2o.entity.Shop;

public interface ShopDao {
	//新增店铺
	int insertShop(Shop shop);
	//更新店铺信息
	int updateShop(Shop shop);
	//查询店铺信息
	Shop queryByShopId(long shopId);
	/*分页查询店铺，可输入的条件有:店铺名(模糊）,店铺状态,店铺类别,区域Id,owner
	 * @param shopCondition
	 * @param rowIndex 从第几行开始
	 * @param pageSize 返回的条数
	 * @param的作用 :有多个参数的时候必须指定唯一的标识，才能取出正确的数据
	 * */
	List<Shop> queryShopList(@Param("shopCondition") Shop shopCondition,
			@Param("rowIndex") int rowIndex,@Param("pageSize") int pageSize);
	//返回queryShopList总数
	int queryShopCount(@Param("shopCondition") Shop shopCondition);
}
