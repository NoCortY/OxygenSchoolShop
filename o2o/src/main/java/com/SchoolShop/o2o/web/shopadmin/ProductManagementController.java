package com.SchoolShop.o2o.web.shopadmin;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import com.SchoolShop.o2o.dto.ProductExecution;
import com.SchoolShop.o2o.entity.Product;
import com.SchoolShop.o2o.entity.ProductCategory;
import com.SchoolShop.o2o.entity.Shop;
import com.SchoolShop.o2o.enums.ProductStateEnum;
import com.SchoolShop.o2o.exceptions.ProductOperationException;
import com.SchoolShop.o2o.service.ProductCategoryService;
import com.SchoolShop.o2o.service.ProductService;
import com.SchoolShop.o2o.util.CodeUtil;
import com.SchoolShop.o2o.util.HttpServletRequestUtil;
import com.fasterxml.jackson.databind.ObjectMapper;

@Controller
@RequestMapping("/shopadmin")
public class ProductManagementController {
	@Autowired
	private ProductService productService;
	@Autowired
	private ProductCategoryService productCategoryService;
	// 支持上传详情图的最大数量
	private static final int IMAGEMAXCOUNT = 6;

	@RequestMapping(value = "/addproduct", method = RequestMethod.POST)
	@ResponseBody
	private Map<String, Object> addProduct(HttpServletRequest request) {
		Map<String, Object> modelMap = new HashMap<String, Object>();
		if (!CodeUtil.checkVerifyCode(request)) {
			modelMap.put("success", false);
			modelMap.put("errMsg", "输入的错误的验证码");
			return modelMap;
		}
		// 接收前端参数的变量的初始化，包括商品，缩略图，详情图列表实体类
		ObjectMapper mapper = new ObjectMapper();
		Product product = null;
		String productStr = HttpServletRequestUtil.getString(request, "productStr");
		MultipartHttpServletRequest multipartRequest = null;
		CommonsMultipartFile imageFile = null;
		List<CommonsMultipartFile> productImgList = new ArrayList<CommonsMultipartFile>();
		CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(
				request.getSession().getServletContext());
		try {
			// 若请求中存在文件流，则取出相关的文件(包括缩略图和详情图)
			if (multipartResolver.isMultipart(request)) {
				multipartRequest = (MultipartHttpServletRequest) request;
				// 取出缩略图并构建File对象
				imageFile = (CommonsMultipartFile) multipartRequest.getFile("thumbnail");
				// 取出详情图列表并构建List<File>对象，最多支持六张图片上传
				for (int i = 0; i < IMAGEMAXCOUNT; i++) {
					CommonsMultipartFile tempImgFileItem = (CommonsMultipartFile) (multipartRequest
							.getFile("productImg" + i));
					if (tempImgFileItem != null) {
						// 若取出的第i个文件流不为空 则加入详情图列表
						productImgList.add(tempImgFileItem);
					} else {
						break;
					}
				}

			} else {
				modelMap.put("success", false);
				modelMap.put("errMsg", "上传图片不能为空");
				return modelMap;
			}
		} catch (Exception e) {
			modelMap.put("success", false);
			modelMap.put("errMsg", e.toString());
			return modelMap;
		}
		try {
			// 尝试获取从前端传过来的表单string流并将其转换成Product实体类
			product = mapper.readValue(productStr, Product.class);
		} catch (Exception e) {
			modelMap.put("success", false);
			modelMap.put("errMsg", e.toString());
			return modelMap;
		}

		// 若Product信息，缩略图以及详情图列表为非空，则开始进行商品添加操作
		if (product != null && imageFile != null && productImgList.size() > 0) {
			try {
				// 从session中获取当前店铺的id并赋值给product，减少对前端数据的依赖
				Shop currentShop = (Shop) request.getSession().getAttribute("currentShop");
				Shop shop = new Shop();
				shop.setShopId(currentShop.getShopId());
				product.setShop(shop);
				// 执行添加操作
				ProductExecution pe = new ProductExecution();
				pe = productService.addProduct(product, imageFile, productImgList);
				if (pe.getState() == ProductStateEnum.SUCCESS.getState()) {
					modelMap.put("success", true);
				} else {
					modelMap.put("success", false);
					modelMap.put("errMsg", pe.getStateInfo());
				}
			} catch (ProductOperationException e) {
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

	@RequestMapping(value = "/getproductbyid", method = RequestMethod.GET)
	@ResponseBody
	private Map<String, Object> getProductById(@RequestParam Long productId) {
		Map<String, Object> modelMap = new HashMap<String, Object>();
		// 非空判断
		if (productId > -1) {
			// 获取商品信息
			Product product = productService.getProductById(productId);
			// 获取该店铺下的商品类别列表
			List<ProductCategory> productCategoryList = productCategoryService
					.getProductCategoryList(product.getShop().getShopId());
			modelMap.put("product", product);
			modelMap.put("productCategoryList", productCategoryList);
			modelMap.put("success", true);
		}else{
			modelMap.put("success", false);
			modelMap.put("errMsg", "empty productId");
		}
		return modelMap;
	}
	@RequestMapping(value = "/modifyproduct",method = RequestMethod.POST)
	@ResponseBody
	private Map<String,Object> modifyProduct(HttpServletRequest request){
		Map<String,Object> modelMap = new HashMap<String,Object>();
		//是商品编辑时候调用  还是上下架操作的时候调用
		//前者进行验证码判断，后者跳过验证码判断
		boolean statusChange = HttpServletRequestUtil.getBoolean(request, "statusChange");
		//验证码判断
		if(!statusChange && !CodeUtil.checkVerifyCode(request)) {
			modelMap.put("success", false);
			modelMap.put("errMsg", "输入了错误的验证码");
			return modelMap;
		}
		
		//接收前端参数的变量的初始化，包括商品，缩略图，详情图列表实体类
		ObjectMapper mapper = new ObjectMapper();
		Product product = null;
		CommonsMultipartFile image = null;
		List<CommonsMultipartFile> imageList = new ArrayList<CommonsMultipartFile>();
		CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(
				request.getSession().getServletContext());
		//若请求中存在文件流，则取出相关的文件（包括缩略图和详情图)
		try {
			if(multipartResolver.isMultipart(request)) {
				MultipartHttpServletRequest multipartHttpServletRequest = (MultipartHttpServletRequest) request;
				//取出缩略图并构建CommonsMultipartFile对象
				if((CommonsMultipartFile) multipartHttpServletRequest.getFile("thumbnail") != null) {
					image = (CommonsMultipartFile)multipartHttpServletRequest.getFile("thumbnail");
				}
				//取出详情图列表并构建List<CommonsMultipartFile>列表对象，最多支持六张图片上传
				for(int i=0;i<IMAGEMAXCOUNT;i++) {
					//若取出的第i个详情图片文件流不为空，则将其加入详情图列表
					if((CommonsMultipartFile)multipartHttpServletRequest.getFile("productImg"+i) !=null) {
						imageList.add((CommonsMultipartFile)multipartHttpServletRequest.getFile("productImg"+i));
					}else {
						//若取出的第i个详情图片文件流为空,终止循环
						break;
					}
				}
			} 
		}catch(Exception e) {
			modelMap.put("success", false);
			modelMap.put("errMsg", e.toString());
			return modelMap;
		}
		try {
			String productStr = HttpServletRequestUtil.getString(request, "productStr");
			product = mapper.readValue(productStr, Product.class);
		}catch(Exception e) {
			modelMap.put("success", false);
			modelMap.put("errMsg", e.toString());
			return modelMap;
		}
		//非空判断
		if(product != null) {
			try {
				//从session中获取当前店铺的Id并赋值给product,建少对前端数据的依赖
				Shop currentShop = (Shop) request.getSession().getAttribute("currentShop");
				Shop shop = new Shop();
				shop.setShopId(currentShop.getShopId());
				product.setShop(shop);
				//开始进行商品信息变更操作
				ProductExecution pe = productService.modifyProduct(product, image, imageList);
				if(pe.getState() == ProductStateEnum.SUCCESS.getState()) {
					modelMap.put("success", true);
				}else {
					modelMap.put("success", false);
					modelMap.put("errMsg", pe.getStateInfo());
				}
			}catch(RuntimeException e) {
				modelMap.put("success", false);
				modelMap.put("errMsg", e.toString());
				 
			}
		}else {
			modelMap.put("success", false);
			modelMap.put("errMsg","请输入商品信息");
		}
		return modelMap;
	}
	@RequestMapping(value = "/getproductlistbyshop",method = RequestMethod.GET)
	@ResponseBody
	private Map<String,Object> getProductListByShop(HttpServletRequest request){
		Map<String,Object> modelMap = new HashMap<String,Object>();
		//获取前台传过来的页码
		int pageIndex = HttpServletRequestUtil.getInt(request, "pageIndex");
		//获取前台传过来的每页要求返回的商品数上限
		int pageSize = HttpServletRequestUtil.getInt(request, "pageSize");
		//从当前session中获取店铺信息,主要是获取shopId
		Shop currentShop = (Shop)request.getSession().getAttribute("currentShop");
		//空值判断
		if((pageIndex>-1)&&(pageSize>-1)&&(currentShop!=null)&&(currentShop.getShopId())!=null) {
			//获取传入的需要检索的条件,包括是否需要从某个商品类别以及模糊查找商品名去筛选某个店铺下的商品列表
			//筛选的条件可以进行排列组合
			long productCategoryId = HttpServletRequestUtil.getLong(request, "productCategoryId");
			String productName = HttpServletRequestUtil.getString(request, "productName");
			Product productCondition = compactProductCondition(currentShop.getShopId(),productCategoryId,productName);
			//传入查询条件以及分页信息进行查询,返回相应的商品列表以及总数
			ProductExecution pe = productService.getProductList(productCondition, pageIndex, pageSize);
			modelMap.put("productList", pe.getProductList());
			modelMap.put("count", pe.getCount());
			modelMap.put("success", true);
		}else {
			modelMap.put("success", false);
			modelMap.put("errMsg", "empty pageSize or pageIndex or Shopid");
		}
		return modelMap;
	}
	private Product compactProductCondition(long shopId,long productCategoryId,String productName) {
		Product productCondition = new Product();
		Shop shop = new Shop();
		shop.setShopId(shopId);
		productCondition.setShop(shop);
		//若有指定类别的要求则添加进去
		if(productCategoryId != -1L) {
			ProductCategory productCategory = new ProductCategory();
			productCategory.setProductCategoryId(productCategoryId);
			productCondition.setProductCategory(productCategory);
		}
		//若有商品名模糊查询的要求则添加进去
		if(productName != null) {
			productCondition.setProductName(productName);
		}
		return productCondition;
	}
}
