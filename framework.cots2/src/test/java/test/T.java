/**
 *all rights reserved,@copyright 2003
 */
package test;

public class T{
  public void m(int x,int y){
    System.out.println("X="+x+"Y="+y);
  }
  public static void main(String[] agrc){
    out:for(int i=0;i<2;i++)
         for(int j=0;j<3;j++){
           System.out.println("I="+i+"J="+j);
           if(i==j){
             continue out;
           }
         }
  }
}