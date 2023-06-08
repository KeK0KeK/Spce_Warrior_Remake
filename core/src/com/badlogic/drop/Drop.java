package com.badlogic.drop;

import java.util.Arrays;
import java.util.Iterator;
import java.util.Random;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.*;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.TimeUtils;

public class Drop extends ApplicationAdapter {
	public Texture imgAlien;
	private Texture bulletImage;
	private Texture playerSpaceshipImage;

	private Texture bg;
	private Texture miniMobImage0;
	private Texture mobImage0;
	private Texture bigMobImage0;
	private Texture miniMobImage1;
	private Texture mobImage1;
	private Texture bigMobImage1;
	private Texture miniMobImage2;
	private Texture mobImage2;
	private Texture bigMobImage2;
	private Sound explosionSound;
	private Sound killMobSound0;
	private Sound killMobSound1;
	private Sound laserSound;
	private Sound lossOfLifeSound;
	private Sound powerUpSound;

	private Music gameMusic;
	Ship ship;
	Alien alien;
	SpriteBatch batch;
	public TextureRegion shipRegion;

	private Array<Alien> aliens;
	TextureRegion[] alienTextures = new TextureRegion[9];
	private long lastDropTime;

	@Override
	public void create() {

		batch = new SpriteBatch();

		bg = new Texture(Gdx.files.internal("sky.png"));

		miniMobImage0 = new Texture(Gdx.files.internal("mob1 small.png"));
		mobImage0 = new Texture(Gdx.files.internal("mob1.png"));
		bigMobImage0 = new Texture(Gdx.files.internal("mob1 big.png"));
		miniMobImage1 = new Texture(Gdx.files.internal("mob2 small.png"));
		mobImage1 = new Texture(Gdx.files.internal("mob2.png"));
		bigMobImage1 = new Texture(Gdx.files.internal("mob2 big.png"));
		miniMobImage2 = new Texture(Gdx.files.internal("mob3 small.png"));
		mobImage2 = new Texture(Gdx.files.internal("mob3.png"));
		bigMobImage2 = new Texture(Gdx.files.internal("mob3 big.png"));
		playerSpaceshipImage = new Texture(Gdx.files.internal("player spaceship0.png"));

		//playerSpaceshipImage = new Texture("player spaceship0.png");
		shipRegion = new TextureRegion(playerSpaceshipImage, 0, 0, 64, 64);

		alienTextures[0] = new TextureRegion(miniMobImage0, 0, 0, 20, 20);
		alienTextures[1] = new TextureRegion(miniMobImage1, 0, 0, 20, 20);
		alienTextures[2] = new TextureRegion(miniMobImage2, 0, 0, 20, 20);
		alienTextures[3] = new TextureRegion(mobImage0, 0, 0, 32, 32);
		alienTextures[4] = new TextureRegion(mobImage1, 0, 0, 32, 32);
		alienTextures[5] = new TextureRegion(mobImage2, 0, 0, 32, 32);
		alienTextures[6] = new TextureRegion(bigMobImage0, 0, 0, 50, 50);
		alienTextures[7] = new TextureRegion(bigMobImage1, 0, 0, 50, 50);
		alienTextures[8] = new TextureRegion(bigMobImage2, 0, 0, 50, 50);


		bulletImage = new Texture("bullet.png");

		imgAlien = new Texture("mob1.png");

		// загружены звуковые эффекты взрывов, убийств и всякого такого
		explosionSound = Gdx.audio.newSound(Gdx.files.internal("explosion.ogg"));
		killMobSound0 = Gdx.audio.newSound(Gdx.files.internal("kill mob 1.wav"));
		killMobSound1 = Gdx.audio.newSound(Gdx.files.internal("kill mob 2.wav"));
		//laserSound = Gdx.audio.newSound(Gdx.files.internal("laser.wav"));
		lossOfLifeSound = Gdx.audio.newSound(Gdx.files.internal("loss of life.mp3"));
		powerUpSound = Gdx.audio.newSound(Gdx.files.internal("powerup.wav"));

		// загружена фоновая музыка
		gameMusic = Gdx.audio.newMusic(Gdx.files.internal("theme.mp3"));

		// немедленно начать воспроизведение фоновой музыки
		gameMusic.setLooping(true);
		gameMusic.play();
		ship = new Ship(shipRegion, bulletImage, Color.WHITE);

		aliens = new Array<Alien>();
		spawnAlienDrop();
	}

	private void spawnAlienDrop()
	{
		if (aliens.size < 10) {
			Alien alien = new Alien(alienTextures[(int)(Math.random()* alienTextures.length)].getTexture(), Color.WHITE, MathUtils.random(0, 8));
			alien.position.x = MathUtils.random(30, 570);
			alien.position.y = 800;
			aliens.add(alien);
			lastDropTime = TimeUtils.nanoTime();
		}
	}

	@Override
	public void render() {
		float deltaTime = Gdx.graphics.getDeltaTime();
		ScreenUtils.clear(0,0,0,1);
		batch.begin();
		batch.draw(bg, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		ship.draw(batch);
		for(Alien alien : aliens)
		{
			Sprite sprite = new Sprite(alienTextures[alien.ID]);
			sprite.setPosition(alien.position.x, alien.position.y);
			sprite.draw(batch);
		}
		for(Iterator<Alien> iter = aliens.iterator(); iter.hasNext();)
		{
			Alien alien = iter.next();
			if (alien.ID == 1 || alien.ID == 4|| alien.ID == 7) alien.position.y -= 200 *Gdx.graphics.getDeltaTime();
			if (alien.ID == 2 || alien.ID == 5|| alien.ID == 8)
			{
				alien.position.x -= 200 *Gdx.graphics.getDeltaTime();
				alien.position.y -= 200 *Gdx.graphics.getDeltaTime();
			}
			if (alien.ID == 3 || alien.ID == 6|| alien.ID == 9)
			{
				alien.position.x += 200 *Gdx.graphics.getDeltaTime();
     			alien.position.y -= 200 *Gdx.graphics.getDeltaTime();
			}
			if(alien.position.y < -40) iter.remove();
			if(alien.position.x < -600) iter.remove();
			if(alien.position.x > 600) iter.remove();
			if(alien.Alive)
			{
				if(Intersector.overlaps(ship.sprite.getBoundingRectangle(), alien.sprite.getBoundingRectangle()))
				{
					explosionSound.play();
					iter.remove();
					Gdx.app.exit();
				}
				if(alien.sprite.getBoundingRectangle().overlaps(ship.spriteBullet.getBoundingRectangle()))
				{
					ship.positionBullet.y = 10000;
					alien.Alive = false;
					killMobSound0.play();
					iter.remove();
				}
			}
		}
		batch.end();
		if(TimeUtils.nanoTime() - lastDropTime > 10000000) spawnAlienDrop();
	}

	@Override
	public void dispose() {
		batch.dispose();
		playerSpaceshipImage.dispose();
	}
}