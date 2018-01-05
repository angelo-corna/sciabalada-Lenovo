package com.trentanof.software.sciabalada;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.Map;

import com.trentanof.software.sciabalada.R;

import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {

	public final static String EXTRA_MESSAGE = "com.trentanof.software.sciabalada.MESSAGE";
	final Context context = this;
	String answer;
    WebServer server; 
    
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		File f = new File(getFilesDir()+"/settings");
		if(!f.exists()) { 
			try {
		        File myFile = new File(getFilesDir()+"/settings");
		        myFile.createNewFile();
		        FileOutputStream fOut = new FileOutputStream(myFile);
		        OutputStreamWriter myOutWriter = new OutputStreamWriter(fOut);
		        String txtData = "/mnt/sdcard/sciabalada";
		        myOutWriter.append(txtData);
		        myOutWriter.close();
		        fOut.close();
		        Toast.makeText(getBaseContext(),
		                "File Settings inizializzato correttamente",
		                Toast.LENGTH_SHORT).show();
		    } catch (Exception e) {
		        Toast.makeText(getBaseContext(), e.getMessage(),
		                Toast.LENGTH_SHORT).show();
		    }
		}
		
		//create realTimeScore file
		String path = getFilesDir().getAbsolutePath();
        File myFile = new File(path+"/realTimeScore");
        try {
			myFile.createNewFile();
	        FileOutputStream fOut;
			fOut = new FileOutputStream(myFile);
	        OutputStreamWriter myOutWriter = new OutputStreamWriter(fOut);
	        String txtData = "<html><META HTTP-EQUIV=\"REFRESH\" CONTENT=\"5\"><body><h1>SCIABALADA on the Web</h1>\n";
	        txtData+="<hr align=”left” size=”1″ width=”300″ color=”red” noshade>\n";
	        txtData+="<br>\n";
	        txtData+="<h2>Nessuna partita in corso</h2>\n";
	        txtData+="</body></html>\n";
	        myOutWriter.append(txtData);
	        myOutWriter.close();
	        fOut.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//start https server
        server = new WebServer();
        try {
            server.start();
        } catch(IOException ioe) {
            Log.w("Httpd", "The server could not start.");
        }
        Log.w("Httpd", "Web server initialized.");
        
		return true;
	}
	
    @Override
    public void onDestroy()
    {
        super.onDestroy();
        if (server != null)
            server.stop();
    }

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
	    switch (item.getItemId()) {
	    case R.id.action_about:
	        // About option clicked.

			 
			 AlertDialog.Builder builder = new AlertDialog.Builder(this);
			 //builder.setTitle("My Title");
			 builder.setMessage("\nSCIABALADA\nVersion 1.2.0\n\nAuthor: Angelo Corna\nTrentanof Software\n");
			 builder.setPositiveButton("OK", null);
			 AlertDialog dialog = builder.show();
			 TextView messageText = (TextView)dialog.findViewById(android.R.id.message);
			 messageText.setGravity(Gravity.CENTER);
			 dialog.show();

	        return true;
	    case R.id.action_exit:
	    	finish();
	        return true;
	    case R.id.action_settings:
			Intent intent = new Intent(this, Settings.class);
			this.startActivity(intent);	
			return true;
	    default:
	        return super.onOptionsItemSelected(item);
	    }
	}
	
	/** Called when the user clicks the scala40 button */
	public void scala40(View v) {
		Intent intent = new Intent(this, TavoloScala.class);
		intent.putExtra(EXTRA_MESSAGE, "scala40");
		this.startActivity(intent);
	}

	/** Called when the user clicks the scala40QD button */
	public void scala40QD(View v) {
		Intent intent = new Intent(this, TavoloScala.class);
		intent.putExtra(EXTRA_MESSAGE, "scala40QD");
		this.startActivity(intent);
	}

	/** Called when the user clicks the scopa button */
	public void scopa(View v) {
		Intent intent = new Intent(this, TavoloScopa.class);
		this.startActivity(intent);
	}
	/** Called when the user clicks the Resume button */
	public void resume(View v) {
		Intent intent = new Intent(this, Resume.class);
		this.startActivity(intent);
	}

	/** Called when the user clicks the Statistiche button */
	public void statistiche(View v) {
		Intent intent = new Intent(this, SelectStatistiche.class);
//		Intent intent = new Intent(this, GraficoSaldo.class);
		this.startActivity(intent);
	}
	
    private class WebServer extends NanoHTTPD {

        public WebServer()
        {
            super(8080);
        }

        @Override
        public Response serve(String uri, Method method, 
                              Map<String, String> header,
                              Map<String, String> parameters,
                              Map<String, String> files) {
        	
        	answer="";
        	try {    		    
			    FileReader fr = new FileReader(getFilesDir().getAbsolutePath()+"/realTimeScore");
			    BufferedReader br = new BufferedReader(fr);
			    String s; 
			    while(true) {
			        s=br.readLine();
			        if(s==null)
			          break;
			          answer+=s;	
			      }
			} catch (IOException e) {
				e.printStackTrace();
			}
        	
            return new NanoHTTPD.Response(answer);
            
        }
    }
}
