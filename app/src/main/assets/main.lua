
--[[local scene = require("lib.scene")
scene:goToScene("telaInicial")]]
DEBUG = false
--local Pessoa = require("models.Pessoa")
--local leonardo = Pessoa({sobrenome="Soares", cpf=123})
--leonardo:save()
local Casa = require("models.Casa")
--local minhaCasa = Casa({
            --nome="davi aline meus amores",
            --idade=28})
local resultados = Casa:findAll()
if #resultados > 0 then
    for i=1, #resultados do
        local casa = resultados[i]
        if casa.pessoa ~= nil then
            print(casa.pessoa.sobrenome)
            print(casa.pessoa.cpf)
        end
    end
end
--minhaCasa:save()
--local resultado = leonardo:where({id=5}):orderBy({desc('nome')}):all()

--leonardo:save()

print("oi")
--leonardo:delete()
--leonardo:all()

--leonardo.nome = "Leo"
--leonardo:all()








