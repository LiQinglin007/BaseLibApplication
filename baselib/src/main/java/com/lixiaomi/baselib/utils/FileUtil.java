package com.lixiaomi.baselib.utils;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.webkit.MimeTypeMap;
import android.widget.TextView;

import com.lixiaomi.baselib.config.AppConfigInIt;
import com.lixiaomi.baselib.config.AppConfigType;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * @describe：File工具类<br>
 * @author：Xiaomi<br>
 * @createTime：2018/5/9<br>
 * @remarks：统一修改变量命名规则<br>
 * @changeTime:2019/2/11<br>
 */
public final class FileUtil {

    private static String mPackageName = "";

    static {
        try {
            String configuration = AppConfigInIt.getConfiguration(AppConfigType.BASE_FILE);
            if (!CheckStringEmptyUtils.isEmpty(configuration)) {
                mPackageName = "/" + configuration;
            }
        } catch (NullPointerException e) {
            LogUtils.loge("BaseFile不能为NULL，请先在Application中配置BaseFile.");
        }
    }

    /**
     * 格式化的模板
     */
    private static final String TIME_FORMAT = "_yyyyMMdd_HHmmss";

    private static final String SDCARD_DIR =
            Environment.getExternalStorageDirectory().getPath();
    /**
     * 下载路径
     */
    public static final String DOWNLOAD_FILE_DIR =
            Environment.getExternalStorageDirectory().getPath() + mPackageName + "/a_download/";

    /**
     * 图片路径
     */
    public static final String TAKE_PHOTO_DIR =
            Environment.getExternalStorageDirectory().getPath() + mPackageName + "/a_photo/";

    /**
     * 默认本地上传图片目录
     */
    public static final String UPLOAD_PHOTO_DIR =
            Environment.getExternalStorageDirectory().getPath() + mPackageName + "/a_upload_photos/";

    /**
     * 网页缓存地址
     */
    public static final String WEB_CACHE_DIR =
            Environment.getExternalStorageDirectory().getPath() + mPackageName + "/app_web_cache/";

    /**
     * 系统相机目录
     */
    public static final String CAMERA_PHOTO_DIR =
            Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM).getPath() + "/Camera/";

    private static String getTimeFormatName(String timeFormatHeader) {
        final Date date = new Date(System.currentTimeMillis());
        //必须要加上单引号
        final SimpleDateFormat dateFormat = new SimpleDateFormat("'" + timeFormatHeader + "'" + TIME_FORMAT, Locale.getDefault());
        return dateFormat.format(date);
    }

    /**
     * @param timeFormatHeader 格式化的头(除去时间部分)
     * @param extension        后缀名
     * @return 返回时间格式化后的文件名
     */
    public static String getFileNameByTime(String timeFormatHeader, String extension) {
        return getTimeFormatName(timeFormatHeader) + "." + extension;
    }

    /**
     * 创建文件夹
     *
     * @param sdcardDirName
     * @return
     */
    @SuppressWarnings("ResultOfMethodCallIgnored")
    private static File createDir(String sdcardDirName) {
        //拼接成SD卡中完整的dir
        final String dir = SDCARD_DIR + "/" + sdcardDirName + "/";
        final File fileDir = new File(dir);
        if (!fileDir.exists()) {
            fileDir.mkdirs();
        }
        return fileDir;
    }

    /**
     * 创建文件
     *
     * @param sdcardDirName
     * @param fileName
     * @return
     */
    @SuppressWarnings("ResultOfMethodCallIgnored")
    public static File createFile(String sdcardDirName, String fileName) {
        return new File(createDir(sdcardDirName), fileName);
    }

    private static File createFileByTime(String sdcardDirName, String timeFormatHeader, String extension) {
        final String fileName = getFileNameByTime(timeFormatHeader, extension);
        return createFile(sdcardDirName, fileName);
    }

    /**
     * 获取文件的MIME
     *
     * @param filePath
     * @return
     */
    public static String getMimeType(String filePath) {
        final String extension = getExtension(filePath);
        return MimeTypeMap.getSingleton().getMimeTypeFromExtension(extension);
    }

    /**
     * 获取文件的后缀名
     *
     * @param filePath
     * @return
     */
    public static String getExtension(String filePath) {
        String suffix = "";
        final File file = new File(filePath);
        final String name = file.getName();
        final int idx = name.lastIndexOf('.');
        if (idx > 0) {
            suffix = name.substring(idx + 1);
        }
        return suffix;
    }

    /**
     * 保存Bitmap到SD卡中
     *
     * @param dir      目录名,只需要写自己的相对目录名即可
     * @param compress 压缩比例 100是不压缩,值约小压缩率越高
     * @return 返回该文件
     */
    public static File saveBitmap(Bitmap mBitmap, String dir, int compress) {

        final String sdStatus = Environment.getExternalStorageState();
        // 检测sd是否可用
        if (!sdStatus.equals(Environment.MEDIA_MOUNTED)) {
            return null;
        }
        FileOutputStream fos = null;
        BufferedOutputStream bos = null;
        File fileName = createFileByTime(dir, "DOWN_LOAD", "jpg");
        try {
            fos = new FileOutputStream(fileName);
            bos = new BufferedOutputStream(fos);
            // 把数据写入文件
            mBitmap.compress(Bitmap.CompressFormat.JPEG, compress, bos);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } finally {
            try {

                if (bos != null) {
                    bos.flush();
                }
                if (bos != null) {
                    bos.close();
                }
                //关闭流
                if (fos != null) {
                    fos.flush();
                }
                if (fos != null) {
                    fos.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        refreshDCIM();

        return fileName;
    }

    public static File writeToDisk(InputStream is, String dir, String name) {
        //创建文件
        final File file = FileUtil.createFile(dir, name);
        BufferedInputStream bis = null;
        FileOutputStream fos = null;
        BufferedOutputStream bos = null;

        //通过输入流一个个读取，转换成文件
        try {
            bis = new BufferedInputStream(is);
            fos = new FileOutputStream(file);
            bos = new BufferedOutputStream(fos);

            byte[] data = new byte[1024 * 4];

            int count;
            while ((count = bis.read(data)) != -1) {
                bos.write(data, 0, count);
            }

            bos.flush();
            fos.flush();


        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (bos != null) {
                    bos.close();
                }
                if (fos != null) {
                    fos.close();
                }
                if (bis != null) {
                    bis.close();
                }
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return file;
    }

    /**
     * @param is        输入流
     * @param dir       下载地址
     * @param prefix    文件的扩展名转换成大写
     * @param extension 扩展名
     * @return
     */
    public static File writeToDisk(InputStream is, String dir, String prefix, String extension) {
        //没有文件的名字，就用扩展名和当前的时间来当文件名，并且创建一个文件，然后转换成一个文件，返回回去
        final File file = FileUtil.createFileByTime(dir, prefix, extension);
        //缓冲输入流
        BufferedInputStream bis = null;
        //文件输出流
        FileOutputStream fos = null;
        //缓冲输出流
        BufferedOutputStream bos = null;

        try {
            //先把输入流加入到缓冲输入流
            bis = new BufferedInputStream(is);
            //然后打开文件的输出流
            fos = new FileOutputStream(file);
            //然后打一个缓冲输出流
            bos = new BufferedOutputStream(fos);

            byte[] data = new byte[1024 * 4];
            //然后循环把bis(缓冲输入流)中的数据一个一个写入到bos（缓冲输出流）中，然后提交数据
            int count;
            while ((count = bis.read(data)) != -1) {
                bos.write(data, 0, count);
            }

            bos.flush();
            fos.flush();


        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            //然后关闭输入输出流
            try {
                if (bos != null) {
                    bos.close();
                }
                if (fos != null) {
                    fos.close();
                }
                if (bis != null) {
                    bis.close();
                }
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return file;
    }

    /**
     * 通知系统刷新系统相册，使照片展现出来
     */
    private static void refreshDCIM() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            //兼容android4.4版本，只扫描存放照片的目录
            MediaScannerConnection.scanFile(AppConfigInIt.getApplicationContext(),
                    new String[]{Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM).getPath()},
                    null, null);
        } else {
            //扫描整个SD卡来更新系统图库，当文件很多时用户体验不佳，且不适合4.4以上版本
            AppConfigInIt.getApplicationContext().sendBroadcast(new Intent(Intent.ACTION_MEDIA_MOUNTED, Uri.parse("file://" +
                    Environment.getExternalStorageDirectory())));
        }
    }

    /**
     * 读取raw目录中的文件,并返回为字符串
     */
    public static String getRawFile(int id) {
        final InputStream is = AppConfigInIt.getApplicationContext().getResources().openRawResource(id);
        final BufferedInputStream bis = new BufferedInputStream(is);
        final InputStreamReader isr = new InputStreamReader(bis);
        final BufferedReader br = new BufferedReader(isr);
        final StringBuilder stringBuilder = new StringBuilder();
        String str;
        try {
            while ((str = br.readLine()) != null) {
                stringBuilder.append(str);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                br.close();
                isr.close();
                bis.close();
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return stringBuilder.toString();
    }


    public static void setIconFont(String path, TextView textView) {
        final Typeface typeface = Typeface.createFromAsset(AppConfigInIt.getApplicationContext().getAssets(), path);
        textView.setTypeface(typeface);
    }

    /**
     * 读取assets目录下的文件,并返回字符串
     */
    public static String getAssetsFile(String name) {
        InputStream is = null;
        BufferedInputStream bis = null;
        InputStreamReader isr = null;
        BufferedReader br = null;
        StringBuilder stringBuilder = null;
        final AssetManager assetManager = AppConfigInIt.getApplicationContext().getAssets();
        try {
            is = assetManager.open(name);
            bis = new BufferedInputStream(is);
            isr = new InputStreamReader(bis);
            br = new BufferedReader(isr);
            stringBuilder = new StringBuilder();
            String str;
            while ((str = br.readLine()) != null) {
                stringBuilder.append(str);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (br != null) {
                    br.close();
                }
                if (isr != null) {
                    isr.close();
                }
                if (bis != null) {
                    bis.close();
                }
                if (is != null) {
                    is.close();
                }
                assetManager.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (stringBuilder != null) {
            return stringBuilder.toString();
        } else {
            return null;
        }
    }

    public static String getRealFilePath(final Context context, final Uri uri) {
        if (null == uri) {
            return null;
        }
        final String scheme = uri.getScheme();
        String data = null;
        if (scheme == null) {
            data = uri.getPath();
        } else if (ContentResolver.SCHEME_FILE.equals(scheme)) {
            data = uri.getPath();
        } else if (ContentResolver.SCHEME_CONTENT.equals(scheme)) {
            final Cursor cursor = context.getContentResolver().query(uri, new String[]{MediaStore.Images.ImageColumns.DATA}, null, null, null);
            if (null != cursor) {
                if (cursor.moveToFirst()) {
                    final int index = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
                    if (index > -1) {
                        data = cursor.getString(index);
                    }
                }
                cursor.close();
            }
        }
        return data;
    }
}
