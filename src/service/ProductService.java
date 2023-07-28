package service;

import java.util.List;

import entity.Product;
import repository.ProductRepository;

public class ProductService {
	
	private ProductRepository productRepository;
	private static ProductService instance;
	
	private ProductService() {
		productRepository = ProductRepository.getInstance();
	}
	
	public static ProductService getInstance() {
		if(instance == null) {
			instance = new ProductService();
		}
		return instance;
	}
	
	public boolean isProductNameDuplicated(String productName) {
		//not null이면 중복이 된것 = 생성불가
		return productRepository.findProductName(productName) != null;
	}
	
	public boolean registerProduct(Product product) {
		return productRepository.saveProduct(product) > 0;
	}
	
	public List<Product> searchProduct(String searchOption, String searchValue) {
		
		return ProductRepository.getInstance().getSearchProductList(searchOption, searchValue);
	}
	
	public boolean removeProduct(int ProductId) {
		return productRepository.deleteProduct(ProductId) > 0;
	}
	
	public Product getProductByProductId(int productId) {
		return productRepository.findProductId(productId);
	}
	
}
