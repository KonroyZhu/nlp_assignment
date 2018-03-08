/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Segment;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 *
 * @author Konroy
 */
public class MMSegment {
    public  Result result=new Result();
    public List<String> dic;
    
    public static void main(String[] args) throws FileNotFoundException{
        String input="那个音频视频和其它要用的文件明天可以发给我么";
        
        MMSegment mm=new MMSegment();
         
        mm.segment(input, 0, 3);  
//        getSegment(input,3,0,result); 
        System.out.print(mm.result.str);  
        
    }
    public MMSegment() {
        try{
        dic=load_dic();
        }catch(FileNotFoundException e){
            e.printStackTrace();
        }
    }
     
    public void segment(String input,int pos,int len){
        if(pos>=input.length())return;//从0开始，且大于时会抛出indexoutofbound
        
//        while(pos<input.length()){  //无需while
            if(pos+len>input.length()){//边界处理，否则当pos+len大于末尾位置取subString时会抛出异常
                len=input.length()-pos;
            } 
             
            String word=input.substring(pos, pos+len);
            System.out.println(word); 
            if(dic.contains(word)){
                
                result.str+=word+"/"; 
                pos+=len;
                len=3;
                segment(input,pos,len);
                
            }else{
                if(len>1){
                    len--;
                    segment(input,pos,len);
                }else{
                    result.str+=input.substring(pos, pos+1)+"/"; 
                    pos+=1;
                    segment(input,pos,len);
                }
            }
            
//        }
   
    }
    public  List<String> load_dic() throws FileNotFoundException{
        List<String> dic=new ArrayList<String>();
        File file=new File("C:\\Users\\Konroy\\Documents\\NetBeansProjects\\自然语言处理_分词\\src\\Segment\\chineseDic.txt");
        Scanner scn=new Scanner(file);
        while(scn.hasNext()){
//            System.out.print("appendin");  
            String word=scn.nextLine().split(",")[0]; 
//            System.out.print(word); 
            dic.add(word);
        }
        return dic; 
    }
    
}

