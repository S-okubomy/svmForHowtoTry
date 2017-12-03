package com.svm.dto;

import java.io.Serializable;


/**
 * 初期遺伝子をつくる際に使用する
 * Javabeans
 * @author t_okubomy
 */
public class InitDto implements Serializable {

//	public static final double minA0=-2.0,maxA0=2.0;   //σの係数の範囲
//	public static final double minA1=-2.0,maxA1=2.0;   //期間の範囲
//	public static final double minA2=-2.0,maxA2=2.0;  //avaの係数
//	public static final double minA3=-2.0,maxA3=2.0;  //全体へｎの係数

//    // 変数の最小値
//	public static final double[] minA 
//	   = {0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0};
//	
//	// 変数の最大値
//	public static final double[] maxA 
//       = {1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0};
    
    // 変数の最小値
    public static final double[] minA 
       = {0.0};
    // 変数の最大値
    public static final double[] maxA 
       = {100.0};

	public static final int NN = 1;                        //個体数
	public static final int calKabuNo = 50;                 //指定の株コードまで調整すること
	public static final int buyDay = 30;                    //売買期間 日
	public static final int calSedai = 1;                     //計算世代数
	public static final String keisanKabuCode = "3250";    //計算世代数
	public static final int nn = minA.length;               //染色体の数
	

	/** x1[][]:染色体 */
	private static double[][] x1;  //共通変数にする

	/** ba1[][]:染色体のバックアップ */
	private static double[][] ba1; //共通変数にする

	/** ac[][]:目的関数の係数 */
	private static double[][] ac1; //共通変数にする

	/** 重み係数 ω(プロジェクト固有） **/
	private static int[] weightParam;  //共通変数にする

	public InitDto() {
	}

//	/** 1件分の支店コードと支店名を初期値として代入するコンストラクタ */
//	public LnitData(String branchCode, String branchName ,int branchAmount) {
//		this.branchCode = branchCode;
//		this.branchName=branchName;
//		this.branchAmount=branchAmount;
//
//	}

	public void setX1(double[][] x1){
		InitDto.x1 = x1;
    }

	public void setBa1(double[][] ba1){
		InitDto.ba1 = ba1;
    }

	public void setAc1(double[][] ac1){
		InitDto.ac1 = ac1;
    }

	public void setWeightParam(int[] weightParam){
        this.weightParam = weightParam;
    }


	/** 染色体を返すゲッター　*/
	public double[][] getX1() {
		return InitDto.x1;
	}

	/** 染色体のバックアップを返すゲッター　*/
	public double[][] getBa1() {
		return InitDto.ba1;
	}

	/** 目的関数の係数を返すゲッター　*/
	public double[][] getAc1() {
		return InitDto.ac1;
	}

	public int[] getWeightParam() {
		return this.weightParam;
	}




}
