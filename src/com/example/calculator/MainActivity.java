package com.example.calculator;

import java.util.ArrayList;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import android.view.View.OnClickListener;

public class MainActivity extends Activity implements View.OnClickListener {

	/**
	 * Buttonの配列
	 */
	Button mButton[];

	/**
	 * Idの配列
	 */
	int mId[] = { R.id.button0, R.id.button1, R.id.button2, R.id.button3,
			R.id.button4, R.id.button5, R.id.button6, R.id.button7,
			R.id.button8, R.id.button9, R.id.buttonPlus, R.id.buttonMinus,
			R.id.buttonEqual, R.id.buttonTen, R.id.buttonClear, R.id.buttonMulti, R.id.buttonDivid };

	/**
	 * キー
	 */
	private final int KEY_PLUS = 10;
	private final int KEY_MINUS = 11;
	private final int KEY_EQUAL = 12;
	private final int KEY_TEN = 13;
	private final int KEY_CLEAR = 14;
	private final int KEY_MULTI = 15;
	private final int KEY_DIVID = 16;
	

	/**
	 * TextView
	 */
	private TextView mTextView;
	private TextView mTextViewU;

	/**
	 * 前の処理
	 */
	private int beforeStatus = 0;

	/**
	 * 計算中の値
	 */
	private ArrayList<String> calcArray;
	/**
	 * 計算する時の配列
	 */
	private ArrayList<String> signArray;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		ReturnAndToast();

		// 表示用TextView
		mTextView = (TextView) findViewById(R.id.display);
		mTextViewU = (TextView) findViewById(R.id.display_u);


		// Button
		mButton = new Button[mId.length];

		// Buttonの取り込みとイベントのはりつけ
		for (int i = 0; i < mId.length; i++) {
			// buttonを取り込む
			mButton[i] = (Button) findViewById(mId[i]);
			// buttonのイベント処理
			mButton[i].setOnClickListener(this);
		}
		calcArray = new ArrayList<String>();
		signArray = new ArrayList<String>(); //Genericsで型変換の手間を未然に防いでみるテスト
	}

	@Override
	public void onClick(View view) {

		// 押されたボタンがどのボタンかを判定
		for (int i = 0; i < mId.length; i++) {
			if (view.equals(mButton[i])) {
				String nowValue = mTextView.getText().toString();

			// CLEAR
			if (i == KEY_CLEAR) {
				mTextView.setText("");
				calcArray.clear();
				signArray.clear();
				beforeStatus = KEY_CLEAR;
			}
			// =
			else if (i == KEY_EQUAL && nowValue.length() > 0) {
				nowValue = checkDisplay(nowValue);
				calcArray.add(nowValue);
				double ans = calc();

			/**
			 * =を押したらtextviewにansを出力したい
			 */
			String ansString = Double.toString(ans);//if中のと混同しないように
			
			//ansStringを文字列処理して小数点切り落とし
		    int index = ansString.indexOf(".");
			    if(ansString.substring(index+1).equals("0")){
			    	String ansStirng = ansString.substring(0,index);}
			
			//答えをテキストビューに表示
			mTextView.setText(ansString);
			
			//過去の答えを配列に格納して履歴に表示
			mTextViewU.setText(ansString);
			beforeStatus = KEY_EQUAL;

			final String Ans = ansString;
		
			Intent intent2 = new Intent();
			intent2.setClassName("com.example.calculator","com.example.calculator.SubActivity");
				
			intent2.putExtra("Ans", Ans);
			startActivity(intent2);		   
		
}		
				
				/**
				 * 四則演算の表示と例外用のbeforeStatus
				 */
				// +
				else if (i == KEY_PLUS && nowValue.length() > 0) {
					calcArray.add(nowValue);
					signArray.add("+");
					beforeStatus = KEY_PLUS;
				}
				// -
				else if (i == KEY_MINUS && nowValue.length() > 0) {
					calcArray.add(nowValue);
					signArray.add("-");
					beforeStatus = KEY_MINUS;
				}
				// *
				else if (i == KEY_MULTI && nowValue.length() > 0) {
					calcArray.add(nowValue);
					signArray.add("*");
					beforeStatus = KEY_MULTI;
				}
				// /
				else if (i == KEY_DIVID && nowValue.length() > 0) {
					calcArray.add(nowValue);
					signArray.add("/");
					beforeStatus = KEY_DIVID;
				}

				// .
				else if (i == KEY_TEN) {
					// .キーを押した時、演算キーが押されていた場合
					nowValue = checkDisplay(nowValue);
					// いきなり.キーが押された場合
					if (nowValue.length() == 0) {
						nowValue = "0.";
					} else {
						nowValue = nowValue + ".";
					}
					mTextView.setText(nowValue);
					beforeStatus = i;
				}
				/** 
				 * 数字の連続表示について
				 */
				else if (i < 10) {
					nowValue = checkDisplay(nowValue);
						// 0しか入力されていない場合は0が２個以上並ばないようにする
						if (nowValue.equals("0") && i == 0) {
							return;
						} else if (nowValue.equals("0") && i != 0) {
							nowValue = "";
						}

					nowValue = nowValue + i;
					mTextView.setText(nowValue);
					beforeStatus = i;
				}
				break;
			}
		}
	}//押されたキーの判定終了
	
	 //SubActivityからのputExtraを受け取る
	 private void ReturnAndToast(){
		 Intent i = getIntent();
			 if(i != null){
				 String str = i.getStringExtra("Return");
				 Toast.makeText(this, str, Toast.LENGTH_LONG).show();
			 }
	}

	
	
	// 演算が行われていた場合を確認する
	// beforeStatusに準じてディスプレイの値を初期化する
	private String checkDisplay(String now) {
		if (beforeStatus == KEY_PLUS || 
			beforeStatus == KEY_MINUS|| 
			beforeStatus == KEY_MULTI|| 
			beforeStatus == KEY_DIVID||
			beforeStatus == KEY_EQUAL //=のときだけ例外にして値の初期化をしたい
				) {
			mTextView.setText("");
			//このへんが空白を入れてるだけで値を初期化できてない
			return "0"; //何これ10にしても変わらないじゃん
		}
		return now; //引数がString nowなので、ifがどうであれ6とか押したら上の処理をした後に6を返す
	}

	// 計算結果を出すメソッド(あとでansとしてインスタンス化)
	private double calc() {
		if (calcArray.size() == 0) {
			return 0.0;
		}
		if (calcArray.size() == 1) {
			return Double.parseDouble(calcArray.get(0));
		}
		double passive = Double.parseDouble(calcArray.get(0));
		double active;
		int j = 0;
		int size = calcArray.size();
		for (int i = 1; i < size; i++) {
			active = Double.parseDouble(calcArray.get(i));
			if (signArray.get(j).equals("+")) {
				passive = passive + active;
			} else if (signArray.get(j).equals("-")) {
				passive = passive - active;
			} else if (signArray.get(j).equals("*")) {
				passive = passive * active;
			} else {
				passive = passive / active;
			}
			j++;
		}
		return passive;
	}	
	
}