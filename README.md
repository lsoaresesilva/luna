# Summary
Its a proof of concept that is possible to create a framework for native mobile user interface components with Lua.

# Objectives

Actualy does not exist a framework written in Lua programming which allows mobile cross-plataform development with native user interface. This project aims to fill this gap and allows a single code to compile natively to iOS and Android with native look and feel.

For example:
```lua
local button = userInterface.newButton()

-- will create a Button in Android and a UIButton in iOS

```
