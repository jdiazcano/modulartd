# Game level editor.

In this module you will be able to edit all the aspects of the game, including all kind of scripts for your towers, mobs, terrain, whatever you think of!

## Customsiations
1. Map
In a map you will be able to modify things like
    * Tile and terrain size
    * Map description
    * Map version
    * Minimum game version that the map will work with
2. Terrain
You will be able to modify the terrain
    * Terrain tiles: You will be able to choose the image (by importing it) that the tile will have
    * Buildable terrain (or not): If a tile is not buildable, then the player will not be able to place any turrets there, this is normally for the path so the user will not be able to build in th emiddle of the path
3. Units
    * Texture: You will be able to choose the image (or group of images for animated units) that will be shown in the game
    * Hitpoints
    * HP regen: How much hitpoints per second the unit will regen
    * Armor
    * Armor type
    * Movement speed
    * Invisibility
    * Flying unit
    * Stun immune
    * Slow immune
    * Reward when killing
    * Sounds: On spawn, on move and on die sounds
    * Lives that will be taken once reaching the end (or counting for the limit)
4. Turrets
    * Texture:
    * Bullet texture
    * Bullet speed
    * Attack speed
    * Range
    * Attack type (like armor type)
    * Show invisibles
    * Air attack
    * Stun %
    * Stun time
    * Slow %
    * Slow time
    * Critical strike %
    * Critical strike multiplier
    * Cost
    * Splash damage
    * Upgrades from: this will be the previous turret that you will need in order to build this turret
    * Sounds: On build, on shoot, on sell sounds.
    * Interest rate increase
5. Levels
    * Unit types and number of units per each type per level
    * Time between units
6. Money
    * Will be possible to create custom coins. A coin is something that will be used as payment to build/update the turrets or other kind of updates (this is something that I'm thinking right now so nothing is planned)
    * A coin will be able to be created inside the Game tab and will be immediately available in all other places of the editor
7. General game
    * Starting money, a table with all the coins will be available and you will only have to input the starting money for each coin
    * Time between levels
    * Lose condition: There are two possibilities:
        1. Lose because N units have reached the end of the path so you have run out of lives
        2. Lose because N units are in the map, this is a cool feature in order to create circle tower defenses with the path A -> B -> C -> D -> B -> C... etc
    * Number of units needed to lose: This is a configuration for the N option above.
    * What to do when an unit reaches the end: You will be able to choose between three options:
        1. Unit dies: The unit just dies and a life (or number of lives) are substracted
        2. Teleport to the start with the same HP and everything, this will still substract the configured lives
        3. Move the unit to the start in order to create circular tower defense games
    * % of the money that you will be given when selling an unit that has already been used (it fired), or 100% if it didn't fire yet
    * Interest rate: Each level you will be able to give the player a % of his current amount of money and this interest rate will be able to be configured by the turrets, for example there will be a turret with 2% interest rate that if you build it you will receive more gold each round