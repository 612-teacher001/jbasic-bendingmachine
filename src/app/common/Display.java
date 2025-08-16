package app.common;

public class Display {

	/**
	 * 改行付きでメッセージを表示する
	 * @param message 表示するメッセージ
	 */
	public static void showMessageln(String message) {
		System.out.println(message);
	}
	
	/**
	 * 改行なしでメッセージを表示する
	 * @param message 表示するメッセージ
	 */
	public static void showMessage(String message) {
		System.out.print(message);
	}

}
