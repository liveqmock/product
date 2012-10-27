package test;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Test {

	public static void main(String[] args) {
	    final List<Integer> ages=new ArrayList<Integer>();
	    for (int i = 0; i < 100; i++) {
	        ages.add(i);
	    }        
	    Thread t=new Thread(){
	        public void run() {
	            for (int i = 100; i < 200; i++) {
	                ages.add(i);
	                Thread.yield();
	            }
	        }
	    };
	    Thread t2=new Thread(){
	        public void run() {
	            Iterator<Integer> it=ages.iterator();
	            while(it.hasNext()){
	                System.out.println(it.next());
	                Thread.yield();
	            }
	        }
	    };
	    t2.start();
	    t.start();
	    
	    
	    System.out.println("32010200".substring(0,4));
	}



}
