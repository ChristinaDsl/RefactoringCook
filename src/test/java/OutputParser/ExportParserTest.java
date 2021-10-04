package OutputParser;

import ComponentsPackage.DataManager;
import ComponentsPackage.DataParams;
import ComponentsPackage.Refactoring;
import ComponentsPackage.Relation;
import junit.framework.TestCase;
import org.junit.Test;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ExportParserTest extends TestCase {
    private DataManager dataManager;
    private ExportParser exportParser;
    private ArrayList<Refactoring> dataToExport;
    private Refactoring refactoring;
    private Refactoring refactoring2;
    private Relation relation;

    public void setUp() throws Exception {
        super.setUp();
        List<String> uses;
        dataToExport = new ArrayList<>();
        dataManager = DataManager.getDataManager();
        exportParser = ExportParser.getExportParser();
        uses = new ArrayList<>();
        uses.add("smell 1");
        uses.add("smell 2");
        refactoring = new Refactoring("refa159","Composing Methods","description of refa159",uses,"step 1,step 2");
        refactoring2 = new Refactoring("refa160","Composing Methods","description of refa160",uses,"step 3,step 1");
        relation = new Relation("Before",refactoring,refactoring2,"info of this relation");
        dataManager.addRefactoringInMap(refactoring);
        dataManager.addRefactoringInMap(refactoring2);
        dataManager.addRelationToMap(refactoring,relation);
        dataToExport.add(refactoring);
        dataToExport.add(refactoring2);
    }


    public void testExportData() {
        Scanner inputStream = null;
        String folderSavePath = "Files/exportTesting.xml";

        exportParser.exportData(folderSavePath,dataToExport);
        try {
            inputStream = new Scanner(new FileInputStream(folderSavePath));
        } catch (FileNotFoundException e) {
            System.out.println("Problem opening input data file.");
            System.exit(0);
        }
        
        assertEquals(DataParams.REFACTORINGS_PARAM[0][0],inputStream.nextLine());
        assertEquals(DataParams.REFACTORINGS_PARAM[1][0]+refactoring.getName()+DataParams.REFACTORINGS_PARAM[1][1],inputStream.nextLine());
        assertEquals(DataParams.REFACTORINGS_PARAM[2][0]+refactoring.getCategory()+DataParams.REFACTORINGS_PARAM[2][1],inputStream.nextLine());
        assertEquals(DataParams.REFACTORINGS_PARAM[3][0]+refactoring.getDescription()+DataParams.REFACTORINGS_PARAM[3][1],inputStream.nextLine());
        assertEquals(DataParams.REFACTORINGS_PARAM[4][0]+String.join(",", refactoring.getUses())+DataParams.REFACTORINGS_PARAM[4][1],inputStream.nextLine());
        assertEquals(DataParams.REFACTORINGS_PARAM[5][0]+refactoring.getMechanics()+DataParams.REFACTORINGS_PARAM[5][1],inputStream.nextLine());
        assertEquals(DataParams.REFACTORINGS_PARAM[0][1],inputStream.nextLine());
        assertEquals("",inputStream.nextLine());

        assertEquals(DataParams.REFACTORINGS_PARAM[0][0],inputStream.nextLine());
        assertEquals(DataParams.REFACTORINGS_PARAM[1][0]+refactoring2.getName()+DataParams.REFACTORINGS_PARAM[1][1],inputStream.nextLine());
        assertEquals(DataParams.REFACTORINGS_PARAM[2][0]+refactoring2.getCategory()+DataParams.REFACTORINGS_PARAM[2][1],inputStream.nextLine());
        assertEquals(DataParams.REFACTORINGS_PARAM[3][0]+refactoring2.getDescription()+DataParams.REFACTORINGS_PARAM[3][1],inputStream.nextLine());
        assertEquals(DataParams.REFACTORINGS_PARAM[4][0]+String.join(",", refactoring2.getUses())+DataParams.REFACTORINGS_PARAM[4][1],inputStream.nextLine());
        assertEquals(DataParams.REFACTORINGS_PARAM[5][0]+refactoring2.getMechanics()+DataParams.REFACTORINGS_PARAM[5][1],inputStream.nextLine());
        assertEquals(DataParams.REFACTORINGS_PARAM[0][1],inputStream.nextLine());
        assertEquals("",inputStream.nextLine());

        assertEquals(DataParams.RELATIONS_PARAM[0][0],inputStream.nextLine());
        assertEquals(DataParams.RELATIONS_PARAM[1][0]+relation.getName()+DataParams.RELATIONS_PARAM[1][1],inputStream.nextLine());
        assertEquals(DataParams.RELATIONS_PARAM[2][0]+relation.getSourceRefactoring().getName()+DataParams.RELATIONS_PARAM[2][1],inputStream.nextLine());
        assertEquals(DataParams.RELATIONS_PARAM[3][0]+relation.getTargetRefactoring().getName()+DataParams.RELATIONS_PARAM[3][1],inputStream.nextLine());
        assertEquals(DataParams.RELATIONS_PARAM[4][0]+relation.getRelationInfo()+DataParams.RELATIONS_PARAM[4][1],inputStream.nextLine());
        assertEquals(DataParams.RELATIONS_PARAM[0][1],inputStream.nextLine());


    }
}