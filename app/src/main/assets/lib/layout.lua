--
-- Created by IntelliJ IDEA.
-- User: macbookair
-- Date: 01/04/17
-- Time: 18:43
-- To change this template use File | Settings | File Templates.
----
-- Created by IntelliJ IDEA.
-- User: macbookair
-- Date: 30/03/17
-- Time: 19:06
-- To change this template use File | Settings | File Templates.
--

local layout = {}

function layout:newLayout(options)
    local newLayout = {}
    setmetatable(newLayout, {__index=layout})
    newLayout._nativeObject = NativeInterface:newLayout(options)
    return newLayout
end


function layout:insert(component)
    self._nativeObject:insert(component._nativeObject)
end

return layout



