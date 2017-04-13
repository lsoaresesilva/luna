package ifpe.luajavaproject;

import android.content.Context;
import android.database.sqlite.SQLiteException;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import android.test.mock.MockContext;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.luaj.vm2.LuaString;
import org.luaj.vm2.LuaTable;
import org.luaj.vm2.LuaValue;
import org.mockito.Mock;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

import luna.luna.sqlite.ActiveRecordNative;
import luna.luna.sqlite.DBAdapter;
import luna.luna.util.ArrayListNative;
import luna.luna.util.DictionaryNative;

import static android.support.test.InstrumentationRegistry.getTargetContext;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Created by macbookair on 05/04/17.
 */

@RunWith(AndroidJUnit4.class)
public class DBTests {

    public Context mContext =  InstrumentationRegistry.getContext();

    @Test(expected = SQLiteException.class )
    public void testExecuteSQLWithMalFormedSQL() {
        getTargetContext().deleteDatabase("mydbs");
        DBAdapter dbAdapter = new DBAdapter(getTargetContext());
        String value = "SQL INSERT";

        dbAdapter.executeSQL(value);
    }

    @Test
    public void testExecuteSQL() {
        getTargetContext().deleteDatabase("mydbs");
        DBAdapter dbAdapter = new DBAdapter(getTargetContext());
        String value = "CREATE TABLE IF NOT EXISTS  PESSOA (NOME VARCHAR (255) NOT NULL);";

        Assert.assertTrue(dbAdapter.executeSQL(value));
    }

    @Test(expected = SQLiteException.class )
    public void testQuerySQLWithMalFormedSQL() {
        getTargetContext().deleteDatabase("mydbs");
        DBAdapter dbAdapter = new DBAdapter(getTargetContext());
        String value = "SQL INSERT";

        dbAdapter.querySQL(value);
    }

    @Test(expected=IllegalArgumentException.class)
    public void testQuerySQLWithNullSQL() {
        getTargetContext().deleteDatabase("mydbs");
        DBAdapter dbAdapter = new DBAdapter(getTargetContext());
        dbAdapter.querySQL(null);
    }

    @Test
    public void testQuerySQL() {

        DBAdapter dbAdapter = new DBAdapter(getTargetContext());
        ArrayListNative<DictionaryNative<String, Object>> result = dbAdapter.querySQL("SELECT * FROM PESSOA");

        Assert.assertEquals(1, result.size());
    }

    /*@Test
    public void testCreateTable(){

        DBAdapter db = new DBAdapter(mContext);
        LuaTable model = new LuaTable();
        LuaTable columns = new LuaTable();
        LuaTable columnDefinition = new LuaTable();
        columnDefinition.set("name", LuaValue.valueOf("nome"));
        columnDefinition.set("type", LuaValue.valueOf("varchar"));
        columnDefinition.set("maxLength", LuaValue.valueOf(255));
        columnDefinition.set("notNull", LuaValue.valueOf(true));
        columns.insert(0, columnDefinition);
        model.set("name", "pessoa");
        model.set("columns", columns);


        ActiveRecordNative acr = ActiveRecordNative.buildModel(model, db);

    }

    //@Test(expected= SQLException.class)
    public void testCreateTableWithWrongSQL(){

        String SQL = "create table null (a b c);";

    }*/


}
