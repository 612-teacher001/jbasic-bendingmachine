package app.service;

import java.util.List;

import app.bean.Product;
import app.common.FixedLengthGenerator;
import app.dao.ProductDAO;

/**
 * 商品一覧表示サービス
 */
public class ProductListService {

	/**
	 * 商品一覧を表示する
	 */
	public void execute() {
		ProductDAO dao = new ProductDAO();
		List<Product> list = dao.findAll();
		this.showProducts(list);
	}

	/**
	 * 商品リストを表示する
	 * @param list 商品リスト
	 */
	private void showProducts(List<Product> list) {
		System.out.printf("%-6s  %-10s  %-6s  %-3s\n", "商品ID", "商品名", "価格", "数量");
		for (Product bean : list) {
			System.out.printf("%-6s  %-10s  %-6s  %-3s\n", 
								FixedLengthGenerator.execute(bean.getId(), 6), 
								FixedLengthGenerator.execute(bean.getName(), 10),
								FixedLengthGenerator.execute(bean.getPrice(), 6), 
								FixedLengthGenerator.execute(bean.getQuantity(), 3) 
							);
		}
		System.out.println("");
		
	}
	
}
