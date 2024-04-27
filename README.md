<img src="https://cdn.modrinth.com/data/cached_images/c49c367c6c18990eb5df2459f70d3bf5e80d3cd2.png" alt="Helix" width="200"/>

# Helix Utility Client
Helix is a QOL Mod that aims to improve your experience by adding various features.
Helix is free and open source, forever.

[About Me](https://integr.is-a.dev/) | [Source](https://github.com/Integr-0/Helix) | [Issues](https://github.com/Integr-0/Helix/issues)

[<img src="https://cdn.modrinth.com/data/cached_images/a27519bb7f8a38f6307d68dc28e889a3c2745cb6.png" alt="Kotlin Adapter" width="200"/>](https://modrinth.com/mod/fabric-language-kotlin) [<img src="https://cdn.modrinth.com/data/cached_images/f927efb8451e54b96685974de26bafabf1878dcd.png" alt="Fabric Api" width="180"/>](https://modrinth.com/mod/fabric-api)

## Installation
Helix requires the Kotlin language adapter and the Fabric API to work correctly

### How to install, step by step
- Download the latest release of Helix, the matching Fabric API and Kotlin language adapter
- Put all 3 mods in your mods folder
- Make sure you have the correct java version installed (17)
- Make sure you have the fabric loader installed
- Launch the fabric loader
- Have fun!

## Accent colors
- ### Rgb
  ![Rgb](https://cdn.modrinth.com/data/cached_images/c5a346d2a2dd2bda9e2fefb772241084c35e4f1e.png)
- ### Orange
  ![Orange](https://cdn.modrinth.com/data/cached_images/1db57417da3e8be84ead9910dff148a6d187ef31.png)
- ### Red
  ![Red](https://cdn.modrinth.com/data/cached_images/20513cb96745bbbf61a4babfdad432383629020c.png)
- ### Green
  ![Green](https://cdn.modrinth.com/data/cached_images/90a95f491a4f653a37ef4a7dacab44bdc46127e3.png)
- ### Yellow
  ![Yellow](https://cdn.modrinth.com/data/cached_images/0557f94277aec0ec6ad40fe412c3beba7760de76.png)
- ### Pink
  ![Pink](https://cdn.modrinth.com/data/cached_images/1070c11db85a079fdeecbe16f5d4ec4ae2999f48.png)
- ### Purple
  ![Purple](https://cdn.modrinth.com/data/cached_images/f14094442d3293eff76b9cd570d276005ba239d7.png)
- ### Blue
  ![Blue](https://cdn.modrinth.com/data/cached_images/8eeb2a7b2d5b870aa92c56026213e91cb0c3039f.png)

## Themes
- ### Dark
  ![Dark](https://cdn.modrinth.com/data/cached_images/c593fc799439a15183dc9d4534b76c5b5abb35a0.png)
- ### Light
  ![Light](https://cdn.modrinth.com/data/cached_images/69b7901b52164e02c1ec36109cd6d2c01d14b8f8.png)

## Ingame features
- ### Armorhud
  Shows you your armor on the screen without having to open your inventory
- ### Autosprint
  Presses the sprint key for you
- ### Betterarm
  Changes the rendering of your ingame hand
- ### Coordinates
  Shows you your coordinates in the world
- ### Cosmetics
  Render cosmetics clientside, for free
- ### Directions
  Shows in what direction you are facing
- ### Fps Display
  Shows your current Fps
- ### Fullbright
  See in the dark
- ### Hotbar
  Changes several status bars to literal bars
- ### Keystrokes
  Shows what keys are being pressed
- ### Nametags
  See your own nametag and add information to it
- ### No Armor
  Hides your armor clientside
- ### No Render
  Disables the rendering of certain things
- ### Ping Display
  Shows your current ping
- ### Chat Format
  Create gradients without leaving minecraft
  ![Format](https://cdn.modrinth.com/data/cached_images/cc9d56a7c154db2f3aef68cc481a1fc3f9cca089.png)
## Discord Presence
Shows some information about the server or world you are on to your friends on discord

## For Developers and Server Owners
The following example code can be used to block modules from being used on your server.
In the future, there may be more options for configuring the mod via the server.
### Json Structure for the payload
This is the structure to use in the payload of the message sent to the player. Invalid json and incorrect module ids will be ignored. You can find the module ids in the respective modules.
The ids can always be found in the header of the module and will always be in the following scheme: `moduleName`.
```json
{
  "disabled": [
    "armorHud",
    "autoSprint"
  ]
}
```
### Sending the configuration json in your plugin
This code can be put for example in the `onJoin` listener. Once disabled, modules stay disabled until the player rejoins the server. 
Then they need to be disabled again.
```java
<player>.sendPluginMessage(<plugin-instance>, "helix:config",
     <json-payload>.toByteArray()
);
```
