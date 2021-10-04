import ComponentsPackage.DataManager;
import ComponentsPackage.Refactoring;
import ComponentsPackage.Relation;
import TTS.FakeVoice;
import TTS.FreeTTS;
import junit.framework.TestCase;

import java.util.ArrayList;

public class TTSSolveSmellTest extends TestCase {
    private Refactoring refactoring;
    private Refactoring refactoring2;
    private Refactoring refactoring3;
    private DataManager dataManager;
    private Relation relation;
    private Relation relation2;
    private Relation relation3;
    private ArrayList<String> uses;
    private ArrayList<String> uses2;
    private FreeTTS freeTTS;
    private FakeVoice fakeVoice;

    public void setUp() throws Exception {
        super.setUp();
        uses = new ArrayList<>();
        uses2 = new ArrayList<>();

        fakeVoice = new FakeVoice();
        dataManager = DataManager.getDataManager();

        uses.add("smell 1");
        uses.add("smell 2");
        uses2.add("smell 1");
        uses2.add("smell 3");

        refactoring = new Refactoring("refa200","Composing Methods","description of refa200",uses,"step 1,step 2");
        refactoring2 = new Refactoring("refa201","Composing Methods","description of refa201",uses2,"step 3,step 1");
        refactoring3 = new Refactoring("refa202","Moving Organizing Data","description of refa202",uses2,"step 3,step 1");

        dataManager.addRefactoringInSystemData(refactoring);
        dataManager.addRefactoringInSystemData(refactoring2);
        dataManager.addRefactoringInSystemData(refactoring3);

    }

    public void testListenHowToSolveSmell(){
        freeTTS = new FreeTTS(null,"smell 1",fakeVoice);
        freeTTS.howToSolveSmell();
        assertEquals("To solve smell 1 you can use refa200 or refa201 or refa202\n",fakeVoice.getHowToSolveThisSmellText());
    }
}
