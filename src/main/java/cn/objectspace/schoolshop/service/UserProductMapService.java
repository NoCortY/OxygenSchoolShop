package cn.objectspace.schoolshop.service;

import cn.objectspace.schoolshop.dto.UserProductMapExecution;
import cn.objectspace.schoolshop.entity.UserProductMap;

public interface UserProductMapService {
	/**
	 * 
	 * @param shopId
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 */
	UserProductMapExecution listUserProductMap(
			UserProductMap userProductCondition, Integer pageIndex,
			Integer pageSize);

	/**
	 * 
	 * @param userProductMap
	 * @return
	 * @throws RuntimeException
	 */
	UserProductMapExecution addUserProductMap(UserProductMap userProductMap)
			throws RuntimeException;

}
