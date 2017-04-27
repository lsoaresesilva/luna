package ifpe.luajavaproject;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.BeforeClass;
import org.junit.Test;
import org.luaj.vm2.LuaTable;
import org.luaj.vm2.LuaValue;

import java.util.ArrayList;
import java.util.HashMap;

import luna.luna.rest.RestNative;

import static org.junit.Assert.*;

/**
 * Created by macbookair on 24/04/17.
 */
public class RestNativeTest {

    private static RestNative restNative;

    @BeforeClass
    public static void setup(){
        restNative = new RestNative();
    }

    @Test(expected = IllegalArgumentException.class)
    public void testInvalidParameter() {
        restNative.doInBackground(null);
        LuaTable invalidTable = new LuaTable();
        restNative.doInBackground(invalidTable);
        invalidTable.set("method", "any");
        restNative.doInBackground(invalidTable);
    }


    @Test(expected =  IllegalArgumentException.class)
    public void testInvalidInputTableToJson() {
        restNative.tableToJson(null);

    }

    @Test
    public void testTableToJson() throws JSONException {
        LuaTable luaParams = new LuaTable();

        luaParams.set("nome", LuaValue.valueOf(new String("Leonardo")));
        luaParams.set("job", LuaValue.valueOf(new String("Professor")));
        int length = luaParams.length();

        JSONObject result = restNative.tableToJson(luaParams);

        assertEquals("Leonardo", result.get("nome"));
        assertEquals("Professor", result.get("job"));
    }

}