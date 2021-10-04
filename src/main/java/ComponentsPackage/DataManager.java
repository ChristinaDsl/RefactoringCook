package ComponentsPackage;

import java.util.*;

public class DataManager {
    private static DataManager dataManager = DataManager.getDataManager();
    private ArrayList<String> typesOfRelation = new ArrayList<>();
    private LinkedHashMap<Refactoring, ArrayList<Relation>> myRelationsMap = new LinkedHashMap<>();
    private LinkedHashMap<String, ArrayList<Refactoring>> mySmellsMap = new LinkedHashMap<>();
    private String[] categories = {"Composing Methods", "Moving Features Between Objects", "Moving Organizing Data", "Simplifying Conditional Expressions", "Making Method Calls Simpler", "Dealing with Generalization"};
    private static final LinkedHashMap<String, ArrayList<Refactoring>> refactoringCategoriesMap = new LinkedHashMap<String, ArrayList<Refactoring>>() {{
        put("Composing Methods", new ArrayList<Refactoring>());
        put("Moving Features Between Objects", new ArrayList<Refactoring>());
        put("Moving Organizing Data", new ArrayList<Refactoring>());
        put("Simplifying Conditional Expressions", new ArrayList<Refactoring>());
        put("Making Method Calls Simpler", new ArrayList<Refactoring>());
        put("Dealing with Generalization", new ArrayList<Refactoring>());
    }};
    private Object NoSuchElementException;

    private DataManager() {
    }

    public static DataManager getDataManager() {
        if (dataManager == null) {
            dataManager = new DataManager();
        }
        return dataManager;
    }

    public void initializeTypesOfRelationList() {
        typesOfRelation.add("Before");
        typesOfRelation.add("After");
        typesOfRelation.add("Instead of");
    }

    public boolean categoryExists(String category) {
        return refactoringCategoriesMap.containsKey(category);
    }

    public String[] getCategories() {
        return categories;
    }

    public boolean refactoringExistsInCategory(String category, Refactoring refactoring) {
        if (refactoringExistsInMap(refactoring.getName())) {
            return refactoringCategoriesMap.get(category).contains(getRefactoringWithThisName(refactoring.getName()));
        } else {
            return false;
        }
    }

    public boolean refactoringExistsInSmell(String smell,String refactoring) {
        if (smellExists(smell)) {
            if (mySmellsMap.get(smell).contains(getRefactoringWithThisName(refactoring))) {
                return true;
            } else {
                return false;
            }
        } else {
            throw new NoSuchElementException();
        }
    }

    public String[] getRefactoringsOfCategory(String category) {
        String[] refactorings = new String[refactoringCategoriesMap.get(category).size()];
        int c = 0;
        for (Refactoring refactoring : refactoringCategoriesMap.get(category)) {
            refactorings[c++] = refactoring.getName();
        }
        return refactorings;
    }

    public boolean smellExists(String smell) {
        if (mySmellsMap.containsKey(smell)) {
            return true;
        }
        return false;
    }

    public String[] getSmells() {
        String[] keys = new String[mySmellsMap.keySet().size()];
        int c = 0;
        for (String smell : mySmellsMap.keySet()) {
            keys[c++] = smell;
        }
        return keys;
    }

    public String[] getRefactoringsOfSmell(String smell) {
        if (smellExists(smell)) {

            String[] refactorings = new String[mySmellsMap.get(smell).size()];
            int c = 0;
            for (Refactoring refactoring : mySmellsMap.get(smell)) {
                refactorings[c++] = refactoring.getName();
            }
            return refactorings;
        } else {
            throw new NoSuchElementException();
        }
    }

    public String[] getExistRelationsList() {
        String[] types = new String[typesOfRelation.size()];
        int c = 0;
        for (String type : typesOfRelation) {
            types[c++] = type;
        }

        return types;
    }

    public Refactoring getRefactoringWithThisName(String name) {
        for (Refactoring refactoring : getAllRefactorings()) {
            if (refactoring.getName().equalsIgnoreCase(name)) {
                return refactoring;
            }
        }
        throw new NoSuchElementException();
    }

    public ArrayList<Relation> getRelationsOfRefactoring(Refactoring refactoring) {
        if (!refactoringExistsInMap(refactoring.getName())) {
            addRefactoringInMap(refactoring);
        }
        return myRelationsMap.get(getRefactoringWithThisName(refactoring.getName()));
    }

    public ArrayList<Refactoring> getAllRefactorings() {
        ArrayList<Refactoring> refactorings = new ArrayList<>();
        int c = 0;
        for (Refactoring ref : myRelationsMap.keySet()) {
            refactorings.add(ref);
        }
        return refactorings;
    }

    public String[] getRefactoringsNames() {
        String[] keys = new String[myRelationsMap.keySet().size()];
        int c = 0;
        for (Refactoring ref : myRelationsMap.keySet()) {
            keys[c++] = ref.getName();
        }
        return keys;
    }

    public boolean relationExistsInMap(Refactoring refactoring, Relation relation) {
        if (refactoringExistsInMap(refactoring.getName())) {
            for (Relation rel : getRelationsOfRefactoring(getRefactoringWithThisName(refactoring.getName()))) {
                if (rel.getName().equals(relation.getName()) && rel.getSourceRefactoring().getName().equals(refactoring.getName())
                        && rel.getTargetRefactoring().getName().equals(relation.getTargetRefactoring().getName())) {
                    return true;
                }
            }
        }
        return false;
    }

    public Relation getRelation(String relationKind, String sourceRefactoring, String targetRefactoring) {
        if (refactoringExistsInMap(sourceRefactoring)) {
            ArrayList<Relation> relations = myRelationsMap.get(getRefactoringWithThisName(sourceRefactoring));
            for (Relation rel : relations) {
                if (rel.getName().equals(relationKind) && rel.getSourceRefactoring().getName().equals(sourceRefactoring)
                        && rel.getTargetRefactoring().getName().equals(targetRefactoring)) {
                    return rel;
                }
            }
        }

        throw new NoSuchElementException();
    }

    public boolean refactoringExistsInMap(String refactoring) {
        for (Refactoring ref : getAllRefactorings()) {
            if (ref.getName().equals(refactoring)) {
                return true;
            }
        }
        return false;
    }


    public void addRefactoringToCategory(Refactoring refactoring) {
        String category = refactoring.getCategory();
        if (categoryExists(refactoring.getCategory())) {
            if (!refactoringExistsInCategory(category, dataManager.getRefactoringWithThisName(refactoring.getName()))) {
                refactoringCategoriesMap.get(category).add(dataManager.getRefactoringWithThisName(refactoring.getName()));
            }
        }
    }

    public void removeRefactoringFromCategory(String refactoring, String category) {
        if (categoryExists(category)) {
            if (refactoringExistsInCategory(category, dataManager.getRefactoringWithThisName(refactoring)))
                refactoringCategoriesMap.get(category).remove(getRefactoringWithThisName(refactoring));
        }
    }


    private void addRefactoringToSmell(String smell, Refactoring refactoring) {
        if (!refactoringExistsInSmell(smell, refactoring.getName())) {
            mySmellsMap.get(smell).add(refactoring);
        }
    }

    public void addSmellsOfRefactoringToMap(Refactoring refactoring) {
        List<String> smells = new ArrayList<String>();
        smells = refactoring.getUses();
        for (String smell : smells) {
            if (!smellExists(smell)) {
                mySmellsMap.put(smell, new ArrayList<Refactoring>());
            }
            addRefactoringToSmell(smell, refactoring);
        }
    }


    public void addNewRelationType(String relationType) {
        typesOfRelation.add(relationType);
    }


    public void addRelationToMap(Refactoring refactoring, Relation relation) {
        Refactoring sourceRefactoring;
        Refactoring targetRefactoring;

        if (!refactoringExistsInMap(refactoring.getName()) && refactoring.getName() != "") {
            sourceRefactoring = refactoring;
            addRefactoringInMap(refactoring);
        } else {
            sourceRefactoring = getRefactoringWithThisName(refactoring.getName());
        }

        if (!refactoringExistsInMap(relation.getTargetRefactoring().getName()) && relation.getTargetRefactoring().getName() != "") {
            targetRefactoring = relation.getTargetRefactoring();
            addRefactoringInMap(targetRefactoring);
        }

        if (relation.getName() != "" && relation.getSourceRefactoring().getName() != "" && relation.getTargetRefactoring().getName() != "" && !relationExistsInMap(refactoring, relation)) {
            myRelationsMap.get(getRefactoringWithThisName(sourceRefactoring.getName())).add(relation);
        }
    }


    public void removeRelationFromRefactoring(ArrayList<Relation> relations) {

        for (Relation relation : relations) {
            myRelationsMap.get(dataManager.getRefactoringWithThisName(relation.getSourceRefactoring().getName())).remove(getRelation(relation.getName(), relation.getSourceRefactoring().getName(), relation.getTargetRefactoring().getName()));
        }
    }

    public void addRefactoringInSystemData(Refactoring refactoring) {
        addRefactoringInMap(refactoring);
        if(refactoringExistsInMap(refactoring.getName())) {
            addRefactoringToCategory(getRefactoringWithThisName(refactoring.getName()));
            addSmellsOfRefactoringToMap(getRefactoringWithThisName(refactoring.getName()));
        }
    }

    public void addRefactoringInMap(Refactoring refactoring) {
        if (refactoring.getName() != "" && !refactoringExistsInMap(refactoring.getName()) && categoryExists(refactoring.getCategory())) {
            myRelationsMap.put(refactoring, new ArrayList<Relation>());
        }
    }

    public void removeRefactoringFromSmells(Refactoring refactoring, List<String> smells) {
        for (String smell : smells) {
            if (refactoringExistsInSmell(smell,refactoring.getName())) {
                mySmellsMap.get(smell).remove(dataManager.getRefactoringWithThisName(refactoring.getName()));
            }
            if(getRefactoringsOfSmell(smell).length==0){
                myRelationsMap.remove(smell);
            }
        }

    }

    public void removeRefactoringDataFromSystem(ArrayList<String> refactorings) {
        for (String refactoring : refactorings) {
            removeRefactoringFromSmells(getRefactoringWithThisName(refactoring), getRefactoringWithThisName(refactoring).getUses());
            removeRefactoringFromCategory(refactoring, getRefactoringWithThisName(refactoring).getCategory());
        }
        removeRefactoringFromMap(refactorings);
    }

    public void removeRefactoringFromMap(ArrayList<String> refactorings) {

        ArrayList<Refactoring> refactoringsToRemove = new ArrayList<>();
        Set<Refactoring> keys = myRelationsMap.keySet();
        for (Refactoring refactoring : keys) {
            if (refactorings.contains(refactoring.getName())) {
                refactoringsToRemove.add(getRefactoringWithThisName(refactoring.getName()));
            }
        }
        for (Refactoring refactoring : refactoringsToRemove){
            myRelationsMap.remove(getRefactoringWithThisName(refactoring.getName()));
        }
    }
}
