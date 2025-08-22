package app.service;

import app.bean.Product;
import app.common.Display;
import app.common.Keyboard;
import app.dao.ProductDAO;

/**
 * 商品更新サービス
 */
public class ProductEditService {

	public void execute() {
		// 更新する商品の商品IDを取得
		int targetId = Keyboard.getInputNumber("商品IDを入力してください。-> ");
		// 商品IDに合致した商品を取得
		ProductDAO dao = new ProductDAO();
		Product product = dao.findById(targetId);
		// 取得した商品の妥当性検査
		if (isNotFound(product)) {
			Display.showMessageln("入力された商品IDの商品は存在しません。");
			return;
		}
		// 商品の更新情報を取得
		String name = Keyboard.getInputString("商品名を入力してください。-> ");
		int price = Keyboard.getInputNumber("価格を入力してください。-> ");
		int quantity = Keyboard.getInputNumber("数量を入力してください。-> ");
		Product target = new Product(targetId, name, price, quantity);
		// 商品を更新する
		dao.save(target);
		// 完了メッセージを表示
		Display.showMessageln("商品を更新しました。");
	}

	private boolean isNotFound(Product product) {
		return (product == null);
	}

}
