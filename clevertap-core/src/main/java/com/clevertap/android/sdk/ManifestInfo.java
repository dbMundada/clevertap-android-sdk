package com.clevertap.android.sdk;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.text.TextUtils;
import androidx.annotation.RestrictTo;

@RestrictTo(RestrictTo.Scope.LIBRARY_GROUP)
public class ManifestInfo {

    private static String accountId;

    private static String accountToken;

    private static String accountRegion;

    private static boolean useADID;

    private static boolean appLaunchedDisabled;

    private static String notificationIcon;

    private static ManifestInfo instance;

    private static String excludedActivitiesForInApps;

    private static boolean sslPinning;

    private static boolean backgroundSync;

    private static boolean useCustomID;

    private static String fcmSenderId;

    private static String packageName;

    private static boolean beta;

    private static String intentServiceName;

    private static String xiaomiAppKey;

    private static String xiaomiAppID;

    private final String[] profileKeys;

    public synchronized static ManifestInfo getInstance(Context context) {
        if (instance == null) {
            instance = new ManifestInfo(context);
        }
        return instance;
    }

    private ManifestInfo(Context context) {
        Bundle metaData = null;
        try {
            PackageManager pm = context.getPackageManager();
            ApplicationInfo ai = pm.getApplicationInfo(context.getPackageName(), PackageManager.GET_META_DATA);
            metaData = ai.metaData;
        } catch (Throwable t) {
            // no-op
        }
        if (metaData == null) {
            metaData = new Bundle();
        }
        if (accountId == null) {
            accountId = _getManifestStringValueForKey(metaData, Constants.LABEL_ACCOUNT_ID);
        }
        if (accountToken == null) {
            accountToken = _getManifestStringValueForKey(metaData, Constants.LABEL_TOKEN);
        }
        if (accountRegion == null) {
            accountRegion = _getManifestStringValueForKey(metaData, Constants.LABEL_REGION);
        }
        notificationIcon = _getManifestStringValueForKey(metaData, Constants.LABEL_NOTIFICATION_ICON);
        useADID = "1".equals(_getManifestStringValueForKey(metaData, Constants.LABEL_USE_GOOGLE_AD_ID));
        appLaunchedDisabled = "1".equals(_getManifestStringValueForKey(metaData, Constants.LABEL_DISABLE_APP_LAUNCH));
        excludedActivitiesForInApps = _getManifestStringValueForKey(metaData, Constants.LABEL_INAPP_EXCLUDE);
        sslPinning = "1".equals(_getManifestStringValueForKey(metaData, Constants.LABEL_SSL_PINNING));
        backgroundSync = "1".equals(_getManifestStringValueForKey(metaData, Constants.LABEL_BACKGROUND_SYNC));
        useCustomID = "1".equals(_getManifestStringValueForKey(metaData, Constants.LABEL_CUSTOM_ID));
        fcmSenderId = _getManifestStringValueForKey(metaData, Constants.LABEL_FCM_SENDER_ID);
        if (fcmSenderId != null) {
            fcmSenderId = fcmSenderId.replace("id:", "");
        }
        packageName = _getManifestStringValueForKey(metaData, Constants.LABEL_PACKAGE_NAME);
        beta = "1".equals(_getManifestStringValueForKey(metaData, Constants.LABEL_BETA));
        if (intentServiceName == null) {
            intentServiceName = _getManifestStringValueForKey(metaData, Constants.LABEL_INTENT_SERVICE);
        }

        if (xiaomiAppKey != null) {
            xiaomiAppKey = _getManifestStringValueForKey(metaData, Constants.LABEL_XIAOMI_APP_KEY);
        }

        if (xiaomiAppID != null) {
            xiaomiAppID = _getManifestStringValueForKey(metaData, Constants.LABEL_XIAOMI_APP_ID);
        }

        profileKeys = parseProfileKeys(metaData);
    }

    public String getAccountId() {
        return accountId;
    }

    public String getExcludedActivities() {
        return excludedActivitiesForInApps;
    }

    public String getFCMSenderId() {
        return fcmSenderId;
    }

    public String getIntentServiceName() {
        return intentServiceName;
    }

    public String getNotificationIcon() {
        return notificationIcon;
    }

    public String[] getProfileKeys() {
        return profileKeys;
    }

    public String getXiaomiAppID() {
        return xiaomiAppID;
    }

    public String getXiaomiAppKey() {
        return xiaomiAppKey;
    }

    boolean enableBeta() {
        return beta;
    }

    String getAccountRegion() {
        return accountRegion;
    }

    String getAcountToken() {
        return accountToken;
    }

    String getPackageName() {
        return packageName;
    }

    boolean isAppLaunchedDisabled() {
        return appLaunchedDisabled;
    }

    boolean isBackgroundSync() {
        return backgroundSync;
    }

    boolean isSSLPinningEnabled() {
        return sslPinning;
    }

    boolean useCustomId() {
        return useCustomID;
    }

    boolean useGoogleAdId() {
        return useADID;
    }

    @SuppressWarnings("ConstantConditions")
    private String[] parseProfileKeys(final Bundle metaData) {
        String profileKeyString = _getManifestStringValueForKey(metaData, Constants.CLEVERTAP_IDENTIFIER);
        return !TextUtils.isEmpty(profileKeyString) ? profileKeyString.split(Constants.SEPARATOR_COMMA)
                : Constants.NULL_STRING_ARRAY;
    }

    static void changeCredentials(String id, String token, String region) {
        accountId = id;
        accountToken = token;
        accountRegion = region;
    }

    static void changeXiaomiCredentials(String xiaomiAppID, String xiaomiAppKey) {
        ManifestInfo.xiaomiAppID = xiaomiAppID;
        ManifestInfo.xiaomiAppKey = xiaomiAppKey;
    }

    /**
     * This returns string representation of int,boolean,string,float value of given key
     *
     * @param manifest bundle to retrieve values from
     * @param name     key of bundle
     * @return string representation of int,boolean,string,float
     */
    private static String _getManifestStringValueForKey(Bundle manifest, String name) {
        try {
            Object o = manifest.get(name);
            return (o != null) ? o.toString() : null;
        } catch (Throwable t) {
            return null;
        }
    }
}
