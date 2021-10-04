import ComponentsPackage.DataManager;
import ComponentsPackage.Refactoring;
import ComponentsPackage.Relation;
import junit.framework.TestCase;

import java.util.ArrayList;

public class RelationRemoveTest extends TestCase {

    private Refactoring refactoring;
    private Refactoring refactoring2;
    private Refactoring refactoring3;
    private DataManager dataManager;
    private Relation relation;
    private Relation relation2;
    private Relation relation3;
    private ArrayList<String> uses;


    public void setUp() throws Exception {
        super.setUp();
        uses = new ArrayList<>();

        dataManager = DataManager.getDataManager();

        uses.add("smell 1");
        uses.add("smell 2");

        refactoring = new Refactoring("refa180","Composing Methods","description of refa180",uses,"step 1,step 2");
        refactoring2 = new Refactoring("refa181","Composing Methods","description of refa182",uses,"step 3,step 1");
        refactoring3 = new Refactoring("refa182","Another Category","description of refa182",uses,"step 3,step 1");

        relation = new Relation("Before",refactoring,refactoring2,"info of this relation");
        relation2 = new Relation("After",refactoring,refactoring3,"info of this relation");
        relation3 = new Relation("Instead of",refactoring,refactoring2,"info of this relation");
    }


    public void testRemoveRelation(){
        Integer sizeBeforeRemove;
        Integer sizeAfterRemove;
        ArrayList<Relation> relationsToRemove = new ArrayList<Relation>();

        dataManager.addRelationToMap(relation.getSourceRefactoring(),relation);
        dataManager.addRelationToMap(relation2.getSourceRefactoring(),relation2);
        dataManager.addRelationToMap(relation3.getSourceRefactoring(),relation3);
        relationsToRemove.add(relation2);
        relationsToRemove.add(relation3);

        sizeBeforeRemove = dataManager.getRelationsOfRefactoring(relation2.getSourceRefactoring()).size();
        dataManager.removeRelationFromRefactoring(relationsToRemove);
        sizeAfterRemove = dataManager.getRelationsOfRefactoring(relation2.getSourceRefactoring()).size();

        assertTrue(sizeBeforeRemove!=sizeAfterRemove);
        assertEquals("Before",dataManager.getRelationsOfRefactoring(relation.getSourceRefactoring()).get(0).getName());
        assertEquals("refa180",dataManager.getRelationsOfRefactoring(relation.getSourceRefactoring()).get(0).getSourceRefactoring().getName());
        assertEquals("refa181",dataManager.getRelationsOfRefactoring(relation.getSourceRefactoring()).get(0).getTargetRefactoring().getName());

    }

}
