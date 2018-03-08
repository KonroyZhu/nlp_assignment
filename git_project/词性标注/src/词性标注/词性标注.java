/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package 词性标注;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import static 词性标注.Scale.scale_L;
import static 词性标注.Scale.scale_S;
import static 词性标注.Scale.sum_line;
import static 词性标注.Scale.sum_row;

/**
 *
 * @author Konroy
 */
public class 词性标注 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        Map<Integer,String> num_tag=getNumTag();
//        double[][] shift={{1,1,1,48637,1,20},{1974,1,427,188,1,39,},{43323,1,1326,17315,1,186},{1068,3721,42471,11774,615,21393},{6073,43,4759,1477,130,1523},{8017,76,4657,1330,955,1}};
//        double[][] launch={{1,1,1,11,44,1},{1,10066,1,1,1,1},{1,1,1,37,134,1},{1,1,5485,1,1,1},{1,1,1,383,1,1},{1,1,1,109,5,1},{69017,1,1,1,1,1},{1,1,1,1,1,48810}};
//        
////        double[][] s_shift={{0.7,0.3},{0.4,0.6}};
////        double[][] s_launch={{0.5,0.1},{0.4,0.3},{0.1,0.6}};
//        
//        double[][] s_shift=scale_L(shift);
//       
//        double[][] s_launch=scale_S(launch);
//        
//        show(s_launch);
        double[] init={0.2,0.1,0.1,0.2,0.3,0.1};  
//       double[] init={0.6,0.4};
        
//        show(launch);
//        System.out.println();
//        show(shift);
//        System.out.println(launch.length);

 double[][] launch={{1,1,1,11,44,1},{1,10066,1,1,1,1},{1,1,1,37,134,1},{1,1,5485,1,1,1},{1,1,1,383,1,1},{1,1,1,109,5,1},{69017,1,1,1,1,1},{1,1,1,1,1,48810}};
         System.out.println(sum_line(launch,0));
        double[][] s_launch=scale_L(launch);
          
        double[][] shift={{1,1,1,48637,1,20},{1974,1,427,188,1,39,},{43323,1,1326,17315,1,186},{1068,3721,42471,11774,615,21393},{6073,43,4759,1477,130,1523},{8017,76,4657,1330,955,1}};
        double[][] s_shift=scale_S(shift);
        System.out.println(sum_row(shift,0));
          System.out.println("shift:");
//          for(int i=0;i<s_launch.length;i++){
//              for(int j=0;j<s_launch[i].length;j++){
//                  System.out.print(s_launch[i][j]+"    ");
//                  if(j==s_launch[i].length-1)System.out.println();
//              }
//              
//          } 




        String target="the-6 bear-0 is-1 on-3 the-6 move-2";
//        String target="normal-0 cold-1 dizzy-2";
        String[] Ob=target.split(" ");
        double[][] last=new double[Ob.length][s_shift.length];
        String[][] tag=new String[Ob.length][s_shift.length];
        for(int i=0;i<Ob.length;i++){
            int index=Integer.parseInt(Ob[i].split("-")[1]);
//            System.out.print(index+": ");
            System.out.println(Ob[i]+": ");
            
            for(int j=0;j<s_shift.length;j++){
               
               if(i==0){
//                   System.out.println("<index:"+index+">"+"<j "+j+">"+s_launch[index][j]);
                   last[i][j]=init[j]*s_launch[index][j];//第index个词 是词性j的概率//初始值不由index控制
                   System.out.print("初始值："+init[j]+"*"+s_launch[index][j]+num_tag.get(j)+": "+last[i][j]+" ");
               }else{
                    System.out.print(num_tag.get(j)+" 前一结点可能取值：");
                   double max=0.0;
                   int bestv=-1;
                    System.out.print("(");
                    for(int v=0;v<last[i].length;v++){
                        
//                        System.out.println("< L:"+last[i-1][v]+">"+"<S:"+s_shift[v][j]+">"+"<L: index: "+index+" j:"+j+" "+s_launch[index][j]+">");
                        double temp=last[i-1][v]*s_shift[v][j]*s_launch[index][j];
                        if(temp>max){
                            max=temp;bestv=v;
                        }
                        System.out.print("取"+num_tag.get(v)+" 概率："+temp+",");
                    }System.out.print(")");
                    
                    last[i][j]=max;
                    System.out.println("最大概率： "+max+"下标：<"+bestv+">  ");
                    tag[i][j]=""+bestv;
                  }

            }
            System.out.println();
        }
        
        show(tag);
        int end=0;
        double max=0.0;
        for(int i=0;i<s_shift.length;i++){//获取未结点
            double temp=last[last.length-1][i];
//            System.out.println(temp);
            if(temp>max){
                max=temp;end=i;
            }
        }
        List<String> result=new ArrayList<String>();//用于记录结果
        int temp=-1;
        System.out.println(end+" "+num_tag.get(end));
        int index=Ob.length-1;
        result.add(Ob[index--].split("-")[0]+"/"+num_tag.get(end));
        for(int i=tag.length-1;i>0;i--){
            if(i==tag.length-1){
                temp=Integer.parseInt(tag[i][end]);
                result.add(Ob[index--].split("-")[0]+"/"+num_tag.get(temp));
            }else{
                temp=Integer.parseInt(tag[i][temp]);
                result.add(Ob[index--].split("-")[0]+"/"+num_tag.get(temp));
            }
            System.out.println(temp+" "+num_tag.get(temp));
        }
       
        
        for( int i=result.size()-1;i>=0;i--){//输出结果
            System.out.print(result.get(i)+" ");
        }
       
        
         DecimalFormat df = new DecimalFormat("#.00");
         System.out.println(df.format(4.473752494117015E-6));
    }
    public static void show(double[][] array){
        for(int i=0;i<array.length;i++){
            for(int j=0;j<array[i].length;j++){
                System.out.print(array[i][j]+" ");
                if(j==array[i].length-1)System.out.println();
            }
        }
    }
    public static void show(String [][] array){
        for(int i=0;i<array.length;i++){
            for(int j=0;j<array[i].length;j++){
                System.out.print(array[i][j]+" ");
                if(j==array[i].length-1)System.out.println();
            }
        }
    }
    public static Map<Integer,String> getNumTag(){
        Map<Integer,String> num_tag=new HashMap<Integer,String>();
        num_tag.put(0, "AT");
        num_tag.put(1, "BEZ");
        num_tag.put(2, "IN");
        num_tag.put(3, "NN");
        num_tag.put(4, "VB");
        num_tag.put(5, "PER");
        return num_tag;
    }

    
}
