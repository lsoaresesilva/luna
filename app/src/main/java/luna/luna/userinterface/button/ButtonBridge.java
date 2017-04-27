package luna.luna.userinterface.button;

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

    public static ButtonBridge newButtonBridge(Object properties, MainActivity context){
        return null;
    }


}
