package luna.luna.userinterface;

import android.app.Activity;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import org.luaj.vm2.LuaTable;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.Varargs;

import java.util.ArrayList;
import java.util.HashMap;

import ifpe.luajavaproject.MainActivity;

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
            if( orientation != null && orientation.isstring()){
                layoutOrientation = orientation.toString();
            }

            LuaValue type = properties.get("type");
            if( type != null && type.isstring()){
                layoutType = type.toString();
            }
            /*LuaValue k = LuaValue.NIL;
            while (true) {
                Varargs n = properties.next(k);
                if ((k = n.arg1()).isnil())
                    break;

                LuaValue v = n.arg(2);
                try {
                    String key = k.toString();
                    if (key.equals("type")) {
                        layoutType = v.isstring() ? v.toString() : null;
                    } else if (key.equals("orientation")) {
                        layoutProperties.put(key, v.toString());
                    }
                } catch (ClassCastException e) {

                }
            }*/
        }

        if (layoutType.equals("linearLayout")) {
            layout = new LinearLayout(activity);
            if( layoutOrientation.equals("horizontal") ) {
                ((LinearLayout) layout).setOrientation(LinearLayout.HORIZONTAL);
            }else {
                ((LinearLayout) layout).setOrientation(LinearLayout.VERTICAL);
            }
                /*
            if( layoutProperties.containsKey("orientation")){
                if( layoutProperties.get("orientation").equals("horizontal") ){
                    ;
                }else if ( layoutProperties.get("orientation").equals("vertical") ){
                    ((LinearLayout)layout).setOrientation(LinearLayout.VERTICAL);
                }

            }*/

        }

        return layout;
    }

    protected  LayoutNativeFactory(Activity activity, LuaTable options){
        this.activity = activity;
        this.androidView = this.createLayout(options);
    }

    public void insert( UserInterfaceComponent component){
        ((ViewGroup)this.androidView).addView(component.getAndroidView());
    }

    public static LayoutNativeFactory newLayoutFactory(MainActivity activity, LuaTable options) {

        LayoutNativeFactory layoutFactory = new LayoutNativeFactory(activity, options);

        return layoutFactory;
    }





}
