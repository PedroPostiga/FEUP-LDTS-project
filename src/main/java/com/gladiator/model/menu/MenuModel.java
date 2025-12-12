package com.gladiator.model.menu;

public class MenuModel {
    public enum Option { PLAY, CREDITS, EXIT }

    private Option selected = Option.PLAY;
    private final Option[] options = Option.values();

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