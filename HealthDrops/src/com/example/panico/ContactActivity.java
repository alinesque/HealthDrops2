package com.example.panico;

import java.io.File;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.healthdrops.R;
import com.example.modelclasses.ArchiveManager;

public class ContactActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_contact);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.contact, menu);
		return true;
	}
	
	public void salvarButton(View v){
		String filename = "contact_info";
		
		File dir = getFilesDir();
		File file = new File(dir,"contact_info");
		boolean deleted = file.delete();
		
		EditText nameEdit = (EditText) findViewById(R.id.name_contact_edittext);
		//Log.e("LOGTAG",nameEdit.toString());
		String name = nameEdit.getText().toString();
		
		EditText emailEdit = (EditText) findViewById(R.id.phone_contact_edittext);
		String email = emailEdit.getText().toString();
		
		ArchiveManager manager = new ArchiveManager(this);
		
		boolean escreveuNome = manager.escrever(filename, name );
		boolean escreveuEmail = manager.escrever(filename, email );
		
		if(escreveuNome && escreveuEmail) {
			TextView escreveuView = (TextView) findViewById(R.id.confirm_contact_textview);
			escreveuView.setText("Nova informação de contato salva");
		} else {
			TextView escreveuView = (TextView) findViewById(R.id.confirm_contact_textview);
			escreveuView.setText("Sua informação não foi salva");
		}
		
	}
	
	/*public void GPSClick(View v){
		//homeLocation = nowLocation;
		ArchiveManager manager = new ArchiveManager(this);
		manager.escrever("gps", input);
		Toast.makeText(this, "updated", Toast.LENGTH_SHORT).show();
	}*/
	
}
