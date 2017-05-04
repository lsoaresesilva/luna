package luna.luna.userinterface.button;

import android.support.annotation.NonNull;

import org.luaj.vm2.LuaFunction;

import ifpe.luajavaproject.MainActivity;


/**
 * Created by macbookair on 26/04/17.
 */

public abstract class ButtonBridge {

    protected ButtonProxy buttonProxy;

    public ButtonProxy getButtonProxy() {
        return buttonProxy;
    }

    public void setButtonProxy(ButtonProxy buttonProxy) {
        this.buttonProxy = buttonProxy;
    }

    @NonNull
    public static ButtonBridge newButtonBridge(Object properties, MainActivity context){
        throw new UnsupportedOperationException("You must implement this method.");
    }

    @NonNull
    public abstract void setTouchCallback(final Object callBack);




}
