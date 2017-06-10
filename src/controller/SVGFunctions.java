package controller;

import java.io.File;
import java.io.PrintWriter;
import java.util.Scanner;

public class SVGFunctions {

	public static int[] getFirstFourIntegers(String line) {
        String nums[] = (line.replaceAll("[^0-9\\s+]", "")).split("\\s+");
        int answer[] = new int[4];
        if(nums.length > 4) {
            for(int i = 0; i < answer.length; i++) {
                answer[i] = Integer.parseInt(nums[i]);
            }
        }
        return answer;
	}
	
	public static int[] getViewboxDimensions(File inputFile) {
        try {
            Scanner in = new Scanner(inputFile);
            String answer = "";
            while(in.hasNextLine()) {
                String input = in.nextLine();
                input = input.toLowerCase();
                String word = "viewbox";
                int len = word.length();
                if(input.contains(word)) {
                    for(int i = 0; i < input.length(); i++) {
                        if(input.charAt(i) == 'v') {
                            if(input.substring(i, i + len).equals("viewbox")) {
                                answer = input.substring(i);
                            }
                        }
                    }
                }
            }
            in.close();
            return getFirstFourIntegers(answer);
        } catch(Exception e) {

        }
        return new int[]{0, 0, 0, 0};
	}
	
	public static void replaceViewbox(File inputFile, String outputFileName, int size) {
	    try {
	        Scanner in = new Scanner(inputFile);
	        String output = "";
	        while(in.hasNextLine()) {
	            String input = in.nextLine();
	            output += input.replaceAll("viewBox=\".+?\"", "viewBox=\"0 0 " + size + " " + size + "\"") + "\n";
	        }
	        in.close();
	        PrintWriter out = new PrintWriter(new File(outputFileName));
	        out.println(output);
	        out.close();
	    } catch (Exception e) {
	
	    }
	}

}
