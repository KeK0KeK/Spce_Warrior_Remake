package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.mygdx.game.Screens.*;

public class MainGame extends Game{

	public GameScreen gameScreen;
	public SkinsScreen skinsScreen;
	public DefeatScreen defeatScreen;
	public MainMenuScreen mainMenuScreen;
	public SpriteBatch batch;
	public BitmapFont font1, font2, font3, font4, font5, font6, font7;




	public void fonts(){
		FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("font.ttf"));
		FreeTypeFontGenerator.FreeTypeFontParameter parameter1 = new FreeTypeFontGenerator.FreeTypeFontParameter();
		FreeTypeFontGenerator.FreeTypeFontParameter parameter2 = new FreeTypeFontGenerator.FreeTypeFontParameter();
		FreeTypeFontGenerator.FreeTypeFontParameter parameter3 = new FreeTypeFontGenerator.FreeTypeFontParameter();
		FreeTypeFontGenerator.FreeTypeFontParameter parameter4 = new FreeTypeFontGenerator.FreeTypeFontParameter();
		FreeTypeFontGenerator.FreeTypeFontParameter parameter5 = new FreeTypeFontGenerator.FreeTypeFontParameter();
		parameter1.size = 52;
		parameter2.size = 25;
		parameter3.size = 43;
		parameter4.size = 53;
		parameter5.size = 70;
		font1 = generator.generateFont(parameter1);
		font2 = generator.generateFont(parameter1);
		font3 = generator.generateFont(parameter3);
		font4 = generator.generateFont(parameter4);
		font5 = generator.generateFont(parameter2);
		font6 = generator.generateFont(parameter2);
		font7 = generator.generateFont(parameter5);
		font1.setColor(1, 0.1f, 0.1f, 1);
		font2.setColor(0.8f, 0.8f, 0.2f, 1);
		font3.setColor(0.8f, 0.8f, 0.2f, 1);
		font4.setColor(0.8f, 0.8f, 0.2f, 1);
		font5.setColor(1, 0.1f, 0.1f, 1);
		font6.setColor(0.8f, 0.8f, 0.2f, 1);
		font7.setColor(1, 0.1f, 0.1f, 1);
	}

	public GameScreen getGameScreen() {
		return gameScreen;
	}

	public void setGameScreen(GameScreen gameScreen) {
		this.gameScreen = gameScreen;
	}

	public SkinsScreen getSkinsScreen() {
		return skinsScreen;
	}

	public void setSkinsScreen(SkinsScreen skinsScreen) {
		this.skinsScreen = skinsScreen;
	}

	public DefeatScreen getDefeatScreen() {
		return defeatScreen;
	}

	public void setDefeatScreen(DefeatScreen defeatScreen) {
		this.defeatScreen = defeatScreen;
	}

	public MainMenuScreen getMainMenuScreen() {
		return mainMenuScreen;
	}

	public void setMainMenuScreen(MainMenuScreen mainMenuScreen) {
		this.mainMenuScreen = mainMenuScreen;
	}

	public SpriteBatch getBatch() {
		return batch;
	}

	public void setBatch(SpriteBatch batch) {
		this.batch = batch;
	}

	public BitmapFont getFont1() {
		return font1;
	}

	public void setFont1(BitmapFont font1) {
		this.font1 = font1;
	}

	public BitmapFont getFont2() {
		return font2;
	}

	public void setFont2(BitmapFont font2) {
		this.font2 = font2;
	}

	public BitmapFont getFont3() {
		return font3;
	}

	public void setFont3(BitmapFont font3) {
		this.font3 = font3;
	}

	public BitmapFont getFont4() {
		return font4;
	}

	public void setFont4(BitmapFont font4) {
		this.font4 = font4;
	}

	public BitmapFont getFont5() {
		return font5;
	}

	public void setFont5(BitmapFont font5) {
		this.font5 = font5;
	}

	public BitmapFont getFont6() {
		return font6;
	}

	public void setFont6(BitmapFont font6) {
		this.font6 = font6;
	}

	public BitmapFont getFont7() {
		return font7;
	}

	public void setFont7(BitmapFont font7) {
		this.font7 = font7;
	}

	@Override
	public void create () {
		batch = new SpriteBatch();
		fonts();
		mainMenuScreen = new MainMenuScreen(this);
		skinsScreen = new SkinsScreen(this);
		gameScreen = new GameScreen(this);
		defeatScreen = new DefeatScreen(this);
		setScreen(mainMenuScreen);
	}

	@Override
	public void resize(int width, int height) {

	}

	@Override
	public void render() {
		super.render();
	}

	@Override
	public void pause() {

	}

	@Override
	public void resume() {

	}

	@Override
	public void dispose () {
		batch.dispose();
	}

}