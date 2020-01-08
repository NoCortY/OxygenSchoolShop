package cn.objectspace.schoolshop.web.frontend;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;

import cn.objectspace.schoolshop.dto.UserAwardMapExecution;
import cn.objectspace.schoolshop.entity.Award;
import cn.objectspace.schoolshop.entity.PersonInfo;
import cn.objectspace.schoolshop.entity.UserAwardMap;
import cn.objectspace.schoolshop.enums.UserAwardMapStateEnum;
import cn.objectspace.schoolshop.service.AwardService;
import cn.objectspace.schoolshop.service.PersonInfoService;
import cn.objectspace.schoolshop.service.UserAwardMapService;
import cn.objectspace.schoolshop.util.HttpServletRequestUtil;
import cn.objectspace.schoolshop.util.QRCodeUtil;
import cn.objectspace.schoolshop.util.baidu.ShortNetAddress;

@Controller
@RequestMapping("/frontend")
public class MyAwardController {
	@Autowired
	private UserAwardMapService userAwardMapService;
	@Autowired
	private AwardService awardService;
	@Autowired
	private PersonInfoService personInfoService;
	private static String URLPREFIX = "https://open.weixin.qq.com/connect/oauth2/authorize?"
			+ "appid=wxd7f6c5b8899fba83&"
			+ "redirect_uri=115.28.159.6/myo2o/shop/exchangeaward&"
			+ "response_type=code&scope=snsapi_userinfo&state=";
	private static String URLSUFFIX = "#wechat_redirect";

	@RequestMapping(value = "/listuserawardmapsbycustomer", method = RequestMethod.GET)
	@ResponseBody
	private Map<String, Object> listUserAwardMapsByCustomer(
			HttpServletRequest request) {
		Map<String, Object> modelMap = new HashMap<String, Object>();
		int pageIndex = HttpServletRequestUtil.getInt(request, "pageIndex");
		int pageSize = HttpServletRequestUtil.getInt(request, "pageSize");
		Long userId = 1L;
		if ((pageIndex > -1) && (pageSize > -1) && (userId != null)) {
			UserAwardMap userAwardMapCondition = new UserAwardMap();
			userAwardMapCondition.setUserId(userId);
			long shopId = HttpServletRequestUtil.getLong(request, "shopId");
			if (shopId > -1) {
				userAwardMapCondition.setShopId(shopId);
			}
			String awardName = HttpServletRequestUtil.getString(request,
					"userName");
			if (awardName != null) {
				userAwardMapCondition.setAwardName(awardName);
			}
			UserAwardMapExecution ue = userAwardMapService.listUserAwardMap(
					userAwardMapCondition, pageIndex, pageSize);
			modelMap.put("userAwardMapList", ue.getUserAwardMapList());
			modelMap.put("count", ue.getCount());
			modelMap.put("success", true);
		} else {
			modelMap.put("success", false);
			modelMap.put("errMsg", "empty pageSize or pageIndex or userId");
		}
		return modelMap;
	}

	@RequestMapping(value = "/adduserawardmap", method = RequestMethod.POST)
	@ResponseBody
	private Map<String, Object> addUserAwardMap(HttpServletRequest request) {
		Map<String, Object> modelMap = new HashMap<String, Object>();
		PersonInfo user = (PersonInfo) request.getSession()
				.getAttribute("user");
		Long awardId = HttpServletRequestUtil.getLong(request, "awardId");
		UserAwardMap userAwardMap = compactUserAwardMap4Add(user, awardId);
		if (userAwardMap != null) {
			try {
				UserAwardMapExecution se = userAwardMapService
						.addUserAwardMap(userAwardMap);
				if (se.getState() == UserAwardMapStateEnum.SUCCESS.getState()) {
					modelMap.put("success", true);
				} else {
					modelMap.put("success", false);
					modelMap.put("errMsg", se.getStateInfo());
				}
			} catch (RuntimeException e) {
				modelMap.put("success", false);
				modelMap.put("errMsg", e.toString());
				return modelMap;
			}

		} else {
			modelMap.put("success", false);
			modelMap.put("errMsg", "请选择领取的奖品");
		}
		return modelMap;
	}

	@RequestMapping(value = "/generateqrcode4award", method = RequestMethod.GET)
	@ResponseBody
	private void generateQRCode4Product(HttpServletRequest request,
			HttpServletResponse response) {
		long userAwardId = HttpServletRequestUtil.getLong(request,
				"userAwardId");
		UserAwardMap userAwardMap = userAwardMapService
				.getUserAwardMapById(userAwardId);
		PersonInfo user = (PersonInfo) request.getSession()
				.getAttribute("user");
		if (userAwardMap != null && user != null && user.getUserId() != null
				&& userAwardMap.getUserId() == user.getUserId()
				&& userAwardMap.getUsedStatus() == 0) {
			long timpStamp = System.currentTimeMillis();
			String content = "{\"userAwardId\":" + userAwardId
					+ ",\"customerId\":" + user.getUserId()
					+ ",\"createTime\":" + timpStamp + "}";
			String longUrl = URLPREFIX + content + URLSUFFIX;
			String shortUrl = ShortNetAddress.getShortURL(longUrl);
			BitMatrix qRcodeImg = QRCodeUtil.generateQRCodeStream(shortUrl,
					response);
			try {
				MatrixToImageWriter.writeToStream(qRcodeImg, "png",
						response.getOutputStream());
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	private UserAwardMap compactUserAwardMap4Add(PersonInfo user, Long awardId) {
		UserAwardMap userAwardMap = null;
		if (user != null && user.getUserId() != null && awardId != -1) {
			userAwardMap = new UserAwardMap();
			PersonInfo personInfo = personInfoService.getPersonInfoById(user
					.getUserId());
			Award award = awardService.getAwardById(awardId);
			userAwardMap.setUserId(user.getUserId());
			userAwardMap.setAwardId(awardId);
			userAwardMap.setShopId(award.getShopId());
			userAwardMap.setUserName(personInfo.getName());
			userAwardMap.setAwardName(award.getAwardName());
			userAwardMap.setPoint(award.getPoint());
			userAwardMap.setCreateTime(new Date());
			userAwardMap.setUsedStatus(1);
		}
		return userAwardMap;
	}
}
