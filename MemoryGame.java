import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.applet.*;
import java.lang.Math;
import java.io.*;
//Memory Game
//Plays a game of Memory 
//Date Modified: Fri June 14th
//Last Modified By: Vic Tong

public class MemoryGame extends Applet implements ActionListener{
  //initializes all the global variables
  JLabel title;
  JButton next,save;
  Save s=new Save();
  JButton[][] buttons;
  int counter=0,clicked=0,previous=0,rand,count=0,something=0,custom=0;
  int i=0,j=0,check=0;
  String click=null,command=null,command2="hello";
  String[] cards=new String[]{"apple","banana","orange","grape","mango","kiwi","strawberry","peach"};
  int[] cardsCounter=new int[8];
  String[][] grid=new String[4][4];
  boolean[][] track=new boolean[4][4];
  Panel g=new Panel(new GridLayout(4,4));
  boolean match=false,play;
  Panel center,card1,card2,card3,card4,card5;
  CardLayout cdlayout=new CardLayout();
  
  public void init(){
    //initializes all the JButtons and JLabels
    //adds the buttons/labels
    center=new Panel();
    center.setLayout(cdlayout);
    setBackground(Color.black);//sets the background colour
    
    //initializes all the panels
    intro();
    openSaveFile();
    choice();
    startGame();
    playAgain();
    //adds the center panel and sets the layout of the applet viewer
    setLayout(new BorderLayout());
    add("Center",center);
  }
  
  public void actionPerformed(ActionEvent e){
    
    if(e.getActionCommand().equals("Next"))//once the user clicks next
      cdlayout.show(center,"openSaveFile");//it shows the save file screen
    
    if(custom==0){
      command=e.getActionCommand();
    }
    
    if(command.equals("yes")){
      //if the user has a save file from a previous game
      if(custom==0)
        openSave();//imports all the information from the file into the needed variables
      cdlayout.show(center,"game");//shows the game screen 
      if(custom>0)
        //plays the game using a method and passing down the action command
        game(e.getActionCommand());
      
      custom++;//adds one to custom to ensure that command stays the same
    }
    else if(command.equals("no")){
      //user doesn't have a save file
      cdlayout.show(center,"choice");//shows the choice screen 
        
      if(custom==1||custom==2){
        command2=e.getActionCommand();
        
        if(command2.equals("fruits")){
          //all the pairs are fruits
          cards=new String[]{"apple","banana","orange","grape","mango","kiwi","strawberry","peach"};
          cdlayout.show(center,"game");//shows the game screen
        }
        else if(command2.equals("school")){
          //all the pairs are school materials
          cards=new String[]{"pencil","binder","eraser","paper","ruler","calculator","computer","scissors"};
          cdlayout.show(center,"game");//shows the game screen
        }
        else if(command2.equals("animals")){
          //all the pairs are annimals
          cards=new String[]{"cow","chicken","cat","dog","fox","tiger","bird","elephant"};
          cdlayout.show(center,"game");//shows the game screen
        }
        scramble(grid,cards);//scrambles the grid after the user chooses their pack
      }
      custom++;//ensures command2 stays the same

    }
    
    if(custom>2){//ensures that the game screen only appears after all the screens appear
      cdlayout.show(center,"game");//shows the game screen
      game(e.getActionCommand());//plays the game
    }
    
    //this is the play again screen
    if(e.getActionCommand().equals("Yes")){
      //if the user wants to play again 
      //everything is reset and goes back the first screen
      cdlayout.show(center,"intro");
      for(int i=0;i<4;i++){
        for(int j=0;j<4;j++){
          buttons[i][j].setIcon(createImageIcon("blank.gif"));
        }
      }
      counter=0;
      custom=0;
      command2=null;
      command=null;
    }
    else if(e.getActionCommand().equals("No"))
      //if the user doesn't want to play again, the console closes
      System.exit(0);
     
  }//actionPerformed
  
  //Screen1
  public void intro(){
    //Pre:center is a cdLayout, card1 is declared
    //Post: Initializes the intro screen
    card1=new Panel();
    
    JLabel title=new JLabel("Welcome to Memory");
    title.setForeground(Color.white);
    title.setFont(new Font("Jokerman",Font.BOLD,40));
    
    JButton next=new JButton("Next");
    next.addActionListener(this);
    next.setActionCommand("Next");
    
    JLabel instructions[]=new JLabel[5];
    String steps[]=null;
    try{
      steps=s.instructions();
    }
    catch(IOException e){
      ;
    }
    
    card1.add(title);
    card1.add(next);
    for(int i=0;i<5;i++){
      instructions[i]=new JLabel(steps[i]);
      instructions[i].setForeground(Color.white);
      instructions[i].setFont(new Font("Jokerman",Font.BOLD,20));
      card1.add(instructions[i]);
    }
    
    center.add("intro",card1);
  }//intro method
  
  
  //Screen2
  public void openSaveFile(){
    card2=new Panel();
    
    JLabel title=new JLabel("Do you have a save file?");
    title.setForeground(Color.white);
    
    //no button
    JButton no=new JButton("No");
    no.addActionListener(this);
    no.setActionCommand("no");
    
    //yes button
    JButton yes=new JButton("Yes");
    yes.addActionListener(this);
    yes.setActionCommand("yes");
    
    card2.add(title);
    card2.add(yes);
    card2.add(no);
    
    center.add("openSaveFile",card2);
  }//openSaveFile method

    
  //Screen3
  public void choice(){
    //Pre: center is cdLayout, card3 is declared
    //Post: Initializes the choice screen
    card3=new Panel();
    
    JLabel title=new JLabel("Which pack do you want to play with?");
    title.setForeground(Color.white);
    
    JButton school=new JButton("School");
    school.addActionListener(this);
    school.setActionCommand("school");
    
    JButton animals= new JButton("Animals");
    animals.addActionListener(this);
    animals.setActionCommand("animals");
    
    JButton fruits=new JButton("Fruits");
    fruits.addActionListener(this);
    fruits.setActionCommand("fruits");
    
    card3.add(title);
    card3.add(fruits);
    card3.add(animals);
    card3.add(school);
    
    center.add("choice",card3);//adds the choice panel
    
  }//choice method
  
  //Screen4
  public void startGame(){
    //Pre:center is cdLayout, card4 is declared
    //Post: Initializes the game panel
    card4=new Panel();
    
    title=new JLabel("Welcome to Memory");
    title.setForeground(Color.white);
    title.setFont(new Font("Impact",Font.BOLD,50));
    
    
    JButton save=new JButton("Save and Close");
    save.addActionListener(this);
    save.setActionCommand("save");
    
    next=new JButton("Next");
    next.addActionListener(this);
    next.setActionCommand("next");
    next.setEnabled(false);
    
    card4.add(title);
    
    buttons=new JButton[4][4];
    for(int i=0;i<4;i++){
      for(int j=0;j<4;j++){
        buttons[i][j]=new JButton(createImageIcon("blank.gif"));
        buttons[i][j].addActionListener(this);
        buttons[i][j].setActionCommand(Integer.toString(i)+Integer.toString(j));
        buttons[i][j].setPreferredSize(new Dimension(90,90));
        g.add(buttons[i][j]); 
      }
    }
    
    card4.add(g);
    card4.add(next);
    card4.add(save);
    
    center.add("game",card4);
  }
  
  //Screen5
  public void playAgain(){
    //Pre: center is cdLayout, card5 is declared
    //Post: Initializes the playAgain screen
    card5=new Panel();

    JLabel message=new JLabel("Do you want to play again?");
    message.setForeground(Color.white);
    message.setFont(new Font("Times New Roman",Font.BOLD,30));
    
    JLabel times=new JLabel("You have guessed "+rand+" times");
    times.setForeground(Color.white);
    times.setFont(new Font("Times New Roman",Font.BOLD,30));
    
    JButton no=new JButton("No");
    no.addActionListener(this);
    no.setActionCommand("No");
    
    JButton yes=new JButton("Yes");
    yes.addActionListener(this);
    yes.setActionCommand("Yes");
    
    card5.add(times);
    card5.add(message);
    card5.add(yes);
    card5.add(no);
    
    center.add("playAgain",card5);
  }
    

  
  public void game(String command){
    //Pre: 2 numbers less than 4
    //Pre: "Next" or "Save"
    //Post: Plays the game
    System.out.println("click "+click);
    if(!(command.equals("yes")||command.equals("no"))){
      turnOff(check,something,i,j,true);
      String nex="hello";
      next.setEnabled(false);
      
      try{
        //puts the location of the card in i and j
        i=Integer.parseInt(command.substring(0,1));
        j=Integer.parseInt(command.substring(1));
      }
      catch(NumberFormatException e){
        //if the command isn't a number
        nex=command;
      }
      System.out.println("grid "+grid[i][j]);
      //set the card that was clicked to the according image
      buttons[i][j].setIcon(createImageIcon(grid[i][j]+".png"));
      
      //prevents the user from clicking the same card twice in a row
      buttons[i][j].removeActionListener(this);
      
      if(counter==1){
        //after 2 cards are selected
        turnOff(check,something,i,j,false);
        if(click.equals(grid[i][j])){
          //if the cards cards
          title.setText("Match!");
          buttons[check][something].removeActionListener(this);
          buttons[i][j].removeActionListener(this);
          match=true;
          track[i][j]=true;
          track[check][something]=true;
          clicked++;
        }
        else{
          //if the cards don't cards
          match=false;
          title.setText("Not a match");
        }
      }
      
      
      if(counter==0){
        //saves the first card clicked
        check=i;
        something=j;
      }
      else{
        //saves the second card clicked
        previous=i;
        count=j;
        next.setEnabled(true);
      }
      
      if(nex.equals("next")){
        //allows the cards to be clicked again
        buttons[check][something].addActionListener(this);
        buttons[i][j].addActionListener(this);
        
        if(match){
          //if the cards match, set the icons as the pair
          buttons[i][j].setIcon(createImageIcon(click+".png"));
          buttons[check][something].setIcon(createImageIcon(click+".png"));
          buttons[check][something].removeActionListener(this);
          buttons[i][j].removeActionListener(this);
        }//if(match)
        else{
          //if the cards don't match, they're set back to blank
          buttons[check][something].setIcon(createImageIcon("blank.gif"));
          buttons[previous][count].setIcon(createImageIcon("blank.gif"));
        }//else
      }//if
      else if(nex.equals("save")){
        //if the user clicks Save and Close
        try{
          //saves all the variables from the game into a file
          s.save(grid,cards,track,check,something,previous,count,counter,rand,click);
        }
        catch(IOException e){
          ;
        }
        System.exit(0);
      }
      
      counter++;
      click=grid[i][j];//puts the card clicked into click so it can be compared later
      if(nex.equals("next")){
        counter=0;//resets the counter
      }
    }//if command isn't yes or no  
    
    //prints out the total number of guesses once all the cards have been cardsed
    rand++;
    if(clicked==8){
      playAgain();
      cdlayout.show(center,"playAgain");
    }
    

  }//game method
  
  public void scramble(String[][]grid,String[] cards){
    String[] cardsCopy=new String[8];
    boolean out=false;
    int count;
    //Pre: cards is all different words
    //Post: scramble all the words from cards into grid
    for(int i=0;i<8;i++){
      cardsCounter[i]=0;
      cardsCopy[i]=cards[i];
    }
    
    for(int i=0;i<4;i++){
      for(int j=0;j<4;j++){
        do{
          count=0;
          rand=(int)(Math.random()*8);
          for(int k=0;k<8;k++){
            if(cardsCopy[k].equals("not"))
              count++;
            
            if(count==8)
              out=true;
          }
          
          if(out)
            break;
        }while(cardsCopy[rand].equals("not"));
        
        cardsCounter[rand]++;
        
        grid[i][j]=cardsCopy[rand];
        
        if(cardsCounter[rand]==2)
          cardsCopy[rand]="not";
        
      }
      
    }
  }//scramble method
  
  
  public void turnOff(int x,int y,int k,int l,boolean off){
    //Pre: All integers are less than 4
    //Post: Depending on the boolean
    //Post:Turns on/off all the buttons but specified in parameters
    for(int i=0;i<4;i++){
      for(int j=0;j<4;j++){
        if((i!=x||j!=y)&&(i!=k||j!=l))
          buttons[i][j].setEnabled(off);
      }
    }
  }//turnOff method
  
  public void openSave(){
    try{
      //puts everything from the file into the needed variables 
      track=s.openTrack();
      grid=s.openGrid();
      cards=s.openWordList();
      counter=s.openCounter();
      cardsCounter=s.openButton();
      rand=s.openRand();     
      click=s.openClick();
      System.out.println(click);
    }
    catch(IOException f){//catches the IOException
      ;//nothing really needs to happen if it's caught
    }
    //inserts all the information from the file into the programs variables
    if(counter==2){
      counter=0;
      check=cardsCounter[0];
      something=cardsCounter[1];
      previous=cardsCounter[2];
      count=cardsCounter[3];
      i=cardsCounter[0];
      j=cardsCounter[1];
      turnOff(check,something,previous,count,false);
      buttons[cardsCounter[0]][cardsCounter[1]].setIcon(createImageIcon(grid[cardsCounter[0]][cardsCounter[1]]+".png"));
      buttons[cardsCounter[2]][cardsCounter[3]].setIcon(createImageIcon(grid[cardsCounter[2]][cardsCounter[3]]+".png"));
      click=grid[cardsCounter[0]][cardsCounter[1]];
      next.setEnabled(true);
    }//if counter is 1
    else if (counter==1){
      check=cardsCounter[2];
      something=cardsCounter[3];
      buttons[cardsCounter[2]][cardsCounter[3]].setIcon(createImageIcon(grid[cardsCounter[2]][cardsCounter[3]]+".png"));
      click=grid[cardsCounter[2]][cardsCounter[3]];
      track[check][something]=false;
    }//if counter is 0
    else{
      counter=0;//resets the counter
    }//else
    
    for(int i=0;i<4;i++){
      for(int j=0;j<4;j++){
        if (track[i][j]){
          buttons[i][j].setIcon(createImageIcon(grid[i][j]+".png"));
          buttons[i][j].removeActionListener(this);
        }
        
      }
    }
  }//openSave method
  
  protected static ImageIcon createImageIcon (String path)
  {
    java.net.URL imgURL = MemoryGame.class.getResource (path);
    if (imgURL != null)
    {
      return new ImageIcon (imgURL);
    }
    else
    {
      System.err.println ("Couldn't find file: " + path);
      return null;
    }
  } //createImageIcon 
}