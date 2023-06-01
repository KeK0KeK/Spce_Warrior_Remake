package com.badlogic.drop;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

import java.util.concurrent.ThreadLocalRandom;

public class Alien {
    public Vector2 position;
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
    }
    public void Draw(SpriteBatch batch)
    {
        sprite.setPosition(position.x, position.y);
        sprite.draw(batch);
    }
}
