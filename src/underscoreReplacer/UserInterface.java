package underscoreReplacer;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

public class UserInterface extends JFrame {

  UserInterfaceListener listener = new UserInterfaceListener();
  
  private final JLabel labelCopyState;
  private final JLabel labelCharacterCount;

  private final JButton copyText;

  private final JTextField textfieldUserInput;
  private final JTextField textfieldOutputText;

  private boolean textfieldPlaceholderActive;

  private final Font arial = new Font("Arial", Font.PLAIN, 14);

  public UserInterface() {
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

    labelCharacterCount = new JLabel("0");
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
    textfieldUserInput.setBorder(BorderFactory.createCompoundBorder(
            textfieldUserInput.getBorder(),
            BorderFactory.createEmptyBorder(3, 0, 3, 0)));
    gbc.weightx = 1.0;
    gbc.gridx = 0;
    gbc.gridy = 0;
    panelUserInput.add(textfieldUserInput, gbc);
    //</editor-fold>

    copyText = new JButton("Copy");
    gbc.weightx = 0.01;
    gbc.gridx = 1;
    gbc.gridy = 1;
    copyText.setActionCommand("CopyText");
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

  public JTextField getTextfieldUserInput() {
    return textfieldUserInput;
  }

  public JTextField getTextfieldOutputText() {
    return textfieldOutputText;
  }

  public JButton getCopyText() {
    return copyText;
  }

  public JLabel getLabelCharacterCount() {
    return labelCharacterCount;
  }

  public JLabel getLabelCopyState() {
    return labelCopyState;
  }

  public Boolean getTextfieldPlaceholderActive() {
    return textfieldPlaceholderActive;
  }
}
