package com.svm.unit;


import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import net.java.sen.SenFactory;
import net.java.sen.StringTagger;
import net.java.sen.dictionary.Token;

import com.svm.util.MyFileUtil;
import com.svm.util.SelectWordUtil;

public class GetNGramUnit {
    
    
    public static void main(String[] args) throws Exception {

        GetNGramUnit getNGramUnit = new GetNGramUnit();
        
        // 質問解析用のNGramを作成する
        String modeQueOrAns = "que";
        getNGramUnit.getNGram(modeQueOrAns);
        
        // 応答解析用のNGramを作成する
        getNGramUnit = new GetNGramUnit();
        modeQueOrAns = "ans";
        getNGramUnit.getNGram(modeQueOrAns);
    }

	public void getNGram(String modeQueOrAns) throws Exception {

		// この3行で解析できる
		StringTagger tagger = SenFactory.getStringTagger(null);
		List<Token> tokens = new ArrayList<Token>();
        
        // Projectのトップディレクトリパス取得
        String folderName = System.getProperty("user.dir");
        // トップディレクトリパス以降を設定
        String inputFolderName = folderName + "\\src\\main\\resources\\inputFile\\";
        String outputFolderName = folderName + "\\src\\main\\resources\\outputFile\\";
        
        // 入力ファイル及び出力ファイル名を設定
		String studyInputFile = inputFolderName + modeQueOrAns + "_studyInput.txt"; // ans_studyInput.txt
		String studyOutputFile = outputFolderName + modeQueOrAns + "_NGramOutput.csv"; // ans_NGramOutput.csv
		
		LinkedHashMap<String,String[]> studyMap = MyFileUtil.readCsvCom(studyInputFile);

		//タイトル作成
		StringBuilder outPutTitle = new StringBuilder();
		outPutTitle.append("データNo,正解ラベル,分類,");

		// 1-gram
		StringBuilder oneGramTitle = new StringBuilder();
		for (String key : studyMap.keySet()) {
			String studyLine = studyMap.get(key)[3];
			tagger.analyze(studyLine, tokens);
			for (Token token : tokens) {
				oneGramTitle.append(token.getSurface() + ",");
			}
		}

		// 2-gram（単語）
		StringBuilder twoGramTitle = new StringBuilder();
		for (String key : studyMap.keySet()) {
			String studyLine = studyMap.get(key)[3];
			tagger.analyze(studyLine, tokens);
			for (int i = 0; i < tokens.size() -1; i++) {
				twoGramTitle.append(tokens.get(i).getSurface()); // 1単語目の出力
				twoGramTitle.append(tokens.get(i + 1).getSurface() + ","); // 連結 2単語目の出力
			}
		}

		// 2-gram（単語/品詞）
		StringBuilder tangoHinshi = new StringBuilder();
		for (String key : studyMap.keySet()) {
			String studyLine = studyMap.get(key)[3];
			tagger.analyze(studyLine, tokens);
			for (int i = 0; i < tokens.size() -1; i++) {
				tangoHinshi.append(tokens.get(i).getSurface());  // 1単語目の出力
				tangoHinshi.append("/");
				tangoHinshi.append(SelectWordUtil.selectWord(tokens.get(i+1).getMorpheme().getPartOfSpeech(), "", "-") + ",");  // 連結 2単語目の出力
			}
		}
		// 全部をまとめる
		StringBuilder allGram = new StringBuilder();
		allGram.append(outPutTitle);
		allGram.append(oneGramTitle);
		allGram.append(twoGramTitle);
		allGram.append(tangoHinshi);

		BufferedWriter newFileStream = new BufferedWriter(new FileWriter(studyOutputFile));

		//タイトル作成
		newFileStream.write(new String(allGram));
		newFileStream.newLine();
		newFileStream.flush();

		//該当文字前後の単語用マップ
		LinkedHashMap<String,String[]> zengoGaitoMojiMap = new LinkedHashMap<String, String[]>();
		int countNum = 1;
		String[] tmpZengoGaitoMoji = new String[2];
		GetNGramUnit testNGramOut = new GetNGramUnit();

		// 素性ベクトル作成
		String[] tmpOneGramTitle = new String(oneGramTitle).split(",");
		String[] tmpTwoGramTitle = new String(twoGramTitle).split(",");
		String[] tmpTangoHinshiGramTitle = new String(tangoHinshi).split(",");
		StringBuilder tmpRenketsu;
		for (String key : studyMap.keySet()) {
			String studyLine = studyMap.get(key)[3];
			tagger.analyze(studyLine, tokens);
			boolean isVectorFlag;

			//1gram チェック
			StringBuilder oneGramSujoVector = new StringBuilder();
			for (String oneGram : tmpOneGramTitle) {
				isVectorFlag = false;
				for (Token token : tokens) {
					if (oneGram.equals(token.getSurface())) {
						oneGramSujoVector.append("1,");
						isVectorFlag = true;
						break;
					}
				}
				if (!isVectorFlag) {
					oneGramSujoVector.append("0,");
				}
			}

			//2gram チェック
			StringBuilder twoGramSujoVector = new StringBuilder();
			for (String twoGram : tmpTwoGramTitle) {
				isVectorFlag = false;
				for (int i = 0; i < tokens.size() -1; i++) {
					tmpRenketsu = new StringBuilder();
					tmpRenketsu.append(tokens.get(i).getSurface()); // 1単語目の出力
					tmpRenketsu.append(tokens.get(i + 1).getSurface()); // 連結 2単語目の出力
					if (twoGram.equals(new String(tmpRenketsu))) {
						twoGramSujoVector.append("1,");
						isVectorFlag = true;
						break;
					}
				}
				if (!isVectorFlag) {
					twoGramSujoVector.append("0,");
				}
			}

			//2-gram（単語/品詞）
			StringBuilder tangoHinshiSujoVector = new StringBuilder();
			for (String TangoHinshiGram : tmpTangoHinshiGramTitle) {
				isVectorFlag = false;
				for (int i = 0; i < tokens.size() -1; i++) {
					tmpRenketsu = new StringBuilder();
					tmpRenketsu.append(tokens.get(i).getSurface()); // 1単語目の出力
					tmpRenketsu.append("/");
					tmpRenketsu.append(SelectWordUtil.selectWord(tokens.get(i+1).getMorpheme().getPartOfSpeech(), "", "-"));  // 連結 2単語目の出力
					if (TangoHinshiGram.equals(new String(tmpRenketsu))) {
						tangoHinshiSujoVector.append("1,");
						isVectorFlag = true;
						break;
					}
				}
				if (!isVectorFlag) {
					tangoHinshiSujoVector.append("0,");
				}
			}

			//素性ベクトルの書き込み
			newFileStream.write(studyMap.get(key)[0] + "," +studyMap.get(key)[1] + "," +studyMap.get(key)[2] + ","
				+ new String(oneGramSujoVector) + new String(twoGramSujoVector)
				+ new String(tangoHinshiSujoVector));
			newFileStream.newLine();
			newFileStream.flush();


			if (studyLine.indexOf("名詞") != -1 && !testNGramOut.jyuFukuCheck(studyLine, zengoGaitoMojiMap)) {
				//該当文字前後の単語をマップへ
				tmpZengoGaitoMoji = testNGramOut.getZengoGaitoMoji(studyLine);
				zengoGaitoMojiMap.put(String.valueOf(countNum), tmpZengoGaitoMoji);
				countNum++;
			}
		}
		newFileStream.close();
		
		System.out.println("--------------NGram出力完了 ファイル名: "  + studyOutputFile + "--------------");
	}

	/**
	 * 文字列を単語単位に分解し、該当文字の前後の単語を返す
	 * @param str
	 * @return　前後の単語
	 */
	private String[] getZengoGaitoMoji(String str) throws IOException {

		StringTagger tagger = SenFactory.getStringTagger(null);
		List<Token> tokens = new ArrayList<Token>();

		tagger.analyze(str, tokens);
		String[] zengoGaitoMoji = new String[2];
		String tmpZenbuGaitoMoji = "";
		String tmpKoubuGaitoMoji = "";
		for (int i = 0; i < tokens.size(); i++) {
			//品詞で「動詞」が見つかった場合
			if ("名詞".equals(tokens.get(i).getSurface())) {
				if (i >= 1 && tokens.size() >= 3) {
					//前部の文字を抽出
					for (int j = i-1; j >= 0; j--) {
						if (tokens.get(j).getMorpheme().getPartOfSpeech().indexOf("名詞") != -1) {
							for (int k = j+1; k <= i-1; k++) {
								tmpZenbuGaitoMoji = tmpZenbuGaitoMoji + tokens.get(k).getSurface();
							}
							zengoGaitoMoji[0] = tmpZenbuGaitoMoji;  // 前位置の単語
							break;
						}
					}
					//後部の文字を抽出
					for (int m = i+1; m < tokens.size(); m++) {
						if (tokens.get(m).getMorpheme().getPartOfSpeech().indexOf("名詞") != -1
								|| tokens.get(m).getMorpheme().getPartOfSpeech().indexOf("記号") != -1) {
							break;
						}
						tmpKoubuGaitoMoji = tmpKoubuGaitoMoji + tokens.get(m).getSurface();
					}

					zengoGaitoMoji[1] = tmpKoubuGaitoMoji;  // 後位置の単語
					break;
				}
			}
		}

		return zengoGaitoMoji;
	}

	/**
	 * 重複値チェック
	 * @param studyLine
	 * @param zengoGaitoMojiMap
	 * @return
	 * @throws IOException
	 */
	private boolean jyuFukuCheck(String studyLine, LinkedHashMap<String,String[]> zengoGaitoMojiMap) throws IOException{

		String[] tmpZengoGaitoMoji = new String[2];

		if (zengoGaitoMojiMap == null) {
			return false;
		}

		boolean jyufukuFlag = false;
		tmpZengoGaitoMoji = getZengoGaitoMoji(studyLine);
		for (String keyZengoGaitoMoji : zengoGaitoMojiMap.keySet()) {
			if(zengoGaitoMojiMap.get(keyZengoGaitoMoji)[0].equals(tmpZengoGaitoMoji[0])
				&& zengoGaitoMojiMap.get(keyZengoGaitoMoji)[1].equals(tmpZengoGaitoMoji[1])) {
				jyufukuFlag = true;
				break;
			}
		}

		if (jyufukuFlag) {
			return true;
		} else {
			return false;
		}
	}
}
