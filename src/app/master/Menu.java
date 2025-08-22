package app.master;

import app.common.Display;
import app.common.Keyboard;
import app.service.ProductDeleteService;
import app.service.ProductEditService;
import app.service.ProductListService;
import app.service.ProductRegistService;

public class Menu {
	
	/**
	 * クラス定数：メニュインデックス定数群
	 */
	private static final int INDEX_QUIT   = 0;
	private static final int INDEX_LIST   = 1;
	private static final int INDEX_REGIST = 2;
	private static final int INDEX_EDIT   = 3;
	private static final int INDEX_DELETE = 4;

	public static void showMenu() {
		boolean isRunning;
		do {
			// 操作メニュの表示
			displayMenu();
			// メニューインデックスの取得
			int index = Keyboard.getInputNumber("メニューを選択してください。-> ");
			// 取得したメニューインデックスによる処理の分岐
			isRunning = dispatchOperation(index);
		} while (isRunning);
		
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
		boolean isRunning;
		switch (index) {
		case INDEX_LIST:
			ProductListService productListService = new ProductListService();
			productListService.execute();
			isRunning = true;
			break;
		case INDEX_REGIST:
			ProductRegistService productRegistService = new ProductRegistService();
			productRegistService.execute();
			isRunning = true;
			break;
		case INDEX_EDIT:
			ProductEditService productEditService = new ProductEditService();
			isRunning = true;
			break;
		case INDEX_DELETE:
			ProductDeleteService productDeleteService = new ProductDeleteService();
			isRunning = true;
			break;
		default:
			isRunning = false;
			break;
		}
		return isRunning;
	}

	/**
	 * メニュを表示する
	 */
	private static void displayMenu() {
		Display.showMessageln("【マスター・メニュー】");
		Display.showMessageln(INDEX_LIST + ". 商品情報一覧");
		Display.showMessageln(INDEX_REGIST + ". 商品情報追加");
		Display.showMessageln(INDEX_EDIT + ". 商品情報変更");
		Display.showMessageln(INDEX_DELETE + ". 商品情報削除");
		Display.showMessageln(INDEX_QUIT + ". 終了");
	}
}
