package ifpe.luajavaproject;

import android.content.Context;

import junit.framework.Assert;

import org.junit.Test;
import org.luaj.vm2.LuaString;
import org.luaj.vm2.LuaTable;
import org.luaj.vm2.LuaValue;
import org.mockito.internal.matchers.Null;

import java.util.AbstractCollection;
import java.util.ArrayList;
import java.util.HashMap;

import luna.luna.sqlite.ActiveRecordNative;
import luna.luna.sqlite.DBAdapter;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Created by macbookair on 04/04/17.
 */
public class ActiveRecordNativeTest {

    /*@Test(expected = NullPointerException.class)
    public void testSaveWithNull() throws Exception {
        ActiveRecordNative ac = new ActiveRecordNative();
        boolean res = ac.save(null);
    }

    @Test(expected=IllegalArgumentException.class)
    public void testGetColumnsWithNoColumns() throws Exception {
        ActiveRecordNative ac = new ActiveRecordNative();
        LuaTable tablePropertiesModel = mock(LuaTable.class);
        LuaTable columns = mock(LuaTable.class);

        when(tablePropertiesModel.get("columns")).thenReturn(columns);
        when(columns.length()).thenReturn(0);

        ac.getColumnsDefinitionsFromModel(tablePropertiesModel);

    }

    @Test(expected=IllegalArgumentException.class)
    public void testgetColumnsWithBadColumnsDefinitions() throws Exception {
        ActiveRecordNative ac = new ActiveRecordNative();
        LuaTable tablePropertiesModel = mock(LuaTable.class);
        LuaTable columns = mock(LuaTable.class);
        LuaTable columnDefinition = mock(LuaTable.class);
        when(tablePropertiesModel.get("columns")).thenReturn(columns);
        when(columns.length()).thenReturn(1);
        when(columns.get(0)).thenReturn(columnDefinition);

        ac.getColumnsDefinitionsFromModel(tablePropertiesModel);

    }

    @Test
    public void testGetColumnsDefinitionsFromModel() throws Exception {
        ActiveRecordNative ac = new ActiveRecordNative();
        LuaTable model = mock(LuaTable.class);
        LuaTable columns = mock(LuaTable.class);
        LuaTable columnDefinition = mock(LuaTable.class);
        when(model.get("columns")).thenReturn(columns);
        when(model.get("name")).thenReturn(LuaValue.valueOf("pessoa"));
        when(columns.length()).thenReturn(1);
        when(columns.get(0)).thenReturn(columnDefinition);
        when(columnDefinition.istable()).thenReturn(true);
        when(columnDefinition.get("name")).thenReturn(LuaValue.valueOf("idade"));
        when(columnDefinition.get("type")).thenReturn(LuaValue.valueOf("varChar"));

        ac.getColumnsDefinitionsFromModel(model);
        ArrayList<String> columnsNames = new ArrayList<>();
        columnsNames.add("idade");
        //Assert.assertEquals(columnsNames, ac.get());

    }

    @Test
    public void testBuildModelWithTableNameNull(){
        LuaTable model = mock(LuaTable.class);
        when(model.get("name")).thenReturn(null);
        DBAdapter dataBase = mock(DBAdapter.class);
        ActiveRecordNative activeRecord = ActiveRecordNative.buildModel(model, dataBase);
        Assert.assertNull(activeRecord);
    }

    @Test(expected = NullPointerException.class)
    public void testBuildModelWithNullModel(){
        DBAdapter dataBase = mock(DBAdapter.class);
        ActiveRecordNative activeRecord = ActiveRecordNative.buildModel(null, dataBase);
    }

    @Test(expected = NullPointerException.class)
    public void testBuildModelWithNullDatabase(){
        LuaTable model = mock(LuaTable.class);
        ActiveRecordNative activeRecord = ActiveRecordNative.buildModel(model, null);
    }

    @Test
    public void testBuildModelIntegratedWithDatabase(){

        LuaTable model = mock(LuaTable.class);
        LuaTable columns = mock(LuaTable.class);
        LuaTable columnDefinition = mock(LuaTable.class);
        when(model.get("columns")).thenReturn(columns);
        when(model.get("nome")).thenReturn(LuaValue.valueOf("Leonardo"));
        when(model.get("name")).thenReturn(LuaValue.valueOf("pessoa"));
        when(columns.length()).thenReturn(1);
        when(columns.get(0)).thenReturn(columnDefinition);
        when(columnDefinition.istable()).thenReturn(true);
        when(columnDefinition.get("name")).thenReturn(LuaValue.valueOf("nome"));
        when(columnDefinition.get("type")).thenReturn(LuaValue.valueOf("varchar"));
        when(columnDefinition.get("notNull")).thenReturn(LuaValue.valueOf(true));
        Context context = mock(Context.class);
        DBAdapter dataBase = new DBAdapter(context);
        ActiveRecordNative activeRecord = ActiveRecordNative.buildModel(model, dataBase);
        org.junit.Assert.assertNotNull(activeRecord);
    }

    /*@Test(expected=IllegalArgumentException.class)
    public void testSaveDefinitionsWithNotNullColumnWithNullValue() throws Exception {
        ActiveRecordNative ac = new ActiveRecordNative();
        // Comparar os valores informados para cada coluna informada

        LuaTable model = mock(LuaTable.class);
        LuaTable columns = mock(LuaTable.class);
        LuaTable columnDefinition = mock(LuaTable.class);
        when(model.get("columns")).thenReturn(columns);
        when(model.get("idade")).thenReturn(null);
        when(columns.length()).thenReturn(1);
        when(columns.get(0)).thenReturn(columnDefinition);
        when(columnDefinition.istable()).thenReturn(true);
        when(columnDefinition.get("name")).thenReturn(LuaValue.valueOf("idade"));
        when(columnDefinition.get("type")).thenReturn(LuaValue.valueOf("varChar"));
        when(columnDefinition.get("notNull")).thenReturn(LuaValue.valueOf(true));
        ac.getColumnsDefinitionsFromModel(model);
    }*/

    /*@Test(expected = NullPointerException.class)
    public void testGetColumnsValuesWithModelNull() throws Exception {
        ActiveRecordNative ac = new ActiveRecordNative();
        ac.getColumnsValues(null);
    }

    @Test
    public void testGetColumnsValuesWithEmptyColumnsDefinitions(){
        ActiveRecordNative ac = mock(ActiveRecordNative.class);
        when(ac.getColumnsDefinitions()).thenReturn(null);
        LuaTable model = mock(LuaTable.class);
        ac.getColumnsValues(model);
    }

    //@Test(expected = NullPointerException.class)
    public void testGetColumnsValuesWithEmptyValuesForColumns() throws Exception {
        Context context = mock(Context.class);
        DBAdapter dataBase = new DBAdapter(context);

        LuaTable model = mock(LuaTable.class);
        LuaTable columns = mock(LuaTable.class);
        LuaTable columnDefinition = mock(LuaTable.class);
        when(model.get("columns")).thenReturn(columns);
        when(model.get("nome")).thenReturn(LuaValue.valueOf("Leonardo"));
        when(columns.length()).thenReturn(1);
        when(columns.get(0)).thenReturn(columnDefinition);
        when(columnDefinition.istable()).thenReturn(true);
        when(columnDefinition.get("name")).thenReturn(LuaValue.valueOf("nome"));
        when(columnDefinition.get("type")).thenReturn(LuaValue.valueOf("varchar"));
        when(columnDefinition.get("notNull")).thenReturn(LuaValue.valueOf(true));

        //ActiveRecordNative ac = ActiveRecordNative.buildModel();
    }


    //@Test
    public void testGetColumnsValues() throws Exception {
        ActiveRecordNative ac = new ActiveRecordNative();
        LuaTable model = mock(LuaTable.class);
        LuaTable columns = mock(LuaTable.class);
        LuaTable columnDefinition = mock(LuaTable.class);
        when(model.get("columns")).thenReturn(columns);
        when(model.get("nome")).thenReturn(LuaValue.valueOf("Leonardo"));
        when(columns.length()).thenReturn(1);
        when(columns.get(0)).thenReturn(columnDefinition);
        when(columnDefinition.istable()).thenReturn(true);
        when(columnDefinition.get("name")).thenReturn(LuaValue.valueOf("nome"));
        when(columnDefinition.get("type")).thenReturn(LuaValue.valueOf("varchar"));
        when(columnDefinition.get("notNull")).thenReturn(LuaValue.valueOf(true));
        HashMap<String, Object> columnsValues = ac.getColumnsValues(model);
        HashMap<String, Object> expectedColumnsValues = new HashMap<>();
        expectedColumnsValues.put("nome", "Leonardo");
        Assert.assertEquals(columnsValues, expectedColumnsValues);
    }*/







}