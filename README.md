Whisker_Tracking
================
Whisker_Trackering is a set of Fiji plugins and macros for tracking a single whisker.

How to Install
--------------
1. Make sure that your Fiji version is up to date (Help > update ImageJ).
These macros also require some plugins which may not be included in old Fiji versions.
2. [ffmpeg](https://ffmpeg.org/) is requried to import movies. Install it if you have not done so.
3. Install Whisker_Trackering using [ImageJ updater](http://imagej.net/Updater). Click "Help" > "Update Fiji" > 
4. Restart Fiji.
5. I recommend you assign short cut keys to plugins in Whisker_Trackering ("Plugins" > "Shortcuts" > "Add Shortcut"). 

How to Use
----------
1. The program expects grayscale, white-background (high pixel value) movies. Good image quality is crucial for correct tracking. I use the Optronis 600x2 camera, Navitar 50mm lens
(f/1.4), and an 2x expansion lens (EX2C, Edmund Optics). The whisker is illuminated
using an infrared LED (M850L2-C1, Thorlabs).

![Alt text](https://raw.github.com/tarokiritani/Whisker_Trackering/master/whisker.jpg "Whisker Image")

2. Run A_Make_Background. Select a movie, and epoch where whisker is moving. This macro generates a background image used in the following plugins.
3. Use B_Track_Whisker. Choose all the movies to process. You are prompted to set the base coordinate for whisker base, radius, and angles. This plugin generates a profile image for each movie.
4. Open a profile image, and run C_Profile_To_Trace to convert the image data to text file containing an angle array.
5. If the tracking is not good, you can manually correct the result using D_Inspect_On_Movie.




7. A new image named "time vs angle" pops up. In this image, each column corresponds to a profile on the arc of a frame. The darkest points in each column are connected.
8. Remove any incorrectly tracked points by a. making an roi, and b. hit edit > clear. It is convenient to make a shortcut for 'clear' in Plugins > Shortcuts > Create shortcut.
9. Repeat this until the whisker is correctly tracked.
10. Save the data in a text file. The text can be directly
 copied and pasted in Matlab.
11. Click your movie stack and run Whisker_Inspector.ijm. Open the output file you generated when prompted.
12. Check whisker position in each frame. Correct whisker position with a left click. Right click the image when you are done.
13. If this software is useful to you, I would appreciate it if you acknowledged it with
the URL (https://github.com/tarokiritani/jWhisktracker).