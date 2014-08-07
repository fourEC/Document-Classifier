package documentclassifier;

import java.io.*;
import java.util.*;

public class Index {
    HashMap<Integer,Research_paper> hashmap = new HashMap();
    public void updateIndex()
    {
        //Get files from directory and create research papers object
        File f;
        String str;
        Research_paper paper = null;
        ArrayList<File> files = WindowsAPI.getFiles();
        ArrayList<Integer> frequency = new ArrayList();
	String[] fields= new String[4];
        Iterator<File> i = files.iterator();
        while(i.hasNext()){
		f = i.next();
                try{
                    fields=Pdf_Read.getDetails(f);
                    ArrayList<String> words_withduplicates = new ArrayList();
                    words_withduplicates = Pdf_Read.readContent(f.getAbsolutePath());
                    ArrayList<String> words_noduplicates = new ArrayList();
                    Iterator<String> itr = words_withduplicates.iterator();
                    
                    while(itr.hasNext()){
                        str = itr.next();
                        if(!words_noduplicates.contains(str))
                            words_noduplicates.add(str);
                            int count = 0;
                            //Get frequency and update frequency arraylist
                            for(int j=0;j<words_withduplicates.size();j++){
                                if(words_withduplicates.get(j).equals(str)){
                                    count++;
                                                       
                                }
                              
                            }
                            frequency.add(count);                            
                    }
                    
                    paper = new Research_paper(fields[0], fields[1], fields[2], fields[3],Pdf_Read.readContent(f.getAbsolutePath()),frequency);
                }
                catch(IOException e){
                    System.out.println("No file found");
                }
                addToIndex(paper);
	}
    }
    
    //to add research paper objects to index
    public void addToIndex(Research_paper r){
        if(checkIfPresent(r))
            System.out.println("Object already present in the index");
        else
            hashmap.put(r.keygen(), r);
    }
 
    public boolean checkIfPresent(Research_paper r){
        return hashmap.containsKey(r.keygen());
    }    

    public void delete(Research_paper r){
        hashmap.remove(r);
    }
    
    public boolean isEmpty(){
        return hashmap.isEmpty();
    }    
      
    //print the contents of all papers
    public void printIndex(){
        Iterator<Integer> keySetIterator = hashmap.keySet().iterator();
        Integer key = null;
            while(keySetIterator.hasNext())
            {
		  key = keySetIterator.next();
		  System.out.println("key: " + key + " value: " + hashmap.get(key).toString());
            }
    }
    
    //update index text file
    public void updateIndexTextFile(String filepath){
        Research_paper r ;
    	Iterator<Integer> keySetIterator = hashmap.keySet().iterator();
        Integer key;
        FileWriter fw = null;
        try{
            fw = new FileWriter(filepath);    
	    BufferedWriter bw = new BufferedWriter(fw);
            while(keySetIterator.hasNext()){
            	key = keySetIterator.next();
            	r = hashmap.get(key);
                bw.write(r.getName()+","+r.getAuthor()+","+r.getPublication()+","+r.getYear()+","+r.getClass_of_paper());
                bw.newLine();
            }
            bw.close();
        }
        catch(IOException e){
            System.out.println("File not present");
        }
    }
    
    public void print(){
        Set<Integer> keyset = hashmap.keySet();
        Iterator itr = keyset.iterator();
        Research_paper paper;
        while(itr.hasNext()){
            paper = (Research_paper) hashmap.get(itr.next());
            System.out.println(paper);
        }
    }
}
