
package.path = package.path .. ';../?.lua'
package.path = package.path .. ';../external/?.lua'
TEST = TRUE
assert(loadfile("../activeRecord.lua"))(true)
local activeRecord = require("activeRecord")

describe("Creating tables", function()
    it("should not proceed without a table name", function()
        local aClass = activeRecord:extend()
        aClass.name = null
        assert.has_error(aClass({}))

    end)

end)