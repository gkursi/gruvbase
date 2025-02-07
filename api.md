# Gruvbase API
### Animation
- Animation: class for animations
    - StaticAnimation: when you don't want the animation to progress with time / you don't know how long the animation will take (eg. mining) / ..
- AnimationUtil: extra utility for animations
### Config
- ConfigManager: used to manage a config folder
    - ConfigFile: config file, uses Jsonable objects
- Jsonable: Both addJson and readJson are given the **ROOT** objects to read.
### Event
- EventManager: Manager for a specific event, subscribing returns an id that can be later used for unsubscribing. If an event is cancelled before calling all of the subscribers, any remaining subscribers won't be called.
- Event: Base class for all events
- ImmutableEvent: Extends event, prevents cancelling
### Logging
To prevent log4j or similar from capturing System.out / System.err and adding unnecessary output to it,
you can either manually create a stream manager for stdOut / stdErr or call `ApiMain.init()` somewhere in your code (recommended)
- Logger: Wrapper for System.out / System.err, better formatting than the default logger
- ANSI: Contains console color codes
- GPrintStream: Base print stream used by the logger, adds `[thread name-hh:mm:ss]` before the output
- StreamManager: Utility class for redirecting stdOut / stdErr
### Module
- Category: Module categories
- Module: Base class for all modules
- HudModule: Extends Module, adds the render(..) method, base class for all hud modules
- ModuleManager: manages the modules, currently you have to manually add them in the constructor, however in the future this will be replaced with reflection scanning.
### Network
- HTTP: Utility class for creating simple GET/PUT requests
- DiscordWebhook: Utility class for using discord webhooks, full credit to <a href="https://gist.github.com/k3kdude/fba6f6b37594eae3d6f9475330733bdb">k3kdude</a>
### Render
- Font
    - FontRenderer: font renderer
    - Font: FontRenderer wrapper for easier usage
- UI (full credit to 0x150)
    - MSAAFramebuffer: framebufffer with msaa
    - Renderer2d: 2D render utils
    - BufferUtils: Buffer rendering utils
- World
    - Renderer3d
    - will have more soon
- ColorScheme: class for storing themes
- ColorUtil: color conversion util
### Setting
- Setting: base class for all settings, can be used alone with any type but not recommended
- ModeSetting: setting for enums
- BooleanSetting: setting for booleans
- NumberSetting: setting for any number (`double` is usually used for any internal calculations)
- TextSetting: setting for text
### Util
Miscellaneous utility classes.
- InputUtil: correctly convert any keycode to a key name (including special keys, like RShift, LAlt, ...)
- EntityUtil: util for entities
- Manager: utility class for managing a list of items, used by ModuleManager
- InventoryUtil: util for finding items in the players inventory
- FakePlayer: utility class for spawning fake players
- DamageUtil: utility class for calculating damage (full credit to meteor client)
- BlockUtil: util for blocks
- TargetUtil: util for finding targets