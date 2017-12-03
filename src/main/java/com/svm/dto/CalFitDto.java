package com.svm.dto;

import java.io.Serializable;

public class CalFitDto implements Serializable {

	/** 各個体の適応度 */
	private double[] fit1;

	/** 適応度の総和 */
	private double sumFit;

	/** 各個体の所持金（円） */
	private int[] haveMoney;

	/** x1[][]:染色体 */
	private double[][] x1;

	/** ba1[][]:染色体のバックアップ */
	private double[][] ba1;

	/** ac[][]:目的関数の係数 */
	private double[][] ac1;

	/** 各個体の実値 **/
	private double[] trueVal;


	public CalFitDto() {
	}

	public void setX1(double[][] x1){
        this.x1 = x1;
    }

	public void setBa1(double[][] ba1){
        this.ba1 = ba1;
    }

	public void setFit1(double[] fit1){
        this.fit1 = fit1;
    }

	public void setTrueVal(double[] trueVal){
        this.trueVal = trueVal;
    }


	public void setSumFit(double sumFit){
        this.sumFit = sumFit;
    }

	public void setHaveMoney(int[] haveMoney){
        this.haveMoney = haveMoney;
    }

	public double[] getFit1() {
		return this.fit1;
	}

	public double[] getTrueVal() {
		return this.trueVal;
	}

	public double getSumFit() {
		return this.sumFit;
	}

	public int[] getHaveMoney() {
		return this.haveMoney;
	}

	/** 染色体を返すゲッター　*/
	public double[][] getX1() {
		return this.x1;
	}

	/** 染色体のバックアップを返すゲッター　*/
	public double[][] getBa1() {
		return this.ba1;
	}











}
