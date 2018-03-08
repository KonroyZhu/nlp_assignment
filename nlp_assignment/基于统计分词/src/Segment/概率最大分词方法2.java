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
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Konroy
 */
public class 概率最大分词方法2 {
    private static Map<String,Double> mdic=new HashMap<String,Double>();
    public static void main(String[] args){
//        String para="中国，是以华夏文明为源泉、中华文化为基础，并以汉族为主体民族的多民族国家，通用汉语、汉字，汉族与少数民族被统称为“中华民族”，又称为炎黄子孙、龙的传人";       mdic=loadDic(new File("").getAbsolutePath()+"\\src\\Segment\\WordFrequency.txt");
//        String para="结合成分子时";
//        String para="作为一种无监督的机器学习方法";
          String para="文档聚类主要是依据著名的聚类假设：同类的文档相似度较大，而不同类的文档相似度较小。作为一种无监督的机器学习方法，聚类由于不需要训练过程，以及不需要预先对文档手工标注类别，因此具有一定的灵活性和较高的自动化处理能力，已经成为对文本信息进行有效地组织、摘要和导航的重要手段，为越来越多的研究人员所关注";


      List<String> segment=segment(para);
       
       for(int i=segment.size()-1;i>=0;i--){
           System.out.print(segment.get(i));
       }
        
    }
    
    
    
    public static List<String> segment(String para){
        
         mdic=loadDic(new File("").getAbsolutePath()+"\\src\\Segment\\WordFrequency.txt");
       List<String> seg_list=seg(para);
       List<Double> acc=new ArrayList<Double>();
       List<String> result=new ArrayList<String>();
        
        for(int i=0;i<seg_list.size();i++){

            System.out.print(seg_list.get(i)+" "+i+" ");
            if(seg_list.get(i).split("-")[1].equals("0")){
                
                System.out.print("null ");
                acc.add(selfP(seg_list.get(i).split("-")[0]));
                result.add(seg_list.get(i).split("-")[0]+"-"+Integer.MAX_VALUE+"-"+Integer.MAX_VALUE+"-"+Integer.MAX_VALUE);
            }else{
                
                    Double max=0.0;
                    int bestindex=0; 
                    for(int j=0;j<i;j++){
                        if(seg_list.get(j).split("-")[2].equals(seg_list.get(i).split("-")[1])){//字符串之间用equals来判断表面是否相等
                            
                            if(acc.get(j)>=max){
                                max=acc.get(j);bestindex=j;
                            }
                            System.out.print(seg_list.get(j));
                            System.out.print(acc.get(j)+"--"+ bestindex);
                        }
                    } 
                    System.out.print("< "+max+"  "+acc.get(bestindex)+" "+seg_list.get(i).split("-")[0]+"-"+seg_list.get(bestindex).split("-")[0]+" >");
                    if(max==0.0){
                        max=1.0;
                    }
                    acc.add(max*selfP(seg_list.get(i).split("-")[0]));
                    result.add(seg_list.get(i)+"-"+bestindex+"-"+acc.get(bestindex));
            }
           
            
            System.out.println();
            
        }
        
        String lastword=getlast(result);
        System.out.println(lastword);
        
        
       List<String> segment=new ArrayList<String>();
       int next=result.size();
       int temp=next;
       for(int i=result.size()-1;i>=0;i--){
          
          if(result.get(i).equals(lastword)){
              next=Integer.parseInt(lastword.split("-")[3]);
              System.out.println(lastword.split("-")[0]+"/");
              segment.add(lastword.split("-")[0]+"/");
          }else{
              if(i==next){
                  String nword=result.get(i);
                  try{
                      next=Integer.parseInt(nword.split("-")[3]);
                      segment.add(nword.split("-")[0]+"/");
                      System.out.println(nword.split("-")[0]+"/");
                  }catch(Exception e){
                      e.printStackTrace();
                  }
                  
              }
               
          }
          
          System.out.println(result.get(i)+"            "+next+"  "+i);
          
       }
        
        return segment;
        
    }
    public static String getlast(List<String> result){
        String last=result.get(result.size()-1).split("-")[1];
       List<String> lastCandidate=new ArrayList<String>();
       for(int i=result.size()-1;i>1;i--){
           if(result.get(i).split("-")[1].equals(last)){
               lastCandidate.add(result.get(i));
           }else{
               break;
           }
       }
//       System.out.print(lastCandidate);
       
       double maxacc=0.0;
       String lw="";
       for(String lastword:lastCandidate){
           if(Double.parseDouble(lastword.split("-")[1])>maxacc){
               maxacc=Double.parseDouble(lastword.split("-")[1]);
               lw=lastword;
           }
       }
       return lw;
    }
   
    public static Double selfP(String str){
        return mdic.keySet().contains(str)?mdic.get(str):1.0;
//          return mdic.get(str);
    }
    
    public static List<String> seg(String para){
       List<String> posSeg=new ArrayList<String>();
       
       
       int start=0;
        int end=para.length();
        int len=1;
        while(start<end){
            
            while(start+len<=end){
                 String seg=para.substring(start, start+len);
                 
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
