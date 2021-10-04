package InputParser;

import ComponentsPackage.DataManager;
import ComponentsPackage.DataParams;
import ComponentsPackage.Refactoring;
import ComponentsPackage.Relation;
import junit.framework.TestCase;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class InputParserTest extends TestCase {
    InputParser inputParser = InputParser.getInputParser();
    DataManager dataManager = DataManager.getDataManager();
    Scanner inputStream;
    String folderSavePath;

    public void setUp() throws Exception {
        super.setUp();
        folderSavePath = "data2.xml";
        //folderSavePath = "Files/importTesting.xml";
        inputParser.importData(null,folderSavePath);
    }


    public void testImportData() {
        Refactoring refactoring;
        Relation relation;
        String nextLine;
        String uses;

        try {
            inputStream = new Scanner(new FileInputStream(folderSavePath));
        } catch (FileNotFoundException e) {
            System.exit(0);
        }
        inputStream.nextLine();
        nextLine = inputStream.nextLine();


        refactoring = dataManager.getRefactoringWithThisName(getParameterValueFromFileLine(nextLine,DataParams.REFACTORINGS_PARAM[1][0],DataParams.REFACTORINGS_PARAM[1][1]));
        assertNotNull(refactoring);

        assertTrue(dataManager.refactoringExistsInMap(refactoring.getName()));
        assertTrue(dataManager.refactoringExistsInCategory(refactoring.getCategory(),refactoring));

        assertEquals(DataParams.REFACTORINGS_PARAM[2][0]+refactoring.getCategory()+DataParams.REFACTORINGS_PARAM[2][1],inputStream.nextLine());
        assertEquals(DataParams.REFACTORINGS_PARAM[3][0]+refactoring.getDescription()+DataParams.REFACTORINGS_PARAM[3][1],inputStream.nextLine());
        assertEquals(DataParams.REFACTORINGS_PARAM[4][0]+String.join(",", refactoring.getUses())+DataParams.REFACTORINGS_PARAM[4][1],inputStream.nextLine());
        assertEquals(DataParams.REFACTORINGS_PARAM[5][0]+refactoring.getMechanics()+DataParams.REFACTORINGS_PARAM[5][1],inputStream.nextLine());
        inputStream.nextLine();
        inputStream.nextLine();
        inputStream.nextLine();

        nextLine = inputStream.nextLine();

        refactoring = dataManager.getRefactoringWithThisName(getParameterValueFromFileLine(nextLine,DataParams.REFACTORINGS_PARAM[1][0],DataParams.REFACTORINGS_PARAM[1][1]));
        assertNotNull(refactoring);
        assertEquals(DataParams.REFACTORINGS_PARAM[1][0]+refactoring.getName()+DataParams.REFACTORINGS_PARAM[1][1],nextLine);
        assertEquals(DataParams.REFACTORINGS_PARAM[2][0]+refactoring.getCategory()+DataParams.REFACTORINGS_PARAM[2][1],inputStream.nextLine());
        assertEquals(DataParams.REFACTORINGS_PARAM[3][0]+refactoring.getDescription()+DataParams.REFACTORINGS_PARAM[3][1],inputStream.nextLine());
        uses = String.join(",", refactoring.getUses());
        assertEquals(DataParams.REFACTORINGS_PARAM[4][0]+uses+DataParams.REFACTORINGS_PARAM[4][1],inputStream.nextLine());
        assertEquals(DataParams.REFACTORINGS_PARAM[5][0]+refactoring.getMechanics()+DataParams.REFACTORINGS_PARAM[5][1],inputStream.nextLine());

        inputStream.nextLine();
        inputStream.nextLine();
        inputStream.nextLine();

        String type = getParameterValueFromFileLine(inputStream.nextLine(),DataParams.RELATIONS_PARAM[1][0],DataParams.RELATIONS_PARAM[1][1]);
        String source = getParameterValueFromFileLine(inputStream.nextLine(),DataParams.RELATIONS_PARAM[2][0],DataParams.RELATIONS_PARAM[2][1]);
        String target = getParameterValueFromFileLine(inputStream.nextLine(),DataParams.RELATIONS_PARAM[3][0],DataParams.RELATIONS_PARAM[3][1]);

        relation = dataManager.getRelation(type,source,target);

        assertNotNull(relation);
        assertEquals(relation.getName(),type);
        assertEquals(relation.getSourceRefactoring().getName(),source);
        assertEquals(relation.getTargetRefactoring().getName(),target);
        assertEquals(DataParams.RELATIONS_PARAM[4][0]+relation.getRelationInfo()+DataParams.RELATIONS_PARAM[4][1],inputStream.nextLine());
    }

    private String getParameterValueFromFileLine(String fileLine, String parameterStartField, String parameterEndField){
        return fileLine.substring(parameterStartField.length(), fileLine.length()-parameterEndField.length());
    }
}