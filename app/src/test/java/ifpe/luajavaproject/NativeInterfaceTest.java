package ifpe.luajavaproject;

import android.app.Activity;
import android.widget.Button;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.luaj.vm2.LuaString;
import org.luaj.vm2.LuaTable;
import org.luaj.vm2.LuaValue;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import luna.luna.userinterface.ButtonNativeFactory;
import luna.luna.userinterface.NativeInterface;

/**
 * Created by macbookair on 03/04/17.
 */


@RunWith(MockitoJUnitRunner.class)
public class NativeInterfaceTest {

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


        ButtonNativeFactory btnFactory = nativeInterface.newButton(tablePropertiesMock);
        Assert.assertNotNull(btnFactory);

        Assert.assertThat(btnFactory, instanceOf(ButtonNativeFactory.class));


        //Assert.assertThat(IsInstanceOf.instanceOf(Button.class), btnFactory , btnFactory.getAndroidView() );
    }
}
