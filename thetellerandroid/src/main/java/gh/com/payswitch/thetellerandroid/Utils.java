package gh.com.payswitch.thetellerandroid;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.scottyab.aescrypt.AESCrypt;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.security.GeneralSecurityException;
import java.security.MessageDigest;
import java.text.DecimalFormat;
import java.util.List;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

import javax.crypto.spec.IvParameterSpec;

import static java.lang.Double.parseDouble;

public class Utils {

    private static final String MD5 = "MD5";
    private static final String CHARSET_NAME = "UTF-8";
    private static final String UTF_8 = "utf-8";

    private static String CIPHER_NAME = "AES/CBC/PKCS5PADDING";
    private static int CIPHER_KEY_LEN = 16; //128 bits
    private static String initVector = "fedcba9876543210"; // 16 bytes IV

    public static String getDeviceImei(Context c) {

//        TelephonyManager mTelephonyManager = (TelephonyManager) c.getSystemService(Context.TELEPHONY_SERVICE);
//        if (c.checkSelfPermission(Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
//            c.requestPermissions(c, new String[]{Manifest.permission.READ_PHONE_STATE}, PERMISSION_READ_STATE);            // here to request the missing permissions, and then overriding
//            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
//            //                                          int[] grantResults)
//            // to handle the case where the user grants the permission. See the documentation
//            // for Activity#requestPermissions for more details.
//            return TODO;
//        }
//        String ip = mTelephonyManager.getDeviceId();

//        if (ip == null) {
//            ip = Settings.Secure.getString(c.getContentResolver(), Settings.Secure.ANDROID_ID);
//        }

        return "";
    }

    public static String minorUnitAmount(String amount) {
        Double amtValue = parseDouble(amount);
        Double amtConverted = amtValue * 100;
        int amountConverted = amtConverted.intValue();
        String amountVal = String.valueOf(amountConverted);
        Integer len = amountVal.length();
        String finalAmt;

        switch (len) {
            case 1 :
                finalAmt = "00000000000" + amountVal;
                break;
            case 2 :
                finalAmt = "0000000000" + amountVal;
                break;
            case 3 :
                finalAmt = "000000000" + amountVal;
                break;
            case 4 :
                finalAmt = "00000000" + amountVal;
                break;
            case 5 :
                finalAmt = "0000000" + amountVal;
                break;
            case 6 :
                finalAmt = "000000" + amountVal;
                break;
            case 7 :
                finalAmt = "00000" + amountVal;
                break;
            case 8 :
                finalAmt = "0000" + amountVal;
                break;
            case 9 :
                finalAmt = "000" + amountVal;
                break;
            case 10 :
                finalAmt = "00" + amountVal;
                break;
            case 11 :
                finalAmt = "0" + amountVal;
                break;
            default :
                finalAmt = amountVal;

        }

        return finalAmt;
    }

    public static void hide_keyboard(Activity activity) {
        InputMethodManager inputMethodManager = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
        View view = activity.getCurrentFocus();
        if (view == null) {
            view = new View(activity);
        }
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    public static boolean isEmailValid(String email) {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    public static String convertChargeRequestPayloadToJson(Payload body) {

        Gson gson = new Gson();
        Type type = new TypeToken<Payload>() {}.getType();
        return gson.toJson(body, type);
    }

    public static List<Meta> pojofyMetaString(String meta) {
        try {
            Gson gson = new Gson();
            Type type = new TypeToken<List<Meta>>() {
            }.getType();
            return gson.fromJson(meta, type);
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String stringifyMeta(List<Meta> meta) {
        Gson gson = new Gson();
        Type type = new TypeToken<List<Meta>>() {}.getType();
        return gson.toJson(meta, type);
    }

    public static String getEncryptedData(String unEncryptedString, String secret) {
        Log.d("sd",unEncryptedString);
        try {
            // hash the secret
//            String md5Hash = getMd5(secret);
//            String cleanSecret = secret;
//            int hashLength = md5Hash.length();
//            String encryptionKey = cleanSecret.substring(0, 12).concat(md5Hash.substring(hashLength - 12, hashLength));

            return encrypt(secret, initVector, unEncryptedString);
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    public static String encryptRef(String key, String ref) {
        try {
            return AESCrypt.encrypt(key, ref);
        }catch (GeneralSecurityException e){
            return null;
        }
    }

    public static String decryptRef(String key, String encryptedRef) {
        try {
            return AESCrypt.decrypt(key, encryptedRef);
        }catch (GeneralSecurityException e){
            return null;
        }
    }

//    private static String encrypt(String data, String key) throws Exception {
//        byte[] keyBytes = key.getBytes(UTF_8);
//        SecretKeySpec skey = new SecretKeySpec(keyBytes, ALGORITHM);
//        Cipher cipher = Cipher.getInstance(TRANSFORMATION);
//
//        cipher.init(Cipher.ENCRYPT_MODE, skey);
//        byte[] plainTextBytes = data.getBytes(UTF_8);
//        byte[] buf = cipher.doFinal(plainTextBytes);
//        return Base64.encodeToString(buf, Base64.DEFAULT);
//
//    }

    /**
     * Encrypt data using AES Cipher (CBC) with 128 bit key
     *
     *
     * @param key  - key to use should be 16 bytes long (128 bits)
     * @param iv - initialization vector
     * @param data - data to encrypt
     * @return encryptedData data in base64 encoding with iv attached at end after a :
     */


    /**
     * Decrypt data using AES Cipher (CBC) with 128 bit key
     *
     * @param key - key to use should be 16 bytes long (128 bits)
     * @param data - encrypted data with iv at the end separate by :
     * @return decrypted data string
     */

    private static String encrypt(String key, String iv, String data) {
        try {
            if (key.length() < CIPHER_KEY_LEN) {
                int numPad = CIPHER_KEY_LEN - key.length();

                for(int i = 0; i < numPad; i++){
                    key += "0"; //0 pad to len 16 bytes
                }

            } else if (key.length() > CIPHER_KEY_LEN) {
                key = key.substring(0, CIPHER_KEY_LEN); //truncate to 16 bytes
            }


            IvParameterSpec initVector = new IvParameterSpec(iv.getBytes("ISO-8859-1"));
            SecretKeySpec skeySpec = new SecretKeySpec(key.getBytes("ISO-8859-1"), "AES");

            Cipher cipher = Cipher.getInstance(CIPHER_NAME);
            cipher.init(Cipher.ENCRYPT_MODE, skeySpec, initVector);

            byte[] encryptedData = cipher.doFinal((data.getBytes()));

            String base64_EncryptedData = new String(Base64.encodeToString(encryptedData, Base64.DEFAULT));
            String base64_IV = new String(Base64.encodeToString(iv.getBytes("ISO-8859-1"), Base64.DEFAULT));

            return base64_EncryptedData + ":" + base64_IV;

        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return null;
    }

    public static String decrypt(String key, String data) {
        try {

            if (key.length() < CIPHER_KEY_LEN) {
                int numPad = CIPHER_KEY_LEN - key.length();

                for(int i = 0; i < numPad; i++){
                    key += "0"; //0 pad to len 16 bytes
                }

            } else if (key.length() > CIPHER_KEY_LEN) {
                key = key.substring(0, CIPHER_KEY_LEN); //truncate to 16 bytes
            }

            String[] parts = data.split(":");

            IvParameterSpec iv = new IvParameterSpec(Base64.decode(parts[1], Base64.DEFAULT));
            SecretKeySpec skeySpec = new SecretKeySpec(key.getBytes("ISO-8859-1"), "AES");

            Cipher cipher = Cipher.getInstance(CIPHER_NAME);
            cipher.init(Cipher.DECRYPT_MODE, skeySpec, iv);

            byte[] decodedEncryptedData = Base64.decode(parts[0], Base64.DEFAULT);

            byte[] original = cipher.doFinal(decodedEncryptedData);

            return new String(original);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return null;
    }

    private static String getMd5(String md5) throws Exception {
        MessageDigest md = MessageDigest.getInstance(MD5);
        byte[] array = md.digest(md5.getBytes(CHARSET_NAME));
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < array.length; ++i) {
            sb.append(Integer.toHexString((array[i] & 0xFF) | 0x100).substring(1, 3));
        }
        return sb.toString();
    }

    public static String obfuscateCardNumber(String first6, String last4) {

        int cardNoLength = first6.length() + last4.length();
        if (cardNoLength < 10) {
            return first6 + last4;
        }
        else {

            int othersLength = 6;

            String exes = "";
            for (int i = 0; i < othersLength; i++) {
                exes += "X";
            }
            return first6 + exes + last4;
        }
    }

    public static String spacifyCardNumber(String cardNo) {

        cardNo = cardNo.replaceAll("\\s", "");
        String spacified = "";

        int len = cardNo.length();

        int nChunks = len/4;
        int rem = len%4;


        for (int i = 0; i < nChunks; i++) {
            spacified += cardNo.substring(i * 4, (i * 4) + 4) + " ";
        }


        spacified += cardNo.substring(nChunks * 4, (nChunks * 4) + rem);

        return spacified;

    }
}