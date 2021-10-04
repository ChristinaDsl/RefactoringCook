package GUI;

import ComponentsPackage.*;
import com.google.common.base.Splitter;
import com.google.common.collect.Lists;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class RefactoringCreatorJFrame {
    private DataManager dataManager;
    private String name;
    private RefactoringCreatorJFrame refactoringCreatorJFrame;
    private JButton addRefactoringJButton;
    private JButton goBackJButton;
    private JPanel frame;
    private JLabel nameLabel;
    private JLabel descriptionLabel;
    private JLabel usesLabel;
    private JLabel instructionsLabel;
    private JLabel mechanicsLabel;
    private JLabel infoLabel;
    private JLabel categoryLabel;
    private JTextField nameTextField;
    private JTextArea descriptionTextArea;
    private JTextArea usesTextArea;
    private JTextArea mechanicsTextArea;
    private JComboBox categoriesComboBox;
    private String refactoringCategory;
    private MyToolWindowFactory myToolWindowFactory;

    public RefactoringCreatorJFrame(String name,RefactoringsListJFrame refactoringsListJFrame) {

        this.refactoringCreatorJFrame = this;

        myToolWindowFactory = new MyToolWindowFactory();
        dataManager = DataManager.getDataManager();

        frame = new JPanel();
        frame.setSize(700, 650);
        frame.setLayout(null);

        nameLabel = new JLabel();
        nameLabel.setBounds(10, 70, 250, 25);
        nameLabel.setText("Name");
        frame.add(nameLabel);

        descriptionLabel = new JLabel();
        descriptionLabel.setBounds(10, 115, 300, 25);
        descriptionLabel.setText("Brief description");
        frame.add(descriptionLabel);

        usesLabel = new JLabel();
        usesLabel.setBounds(10, 255, 150, 25);
        usesLabel.setText("Uses of refactoring");
        frame.add(usesLabel);

        instructionsLabel = new JLabel();
        instructionsLabel.setBounds(10, 275, 150, 25);
        instructionsLabel.setText("(Add comma(','))");
        frame.add(instructionsLabel);

        mechanicsLabel = new JLabel();
        mechanicsLabel.setBounds(10, 325, 300, 25);
        mechanicsLabel.setText("How to apply refactoring");
        frame.add(mechanicsLabel);

        categoryLabel= new JLabel();
        categoryLabel.setBounds(10, 465, 200, 25);
        categoryLabel.setText("Select Category");
        frame.add(categoryLabel);

        nameTextField = new JTextField();
        nameTextField.setBounds(200, 70, 300, 25);
        frame.add(nameTextField);

        descriptionTextArea = new JTextArea();
        descriptionTextArea.setBounds(200, 115, 300, 120);
        frame.add(descriptionTextArea);
        descriptionTextArea.setLineWrap(true);
        descriptionTextArea.setWrapStyleWord(true);

        usesTextArea = new JTextArea();
        usesTextArea.setBounds(200, 255, 300, 50);
        usesTextArea.setLineWrap(true);
        usesTextArea.setWrapStyleWord(true);
        frame.add(usesTextArea);

        mechanicsTextArea = new JTextArea();
        mechanicsTextArea.setBounds(200, 325, 300, 120);
        frame.add(mechanicsTextArea);
        mechanicsTextArea.setLineWrap(true);
        mechanicsTextArea.setWrapStyleWord(true);

        categoriesComboBox =  new JComboBox(getCategoriesComboBoxItems());
        categoriesComboBox.setBounds(200, 465, 400, 25);
        frame.add(categoriesComboBox);

        infoLabel = new JLabel();
        infoLabel.setBounds(240, 570, 440, 25);
        frame.add(infoLabel);

        addRefactoringJButton = new JButton("Add Refactoring");
        addRefactoringJButton.setBounds(200, 510, 250, 40);
        frame.add(addRefactoringJButton);

        goBackJButton = new JButton("Back");
        goBackJButton.setBounds(10,10,70,40);
        goBackJButton.setForeground(Color.red.darker());
        frame.add(goBackJButton);

        frame.setVisible(true);

        if(name!=""){
            this.name = name;
            nameTextField.setText(name);
            nameTextField.setEnabled(false);
        }else{ this.name = nameTextField.getText();}

        categoriesComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) { refactoringCategory = categoriesComboBox.getSelectedItem().toString(); }
        });

        addRefactoringJButton.addActionListener((new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                List<String> smells = new ArrayList<>();
                List<String> listOfUses = Lists.newArrayList(Splitter.on(",").split(usesTextArea.getText()));
                String mechanics = mechanicsTextArea.getText();
                Refactoring refactoring =  new Refactoring(nameTextField.getText(),refactoringCategory,descriptionTextArea.getText(),listOfUses, mechanics);
                if( nameTextField.getText().isEmpty() || categoriesComboBox.getSelectedIndex() == -1 || categoriesComboBox.getSelectedItem().equals("Please select the category of refactoring")){
                    setInfoLabel("Missing Components",Color.RED);
                }else if(dataManager.refactoringExistsInMap(refactoring.getName())){
                    setInfoLabel("This refactoring exists already",Color.RED);
                }else {
                    int dialogResult = JOptionPane.showConfirmDialog (null, "Do you want to add '"+  refactoring.getName()+"' "+"in existing refactorings?", "Yes", JOptionPane.YES_NO_OPTION);
                    if(dialogResult == JOptionPane.YES_OPTION) {
                        dataManager.addRefactoringInSystemData(refactoring);
                        infoLabel.setText("");
                        refactoringsListJFrame.updateRefactoringsList(refactoringCategory);
                        refactoringsListJFrame.setInfoLabel("Refactoring added successfully", Color.GREEN);
                        frame.setVisible(false);
                    }
                }
            }}));

        goBackJButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                refactoringsListJFrame.setInfoLabel("",Color.red);
                myToolWindowFactory.updateToolWindowContent(refactoringsListJFrame.getContent());
            }
        });

        myToolWindowFactory.updateToolWindowContent(frame);
    }

    public JPanel getContent() {
        return frame;
    }

    public String[] getCategoriesComboBoxItems(){
        int counter=0;
        String[] comboBoxItems = new String[dataManager.getCategories().length+1];
        comboBoxItems[counter++]= "Please select the category of refactoring";
        for(String category : dataManager.getCategories()){
            comboBoxItems[counter++] = category;
        }
        return comboBoxItems;
    }

    public void setInfoLabel(String text, Color color){
        infoLabel.setText(text);
        infoLabel.setForeground(color.darker());
        infoLabel.setVisible(true);
    }
}
