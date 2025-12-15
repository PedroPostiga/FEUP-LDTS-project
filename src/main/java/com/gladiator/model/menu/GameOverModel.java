package com.gladiator.model.menu;

public class GameOverModel {
    public enum Option { MENU, QUIT }
    
    private final boolean won;
    private Option selected = Option.MENU;
    private final Option[] options = Option.values();

    public GameOverModel(boolean won) {
        this.won = won;
    }

    public boolean isWon() {
        return won;
    }

    public Option getSelected() {
        return selected;
    }

    public void setSelected(Option selected) {
        this.selected = selected;
    }

    public void nextSelected() {
        int currentIndex = selected.ordinal();
        int nextIndex = (currentIndex + 1) % options.length;
        selected = options[nextIndex];
    }

    public void previousSelected() {
        int currentIndex = selected.ordinal();
        int previousIndex = (currentIndex - 1 + options.length) % options.length;
        selected = options[previousIndex];
    }
}

