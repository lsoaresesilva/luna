package luna.luna.userinterface.button;

import android.support.annotation.NonNull;

import org.luaj.vm2.LuaFunction;
import org.luaj.vm2.LuaTable;

import java.util.HashMap;

import ifpe.luajavaproject.MainActivity;
import luna.luna.syntax.data.LuaHashMapAdapter;
import luna.luna.syntax.data.LunaHashMapAdapter;
import luna.luna.syntax.function.LunaFunctionAdapter;
import luna.luna.syntax.function.LuaFunctionAdapter;
import luna.luna.util.lua.LuaUtil;


/**
 * Created by macbookair on 26/04/17.
 */

public class LuaButtonBridge extends ButtonBridge{


    public static ButtonBridge newButtonBridge(Object properties, MainActivity context) {
        if(context != null && properties != null &&
            (properties instanceof LuaTable)){

            LuaTable buttonProperties = (LuaTable)properties;
            LunaHashMapAdapter luaPropertiesAdaptee = new LuaHashMapAdapter();
            luaPropertiesAdaptee.create(buttonProperties);

            //HashMap javaButtonProperties = LuaUtil.tableToHashMap(buttonProperties);
            if (luaPropertiesAdaptee.size() > 0) {
                ButtonBridge newButtonBridge = new LuaButtonBridge();
                newButtonBridge.buttonProxy = ButtonProxy.newButtonProxy(luaPropertiesAdaptee, context);

                return newButtonBridge;
            }

        }


        return null;
    }

    @NonNull
    @Override
    public void setTouchCallback(Object callBack) {
        if(callBack != null && callBack instanceof LuaFunction){
                LunaFunctionAdapter luaFunctionAdapter = new LuaFunctionAdapter();
                luaFunctionAdapter.create(callBack);
                this.getButtonProxy().setTouchCallback(luaFunctionAdapter);

        }else{
            throw new IllegalArgumentException("A callback is required.");
        }
    }
}
