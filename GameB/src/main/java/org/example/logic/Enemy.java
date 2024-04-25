package org.example.logic;

import javax.swing.*;
import java.awt.*;

public class Enemy extends Entity {
    private int damage;

    public Enemy(int x, int y, String url, int damage) {
        super(x, y, url);
        this.damage = damage;
    }
}
