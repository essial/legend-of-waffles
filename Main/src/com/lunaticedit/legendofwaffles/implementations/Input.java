package com.lunaticedit.legendofwaffles.implementations;

import java.util.HashMap;

public class Input {
    private static Input _input;
    public static Input getInstance() {
        if (_input == null) {
            _input = new Input();
        }
        return _input;
    }

    private final HashMap<Integer, Boolean> _keyState;
    private Input() {
        _keyState = new HashMap<Integer, Boolean>();
    }

    public boolean getKeyState(int key) {
        if (!_keyState.containsKey(key))
        { return false; }
        return _keyState.get(key);
    }

    public void setKeyState(int key, boolean state) {
        _keyState.put(key, state);
    }
}
