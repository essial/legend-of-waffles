package com.lunaticedit.legendofwaffles;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.lunaticedit.legendofwaffles.consumers.RenderConsumer;
import com.lunaticedit.legendofwaffles.consumers.RepositoryConsumer;
import com.lunaticedit.legendofwaffles.factories.RepositoryFactory;
import com.lunaticedit.legendofwaffles.factories.SceneFactory;
import com.lunaticedit.legendofwaffles.helpers.Constants;

public class GameScreen implements Screen {
    private Rectangle viewport;

    public GameScreen() throws Exception {
        super();
        (new RepositoryConsumer(new RepositoryFactory(), new SceneFactory())).bootstrap();
    }

    @Override
    public void render(final float delta) {
        try {
            (new RenderConsumer(new RepositoryFactory())).render();
        } catch (Exception e) {
            e.printStackTrace(); // TODO: Proper exception handling
        }
    }

    @Override
    public void resize(final int width, final int height) {
        float aspectRatio = (float)width/(float)height;
        float scale;
        Vector2 crop = new Vector2(0f, 0f);
        if(aspectRatio > Constants.AspectRatio)
        {
            scale = (float)height/(float)Constants.GameHeight;
            crop.x = (width - Constants.GameWidth * scale)/2f;
        }
        else if(aspectRatio < Constants.AspectRatio)
        {
            scale = (float)width/(float)Constants.GameWidth;
            crop.y = (height - Constants.GameHeight*scale)/2f;
        }
        else
        {
            scale = (float)width/(float)Constants.GameWidth;
        }

        float w = (float)Constants.GameWidth * scale;
        float h = (float)Constants.GameHeight * scale;
        viewport = new Rectangle(crop.x, crop.y, w, h);
    }

    @Override
    public void show() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void dispose() {

    }
}
