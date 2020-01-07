package com.imooc.myo2o.service;

import com.imooc.myo2o.dto.UserShopMapExecution;
import com.imooc.myo2o.entity.UserShopMap;

public interface UserShopMapService {

	UserShopMapExecution listUserShopMap(UserShopMap userShopMapCondition,
			int pageIndex, int pageSize);

}
