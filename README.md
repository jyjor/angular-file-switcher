<!-- Plugin description -->

**Angular File Switcher** is a plugin that switches between similarly named files within a directory. It is
specifically designed for Angular component files but works with any set of files within a directory where file names (
except for the file extensions) match.

I built this as a reaction to Angular CLI QuickSwitch Plugin. While I find it a valuable plugin, it doesn't function
_exactly_ how I want in all circumstances. It has the following enhancements:

- Choose file extensions to include or exclude.
- This plugin works with multiple tab groups how one would expect.
- It doesn't unexpectedly rearrange the tab order.
- Optionally keep files open - by tab group or workspace.
- If you open a similarly named file using a non-file-switcher command (such as Ctrl+Click), you can optionally close
  other similarly named files.

## Features

- **Cycle Between Files**: Press `Alt+S` to cycle through all component files (TypeScript, HTML, CSS, etc.)
- **Direct File Access**: Jump directly to specific file types:
  - `Alt+T` - TypeScript file (.ts)
  - `Alt+H` - HTML template file (.html)
  - `Alt+C` - CSS/Style file (.scss, .css)
  - `Alt+P` - Test file (.spec.ts)

## Configuration

### File Extensions

Configure which file extensions to include when switching:

1. Open **Settings/Preferences** (`Ctrl+Alt+S` or `⌘,`)
2. Navigate to **Tools > Angular File Switcher**
3. Customize the file extension types:
   - **Class**: TypeScript/JavaScript files (.ts, .js)
   - **Template**: HTML and other template files (.html, .php, etc.)
   - **Style**: CSS and other stylesheet files (.css, .scss, etc.)
   - **Test**: Test files (.spec.ts, .spec.js, etc.)

### Customizing Keyboard Shortcuts

To customize the keyboard shortcuts:

1. Open **Settings/Preferences** (`Ctrl+Alt+S` or `⌘,`)
2. Navigate to **Keymap**
3. Search for "Angular File Switcher" to see all available actions
4. Right-click on an action and select **Add Keyboard Shortcut** to set your preferred keys
5. To remove the default shortcut, right-click again and select **Remove Shortcut**

Available actions:

- **Open Next Similarly Named File**: Cycle through all matching files (Default: `Alt+S`)
- **Open TypeScript File**: Jump directly to TypeScript file (Default: `Alt+T`)
- **Open HTML Template File**: Jump directly to HTML file (Default: `Alt+H`)
- **Open CSS/Style File**: Jump directly to CSS/style file (Default: `Alt+C`)
- **Open Test File**: Jump directly to test file (Default: `Alt+P`)

## Links

Source code: https://github.com/jyjor/angular-file-switcher

Issues: https://github.com/jyjor/angular-file-switcher/issues

<!-- Plugin description end -->
