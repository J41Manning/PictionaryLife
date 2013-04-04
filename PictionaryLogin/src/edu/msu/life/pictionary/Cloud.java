package edu.msu.life.pictionary;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import android.util.Log;
import android.util.Xml;

public class Cloud {
    
	private static final String MAGIC = "NechAtHa6RuzeR8x";
    
    private static final String LOGIN_URL = "https://www.cse.msu.edu/~siddiq39/cse476/pictionary-login.php"; //TODO
    private static final String REGISTER_URL = ""; //TODO
    private static final String UTF8 = "UTF-8";
    
    public static void logStream(InputStream stream) {
        BufferedReader reader = new BufferedReader(
                new InputStreamReader(stream));
    
        try {
            String line;
            while ((line = reader.readLine()) != null) {
                Log.e("476", line);
            }
        } catch (IOException ex) {
            return;
        }
    }
    
    /**
     * Open a connection to login to the cloud.
     * @return true if logged in, false otherwise
     */
    public boolean loginToCloud(String username, String password) {

        String query = LOGIN_URL + "?user=" + username + "&pw=" + password+ "&magic=" + MAGIC;
        
        InputStream stream = null;
        try {
            URL url = new URL(query);

            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            int responseCode = conn.getResponseCode();
            if(responseCode != HttpURLConnection.HTTP_OK) {
                return false;
            }
            
            stream = conn.getInputStream();
          //logStream(stream);
            return resultStatus(stream);

        } catch (MalformedURLException e) {
            // Should never happen
            return false;
        } catch (IOException ex) {
            return false;
        }
    }
    
    /**
     * Open a connection to register a new account.
     * @return true if registered, false otherwise
     */
    public boolean registerToCloud(String username, String password) {

        String query = REGISTER_URL + "?user=" + username 
   			 + "&pw=" + password
   			 + "&magic=" + MAGIC;
        
        InputStream stream = null;
        try {
            URL url = new URL(query);

            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
           
            int responseCode = conn.getResponseCode();
            if(responseCode != HttpURLConnection.HTTP_OK) {
                return false;
            } 
            
            stream = conn.getInputStream();
            return resultStatus(stream);
            
        } catch (MalformedURLException e) {
            // Should never happen
            return false;
        } catch (IOException ex) {
            return false;
        }
    }
    
    /**
     * Parse all the results and return the status
     * 
     * @param stream The result stream
     * @return False if status is "no" or exceptions are thrown, True otherwise
     */
    
    private static boolean resultStatus(InputStream stream) {
        /**
         * Create an XML parser for the result
         */
        try {
            XmlPullParser xmlR = Xml.newPullParser();
            xmlR.setInput(stream, UTF8);
            
            xmlR.nextTag();      // Advance to first tag
            xmlR.require(XmlPullParser.START_TAG, null, "user");
            
            String status = xmlR.getAttributeValue(null, "status");
            if(status.equals("no")) {
                return false;
            }
            
            // We are done
        } catch(XmlPullParserException ex) {
            return false;
        } catch(IOException ex) {
            return false;
        }
        return true;
    }
    
    /**
     * Skip the XML parser to the end tag for whatever 
     * tag we are currently within.
     * @param xml the parser
     * @throws IOException
     * @throws XmlPullParserException
     */
    public static void skipToEndTag(XmlPullParser xml) 
            throws IOException, XmlPullParserException {
        int tag;
        do
        {
            tag = xml.next();
            if(tag == XmlPullParser.START_TAG) {
                // Recurse over any start tag
                skipToEndTag(xml);
            }
        } while(tag != XmlPullParser.END_TAG && 
        tag != XmlPullParser.END_DOCUMENT);
    }
}