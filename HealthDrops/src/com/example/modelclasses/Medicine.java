package com.example.modelclasses;

import java.util.ArrayList;

import android.content.Context;
import android.content.Intent;
import android.provider.AlarmClock;

public class Medicine {

	
	private String name;
	private int numDosisRestante, numPillsDose, numPillsCaixa;
	private Horario horaUltimaDose = new Horario();
	private Horario intervalDosis = new Horario();
	Context context;
	
	public Medicine(Context inContext, String inName, int inHoraIntervalDosis,int inMinIntervalDosis, int inNumDosisTotal, int inNumPillsDose, int inHoraDoseInicial, int inMinDoseInicial, int inNumPillsCaixa) {
		context = inContext;
		
		name = inName;
		intervalDosis.setHour(inHoraIntervalDosis);
		intervalDosis.setMinute(inMinIntervalDosis);
		numDosisRestante = inNumDosisTotal;
		numPillsDose = inNumPillsDose;
		horaUltimaDose.setHour(inHoraDoseInicial);
		horaUltimaDose.setMinute(inMinDoseInicial);
		numPillsCaixa = inNumPillsCaixa;
		
	}


	public void setAlarme() {
		//setar alarme efetivamente			
		String message = "Tomar " + numPillsDose + " comprimidos de " + name;
	
		Intent openNewAlarm = new Intent(AlarmClock.ACTION_SET_ALARM);
        openNewAlarm.putExtra(AlarmClock.EXTRA_HOUR, Horario.somarHora(intervalDosis.getHour(),horaUltimaDose.getHour()));
        openNewAlarm.putExtra(AlarmClock.EXTRA_MINUTES, Horario.somarMinute(intervalDosis.getMinute(), horaUltimaDose.getMinute()));
        openNewAlarm.putExtra(AlarmClock.EXTRA_MESSAGE, message);
        openNewAlarm.putExtra(AlarmClock.EXTRA_SKIP_UI, true);
        
        context.startActivity(openNewAlarm);
				
		horaUltimaDose.setMinute(Horario.somarMinute(horaUltimaDose.getMinute(), intervalDosis.getMinute()));
		if(horaUltimaDose.getMinute() + intervalDosis.getMinute() >= 60) {                  //se os minutos fazem passar de uma hora, aumentar a hora
			horaUltimaDose.setHour(Horario.somarHora(horaUltimaDose.getHour(), 1));
		} 
		horaUltimaDose.setHour(Horario.somarHora(horaUltimaDose.getHour(), intervalDosis.getHour()));
		
		numDosisRestante--;
	}
	
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getHoraIntervalDosis() {
		return intervalDosis.getHour();
	}

	public int getMinIntervalDosis() {
		return intervalDosis.getMinute();
	}
	
	public void setHoraIntervalDosis(int inIntervalDosis) {
		intervalDosis.setHour(inIntervalDosis);
	}
	
	public void setMinIntervalDosis(int inIntervalDosis) {
		intervalDosis.setMinute(inIntervalDosis);
	}

	public int getNumDosisTotal() {
		return numDosisRestante;
	}

	public void setNumDosisTotal(int numDosisTotal) {
		this.numDosisRestante = numDosisTotal;
	}

	public int getNumPillsDose() {
		return numPillsDose;
	}

	public void setNumPillsDose(int numPillsDose) {
		this.numPillsDose = numPillsDose;
	}

	public int getHoraUltimaDose() {
		return horaUltimaDose.getHour();
	}

	public void setHoraUltimaDose(int inHoraUltimaDose) {
		horaUltimaDose.setHour(inHoraUltimaDose);
	}
	
	public int getMinUltimaDose() {
		return horaUltimaDose.getMinute();
	}

	public void setMinUltimaDose(int inMinUltimaDose) {
		horaUltimaDose.setMinute(inMinUltimaDose);
	}

	public Context getContext() {
		return context;
	}

	public void setContext(Context context) {
		this.context = context;
	}


	public int getNumDosisRestante() {
		return numDosisRestante;
	}


	public Horario getIntervalDosis() {
		return intervalDosis;
	}


	public void setNumDosisRestante(int numDosisRestante) {
		this.numDosisRestante = numDosisRestante;
	}


	public void setHoraUltimaDose(Horario horaUltimaDose) {
		this.horaUltimaDose = horaUltimaDose;
	}


	public void setIntervalDosis(Horario intervalDosis) {
		this.intervalDosis = intervalDosis;
	}


	public int getNumPillsCaixa() {
		return numPillsCaixa;
	}


	public void setNumPillsCaixa(int numPillsCaixa) {
		this.numPillsCaixa = numPillsCaixa;
	}
	
}
