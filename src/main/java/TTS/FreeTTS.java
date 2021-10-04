package TTS;

import ComponentsPackage.Refactoring;

public class FreeTTS  {
    private TTSVoice voice;
    private Refactoring refactoring;
    private String smell;

    public FreeTTS(Refactoring refactoring, String smell,TTSVoice voice) {
        this.refactoring = refactoring;
        this.smell = smell;
        this.voice = voice;
    }

    public void description(){
        voice.description(refactoring);
    }

    public void uses() { voice.uses(refactoring);
    }
    public void mechanics() {
        voice.mechanics(refactoring);
    }

    public void relations() {
        voice.relations(refactoring);
    }

    public void howToSolveSmell() {
        voice.howToSolveSmell(smell);
    }




}
