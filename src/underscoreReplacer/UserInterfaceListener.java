package underscoreReplacer;

import java.awt.Color;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.Timer;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

public class UserInterfaceListener implements ActionListener, DocumentListener, KeyListener, FocusListener {

//  UserInterface gui = new UserInterface();
//
//  private JTextField textfieldUserInput = gui.getTextfieldUserInput();
//  private JTextField textfieldOutputText = gui.getTextfieldUserInput();
//  private JButton copyText = gui.getCopyText();
//  private JLabel labelCharacterCount = gui.getLabelCharacterCount();
//  private JLabel labelCopyState = gui.getLabelCopyState();
//  private Boolean textfieldPlaceholderActive = gui.getTextfieldPlaceholderActive();

  public UserInterfaceListener() {

    textfieldUserInput.getDocument().addDocumentListener(new DocumentListener() { //Set up the text field to check for changes
      @Override
      public void changedUpdate(DocumentEvent e) {
        update();
      }

      @Override
      public void removeUpdate(DocumentEvent e) {
        update(); //Deletion
      }

      @Override
      public void insertUpdate(DocumentEvent e) {
        update(); //Addition
      }

      //Replace underscores with spaces
      private void update() {
        long underscoreCount = textfieldUserInput.getText().chars().filter(ch -> ch == '_').count(); //Filter underscores
        textfieldUserInput.setText(String.valueOf(underscoreCount)); //Count total underscores

        if (!textfieldUserInput.getText().equals("Enter Text")) {
          labelCharacterCount.setText(textfieldUserInput.getText().length() + "");
        }

        if ((!(textfieldUserInput.getText().equals("Enter Text")) && (textfieldUserInput.getText().length() > 0))) {
          textfieldOutputText.setText(textfieldUserInput.getText().replaceAll("_", " "));
        } else if (((textfieldUserInput.getText().length() < 1))) {
          textfieldOutputText.setText(null);
        }
      }
    });
  }

  //<editor-fold defaultstate="collapsed" desc="Action Events">
  @Override
  public void actionPerformed(ActionEvent ae) {
    //<editor-fold defaultstate="collapsed" desc="Copy to Clipboard state">
    if ("CopyText".equals(ae.getActionCommand())) {
      Timer timer = new Timer(2000, event -> {
        labelCopyState.setText(null);
      });

      if ((textfieldOutputText.getText().equals("")) || (textfieldOutputText.getText() == null)) {
        labelCopyState.setText("Nothing copied");
        timer.start();

      } else if ((!textfieldOutputText.getText().equals("Enter Text")) && (textfieldUserInput.getText().length() > 0)) {
        labelCopyState.setText("Copied to Clipboard");
        timer.start();

        StringSelection stringSelection = new StringSelection(textfieldOutputText.getText());
        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        clipboard.setContents(stringSelection, null);
      }
    }
    //</editor-fold>
  }

  @Override
  public void keyTyped(KeyEvent e) {
    System.out.println("keyTyped has not been coded yet.");
  }

  @Override
  public void keyPressed(KeyEvent e) {
    System.out.println("keyPressed has not been coded yet.");
  }

  @Override
  public void keyReleased(KeyEvent e) {
    System.out.println("keyReleased has not been coded yet.");
  }

  public void focusGained(FocusEvent e) {
    if (textfieldPlaceholderActive) { //If Placeholder ("Enter Text") showing before focus gained
      textfieldUserInput.setText(null); //Set text to null
      textfieldUserInput.setForeground(Color.black); //Set text to black
      textfieldPlaceholderActive = false; //Set to inactive while element is focused
    }
  }

  public void focusLost(FocusEvent e) {
    if (textfieldUserInput.getText().isEmpty()) { //If enterText textfield empty
      textfieldUserInput.setText("Enter Text"); //Set text to Placeholder ("Enter Text")
      textfieldUserInput.setForeground(Color.lightGray); //Set text to light gray
      textfieldPlaceholderActive = true; //Set to active when element focus is lost
    }
  }
  //</editor-fold>
}
