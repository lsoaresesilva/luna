--
-- Created by IntelliJ IDEA.
-- User: macbookair
-- Date: 30/03/17
-- Time: 19:06
-- To change this template use File | Settings | File Templates.
--
--local class = require("lib.external.30log")
--local scene = class("Scene")
local scene = {}

function scene:newScene(options)
    local newScene = {}
    local nativeScene = NativeInterface:newScene(options)
    setmetatable(newScene, {__index=scene})
    newScene._nativeObject = nativeScene
    return newScene
end

function scene:goToScene(nextScene)
    local ns = require("controllers."..nextScene)
    if ns == nil then
        error("Failed to load controller "..nextScene);
    end

    ns._nativeObject:goToScene()
end

return scene

