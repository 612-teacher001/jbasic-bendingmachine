package app.bean;

import java.io.Serializable;

/**
 * productsテーブルの1レコードを管理するJavaBeans
 */
public class Product implements Serializable {
	
	/**
	 * フィールド
	 */
	private int id;       // 商品ID
	private String name;  // 商品名
	private int price;    // 価格
	private int quantity; // 数量
	
	/**
	 * 引数なしコンストラクタ：デフォルトコンストラクタと機能的に同値
	 */
	public Product() {}

	/**
	 * コンストラクタ
	 * @param name     商品名
	 * @param price    価格
	 * @param quantity 数量
	 */
	public Product(String name, int price, int quantity) {
		this.name = name;
		this.price = price;
		this.quantity = quantity;
	}

	/**
	 * コンストラクタ
	 * @param id       商品ID
	 * @param name     商品名
	 * @param price    価格
	 * @param quantity 数量
	 */
	public Product(int id, String name, int price, int quantity) {
		this(name, price, quantity);
		this.id = id;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Product [");
		builder.append("id=" + id + ", ");
		builder.append("name=" + name + ", ");
		builder.append("price=" + price + ", ");
		builder.append("quantity=" + quantity);
		builder.append("]");
		return builder.toString();
	}
	
}
