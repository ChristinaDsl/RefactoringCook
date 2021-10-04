package TTS;

import ComponentsPackage.DataManager;
import ComponentsPackage.Refactoring;
import ComponentsPackage.Relation;

import java.util.ArrayList;
import java.util.List;

public class FakeVoice implements TTSVoice {

    private DataManager dataManager;
    private String descriptionText="";
    private String usesText="";
    private String mechanicsText="";
    private String relationsText="";
    private String howToSolveThisSmellText="";

    public FakeVoice(){
        dataManager = DataManager.getDataManager();
    }


    public void description(Refactoring refactoring){
        if (refactoring.getDescription().isEmpty()) {
            descriptionText= "There is no description of this refactoring";
        } else {
            descriptionText= refactoring.getDescription();
        }
    }

    public void uses(Refactoring refactoring) {
        List<String> uses = refactoring.getUses();

        if(refactoring.getUses().size()==1) {
            usesText = "There are no uses of this refactoring";
        }else{
            usesText="You can apply "+refactoring.getName()+" in ";
            for(String use: uses){ usesText+=use+" ";}
        }
    }

    public void mechanics(Refactoring refactoring) {
        String mechanics = refactoring.getMechanics();
        if(refactoring.getMechanics().isEmpty()) {
            mechanicsText="There are no instructions to apply this refactoring";
        }else{ mechanicsText=mechanics;}
    }


    public void relations(Refactoring refactoring) {
        ArrayList<Relation> relations;

        relations = dataManager.getRelationsOfRefactoring(refactoring);
        if(relations.isEmpty()){
            relationsText="There are no other relative refactorings to this one";
        }else{
            relationsText+="You can apply "+refactoring.getName()+" ";
            for(Relation relation : relations){
                relationsText+=relation.getName()+" "+relation.getTargetRefactoring().getName()+",";
            }

        }
    }


    public void howToSolveSmell(String smell) {
        String[] refactorings = DataManager.getDataManager().getRefactoringsOfSmell(smell);
            howToSolveThisSmellText += "To solve " + smell + " you can use ";
            for (String refact : refactorings) {
                howToSolveThisSmellText += refact;
                if (!refact.equals(refactorings[refactorings.length - 1])) {
                    howToSolveThisSmellText += " or ";
                }
            }
            howToSolveThisSmellText += "\n";

    }

    public String getDescriptionText(){
        return descriptionText;
    }

    public String getUsesText() {
        return usesText;
    }

    public String getMechanicsText() {
        return mechanicsText;
    }

    public String getRelationsText(){
        return relationsText;
    }

    public String getHowToSolveThisSmellText() {
        return howToSolveThisSmellText;
    }



}
