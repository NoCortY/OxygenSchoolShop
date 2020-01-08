package cn.objectspace.schoolshop.web.shop;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import com.fasterxml.jackson.databind.ObjectMapper;

import cn.objectspace.schoolshop.dto.AwardExecution;
import cn.objectspace.schoolshop.entity.Award;
import cn.objectspace.schoolshop.entity.Shop;
import cn.objectspace.schoolshop.enums.AwardStateEnum;
import cn.objectspace.schoolshop.service.AwardService;
import cn.objectspace.schoolshop.util.CodeUtil;
import cn.objectspace.schoolshop.util.HttpServletRequestUtil;

@Controller
@RequestMapping("/shop")
public class AwardManagementController {
	@Autowired
	private AwardService awardService;

	@RequestMapping(value = "/listawardsbyshop", method = RequestMethod.GET)
	@ResponseBody
	private Map<String, Object> listAwardsByShop(HttpServletRequest request) {
		Map<String, Object> modelMap = new HashMap<String, Object>();
		int pageIndex = HttpServletRequestUtil.getInt(request, "pageIndex");
		int pageSize = HttpServletRequestUtil.getInt(request, "pageSize");
		Shop currentShop = (Shop) request.getSession().getAttribute(
				"currentShop");
		if ((pageIndex > -1) && (pageSize > -1) && (currentShop != null)
				&& (currentShop.getShopId() != null)) {
			String awardName = HttpServletRequestUtil.getString(request,
					"awardName");
			Award awardCondition = compactAwardCondition4Search(
					currentShop.getShopId(), awardName);
			AwardExecution ae = awardService.getAwardList(awardCondition,
					pageIndex, pageSize);
			modelMap.put("awardList", ae.getAwardList());
			modelMap.put("count", ae.getCount());
			modelMap.put("success", true);
		} else {
			modelMap.put("success", false);
			modelMap.put("errMsg", "empty pageSize or pageIndex or shopId");
		}
		return modelMap;
	}

	@RequestMapping(value = "/getawardbyid", method = RequestMethod.GET)
	@ResponseBody
	private Map<String, Object> getAwardbyId(HttpServletRequest request) {
		Map<String, Object> modelMap = new HashMap<String, Object>();
		long awardId = HttpServletRequestUtil.getLong(request, "awardId");
		if (awardId > -1) {
			Award award = awardService.getAwardById(awardId);
			modelMap.put("award", award);
			modelMap.put("success", true);
		} else {
			modelMap.put("success", false);
			modelMap.put("errMsg", "empty awardId");
		}
		return modelMap;
	}

	@RequestMapping(value = "/addaward", method = RequestMethod.POST)
	@ResponseBody
	private Map<String, Object> addAward(HttpServletRequest request) {
		Map<String, Object> modelMap = new HashMap<String, Object>();
		if (!CodeUtil.checkVerifyCode(request)) {
			modelMap.put("success", false);
			modelMap.put("errMsg", "输入了错误的验证码");
			return modelMap;
		}
		ObjectMapper mapper = new ObjectMapper();
		Award award = null;
		String awardStr = HttpServletRequestUtil.getString(request, "awardStr");
		MultipartHttpServletRequest multipartRequest = null;
		CommonsMultipartFile thumbnail = null;
		CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(
				request.getSession().getServletContext());
		if (multipartResolver.isMultipart(request)) {
			multipartRequest = (MultipartHttpServletRequest) request;
			thumbnail = (CommonsMultipartFile) multipartRequest
					.getFile("thumbnail");
		} else {
			modelMap.put("success", false);
			modelMap.put("errMsg", "上传图片不能为空");
			return modelMap;
		}
		try {
			award = mapper.readValue(awardStr, Award.class);
		} catch (Exception e) {
			modelMap.put("success", false);
			modelMap.put("errMsg", e.toString());
			return modelMap;
		}
		if (award != null && thumbnail != null) {
			try {
				Shop currentShop = (Shop) request.getSession().getAttribute(
						"currentShop");
				award.setShopId(currentShop.getShopId());
				AwardExecution ae = awardService.addAward(award, thumbnail);
				if (ae.getState() == AwardStateEnum.SUCCESS.getState()) {
					modelMap.put("success", true);
				} else {
					modelMap.put("success", false);
					modelMap.put("errMsg", ae.getStateInfo());
				}
			} catch (RuntimeException e) {
				modelMap.put("success", false);
				modelMap.put("errMsg", e.toString());
				return modelMap;
			}

		} else {
			modelMap.put("success", false);
			modelMap.put("errMsg", "请输入商品信息");
		}
		return modelMap;
	}

	@RequestMapping(value = "/modifyaward", method = RequestMethod.POST)
	@ResponseBody
	private Map<String, Object> modifyAward(HttpServletRequest request) {
		boolean statusChange = HttpServletRequestUtil.getBoolean(request,
				"statusChange");
		Map<String, Object> modelMap = new HashMap<String, Object>();
		if (!statusChange && !CodeUtil.checkVerifyCode(request)) {
			modelMap.put("success", false);
			modelMap.put("errMsg", "输入了错误的验证码");
			return modelMap;
		}
		ObjectMapper mapper = new ObjectMapper();
		Award award = null;
		String awardStr = HttpServletRequestUtil.getString(request, "awardStr");
		MultipartHttpServletRequest multipartRequest = null;
		CommonsMultipartFile thumbnail = null;
		CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(
				request.getSession().getServletContext());
		if (multipartResolver.isMultipart(request)) {
			multipartRequest = (MultipartHttpServletRequest) request;
			thumbnail = (CommonsMultipartFile) multipartRequest
					.getFile("thumbnail");
		}
		try {
			award = mapper.readValue(awardStr, Award.class);
		} catch (Exception e) {
			modelMap.put("success", false);
			modelMap.put("errMsg", e.toString());
			return modelMap;
		}
		if (award != null) {
			try {
				Shop currentShop = (Shop) request.getSession().getAttribute(
						"currentShop");
				award.setShopId(currentShop.getShopId());
				AwardExecution pe = awardService.modifyAward(award, thumbnail);
				if (pe.getState() == AwardStateEnum.SUCCESS.getState()) {
					modelMap.put("success", true);
				} else {
					modelMap.put("success", false);
					modelMap.put("errMsg", pe.getStateInfo());
				}
			} catch (RuntimeException e) {
				modelMap.put("success", false);
				modelMap.put("errMsg", e.toString());
				return modelMap;
			}

		} else {
			modelMap.put("success", false);
			modelMap.put("errMsg", "请输入商品信息");
		}
		return modelMap;
	}

	private Award compactAwardCondition4Search(long shopId, String awardName) {
		Award awardCondition = new Award();
		awardCondition.setShopId(shopId);
		if (awardName != null) {
			awardCondition.setAwardName(awardName);
		}
		return awardCondition;
	}

}
