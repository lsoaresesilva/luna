package luna.luna.userinterface;

import android.app.Activity;
import android.widget.EditText;
import android.widget.LinearLayout;

import org.luaj.vm2.LuaError;
import org.luaj.vm2.LuaValue;

import ifpe.luajavaproject.R;

/**
 * Created by macbookair on 27/03/17.
 */
public class TextFieldNativeLayer extends UserInterfaceComponent{

    public static TextFieldNativeLayer newTextField(Activity context, LuaValue properties){
        EditText textInputCreated = new EditText(context);
        if( properties != null ) {
            try {

                LuaValue identifier = properties.get("id");
                LuaValue width = properties.get("width");
                LuaValue height = properties.get("height");
                LuaValue text = properties.get("text");
                if (identifier != null && identifier.isint()) {
                    textInputCreated.setId(identifier.toint());

                }

                if (text != null && text.isstring()) {
                    textInputCreated.setText(text.toString());
                }


            } catch (LuaError luaError) {

            }
        }

        return new TextFieldNativeLayer(textInputCreated);
    }

    private TextFieldNativeLayer(EditText androidView){
        this.androidView = androidView;
    }

    public String getText(){
        return ((EditText)this.androidView).getText().toString();
    }
}
