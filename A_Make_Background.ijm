iterate = 1;
openmovie = 1;

while(iterate==1){
	filepath = File.openDialog("Select a movie to generate a background");
	run("Movie (FFMPEG)...", "choose=" + filepath + " use_virtual_stack first_frame=0 last_frame=-1");
	wait(500);
	waitForUser("find the epoch (start and end frames) where whisker is moving");
	Dialog.create("");
	Dialog.addNumber("Start frame:", 1);
	Dialog.addNumber("End frame:", 100);
	Dialog.show();
	s = toString(Dialog.getNumber());
	e = toString(Dialog.getNumber());
	run("Movie (FFMPEG)...", "choose=" + filepath + " use_virtual_stack first_frame=" + s + " last_frame=" + e);
	run("Z Project...", " projection=Median");
	Dialog.create("Manual Inspection");
	Dialog.addChoice("Is this image OK?", newArray("Yes", "No"));
	Dialog.show();
	choice = Dialog.getChoice();
	if (choice == "Yes") {
		iterate = 0;
	}
}

Dialog.create("");
Dialog.addMessage("Save the image somewhere.");
Dialog.show();