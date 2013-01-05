package com.lunaticedit.legendofwaffles;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.lunaticedit.legendofwaffles.consumers.ProcessableConsumer;
import com.lunaticedit.legendofwaffles.consumers.RenderConsumer;
import com.lunaticedit.legendofwaffles.consumers.RepositoryConsumer;
import com.lunaticedit.legendofwaffles.factories.RepositoryFactory;
import com.lunaticedit.legendofwaffles.factories.SceneFactory;
import com.lunaticedit.legendofwaffles.factories.SpriteBatchFactory;
import com.lunaticedit.legendofwaffles.helpers.Constants;
import com.lunaticedit.legendofwaffles.implementations.Input;

public class GameScreen implements Screen, InputProcessor {
    private Rectangle _viewport;
    private final Camera _camera;

    public GameScreen() throws Exception {
        super();

        // Bootstrap the repository and scene factory
        (new RepositoryConsumer(new RepositoryFactory(), new SceneFactory())).bootstrap();

        // Set up libGdx specific stuff...
        Gdx.input.setInputProcessor(this);
        Gdx.input.setCatchBackKey(true);
        Gdx.input.setCatchMenuKey(true);

        // Set up our camera
        _camera = new OrthographicCamera(Constants.GameWidth, Constants.GameHeight);
    }

    @Override
    public void render(final float delta) {

        // We must have at least one resize event before we start rendering so that
        // we can correctly calculate aspect ratio and such..
        if (_viewport == null)
        { return; }

        // Recalculate the view and projection matrices in-case the camera has been
        // modified since the last call.
        _camera.update();

        // Configure the viewport so that we can render to a know resolution and
        // let the hardware figure out the stretching/scaling stuff.
        Gdx.gl.glViewport(
                (int) _viewport.x,
                (int) _viewport.y,
                (int) _viewport.width,
                (int) _viewport.height
        );

        // Set the projection matrix of the sprite-batch factory to the one
        // we have calculated in the camera object.
        (new SpriteBatchFactory())
                .generate()
                .setProjectionMatrix(_camera.combined);


        // Clear the screen to a NES-ish blue..
        // TODO: This needs to be changeable in the engine...
        Gdx.gl.glClearColor(0.3f, 0.5f, 0.9f, 1.0f);
        Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);

        // Begin generation of the sprite batch
        (new SpriteBatchFactory())
                .generate()
                .begin();

        // Render the current screen (into the sprite batch)
        try { (new RenderConsumer(new RepositoryFactory())).render(); }
        catch (Exception e) { e.printStackTrace(); } // TODO: Proper exception handling

        // Close the sprite batch now that we have finished rendering.
        (new SpriteBatchFactory())
                .generate()
                .end();

        // Process updates
        try { (new ProcessableConsumer(new RepositoryFactory())).process(); }
        catch (Exception e) { e.printStackTrace(); } // TODO: Proper exception handling

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
        _viewport = new Rectangle(crop.x, crop.y, w, h);
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

    @Override
    public boolean keyDown(final int keycode) {
        Input.getInstance().setKeyState(keycode, true);
        return true;
    }

    @Override
    public boolean keyUp(final int keycode) {
        Input.getInstance().setKeyState(keycode, false);
        return false;
    }

    @Override
    public boolean keyTyped(final char character) {
        return false;
    }

    @Override
    public boolean touchDown(final int screenX, final int screenY, final int pointer, final int button) {
        return false;
    }

    @Override
    public boolean touchUp(final int screenX, final int screenY, final int pointer, final int button) {
        return false;
    }

    @Override
    public boolean touchDragged(final int screenX, final int screenY, final int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(final int screenX, final int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(final int amount) {
        return false;
    }
}
