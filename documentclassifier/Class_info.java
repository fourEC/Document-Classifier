package documentclassifier;

import java.util.ArrayList;
import java.io.*;
import java.util.Scanner;

public class Class_info {
    public String classname;
    public ArrayList<String> keywords;
    private String path;
    private static int index = -1;
    public Class_info(String name, String path){
        keywords = new ArrayList();
        this.classname = name;
        this.path = path;
        index++;
        
        Scanner in=null;
        String temp;
        try{
            in = new Scanner(new FileReader(path));
            while(in.hasNext()){
                temp = in.next();
                if(!keywords.contains(temp))                
                  keywords.add(temp);
            }
        }
        catch(FileNotFoundException e){
            System.out.println(e);
        }
       
    }
    public ArrayList<String> getKeywords(){
        return keywords;
    }
    
    public int getIndex(){
        return index;
    }
    public int search(String keyword){
        if(keywords.contains(keyword)){
            return 1;
        }
        return 0;
    }
    public String getClassName(){
        return classname;
        
    }  
    
}
