//this will be the binary tree build

public class Tree {

   //yes should be yes, no should be no
   
   String bottom;
   Tree yes;
   Tree no;
   
   public Tree(String node) {
      this(node, null, null);                         //makes a basic tree
   }
   
   public Tree(String node, Tree yes, Tree no) {     //makes a tree with a question/animal as the node and a path for yes and for no
      this.bottom = node;
      this.yes = yes;
      this.no = no;
   }
 
  //getters
   
   public Tree getYes() {
      return yes;                            //returns the yes branch (tree)
   }
   
   public Tree getNo() {
      return no;                             //returns the no branch (tree)
   }
   
   public String getQuestion() {
      return bottom;                         //returns the node (question/animal)
   }
   
   public void print() {
      System.out.println(bottom);
   }
}