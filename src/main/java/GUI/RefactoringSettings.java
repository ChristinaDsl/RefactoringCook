package GUI;

import ComponentsPackage.DataManager;
import InputParser.InputParser;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.NoSuchElementException;

public class RefactoringSettings extends JComponent {
    private final RefactoringSettings refactoringSettings;
    private DataManager dataManager;
    private InputParser inputParser;
    private JPanel f;
    private JFrame filePathFrame;
    private JButton showRefactoringsButton;
    private JButton showSmellsButton;
    private JButton importJButton;
    private JButton createTypeOfRelationJButton;
    private JButton insertRelationTypeJButton;
    private JLabel infoLabel;
    private JTextField typeOfRelationJTextField;
    private JLabel typeOfRelationJLabel;
    private JPanel newToolWindowContent;
    private MyToolWindowFactory myToolWindowFactory;
    private JButton goBackJButton;


    public RefactoringSettings(){
        this.refactoringSettings = this;
        dataManager = DataManager.getDataManager();
        inputParser = InputParser.getInputParser();
        myToolWindowFactory = new MyToolWindowFactory();

        f = new JPanel();
        f.setSize(800, 600);

        newToolWindowContent = new JPanel();

        showRefactoringsButton = new JButton("Show Refactorings");
        //showRefactoringsButton.setFont(new Font("Chilanka", Font.BOLD, 14));
        showRefactoringsButton.setBounds(130, 100, 250, 40);
        f.add(showRefactoringsButton);

        importJButton = new JButton("Import refactorings and its relations from XML file");
        importJButton.setBounds(130, 180, 500, 40);
        f.add(importJButton);


        insertRelationTypeJButton= new JButton("Create a new type of relation");
        insertRelationTypeJButton.setBounds(130, 260, 250, 40);
        f.add(insertRelationTypeJButton);

        typeOfRelationJLabel = new JLabel("Give new type a name: ");
        typeOfRelationJLabel.setBounds(130, 300, 250, 40);
        f.add(typeOfRelationJLabel);
        typeOfRelationJLabel.setVisible(false);

        typeOfRelationJTextField = new JTextField();
        typeOfRelationJTextField.setBounds(130, 340, 250, 40);
        f.add(typeOfRelationJTextField);
        typeOfRelationJTextField.setVisible(false);

        createTypeOfRelationJButton= new JButton("Add");
        createTypeOfRelationJButton.setBounds(130, 380, 250, 40);
        f.add(createTypeOfRelationJButton);
        createTypeOfRelationJButton.setVisible(false);

        goBackJButton = new JButton("Back");
        goBackJButton.setForeground(Color.red.darker());
        goBackJButton.setBounds(10,10,70,40);
        f.add(goBackJButton);

        infoLabel = new JLabel();
        infoLabel.setBounds(130, 340, 500, 25);
        f.add(infoLabel);

        importJButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                String selectedFilePath = selectFilePath();
                if (selectedFilePath != null) {
                    try{
                        infoLabel.setVisible(false);
                        inputParser.importData(refactoringSettings,selectedFilePath);
                        setInfoLabel("Successful import of data!", Color.GREEN);
                    } catch (NoSuchElementException exception){
                        setInfoLabel("Unsuccesfull import of data", Color.RED);
                    }
                }else{
                    setInfoLabel("Unsuccesfull import of data", Color.RED);
                }
            }});

        showRefactoringsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                new RefactoringsListJFrame(refactoringSettings);
            }
        });

        f.setLayout(null);
        f.setVisible(true);


        insertRelationTypeJButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                typeOfRelationJLabel.setVisible(true);
                typeOfRelationJTextField.setVisible(true);
                createTypeOfRelationJButton.setVisible(true);
            }
        });

        createTypeOfRelationJButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                if(typeOfRelationJTextField.getText().isEmpty()){
                    setInfoLabel("Missing Components",Color.RED);
                }else{
                    if(typeOfRelationJTextField.getText().equalsIgnoreCase("Before") ||
                            typeOfRelationJTextField.getText().equalsIgnoreCase("After") ||
                            typeOfRelationJTextField.getText().equalsIgnoreCase("Instead of")){
                        setInfoLabel("This type of relation already exists...",Color.RED);
                    }else{
                        dataManager.addNewRelationType(typeOfRelationJTextField.getText());
                        setInfoLabel("New type of relation successfully added",Color.green);
                    }

                    typeOfRelationJTextField.setText("");
                    typeOfRelationJTextField.setVisible(false);
                    typeOfRelationJLabel.setVisible(false);
                    createTypeOfRelationJButton.setVisible(false);
                }
            }
        });

        goBackJButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                MainMenu mainMenu = new MainMenu();
                myToolWindowFactory.updateToolWindowContent(mainMenu.getContent());
            }
        });

        myToolWindowFactory.updateToolWindowContent(f);

    }


    public String selectFilePath(){
        JFileChooser fileChooser = new JFileChooser();
        filePathFrame = new JFrame();
        fileChooser.setCurrentDirectory(new java.io.File("."));
        int result = fileChooser.showOpenDialog(filePathFrame);
        if (result == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            if (selectedFile.getAbsolutePath().endsWith(".xml")){
                return selectedFile.getAbsolutePath();
            }else{
                return null;
            }
        }else{ return null; }
    }

    public void setInfoLabel(String text, Color color){
        infoLabel.setText(text);
        infoLabel.setForeground(color.darker());
        infoLabel.setVisible(true);
    }

    public JPanel getContent() {
        return f;
    }
}
