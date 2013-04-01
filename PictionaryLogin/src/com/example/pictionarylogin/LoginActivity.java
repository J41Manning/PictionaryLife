package com.example.pictionarylogin;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.widget.TextView;
 
public class LoginActivity extends Activity {
   
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        setContentView(R.layout.activity_login);
 
        TextView registerView = (TextView) findViewById(R.id.link_to_register);
 
        registerView.setOnClickListener(new View.OnClickListener() {
 
            public void onClick(View v) {
                // Switch to Register screen
                Intent i = new Intent(getApplicationContext(), RegisterActivity.class);
                startActivity(i);
            }
        });
    }
}