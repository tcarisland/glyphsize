# glyphsize
An SVG batch processing program for preprocessing individual glyphs.

This program is made so that I can design my typefaces using a proper vector graphics editor (i.e. Affinity Designer), export the individual glyphs and almost automagically import them into FontForge.

The program maps the glyphs according to a naming convention listed in the corresponding .csv table - these should be entered by the user when designing the glyphs in Affinity Designer.

It also resizes the canvas size to attain a uniform size to avoid FontForge resizing the glyphs.

It uses the Swing library to create a simple graphical user interface, and Batik to display the SVG files as preview images.
