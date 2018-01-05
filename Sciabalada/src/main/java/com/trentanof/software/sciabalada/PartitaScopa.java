package com.trentanof.software.sciabalada;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.Arrays;

import android.os.Bundle;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.LightingColorFilter;
import android.graphics.drawable.Drawable;
import android.text.InputType;
import android.view.Gravity;
import android.view.Menu;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

public class PartitaScopa extends Activity {
	//// create the players
	Player pl1 = new Player(0,"dummy",1);
	Player pl2 = new Player(0,"dummy",2);

	final Context context = this;
	String valueP;
	String kp;
	String destinationPath;
	java.util.Date date= new java.util.Date();
	String timestamp = new java.text.SimpleDateFormat("yyyyMMdd_hmmssa").format(date);
	String fileName = "scopa_"+timestamp;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_partita_scopa);
		// Get the message from the intent
		Intent intent = getIntent();
		String message = intent.getStringExtra(TavoloScopa.EXTRA_MESSAGE);
		//System.out.println(message);
		
		String[] parts = message.split("#");
	    int plcounter = 1;
	    for (String name : parts) {
			switch (plcounter) {
				case 1:	kp = name;
						if(!kp.equals("new")){fileName = kp;}
				break;
				case 2:	valueP = name;
				break;	
				case 3: pl1.init(kp,name);
					if(name.equals("Angelo & Co")){pl1.setPictureName("angelocowin");}
					if(name.equals("Mauro & Karmen")){pl1.setPictureName("maurokarmenwin");}
					if(name.equals("Renzo & Katia")){pl1.setPictureName("renzokatiawin");}
				break;	
				case 4: pl2.init(kp,name);
					if(name.equals("Angelo & Co")){pl2.setPictureName("angelocowin");}
					if(name.equals("Mauro & Karmen")){pl2.setPictureName("maurokarmenwin");}
					if(name.equals("Renzo & Katia")){pl2.setPictureName("renzokatiawin");}
				break;	
			}
	    	plcounter++;
	    }

   		TextView jackpot = (TextView) findViewById(R.id.jackpot);
		jackpot.setText("Jackpot "+Float.parseFloat(valueP)+" €");
 		
		// display the players
		DisplayPlayerData(pl1);
		DisplayPlayerData(pl2);
		
		setColorsGames();
		
		//** get destination path 
	    try {
		    FileReader fr = new FileReader(getFilesDir()+"/settings");
		    BufferedReader br = new BufferedReader(fr);
		    destinationPath = br.readLine();
		} catch (IOException e) {
			e.printStackTrace();
		}

	    saveFile("onGoing");
	}

/*	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.partita_scopa, menu);
		return true;
	}
*/	
	
	//** Called when the user clicks on progressbars */
	public void updScore1(View view) {
		UpdatePlayerScore(pl1);
	}
	public void updScore2(View view) {
		UpdatePlayerScore(pl2);
	}

	public void UpdatePlayerScore(Player pl){
		 final Player plselected = pl;	
		 AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
		 alertDialogBuilder.setCancelable(false);
	
		 LinearLayout layout = new LinearLayout(this);
		 layout.setOrientation(LinearLayout.VERTICAL);
		 LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(100,LinearLayout.LayoutParams.WRAP_CONTENT);
		 params.gravity= Gravity.CENTER; 
		 params.topMargin=10;
		 params.bottomMargin=10;
	
		 ImageView image = new ImageView(this);
		 int id = getResources().getIdentifier(plselected.getPictureName(), "drawable", getPackageName());
	     image.setImageResource(id);
	     layout.addView(image,params);
	
		 alertDialogBuilder.setView(layout); 
	
		 alertDialogBuilder.setTitle("Aggiorna il punteggio di "+plselected.getName());
			
		 final EditText textBox = new EditText(PartitaScopa.this);
		 textBox.setInputType(InputType.TYPE_CLASS_NUMBER);
		 textBox.setGravity(Gravity.CENTER);
		 layout.addView(textBox, params);
	
		 alertDialogBuilder.setView(layout); 
			 
		 alertDialogBuilder.setPositiveButton("Esci",
		            new DialogInterface.OnClickListener() {
		                public void onClick(DialogInterface dialog, int id) {
		                    dialog.cancel();
		                }
		            });
			 
		 alertDialogBuilder.setNeutralButton("Sostituisci",
		            new DialogInterface.OnClickListener() {
		                public void onClick(DialogInterface dialog, int id) {
		                	String replaceScoreString = String.valueOf(textBox.getText());
		            		if(!replaceScoreString.equals("")){
		            			int replaceScore = Integer.parseInt(replaceScoreString);
		            			plselected.setScore(replaceScore);
		            			if(plselected.getScore() > 150){plselected.setIsOut(true);}
		            			DisplayPlayerData(plselected);
		            		}
							saveFile("onGoing");
		                }
		            });
			
			 alertDialogBuilder.setNegativeButton("Aggiungi",
			            new DialogInterface.OnClickListener() {
			                @SuppressLint("ResourceAsColor")
							public void onClick(DialogInterface dialog, int id) {
			                	String addScoreString = String.valueOf(textBox.getText());
			                	if(!addScoreString.equals("")){
			            			int addScore = Integer.parseInt(addScoreString);
			            			plselected.setScore(plselected.getScore()+addScore);
			            			plselected.setGames(plselected.getGames()+1);
			            			DisplayPlayerData(plselected);
			            			setColorsGames();
		            				// and the winner is .....
			                		if (pl1.getScore() != pl2.getScore() & pl1.getGames() == pl2.getGames()){
			                			if (pl1.getScore() > 31 | pl2.getScore() > 31){
			                				String winName;
			                				String winImage;
			                				if(pl1.getScore() > pl2.getScore()){
			                					winName = pl1.getName(); 
			                					winImage = pl1.getPictureName(); 
			                				}else{
			                					winName = pl2.getName();
			                					winImage = pl2.getPictureName(); 
			                				} 
						                	AlertDialog.Builder alertDialogBuilderWin = new AlertDialog.Builder(context);
						                	alertDialogBuilderWin.setCancelable(false);
						                	alertDialogBuilderWin.setTitle("and the winnner is ......... "+winName);
						                	
							       			 LinearLayout layout1 = new LinearLayout(context);
							    			 layout1.setOrientation(LinearLayout.VERTICAL);
							    			 LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(100,LinearLayout.LayoutParams.WRAP_CONTENT);
							    			 params.gravity= Gravity.CENTER; 
							    			 params.topMargin=10;
							    			 params.bottomMargin=10;
							    	
							    			 ImageView image = new ImageView(context);
							    			 int id1 = getResources().getIdentifier(winImage, "drawable", getPackageName());
							    		     image.setImageResource(id1);
							    		     layout1.addView(image,params);
						    	
							    			 alertDialogBuilderWin.setView(layout1); 
						                	
						   				 	alertDialogBuilderWin.setNeutralButton("Non Salvare",
										            new DialogInterface.OnClickListener() {
										                public void onClick(DialogInterface dialog, int id) {
										                	finish();
										                }
										            });
						   				 	alertDialogBuilderWin.setNegativeButton("Salva",
										            new DialogInterface.OnClickListener() {
										                public void onClick(DialogInterface dialog, int id) {
										                	saveFile("closed");
										                	finish();
										                }
										            });
					                	
						        			AlertDialog alertDialogWin = alertDialogBuilderWin.create();
						        			alertDialogWin.show();
					                	}
			                		}
			                	}
								saveFile("onGoing");
			                }
			            });
			 	AlertDialog alertDialog = alertDialogBuilder.create();
			 	alertDialog.show();
		}

	
	public void DisplayPlayerData(Player pl){
		
		// define animation
		
		Integer rank = pl.getRank();
		// display values
		switch (rank) {
	        case 1: TextView pl1Name = (TextView) findViewById(R.id.player1);
	        		ProgressBar scoreBar1 = (ProgressBar) findViewById(R.id.scoreBar1);
	        		TextView scoreText1 = (TextView) findViewById(R.id.scoreText1);
	        		TextView pl1Games = (TextView) findViewById(R.id.games1);
					TextView imgGames1 = (TextView) findViewById(R.id.games1); 
	        		pl1Name.setText(pl.getName());;
	        		scoreText1.setText("" + pl.getScore());		
	        		scoreBar1.setProgress(pl.getScore());
        			pl1Games.setText("" + pl.getGames());
	        		break;
	        case 2: TextView pl2Name = (TextView) findViewById(R.id.player2);
					ProgressBar scoreBar2 = (ProgressBar) findViewById(R.id.scoreBar2);
					TextView scoreText2 = (TextView) findViewById(R.id.scoreText2);
	        		TextView pl2Games = (TextView) findViewById(R.id.games2);
					TextView imgGames2 = (TextView) findViewById(R.id.games2); 
					pl2Name.setText(pl.getName());;
		    		scoreText2.setText("" + pl.getScore());		
		    		scoreBar2.setProgress(pl.getScore());
        			pl2Games.setText("" + pl.getGames());
	        		break;
	    }
	}
	
	public String retval(Integer Num){
		String stringx = "";
		return stringx;	
	}
	
	public void setColorsGames(){
		int[] allGames = {pl1.getGames(),pl2.getGames()};
		Arrays.sort(allGames);  
		int maxGames = allGames[allGames.length-1]; 

		TextView imgGames1 = (TextView) findViewById(R.id.games1); 
		if(pl1.getGames() < maxGames){
				imgGames1.setBackgroundColor(Color.RED);
			}else{
				imgGames1.setBackgroundColor(Integer.parseInt("35d926", 16)+0xFF000000);
		}
		
		TextView imgGames2 = (TextView) findViewById(R.id.games2); 
			if(pl2.getGames() < maxGames){
				imgGames2.setBackgroundColor(Color.RED);
			}else{
				imgGames2.setBackgroundColor(Integer.parseInt("35d926", 16)+0xFF000000);
			}
	}
	
	public void saveFile(String gameType){
		try {
	        File myFile = new File(destinationPath+"/"+fileName);
	        myFile.createNewFile();
	        FileOutputStream fOut = new FileOutputStream(myFile);
	        OutputStreamWriter myOutWriter = new OutputStreamWriter(fOut);
	        String txtData = gameType+"\n";
			txtData=txtData+(Float.parseFloat(valueP)+"\n");
    		txtData=txtData+pl1.getName()+","+pl1.getScore()+","+pl1.getIsOut()+","+pl1.getGames()+","+pl1.getReenter()+"\n";
    		txtData=txtData+pl2.getName()+","+pl2.getScore()+","+pl2.getIsOut()+","+pl2.getGames()+","+pl2.getReenter()+"\n";
	        myOutWriter.append(txtData);
	        myOutWriter.close();
	        fOut.close();
	        if (gameType == "closed"){
	        Toast.makeText(getBaseContext(),
	                "Done writing "+fileName,
	                Toast.LENGTH_SHORT).show();
	        }
	    } catch (Exception e) {
 	        Toast.makeText(getBaseContext(), e.getMessage(),
	                Toast.LENGTH_SHORT).show();
	    }
		
		//save realTimeScore file
		try {
	        File myFile = new File(getFilesDir().getAbsolutePath()+"/realTimeScore");
	        myFile.createNewFile();
	        FileOutputStream fOut = new FileOutputStream(myFile);
	        OutputStreamWriter myOutWriter = new OutputStreamWriter(fOut);
	        String txtData = "<html><META HTTP-EQUIV=\"REFRESH\" CONTENT=\"5\"><body><h1>SCIABALADA on the Web</h1>\n";
	        txtData+="<hr align=”left” size=”1″ width=”300″ color=”red” noshade>\n";
	        txtData+="<br>\n";
	        
	        String ngsf;
	        if(fileName.substring(0,5).equals("scopa")){ngsf="Partita a Scopa del "+fileName.substring(12, 14)+"/"+fileName.substring(10, 12)+"/"+fileName.substring(6, 10);
			}else if(fileName.substring(0,9).equals("scala40QD")){ngsf="Partita a Scala 40 Quick & Dirty del "+fileName.substring(16, 18)+"/"+fileName.substring(14, 16)+"/"+fileName.substring(10, 14);
			}else{ngsf="Partita a Scala 40 del "+fileName.substring(14, 16)+"/"+fileName.substring(12, 14)+"/"+fileName.substring(8, 12);}
	        if(gameType == "closed"){
	        	txtData+="<h2>"+ngsf+" Terminata<h2>\n";
	        }else{
	        	txtData+="<h2>"+ngsf+" in Corso<h2>\n";
	        }

	        txtData+="<h2>Jackpot: "+Float.parseFloat(valueP)+" euro<h2><br>";

	        txtData+="<table cellpadding=0 cellspacing=0 style=\"height:100%; width:100%\">\n";
        	
        	//pl1	
        	String bgcolor = "efefef";
	        txtData+="<tr>\n";
    		if(pl1.getScore() > 30 && pl1.getScore() > pl2.getScore()){
	    		txtData+="<td bgcolor=\"#"+bgcolor+"\" valign=\"center\" align=\"center\"><h1><font color=\"#FF0000\">WIN</font><h1></td>\n";
    		}else{
	    		txtData+="<td bgcolor=\"#"+bgcolor+"\" valign=\"center\" align=\"center\"><h1><font color=\"#FF0000\"></font><h1></td>\n";
    		}
    		txtData+="<td bgcolor=\"#"+bgcolor+"\" valign=\"center\"><h1>"+pl1.getName()+"<h1></td>\n";
    		txtData+="<td bgcolor=\"#"+bgcolor+"\" valign=\"center\"><h1>"+pl1.getScore()+"</h1></td>\n";
	        txtData+="</tr>\n";

        	//pl2	
        	bgcolor = "ffffff";
	        txtData+="<tr>\n";
    		if(pl2.getScore() > 30 && pl2.getScore() > pl1.getScore()){
	    		txtData+="<td bgcolor=\"#"+bgcolor+"\" valign=\"center\" align=\"center\"><h1><font color=\"#FF0000\">WIN</font><h1></td>\n";
    		}else{
	    		txtData+="<td bgcolor=\"#"+bgcolor+"\" valign=\"center\" align=\"center\"><h1><font color=\"#FF0000\"></font><h1></td>\n";
    		}
    		txtData+="<td bgcolor=\"#"+bgcolor+"\" valign=\"center\"><h1>"+pl2.getName()+"<h1></td>\n";
    		txtData+="<td bgcolor=\"#"+bgcolor+"\" valign=\"center\"><h1>"+pl2.getScore()+"</h1></td>\n";
	        txtData+="</tr>\n";
		
	        txtData+="</table>\n";
	        
	        txtData+="</body></html>\n";
	        myOutWriter.append(txtData);
	        myOutWriter.close();
	        fOut.close();
	    } catch (Exception e) {
	        Toast.makeText(getBaseContext(), e.getMessage(),
	                Toast.LENGTH_SHORT).show();
	    }

	}
}
