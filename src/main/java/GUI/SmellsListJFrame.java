package GUI;

import ComponentsPackage.DataManager;
import OutputParser.ExportParser;
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


public class SmellsListJFrame {
    private DataManager dataManager=DataManager.getDataManager();
    private ExportParser exportParser;
    private JPanel frame;
    private JButton instructionsJButton;
    private JButton goBackJButton;
    private JList<String> smellsJList;
    private JScrollPane scrollableJList;
    private MyToolWindowFactory myToolWindowFactory;
    private TTSVoice ttsVoice;
    private FreeTTS freeTTS;

    public SmellsListJFrame(){

        myToolWindowFactory = new MyToolWindowFactory();
        dataManager = DataManager.getDataManager();
        exportParser = ExportParser.getExportParser();

        frame = new JPanel();
        frame.setSize(700, 400);
        frame.setLayout(null);
        frame.setVisible(true);

        smellsJList = new JBList();
        smellsJList.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        smellsJList.setForeground(Color.DARK_GRAY);
        smellsJList.setSelectionBackground(Color.white);
        smellsJList.setBounds(10,10, 200,300);
        prepareSmellsList();

        scrollableJList = new JScrollPane(smellsJList);
        scrollableJList.setBounds(10,70, 300,400);
        scrollableJList.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        scrollableJList.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        frame.add(scrollableJList);

        instructionsJButton = new JButton("How to solve this smell");
        instructionsJButton.setBounds(340, 70, 200, 25);
        frame.add(instructionsJButton);

        goBackJButton = new JButton("Back");
        goBackJButton.setForeground(Color.red.darker());
        goBackJButton.setBounds(10,10,70,40);
        frame.add(goBackJButton);

        instructionsJButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                if(smellsJList.getSelectedIndex() != -1){
                    ttsVoice = new FreeTTSVoice();
                    freeTTS = new FreeTTS(null,smellsJList.getSelectedValue(),ttsVoice);
                    freeTTS.howToSolveSmell();
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

    public void prepareSmellsList(){
        String totalSmells[] = dataManager.getSmells();

        smellsJList.setModel(new AbstractListModel() {
            String[] values = totalSmells;
            public int getSize() {
                return values.length;
            }
            public Object getElementAt(int index) {
                return values[index];
            }
        });

        addListSelectionListener(smellsJList);
    }

    public void addListSelectionListener(JList smellsJList){
        smellsJList.addListSelectionListener(new ListSelectionListener() {
            public void valueChanged(ListSelectionEvent e) {
                if (smellsJList.getSelectedIndex() != -1) {
                    instructionsJButton.setEnabled(true);
                } else if (smellsJList.getSelectedIndex() == -1) {
                    instructionsJButton.setEnabled(false);
                }
            }
        });
    }

    public JPanel getContent(){
        return frame;
    }

}
