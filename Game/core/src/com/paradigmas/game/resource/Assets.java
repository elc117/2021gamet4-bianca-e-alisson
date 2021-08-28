package com.paradigmas.game.resource;

import static net.dermetfan.gdx.assets.AnnotationAssetManager.*;

import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.graphics.Texture;

import net.dermetfan.gdx.assets.AnnotationAssetManager;

public class Assets {

    public static final AnnotationAssetManager manager = new AnnotationAssetManager(new InternalFileHandleResolver());

    /// * BLOCOS:
    // Ground
    @Asset public static final AssetDescriptor<Texture> Ground_Left_1 = new AssetDescriptor<>("Mapa_Texturas/Ground_Left_1.png", Texture.class);
    @Asset public static final AssetDescriptor<Texture> Ground_Mid_1 = new AssetDescriptor<>("Mapa_Texturas/Ground_Mid_1.png", Texture.class);
    @Asset public static final AssetDescriptor<Texture> Ground_Right_1 = new AssetDescriptor<>("Mapa_Texturas/Ground_Right_1.png", Texture.class);

    // Preenchimento
    @Asset public static final AssetDescriptor<Texture> Mid_1 = new AssetDescriptor<>("Mapa_Texturas/Mid_1.png", Texture.class);

    // Plattaform
    @Asset public static final AssetDescriptor<Texture> Platt_Left_1 = new AssetDescriptor<>("Mapa_Texturas/Platt_Left_1.png", Texture.class);
    @Asset public static final AssetDescriptor<Texture> Platt_Mid_1 = new AssetDescriptor<>("Mapa_Texturas/Platt_Mid_1.png", Texture.class);
    @Asset public static final AssetDescriptor<Texture> Platt_Right_1 = new AssetDescriptor<>("Mapa_Texturas/Platt_Right_1.png", Texture.class);

    // Wall
    @Asset public static final AssetDescriptor<Texture> Wall_Mid_Right_1 = new AssetDescriptor<>("Mapa_Texturas/Wall_Mid-Right_1.png", Texture.class);
    @Asset public static final AssetDescriptor<Texture> Wall_Mid_Left_1 = new AssetDescriptor<>("Mapa_Texturas/Wall_Mid-Left_1.png", Texture.class);

    // BackGround
    @Asset public static final AssetDescriptor<Texture> dirt = new AssetDescriptor<>("Mapa_Texturas/Dirt_texture.png", Texture.class);
    @Asset public static final AssetDescriptor<Texture> cobblestone = new AssetDescriptor<>("Mapa_Texturas/Pedra_texture.png", Texture.class);
    @Asset public static final AssetDescriptor<Texture> obsidian = new AssetDescriptor<>("Mapa_Texturas/Obsidian_Texture.png", Texture.class);


    // OBJECTIVES
    @Asset public static final AssetDescriptor<Texture> Codigo_1 = new AssetDescriptor<>("Objectives/Codigo_1.png", Texture.class);

    // * ENTIDADES:
    /** TODO: MUDAR O TAMANHO DO PERSONAGEM E FAZER A ANIMAÇÃO DA MOVIMENTAÇÃO*/
    @Asset public static final AssetDescriptor<Texture> playerH = new AssetDescriptor<>("Personagem_principal_H/Cow__000.png", Texture.class);

    public static void load() {
        Texture.setAssetManager(manager);

        manager.load(Assets.class);
    }
}