package GUI;

import ComponentsPackage.DataManager;
import ComponentsPackage.Refactoring;
import ComponentsPackage.Relation;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class RelationCreatorJFrame extends JFrame {
    private DataManager dataManager;
    private JPanel frame;
    private JLabel infoLabel;
    private String relationKind;
    private String targetRefactoring;
    private JComboBox relationComboBox;
    private JComboBox targetRefactoringCb;
    private JButton addRelationJButton;
    private JButton goBackJButton;
    private JTextArea descriptionJArea;
    private JLabel descriptionJLabel;
    private MyToolWindowFactory myToolWindowFactory;

    public RelationCreatorJFrame(Refactoring refactoring,RelationsListOfRefactoringJFrame relationsListOfRefactoringJFrame) {
        dataManager = DataManager.getDataManager();

        myToolWindowFactory = new MyToolWindowFactory();

        frame = new JPanel();
        frame.setSize(500, 300);
        frame.setLayout(null);
        frame.setVisible(true);

        relationComboBox =  new JComboBox(getRelationsComboBoxItems());
        //relationComboBox.setFont(new Font("Chilanka", Font.BOLD, 14));
        relationComboBox.setBounds(20, 50, 250, 40);
        frame.add(relationComboBox);

        targetRefactoringCb=  new JComboBox(getRefactoringsComboBoxItems());
        //targetRefactoringCb.setFont(new Font("Chilanka", Font.BOLD, 14));
        targetRefactoringCb.setBounds(20, 90, 350, 40);
        frame.add(targetRefactoringCb);

        descriptionJArea = new JTextArea();
        //descriptionJArea.setFont(new Font("Chilanka", Font.BOLD, 14));
        descriptionJArea.setLineWrap(true);
        descriptionJArea.setWrapStyleWord(true);
        descriptionJArea.setBounds(20, 160, 250, 250);
        frame.add(descriptionJArea);

        addRelationJButton = new JButton("Add relation");
        addRelationJButton.setBounds(20, 430, 250, 40);
        frame.add(addRelationJButton);

        goBackJButton = new JButton("Back");
        goBackJButton.setForeground(Color.red.darker());
        goBackJButton.setBounds(10,10,70,40);
        frame.add(goBackJButton);

        descriptionJLabel = new JLabel("Give a brief description of relation" );
        descriptionJLabel.setBounds(20, 140, 450, 25);
        frame.add(descriptionJLabel);

        infoLabel = new JLabel();
        infoLabel.setBounds(10, 500, 450, 25);
        frame.add(infoLabel);


        relationComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) { relationKind = relationComboBox.getSelectedItem().toString(); }
        });


        targetRefactoringCb.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) { targetRefactoring = targetRefactoringCb.getSelectedItem().toString(); }
        });


        addRelationJButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                String str="";
                Relation relation = new Relation(relationKind,refactoring,dataManager.getRefactoringWithThisName(targetRefactoringCb.getSelectedItem().toString()),str);
                if (targetRefactoringCb.getSelectedIndex() == -1 || targetRefactoringCb.getSelectedItem().equals("Please select target refactoring") ||
                        relationComboBox.getSelectedIndex() == -1 || relationComboBox.getSelectedItem().equals("Please select relation kind")) {
                        setInfoLabel("Missing components..", Color.RED);
                } else if (targetRefactoringCb.getSelectedItem().equals(refactoring.getName())) {
                    setInfoLabel("Target refactoring can't be the same with Source refactoring!", Color.RED);
                }else if(dataManager.relationExistsInMap(refactoring, relation)) {
                    infoLabel.setText("Relation '" + relationKind + "' of refactoring '" + refactoring.getName() + "' exists already");
                    infoLabel.setForeground(Color.RED);
                } else{
                    int dialogResult = JOptionPane.showConfirmDialog (null, "Do you want to add '"+  refactoring.getName()+" "+relationComboBox.getSelectedItem()+" "+targetRefactoring+"' in existing relations?", "Yes", JOptionPane.YES_NO_OPTION);
                    if(dialogResult == JOptionPane.YES_OPTION) {
                        dataManager.addRelationToMap(refactoring, relation);
                        relationsListOfRefactoringJFrame.setInfoLabel("Relation added successfully", Color.GREEN);
                        relationsListOfRefactoringJFrame.updateRelationsList();
                        myToolWindowFactory.updateToolWindowContent(relationsListOfRefactoringJFrame.getContent());
                    }
                }
        }});

        goBackJButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                relationsListOfRefactoringJFrame.setInfoLabel("",Color.red);
                myToolWindowFactory.updateToolWindowContent(relationsListOfRefactoringJFrame.getContent());
            }
        });

        myToolWindowFactory.updateToolWindowContent(frame);

    }

    public String[] getRelationsComboBoxItems(){
        int counter=0;
        String[] comboBoxItems = new String[dataManager.getExistRelationsList().length+1];
        comboBoxItems[counter]= "Please select relation kind";
        for(String rel : dataManager.getExistRelationsList()){
            counter++;
            comboBoxItems[counter] = rel;
        }
        return comboBoxItems;
    }


    public String[] getRefactoringsComboBoxItems(){
        int counter=0;
        String[] comboBoxItems = new String[dataManager.getRefactoringsNames().length+1];
        comboBoxItems[counter]= "Please select target refactoring";
        for(String rel : dataManager.getRefactoringsNames()){
            counter++;
            comboBoxItems[counter] = rel;
        }
        return comboBoxItems;
    }

    public void setInfoLabel(String text, Color color){
        infoLabel.setText(text);
        infoLabel.setForeground(color.darker());
    }
}
