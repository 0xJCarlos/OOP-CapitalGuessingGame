package proyecto.pkgfinal;


import java.io.*;

import org.apache.batik.transcoder.image.JPEGTranscoder;
import org.apache.batik.transcoder.TranscoderInput;
import org.apache.batik.transcoder.TranscoderOutput;

public class convertirImagen {
    public static void main(String[] args) throws Exception {
        
        JPEGTranscoder t = new JPEGTranscoder();
        
        t.addTranscodingHint(JPEGTranscoder.KEY_QUALITY, new Float(.8));
        
        String svgURI = new File(args[0]).toURL().toString();
        TranscoderInput input = new TranscoderInput(svgURI);
        
        OutputStream ostream = new FileOutputStream("res/image.jpg");
        TranscoderOutput output = new TranscoderOutput(ostream);
        
        t.transcode(input, output);
        
        ostream.flush();
        ostream.close();
        
        
    }
}
