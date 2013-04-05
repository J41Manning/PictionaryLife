package edu.msu.life.pictionary;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
 
public class LoginActivity extends FragmentActivity {
   
	public static final String PREFS_NAME = "MyPrefsFile";
	private static final String PREF_USERNAME = "username";
	private static final String PREF_PASSWORD = "password";
	
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
 
        SharedPreferences pref = getSharedPreferences(PREFS_NAME,MODE_PRIVATE);   
        String username = pref.getString(PREF_USERNAME, null);
        String password = pref.getString(PREF_PASSWORD, null);

        if (!username.equals("") && !password.equals("")) {
        	CheckBox checkBox = (CheckBox) findViewById(R.id.checkboxRemember);
        	checkBox.setChecked(true);
            inputUsername.setText(username);
            inputPassword.setText(password);            
        }
        
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
            	
            	boolean inputValid = !username.equals("") && !password.equals("");
            	
            	if (inputValid) {
            		LoginDlg loginDlg = new LoginDlg(username, password);
            		loginDlg.show(getSupportFragmentManager(), "load");
            	} else {
            		Context context = getApplicationContext();
            		int msgId = R.string.msg_empty_username_or_password;
            		int duration = Toast.LENGTH_SHORT;
            		Toast toast = Toast.makeText(context, msgId, duration);
            		toast.show();
            	}
            }
        });
        
    }
	
	public void login(String username, String password, boolean wait) {
		
		CheckBox checkBox = (CheckBox) findViewById(R.id.checkboxRemember);
		boolean remember = checkBox.isChecked();
		
		if (remember) {
			getSharedPreferences(PREFS_NAME,MODE_PRIVATE)
	        .edit()
	        .putString(PREF_USERNAME, username)
	        .putString(PREF_PASSWORD, password)
	        .commit();
		} else {
			getSharedPreferences(PREFS_NAME,MODE_PRIVATE)
	        .edit()
	        .putString(PREF_USERNAME, "")
	        .putString(PREF_PASSWORD, "")
	        .commit();	
		}
		
		if (wait) {
	        Intent i = new Intent(getApplicationContext(), WaitingActivity.class);
	        startActivity(i);
		} else {
	        Intent i = new Intent(getApplicationContext(), EditingActivity.class);
	        startActivity(i);	
		}
	}
	
}