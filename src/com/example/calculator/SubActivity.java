package com.example.calculator;

import android.app.Activity;  
import android.content.Intent;  
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import com.example.calculator.MainActivity;

public class SubActivity extends Activity{  

	@Override  
    protected void onCreate(Bundle savedInstanceState) {  
        super.onCreate(savedInstanceState);  
        setContentView(R.layout.activity_sub);
    
        final Button bt2 = (Button) findViewById(R.id.ButtonSub);

        bt2.setOnClickListener(new OnClickListener(){
        	@Override
        	public void onClick(View v){
        		intentMethod();
        		}
        	private void intentMethod(){
	        	Intent i = new Intent();
				i.setClassName("com.example.calculator","com.example.calculator.MainActivity");
				i.putExtra("com.example.calculator.testString", "戻りました");
				startActivity(i);
        	}
        });
    }
}
