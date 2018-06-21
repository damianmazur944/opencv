package hello.opencv;

import hello.CounterHandler;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.video.BackgroundSubtractorMOG2;
import org.opencv.videoio.VideoCapture;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.imageio.ImageIO;
import javax.xml.bind.DatatypeConverter;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import static java.util.Objects.isNull;
@Component
@RestController
public class ImageReader {

    @Autowired
    CounterHandler counterHandler;

    static{
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
    }
    Mat matrix;
    Mat bgModel;
    VideoCapture cap;
    ImageConverter converter = new ImageConverter();
    BackgroundSubtractorMOG2 sub = BackgroundSubstractor.createSubstrator();

    @RequestMapping("/updateBgModel")
    public void updateBgModel(){
        double tres = sub.getVarThreshold();
        bgModel = matrix;
        sub = BackgroundSubstractor.createSubstrator();
        sub.setVarThreshold(tres);
        System.out.println("Model updated.");
    }

    @Scheduled(fixedDelay = 1)
    public BufferedImage getOneFrame() {
        cap = new VideoCapture();
        cap.open(0);
        matrix = converter.getMatrix();
        try {
            cap.grab();
            cap.retrieve(matrix);
        }
        catch(Exception e){
            e.printStackTrace();
        }
        if(isNull(bgModel)){
            updateBgModel();
        }

        Mat fgMask = bgModel;
        sub.apply(matrix, fgMask,0);
        System.out.println(sub.getVarThreshold());
        Mat output = new Mat();
        matrix.copyTo(output, fgMask);
        BufferedImage bufferdedImage = converter.getImage(output);
        counterHandler.counterIncrementedCallback(123, bufferdedImage);
        return bufferdedImage;
    }

    public void setThreshold(int value){
        BackgrundSubstractorConfigurator.setThreshold(sub, value);
    }


}
