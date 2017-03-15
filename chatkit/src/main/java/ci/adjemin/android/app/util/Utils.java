package ci.adjemin.android.app.util;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.Settings;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextPaint;
import android.text.TextUtils;
import android.text.style.MetricAffectingSpan;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;


import java.io.*;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by angebagui on 15/02/2016.
 */
public class Utils {

    public static String get(int number){
        if (number<10){
            return "0"+number;
        }else {
            return String.valueOf(number);
        }
    }
    public static String getHalfdayOfDay(int number){
        return null;
    }

    // O8 78 26 29

    public Utils() {
    }

    static String deviceInfo = "";
    static enumDensity density = null;
    static HashMap<String, String> deviceInfoParams;

    /**
     * Copy an InputStream to an OutputStream
     *
     * @param is InputStream
     * @param os OutputStream
     */
    public static void CopyStream(InputStream is, OutputStream os) throws IOException {
        final int buffer_size = 1024;
        byte[] bytes = new byte[buffer_size];
        for (; ; ) {
            int count = is.read(bytes, 0, buffer_size);
            if (count == -1)
                break;
            os.write(bytes, 0, count);
        }
    }

    /**
     * Convert an integer array to csv format
     *
     * @param array Input array
     * @return csv
     */
    public static String intArrayToCSV(int[] array) {
        String result = "";
        for (int item : array) {
            if (result != "")
                result += ",";
            result += String.valueOf(item);
        }
        return result;
    }

    /**
     * Exit with kill process
     */
    public static void exit() {
        android.os.Process.killProcess(android.os.Process.myPid());
    }

    /**
     * Exit application
     *
     * @param activity
     */
    public static void exit(Activity activity) {
        exit((Context) activity);
        System.exit(0);

    }

    /**
     * Exit with finish context
     *
     * @param context Context of activity of an activity
     */
    public static void exit(Context context) {
        Activity activity = (Activity) context;
        activity.finish();
    }

/*    public static void loadBitmapFromFresco(Context context, String url , DataSubscriber dataSubscriber){

        ImagePipeline imagePipeline = Fresco.getImagePipeline();
        ImageRequest imageRequest =  ImageRequestBuilder
                .newBuilderWithSource(Uri.parse(url))
                .setRequestPriority(Priority.HIGH)
                .setLowestPermittedRequestLevel(ImageRequest.RequestLevel.FULL_FETCH)
                .build();

        DataSource dataSource =
                imagePipeline.fetchDecodedImage(imageRequest, context);

        try {

            dataSource.subscribe(dataSubscriber, CallerThreadExecutor.getInstance());

        }finally {

            dataSource.close();
        }
    }*/

    public static File uriToFile(Context context, Uri uri){
        try {
            InputStream inputStream = context.getContentResolver().openInputStream(uri);
            File file = new File("YOUR FILE");
            copyInputStreamToFile(inputStream,file);
            return file;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;

    }
    private static void copyInputStreamToFile(InputStream in, File file) throws IOException {
        OutputStream out = new FileOutputStream(file);
        byte[] buf = new byte[10 * 1024];
        int len;
        while ((len = in.read(buf)) > 0) {
            out.write(buf, 0, len);
        }
        out.close();
        in.close();
    }
    public static File createTempImageFile(Context context) {
        //return new File(context.getExternalFilesDir(Environment.DIRECTORY_PICTURES), System.currentTimeMillis() + "_image.jpeg");
        try {
            return createImageFile(context);
        } catch (IOException e) {
            e.printStackTrace();
            Log.e(Utils.class.getSimpleName(),"Error Found ",e);
            return null;

        }
    }
    private static File createImageFile(Context context) throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault())
                .format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File file =File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir);     /* directory */
        file.setWritable(true);
        file.setReadable(true);

        return file;
    }

    public static double getDistanceBetweenLocations(double originLat, double originLng,double destinationLat, double destinationLng ){

        int radiusOfEarth = 6371; // Km

        double dLat = Math.toRadians(destinationLat - originLat);
        double dLng = Math.toRadians(destinationLng - originLng);

        double a =  Math.sin(dLat / 2) * Math.sin(dLat / 2) + Math.cos(Math.toRadians(originLat)) * Math.cos(Math.toRadians(destinationLat)) * Math.sin(dLng / 2) * Math.sin(dLng / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

        return radiusOfEarth * c;
    }

    /**
     * Check if network is available
     *
     * @param context Application context
     * @return
     */
    public static boolean isConnected(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager
                .getActiveNetworkInfo();
        return activeNetworkInfo != null;
    }

    /**
     * Making a string of currency and convert it to a format with separators
     *
     * @param value
     * @return
     */
    public static String moneySeparator(String value) {
        return moneySeparator(value, ".");
    }

    /**
     * Making a string of currency and convert it to a format with separators with specific separator
     *
     * @param value     Money value
     * @param separator Your specific separator
     * @return
     */
    public static String moneySeparator(String value, String separator) {
        String result ;
        if(value != null && value.length()>3){
            int len = value.length();
            int loop = (len / 3);

            int start = 0;
            int end = len - (loop * 3);

            result = value.substring(start, end);

            for (int i = 0; i < loop; i++) {
                start = end;
                end += 3;
                if (result.equals(""))
                    result = value.substring(start, end);
                else
                    result = result + separator + value.substring(start, end);
            }
        }else {
            result = value;
        }


        return result;
    }

    /**
     * Get Device model
     *
     * @return
     */
    public static String getMobileModel() {
        // Device model
        return Build.MODEL;
    }

    /**
     * Get Device Manufacturer
     *
     * @return
     */
    public static String getMobileManufacturer() {
        // Device model
        return Build.MANUFACTURER;
    }

    /**
     * Get Device product
     *
     * @return
     */
    public static String getMobileProduct() {
        // Device model
        return Build.PRODUCT;
    }

    /**
     * Get Device fingerprint
     *
     * @return
     */
    public static String getMobileFingerprint() {
        // Device model
        return Build.FINGERPRINT;
    }

    /**
     * Get Device ID
     *
     * @return
     */
    public static String getMobileId() {
        // Device model
        return Build.ID;
    }

    /**
     * Get Device Android version
     *
     * @return
     */
    public static String getAndroidVersion() {
        // Android version
        return Build.VERSION.RELEASE;
    }

    /**
     * Get Device Android version integer
     *
     * @return
     */
    public static int getAndroidVersionInt() {
        // Android version
        return Build.VERSION.SDK_INT;
    }


    /**
     * Get Display Width
     *
     * @param context Application context
     * @return
     */
    @SuppressLint("NewApi")
    @SuppressWarnings("deprecation")
    public static int getDisplayWidth(Context context) {
        Activity activity = (Activity) context;
        if (Integer.valueOf(Build.VERSION.SDK_INT) < 13) {
            Display display = activity.getWindowManager()
                    .getDefaultDisplay();
            return display.getWidth();
        } else {
            Display display = activity.getWindowManager()
                    .getDefaultDisplay();
            Point size = new Point();
            display.getSize(size);
            return size.x;
        }
    }

    /**
     * Get display height
     *
     * @param context Application context
     * @return
     */
    @SuppressLint("NewApi")
    @SuppressWarnings("deprecation")
    public static int getDisplayHeight(Context context) {
        Activity activity = (Activity) context;
        if (Integer.valueOf(Build.VERSION.SDK_INT) < 13) {
            Display display = activity.getWindowManager()
                    .getDefaultDisplay();
            return display.getHeight();
        } else {
            Display display = activity.getWindowManager()
                    .getDefaultDisplay();
            Point size = new Point();
            display.getSize(size);
            return size.y;
        }
    }

    /**
     * Get Application name
     *
     * @param context Application context
     * @return
     * @throws PackageManager.NameNotFoundException
     */
    public static String getApplicationName(Context context)
            throws PackageManager.NameNotFoundException {
        // Application version
        PackageInfo pInfo = context.getPackageManager().getPackageInfo(
                context.getPackageName(), 0);
        return pInfo.packageName;

    }

    /**
     * Get Application version name
     *
     * @param context Application context
     * @return
     * @throws PackageManager.NameNotFoundException
     */
    public static String getApplicationVersionName(Context context) throws PackageManager.NameNotFoundException {
        // Application version
        PackageInfo pInfo = context.getPackageManager().getPackageInfo(
                context.getPackageName(), 0);
        return pInfo.versionName;
    }

    /**
     * Get application version code
     *
     * @param context Application context
     * @return
     * @throws PackageManager.NameNotFoundException
     */
    public static int getApplicationVersionCode(Context context) throws PackageManager.NameNotFoundException {
        // Application version
        PackageInfo pInfo = null;
        pInfo = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
        return pInfo.versionCode;
    }

    /**
     * Get android ID
     *
     * @param context Application context
     * @return
     */
    public static String getAndroidID(Context context) {

        String m_szAndroidID = Settings.Secure.getString(context.getContentResolver(),
                Settings.Secure.ANDROID_ID);
        return m_szAndroidID;
    }
    /**
     * Getting a full list of data from device
     *
     * @param context Application context
     * @return
     * @throws UnsupportedEncodingException
     * @throws PackageManager.NameNotFoundException
     */
    public static HashMap<String, String> getDeviceInfoParamsForUrl(Context context) throws UnsupportedEncodingException, PackageManager.NameNotFoundException {
        if (deviceInfoParams == null) {
            deviceInfoParams = new HashMap<>();

            deviceInfoParams.put("androidVersionName", Utils.getAndroidVersion());
            deviceInfoParams.put("androidVersionId", Utils.getAndroidVersionInt() + "");
            deviceInfoParams.put("androidId", Utils.getAndroidID(context));
            deviceInfoParams.put("mobileModel", Utils.getMobileModel());
            deviceInfoParams.put("mobileManufacturer", Utils.getMobileManufacturer());
            deviceInfoParams.put("mobileId", Utils.getMobileId());
            deviceInfoParams.put("mobileProduct", Utils.getMobileProduct());
            deviceInfoParams.put("applicationName", Utils.getApplicationName(context));
            deviceInfoParams.put("applicationVersionName", Utils.getApplicationVersionName(context));
            deviceInfoParams.put("applicationVersionCode", Utils.getApplicationVersionCode(context) + "");
            deviceInfoParams.put("screenWidth", Utils.getDisplayWidth(context) + "");
            deviceInfoParams.put("screenHeight", Utils.getDisplayWidth(context) + "");
            deviceInfoParams.put("screenDensity", Utils.getDisplayDensity(context) + "");
            deviceInfoParams.put("screenDensityName", Utils.getDisplaySize(context).toString());
            deviceInfoParams.put("atdPackages", Utils.getKarinaPackages(context));
        }
        return new HashMap<>(deviceInfoParams);
    }

    /**
     * Checking a service is running or not
     *
     * @param context   Application context
     * @param myService Set your service class
     * @return
     */
    public static boolean isServiceRunning(Context context, Class<?> myService) {
        ActivityManager manager = (ActivityManager) context
                .getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager
                .getRunningServices(Integer.MAX_VALUE)) {
            if (myService.getName().equals(service.service.getClassName())) {
                return true;
            }
        }
        return false;
    }

    /**
     * Get Display density
     *
     * @param context Application context
     * @return
     */
    public static int getDisplayDensity(Context context) {
        DisplayMetrics metrics = context.getResources().getDisplayMetrics();
        return metrics.densityDpi;
    }

    /**
     * Get display size
     *
     * @param context Application context
     * @return
     */
    public static enumDensity getDisplaySize(Context context) {
        if (density == null) {
            DisplayMetrics metrics = context.getResources().getDisplayMetrics();
            density = enumDensity.fromFloat(metrics.density);
        }
        return density;
    }

    /**
     * Get all applications of karina in device
     *
     * @param context Application context
     * @return
     */
    public static String getKarinaPackages(Context context) {
        final PackageManager pm = context.getPackageManager();
        // get a list of installed apps.
        List<PackageInfo> packages = pm
                .getInstalledPackages(PackageManager.GET_META_DATA);
        // .getInstalledApplications(PackageManager.GET_META_DATA);

        String result = "";

        for (PackageInfo packageInfo : packages) {
            if (packageInfo.packageName.contains("karina")) {
                if (TextUtils.isEmpty(result))
                    result = packageInfo.packageName + "("
                            + packageInfo.versionCode + ")";
                else
                    result += ", " + packageInfo.packageName + "("
                            + packageInfo.versionCode + ")";
            }
        }
        return result;
    }

    /**
     * Making empty all application data in cache
     *
     * @param context Application context
     * @return
     */
    public static Boolean emptyAllApplicationData(Context context) {
        File cache = context.getCacheDir();
        File appDir = new File(cache.getParent());

        if (appDir.exists()) {
            String[] children = appDir.list();
            for (String s : children) {
                if (!s.equals("databases")) {
                    deleteDir(new File(appDir, s));
                }
            }
        }

        return true;
    }

    /**
     * Delete directory and subdirectory
     *
     * @param dir File dir address
     * @return
     */
    public static boolean deleteDir(File dir) {
        if (dir != null & dir.isDirectory()) {
            String[] children = dir.list();

            for (int i = 0; i < children.length; i++) {
                boolean success = deleteDir(new File(dir, children[i]));
                if (!success)
                    return false;
            }
        }
        return dir.delete();
    }

    /**
     * Checking install package permission
     *
     * @param context Application context
     * @return
     */
    public static boolean checkInstallPackagesPermission(Context context) {
        String permission = "android.permission.INSTALL_PACKAGES";
        int res = context.checkCallingOrSelfPermission(permission);
        return (res == PackageManager.PERMISSION_GRANTED);
    }

    /**
     * Checking write sms permission
     *
     * @param context Application context
     * @return
     */
    public static boolean checkWriteSMSPermission(Context context) {
        String permission = "android.permission.WRITE_SMS";
        int res = context.checkCallingOrSelfPermission(permission);
        return (res == PackageManager.PERMISSION_GRANTED);
    }

    /**
     * Checking read sms permission
     *
     * @param context Application context
     * @return
     */
    public static boolean checkReadSMSPermission(Context context) {
        String permission = "android.permission.READ_SMS";
        int res = context.checkCallingOrSelfPermission(permission);
        return (res == PackageManager.PERMISSION_GRANTED);
    }

    /**
     * Checking if a string number or not
     *
     * @param value string value
     * @return
     */
    public static boolean isNumber(String value) {
        try {
            Double.parseDouble(value);
        } catch (NumberFormatException nfe) {
            return false;
        }
        return true;
    }

    /**
     * Getting all installed packages
     *
     * @param context
     * @return
     */
    public static List<PackageInfo> getAllInstalledApplication(Context context) {
        final PackageManager pm = context.getPackageManager();
        // get a list of installed apps.
        List<PackageInfo> packages = pm
                .getInstalledPackages(PackageManager.GET_META_DATA);

        return packages;
    }

    public static String getFrontPackageName(Context ctx) {

        ActivityManager activityManager = (ActivityManager) ctx.getSystemService(Context.ACTIVITY_SERVICE);

        String str = "";
        List<ActivityManager.RunningAppProcessInfo> processes = activityManager.getRunningAppProcesses();

        str = processes.get(0).processName;

        return str;
    }

    public static boolean isMyServiceRunning(Context ctx, String serviceClassName) {
        final ActivityManager activityManager = (ActivityManager) ctx.getSystemService(Context.ACTIVITY_SERVICE);
        final List<ActivityManager.RunningServiceInfo> services = activityManager.getRunningServices(Integer.MAX_VALUE);

        for (ActivityManager.RunningServiceInfo runningServiceInfo : services) {
            if (runningServiceInfo.service.getClassName().equalsIgnoreCase(serviceClassName)) {
                return true;
            }
        }
        return false;
    }

    private boolean isMyServiceRunning(Context ctx, Class<?> serviceClass) {
        ActivityManager manager = (ActivityManager) ctx.getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (serviceClass.getName().equals(service.service.getClassName())) {
                return true;
            }
        }
        return false;
    }

    /**
     * Get IP address from first non-localhost interface
     *
     * @param useIPv4 true=return ipv4, false=return ipv6
     * @return address or empty string
     */
    public static String getIPAddress(boolean useIPv4) {
        try {
            List<NetworkInterface> interfaces = Collections.list(NetworkInterface.getNetworkInterfaces());
            for (NetworkInterface intf : interfaces) {
                List<InetAddress> addrs = Collections.list(intf.getInetAddresses());
                for (InetAddress addr : addrs) {
                    if (!addr.isLoopbackAddress()) {
                        String sAddr = addr.getHostAddress();
                        //boolean isIPv4 = InetAddressUtils.isIPv4Address(sAddr);
                        boolean isIPv4 = sAddr.indexOf(':') < 0;

                        if (useIPv4) {
                            if (isIPv4)
                                return sAddr;
                        } else {
                            if (!isIPv4) {
                                int delim = sAddr.indexOf('%'); // drop ip6 zone suffix
                                return delim < 0 ? sAddr.toUpperCase() : sAddr.substring(0, delim).toUpperCase();
                            }
                        }
                    }
                }
            }
        } catch (Exception ex) {
        } // for now eat exceptions
        return "";
    }

    /**
     * check if current device is Tablet
     *
     * @param context Application context
     * @return isTablet
     */
    public static boolean isTablet(Context context) {
        return (context.getResources().getConfiguration().screenLayout
                & Configuration.SCREENLAYOUT_SIZE_MASK)
                >= Configuration.SCREENLAYOUT_SIZE_LARGE;
    }

    /**
     * check if current device state is Portrait
     *
     * @param context Application context
     * @return isPortrait
     */
    public static boolean isPortrait(Context context) {
        return context.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT;
    }

    /**
     * get device current timestamp
     *
     * @return isPortrait
     */
    public static long getCurrentTimestamp() {
        return System.currentTimeMillis() / 1000;
    }

    /**
     * Enumeration of standard density of displays
     */
    public static enum enumDensity {
        xxxhdpi(4.0f), xxhdpi(3.0f), xhdpi(2.0f), hdpi(1.5f), tvdpi(1.33f), mdpi(
                1.0f), ldpi(0.75f);

        private Float value;

        enumDensity(Float v) {
            setValue(v);
        }

        public static enumDensity fromFloat(Float v) {
            if (v != null) {
                for (enumDensity s : enumDensity.values()) {
                    if (v.equals(s.getValue())) {
                        return s;
                    }
                }
                return enumDensity.xxxhdpi;
            }
            return null;
        }

        public Float getValue() {
            return value;
        }

        public void setValue(Float value) {
            this.value = value;
        }
    }

    /**
     * Apply typeface to a plane text and return spannableString
     *
     * @param text     Text that you want to apply typeface
     * @param typeface Typeface that you want to apply to your text
     * @return spannableString
     */
    public static SpannableString applyTypefaceToString(String text, final Typeface typeface) {
        SpannableString spannableString = new SpannableString(text);
        spannableString.setSpan(new MetricAffectingSpan() {
                                    @Override
                                    public void updateMeasureState(TextPaint p) {
                                        p.setTypeface(typeface);

                                        // Note: This flag is required for proper typeface rendering
                                        p.setFlags(p.getFlags() | Paint.SUBPIXEL_TEXT_FLAG);
                                    }

                                    @Override
                                    public void updateDrawState(TextPaint tp) {
                                        tp.setTypeface(typeface);

                                        // Note: This flag is required for proper typeface rendering
                                        tp.setFlags(tp.getFlags() | Paint.SUBPIXEL_TEXT_FLAG);
                                    }
                                }, 0, spannableString.length(),
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        return spannableString;
    }
}
