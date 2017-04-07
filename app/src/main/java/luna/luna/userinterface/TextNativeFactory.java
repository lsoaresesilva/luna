package luna.luna.userinterface;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.luaj.vm2.Globals;
import org.luaj.vm2.LuaError;
import org.luaj.vm2.LuaFunction;
import org.luaj.vm2.LuaValue;

import ifpe.luajavaproject.MainActivity;

/**
 * This class is a layer between lua button and native buttons. Its a abstraction between the real implementation
 * In iOs there must be the same properties and methods
 * Created by macbookair on 27/03/17.
 */
public class TextNativeFactory extends UserInterfaceComponent{

    private MainActivity _activity;

    public static TextNativeFactory newText(MainActivity context, LuaValue properties){
        TextView textCreated = new TextView(context);
        if(properties != null) {
            try {
                LuaValue identifier = properties.get("id");
                LuaValue text = properties.get("text");
                if (identifier != null && identifier.isint()) {
                    textCreated.setId(identifier.toint());

                }

                if (text != null && text.isstring()) {
                    textCreated.setText(text.toString());
                }


            } catch (LuaError luaError) {

            }
        }else{
            // Throws an exception as there is no text without a text
        }
        return new TextNativeFactory(textCreated, context);
    }

    private TextNativeFactory(TextView androidView, MainActivity activity) {
        this.androidView = androidView;
        this._activity = activity;

    }

    public void setIdentifier(int id){
        androidView.setId(id);
    }


    public MainActivity get_activity() {
        return _activity;
    }

}
