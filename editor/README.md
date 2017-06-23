# Game level editor.

In this module you will be able to edit all the aspects of the game, including all kind of scripts for your towers, mobs, terrain, whatever you think of!

## Customisations
1. Map
    * Tile and terrain size
    * Map description
    * Map version
    * Author notes to read them when you play the game for the first time
    * Number of units needed for the game to lose
    * Minimum game version that the map will work with
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
1. Coins
    * Define your own coins with a custom image and name
    * When starting a game, you will have to also define how much money of each coin the player will be given
1. Terrain
    * Terrain tiles: You will be able to choose the image (by importing it) that the tile will have
    * Buildable terrain (or not): If a tile is not buildable, then the player will not be able to place any turrets there, this is normally for the path so the user will not be able to build in th emiddle of the path
1. Units
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
    * Events that can occur to an unit and will be possible to script (JS or KTS)
        1. Spawn: Fired when the unit is spawned
        1. Death: Fired when the unit dies 
        1. Region: Fired when the unit enters to a new region.
        1. Damage: Fired when the unit takes damage
        1. Final: Fired when the unit arrives to the last region, also the lives will be taken away.
1. Turrets
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
    * Cost. This will include different coins and amount of these coins. For example: 1 Wood, 5 Water.
    * Splash damage
    * Upgrades from: this will be the previous turret that you will need in order to build this turret
    * Sounds: On build, on shoot, on sell sounds.
    * Interest rate increase
    * Events
        1. Built: Fired when the turret is built (will be fired only once per turret)
        1. Shoot: Fired when the turret shoots an unit
        1. Sell: Fired when the turret is sold (will be fired only once per turret)
        1. Kill: Fired when the turret kills an unit
1. Levels
    * Unit types and number of units per each type per level
    * Time between units
    * Events
        1. Start: A new level has started
        1. Finish: A level has been finished (all the units of the level have been killed)
    
## Movement
The movement will be determined by regions. Each region will be determined by either:
1. A tile (the center), so the unit will walk from the center of a tile to the center of the next tile
1. A point. The unit will walk from a point to another. This should be the way to do it because it allows more flexibility when defining the movement of the units.

Either way it should be done by defining a graph, so from one point an unit can go to two or more points, being distributed equally (as possible) between the child nodes. This will allow more flexibility for paths.
