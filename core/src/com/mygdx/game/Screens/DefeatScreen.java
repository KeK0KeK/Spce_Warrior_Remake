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

import java.util.Scanner;

public class DefeatScreen implements Screen, InputProcessor {
    private Texture bg, exitMenuBtn, exitMenuBtnDown, saveBtn, saveBtnDown;
    private Music buttonSound;
    private boolean isExitMenuDown, isSaveDown;
    private final MainGame mainGame;
    private OrthographicCamera camera;
    private float cameraWidth = 600F;
    private float cameraHeight = 800F;
    private int width, height;
    private float ppuX, ppuY;
    private int score, coin;
    String scorePrint, coinPrint;

    public DefeatScreen(MainGame mainGame) {
        this.mainGame = mainGame;
    }

    //геттеры и сеттеры
    public Texture getBg() {
        return bg;
    }

    public void setBg(Texture bg) {
        this.bg = bg;
    }

    public Texture getExitMenuBtn() {
        return exitMenuBtn;
    }

    public void setExitMenuBtn(Texture exitMenuBtn) {
        this.exitMenuBtn = exitMenuBtn;
    }

    public Texture getExitMenuBtnDown() {
        return exitMenuBtnDown;
    }

    public void setExitMenuBtnDown(Texture exitMenuBtnDown) {
        this.exitMenuBtnDown = exitMenuBtnDown;
    }

    public Music getButtonSound() {
        return buttonSound;
    }

    public void setButtonSound(Music buttonSound) {
        this.buttonSound = buttonSound;
    }

    public boolean isExitMenuDown() {
        return isExitMenuDown;
    }

    public void setExitMenuDown(boolean exitMenuDown) {
        isExitMenuDown = exitMenuDown;
    }

    public Texture getSaveBtn() {
        return saveBtn;
    }

    public void setSaveBtn(Texture saveBtn) {
        this.saveBtn = saveBtn;
    }

    public Texture getSaveBtnDown() {
        return saveBtnDown;
    }

    public void setSaveBtnDown(Texture saveBtnDown) {
        this.saveBtnDown = saveBtnDown;
    }

    public boolean isSaveDown() {
        return isSaveDown;
    }

    public void setSaveDown(boolean saveDown) {
        isSaveDown = saveDown;
    }

    public MainGame getMainGame() {
        return mainGame;
    }

    public OrthographicCamera getCamera() {
        return camera;
    }

    public void setCamera(OrthographicCamera camera) {
        this.camera = camera;
    }

    public float getCameraWidth() {
        return cameraWidth;
    }

    public void setCameraWidth(float cameraWidth) {
        this.cameraWidth = cameraWidth;
    }

    public float getCameraHeight() {
        return cameraHeight;
    }

    public void setCameraHeight(float cameraHeight) {
        this.cameraHeight = cameraHeight;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public float getPpuX() {
        return ppuX;
    }

    public void setPpuX(float ppuX) {
        this.ppuX = ppuX;
    }

    public float getPpuY() {
        return ppuY;
    }

    public void setPpuY(float ppuY) {
        this.ppuY = ppuY;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public String getScorePrint() {
        return scorePrint;
    }

    public void setScorePrint(String scorePrint) {
        this.scorePrint = scorePrint;
    }

    public int getCoin() {
        return coin;
    }

    public void setCoin(int coin) {
        this.coin = coin;
    }

    public String getCoinPrint() {
        return coinPrint;
    }

    public void setCoinPrint(String coinPrint) {
        this.coinPrint = coinPrint;
    }

    //устанавка позиции камеры на сцене
    public void SetCamera(float x, float y){
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

    //загрузка текстур
    private void loadTextures(){
        bg = new Texture(Gdx.files.internal("sky.png"));
        exitMenuBtn = new Texture("button5.png");
        exitMenuBtnDown = new Texture("button6.png");
        saveBtn = new Texture("button3.png");
        saveBtnDown = new Texture("button4.png");
    }

    //отрисовка счета
    public void drawScore(){
        score = getScore();
        scorePrint = String.valueOf(score);
        mainGame.font4.draw(mainGame.batch, "you score:", 20, 650);
        mainGame.font4.draw(mainGame.batch, scorePrint, 320, 550);
    }

    //отрисовка текста
    public void showText(){
        mainGame.font7.draw(mainGame.batch, "You", 110, 790);
        mainGame.font7.draw(mainGame.batch, "lose", 310, 790);
        mainGame.font3.draw(mainGame.batch, "<-", 20, 80);
        mainGame.font3.draw(mainGame.batch, "Save", 440, 80);
    }
    //загрузка музыки
    private void loadMusic() {
        buttonSound = Gdx.audio.newMusic(Gdx.files.internal("button_sound.wav"));
    }
    //отрисовка фона
    public void showBG() {
        mainGame.batch.draw(bg,0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
    }

    //отрисовка нужной текстуры кнопки
    public void showButton() {
        if (!isExitMenuDown) {
            mainGame.batch.draw(exitMenuBtn, 0, 10);
        } else {
            mainGame.batch.draw(exitMenuBtnDown, 0, 10);
        }
        if (!isSaveDown) {
            mainGame.batch.draw(saveBtn, 420, 10);
        } else {
            mainGame.batch.draw(saveBtnDown, 420, 10);
        }
    }

    public void drawCoinOutGame(){
        coin = mainGame.skinsScreen.getShopCoin();
        coinPrint = String.valueOf(coin);
        mainGame.font4.draw(mainGame.batch, "All coin:", 20, 400);
        mainGame.font4.draw(mainGame.batch, coinPrint, 320, 300);
    }

    //создание экрана
    @Override
    public void show() {
        mainGame.batch = new SpriteBatch();
        Gdx.input.setInputProcessor(this);
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 600, 800);
        loadTextures();
        loadMusic();
    }

    //отрисовка текстур
    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0.2f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        SetCamera(cameraWidth / 2, cameraHeight / 2f);
        camera.update();
        mainGame.batch.setProjectionMatrix(camera.combined);
        mainGame.batch.begin();
        showBG();
        showButton();
        showText();
        drawScore();
        drawCoinOutGame();
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
    }

    @Override
    public void dispose() {
        mainGame.batch.dispose();
        bg.dispose();
        exitMenuBtnDown.dispose();
        exitMenuBtn.dispose();
        saveBtn.dispose();
        saveBtnDown.dispose();
    }

    @Override
    public boolean keyDown(int keycode) {
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        if((height-screenY)/ppuY >= 10 && (height-screenY)/ppuY <= 90 && screenX/ppuX>=0 && screenX/ppuX<=120) {
            buttonSound.play();
            isExitMenuDown = true;
        }
        if((height-screenY)/ppuY >= 10 && (height-screenY)/ppuY <= 90 && screenX/ppuX>=420 && screenX/ppuX<=590) {
            buttonSound.play();
            isSaveDown = true;
        }
        return true;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        if (!Gdx.app.getType().equals(Application.ApplicationType.Desktop))
            return false;

        if(isSaveDown){
            mainGame.skinsScreen.getBuyBtn();
            mainGame.skinsScreen.writeFileShop();
        }

        if(isExitMenuDown){
            dispose();
            mainGame.setScreen(mainGame.mainMenuScreen);
        }
        isSaveDown = false;
        isExitMenuDown = false;
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
