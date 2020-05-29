package removeunderscore;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.Timer;
import javax.swing.border.EmptyBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

public class RemoveUnderscore extends JFrame implements ActionListener, KeyListener, FocusListener {

  private JTextField enterText; //Text user enters
  private JTextField outputText; //Text with removed underscores
  private final String placeholder = "Enter Text"; //Placeholder text

  private JLabel labelCopyState; //Text to display if copied

  private boolean showingPlaceholder = true; //Switch for Placeholder text

  public static void main(String[] args) {
    RemoveUnderscore prg = new RemoveUnderscore();
  }

  public RemoveUnderscore() {
    model();
    view();
    controller();
  }

//<editor-fold defaultstate="collapsed" desc="Action Events">
  @Override
  public void actionPerformed(ActionEvent ae) {
    if ("CopyText".equals(ae.getActionCommand())) { //If copy text button is pressed
      StringSelection stringSelection = new StringSelection(outputText.getText()); //Transfer content in outputText to stringSelection
      Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard(); //Initialize the variable clipboard and assign system clipboard to it
      clipboard.setContents(stringSelection, null); //Set system clipboard content to text in stringSeleciton (outputText)

      if ((outputText.getText().equals("")) || (outputText.getText() == null)) {
        labelCopyState.setForeground(Color.black); //Set text to black
        labelCopyState.setText("Nothing copied");

        Timer timer = new Timer(2000, (ActionEvent e) -> {
          labelCopyState.setText(null);
        });

        timer.setRepeats(false);
        timer.start();
      } else if ((!outputText.getText().equals("Enter Text")) && (enterText.getText().length() > 0)) {
        labelCopyState.setForeground(Color.blue); //Set text to blue
        labelCopyState.setText("Copied to Clipboard");

        Timer timer = new Timer(2000, (ActionEvent e) -> {
          labelCopyState.setText(null);
        });
        timer.setRepeats(false);
        timer.start();
      }
    }
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

  @Override
  public void focusGained(FocusEvent e) {
    if (showingPlaceholder) { //If Placeholder ("Enter Text") showing before focus gained
      enterText.setText(null); //Set text to null
      enterText.setForeground(Color.black); //Set text to black
      showingPlaceholder = false; //Set to inactive while element is focused
    }
  }

  @Override
  public void focusLost(FocusEvent e) {
    if (!showingPlaceholder) { //If enterText textfield empty
      enterText.setText(placeholder); //Set text to Placeholder ("Enter Text")
      enterText.setForeground(Color.lightGray); //Set text to light gray
      showingPlaceholder = true; //Set to active when element focus is lost
    }
  }
//</editor-fold>

  private void model() {

  }

  private void view() {
    Font arial = new Font("Arial", Font.PLAIN, 14);

    GridBagConstraints gbc = new GridBagConstraints();
    gbc.fill = GridBagConstraints.BOTH;

    JPanel outPanel = new JPanel(); //Outer Window
    outPanel.setBorder(new EmptyBorder(15, 15, 15, 15)); //Padding around (N, W, S, E)
    outPanel.setLayout(new BoxLayout(outPanel, BoxLayout.X_AXIS));

    JPanel cenPanel = new JPanel(); //Inner Window
    cenPanel.setLayout(new BorderLayout()); //Set window to contain 5 regions

    JPanel botPanel = new JPanel(new GridBagLayout()); //Inner Window

    JPanel textContainer = new JPanel(); //Inner Center Window
    textContainer.setLayout(new GridLayout(1, 3)); //Rows, columns

    JPanel txtOne = new JPanel();
    JPanel txtTwo = new JPanel();
    JPanel txtThr = new JPanel();

    JPanel buttonContainer = new JPanel(); //Inner Center, Bottom right Window    
    buttonContainer.setLayout(new BoxLayout(buttonContainer, BoxLayout.X_AXIS));

    JLabel labelCharacterCount = new JLabel("0");
    labelCharacterCount.setHorizontalAlignment(JLabel.LEFT);

    JLabel labelCharacters = new JLabel("Characters:"); //Inner Center
    labelCharacters.setHorizontalAlignment(JLabel.RIGHT);

    JLabel labelUnderscoreCount = new JLabel("0");
    labelUnderscoreCount.setHorizontalAlignment(JLabel.LEFT);

    JLabel labelUnderscores = new JLabel("Underscores:"); //Inner Center
    labelUnderscores.setHorizontalAlignment(JLabel.RIGHT);

    labelCopyState = new JLabel();
    labelCopyState.setForeground(Color.blue); //Set text to blue

    textContainer.add(txtOne);
    textContainer.add(txtTwo);
    textContainer.add(txtThr);

    txtOne.add(labelCharacters);
    txtOne.add(labelCharacterCount);
    txtTwo.add(labelCopyState);
    txtThr.add(labelUnderscores);
    txtThr.add(labelUnderscoreCount);

    outputText = new JTextField(); //Text with underscores removed
    outputText.setFont(arial);
    outputText.setMargin(new Insets(3, 3, 3, 3));
    outputText.setEditable(false);
    gbc.weightx = 1.0;
    gbc.gridx = 0;
    gbc.gridy = 0;
    botPanel.add(outputText, gbc); //Set text to south

    enterText = new JTextField();
    enterText.setMargin(new Insets(4, 4, 5, 4));
    enterText.setFont(arial);
    enterText.addFocusListener(this);
    enterText.getDocument().addDocumentListener(new DocumentListener() { //Set up the text field to check for changes

      @Override
      public void changedUpdate(DocumentEvent e) {
        update(); //Does nothing for text changes
      }

      @Override
      public void removeUpdate(DocumentEvent e) {
        update(); //Function call on deletion 
      }

      @Override
      public void insertUpdate(DocumentEvent e) {
        update(); //Function call on addition
      }

      public void update() {
        long underscores = enterText.getText().chars().filter(ch -> ch == '_').count(); //Retrieve text from enterText, split into chracters, filter out '_' and count the occurences
        labelUnderscoreCount.setText(String.valueOf(underscores)); //Set the label to the value of underscores

        if (!enterText.getText().equals("Enter Text")) { //If enterText field does not contain "Enter Text"
          labelCharacterCount.setText(enterText.getText().length() + ""); //Update the character label to entered text length
        }

        if ((!(enterText.getText().equals(placeholder)) && (enterText.getText().length() > 0))) { //If enterText doesn't contain "Enter Text" and length of text entered is greater than 0
          outputText.setText(enterText.getText().replaceAll("_", " ")); //Set outputText to text retrieved from enterText with underscores replaced with a space
        } else if (((enterText.getText().length() < 1))) { //If enterText has no text
          outputText.setText(null); //Set outputText to nothing
        }
      }
    });

    JButton copyText = new JButton("Copy");
    gbc.weightx = 0.01;
    gbc.gridx = 1;
    gbc.gridy = 0;
    copyText.setActionCommand("CopyText");
    copyText.addActionListener(this);
    botPanel.add(copyText, gbc);

    add(outPanel); //Outer container
    outPanel.add(cenPanel); //Inner container for everything below

    cenPanel.add(buttonContainer); //Copy Button
    cenPanel.add(enterText, BorderLayout.NORTH); //Enter Textfield
    cenPanel.add(textContainer, BorderLayout.CENTER); //'Characters' and 'Underscores' label
    cenPanel.add(botPanel, BorderLayout.SOUTH); //Output textfield

//<editor-fold defaultstate="collapsed" desc="Default parameters">
    setTitle("Underscore Remover");
    setSize(550, 155);
    setLocationRelativeTo(null);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setVisible(true);
//</editor-fold>
  }

  private void controller() {
    //System.out.println("controller has not been coded yet.");
  }
}
