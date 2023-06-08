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

import java.io.*;
import java.util.Scanner;

public class SkinsScreen implements Screen, InputProcessor {
    private Texture playerSkin;
    private final MainGame mainGame;
    private OrthographicCamera camera;
    private final int skinsCount = 16;
    private final Texture[] skinsTexture = new Texture[skinsCount];
    private final int[] skinsX = new int[]{70, 210, 350, 490, 70, 210, 340, 490, 70, 210, 350, 490, 70, 210, 350, 490};
    private final int[] skinsY = new int[]{600, 590, 595, 610, 440, 440, 450, 440, 270, 280, 275, 290, 110, 130, 130, 120};
    private Texture selectSkin, buySkin, dontBuySkin;
    private Texture exitMenuBtn, exitMenuBtnDown;
    private Texture bg;
    private Music buttonSound;
    private int width, height;
    private float ppuX, ppuY;
    private final int btnCount = 16;
    private final int[] btnX = new int[]{20, 160, 300, 440, 20, 160, 300, 440, 20, 160, 300, 440, 20, 160, 300, 440};
    private final int[] btnY = new int[]{570, 570, 570, 570, 410, 410, 410, 410, 250, 250, 250, 250, 90, 90, 90, 90};
    private final boolean[] buyBtn = new boolean[btnCount];
    private final boolean[] selectBtn = new boolean[btnCount];
    private int selectedSkin;
    private boolean isExitMenuDown;
    private boolean isBtnSelected = false;
    private float cameraWidth = 600F;
    private float cameraHeight = 800F;
    private int shopCoin;
    private String coinPrint,  line;

    public SkinsScreen(MainGame mainGame) {
        this.mainGame = mainGame;
        readFileShop();
        show();
    }
    //геттеры и сеттеры


    public Texture getPlayerSkin() {
        return playerSkin;
    }

    public void setPlayerSkin(Texture playerSkin) {
        this.playerSkin = playerSkin;
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

    public int getSkinsCount() {
        return skinsCount;
    }

    public Texture[] getSkinsTexture() {
        return skinsTexture;
    }

    public int[] getSkinsX() {
        return skinsX;
    }

    public int[] getSkinsY() {
        return skinsY;
    }

    public Texture getSelectSkin() {
        return selectSkin;
    }

    public void setSelectSkin(Texture selectSkin) {
        this.selectSkin = selectSkin;
    }

    public Texture getBuySkin() {
        return buySkin;
    }

    public void setBuySkin(Texture buySkin) {
        this.buySkin = buySkin;
    }

    public Texture getDontBuySkin() {
        return dontBuySkin;
    }

    public void setDontBuySkin(Texture dontBuySkin) {
        this.dontBuySkin = dontBuySkin;
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

    public Texture getBg() {
        return bg;
    }

    public void setBg(Texture bg) {
        this.bg = bg;
    }

    public Music getButtonSound() {
        return buttonSound;
    }

    public void setButtonSound(Music buttonSound) {
        this.buttonSound = buttonSound;
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

    public int getBtnCount() {
        return btnCount;
    }

    public int[] getBtnX() {
        return btnX;
    }

    public int[] getBtnY() {
        return btnY;
    }

    public boolean[] getBuyBtn() {
        return buyBtn;
    }

    public boolean[] getSelectBtn() {
        return selectBtn;
    }

    public int getSelectedSkin() {
        return selectedSkin;
    }

    public void setSelectedSkin(int selectedSkin) {
        this.selectedSkin = selectedSkin;
    }

    public boolean isExitMenuDown() {
        return isExitMenuDown;
    }

    public void setExitMenuDown(boolean exitMenuDown) {
        isExitMenuDown = exitMenuDown;
    }

    public boolean isBtnSelected() {
        return isBtnSelected;
    }

    public void setBtnSelected(boolean btnSelected) {
        isBtnSelected = btnSelected;
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

    public int getShopCoin() {
        return shopCoin;
    }

    public void setShopCoin(int coin) {
        this.shopCoin += coin;
    }

    public String getCoinPrint() {
        return coinPrint;
    }

    public void setCoinPrint(String coinPrint) {
        this.coinPrint = coinPrint;
    }

    public String getLine() {
        return line;
    }

    public void setLine(String line) {
        this.line = line;
    }

    //загрузка звуков кнопок
    private void loadMusic() {
        buttonSound = Gdx.audio.newMusic(Gdx.files.internal("button_sound.wav"));
    }

    //загрузка текстур
    private void loadTextures() {
        bg = new Texture(Gdx.files.internal("sky.png"));
        selectSkin = new Texture("frame.png");
        dontBuySkin = new Texture("frame2.png");
        buySkin = new Texture("frame3.png");
        skinsTexture[0] = new Texture("alien1.png");
        skinsTexture[1] = new Texture("bluecargoship.png");
        skinsTexture[2] = new Texture("blueship2.png");
        skinsTexture[3] = new Texture("destroyer.png");
        skinsTexture[4] = new Texture("F5S4.png");
        skinsTexture[5] = new Texture("greenship2.png");
        skinsTexture[6] = new Texture("RD2.png");
        skinsTexture[7] = new Texture("orangeship2.png");
        skinsTexture[8] = new Texture("shuttlenoweps.png");
        skinsTexture[9] = new Texture("wship1.png");
        skinsTexture[10] = new Texture("alien2.png");
        skinsTexture[11] = new Texture("blue1.png");
        skinsTexture[12] = new Texture("blueshuttlenoweps.png");
        skinsTexture[13] = new Texture("F5S2.png");
        skinsTexture[14] = new Texture("orangeship3.png");
        skinsTexture[15] = new Texture("redship4.png");
        exitMenuBtn = new Texture("button5.png");
        exitMenuBtnDown = new Texture("button6.png");
    }

    //прорисовка фона
    public void showBG() {
        mainGame.batch.draw(bg, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
    }

    //прорисовка текста
    public void showText() {
        mainGame.font4.draw(mainGame.batch, "Shop", 222, 790);
        mainGame.font1.draw(mainGame.batch, "Shop", 220, 790);
        mainGame.font3.draw(mainGame.batch, "<-", 20, 80);
    }

    //прорисовка количества монет
    public void shopCoin() {
        coinPrint = String.valueOf(shopCoin);
        mainGame.font3.draw(mainGame.batch, "coin:", 300, 60);
        mainGame.font3.draw(mainGame.batch, coinPrint, 500, 60);
        mainGame.font5.draw(mainGame.batch, "all for 10 coins", 200, 90);

    }

    //прорисовка скинов для покупки в магазине
    public void drawSkins() {
        for (int i = 0; i < skinsCount; i++) {
            mainGame.batch.draw(skinsTexture[i], skinsX[i], skinsY[i]);
        }
    }

    //прорисовка кнопки выхода
    public void exitButton() {
        if (!isExitMenuDown) {
            mainGame.batch.draw(exitMenuBtn, 0, 10);
        } else {
            mainGame.batch.draw(exitMenuBtnDown, 0, 10);
        }
    }

    //прорисовка рамок показывающих куплен ли скин
    public void buyingSkin() {
        buyBtn[0] = true;
        for (int i = 0; i < btnCount; i++) {
            if (!buyBtn[i]) {
                mainGame.batch.draw(dontBuySkin, btnX[i], btnY[i]);
            } else {
                mainGame.batch.draw(buySkin, btnX[i], btnY[i]);
            }
        }
    }

    //прорисовка рамок показывающих выбран ли скин
    public void selectSkin() {
        for (int i = 0; i < btnCount; i++) {
            if (selectBtn[i]) {
                mainGame.batch.draw(selectSkin, btnX[i], btnY[i]);
            }
        }
    }

    //загрузка данных из файла
    public void readFileShop() {
        try {
            File file = new File("assets/shop.txt");
            Scanner scanner = new Scanner(file);
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                if (line.startsWith("Shop coin: ")) {
                    shopCoin = Integer.parseInt(line.substring(11)); // удаляем "Shop coin: " и преобразуем строку в число
                } else if (line.startsWith("Selected skin: ")) {
                    selectedSkin = Integer.parseInt(line.substring(15)); // удаляем "Selected skin: " и преобразуем строку в число
                } else if (line.startsWith("Bought skins: ")) {
                    String[] boughtSkins = line.substring(14).split(" "); // удаляем "Bought skins: " и разбиваем строку на массив строк по пробелам
                    for (String str : boughtSkins) {
                        if (!str.isEmpty()) { // проверяем, что строка не пустая
                            int value = Integer.parseInt(str.trim()); // удаляем лишние пробелы и преобразуем строку в число
                            buyBtn[value] = true;
                        }
                    }
                }
            }
            scanner.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    //загрузка в файл
    public void writeFileShop() {
        try {
            buyBtn[0] = true;
            FileWriter writer = new FileWriter("assets/shop.txt");
            writer.write("Shop coin: " + shopCoin + "\n");
            writer.write("Selected skin: " + selectedSkin + "\n");
            writer.write("Bought skins: ");
            for (int i = 0; i < buyBtn.length; i++) {
                if ((buyBtn[i])) {
                    writer.write(" " + i);
                }
            }
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
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

    //создание экрана
    @Override
    public void show() {
        ppuX = (float) width / cameraWidth;
        ppuY = (float) height / cameraHeight;
        isExitMenuDown = false;
        mainGame.batch = new SpriteBatch();
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 600, 800);
        loadTextures();
        loadMusic();
        Gdx.input.setInputProcessor(this);
    }
    //рендер текстур
    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0.2f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        SetCamera(cameraWidth / 2, cameraHeight / 2f);
        mainGame.batch.begin();
        mainGame.batch.setProjectionMatrix(camera.combined);
        showBG();
        shopCoin();
        drawSkins();
        exitButton();
        buyingSkin();
        selectSkin();
        showText();
        writeFileShop();
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
        exitMenuBtn.dispose();
        exitMenuBtnDown.dispose();
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

    //границы кнопок и обработка нажатий
    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        //обработка нажатий в пределах рамок
        for (int i = 0; i < btnCount; i++) {
            if ((height - screenY) / ppuY >= btnY[i] && (height - screenY) / ppuY <= btnY[i] + 150 && screenX / ppuX >= btnX[i] && screenX / ppuX <= btnX[i] + 140) {
                if ((shopCoin >= 10) && (!buyBtn[i])) {//покупка
                    buttonSound.play();
                    shopCoin = shopCoin - 10;
                    buyBtn[i] = true;
                } else if ((buyBtn[i]) && (!selectBtn[i])) {//выбор
                    buttonSound.play();
                    if (selectedSkin != -1) {//очистка выбора
                        selectBtn[selectedSkin] = false;
                    }
                    selectedSkin = i;
                    selectBtn[i] = true;
                }
            }
        }
        //обработка нажатий кнопки выхода в меню
        if ((height - screenY) / ppuY >= 10 && (height - screenY) / ppuY <= 93 && screenX / ppuX >= 0 && screenX / ppuX <= 100) {
            buttonSound.play();
            isExitMenuDown = true;
        }
        return true;
    }

    //при нажатии происходит действие
    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        if (!Gdx.app.getType().equals(Application.ApplicationType.Desktop))
            return false;
        //передача выбранной текстуры в переменную
        for (int i = 0; i < btnCount; i++) {
            if (selectBtn[i]) {
                playerSkin = skinsTexture[i];
                isBtnSelected = true;
                break;
            } else {
                playerSkin = skinsTexture[0];
            }
        }
        if (isBtnSelected) {
            //установка текстуры в игровом экране
            mainGame.gameScreen.setPlayerTexture(playerSkin);
        }
        //при нажатии происходит переход в меню
        if (isExitMenuDown) {
            dispose();
            mainGame.setScreen(mainGame.mainMenuScreen);
        }

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