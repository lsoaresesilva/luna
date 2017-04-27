package luna.luna.userinterface.button;

import org.luaj.vm2.LuaTable;

import java.util.HashMap;

import ifpe.luajavaproject.MainActivity;
import luna.luna.util.lua.LuaUtil;


/**
 * Created by macbookair on 26/04/17.
 */

public class LuaButtonBridge extends ButtonBridge{


    public static ButtonBridge newButtonBridge(Object properties, MainActivity context) {
        if(context != null && properties != null &&
            (properties instanceof LuaTable)){

            LuaTable buttonProperties = (LuaTable)properties;
            HashMap javaButtonProperties = LuaUtil.tableToHashMap(buttonProperties);
            if (javaButtonProperties.size() > 0) {
                ButtonBridge newButtonBridge = new LuaButtonBridge();
                newButtonBridge.buttonProxy = ButtonProxy.newButtonProxy(javaButtonProperties, context);

                return newButtonBridge;
            }

        }


        return null;
    }
}
