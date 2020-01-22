package Helpers;

public class BasicTable {

	public static void printLine(int[] laengen) {
		String ausgabe = "+";
		for (int i = 0; i < laengen.length; i++) {
			for (int ii = 0; ii < laengen[i]; ii++) {
				ausgabe += "-";
			}
			ausgabe += "+";
		}

		System.out.println(ausgabe);

	}

	private static void printRow(String[] spalten, int[] laengen, String[] orientation) {
		String[] ausgabeArray = new String[spalten.length];
		String ausgabe = "";
		for (int i = 0; i < spalten.length; i++) {
			if (i == 0) {
				ausgabe = "|";
			} else {
				ausgabe = "";
			}
			switch (orientation[i]) {
				case "center":
					int firstI;
					int l = (laengen[i] - spalten[i].length()) / 2;
					for (firstI = 0; firstI < l; firstI++) {
						ausgabe+= " ";
					}

					ausgabe += spalten[i];
					for (int ii = firstI + spalten[i].length(); ii <laengen[i]; ii++) {
						ausgabe+= " ";
					}
					break;
				case "right":
					for (int ii = spalten[i].length(); ii < laengen[i] -1; ii++) {
						ausgabe+= " ";
					}
					ausgabe += spalten[i] + " ";
					break;
				default:
					ausgabe += " " + spalten[i];
					for (int ii = spalten[i].length(); ii < laengen[i] - 1; ii++) {
						ausgabe+= " ";
					}
					break;
			}

			ausgabeArray[i] = ausgabe + "|";
		}
		ausgabe = "";
		for (int i = 0; i < ausgabeArray.length; i++) {
			ausgabe += ausgabeArray[i];
		}
		System.out.println(ausgabe);
	}

	public static void generateTable(String[] spalten, String[][] zeilen, int[] laengen, String[] orientation) {
		printLine(laengen);
		printRow(spalten,laengen, orientation);
		printLine(laengen);
		for(String[] zeile: zeilen) {
			printRow(zeile, laengen, orientation);
		}
		printLine(laengen);
	}

}
