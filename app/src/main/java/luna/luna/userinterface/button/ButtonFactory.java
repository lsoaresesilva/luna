package luna.luna.userinterface.button;

import ifpe.luajavaproject.MainActivity;
import luna.luna.userinterface.button.ButtonBridge;
import luna.luna.userinterface.button.LuaButtonBridge;


/**
 * Created by macbookair on 26/04/17.
 */

public class ButtonFactory {

    public static ButtonBridge newButton(String language, Object properties, MainActivity context){
        if(language.equals("lua")){
            return LuaButtonBridge.newButtonBridge(properties, context);
        }

        return null;
    }
}
