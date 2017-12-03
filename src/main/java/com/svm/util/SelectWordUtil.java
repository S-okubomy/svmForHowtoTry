package com.svm.util;


public class SelectWordUtil {

	/**
	 * インスタンス化禁止のため、コンストラクタをprivateにしている。
	 */
	private SelectWordUtil() {}


	/**
	 * 文字列から指定の文字間を抜き出す
	 * @param line
	 * @param beforeWord
	 * @param afterWord
	 * @return　抜き出した文字列 指定の文字列がない場合は、そのまま返す
	 */
	public static String selectWord(String line, String beforeWord, String afterWord ) {

		if (!SelectWordUtil.isNullOrEmpty(line)) {
			int indexBefore = line.indexOf(beforeWord);
			if (indexBefore == -1) {
				return line;
			}

			indexBefore += beforeWord.length();
			int indexAfter = line.indexOf(afterWord);
			if (indexAfter == -1) {
				return line;
			}

			return line.substring(indexBefore,indexAfter) ;
		} else {
			return "";
		}

	}

	/**
	 * 文字列から指定の文字間を抜き出す
	 * @param line
	 * @param beforeWord
	 * @param afterWord
	 * @return　抜き出した文字列 指定の文字列がない場合は、空文字を返す
	 */
	public static String selectWordWithEmp(String line, String beforeWord, String afterWord ) {

		if (!SelectWordUtil.isNullOrEmpty(line)) {
			int indexBefore = line.indexOf(beforeWord);
			if (indexBefore == -1) {
				return "";
			}

			indexBefore += beforeWord.length();
			int indexAfter = line.indexOf(afterWord);
			if (indexAfter == -1) {
				return "";
			}

			return line.substring(indexBefore,indexAfter) ;
		} else {
			return "";
		}

	}

	/**
	 * 文字列から指定の文字から後ろを取り出す
	 * @param line
	 * @param beforeWord
	 * @param afterWord
	 * @return　抜き出した文字列 指定の文字列がない場合は、空文字を返す
	 */
	public static String selectWordKoubuWithEmp(String line, String beforeWord) {

		if (!SelectWordUtil.isNullOrEmpty(line)) {
			int indexBefore = line.indexOf(beforeWord);
			if (indexBefore == -1) {
				return "";
			}

			indexBefore += beforeWord.length();

			return line.substring(indexBefore);
		} else {
			return "";
		}

	}


	/**
	 * strがnullもしくは空文字であればtrueを返す
	 * @param str
	 * @return
	 */
	public static boolean isNullOrEmpty(String str) {

	    return (str == null || str.length() == 0);
	}
}
