//this goes through my tree and adds new branches

public class ZooMaster {

   public ZooMaster() {}
   
   public Tree add(String code, String question, String answer, Tree branch) {                  //this goes through the tree
      
      if(code.length() > 1) {       
         if(code.substring(0,1).equals("y")) {
            return new Tree(branch.getQuestion(), this.add(code.substring(1), question, answer, branch.getYes()), branch.getNo());
         }
         if(code.substring(0,1).equals("n")) {                                                           
            return new Tree(branch.getQuestion(), branch.getYes(), this.add(code.substring(1), question, answer, branch.getNo()));
         }
      }
      
      return addPoint(question, answer, branch, code);                                           //this adds the new section of question and animal to the main tree
   
   }
   
       //this will replace an animal
   public Tree addPoint(String newQuestion, String newAnimal, Tree branch, String yesOrNo) {    //this adds the point in the tree
      Tree newTree;
      if(yesOrNo.substring(0,1).toLowerCase().equals("n")) {                                     //if statement chooses if the new tree goes in the no side or the yes one
         newTree = new Tree(newQuestion, branch, new Tree(newAnimal));
      }
      else {
         newTree = new Tree(newQuestion, new Tree(newAnimal), branch);
      }
      return newTree;
   }
}