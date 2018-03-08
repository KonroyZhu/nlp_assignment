 /*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Segment;
 
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Konroy
 */
public class RMMSegment {
     public List<String> Result;
    public String  segment(String paragraph) throws FileNotFoundException{
         String result="/"; 
       int maxlen=3;
       int pos=paragraph.length(); 
      
       List<String> dic=new MMSegment().load_dic( );//加载词典
       Result=new ArrayList<String>();
       long begintime = System.currentTimeMillis();//开始时间
       int len=maxlen;//初始化当前长度为3
       while(pos>0){
           if(pos-len<0){//边界处理：当当前位置减去当前长度，当前长度就取当前位置-0的长度
               len=pos;  
           }
            String word=paragraph.substring(pos-len, pos);//分出的词块
            System.out.println(word);
            if(dic.contains(word)){//如果词典里有该词块，就加到结果里面去
                result+=word+"/";
                Result.add(word);
                pos-=len;//位置前移
                len=maxlen;//将当前长度设置回3
            }else{//该词块没有在词典里面
                if(len>1){
                    len--; 
                }else{
                    len=1;
                }

            }
       }
       System.out.println(result); 
       long endtime=System.currentTimeMillis();//结束时间
       long costTime = (endtime - begintime);
       System.out.println(costTime/1000.0+" s"); 
       return result;
    }
    public static void main(String[] args) throws FileNotFoundException{
        String paragraph="那个音频视频和其它要用的文件明天可以发给我么";
        RMMSegment rmm=new RMMSegment();
        rmm.segment(paragraph);
       
       
    }
    
}
