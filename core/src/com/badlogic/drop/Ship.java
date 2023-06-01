package com.badlogic.drop;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

public class Ship {
    public Vector2 position;
    public Vector2 positionBullet;
    public Sprite sprite;
    public Sprite spriteBullet;
    public float speed = 300;
    public float speedBullet = 1000;
    public Ship(Texture playerSpaceshipImage, Texture bulletImage, Color color)
    {
        sprite = new Sprite(playerSpaceshipImage);
        spriteBullet = new Sprite(bulletImage);
        spriteBullet.setScale(1);
        spriteBullet.setColor(color);
        sprite.setScale(1);
        sprite.setColor(color);
        position = new Vector2(Gdx.graphics.getWidth()/2-20, sprite.getScaleY()*sprite.getHeight()/2);
        positionBullet = new Vector2(0, 1000);
    }

    public void update(float deltaTime)
    {
        if(Gdx.input.isKeyPressed(Input.Keys.SPACE) && positionBullet.y>=Gdx.graphics.getHeight()) {
            positionBullet.x = position.x+20;
            positionBullet.y = position.y+50;
        }
        if(Gdx.input.isKeyPressed(Input.Keys.A)) position.x-=deltaTime*speed;
        if(Gdx.input.isKeyPressed(Input.Keys.D)) position.x+=deltaTime*speed;

        if(position.x-(sprite.getWidth()*sprite.getScaleX()/2)<=0) position.x = (sprite.getWidth()*sprite.getScaleX()/2);
        if(position.x+(sprite.getWidth()*sprite.getScaleX()/2)>=Gdx.graphics.getWidth()) position.x = Gdx.graphics.getWidth()-(sprite.getWidth()*sprite.getScaleX()/2);

        positionBullet.y+=deltaTime*speedBullet;
    }

    public void draw(SpriteBatch batch)
    {
        update(Gdx.graphics.getDeltaTime());
        sprite.setPosition(position.x, position.y);
        sprite.draw(batch);
        spriteBullet.setPosition(positionBullet.x, positionBullet.y);
        spriteBullet.draw(batch);
    }


}
