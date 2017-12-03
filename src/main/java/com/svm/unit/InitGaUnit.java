package com.svm.unit;

import java.util.Random;

import com.svm.dto.InitDto;


/**
 * 初期遺伝子をつくるクラス
 * @author Administrator
 *
 */
public class InitGaUnit {

	//ランダムーサーチをして，ある程度制約を満たした解を
	//初期個体とする．
	public InitDto initGa(){

		//Randomクラスのインスタンス化
        Random rnd = new Random();
        InitDto initDto = new InitDto();

    	/** 各個体の適応度 初期化 */
		double[][] x1 = new double[InitDto.NN][InitDto.minA.length];
		double[][] ac1 = new double[InitDto.NN][InitDto.minA.length];
		double[][] ba1 = new double[InitDto.NN][InitDto.minA.length];

		for(int i1 =0; i1 < InitDto.NN; i1++){
		    for (int i2 = 0; i2 < InitDto.minA.length; i2++) { // 変数の数分
		          x1[i1][i2] = (InitDto.maxA[i2]-InitDto.minA[i2])*(rnd.nextDouble()) + InitDto.minA[i2];    /*乱数を使って実数*/
		          ba1[i1][i2] = x1[i1][i2];    /*バックアップをとる*/
		          ac1[i1][i2] = ba1[i1][i2];
		    }
		}

		initDto.setAc1(ac1);
		initDto.setX1(x1);
		initDto.setBa1(ba1);

		return initDto;
	}
}
