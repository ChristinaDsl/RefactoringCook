import ComponentsPackage.DataManager;
import ComponentsPackage.Refactoring;
import ComponentsPackage.Relation;
import TTS.FakeVoice;
import TTS.FreeTTS;
import junit.framework.TestCase;
import org.junit.Before;
import org.junit.BeforeClass;

import java.util.ArrayList;

public class TTSRefactoringRecommendationsTest extends TestCase {

    private Refactoring refactoring;
    private Refactoring refactoring2;
    private Refactoring refactoring3;
    private DataManager dataManager;
    private Relation relation;
    private Relation relation2;
    private Relation relation3;
    private ArrayList<String> uses;
    private FreeTTS freeTTS;
    private FakeVoice fakeVoice;

    public void setUp() throws Exception {
        super.setUp();
        uses = new ArrayList<>();

        fakeVoice = new FakeVoice();
        dataManager = DataManager.getDataManager();

        refactoring = new Refactoring("refa163","Composing Methods","description of refa163",uses,"step 1,step 2");
        refactoring2 = new Refactoring("refa164","Composing Methods","description of refa164",uses,"step 3,step 1");
        refactoring3 = new Refactoring("refa165","Moving Organizing Data","description of refa165",uses,"step 3,step 1");

        dataManager.addRefactoringInMap(refactoring);
        dataManager.addRefactoringInMap(refactoring2);
        dataManager.addRefactoringInMap(refactoring3);

        relation = new Relation("Before",refactoring,refactoring2,"info of this relation");
        relation2 = new Relation("After",refactoring2,refactoring3,"info of this relation");
        relation3 = new Relation("Instead of",refactoring,refactoring3,"info of this relation");

        dataManager.addRelationToMap(refactoring,relation);
        dataManager.addRelationToMap(refactoring2,relation2);
        dataManager.addRelationToMap(refactoring,relation3);

    }

    public void testListenRecommendationsOfRefactorings(){
        freeTTS = new FreeTTS(refactoring,null,fakeVoice);
        freeTTS.relations();
        assertEquals("You can apply "+refactoring.getName()+" "+relation.getName()+" "+relation.getTargetRefactoring().getName()+","
                +relation3.getName()+" "+relation3.getTargetRefactoring().getName()+",",fakeVoice.getRelationsText());
    }

    public void testListenThatRefactoringHasNoRecommendations(){
        freeTTS = new FreeTTS(dataManager.getRefactoringWithThisName(refactoring3.getName()),null,fakeVoice);
        freeTTS.relations();
        assertEquals("There are no other relative refactorings to this one",fakeVoice.getRelationsText());
    }



}
