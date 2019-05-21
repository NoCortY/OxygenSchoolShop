package com.SchoolShop.o2o.dao;

import static org.junit.Assert.assertEquals;

import java.util.List;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.SchoolShop.o2o.BaseTest;
import com.SchoolShop.o2o.entity.Area;

public class AreaDaoTest extends BaseTest{
	@Autowired
	private AreaDao areaDao;
	
	@Test
	public void testQueryArea() {
		List<Area> areaList = areaDao.queryArea();
		assertEquals(2,areaList.size());
	}
}
 