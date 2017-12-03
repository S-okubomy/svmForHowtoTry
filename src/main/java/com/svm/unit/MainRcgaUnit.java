package com.svm.unit;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.text.DecimalFormat;

import com.svm.dto.CalFitDto;
import com.svm.dto.CalProbaDto;
import com.svm.dto.InitDto;
import com.svm.dto.ResultGeneDto;

/**
 * RCGAのメインクラス
 * @author Administrator
 *
 */
public class MainRcgaUnit {

	private static final int mSedai = InitDto.calSedai; //繰り返し世代数


	public ResultGeneDto calGene(String evaluationClsName, String modeName) {

	    // 最適化の結果返却用
        ResultGeneDto resultGeneDto = new ResultGeneDto();
	    
		try{
			// Projectのトップディレクトリパス取得
			String folderName = System.getProperty("user.dir");
			// トップディレクトリパス以降を設定
			folderName = folderName + "\\src\\main\\resources\\outputFile\\";
			// String folderName ="/opt/";    // Linux用

			String fname1 ="getStudyManModelTestHistLog";
			String fnameUse ="getStudyManModelTestHist";

			//データ保存用 変数
			int writeL = 0;
			int writeFitMaxNum = 0;
			double writeFitMax = 0;

			//出力用ファイルのオープン
			FileOutputStream fos = new FileOutputStream( folderName + fname1 + ".csv");
			OutputStreamWriter osw = new OutputStreamWriter(fos , "UTF-8");
			BufferedWriter bw = new BufferedWriter(osw);

			//フォーマット
			DecimalFormat df1 = new DecimalFormat("0.00");
			DecimalFormat df2 = new DecimalFormat("0.000");

			/* 初期遺伝子をつくる */
			InitGaUnit initGa = new InitGaUnit();
			InitDto initDto = new InitDto();

			/* 適応度を計算する */
			CalFitDto calFitDto = new CalFitDto();
			CalFitUnit calFit = new CalFitUnit();

			/* 選択確率を計算する */
			CalProbaDto calProbaDto = new CalProbaDto();
			ProbaSelectUnit probaSelecct = new ProbaSelectUnit();

			/* ルーレット選択を行う */
			RouletteSelectUnit selectR = new RouletteSelectUnit();

			/* ブレンド交叉（BLX)をする */
			CrossBLXUnit crossBLX = new CrossBLXUnit();

			/* 突然変異（一様突然変異）をする */
			MutationUnit mutation = new MutationUnit();

			/*染色体の遺伝子の値を関数の係数にする*/
			ConvertUnit convert1 = new ConvertUnit();
			
			String strPr = "個体No: " + " 適応度 ";
			for (int i = 0; i < InitDto.nn; i++) {
			    strPr = strPr + " 係数" + Integer.valueOf(i).toString() + " ";
			}
			System.out.println(strPr);
			
			
			String strWrite = "世代," + "個体No," + "実値," + "適応度,";
	        for (int i = 0; i < InitDto.nn - 1; i++) {
	            strWrite = strWrite + " 係数" + Integer.valueOf(i).toString() + ",";
	        }
	        strWrite = strWrite + " 係数" + Integer.valueOf(InitDto.nn - 1).toString() + "\r\n";
			bw.write(strWrite);

			/* 初期遺伝子をつくる */
			initDto = initGa.initGa();

			/* 適応度を計算する */
			calFitDto = calFit.calFit(initDto, evaluationClsName, modeName);

			/* 全世代 最大の個体を取得*/
			double[] acGenMax1 = new double[InitDto.minA.length];
			double fitGenMax1 = 0;
			double truvalGenMax1 = 0;
			int LGenMax1 = 0;
			int fitMaxNumGen1 = 0;

			for(int L = 0; L < mSedai; L++){

				/* 選択確率を計算する */
				calProbaDto = probaSelecct.probaSelecct(calFitDto);

				/* ルーレット選択を行う */
				initDto = selectR.selectR(calProbaDto,calFitDto);

				/* ブレンド交叉（BLX)をする */
				initDto = crossBLX.crossBLX(initDto);

				/* 突然変異（一様突然変異）をする */
				initDto = mutation.mutation(initDto);

				/*染色体の遺伝子の値を関数の係数にする*/
				initDto = convert1.convert1(initDto);

				/* 適応度を計算する */
				calFitDto = calFit.calFit(initDto, evaluationClsName, modeName);

				double[] trueVal = new double[InitDto.NN];
				trueVal = calFitDto.getTrueVal();

				double[] fit1 = new double[InitDto.NN];
				fit1 = calFitDto.getFit1();

				/** x1[][]:染色体 */
				double[][] ac1 = new double[InitDto.NN][InitDto.nn];
				ac1 = initDto.getAc1();

				System.out.println("世代： " + L);
				double fitMax = fit1[0];
				int fitMaxNum = 0;
				for(int i =1; i < fit1.length ; i++) {
					if (fit1[i] > fitMax) {
						fitMax = fit1[i];
						fitMaxNum = i;
					}
				}
				
	            String strPrMax = "個体No: " + fitMaxNum + " ,実値: " + df2.format(trueVal[fitMaxNum])  + " ,適応度： " + df2.format(fit1[fitMaxNum]) + "  ";
	            for (int i = 0; i < InitDto.nn; i++) {
	                strPrMax = strPrMax + df1.format(ac1[fitMaxNum][i]) + "  ";
	            }
				System.out.println(strPrMax);
				
				//CSVに書き込み
	            String strWriteMax = L + "," + fitMaxNum + "," + df2.format(trueVal[fitMaxNum]) + "," + df2.format(fit1[fitMaxNum]) + ",";
	            for (int i = 0; i < InitDto.nn - 1; i++) {
	                strWriteMax = strWriteMax + df1.format(ac1[fitMaxNum][i]) + ",";
	            }
	            strWriteMax = strWriteMax + df1.format(ac1[fitMaxNum][InitDto.nn - 1]) + "\r\n";
				bw.write(strWriteMax);

				//全世代で適応度が最大のものを保存
				if (fit1[fitMaxNum] > fitGenMax1) {
					fitGenMax1 = fit1[fitMaxNum];
					fitMaxNumGen1 = fitMaxNum;
					LGenMax1 = L;
					truvalGenMax1 = trueVal[fitMaxNum];
					for(int i =0; i < InitDto.nn ; i++) {
						acGenMax1[i] = ac1[fitMaxNum][i];
					}
				}

			}
			//ファイルクローズ
			bw.close();

			writeL = LGenMax1;
			writeFitMaxNum = fitMaxNumGen1;
			writeFitMax = fitGenMax1;
            String strPrMaxAll = "最大世代: " + writeL +  " ,個体No: " + writeFitMaxNum + " ,実値: " + df2.format(truvalGenMax1)  + " ,適応度： " + df2.format(writeFitMax) + "  ";
            for (int i = 0; i < InitDto.nn; i++) {
                strPrMaxAll = strPrMaxAll + df1.format(acGenMax1[i]) + "  ";
            }
            System.out.println(strPrMaxAll);
			
			//最終結果をCSVへ書き込み
			FileOutputStream fosUse = new FileOutputStream( folderName + fnameUse + ".csv");
			OutputStreamWriter oswUse = new OutputStreamWriter(fosUse , "UTF-8");
			BufferedWriter bwUse = new BufferedWriter(oswUse);
			
			// タイトルの書き込み
            String strWriteMaxFinalTitle = "世代," + "個体No," + "適応度,";
            for (int i = 0; i < InitDto.nn - 1; i++) {
                strWriteMaxFinalTitle = strWriteMaxFinalTitle + " 係数" + Integer.valueOf(i).toString() + ",";
            }
            strWriteMaxFinalTitle = strWriteMaxFinalTitle + " 係数" + Integer.valueOf(InitDto.nn - 1).toString() + "\r\n";
			bwUse.write(strWriteMaxFinalTitle);
			
			// 値の書き込み
            String strWriteMaxFinal = writeL + "," + writeFitMaxNum + "," + df2.format(writeFitMax) + ",";
            for (int i = 0; i < InitDto.nn - 1; i++) {
                strWriteMaxFinal = strWriteMaxFinal + df1.format(acGenMax1[i]) + ",";
            }
            strWriteMaxFinal = strWriteMaxFinal + df1.format(acGenMax1[InitDto.nn - 1]) + "\r\n";
            bwUse.write(strWriteMaxFinal);

			//ファイルクローズ
			bwUse.close();
			
			// 最適化後の結果をセットする
			resultGeneDto.setFit1(fitMaxNumGen1);
    	    resultGeneDto.setTrueVal1(truvalGenMax1);
    	    resultGeneDto.setAc1(acGenMax1);
			
		} catch (Exception e) {
		    System.out.println(e.getMessage());
		}
		
		return resultGeneDto;
	}
}
