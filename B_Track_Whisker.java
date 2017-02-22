import javax.swing.JFileChooser;
import ij.plugin.*;
import ij.plugin.frame.*;
import ij.*;
import java.io.File;
import ij.io.OpenDialog;
import ij.gui.GenericDialog;
import javax.swing.filechooser.FileNameExtensionFilter;
import org.apache.commons.io.FilenameUtils;
import ij.process.*;
import ij.gui.*;
import java.awt.*;
import java.util.ArrayList;
import ij.io.FileSaver;

public class B_Track_Whisker implements PlugIn {
	public void run(String arg) {
		JFileChooser chooser = new JFileChooser();
		chooser.setMultiSelectionEnabled(true);
		chooser.setCurrentDirectory(new File(System.getProperty("user.home")));
		FileNameExtensionFilter filter = new FileNameExtensionFilter(
        "Movies opened by ffmpeg like avi or mpeg, etc...", "avi", "mpeg");
    	chooser.setFileFilter(filter);
    	int result = chooser.showOpenDialog(null);
		switch (result) {
			case JFileChooser.APPROVE_OPTION:
				break;
			case JFileChooser.CANCEL_OPTION:
				System.out.println("Cancel or the close-dialog icon was clicked");
				return;
			case JFileChooser.ERROR_OPTION:
				System.out.println("Error");
				return;
		}
		
		File[] files = chooser.getSelectedFiles();
		
		OpenDialog od = new OpenDialog("background image");
		double r = 200, a1 = 180, a2 = 0, x = 0, y = 0;
     	GenericDialog gd = new GenericDialog("Parameters");
        gd.addNumericField("Radius: ", r, 0);
        gd.addNumericField("Angle1: ", a1, 0);
        gd.addNumericField("Angle2: ", a2, 0);
        gd.addNumericField("baseX: ", x, 0);
        gd.addNumericField("baseY: ", y, 0);
        gd.showDialog();
        if (gd.wasCanceled()) return;
        r = gd.getNextNumber();
        a1 = gd.getNextNumber();
        a2 = gd.getNextNumber();
        x = gd.getNextNumber();
        y = gd.getNextNumber();
		for(File f : files){
			IJ.run("Movie (FFMPEG)...", "choose=" +f.getParent() + "/" +f.getName() + " use_virtual_stack first_frame=0 last_frame=-1");
			makeProfileFig((float)r, (float)a1, (float)a2, (float)x, (float)y, od.getDirectory()+ "/" + od.getFileName(), f.getParent() + "/" + FilenameUtils.removeExtension(f.getName())+"profile.tif");
		}
	}

	public void makeProfileFig(float r, float a1, float a2, float baseX, float baseY, String bg, String pf){
	    ImagePlus imp = IJ.getImage();
	    a1 = (float)Math.toRadians((double)a1);
	    a2 = (float)Math.toRadians((double)a2);
	    int nSeg = 200;
	    float[] xpoints = new float[nSeg];
	    float[] ypoints = new float[nSeg];
	    int n;
	    for(n = 0; n<nSeg; n++){
	        xpoints[n] = baseX + r * (float)Math.cos((double)((a2 - a1)/(nSeg-1) * n + a1));
	        ypoints[n] = baseY - r * (float)Math.sin((double)((a2 - a1)/(nSeg-1) * n + a1));  
	    }
	
	    ImagePlus bgIMP = IJ.openImage(bg);
	    bgIMP.setRoi(new PolygonRoi(xpoints, ypoints, nSeg, Roi.POLYLINE));
	    ProfilePlot bgPP = new ProfilePlot(bgIMP);
	    double[] bgP = bgPP.getProfile();
	        
	    int sNum;
	    float[][] profile2D = new float[imp.getNSlices()][bgP.length];
	    for(sNum = 1; sNum < imp.getNSlices()+1; sNum++){
	        imp.setSlice(sNum);
	        imp.setRoi(new PolygonRoi(xpoints, ypoints, nSeg, Roi.POLYLINE));
	        ProfilePlot pp = new ProfilePlot(imp);
	        double[] p = pp.getProfile();
	        int y;
	        for(y=0; y<p.length; y++){
	             profile2D[sNum-1][y]= (float)p[y] - (float)bgP[y];
	        }
	    }
	    FloatProcessor fp = new FloatProcessor(profile2D);
	    ImagePlus prof = new ImagePlus("profile", fp);
	    IJ.run(prof, "Abs", "");
	    IJ.run(prof, "Invert", "");
	    IJ.run(prof, "Enhance Contrast", "saturated = 0.35");
	    FileSaver fs = new FileSaver(prof);
	    fs.saveAsTiff(pf);
	    imp.close();
	}
}