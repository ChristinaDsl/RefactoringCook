package TTS;

import ComponentsPackage.DataManager;
import ComponentsPackage.Refactoring;
import ComponentsPackage.Relation;
import com.sun.speech.freetts.Voice;
import com.sun.speech.freetts.VoiceManager;

import java.util.ArrayList;
import java.util.List;

public class FreeTTSVoice implements TTSVoice {

    private Voice voice;
    private DataManager dataManager = DataManager.getDataManager();

    public FreeTTSVoice() {
        System.setProperty("freetts.voices", "com.sun.speech.freetts.en.us.cmu_us_kal.KevinVoiceDirectory");
        voice = VoiceManager.getInstance().getVoice("kevin");
    }

    @Override
    public void description(Refactoring refactoring) {
        if (voice != null) {
            voice.allocate();
        }
        try {
            prepareVoice();
            if (refactoring.getDescription().isEmpty()) {
                voice.speak("There is no description of this refactoring");
            } else {
                voice.speak(refactoring.getDescription());
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void uses(Refactoring refactoring) {
        List<String> uses = refactoring.getUses();

        String text;
        if (voice != null) {
            voice.allocate();
        }
        try {
            prepareVoice();


            if (refactoring.getUses().get(0).equalsIgnoreCase("")) {
                voice.speak("There are no uses of this refactoring");
            } else {
                text = "You can apply " + refactoring.getName() + " in ";
                for (String use : uses) {
                    text += use + " ";
                }
                voice.speak(text);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void mechanics(Refactoring refactoring) {
        String mechanics = refactoring.getMechanics();
        if (voice != null) {
            voice.allocate();
        }
        try {
            prepareVoice();

            if (refactoring.getMechanics().isEmpty()) {
                voice.speak("There are no instructions to apply this refactoring");
            } else {
                voice.speak(mechanics);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void relations(Refactoring refactoring) {
        ArrayList<Relation> relations;
        String text = "";
        if (voice != null) {
            voice.allocate();
        }
        try {
            relations = dataManager.getRelationsOfRefactoring(refactoring);
            prepareVoice();

            if (relations.isEmpty()) {
                voice.speak("There are no other relative refactorings to this one");
            } else {
                text += "You can apply " + refactoring.getName() + " ";
                for (Relation relation : relations) {
                    text += relation.getName()+" "+relation.getTargetRefactoring().getName()+",";
                }
                voice.speak(text);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    public void howToSolveSmell(String smell) {
        String[] refactorings = DataManager.getDataManager().getRefactoringsOfSmell(smell);
        String text = "";
        if (voice != null) {
            voice.allocate();
        }
        try {
            prepareVoice();
            text += "To solve " + smell + "you can use ";
            for (String refact : refactorings) {
                text += refact;
                if (!refact.equals(refactorings[refactorings.length - 1])) {
                    text += " or ";
                }
            }
            text += "\n";
            voice.speak(text);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void prepareVoice() {
        voice.setRate(140);
        voice.setPitch(150);
        voice.setVolume(5);
    }
}
