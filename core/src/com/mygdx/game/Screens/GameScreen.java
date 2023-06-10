package com.mygdx.game.Screens;

import com.badlogic.gdx.*;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;
import com.mygdx.game.MainGame;

import java.util.Iterator;
import java.util.Timer;

public class GameScreen implements Screen, InputProcessor {
    private Texture playerTexture, bg, gamePlayerSkin, exitMenuBtn, exitMenuBtnDown,
            pauseTexture, playTexture, livesTexture, healthup, mobImage, bulletImage, shieldUp, activeShield;
    private Music buttonSound;
    private Sound killMobSound, explosionSound, laserSound, powerup, shieldOn, shieldOff;
    private boolean isExitMenuDown, isPaused;
    private final MainGame mainGame;
    private ShapeRenderer shapeRenderer;
    private Rectangle player;
    private Rectangle shipShield;
    private OrthographicCamera camera;
    private float cameraWidth = 600F;
    private float cameraHeight = 800F;
    private int width, height;
    private float ppuX, ppuY;
    private int score = 0;
    private int coin = 0;
    private int lives = 3;
    int shieldLives = 3;
    private boolean activate = false;
    private int playerSpeed = 400;
    String scorePrint, coinPrint;
    private Array<Rectangle> mobArmy;
    private Array<Rectangle> laserBullets;
    private Array<Rectangle> healthupPacks;
    private Array<Rectangle> shields;
    private long lastDropTime;
    private long lastBulletDropTime;
    private long bulletInterval = 500000000;

    public GameScreen(MainGame mainGame) {
        this.mainGame = mainGame;
    }

    //сеттер для получения текстуры игрока
    public void setPlayerTexture(Texture playerSkin) {
        this.playerTexture  = playerSkin;
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

    //загрузка текстур
    private void loadTextures(){
        bg = new Texture(Gdx.files.internal("sky.png"));
        exitMenuBtn = new Texture("button3.png");
        exitMenuBtnDown = new Texture("button4.png");
        livesTexture = new Texture("heart.png");
        mobImage = new Texture(Gdx.files.internal("mob1.png"));
        healthup = new Texture("healthup.png");
        shieldUp = new Texture("shield.png");
        activeShield = new Texture("ship shield.png");
        pauseTexture = new Texture(Gdx.files.internal("pause.png"));
        playTexture = new Texture(Gdx.files.internal("play.png"));
        if (bulletImage == null){
            bulletImage = new Texture(Gdx.files.internal("bullet.png"));
        }
        if (playerTexture  == null){
            playerTexture  = new Texture(Gdx.files.internal("alien1.png"));
        }
        gamePlayerSkin = playerTexture;
    }

    private void loadSound(){
        explosionSound = Gdx.audio.newSound(Gdx.files.internal("explosion.ogg"));
        killMobSound = Gdx.audio.newSound(Gdx.files.internal("kill mob 1.wav"));
        laserSound = Gdx.audio.newSound(Gdx.files.internal("piu-piu.ogg"));
        powerup = Gdx.audio.newSound(Gdx.files.internal("powerup.wav"));
        shieldOn = Gdx.audio.newSound(Gdx.files.internal("shield On.mp3"));
        shieldOff = Gdx.audio.newSound(Gdx.files.internal("shield Off.mp3"));
    }

    //прорисовка счета и монет
    public void drawScore(){
        scorePrint = String.valueOf(score);
        mainGame.font6.draw(mainGame.batch, "score:", 220, 780);
        mainGame.font6.draw(mainGame.batch, scorePrint, 350, 780);
    }

    public void drawCoinInGame(){
        coinPrint = String.valueOf(coin);
        mainGame.font6.draw(mainGame.batch, "coin:", 20, 780);
        mainGame.font6.draw(mainGame.batch, coinPrint, 120, 780);
    }

    //прорисовка текста
    public void showText(){
        mainGame.font4.draw(mainGame.batch, "Pause", 222, 790);
        mainGame.font1.draw(mainGame.batch, "Pause", 220, 790);
        mainGame.font3.draw(mainGame.batch, "Menu", 25, 80);
    }

    //загрузка звуков кнопок
    private void loadMusic() {
        buttonSound = Gdx.audio.newMusic(Gdx.files.internal("button_sound.wav"));
    }

    //прорисовка фона
    public void showBG() {
        mainGame.batch.draw(bg,0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
    }

    //прорисовка кнопки выхода в меню
    public void showButton() {
        if (!isExitMenuDown) {
            mainGame.batch.draw(exitMenuBtn, 0, 10);
        } else {
            mainGame.batch.draw(exitMenuBtnDown, 0, 10);
        }
    }

    //при проигрыше все полученные значения передаются на нужные экраны
    public void defeat() {
        if (lives <= 0) {
            mainGame.defeatScreen.setScore(score);
            mainGame.defeatScreen.setCoin(coin);
            mainGame.skinsScreen.setShopCoin(coin);
            dispose();
            isPaused = false;
            mainGame.setScreen(mainGame.defeatScreen);
        }
    }

    //Отрисовка жизней
    public void drawLives() {
        if (lives >= 3) {
            mainGame.batch.draw(livesTexture, 90, 700);
        }
        if (lives >= 2) {
            mainGame.batch.draw(livesTexture, 50, 700);
        }
        if (lives >= 1) {
            mainGame.batch.draw(livesTexture, 10, 700);
        }
    }

    //Метод создания врагов
    private void spawnMobArmy()
    {
        Rectangle mob = new Rectangle();
        mob.x = MathUtils.random(0, 600-64);
        mob.y = 800;
        mob.width = 32;
        mob.height = 32;
        mobArmy.add(mob);
        lastDropTime = TimeUtils.nanoTime();
    }

    //Метод создания плазменных выстрелов
    private void spawnBulletDrop()
    {
        Rectangle laserBullet = new Rectangle();
        laserBullet.x = player.x + 20;
        laserBullet.y = player.y + 50;
        laserBullet.width = 2;
        laserBullet.height = 12;
        laserBullets.add(laserBullet);
        lastBulletDropTime = TimeUtils.nanoTime();
    }

    //Метод создания ремонтных ящиков
    private void spawnHealthup(float x, float y)
    {
        Rectangle healthupPack = new Rectangle();
        healthupPack.x = x;
        healthupPack.y = y;
        healthupPack.width = 25;
        healthupPack.height = 25;
        healthupPacks.add(healthupPack);
        lastDropTime = TimeUtils.nanoTime();
    }

    private void spawnShield(float x, float y)
    {
        Rectangle shield = new Rectangle();
        shield.x = x;
        shield.y = y;
        shield.width = 25;
        shield.height = 25;
        shields.add(shield);
        lastDropTime = TimeUtils.nanoTime();
    }

    //создание экрана
    @Override
    public void show() {
        mainGame.batch = new SpriteBatch();
        Gdx.input.setInputProcessor(this);
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 600, 800);
        shapeRenderer = new ShapeRenderer();

        loadTextures();
        loadMusic();
        loadSound();

        player = new Rectangle();
        player.x = 600 / 2 - 64 / 2;
        player.y = 20;
        player.width = 64;
        player.height = 64;

        shipShield = new Rectangle();
        shipShield.x = player.x - 34;
        shipShield.y = player.y;
        shipShield.width = 120;
        shipShield.height = 120;

        mobArmy = new Array<Rectangle>();
        spawnMobArmy();

        laserBullets = new Array<Rectangle>();
        healthupPacks = new Array<Rectangle>();
        shields = new Array<Rectangle>();
    }

    //отрисовка текстур
    @Override
    public void render(float delta) {
        float deltaTime = Gdx.graphics.getDeltaTime();
        if(!isPaused){
            Gdx.gl.glClearColor(0, 0, 0.2f, 1);
            Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
            setCamera(cameraWidth / 2, cameraHeight / 2f);
            camera.update();
            mainGame.batch.setProjectionMatrix(camera.combined);
            mainGame.batch.begin();
            showBG();
            mainGame.batch.draw(pauseTexture, 560, 750);
            mainGame.batch.draw(gamePlayerSkin, player.x, player.y);
            drawScore();
            drawCoinInGame();
            drawLives();

            if (activate)
            {
                mainGame.batch.draw(activeShield, player.x - 34, player. y);
            }

            for(Rectangle mob: mobArmy)
            {
                mainGame.batch.draw(mobImage, mob.x, mob.y);
            }
            for (Rectangle laserBullet: laserBullets)
            {
                mainGame.batch.draw(bulletImage, laserBullet.x, laserBullet.y);
            }
            for (Rectangle healthupPack: healthupPacks)
            {
                mainGame.batch.draw(healthup, healthupPack.x, healthupPack.y);
                healthupPack.y -= 350 * Gdx.graphics.getDeltaTime();
            }
            for (Rectangle shield: shields)
            {
                mainGame.batch.draw(shieldUp, shield.x, shield.y);
                shield.y -= 350 * Gdx.graphics.getDeltaTime();
            }

            for(Iterator<Rectangle> iter = mobArmy.iterator(); iter.hasNext(); )
            {
                Rectangle mob = iter.next();
                if (score >= 480)
                {
                    mob.y -= 370 * Gdx.graphics.getDeltaTime();
                    if(TimeUtils.nanoTime() - lastDropTime > 500000000) spawnMobArmy();

                } else{
                    mob.y -= 200 * Gdx.graphics.getDeltaTime();
                    if(TimeUtils.nanoTime() - lastDropTime > 1000000000) spawnMobArmy();
                }
                if(mob.y + 32 < 0) {
                    if(mobArmy.contains(mob, true)){
                        mobArmy.removeValue(mob, true);
                    }
                }
                if(mob.overlaps(player))
                {
                    explosionSound.play();
                    if(mobArmy.contains(mob, true)){
                        mobArmy.removeValue(mob, true);
                    }
                    lives -= 1;
                }
                if (activate)
                {
                    if(mob.overlaps(shipShield))
                    {
                        explosionSound.play();
                        if(mobArmy.contains(mob, true)){
                            mobArmy.removeValue(mob, true);
                        }
                        shieldLives -= 1;
                    }
                }
                if (shieldLives == 0) {
                    activate = false;
                    shieldOff.play();
                    shieldLives = 3;
                }

                for(Iterator<Rectangle> iter2 = laserBullets.iterator(); iter2.hasNext(); ){
                    Rectangle laserBullet = iter2.next();
                    if (mob.overlaps(laserBullet)){
                        if (Intersector.overlaps(mob, laserBullet))
                        {
                            if ( Math.random() > 0.2 && Math.random()< 0.3) spawnHealthup(mob.x + mob.width / 2, mob.y + mob.height / 2);
                            if (Math.random()< 0.1) spawnShield(mob.x + mob.width, mob.y + mob.height);
                        }
                        killMobSound.play();
                        if(mobArmy.contains(mob, true)){
                            mobArmy.removeValue(mob, true);
                        }
                        if(laserBullets.contains(laserBullet, true)){
                            laserBullets.removeValue(laserBullet, true);
                        }
                        score += 20;
                        coin += 2;

                    }
                }
            }

            for (Iterator<Rectangle> iter = laserBullets.iterator(); iter.hasNext(); ){
                Rectangle laserBullet = iter.next();
                laserBullet.y += 600 * Gdx.graphics.getDeltaTime();
                if(laserBullet.y + 64 > 900) {
                    if(laserBullets.contains(laserBullet, true)){
                        laserBullets.removeValue(laserBullet, true);
                    }
                }
            }

            for (Iterator<Rectangle> iter = healthupPacks.iterator(); iter.hasNext();) {
                Rectangle healthupPack = iter.next();
                if (healthupPack.y + 25 < 0) {
                    if(healthupPacks.contains(healthupPack, true)){
                        healthupPacks.removeValue(healthupPack, true);
                    }
                }
                if (healthupPack.overlaps(player))
                {
                    if(lives <=2 ){
                        lives += 1;
                        powerup.play();
                        if(healthupPacks.contains(healthupPack, true)){
                            healthupPacks.removeValue(healthupPack, true);
                        }
                    }
                    if (lives>=3)
                    {
                        powerup.play();
                        if(healthupPacks.contains(healthupPack, true)){
                            healthupPacks.removeValue(healthupPack, true);
                        }
                    }
                }
            }

            for (Iterator<Rectangle> iter = shields.iterator(); iter.hasNext();) {
                Rectangle shield = iter.next();
                if (shield.y + 25 < 0) {
                    if(shields.contains(shield, true)){
                        shields.removeValue(shield, true);
                    }
                }
                if (shield.overlaps(player))
                {
                    if (activate)
                    {
                        powerup.play();
                        if(shields.contains(shield, true)){
                            shields.removeValue(shield, true);
                        }
                    }else{
                        shieldOn.play();
                        activate = true;
                        iter.remove();
                    }
                }
            }
            mainGame.batch.end();
        }else {
            //отрисовка экрана паузы
            mainGame.batch.begin();
            mainGame.batch.draw(bg,0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
            showBG();
            mainGame.batch.draw(playTexture, 560, 750);
            showButton();
            showText();
            mainGame.batch.end();
            isPaused = true;
        }
        if(Gdx.input.isKeyPressed(Input.Keys.A))
        {
            player.x -= deltaTime*playerSpeed;
            shipShield.x -= deltaTime*playerSpeed;
        }
        if(Gdx.input.isKeyPressed(Input.Keys.D))
        {
            player.x += deltaTime*playerSpeed;
            shipShield.x += deltaTime*playerSpeed;
        }
        if (TimeUtils.nanoTime() - lastBulletDropTime > bulletInterval)
        {
            if(Gdx.input.isKeyPressed(Input.Keys.SPACE))
            {
                laserSound.play();
                spawnBulletDrop();
            }
        }

        if(player.x < 0) { player.x = 0; }
        if(player.x > 600-64) { player.x = 600-64; }
        defeat();
    }

    @Override
    public void resize(int width, int height) {
        setSize(width, height);
        this.width = width;
        this.height = height;
    }

    @Override
    public void pause() {
        isPaused = true;
    }

    @Override
    public void resume() {
        isPaused = true;
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
        pauseTexture.dispose();
        playTexture.dispose();
        //после очистки все значения становятся изначальными
        lives = 3;
        score = 0;
        coin = 0;
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
        if ((height - screenY) / ppuY >= 750 && (height - screenY) / ppuY <= 796 && screenX / ppuX >= 560 && screenX / ppuX <= 596) {
            isPaused = !isPaused;
        }
        if((isPaused)&&((height-screenY)/ppuY >= 10 && (height-screenY)/ppuY <= 120 && screenX/ppuX>=0 && screenX/ppuX<=120)) {
                buttonSound.play();
                isExitMenuDown = true;
        }

        return true;

    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        if (!Gdx.app.getType().equals(Application.ApplicationType.Desktop))
            return false;
        if(isPaused){
            if(isExitMenuDown){
                dispose();
                isPaused = false;
                mainGame.setScreen(mainGame.mainMenuScreen);
            }
            isExitMenuDown = false;
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
