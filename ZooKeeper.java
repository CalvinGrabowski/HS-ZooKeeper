//what to do

//This will ask the user and figure out what the user is wanting

import java.util.*;
import java.io.*;

public class ZooKeeper {

   public static void main(String[] args) throws FileNotFoundException {

      String yesOrNo = null;

      Scanner sc = new Scanner(System.in);
      Scanner line = new Scanner(new File("ZooAnimals.txt")); // this has all the lines of the file and will go through
                                                              // them
      String answer = null;

      Tree animals = new Tree("Does it have fur?", new Tree("bunny"), new Tree("Lizard")); // basic tree
      Tree current = animals; // animals is the tree that gets built off of. Current is the place we are in
                              // that tree

      String placement = ""; // placement is where we are in the tree and helps when we make a new animal

      ZooMaster master = new ZooMaster(); // the ZooMaster controls building the tree

      current = animals;

      String curLine;
      int colon1 = 0;
      int colon2 = 0;

      PrintStream output = new PrintStream(new File("output.txt"));

      while (line.hasNext()) { // this adds all the things that were saved from previous trees
         curLine = line.nextLine();
         output.println(curLine);

         for (int i = 0; i < curLine.length(); i++) {
            if (colon1 == 0) {
               if (curLine.substring(i, i + 1).equals(":")) { // finds the first colon in a line
                  colon1 = i;
                  i++;
               }
            }
            if (colon2 == 0) {
               if (curLine.substring(i, i + 1).equals(":")) { // finds the second colon in a line
                  colon2 = i;
               }
            }
         }
         if (colon1 != 0 && colon2 != 0) { // if it doesn't have two colons it is not going to read the line and it will
                                           // skip it

            animals = master.add(curLine.substring(0, colon1), curLine.substring(colon1 + 1, colon2),
                  curLine.substring(colon2 + 1, curLine.length()), animals);
         }
         colon1 = 0; // this allows me to write notes if I wanted on the file and as long as there
                     // isn't two colons it won't cause any problems
         colon2 = 0;

      }

      System.out.println("reply 'back' to go back to the beginning, or type 'quit' to exit");
      System.out.println("Think of an animal and then answer those questions for the animal");
      System.out.println();

      PrintStream mainFile = new PrintStream("ZooAnimals.txt");
      Scanner outputLine = new Scanner(new File("output.txt")); // I use output to save all the data for zoo animals so
                                                                // I can copy it over change it and then paste it back
                                                                // into zoo animals

      while (outputLine.hasNext()) {
         mainFile.println(outputLine.nextLine()); // this saves all the work into ZooAnimals setting up adding the next
                                                  // few lines
      }

      boolean running = true;
      while (running) {

         // System.out.println(placement); //shows location in the tree
         if (placement.equals("")) {
            current = animals;
         }

         if ((current.getYes() != null && current.getNo() != null)) {
            current.print();
            // prints question or animal
            answer = sc.nextLine();
         } else {
            System.out.println("Were you thinking of a " + current.getQuestion());
            if (sc.nextLine().substring(0, 1).equals("y")) { // shows that it can guess your animal
               System.out.println("CONGRADULATIONS!!!! I have guessed your animal!");
               running = false;
            } else {
               animals = addAnimal(animals, placement, master, mainFile);
               placement = "";
            }
         }

         if (answer.equals("quit")) {
            running = false; // ends the while loop
         }
         if (answer.equals("back")) {
            current = animals; // goes back to the beginning
            placement = "";
         }
         if (answer.equals("print")) {
            ZooKeeper.print(animals, 1, "");
         }
         if (answer.equals("yes") && current.getYes() != null) {

            current = current.getYes(); // this moves down the yes branch and tracks that progress in placement
            placement = placement + "y";

         }
         if (answer.equals("no") && current.getNo() != null) {

            current = current.getNo(); // this moves down the no branch and tracks that progress in placement
            placement = placement + "n";

         }
      }

   }

   private static void print(Tree root, int level, String code) {
      if (root != null) {
         System.out.println("Question #" + level + "." + code + "             " + root.getQuestion());
         print(root.getYes(), level + 1, code + "y");

         // it counts up one every time it goes to the right or left showing the
         // different levels and adding that many spaces
         print(root.getNo(), level + 1, code + "n");
      }
   }

   public static Tree addAnimal(Tree animals, String code, ZooMaster master, PrintStream mainFile) { // this allows new
                                                                                                     // animals to be
                                                                                                     // added

      Scanner sc = new Scanner(System.in);
      System.out.println("Do you want to add your animal?"); // added this in the method so if I wanted to use it
                                                             // elsewhere I wouldn't have to ask them this again
      if (sc.nextLine().equals("yes")) {

         System.out.println("what animal were you thinking of?");
         String newAnimal = sc.nextLine();
         System.out.println(
               "and what would be a good question to distinguish this animal? (comparing to the animal you said it wasn't)");
         String newQuestion = sc.nextLine(); // these are all questions for the new animal to have a way to identify it
         System.out.println(
               "for the question: (" + newQuestion + ") was the " + newAnimal + " a yes or a no to that question?");
         String yesOrNo = sc.nextLine();
         System.out.println("Adding... press enter to continue");

         animals = master.add(code + yesOrNo.substring(0, 1), newQuestion, newAnimal, animals); // adds the new question
                                                                                                // and animal into the
                                                                                                // main tree
         mainFile.println(code + yesOrNo.substring(0, 1) + ":" + newQuestion + ":" + newAnimal); // adds the animal to
                                                                                                 // the main file so you
                                                                                                 // can reload the main
                                                                                                 // tree
      }

      return animals;
   }

}