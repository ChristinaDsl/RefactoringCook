import ComponentsPackage.DataManager;
import ComponentsPackage.Refactoring;
import ComponentsPackage.Relation;
import junit.framework.TestCase;

import java.util.ArrayList;
import java.util.List;

public class RelationCreationTest extends TestCase {

    private DataManager dataManager;
    private Relation relation;
    private Relation relation2;
    private Relation relation3;
    private Relation relation4;
    private Relation relation5;
    private Refactoring refactoring;
    private Refactoring refactoring2;
    private Refactoring refactoring3;
    private Refactoring refactoring4;
    private Refactoring refactoring5;

    public void setUp() throws Exception {
        super.setUp();
        List<String> uses;
        dataManager = DataManager.getDataManager();

        uses = new ArrayList<>();
        refactoring = new Refactoring("refa177","Composing Methods","description of refa177",uses,"step 1,step 2");
        refactoring2 = new Refactoring("refa178","Composing Methods","description of refa178",uses,"step 3,step 1");
        refactoring3 = new Refactoring("refa179","Composing Methods","description of refa179",uses,"step 3,step 1");

        relation = new Relation("Before",refactoring,refactoring2,"info of this relation");
        relation2 = new Relation("After",refactoring2,refactoring3,"info of this relation");
        relation3 = new Relation("",refactoring3,refactoring,"info of this relation");
    }


    public void testCreateRelation(){
        Relation newRelation;
        Integer initNumberOfRelations;
        Integer numberOfRelations;


        dataManager.addRelationToMap(refactoring,relation);
        initNumberOfRelations = dataManager.getRelationsOfRefactoring(refactoring).size();
        dataManager.addRelationToMap(refactoring,relation);
        numberOfRelations = dataManager.getRelationsOfRefactoring(refactoring).size();

        newRelation = dataManager.getRelation(relation.getName(),relation.getSourceRefactoring().getName(),relation.getTargetRefactoring().getName());

        assertTrue(initNumberOfRelations==numberOfRelations);
        assertTrue(dataManager.relationExistsInMap(newRelation.getSourceRefactoring(),newRelation));
        assertEquals("Before",newRelation.getName());
        assertEquals("refa177",newRelation.getSourceRefactoring().getName());
        assertEquals("refa178",newRelation.getTargetRefactoring().getName());
        assertEquals("info of this relation",newRelation.getRelationInfo());
    }


    public void testNotCreateRelation(){

        dataManager.addRelationToMap(refactoring3,relation3);

        assertTrue(!dataManager.relationExistsInMap(refactoring3,relation3));

    }
}
