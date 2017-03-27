package ifpe.luajavaproject;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.graphics.Color;
import android.widget.Button;
import android.widget.LinearLayout;

import org.luaj.vm2.LuaError;
import org.luaj.vm2.LuaValue;

import userinterface.ButtonAndroidLayer;

/**
 * Created by macbookair on 26/03/17.
 *
 * TODO:
 *      * Permitir o desenho de botões (10%)
 *      * Permitir o desenho de campos de texto
 *      * Permitir o desenho de um date picker
 *      * Permitir o desenho de um Radio
 *      * Permitir o desenho de um Checkbox
 *      * Permitir grids
 *      * Permitir menu
 */
public class HandleLua {

    private Activity activity;

    public HandleLua(Activity activity){
        this.activity = activity;
    }

    public void createActivity(){
        FragmentManager fragmentManager = activity.getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        Fragment a = new Fragment();

        LinearLayout linearLayout = new LinearLayout(activity);
        linearLayout.setBackgroundColor(Color.BLACK);
        linearLayout.setPadding(5, 5, 5, 5);
        linearLayout.setOrientation(LinearLayout.VERTICAL);

        fragmentTransaction.add(a, "tag");
        //a.setContentView(linearLayout);
    }

    public void testando(String text){
        Button b = (Button)this.activity.findViewById(123);
        //Button c = b;

    }

    public ButtonAndroidLayer drawButton(LuaValue properties){


        //ButtonAndroidLayer buttonAndroidLayer = new ButtonAndroidLayer(buttonCreated);

        return ButtonAndroidLayer.newButtonFactory(this.activity, properties);
    }
}


