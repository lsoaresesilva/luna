-- TODO:
-- * Criar uma classe para representar o botão
-- * Criar uma interface em C++ para representar a camada. Aproveitar entre ios e Android

-- This button is a layer between native buttons (android and ios) and programmers
local button = {

}

-- This nativebutton must implement a Generic Interface between Android and iOS
function button:newButton(options)
    local newButton = {}
    setmetatable(newButton, {__index=button})

    local buttonCreated = obj:drawButton(options)
    newButton._target = buttonCreated

    return newButton
end

function button:setText(text)
    self._target:setText(text)
end

function button:setIdentifier(id)
    if type(id) == "number" then
        self._target:setIdentifier(id)
    end
end

function drawNativeButton(options)

end

local res = button:newButton({id=123456, text="Davi e aline meus amores"})
