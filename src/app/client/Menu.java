package app.client;

import app.common.Keyboard;
import app.service.PurchaseService;

public class Menu {
	
	/**
	 * 操作メニュを表示し選択された操作を実行する
	 */
	public static void showMenu() {
		// ループを続けるかどうかを判断するフラグ
		Boolean isRunning = null;
		do {
			// 操作メニュの表示
			System.out.println("【クライアントメニュ】");
			System.out.println("1. 商品購入");
			System.out.println("0. 終了");
			// メニューインデックスの取得
			int index = Keyboard.getInputNumber("メニューを選択してください。-> ");
			// 取得したメニューインデックスによる処理の分岐
			switch (index) {
			case 1:
				PurchaseService service = new PurchaseService();
				isRunning = true;
				break;
			default:
				isRunning = false;
				break;
			}
		} while(isRunning);
		
		// プログラム終了
		System.out.println("終了します。");
		System.exit(0);
	}

}
