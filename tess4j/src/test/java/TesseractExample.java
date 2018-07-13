import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import net.sourceforge.tess4j.*;
import net.sourceforge.tess4j.util.ImageHelper;
import org.junit.Test;

import javax.imageio.ImageIO;

public class TesseractExample {
    public static void main(String[] args) throws IOException {
        // ImageIO.scanForPlugins(); // for server environment
        File imageFile = new File("d://temp/1114.png");

        ITesseract instance = new Tesseract(); // JNA Interface Mapping
        // ITesseract instance = new Tesseract1(); // JNA Direct Mapping
        // instance.setDatapath("<parentPath>"); // replace <parentPath> with path to parent directory of tessdata
        // instance.setLanguage("eng");
        instance.setTessVariable("enable_new_segsearch","0");
        instance.setDatapath("D:\\Tesseract-OCR\\tessdata");//当tessdata的目录是D:\\Tess4J-3.4.8-src\\Tess4J\\tessdata时
        instance.setLanguage("chi_sim");//选择字库文件（只需要文件名，不需要后缀名）

        try {
           // String result = instance.doOCR(replaceWithWhiteColor(  ImageHelper.convertImageToBinary(ImageIO.read(imageFile))));
            String result = instance.doOCR((ImageIO.read(imageFile)));

            System.out.println(result);
        } catch (TesseractException e) {
            System.err.println(e.getMessage());
        }
    }

    @Test
    public void name() {
    }
    @Test
    public  void grayImage() throws IOException{
        String url="d://temp/tupian.png";
        File file = new File(url);
        BufferedImage image = ImageIO.read(file);

//        int width = image.getWidth();
//        int height = image.getHeight();
//
//        BufferedImage grayImage = new BufferedImage(width, height, BufferedImage.TYPE_BYTE_GRAY);//重点，技巧在这个参数BufferedImage.TYPE_BYTE_GRAY
//        for(int i= 0 ; i < width ; i++){
//            for(int j = 0 ; j < height; j++){
//                int rgb = image.getRGB(i, j);
//                grayImage.setRGB(i, j, rgb);
//            }
//        }
        BufferedImage grayImage=replaceWithWhiteColor(ImageHelper.convertImageToGrayscale(image));

        File newFile = new File("d://temp/tupiantemp.png");
        ImageIO.write(grayImage, "png", newFile);
    }

    public static BufferedImage replaceWithWhiteColor(BufferedImage bi) {

        int[] rgb = new int[3];

        int width = bi.getWidth();

        int height = bi.getHeight();

        int minx = bi.getMinX();

        int miny = bi.getMinY();

        /**

         * 遍历图片的像素，为处理图片上的杂色，所以要把指定像素上的颜色换成目标白色 用二层循环遍历长和宽上的每个像素

         */

        int hitCount = 0;

        for (int i = minx; i < width-1; i++) {

            for (int j = miny; j < height; j++) {

                /**

                 * 得到指定像素（i,j)上的RGB值，

                 */

                int pixel = bi.getRGB(i, j);

                int pixelNext = bi.getRGB(i+1, j);

                /**

                 * 分别进行位操作得到 r g b上的值

                 */

                rgb[0] = (pixel & 0xff0000) >> 16;

                rgb[1] = (pixel & 0xff00) >> 8;

                rgb[2] = (pixel & 0xff);

                /**

                 * 进行换色操作，我这里是要换成白底，那么就判断图片中rgb值是否在范围内的像素

                 */
//175~141
//经过不断尝试，RGB数值相互间相差15以内的都基本上是灰色，

//对以身份证来说特别是介于73到78之间，还有大于100的部分RGB值都是干扰色，将它们一次性转变成白色

//                if (rgb[0]>=92&&rgb[0]<=160&&rgb[1]>=92&&rgb[1]<=160&&rgb[2]>=92&&rgb[2]<=160) {
//
//                    //进行换色操作,0xffffff是白色
//
//                    bi.setRGB(i, j, 0x000000);
//
//                }
                if(rgb[0]>190&&rgb[1]>190&&rgb[2]>190)
                    bi.setRGB(i,j,0xffffff);

            }

        }

        return bi;

    }
}