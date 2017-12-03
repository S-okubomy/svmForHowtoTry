package com.svm.interfaceEva;


/**
 * 評価式のクラスに実装するインターフェースです。
 * 評価式のクラスに実装しないとエラーとなります。
 * @author Administrator
 * @param double[] gaParamet 評価式の引数（使用インデックスは任意）
 *
 */
public interface BaseEvaVal {
    
    double execute(double[] gaParamet, String modeName);
    
}
