/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.util.ArrayList;
import java.util.List;

public class Ffavourites {

    private List<FavouriteItem> items = new ArrayList<>();

    public List<FavouriteItem> getItems() {
        return items;
    }

    public void add(FavouriteItem item) {
        for (FavouriteItem i : items) {
            if (i.getId() == item.getId()) return;
        }
        items.add(item);
    }

    public void remove(int id) {
        items.removeIf(i -> i.getId() == id);
    }
}

