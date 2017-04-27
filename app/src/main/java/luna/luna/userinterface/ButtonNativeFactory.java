package luna.luna.userinterface;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import org.luaj.vm2.Globals;
import org.luaj.vm2.LuaError;
import org.luaj.vm2.LuaFunction;
import org.luaj.vm2.LuaNil;
import org.luaj.vm2.LuaTable;
import org.luaj.vm2.LuaValue;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import ifpe.luajavaproject.MainActivity;

/**
 * This class is a abstraction to represent a native Button. It does not provide direct access to native button, instead encapsulates it providing access to only "allowed" methods.
 * Methods in this class share common signature between Android and iOS.
 * In Luna Lua API access to this class is made through lib.userinterface.button library.
 * For Lua it is not know which implementation will be called, as soon as the Native implementation provides these signatures.
 * Created by Leonardo Soares e Silva on 27/03/17.
 */
public class ButtonNativeFactory extends UserInterfaceComponent{

    private MainActivity _activity;

    /**
     * Creates a instance of ButtonNativeFactory.
     * Internally it creates an instance of native button configured with properties, which is a LuaTable, passed to this method as parameter.
     * A button can be represented as text only or with image. The properties passed as parameter must include a text or img key.
     * The former is the text to be displayed in the button, img is a LuaTable with two keys: normal and pressed, values for these keys are the name of the image to be used when for a normal and pressed state button. This image must be located inside assets/img folder.
     * @param context An instance of Context.
     * @param properties An instance of LuaTable with
     * @return
     */
    public static ButtonNativeFactory newButtonFactory(MainActivity context, LuaValue properties){

        if( properties != null && properties instanceof LuaTable && ((LuaTable)properties).length() > 0) {
            try {
                LuaValue identifier = properties.get("id");
                LuaValue width = properties.get("width");
                LuaValue height = properties.get("height");
                LuaValue text = properties.get("text");
                LuaTable imgSrc = (LuaTable) properties.get("img");

                View buttonCreated;

                if(imgSrc != null && !(imgSrc.get("normal") instanceof LuaNil) ){

                    buttonCreated = new ImageButton(context);
                    try{
                        InputStream imgFileNormal = context.getAssets().open("img/"+imgSrc.get("normal"));
                        InputStream imgFilePressed = null;
                        if( !(imgSrc.get("pressed") instanceof LuaNil) ){
                            imgFilePressed = context.getAssets().open("img/"+imgSrc.get("pressed"));
                        }
                        Bitmap bitmapNormal = BitmapFactory.decodeStream(imgFileNormal);
                        Bitmap bitmapPressed = BitmapFactory.decodeStream(imgFilePressed);
                        ((ImageButton)buttonCreated).setImageBitmap(bitmapNormal);
                    }catch(IOException e){
                        throw new IllegalArgumentException("Image for button not found.");
                    }
                }
                else{
                    buttonCreated = new Button(context);
                    if (text != null && text.isstring()) {
                        ((Button)buttonCreated).setText(text.toString());
                    }else{
                        throw new IllegalArgumentException("Missing properties 'text' for button creation.");
                    }
                }

                if (identifier != null && identifier.isint() ) {
                    buttonCreated.setId(identifier.toint());
                }

                return new ButtonNativeFactory(buttonCreated, context);
            } catch (LuaError luaError) {
                    return null;
            }
        }else{
            // there must be at least a text property
            throw new IllegalArgumentException("Missing properties for button creation.");
        }
    }

    private ButtonNativeFactory(View _target, MainActivity activity) {
        this.androidView = _target;
        this._activity = activity;

    }

    public void setIdentifier(int id){
        androidView.setId(id);
    }


    public MainActivity get_activity() {
        return _activity;
    }

    public void setTouchCallback(final LuaFunction callBackName){
        androidView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Globals globals = get_activity().getGlobals();
                try{
                    if ( callBackName.checkfunction() != null ){
                        callBackName.invoke();
                    }
                }catch(LuaError le){
                    System.out.println(le.getMessage());
                }

            }
        });
    }
}
