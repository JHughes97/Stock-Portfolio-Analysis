/*
 * Class for reading from and writing to files
 * Include 2 methods
 * 1 method for reading from files
 * 1 method for writing to files
 */

import java.io.*;

public class FileIO {

	/**
	 * Method for reading from files
	 * @param fileName
	 * @return stringArray
	 */
    public String[] load(String f){
    	File file=new File(f);
    	StringBuffer contents=new StringBuffer();
    	BufferedReader input=null;

    	try{
    		input=new BufferedReader(new FileReader(file));
    		String line=null;
    		int i=0;
    		while((line=input.readLine())!=null){
    			contents.append(line);
    			i++;
    			contents.append(System.getProperty("line.separator"));
    		}
    	}
    	catch(FileNotFoundException e){
    		System.out.println("Unable to find file.");
    		e.printStackTrace();
    	}
    	catch(IOException e){
    		System.out.println("IO exception when processing file");
    		e.printStackTrace();
    	}
    	finally{
    		try{
    			if(input!=null){
    				input.close();
    			}
    		}
    		catch(IOException e){
    			System.out.println("IO exception when processing file");
    		}
    	}

    	String[] array=contents.toString().split("\n");
    	for(String s:array)
    		s.trim();
    	return array;
    }

	/**
	 * Method for writing to files
	 * @param fileName stringArray
	 */
    public void save(String f,String[] array) throws FileNotFoundException, IOException{
    	File file=new File(f);
    	Writer output=null;
    	try{
    		output=new BufferedWriter(new FileWriter(file));
    		for(int i=0;i<array.length;i++){
    			output.write(array[i]);
    			output.write(System.getProperty("line.separator"));
    		}
    	}
    	finally{
    		if(output!=null)
    			output.close();
    	}
    }

}