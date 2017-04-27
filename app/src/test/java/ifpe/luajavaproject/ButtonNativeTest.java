package ifpe.luajavaproject;

import android.content.res.AssetManager;
import android.widget.Button;
import android.widget.ImageButton;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.luaj.vm2.LuaString;
import org.luaj.vm2.LuaTable;
import org.luaj.vm2.LuaValue;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;

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

/*
    @Mock
    MainActivity mActivity;

    @Test(expected=IllegalArgumentException.class)
    public void testButtonCreationWithNullParameters(){
        NativeInterface nativeInterface = new NativeInterface(mActivity);
        ButtonNativeFactory btnFactory = nativeInterface.newButton(null);
    }

    @Test(expected=IllegalArgumentException.class)
    public void testImageButtonCreationInvalidImage() throws IOException {

        MainActivity mainActivityMock = mock(MainActivity.class);
        NativeInterface nativeInterfaceBtnImg = new NativeInterface(mainActivityMock);
        AssetManager managerMock = mock(AssetManager.class);
        when(managerMock.open("/img/btn.png")).thenThrow(EOFException.class);

        ButtonNativeFactory btnFactory = nativeInterfaceBtnImg.newButton(null);
    }

    @Test(expected=IllegalArgumentException.class)
    public void testButtonCreationWithoutTextAndImgProperty(){
        NativeInterface nativeInterface = new NativeInterface(mActivity);
        LuaTable tablePropertiesMock = mock(LuaTable.class);
        ButtonNativeFactory btnFactory = nativeInterface.newButton(tablePropertiesMock);

    }

    @Test(expected=IllegalArgumentException.class)
    public void testButtonCreationPropertyNotATable(){
        NativeInterface nativeInterface = new NativeInterface(mActivity);
        LuaValue mockProperties = mock(LuaValue.class);

        ButtonNativeFactory btnFactory = nativeInterface.newButton(mockProperties);
    }

    @Test
    public void testButtonCreation() throws IOException {
        // Test for text  button
        NativeInterface nativeInterface = new NativeInterface(mActivity);
        LuaTable tablePropertiesMock = mock(LuaTable.class);
        when(tablePropertiesMock.get("text")).thenReturn(LuaString.valueOf("clica"));

        ButtonNativeFactory btnFactory = nativeInterface.newButton(tablePropertiesMock);
        Assert.assertThat(btnFactory.getAndroidView(), instanceOf(Button.class));

        // Test for img button
        MainActivity mainActivityMock = mock(MainActivity.class);
        AssetManager managerMock = mock(AssetManager.class);
        when(managerMock.open("/img/btn.png")).thenReturn(new InputStream() {
            @Override
            public int read() throws IOException {
                return 0;
            }
        });
        when(mainActivityMock.getAssets()).thenReturn(managerMock);
        NativeInterface nativeInterfaceBtnImg = new NativeInterface(mainActivityMock);
        LuaTable imgBtnPropertiesMock = mock(LuaTable.class);
        LuaTable imgPropertiesMock = mock(LuaTable.class);
        when(imgPropertiesMock.get("normal")).thenReturn(LuaString.valueOf("btn.png"));
        when(imgBtnPropertiesMock.get("img")).thenReturn(imgPropertiesMock);
        ButtonNativeFactory imgBtnFactory = nativeInterfaceBtnImg.newButton(imgBtnPropertiesMock);
        Assert.assertThat(imgBtnFactory.getAndroidView(), instanceOf(ImageButton.class));
    }

*/

}
