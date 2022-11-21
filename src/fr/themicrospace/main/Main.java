package fr.themicrospace.main;

public class Main {

	private static TheMicroSpace tms;
	
	public static void main(String[] args) {
		
		tms = new TheMicroSpace();
		tms.start();
	}
	
	public static TheMicroSpace getTms() {
		return tms;
	}
}