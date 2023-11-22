package lab3;

import java.awt.*;

public class Rule {
    Character nonterminal;
    String terminal;

    public Rule(Character nonterminal, String terminal) {
        this.nonterminal = nonterminal;
        this.terminal = terminal;
    }

    public int getLength(){
        if(nonterminal == null){
            return terminal.length();
        }
        return terminal.length() + 1;
    }

    public Character getNonterminal() {
        return nonterminal;
    }

    public String getTerminal() {
        return terminal;
    }

    public void setNonterminal(Character nonterminal) {
        this.nonterminal = nonterminal;
    }

    public void setTerminal(String terminal) {
        this.terminal = terminal;
    }
}
