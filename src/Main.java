import java.io.BufferedReader;
import java.io.Console;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class Main {

	static int[] rotate(final int[] unOrderedArr, final int orderToRotate) {
		final int length = unOrderedArr.length;
		final int[] rotated = new int[length];
		for (int i = 0; i < length; i++) {
			rotated[(i + orderToRotate) % length] = unOrderedArr[i];
		}
		return rotated;
	}

	static char[] rotateChar(final char[] unOrderedArr, final int orderToRotate) {
		final int length = unOrderedArr.length;
		final char[] rotated = new char[length];
		for (int i = 0; i < length; i++) {
			rotated[(i + orderToRotate) % length] = unOrderedArr[i];
		}
		return rotated;
	}
	
	private static int znajdzDlugoscKluczaFriedman(String szyfrogram) {
		   int[] koincydencje = new int[szyfrogram.length()];
		   for(int i=1;i <szyfrogram.length();i++)
		      for (int j=0; j < szyfrogram.length(); j++)
		         if(szyfrogram.charAt(j) == szyfrogram.charAt((i+j)%szyfrogram.length()))
		            koincydencje[i]++;
		   int sred=0;
		   int odchyl=0;
		   for (int j=0; j < szyfrogram.length(); j++)
		      sred+=koincydencje[j];
		   sred/=szyfrogram.length();
		   for (int j=0; j < szyfrogram.length(); j++)
		      odchyl+=Math.abs(koincydencje[j]-sred);
		   odchyl/=szyfrogram.length();
		   for (int j=0; j < szyfrogram.length(); j++)
		      if(koincydencje[j] < sred + odchyl) return j;
		         return 0;
		}
	

	public static String odgadnijKlucz(String szyfrogram, int[][] czestosci, int[] czestosciPL, int dlKlucza){
		   String tmp = "";
		   for(int i=0;i <dlKlucza;i++){
		      int indexZminOdchyleniami=0;
		      int minOdch = Integer.MAX_VALUE;
		      for(int j=0;j<26;j++){
		         int sumaOdchylen = 0;
		         for(int c='a';c<='z';c++){
		            sumaOdchylen += Math.abs(czestosci[i][(c-'a'+j)%26] - czestosciPL[c-'a']);
		         }
		         if(sumaOdchylen < minOdch){
		            indexZminOdchyleniami = j;
		            minOdch = sumaOdchylen;
		         }
		      }
		      tmp += (char)('a'+indexZminOdchyleniami);
		   }
		   return tmp;
		}
	
	

	

	

	public static void main(String[] args) {
		double lastDiff = 99999999;
		String wholeText = "";
		File file = new File("tekst.txt");

		try {
			Scanner scanner = new Scanner(file);

			while (scanner.hasNext()) {
				String c = scanner.next();
				wholeText = wholeText + c;
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		int[] counters = new int[0x100];
		for (int i = 0; i < wholeText.length(); i++) {
			char c = wholeText.charAt(i);
			counters[(int) c]++;
		}

		int lenghtOfWholeText = wholeText.length();
		
		char[] wholeTextCharArray = wholeText.toCharArray();
		
		

		try {

			PrintWriter maxCounters = new PrintWriter("counters.txt");
			for (char c = 'a'; c <= 'z'; c++) {
				maxCounters.print(counters[(int) c] + "\t" + c + "\t");
				for (int i = 0; i < counters[(int) c]; i++) {
					maxCounters.print("*");
				}
				maxCounters.println();
			}
			maxCounters.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


		int[] frequency = new int[26];
		frequency[0] = 100;
		frequency[1] = 15;
		frequency[2] = 37;
		frequency[3] = 34;
		frequency[4] = 90;
		frequency[5] = 1;
		frequency[6] = 15;
		frequency[7] = 12;
		frequency[8] = 88;
		frequency[9] = 27;
		frequency[10] = 35;
		frequency[11] = 37;
		frequency[12] = 31;
		frequency[13] = 55;
		frequency[14] = 79;
		frequency[15] = 25;
		frequency[16] = 0;
		frequency[17] = 38;
		frequency[18] = 55;
		frequency[19] = 39;
		frequency[20] = 22;
		frequency[21] = 0;
		frequency[22] = 46;
		frequency[23] = 0;
		frequency[24] = 43;
		frequency[25] = 65;

		try {
			int it = 0;
			int it2 = 0;
			int minDiff = 0;

			int minimalOverall = 99999;
			PrintWriter maxCounters = new PrintWriter("frequencies.txt");
			maxCounters.println(wholeText);
			maxCounters.println();
			maxCounters.println("Metoda Friedmana: ");
			
			for(int iteration = 1; iteration < 22; iteration++) {
				int letterCounter = 0;
				char[] wholeTextMoved = rotateChar(wholeTextCharArray, iteration);
				
				for(int move = 0; move < wholeTextCharArray.length; move ++) {
					if(wholeTextCharArray[move] == wholeTextMoved[move]) {
						letterCounter = letterCounter + 1;
					}
				}
				maxCounters.println("Dla przesuniecia i = " + iteration + " zanotowano " + letterCounter + " koincydencji.");
				letterCounter = 0;
			}
			maxCounters.println();
			maxCounters.println("Zauwazamy, ze dla wielokrotnosci 5 (5,10,15,20) jest najwiecej koincydencji");
			maxCounters.println("Wynika z tego ze klucz ma dlugosc rowna 5!");
			maxCounters.println();
			
			char[] chars = new char[26];
			int index = 0;
			for (char c = 'a'; c <= 'z'; c++) {
				chars[index] = c;
				index++;
			}
			maxCounters.println("Wszystko podane w czestosciach");
			for (int y = 0; y < 25; y++) {
				double diff = 0;
				double avgDiff = 0;

				int[] ggg = rotate(frequency, y);
				char[] rotatedChars = rotateChar(chars, y);
				int b = 0;
				int g1 = 0, g2 = 0;
				maxCounters.println("Iteracja nr: " + y);
				for (char c = 'a'; c <= 'z'; c++) {
					double val1 = counters[(int) c];
					double val = (val1 / lenghtOfWholeText) * 1000;
					val = Math.floor(val);
					int mmm = (int) val;
					// maxCounters.print(c + " " + mmm + "\t" + ggg[b] + "\t");
					maxCounters.print(c + " " + mmm + "\t" + rotatedChars[b] + " " + ggg[b] + "\t");
					for (int i = 0; i < mmm; i += 3) {
						maxCounters.print("*");
						g1 = g1 + 1;
					}
					maxCounters.println("\t\t");
					maxCounters.print("\t\t");

					for (int a = 0; a < ggg[b]; a += 3) {
						maxCounters.print("*");
						g2 = g2 + 1;
					}

					diff = mmm - ggg[b];
					b = b + 1;
					if (diff < 0)
						diff *= -1;
					if (diff > minDiff) {
						minDiff = (int) diff;
					}
					avgDiff = avgDiff + diff;
					diff = 0;
					maxCounters.println();
					maxCounters.println();
				}
				maxCounters.println("Roznica srednia: " + avgDiff / 26);
				maxCounters.println("Roznica maksymalna: " + minDiff);
				if (minimalOverall > minDiff) {
					minimalOverall = minDiff;
					it2 = y;
				}
				minDiff = 0;
				if (lastDiff > avgDiff) {
					lastDiff = avgDiff;
					it = y;
				}
				avgDiff = 0;
				maxCounters.println();
				maxCounters.println();
			}

			maxCounters.println("Roznica srednia minimalna: " + lastDiff / 26);
			maxCounters.println("Indeks: " + it);

			maxCounters.println("Roznica pojedyncza maksymalna: " + minimalOverall);
			maxCounters.println("Indeks: " + it2);

			maxCounters.println();
			
			maxCounters.println("Teraz policze najczesciej pojawiajace sie slowa co najmniej 3 literowe:");

			Map<String, Integer> stringMap = new HashMap<String, Integer>();

			for (int p = 0; p < lenghtOfWholeText - 2; p++) {
				char myChar1 = wholeText.charAt(p);
				char myChar2 = wholeText.charAt(p + 1);
				char myChar3 = wholeText.charAt(p + 2);

				String myString = new StringBuilder().append(myChar1).append(myChar2).append(myChar3).toString();

				if (stringMap.containsKey(myString)) {
					int cnt = stringMap.get(myString);
					stringMap.put(myString, ++cnt);
				}

				else {
					stringMap.put(myString, 1);
				}

			}

			for (Map.Entry<String, Integer> entry : stringMap.entrySet()) {
				if (entry.getValue() > 2)
					maxCounters.println(entry.getKey() + " " + entry.getValue());
			}

			Map<String, Integer> stringMap2 = new HashMap<String, Integer>();

			for (int p = 0; p < lenghtOfWholeText - 3; p++) {
				char myChar1 = wholeText.charAt(p);
				char myChar2 = wholeText.charAt(p + 1);
				char myChar3 = wholeText.charAt(p + 2);
				char myChar4 = wholeText.charAt(p + 3);

				String myString = new StringBuilder().append(myChar1).append(myChar2).append(myChar3).append(myChar4).toString();

				if (stringMap2.containsKey(myString)) {
					int cnt = stringMap2.get(myString);
					stringMap2.put(myString, ++cnt);
				}

				else {
					stringMap2.put(myString, 1);
				}

			}

			for (Map.Entry<String, Integer> entry : stringMap2.entrySet()) {
				if (entry.getValue() > 2)
					maxCounters.println(entry.getKey() + " " + entry.getValue());
			}
			
			maxCounters.println();
			
			maxCounters.println("Kandydaci na litery 5-literowego klucza: ");
			maxCounters.println("Iteracja nr 0, Iteracja nr. 8, Iteracja nr. 15, Iteracja nr. 17, Iteracja nr. 19");
			maxCounters.println("Jesli chodzi o iteracje nr. 0 to tekst bedzie zaczynal sie od slowa na Y, wiec prawdopodobnie odpada");
			maxCounters.println("Jesli chodzi o iteracje nr. 8 to tekst bedzie zaczynal sie od slowa na Q, wiec prawdopodobnie odpada");
			maxCounters.println("Jesli chodzi o iteracje nr. 15 to tekst bedzie zaczynal sie od slowa na J");
			maxCounters.println("Jesli chodzi o iteracje nr. 17 to tekst bedzie zaczynal sie od slowa na H");
			maxCounters.println("Jesli chodzi o iteracje nr. 19 to tekst bedzie zaczynal sie od slowa na F");
			maxCounters.println();
			maxCounters.println("Mamy 3 kandydatow na pierwsza litere hasla");
			maxCounters.println();
			maxCounters.println("Jesli chodzi o druga litere tekstu mamy zaszyfrowana M");
			maxCounters.println("Jesli chodzi o iteracje nr. 0 to tekst bedzie mial druga litere na M. JM, HM, FM nie ma takich slow. Odrzucamy");
			maxCounters.println("Jesli chodzi o iteracje nr. 8 to tekst bedzie mial druga litere na E. JE, HE, FE istnieja poczatki slow. Dobry kandydat na druga litere hasla");
			maxCounters.println("Jesli chodzi o iteracje nr. 15 to tekst bedzie mial druga litere na X. JX, HX, FX nie ma takich slow. Odrzucamy");
			maxCounters.println("Jesli chodzi o iteracje nr. 17 to tekst bedzie mial druga litere na V. JV, HV, FV nie ma takich slow. Odrzucamy");
			maxCounters.println("Jesli chodzi o iteracje nr. 19 to tekst bedzie mial druga litere na T. JT, HT, FT nie ma takich slow. Odrzucamy");
			maxCounters.println();
			maxCounters.println("Stad wiemy ze iteracja nr 8 bedzie druga litera hasla. Jest to litera I");
			maxCounters.println("Popatrzmy na kandydatow na 1 litere hasla: 15 (P), 17 (R), 19 (T). Obecnie haslo moze sie zaczynac od tych liter");
			maxCounters.println();
			maxCounters.println("Jesli chodzi o trzecia litere tekstu mamy zaszyfrowane J");
			maxCounters.println("Jesli chodzi o iteracje nr. 0 to tekst bedzie mial trzecia litere na J. JEJ, HEJ, FEJ istnieja poczatki slow procz FEJ");
			maxCounters.println("Jesli chodzi o iteracje nr. 15 to tekst bedzie mial trzecia litere na U. JEU, HEU, FEU nie istnieja poczatki slow. Odrzucamy");
			maxCounters.println("Jesli chodzi o iteracje nr. 17 to tekst bedzie mial trzecia litere na S. JES, HES, FES istnieja poczatki slow");
			maxCounters.println("Jesli chodzi o iteracje nr. 19 to tekst bedzie mial trzecia litere na Q. JEQ, HEQ, FEQ nie istnieja poczatki slow. Odrzucamy");
			maxCounters.println();
			maxCounters.println("Jesli chodzi o czwarta litere tekstu mamy zaszyfrowane L");
			maxCounters.println("Jesli chodzi o iteracje nr. 0 to tekst bedzie mial czwarta litere na L. JEJL, HEJL, JESL, FESL, HESL istnieja JESL (jesli)");
			maxCounters.println("Jesli chodzi o iteracje nr. 15 to tekst bedzie mial czwarta litere na W. JEJW, HEJW, JESW, FESW, HESW odrzucamy");
			maxCounters.println("Jesli chodzi o iteracje nr. 17 to tekst bedzie mial czwarta litere na U. JEJU, HEJU, JESU, FESU, HESU wszystkie sa okej. Dobry kandydat");
			maxCounters.println("Jesli chodzi o iteracje nr. 19 to tekst bedzie mial czwarta litere na T. JEJT, HEJT, JEST, FEST, HEST niektore sa okej");
			maxCounters.println();
			maxCounters.println("Jesli chodzi o piata litere tekstu mamy zaszyfrowane B");
			maxCounters.println("Jesli chodzi o iteracje nr. 0 to tekst bedzie mial piata litere na B. JESLB, HEJTB, FESTB, HESTB, FESUB, HEJUB, JEJUB nic nie jest ok ");
			maxCounters.println("Jesli chodzi o iteracje nr. 15 to tekst bedzie mial piata litere na M. JESLM, HEJTM, FESTM, HESTM, FESUM, HEJUM, JEJUM nic nie jest ok ");
			maxCounters.println("Jesli chodzi o iteracje nr. 17 to tekst bedzie mial piata litere na K. JESLK, HEJTK, FESTK, HESTK, FESUK, HEJUK, JEJUM nic nie jest ok ");
			maxCounters.println("Jesli chodzi o iteracje nr. 19 to tekst bedzie mial piata litere na I. JESLI, HEJTI, FESTI, HESTI, FESUI, HEJUI, JEJUI. Slowo JESLI jest jedynym ktore jest poprawne. To jest jedyna sluszna droga");
			maxCounters.println();
			maxCounters.println("Droga dedukcji musimy uzyskac slowo JESLI.");
			maxCounters.println("Naszym haslem bedzie wtedy PIRAT. Udalo nam sie odgadnac 5-literowe, polskie haslo");
			maxCounters.println();
			maxCounters.println("Teraz przetlumaczmy tekst");
			maxCounters.println();
			String decryptedText = decrypt(wholeText, "pirat");
			maxCounters.println(decryptedText);
			maxCounters.println();
			maxCounters.println("A teraz napiszmy go po polsku: ");
			maxCounters.println();
			maxCounters.println("Jeśli chodzi o władców ludzie błądzą i zajmują dwa stanowiska.");
			maxCounters.println("Niektórzy z nich myślą, że wszyscy ludzie powinni być równi i że nie powinno być wsród nich być ani władcy ani poddanego.");
			maxCounters.println("Nie wiedzą jednak, że oznacza to unicestwienie władcy i sprawiedliwości jednocześnie, bo nie ma sprawiedligości wsród ludu inaczej niż przez władcę.");
			maxCounters.println("Niektórzy sądzą, że nie ma tu znaczenia, jeśli władca jest odrażający i nie podporządkowuje się prawu.");
			maxCounters.println("To oczywiste zepsucie. Od ciebie zależy, by twoje rządy były rządami sprawiedliwości, a nie od tego, co odpychające");
			maxCounters.println("Nie dawaj wiary temu, kto chciałby cię do tego popchnąć i przedstawiał ci takie rządy w pięknych barwach");
			
			
			
			
			maxCounters.println();
			maxCounters.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

	}
	
	public static String format(String text) {
		return text.toUpperCase().replaceAll("[^\\p{L}]", "");
	}
	
	public static String decrypt(String ciphertext, String keyword) {
		ciphertext = format(ciphertext);
		keyword = format(keyword);
		String plaintext = "";

		for (int i = 0; i < ciphertext.length(); i++) {
			int cipherChar = ciphertext.charAt(i) - 'A';
			int keyChar = keyword.charAt(i % keyword.length()) - 'A';
			int plainChar = ((cipherChar - keyChar) % 26);
			if (plainChar < 0) {
				plainChar += 26;
			}
			plainChar += 'A';
			plaintext += (char) plainChar;
		}

		return plaintext;
	}

}
