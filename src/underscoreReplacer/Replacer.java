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

class Replacer extends JFrame implements ActionListener, FocusListener {

  private JLabel labelCopyStateRemove;
  private JTextField textfieldUserInputRemove;
  private JTextField textfieldOutputTextRemove;
  
  private boolean textfieldPlaceholderActiveRemove = false;
  private boolean textfieldPlaceholderActiveAdd = false;

  private JLabel labelCopyStateAdd;
  private JTextField textfieldUserInputAdd;
  private JTextField textfieldOutputTextAdd;

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
    if ("CopyTextRemove".equals(ae.getActionCommand())) {
      Timer timer = new Timer(2000, event -> {
        labelCopyStateRemove.setText(null);
      });

      if ((textfieldOutputTextRemove.getText().equals("")) || (textfieldOutputTextRemove.getText() == null)) {
        labelCopyStateRemove.setText("Nothing copied");
        timer.start();

      } else if ((!textfieldOutputTextRemove.getText().equals("Enter Text")) && (textfieldUserInputRemove.getText().length() > 0)) {
        labelCopyStateRemove.setText("Copied to Clipboard");
        timer.start();

        StringSelection stringSelection = new StringSelection(textfieldOutputTextRemove.getText());
        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        clipboard.setContents(stringSelection, null);
      }
    }
    //</editor-fold>
  }

  @Override
  public void focusGained(FocusEvent e) {
    if (textfieldPlaceholderActiveRemove) { //If Placeholder ("Enter Text") showing before focus gained
      textfieldUserInputRemove.setText(null); //Set text to null
      textfieldUserInputRemove.setForeground(Color.black); //Set text to black
      textfieldPlaceholderActiveRemove = false; //Set to inactive while element is focused
    }
    
    if (textfieldPlaceholderActiveAdd) { //If Placeholder ("Enter Text") showing before focus gained
      textfieldUserInputAdd.setText(null); //Set text to null
      textfieldUserInputAdd.setForeground(Color.black); //Set text to black
      textfieldPlaceholderActiveAdd = false; //Set to inactive while element is focused
    }
  }

  @Override
  public void focusLost(FocusEvent e) {
    if (textfieldUserInputRemove.getText().isEmpty()) { //If enterText textfield empty
      textfieldUserInputRemove.setText(placeholderText); //Set text to Placeholder ("Enter Text")
      textfieldUserInputRemove.setForeground(Color.lightGray); //Set text to light gray
      textfieldPlaceholderActiveRemove = true; //Set to active when element focus is lost
    }
    
    if (textfieldUserInputAdd.getText().isEmpty()) { //If enterText textfield empty
      textfieldUserInputAdd.setText(placeholderText); //Set text to Placeholder ("Enter Text")
      textfieldUserInputAdd.setForeground(Color.lightGray); //Set text to light gray
      textfieldPlaceholderActiveAdd = true; //Set to active when element focus is lost
    }
  }
  //</editor-fold>

  private void GUI() {
    GridBagConstraints gbc = new GridBagConstraints();
    gbc.fill = GridBagConstraints.BOTH;

    GridBagConstraints gbd = new GridBagConstraints();
    gbd.fill = GridBagConstraints.BOTH;

    JPanel panelMain = new JPanel();
    panelMain.setLayout(new BoxLayout(panelMain, BoxLayout.Y_AXIS));
    panelMain.setBorder(new EmptyBorder(15, 15, 15, 15));

    //<editor-fold defaultstate="collapsed" desc="Removing Underscores">
    JPanel panelUnderscoreRemove = new JPanel();
    panelUnderscoreRemove.setBorder(BorderFactory.createTitledBorder("Remove Underscores"));
    panelUnderscoreRemove.setLayout(new BoxLayout(panelUnderscoreRemove, BoxLayout.X_AXIS));

    JPanel panelUnderscoreRemove_Inner = new JPanel();
    panelUnderscoreRemove_Inner.setBorder(new EmptyBorder(5, 5, 5, 5));
    panelUnderscoreRemove_Inner.setLayout(new BoxLayout(panelUnderscoreRemove_Inner, BoxLayout.X_AXIS));

    JPanel panelUnderscoreRemove_Inner_Inner = new JPanel();
    panelUnderscoreRemove_Inner_Inner.setLayout(new BorderLayout());

    JPanel panelUserInputRemove = new JPanel(new GridBagLayout());
    JPanel panelUserOutputRemove = new JPanel(new GridBagLayout());

    JPanel labelContainerRemove = new JPanel();
    labelContainerRemove.setLayout(new GridLayout(1, 3));

    JPanel panelCharacterCountRemove = new JPanel();
    JPanel panelCopyStateRemove = new JPanel();
    JPanel panelUnderscoreCountRemove = new JPanel();

    JPanel buttonContainerRemove = new JPanel();
    buttonContainerRemove.setLayout(new BoxLayout(buttonContainerRemove, BoxLayout.X_AXIS));

    JLabel labelCharacterCountRemove = new JLabel("0");
    labelCharacterCountRemove.setHorizontalAlignment(JLabel.LEFT);

    JLabel labelCharactersRemove = new JLabel("Characters:");
    labelCharactersRemove.setHorizontalAlignment(JLabel.RIGHT);

    JLabel labelUnderscoreCountRemove = new JLabel("0");
    labelUnderscoreCountRemove.setHorizontalAlignment(JLabel.LEFT);

    JLabel labelUnderscoresRemove = new JLabel("Underscores:");
    labelUnderscoresRemove.setHorizontalAlignment(JLabel.RIGHT);

    labelCopyStateRemove = new JLabel();

    labelContainerRemove.add(panelCharacterCountRemove);
    labelContainerRemove.add(panelCopyStateRemove);
    labelContainerRemove.add(panelUnderscoreCountRemove);

    panelCharacterCountRemove.add(labelCharactersRemove);
    panelCharacterCountRemove.add(labelCharacterCountRemove);
    panelCopyStateRemove.add(labelCopyStateRemove);
    panelUnderscoreCountRemove.add(labelUnderscoresRemove);
    panelUnderscoreCountRemove.add(labelUnderscoreCountRemove);

    //<editor-fold defaultstate="collapsed" desc="Output Textfield">
    textfieldOutputTextRemove = new JTextField();
    textfieldOutputTextRemove.setFont(arial);
    textfieldOutputTextRemove.setEditable(false);
    gbc.weightx = 1.0;
    gbc.gridx = 0;
    gbc.gridy = 1;
    panelUserOutputRemove.add(textfieldOutputTextRemove, gbc);
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="User input Textfield">
    textfieldUserInputRemove = new JTextField();
    textfieldUserInputRemove.setFont(arial);
    textfieldUserInputRemove.addFocusListener(this);
    textfieldUserInputRemove.setBorder(BorderFactory.createCompoundBorder(
            textfieldUserInputRemove.getBorder(),
            BorderFactory.createEmptyBorder(3, 0, 3, 0)));
    gbc.weightx = 1.0;
    gbc.gridx = 0;
    gbc.gridy = 0;
    panelUserInputRemove.add(textfieldUserInputRemove, gbc);
    textfieldUserInputRemove.getDocument().addDocumentListener(new DocumentListener() { //Set up the text field to check for changes
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
        long underscoreCountRemove = textfieldUserInputRemove.getText().chars().filter(ch -> ch == '_').count(); //Filter underscores
        labelUnderscoreCountRemove.setText(String.valueOf(underscoreCountRemove)); //Count total underscores

        if (!textfieldUserInputRemove.getText().equals("Enter Text")) {
          labelCharacterCountRemove.setText(textfieldUserInputRemove.getText().length() + "");
        }

        if ((!(textfieldUserInputRemove.getText().equals(placeholderText)) && (textfieldUserInputRemove.getText().length() > 0))) {
          textfieldOutputTextRemove.setText(textfieldUserInputRemove.getText().replaceAll("_", " "));
        } else if (((textfieldUserInputRemove.getText().length() < 1))) {
          textfieldOutputTextRemove.setText(null);
        }
      }
    });
    //</editor-fold>

    JButton copyTextRemove = new JButton("Copy");
    gbc.weightx = 0.01;
    gbc.gridx = 1;
    gbc.gridy = 1;
    copyTextRemove.setActionCommand("CopyTextRemove");
    copyTextRemove.addActionListener(this);
    panelUserOutputRemove.add(copyTextRemove, gbc);
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Adding Underscores">
    JPanel panelUnderscoreAdd = new JPanel();
    panelUnderscoreAdd.setBorder(BorderFactory.createTitledBorder("Add Underscores"));
    panelUnderscoreAdd.setLayout(new BoxLayout(panelUnderscoreAdd, BoxLayout.X_AXIS));

    JPanel panelUnderscoreAdd_Inner = new JPanel();
    panelUnderscoreAdd_Inner.setBorder(new EmptyBorder(5, 5, 5, 5));
    panelUnderscoreAdd_Inner.setLayout(new BoxLayout(panelUnderscoreAdd_Inner, BoxLayout.X_AXIS));

    JPanel panelUnderscoreAdd_Inner_Inner = new JPanel();
    panelUnderscoreAdd_Inner_Inner.setLayout(new BorderLayout());

    JPanel panelUserInputAdd = new JPanel(new GridBagLayout());
    JPanel panelUserOutputAdd = new JPanel(new GridBagLayout());

    JPanel labelContainerAdd = new JPanel();
    labelContainerAdd.setLayout(new GridLayout(1, 3));

    JPanel panelCharacterCountAdd = new JPanel();
    JPanel panelCopyStateAdd = new JPanel();
    JPanel panelUnderscoreCountAdd = new JPanel();

    JPanel buttonContainerAdd = new JPanel();
    buttonContainerAdd.setLayout(new BoxLayout(buttonContainerAdd, BoxLayout.X_AXIS));

    JLabel labelCharacterCountAdd = new JLabel("0");
    labelCharacterCountAdd.setHorizontalAlignment(JLabel.LEFT);

    JLabel labelCharactersAdd = new JLabel("Characters:");
    labelCharactersAdd.setHorizontalAlignment(JLabel.RIGHT);

    JLabel labelUnderscoreCountAdd = new JLabel("0");
    labelUnderscoreCountAdd.setHorizontalAlignment(JLabel.LEFT);

    JLabel labelUnderscoresAdd = new JLabel("Underscores:");
    labelUnderscoresAdd.setHorizontalAlignment(JLabel.RIGHT);

    labelCopyStateAdd = new JLabel();

    labelContainerAdd.add(panelCharacterCountAdd);
    labelContainerAdd.add(panelCopyStateAdd);
    labelContainerAdd.add(panelUnderscoreCountAdd);

    panelCharacterCountAdd.add(labelCharactersAdd);
    panelCharacterCountAdd.add(labelCharacterCountAdd);
    panelCopyStateAdd.add(labelCopyStateAdd);
    panelUnderscoreCountAdd.add(labelUnderscoresAdd);
    panelUnderscoreCountAdd.add(labelUnderscoreCountAdd);

    //<editor-fold defaultstate="collapsed" desc="Output Textfield">
    textfieldOutputTextAdd = new JTextField();
    textfieldOutputTextAdd.setFont(arial);
    textfieldOutputTextAdd.setEditable(false);
    gbd.weightx = 1.0;
    gbd.gridx = 0;
    gbd.gridy = 1;
    panelUserOutputAdd.add(textfieldOutputTextAdd, gbd);
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="User input Textfield">
    textfieldUserInputAdd = new JTextField();
    textfieldUserInputAdd.setFont(arial);
    textfieldUserInputAdd.addFocusListener(this);
    textfieldUserInputAdd.setBorder(BorderFactory.createCompoundBorder(
            textfieldUserInputAdd.getBorder(),
            BorderFactory.createEmptyBorder(3, 0, 3, 0)));
    gbd.weightx = 1.0;
    gbd.gridx = 0;
    gbd.gridy = 0;
    panelUserInputAdd.add(textfieldUserInputAdd, gbd);
    textfieldUserInputAdd.getDocument().addDocumentListener(new DocumentListener() { //Set up the text field to check for changes
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
        long underscoreCount = textfieldUserInputAdd.getText().chars().filter(ch -> ch == '_').count(); //Filter underscores
        labelUnderscoreCountRemove.setText(String.valueOf(underscoreCount)); //Count total underscores

        if (!textfieldUserInputAdd.getText().equals("Enter Text")) {
          labelCharacterCountRemove.setText(textfieldUserInputAdd.getText().length() + "");
        }

        if ((!(textfieldUserInputAdd.getText().equals(placeholderText)) && (textfieldUserInputRemove.getText().length() > 0))) {
          textfieldOutputTextAdd.setText(textfieldUserInputAdd.getText().replaceAll("_", " "));
        } else if (((textfieldUserInputAdd.getText().length() < 1))) {
          textfieldOutputTextAdd.setText(null);
        }
      }
    });
    //</editor-fold>

    JButton copyTextAdd = new JButton("Copy");
    gbd.weightx = 0.01;
    gbd.gridx = 1;
    gbd.gridy = 1;
    copyTextAdd.setActionCommand("CopyTextAdd");
    copyTextAdd.addActionListener(this);
    panelUserOutputAdd.add(copyTextAdd, gbd);
    //</editor-fold>

    add(panelMain);
    panelMain.add(panelUnderscoreRemove, BorderLayout.NORTH);
    panelMain.add(panelUnderscoreAdd, BorderLayout.SOUTH);

    panelUnderscoreRemove.add(panelUnderscoreRemove_Inner);
    panelUnderscoreRemove_Inner.add(panelUnderscoreRemove_Inner_Inner);

    panelUnderscoreAdd.add(panelUnderscoreAdd_Inner);
    panelUnderscoreAdd_Inner.add(panelUnderscoreAdd_Inner_Inner);

    panelUnderscoreRemove_Inner_Inner.add(buttonContainerRemove);
    panelUnderscoreRemove_Inner_Inner.add(labelContainerRemove, BorderLayout.NORTH);
    panelUnderscoreRemove_Inner_Inner.add(panelUserInputRemove, BorderLayout.CENTER);
    panelUnderscoreRemove_Inner_Inner.add(panelUserOutputRemove, BorderLayout.SOUTH);

    panelUnderscoreAdd_Inner_Inner.add(buttonContainerAdd);
    panelUnderscoreAdd_Inner_Inner.add(labelContainerAdd, BorderLayout.NORTH);
    panelUnderscoreAdd_Inner_Inner.add(panelUserInputAdd, BorderLayout.CENTER);
    panelUnderscoreAdd_Inner_Inner.add(panelUserOutputAdd, BorderLayout.SOUTH);

    //<editor-fold defaultstate="collapsed" desc="Default Settings">
    setTitle("Underscore Replacer");
    setSize(650, 300);
    setResizable(false);
    setLocationRelativeTo(null);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setVisible(true);
    //</editor-fold>
  }
}
