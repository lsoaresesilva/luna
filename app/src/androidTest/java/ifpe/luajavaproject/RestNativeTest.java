package ifpe.luajavaproject;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.luaj.vm2.LuaTable;
import org.luaj.vm2.LuaValue;

import luna.luna.rest.RestNative;

import static org.junit.Assert.assertEquals;

/**
 * Created by macbookair on 22/04/17.
 */
@RunWith(AndroidJUnit4.class)
public class RestNativeTest {

    private static RestNative restNative;
    @Rule
    public ActivityTestRule<MainActivity> mActivityRule = new ActivityTestRule<>(
            MainActivity.class);

    @BeforeClass
    public static void setUp(){

        restNative = new RestNative();

    }

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