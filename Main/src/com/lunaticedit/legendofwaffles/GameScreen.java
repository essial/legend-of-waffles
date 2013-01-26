package com.lunaticedit.legendofwaffles;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.FrameBuffer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.lunaticedit.legendofwaffles.factories.RepositoryFactory;
import com.lunaticedit.legendofwaffles.factories.SceneFactory;
import com.lunaticedit.legendofwaffles.factories.SpriteBatchFactory;
import com.lunaticedit.legendofwaffles.helpers.Constants;
import com.lunaticedit.legendofwaffles.implementations.Input;
import com.lunaticedit.legendofwaffles.services.ProcessableServices;
import com.lunaticedit.legendofwaffles.services.RenderServices;
import com.lunaticedit.legendofwaffles.services.RepositoryServices;

public class GameScreen implements Screen, InputProcessor {
    private Rectangle _viewport;
    private final Camera _camera;
    private final FrameBuffer _fbo;

    public GameScreen() throws UnsupportedOperationException {
        (new RepositoryServices(new RepositoryFactory(), new SceneFactory())).bootstrap();
        Gdx.input.setInputProcessor(this);
        Gdx.input.setCatchBackKey(true);
        Gdx.input.setCatchMenuKey(true);
        _camera = new OrthographicCamera(Constants.GameWidth, Constants.GameHeight);
        _fbo = new FrameBuffer(Pixmap.Format.RGB565, Constants.GameWidth, Constants.GameHeight, false);
        Gdx.gl.glTexParameterf(GL20.GL_TEXTURE_2D, GL20.GL_TEXTURE_MAG_FILTER, GL20.GL_NEAREST);
        Gdx.gl.glTexParameterf(GL20.GL_TEXTURE_2D, GL20.GL_TEXTURE_MIN_FILTER, GL20.GL_NEAREST);
        Gdx.gl.glTexParameterf(GL20.GL_TEXTURE_2D, GL20.GL_TEXTURE_WRAP_S, GL20.GL_CLAMP_TO_EDGE);
        Gdx.gl.glTexParameterf(GL20.GL_TEXTURE_2D, GL20.GL_TEXTURE_WRAP_T, GL20.GL_CLAMP_TO_EDGE);
    }
    @Override
    public void render(final float delta) {
        if (_viewport == null) { return; }
        _camera.update();
        final SpriteBatch batch = (new SpriteBatchFactory()).generate();
        Gdx.gl.glViewport((int) _viewport.x, (int) _viewport.y, (int) _viewport.width, (int) _viewport.height);
        batch.setProjectionMatrix(_camera.combined);
        _fbo.begin();
        Gdx.gl.glClearColor(0.3f, 0.5f, 0.9f, 1.0f);
        Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
        batch.begin();
        try { (new RenderServices(new RepositoryFactory())).render(); }
        catch (Exception e) { Gdx.app.log("Error", e.getMessage(), e); }
        batch.end();
        _fbo.end();
        batch.begin();
        Gdx.gl.glViewport((int) _viewport.x, (int) _viewport.y, (int) _viewport.width, (int) _viewport.height);
        batch.draw(_fbo.getColorBufferTexture(), -Constants.GameWidth / 2, -Constants.GameHeight / 2,
                Constants.GameWidth, Constants.GameHeight, 0, 0, Constants.GameWidth, Constants.GameHeight, false, true);
        batch.end();

        try { (new ProcessableServices(new RepositoryFactory())).process(); }
        catch (Exception e) { Gdx.app.log("Error", e.getMessage(), e); }
    }
    /*

        public void render(final float delta) {
        if (_viewport == null) { return; }
        _camera.update();
        Gdx.gl.glViewport((int) _viewport.x, (int) _viewport.y, (int) _viewport.width, (int) _viewport.height);
        (new SpriteBatchFactory()).generate().setProjectionMatrix(_camera.combined);
        Gdx.gl.glClearColor(0.3f, 0.5f, 0.9f, 1.0f);
        Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
        (new SpriteBatchFactory()).generate().begin();
        try { (new RenderServices(new RepositoryFactory())).render(); }
        catch (Exception e) { Gdx.app.log("Error", e.getMessage(), e); }
        (new SpriteBatchFactory()).generate().end();
        try { (new ProcessableServices(new RepositoryFactory())).process(); }
        catch (Exception e) { Gdx.app.log("Error", e.getMessage(), e); }
    }

     */


    @Override
    public void resize(final int width, final int height) {
        float aspectRatio = (float)width/(float)height;
        float scale;
        Vector2 crop = new Vector2(0f, 0f);
        if(aspectRatio > Constants.AspectRatio)
        { scale = (float)height/(float)Constants.GameHeight; crop.x = (width - Constants.GameWidth * scale)/2f; }
        else if(aspectRatio < Constants.AspectRatio)
        { scale = (float)width/(float)Constants.GameWidth;   crop.y = (height - Constants.GameHeight*scale)/2f; }
        else { scale = (float)width/(float)Constants.GameWidth; }
        float w = (float)Constants.GameWidth * scale;
        float h = -(float)Constants.GameHeight * scale;
        _viewport = new Rectangle(crop.x, crop.y, w, h);
    }
    @Override
    public void show() {}
    @Override
    public void hide() {}
    @Override
    public void pause() {}
    @Override
    public void resume() {}
    @Override
    public void dispose() {}
    @Override
    public boolean keyDown(final int keycode)
    { Input.getInstance().setKeyState(keycode, true); return true; }
    @Override
    public boolean keyUp(final int keycode)
    { Input.getInstance().setKeyState(keycode, false); return false; }
    @Override
    public boolean keyTyped(final char character)
    { return false; }
    @Override
    public boolean touchDown(final int screenX, final int screenY, final int pointer, final int button)
    { return false; }
    @Override
    public boolean touchUp(final int screenX, final int screenY, final int pointer, final int button)
    { return false; }
    @Override
    public boolean touchDragged(final int screenX, final int screenY, final int pointer)
    { return false; }
    @Override
    public boolean mouseMoved(final int screenX, final int screenY)
    { return false; }
    @Override
    public boolean scrolled(final int amount)
    { return false; }
}
