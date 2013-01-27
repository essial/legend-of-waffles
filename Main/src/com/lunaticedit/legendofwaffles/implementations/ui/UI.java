package com.lunaticedit.legendofwaffles.implementations.ui;

import java.util.LinkedList;

public class UI {
    private final LinkedList<View> _views;

    public UI() {
        _views = new LinkedList<View>();
    }
    public void reset() {
        _views.clear();
    }
    public boolean isModal() {
        for(View view : _views) {
            if (view instanceof InputView) { return true; }
        }
        return false;
    }
    public void createInputView(final String[] choices, final int x, final int y) {
        _views.add(new InputView(choices, x, y));
    }
    public void render() {

    }
    public void process() {

    }
}
