package luna.luna.userinterface;

import android.app.Activity;
import android.graphics.Color;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import org.luaj.vm2.LuaNil;
import org.luaj.vm2.LuaTable;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.Varargs;

import java.util.ArrayList;
import java.util.HashMap;

import ifpe.luajavaproject.MainActivity;
import luna.luna.userinterface.button.ButtonBridge;
import luna.luna.userinterface.button.ButtonProxy;

/**
 * Created by macbookair on 01/04/17.
 */
public class LayoutNativeFactory extends UserInterfaceComponent{


    protected Activity activity;


    public ViewGroup createLayout(LuaTable properties){
        // Default values
        String layoutType = "linearLayout";
        String layoutOrientation = "horizontal";

        HashMap<String, Object> layoutProperties = new HashMap();
        ViewGroup layout = null;

        if(properties != null && properties instanceof LuaTable) {
            LuaValue orientation = properties.get("orientation");
            if( !(orientation instanceof LuaNil) && orientation.isstring()){
                layoutOrientation = orientation.toString();
            }

            LuaValue type = properties.get("type");
            if( !(type instanceof LuaNil) && type.isstring()){
                layoutType = type.toString();
            }

        }

        if (layoutType.equals("linearLayout")) {
            layout = new LinearLayout(activity);
            layout.setBackgroundColor(Color.BLUE);
            if( layoutOrientation.equals("horizontal") ) {
                ((LinearLayout) layout).setOrientation(LinearLayout.HORIZONTAL);
            }else {
                ((LinearLayout) layout).setOrientation(LinearLayout.VERTICAL);
            }

        }

        return layout;
    }

    protected  LayoutNativeFactory(Activity activity, LuaTable options){
        this.activity = activity;
        this.androidView = this.createLayout(options);
    }

    /*public void insert( UserInterfaceComponent component){
        ((ViewGroup)this.androidView).addView(component.getAndroidView());
    }*/

    public void insert( ButtonBridge component){
        ((ViewGroup)this.androidView).addView(component.getButtonProxy().getAndroidView());
    }

    public static LayoutNativeFactory newLayoutFactory(MainActivity activity, LuaTable options) {

        LayoutNativeFactory layoutFactory = new LayoutNativeFactory(activity, options);

        return layoutFactory;
    }





}
