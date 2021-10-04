package ComponentsPackage;

import junit.framework.TestCase;
import org.junit.Assert;
import java.util.ArrayList;
import java.util.List;

public class DataManagerTest extends TestCase {

    private Refactoring refactoring;
    private Refactoring refactoring2;
    private Refactoring refactoring3;
    private Relation relation;
    private Relation relation2;
    private DataManager dataManager;
    private String relationType;
    private ArrayList<String> uses = new ArrayList<>();
    private ArrayList<String> uses2 = new ArrayList<>();


    public void setUp() throws Exception{
        super.setUp();
        ArrayList<String> uses = new ArrayList<>();
        ArrayList<String> uses2 = new ArrayList<>();
        dataManager= DataManager.getDataManager();

        uses.add("smell 1");
        uses.add("smell 2");
        uses2.add("smell 1");
        uses2.add("smell 3");


        refactoring = new Refactoring("refa159","Composing Methods","description of refa159",uses,"step 1,step 2");
        refactoring2 = new Refactoring("refa160","Moving Features Between Objects","description of refa160",uses2,"step 3,step 1");
        refactoring3 = new Refactoring("refa161","Composing Methods","description of refa161",uses2,"step 3,step 1");


        dataManager.addRefactoringInSystemData(refactoring);
        dataManager.addRefactoringInSystemData(refactoring2);
        dataManager.addRefactoringInSystemData(refactoring3);

        relation = new Relation("Before", refactoring, refactoring3, "info of this relation");
        relation2 = new Relation("After", refactoring3, refactoring, "info of this relation");

        dataManager.addRelationToMap(relation.getSourceRefactoring(),relation);
        dataManager.addRelationToMap(relation2.getSourceRefactoring(),relation2);

    }


    public void testAddRefactoringToCategory() {
        String[] refactoringsOfCategory1;
        String[] refactoringsOfCategory2;

        refactoringsOfCategory1 = dataManager.getRefactoringsOfCategory("Moving Features Between Objects");
        refactoringsOfCategory2=  dataManager.getRefactoringsOfCategory("Composing Methods");

        Assert.assertTrue(dataManager.refactoringExistsInCategory(refactoring2.getCategory(),refactoring2));
        Assert.assertTrue(dataManager.refactoringExistsInCategory(refactoring.getCategory(),refactoring));
        Assert.assertTrue(dataManager.refactoringExistsInCategory(refactoring3.getCategory(),refactoring3));
    }


    public void testAddRefactoringInMap() {
        Assert.assertTrue(dataManager.getAllRefactorings().contains(dataManager.getRefactoringWithThisName(refactoring.getName())));
        Assert.assertTrue(dataManager.getAllRefactorings().contains(dataManager.getRefactoringWithThisName(refactoring2.getName())));
        Assert.assertTrue(dataManager.getAllRefactorings().contains(dataManager.getRefactoringWithThisName(refactoring3.getName())));
    }



    public void testAddSmellsOfRefactoringToMap() {

        List<String> smells1 = new ArrayList<>();
        List<String> smells2 = new ArrayList<>();
        String[] refactoringsOfSmell = new String[3];

        smells1 = refactoring.getUses();
        smells2 = refactoring2.getUses();
        refactoringsOfSmell = dataManager.getRefactoringsOfSmell(refactoring.getUses().get(0));

        for(String smell: smells1){
            Assert.assertTrue(dataManager.smellExists(smell));
        }

        for(String smell: smells2){
            Assert.assertTrue(dataManager.smellExists(smell));
        }

        for(String refa :refactoringsOfSmell){
            Assert.assertTrue(dataManager.refactoringExistsInSmell("smell 1",refa));
        }

    }



    public void testAddNewRelationType() {
        relationType = "NewRelationType";
        dataManager.addNewRelationType(relationType);
        Assert.assertEquals( "NewRelationType", dataManager.getExistRelationsList()[dataManager.getExistRelationsList().length-1]);
    }



    public void testAddRelationToMap() {
        ArrayList<Relation> relations;

        relations = dataManager.getRelationsOfRefactoring(refactoring);

        for (Relation relation : relations) {
            Assert.assertTrue(dataManager.relationExistsInMap(relation.getSourceRefactoring(),relation));
            Assert.assertEquals(relation.getName(), "Before");
            Assert.assertEquals(relation.getSourceRefactoring().getName(), refactoring.getName());
            Assert.assertEquals(relation.getTargetRefactoring().getName(), refactoring3.getName());
            Assert.assertEquals(relation.getRelationInfo(), "info of this relation");
        }
    }


    public void testRemoveRelationFromRefactoring() {
        ArrayList<Relation> relationsToDelete = new ArrayList<>();

        Assert.assertTrue(dataManager.relationExistsInMap(relation.getSourceRefactoring(), relation));
        Assert.assertTrue(dataManager.relationExistsInMap(relation2.getSourceRefactoring(), relation2));

        relationsToDelete.add(relation);
        relationsToDelete.add(relation2);

        dataManager.removeRelationFromRefactoring(relationsToDelete);


        Assert.assertTrue(!dataManager.relationExistsInMap(relation.getSourceRefactoring(),relation));
        Assert.assertTrue(!dataManager.relationExistsInMap(relation2.getSourceRefactoring(),relation2));

    }


    public void testRemoveRefactoringFromCategory(){

        Assert.assertTrue(dataManager.refactoringExistsInCategory("Composing Methods",refactoring));
        Assert.assertTrue(dataManager.refactoringExistsInCategory("Moving Features Between Objects",refactoring2));

        dataManager.removeRefactoringFromCategory(refactoring.getName(),refactoring.getCategory());
        dataManager.removeRefactoringFromCategory(refactoring2.getName(),refactoring2.getCategory());

        Assert.assertTrue(!dataManager.refactoringExistsInCategory("Composing Methods",refactoring));
        Assert.assertTrue(!dataManager.refactoringExistsInCategory("Moving Features Between Objects",refactoring2));

    }

    public void testRemoveRefactoringFromMap() {
        ArrayList<String> refactoringsToDelete = new ArrayList<>();
        Assert.assertTrue(dataManager.refactoringExistsInMap(refactoring.getName()));
        Assert.assertTrue(dataManager.refactoringExistsInMap(refactoring2.getName()));

        refactoringsToDelete.add(refactoring.getName());
        refactoringsToDelete.add(refactoring2.getName());
        dataManager.removeRefactoringFromMap(refactoringsToDelete);

        Assert.assertTrue(!dataManager.refactoringExistsInMap(refactoring.getName()));
        Assert.assertTrue(!dataManager.refactoringExistsInMap(refactoring2.getName()));

    }

}