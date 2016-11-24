package finalProject;

import java.io.*;
import javax.swing.*;

public class LoginData implements Serializable
{
  private JTextField usernameField;
  private JPasswordField passwordField;
  private String username, password;
  
  LoginData(JTextField username, JPasswordField password)
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
