package documentclassifier;

import java.io.*;
import java.util.*;

//Singleton pattern for punctuation list since it has to be instantiated just once
public class punctuationList {
    ArrayList<String> p;
    private static punctuationList instance = null;
    private punctuationList(){
        p = new ArrayList<>();
        p.add("!"); p.add("@"); p.add("#"); p.add("$"); p.add("%"); p.add("^");
        p.add("&"); p.add("*"); p.add("("); p.add(")"); p.add("_"); p.add("`");
        p.add("~"); p.add("="); p.add("+"); p.add("{"); p.add("[");
        p.add("}"); p.add("]"); p.add(":"); p.add(";"); p.add("<"); p.add(">"); 
        p.add(","); p.add("."); p.add("/"); p.add("?"); p.add("'");         
    }
    public static synchronized punctuationList getInstance(){
        if(null==instance)
            instance = new punctuationList();
        return instance;        
    }
    @Override
    protected Object clone() throws CloneNotSupportedException {
        throw new CloneNotSupportedException("Clone is not allowed.");
    }
}
