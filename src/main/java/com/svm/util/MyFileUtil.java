package com.svm.util;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedHashMap;


public class MyFileUtil {

	/**
	 * インスタンス化禁止のため、コンストラクタをprivateにしている。
	 */
	private MyFileUtil() {}

	/**
	 * CSVファイルを読み込んでマップを返す
	 * @param csvFileInput
	 * @return
	 * @throws Exception
	 */
	public static LinkedHashMap<String,String[]> readCsvFile(String csvFileInput) throws Exception {
		InputStreamReader ir = null;
		BufferedReader bfrdrdrJisho = null;
		LinkedHashMap<String,String[]> map = new LinkedHashMap<String, String[]>();

		try{
			ir = new InputStreamReader(new FileInputStream(csvFileInput) , "UTF-8");
			bfrdrdrJisho = new  BufferedReader(ir);
			String strJisho;
			while ((strJisho = bfrdrdrJisho.readLine()) != null ) {
				String[] tmpArr = strJisho.split(",");
				if (tmpArr == null || tmpArr.length < 1) {
					throw new Exception("データが不正です");
				}
				map.put(tmpArr[0], tmpArr);
			}
		} catch (IOException e){
			e.printStackTrace();
			System.out.println("ファイル読み込み失敗");
		} finally {
			try {
				// ストリームは必ず finally で close
				if (bfrdrdrJisho != null) {
					bfrdrdrJisho.close();
				}
			} catch (IOException e) {
			}
		}
		return map;
	}

	/**
	 * 品詞データ（変数）を読み取る
	 * @param csvHinshiFileInput
	 * @return
	 * @throws Exception
	 */
	public static LinkedHashMap<String,String[]> readCsvHinshi(String csvHinshiFileInput) throws Exception {
		InputStreamReader ir = null;
		BufferedReader bfrdrdrJisho = null;
		LinkedHashMap<String,String[]> map = new LinkedHashMap<String, String[]>();
		int countNum = 1;

		try{
			ir = new InputStreamReader(new FileInputStream(csvHinshiFileInput) , "UTF-8");
			bfrdrdrJisho = new  BufferedReader(ir);
			String strJisho;
			while ((strJisho = bfrdrdrJisho.readLine()) != null ) {
				String[] tmpArr = strJisho.split(",");
				if (tmpArr == null || tmpArr.length < 1) {
					throw new Exception("データが不正です");
				}
				map.put(String.valueOf(countNum), tmpArr);
				countNum++;
			}
		} catch (IOException e){
			e.printStackTrace();
			System.out.println("ファイル読み込み失敗");
		} finally {
			try {
				// ストリームは必ず finally で close
				if (bfrdrdrJisho != null) {
					bfrdrdrJisho.close();
				}
			} catch (IOException e) {
			}
		}
		return map;

	}

	/**
	 * csvファイルを読み取る
	 * @param csvCommonFileInput
	 * @return
	 * @throws Exception
	 */
	public static LinkedHashMap<String,String[]> readCsvCom(String csvHinshiFileInput) throws Exception {
		InputStreamReader ir = null;
		BufferedReader bfrdrdrCom = null;
		LinkedHashMap<String,String[]> map = new LinkedHashMap<String, String[]>();
		int countNum = 1;

		try{
			ir = new InputStreamReader(new FileInputStream(csvHinshiFileInput) , "UTF-8");
			bfrdrdrCom = new  BufferedReader(ir);
			String strCsv;
			while ((strCsv = bfrdrdrCom.readLine()) != null ) {
				String[] tmpArr = strCsv.split(",");
				if (tmpArr == null || tmpArr.length < 1) {
					throw new Exception("データが不正です");
				}
				map.put(String.valueOf(countNum), tmpArr);
				countNum++;
			}
		} catch (IOException e){
			e.printStackTrace();
			System.out.println("ファイル読み込み失敗");
		} finally {
			try {
				// ストリームは必ず finally で close
				if (bfrdrdrCom != null) {
					bfrdrdrCom.close();
				}
			} catch (IOException e) {
			}
		}
		return map;
	}

}
