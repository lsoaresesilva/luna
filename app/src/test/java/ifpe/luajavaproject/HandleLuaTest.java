package ifpe.luajavaproject;

import android.app.Activity;
import android.widget.Button;
import android.widget.LinearLayout;

import net.bytebuddy.jar.asm.Handle;

import org.junit.Test;

import static org.junit.Assert.*;
import org.luaj.vm2.LuaInteger;
import org.luaj.vm2.LuaTable;
import org.luaj.vm2.LuaValue;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Created by macbookair on 27/03/17.
 */
public class HandleLuaTest {

    //@Test
    public void testButtonCreation(){
        // Informar os parâmetros do bt
        LuaTable lt = mock(LuaTable.class);
        when(lt.get("id")).thenReturn(LuaValue.valueOf(12345));
        // Deve retornar um Button
        Activity mockedActivity = mock(Activity.class);
        HandleLua hl = new HandleLua(mockedActivity);
        when(mockedActivity.findViewById(R.id.linha)).thenReturn(new LinearLayout(mockedActivity));
        Button bdReal = hl.drawButton(lt);
        Button bdExpected = new Button(mockedActivity);
        bdExpected.setId(12345);
        assertEquals(bdExpected.getId(), bdReal.getId());

    }
}
