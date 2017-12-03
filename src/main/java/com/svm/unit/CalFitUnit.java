package com.svm.unit;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import com.svm.interfaceEva.BaseEvaVal;
import com.svm.dto.CalFitDto;
import com.svm.dto.InitDto;

/**
 * 適応度を計算するクラス
 * @author Administrator
 *
 */
public class CalFitUnit {

	/**
	 * 適応度を計算する
	 * @param initDto
	 * @param baseDay
	 * @return
	 * @throws Exception 
	 */
	public CalFitDto calFit(InitDto initDto, String evaluationClsName
	        , String modeName) throws Exception {

		//************************** ↓↓↓ 共通（固定PG部分） ↓↓↓ **********************************//
		/** ac[][]:目的関数の係数 */
		double[][] gaParameter = initDto.getAc1();

    	/** 各個体の適応度 初期化 */
    	double[] fit1 = new double[InitDto.NN];
    	double[] trueVal = new double[InitDto.NN];
    	for(int i = 0; i < InitDto.NN; i++) { //固体の数
    		fit1[i] = 0;
    		trueVal[i] = 0;
    	}

    	for(int i = 0; i < InitDto.NN; i++) { //固体の数
    		trueVal[i] = getEvaValue(evaluationClsName, gaParameter[i], modeName);
        	fit1[i] = 10.0 * trueVal[i];
    	}

    	// 適応度の総和 
    	double sumFit =0;
		for(int i = 0; i < InitDto.NN; i++) {
    		sumFit = sumFit + fit1[i];
    	}
		CalFitDto calFitDto = new CalFitDto();
		calFitDto.setTrueVal(trueVal);
		calFitDto.setFit1(fit1);
		calFitDto.setSumFit(sumFit);

		return calFitDto;
	}

	private double getEvaValue(String evaluationClsName, double[] gaParameter
	        , String modeName) throws Exception {
		
		double evaValue = 0;
		try {
    		// クラス名からクラスのインスタンスを取得する。
			Class<? extends BaseEvaVal> evaluationCls 
			    = Class.forName(evaluationClsName).asSubclass(BaseEvaVal.class);
			Method method = evaluationCls.getMethod("execute", double[].class, String.class);
			evaValue = (double)method.invoke(evaluationCls.newInstance(), gaParameter, modeName);
		} catch (ClassCastException e) {
            throw new Exception("評価クラスにはBaseEvaValを実装したクラスを指定する必要があります。", e);
        } catch (ClassNotFoundException e) {
            throw new Exception("評価クラスが見つかりません。");
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
            e.printStackTrace();
        }
		
		return evaValue;
	}
}
