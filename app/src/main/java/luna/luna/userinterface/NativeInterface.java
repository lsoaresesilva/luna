package luna.luna.userinterface;

import org.luaj.vm2.LuaTable;
import org.luaj.vm2.LuaValue;

import ifpe.luajavaproject.MainActivity;
import luna.luna.userinterface.button.ButtonBridge;
import luna.luna.userinterface.button.ButtonFactory;

/**
 * Created by macbookair on 26/03/17.
 *
 * TODO:
 *      * Permitir o desenho de botões (10%)
 *      **  Implementar callback para o rouch no botao
 *      * Permitir o desenho de campos de texto
 *      * Permitir o desenho de um date picker
 *      * Permitir o desenho de um Radio
 *      * Permitir o desenho de um Checkbox
 *      * Permitir grids
 *      * Permitir menu
 *
 *  This class is the bridge between Lua and Java UI manipulation.
 *  Actually, Luna userinterface libs make calls to this class to create components.
 *  Its a kind of Factor class. For every newXXX called, an "abstracted" (do not confuse with abstract form java) class is returned instead of the native android object.
 *  This "abstracted" class contains does not contains all methods from native android, only the one supported on Lua. Anyone could extend this class and add more behavior to Luna interface components.
 *  This decision was made to prevent users on Lua calling functions directly on native objects which may produce unpredectible behavior.
 */
public class NativeInterface {

    private MainActivity activity;

    public NativeInterface(MainActivity activity){
        this.activity = activity;
    }

    public TextFieldNativeLayer newTextField(LuaValue properties){
        return TextFieldNativeLayer.newTextField(this.activity, properties);
    }

    /*public ButtonNativeFactory newButton(LuaValue properties){
        return ButtonNativeFactory.newButtonFactory(this.activity, properties);
    }*/

    public ButtonBridge newButton(String language, Object properties){
        return ButtonFactory.newButton(language, properties, this.activity);
    }

    public SceneNativeFactory newScene(LuaTable options){
        return SceneNativeFactory.newSceneFactory(this.activity, options);
    }

    public TextNativeFactory newText(LuaTable options){
        return TextNativeFactory.newText(this.activity, options);
    }

    public LayoutNativeFactory newLayout(LuaTable options){
        return LayoutNativeFactory.newLayoutFactory(this.activity, options);
    }


}


