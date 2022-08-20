<!-- Plugin description -->
**Angular File Switcher** is a simple plugin that switches between similarly named files within a directory. It is
specifically designed for Angular component files.

I built this as a reaction to Angular CLI QuickSwitch Plugin. While I find it a valuable plugin, it doesn't function _
exactly_ how I want in all circumstances. Additionally, it has some buggy behaviors I needed to fix:

* This plugin works with multiple tab groups how one would expect.
* It doesn't unexpectedly rearrange the tab order.
* You can optionally keep files open - by tab group or workspace
* If you open a similarly named file using a non-file-switcher command (such as Ctrl+Click), you can optionally close
  other similarly named files.

Use the `Angular File Switcher` → `Open Next Similarly Named File` action to cycle between component files in a
directory.

## Settings

### Cycle through these files extensions

`ts` `js` `html` `php` `haml` `jade` `pug` `slim` `css` `sass` `scss` `less` `styl`

Remove any file extensions you will never use and add any that are missing. Yes, you can include **test file**,
storybook, or any other extensions here.

For example, I code in React and Angular. For React I use `js` and `test.js` files and like to switch between those two
only. For Angular I switch between `ts` `html` `scss` and I don't want to include the test files, so I don't
add `spec.ts`.
`ts` `js` `html` `css` `scss` `test.js`

Keep in mind that they have to have the same base name to work:
These files will cycle:

* my-date-picker.component.ts
* my-date-picker.component.html
* my-date-picker.component.css
* my-date-picker.component.spec.ts

But this would not

* my-date-picker.spec.ts

### Tab groups operate independently of one another

I _love_ this setting. I often have multiple tab groups and sometimes like to have my `ts` file on the left and
the `html` file on the right. With this setting enabled, switching between files only closes files that are in the same
tab group. Disable this setting if you want it to open and close files everywhere.

### Editor close behavior

#### Never close editor windows

Choose this setting if you want to quickly open component files, but never close them automatically.

#### Only close on `Open Next Similarly Named File` action

(Default) Choose this setting if you want to cycle through the component files and have just one open. Open a file in a
different way, however, and it will not close any files until you start cycling again. Examples of other ways you can
open a component file:

* Open a file through the Project files window
* Ctrl+B or Ctrl+Click to navigate to a definition
* Ctrl+Shift+N to open a file matching a pattern

#### Always close similarly names files

This is my preferred behavior. Whenever a file is opened, it closes all other files of that component.

Source code: https://github.com/jyjor/angular-file-switcher

<!-- Plugin description end -->
