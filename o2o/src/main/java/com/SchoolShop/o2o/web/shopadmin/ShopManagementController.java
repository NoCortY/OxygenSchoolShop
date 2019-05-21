package com.SchoolShop.o2o.web.shopadmin;

import java.io.UnsupportedEncodingException;
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
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import com.SchoolShop.o2o.dto.ShopExecution;
import com.SchoolShop.o2o.entity.Area;
import com.SchoolShop.o2o.entity.PersonInfo;
import com.SchoolShop.o2o.entity.Shop;
import com.SchoolShop.o2o.entity.ShopCategory;
import com.SchoolShop.o2o.enums.ShopStateEnum;
import com.SchoolShop.o2o.service.AreaService;
import com.SchoolShop.o2o.service.ShopCategoryService;
import com.SchoolShop.o2o.service.ShopService;
import com.SchoolShop.o2o.util.CodeUtil;
import com.SchoolShop.o2o.util.HttpServletRequestUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
@Controller
@RequestMapping("/shopadmin")
public class ShopManagementController {
	@Autowired
	private ShopService shopService;
	@Autowired
	private ShopCategoryService shopCategoryService;
	@Autowired
	private AreaService areaService;
	
	
	/**
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/getshopmanagementinfo",method = RequestMethod.GET)
	@ResponseBody
	private Map<String,Object> getShopManagementInfo(HttpServletRequest request){
		Map<String,Object> modelMap = new HashMap<String,Object>();
		long shopId = HttpServletRequestUtil.getLong(request, "shopId");
		if(shopId <= 0) {
			//如果前端没有传shopId则尝试从session中获取
			Object currentShopObj = request.getSession().getAttribute("currentShop");
			if(currentShopObj == null) {
				modelMap.put("redirect", true);
				modelMap.put("url", "/o2o/shopadmin/shoplist");
			}else {
				Shop currentShop = (Shop)currentShopObj;
				modelMap.put("redirect", false);
				modelMap.put("shopId",currentShop.getShopId());
			}
		}else {
			Shop currentShop = new Shop();
			currentShop.setShopId(shopId);
			request.getSession().setAttribute("currentShop", currentShop);
			modelMap.put("redirect", false);
		}
		return modelMap;
		
	}
	@RequestMapping(value = "/getshoplist",method = RequestMethod.GET)
	@ResponseBody
	private Map<String,Object>getShopList(HttpServletRequest request){
		Map<String,Object> modelMap = new HashMap<String,Object>();
		PersonInfo user = new PersonInfo();
		user.setUserId(1L);
		user.setName("test");
		request.getSession().setAttribute("user", user);
		user = (PersonInfo)request.getSession().getAttribute("user");
		try {
			Shop shopCondition = new Shop();
			shopCondition.setOwner(user);
			ShopExecution se = shopService.getShopList(shopCondition, 0, 100);
			modelMap.put("shopList", se.getShopList());
			modelMap.put("user", user);
			modelMap.put("success", true);
		}catch(Exception e) {
			modelMap.put("success", false);
			modelMap.put("errMsg", e.getMessage());
		}
		return modelMap;
	}
	//value值用用在网址上  getshopbyid?shopId=1(路由)
	@RequestMapping(value = "/getshopbyid",method = RequestMethod.GET)
	@ResponseBody
	private Map<String,Object> getShopById(HttpServletRequest request){
		Map<String,Object> modelMap = new HashMap<String,Object>();
		Long shopId = HttpServletRequestUtil.getLong(request, "shopId");
		if(shopId > -1) {
			try {
				Shop shop = shopService.getByShopId(shopId);
				List<Area> areaList = areaService.getAreaList();
				modelMap.put("shop", shop);
				modelMap.put("areaList", areaList);//这里的key 必须和JavaScript中的名字一样
				modelMap.put("success", true);
			}catch(Exception e) {
				modelMap.put("success", false);
				modelMap.put("errMsg", e.toString());
			}
		}else {
			modelMap.put("success", false);
			modelMap.put("errMsg", "empty shopId");
		}
		return modelMap;
	}
	
	//修改店铺信息
	@RequestMapping(value = "/modifyshop",method=RequestMethod.POST)
	@ResponseBody//将返回值转成JSON串
	private Map<String,Object> modifyShop(HttpServletRequest request) throws UnsupportedEncodingException{
		//1.接收并转化相应的参数，包括店铺信息以及图片信息
		Map<String,Object> modelMap = new HashMap<String,Object>();
		if(!CodeUtil.checkVerifyCode(request)) {
			modelMap.put("success", false);
			modelMap.put("errMsg", "输入了错误的验证码");
			return modelMap;
		}
		String shopStr = HttpServletRequestUtil.getString(request, "shopStr");
		ObjectMapper mapper = new ObjectMapper();
		Shop shop = null;
		try {
			shop = mapper.readValue(shopStr,Shop.class);
		}catch(Exception e) {
			modelMap.put("success", false);
			modelMap.put("errMsg", e.getMessage());
			return modelMap;
		}
		
		
		CommonsMultipartFile shopImg = null;
		CommonsMultipartResolver commonsMultipartResolver = new CommonsMultipartResolver(
				request.getSession().getServletContext());
		if(commonsMultipartResolver.isMultipart(request)) {
			MultipartHttpServletRequest multipartHttpServletRequest = (MultipartHttpServletRequest)request;
			shopImg = (CommonsMultipartFile)multipartHttpServletRequest.getFile("shopImg");
		}
		//2.修改店铺信息
		if(shop != null && shop.getShopId()!=null) {
			ShopExecution se;
			if(shopImg!=null) {//由于更新不一定要重新上传图片所以可以有null的情况
				se = shopService.modifyShop(shop, shopImg, shopImg.getOriginalFilename());
			}else {
				se=shopService.modifyShop(shop, null, null);
			}
			
			if(se.getState() == ShopStateEnum.SUCCESS.getState()) {
				modelMap.put("success", true);
			}else {
				modelMap.put("success", false);
				modelMap.put("errMsg", se.getStateInfo());
			}
			return modelMap;
		}else {
			modelMap.put("success", false);
			modelMap.put("errMsg", "请输入店铺信息");
			return modelMap;
		}
		//3.返回结果
	}
	@RequestMapping(value = "/getshopinitinfo",method=RequestMethod.GET)
	@ResponseBody//将返回值转成JSON串
	private Map<String,Object>getShopInitInfo(){
		Map<String,Object> modelMap = new HashMap<String,Object>();
		List<ShopCategory> shopCategoryList = new ArrayList<ShopCategory>();
		List<Area> areaList = new ArrayList<Area>();
		try {
			/****************************************记录点 修改过*******************************************************************/
			shopCategoryList = shopCategoryService.getShopCatgoryList(new ShopCategory());
			/****************************************记录点 修改过*******************************************************************/
			areaList = areaService.getAreaList();
			modelMap.put("shopCategoryList", shopCategoryList);
			modelMap.put("areaList", areaList);
			modelMap.put("success", true);
		}catch(Exception e) {
			modelMap.put("success", false);
			modelMap.put("errMsg", e.getMessage());
		}
		return modelMap;
	}
	@RequestMapping(value = "/registershop",method = RequestMethod.POST)
	@ResponseBody
	private Map<String,Object> registerShop(HttpServletRequest request) throws UnsupportedEncodingException{
		//1.接收并转化相应的参数，包括店铺信息以及图片信息
		Map<String,Object> modelMap = new HashMap<String,Object>();
		if(!CodeUtil.checkVerifyCode(request)) {
			modelMap.put("success", false);
			modelMap.put("errMsg", "输入了错误的验证码");
			return modelMap;
		}
		String shopStr = HttpServletRequestUtil.getString(request, "shopStr");
		ObjectMapper mapper = new ObjectMapper();
		Shop shop = null;
		try {
			shop = mapper.readValue(shopStr,Shop.class);
		}catch(Exception e) {
			modelMap.put("success", false);
			modelMap.put("errMsg", e.getMessage());
			return modelMap;
		}
		
		
		CommonsMultipartFile shopImg = null;
		CommonsMultipartResolver commonsMultipartResolver = new CommonsMultipartResolver(
				request.getSession().getServletContext());
		if(commonsMultipartResolver.isMultipart(request)) {
			MultipartHttpServletRequest multipartHttpServletRequest = (MultipartHttpServletRequest)request;
			shopImg = (CommonsMultipartFile)multipartHttpServletRequest.getFile("shopImg");
		}else {
			modelMap.put("success", false);
			modelMap.put("errMsg", "上传图片不能为空");
			return modelMap;
		}
		//2.注册店铺
		if(shop != null && shopImg != null) {
			/*PersonInfo owner = (PersonInfo)request.getSession().getAttribute("user");
			shop.setOwner(owner);*/
			PersonInfo owner = new PersonInfo();
			owner.setUserId(1L);
			shop.setOwner(owner);
			/*
			 * 出现问题
			 * 问题起因：从网页中无法提交图片
			 * debug：在ImageUtil中发现图片文件无法获取扩展名，导致字符串数组越界
			 * 问题所在：在此类中的shopImgFile修改文件名却没有添加文件扩展名
			 * 解决：重载getFileExtension函数，获取CommonMultipartFile的扩展名，在创建File时加入扩展名，问题解决
			 * String fileExtension = new String();
			   fileExtension = ImageUtil.getFileExtension(shopImg);
			   File shopImgFile = new File(PathUtil.getImgBasePath()+ImageUtil.getRandomFileName()+fileExtension);
			   try {
			     	shopImgFile.createNewFile();
			   } catch (IOException e) {
			 	   modelMap.put("success", false);
				   modelMap.put("errMsg", e.getMessage());
				   return modelMap;
			   }
			   try {
				   FileUtil.inputStreamToFile(shopImg.getInputStream(),shopImgFile);
			   } catch (IOException e) {
				   modelMap.put("success", false);
				   modelMap.put("errMsg", e.getMessage());
				   return modelMap;
			   }
			 * */
			
			ShopExecution se = shopService.addShop(shop,shopImg);
			if(se.getState() == ShopStateEnum.CHECK.getState()) {
				modelMap.put("success", true);
				//该用户可以操作的店铺列表
				@SuppressWarnings("unchecked")
				List<Shop> shopList = (List<Shop>)request.getSession().getAttribute("shopList");
				if(shopList == null || shopList.size()==0) {
					shopList = new ArrayList<Shop>();
				}
				shopList.add(se.getShop());
				request.getSession().setAttribute("shopList",shopList);
			}else {
				modelMap.put("success", false);
				modelMap.put("errMsg", se.getStateInfo());
			}
			return modelMap;
		}else {
			modelMap.put("success", false);
			modelMap.put("errMsg", "请输入店铺信息");
			return modelMap;
		}
		//3.返回结果
	}

}
