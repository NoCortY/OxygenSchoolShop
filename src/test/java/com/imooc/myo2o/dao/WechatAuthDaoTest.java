package com.imooc.myo2o.dao;

import static org.junit.Assert.assertEquals;

import java.util.Date;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;

import com.imooc.myo2o.BaseTest;

import cn.objectspace.schoolshop.dao.WechatAuthDao;
import cn.objectspace.schoolshop.entity.WechatAuth;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class WechatAuthDaoTest extends BaseTest {
	@Autowired
	private WechatAuthDao wechatAuthDao;

	@Test
	public void testAInsertWechatAuth() throws Exception {
		WechatAuth wechatAuth = new WechatAuth();
		wechatAuth.setUserId(1L);
		wechatAuth.setOpenId("dafahizhfdhaih");
		wechatAuth.setCreateTime(new Date());
		int effectedNum = wechatAuthDao.insertWechatAuth(wechatAuth);
		assertEquals(1, effectedNum);
	}

	@Test
	public void testBQueryWechatAuthByOpenId() throws Exception {
		WechatAuth wechatAuth = wechatAuthDao
				.queryWechatInfoByOpenId("dafahizhfdhaih");
		assertEquals("test", wechatAuth.getPersonInfo().getName());
	}

	@Test
	public void testDeleteWechatAuth() throws Exception {
		WechatAuth wechatAuth = wechatAuthDao
				.queryWechatInfoByOpenId("dafahizhfdhaih");
		int effectedNum = wechatAuthDao.deleteWechatAuth(wechatAuth
				.getWechatAuthId());
		assertEquals(1, effectedNum);
	}
}
