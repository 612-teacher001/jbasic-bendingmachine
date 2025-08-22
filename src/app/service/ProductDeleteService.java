package app.service;

import app.bean.Product;
import app.common.Display;
import app.common.Keyboard;
import app.dao.ProductDAO;

/**
 * 商品削除サービス
 */
public class ProductDeleteService {

	/**
	 * 商品IDを元にして商品を削除する
	 */
	public void execute() {
		// 削除対象商品の商品IDを取得
		int targetId = Keyboard.getInputNumber("商品IDを入力してください。-> ");
		// 取得した商品IDをもとに商品を取得
		ProductDAO dao = new ProductDAO();
		Product target = dao.findById(targetId);
		if (isNotFound(target)) {
			Display.showMessageln("入力された商品IDの商品は存在しません。");
			return;
		}
		// 商品を削除
		dao.deleteById(targetId);
		Display.showMessageln("商品を削除しました。");
	}

	private boolean isNotFound(Product target) {
		return (target == null);
	}

}
