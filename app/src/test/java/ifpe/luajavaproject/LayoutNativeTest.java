package ifpe.luajavaproject;

import android.widget.Button;
import android.widget.LinearLayout;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.luaj.vm2.LuaString;
import org.luaj.vm2.LuaTable;
import org.luaj.vm2.LuaValue;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import luna.luna.userinterface.LayoutNativeFactory;
import luna.luna.userinterface.NativeInterface;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Created by macbookair on 04/04/17.
 */

@RunWith(MockitoJUnitRunner.class)
public class LayoutNativeTest {

    @Mock
    MainActivity mActivity;

    @Test
    public void testLayoutCreationWithNoProperties(){
        NativeInterface nativeInterface = new NativeInterface(mActivity);
        LayoutNativeFactory ltFactory = nativeInterface.newLayout(null);

        Assert.assertThat(ltFactory.getAndroidView(), instanceOf(LinearLayout.class));
    }


}
