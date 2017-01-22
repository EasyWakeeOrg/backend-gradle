package com.easywakee.entities;
//A class with attributes hour and minutes to handle the time
public class Time {
	private int hour;
	private int minute;
	
	public Time(){}
	
	public Time(int hour, int minute) {
		this.hour = hour;
		this.minute = minute;
	}
	public int getHour() {
		return hour;
	}
	public void setHour(int hour) {
		this.hour = hour;
	}
	public int getMinute() {
		return minute;
	}
	public void setMinute(int minute) {
		this.minute = minute;
	}
	
	//Substract a number of min to this time and rthis time to the new obtained Time
	public void substract(int min){
		while(min>59){
			if(this.hour==0){
				this.hour = 23;
			}
			else{
				this.hour --;
			}
			min -= 60;
		}
		if(min>this.minute){
			this.hour --;
			min -= this.minute;
			this.minute = 60 - min;
		}
		else{
			if(min!=0)
				this.minute -= min;
		}
	}
	
	@Override
	public String toString(){
		return ""+hour+":"+minute;
	}
}
