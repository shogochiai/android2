package com.example.calculator;

import android.app.Activity;  
import android.content.Intent;  
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

	public class SubActivity extends Activity implements OnClickListener{  

		@Override  
	    protected void onCreate(Bundle savedInstanceState) {  
	        super.onCreate(savedInstanceState);  
	        setContentView(R.layout.activity_sub);  
	    
	        final Button bt2 = (Button) findViewById(R.id.ButtonSub);
	        bt2.setOnClickListener(new OnClickListener(){
	        	public void onClick(View v){
	    			Intent i = new Intent();
    				i.setClassName("com.example.calculator","com.example.calculator.MainActivity");
    				i.putExtra("com.example.calculator.testString", "!TEST STRING!");
    				startActivity(i);
    				}
	        });
	        
	        
	        /**インテントより渡された値を取得します。  
	        Intent i = getIntent();  
	        if (i != null){
	            String str = i.getStringExtra("com.example.calculator.intent.testString");
	            System.out.println(str);
	        }  */
	    }

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			
		}  

	}
