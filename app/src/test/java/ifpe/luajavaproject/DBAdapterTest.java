package ifpe.luajavaproject;

import android.content.Context;

import net.bytebuddy.asm.Advice;

import org.junit.Assert;
import org.junit.Test;
import org.luaj.vm2.LuaString;
import org.luaj.vm2.LuaTable;
import org.luaj.vm2.LuaValue;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.IllegalFormatException;

import luna.luna.sqlite.ActiveRecordNative;
import luna.luna.sqlite.DBAdapter;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Created by macbookair on 05/04/17.
 */

public class DBAdapterTest {

    @Test(expected = IllegalArgumentException.class)
    public void testExecuteSQLWithNullSQL() {
        Context contextMock = mock(Context.class);
        DBAdapter dbAdapter = new DBAdapter(contextMock);
        dbAdapter.executeSQL(null);
    }



    /*@Test(expected = IllegalArgumentException.class)
    public void testSQLGenerateTableSQLWithModelNull() {
        Context contextMock = mock(Context.class);
        DBAdapter dbAdapter = new DBAdapter(contextMock);
        dbAdapter.generateCreateTableSQL(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testSQLGenerateTableSQLWithNullColumns() {
        Context contextMock = mock(Context.class);
        DBAdapter dbAdapter = new DBAdapter(contextMock);
        ActiveRecordNative arn = mock(ActiveRecordNative.class);

        when(arn.getColumnsDefinitions()).thenReturn(null);
        dbAdapter.generateCreateTableSQL(arn);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testSQLGenerateTableSQLMissingColumnInformation() {
        Context contextMock = mock(Context.class);
        DBAdapter dbAdapter = new DBAdapter(contextMock);
        ActiveRecordNative arn = mock(ActiveRecordNative.class);

        ArrayList<HashMap<String, Object>> mockColumnsDefinitions = mock(ArrayList.class);
        when(arn.getColumnsDefinitions()).thenReturn(mockColumnsDefinitions);
        when(arn.getColumnsDefinitions().size()).thenReturn(1);
        HashMap<String, Object> mockColumnDefinition = mock(HashMap.class);
        when(arn.getColumnsDefinitions().get(0)).thenReturn(mockColumnDefinition);
        when(mockColumnDefinition.get("name")).thenReturn("");
        when(mockColumnDefinition.get("type")).thenReturn(null);

        dbAdapter.generateCreateTableSQL(arn);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testSQLGenerateCreateTableUnitWithTableNameNull() {

        Context contextMock = mock(Context.class);
        DBAdapter db = new DBAdapter(contextMock);

        ActiveRecordNative ac = mock(ActiveRecordNative.class);
        when(ac.getTableName()).thenReturn(null);
        ArrayList<HashMap<String, Object>> mockColumnsDefinitions = mock(ArrayList.class);
        when(ac.getColumnsDefinitions()).thenReturn(mockColumnsDefinitions);
        when(ac.getColumnsDefinitions().size()).thenReturn(1);
        db.generateCreateTableSQL(ac);

    }

    @Test
    public void testSQLGenerateCreateTableSQLIntegratedWithActiveRecord() {

        Context contextMock = mock(Context.class);
        DBAdapter db = new DBAdapter(contextMock);

        LuaTable model = mock(LuaTable.class);
        LuaTable columns = mock(LuaTable.class);
        LuaTable columnDefinition = mock(LuaTable.class);
        when(model.get("columns")).thenReturn(columns);
        when(model.get("name")).thenReturn(LuaValue.valueOf("pessoa"));

        when(model.get("idade")).thenReturn(null);
        when(columns.length()).thenReturn(1);
        when(columns.get(0)).thenReturn(columnDefinition);
        when(columnDefinition.istable()).thenReturn(true);
        when(columnDefinition.get("name")).thenReturn(LuaValue.valueOf("nome"));
        when(columnDefinition.get("type")).thenReturn(LuaValue.valueOf("varchar"));
        when(columnDefinition.get("maxLength")).thenReturn(LuaValue.valueOf(255));
        when(columnDefinition.get("notNull")).thenReturn(LuaValue.valueOf(true));

        ActiveRecordNative arn = ActiveRecordNative.buildModel(model, db);

        String sqlResult = db.generateCreateTableSQL(arn);
        String sqlExpected = " CREATE TABLE pessoa ( nome varchar (255) NOT NULL );";
        Assert.assertEquals(sqlExpected, sqlResult);
    }

    @Test(expected = NullPointerException.class)
    public void testSQLCreateTableSQLContextNull() {
        DBAdapter db = new DBAdapter(null);
        LuaTable model = mock(LuaTable.class);
        LuaTable columns = mock(LuaTable.class);
        LuaTable columnDefinition = mock(LuaTable.class);
        when(model.get("columns")).thenReturn(columns);
        when(model.get("name")).thenReturn(LuaValue.valueOf("pessoa"));

        when(model.get("idade")).thenReturn(null);
        when(columns.length()).thenReturn(1);
        when(columns.get(0)).thenReturn(columnDefinition);
        when(columnDefinition.istable()).thenReturn(true);
        when(columnDefinition.get("name")).thenReturn(LuaValue.valueOf("nome"));
        when(columnDefinition.get("type")).thenReturn(LuaValue.valueOf("varchar"));
        when(columnDefinition.get("maxLength")).thenReturn(LuaValue.valueOf(255));
        when(columnDefinition.get("notNull")).thenReturn(LuaValue.valueOf(true));

        ActiveRecordNative acr = ActiveRecordNative.buildModel(model, db);

        db.createTable(acr);
    }*/



}
