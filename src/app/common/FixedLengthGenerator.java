package app.common;

public class FixedLengthGenerator {
	
	/**
	 * 指定された文字数の文字列に変換する
	 * @param target 変換対象文字列
	 * @param length 変換後の文字数
	 * @return 指定された文字数の文字列
	 */
	public static String execute(String target, int length) {
	    if (target == null) {
	        target = "";
	    }
	    if (target.length() > length) {
	        return target.substring(0, length); // 長い場合は切り詰め
	    } else {
	        return String.format("%-" + length + "s", target); // 短い場合は右側をスペースで埋める
	    }
	}

	/**
	 * 指定された文字数の数字列に変換する
	 * @param target 変換対象整数
	 * @param length 変換後の文字数
	 * @return 指定された文字数の数字列
	 */
	public static String execute(int target, int length) {
		return execute(Integer.toString(target), length);
	}
	
	

}
