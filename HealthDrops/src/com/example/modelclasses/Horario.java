package com.example.modelclasses;

public class Horario {
	private int hour,minute;
	
	public static int somarHora(int hora, int hora2) {
		if((hora + hora2) <= 24) {
			return hora + hora2;
		}
		else {
			return hora + hora2 - 24;
		}
	}
	
	public static int somarMinute(int min, int min2) {
		if((min + min2) <= 60) {
			return min + min2;
		}
		else {
			return min + min2 - 60;
		}
	}
	
	public int getHour() {
		return hour;
	}

	public void setHour(int inHour) {
		if(inHour >= 0 && inHour <=23 ) hour = inHour;
	}

	public int getMinute() {
		return minute;
	}

	public void setMinute(int inMinute) {
		if(inMinute >= 0 && inMinute <=59 ) minute = inMinute;
	}
		
}
