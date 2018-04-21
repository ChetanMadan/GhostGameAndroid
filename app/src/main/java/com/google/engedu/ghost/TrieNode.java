package com.google.engedu.ghost;

import java.util.AbstractList;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import java.util.Set;


public class TrieNode {
    private HashMap<Character, TrieNode> children;
    private boolean isWord;

    public TrieNode() {
        children = new HashMap<>();
        isWord = false;
    }

    public void add(String s) {
        s = s+"1";
        TrieNode temp1 = this;
        HashMap<Character, TrieNode> temp = children;
        char ch;
        for(int i=0; i<s.length(); i++){
            ch = s.charAt(i);
            //addLetter(ch);

                if(temp.containsKey(ch)){
                    temp1 = temp.get(ch);
                    temp = temp1.children;
                    continue;
                }

                    TrieNode tr = new TrieNode();
                    temp.put(ch, tr);
                    temp1 = temp.get(ch);
                    temp = temp1.children;
                    //break;


        }
        //addLetter('1');
    }



    public boolean isWord(String s) {
        HashMap<Character, TrieNode> temp = children;
        TrieNode temp1 = this;
        int i=0;
        char ch = s.charAt(i++);
        while(true){
            if(temp.containsKey(ch)){
                if(i == s.length()){
                    temp1 = temp.get(ch);
                    temp = temp1.children;
                    if(temp.containsKey('1')){
                        return true;
                    }
                    return false;
                }
                temp1 = temp.get(ch);
                temp = temp1.children;
                ch = s.charAt(i++);
            }
            else {
                break;
            }
        }
        return false;
    }


    public String getAnyWordStartingWith(String s) {
        String val = "";
        if (s.equals("")) {
            Random r = new Random();
            char sm = (char) (r.nextInt(26) + 97);
            return sm + "";
        }
        int i = 0;
        char ch;
        HashMap<Character, TrieNode> temp = children;
        TrieNode temp1 = this;
        while (i < s.length()) {
            ch = s.charAt(i++);
            if (temp.containsKey(ch)) {
                val = val+ch;
                temp1 = temp.get(ch);
                temp = temp1.children;
            } else {
                return null;
            }
        }
        while (true) {
            for (char a = 'a'; a <= 'z'; a++) {
                if (temp.containsKey(a)) {
                    val = val+a;
                    temp1 = temp.get(a);
                    temp = temp1.children;
                    i++;
                    break;
                }
            }
            if (i > s.length() && temp.containsKey('1')) {
                return val;
            }
            else if (temp.size() == 0){
                return null;
            }
            else if (temp.size() == 1 && temp.containsKey('1')) {
                return val;
            }
        }
    }

    public String getGoodWordStartingWith(String s) {
        String val = "";
        if (s.equals("")) {
            Random r = new Random();
            char sm = (char) (r.nextInt(26) + 97);
            return sm + "";
        }
        int i = 0;
        char ch;
        HashMap<Character, TrieNode> temp = children;
        TrieNode temp1 = this;
        while (i < s.length()) {    //go to the hash map of the prefix
            ch = s.charAt(i++);
            if (temp.containsKey(ch)) {
                val = val+ch;
                temp1 = temp.get(ch);
                temp = temp1.children;
            } else {
                return null;
            }
        }
        Set<Character> keyS = temp.keySet();
        String keys1 = keyS.toString();
        String keys = "";
        for (int j=0; j<keys1.length(); j++){
            char a = keys1.charAt(j);
            if(a>='a' && a<='z'){
                keys = keys+a;
            }
        }
        ArrayList<Character> prePlusLetter = new ArrayList<>();
        for(int j=0; j<keys.length(); j++){
            ch = keys.charAt(j);
            if(isWord(s+ch)){
                prePlusLetter.add(ch);
            }
        }
        if(prePlusLetter.size() != 0){
            Random r = new Random();
            int rr = r.nextInt(prePlusLetter.size());
            val = val+prePlusLetter.get(rr);
            return val;
        }
        Random r = new Random();
        int rr = r.nextInt(keys.length());
        val = val+keys.charAt(rr);
        return val;
    }

    public HashMap<Character, TrieNode> getChildMap(){
        return children;
    }
}
