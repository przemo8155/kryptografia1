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
import java.util.List;
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
			char[] chars = new char[26];
			int index = 0;
			for (char c = 'a'; c <= 'z'; c++) {
				chars[index] = c;
				index++;
			}
			maxCounters.println("Wszystko podane w czestosciach");
			for (int y = 1; y < 25; y++) {
				double diff = 0;
				double avgDiff = 0;
				
				
				
				int[] ggg = rotate(frequency, y);
				char[] rotatedChars = rotateChar(chars, y);
				int b = 0;
				int g1 = 0, g2 = 0;
				maxCounters.println("Iteracja nr: " + y);
				for (char c = 'a'; c <= 'z'; c++) {
					double val1 = counters[(int)c];
					double val = (val1/lenghtOfWholeText) * 1000;
					val = Math.floor(val);
					int mmm = (int)val;  
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
					if(diff < 0) diff *= -1;
					if(diff > minDiff) {
						minDiff = (int) diff;
					}
					avgDiff = avgDiff + diff;
					diff = 0;
					maxCounters.println();
					maxCounters.println();
				}
				maxCounters.println("Roznica srednia: " + avgDiff/26);
				maxCounters.println("Roznica maksymalna: " + minDiff);
				if(minimalOverall > minDiff) {
					minimalOverall = minDiff;
					it2 = y;
				}
				minDiff = 0;
				if(lastDiff > avgDiff) {
					lastDiff = avgDiff;
					it = y;
				}
				avgDiff = 0;
				maxCounters.println();
				maxCounters.println();
			}

			maxCounters.println("Roznica srednia minimalna: " + lastDiff/26);
			maxCounters.println("Indeks: " + it);
			
			maxCounters.println("Roznica pojedyncza maksymalna: " + minimalOverall);
			maxCounters.println("Indeks: " + it2);
			maxCounters.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

	}

}
