package com.example.modelclasses;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

import android.content.Context;

public class ArchiveManager {
	
	Context context;
	
	public ArchiveManager(Context inContext) {
		context = inContext;
	}
	
	public boolean escrever(String filename, String input) {	

		try {
		  
		  OutputStreamWriter outputStream = new OutputStreamWriter(context.getApplicationContext().openFileOutput(filename, Context.MODE_APPEND));
		  outputStream.write(input);
		  outputStream.write("DIV");//divisoria dos logs, ver se tem melhor separador, botar data, prever separador no texto
		  outputStream.close();
		  return true;
		}catch(Exception e) {
		  e.printStackTrace();
		  return false;
		}
	}
	
	public String ler(String filename) {
		String output = "";
		try {
			  InputStream inputStream = context.getApplicationContext().openFileInput(filename);
			  InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
			  BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
              String receiveString = "";
              StringBuilder stringBuilder = new StringBuilder();
               
              while((receiveString = bufferedReader.readLine()) != null) {
                  stringBuilder.append(receiveString);
              }
               
              inputStream.close();
              output = stringBuilder.toString();
              //divisoria dos logs, ver se tem melhor separador, botar data, prever separador no texto
			  return output;
			}catch(Exception e) {
			  e.printStackTrace();
			  return null;
			}		
	}
	
}
