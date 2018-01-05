package com.trentanof.software.sciabalada;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;

import com.trentanof.software.sciabalada.PartitaScala;
import com.trentanof.software.sciabalada.R;

import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class Settings extends Activity {

	

	public final static String EXTRA_MESSAGE = "com.trentanof.software.sciabalada.MESSAGE";
	final Context context = this;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_settings);
		
	    try {
		    FileReader fr = new FileReader(getFilesDir()+"/settings");
		    BufferedReader br = new BufferedReader(fr);
		    String fl = br.readLine();
			EditText destinationFolderValue = (EditText) findViewById(R.id.destinationFolderValue);
			destinationFolderValue.setText(fl);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void save(View v) {
		
		EditText editTextp = (EditText) findViewById(R.id.destinationFolderValue);
		String valuePath = editTextp.getText().toString();
		
		try {
	        File myFile = new File(getFilesDir()+"/settings");
	        myFile.createNewFile();
	        FileOutputStream fOut = new FileOutputStream(myFile);
	        OutputStreamWriter myOutWriter = new OutputStreamWriter(fOut);
	        String txtData = valuePath;
	        myOutWriter.append(txtData);
	        myOutWriter.close();
	        fOut.close();
	        Toast.makeText(getBaseContext(),
	                "Settings salvati correttamente",
	                Toast.LENGTH_SHORT).show();
	    } catch (Exception e) {
	        Toast.makeText(getBaseContext(), e.getMessage(),
	                Toast.LENGTH_SHORT).show();
	    }

	}
	
}
