package cn.objectspace.schoolshop.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.objectspace.schoolshop.dao.ProductCategoryDao;
import cn.objectspace.schoolshop.dao.ProductDao;
import cn.objectspace.schoolshop.dto.ProductCategoryExecution;
import cn.objectspace.schoolshop.entity.ProductCategory;
import cn.objectspace.schoolshop.enums.ProductCategoryStateEnum;
import cn.objectspace.schoolshop.service.ProductCategoryService;

@Service
public class ProductCategoryServiceImpl implements ProductCategoryService {
	@Autowired
	private ProductCategoryDao productCategoryDao;
	@Autowired
	private ProductDao productDao;

	@Override
	public List<ProductCategory> getByShopId(long shopId) {
		return productCategoryDao.queryByShopId(shopId);
	}

	@Override
	@Transactional
	public ProductCategoryExecution batchAddProductCategory(
			List<ProductCategory> productCategoryList) throws RuntimeException {
		if (productCategoryList != null && productCategoryList.size() > 0) {
			try {
				int effectedNum = productCategoryDao
						.batchInsertProductCategory(productCategoryList);
				if (effectedNum <= 0) {
					throw new RuntimeException("店铺类别失败");
				} else {

					return new ProductCategoryExecution(
							ProductCategoryStateEnum.SUCCESS);
				}

			} catch (Exception e) {
				throw new RuntimeException("batchAddProductCategory error: "
						+ e.getMessage());
			}
		} else {
			return new ProductCategoryExecution(
					ProductCategoryStateEnum.INNER_ERROR);
		}

	}

	@Override
	@Transactional
	public ProductCategoryExecution deleteProductCategory(
			long productCategoryId, long shopId) throws RuntimeException {
		try {
			int effectedNum = productDao
					.updateProductCategoryToNull(productCategoryId);
			if (effectedNum < 0) {
				throw new RuntimeException("商品类别更新失败");
			}
		} catch (Exception e) {
			throw new RuntimeException("deleteProductCategory error: "
					+ e.getMessage());
		}
		try {
			int effectedNum = productCategoryDao.deleteProductCategory(
					productCategoryId, shopId);
			if (effectedNum <= 0) {
				throw new RuntimeException("店铺类别删除失败");
			} else {
				return new ProductCategoryExecution(
						ProductCategoryStateEnum.SUCCESS);
			}

		} catch (Exception e) {
			throw new RuntimeException("deleteProductCategory error: "
					+ e.getMessage());
		}
	}

}
