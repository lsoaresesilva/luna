package userinterface;

import android.app.Activity;
import android.content.Context;
import android.widget.Button;
import android.widget.LinearLayout;

import org.luaj.vm2.LuaError;
import org.luaj.vm2.LuaValue;

import ifpe.luajavaproject.R;

/**
 * This class is a layer between lua button and native buttons. Its a abstraction between the real implementation
 * In iOs there must be the same properties and methods
 * Created by macbookair on 27/03/17.
 */
public class ButtonAndroidLayer {

    private Button _target; // this should not be called directly

    public static ButtonAndroidLayer newButtonFactory(Activity context, LuaValue properties){
        Button buttonCreated = new Button(context);
        try{
            LuaValue identifier = properties.get("id");
            LuaValue width = properties.get("width");
            LuaValue height = properties.get("height");
            LuaValue text = properties.get("text");
            if (identifier.isint() && identifier != null) {
                buttonCreated.setId(identifier.toint());

            }

            if(text.isstring() && text != null){
                buttonCreated.setText(text.toString());
            }


            if(width.isint() && width != null ){

            }

            if(height.isint() && height != null){

            }
        }catch(LuaError luaError){

        }


        LinearLayout l = (LinearLayout)context.findViewById(R.id.linha);
        l.addView(buttonCreated);

        return new ButtonAndroidLayer(buttonCreated);
    }

    private ButtonAndroidLayer(Button _target) {
        this._target = _target;

    }

    public void setIdentifier(int id){
            _target.setId(id);
    }

    public void setText(String text){
        _target.setText(text);
    }
}
