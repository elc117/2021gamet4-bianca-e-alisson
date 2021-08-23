package com.paradigmas.game.resource;

import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;

public class Assets {

    public static final AssetManager manager = new AssetManager();

    public static final AssetDescriptor<Texture> dirt = new AssetDescriptor<>("Mapa_Texturas/Dirt_texture.png", Texture.class);
    public static final AssetDescriptor<Texture> cobblestone = new AssetDescriptor<>("Mapa_Texturas/Pedra_texture.png", Texture.class);
    public static final AssetDescriptor<Texture> obsidian = new AssetDescriptor<>("Mapa_Texturas/Obsidian_Texture.png", Texture.class);
    public static final AssetDescriptor<Texture> playerH = new AssetDescriptor<>("Personagem_principal_H/Cow__000.png", Texture.class);

    public static void load() {
        Texture.setAssetManager(manager);

        manager.load(dirt);
        manager.load(cobblestone);
        manager.load(obsidian);
        manager.load(playerH);
    }
}
