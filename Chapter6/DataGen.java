package Charpter6;
import java.io.*;

public class DataGen {
	
	public static void main(String[] args) {
		
		int num = 5000000;
		int b;
		String dir;
		FileWriter fos = null;
		BufferedWriter bos = null;
		
		try {
			dir = "E:\\JavaWorkspace\\AlgorithmsIntroduce\\src\\Charpter6\\DataSet.dat";
			fos = new FileWriter(dir);
			bos = new BufferedWriter(fos);
		} catch(IOException e) {
//			e.printStackTrace();
			System.out.println("File Not Found");
			System.exit(-1);
		}
		
		try {
			for(int i=0; i<num; i++) {
				b = (int)Math.round(Math.random() * 10000);
				bos.write(String.valueOf(b));
				bos.newLine();
			}
			bos.close();
		} catch(IOException e) {
//			e.printStackTrace();
			System.out.println("File writes error");
			System.exit(-1);
		}
		
		System.out.println("File Writes Successfully");
		
	}

}