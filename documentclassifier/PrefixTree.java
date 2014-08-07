package documentclassifier;

import documentclassifier.Pdf_Read;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;

public class PrefixTree
{
    static TrieNode createTree()
    {
        return(new TrieNode('\0', false));
    }
    
    static void insertWord(TrieNode root, String word)
    {
        int offset = 97;
        word=word.toLowerCase();
        int l = word.length();
        char[] letters = word.toCharArray();
        TrieNode curNode = root;
        int i;
        for (i = 0; i < l; i++)
        {
            if (curNode.links[letters[i]-offset] == null)
                curNode.links[letters[i]-offset] = new TrieNode(letters[i], i == l-1 ? true : false);
            curNode = curNode.links[letters[i]-offset];
        }
        if(i==l && curNode.fullWord==false)
        	curNode.fullWord=true;
    }

    static void insertFromFolder(TrieNode root)
    {
        //Get files from directory and create research papers object
        File f;
        ArrayList<File> files = WindowsAPI.getFiles();
        String[] fields= new String[4];
        Iterator<File> i = files.iterator();
        while(i.hasNext()){
		f = i.next();
		fields = Pdf_Read.getDetails(f);

        insertWord(root, fields[0].toLowerCase());
        insertWord(root, fields[2].toLowerCase());
        insertWord(root, fields[3].toLowerCase());
        }
    }
    
    static String find(TrieNode root, String word)
    {
        char[] letters = word.toCharArray();
        int l = letters.length;
        int offset = 97;
        TrieNode curNode = root;
        
        int i;
        for (i = 0; i < l; i++){
            if (curNode == null)
                return "search unsuccessful";
            curNode = curNode.links[letters[i]-offset];

        }
        
        if (i == l && curNode == null)
            return "search unsuccessful";
       
        return "file found";
    }
    
    static void printTree(TrieNode root, int level, char[] branch)
    {
        if (root == null)
            return;
        
        for (int i = 0; i < root.links.length; i++)
        {
            branch[level] = root.letter;
            printTree(root.links[i], level+1, branch);    
        }
        
        if (root.fullWord)
        {
            for (int j = 1; j <= level; j++)
                System.out.print(branch[j]);
            System.out.println();
        }
    }
}

class TrieNode
{
    char letter;
    TrieNode[] links;
    boolean fullWord;
    String details; 
    TrieNode(char letter, boolean fullWord)
    {
        this.letter = letter;
        links = new TrieNode[26];
        this.fullWord = fullWord;
        details=null;
    }
}