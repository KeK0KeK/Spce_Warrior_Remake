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
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.TimeUtils;

public class Drop extends ApplicationAdapter {
	public Texture imgAlien;
	private Texture bulletImage;
	private Texture playerSpaceshipImage;

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

	private Array<Alien> aliens;
	Texture[] alienTextures = new Texture[10];
	private long lastDropTime;

	@Override
	public void create() {

		batch = new SpriteBatch();

		alienTextures[0] = new Texture(Gdx.files.internal("bullet.png"));
		alienTextures[1] = new Texture(Gdx.files.internal("mob1 small.png"));
		alienTextures[2] = new Texture(Gdx.files.internal("mob1.png"));
		alienTextures[3] = new Texture(Gdx.files.internal("mob1 big.png"));
		alienTextures[4] = new Texture(Gdx.files.internal("mob2 small.png"));
		alienTextures[5] = new Texture(Gdx.files.internal("mob2.png"));
		alienTextures[6] = new Texture(Gdx.files.internal("mob2 big.png"));
		alienTextures[7] = new Texture(Gdx.files.internal("mob3 small.png"));
		alienTextures[8] = new Texture(Gdx.files.internal("mob3.png"));
		alienTextures[9] = new Texture(Gdx.files.internal("mob3 big.png"));
		playerSpaceshipImage = new Texture(Gdx.files.internal("player spaceship0.png"));

		playerSpaceshipImage = new Texture("player spaceship0.png");
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

		ship = new Ship(playerSpaceshipImage, bulletImage, Color.WHITE);
		aliens = new Array<Alien>();
		spawnAlienDrop();
	}
	private void spawnAlienDrop()
	{
		if (aliens.size < 10) {
			Alien alien = new Alien(alienTextures[(int)(Math.random()*9)], Color.WHITE, MathUtils.random(1, 3));
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
		ship.draw(batch);
		for(Alien alien : aliens)
		{
			batch.draw(alienTextures[(int)(Math.random()*9)], alien.position.x, alien.position.y);
		}
		for(Iterator<Alien> iter = aliens.iterator(); iter.hasNext();)
		{
			Alien alien = iter.next();
			if (alien.ID == 1) alien.position.y -= 200 *Gdx.graphics.getDeltaTime();
			if (alien.ID == 2)
			{
				alien.position.x -= 200 *Gdx.graphics.getDeltaTime();
				alien.position.y -= 200 *Gdx.graphics.getDeltaTime();
			}
			if (alien.ID == 3)
			{
				alien.position.x += 200 *Gdx.graphics.getDeltaTime();
				alien.position.y -= 200 *Gdx.graphics.getDeltaTime();
			}
			if(alien.position.y < -40) iter.remove();
			if(alien.position.x < -600) iter.remove();
			if(alien.position.x > 600) iter.remove();
			if(ship.sprite.getBoundingRectangle().overlaps(alien.sprite.getBoundingRectangle()))
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
//		for(int i=0;i<aliens.length;i++)
//		{
//			if(aliens[i].Alive)
//			{
//				if (ship.spriteBullet.getBoundingRectangle().overlaps(aliens[i].sprite.getBoundingRectangle()))
//				{
//					ship.positionBullet.y = 10000;
//					aliens[i].Alive = false;
//					killMobSound0.play();
//					break;
//				}
//			}
//		}

//		for(int i = 0; i<aliens.length;i++)
//		{
//			if(aliens[i].Alive)
//			{
//				aliens[i].position =new Vector2(aliens[i].position_initial.x+offset_aliens.x, aliens[i].position_initial.y+offset_aliens.y);
//				if(aliens[i].Alive)
//				{
//					aliens[i].Draw(batch);
//					if(aliens[i].sprite.getBoundingRectangle().overlaps(ship.sprite.getBoundingRectangle()))
//					{
//						Gdx.app.exit();
//					}
//				}
//			}
//		}
		batch.end();

		if(TimeUtils.nanoTime() - lastDropTime > 10000000) spawnAlienDrop();
	}

	@Override
	public void dispose() {
		batch.dispose();
		playerSpaceshipImage.dispose();
	}
}