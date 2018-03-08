/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package 词性标注;


/**
 *
 * @author Konroy
 */
public class Scale {
    public static void main(String[] args){
         double[][] launch={{1,1,1,11,44,1},{1,10066,1,1,1,1},{1,1,1,37,134,1},{1,1,5485,1,1,1},{1,1,1,383,1,1},{1,1,1,109,5,1},{69017,1,1,1,1,1},{1,1,1,1,1,48810}};
         System.out.println(sum_line(launch,0));
        double[][] s_launch=scale_L(launch);
          
        double[][] shift={{1,1,1,48637,1,20},{1974,1,427,188,1,39,},{43323,1,1326,17315,1,186},{1068,3721,42471,11774,615,21393},{6073,43,4759,1477,130,1523},{8017,76,4657,1330,955,1}};
        double[][] s_shift=scale_S(shift);
        System.out.println(sum_row(shift,0));
          System.out.println("shift:");
          for(int i=0;i<s_shift.length;i++){
              for(int j=0;j<s_shift[i].length;j++){
                  System.out.print(s_shift[i][j]+"    ");
                  if(j==s_shift[i].length-1)System.out.println();
              }
              
          }
          System.out.println("launch:");
           for(int i=0;i<s_launch.length;i++){
              for(int j=0;j<s_launch[i].length;j++){
                  System.out.print(s_launch[i][j]+"    ");
                  if(j==s_launch[i].length-1)System.out.println();
              }
              
          }
               System.out.print(s_launch[6][1]+"    ");
    }
    public static double[][] scale_L(double[][] array){
        double[][] scaleArray=new double[array.length][array[0].length];
        
        for (int i=0;i<array.length;i++){
            for(int j=0;j<array[i].length;j++){
                scaleArray[i][j]=array[i][j]/sum_line(array,j);
            }
        }

        return scaleArray;
    }
     public static double[][] scale_S(double[][] array){
        double[][] scaleArray=new double[array.length][array[0].length];
        
        for (int i=0;i<array.length;i++){
            
            for(int j=0;j<array[i].length;j++){
                scaleArray[i][j]=array[i][j]/sum_row(array,i);
            }
        }

        return scaleArray;
    }
    public static double sum_row(double[][] array,int row_index){
        double sum=0.0;
        for(int i=0;i<array[row_index].length;i++){
            sum+=array[row_index][i];
        }
        return sum;
    }
    public static double sum_line(double[][] array,int line_index){
        double sum=0.0;
        for (int i=0;i<array.length;i++){
            sum+=array[i][line_index];
        }
        return sum;
    }
}
