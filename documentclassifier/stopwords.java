package documentclassifier;

import java.io.*;
import java.util.*;

//Singleton pattern for stopwords since it has to be instantiated just once
public class stopwords {
    ArrayList<String> sw;
    private static stopwords instance = null;
    private stopwords(){
        Scanner in=null;
        String temp;
        sw = new ArrayList<>();
        try{
            in = new Scanner(new FileReader("D://stopwords.txt"));
            while(in.hasNext()){
                temp = in.next();
                if(!sw.contains(temp))                
                  sw.add(temp);
            }
        }
        catch(FileNotFoundException e){
            System.out.println(e);
        }
       
    }
    public static synchronized stopwords getInstance(){
        if(null==instance)
            instance = new stopwords();
        return instance;        
    }
    
    @Override
    protected Object clone() throws CloneNotSupportedException {
        throw new CloneNotSupportedException("Clone is not allowed.");
    }
}