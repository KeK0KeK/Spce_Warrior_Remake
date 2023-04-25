package com.badlogic.drop;

import java.util.Iterator;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.TimeUtils;

public class Drop extends ApplicationAdapter {
	private Texture projectileExplosion0;
	private Texture projectileExplosion1;
	private Texture projectileExplosion2;
	private Texture projectileExplosion3;
	private Texture projectileExplosion4;
	private Texture projectileExplosion5;
	private Texture projectileExplosion6;
	private Texture blackTexture;
	private Texture shipFragments0;
	private Texture shipFragments1;
	private Texture shipFragments2;
	private Texture shipFragments3;
	private Texture shipFragments4;
	private Texture shipFragments5;
	private Texture shipFragments6;
	private Texture shipFragments7;
	private Texture bulletImage;
	private Texture healthupImage;
	private Texture miniMobImage0;
	private Texture mobImage0;
	private Texture bigMobImage0;
	private Texture miniMobImage1;
	private Texture mobImage1;
	private Texture bigMobImage1;
	private Texture miniMobImage2;
	private Texture mobImage2;
	private Texture bigMobImage2;
	private Texture playerSpaceshipImage;
	private Texture skyImage;

	private Sound explosionSound;
	private Sound killMobSound0;
	private Sound killMobSound1;
	private Sound laserSound;
	private Sound lossOfLifeSound;
	private Sound powerUpSound;

	private Music gameMusic;
	private SpriteBatch batch;
	private OrthographicCamera camera;

	private Rectangle ship;
	private Array<Rectangle> laserBulletDrops;
	private Array<Rectangle> mobDrops;
	private long lastDropTime;

	@Override
	public void create() {
		// загружены изображения врагов, эффектов, корабля, бэкграунда
		projectileExplosion0 = new Texture(Gdx.files.internal("ex0.png"));
		projectileExplosion1 = new Texture(Gdx.files.internal("ex1.png"));
		projectileExplosion2 = new Texture(Gdx.files.internal("ex2.png"));
		projectileExplosion3 = new Texture(Gdx.files.internal("ex3.png"));
		projectileExplosion4 = new Texture(Gdx.files.internal("ex4.png"));
		projectileExplosion5 = new Texture(Gdx.files.internal("ex5.png"));
		projectileExplosion6 = new Texture(Gdx.files.internal("ex6.png"));
		blackTexture = new Texture(Gdx.files.internal("ex7.png"));
		shipFragments0 = new Texture(Gdx.files.internal("expl0.png"));
		shipFragments1 = new Texture(Gdx.files.internal("expl1.png"));
		shipFragments2 = new Texture(Gdx.files.internal("expl2.png"));
		shipFragments3 = new Texture(Gdx.files.internal("expl3.png"));
		shipFragments4 = new Texture(Gdx.files.internal("expl4.png"));
		shipFragments5 = new Texture(Gdx.files.internal("expl5.png"));
		shipFragments6 = new Texture(Gdx.files.internal("expl6.png"));
		shipFragments7 = new Texture(Gdx.files.internal("expl7.png"));
		bulletImage = new Texture(Gdx.files.internal("bullet.png"));
		healthupImage = new Texture(Gdx.files.internal("healthup.png"));
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
		skyImage = new Texture(Gdx.files.internal("sky.png"));

		// загружены звуковые эффекты взрывов, убийств и всякого такого
		explosionSound = Gdx.audio.newSound(Gdx.files.internal("explosion.ogg"));
		killMobSound0 = Gdx.audio.newSound(Gdx.files.internal("kill mob 1.wav"));
		killMobSound1 = Gdx.audio.newSound(Gdx.files.internal("kill mob 1.wav"));
		//laserSound = Gdx.audio.newSound(Gdx.files.internal("laser.wav"));
		lossOfLifeSound = Gdx.audio.newSound(Gdx.files.internal("loss of life.mp3"));
		powerUpSound = Gdx.audio.newSound(Gdx.files.internal("powerup.wav"));

		// загружена фоновая музыка
		gameMusic = Gdx.audio.newMusic(Gdx.files.internal("theme.mp3"));

		// немедленно начать воспроизведение фоновой музыки
		gameMusic.setLooping(true);
		gameMusic.play();

		// создайте камеру (игровой экран) и SpriteBatch (класс для отрисовки спрайтов на экране)
		camera = new OrthographicCamera();
		camera.setToOrtho(false, 1024, 800);
		batch = new SpriteBatch();

		// создайте прямоугольник для логического представления корабля
		ship = new Rectangle();
		ship.x = 1024 / 2 - 64 / 2; // центрировать корабль по горизонтали
		ship.y = 20; // нижний левый угол корабля на 20 пикселей выше нижнего края экрана
		ship.width = 64;
		ship.height = 64;

		//Создаём прямоугольник для логического представления пули

		// создаём массив мобов и создаём первого моба
		mobDrops = new Array<Rectangle>();
		spawnMobDrop();

		// создаём массив пуль и создаём первую пулю
		laserBulletDrops = new Array<Rectangle>();
		spawnLaserBulletDrop();
	}

	private void spawnMobDrop() {
		Rectangle mobDrop = new Rectangle();
		mobDrop.x = MathUtils.random(0, 1024-64);
		mobDrop.y = 800;
		mobDrop.width = 64;
		mobDrop.height = 64;
		mobDrops.add(mobDrop);
		lastDropTime = TimeUtils.nanoTime();
	}

	private void spawnLaserBulletDrop() {
		Rectangle laserBulletDrop = new Rectangle();
		laserBulletDrop.x = ship.x + 20;
		laserBulletDrop.y = ship.y + 50;
		laserBulletDrop.width = 2;
		laserBulletDrop.height = 12;
		laserBulletDrops.add(laserBulletDrop);
		lastDropTime = TimeUtils.nanoTime();
	}

	@Override
	public void render() {
		// очистка экрана неким цветом
		ScreenUtils.clear(0, 0, 0.2f, 1);

		// обновляем матрицы камеры
		camera.update();

		// приказываем SpriteBatch рендерить в
		// системе координат, заданной камерой.
		batch.setProjectionMatrix(camera.combined);

		// начинаем новую партию и рисуем корабль и
		// всех мобов
		batch.begin();
		batch.draw(skyImage, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		batch.draw(playerSpaceshipImage, ship.x, ship.y);
		for(Rectangle mobDrop: mobDrops) {
			batch.draw(mobImage0, mobDrop.x, mobDrop.y);
		}
		for(Rectangle laserBulletDrop: laserBulletDrops) {
			batch.draw(bulletImage, laserBulletDrop.x, laserBulletDrop.y);
		}
		batch.end();

		// обрабатывать пользовательский ввод
		if(Gdx.input.isTouched()) {
			Vector3 touchPos = new Vector3();
			touchPos.set(Gdx.input.getX(), Gdx.input.getY(), 0);
			camera.unproject(touchPos);
			ship.x = touchPos.x - 64 / 2;
		}
		if(Gdx.input.isKeyPressed(Keys.SPACE)) spawnLaserBulletDrop();
		if(Gdx.input.isKeyPressed(Keys.LEFT)) ship.x -= 1000 * Gdx.graphics.getDeltaTime();
		if(Gdx.input.isKeyPressed(Keys.RIGHT)) ship.x += 1000 * Gdx.graphics.getDeltaTime();

		// убедимся, что корабль остается в пределах экрана
		if(ship.x < 0) ship.x = 0;
		if(ship.x > 1024 - 64) ship.x = 1024 - 64;

		// проверим, нужно ли нам создать нового моба
		if(TimeUtils.nanoTime() - lastDropTime > 1000000000) spawnMobDrop();
		if(TimeUtils.nanoTime() - lastDropTime > 1000000000) spawnLaserBulletDrop();

		// Перемещение мобов. Удаляем мобов, которые находятся под нижним краем
		// экрана или попавших своим хитбоксом в хитбокс корабля.
		// В последнем случае мы воспроизводим звуковой эффект.
		for (Iterator<Rectangle> iter = mobDrops.iterator(); iter.hasNext(); ) {
			Rectangle mobDrop = iter.next();
			mobDrop.y -= 200 * Gdx.graphics.getDeltaTime();
			if(mobDrop.y + 64 < 0) iter.remove();
			if(mobDrop.overlaps(ship)) {
				explosionSound.play();
				iter.remove();
			}
		}

		for (Iterator<Rectangle> iter = laserBulletDrops.iterator(); iter.hasNext(); ) {
			Rectangle laserBulletDrop = iter.next();
			laserBulletDrop.y +=200 * Gdx.graphics.getDeltaTime();
			if (laserBulletDrop.y + 64 < 0) iter.remove();
			//if (laserBulletDrop.overlaps(ship)) {
			//	explosionSound.play();
			//	iter.remove();
			//}
		}
	}

	@Override
	public void dispose() {
		// Распоряжение всеми родными ресурсами | спасибо переводчику (хз что означает)
		mobImage0.dispose();
		playerSpaceshipImage.dispose();
		explosionSound.dispose();
		gameMusic.dispose();
		batch.dispose();
	}
}