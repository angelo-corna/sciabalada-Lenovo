package com.trentanof.software.sciabalada;
import com.trentanof.software.sciabalada.PartitaScala;

import java.io.*;
import java.net.*;
import java.util.ArrayList;

import android.os.Bundle;
import android.os.Environment;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.text.InputType;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.RelativeLayout;

public class Resume extends Activity {
	
	RelativeLayout rl;
	RadioGroup rg;
	ArrayList<String> suspendedGames = new ArrayList<String>();
	String path;
	String message = "";
	String ng;
	String ngs;
	public final static String EXTRA_MESSAGE = "com.trentanof.software.sciabalada.MESSAGE";
	final Context context = this;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_resume);
		
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
		for (int i=0; i < file.length; i++)
		{
		    try {
			    FileReader fr = new FileReader(path+"/"+file[i].getName());
			    BufferedReader br = new BufferedReader(fr);
			    String fl = br.readLine();
			    if(fl.equals("onGoing")){
			    		suspendedGames.add(file[i].getName());
		    	}		
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		//System.out.println("partite sospese: "+suspendedGames.size());

		int nsg=suspendedGames.size();
		
		final RadioButton[] rb = new RadioButton[nsg];
		rl=(RelativeLayout) findViewById(R.id.rl);
		rg=new RadioGroup(this);
		
		int n=nsg;
		for(int i=0; i<n; i++)
		{
			rb[i]  = new RadioButton(this);
			rg.addView(rb[i]);
			ng = suspendedGames.get(i);
			if(ng.substring(0,5).equals("scopa")){rb[i].setText("Partita a Scopa del "+ng.substring(12, 14)+"/"+ng.substring(10, 12)+"/"+ng.substring(6, 10));
			}else if(ng.substring(0,9).equals("scala40QD")){rb[i].setText("Partita a Scala 40 Quick & Dirty del "+ng.substring(16, 18)+"/"+ng.substring(14, 16)+"/"+ng.substring(10, 14));
			}else{rb[i].setText("Partita a Scala 40 del "+ng.substring(14, 16)+"/"+ng.substring(12, 14)+"/"+ng.substring(8, 12));}
		}
		rl.addView(rg);
		rl.setPadding(50, 50, 50, 50);
		
		
		
		rg.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
			int pos = rg.indexOfChild(findViewById(checkedId));
		    ngs = suspendedGames.get(pos);
		    Button resume=(Button)findViewById(R.id.resume);
			resume.setEnabled(true);
		    Button delete=(Button)findViewById(R.id.delete);
			delete.setEnabled(true);

			}
		});
	}

	/** Called when the user clicks the resume button */
	public void resume(View v) {
	    
		try {
		    FileReader fr = new FileReader(path+"/"+ngs);
		    BufferedReader br = new BufferedReader(fr);
		    String s; 
		    while(true) {
		        s=br.readLine();
		        if(s==null)
		          break;
		          if (s.equals("onGoing")){message=ngs;}
		          else{message=message+"#"+s;}

		      }
		} catch (IOException e) {
			e.printStackTrace();
		}
           // System.out.println("ngs="+ngs);
		if(ngs.substring(0,5).equals("scopa")){
			Intent intent = new Intent(Resume.this, PartitaScopa.class);
			intent.putExtra(EXTRA_MESSAGE, message);
			startActivity(intent);
		}else if(ngs.substring(0,9).equals("scala40QD")){
			Intent intent = new Intent(Resume.this, PartitaScala40QD.class);
			intent.putExtra(EXTRA_MESSAGE, message);
			startActivity(intent);
		}else{
			Intent intent = new Intent(Resume.this, PartitaScala.class);
			intent.putExtra(EXTRA_MESSAGE, message);
			startActivity(intent);
		}
	}
	
	public void delete(View v) {
		 AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
		 alertDialogBuilder.setCancelable(false);

		 LinearLayout layout = new LinearLayout(this);
		 layout.setOrientation(LinearLayout.VERTICAL);
		 LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(100,LinearLayout.LayoutParams.WRAP_CONTENT);
		 params.gravity= Gravity.CENTER; 
		 params.topMargin=10;
		 params.bottomMargin=10;

		 ImageView image = new ImageView(this);
		 int id = getResources().getIdentifier("alert", "drawable", getPackageName());
	     image.setImageResource(id);

	     layout.addView(image,params);
	     
		 alertDialogBuilder.setView(layout); 
		 
		 String ngsf;
		 if(ngs.substring(0,5).equals("scopa")){ngsf="Partita a Scopa del "+ngs.substring(12, 14)+"/"+ngs.substring(10, 12)+"/"+ngs.substring(6, 10);
		 }else if(ngs.substring(0,9).equals("scala40QD")){ngsf="Partita a Scala 40 Quick & Dirty del "+ngs.substring(16, 18)+"/"+ngs.substring(14, 16)+"/"+ngs.substring(10, 14);
		 }else{ngsf="Partita a Scala 40 del "+ngs.substring(14, 16)+"/"+ngs.substring(12, 14)+"/"+ngs.substring(8, 12);}

			
		 alertDialogBuilder.setTitle("Conferma Eliminazione della "+ngsf);

		 alertDialogBuilder.setNegativeButton("Eliminare",
		           new DialogInterface.OnClickListener() {
		               public void onClick(DialogInterface dialog, int id) {
		           	    	File f2d = new File(path+"/"+ngs);
		           	    	f2d.delete();
		           	    	finish();
		           	    }     
		           });
			
		 alertDialogBuilder.setPositiveButton("Non Eliminare",
			            new DialogInterface.OnClickListener() {
			                public void onClick(DialogInterface dialog, int id) {
			                    dialog.cancel();
			                }
			            });
		 AlertDialog alertDialog = alertDialogBuilder.create();
		 alertDialog.show();
	}

		


}