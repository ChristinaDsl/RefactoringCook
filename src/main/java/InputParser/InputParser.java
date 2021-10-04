package InputParser;

import ComponentsPackage.DataManager;
import ComponentsPackage.DataParams;
import ComponentsPackage.Refactoring;
import ComponentsPackage.Relation;
import GUI.RefactoringSettings;
import com.google.common.base.Splitter;
import com.google.common.collect.Lists;

import java.awt.*;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.*;

public class InputParser {

    private static DataManager dataManager=DataManager.getDataManager();
    private static InputParser inputParser;
    private HashMap<String,Refactoring> refactoringsInput;
    private ArrayList<Relation> relationsInput;
    private RefactoringSettings refactoringSettings;


    private void  InputParser(){}

    public static InputParser getInputParser() {
        if (inputParser == null) {
            inputParser= new InputParser();
        }
        return inputParser;
    }

    public void importData (RefactoringSettings refactoringSettings,String folderSavePath)
    {
        this.refactoringSettings = refactoringSettings;

        Scanner inputStream = null;
        refactoringsInput = new HashMap<>();
        relationsInput = new ArrayList<>();
        Integer emptyLines=0;
        try {
            inputStream = new Scanner(new FileInputStream(folderSavePath));
        } catch (FileNotFoundException e) {
            throw new NoSuchElementException();
        }

        while (inputStream.hasNextLine()) {
            emptyLines=0;
            String nextLine = inputStream.nextLine();
            while(nextLine.isEmpty()){
                emptyLines++;
                if(emptyLines==1){
                    if(inputStream.hasNextLine()){
                        nextLine = inputStream.nextLine();
                    }else{
                        emptyLines++;
                        break;
                    }
                }
            }


            if(nextLine.startsWith(DataParams.REFACTORINGS_PARAM[0][0])){
                readRefactoringData(inputStream);
            }else if(nextLine.startsWith(DataParams.RELATIONS_PARAM[0][0])) {
                readRelationData(inputStream);
            }else if(emptyLines==2){
                emptyLines=0;
                break;
            }else{
                throw new NoSuchElementException();
            }
        }
        saveRefactoringsToMap(refactoringsInput);
        saveRelationsToMap(relationsInput);
    }

    private String getParameterValueFromFileLine(String fileLine, String parameterStartField, String parameterEndField){
        return fileLine.substring(parameterStartField.length(), fileLine.length()-parameterEndField.length());
    }

    private void readRefactoringData(Scanner inputStream){
        String category="";
        String name="";
        String description="";
        String uses="";
        String mechanics="";
        String nextLine;

        nextLine = inputStream.nextLine();
        if(nextLine.startsWith(DataParams.REFACTORINGS_PARAM[1][0]) && nextLine.endsWith(DataParams.REFACTORINGS_PARAM[1][1])){
            name = getParameterValueFromFileLine(nextLine, DataParams.REFACTORINGS_PARAM[1][0], DataParams.REFACTORINGS_PARAM[1][1]);
        }else {
            throw new NoSuchElementException();
        }

        nextLine = inputStream.nextLine();
        if(nextLine.startsWith(DataParams.REFACTORINGS_PARAM[2][0]) && nextLine.endsWith(DataParams.REFACTORINGS_PARAM[2][1])){
            category = getParameterValueFromFileLine(nextLine, DataParams.REFACTORINGS_PARAM[2][0], DataParams.REFACTORINGS_PARAM[2][1]);
        }else{
            throw new NoSuchElementException();
        }


        nextLine = inputStream.nextLine();

        if(nextLine.startsWith(DataParams.REFACTORINGS_PARAM[3][0])){
            while(!nextLine.endsWith(DataParams.REFACTORINGS_PARAM[3][1])) {
                description += nextLine;
                nextLine = inputStream.nextLine();
                if (nextLine.startsWith(DataParams.REFACTORINGS_PARAM[4][0])) {
                    throw new NoSuchElementException();
                }
            }
        }else{
            throw new NoSuchElementException();
        }
        description+=nextLine;
        description = getParameterValueFromFileLine(description, DataParams.REFACTORINGS_PARAM[3][0], DataParams.REFACTORINGS_PARAM[3][1]);

        nextLine = inputStream.nextLine();
        if(nextLine.startsWith(DataParams.REFACTORINGS_PARAM[4][0]) ){
            while(!nextLine.endsWith(DataParams.REFACTORINGS_PARAM[4][1])){
                uses+=nextLine;
                nextLine=inputStream.nextLine();
                if(nextLine.startsWith(DataParams.REFACTORINGS_PARAM[5][0])){
                    throw new NoSuchElementException();
                }
            }
        }else{
            throw new NoSuchElementException();
        }
        uses+=nextLine;
        uses = getParameterValueFromFileLine(uses, DataParams.REFACTORINGS_PARAM[4][0], DataParams.REFACTORINGS_PARAM[4][1]);


        nextLine = inputStream.nextLine();
        if(nextLine.startsWith(DataParams.REFACTORINGS_PARAM[5][0])) {
            while(!nextLine.endsWith(DataParams.REFACTORINGS_PARAM[5][1])) {
                mechanics += nextLine;
                nextLine = inputStream.nextLine();
                if (nextLine.startsWith(DataParams.REFACTORINGS_PARAM[0][1])) {
                    throw new NoSuchElementException();
                }
            }
        }else{
            throw new NoSuchElementException();
        }
        mechanics+=nextLine;
        mechanics = getParameterValueFromFileLine(mechanics, DataParams.REFACTORINGS_PARAM[5][0], DataParams.REFACTORINGS_PARAM[5][1]);

        nextLine = inputStream.nextLine();
        if(nextLine.startsWith(DataParams.REFACTORINGS_PARAM[0][1]) && name!="" && category!="" && dataManager.categoryExists(category)) {
            if(!uses.contains(",")){

            }
            Refactoring refactoring = new Refactoring(name, category, description, Lists.newArrayList(Splitter.on(",").split(uses)), mechanics);
            refactoringsInput.put(name, refactoring);
        }else{
            throw new NoSuchElementException();
        }

    }

    private void readRelationData(Scanner inputStream) throws NoSuchElementException {
        String nextLine;
        String relationKind="";
        String sourceRefactoringName = "";
        String targetRefactoringName = "";
        String relationInfo = "";

        nextLine = inputStream.nextLine();
        if(nextLine.startsWith(DataParams.RELATIONS_PARAM[1][0]) && nextLine.endsWith(DataParams.RELATIONS_PARAM[1][1])){
            relationKind = getParameterValueFromFileLine(nextLine, DataParams.RELATIONS_PARAM[1][0], DataParams.RELATIONS_PARAM[1][1]);
        }else{
            throw new NoSuchElementException();
        }

        nextLine = inputStream.nextLine();
        if(nextLine.startsWith(DataParams.RELATIONS_PARAM[2][0]) && nextLine.endsWith(DataParams.RELATIONS_PARAM[2][1])){
            sourceRefactoringName = getParameterValueFromFileLine(nextLine, DataParams.RELATIONS_PARAM[2][0], DataParams.RELATIONS_PARAM[2][1]);
        }else{
            throw new NoSuchElementException();
        }

        nextLine = inputStream.nextLine();
        if(nextLine.startsWith(DataParams.RELATIONS_PARAM[3][0]) && nextLine.endsWith(DataParams.RELATIONS_PARAM[3][1])){
            targetRefactoringName = getParameterValueFromFileLine(nextLine, DataParams.RELATIONS_PARAM[3][0], DataParams.RELATIONS_PARAM[3][1]);

        }else{
            throw new NoSuchElementException();
        }


        nextLine = inputStream.nextLine();
        if(nextLine.startsWith(DataParams.RELATIONS_PARAM[4][0])) {
            while(!nextLine.endsWith(DataParams.RELATIONS_PARAM[4][1])) {
                relationInfo += nextLine;
                nextLine = inputStream.nextLine();
                if (nextLine.startsWith(DataParams.RELATIONS_PARAM[0][1])) {
                    throw new NoSuchElementException();
                }
            }
        }else{
            throw new NoSuchElementException();
        }
        relationInfo+=nextLine;
        relationInfo = getParameterValueFromFileLine(relationInfo, DataParams.RELATIONS_PARAM[4][0], DataParams.RELATIONS_PARAM[4][1]);

        nextLine = inputStream.nextLine();
        if(nextLine.startsWith(DataParams.RELATIONS_PARAM[0][1]) && relationKind!="" && sourceRefactoringName!="" && targetRefactoringName!="") {
            Relation relation = new Relation(relationKind, getRefactoring(sourceRefactoringName), getRefactoring(targetRefactoringName), relationInfo);
            relationsInput.add(relation);
        }else{
            throw new NoSuchElementException();
        }
    }

    private Refactoring getRefactoring(String refactoringName){
        Refactoring refactoring;
        if ((!dataManager.refactoringExistsInMap(refactoringName)) && !refactoringsInput.containsKey(refactoringName)) {
            throw new NoSuchElementException();
        }else if(refactoringsInput.containsKey(refactoringName)) {
            refactoring = refactoringsInput.get(refactoringName);
        }else{ refactoring = dataManager.getRefactoringWithThisName(refactoringName); }
        return refactoring;
    }

    private void saveRefactoringsToMap(HashMap<String,Refactoring> refactoringsInput){
        if(!refactoringsInput.isEmpty()){
            for(Refactoring refa: refactoringsInput.values()){
                dataManager.addRefactoringInSystemData(refa);
            }
            if(refactoringSettings != null){
                refactoringSettings.setInfoLabel("Successful import of data!", Color.GREEN);
            }
        }
    }

    private void saveRelationsToMap(ArrayList<Relation> relationsInput){
        if(!relationsInput.isEmpty()){
            for(Relation rel: relationsInput){
                if(!dataManager.relationExistsInMap(rel.getSourceRefactoring(), rel)){
                    dataManager.addRelationToMap(rel.getSourceRefactoring(), rel);}
            }
        }
    }

}
