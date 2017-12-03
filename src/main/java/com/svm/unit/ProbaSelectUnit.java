package com.svm.unit;

import com.svm.dto.CalFitDto;
import com.svm.dto.CalProbaDto;
import com.svm.dto.InitDto;

/**
 * 選択確立を計算するクラス
 * @author Administrator
 *
 */
public class ProbaSelectUnit {

	//ランダムーサーチをして，ある程度制約を満たした解を
	//初期個体とする．
	public CalProbaDto probaSelecct(CalFitDto calFitDto){

        /** 選択確率 */
        double ps;
        /** 選択確率の総和 */
        double sumPs;
        /**選択確率の保存用 */
        double[] rpsn = new double[InitDto.NN + 1];

        /** 各個体の適応度 初期化 */
        double[] fit1 = new double[InitDto.NN];
        fit1 = calFitDto.getFit1();
        
        /** 適応度の総和 */
        double sumFit = calFitDto.getSumFit();
        
        sumPs=0;
        rpsn[0]=0;
        for(int i=0; i< InitDto.NN; i++){
        	ps=(fit1[i] /sumFit)*100d;    /*選択確率の計算*/
        	sumPs=sumPs+ps;      /*総和を計算*/
        	rpsn[i+1]=sumPs;    /*記憶*/
        }
        
        CalProbaDto calProbaDto = new CalProbaDto();
        calProbaDto.setRpsn(rpsn);
        
        return calProbaDto;
	}
}
