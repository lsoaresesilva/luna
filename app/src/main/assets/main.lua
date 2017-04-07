
--[[local scene = require("lib.scene")
scene:goToScene("telaInicial")]]
local activeRecord = require("lib.activeRecord")
print("criando tabela")
local pessoaClass = activeRecord:extend("Pessoa")
local leonardo = pessoaClass({
    {name="nome", type="varchar" }
  })
print("criou")
leonardo.nome = "Leo"
leonardo:save()








