package lenote.com.picturecompare;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Matrix;
import android.graphics.Paint;

import java.io.File;

/**
 * Created by wxy on 2015/7/11.
 */
public class tools {
    private static final String TAG = "tools";

    public static boolean comparePictures(Bitmap bitmap1,Bitmap bitmap2){
        Bitmap bit1=toGrayscale(get64pixBitmap(bitmap1));
        double pix1=getAveragePix(bit1);
        String com1=getCompareString(bit1, pix1);
        Bitmap bit2=toGrayscale(get64pixBitmap(bitmap2));
        double pix2=getAveragePix(bit2);
        String com2=getCompareString(bit2, pix2);
        int count=0;
        for(int i=0;i<com1.length();i++){
            if(com1.charAt(i)!=com2.charAt(i)){
                count++;
            }
        }
        return count<=20;
    }

    private static String getCompareString(Bitmap bit2, double pix2) {
        int w=bit2.getWidth();
        int h=bit2.getHeight();
        String whole="";
        for(int i=0;i<w;i++){
            for(int j=0;j<h;j++){
                whole+=(bit2.getPixel(i,j)>=pix2?"1":"0");
            }
        }
        return whole;
    }

    private static double getAveragePix(Bitmap bit2) {
        int w=bit2.getWidth();
        int h=bit2.getHeight();
        double whole=0;
        for(int i=0;i<w;i++){
            for(int j=0;j<h;j++){
                whole+=bit2.getPixel(i,j);
            }
        }
        return whole/(w*h);
    }

    private static Bitmap get64pixBitmap(Bitmap bitmap) {
        int w = bitmap.getWidth();
        int h = bitmap.getHeight();
        Matrix matrix = new Matrix();
        float scaleWidth = ((float) 8 / w);
        float scaleHeight = ((float) 8 / h);
        matrix.postScale(scaleWidth, scaleHeight);
        return Bitmap.createBitmap(bitmap, 0, 0, w, h, matrix, true);
    }
    public static Bitmap toGrayscale(Bitmap bmpOriginal) {
        int width, height;
        height = bmpOriginal.getHeight();
        width = bmpOriginal.getWidth();

        Bitmap bmpGrayscale = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565);
        Canvas c = new Canvas(bmpGrayscale);
        Paint paint = new Paint();
        ColorMatrix cm = new ColorMatrix();
        cm.setSaturation(0);
        ColorMatrixColorFilter f = new ColorMatrixColorFilter(cm);
        paint.setColorFilter(f);
        c.drawBitmap(bmpOriginal, 0, 0, paint);
        return bmpGrayscale;
    }

    public static Bitmap getBitmap(File dst) {
        int width=200, height=200;
        if (null != dst && dst.exists()) {
            BitmapFactory.Options opts = null;
            if (width > 0 && height > 0) {
                opts = new BitmapFactory.Options();            //设置inJustDecodeBounds为true后，decodeFile并不分配空间，此时计算原始图片的长度和宽度
                        opts.inJustDecodeBounds = true;
                BitmapFactory.decodeFile(dst.getPath(), opts);
                // 计算图片缩放比例
                final int minSideLength = Math.min(width, height);
                opts.inSampleSize = ImageResizer.calculateInSampleSize(opts, minSideLength,
                        width * height);            //这里一定要将其设置回false，因为之前我们将其设置成了true
                        opts.inJustDecodeBounds = false;
                opts.inInputShareable = true;
                opts.inPurgeable = true;
            }
            try {
                return BitmapFactory.decodeFile(dst.getPath(), opts);
            } catch (OutOfMemoryError e) {
                e.printStackTrace();
            }
        }
        return null;
    }
}
