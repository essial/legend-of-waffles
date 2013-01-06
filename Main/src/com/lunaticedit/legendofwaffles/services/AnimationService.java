package com.lunaticedit.legendofwaffles.services;

import com.lunaticedit.legendofwaffles.contracts.Animation;

public class AnimationService {
    private Animation _animation;
    public AnimationService(final Animation animation) {
        _animation = animation;
    }

    public void update() {
        if (!_animation.getShouldAnimate()) {
            _animation.setCurrentFrame(1);
            return;
        }

        if ((System.currentTimeMillis() - _animation.getAnimationTime()) < _animation.getAnimationSpeed())
        { return; }

        _animation.setAnimationTime(System.currentTimeMillis());

        final int newFrame = _animation.getCurrentFrame() + 1;
        _animation.setCurrentFrame((newFrame > 3) ? 0 : newFrame);
    }
}
