package com.lunaticedit.legendofwaffles.implementations.ui;

import java.util.LinkedList;

public class UI {
    private final LinkedList<View> _views;
    private final LinkedList<View> _removalQueue;
    private int _modalResult;

    public UI() {
        _views = new LinkedList<View>();
        _removalQueue = new LinkedList<View>();
        _modalResult = -1;
    }
    public void reset() {
        _views.clear();
        _removalQueue.clear();
        _modalResult = -1;
    }
    public boolean isModal() {
        for(View view : _views) {
            if (view instanceof InputView) { return true; }
        }
        return false;
    }
    public void createInputView(final String[] choices, final int x, final int y) {
        _views.add(new InputView(choices, x, y));
        _modalResult = -1;
    }
    public void render() {
        for (View view : _views) { view.Draw(); }
    }
    public void process() {
        for (View view : _views)        { view.Process(); }
        for (View view : _removalQueue) { _views.remove(view); }
        _removalQueue.clear();
    }
    public void setModalResult(int val) {
        _modalResult = val;
    }
    public int getModalResult() {
        return _modalResult;
    }
    public void removeView(View view) {
        _removalQueue.add(view);
    }
}
