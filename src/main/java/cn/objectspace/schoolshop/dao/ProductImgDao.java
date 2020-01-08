package cn.objectspace.schoolshop.dao;

import java.util.List;

import cn.objectspace.schoolshop.entity.ProductImg;

public interface ProductImgDao {

	List<ProductImg> queryProductImgList(long productId);

	int batchInsertProductImg(List<ProductImg> productImgList);

	int deleteProductImgByProductId(long productId);
}
