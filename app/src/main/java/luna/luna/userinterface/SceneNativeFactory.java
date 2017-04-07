package luna.luna.userinterface;

import android.text.Layout;
import android.widget.RelativeLayout;

import org.luaj.vm2.LuaError;
import org.luaj.vm2.LuaTable;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.lib.jse.CoerceLuaToJava;

import java.util.IllegalFormatCodePointException;

import ifpe.luajavaproject.MainActivity;
import ifpe.luajavaproject.R;

/**
 * This class is an "abstraction" for native elements used to create an Activity and layouts.
 */
public class SceneNativeFactory{

    private final MainActivity activity;
    private LayoutNativeFactory sceneMainLayout;

    public static SceneNativeFactory newSceneFactory(MainActivity activity, LuaTable properties) {
        if( properties != null ) {
            try {
                LuaTable layoutProperty = (LuaTable)properties.get("layout");
                LayoutNativeFactory layout = null;
                if( layoutProperty != null)
                    layout = (LayoutNativeFactory)CoerceLuaToJava.coerce(layoutProperty.get("_nativeObject"), LayoutNativeFactory.class);
                if(layout == null){
                    throw new IllegalArgumentException("A scene must have a layout.");
                }

                SceneNativeFactory sceneFactory = new SceneNativeFactory(activity, layout);
                return sceneFactory;
            }catch(LuaError le ){

            }catch( ClassCastException ce){
                throw new ClassCastException("Did you pass a correct layout?.");
            }

        }

        return null;
    }

    private SceneNativeFactory(MainActivity activity, LayoutNativeFactory layout) {
        this.activity = activity;
        this.sceneMainLayout = layout;
    }

    /**
     * Show the contents of this scene (actually its main layouts  inner views).
     */
    public void goToScene(){

        if( sceneMainLayout != null ) {
            RelativeLayout mainLayout = (RelativeLayout) this.activity.findViewById(R.id.main_layout);
            if (mainLayout != null) {
                mainLayout.removeAllViews();
                mainLayout.addView(sceneMainLayout.getAndroidView());
            }
        }else{
            throw new IllegalArgumentException("A scene must have a layout.");
        }

    }

    public LayoutNativeFactory getSceneMainLayout() {
        return sceneMainLayout;
    }

    public void setSceneMainLayout(LayoutNativeFactory sceneMainLayout) {
        this.sceneMainLayout = sceneMainLayout;
    }




}
