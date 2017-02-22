Dialog.create("Parameters");
Dialog.addNumber("Radius:", 200);
Dialog.addNumber("Angle1:", 140);
Dialog.addNumber("Angle2:", 300);
Dialog.addNumber("x:", 200);
Dialog.addNumber("y:", 150);
Dialog.show();
r = Dialog.getNumber();
theta1 = Dialog.getNumber();
theta2 = Dialog.getNumber();
baseX = Dialog.getNumber();
baseY = Dialog.getNumber();

run("Line Width...", "line=1");
iterate = 1;
width = getWidth();
height = getHeight();
while(iterate == 1) {
	minArray = newArray(width);
	columns = newArray(width);
	for (i=0; i<width; i++) {
		columns[i] = i;
		makeLine(i, 0, i, height-1);
		columnProfile = getProfile();
		columnMin = Array.findMinima(columnProfile, 1);
		minArray[i] = columnMin[0];
	}
	roiManager("reset");
	makeSelection("polyline", columns, minArray);
	roiManager("add");
	roiManager("Show All");
	roiManager("Set Color", "red");
	roiManager("Set Line Width", 2);
	setTool("rectangle");
	run("Colors...", "foreground=white background=white selection=red");
	waitForUser("Inspect manually: \n Get rid of incorrectly tracked positions. \n Make an roi > Edit > Clear (or create a shortcut).");
	Dialog.create("Manual Inspection");
	Dialog.addChoice("Is this OK?", newArray("Find minima again!", "Save the data!"));
	Dialog.show();
	if (Dialog.getChoice == "Save the data!") {
		iterate = 0;
	}
}

imageDir = getDirectory("image"); // need to specify the folder?
f = File.open(""); // display file open dialog
fName = File.name;
fFolder = File.directory;
fPath = fFolder + File.separator + fName;
File.close(f);

File.append("angleArray = [", fPath); 
for (i=0; i<width; i++) {
	minAngle = (theta1 + (theta2 - theta1) * minArray[i]/(height - 1));
	minAngle = toString(minAngle);
	File.append(" " + minAngle, fPath);
}

File.append("];", fPath);
File.append("\r", fPath);
File.append("r = " + r + ";", fPath);
File.append("basePoint = [" + baseX + " " + baseY + "];", fPath);
//File.append("x1 = " + xCoordinates[0] + "; y1 = " + yCoordinates[0] + ";", fPath);