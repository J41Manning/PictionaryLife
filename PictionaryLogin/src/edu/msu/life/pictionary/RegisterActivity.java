package edu.msu.life.pictionary;

import android.os.Bundle;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
 
public class RegisterActivity extends FragmentActivity {
    
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        setContentView(R.layout.activity_register);
 
    	TextView logo = (TextView) findViewById(R.id.logo);
    	Typeface font = Typeface.createFromAsset(getAssets(), "comesinhandy.ttf");  
    	logo.setTypeface(font); 
    	
        TextView loginScreen = (TextView) findViewById(R.id.link_to_login);
        Button registerBtn = (Button) findViewById(R.id.btnRegister);
        
        final EditText inputUsername = (EditText) findViewById(R.id.inputUsername);
        final EditText inputPassword = (EditText) findViewById(R.id.inputPassword);
        final EditText inputConfirm = (EditText) findViewById(R.id.inputConfirm);
        
        loginScreen.setOnClickListener(new View.OnClickListener() {
 
            public void onClick(View arg0) {
                // Switching to Login Screen/closing register screen
                finish();
            }
        });
        
        registerBtn.setOnClickListener(new View.OnClickListener() {
        	 
            public void onClick(View v) {
            	// Register and display the register dialog
            	String username = inputUsername.getText().toString();
            	String password = inputPassword.getText().toString();
            	String confirm = inputConfirm.getText().toString();
            	
            	if (password.equals(confirm)) {
                	RegisterDlg registerDlg = new RegisterDlg(username, password);
                	registerDlg.show(getSupportFragmentManager(), "register");	
            	} else {
            		Context context = getApplicationContext();
            		int msgId = R.string.msg_passwords_do_not_match;
            		int duration = Toast.LENGTH_SHORT;

            		Toast toast = Toast.makeText(context, msgId, duration);
            		toast.show();
            	}
            }
        });
    }
	
	void registered() {
        Intent i = new Intent(getApplicationContext(), LoginActivity.class);
        startActivity(i);
	}
}
