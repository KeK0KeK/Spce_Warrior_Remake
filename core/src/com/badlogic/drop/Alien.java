package com.badlogic.drop;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

import java.util.concurrent.ThreadLocalRandom;

public class Alien {
    public Vector2 position;
    public Rectangle alienRectangle;
    public Sprite sprite;
    public int ID;
    Boolean Alive = true;
    public Alien(Texture alienTextures, Color color, int ID)
    {
        position = new Vector2();
        sprite = new Sprite(alienTextures);
        sprite.setColor(color);
        sprite.setScale(1);
        this.ID = ID;
        alienRectangle = new Rectangle(position.x - (sprite.getWidth() * sprite.getScaleX() / 2),
                                        position.y - (sprite.getHeight() * sprite.getScaleY() / 2),
                                        sprite.getWidth() * sprite.getScaleX(),
                                        sprite.getHeight() * sprite.getScaleY());
    }

    public Rectangle getBounds()
    {
        return sprite.getBoundingRectangle();
    }
    public void Draw(SpriteBatch batch)
    {
        sprite.setPosition(position.x, position.y);
        sprite.draw(batch);
    }
}
