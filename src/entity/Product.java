package entity;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class Product {
	private int productId;
	private String productName;
	private String productPrice;
	private int productColorId;
	private int productCategoryID;
	
}
