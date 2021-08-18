package com.paradigmas.game.resource;

import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;

public class Assets {

    public static final AssetManager manager = new AssetManager();


    public static final AssetDescriptor<Texture> dirt = new AssetDescriptor<Texture>("Mapa_Texturas/Dirt_texture.png", Texture.class);
    public static final AssetDescriptor<Texture> cobblestone = new AssetDescriptor<Texture>("Mapa_Texturas/Pedra_texture.png", Texture.class);
    public static final AssetDescriptor<Texture> obsidian = new AssetDescriptor<Texture>("Mapa_Texturas/Obsidian_Texture.png", Texture.class);
    public static final AssetDescriptor<Texture> playerH = new AssetDescriptor<Texture>("Personagem_principal_H/Cow__000.png", Texture.class);

    public static void load() {
        manager.load(dirt);
        manager.load(cobblestone);
        manager.load(obsidian);
        manager.load(playerH);
    }

}
