package service;

import java.util.ArrayList;
import java.util.List;

import entity.ProductColor;
import repository.ProductColorRepository;

public class ProductColorService {
	
	private ProductColorRepository productColorRepository;
	private static ProductColorService instance;
	
	private ProductColorService() {
		productColorRepository = productColorRepository.getInstance();
	}
	
	public static ProductColorService getInstance() {
		if(instance == null) {
			instance = new ProductColorService();
		}
		return instance;
	}
	
	public List<String> getProductColorNameList() {
		List<String> productColorNameList = new ArrayList<>();
		//재사용을 염두해두고 짜둔것 나중에 ProductColorListAll을 변형해서 적용가능
		productColorRepository.getProductColorListAll().forEach(productColor -> {
			productColorNameList.add(productColor.getProductColorName());
		});
		
		return productColorNameList;
	}
	
//	중복이 되면 result = true
	public boolean isProductColorNameDuplicated(String productColorName) {
		boolean result = false;
		result = productColorRepository.findProductColorbyProductColorName(productColorName) != null;
		return result;
	}
	
	public boolean registerProductColor(ProductColor productColor) {
		boolean result = false;
		//
		result = productColorRepository.saveProductColor(productColor) > 0;
		return result;
	}
}
