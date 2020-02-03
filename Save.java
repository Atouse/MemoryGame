//Vic Tong
//Save class
//Last Modified: 
//Saves the game and stores all the variables in a file called "Memory.txt"
import java.io.*;
import java.util.*;

public class Save 
{
  public void save(String[][] grid,String[] wordList,boolean[][] track,int x,int y,int previous,int count,int counter,int rand,String click) throws IOException{
    PrintWriter output=new PrintWriter(new FileWriter("Memory.txt"));
    
    output.println("grid");//marks the grid variable
    for(int i=0;i<4;i++){
      for(int j=0;j<4;j++){
        output.println(grid[i][j]);
      }
    }
    
    output.println("match");//marks the word list variable
    for(int i=0;i<8;i++){
      output.println(wordList[i]);
    }
    
    output.println("track");//marks the track variable
    for(int i=0;i<4;i++){
      for(int j=0;j<4;j++){
        output.println(track[i][j]);
      }
    }
    
    output.println("counter");//marks the button variables
    output.println(counter);
    
    output.println("buttons");
    output.println(x);
    output.println(y);
    
    output.println(previous);
    output.println(count);
    
    output.println("rand");
    output.println(rand);
    
    output.println("click");
    output.println(click);
    
    output.close();
  }
  
  public String openClick() throws IOException{
    BufferedReader input=new BufferedReader(new FileReader("Memory.txt"));
    String line;
    do{
      line=input.readLine();
    }while(!(line.equals("click")));
    
    line=input.readLine();
    return line;
  }
  
  
  public int[] openButton() throws IOException{
    BufferedReader input=new BufferedReader(new FileReader("Memory.txt"));
    String line;
    int[] buttons=new int[4];
    do{
      line=input.readLine();
    }while(!(line.equals("buttons")));
    
    for(int i=0;i<4;i++){
      line=input.readLine();
      buttons[i]=Integer.parseInt(line);
    }
    return buttons;
  }
  
  public int openRand() throws IOException{
    BufferedReader input=new BufferedReader(new FileReader("Memory.txt"));
    String line;

    do{
      line=input.readLine();
    }while(!(line.equals("rand")));
    
    line=input.readLine();
    return Integer.parseInt(line);
  }
  public int openCounter() throws IOException{
    BufferedReader input=new BufferedReader(new FileReader("Memory.txt"));
    String line;
    
    do{
      line=input.readLine();
    }while(!(line.equals("counter")));
    
    line=input.readLine();
    return Integer.parseInt(line);
  }
  
  public void openButtons() throws IOException{
    BufferedReader input=new BufferedReader(new FileReader("Memory.txt"));
    MemoryGame m=new MemoryGame();
    String line;
    int x,y,previous,count,counter;
    
    do{
      line=input.readLine();
    }while(!(line.equals("clicked")));
    
    line=input.readLine();
    counter=Integer.parseInt(line);
    
    if(counter==1){
      line=input.readLine();
      x=Integer.parseInt(line);
      
      line=input.readLine();
      y=Integer.parseInt(line);
      
      line=input.readLine();
      previous=Integer.parseInt(line);
      
      line=input.readLine();
      count=Integer.parseInt(line);
      
      m.turnOff(x,y,previous,count,false);
    }
    else{
      line=input.readLine();
      x=Integer.parseInt(line);
      
      line=input.readLine();
      y=Integer.parseInt(line);
    }
    
  }
  
  
  
  
  public String[][] openGrid() throws IOException{
    BufferedReader input=new BufferedReader(new FileReader("Memory.txt"));
    String[][]grid=new String[4][4];
    String line;
    
    do{
      line=input.readLine();
    }while(!(line.equals("grid")));
    
    for(int i=0;i<4;i++){
      for(int j=0;j<4;j++){
        line=input.readLine();
        grid[i][j]=line;
      }
    }
    return grid;
  }
  
  public String[] openWordList() throws IOException{
    BufferedReader input=new BufferedReader(new FileReader("Memory.txt"));
    String wordList[]=new String[8];
    String line;
    
    do{
      line=input.readLine();
    }while(!(line.equals("match")));
    
    for(int i=0;i<8;i++){
      line=input.readLine();
      wordList[i]=line;
    }
  return wordList; 
    
  }
  public boolean[][] openTrack() throws IOException{
    BufferedReader input=new BufferedReader(new FileReader("Memory.txt"));
    boolean[][]track=new boolean[4][4];
    String line;
    
    do{
      line=input.readLine();
    }while(!(line.equals("track")));
    
    for(int i=0;i<4;i++){
      for(int j=0;j<4;j++){
        line=input.readLine();
        track[i][j]=Boolean.parseBoolean(line);
      }
    }
    return track;
    
  }
  
  public String[] instructions() throws IOException{
    BufferedReader input=new BufferedReader(new FileReader("Instructions.txt"));
    
    String instructions[]=new String[5];
    String line;
    for(int i=0;i<5;i++){
      line=input.readLine();
      instructions[i]=line;
    }
    return instructions;
  }
     
  
      
      
  
  
}