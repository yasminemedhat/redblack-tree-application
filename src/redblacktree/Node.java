/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package redblacktree;

/**
 *
 * @author lenovo
 * @param <T>
 */
public class Node<T extends Comparable<T>>
{
     
      
    public static final int BLACK = 0;
    public static final int RED = 1;
    public T key; // the key of each node
    Node<T> parent,left,right;
   
    public int color;
 
      // Constructor which sets key to the argument.
        Node(T key){
        this.key = key;
        color = BLACK;
        parent = null;
        left = null;
        right = null;
	}
        Node(){
       color = BLACK;
        parent = null;
        left = null;
        right = null;
	}

	
}
