--
-- Created by IntelliJ IDEA.
-- User: macbookair
-- Date: 09/04/17
-- Time: 23:00
-- To change this template use File | Settings | File Templates.
--

-- 
-- Criar uma tabela de colunas, para mapear isto. após isto, estes nomes de variáveis serão usadas apenas como gets e sets associando às colunas.
-- ex: leonardo = Pessoa({nome='leonardo'}) == o nome da classe e do arquivo devem ser exatamente iguais
-- se existir a coluna, https://www.lua.org/pil/13.4.4.html
--[[ Etapas de criação:
-- 1. Definição da estrutura do Model
--- local Pessoa = Model:extend("Pessoa", 
          { 
            columns = {
                nome = {},
                idade = { notNull=true }
          }
      })
-- 2. Criação de uma instância
--- local leonardo = Pessoa({nome="Leonardo", idade=30})
-- 3. Salvar:
--- leonardo:save()
  ]]

-- TODO: não permitir duas foreignkeys para um relacionamento

local class

--[[
-- exemplo:
 - local Casa = Model:extend("Casa", { columns = {
    nome = {},
    idade = { notNull=true }
  },
  hasOne = {
    endereco = { class }
  }
  })
 ]]

--DEBUG = true
local class
if DEBUG == false then
    class = require("lib.external.30log")
    require("lib.orm.create_table")
else
    class = require("30log")
    require("create_table")
end

--[[
-- Represents a single row on database following Active Record pattern.
 - Contains methods to insert and query table.
 -
 ]]
Model = class("Model")

function Model:init(columns, model)

    if columns == nil or type(columns) ~= "table" then
        error("A model cannot be instantiated without column")
    end
    
    if model == nil or type(model) ~= "table" then
        error("Did you missed to pass your model?!")
    end
    
    if model.columns == nil or type(model.columns) ~= "table" then
        error("Did you missed to create your model columns definitions?")
    end
    -- percorrer a tabela
    
    -- save columns values in model.
    for columnName, value in pairs(columns) do
        if columnName ~= nil then              
              model[columnName] = value
        end
    end
    
    self:_createTableSQL(model)

end

function Model:findAll()
    local generatedSql = Table:_generateSQLSelect(self)
    --local generatedSql = "SELECT t1.rowid as Casa_rowid, t1.nome as Casa_nome,t1.idade as Casa_idade, t2.sobrenome as Pessoa_sobrenome, t2.cpf as Pessoa_cpf FROM Casa as t1 LEFT OUTER JOIN Pessoa as t2 ON t1.pessoa_id = t2.rowid"
    local rawResults = DBAdapter:querySQL(generatedSql)
    local models = self:buildModels(rawResults)
    return models
end

function Model:buildModels( rawNativeSQLResult )
    local models = {}

    for i=0, rawNativeSQLResult:count()-1 do -- starts in 0 as it is a Java/Objective-c array

        local columnValueDictionary = rawNativeSQLResult:get(i)
        local model = {}
        local belongedModels = {}

        if self.belongsTo ~= nil then
            for foreignKey, foreignTable in pairs(self.belongsTo) do

                belongedModels[foreignKey] = {}
            end
        end

        local columns = columnValueDictionary:getAllKeys()

        -- verificar a quem pertence a coluna: pode ser a própria classe:
        -- Casa_
        -- pode ser a um de seus belongs, ter esta lista pronta


        for j=0, columns:count()-1 do -- starts in 0 as it is a Java/Objective-c array

            local columnNameWithAlias = columns:get(j)
            local columnNameWithoutAlias = string.gsub(columnNameWithAlias, "^(.*)_", "")
            local alias = string.gsub(columnNameWithAlias, "_(.*)", "")
            local columnValue = columnValueDictionary:get(columnNameWithAlias)

            --columnNames  follows this pattern: Classname_column, lets get only column.
            --local columnNamedUp = columnName:gsub(self.name.."_", "")
            if alias == model.name then
                model[columnNameWithoutAlias] = columnValue
            else
                if self.belongsTo ~= nil and self.belongsTo[alias] ~= nil then

                    belongedModels[alias][columnNameWithoutAlias] = columnValue
                end
            end

        end



        for foreignKeyName, v in pairs(belongedModels) do

            if self.belongsTo ~= nil and self.belongsTo[foreignKeyName] ~= nil then

                local belongedClass = require("models."..self.belongsTo[foreignKeyName])
                local belongedModel = belongedClass(belongedModels[foreignKeyName])

                model[foreignKeyName] = belongedModel

            end
        end

        local myClass = require("models."..self.name)
        local myModel = myClass(model)
        table.insert(models, myModel)
    end

    return models
end

function Model:update()
    local generatedSql = Table:_generateSQLUpdate(self)
    local result = DBAdapter:executeSQL(generatedSql)
    if result then
        return true
    end

    return false
end

function Model:delete()
    local generatedSql = Table:_generateSQLDelete(self)
    local result = DBAdapter:executeSQL(generatedSql)
    if result then
        self.rowid = nil
        return true
    end

    return false
end

function Model:save()
    local generatedSqlSave = Table:_generateSQLInsert(self)
    local primaryKey = DBAdapter:save(generatedSqlSave)

    if primaryKey ~= nil then
        self.rowid = primaryKey
        return true
    end

    return false
end

-- Creates a table in SQLite based on this model columns definitions
function Model:_createTableSQL(model)
    local result
    if model.isTableCreated == nil or model.isTableCreated == false then

      if model.columns == nil and model.name == nil then
          error( "You should not call this method directly.")
      end
    
    
      local sql = Table:_generateSQL(model)


      if DEBUG == false then
        result = DBAdapter:executeSQL(sql)
        -- should not create a table everytime a new instance is created
        local myClass = require("models."..model.name)
        myClass.isTableCreated = true
      else
        result = true
      end


    end

    return result
end
  
  
  
  

return Model