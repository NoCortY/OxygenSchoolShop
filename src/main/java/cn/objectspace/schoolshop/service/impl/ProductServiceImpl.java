package cn.objectspace.schoolshop.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import cn.objectspace.schoolshop.dao.ProductDao;
import cn.objectspace.schoolshop.dao.ProductImgDao;
import cn.objectspace.schoolshop.dto.ProductExecution;
import cn.objectspace.schoolshop.entity.Product;
import cn.objectspace.schoolshop.entity.ProductImg;
import cn.objectspace.schoolshop.enums.ProductStateEnum;
import cn.objectspace.schoolshop.service.ProductService;
import cn.objectspace.schoolshop.util.FileUtil;
import cn.objectspace.schoolshop.util.ImageUtil;
import cn.objectspace.schoolshop.util.PageCalculator;

@Service
public class ProductServiceImpl implements ProductService {
	@Autowired
	private ProductDao productDao;
	@Autowired
	private ProductImgDao productImgDao;

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

	@Override
	public Product getProductById(long productId) {
		return productDao.queryProductByProductId(productId);
	}

	@Override
	@Transactional
	public ProductExecution addProduct(Product product, CommonsMultipartFile thumbnail,
			List<CommonsMultipartFile> productImgs) throws RuntimeException {
		if (product != null && product.getShop() != null && product.getShop().getShopId() != null) {
			product.setCreateTime(new Date());
			product.setLastEditTime(new Date());
			product.setEnableStatus(1);
			if (thumbnail != null) {
				addThumbnail(product, thumbnail);
			}
			try {
				int effectedNum = productDao.insertProduct(product);
				if (effectedNum <= 0) {
					throw new RuntimeException("创建商品失败");
				}
			} catch (Exception e) {
				throw new RuntimeException("创建商品失败:" + e.toString());
			}
			if (productImgs != null && productImgs.size() > 0) {
				addProductImgs(product, productImgs);
			}
			return new ProductExecution(ProductStateEnum.SUCCESS, product);
		} else {
			return new ProductExecution(ProductStateEnum.EMPTY);
		}
	}

	@Override
	@Transactional
	public ProductExecution modifyProduct(Product product, CommonsMultipartFile thumbnail,
			List<CommonsMultipartFile> productImgs) throws RuntimeException {
		if (product != null && product.getShop() != null && product.getShop().getShopId() != null) {
			product.setLastEditTime(new Date());
			if (thumbnail != null) {
				Product tempProduct = productDao.queryProductByProductId(product.getProductId());
				if (tempProduct.getImgAddr() != null) {
					FileUtil.deleteFile(tempProduct.getImgAddr());
				}
				addThumbnail(product, thumbnail);
			}
			if (productImgs != null && productImgs.size() > 0) {
				deleteProductImgs(product.getProductId());
				addProductImgs(product, productImgs);
			}
			try {
				int effectedNum = productDao.updateProduct(product);
				if (effectedNum <= 0) {
					throw new RuntimeException("更新商品信息失败");
				}
				return new ProductExecution(ProductStateEnum.SUCCESS, product);
			} catch (Exception e) {
				throw new RuntimeException("更新商品信息失败:" + e.toString());
			}
		} else {
			return new ProductExecution(ProductStateEnum.EMPTY);
		}
	}

	private void addProductImgs(Product product, List<CommonsMultipartFile> productImgs) {
		String dest = FileUtil.getShopImagePath(product.getShop().getShopId());
		List<String> imgAddrList = ImageUtil.generateNormalImgs(productImgs, dest);
		if (imgAddrList != null && imgAddrList.size() > 0) {
			List<ProductImg> productImgList = new ArrayList<ProductImg>();
			for (String imgAddr : imgAddrList) {
				ProductImg productImg = new ProductImg();
				productImg.setImgAddr(imgAddr);
				productImg.setProductId(product.getProductId());
				productImg.setCreateTime(new Date());
				productImgList.add(productImg);
			}
			try {
				int effectedNum = productImgDao.batchInsertProductImg(productImgList);
				if (effectedNum <= 0) {
					throw new RuntimeException("创建商品详情图片失败");
				}
			} catch (Exception e) {
				throw new RuntimeException("创建商品详情图片失败:" + e.toString());
			}
		}
	}

	private void deleteProductImgs(long productId) {
		List<ProductImg> productImgList = productImgDao.queryProductImgList(productId);
		for (ProductImg productImg : productImgList) {
			FileUtil.deleteFile(productImg.getImgAddr());
		}
		productImgDao.deleteProductImgByProductId(productId);
	}

	private void addThumbnail(Product product, CommonsMultipartFile thumbnail) {
		String dest = FileUtil.getShopImagePath(product.getShop().getShopId());
		String thumbnailAddr = ImageUtil.generateThumbnail(thumbnail, dest);
		product.setImgAddr(thumbnailAddr);
	}
}
