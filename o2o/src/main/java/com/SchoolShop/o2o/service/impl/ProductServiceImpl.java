package com.SchoolShop.o2o.service.impl;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.SchoolShop.o2o.dao.ProductDao;
import com.SchoolShop.o2o.dao.ProductImgDao;
import com.SchoolShop.o2o.dto.ProductExecution;
import com.SchoolShop.o2o.entity.Product;
import com.SchoolShop.o2o.entity.ProductImg;
import com.SchoolShop.o2o.enums.ProductStateEnum;
import com.SchoolShop.o2o.exceptions.ProductOperationException;
import com.SchoolShop.o2o.service.ProductService;
import com.SchoolShop.o2o.util.FileUtil;
import com.SchoolShop.o2o.util.ImageUtil;
import com.SchoolShop.o2o.util.PageCalculator;
import com.SchoolShop.o2o.util.PathUtil;

@Service
public class ProductServiceImpl implements ProductService {
	@Autowired
	private ProductDao productDao;
	@Autowired
	private ProductImgDao productImgDao;

	@Override
	@Transactional//spring事务管理
	public ProductExecution addProduct(Product product, CommonsMultipartFile image, List<CommonsMultipartFile> imageList)
			throws ProductOperationException {
		/*
		 * 1.处理缩略图，获取缩略图的相对路径并赋值给product
		 * 2.往tb_product表中写入商品信息
		 * 3.结合productId 批量处理商品详情图
		 * 4.将商品详情图列表批量插入tb_product_img中
		 * */
		//空值判断
		if(product!=null && product.getShop()!=null && product.getShop().getShopId()!=null) {
			//给商品设置上默认属性
			product.setCreateTime(new Date());
			product.setLastEditTime(new Date());
			//默认为上架状态
			product.setEnableStatus(1);
			//若商品缩略图不为空则添加
			if(image!=null) {
				try {
					addImage(product,image);
				} catch (UnsupportedEncodingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			try {
				//创建商品信息
				int effectedNum = productDao.insertProduct(product);
				if(effectedNum<=0) {
					throw new ProductOperationException("商品创建失败,数据库异常");
				}
			}catch(Exception e) {
				throw new ProductOperationException("商品创建失败"+e.getMessage());
			}
			//若商品详情图不为空
			if(imageList!=null&&imageList.size()>0) {
				try {
					addProductImageList(product,imageList);
				} catch (UnsupportedEncodingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			return new ProductExecution(ProductStateEnum.SUCCESS,product);
		}else {
			return new ProductExecution(ProductStateEnum.EMPTY_LIST);
		}
	}

	//添加缩略图函数
	private void addImage(Product product,CommonsMultipartFile image) throws UnsupportedEncodingException {
		//创建临时File变量
		File tempImg = null;
		try {
			tempImg = new File(PathUtil.getImgBasePath()+ImageUtil.getRandomFileName()+ImageUtil.getFileExtension(image));
		} catch (UnsupportedEncodingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		try {
			FileUtil.inputStreamToFile(image.getInputStream(),tempImg);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
 		String dest = PathUtil.getShopImagePath(product.getShop().getShopId());
 		String imageAddr = ImageUtil.generateThumbnail(tempImg, dest);
		product.setImgAddr(imageAddr);
		tempImg.delete();
	}
	private void addProductImageList(Product product,List<CommonsMultipartFile> productImageList) throws UnsupportedEncodingException {
		//获取图片存储路径 直接存放到相应店铺文件夹底下
		String dest = PathUtil.getShopImagePath(product.getShop().getShopId());
		List<ProductImg> productImgList = new ArrayList<ProductImg>();
		//遍历图片一次并添加进productImg实体类里
		for(CommonsMultipartFile productImage:productImageList) {
			File tempImgListItem = new File(PathUtil.getImgBasePath()+ImageUtil.getRandomFileName()+ImageUtil.getFileExtension(productImage));
			try {
				FileUtil.inputStreamToFile(productImage.getInputStream(),tempImgListItem);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			String imgAddr = ImageUtil.generateNormalThumbnail(tempImgListItem, dest);
			ProductImg productImg = new ProductImg();
			productImg.setImgAddr(imgAddr);
			productImg.setProductId(product.getProductId());
			productImg.setCreateTime(new Date());
			productImgList.add(productImg);
			tempImgListItem.delete();
		}
		//如果确实有图片需要添加，执行批量添加操作
		if(productImgList.size()>0) {
			try {
				int effectedNum = productImgDao.batchInsertProductImg(productImgList);
				if(effectedNum<=0) {
					throw new ProductOperationException("创建商品详情图片失败");
				}
			}catch(Exception e) {
				throw new ProductOperationException("创建商品详情图片失败:"+e.toString());
			}
		}
	}

	@Override
	public Product getProductById(long productId) {
		return productDao.queryProductById(productId);
	}

	@Override
	@Transactional
	/*1.若缩略图参数有值，则处理缩略图，
	 * 若原先存在缩略图则先删除再添加新图，之后获取缩略图相对路径并赋值给product
	 * 2.若商品详情图列表参数有值，对商品详情图片列表进行同样的操作
	 * 3.将tb_product_img下面的该商品原先的商品详情图记录全部清除
	 * 4.更新tb_product的信息*/
	public ProductExecution modifyProduct(Product product, CommonsMultipartFile image,
			List<CommonsMultipartFile> imageList) throws ProductOperationException {
		//空值判断
		if(product != null&&product.getShop()!=null&&product.getShop().getShopId()!=null) {
			//给商品设置上默认属性
			product.setLastEditTime(new Date());
			//若商品缩略图不为空且原有缩略图不为空则删除原有缩略图并添加
			if(image != null) {
				Product tempProduct = productDao.queryProductById(product.getProductId());
				if(tempProduct.getImgAddr() != null) {
					try {
						ImageUtil.deleteFileOrPath(tempProduct.getImgAddr());
					} catch (UnsupportedEncodingException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				try {
					addImage(product,image);
				} catch (UnsupportedEncodingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if(imageList != null && imageList.size()>0) {
				deleteProductImgList(product.getProductId());
				try {
					addProductImageList(product,imageList);
				} catch (UnsupportedEncodingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} 
			}
			try {
				//更新商品信息
				int effectedNum = productDao.updateProduct(product);
				if(effectedNum <= 0) {
					throw new ProductOperationException("更新商品信息失败");
				}
				return new ProductExecution(ProductStateEnum.SUCCESS,product);
			}catch(Exception e) {
				System.out.println(e.toString());
				throw new ProductOperationException("更新商品信息失败:"+e.toString());
			}
			
		}else {
			return new ProductExecution(ProductStateEnum.EMPTY_LIST);
		}
	}
	private void deleteProductImgList(long productId) {
		List<ProductImg> productImgList = productImgDao.queryProductImgList(productId);
		//删除原来的图片
		for(ProductImg productImg : productImgList) {
			try {
				ImageUtil.deleteFileOrPath(productImg.getImgAddr());
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		productImgDao.deleteProductImgByProductId(productId);
	}

	@Override
	public ProductExecution getProductList(Product productCondition, int pageIndex, int pageSize) {
		int rowIndex = PageCalculator.calculateRowIndex(pageIndex, pageSize);
		List<Product> productList = productDao.queryProductList(productCondition, rowIndex, pageSize);
		int count = productDao.queryProductCount(productCondition);
		ProductExecution pe = new ProductExecution();
		pe.setProductList(productList);
		pe.setCount(count);
		return pe;
	}
}
