import ComponentsPackage.DataManager;
import ComponentsPackage.Refactoring;
import junit.framework.TestCase;

import java.util.ArrayList;

public class RefactoringRemoveTest extends TestCase {

    private Refactoring refactoring;
    private Refactoring refactoring2;
    private Refactoring refactoring3;
    private DataManager dataManager;
    private ArrayList<String> uses;


    public void setUp() throws Exception {
        super.setUp();
        uses = new ArrayList<>();

        dataManager = DataManager.getDataManager();

        uses.add("smell 1");
        uses.add("smell 2");

        refactoring = new Refactoring("refa170","Composing Methods","description of refa170",uses,"step 1,step 2");
        refactoring2 = new Refactoring("refa171","Composing Methods","description of refa172",uses,"step 3,step 1");
        refactoring3 = new Refactoring("refa172","Another Category","description of refa172",uses,"step 3,step 1");

        dataManager.addRefactoringInMap(refactoring);
        dataManager.addRefactoringInMap(refactoring2);
        dataManager.addRefactoringInMap(refactoring3);
    }


    public void testRemoveRefactoring(){
        ArrayList<String> refactoringsToRemove = new ArrayList<String>();

        refactoringsToRemove.add(refactoring2.getName());
        refactoringsToRemove.add(refactoring3.getName());

        dataManager.removeRefactoringFromMap(refactoringsToRemove);

        for(String refactoring: refactoringsToRemove){
            assertTrue(!dataManager.refactoringExistsInMap(refactoring));
        }
    }

}
