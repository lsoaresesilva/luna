package luna.luna.userinterface;

import android.view.View;

/**
 * Created by macbookair on 30/03/17.
 */
public class UserInterfaceComponent {

    protected View androidView;

    public View getAndroidView() {
        return androidView;
    }

    public void setAndroidView(View androidView)
    {
        this.androidView = androidView;
    }
}
