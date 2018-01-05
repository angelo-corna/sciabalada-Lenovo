package com.trentanof.software.sciabalada;

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

public class TavoloScopa extends Activity {

	int AngeloCo = 0;
	int MauroKarmen = 0;
	int RenzoKatia = 0;
	String message = "";

	public final static String EXTRA_MESSAGE = "com.trentanof.software.sciabalada.MESSAGE";
	final Context context = this;

	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_tavolo_scopa);
	}

	/*
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.tavolo_scopa, menu);
		return true;
	}
	*/

	/** Called when the user clicks the AngeloCo image */
	public void setAngeloCo(View v) {
		ImageView img= (ImageView) findViewById(R.id.angeloCo);
		if (AngeloCo == 0)
			{img.setImageResource(R.drawable.angeloco1); AngeloCo = 1;}
		else
			{img.setImageResource(R.drawable.angeloco0); AngeloCo = 0;}
	}	

	/** Called when the user clicks the MauroKarmen image */
	public void setMauroKarmen(View v) {
		ImageView img= (ImageView) findViewById(R.id.mauroKarmen);
		if (MauroKarmen == 0)
			{img.setImageResource(R.drawable.maurokarmen1); MauroKarmen = 1;}
		else
			{img.setImageResource(R.drawable.maurokarmen0); MauroKarmen = 0;}
	}	

	/** Called when the user clicks the RenzoKatia image */
	public void setRenzoKatia(View v) {
		ImageView img= (ImageView) findViewById(R.id.renzoKatia);
		if (RenzoKatia == 0)
			{img.setImageResource(R.drawable.renzokatia1); RenzoKatia = 1;}
		else
			{img.setImageResource(R.drawable.renzokatia0); RenzoKatia = 0;}
	}	
	
	/** Called when the user clicks the play button */
	public void play(View v) {
		
		EditText editTextp = (EditText) findViewById(R.id.valuePuntata);
		String valueP = editTextp.getText().toString();

		int numPlayers = AngeloCo + MauroKarmen + RenzoKatia;

		if (valueP.equals("") || numPlayers != 2) {
			AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
			alertDialogBuilder.setTitle("Attenzione");
			alertDialogBuilder
				.setMessage("Inserire il valore della puntata e selezionare due coppie")
				.setCancelable(false)
				.setNegativeButton("OK",new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog,int id) {
						dialog.cancel();
					}
				});
			AlertDialog alertDialog = alertDialogBuilder.create();
			alertDialog.show();
		}else{
			Intent intent = new Intent(this, PartitaScopa.class);
			String message = "new#"+valueP;
			if (AngeloCo == 1)message=message+"#Angelo & Co";
			if (MauroKarmen == 1)message=message+"#Mauro & Karmen";
			if (RenzoKatia == 1)message=message+"#Renzo & Katia";
			//message=message.substring(1,message.length());
			intent.putExtra(EXTRA_MESSAGE, message);
			startActivity(intent);
		}
	}	
}
