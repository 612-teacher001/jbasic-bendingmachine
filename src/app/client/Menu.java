package app.client;

import app.common.Display;
import app.common.Keyboard;
import app.service.PurchaseService;

public class Menu {
	
	/**
	 * クラス定数：メニュインデックス定数群
	 */
	private static final int INDEX_QUIT = 0;
	private static final int INDEX_PURCHASE = 1;
	
	/**
	 * 操作メニュを表示し選択された操作を実行する
	 */
	public static void showMenu() {
		// ループを続けるかどうかを判断するフラグ
		boolean isRunning; // ※ 最初のループで必ず上書きされるので変数宣言のみでOK！
		do {
			// 操作メニュの表示
			displayMenu();
			// メニューインデックスの取得
			int index = Keyboard.getInputNumber("メニューを選択してください。-> ");
			// 取得したメニューインデックスによる処理の分岐
			isRunning = dispatchOperation(index);
		} while(isRunning);
		
		// プログラム終了
		Display.showMessageln("終了します。");
		System.exit(0);
	}

	/**
	 * メニュインデックスによる処理の分岐
	 * @param index メニュインデックス
	 * @return 後の処理がある場合はtrue、それ以外はfalse
	 */
	private static Boolean dispatchOperation(int index) {
		// 戻り値の初期化
		Boolean result = null;
		// メニュインデックスによる処理の分岐
		switch (index) {
		case INDEX_PURCHASE: // 商品購入の場合
			PurchaseService service = new PurchaseService();
			return true;
		case INDEX_QUIT: // 終了の場合
		default: // 未定義のインデックスの場合
			return false;
		}
	}

	/**
	 * メニュを表示する
	 */
	private static void displayMenu() {
		Display.showMessageln("【クライアントメニュ】");
		Display.showMessageln(INDEX_PURCHASE + ". 商品購入");
		Display.showMessageln(INDEX_QUIT + ". 終了");
	}

}
