package.path = package.path..";../?.lua"
package.path = package.path..";../../external/?.lua"
package.path = package.path..";../../../models/?.lua"
package.path = package.path..";../../util/?.lua"

require("model")
local Casa = require("Casa")

describe("Instantiating a model", function()
    
      it("Should fail, because there is no column definitions", function()
          assert.has.errors(function() 
              Model:init()
              end)
      end)
    
    it("Should fail, because there is no model", function()
          assert.has.errors(function() 
              Model:init({})
              end)
      end)
    
    it("Should fail, because model does not contains columns definitions", function()
          assert.has.errors(function() 
              Model:init({}, {})
              end)
      end)
    
     it("Should create a column structure for this model", function()
          local leonardo = Casa({idade=30})
          local result = leonardo.columns
          assert.are.equals("table", type(result))
--          assert.are.equals("table", type(result.telefone))
      end)

end)

describe("Returning instances from database", function()

    it("should return a table with tables which contains columns values", function()

        end)

end)