package com.lunaticedit.legendofwaffles.implementations.ui;

import com.lunaticedit.legendofwaffles.implementations.graphicsgenerator.TilesetGraphicsGenerator;

public class InputView implements View {
    private final String[] _choices;
    private final int _x;
    private final int _y;
    private final int _width;
    private final int _height;

    public InputView(final String[] choices, final int x, final int y) {
        _choices = choices;
        _x = x;
        _y = y;
        int w = 0;
        _height = choices.length;
        for(String choice : choices) {
            if (choice.length() > w) { w = choice.length(); }
        }
        _width = w;
    }

    @Override
    public void Draw() {
        final TilesetGraphicsGenerator g = (new TilesetGraphicsGenerator());

    }
}
