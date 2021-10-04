package TTS;

import ComponentsPackage.Refactoring;

public interface TTSVoice {
    void description(Refactoring refactoring);
    void uses(Refactoring refactoring);
    void mechanics(Refactoring refactoring);
    void relations(Refactoring refactoring);
    void howToSolveSmell(String smell);
}
