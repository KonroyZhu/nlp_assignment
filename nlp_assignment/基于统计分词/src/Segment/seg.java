/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Segment;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

/**
 *
 * @author Konroy
 */
public class seg {
    private static Map<String,Double> mdic=new HashMap<String,Double>();
    public static void main(String[] args){
         mdic=loadDic(new File("").getAbsolutePath()+"\\src\\Segment\\WordFrequency.txt");
        String para="为越来越多的研究人员所关注";
        List<String> posSeg=seg(para);
        
        for(String s: posSeg){
            System.out.println(s);
        }
    }
    
     public static List<String> seg(String para){
       List<String> posSeg=new ArrayList<String>();
       
       
       int start=0;
        int end=para.length();
        int len=1;
        while(start<end){
            
            while(start+len<=end){
                 String seg=para.substring(start, start+len);
                 System.out.println("<SEG "+seg+" />");
                 if(mdic.keySet().contains(seg)){
                      len++;
                     posSeg.add(seg+"-"+start+"-"+(start+len-1)); 
                 } else if(len==1){
                     posSeg.add(seg+"-"+start+"-"+(start+len));
                     break;
                 }else{
                     len=1;
                     posSeg.add(seg+"-"+start+"-"+(start+len-1));
                     break;
                 }
            } 

            start++;
        }
        
       return posSeg;
   } 
     public static Map<String,Double> loadDic(String path){
        Map<String,Double> mdic=new HashMap<String,Double>();
        try {
            Scanner scanner=new Scanner(new File(path));
            while(scanner.hasNextLine()){
                //dic.add(scanner.nextLine().split(",")[0]);//获取词
                String line=scanner.nextLine();//得先保存---不能nextLine（）两次
                mdic.put(line.split(",")[0],Double.parseDouble(line.split(",")[2].replace("%", ""))/100);
            }  
        } catch (FileNotFoundException ex) {
           ex.printStackTrace(); 
        }
        return mdic;
    }
}
