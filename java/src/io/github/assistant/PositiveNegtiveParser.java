package io.github.assistant;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;

/**
 * read positive and negative word to hash map
 * @author zhihan
 *
 */
public class PositiveNegtiveParser {
	
	private HashMap positiveWords = new HashMap();
	private HashMap negativeWords = new HashMap();
	
	public PositiveNegtiveParser(){
		this.readPos();
		this.readNeg();
	}
	
	public void readPos(){
		File file = new File(VariableMapping.POS_WORDS_FILE_ADDRESS);
		FileReader reader;
		try {
			reader = new FileReader(file);
			 BufferedReader br = new BufferedReader(reader);
			 while(br.readLine()!=null){
				positiveWords.put(br.readLine(),true);
			 }
			 
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
       
	}
	public void readNeg(){
		
		File file = new File(VariableMapping.NEG_WORDS_FILE_ADDRESS);
		FileReader reader;
		try {
			reader = new FileReader(file);
			 BufferedReader br = new BufferedReader(reader);
			 while(br.readLine()!=null){
				negativeWords.put(br.readLine(),true);
			 }
			 
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	}
	
	public boolean isPositiveWord(String word){
		if(this.positiveWords.containsKey(word)){
			return true;
		}
		else{
			return false;
		}
	}
	
	public boolean isNegativeWord(String word){
		if(this.negativeWords.containsKey(word)) 
		{return true;}
		else{ return false;}
		}
	}


