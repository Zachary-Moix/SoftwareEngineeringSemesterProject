package main;

import java.io.Serializable;

import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class CreateAccountData implements Serializable
{
  private JTextField usernameField;
  private JPasswordField passwordField;
  private String username, password;
  
  CreateAccountData(JTextField username, JPasswordField password)
  {
    this.usernameField = username;
    this.passwordField = password;
  }
  
  String getUsername()
  {
    username = usernameField.getText();
    
    return username;
  }
  String getPassword()
  {
    password = passwordField.getText();
    
    return password;
  }
  
  JTextField getUsernameField()
  {
    return usernameField;
  }
  JPasswordField getPasswordField()
  {
    return passwordField;
  }
}