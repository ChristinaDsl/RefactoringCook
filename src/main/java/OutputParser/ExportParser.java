package OutputParser;

import ComponentsPackage.DataManager;
import ComponentsPackage.DataParams;
import ComponentsPackage.Refactoring;
import ComponentsPackage.Relation;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;



public class ExportParser {
    private static ExportParser exportParser;
    private String folderSavePath;
    private ArrayList<Refactoring> refactoringsArrayList;
    private DataManager dataManager;

    public static ExportParser getExportParser() {
        if (exportParser == null) {
                exportParser = new ExportParser();
        }
        return exportParser;
    }

    public void exportData(String folderSavePath,ArrayList<Refactoring> refactoringsArrayList){

        this.refactoringsArrayList= refactoringsArrayList;
        PrintWriter outputStream = null;

        dataManager = DataManager.getDataManager();
        this.folderSavePath=folderSavePath;

        try
        {
            outputStream = new PrintWriter(new FileOutputStream(folderSavePath));
        }
        catch(FileNotFoundException e)
        {
            System.out.println("Problem opening: "+folderSavePath);
        }
        exportRefactorings(outputStream);
        exportRelations(outputStream);

        outputStream.close();
    }


    private void exportRefactorings(PrintWriter outputStream){
        String uses;
        for(Refactoring refactoring: refactoringsArrayList){
            outputStream.println(DataParams.REFACTORINGS_PARAM[0][0]);
            outputStream.println(DataParams.REFACTORINGS_PARAM[1][0]+refactoring.getName()+DataParams.REFACTORINGS_PARAM[1][1]);
            outputStream.println(DataParams.REFACTORINGS_PARAM[2][0]+refactoring.getCategory()+DataParams.REFACTORINGS_PARAM[2][1]);
            outputStream.println(DataParams.REFACTORINGS_PARAM[3][0]+refactoring.getDescription()+DataParams.REFACTORINGS_PARAM[3][1]);

            uses = String.join(",", refactoring.getUses());
            outputStream.println(DataParams.REFACTORINGS_PARAM[4][0]+uses+DataParams.REFACTORINGS_PARAM[4][1]);
            outputStream.println(DataParams.REFACTORINGS_PARAM[5][0]+refactoring.getMechanics()+DataParams.REFACTORINGS_PARAM[5][1]);
            outputStream.println(DataParams.REFACTORINGS_PARAM[0][1]);
            outputStream.println("");
        }
    }

    private void exportRelations(PrintWriter outputStream){
        List<Relation> relations = new ArrayList<>();
        for(Refactoring refactoring: refactoringsArrayList){
            relations = dataManager.getRelationsOfRefactoring(dataManager.getRefactoringWithThisName(refactoring.getName()));
            for(Relation relation: dataManager.getRelationsOfRefactoring(refactoring)) {
                outputStream.println(DataParams.RELATIONS_PARAM[0][0]);
                outputStream.println(DataParams.RELATIONS_PARAM[1][0] + relation.getName() + DataParams.RELATIONS_PARAM[1][1]);
                outputStream.println(DataParams.RELATIONS_PARAM[2][0] + relation.getSourceRefactoring().getName() + DataParams.RELATIONS_PARAM[2][1]);
                outputStream.println(DataParams.RELATIONS_PARAM[3][0] + relation.getTargetRefactoring().getName() + DataParams.RELATIONS_PARAM[3][1]);
                outputStream.println(DataParams.RELATIONS_PARAM[4][0] + relation.getRelationInfo() + DataParams.RELATIONS_PARAM[4][1]);
                outputStream.println(DataParams.RELATIONS_PARAM[0][1]);
                outputStream.println("");
            }
        }

    }
}
