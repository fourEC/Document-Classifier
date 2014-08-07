package documentclassifier;

import java.util.ArrayList;
import java.util.*;

public class Tf_Idf {
    
    public Tf_Idf(){}
    
    public void classify(Index index, ClassList class_list){
           Set<Integer> keyset = index.hashmap.keySet();
           Iterator itr = keyset.iterator();
           
           ArrayList<String> class_keywords;
           Iterator class_keywords_iterator;
           ArrayList<String> list;
           Iterator document_keyword_itr;
           ArrayList<Integer> frequency;
           Iterator document_frequency_iterator;
           Class_info current_class;
           Iterator itr_class;
           Set<Integer> classes;
           Research_paper paper;
           
           while(itr.hasNext()){
               float class_scores[] = new float[class_list.hashmap.size()];
               int document_index;
               String classname;
               int key = (int) itr.next();
               paper = index.hashmap.get(key);
               classes = class_list.hashmap.keySet();
               itr_class = classes.iterator();
              
               //For each class get document score
               int i = 0;
               String keyword;
               while(itr_class.hasNext()){
                  int freq;
                  int curr_class_index = (Integer) itr_class.next();
                  current_class = class_list.hashmap.get(curr_class_index);
                  class_keywords = current_class.getKeywords();
                  class_keywords_iterator = class_keywords.iterator();
                  list = paper.get_Keywords();
                  document_keyword_itr = (Iterator) list.iterator();
                  frequency = paper.getFrequencyList();
                  document_frequency_iterator = frequency.iterator();
                 
                  class_scores[i] = 0;
                  while(document_keyword_itr.hasNext() && document_frequency_iterator.hasNext()){
                      keyword = (String) document_keyword_itr.next();
                      if(class_keywords.contains(keyword)){                     
                          class_scores[i] += (Integer) document_frequency_iterator.next() * getImportance(index, keyword);
                      }
                      else{
                          class_scores[i] += 0.0002 * getImportance(index, keyword);
                      } 
                  }
                  i++;             
               }
               document_index = getMaxIndex(class_scores);
               System.out.println("Doc Index:" + document_index);
               Class_info class_id = class_list.hashmap.get(document_index);
               System.out.println("Paper:" + class_id);
               classname = class_id.classname;
               paper.setClass(classname);               
           }
    }
    
    public float getImportance(Index index, String keyword){
        Set<Integer> keyset = index.hashmap.keySet();
        Iterator itr = keyset.iterator();
        int Dif = 0;
        int doc_count = 0;
        Research_paper paper;
        while(itr.hasNext()){
            doc_count++;
            int key = (int) itr.next();
            paper = index.hashmap.get(key);
            if(paper.get_Keywords().contains(keyword)){
                Dif++;
            }
        }
        float importance = (float) Math.log(doc_count/Dif);
        return importance;
        
    }
    
    public int getMaxIndex(float arr[]){
        int i = 0,j,len = arr.length;
        float maxval = arr[0];
        for(j=1;j<len;j++){
            if(arr[j]>maxval){
                maxval = arr[j];
                i = j;
            }
        }  
        System.out.print(maxval+" ");
        return i;
    }
}
