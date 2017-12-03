package com.svm.unit;

import com.svm.dto.InitDto;

/**
 * 染色体の遺伝子の値を関数の係数にするクラス
 * @author Administrator
 *
 */
public class ConvertUnit {

	public InitDto convert1(InitDto initDto) {

		double[][] ba1 = new double[InitDto.NN][InitDto.nn];
		ba1 = initDto.getBa1();

		double[][] ac1 = new double[InitDto.NN][InitDto.nn];
		ac1 = initDto.getAc1();

		for (int i = 0; i < InitDto.NN; i++) {
			for(int j = 0; j < InitDto.nn; j++) {
				ac1[i][j]=ba1[i][j];
			}
		}

		initDto.setAc1(ac1);
		return initDto;
	}
}
