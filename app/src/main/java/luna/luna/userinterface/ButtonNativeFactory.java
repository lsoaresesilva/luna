package luna.luna.userinterface;

import android.view.View;
import android.widget.Button;

import org.luaj.vm2.Globals;
import org.luaj.vm2.LuaError;
import org.luaj.vm2.LuaFunction;
import org.luaj.vm2.LuaTable;
import org.luaj.vm2.LuaValue;

import ifpe.luajavaproject.MainActivity;

/**
 * This class is a layer between lua button and native buttons. Its a abstraction between the real implementation
 * In iOs there must be the same properties and methods
 * Created by macbookair on 27/03/17.
 */
public class ButtonNativeFactory extends UserInterfaceComponent{

    private MainActivity _activity;

    public static ButtonNativeFactory newButtonFactory(MainActivity context, LuaValue properties){
        Button buttonCreated = new Button(context);

        if( properties != null && properties instanceof LuaTable) {
            try {
                LuaValue identifier = properties.get("id");
                LuaValue width = properties.get("width");
                LuaValue height = properties.get("height");
                LuaValue text = properties.get("text");

                if (text != null && text.isstring()) {
                    buttonCreated.setText(text.toString());
                }else{
                    throw new IllegalArgumentException("Cannot create a button without 'text' property");
                }

                if (identifier != null && identifier.isint() ) {
                    buttonCreated.setId(identifier.toint());

                }




            } catch (LuaError luaError) {

            }
        }else{
            // there must be at least a text property
            throw new IllegalArgumentException("Cannot create a button without a table with properties");
        }

        //LinearLayout l = (LinearLayout)context.findViewById(R.id.main_layout);
        //l.addView(buttonCreated);


        return new ButtonNativeFactory(buttonCreated, context);
    }

    private ButtonNativeFactory(Button _target, MainActivity activity) {
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
