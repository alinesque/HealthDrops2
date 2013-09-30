package com.example.diario;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import com.example.modelclasses.*;
import com.example.healthdrops.*;

public class DiarioActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_diario);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.diario, menu);
		return true;
	}
	
	public void botaoEscrever(View view) {	
		String filename = "logs";
		EditText log = (EditText) findViewById(R.id.log_diario_edittext);				//pegar texto digitado pelo usuário
		String input = log.getText().toString();
		
		ArchiveManager manager = new ArchiveManager(this);
		
		boolean escreveu = manager.escrever(filename, input);					//escrever entrada no arquivo "logs.txt"
		
		if(escreveu) {
			TextView escreveuView = (TextView) findViewById(R.id.confirm_diario_textview);		//sucesso!
			escreveuView.setText("Seu log foi salvo");
		}
		else {
			TextView escreveuView = (TextView) findViewById(R.id.confirm_diario_textview);     //fracasso
			escreveuView.setText("Seu log não foi salvo");
		}
		
 	}
	
	public void lerDiarioButton(View view) {
	   	Intent intent = new Intent(this, LerDiarioActivity.class);
		startActivity(intent);
	}
	
}
