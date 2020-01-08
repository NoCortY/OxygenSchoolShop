package cn.objectspace.schoolshop.service;

import cn.objectspace.schoolshop.dto.UserShopMapExecution;
import cn.objectspace.schoolshop.entity.UserShopMap;

public interface UserShopMapService {

	UserShopMapExecution listUserShopMap(UserShopMap userShopMapCondition,
			int pageIndex, int pageSize);

}
