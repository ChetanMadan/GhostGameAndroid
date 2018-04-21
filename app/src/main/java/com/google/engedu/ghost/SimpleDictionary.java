package com.google.engedu.ghost;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Random;

public class SimpleDictionary implements GhostDictionary {
    private ArrayList<String> words;
    private ArrayList<String> goodWords;

    public SimpleDictionary(InputStream wordListStream) throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(wordListStream));
        words = new ArrayList<>();
        String line = null;
        while((line = in.readLine()) != null) {
            String word = line.trim();
            if (word.length() >= MIN_WORD_LENGTH)
              words.add(line.trim());
        }
    }

    @Override
    public boolean isWord(String word) {
        return words.contains(word);
    }

    @Override
    public String getAnyWordStartingWith(String prefix) {
        Random rn = new Random();
        if(prefix.equals("")){
            int ran = rn.nextInt(words.size());
            String word = words.get(ran);
            return word;
        }
        return binSearch(prefix);

    }

    @Override
    public String getGoodWordStartingWith(String prefix) {
        String selected = "h";
        Random rn = new Random();
        int ran;

        if(prefix.equals("")){
            ran = rn.nextInt(words.size());
            String word = words.get(ran);
            return word;
        }

        ArrayList<String> eGoodWords = new ArrayList<>();
        ArrayList<String> oGoodWords = new ArrayList<>();
        goodWords = new ArrayList<>();
        binSearch1(prefix, 0, words.size()-1);
        for(String s:goodWords){
            if(s.length()%2 == 0){
                eGoodWords.add(s);
            }
            else {
                oGoodWords.add(s);
            }
        }
        if(goodWords.size() != 0){

            if(prefix.length()%2 == 0){    //if game started with computer's turn
                if(eGoodWords.size() != 0){
                    ran = rn.nextInt(eGoodWords.size());
                    selected = eGoodWords.get(ran);
                }
                else {
                    ran = rn.nextInt(oGoodWords.size());
                    selected = oGoodWords.get(ran);
                }
            }
            else {
                if(oGoodWords.size() != 0){
                    ran = rn.nextInt(oGoodWords.size());
                    selected = oGoodWords.get(ran);
                }
                else {
                    ran = rn.nextInt(eGoodWords.size());
                    selected = eGoodWords.get(ran);
                }
            }
        }

        return selected;
    }

    public String binSearch(String prefix){
        int low=0, high=words.size()-1,mid;
        while(low<=high){
            mid=(low+high)/2;
            String midString = words.get(mid);
            if(midString.startsWith(prefix)){
                return midString;
            }
            else if(midString.compareToIgnoreCase(prefix)>0){
                high = mid-1;
            }
            else {
                low = mid+1;
            }
        }
        return null;
    }
    public void binSearch1(String prefix, int low, int high){
        int mid;

        if(low<=high){
            mid=(low+high)/2;
            String midString = words.get(mid);
            if(midString.startsWith(prefix)){
                goodWords.add(midString);
                binSearch1(prefix, low, mid-1);
                binSearch1(prefix, mid+1, high);
            }
            else if(midString.compareToIgnoreCase(prefix)>0){
                binSearch1(prefix, low, mid-1);
            }
            else {
                binSearch1(prefix, mid + 1, high);
            }


            /*else if(midString.compareToIgnoreCase(prefix)>0){
                high = mid-1;
            }
            else {
                low = mid+1;
            }*/
        }
    }
}
