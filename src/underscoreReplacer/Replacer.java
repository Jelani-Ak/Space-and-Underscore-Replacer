package underscoreReplacer;

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

class Replacer extends JFrame implements ActionListener, KeyListener, FocusListener {
  
  private JLabel labelCopyState;

  private JTextField textfieldUserInput;
  private JTextField textfieldOutputText;
  
  private boolean textfieldPlaceholderActive = true;
  
  private final String placeholderText = "Enter Text";

  private final Font arial = new Font("Arial", Font.PLAIN, 14);
  
  public static void main(String[] args) {
    Replacer replacer = new Replacer();
  }

  public Replacer() {
    GUI();
  }

//<editor-fold defaultstate="collapsed" desc="Action Events">
  @SuppressWarnings("SpellCheckingInspection")
  @Override
  public void actionPerformed(ActionEvent ae) {
    if ("CopyText".equals(ae.getActionCommand())) { //If copy text button is pressed
      Timer timer = new Timer(2000, (ActionEvent e) -> labelCopyState.setText(null));
      timer.restart();

      if ((textfieldOutputText.getText().equals("")) || (textfieldOutputText.getText() == null)) {
        timer.start();
        labelCopyState.setText("Nothing copied");

      } else if ((!textfieldOutputText.getText().equals("Enter Text")) && (textfieldUserInput.getText().length() > 0)) {
        timer.start();
        labelCopyState.setText("Copied to Clipboard");

        StringSelection stringSelection = new StringSelection(textfieldOutputText.getText()); //Transfer content in outputText to stringSelection
        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard(); //Initialize the variable clipboard and assign system clipboard to it
        clipboard.setContents(stringSelection, null); //Set system clipboard content to text in stringSeleciton (outputText)
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
    if (textfieldPlaceholderActive) { //If Placeholder ("Enter Text") showing before focus gained
      textfieldUserInput.setText(null); //Set text to null
      textfieldUserInput.setForeground(Color.black); //Set text to black
      textfieldPlaceholderActive = false; //Set to inactive while element is focused
    }
  }

  @Override
  public void focusLost(FocusEvent e) {
    if (textfieldUserInput.getText().isEmpty()) { //If enterText textfield empty
      textfieldUserInput.setText(placeholderText); //Set text to Placeholder ("Enter Text")
      textfieldUserInput.setForeground(Color.lightGray); //Set text to light gray
      textfieldPlaceholderActive = true; //Set to active when element focus is lost
    }
  }
  //</editor-fold>

  private void GUI() {
    GridBagConstraints gbc = new GridBagConstraints();
    gbc.fill = GridBagConstraints.BOTH;

    JPanel outPanel = new JPanel();
    outPanel.setBorder(new EmptyBorder(15, 15, 15, 15));
    outPanel.setLayout(new BoxLayout(outPanel, BoxLayout.X_AXIS));

    JPanel cenPanel = new JPanel();
    cenPanel.setLayout(new BorderLayout());

    JPanel botPanel = new JPanel(new GridBagLayout());

    JPanel textContainer = new JPanel();
    textContainer.setLayout(new GridLayout(1, 3));

    JPanel txtOne = new JPanel();
    JPanel txtTwo = new JPanel();
    JPanel txtThr = new JPanel();

    JPanel buttonContainer = new JPanel();
    buttonContainer.setLayout(new BoxLayout(buttonContainer, BoxLayout.X_AXIS));

    JLabel labelCharacterCount = new JLabel("0");
    labelCharacterCount.setHorizontalAlignment(JLabel.LEFT);

    JLabel labelCharacters = new JLabel("Characters:");
    labelCharacters.setHorizontalAlignment(JLabel.RIGHT);

    JLabel labelUnderscoreCount = new JLabel("0");
    labelUnderscoreCount.setHorizontalAlignment(JLabel.LEFT);

    JLabel labelUnderscores = new JLabel("Underscores:");
    labelUnderscores.setHorizontalAlignment(JLabel.RIGHT);

    labelCopyState = new JLabel();

    textContainer.add(txtOne);
    textContainer.add(txtTwo);
    textContainer.add(txtThr);

    txtOne.add(labelCharacters);
    txtOne.add(labelCharacterCount);
    txtTwo.add(labelCopyState);
    txtThr.add(labelUnderscores);
    txtThr.add(labelUnderscoreCount);

    textfieldOutputText = new JTextField();
    textfieldOutputText.setFont(arial);
    textfieldOutputText.setEditable(false);
    gbc.weightx = 1.0;
    gbc.gridx = 0;
    gbc.gridy = 0;
    botPanel.add(textfieldOutputText, gbc);

    textfieldUserInput = new JTextField();
    textfieldUserInput.setFont(arial);
    textfieldUserInput.addFocusListener(this);
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
        long underscoreCount = textfieldUserInput.getText().chars().filter(ch -> ch == '_').count(); //Filter out underscores
        labelUnderscoreCount.setText(String.valueOf(underscoreCount));

        if (!textfieldUserInput.getText().equals("Enter Text")) {
          labelCharacterCount.setText(textfieldUserInput.getText().length() + "");
        }

        if ((!(textfieldUserInput.getText().equals(placeholderText)) && (textfieldUserInput.getText().length() > 0))) {
          textfieldOutputText.setText(textfieldUserInput.getText().replaceAll("_", " "));
        } else if (((textfieldUserInput.getText().length() < 1))) {
          textfieldOutputText.setText(null);
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

    add(outPanel);
    outPanel.add(cenPanel);

    cenPanel.add(buttonContainer); //Copy Button
    cenPanel.add(textfieldUserInput, BorderLayout.CENTER); //Enter Textfield
    cenPanel.add(textContainer, BorderLayout.NORTH); //'Characters' and 'Underscores' label
    cenPanel.add(botPanel, BorderLayout.SOUTH); //Output textfield

    //<editor-fold defaultstate="collapsed" desc="Default Settings">
    setTitle("Underscore Replacer");
    setSize(550, 155);
    setLocationRelativeTo(null);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setVisible(true);
    //</editor-fold>
  }
}
