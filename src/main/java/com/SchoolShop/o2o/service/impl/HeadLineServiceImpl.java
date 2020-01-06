package com.SchoolShop.o2o.service.impl;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.SchoolShop.o2o.dao.HeadLineDao;
import com.SchoolShop.o2o.entity.HeadLine;
import com.SchoolShop.o2o.service.HeadLineService;

@Service
public class HeadLineServiceImpl implements HeadLineService {
	@Autowired
	private HeadLineDao headLineDao;
	@Override
	public List<HeadLine> getHeadLineList(HeadLine headLineCondition) throws IOException {
		// TODO Auto-generated method stub
		return headLineDao.queryHeadLine(headLineCondition);
	}

}
