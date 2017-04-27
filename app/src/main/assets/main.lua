-- BUG no iOS tem q adicionar isso pra reconhecer bibliotecas externas
package.path = package.path..";lib/external/?.lua"

local scene = require("lib.userinterface.scene")

scene:goToScene("teste_ui")