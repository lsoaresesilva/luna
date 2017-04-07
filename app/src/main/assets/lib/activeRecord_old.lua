--
-- Created by IntelliJ IDEA.
-- User: macbookair
-- Date: 04/04/17
-- Time: 21:22
-- To change this template use File | Settings | File Templates.
--

local class = require("lib.external.30log")
local activeRecord = class("ActiveRecord")

function activeRecord:init(columns)
    if options == nil then
        error("You must specify columns properties.")
    end

    -- parameters:
        -- name
        -- type (varchar, int, datetime)
        -- maxLength
        -- notNull
        -- pKey

    -- default values:
        -- maxLength
        -- notNull
        -- pKey
    local defaultMaxLength = 255
    local defaultNotNull = false
    local defaultPKey = false

    self.columns = {}

       for i=1, #columns do
        local property = columns[i]
        local attribute = property.name
        self[attribute] = nil

        if property.name == nil or property.type == nil then
            error("You must specify name and type for columns.")
        end

        property.maxLength = property.maxLength or defaultMaxLength
        property.notNull = property.notNull or defaultNotNull
        --property.pKey = property.pKey or defaultPKey

        table.insert(self.columns, {
            name=property.name,
            type=property.type,
            maxLength=property.maxLength,
            notNull=property.notNull,
            --pKey=property.pKey
        })
    end

    -- verificar se foi adicionado corretamente, se não foi então dar erro

    self._nativeObject = ActiveRecord:buildModel(self.columns)

    if self._nativeObject ~= nil then
        error("There was a error creating your model: could not create table.")
    end


end


function activeRecord:save()
    print("salvando")
    return ActiveRecord:save(self)
end

return activeRecord