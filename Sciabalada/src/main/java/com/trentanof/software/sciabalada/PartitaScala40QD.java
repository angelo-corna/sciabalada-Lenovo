package com.trentanof.software.sciabalada;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.trentanof.software.sciabalada.R;

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
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;


public class PartitaScala40QD extends Activity {

	//// create the players
	Player pl1 = new Player(0,"dummy",1);
	Player pl2 = new Player(0,"dummy",2);
	Player pl3 = new Player(0,"dummy",3);
	Player pl4 = new Player(0,"dummy",4);
	Player pl5 = new Player(0,"dummy",5);
	Player pl6 = new Player(0,"dummy",6);
	Player pl7 = new Player(0,"dummy",7);
	Player pl8 = new Player(0,"dummy",8);
	Player pl9 = new Player(0,"dummy",9);
	Player pl10 = new Player(0,"dummy",10);
	
	final Context context = this;
	String valueP;
	int numPlayers=0;
	String kp;
	String destinationPath;
	int numReenters = 0;
	java.util.Date date= new java.util.Date();
	String timestamp = new java.text.SimpleDateFormat("yyyyMMdd_hmmssa").format(date);
	String fileName = "scala40QD_"+timestamp;
	
	Player[] effectivePl = new Player[10];

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_partita_scala40_qd);
		// Get the message from the intent
		Intent intent = getIntent();
		String message = intent.getStringExtra(TavoloScala.EXTRA_MESSAGE);

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
						numPlayers += 1;
				break;	
				case 4: pl2.init(kp,name);
						numPlayers += 1;
				break;	
				case 5: pl3.init(kp,name);
						numPlayers += 1;
				break;	
				case 6: pl4.init(kp,name);
						numPlayers += 1;
				break;	
				case 7: pl5.init(kp,name);
						numPlayers += 1;
				break;	
				case 8: pl6.init(kp,name);
						numPlayers += 1;
				break;	
				case 9: pl7.init(kp,name);
						numPlayers += 1;
				break;	
				case 10: pl8.init(kp,name);
						numPlayers += 1;
				break;	
				case 11: pl9.init(kp,name);
						numPlayers += 1;
				break;	
				case 12: pl10.init(kp,name);
						numPlayers += 1;
				break;	
			}
	    	plcounter++;
	    }

	    int plcounterEff = 0;
	    // Player[] effectivePl = new Player[numPlayers];
		if (pl1.getName() != "dummy"){effectivePl[plcounterEff] = pl1; plcounterEff += 1;}
		if (pl2.getName() != "dummy"){effectivePl[plcounterEff] = pl2; plcounterEff += 1;}
		if (pl3.getName() != "dummy"){effectivePl[plcounterEff] = pl3; plcounterEff += 1;}
		if (pl4.getName() != "dummy"){effectivePl[plcounterEff] = pl4; plcounterEff += 1;}
		if (pl5.getName() != "dummy"){effectivePl[plcounterEff] = pl5; plcounterEff += 1;}
		if (pl6.getName() != "dummy"){effectivePl[plcounterEff] = pl6; plcounterEff += 1;}
		if (pl7.getName() != "dummy"){effectivePl[plcounterEff] = pl7; plcounterEff += 1;}
		if (pl8.getName() != "dummy"){effectivePl[plcounterEff] = pl8; plcounterEff += 1;}
		if (pl9.getName() != "dummy"){effectivePl[plcounterEff] = pl9; plcounterEff += 1;}
		if (pl10.getName() != "dummy"){effectivePl[plcounterEff] = pl10; plcounterEff += 1;}
		
		
   		TextView jackpot = (TextView) findViewById(R.id.jackpot);
		jackpot.setText("Jackpot "+numPlayers*Float.parseFloat(valueP)+" €");
 		

		// display the players
		DisplayPlayerData(pl1);
		DisplayPlayerData(pl2);
		DisplayPlayerData(pl3);
		DisplayPlayerData(pl4);
		DisplayPlayerData(pl5);
		DisplayPlayerData(pl6);
		DisplayPlayerData(pl7);
		DisplayPlayerData(pl8);
		DisplayPlayerData(pl9);
		DisplayPlayerData(pl10);
		
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

	/* @Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.partita_scala40_qd, menu);
		return true;
	}
	*/
	
	//** Called when the user clicks on progressbars 
	public void updScore1(View view) {
		UpdatePlayerScore(pl1);
	}
	public void updScore2(View view) {
		UpdatePlayerScore(pl2);
	}
	public void updScore3(View view) {
		UpdatePlayerScore(pl3);
	}
	public void updScore4(View view) {
		UpdatePlayerScore(pl4);
	}
	public void updScore5(View view) {
		UpdatePlayerScore(pl5);
	}
	public void updScore6(View view) {
		UpdatePlayerScore(pl6);
	}
	public void updScore7(View view) {
		UpdatePlayerScore(pl7);
	}
	public void updScore8(View view) {
		UpdatePlayerScore(pl8);
	}
	public void updScore9(View view) {
		UpdatePlayerScore(pl9);
	}
	public void updScore10(View view) {
		UpdatePlayerScore(pl10);
	}
	
	public void UpdatePlayerScore(Player pl){
		 final Player plselected = pl;	
		 if(plselected.getIsOut() == false){
			 AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
			 alertDialogBuilder.setCancelable(false);
	
			 LinearLayout layout = new LinearLayout(this);
			 layout.setOrientation(LinearLayout.VERTICAL);
			 LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(100,LinearLayout.LayoutParams.WRAP_CONTENT);
			 params.gravity= Gravity.CENTER; 
			 params.topMargin=10;
			 params.bottomMargin=10;
	
			 //ImageView image = new ImageView(this);
			 //int id = getResources().getIdentifier(plselected.getPictureName(), "drawable", getPackageName());
		     //image.setImageResource(id);
		     //layout.addView(image,params);
	
			 alertDialogBuilder.setView(layout); 
	
			 alertDialogBuilder.setTitle("Aggiorna il punteggio di "+plselected.getName());
			
			 final EditText textBox = new EditText(PartitaScala40QD.this);
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
			            			if(plselected.getScore() > 150){plselected.setIsOut(true);}
			            			DisplayPlayerData(plselected);
			            			setColorsGames();
			            			// and the winner is .....
				                	int numPlayersIn=0;	
		                			String namePlayerIn = "";
				                	for (int a=0; a < numPlayers; a++) {
				                		if (effectivePl[a].getIsOut() == false){
				                			numPlayersIn = numPlayersIn +1;
				                			namePlayerIn=effectivePl[a].getName();
				                		}
				                	}	
			                		if (numPlayersIn == 1){
						                	AlertDialog.Builder alertDialogBuilderWin = new AlertDialog.Builder(context);
						                	alertDialogBuilderWin.setCancelable(false);
						                	alertDialogBuilderWin.setTitle("and the winnner is ......... "+namePlayerIn);
						                	
							       			 LinearLayout layout1 = new LinearLayout(context);
							    			 layout1.setOrientation(LinearLayout.VERTICAL);
							    			 LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(100,LinearLayout.LayoutParams.WRAP_CONTENT);
							    			 params.gravity= Gravity.CENTER; 
							    			 params.topMargin=10;
							    			 params.bottomMargin=10;
							    	
							    			 ImageView image = new ImageView(context);
							    			 int id1 = getResources().getIdentifier(namePlayerIn.toLowerCase()+"1", "drawable", getPackageName());
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
								saveFile("onGoing");
			                }
			            });
			 	AlertDialog alertDialog = alertDialogBuilder.create();
			 	alertDialog.show();
		 	}
		}

	

	public void DisplayPlayerData(Player pl){
	
		// define animation
		Animation anim = new AlphaAnimation(0.0f, 1.0f);
		anim.setDuration(500); //You can manage the time of the blink with this parameter
		anim.setStartOffset(20);
		anim.setRepeatMode(Animation.REVERSE);
		anim.setRepeatCount(Animation.INFINITE);
		
		Integer rank = pl.getRank();
		// display values
		switch (rank) {
	        case 1: TextView pl1Name = (TextView) findViewById(R.id.player1);
	        		ProgressBar scoreBar1 = (ProgressBar) findViewById(R.id.scoreBar1);
	        		TextView scoreText1 = (TextView) findViewById(R.id.scoreText1);
	        		TextView pl1Games = (TextView) findViewById(R.id.games1);
					TextView imgGames1 = (TextView) findViewById(R.id.games1); 
	        		if (pl1.getName().equals("dummy")){
						pl1Name.setVisibility(TextView.GONE);
			    		scoreText1.setVisibility(TextView.GONE);		
			    		scoreBar1.setVisibility(ProgressBar.GONE);
	        			pl1Games.setVisibility(TextView.GONE);
	        		}else{
		        		pl1Name.setText(pl.getName());;
		        		scoreText1.setText("" + pl.getScore());		
		        		scoreBar1.setProgress(pl.getScore());
	        			pl1Games.setText("" + pl.getGames());
		        		if (pl.getIsOut() == true){
		        			pl1Name.setTextColor(Color.GRAY);
		        			scoreText1.setTextColor(Color.GRAY);
	        				imgGames1.setBackgroundColor(Color.GRAY);
	        				Drawable drawable = scoreBar1.getProgressDrawable();
	        				drawable.setColorFilter(new LightingColorFilter(0xFF000000, 0xFF8c8a8c));
		        		}
	        		}
	        		break;
	        case 2: TextView pl2Name = (TextView) findViewById(R.id.player2);
					ProgressBar scoreBar2 = (ProgressBar) findViewById(R.id.scoreBar2);
					TextView scoreText2 = (TextView) findViewById(R.id.scoreText2);
	        		TextView pl2Games = (TextView) findViewById(R.id.games2);
					TextView imgGames2 = (TextView) findViewById(R.id.games2); 
	        		if (pl2.getName().equals("dummy")){
						pl2Name.setVisibility(TextView.GONE);
			    		scoreText2.setVisibility(TextView.GONE);		
			    		scoreBar2.setVisibility(ProgressBar.GONE);
	        			pl2Games.setVisibility(TextView.GONE);
	        		}else{
						pl2Name.setText(pl.getName());;
			    		scoreText2.setText("" + pl.getScore());		
			    		scoreBar2.setProgress(pl.getScore());
	        			pl2Games.setText("" + pl.getGames());
	        			if (pl.getIsOut() == true){
		        			pl2Name.setTextColor(Color.GRAY);
		        			scoreText2.setTextColor(Color.GRAY);
	        				imgGames2.setBackgroundColor(Color.GRAY);
	        				Drawable drawable = scoreBar2.getProgressDrawable();
	        				drawable.setColorFilter(new LightingColorFilter(0xFF000000, 0xFF8c8a8c));
		        		}
	        		}
	        		break;
	        case 3: TextView pl3Name = (TextView) findViewById(R.id.player3);
					ProgressBar scoreBar3 = (ProgressBar) findViewById(R.id.scoreBar3);
					TextView scoreText3 = (TextView) findViewById(R.id.scoreText3);
	        		TextView pl3Games = (TextView) findViewById(R.id.games3);
					TextView imgGames3 = (TextView) findViewById(R.id.games3); 
	        		if (pl3.getName().equals("dummy")){
						pl3Name.setVisibility(TextView.GONE);
			    		scoreText3.setVisibility(TextView.GONE);		
			    		scoreBar3.setVisibility(ProgressBar.GONE);
	        			pl3Games.setVisibility(TextView.GONE);
	        		}else{
						pl3Name.setText(pl.getName());
			    		scoreText3.setText("" + pl.getScore());		
			    		scoreBar3.setProgress(pl.getScore());
	        			pl3Games.setText("" + pl.getGames());
		        		if (pl.getIsOut() == true){
		        			pl3Name.setTextColor(Color.GRAY);
		        			scoreText3.setTextColor(Color.GRAY);
	        				imgGames3.setBackgroundColor(Color.GRAY);
	        				Drawable drawable = scoreBar3.getProgressDrawable();
	        				drawable.setColorFilter(new LightingColorFilter(0xFF000000, 0xFF8c8a8c));
		        		}
	        		}	
	        		break;
	        case 4: TextView pl4Name = (TextView) findViewById(R.id.player4);
	        		ProgressBar scoreBar4 = (ProgressBar) findViewById(R.id.scoreBar4);
	        		TextView scoreText4 = (TextView) findViewById(R.id.scoreText4);
	        		TextView pl4Games = (TextView) findViewById(R.id.games4);
					TextView imgGames4 = (TextView) findViewById(R.id.games4); 
	        		if (pl4.getName().equals("dummy")){
						pl4Name.setVisibility(TextView.GONE);
			    		scoreText4.setVisibility(TextView.GONE);		
			    		scoreBar4.setVisibility(ProgressBar.GONE);
	        			pl4Games.setVisibility(TextView.GONE);
	    		}else{
		        		pl4Name.setText(pl.getName());;
			    		scoreText4.setText("" + pl.getScore());		
			    		scoreBar4.setProgress(pl.getScore());
	        			pl4Games.setText("" + pl.getGames());
		        		if (pl.getIsOut() == true){
		        			pl4Name.setTextColor(Color.GRAY);
		        			scoreText4.setTextColor(Color.GRAY);
	        				imgGames4.setBackgroundColor(Color.GRAY);
	        				Drawable drawable = scoreBar4.getProgressDrawable();
	        				drawable.setColorFilter(new LightingColorFilter(0xFF000000, 0xFF8c8a8c));
		        		}
	        		}
		            break;
	        case 5: TextView pl5Name = (TextView) findViewById(R.id.player5);
					ProgressBar scoreBar5 = (ProgressBar) findViewById(R.id.scoreBar5);
					TextView scoreText5 = (TextView) findViewById(R.id.scoreText5);
	        		TextView pl5Games = (TextView) findViewById(R.id.games5);
					TextView imgGames5 = (TextView) findViewById(R.id.games5); 
	        		if (pl5.getName().equals("dummy")){
						pl5Name.setVisibility(TextView.GONE);
			    		scoreText5.setVisibility(TextView.GONE);		
			    		scoreBar5.setVisibility(ProgressBar.GONE);
	        			pl5Games.setVisibility(TextView.GONE);
	        		}else{
						pl5Name.setText(pl.getName());;
			    		scoreText5.setText("" + pl.getScore());		
			    		scoreBar5.setProgress(pl.getScore());
	        			pl5Games.setText("" + pl.getGames());
		        		if (pl.getIsOut() == true){
		        			pl5Name.setTextColor(Color.GRAY);
		        			scoreText5.setTextColor(Color.GRAY);
	        				imgGames5.setBackgroundColor(Color.GRAY);
	        				Drawable drawable = scoreBar5.getProgressDrawable();
	        				drawable.setColorFilter(new LightingColorFilter(0xFF000000, 0xFF8c8a8c));
		        		}
	        		}
	        		break;
	        case 6: TextView pl6Name = (TextView) findViewById(R.id.player6);
					ProgressBar scoreBar6 = (ProgressBar) findViewById(R.id.scoreBar6);
					TextView scoreText6 = (TextView) findViewById(R.id.scoreText6);
	        		TextView pl6Games = (TextView) findViewById(R.id.games6);
					TextView imgGames6 = (TextView) findViewById(R.id.games6); 
	        		if (pl6.getName().equals("dummy")){
						pl6Name.setVisibility(TextView.GONE);
			    		scoreText6.setVisibility(TextView.GONE);		
			    		scoreBar6.setVisibility(ProgressBar.GONE);
	        			pl6Games.setVisibility(TextView.GONE);
	        		}else{
						pl6Name.setText(pl.getName());;
			    		scoreText6.setText("" + pl.getScore());		
			    		scoreBar6.setProgress(pl.getScore());
	        			pl6Games.setText("" + pl.getGames());
		        		if (pl.getIsOut() == true){
		        			pl6Name.setTextColor(Color.GRAY);
		        			scoreText6.setTextColor(Color.GRAY);
	        				imgGames6.setBackgroundColor(Color.GRAY);
	        				Drawable drawable = scoreBar6.getProgressDrawable();
	        				drawable.setColorFilter(new LightingColorFilter(0xFF000000, 0xFF8c8a8c));
		        		}
	        		}
	        		break;
	        case 7: TextView pl7Name = (TextView) findViewById(R.id.player7);
					ProgressBar scoreBar7 = (ProgressBar) findViewById(R.id.scoreBar7);
					TextView scoreText7 = (TextView) findViewById(R.id.scoreText7);
	        		TextView pl7Games = (TextView) findViewById(R.id.games7);
					TextView imgGames7 = (TextView) findViewById(R.id.games7); 
	        		if (pl7.getName().equals("dummy")){
						pl7Name.setVisibility(TextView.GONE);
			    		scoreText7.setVisibility(TextView.GONE);		
			    		scoreBar7.setVisibility(ProgressBar.GONE);
	        			pl7Games.setVisibility(TextView.GONE);
	        		}else{
						pl7Name.setText(pl.getName());;
			    		scoreText7.setText("" + pl.getScore());		
			    		scoreBar7.setProgress(pl.getScore());
	        			pl7Games.setText("" + pl.getGames());
		        		if (pl.getIsOut() == true){
		        			pl7Name.setTextColor(Color.GRAY);
		        			scoreText7.setTextColor(Color.GRAY);
	        				imgGames7.setBackgroundColor(Color.GRAY);
	        				Drawable drawable = scoreBar7.getProgressDrawable();
	        				drawable.setColorFilter(new LightingColorFilter(0xFF000000, 0xFF8c8a8c));
		        		}
	        		}
	        		break;
	        case 8: TextView pl8Name = (TextView) findViewById(R.id.player8);
					ProgressBar scoreBar8 = (ProgressBar) findViewById(R.id.scoreBar8);
					TextView scoreText8 = (TextView) findViewById(R.id.scoreText8);
	        		TextView pl8Games = (TextView) findViewById(R.id.games8);
					TextView imgGames8 = (TextView) findViewById(R.id.games8); 
	        		if (pl8.getName().equals("dummy")){
						pl8Name.setVisibility(TextView.GONE);
			    		scoreText8.setVisibility(TextView.GONE);		
			    		scoreBar8.setVisibility(ProgressBar.GONE);
	        			pl8Games.setVisibility(TextView.GONE);
	        		}else{
						pl8Name.setText(pl.getName());;
			    		scoreText8.setText("" + pl.getScore());		
			    		scoreBar8.setProgress(pl.getScore());
	        			pl8Games.setText("" + pl.getGames());
		        		if (pl.getIsOut() == true){
		        			pl8Name.setTextColor(Color.GRAY);
		        			scoreText8.setTextColor(Color.GRAY);
	        				imgGames8.setBackgroundColor(Color.GRAY);
	        				Drawable drawable = scoreBar8.getProgressDrawable();
	        				drawable.setColorFilter(new LightingColorFilter(0xFF000000, 0xFF8c8a8c));
		        		}
	        		}
	        		break;
	        case 9: TextView pl9Name = (TextView) findViewById(R.id.player9);
					ProgressBar scoreBar9 = (ProgressBar) findViewById(R.id.scoreBar9);
					TextView scoreText9 = (TextView) findViewById(R.id.scoreText9);
	        		TextView pl9Games = (TextView) findViewById(R.id.games9);
					TextView imgGames9 = (TextView) findViewById(R.id.games9); 
	        		if (pl9.getName().equals("dummy")){
						pl9Name.setVisibility(TextView.GONE);
			    		scoreText9.setVisibility(TextView.GONE);		
			    		scoreBar9.setVisibility(ProgressBar.GONE);
	        			pl9Games.setVisibility(TextView.GONE);
	        		}else{
						pl9Name.setText(pl.getName());;
			    		scoreText9.setText("" + pl.getScore());		
			    		scoreBar9.setProgress(pl.getScore());
	        			pl9Games.setText("" + pl.getGames());
		        		if (pl.getIsOut() == true){
		        			pl9Name.setTextColor(Color.GRAY);
		        			scoreText9.setTextColor(Color.GRAY);
	        				imgGames9.setBackgroundColor(Color.GRAY);
	        				Drawable drawable = scoreBar9.getProgressDrawable();
	        				drawable.setColorFilter(new LightingColorFilter(0xFF000000, 0xFF8c8a8c));
		        		}
	        		}
	        		break;
	        case 10: TextView pl10Name = (TextView) findViewById(R.id.player10);
					ProgressBar scoreBar10 = (ProgressBar) findViewById(R.id.scoreBar10);
					TextView scoreText10 = (TextView) findViewById(R.id.scoreText10);
	        		TextView pl10Games = (TextView) findViewById(R.id.games10);
					TextView imgGames10 = (TextView) findViewById(R.id.games10); 
	        		if (pl10.getName().equals("dummy")){
						pl10Name.setVisibility(TextView.GONE);
			    		scoreText10.setVisibility(TextView.GONE);		
			    		scoreBar10.setVisibility(ProgressBar.GONE);
	        			pl10Games.setVisibility(TextView.GONE);
	        		}else{
						pl10Name.setText(pl.getName());;
			    		scoreText10.setText("" + pl.getScore());		
			    		scoreBar10.setProgress(pl.getScore());
	        			pl10Games.setText("" + pl.getGames());
		        		if (pl.getIsOut() == true){
		        			pl10Name.setTextColor(Color.GRAY);
		        			scoreText10.setTextColor(Color.GRAY);
	        				imgGames10.setBackgroundColor(Color.GRAY);
	        				Drawable drawable = scoreBar10.getProgressDrawable();
	        				drawable.setColorFilter(new LightingColorFilter(0xFF000000, 0xFF8c8a8c));
		        		}
	        		}
	        		break;
	        		
	    }
	}

	
	
	
	public String retval(Integer Num){
		String stringx = "";
		return stringx;	
	}
	
	public void setColorsGames(){
		int[] allGames = {pl1.getGames(),pl2.getGames(),pl3.getGames(),pl4.getGames(),pl5.getGames(),pl6.getGames(),pl7.getGames(),pl8.getGames()};
		Arrays.sort(allGames);  
		int maxGames = allGames[allGames.length-1]; 
		TextView imgGames1 = (TextView) findViewById(R.id.games1); 
		if(pl1.getIsOut() == false){
			if(pl1.getGames() < maxGames){
				imgGames1.setBackgroundColor(Color.RED);
			}else{
				imgGames1.setBackgroundColor(Integer.parseInt("35d926", 16)+0xFF000000);
			}
		}	
		TextView imgGames2 = (TextView) findViewById(R.id.games2); 
		if(pl2.getIsOut() == false){
			if(pl2.getGames() < maxGames){
				imgGames2.setBackgroundColor(Color.RED);
			}else{
				imgGames2.setBackgroundColor(Integer.parseInt("35d926", 16)+0xFF000000);
			}
		}
		TextView imgGames3 = (TextView) findViewById(R.id.games3); 
		if(pl3.getIsOut() == false){
			if(pl3.getGames() < maxGames){
				imgGames3.setBackgroundColor(Color.RED);
			}else{
				imgGames3.setBackgroundColor(Integer.parseInt("35d926", 16)+0xFF000000);
			}
		}
		TextView imgGames4 = (TextView) findViewById(R.id.games4); 
		if(pl4.getIsOut() == false){
			if(pl4.getGames() < maxGames){
				imgGames4.setBackgroundColor(Color.RED);
			}else{
				imgGames4.setBackgroundColor(Integer.parseInt("35d926", 16)+0xFF000000);
			}
		}	
		TextView imgGames5 = (TextView) findViewById(R.id.games5); 
		if(pl5.getIsOut() == false){
			if(pl5.getGames() < maxGames){
				imgGames5.setBackgroundColor(Color.RED);
			}else{
				imgGames5.setBackgroundColor(Integer.parseInt("35d926", 16)+0xFF000000);
			}
		}	
		TextView imgGames6 = (TextView) findViewById(R.id.games6); 
		if(pl6.getIsOut() == false){
			if(pl6.getGames() < maxGames){
				imgGames6.setBackgroundColor(Color.RED);
			}else{
				imgGames6.setBackgroundColor(Integer.parseInt("35d926", 16)+0xFF000000);
			}
		}	
		TextView imgGames7 = (TextView) findViewById(R.id.games7); 
		if(pl7.getIsOut() == false){
			if(pl7.getGames() < maxGames){
				imgGames7.setBackgroundColor(Color.RED);
			}else{
				imgGames7.setBackgroundColor(Integer.parseInt("35d926", 16)+0xFF000000);
			}
		}
		TextView imgGames8 = (TextView) findViewById(R.id.games8); 
		if(pl8.getIsOut() == false){
			if(pl8.getGames() < maxGames){
				imgGames8.setBackgroundColor(Color.RED);
			}else{
				imgGames8.setBackgroundColor(Integer.parseInt("35d926", 16)+0xFF000000);
			}
		}
		TextView imgGames9 = (TextView) findViewById(R.id.games9); 
		if(pl9.getIsOut() == false){
			if(pl9.getGames() < maxGames){
				imgGames9.setBackgroundColor(Color.RED);
			}else{
				imgGames9.setBackgroundColor(Integer.parseInt("35d926", 16)+0xFF000000);
			}
		}
		TextView imgGames10 = (TextView) findViewById(R.id.games10); 
		if(pl10.getIsOut() == false){
			if(pl10.getGames() < maxGames){
				imgGames10.setBackgroundColor(Color.RED);
			}else{
				imgGames10.setBackgroundColor(Integer.parseInt("35d926", 16)+0xFF000000);
			}
		}

	}

	
	public void saveFile(String gameType){
		try {
	        File myFile = new File(destinationPath+"/"+fileName);
	        myFile.createNewFile();
	        FileOutputStream fOut = new FileOutputStream(myFile);
	        OutputStreamWriter myOutWriter = new OutputStreamWriter(fOut);
	        String txtData = gameType+"\n";
			if(gameType.equals("closed")){
				txtData=txtData+((numPlayers*Float.parseFloat(valueP))+","+valueP+",0\n");
			}else{
				txtData=txtData+valueP+"\n";
			}	
	        for (int b=0; b < numPlayers; b++) {
	    		txtData=txtData+effectivePl[b].getName()+","+effectivePl[b].getScore()+","+effectivePl[b].getIsOut()+","+effectivePl[b].getGames()+","+effectivePl[b].getReenter()+"\n";
	    	}			
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

	        txtData+="<h2>Jackpot: "+(numPlayers*Float.parseFloat(valueP))+" euro<h2><br>";
	        txtData+="<table cellpadding=0 cellspacing=0 style=\"height:100%; width:100%\">\n";
        	String bgcolor = "FFFFFF";
	        for (int b=0; b < numPlayers; b++) {
	        	if(bgcolor == "FFFFFF"){bgcolor = "EFEFEF";}else{bgcolor = "FFFFFF";}
	        	//if(((b/2) - Math.floor(b/2)) == 0){bgcolor = "000000";}{bgcolor = "FF0000";}
	        	String colorf;
	        	txtData+="<tr>\n";
	    		if(effectivePl[b].getIsOut() == true){
	    			colorf="bbbbbb";
		    		txtData+="<td bgcolor=\"#"+bgcolor+"\" valign=\"center\" align=\"center\"><h1><font color=\"#"+colorf+"\">OUT</font><h1></td>\n";
	    		}else{
	    			colorf="000000";
		    		txtData+="<td bgcolor=\"#"+bgcolor+"\" valign=\"center\" align=\"center\"><h1><font color=\"#"+colorf+"\"></font><h1></td>\n";
	    		}
	    		txtData+="<td bgcolor=\"#"+bgcolor+"\" valign=\"center\"><h1><font color=\"#"+colorf+"\">"+effectivePl[b].getName()+"</font><h1></td>\n";
	    		if(effectivePl[b].getIsOut() == true){
		    		txtData+="<td bgcolor=\"#"+bgcolor+"\" valign=\"center\"><h1><font color=\"#"+colorf+"\">"+effectivePl[b].getScore()+"</font></h1></td>\n";
	    		}else{
	    			if(effectivePl[b].getScore() > 150){
	    				txtData+="<td bgcolor=\"#"+bgcolor+"\" valign=\"center\"><h1><font color=\"#FF0000\">"+effectivePl[b].getScore()+"</font color></h1></td>\n";
	    			}else{
	    				txtData+="<td bgcolor=\"#"+bgcolor+"\" valign=\"center\"><h1><font color=\"#"+colorf+"\">"+effectivePl[b].getScore()+" ("+(150-effectivePl[b].getScore())+")</font></h1></td>\n";
	    			}
	    		}
	    	}			
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