package com.svm.util;

import java.util.Arrays;


public class GetHinshiUtil {

	/**
	 * インスタンス化禁止のため、コンストラクタをprivateにしている。
	 */
	private GetHinshiUtil() {}

	/**
	 *
	 * @author Administrator
	 *
	 */
	public static enum HINSHI_MEI {

		動詞(1),
		形容詞(2),
		名詞(3),
		副詞(4),
		接続詞(5),
		助動詞(6),
		助詞(7),
		記号(8);

		int hinshiCode;
		HINSHI_MEI(int hinshiCode) {
			this.hinshiCode = hinshiCode;
		}

		static HINSHI_MEI get(int hinshiCode) {
			for (HINSHI_MEI hinshi : values()) {
				if (hinshi.hinshiCode == hinshiCode) {
					return hinshi;
				}
			}
			return null;
		}
	}

	/**
	 * GAによる染色体値から品詞名を返す
	 *
	 * @param gaParameter GAによる染色体値
	 * @return
	 */
	public static String getHinshiFromGA(double gaParameter) {

		//GAの遺伝子から品詞名を返す
		return HINSHI_MEI.get((int)Math.round(gaParameter)).toString();
	}

	/**
	 * 配列に指定の文字列が入っていれば、trueを返す
	 * @param hinshiMei
	 * @param hinArray
	 * @return
	 */
	public static boolean containHinshi(String hinshiMei, String[] hinshiArray) {

		if(Arrays.asList(hinshiArray).contains(hinshiMei)){
			return true;
		}

		return false;
	}

	/**
	 * 実数を四捨五入して文字列を返す
	 * @param gaParameter
	 * @return
	 */
	public static String getHinshiNum(double gaParameter) {

		//GAの遺伝子から番号を返す
		return String.valueOf((int)Math.round(gaParameter));
	}

}
