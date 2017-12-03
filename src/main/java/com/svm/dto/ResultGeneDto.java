package com.svm.dto;

import java.io.Serializable;

@SuppressWarnings("serial")
public class ResultGeneDto implements Serializable {

	/** 適応度（最大個体） */
	private double fit1;

	/** 評価値（最大個体） */
	private double trueVal1;

	/** ac1[]:目的関数の係数（最大個体） */
	private double[] ac1;

	public ResultGeneDto() {
	}

	public void setFit1(double fit1){
        this.fit1 = fit1;
    }

	public void setTrueVal1(double trueVal1){
        this.trueVal1 = trueVal1;
    }
	
   public void setAc1(double[] ac1){
        this.ac1 = ac1;
    }

	public double getFit1() {
		return this.fit1;
	}

	public double getTrueVal1() {
		return this.trueVal1;
	}

	/** ac1[]:目的関数の係数（最大個体）　*/
	public double[] getAc1() {
		return this.ac1;
	}

}
