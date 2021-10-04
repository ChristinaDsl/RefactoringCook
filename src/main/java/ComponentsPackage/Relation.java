package ComponentsPackage;

public class Relation {

    private String name;
    private Refactoring sourceRefactoring;
    private Refactoring targetRefactoring;
    private String relationInfo;

    public Relation(String name, Refactoring sourceRefactoring, Refactoring targetRefactoring,String relationInfo){
        this.name = name;
        this.sourceRefactoring = sourceRefactoring;
        this.targetRefactoring = targetRefactoring;
        this.relationInfo = relationInfo;
    }

    public String getName(){
        return name;
    }

    public Refactoring getSourceRefactoring(){
        return sourceRefactoring;
    }

    public Refactoring getTargetRefactoring(){
        return targetRefactoring;
    }

    public String getRelationInfo(){ return relationInfo; }

}
