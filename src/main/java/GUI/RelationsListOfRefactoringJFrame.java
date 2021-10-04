package GUI;

import ComponentsPackage.DataManager;
import ComponentsPackage.Refactoring;
import ComponentsPackage.Relation;
import com.intellij.ui.components.JBList;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class RelationsListOfRefactoringJFrame {
    private DataManager dataManager;
    private Refactoring refactoring;
    private JPanel frame;
    private RelationsListOfRefactoringJFrame relationsListOfRefactoringJFrame;
    private RefactoringsListJFrame refactoringsListJFrame;
    private JList<String> relationsJList;
    private JScrollPane scrollableJList;
    private JLabel infoLabel;
    private JButton removeRelationJButton;
    private JButton selectAllJButton;
    private JButton addRelationJButton;
    private JButton goBackJButton;
    private MyToolWindowFactory myToolWindowFactory;
    private JLabel categoryJLabel;

    public RelationsListOfRefactoringJFrame(Refactoring refactoring, RefactoringsListJFrame refactoringsListJFrame, RefactoringSettings refactoringSettings) {
        this.refactoringsListJFrame = refactoringsListJFrame;
        this.refactoring = refactoring;
        relationsListOfRefactoringJFrame = this;

        myToolWindowFactory = new MyToolWindowFactory();

        dataManager = DataManager.getDataManager();

        frame = new JPanel();
        frame.setSize(700, 400);
        frame.setLayout(null);
        frame.setVisible(true);

        relationsJList = new JBList();
        relationsJList.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        relationsJList.setForeground(Color.CYAN);
        relationsJList.setForeground(Color.DARK_GRAY);
        relationsJList.setSelectionBackground(Color.white);
        relationsJList.setBounds(10, 10, 200, 200);
        updateRelationsList();

        scrollableJList = new JScrollPane(relationsJList);
        scrollableJList.setBounds(10, 80, 300, 400);
        scrollableJList.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        scrollableJList.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        frame.add(scrollableJList);

        addRelationJButton = new JButton("Add a relation");
        addRelationJButton.setBounds(340, 80, 250,40 );
        frame.add(addRelationJButton);

        removeRelationJButton = new JButton("Remove relation");
        removeRelationJButton.setBounds(340, 140, 250,40 );
        frame.add(removeRelationJButton);

        selectAllJButton = new JButton("Select All");
        selectAllJButton.setBounds(10, 340, 250,40 );
        frame.add(selectAllJButton);

        goBackJButton = new JButton("Back");
        goBackJButton.setForeground(Color.red.darker());
        goBackJButton.setBounds(10,10,70,40);
        frame.add(goBackJButton);

        infoLabel = new JLabel();
        infoLabel.setBounds(340, 200, 400, 200);
        frame.add(infoLabel);


        removeRelationJButton.setEnabled(false);
        relationsJList.addListSelectionListener(new ListSelectionListener() {
            public void valueChanged(ListSelectionEvent e) {
                if (relationsJList.getSelectedIndex() != -1) { removeRelationJButton.setEnabled(true);
                } else { removeRelationJButton.setEnabled(false); }
            }
        });


        addRelationJButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                new RelationCreatorJFrame(refactoring,relationsListOfRefactoringJFrame);
                //setInfoLabel("Relation(s) added successfully", Color.GREEN);
            }
        });


        removeRelationJButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                ArrayList<String> selectedRelations = (ArrayList<String>) relationsJList.getSelectedValuesList();
                ArrayList<Relation> itemsToDelete = new ArrayList<>();
                String targetRefactoring="";
                String relation="";
                int i=0;
                if (selectedRelations.size() > 0){
                    int dialogResult = JOptionPane.showConfirmDialog(null, "Do you want to remove selected relations?", "Yes", JOptionPane.YES_NO_OPTION);
                    if (dialogResult == JOptionPane.YES_OPTION) {
                        for (String rel: selectedRelations) {
                            String[] temp = rel.split(" ");
                            if(temp[0].equals("Instead")){
                                relation = temp[0]+" "+temp[1];

                                i=2;
                            }
                            else{
                                relation = temp[0];
                                i=1;
                            }
                            for(int y=i;y<temp.length;y++){
                                if(y==temp.length-1){
                                    targetRefactoring+=temp[y];
                                }else {
                                    targetRefactoring += temp[y] + " ";
                                }
                            }

                            itemsToDelete.add(dataManager.getRelation(relation,refactoring.getName(),targetRefactoring));
                        }
                        dataManager.removeRelationFromRefactoring(itemsToDelete);
                        setInfoLabel("Relation(s) removed successfully", Color.GREEN);
                        updateRelationsList();
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Nothing selected", "", JOptionPane.WARNING_MESSAGE);
                }
            }
        });

        selectAllJButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                relationsJList.setSelectionInterval(0, relationsJList.getModel().getSize()-1);
            }
        });

        goBackJButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                myToolWindowFactory.updateToolWindowContent(refactoringsListJFrame.getContent());
            }
        });

        myToolWindowFactory.updateToolWindowContent(frame);
    }

    public void setInfoLabel(String text, Color color){
        infoLabel.setText(text);
        infoLabel.setForeground(color.darker());
    }

    public void updateRelationsList(){

        int counter=0;
        String relations[] = new String[dataManager.getRelationsOfRefactoring(refactoring).size()];
        for(Relation rel: dataManager.getRelationsOfRefactoring(refactoring)){
            relations[counter] = rel.getName()+" "+rel.getTargetRefactoring().getName();
            counter++;
        }
        relationsJList.setModel(new AbstractListModel() {
            String[] values = relations;
            public int getSize() {
                return values.length;
            }
            public Object getElementAt(int index) {
                return values[index];
            }
        });

        categoryJLabel = new JLabel(refactoring.getName());
        categoryJLabel.setBounds(10,55,250,25);
        frame.add(categoryJLabel);
        categoryJLabel.setVisible(true);
    }

    public JPanel getContent(){
        return frame;
    }
}
