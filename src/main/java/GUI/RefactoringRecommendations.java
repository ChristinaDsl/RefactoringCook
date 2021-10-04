package GUI;

import ComponentsPackage.DataManager;
import ComponentsPackage.DataParams;
import TTS.FreeTTS;
import TTS.FreeTTSVoice;
import TTS.TTSVoice;
import com.intellij.ui.components.JBList;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.LinkedHashMap;

public class RefactoringRecommendations {
    private DataManager dataManager=DataManager.getDataManager();
    private JPanel frame;
    private RefactoringRecommendations refactoringRecommendations;
    private JButton descriptionJButton;
    private JButton usesJButton;
    private JButton mechanicsJButton;
    private JButton relationsJButton;
    private JButton nextCategoryJButton;
    private JButton previousCategoryJButton;
    private JButton goBackJButton;
    private JLabel infoLabel;
    private JScrollPane scrollableJList;
    private String currentCategory;
    private JList currentJList;
    private JLabel categoryJLabel;
    private LinkedHashMap<String, JBList> refactoringCategoriesJBLists = DataParams.refactoringCategoriesJBLists;
    private MyToolWindowFactory myToolWindowFactory;
    private TTSVoice ttsVoice;
    private FreeTTS freeTTS;

    public RefactoringRecommendations() {
        this.refactoringRecommendations = this;
        dataManager = DataManager.getDataManager();

        myToolWindowFactory = new MyToolWindowFactory();

        frame = new JPanel();
        frame.setSize(700, 500);
        frame.setLayout(null);
        frame.setVisible(true);

        makeCurrentRefactoringsList("Composing Methods");

        infoLabel = new JLabel();
        infoLabel.setBounds(300, 395, 500, 25);
        frame.add(infoLabel);

        descriptionJButton = new JButton("Refactoring Description");
        descriptionJButton.setBounds(510, 80, 200, 25);
        frame.add(descriptionJButton);

        usesJButton = new JButton("Use of Refactoring");
        usesJButton.setBounds(510, 120, 200, 25);
        frame.add(usesJButton);

        mechanicsJButton = new JButton("How to apply refactoring");
        mechanicsJButton.setBounds(510, 160, 200, 25);
        frame.add(mechanicsJButton);

        relationsJButton = new JButton("Relations");
        relationsJButton.setBounds(510, 200, 200, 25);
        frame.add(relationsJButton);

        nextCategoryJButton = new JButton("Next category");
        nextCategoryJButton.setForeground(Color.red.darker());
        nextCategoryJButton.setBounds(330, 350, 150, 25);
        frame.add(nextCategoryJButton);

        previousCategoryJButton = new JButton("Previous category");
        previousCategoryJButton.setForeground(Color.red.darker());
        previousCategoryJButton.setBounds(10, 350, 150, 25);
        frame.add(previousCategoryJButton);

        goBackJButton = new JButton("Back");
        goBackJButton.setBounds(10,10,70,40);
        goBackJButton.setForeground(Color.red.darker());
        frame.add(goBackJButton);

        descriptionJButton.setEnabled(false);
        usesJButton.setEnabled(false);
        mechanicsJButton.setEnabled(false);
        relationsJButton.setEnabled(false);

        descriptionJButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                if (currentJList.getSelectedIndex() != -1) {
                    freeTTS.description();
                }
            }
        });

        usesJButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                if (currentJList.getSelectedIndex() != -1) {
                    freeTTS.uses();
                }
            }
        });

        mechanicsJButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                if (currentJList.getSelectedIndex() != -1) {
                    freeTTS.mechanics();
                }
            }
        });

        descriptionJButton.setEnabled(false);
        usesJButton.setEnabled(false);
        mechanicsJButton.setEnabled(false);
        relationsJButton.setEnabled(false);



        relationsJButton.addActionListener((new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                if (currentJList.getSelectedIndex() != -1) {
                    freeTTS.relations();
                }

            }
        }));

        nextCategoryJButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                ArrayList<String> listOfKeys = new ArrayList<String>(refactoringCategoriesJBLists.keySet());
                for(int i=0; i< listOfKeys.size();i++){
                    if(currentCategory==listOfKeys.get(i)){
                        if (i == listOfKeys.size()-1){ break;}
                        scrollableJList.setVisible(false);
                        categoryJLabel.setVisible(false);
                        makeCurrentRefactoringsList(listOfKeys.get(i+1));

                        break;
                    }
                }
            }
        });

        previousCategoryJButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                ArrayList<String> listOfKeys = new ArrayList<String>(refactoringCategoriesJBLists.keySet());
                for(int i=0; i< listOfKeys.size();i++){
                    if(currentCategory==listOfKeys.get(i)){
                        if (i == 0){ break;}
                        scrollableJList.setVisible(false);
                        categoryJLabel.setVisible(false);
                        makeCurrentRefactoringsList(listOfKeys.get(i-1));
                        break;
                    }
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

        myToolWindowFactory.updateToolWindowContent(frame);
    }


    public void makeCurrentRefactoringsList(String category){

        currentCategory = category;
        currentJList = refactoringCategoriesJBLists.get(category);

        categoryJLabel = new JLabel(category);
        categoryJLabel.setBounds(10,60,250,25);
        frame.add(categoryJLabel);
        categoryJLabel.setVisible(true);

        scrollableJList = new JScrollPane(currentJList);
        scrollableJList.setBounds(10,80, 470,250);
        scrollableJList.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        scrollableJList.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollableJList.setForeground(Color.DARK_GRAY);
        scrollableJList.setBackground(Color.white);
        frame.add(scrollableJList);
        scrollableJList.setVisible(true);

        String[] categories = dataManager.getCategories();
        refactoringCategoriesJBLists.get(category).setModel(new AbstractListModel() {
            String[] values = dataManager.getRefactoringsOfCategory(category);

            public int getSize() {
                return values.length;
            }

            public Object getElementAt(int index) {
                return values[index];
            }

        });

        addListSelectionListener();
    }

    public void addListSelectionListener() {

        currentJList.addListSelectionListener(new ListSelectionListener() {
            public void valueChanged(ListSelectionEvent e) {
                if (currentJList.getSelectedIndex() != -1) {
                    ttsVoice = new FreeTTSVoice();
                    freeTTS = new FreeTTS(dataManager.getRefactoringWithThisName(currentJList.getSelectedValue().toString()),null,ttsVoice);
                    descriptionJButton.setEnabled(true);
                    usesJButton.setEnabled(true);
                    mechanicsJButton.setEnabled(true);
                    relationsJButton.setEnabled(true);
                } else if (currentJList.getSelectedIndex() == -1) {
                    descriptionJButton.setEnabled(false);
                    usesJButton.setEnabled(false);
                    mechanicsJButton.setEnabled(false);
                    relationsJButton.setEnabled(false);
                }
            }
        });
    }

    public JPanel getContent(){
        return frame;
    }
}
