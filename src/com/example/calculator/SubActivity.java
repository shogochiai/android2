package com.example.calculator;

import android.app.Activity;  
import android.content.Intent;  
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

public class SubActivity extends Activity{  

	@Override  
    protected void onCreate(Bundle savedInstanceState) {  
        super.onCreate(savedInstanceState);  
        setContentView(R.layout.activity_sub);
        ReturnAndToast();
    
        final Button bt2 = (Button) findViewById(R.id.ButtonSub);

        bt2.setOnClickListener(new OnClickListener(){
        	@Override
        	public void onClick(View v){
        		intentMethod();
        		}
        	private void intentMethod(){
	        	Intent i = new Intent();
				i.setClassName("com.example.calculator","com.example.calculator.MainActivity");
				i.putExtra("Return", "Returned");
				startActivity(i);
        	}
        });
    }

	//SubActivityからのputExtraを受け取る
	private void ReturnAndToast(){
		 Intent intent = getIntent();
			 if(intent != null){
				 String str = intent.getStringExtra("ansString");
				 Toast.makeText(this, str, Toast.LENGTH_LONG).show();
			 }

	 }
	
}
