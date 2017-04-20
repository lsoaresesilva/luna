
--[[local scene = require("lib.scene")
scene:goToScene("telaInicial")]]
DEBUG = false

local Casa = require("models.Casa")
local Pessoa = require("models.Pessoa")
local casaId = Casa:where({rowid=2}):findAll()
if #casaId > 0 then
    casaId[1].pessoa = nil
    casaId[1]:save()
end
local resultadosPessoa = Pessoa:where({rowid=4}):findAll()


if #resultadosPessoa > 0 then
    for i=1, #resultadosPessoa do
        local pessoa = resultadosPessoa[i]
        if #pessoa.casas > 0 then
            for j=1, #pessoa.casas do
                if pessoa.casas[j] ~= nil then
                    --print(casa.pessoa.sobrenome) --> 'Soares'
                    print(pessoa.casas[j].nome) --> 123
                    print(pessoa.casas[j].idade)

                end
            end
        end
    end
end

--[[
local opa = Casa:where({pessoa_id=3}):OR({pessoa_id=4}):findAll()
if #resultados > 0 then
    for i=1, #resultados do
        local casa = resultados[i]
        if casa.pessoa ~= nil then
            print(casa.pessoa.sobrenome) --> 'Soares'
            print(casa.pessoa.cpf) --> 123
        end
    end
end]]
--
-- EXEMPLO: carregando todos os registros com JOIN
--[[
--

local Pessoa = require("models.Pessoa")
local leonardo = Pessoa({sobrenome="Soares", cpf=123})
leonardo:save()

local minhaCasa = Casa({
            nome="davi aline meus amores",
            idade=28,
            pessoa=leonardo})
minhaCasa:save()

local resultados = Casa:findAll()
if #resultados > 0 then
    for i=1, #resultados do
        local casa = resultados[i]
        if casa.pessoa ~= nil then
            print(casa.pessoa.sobrenome) --> 'Soares'
            print(casa.pessoa.cpf) --> 123
        end
    end
end]]

--local resultado = leonardo:where({id=5}):orderBy({desc('nome')}):all()

--leonardo:save()

print("oi")
--leonardo:delete()
--leonardo:all()

--leonardo.nome = "Leo"
--leonardo:all()








