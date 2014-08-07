package documentclassifier;

import com.snowtide.pdf.*;
import java.io.*;
import java.util.*;

public class WindowsAPI {
  
   //Extract contents of PDF 
   public static StringBuffer extractPDF(String pdf) throws IOException{
       PDFTextStream stream = new PDFTextStream(pdf);
       StringBuffer sb = new StringBuffer(10000);
       OutputTarget tgt = OutputTarget.forBuffer(sb);
       stream.pipe(tgt);
       stream.close();          
       return sb; 
   }
   
   //Get Files from given directory
   public static ArrayList<File> getFiles(){
       File temp;
       ArrayList<File> listPDF = new ArrayList<>();
       int i,len;
       File folder = new File("C://Users//Pavan//Desktop//papers");
       File[] listOfFiles = folder.listFiles();
       len = listOfFiles.length;
       for (i=0;i<len;i++){
           temp = listOfFiles[i];
           if(isPDF(temp.getName()))
               listPDF.add(temp);       
       }
       return listPDF;
   }
   
  //Helper function to check that file is PDF
   public static boolean isPDF(String file){
       int lastindex;
       lastindex = file.lastIndexOf('.');
       String extension = file.substring(lastindex+1);
       if(extension.compareToIgnoreCase("pdf")==0)
           return true;
       else
           return false;
   }
}
