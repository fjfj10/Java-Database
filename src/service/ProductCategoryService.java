package service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;

import com.mysql.cj.protocol.Resultset;

import entity.ProductCategory;
import entity.ProductColor;
import repository.ProductCategoryRepository;


public class ProductCategoryService {
	
	private ProductCategoryRepository productCategoryRepository;
	private static ProductCategoryService instance;
	
	private ProductCategoryService() {
		productCategoryRepository = productCategoryRepository.getInstance();
	}
	
	public static ProductCategoryService getInstance() {
		if(instance == null) {
			instance = new ProductCategoryService();
		}
		return instance;
	}

	public List<String> getProductCategoryNameList() {
		List<String> productCategoryNameList = new ArrayList<>();
		productCategoryRepository.getProductCategoryListAll().forEach(productCategory -> {
			productCategoryNameList.add(productCategory.getProductCategoryName());
		});
		return productCategoryNameList;
	}
	
//	중복이 되면 result = true
	public boolean isProductCategoryNameDuplicated(String productCategoryName) {
		boolean result = false;
		result = productCategoryRepository.findProductCategorybyProductCategoryName(productCategoryName) != null;
		return result;
	}
	
	public boolean registerProductCategory(ProductCategory productCategory) {
		boolean result = false;
		//
		result = productCategoryRepository.saveProductCategory(productCategory) > 0;
		return result;
	}
}
