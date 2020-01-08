package cn.objectspace.schoolshop.service;

import java.util.List;

import org.springframework.web.multipart.commons.CommonsMultipartFile;

import cn.objectspace.schoolshop.dto.ProductExecution;
import cn.objectspace.schoolshop.entity.Product;

public interface ProductService {
	ProductExecution getProductList(Product productCondition, int pageIndex, int pageSize);

	Product getProductById(long productId);

	ProductExecution addProduct(Product product, CommonsMultipartFile thumbnail, List<CommonsMultipartFile> productImgs)
			throws RuntimeException;

	ProductExecution modifyProduct(Product product, CommonsMultipartFile thumbnail,
			List<CommonsMultipartFile> productImgs) throws RuntimeException;
}
