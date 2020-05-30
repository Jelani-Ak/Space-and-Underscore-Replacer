package underscoreReplacer;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.BorderFactory;
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

    JPanel mainPanel = new JPanel();
    mainPanel.setBorder(new EmptyBorder(15, 15, 15, 15));
    mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.X_AXIS));

    JPanel centralPanel = new JPanel();
    centralPanel.setLayout(new BorderLayout());

    JPanel panelUserInput = new JPanel(new GridBagLayout());
    JPanel panelUserOutput = new JPanel(new GridBagLayout());

    JPanel labelContainer = new JPanel();
    labelContainer.setLayout(new GridLayout(1, 3));

    JPanel panelCharacterCount = new JPanel();
    JPanel panelCopyState = new JPanel();
    JPanel panelUnderscoreCount = new JPanel();

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

    labelContainer.add(panelCharacterCount);
    labelContainer.add(panelCopyState);
    labelContainer.add(panelUnderscoreCount);

    panelCharacterCount.add(labelCharacters);
    panelCharacterCount.add(labelCharacterCount);
    panelCopyState.add(labelCopyState);
    panelUnderscoreCount.add(labelUnderscores);
    panelUnderscoreCount.add(labelUnderscoreCount);

    //<editor-fold defaultstate="collapsed" desc="Output Textfield">
    textfieldOutputText = new JTextField();
    textfieldOutputText.setFont(arial);
    textfieldOutputText.setEditable(false);
    gbc.weightx = 1.0;
    gbc.gridx = 0;
    gbc.gridy = 1;
    panelUserOutput.add(textfieldOutputText, gbc);
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="User input Textfield">
    textfieldUserInput = new JTextField();
    textfieldUserInput.setFont(arial);
    textfieldUserInput.addFocusListener(this);
    textfieldUserInput.setBorder(BorderFactory.createCompoundBorder(
            textfieldUserInput.getBorder(),
            BorderFactory.createEmptyBorder(3, 0, 3, 0)));
    gbc.weightx = 1.0;
    gbc.gridx = 0;
    gbc.gridy = 0;
    panelUserInput.add(textfieldUserInput, gbc);
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
        labelUnderscoreCount.setText(String.valueOf(underscoreCount)); //Count total underscores

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
    //</editor-fold>

    JButton copyText = new JButton("Copy");
    gbc.weightx = 0.01;
    gbc.gridx = 1;
    gbc.gridy = 1;
    copyText.setActionCommand("CopyText");
    copyText.addActionListener(this);
    panelUserOutput.add(copyText, gbc);

    add(mainPanel);
    mainPanel.add(centralPanel);

    centralPanel.add(buttonContainer);
    centralPanel.add(labelContainer, BorderLayout.NORTH);
    centralPanel.add(panelUserInput, BorderLayout.CENTER);
    centralPanel.add(panelUserOutput, BorderLayout.SOUTH);

    //<editor-fold defaultstate="collapsed" desc="Default Settings">
    setTitle("Underscore Replacer");
    setSize(650, 165);
    setResizable(false);
    setLocationRelativeTo(null);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setVisible(true);
    //</editor-fold>
  }
}
