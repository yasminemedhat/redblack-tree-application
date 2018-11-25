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
public class RedBlackTree<T extends Comparable<T>>
{

    public final int RED = 0;
    public final int BLACK = 1;
    final Node nil = new Node();
    private int treeSize=0;
    Node root = nil;

    public RedBlackTree()
    {

        root.left = nil;
        root.right = nil;
        root.parent = nil;

    }

    public void printTree()
    {
        printTree1(root);
    }

    private void printTree1(Node x)
    {
        
        if (x != nil)
        {
            System.out.println(x.key + "-" + x.color + "-left" + x.left.key + "-right" + x.right.key + "-parent" + x.parent.key);
            if (x.left != nil)
            {
                printTree1(x.left);
            }
            if (x.right != nil)
            {
                printTree1(x.right);
            }

        }
        System.out.println(" root of tree: " + root.key);

    }

    public void insert(T key)
    {
        Node f=search(key);
        if(f!=nil){
            System.out.println("This word already exist rakez shwaya");
            return;
        }
        Node x = new Node(key);
        treeSize++;
        Node r = root;
        Node y = nil;
        while (r != nil)
        {
            y = r;
            // if x.key is < than the current key, go left
            if (x.key.compareTo(r.key) < 0)
            {
                r = r.left;
            } else  // if x.key is >= than the current key, go right
            {
                r = r.right;
            }
        }
        x.parent = y; // found the right leaf 
        if (y == nil)
        {
            root = x;

        } else if (x.key.compareTo(y.key) < 0)
        {
            y.left = x;
        } else
        {
            y.right = x;
        }

        x.left = nil;
        x.right = nil;
        x.color = Node.RED;
        insertFix(x);
        }

    public void insertFix(Node x)
    {
        Node y = nil;
        // While there is a violation of the RedBlackTree properties..
        while (x.parent.color == Node.RED)
        {

            // If x's parent is the the left child of it's parent.
            if (x.parent == x.parent.parent.left)
            {

                // Initialize y to x 's uncle
                y = x.parent.parent.right;

                // Case 1: if y is red...recolor
                if (y.color == Node.RED)
                {
                    x.parent.color = Node.BLACK;
                    y.color = Node.BLACK;
                    x.parent.parent.color = Node.RED;
                    x = x.parent.parent;
                } // Case 2: if y is black & x is a right child
                else if (x == x.parent.right)
                {

                    // leftRotaet around x's parent
                    x = x.parent;
                    rotateLeft(x);
                } // Case 3: else y is black & z is a left child
                else
                {
                    // recolor and rotate round x's grandparent
                    x.parent.color = Node.BLACK;
                    x.parent.parent.color = Node.RED;
                    rotateRight(x.parent.parent);
                }
            } // If x's parent is the right child of it's parent.
            else
            {

                // Initialize y to x's uncle
                y = x.parent.parent.left;

                // Case 1: if y is red...recolor
                if (y.color == Node.RED)
                {
                    x.parent.color = Node.BLACK;
                    y.color = Node.BLACK;
                    x.parent.parent.color = Node.RED;
                    x = x.parent.parent;
                } // Case 2: if y is black and x is a left child
                else if (x == x.parent.left)
                {
                    // rightRotate around x's parent
                    x = x.parent;
                    rotateRight(x);
                } // Case 3: if y  is black and x is a right child
                else
                {
                    // recolor and rotate around x's grandparent
                    x.parent.color = Node.BLACK;
                    x.parent.parent.color = Node.RED;
                    rotateLeft(x.parent.parent);
                }
            }
        }

        // Color root black at all times
        root.color = Node.BLACK;

    }
    public void delete(T key){
        Node z= search(key);
        Node y=nil;
        Node x=nil;
        if(z==nil) {
            System.out.println("ERROR, word does not exist!");
            return;
        }
        else {
            treeSize--;
            if(z.left==nil || z.right== nil) // case 1 (zero or 1 children) we remove z directly yaaaay
                y=z;
            else{
                y=treeSuccessor(z.left);  //case 2 (2 children) we must find the succesor
            }
            x = y.right;
            x.parent = y.parent; // link x's parent to y's parent

	    
		if (y.parent==nil)// If y's parent is nil then x is the root
			root = x;

		else if (y.parent.left!=nil && y.parent.left == y) // else if y is a left child set x to be y's left sibling
			y.parent.left = x;

		else if (y.parent.right!=nil && y.parent.right == y) // else if y is a right child, set x to be y's right sibling
			y.parent.right = x;
                if (y != z){ 
			z.key = y.key;
		}

		
		// RedBlackTree properties so call deleteFixup()
		if (y.color == Node.BLACK)   //if the node to be deleted is red then we remove it directly else we need to fix the whole tree
			deleteFixup(x);
                System.out.println("DELETED");
	}
        
    }
    
    public void deleteFixup(Node x){ 
        Node s;
        while (x != root && x.color == Node.BLACK){

	    // if x is a left child
	    if (x == x.parent.left){
                s = x.parent.right; // s = x's sibling
                if (s.color == Node.RED){ //case1: s is red
		    s.color = Node.BLACK;
		    x.parent.color = Node.RED;
		    rotateLeft(x.parent);
		    s = x.parent.right; // set s with the new sibling --> now it becomes case 2
		}
                if (s.left.color == Node.BLACK && s.right.color == Node.BLACK){//case2: s's children are both black(so rasict :s)
		    s.color = Node.RED;
		    x = x.parent;
		}
                else{
		    // Case 3: s's right child is black and left ir red
		    if (s.right.color == Node.BLACK){
		        s.left.color = Node.BLACK;
		        s.color = Node.RED;
			rotateRight(s);
			s = x.parent.right; //set the new sibling
		    }
		    // Case 4: s = black, s.right = red
		    s.color = x.parent.color;
		    x.parent.color = Node.BLACK;
		    s.right.color = Node.BLACK;
		    rotateLeft(x.parent);
		    x = root;
		    }
			
            }
            // if x is it's parent's right child
	    else{
		s = x.parent.left; //s = x's sibling
                // Case 1, s's color is red
		if (s.color == Node.RED){
		s.color = Node.BLACK;
		x.parent.color = Node.RED;
		rotateRight(x.parent);
		s = x.parent.left; //set the new sibling
		}
		// Case 2, both of s's children are black
		if (s.right.color == Node.BLACK && s.left.color == Node.BLACK){
		    s.color = Node.RED;
		    x = x.parent; //recurse
		}
                 // Case 3 / Case 4
		else{
		    // Case 3, s's left child is black
		    if (s.left.color == Node.BLACK){
			s.right.color = Node.BLACK;
			s.color = Node.RED;
			rotateLeft(s);
			s = x.parent.left;
		    }
  		    // Case 4, s = black, and s.left = red
		    s.color = x.parent.color;
		    x.parent.color = Node.BLACK;
		    s.left.color = Node.BLACK;
		    rotateRight(x.parent);
		    x = root;
		    }
		}
        }
    } 
        public Node search(T key)
    {
       
        Node current = root;
        // While we haven't reached the end of the tree
        while (current != nil)
        {
            if (current.key.equals(key))
            {
                System.out.println("Found");
                return current;
            } 
            else if (current.key.compareTo(key) < 0) // go right if bigger
            {
                current = current.right;
            } // go left if smaller
            else
            {
                current = current.left;
            }
        }
         
        return nil;
    }
    
     public void printHeight() {
        
         int h=height(root);
         System.out.println("height of tree is " + h);
        
     }
    private int height(Node x) {
        if (x == nil) return 0;
        
        return 1 + Math.max(height(x.left), height(x.right));    
    }   
    private Node treeSuccessor(Node z){
        while(z.right != nil)
            z=z.right;
        
        return z;
    }
        
    public void rotateLeft(Node x)
    {
        Node temp = x.right;
        x.right = temp.left; // turn temp's left subtree into x's right subtree
        if (temp.left != nil)
        {
            temp.left.parent = x;
        }
        temp.parent = x.parent;  // link x's parent to temp's parent
        if (x.parent == nil) // if x was a root
        {
            root = temp;
        } else if (x == x.parent.left) // if x was a left child
        {
            x.parent.left = temp;
        } else // if x was a right child
        {
            x.parent.right = temp;
        }
        temp.left = x;
        x.parent = temp;
    }

    public void rotateRight(Node x)
    {
        Node temp = x.left;
        x.left = temp.right; // turn temp's right subtree into x's left subtree
        if (temp.right != nil)
        {
            temp.right.parent = x;
        }
        temp.parent = x.parent;  // link x's parent to temp's parent
        if (x.parent == nil) // if x was a root
        {
            root = temp;
        } else if (x == x.parent.right) // if x was a rght child
        {
            x.parent.right = temp;
        } else // if x was a left child
        {
            x.parent.left = temp;
        }
        temp.right = x;
        x.parent = temp;
    }

   
public void printSize(){
    System.out.println("There are " + treeSize + " words in the dictionary.");
}

}
