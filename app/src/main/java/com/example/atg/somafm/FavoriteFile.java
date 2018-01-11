package com.example.atg.somafm;

import android.content.Context;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by atg on 07.01.18.
 */

public class FavoriteFile {
    private String filePath;
    private Context context;

    private List<String> lst = new ArrayList<>();

    public FavoriteFile(Context context) {
        this.context = context;
        filePath = context.getFilesDir() + "/favorites.txt";

        readFile();
    }

    public boolean isFavorite(String radioName) {
        return lst.contains(radioName);
    }

    public void setFavorite(String radioName) {
        if(!isFavorite(radioName)) {
            lst.add(radioName);
            saveFile();
        }
    }

    public void removeFavorite(String radioName) {
        lst.remove(radioName);
        saveFile();
    }

    private void readFile() {
        BufferedReader reader = null;
        try {
            reader = new BufferedReader( new FileReader(filePath));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return;
        }
        String line = null;
        try {
            while((line = reader.readLine()) != null) {
                lst.add(line);
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void saveFile() {
        FileWriter writer = null;
        try {
            writer = new FileWriter(filePath, false);
        } catch (IOException e) {
            e.printStackTrace();
        }
        for(String str : lst) {
            try {
                writer.write(str + "\n");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try {
            writer.flush();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
