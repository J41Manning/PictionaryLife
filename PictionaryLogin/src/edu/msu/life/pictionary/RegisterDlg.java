package edu.msu.life.pictionary;

import java.io.IOException;
import java.io.InputStream;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import android.support.v4.app.DialogFragment;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Xml;
import android.view.View;
import android.widget.Toast;

public class RegisterDlg extends DialogFragment {
	
	RegisterDlg(String username, String password) {
		RegisterDlg.username = username;
		RegisterDlg.password = password;
	}
	
	protected final static String USERNAME = "username";
	protected final static String PASSWORD = "password";
	
	private static String username;
	
	private static String password;
	
    /**
     * Set true if we want to cancel
     */
	protected boolean cancel = false;
	
	/**
     * Create the dialog box
     */
    @Override
    public Dialog onCreateDialog(Bundle bundle) {
    	
        if(bundle != null) {
            username = bundle.getString(USERNAME);
            password = bundle.getString(PASSWORD);
        }
        
        cancel = false;
        
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        // Set the title
        builder.setTitle(R.string.msg_registering);

        builder.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                    	cancel = true;
                    }
                });

        
        // Create the dialog box
        final AlertDialog dlg = builder.create();
        
        // Get a reference to the view we are going to load into
        final View view = (View)getActivity().findViewById(R.id.registerView);
        
        /*
         * Create a thread to load the hatting from the cloud
         */
        new Thread(new Runnable() {

            @Override
            public void run() {
                // Create a cloud object and get the XML
                Cloud cloud = new Cloud();
                InputStream stream = cloud.loginToCloud(RegisterDlg.username, RegisterDlg.password);
                
                if(cancel) {
                    return;
                }
                
                // Test for an error
                boolean fail = stream == null;
                if(!fail) {
                    try {
                        XmlPullParser xml = Xml.newPullParser();
                        xml.setInput(stream, "UTF-8");       
                        
                        xml.nextTag();      // Advance to first tag
                        xml.require(XmlPullParser.START_TAG, null, "user");
                        
                        String status = xml.getAttributeValue(null, "status");
                        
                        if(!status.equals("yes")) {
                        	fail = true;
                        }
                        
                    } catch(IOException ex) {
                        fail = true;
                    } catch(XmlPullParserException ex) {
                        fail = true;
                    } finally {
                        try {
                            stream.close(); 
                        } catch(IOException ex) {
                        }
                    }
                }
                
                final boolean fail1 = fail;
                view.post(new Runnable() {

                    @Override
                    public void run() {
                        
                    	dlg.dismiss();
                        if(fail1) {
                            Toast.makeText(view.getContext(), getString(R.string.msg_register_fail) + username, Toast.LENGTH_SHORT).show();
                        } else {
                            // Success!
                            if(getActivity() instanceof LoginActivity) {
                                // ((RegisterActivity)getActivity()).updateUI();
                            }
                        }
                        
                    }
                
                });
            }
        }).start();
        
        return dlg;
    }
    
    /** 
     * Save the instance state
     */
    @Override
    public void onSaveInstanceState(Bundle bundle) {
        super.onSaveInstanceState(bundle);
        
        bundle.putString(USERNAME, username);
        bundle.putString(PASSWORD, password);
    }
    
    /**
     * Called when the view is destroyed.
     */
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        cancel = true;
    }
}
