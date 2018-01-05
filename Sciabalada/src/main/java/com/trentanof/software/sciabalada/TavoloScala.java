package com.trentanof.software.sciabalada;

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

public class TavoloScala extends Activity {

	
	int Angelo = 0;
	int Co = 0;
	int Guest1 = 0;
	int Karmen = 0;
	int Katia = 0;
	int Mario = 0;
	int Mauro = 0;
	int Renzo = 0;
	int Guest3 = 0;
	int Guest2 = 0;
	String message = "";
	
	public final static String EXTRA_MESSAGE = "com.trentanof.software.sciabalada.MESSAGE";
	final Context context = this;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_tavolo_scala);
		// Get the message from the intent
		Intent intent = getIntent();
		message = intent.getStringExtra(TavoloScala.EXTRA_MESSAGE);
		EditText editTextr = (EditText) findViewById(R.id.valueRientri);
		TextView rientri = (TextView) findViewById(R.id.rientri);
		if(message.equals("scala40QD")){editTextr.setEnabled(false);rientri.setVisibility(View.GONE);editTextr.setVisibility(View.GONE);}
	}

	/* @Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.tavolo_scala, menu);
		return true;
	}
	*/


	
	/** Called when the user clicks the Angelo image */
	public void setAngelo(View v) {
		ImageView img= (ImageView) findViewById(R.id.angelo);
		if (Angelo == 0)
			{img.setImageResource(R.drawable.angelo1); Angelo = 1;}
		else
			{img.setImageResource(R.drawable.angelo0); Angelo = 0;}
	}	

	/** Called when the user clicks the Co image */
	public void setCo(View v) {
		ImageView img= (ImageView) findViewById(R.id.co);
		if (Co == 0)
			{img.setImageResource(R.drawable.co1); Co = 1;}
		else
			{img.setImageResource(R.drawable.co0); Co = 0;}
	}	

	/** Called when the user clicks the guest1 image */
	public void setGuest1(View v) {
		ImageView img= (ImageView) findViewById(R.id.guest1);
		if (Guest1 == 0)
			{img.setImageResource(R.drawable.guest11); Guest1 = 1;}
		else
			{img.setImageResource(R.drawable.guest10); Guest1 = 0;}
	}	
	
	/** Called when the user clicks the karmen image */
	public void setKarmen(View v) {
		ImageView img= (ImageView) findViewById(R.id.karmen);
		if (Karmen == 0)
			{img.setImageResource(R.drawable.karmen1); Karmen = 1;}
		else
			{img.setImageResource(R.drawable.karmen0); Karmen = 0;}
	}	
	
	/** Called when the user clicks the katia image */
	public void setKatia(View v) {
		ImageView img= (ImageView) findViewById(R.id.katia);
		if (Katia == 0)
			{img.setImageResource(R.drawable.katia1); Katia = 1;}
		else
			{img.setImageResource(R.drawable.katia0); Katia = 0;}
	}
	
	/** Called when the user clicks the mario image */
	public void setMario(View v) {
		ImageView img= (ImageView) findViewById(R.id.mario);
		if (Mario == 0)
			{img.setImageResource(R.drawable.mario1); Mario = 1;}
		else
			{img.setImageResource(R.drawable.mario0); Mario = 0;}
	}	
	
	/** Called when the user clicks the mauro image */
	public void setMauro(View v) {
		ImageView img= (ImageView) findViewById(R.id.mauro);
		if (Mauro == 0)
			{img.setImageResource(R.drawable.mauro1); Mauro = 1;}
		else
			{img.setImageResource(R.drawable.mauro0); Mauro = 0;}
	}	
	
	/** Called when the user clicks the renzo image */
	public void setRenzo(View v) {
		ImageView img= (ImageView) findViewById(R.id.renzo);
		if (Renzo == 0)
			{img.setImageResource(R.drawable.renzo1); Renzo = 1;}
		else
			{img.setImageResource(R.drawable.renzo0); Renzo = 0;}
	}	
	
	/** Called when the user clicks the guest3 image */
	public void setGuest3(View v) {
		ImageView img= (ImageView) findViewById(R.id.guest3);
		if (Guest3 == 0)
			{img.setImageResource(R.drawable.guest31); Guest3 = 1;}
		else
			{img.setImageResource(R.drawable.guest30); Guest3 = 0;}
	}	

	/** Called when the user clicks the guest2 image */
	public void setGuest2(View v) {
		ImageView img= (ImageView) findViewById(R.id.guest2);
		if (Guest2 == 0)
			{img.setImageResource(R.drawable.guest21); Guest2 = 1;}
		else
			{img.setImageResource(R.drawable.guest20); Guest2 = 0;}
	}
	
/** Called when the user clicks the play button */
	public void play(View v) {
		
		EditText editTextp = (EditText) findViewById(R.id.valuePuntata);
		String valueP = editTextp.getText().toString();

		EditText editTextr = (EditText) findViewById(R.id.valueRientri);
		String valueR = editTextr.getText().toString();
		
		int numPlayers = Angelo + Co + Guest1 + Karmen + Katia + Mario + Mauro + Renzo;

		if (message.equals("scala40")){
			if (valueP.equals("") || valueR.equals("") || numPlayers < 2) {
				AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
				alertDialogBuilder.setTitle("Attenzione");
				alertDialogBuilder
					.setMessage("Inserire il valore della puntata e del rientro e selezionare almeno due giocatori")
					.setCancelable(false)
					.setNegativeButton("OK",new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog,int id) {
							dialog.cancel();
						}
					});
				AlertDialog alertDialog = alertDialogBuilder.create();
				alertDialog.show();
			}else{
				Intent intent = new Intent(this, PartitaScala.class);
				String message = "new#"+valueP+","+valueR;
				if (Angelo == 1)message=message+"#Angelo";
				if (Co == 1)message=message+"#Co";
				if (Guest1 == 1)message=message+"#Guest1";
				if (Guest2 == 1)message=message+"#Guest2";
				if (Karmen == 1)message=message+"#Karmen";
				if (Katia == 1)message=message+"#Katia";
				if (Guest3 == 1)message=message+"#Guest3";
				if (Mario == 1)message=message+"#Mario";
				if (Mauro == 1)message=message+"#Mauro";
				if (Renzo == 1)message=message+"#Renzo";
				//message=message.substring(1,message.length());
				intent.putExtra(EXTRA_MESSAGE, message);
				startActivity(intent);
			}
		}else{
			if (valueP.equals("") || numPlayers < 2) {
				AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
				alertDialogBuilder.setTitle("Attenzione");
				alertDialogBuilder
					.setMessage("Inserire il valore della puntata e selezionare almeno due giocatori")
					.setCancelable(false)
					.setNegativeButton("OK",new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog,int id) {
							dialog.cancel();
						}
					});
				AlertDialog alertDialog = alertDialogBuilder.create();
				alertDialog.show();
			}else{
				Intent intent = new Intent(this, PartitaScala40QD.class);
				String message = "new#"+valueP;
				if (Angelo == 1)message=message+"#Angelo";
				if (Co == 1)message=message+"#Co";
				if (Guest1 == 1)message=message+"#Guest1";
				if (Guest2 == 1)message=message+"#Guest2";
				if (Karmen == 1)message=message+"#Karmen";
				if (Katia == 1)message=message+"#Katia";
				if (Guest3 == 1)message=message+"#Guest3";
				if (Mario == 1)message=message+"#Mario";
				if (Mauro == 1)message=message+"#Mauro";
				if (Renzo == 1)message=message+"#Renzo";
				//System.out.println(message);
				//message=message.substring(1,message.length());
				intent.putExtra(EXTRA_MESSAGE, message);
				startActivity(intent);
			}
		}	
	}	
}
