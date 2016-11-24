package finalProject;

import java.awt.CardLayout;

import javax.swing.JPanel;

import lab7out.CreateAccountPanel;
import lab7out.LoginPanel;
import ocsf.client.AbstractClient;

public class Client extends AbstractClient
{
  private CardLayout c1;
  private JPanel cont;
  
  public Client(CardLayout cardLayout, JPanel container)
  {
    super("localhost",8300);
    
    c1 = cardLayout;
    cont = container;
  }

  @Override
  public void handleMessageFromServer(Object arg0)
  {
    if(((String)arg0).equals("Valid"))
    {
      //System.out.println("Login Complete...");
      
      //change view to lobby
      c1.show(cont, "4");
    }
    
    if(((String)arg0).equals("Incorrect Username or Password"))
    {
        //LoginPanel loginPanel = (LoginPanel)cont.getComponent(1);   //*****************JAKE LET ME KNOW WHAT I NEED TO DO TO SET UP THE PANEL CORRECTLY WITH INVALID CREDENTIALS
        //loginPanel. setErrorMsg ("Incorrect Username or Password");
        //loginPanel.clearTextFields();
        
        c1.show(cont, "2");
    }
    
    if(((String)arg0).equals("New Account Created"))
    {
      //System.out.println("Login with new credentials...");    //*********JAKE LET ME KNOW WHAT I NEED TO DO TO SET UP LOGIN AFTER CREATING A NEW ACCOUNT
     
      c1.show(cont, "2");
    }
    
    if(((String)arg0).equals("Credentials In Use"))
    {
        CreateAccountPanel createAccountPanel = (CreateAccountPanel)cont.getComponent(3);     //******AGAIN WITH THE COMPONENT ISSUE
        //SET UP CREATE ACCOUNT PANEL AFTER CREDENTIALS BEING IN USE
        //createAccountPanel.setErrorMsg ("Credentials In Use");
        //createAccountPanel.clearTextFields();
        
        c1.show(cont, "3");
    }
  }
  
  public void setCardLayout(CardLayout cl)
  {
    this.c1 = cl;
  }
  
  public void setContainer(JPanel container)
  {
    this.cont = container;
  }


  
  public void connectionException (Throwable exception) 
  {
    
  }
  public void connectionEstablished()
  {
    
  }

}



/*//*************************NOTES****************************
 * CARD VIEW NUMBER CORRESPONDENCE:
 * 1: MAIN SCREEN
 * 2: LOGIN SCREEN
 * 3: CREATE ACCOUNT SCREEN
 * 4: LOBBY
 * 5: GAME
 * 6. IP/PORT MOD
 * 
 * 
 */
