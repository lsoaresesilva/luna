package.path = package.path..";../?.lua"
package.path = package.path..";../../external/?.lua"
package.path = package.path..";../../util/?.lua"
package.path = package.path..";../../../models/?.lua"


require("create_table")
local Casa = require("Casa")
local Pessoa = require("Pessoa")

describe("Generating SQL for UPDATE", function()

    it("Cannot generate SQL because a model has no name", function ()

        assert.has.errors(function()
            Table:_generateSQLUpdate({})
        end)
    end)

    it("Cannot generate SQL because a model was not passed as parameter", function ()

        assert.has.errors(function()
            Table:_generateSQLUpdate()
        end)
    end)

    it("Should create a update sql string", function ()

        --local casaClass = {name="Casa", rowid=4 }
        local instance = Casa({idade=98, rowid=7, nome="mudou aline"})
        local sql = Table:_generateSQLUpdate(instance)
        assert.are.equals("UPDATE Casa SET nome = 'mudou aline', idade = 98 WHERE rowid = 7", sql)
    end)
end)

describe("Generating SQL for delete", function()

    it("Cannot generate SQL because a model has no name", function ()

        assert.has.errors(function()
            Table:_generateSQLDelete({})
        end)
    end)

    it("Cannot generate SQL because a model was not passed as parameter", function ()

        assert.has.errors(function()
            Table:_generateSQLDelete()
        end)
    end)

    it("Should create a delete sql string", function ()

        local casaClass = {name="Casa", rowid=4}
        local sql = Table:_generateSQLDelete(casaClass)
        assert.are.equals("DELETE FROM Casa WHERE rowid = 4", sql)
    end)
end)

describe("Generating SQL for select", function()

    it("Cannot generate SQL because a model has no name", function ()

        assert.has.errors(function()
            Table:_generateSQLSelect({})
        end)
    end)

    it("Cannot generate SQL because a model was not passed as parameter", function ()

        assert.has.errors(function()
            Table:_generateSQLSelect()
        end)
    end)

    it("Should create a select sql string", function ()

        local casaClass = {name="Casa", columns = {
        nome = {},
        idade = { notNull=true }}}
        local sql = Table:_generateSQLSelect(casaClass)
        assert.are.equals("SELECT t1.rowid as Casa_rowid, t1.nome as Casa_nome,t1.idade as Casa_idade FROM Casa as t1", sql)
    end)

    it("Should create a select sql string with a left join but with null value", function ()

        local casaClass = {name="Casa", belongsTo={pessoa="Pessoa"}, columns = {
            nome = {},
            idade = { notNull=true }}}
        local sql = Table:_generateSQLSelect(casaClass)
        assert.are.equals("SELECT t1.rowid as Casa_rowid, t1.nome as Casa_nome,t1.idade as Casa_idade, t2.sobrenome as Pessoa_sobrenome, t2.cpf as Pessoa_cpf FROM Casa as t1 LEFT OUTER JOIN Pessoa as t2 ON t1.pessoa_id = t2.rowid", sql)
    end)
end)

describe("Generating SQL for Insert", function()

    it("Cannot generate SQL because a model was not passed as parameter", function ()

        assert.has.errors(function()
            Table:_generateSQLInsert()
        end)
    end)

    it("Cannot generate SQL because a model has no name or column", function ()

        assert.has.errors(function()
            Table:_generateSQLInsert({})
        end)
    end)

    it("should create a insert sql string", function ()
        local instance = Pessoa({sobrenome="leonardo"})
        local sql = Table:_generateSQLInsert(instance)
        assert.are.equals("INSERT INTO Pessoa (sobrenome,cpf) VALUES ('leonardo',null)", sql)
    end)

    it("should create a insert sql string with a table that uses join", function ()
        local instancePessoa = Pessoa({sobrenome="leonardo"})
        instancePessoa.rowid = 3
        local instance = Casa({idade=30, nome="Leonardo", pessoa=instancePessoa})
        local sql = Table:_generateSQLInsert(instance)
        assert.are.equals("INSERT INTO Casa (pessoa_id,nome,idade) VALUES (3,'Leonardo',30)", sql)
    end)

    it("should create a insert sql string with a table that uses join but with null foreign key", function ()
        local instance = Casa({idade=30, nome="Leonardo"})
        local sql = Table:_generateSQLInsert(instance)
        assert.are.equals("INSERT INTO Casa (pessoa_id,nome,idade) VALUES (null,'Leonardo',30)", sql)
    end)
end)

describe("Generating SQL for create table", function()
    
    it("Cannot generate SQL because a model was not passed as parameter", function ()
                        
          assert.has.errors(function() 
                Table:_generateSQL()
              end)
      end)

    it("Cannot generate SQL because a column definition was malformed", function ()

        assert.has.errors(function()
            Table:_generateSQL()
        end)
    end)
    
    it("Cannot generate SQL because a model has no name or column", function ()
                        
          assert.has.errors(function() 
                Table:_generateSQL({})
              end)
      end)
    
    it("Should generate a create table sql ", function ()
        local sql = Table:_generateSQL({name="Pessoa", columns={idade={}, nome={notNull=true}}})
      assert.are.equals("CREATE TABLE IF NOT EXISTS Pessoa ( nome not null, idade )", sql)

      end)

    it("Should generate a create table sql with a one to one relationship ", function ()

        local instance = {name="Casa",
                          columns={
                              idade={},
                              nome={notNull=true }
                          },
                          belongsTo = {
                              pessoa="Pessoa",
                              email="Email"
                          }
                          }
        local sql = Table:_generateSQL(instance)
        assert.are.equals("CREATE TABLE IF NOT EXISTS Casa ( nome not null, idade , pessoa_id, email_id, FOREIGN KEY(pessoa_id) REFERENCES Pessoa(rowid), FOREIGN KEY(email_id) REFERENCES Email(rowid))", sql)

    end)
end)