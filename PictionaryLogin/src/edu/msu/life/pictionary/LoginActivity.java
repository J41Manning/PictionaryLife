package edu.msu.life.pictionary;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.content.Intent;
import android.graphics.Typeface;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
 
public class LoginActivity extends FragmentActivity {
   
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        setContentView(R.layout.activity_login);
 
    	TextView logo = (TextView) findViewById(R.id.logo);
    	Typeface font = Typeface.createFromAsset(getAssets(), "comesinhandy.ttf");  
    	logo.setTypeface(font); 
        
        TextView registerView = (TextView) findViewById(R.id.link_to_register);
        Button loginBtn = (Button) findViewById(R.id.btnLogin);
        
        final EditText inputUsername = (EditText) findViewById(R.id.inputUsername);
        final EditText inputPassword = (EditText) findViewById(R.id.inputPassword);
 
        registerView.setOnClickListener(new View.OnClickListener() {
 
            public void onClick(View v) {
                // Switch to Register screen
                Intent i = new Intent(getApplicationContext(), RegisterActivity.class);
                startActivity(i);
            }
        });
        
        loginBtn.setOnClickListener(new View.OnClickListener() {
 
            public void onClick(View v) {
                // Login and display the login dialog
            	String username = inputUsername.getText().toString();
            	String password = inputPassword.getText().toString();
            	
            	LoginDlg loginDlg = new LoginDlg(username, password);
            	loginDlg.show(getSupportFragmentManager(), "load");
            }
        });
    
        
    }
	
	public void login() {
        Intent i = new Intent(getApplicationContext(), WaitingActivity.class);
        startActivity(i);
	}
	
}