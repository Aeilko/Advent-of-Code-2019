package utils;

public class AoC {

	public static void main(String[] args){
		stringLinesToArray();
	}

	public static void stringLinesToArray(){
		String[] in = LinesToArray.split("\n");
		String res = String.join("\",\"", in);
		System.out.println("{\"" + res + "\"}");
	}

	public static final String LinesToArray = "";
}
