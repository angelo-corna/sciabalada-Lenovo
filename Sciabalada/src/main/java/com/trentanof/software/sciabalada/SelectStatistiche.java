package com.trentanof.software.sciabalada;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.RelativeLayout;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

public class SelectStatistiche extends Activity {
	
	RelativeLayout rl;
	RadioGroup rg;
	ArrayList<String> yearsList = new ArrayList<String>();
	String path;
	String year = "";
	public final static String EXTRA_MESSAGE = "com.trentanof.software.sciabalada.MESSAGE";
	final Context context = this;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_selectstatistiche);
		
		// path = getFilesDir().getAbsolutePath();
		
		//** get destination path 
	    try {
		    FileReader fr = new FileReader(getFilesDir()+"/settings");
		    BufferedReader br = new BufferedReader(fr);
		    path = br.readLine();
		} catch (IOException e) {
			e.printStackTrace();
		}
	    
		File f = new File(path);        
		File file[] = f.listFiles();
		for (int i=0; i < file.length; i++) {
			String fileName=file[i].getName();
            if(!fileName .equals(".ttxfolder")) {
                String[] fileNameParts = fileName.split("_");
                String year = fileNameParts[1].substring(0, 4);
                if (!yearsList.contains(year)) {
                    yearsList.add(year);
                }
            }
		}
		Collections.sort(yearsList, Collections.reverseOrder());
		yearsList.add("Overall");

		int nsg=yearsList.size();
		
		final RadioButton[] rb = new RadioButton[nsg];
		rl=(RelativeLayout) findViewById(R.id.yl);
		rg=new RadioGroup(this);
		
		for(int i=0; i<yearsList.size(); i++)
		{
			rb[i]  = new RadioButton(this);
			rg.addView(rb[i]);
			rb[i].setText(yearsList.get(i));
		}
		rl.addView(rg);
		rl.setPadding(50, 50, 50, 50);

		rg.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
			int pos = rg.indexOfChild(findViewById(checkedId));
		    year = yearsList.get(pos);
		    Button calcYear=(Button)findViewById(R.id.calcyear);
			calcYear.setEnabled(true);
			}
		});
	}

	/** Called when the user clicks the resume button */
	public void calculate(View v) {
			Intent intent = new Intent(SelectStatistiche.this, Statistiche.class);
			intent.putExtra(EXTRA_MESSAGE, year);
			startActivity(intent);
	}
	

}