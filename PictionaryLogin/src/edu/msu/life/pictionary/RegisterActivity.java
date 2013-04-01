package edu.msu.life.pictionary;

import android.os.Bundle;
import android.app.Activity;
import android.view.View;
import android.widget.TextView;
 
public class RegisterActivity extends Activity {
    
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        setContentView(R.layout.activity_register);
 
        TextView loginScreen = (TextView) findViewById(R.id.link_to_login);
 
        loginScreen.setOnClickListener(new View.OnClickListener() {
 
            public void onClick(View arg0) {
                // Switching to Login Screen/closing register screen
                finish();
            }
        });
    }
}
