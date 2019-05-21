package com.SchoolShop.o2o.web.shopadmin;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.SchoolShop.o2o.dto.ProductCategoryExecution;
import com.SchoolShop.o2o.dto.Result;
import com.SchoolShop.o2o.entity.ProductCategory;
import com.SchoolShop.o2o.entity.Shop;
import com.SchoolShop.o2o.enums.ProductCategoryStateEnum;
import com.SchoolShop.o2o.exceptions.ProductCategoryOperationException;
import com.SchoolShop.o2o.service.ProductCategoryService;

@Controller
@RequestMapping("/shopadmin")
public class ProductCategoryManagementController {
	@Autowired
	private ProductCategoryService productCategoryService;
	@RequestMapping(value="/getproductcategorylist",method = RequestMethod.GET)
	@ResponseBody
	private Result<List<ProductCategory>> getProductCategoryList(HttpServletRequest request){
		
		Shop currentShop = (Shop)request.getSession().getAttribute("currentShop");
		List<ProductCategory> list = null;
		if(currentShop != null && currentShop.getShopId()>0) {
			list = productCategoryService.getProductCategoryList(currentShop.getShopId());
			return new Result<List<ProductCategory>>(true,list);
		}else {
			ProductCategoryStateEnum ps = ProductCategoryStateEnum.INNER_ERROR;
			return new Result<List<ProductCategory>>(false,ps.getState(),ps.getStateInfo());
		}
	}
	@RequestMapping(value="/addproductcategorys",method = RequestMethod.POST)
	@ResponseBody
	//由于批量传入所以参数使用RequestBody注解
	private Map<String,Object> addProductCategorys(@RequestBody List<ProductCategory> productCategoryList,
			HttpServletRequest request){
		Map<String,Object> modelMap = new HashMap<String,Object>();
		Shop currentShop = (Shop)request.getSession().getAttribute("currentShop");
		for(ProductCategory pc:productCategoryList) {//这种for结构是指pc在list中遍历
			pc.setShopId(currentShop.getShopId());//把遍历到的信息加入pc.shopId
		}
		if(productCategoryList != null&& productCategoryList.size()>0) {
			try {
				ProductCategoryExecution pe = productCategoryService.batchAddProductCategory(productCategoryList);
				if(pe.getState()==ProductCategoryStateEnum.SUCCESS.getState()) {
					modelMap.put("success", true);
				}else {
					modelMap.put("success", true);
					modelMap.put("errMsg",pe.getStateInfo());
				}
			}catch(ProductCategoryOperationException e) {
				modelMap.put("success", false);
				modelMap.put("errMsg", e.toString());
				return modelMap;
			}
		}else {
			modelMap.put("success", false);
			modelMap.put("errMsg", "请至少输入一个商品类别");
		}
		return modelMap;
	}
	@RequestMapping(value="/removeproductcategory",method=RequestMethod.POST)
	@ResponseBody
	private Map<String,Object> removeProductCategory(Long productCategoryId,HttpServletRequest request){
		Map<String,Object> modelMap = new HashMap<String,Object>();
		if(productCategoryId != null&&productCategoryId >0) {
			try {
				Shop currentShop = (Shop) request.getSession().getAttribute("currentShop");
				ProductCategoryExecution pe = productCategoryService.deleteProductCategory(productCategoryId, currentShop.getShopId());
				if(pe.getState()==ProductCategoryStateEnum.SUCCESS.getState()) {
					modelMap.put("success", true);
				}else {
					modelMap.put("success", false);
					modelMap.put("errMsg", pe.getStateInfo());
				}
			}catch(ProductCategoryOperationException e){
				modelMap.put("success", false);
				modelMap.put("errMsg", e.toString());
				return modelMap;
			}
		}else {
			modelMap.put("success", false);
			modelMap.put("errMsg", "请至少选择一个商品类别");
		}
		return modelMap; 
	} 
}
