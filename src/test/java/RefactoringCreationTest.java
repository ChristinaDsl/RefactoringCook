import ComponentsPackage.DataManager;
import ComponentsPackage.Refactoring;
import junit.framework.TestCase;
import java.util.ArrayList;

public class RefactoringCreationTest extends TestCase {

    private Refactoring refactoring;
    private Refactoring refactoring2;
    private Refactoring refactoring3;
    private Refactoring refactoring4;
    private DataManager dataManager;
    private ArrayList<String> uses;
    private ArrayList<String> uses2;

    public void setUp() throws Exception {
        super.setUp();
        uses = new ArrayList<>();
        uses2 = new ArrayList<>();

        dataManager = DataManager.getDataManager();

        uses.add("smell 1");
        uses.add("smell 2");
        uses2.add("smell 3");
        uses2.add("smell 4");

        refactoring = new Refactoring("refa102","Composing Methods","description of refa102",uses,"step 1,step 2");
        refactoring2 = new Refactoring("","Composing Methods","description of refa167",uses2,"step 3,step 1");
        refactoring3 = new Refactoring("refa167","Another Category","description of refa167",uses2,"step 3,step 1");
        refactoring4 = new Refactoring("refa168","Composing Methods","description of refa168",uses,"step 1,step 2");

        dataManager.addRefactoringInSystemData(refactoring);
        dataManager.addRefactoringInSystemData(refactoring4);
    }


    public void testCreateRefactoring(){
        Refactoring newRefactoring;
        String[] expectedUses= new String[2];

        expectedUses[0] = "refa102";
        expectedUses[1] = "refa168";

        newRefactoring = dataManager.getRefactoringWithThisName(refactoring.getName());

   
        assertTrue(dataManager.refactoringExistsInMap(newRefactoring.getName()));
        assertTrue(dataManager.refactoringExistsInCategory("Composing Methods", newRefactoring));

        assertEquals("Composing Methods",newRefactoring.getCategory());
        assertEquals("description of refa102",newRefactoring.getDescription());
        assertEquals(uses,newRefactoring.getUses());
        assertEquals("step 1,step 2",newRefactoring.getMechanics());

        assertTrue(dataManager.refactoringExistsInSmell("smell 1", refactoring.getName()));
        assertTrue(dataManager.refactoringExistsInSmell("smell 1", refactoring4.getName()));
    }


    public void testNotCreateRefactoring(){

        Integer initNumberOfRefactorings;
        Integer numberOfRefactorings;

        initNumberOfRefactorings = dataManager.getAllRefactorings().size();

        dataManager.addRefactoringInMap(refactoring);
        numberOfRefactorings = dataManager.getAllRefactorings().size();

        dataManager.addRefactoringInMap(refactoring2);
        dataManager.addRefactoringInMap(refactoring3);

        assertTrue(initNumberOfRefactorings==numberOfRefactorings);

        assertTrue(!dataManager.refactoringExistsInMap(refactoring2.getName()));

        assertTrue(!dataManager.refactoringExistsInMap(refactoring3.getName()));

    }

}
