package GUI;

import InputParser.InputParser;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class MainMenu {
    private JButton refactoringSettings;
    private JButton refactoringRecommendations;
    private JPanel myToolWindowContent;
    private JButton showSmellsButton;
    private InputParser inputParser;


    public MainMenu(){
        inputParser= InputParser.getInputParser();

        myToolWindowContent = new JPanel();

        refactoringSettings = new JButton("Refactoring Settings");
        refactoringSettings.setBounds(130, 100, 250, 40);
        myToolWindowContent.add(refactoringSettings);

        refactoringRecommendations = new JButton("Refactoring Recommendations");
        refactoringRecommendations.setBounds(130, 100, 250, 40);
        myToolWindowContent.add(refactoringRecommendations);

        showSmellsButton = new JButton("Smells");
        showSmellsButton.setBounds(130, 100, 250, 40);
        myToolWindowContent.add(showSmellsButton);

        refactoringSettings.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                new RefactoringSettings();
            }
        });

        refactoringRecommendations.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                new RefactoringRecommendations();
            }
        });

        showSmellsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                new SmellsListJFrame();
            }
        });
    }

    public JPanel getContent(){
        return myToolWindowContent;
    }




}
