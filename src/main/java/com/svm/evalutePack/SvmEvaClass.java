package com.svm.evalutePack;

import static com.svm.util.FmtUtil.dblToStr;

import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.util.LinkedHashMap;

import com.svm.interfaceEva.BaseEvaVal;
import com.svm.util.MyFileUtil;


/**
 * @author Administrator
 *
 */
public class SvmEvaClass implements BaseEvaVal{

    public static final String SEIKAI = "T";
    public static final String FUSEIKAI = "F";
    private static final int maxRoopCnt = 200;
    
    /* (非 Javadoc)
     * @see interfaceEva.BaseEvaVal#execute(double)
     */
    public double execute(double[] gaParameter, String modeName){
        
        // Projectのトップディレクトリパス取得
        String folderName = System.getProperty("user.dir");

        // トップディレクトリパス以降を設定（※ここでのinputFileはoutputFileフォルダーにある）
        String inputFolderName = folderName + "\\src\\main\\resources\\outputFile\\";
        String resultFolderName = folderName + "\\src\\main\\resources\\resultSvmFile\\";
        
        // 入力ファイル及び出力ファイル名を設定
        //学習データの読み込みファイル名を設定
        String csvFileInput = inputFolderName + modeName + "_NGramOutput.csv"; // ans_NGramOutput.csv
        // 学習結果の出力ファイル名を設定
        String weightValName = resultFolderName + modeName + "_outWeightValue.csv"; // ans_outWeightValue.csv
        
        LinkedHashMap<String,String[]> studyMap = null;
        try {
            studyMap = MyFileUtil.readCsvFile(csvFileInput);
        } catch (Exception e) {
            e.printStackTrace();
        }

        //初期化
        int vectorSu = studyMap.get("データNo").length - 3; //ベクトル数
        int[] vectorX1 = new int[vectorSu];
        int[] weightParam = new int[vectorSu];
        for(int i = 0; i < vectorSu; i++) { //ベクトルの数
            weightParam[i] = 1;
        }
        
        LinkedHashMap<String, int[]> weightParamMap = new LinkedHashMap<String, int[]>();
        for (String key : studyMap.keySet()) {
            if (!"データNo".equals(studyMap.get(key)[0])) {
                if (!weightParamMap.containsKey(studyMap.get(key)[2])) {
                    weightParamMap.put(studyMap.get(key)[2], weightParam.clone());
                }
            }
        }
        
        double evaValue = 0;
        double fxValue = 0;
        int countAll = 0;
        // 重み係数のマップを分類ごとにループし、修正する。
        for (String keyWeightParam : weightParamMap.keySet()) {
            // 学習データを一行ずつ判断する。
            boolean isSeikai = false;
            boolean isFuseikai = false;
            for (int i = 0; i < maxRoopCnt; i++) { // 重み係数を1回だけではなく繰り返し修正することで正解率上げる
                for (String key : studyMap.keySet()) {
                    if (!"データNo".equals(studyMap.get(key)[0])) {
                        countAll++;
                        //マップの配列を入れ替え
                        for(int ii = 0; ii < vectorSu; ii++) {
                            vectorX1[ii] = Integer.valueOf(studyMap.get(key)[ii + 3]);
                        }
                        // 決定関数を計算（判断基準）
                        fxValue = (double)getNaiseki(weightParamMap.get(keyWeightParam), vectorX1)
                                + gaParameter[0];
                        if (fxValue >= 0) { // 正しいと判断した場合
                            
                            // 不正解ラベル & 分類に一致→重み係数を修正
                            if (FUSEIKAI.equals(studyMap.get(key)[1]) 
                                    && studyMap.get(key)[2].equals(keyWeightParam)) {
                                for(int ii = 0; ii < vectorSu; ii++) {
                                    weightParam[ii] = weightParamMap.get(keyWeightParam)[ii] 
                                            + ((-1) * Integer.valueOf(studyMap.get(key)[ii + 3]));
                                }
                                weightParamMap.put(keyWeightParam, weightParam.clone());
                                // 画面に学習結果（判断）を表示
                                System.out.println("判断NG  No" + studyMap.get(key)[0] + " fx " + fxValue + " 分類  " + keyWeightParam);
         
                                // 正解ラベル & 分類に一致→重み係数そのまま
                            } else if (SEIKAI.equals(studyMap.get(key)[1])
                                    && studyMap.get(key)[2].equals(keyWeightParam)) {
                                evaValue = evaValue + 1;
                                System.out.println("判断OK  No" + studyMap.get(key)[0] + " fx " + fxValue + " 分類 " + keyWeightParam);
                            
                                isSeikai = true;
                                
                            // 正解ラベル & 分類に不一致→重み係数を修正
                            } else if (SEIKAI.equals(studyMap.get(key)[1])
                                    && !studyMap.get(key)[2].equals(keyWeightParam)) {
                                for(int ii = 0; ii < vectorSu; ii++) {
                                    weightParam[ii] = weightParamMap.get(keyWeightParam)[ii] 
                                            + ((-1) * Integer.valueOf(studyMap.get(key)[ii + 3]));
                                }
                                weightParamMap.put(keyWeightParam, weightParam.clone());
                                // 画面に学習結果（判断）を表示
                                System.out.println("判断NG  No" + studyMap.get(key)[0] + " fx " + fxValue + " 分類  " + keyWeightParam);
                            }
                        } else { // 誤りと判断した場合
                            // 不正解ラベル & 分類に一致→重み係数そのまま
                            if (FUSEIKAI.equals(studyMap.get(key)[1]) 
                                    && studyMap.get(key)[2].equals(keyWeightParam)) {
                                evaValue = evaValue + 1;
                                System.out.println("判断OK  No" + studyMap.get(key)[0] + " fx " + fxValue + " 分類 " + keyWeightParam);
                                
                                isFuseikai = true;
                                
                            // 正解ラベル & 分類に一致→重み係数を修正
                            } else if (SEIKAI.equals(studyMap.get(key)[1])
                                    && studyMap.get(key)[2].equals(keyWeightParam)) {
                                for(int ii = 0; ii < vectorSu; ii++) {
                                    weightParam[ii] = weightParamMap.get(keyWeightParam)[ii] 
                                            + ((+1) * Integer.valueOf(studyMap.get(key)[ii + 3]));
                                }
                                weightParamMap.put(keyWeightParam, weightParam.clone());
                                // 画面に学習結果（判断）を表示
                                System.out.println("判断NG  No" + studyMap.get(key)[0] + " fx " + fxValue + " 分類 " + keyWeightParam);
                                
                            // 正解ラベル & 分類に不一致→重み係数そのまま
                            } else if (SEIKAI.equals(studyMap.get(key)[1])
                                    && !studyMap.get(key)[2].equals(keyWeightParam)) {
                                evaValue = evaValue + 1;
                                System.out.println("判断OK  No" + studyMap.get(key)[0] + " fx " + fxValue + " 分類 " + keyWeightParam);
                            }
                        }
                    }
                }
                
//                if (isSeikai && isFuseikai) {
//                    System.out.println("Break 正解&不正解" + " 分類 " + keyWeightParam);
//                    break;
//                }
            }
        }
        
        System.out.println("個体の実値 " + evaValue + " 正解率" + dblToStr((evaValue/countAll) * 100) + "%");
        // 重み係数をCSVに出力する。
        outPutWeightValue(weightValName, weightParamMap, studyMap);

        return evaValue;
    }

    /**
     * 内積を計算する
     * @param vectorX1
     * @param vectorX2
     * @return 内積
     */
    private int getNaiseki(int[] vectorX1, int[] vectorX2) {
        //内積
        int naisekiValue = 0;
        for(int ii = 0; ii < vectorX1.length; ii++) {
            naisekiValue = naisekiValue + vectorX1[ii] * vectorX2[ii];
        }

        return naisekiValue;
    }
    
    private void outPutWeightValue(String weightValName, LinkedHashMap<String, int[]> weightParamMap
            , LinkedHashMap<String,String[]> studyMap) {
        
        //CSVへ書き込み
        StringBuilder weightParamOut = new StringBuilder();
        
        // タイトルラベルを書き込み
        for (String key : studyMap.keySet()) {
            if ("データNo".equals(studyMap.get(key)[0])) {
                weightParamOut.append("質問分類" + ",");
                for(int i = 0; i < studyMap.get("データNo").length - 3; i++) {
                    weightParamOut.append(studyMap.get(key)[i + 3] + ",");
                }
                weightParamOut.append("\r\n");
                break;
            }
        }
        // 重み係数の書き込み
        for (String weightParamKey : weightParamMap.keySet()) {
            weightParamOut.append(weightParamKey + ",");
            for (int i =0; i < weightParamMap.get(weightParamKey).length; i++) {
                weightParamOut.append(Integer.toString(weightParamMap.get(weightParamKey)[i]) + ",");
            }
            weightParamOut.append("\r\n");
        }
        try {
            FileOutputStream fosWeight = new FileOutputStream(weightValName);
            OutputStreamWriter oswWeight = new OutputStreamWriter(fosWeight , "UTF-8");
            BufferedWriter bwWeight = new BufferedWriter(oswWeight);
            bwWeight.write(new String(weightParamOut));
            //ファイルクローズ
            bwWeight.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    
}
