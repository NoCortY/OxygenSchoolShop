package com.SchoolShop.o2o.web.shopadmin.frontend;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.SchoolShop.o2o.entity.HeadLine;
import com.SchoolShop.o2o.entity.ShopCategory;
import com.SchoolShop.o2o.service.HeadLineService;
import com.SchoolShop.o2o.service.ShopCategoryService;

@Controller
@RequestMapping("/frontend")
public class MainPageController {
	@Autowired
	private ShopCategoryService shopCategoryService;
	@Autowired
	private HeadLineService headLineService;
	
	
	@RequestMapping(value="/listmainpageinfo",method = RequestMethod.GET)
	@ResponseBody
	private Map<String,Object> listMainPageInfo(){
		Map<String,Object> modelMap = new HashMap<String,Object>();
		List<ShopCategory> shopCategoryList = new ArrayList<ShopCategory>();
		try {
			//获取一级店铺类别列表(即parentId == null 的ShopCategory)
			shopCategoryList = shopCategoryService.getShopCatgoryList(null);
			modelMap.put("shopCategoryList", shopCategoryList);
		}catch(Exception e) {
			modelMap.put("success", false);
			modelMap.put("errMsg", e.getMessage());
			return modelMap;
		}
		List<HeadLine> headLineList = new ArrayList<HeadLine>();
		try {
			//获取状态为可用（1）的头条列表
			HeadLine headLineCondition = new HeadLine();
			headLineCondition.setEnableStatus(1);
			headLineList = headLineService.getHeadLineList(headLineCondition);
			modelMap.put("headLineList", headLineList);
		}catch(Exception e) {
			modelMap.put("success",false);
			modelMap.put("errMsg", e.getMessage());
			return modelMap;
		}
		modelMap.put("success", true);
		return modelMap;
	}
	
}
