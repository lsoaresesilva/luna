--
-- Created by IntelliJ IDEA.
-- User: macbookair
-- Date: 04/04/17
-- Time: 21:22
-- To change this template use File | Settings | File Templates.
--

DB = {new=true, type="sqlite3"}

local class = require("lib.external.30log")
local Table = require("lib.external.4daysorm.model")
local fields = require("lib.external.4daysorm.tools.fields")
local activeRecord = class("ActiveRecord")



function activeRecord:init(columns)
    print("init")
    local tableName = self.name

    local modelColumnsAndDefinitions = {}
    modelColumnsAndDefinitions.__tablename__ = tableName

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

        modelColumnsAndDefinitions[columnName] = column

        -- pegar o nome da coluna , adicionar como chave valor de columnsdefinitions
    end
    local sql = nil
    local table = nil
    self.table, sql = Table(modelColumnsAndDefinitions)
    self.columns = modelColumnsAndDefinitions

    local result = DBAdapter:executeSQL(sql)

    -- execute SQL

end

function activeRecord:save()
    local columnsValues = {}
    for key,value in pairs(self.columns) do
        if self[key] ~= nil then
          columnsValues[key] = self[key]
        end
    end
    local pessoa = self.table(columnsValues)
    pessoa.nome = "Leonardo"
    local sqlSave = pessoa:save()
    local result = DBAdapter:executeSQL(sqlSave)
    local opa = sqlSave
end

return activeRecord