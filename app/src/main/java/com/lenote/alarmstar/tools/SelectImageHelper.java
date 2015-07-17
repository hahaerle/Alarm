package lenote.com.picturecompare;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.util.UUID;


public abstract class SelectImageHelper {

    public final static int REQ_CODE_PICK_GALLERY = 901;
    public final static int REQ_CODE_PICK_CAMERA = 902;


    private static String uuid = UUID.randomUUID().toString() + ".jpg";


    private Activity activity;
    private Fragment fragment = null;

    public SelectImageHelper(Activity activity) {
        this.activity = activity;
    }

    public SelectImageHelper(Activity activity, Fragment fragment) {
        this(activity);
        this.fragment = fragment;

    }


    public boolean checkValide() {

        if (!checkWriteExternalPermission()) {
            DebugLog.e("we need android.permission.WRITE_EXTERNAL_STORAGE");
            return false;
        }
        if (!checkSDisAvailable()) {
            DebugLog.e("请检查存储卡是否可用。");
            return false;
        }
        return true;
    }

    public File getSelectedImageFile() {
        return getTempImageFile();
    }


    /**
     * crop 이 필요한 경우 설정함. 설정하지 않으면 crop 하지 않음.
     *
     * @param width
     * crop size width.
     * @param height
     * crop size height.
     */
    public int mCropAspectWidth = 1, mCropAspectHeight = 1;

    public void setCropOption(int aspectX, int aspectY) {
        mCropAspectWidth = aspectX;
        mCropAspectHeight = aspectY;
    }

    /**
     * 设置您要使用的最大尺寸的图像。水平和垂直尺寸必须小于您指定的大小调整图像的大小。 default size : 500
     *
     * @param sizePixel
     * 기본 500
     */
    private int mImageSizeBoundary = 500;

    private int mMaxImageSize = 960;


    private int imageWidth;
    private int imageHeight;

    public void setImageSizeBoundary(int sizePixel) {
        mImageSizeBoundary = sizePixel;
    }

    private boolean checkWriteExternalPermission() {
        String permission = "android.permission.WRITE_EXTERNAL_STORAGE";
        int res = activity.checkCallingOrSelfPermission(permission);
        return (res == PackageManager.PERMISSION_GRANTED);
    }

    private boolean checkSDisAvailable() {
        String state = Environment.getExternalStorageState();
        return state.equals(Environment.MEDIA_MOUNTED);
    }

    public File getTempImageFile() {
        File path = StorageUtils.getCacheDirectory(activity);
        if (!path.exists()) {
            path.mkdirs();
        }
        File file = new File(path, uuid);
        return file;
    }

    public static File getTakeTempImageFile(Context context, String _uuid) {
        uuid = _uuid;
        File path = StorageUtils.getCacheDirectory(context);
        DebugLog.i("iamge Path:" + path.getPath());
        if (!path.exists()) {
            path.mkdirs();
        }
        File file = new File(path, uuid);

        DebugLog.i("file:" + file.getPath() + ",---->" + uuid);


        return file;
    }

    /**
     * 从相册现在照片
     */
    public void doGalley() {
        if (!checkValide()) {
            return;
        }

        Intent i = new Intent(Intent.ACTION_PICK);
        i.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                MediaStore.Images.Media.CONTENT_TYPE);

        if (i.resolveActivity(activity.getPackageManager()) == null) {
            Toast.makeText(activity, "设备上缺少相册类应用", Toast.LENGTH_SHORT).show();
            return;
        }

        if (fragment != null) {
            activity.startActivityFromFragment(fragment, i, REQ_CODE_PICK_GALLERY);
        } else {
            activity.startActivityForResult(i, REQ_CODE_PICK_GALLERY);
        }
    }

    /**
     *
     */
    public void doCamera() {
        if (!checkValide()) {
            return;
        }
        Intent intent = new Intent(
                MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(
                MediaStore.EXTRA_OUTPUT,
                Uri.fromFile(getTempImageFile()));
        intent.putExtra("return-data", true);

        if (intent.resolveActivity(activity.getPackageManager()) == null) {
            Toast.makeText(activity, "设备上缺少拍照应用", Toast.LENGTH_SHORT).show();
            return;
        }

        if (fragment != null) {
            activity.startActivityFromFragment(fragment, intent, REQ_CODE_PICK_CAMERA);
        } else {
            activity.startActivityForResult(intent, REQ_CODE_PICK_CAMERA);
        }
    }



    public void doFinalProcess() {
        try {
            // sample size 를 적용하여 bitmap load.
            Bitmap bitmap = ImageResizer.decodeSampledBitmapFromFile(
                    getTempImageFile().getPath(), mImageSizeBoundary,
                    mImageSizeBoundary);
            if (bitmap == null) {
                doImageSelectFail();
                return;
            }
            // image boundary size 에 맞도록 이미지 축소.
            bitmap = resizeImageWithinBoundary(bitmap);
            //
            // // 결과 file 을 얻어갈 수 있는 메서드 제공.
            saveBitmapToFile(bitmap);

            if (!bitmap.isRecycled()) {
                DebugLog.i("[bitmap recycle]");
                bitmap.recycle();
            }
            doImageSelectFinalProcess(getTempImageFile());
        } catch (OutOfMemoryError e) {
            DebugLog.e("doFinalProcess()---->outOfMemory"+e.getMessage());
            doImageSelectFail();
        } catch (Exception e) {
            DebugLog.e("选择图片出现了异常:"+e.getMessage());
            doImageSelectFail();
        }

    }

    /**
     * 选择图片后
     */
    public abstract void doImageSelectFinalProcess(File imageFile);

    public abstract void doImageSelectFail();

    private void saveBitmapToFile(Bitmap bitmap) throws Exception {
        File target = getTempImageFile();
        FileOutputStream fos = new FileOutputStream(target, false);
        DebugLog.i("FileOutputStream:" + fos);
        bitmap.compress(Bitmap.CompressFormat.JPEG, 80, fos);
        fos.close();
    }

    /**
     * 이미지 사이즈 수정 후, 카메라 rotation 정보가 있으면 회전 보정함.
     */
    private void correctCameraOrientation(File imgFile) {
        Bitmap bitmap = loadImageWithSampleSize(imgFile);
        if (bitmap == null) {
            doImageSelectFail();

            return;
        }
        try {
            ExifInterface exif = new ExifInterface(imgFile.getAbsolutePath());
            int exifOrientation = exif.getAttributeInt(
                    ExifInterface.TAG_ORIENTATION,
                    ExifInterface.ORIENTATION_NORMAL);
            int exifRotateDegree = exifOrientationToDegrees(exifOrientation);
            bitmap = rotateImage(bitmap, exifRotateDegree);
            saveBitmapToFile(bitmap);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private Bitmap rotateImage(Bitmap bitmap, int degrees) {
        if (degrees != 0 && bitmap != null) {
            Matrix m = new Matrix();
            m.setRotate(degrees, (float) bitmap.getWidth() / 2,
                    (float) bitmap.getHeight() / 2);
            try {
                Bitmap converted = Bitmap.createBitmap(bitmap, 0, 0,
                        bitmap.getWidth(), bitmap.getHeight(), m, true);
                if (bitmap != converted) {
                    bitmap.recycle();
                    bitmap = converted;
                }
            } catch (OutOfMemoryError ex) {
            }
        }
        return bitmap;
    }

    /**
     * EXIF정보를 회전각도로 변환하는 메서드
     *
     * @param exifOrientation EXIF 회전각
     * @return 실제 각도
     */
    private int exifOrientationToDegrees(int exifOrientation) {
        if (exifOrientation == ExifInterface.ORIENTATION_ROTATE_90) {
            return 90;
        } else if (exifOrientation == ExifInterface.ORIENTATION_ROTATE_180) {
            return 180;
        } else if (exifOrientation == ExifInterface.ORIENTATION_ROTATE_270) {
            return 270;
        }
        return 0;
    }

    /**
     * 원하는 크기의 이미지로 options 설정.
     */
    private Bitmap loadImageWithSampleSize(File file) {


        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(file.getAbsolutePath(), options);
        int width = options.outWidth;
        int height = options.outHeight;
        int longSide = Math.max(width, height);
        int sampleSize = 1;
        if (longSide > mMaxImageSize) {
            sampleSize = longSide / mMaxImageSize;
        }
        options.inJustDecodeBounds = false;
        options.inSampleSize = sampleSize;
        options.inPurgeable = true;
        options.inDither = false;

        Bitmap bitmap = BitmapFactory.decodeFile(file.getAbsolutePath(),
                options);
        return bitmap;
    }

    /**
     * mImageSizeBoundary 크기로 이미지 크기 조정. mImageSizeBoundary 보다 작은 경우 resize하지
     * 않음.
     */
    private Bitmap resizeImageWithinBoundary(Bitmap bitmap) {
        if (bitmap == null) return bitmap;
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();

        if (width > height) {
            if (width > mImageSizeBoundary) {
                bitmap = resizeBitmapWithWidth(bitmap, mImageSizeBoundary);
            }
        } else {
            if (height > mImageSizeBoundary) {
                bitmap = resizeBitmapWithHeight(bitmap, mImageSizeBoundary);
            }
        }
        return bitmap;
    }

    private Bitmap resizeBitmapWithHeight(Bitmap source, int wantedHeight) {
        if (source == null)
            return null;

        int width = source.getWidth();
        int height = source.getHeight();

        float resizeFactor = wantedHeight * 1f / height;

        int targetWidth, targetHeight;
        targetWidth = (int) ((width * resizeFactor));
        targetHeight = (int) ((height * resizeFactor) + 0.5f);

        Bitmap resizedBitmap = Bitmap.createScaledBitmap(source, targetWidth,
                targetHeight, true);

        return resizedBitmap;
    }

    private Bitmap resizeBitmapWithWidth(Bitmap source, int wantedWidth) {
        if (source == null)
            return null;

        int width = source.getWidth();
        int height = source.getHeight();

        float resizeFactor = wantedWidth * 1f / width;

        int targetWidth, targetHeight;
        targetWidth = (int) ((width * resizeFactor) + 0.5f);
        targetHeight = (int) ((height * resizeFactor));
        DebugLog.i("resizeBitMap------>targetWidth:" + targetWidth
                + ",----->targetHeight:" + targetHeight);

        Bitmap resizedBitmap = Bitmap.createScaledBitmap(source, targetWidth,
                targetHeight, true);

        return resizedBitmap;
    }

    private void copyUriToFile(Uri srcUri, File target) {
        FileInputStream inputStream = null;
        FileOutputStream outputStream = null;
        FileChannel fcin = null;
        FileChannel fcout = null;
        try {
            // 스트림 생성
            inputStream = (FileInputStream) activity.getContentResolver()
                    .openInputStream(srcUri);//可能有异常，inputStream 为null
            outputStream = new FileOutputStream(target);

            // 채널 생성
            fcin = inputStream.getChannel();
            fcout = outputStream.getChannel();

            // 채널을 통한 스트림 전송
            long size = fcin.size();
            fcin.transferTo(0, size, fcout);
        } catch (Exception e) {
            e.printStackTrace();
            doImageSelectFail();
        } finally {
            try {
                if (fcout != null) {
                    fcout.close();
                }
            } catch (IOException ioe) {
                doImageSelectFail();
            }
            if (fcin != null) {
                try {
                    fcin.close();
                } catch (IOException ioe) {
                    doImageSelectFail();
                }
            }
            try {
                if (outputStream != null) {
                    outputStream.close();
                }
            } catch (IOException ioe) {
                doImageSelectFail();
            }
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException ioe) {
                    doImageSelectFail();
                }
            }
        }
    }



    /**
     * @param data
     */
    public void doPickGallery(Intent data) {
        uuid = UUID.randomUUID().toString() + ".jpg";
        Uri uri = data.getData();
        copyUriToFile(uri, getTempImageFile());

        correctCameraOrientation(getTempImageFile());
        doFinalProcess();
    }

    /**
     * @param data
     */
    public void doPickCamera(Intent data) {
//        uuid = UUID.randomUUID().toString() + ".jpg";
        correctCameraOrientation(getTempImageFile());
        doFinalProcess();
    }
}
