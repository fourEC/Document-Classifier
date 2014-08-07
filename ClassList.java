package documentclassifier;

import java.util.HashMap;

public class ClassList {
    
    private static int index = -1;
    public HashMap<Integer,Class_info> hashmap = new HashMap();
    public ClassList(){
           
    }
    public void addClass(Class_info class_var){
        index++;
        hashmap.put(index, class_var);
    }
   
    
}
