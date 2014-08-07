package documentclassifier;
import java.io.*;
import java.util.*;
import com.snowtide.pdf.*;

public class Pdf_Read{
    
    public static ArrayList<String> readContent(String pdf) throws IOException{ 
        
        //Get text from PDF file
        StringBuffer sb = WindowsAPI.extractPDF(pdf);
        
        //One time creation of stopwords object and punctuationList object
        stopwords instance = stopwords.getInstance(); 
        punctuationList pl = punctuationList.getInstance();
                
        //Prepare list of strings
        ArrayList<String> words = new ArrayList();
        String temp;
        StringTokenizer st = new StringTokenizer(sb.toString());
        while(st.hasMoreTokens()){
            temp = st.nextToken();
            if(temp.length()>=3){
                temp = temp.toLowerCase();
                if(!instance.sw.contains(temp) && !checkPunctuation(temp,pl.p) && temp.charAt(0)>='a' && temp.charAt(0)<='z')
                    words.add(temp);
            }
        }
        return words;
    }
    
    //Given a string and array list of punctuation returns true if punctuation exists else false
    public static boolean checkPunctuation(String temp,ArrayList<String> p){
        int len = temp.length(),i;
        String c;
        for(i=0;i<len;i++){
            c = temp.charAt(i) + "";            
            if(p.contains(c))
                return true;
        }
        return false;
    }
    
    //Get details of paper
    public static String[] getDetails(File f){
        String name = f.getName();
        int lastIndexDot = name.lastIndexOf('.');
        name = name.substring(0,lastIndexDot);
        String details[] = name.split("_");
        return details;
    }
}