--
-- Created by IntelliJ IDEA.
-- User: macbookair
-- Date: 04/04/17
-- Time: 21:22
-- To change this template use File | Settings | File Templates.
--
--[[
-- Implementation of ActiveRecord Pattern.
 - This is the base class for all models.
 -
 ]]
package.path = package.path..";../models/?.lua"
DB = {new=true, type="sqlite3", DB=true, backtrace=true }

local class = require("lib.external.30log")
local Table = require("lib.external.4daysorm.model")
local fields = require("lib.external.4daysorm.tools.fields")

local ActiveRecord = class("ActiveRecord")

local isTableCreated = false -- used to confirm that sql creation of tables will be only executed once.

function ActiveRecord:createTable(columns)
    if isTableCreated == false then
        print("vamos criar")
        local tableName = self.name
        self.schema = {}

        self.schema.__tablename__ = tableName
        if type(columns) == "table" then
            for i=1, #columns do
                local columnDefinition = columns[i]

                local columnName = columnDefinition.name
                local column = nil
                local max_length = columnDefinition.maxLength or nil
                local unique = columnDefinition.unique or false
                local primary_key = columnDefinition.primaryKey or false
                local null = columnDefinition.null or false
                local default = columnDefinition.default or nil
                local columnDefinitions =
                {
                    max_length=self.max_length,
                    unique = self.unique,
                    primary_key = self.primary_key,
                    null = self.null,
                    default = self.default
                }

                if columnDefinition.type == "varchar" then
                    column = fields.CharField(columnDefinitions)
                elseif columnDefinition.type == "primaryKey" then
                    column = fields.PrimaryField(columnDefinitions)
                elseif columnDefinition.type == "integer" then
                    column = fields.IntegerField(columnDefinitions)
                elseif columnDefinition.type == "text" then
                    column = fields.TextField(columnDefinitions)
                elseif columnDefinition.type == "bool" then
                    column = fields.BooleandField(columnDefinitions)
                elseif columnDefinition.type == "dateTime" then
                    column = fields.DateTimeField(columnDefinitions)
                elseif columnDefinition.type == "foreignKey" then
                    column = fields.ForeignKey(columnDefinitions)
                end
                -- adicionar column a columnsdefitions

                self.schema[columnName] = column

                -- pegar o nome da coluna , adicionar como chave valor de columnsdefinitions
            end
        end
        local sql = nil
        local table = nil
        self.table, sql = Table(self.schema)
        print("sql")
        print(sql)
        --self.columns = modelColumnsAndDefinitions

        --local result = DBAdapter:executeSQL(sql)
        -- verificar se result foi ok , se foi
        isTableCreated = true
        -- execute SQL
    end
end

function ActiveRecord:init(columnsValues)


    if columnsValues ~= nil and type(columnsValues) == "table" then
        for key,value in pairs(columnsValues) do
            print("ch")
        print(key)
            print("vl")
            print(value)
            self[key] = value
        end

        --self.instance = self.table(columnsValues)
    end


end


function ActiveRecord:buildModelStructureFromSQL( rawNativeSQLResult )
    local models = {}
    for i=0, rawNativeSQLResult:count()-1 do -- starts in 0 as it is a Java/Objective-c array

        local columnValueDictionary = rawNativeSQLResult:get(i)
        local model = {}
        local columns = columnValueDictionary:getAllKeys()
        for j=0, columns:count()-1 do -- starts in 0 as it is a Java/Objective-c array
            print(j)
            local columnName = columns:get(j)
            --columnNames  follows this pattern: Classname_column, lets get only column.
            local columnNamedUp = columnName:gsub(self.name.."_", "")

            model[columnNamedUp] = columnValueDictionary:get(columnName)
        end

        table.insert(models, model)
    end

    return models
end

function ActiveRecord:buildActiveRecordInstanceFromModelStructure(rawModels)
    local activeRecords = {}

    -- Lets create instances and table rows for each result from SQL
    local myClass = require("models."..self.name)
    for i=1, #rawModels do

        local myActiveRecord = myClass(rawModels[i])

        myActiveRecord.table = self.table
        myActiveRecord.schema = self.schema
        table.insert(activeRecords, myActiveRecord)
    end

    return activeRecords
end

-- TODO: colocar isto no construtor para ser usado apenas uma vez
-- Creates a Table Row instance based on self schema
function ActiveRecord:_generateTableRow()
    local columnsValues = {}
    for k,v in pairs(self.schema) do

        if self[k] ~= nil then
            columnsValues[k] = self[k]
        end
    end

    if self.id ~= nil then
        columnsValues.id = self.id
    end

    local myModel = self.table(columnsValues)
    return myModel
end

function ActiveRecord:where(whereSQL)
    self.activeSelect = self.table.get:where({whereSQL})
    return self
end

function ActiveRecord:orderBy(orderyBySQL)
    self.activeOrder = self.table.get:order_by({orderyBySQL})
    return self
end

function ActiveRecord:delete()
    local generatedSql
    if self.activeSelect == nil then
        local myModel = self:_generateTableRow()
        generatedSql = myModel:delete()

    else
        generatedSql = self.activeSelect:delete()
    end

    local result = DBAdapter:executeSQL(generatedSql)
    if result == true then
    end

end

function ActiveRecord:update(columnsValues)
    local generatedSql
    if self.activeSelect == nil then
        local myModel = self:_generateTableRow()
        generatedSql = myModel:update(columnsValues)

    else
        generatedSql = self.activeSelect:update(columnsValues)
    end

    local result = DBAdapter:executeSQL(generatedSql)
    if result == true then
    end
    --self.activeSelect = self.table.get:where({whereSQL})
    return self
end


function ActiveRecord:save()

    local myModel = self:_generateTableRow()
    local generatedSqlSave = myModel:save()
    print(generatedSqlSave)
    local primaryKey = DBAdapter:save(generatedSqlSave)
    print(primaryKey)
    if primaryKey ~= nil then
        self.id = primaryKey
    end
end

function ActiveRecord:first()
    local generatedSQL
    -- if there is an activeSelect (where condition) then we gonna get based on this select

    if self.activeSelect == nil then
        generatedSQL= self.table.get:first()
    else
        generatedSQL= self.activeSelect:first()
        self.activeSelect = nil
    end

    local sqlRawResults = DBAdapter:querySQL(generatedSQL)

    local rawModels = self:buildModelStructureFromSQL(sqlRawResults)
    local activeRecords = self:buildActiveRecordInstanceFromModelStructure(rawModels)
    if type(activeRecords) == "table" and #activeRecords >= 1 then
        return activeRecords[1]
    else
        return nil
    end
end

function ActiveRecord:all()
    local sqlQueryAll
    -- if there is an activeSelect (where condition) then we gonna get based on this select
    if self.activeSelect == nil and self.activeOrder == nil  then
        sqlQueryAll= self.table.get:all()
    else

        local myTable = self:_mixWhereOrderGroupbyHaving()

        sqlQueryAll= myTable:all()

        self.activeSelect = nil
        self.activeOrder = nil
    end

    --self.table.get:_generate({{id=1, nome="Leonardo"}, {id=2, nome="Leonardo"}})
    local sqlRawResults = DBAdapter:querySQL(sqlQueryAll)

    local rawModels = self:buildModelStructureFromSQL(sqlRawResults)
    local activeRecords = self:buildActiveRecordInstanceFromModelStructure(rawModels)


    return activeRecords
end

function ActiveRecord:_mixWhereOrderGroupbyHaving()
    local myTable

    if self.activeSelect ~= nil then
        if myTable == nil then
            myTable = self.activeSelect
        else
            myTable._rules.where = self.activeSelect._rules.where
        end
    end

    if self.activeOrder ~= nil then
        if myTable == nil then
            myTable = self.activeOrder
        else
            myTable._rules.order = self.activeOrder._rules.order
        end
    end

    return myTable
end

return ActiveRecord