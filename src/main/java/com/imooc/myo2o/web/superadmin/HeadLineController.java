package com.imooc.myo2o.web.superadmin;

import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.imooc.myo2o.dto.HeadLineExecution;
import com.imooc.myo2o.entity.ConstantForSuperAdmin;
import com.imooc.myo2o.entity.HeadLine;
import com.imooc.myo2o.enums.HeadLineStateEnum;
import com.imooc.myo2o.service.HeadLineService;
import com.imooc.myo2o.util.HttpServletRequestUtil;

@Controller
@RequestMapping("/superadmin")
public class HeadLineController {
	@Autowired
	private HeadLineService headLineService;

	@RequestMapping(value = "/listheadlines", method = RequestMethod.POST)
	@ResponseBody
	private Map<String, Object> listHeadLines(HttpServletRequest request) {
		Map<String, Object> modelMap = new HashMap<String, Object>();
		List<HeadLine> list = new ArrayList<HeadLine>();
		try {
			Integer enableStatus = HttpServletRequestUtil.getInt(request,
					"enableStatus");
			HeadLine headLine = new HeadLine();
			if (enableStatus > -1) {
				headLine.setEnableStatus(enableStatus);
			}
			list = headLineService.getHeadLineList(headLine);
			modelMap.put(ConstantForSuperAdmin.PAGE_SIZE, list);
			modelMap.put(ConstantForSuperAdmin.TOTAL, list.size());
		} catch (Exception e) {
			e.printStackTrace();
			modelMap.put("success", false);
			modelMap.put("errMsg", e.toString());
		}
		return modelMap;
	}

	@RequestMapping(value = "/addheadline", method = RequestMethod.POST)
	@ResponseBody
	private Map<String, Object> addHeadLine(HttpServletRequest request) {
		Map<String, Object> modelMap = new HashMap<String, Object>();
		ObjectMapper mapper = new ObjectMapper();
		HeadLine headLine = null;
		String headLineStr = HttpServletRequestUtil.getString(request,
				"headLineStr");
		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
		CommonsMultipartFile thumbnail = (CommonsMultipartFile) multipartRequest
				.getFile("headTitleManagementAdd_lineImg");
		try {
			headLine = mapper.readValue(headLineStr, HeadLine.class);
		} catch (Exception e) {
			modelMap.put("success", false);
			modelMap.put("errMsg", e.toString());
			return modelMap;
		}
		if (headLine != null && thumbnail != null) {
			try {
				// decode可能有中文的地方
				headLine.setLineName((headLine.getLineName() == null) ? null
						: URLDecoder.decode(headLine.getLineName(), "UTF-8"));
				HeadLineExecution ae = headLineService.addHeadLine(headLine,
						thumbnail);
				if (ae.getState() == HeadLineStateEnum.SUCCESS.getState()) {
					modelMap.put("success", true);
				} else {
					modelMap.put("success", false);
					modelMap.put("errMsg", ae.getStateInfo());
				}
			} catch (Exception e) {
				modelMap.put("success", false);
				modelMap.put("errMsg", e.toString());
				return modelMap;
			}

		} else {
			modelMap.put("success", false);
			modelMap.put("errMsg", "请输入头条信息");
		}
		return modelMap;
	}

	@RequestMapping(value = "/modifyheadline", method = RequestMethod.POST)
	@ResponseBody
	private Map<String, Object> modifyHeadLine(HttpServletRequest request) {
		Map<String, Object> modelMap = new HashMap<String, Object>();
		ObjectMapper mapper = new ObjectMapper();
		HeadLine headLine = null;
		String headLineStr = HttpServletRequestUtil.getString(request,
				"headLineStr");
		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
		CommonsMultipartFile thumbnail = (CommonsMultipartFile) multipartRequest
				.getFile("headTitleManagementEdit_lineImg");
		try {
			headLine = mapper.readValue(headLineStr, HeadLine.class);
		} catch (Exception e) {
			modelMap.put("success", false);
			modelMap.put("errMsg", e.toString());
			return modelMap;
		}
		if (headLine != null && headLine.getLineId() != null) {
			try {
				// decode可能有中文的地方
				headLine.setLineName((headLine.getLineName() == null) ? null
						: URLDecoder.decode(headLine.getLineName(), "UTF-8"));
				HeadLineExecution ae = headLineService.modifyHeadLine(headLine,
						thumbnail);
				if (ae.getState() == HeadLineStateEnum.SUCCESS.getState()) {
					modelMap.put("success", true);
				} else {
					modelMap.put("success", false);
					modelMap.put("errMsg", ae.getStateInfo());
				}
			} catch (Exception e) {
				modelMap.put("success", false);
				modelMap.put("errMsg", e.toString());
				return modelMap;
			}

		} else {
			modelMap.put("success", false);
			modelMap.put("errMsg", "请输入头条信息");
		}
		return modelMap;
	}

	@RequestMapping(value = "/removeheadline", method = RequestMethod.POST)
	@ResponseBody
	private Map<String, Object> removeHeadLine(Long headLineId) {
		Map<String, Object> modelMap = new HashMap<String, Object>();
		if (headLineId != null && headLineId > 0) {
			try {
				HeadLineExecution ae = headLineService
						.removeHeadLine(headLineId);
				if (ae.getState() == HeadLineStateEnum.SUCCESS.getState()) {
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
			modelMap.put("errMsg", "请输入头条信息");
		}
		return modelMap;
	}

	@RequestMapping(value = "/removeheadlines", method = RequestMethod.POST)
	@ResponseBody
	private Map<String, Object> removeHeadLines(String headLineIdListStr) {
		Map<String, Object> modelMap = new HashMap<String, Object>();
		ObjectMapper mapper = new ObjectMapper();
		JavaType javaType = mapper.getTypeFactory().constructParametricType(
				ArrayList.class, Long.class);
		List<Long> headLineIdList = null;
		try {
			headLineIdList = mapper.readValue(headLineIdListStr, javaType);
		} catch (Exception e) {
			modelMap.put("success", false);
			modelMap.put("errMsg", e.toString());
		}
		if (headLineIdList != null && headLineIdList.size() > 0) {
			try {
				HeadLineExecution ae = headLineService
						.removeHeadLineList(headLineIdList);
				if (ae.getState() == HeadLineStateEnum.SUCCESS.getState()) {
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
			modelMap.put("errMsg", "请输入区域信息");
		}
		return modelMap;
	}

}
