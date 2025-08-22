package app.service;

import app.bean.Product;
import app.common.Display;
import app.common.Keyboard;
import app.dao.ProductDAO;

/**
 * 商品追加サービス
 */
public class ProductRegistService {

	
	/**
	 * 商品を新規追加する
	 */
	public void execute() {
		// キーボードから「商品名」「価格」「数量」を取得
		String name = Keyboard.getInputString("商品名を入力してください。-> ");
		int price = Keyboard.getInputNumber("価格を入力医してください。-> ");
		int quantity = Keyboard.getInputNumber("数量を入力してください。-> ");
		// 入力値から商品をインスタンス化
		Product product = new Product(name, price, quantity);
		// 商品インスタンスを登録
		ProductDAO dao = new ProductDAO();
		dao.save(product);
		// 完了メッセージを表示
		Display.showMessageln("商品を登録しました。\n");
	}

}
