package documentclassifier;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

import javax.swing.Spring;

public class Research_paper {
	private String Name;
	private String Author;
	private String Publication;
	private String Year;
	private String class_of_paper;

        private ArrayList<String> keywords;
        private ArrayList<Integer> frequency;
	private int key;
	
	public Research_paper(String name, String author, String Publication, String year, ArrayList<String> keywords,ArrayList<Integer> frequency){
		this.Name = name;
		this.Author = author;
 		this.Publication = Publication;
		this.Year = year;
		this.key = keygen(keywords);
		this.keywords = keywords;
                this.frequency = frequency;
        }
	
	public String getName() {
		return Name;
	}
	public String getAuthor() {
		return Author;
	}
	public String getPublication() {
		return Publication;
	}
	public String getYear() {
		return Year;
	}
	public String getClass_of_paper() {
		return class_of_paper;
	}
	public ArrayList<String> get_Keywords() {
		return keywords;
	}
        
        public ArrayList<Integer> getFrequencyList(){
            return frequency;
        }
	
	private int keygen(ArrayList<String> sw) {
		
                int key = 0;
		Iterator i = sw.iterator();
		int temp;
		String temp_string;
                
		while(i.hasNext()){
			temp_string = (String) i.next();
			temp = Math.abs(temp_string.hashCode()) % temp_string.length();
			key += temp;
		}
		
                return key;
	}
        
        public int keygen(){
            return key;
        }
        
	public boolean check_equals(Research_paper obj) {
            if(this.key==obj.key)
                return true;
            else
                return false;
	}
	public String toString(){
		return "Name::" + Name + " Author::"+Author+" Publication::"+Publication + " Year::" + Year + " Subject::" +class_of_paper + "\nKeywords:::\n" + keywords;
		
	}	
        
        public void setClass(String classname){
            this.class_of_paper = classname;
        }
      
}
