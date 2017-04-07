package ifpe.luajavaproject;

import android.widget.Button;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.luaj.vm2.LuaString;
import org.luaj.vm2.LuaTable;
import org.luaj.vm2.LuaValue;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import luna.luna.userinterface.ButtonNativeFactory;
import luna.luna.userinterface.NativeInterface;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Created by macbookair on 04/04/17.
 */
@RunWith(MockitoJUnitRunner.class)
public class ButtonNativeTest {


    @Mock
    MainActivity mActivity;

    @Test(expected=IllegalArgumentException.class)
    public void testButtonCreationWithNullParameters(){
        NativeInterface nativeInterface = new NativeInterface(mActivity);
        ButtonNativeFactory btnFactory = nativeInterface.newButton(null);
    }

    @Test
    public void testButtonCreation(){
        NativeInterface nativeInterface = new NativeInterface(mActivity);
        LuaTable tablePropertiesMock = mock(LuaTable.class);
        LuaValue s = LuaString.valueOf("clica");
        when(tablePropertiesMock.get("text")).thenReturn(s);

        ButtonNativeFactory btnFactory = nativeInterface.newButton(tablePropertiesMock);

        Assert.assertThat(btnFactory.getAndroidView(), instanceOf(Button.class));
    }

    @Test(expected=IllegalArgumentException.class)
    public void testButtonCreationWithoutTextProperty(){
        NativeInterface nativeInterface = new NativeInterface(mActivity);
        LuaTable tablePropertiesMock = mock(LuaTable.class);
        ButtonNativeFactory btnFactory = nativeInterface.newButton(tablePropertiesMock);

        Assert.assertThat(btnFactory.getAndroidView(), instanceOf(Button.class));
    }

    @Test(expected=IllegalArgumentException.class)
    public void testButtonCreationPropertyNotATable(){
        NativeInterface nativeInterface = new NativeInterface(mActivity);
        LuaValue itIsNotATable = mock(LuaValue.class);

        ButtonNativeFactory btnFactory = nativeInterface.newButton(itIsNotATable);

        Assert.assertThat(btnFactory.getAndroidView(), instanceOf(Button.class));
    }

}
