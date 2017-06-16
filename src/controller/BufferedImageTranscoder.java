package controller;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import org.apache.batik.transcoder.TranscoderException;
import org.apache.batik.transcoder.TranscoderInput;
import org.apache.batik.transcoder.TranscoderOutput;
import org.apache.batik.transcoder.image.ImageTranscoder;
import org.apache.batik.transcoder.image.PNGTranscoder;
import org.apache.commons.io.FileUtils;

public class BufferedImageTranscoder extends ImageTranscoder {
		private BufferedImage image = null;		
		@Override
		public BufferedImage createImage(int weight, int height)
		{
			return new BufferedImage(weight, height, BufferedImage.TYPE_INT_ARGB);
		}	
		@Override
		public void writeImage(BufferedImage image, TranscoderOutput output) throws TranscoderException
		{
			this.image = image;
		}
		public BufferedImage getBufferedImage()
		{
			return image;
		}
		
		public static BufferedImage loadImage(File svgFile, float width, float height) {
			BufferedImageTranscoder imageTranscoder = new BufferedImageTranscoder();
		    imageTranscoder.addTranscodingHint(PNGTranscoder.KEY_WIDTH, width);
		    imageTranscoder.addTranscodingHint(PNGTranscoder.KEY_HEIGHT, height);
		    try {
		    	InputStream svgStream = FileUtils.openInputStream(svgFile);
			    TranscoderInput input = new TranscoderInput(svgStream);
			    imageTranscoder.transcode(input, null);
			} catch (TranscoderException e) {
				e.printStackTrace();
				return null;
			} catch (IOException e) {
				e.printStackTrace();
				return null;
			}
		    return imageTranscoder.getBufferedImage();
		}
}