WhiskerTracking
================
WhiskerTrackering is a set of Fiji plugins and macros for tracking a single whisker.

How to Install
--------------
1. Make sure that your Fiji version is up to date (Help > update ImageJ).
These macros also require some plugins which may not be included in old Fiji versions.
2. [ffmpeg](https://ffmpeg.org/) is requried to import movies. Install it if you have not done so.
3. Install Whisker_Trackering using [ImageJ updater](http://imagej.net/Updater). Open Fiji, "Help" > "Update" > "Manage update sites" > select "WhsiketTracking" > "Close" > "Apply changes" in "ImageJ Updater".
4. Restart Fiji.
5. I recommend you assign short cut keys to plugins in WhiskerTrackering ("Plugins" > "Shortcuts" > "Add Shortcut"). 

How to Use
----------
1. The program expects grayscale, white-background (high pixel value) movies. Good image quality is crucial for correct tracking. I use the Optronis 600x2 camera, Navitar 50mm lens
(f/1.4), and an 2x expansion lens (EX2C, Edmund Optics). The whisker is illuminated
using an infrared LED (M850L2-C1, Thorlabs).
![Alt tag](https://github.com/tarokiritani/WhiskerTrackering/blob/master/whisker.jpg)
2. Run A_Make_Background. Select a movie, and epoch where whisker is moving. This macro generates a background image used in the following plugins.
3. Use B_Track_Whisker. Choose all the movies to process. You are prompted to set the base coordinate for whisker base, radius, and angles. This plugin generates a profile image for each movie. In this image, each column corresponds to a profile on the arc of a frame. The darkest points in each column are connected.
4. Open a profile image, and run C_Profile_To_Trace to convert the image data to text file containing an angle array. Remove any incorrectly tracked points by a. making an roi, and b. backspace or edit > clear (create a shortcut!).
5. If the tracking is not good, you can manually correct the result using D_Inspect_On_Movie. Check whisker position in each frame. Correct whisker position with a left click. Right click the image when you are done.
6. The resulting text file can be directly copied and pasted in Matlab.
7. If this software is useful to you, I would appreciate it if you acknowledged it with
my paper in prep..