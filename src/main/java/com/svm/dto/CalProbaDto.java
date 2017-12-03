package com.svm.dto;

/**
 * 選択確率のJavaBeans
 * @author Administrator
 *
 */
public class CalProbaDto extends InitDto {

	/** 選択確率 */
	private double ps;

	/** 選択確率の総和 */
	private double sumPs;

	/**選択確率の保存用 */
	private double[] rpsn;

	public void setPs(double ps){
        this.ps = ps;
    }

	public void setSumPs(double sumPs){
        this.sumPs = sumPs;
    }

	public void setRpsn(double[] rpsn){
        this.rpsn = rpsn;
    }


	public double getPs() {
		return this.ps;
	}

	public double getSumPs() {
		return this.sumPs;
	}

	public double[] getRpsn() {
		return this.rpsn;
	}


}
