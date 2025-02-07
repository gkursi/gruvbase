package xyz.qweru.api.util;

import org.lwjgl.glfw.GLFW;

/**
 * fixed getKey method to support special keys
 * @see org.lwjgl.glfw.GLFW
 */
public class InputUtil {

    public static final int KEY_UNKNOWN = -1;

    /** Printable keys. */
    public static final int
            KEY_SPACE         = 32,
            KEY_APOSTROPHE    = 39,
            KEY_COMMA         = 44,
            KEY_MINUS         = 45,
            KEY_PERIOD        = 46,
            KEY_SLASH         = 47,
            KEY_0             = 48,
            KEY_1             = 49,
            KEY_2             = 50,
            KEY_3             = 51,
            KEY_4             = 52,
            KEY_5             = 53,
            KEY_6             = 54,
            KEY_7             = 55,
            KEY_8             = 56,
            KEY_9             = 57,
            KEY_SEMICOLON     = 59,
            KEY_EQUAL         = 61,
            KEY_A             = 65,
            KEY_B             = 66,
            KEY_C             = 67,
            KEY_D             = 68,
            KEY_E             = 69,
            KEY_F             = 70,
            KEY_G             = 71,
            KEY_H             = 72,
            KEY_I             = 73,
            KEY_J             = 74,
            KEY_K             = 75,
            KEY_L             = 76,
            KEY_M             = 77,
            KEY_N             = 78,
            KEY_O             = 79,
            KEY_P             = 80,
            KEY_Q             = 81,
            KEY_R             = 82,
            KEY_S             = 83,
            KEY_T             = 84,
            KEY_U             = 85,
            KEY_V             = 86,
            KEY_W             = 87,
            KEY_X             = 88,
            KEY_Y             = 89,
            KEY_Z             = 90,
            KEY_LEFT_BRACKET  = 91,
            KEY_BACKSLASH     = 92,
            KEY_RIGHT_BRACKET = 93,
            KEY_GRAVE_ACCENT  = 96,
            KEY_WORLD_1       = 161,
            KEY_WORLD_2       = 162;


    public static String getKey(int key) {
        switch (key) {
            case KEY_ESCAPE -> {
                return "Esc";
            }
            case KEY_RETURN -> {
                return "Return";
            }
            case KEY_TAB -> {
                return "Tab";
            }
            case KEY_BACKSPACE -> {
                return "Backspace";
            }
            case KEY_INSERT -> {
                return "Insert";
            }
            case KEY_DELETE -> {
                return "Delete";
            }
            case KEY_RIGHT -> {
                return "Right";
            }
            case KEY_LEFT -> {
                return "Left";
            }
            case KEY_UP -> {
                return "Up";
            }
            case KEY_DOWN -> {
                return "Down";
            }
            case KEY_PAGE_UP -> {
                return "Page Up";
            }
            case KEY_PAGE_DOWN -> {
                return "Page Dwn";
            }
            case KEY_HOME -> {
                return "Home";
            }
            case KEY_END -> {
                return "End";
            }
            case KEY_CAPS_LOCK -> {
                return "Caps Lck";
            }
            case KEY_SCROLL_LOCK -> {
                return "Scrl Lck";
            }
            case KEY_NUM_LOCK -> {
                return "Num Lck";
            }
            case KEY_PRINT_SCREEN -> {
                return "Prnt Scr";
            }
            case KEY_PAUSE -> {
                return "Pause";
            }
            case KEY_LEFT_SHIFT -> {
                return "LShift";
            }
            case KEY_LEFT_ALT -> {
                return "LAlt";
            }
            case KEY_RIGHT_SHIFT -> {
                return "RShift";
            }
            case KEY_RIGHT_ALT -> {
                return "RAlt";
            }
            case KEY_LAST -> {
                return "Menu";
            }
            case KEY_LEFT_CONTROL -> {
                return "LCtrl";
            }
            case KEY_RIGHT_CONTROL -> {
                return "RCtrl";
            }
            case KEY_LEFT_SUPER -> {
                return "LWin";
            }
            case KEY_RIGHT_SUPER -> {
                return "RWin";
            }
            case KEY_UNKNOWN -> {
                return "";
            }
            default -> {
                try {
                    return GLFW.glfwGetKeyName(key, GLFW.glfwGetKeyScancode(key));
                } catch (Exception ignored) {
                    return "";
                }
            }
        }
    }

    /** Function keys. */
    public static final int
            KEY_ESCAPE        = 256,
            KEY_RETURN        = 257,
            KEY_TAB           = 258,
            KEY_BACKSPACE     = 259,
            KEY_INSERT        = 260,
            KEY_DELETE        = 261,
            KEY_RIGHT         = 262,
            KEY_LEFT          = 263,
            KEY_DOWN          = 264,
            KEY_UP            = 265,
            KEY_PAGE_UP       = 266,
            KEY_PAGE_DOWN     = 267,
            KEY_HOME          = 268,
            KEY_END           = 269,
            KEY_CAPS_LOCK     = 280,
            KEY_SCROLL_LOCK   = 281,
            KEY_NUM_LOCK      = 282,
            KEY_PRINT_SCREEN  = 283,
            KEY_PAUSE         = 284,
            KEY_F1            = 290,
            KEY_F2            = 291,
            KEY_F3            = 292,
            KEY_F4            = 293,
            KEY_F5            = 294,
            KEY_F6            = 295,
            KEY_F7            = 296,
            KEY_F8            = 297,
            KEY_F9            = 298,
            KEY_F10           = 299,
            KEY_F11           = 300,
            KEY_F12           = 301,
            KEY_F13           = 302,
            KEY_F14           = 303,
            KEY_F15           = 304,
            KEY_F16           = 305,
            KEY_F17           = 306,
            KEY_F18           = 307,
            KEY_F19           = 308,
            KEY_F20           = 309,
            KEY_F21           = 310,
            KEY_F22           = 311,
            KEY_F23           = 312,
            KEY_F24           = 313,
            KEY_F25           = 314,
            KEY_KP_0          = 320,
            KEY_KP_1          = 321,
            KEY_KP_2          = 322,
            KEY_KP_3          = 323,
            KEY_KP_4          = 324,
            KEY_KP_5          = 325,
            KEY_KP_6          = 326,
            KEY_KP_7          = 327,
            KEY_KP_8          = 328,
            KEY_KP_9          = 329,
            KEY_KP_DECIMAL    = 330,
            KEY_KP_DIVIDE     = 331,
            KEY_KP_MULTIPLY   = 332,
            KEY_KP_SUBTRACT   = 333,
            KEY_KP_ADD        = 334,
            KEY_KP_RETURN     = 335,
            KEY_KP_EQUAL      = 336,
            KEY_LEFT_SHIFT    = 340,
            KEY_LEFT_CONTROL  = 341,
            KEY_LEFT_ALT      = 342,
            KEY_LEFT_SUPER    = 343,
            KEY_RIGHT_SHIFT   = 344,
            KEY_RIGHT_CONTROL = 345,
            KEY_RIGHT_ALT     = 346,
            KEY_RIGHT_SUPER   = 347,
            KEY_MENU          = 348,
            KEY_LAST          = KEY_MENU;

    /** If this bit is set one or more Shift keys were held down. */
    public static final int MOD_SHIFT = 0x1;

    /** If this bit is set one or more Control keys were held down. */
    public static final int MOD_CONTROL = 0x2;

    /** If this bit is set one or more Alt keys were held down. */
    public static final int MOD_ALT = 0x4;

    /** If this bit is set one or more Super keys were held down. */
    public static final int MOD_SUPER = 0x8;

    /** If this bit is set the Caps Lock key is enabled and the  input mode is set. */
    public static final int MOD_CAPS_LOCK = 0x10;

    /** If this bit is set the Num Lock key is enabled and the  input mode is set. */
    public static final int MOD_NUM_LOCK = 0x20;

    /** Mouse buttons. See <a target="_blank" href="http://www.glfw.org/docs/latest/input.html#input_mouse_button">mouse button input</a> for how these are used. */
    public static final int
            MOUSE_BUTTON_1      = 0,
            MOUSE_BUTTON_2      = 1,
            MOUSE_BUTTON_3      = 2,
            MOUSE_BUTTON_4      = 3,
            MOUSE_BUTTON_5      = 4,
            MOUSE_BUTTON_6      = 5,
            MOUSE_BUTTON_7      = 6,
            MOUSE_BUTTON_8      = 7,
            MOUSE_BUTTON_LAST   = MOUSE_BUTTON_8,
            MOUSE_BUTTON_LEFT   = MOUSE_BUTTON_1,
            MOUSE_BUTTON_RIGHT  = MOUSE_BUTTON_2,
            MOUSE_BUTTON_MIDDLE = MOUSE_BUTTON_3;
}