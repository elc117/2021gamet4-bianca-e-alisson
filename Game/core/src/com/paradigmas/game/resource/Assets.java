package com.paradigmas.game.resource;

import static net.dermetfan.gdx.assets.AnnotationAssetManager.Asset;

import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.graphics.Texture;

import net.dermetfan.gdx.assets.AnnotationAssetManager;

public class Assets {

    public static final AnnotationAssetManager manager = new AnnotationAssetManager(new InternalFileHandleResolver());

    /// * BLOCOS:
    // Ground
    @Asset public static final AssetDescriptor<Texture> Ground_Left_1 = new AssetDescriptor<>("Mapa_Texturas/teste.png", Texture.class);
    @Asset public static final AssetDescriptor<Texture> Ground_Mid_1 = new AssetDescriptor<>("Mapa_Texturas/teste.png", Texture.class);
    @Asset public static final AssetDescriptor<Texture> Ground_Right_1 = new AssetDescriptor<>("Mapa_Texturas/teste.png", Texture.class);


    // Preenchimento
    @Asset public static final AssetDescriptor<Texture> Mid_1 = new AssetDescriptor<>("Mapa_Texturas/teste.png", Texture.class);

    // Plattaform
    @Asset public static final AssetDescriptor<Texture> Platt_Left_1 = new AssetDescriptor<>("Mapa_Texturas/teste.png", Texture.class);
    @Asset public static final AssetDescriptor<Texture> Platt_Mid_1 = new AssetDescriptor<>("Mapa_Texturas/teste.png", Texture.class);
    @Asset public static final AssetDescriptor<Texture> Platt_Right_1 = new AssetDescriptor<>("Mapa_Texturas/teste.png", Texture.class);

    // Wall
    @Asset public static final AssetDescriptor<Texture> Wall_Mid_Right_1 = new AssetDescriptor<>("Mapa_Texturas/teste.png", Texture.class);
    @Asset public static final AssetDescriptor<Texture> Wall_Mid_Left_1 = new AssetDescriptor<>("Mapa_Texturas/teste.png", Texture.class);

    /// * BackGround
    // menu
    @Asset public static final AssetDescriptor<Texture> menu_background_002 = new AssetDescriptor<>("Mapa_Texturas/menu_background_002.jpg", Texture.class);

    // next fase
    @Asset public static final AssetDescriptor<Texture> nextfase_background_001 = new AssetDescriptor<>("Mapa_Texturas/nextfase_background_001.png", Texture.class);

    // lose
    @Asset public static final AssetDescriptor<Texture> lose_background_001 = new AssetDescriptor<>("Mapa_Texturas/lose_background_001.png", Texture.class);

    @Asset public static final AssetDescriptor<Texture> dirt = new AssetDescriptor<>("Mapa_Texturas/Dirt_texture.png", Texture.class);
    @Asset public static final AssetDescriptor<Texture> cobblestone = new AssetDescriptor<>("Mapa_Texturas/Pedra_texture.png", Texture.class);
    @Asset public static final AssetDescriptor<Texture> obsidian = new AssetDescriptor<>("Mapa_Texturas/Obsidian_Texture.png", Texture.class);


    /// * OBJECTIVES
    @Asset public static final AssetDescriptor<Texture> Codigo_1 = new AssetDescriptor<>("Objectives/Codigo_1.png", Texture.class);
    @Asset public static final AssetDescriptor<Texture> Coffe = new AssetDescriptor<>("Objectives/java_logo-coffe.png", Texture.class);
    @Asset public static final AssetDescriptor<Texture> Potion = new AssetDescriptor<>("Objectives/potion.png", Texture.class);
    @Asset public static final AssetDescriptor<Texture> Sleepy = new AssetDescriptor<>("Objectives/sleepy.png", Texture.class);

    /// * ENTIDADES:
    /** TODO: MUDAR O TAMANHO DO PERSONAGEM E FAZER A ANIMAÇÃO DA MOVIMENTAÇÃO*/
    @Asset public static final AssetDescriptor<Texture> playerH = new AssetDescriptor<>("Personagem_principal_H/Cow__000.png", Texture.class);

    //IDLLE
    @Asset public static final AssetDescriptor<Texture> Idle_right_000 = new AssetDescriptor<>("Personagem_principal_H/Idle_right_000.png", Texture.class);
    @Asset public static final AssetDescriptor<Texture> Idle_left_000 = new AssetDescriptor<>("Personagem_principal_H/Idle_left_000.png", Texture.class);

    // RUN
    @Asset public static final AssetDescriptor<Texture> Run__000 = new AssetDescriptor<>("Personagem_principal_H/Run__000.png", Texture.class);
    @Asset public static final AssetDescriptor<Texture> Run__001 = new AssetDescriptor<>("Personagem_principal_H/Run__001.png", Texture.class);
    @Asset public static final AssetDescriptor<Texture> Run__002 = new AssetDescriptor<>("Personagem_principal_H/Run__002.png", Texture.class);
    @Asset public static final AssetDescriptor<Texture> Run__003 = new AssetDescriptor<>("Personagem_principal_H/Run__003.png", Texture.class);
    @Asset public static final AssetDescriptor<Texture> Run__004 = new AssetDescriptor<>("Personagem_principal_H/Run__004.png", Texture.class);
    @Asset public static final AssetDescriptor<Texture> Run__005 = new AssetDescriptor<>("Personagem_principal_H/Run__005.png", Texture.class);
    @Asset public static final AssetDescriptor<Texture> Run__006 = new AssetDescriptor<>("Personagem_principal_H/Run__006.png", Texture.class);
    @Asset public static final AssetDescriptor<Texture> Run__007 = new AssetDescriptor<>("Personagem_principal_H/Run__007.png", Texture.class);
    @Asset public static final AssetDescriptor<Texture> Run__008 = new AssetDescriptor<>("Personagem_principal_H/Run__008.png", Texture.class);
    @Asset public static final AssetDescriptor<Texture> Run__009 = new AssetDescriptor<>("Personagem_principal_H/Run__009.png", Texture.class);

    /// * BUTTONS
    // MENU
    @Asset public static final AssetDescriptor<Texture> menu_icon_1 = new AssetDescriptor<>("Mapa_Texturas/menu_icon_1.png", Texture.class);

    /// * BUTTONS
    @Asset public static final AssetDescriptor<Texture> Start = new AssetDescriptor<>("buttons/Start2.png", Texture.class);
    @Asset public static final AssetDescriptor<Texture> On_Start = new AssetDescriptor<>("buttons/On_Start2.png", Texture.class);
    @Asset public static final AssetDescriptor<Texture> Controls = new AssetDescriptor<>("buttons/controls2.png", Texture.class);
    @Asset public static final AssetDescriptor<Texture> On_Controls = new AssetDescriptor<>("buttons/on_controls2.png", Texture.class);
    @Asset public static final AssetDescriptor<Texture> NextFase = new AssetDescriptor<>("buttons/skip2.png", Texture.class);
    @Asset public static final AssetDescriptor<Texture> On_NextFase = new AssetDescriptor<>("buttons/on_skip2.png", Texture.class);
    @Asset public static final AssetDescriptor<Texture> Retry = new AssetDescriptor<>("buttons/retry2.png", Texture.class);
    @Asset public static final AssetDescriptor<Texture> On_Retry = new AssetDescriptor<>("buttons/on_retry2.png", Texture.class);

    public static void load() {
        Texture.setAssetManager(manager);

        manager.load(Assets.class);
    }
}
