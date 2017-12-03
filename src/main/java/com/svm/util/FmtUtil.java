package com.svm.util;

import java.text.DecimalFormat;

/**
 * @author Administrator
 * Formatに関するUtilクラス
 */
public class FmtUtil {

    /**
     * インスタンス化禁止のため、コンストラクタをprivateにしている。
     */
    private FmtUtil() {};
    
    /**
     * double型をString型（少数点以下2桁）に変換します。
     * @param fx
     * @return
     */
    public static String dblToStr(double fx) {
        //フォーマット
        DecimalFormat df1 = new DecimalFormat("0.00");
        
        return df1.format(fx);
    }
    
    
}
