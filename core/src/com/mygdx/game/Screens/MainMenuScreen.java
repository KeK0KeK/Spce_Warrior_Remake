package com.mygdx.game.Screens;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.MainGame;

public class MainMenuScreen implements Screen, InputProcessor{

    private final MainGame mainGame;
    private OrthographicCamera camera;
    private Texture menuBtn, menuBtnDown;
    private Texture exitBtn, exitBtnDown;
    private Texture bg;
    private final int menuBtnCount = 2;
    private final int menuBtnX = 120;
    private final int[] menuBtnY = new int[]{500,355};
    private final boolean[] pressBtn = new boolean[menuBtnCount];
    private boolean isExitDown;
    private Music gameMusic;
    private Music buttonSound;
    private int width, height;
    private float ppuX, ppuY;
    private float cameraWidth = 600F;
    private float cameraHeight = 800F;
    //
    public MainMenuScreen(MainGame main){
        this.mainGame = main;
        show();
    }

    //загрузка фоновой музыки и звуков
    private void loadMusic() {
        if (gameMusic != null){
            gameMusic.stop();
        }
        gameMusic = Gdx.audio.newMusic(Gdx.files.internal("MenuTheme.wav"));
        buttonSound = Gdx.audio.newMusic(Gdx.files.internal("button_sound.wav"));
        gameMusic.setLooping(true);
        gameMusic.play();
    }

    //загрузка текстур
    private void loadTextures(){
        bg = new Texture(Gdx.files.internal("sky.png"));
        menuBtn = new Texture("button1.png");
        menuBtnDown = new Texture("button2.png");
        exitBtn = new Texture("button3.png");
        exitBtnDown = new Texture("button4.png");
    }

    //прорисовка фона
    public void showBG() {
        mainGame.batch.draw(bg,0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
    }

    //прорисовка текста
    public void showText(){
        mainGame.font4.draw(mainGame.batch, "Space", 53, 730);
        mainGame.font4.draw(mainGame.batch, "Warrior", 286, 730);
        mainGame.font1.draw(mainGame.batch, "Space Warrior", 52, 730);
        mainGame.font2.draw(mainGame.batch, "Start", 220, 585);
        mainGame.font2.draw(mainGame.batch, "Shop", 230, 440);
        mainGame.font3.draw(mainGame.batch, "Exit", 240, 100);
    }

    //вывод кнопок поверх фона
    public void showButton() {
        for (int i =0; i<menuBtnCount; i++){
            if (!pressBtn[i]) {
                mainGame.batch.draw(menuBtn, menuBtnX, menuBtnY[i]);
            } else {
                mainGame.batch.draw(menuBtnDown, menuBtnX, menuBtnY[i]);
            }
        }
        if (!isExitDown) {
            mainGame.batch.draw(exitBtn, 215, 30);
        } else {
            mainGame.batch.draw(exitBtnDown, 215, 30);
        }
    }

    //устанавка позиции камеры на сцене
    public void setCamera(float x, float y){
        this.camera.position.set(x, y,0);
        this.camera.update();
    }

    //устанавка размера экрана в пикселях и рассчет количества пикселей для оси X и Y
    public void setSize (int w, int h) {
        this.width = w;
        this.height = h;
        ppuX = (float)width / cameraWidth;
        ppuY = (float)height / cameraHeight ;
    }

    //создание экрана
    @Override
    public void show() {
        ppuX = (float)width / cameraWidth;
        ppuY = (float)height / cameraHeight;
        for (int i = 0; i<menuBtnCount; i++){
            pressBtn[i] = false;
        }
        isExitDown = false;
        mainGame.batch = new SpriteBatch();
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 600, 800);
        loadTextures();
        loadMusic();
        Gdx.input.setInputProcessor(this);
    }

    //отрисовка текстур
    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0.2f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        setCamera(cameraWidth/2, cameraHeight / 2f);

        mainGame.batch.begin();
        mainGame.batch.setProjectionMatrix(camera.combined);
        showBG();
        showButton();
        showText();
        mainGame.batch.end();
    }

    @Override
    public void resize(int width, int height) {
        setSize(width, height);
        this.width = width;
        this.height = height;
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {
        Gdx.input.setInputProcessor(null);
    }

    //очистка экрана
    @Override
    public void dispose() {
        Gdx.input.setInputProcessor(null);
        menuBtnDown.dispose();
        menuBtn.dispose();
        exitBtn.dispose();
        exitBtnDown.dispose();
        bg.dispose();
    }

    @Override
    public boolean keyDown(int keycode) {
        return true;
    }

    @Override
    public boolean keyUp(int keycode) {
        return true;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    //границы кнопок и обработка нажатий (при нажатии меняется спрайт кнопки)
    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        for (int i = 0; i<menuBtnCount; i++){
            if((height-screenY)/ppuY >= menuBtnY[i] && (height-screenY)/ppuY <= menuBtnY[i]+100 && screenX/ppuX>=120 && screenX/ppuX<=495) {
                buttonSound.play();
                pressBtn[i] = true;
            }
        }
        if((height-screenY)/ppuY >= 30 && (height-screenY)/ppuY <= 110 && screenX/ppuX>=215 && screenX/ppuX<=385)
            isExitDown = true;

        return true;
    }

    //при нажатии происходит переход на другой экран
    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        if (!Gdx.app.getType().equals(Application.ApplicationType.Desktop))
            return false;

        if(pressBtn[0]){
            dispose();
            mainGame.setScreen(mainGame.gameScreen);
        }
        if(pressBtn[1]){
            dispose();
            mainGame.setScreen(mainGame.skinsScreen);
        }
        if(isExitDown){
            dispose();
            Gdx.app.exit();
        }

        return true;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(float amountX, float amountY) {
        return false;
    }
}