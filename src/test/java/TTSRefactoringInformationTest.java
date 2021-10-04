import ComponentsPackage.DataManager;
import ComponentsPackage.Refactoring;
import ComponentsPackage.Relation;
import TTS.FakeVoice;
import TTS.FreeTTS;
import junit.framework.TestCase;

import java.util.ArrayList;

public class TTSRefactoringInformationTest extends TestCase {
    private Refactoring refactoring;
    private Refactoring refactoring2;
    private Refactoring refactoring3;
    private DataManager dataManager;
    private Relation relation;
    private Relation relation2;
    private Relation relation3;
    private ArrayList<String> uses;
    private ArrayList<String> uses2;
    private ArrayList<String> uses3;
    private FreeTTS freeTTS;
    private FakeVoice fakeVoice;
    private Integer count;

    public void setUp() throws Exception {
        super.setUp();
        uses = new ArrayList<>();
        uses2 = new ArrayList<>();
        uses3 = new ArrayList<>();

        uses3.add("");
        fakeVoice = new FakeVoice();
        dataManager = DataManager.getDataManager();

        uses.add("smell 1");
        uses.add("smell 2");
        uses2.add("smell 1");
        uses2.add("smell 3");


        refactoring = new Refactoring("refa190","Composing Methods","description of refa190",uses,"step 1,step 2");
        refactoring2 = new Refactoring("refa191","Composing Methods","",uses2,"");
        refactoring3 = new Refactoring("refa192","Moving Organizing Data","description of refa192",uses3,"step 3,step 1");

        dataManager.addRefactoringInMap(refactoring);
        dataManager.addRefactoringInMap(refactoring2);
        dataManager.addRefactoringInMap(refactoring3);

    }


    public void testListenDescription(){
        freeTTS = new FreeTTS(refactoring,null,fakeVoice);
        freeTTS.description();
        assertEquals("description of refa190",fakeVoice.getDescriptionText());
    }

    public void testListenThatHasNoDescription(){
        freeTTS = new FreeTTS(dataManager.getRefactoringWithThisName(refactoring2.getName()),null,fakeVoice);
        freeTTS.description();
        assertEquals("There is no description of this refactoring",fakeVoice.getDescriptionText());
    }

    public void testListenUses(){
        freeTTS = new FreeTTS(refactoring,null,fakeVoice);
        freeTTS.uses();
        assertEquals("You can apply "+refactoring.getName()+" in "+"smell 1 "+"smell 2 ",fakeVoice.getUsesText());
    }

    public void testListenThatHasNoUses(){
        freeTTS = new FreeTTS(dataManager.getRefactoringWithThisName(refactoring3.getName()),null,fakeVoice);
        freeTTS.uses();
        assertEquals("There are no uses of this refactoring",fakeVoice.getUsesText());
    }

    public void testListenMechanics(){
        freeTTS = new FreeTTS(refactoring,null,fakeVoice);
        freeTTS.mechanics();
        assertEquals("step 1,step 2",fakeVoice.getMechanicsText());

    }

    public void testListenThatHasNoMechanics(){
        freeTTS = new FreeTTS(dataManager.getRefactoringWithThisName(refactoring2.getName()),null,fakeVoice);
        freeTTS.mechanics();
        assertEquals("There are no instructions to apply this refactoring",fakeVoice.getMechanicsText());
    }

}
