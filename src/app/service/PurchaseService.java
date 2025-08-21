package app.service;

import java.util.List;

import app.bean.Product;
import app.common.Display;
import app.common.FixedLengthGenerator;
import app.common.Keyboard;
import app.dao.ProductDAO;

public class PurchaseService {

	/**
	 * 商品購入手続を実行する
	 */
	public void execute() {
		// 1. ユーザから投入金額を受け取る
		//    → 「残金」を計算する基準になるので最初に入力させる
		int charge = Keyboard.getInputNumber("商品を購入するための金額を入力してください。-> ");
		do {
			// 2. 残金と商品一覧の表示
			// 2-1. 現在の残金を表示 → ユーザが今いくら使えるのかを都度わかるようにする
			Display.showMessageln("残額　：　" + charge + "円");

			// 2-2. 商品一覧を取得して表示 → ユーザがどの商品を買えるか判断できるようにする
			ProductDAO dao = new ProductDAO();
			List<Product> list = dao.findAll();
			showProducts(list);
			
			// 3. 購入を続けるかどうかを確認 → ユーザが購入をやめたい場合はここで終了できるようにする
			String input = Keyboard.getInputString("商品購入手続きを行いますか？（y/n）-> ");
			if (!input.equals("y")) {
				// 購入を中断する場合 → 入力値が「y」以外である場合（仕様には詳細がないでのこの条件にしておく） 
				return;
			}

			// 4. 購入処理
			// 4-1. 購入する商品ID取得と商品インスタンスの取得
			int id = Keyboard.getInputNumber("購入する商品IDを入力してください。-> ");
			Product product = dao.findById(id);
			
			// 4-2. 商品が存在しない場合 → 誤ったIDを入力したケース。これ以上進められないので終了
			if (product == null) {
				Display.showMessageln("該当する商品がありません。");
				return;
			}

			// 4-3. 在庫切れの場合 → 数量が0なので購入不可能
			if (product.getQuantity() == 0) {
				Display.showMessageln("売り切れです。購入できません。");
				return;
			}

			// 4-4. 残金不足の場合 → 購入金額より少ないので処理を進められない
			if (charge < product.getPrice()) {
				Display.showMessageln("お金が足りません。購入できません。");
				return;
			}
			
			// 4-5. 購入処理を実行する → 残金を減らし、在庫を1つ減らしてDBに保存する
			charge -= product.getPrice();
			int quantity = product.getQuantity() - 1;
			product.setQuantity(quantity);
			dao.save(product);
			
			// 4-6. 購入完了のメッセージを表示
			Display.showMessageln("\nご購入ありがとうございました。");
			
			// 4-7. 購入商品の表示
			Display.showMessageln("■ 購入した商品");
			showProduct(product);

		} while (charge > 0); 
		// 5. 残金が0円になるまでは繰り返す → お金がなくなると購入を続けられないのでループ終了
		
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
		
	}

	/**
	 * 商品インスタンスを表示する
	 * @param product 表示する商品インスタンス
	 */
	private void showProduct(Product product) {
		System.out.printf("%-6s  %-10s  %-6s\n", "商品ID", "商品名", "価格");
		System.out.printf("%-6s  %-10s  %-6s\n\n", 
							FixedLengthGenerator.execute(product.getId(), 6), 
							FixedLengthGenerator.execute(product.getName(), 10),
							FixedLengthGenerator.execute(product.getPrice(), 6)
						);
		
	}

}


