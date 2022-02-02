import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

/**
 * The GUI class creates and uses an interface to interact with the WordleSolver class
 */
class GUI {

    // WordleSolver instance containing tool functionality
    private final WordleSolver wordleSolver;

    // Primary GUI components
    private final JFrame frame;
    private final JPanel controlArea;
    private final JTextPane outputArea;

    // Menu Bar components
    private final JMenuBar menuBar;
    private final JMenu helpMenu;
    private final JMenuItem howToMenuItem;

    // Control Panel components
    private final GridBagLayout controlLayout;
    private final JTextField inputField;
    private final JTextField excludeField;
    private final JTextField includeField;
    private final JButton searchButton;

    public GUI(WordleSolver wordleSolver) {
        this.wordleSolver = wordleSolver;

        // Main frame setup
        frame = new JFrame();
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        controlArea = new JPanel();
        outputArea = new JTextPane();
        JScrollPane outputAreaScroll = new JScrollPane(outputArea);
        frame.setTitle("Wordle Solver");

        frame.getContentPane().setLayout(new BoxLayout(frame.getContentPane(), BoxLayout.X_AXIS));
        frame.getContentPane().add(controlArea);
        frame.getContentPane().add(outputAreaScroll);

        controlArea.setPreferredSize(new Dimension(250, 300));
        outputArea.setPreferredSize(new Dimension(200, 300));
        outputArea.setEditable(false);

        // Menu bar setup
        menuBar = new JMenuBar();
        helpMenu = new JMenu("Help");
        howToMenuItem = new JMenuItem("How To Use");
        menuBar.add(helpMenu);
        helpMenu.add(howToMenuItem);
        frame.setJMenuBar(menuBar);

        howToMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null, "Wordle Solver - How to Use\n\n"
                        + "Enter partial words using \"?\" for unknown letters (\"e.g. wo?d??\") \n and \"-\" between words "
                        + "to find matching full words (e.g. cr?sswo?d-pu?zle).\n\n"
                        + "Add letters to exclude in the exclude field, and letters that must be included in the include field.");
            }
        });

        // Setting up GridBagLayout and its constraints for the control area
        controlLayout = new GridBagLayout();
        controlArea.setLayout(controlLayout);
        GridBagConstraints controlConstr = new GridBagConstraints();

        int column = 0;
        int row = 0;

        addToGridBagLayout(new JLabel("Enter Word Search Pattern:"), controlArea, controlConstr, column, row++, 3, 1, GridBagConstraints.HORIZONTAL);
        inputField = new JTextField();
        addToGridBagLayout(inputField, controlArea, controlConstr, column, row++, 3, 1, GridBagConstraints.HORIZONTAL);

        addToGridBagLayout(new JLabel("Exclude Letters:"), controlArea, controlConstr, column, row++, 3, 1, GridBagConstraints.HORIZONTAL);
        excludeField = new JTextField();
        addToGridBagLayout(excludeField, controlArea, controlConstr, column, row++, 3, 1, GridBagConstraints.HORIZONTAL);

        addToGridBagLayout(new JLabel("Include Letters:"), controlArea, controlConstr, column, row++, 3, 1, GridBagConstraints.HORIZONTAL);
        includeField = new JTextField();
        addToGridBagLayout(includeField, controlArea, controlConstr, column, row++, 3, 1, GridBagConstraints.HORIZONTAL);

        searchButton = new JButton("Search");
        addToGridBagLayout(searchButton, controlArea, controlConstr, column, row++, 1, 1, GridBagConstraints.CENTER);

        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!inputField.getText().equals("")) {
                    displayMatches();
                }
            }
        });

        frame.pack();
        frame.setVisible(true);
    }

    /**
     * Gets words matching the current contents of the inputField and adds them to the outputArea
     */
    public void displayMatches() {
        String[] words = {};

        if (inputField.getText().contains("-")) {
            words = inputField.getText().toLowerCase().split("-");
        } else {
            words = new String[]{inputField.getText().toLowerCase()};
        }

        outputArea.setText("");

        for (int i = 0; i < words.length; i++) {
            List<String> matches = wordleSolver.matchWords(words[i].toCharArray());

            matches = wordleSolver.excludeWords(matches, excludeField.getText().toLowerCase());
            matches = wordleSolver.includeWords(matches, includeField.getText().toLowerCase());
            matches = wordleSolver.sortByCharacterScore(matches);

            if (matches.size() != 0) {
                if (matches.size() == 1) {
                    outputArea.setText(outputArea.getText() + matches.size() + " Match for \"" + words[i] + "\":" + "\n");
                } else {
                    outputArea.setText(outputArea.getText() + matches.size() + " Matches for \"" + words[i] + "\":" + "\n");
                }

                if (matches.size() <= 1000) {
                    for (String s : matches) {
                        outputArea.setText(outputArea.getText() + "\n" + s);
                    }
                } else {
                    for (int j = 0; j < 1000; j++) {
                        outputArea.setText(outputArea.getText() + "\n" + matches.get(j));
                    }

                    JOptionPane.showMessageDialog(null, "Returned " + matches.size() + " matches for " + words[i] + ", displaying first 1000 matches.\n" + "Consider filling in more letters.");
                }
            } else {
                outputArea.setText(outputArea.getText() + "No Matches for \"" + words[i] + "\"");
            }

            if (i != words.length) {
                outputArea.setText(outputArea.getText() + "\n\n");
            } else {
                outputArea.setText(outputArea.getText() + "\n");
            }
        }

        outputArea.setCaretPosition(0);
    }

    /**
     * Method to add components to the GridBagLayout
     *
     * @param component   to be added
     * @param area        to add them to
     * @param constraints object for the GridBagLayout
     * @param gridx
     * @param gridy
     * @param gridwidth
     * @param gridheight
     * @param fill
     */
    public void addToGridBagLayout(JComponent component, JComponent area, GridBagConstraints constraints, int gridx, int gridy, int gridwidth, int gridheight, int fill) {
        constraints.gridx = gridx;
        constraints.gridy = gridy;
        constraints.gridwidth = gridwidth;
        constraints.gridheight = gridheight;
        constraints.fill = fill;
        area.add(component, constraints);
    }
}