import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.applet.*;

public class Introduction extends Applet implements ActionListener{
  boolean out;
  public void screen1(){
    JLabel title;
    JButton yes,no;
    title=new JLabel("Do you have a save file?");
    
    yes=new JButton("Yes");
    yes.addActionListener(this);
    yes.setActionCommand("yes");
    
    no=new JButton("No");
    no.addActionListener(this);
    no.setActionCommand("no");
    
    add(title);
    add(yes);
    add(no);
  }
  
  public void actionPerformed(ActionEvent e){
    String answer=e.getActionCommand();
    
    if(answer.equals("yes")){
      out=true;
    }
    else
      out=false;
  }
  
  public boolean or(){
    return out;
  }
}