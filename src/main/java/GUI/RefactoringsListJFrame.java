package GUI;

import ComponentsPackage.DataManager;
import ComponentsPackage.DataParams;
import ComponentsPackage.Refactoring;
import OutputParser.ExportParser;
import com.intellij.ui.components.JBList;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.LinkedHashMap;

public class RefactoringsListJFrame {

    private DataManager dataManager=DataManager.getDataManager();
    private ExportParser exportParser;
    private JPanel frame;
    private RefactoringsListJFrame refactoringsListJFrame;
    private JButton addRefactoringJButton;
    private JButton removeRefactoringJButton;
    private JButton showRelationsJButton;
    private JButton exportRefactoringsJButton;
    private JButton selectAllJButton;
    private JButton nextCategoryJButton;
    private JButton previousCategoryJButton;
    private JButton goBackJButton;
    private JLabel infoLabel;
    private JScrollPane scrollableJList;
    private JLabel categoryJLabel;
    private int framesHeight=500;
    private String currentCategory;
    private JList currentJList;
    private  LinkedHashMap<String, JBList> refactoringCategoriesJBLists = DataParams.refactoringCategoriesJBLists;
    private MyToolWindowFactory myToolWindowFactory;
    public RefactoringsListJFrame(RefactoringSettings refactoringSettings){
        myToolWindowFactory = new MyToolWindowFactory();

        frame = new JPanel();
        frame.setSize(900, 380);
        frame.setLayout(null);

        makeCurrentRefactoringsList("Composing Methods");
        this.refactoringsListJFrame = this;
        dataManager = DataManager.getDataManager();
        exportParser = ExportParser.getExportParser();

        initializeJBLists();

        frame.setVisible(true);

        infoLabel = new JLabel();
        infoLabel.setBounds(140, 395, 500, 25);
        frame.add(infoLabel);

        addRefactoringJButton = new JButton("Add a new refactoring");
        addRefactoringJButton.setBounds(510, 80, 200, 25);
        frame.add(addRefactoringJButton);

        removeRefactoringJButton = new JButton("Remove refactoring");
        removeRefactoringJButton.setBounds(510,120 , 200, 25);
        frame.add(removeRefactoringJButton);

        showRelationsJButton = new JButton("Show relations");
        showRelationsJButton.setBounds(510, 160, 200, 25);
        frame.add(showRelationsJButton);

        exportRefactoringsJButton = new JButton("Export refactoring(s)");
        exportRefactoringsJButton.setBounds(510, 200, 200, 25);
        frame.add(exportRefactoringsJButton);

        selectAllJButton = new JButton(("Select all"));
        selectAllJButton.setBounds(380, 45, 100, 25);
        frame.add(selectAllJButton);

        nextCategoryJButton =  new JButton(("Next category"));
        nextCategoryJButton.setForeground(Color.red.darker());
        nextCategoryJButton.setBounds(330, 350, 150, 25);
        frame.add(nextCategoryJButton);

        previousCategoryJButton =  new JButton(("Previous category"));
        previousCategoryJButton.setForeground(Color.red.darker());
        previousCategoryJButton.setBounds(10, 350, 150, 25);
        frame.add(previousCategoryJButton);

        goBackJButton = new JButton("Back");
        goBackJButton.setForeground(Color.red.darker());
        goBackJButton.setBounds(10,10,70,40);
        frame.add(goBackJButton);

        removeRefactoringJButton.setEnabled(false);
        showRelationsJButton.setEnabled(false);


        addRefactoringJButton.addActionListener((new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                infoLabel.setVisible(false);
                new RefactoringCreatorJFrame("",refactoringsListJFrame);
            }
        }));


        removeRefactoringJButton.addActionListener((new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                ArrayList<String> itemsToDelete= new ArrayList<>();
                ArrayList<String> selectedRefactorings = (ArrayList<String>) currentJList.getSelectedValuesList();
                infoLabel.setVisible(false);
                if (currentJList.getSelectedValuesList().size() >0){
                    int dialogResult = JOptionPane.showConfirmDialog (null, "Do you want to delete selected refactorings?", "Yes", JOptionPane.YES_NO_OPTION);
                    if(dialogResult == JOptionPane.YES_OPTION){
                        for(int i=0;i<selectedRefactorings.size(); i++) {
                            itemsToDelete.add(selectedRefactorings.get(i));
                        }
                        dataManager.removeRefactoringDataFromSystem(itemsToDelete);
                        setInfoLabel("Selected refactorings removed successfully",Color.GREEN);
                        updateRefactoringsList(currentCategory);
                    }
                }else{
                    JOptionPane.showMessageDialog(null, "Nothing selected", "", JOptionPane.WARNING_MESSAGE);
                }
            }
        }));

        showRelationsJButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                infoLabel.setVisible(false);
                if(currentJList.getSelectedIndex()!= -1){
                    new RelationsListOfRefactoringJFrame(dataManager.getRefactoringWithThisName(currentJList.getSelectedValue().toString()),refactoringsListJFrame, refactoringSettings);
                }
            }
        });

        exportRefactoringsJButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                ListModel model;

                ArrayList<Refactoring> exportingItems= new ArrayList<>();
                infoLabel.setVisible(false);
                if (currentJList.getSelectedValuesList().size() >0){
                    ArrayList<String> selectedRefactorings = (ArrayList<String>) currentJList.getSelectedValuesList();
                    for(int i=0;i<selectedRefactorings.size(); i++) { exportingItems.add(dataManager.getRefactoringWithThisName(selectedRefactorings.get(i))); }
                }else{
                    for(String category: refactoringCategoriesJBLists.keySet()){
                        model = refactoringCategoriesJBLists.get(category).getModel();
                        for ( int i = 0 ; i< model.getSize() ; i++){
                            exportingItems.add(dataManager.getRefactoringWithThisName(model.getElementAt(i).toString()));
                        }
                    }

                }
                if(exportingItems.size() !=0 ) {
                    String filePath = refactoringSettings.selectFilePath();
                    if (filePath!= null) {
                        int dialogResult = JOptionPane.showConfirmDialog(null, "Do you want to export selected refactorings and its relations to XML file?", "Yes", JOptionPane.YES_NO_OPTION);
                        if (dialogResult == JOptionPane.YES_OPTION) {
                            exportParser.exportData(filePath, exportingItems);
                            setInfoLabel(" Selected refactorings and its relations successfully exported to XML file", Color.GREEN);
                        }
                    }else{
                        setInfoLabel("Unsuccessful export of refactorings and its relations to file",Color.RED);
                    }
                }
            }
        });

        selectAllJButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                currentJList.setSelectionInterval(0, currentJList.getModel().getSize()-1);
                showRelationsJButton.setEnabled(false);
            }
        });


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
                        infoLabel.setVisible(false);
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
                        infoLabel.setVisible(false);
                        break;
                    }
                }
            }
        });

        goBackJButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                new RefactoringSettings();
            }
        });

    }


    public void updateRefactoringsList(String refactoringCategory) {

        refactoringCategoriesJBLists.get(refactoringCategory).setModel(new AbstractListModel() {
            String[] values = dataManager.getRefactoringsOfCategory(refactoringCategory);

            public int getSize() {
                return values.length;
            }

            public Object getElementAt(int index) {
                return values[index];
            }

        });

        myToolWindowFactory.updateToolWindowContent(frame);
    }

    public void setInfoLabel(String text, Color color){
        infoLabel.setVisible(true);
        infoLabel.setText(text);
        infoLabel.setForeground(color.darker());
    }

    public void initializeJBLists(){
        for(String cate: refactoringCategoriesJBLists.keySet()){
            JBList jlist;
            jlist = refactoringCategoriesJBLists.get(cate);
            jlist.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
            jlist.setForeground(Color.BLACK);
            jlist.setSelectionBackground(Color.white);
            jlist.setBounds(10,25, 500,170);
        }
    }

    public void makeCurrentRefactoringsList(String category){

        currentCategory = category;
        currentJList = refactoringCategoriesJBLists.get(category);

        categoryJLabel = new JLabel(category);
        categoryJLabel.setBounds(10,55,250,25);
        frame.add(categoryJLabel);
        categoryJLabel.setVisible(true);

        scrollableJList = new JScrollPane(currentJList);
        scrollableJList.setBounds(10,80, 470,250);
        scrollableJList.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        scrollableJList.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        frame.add(scrollableJList);
        scrollableJList.setVisible(true);

        updateRefactoringsList(currentCategory);
        addListSelectionListener();
    }
    public void addListSelectionListener() {
        currentJList.addListSelectionListener(new ListSelectionListener() {
            public void valueChanged(ListSelectionEvent e) {
                if (currentJList.getSelectedIndex() != -1) {
                    removeRefactoringJButton.setEnabled(true);
                    showRelationsJButton.setEnabled(true);
                    exportRefactoringsJButton.setEnabled(true);
                } else if (currentJList.getSelectedIndex() == -1) {
                    removeRefactoringJButton.setEnabled(false);
                    showRelationsJButton.setEnabled(false);
                }
            }
        });
    }

    public JPanel getContent(){
        return frame;
    }

}
