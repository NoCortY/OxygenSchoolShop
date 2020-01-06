package com.SchoolShop.o2o.service;
import java.util.List;

import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.SchoolShop.o2o.dto.ProductExecution;
import com.SchoolShop.o2o.entity.Product;
import com.SchoolShop.o2o.exceptions.ProductOperationException;

public interface ProductService {
	ProductExecution addProduct(Product product,CommonsMultipartFile image,List<CommonsMultipartFile> imageList)
			throws ProductOperationException;
	Product getProductById(long productId);
	ProductExecution modifyProduct(Product product,CommonsMultipartFile image,List<CommonsMultipartFile> imageList)
		throws ProductOperationException;
	public ProductExecution getProductList(Product productCondition, int pageIndex, int pageSize);
}
