package cn.objectspace.schoolshop.service;

import org.springframework.web.multipart.commons.CommonsMultipartFile;

import cn.objectspace.schoolshop.dto.WechatAuthExecution;
import cn.objectspace.schoolshop.entity.WechatAuth;

public interface WechatAuthService {

	/**
	 * 
	 * @param openId
	 * @return
	 */
	WechatAuth getWechatAuthByOpenId(String openId);

	/**
	 * 
	 * @param wechatAuth
	 * @param profileImg
	 * @return
	 * @throws RuntimeException
	 */
	WechatAuthExecution register(WechatAuth wechatAuth,
			CommonsMultipartFile profileImg) throws RuntimeException;

}
