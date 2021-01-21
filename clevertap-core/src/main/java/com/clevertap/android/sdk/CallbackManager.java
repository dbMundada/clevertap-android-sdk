package com.clevertap.android.sdk;

import static com.clevertap.android.sdk.Utils.runOnUiThread;

import androidx.annotation.RestrictTo;
import androidx.annotation.RestrictTo.Scope;
import com.clevertap.android.sdk.displayunits.DisplayUnitListener;
import com.clevertap.android.sdk.displayunits.model.CleverTapDisplayUnit;
import com.clevertap.android.sdk.product_config.CTProductConfigListener;
import com.clevertap.android.sdk.pushnotification.CTPushNotificationListener;
import com.clevertap.android.sdk.pushnotification.amp.CTPushAmpListener;
import java.lang.ref.WeakReference;
import java.util.ArrayList;

@RestrictTo(Scope.LIBRARY)
public class CallbackManager {

    private WeakReference<DisplayUnitListener> displayUnitListenerWeakReference;

    private GeofenceCallback geofenceCallback;

    private CTInboxListener inboxListener;

    private final CleverTapInstanceConfig mConfig;

    private WeakReference<CTProductConfigListener> productConfigListener;

    private CTPushAmpListener pushAmpListener = null;

    private CTPushNotificationListener pushNotificationListener = null;

    CallbackManager(final CleverTapInstanceConfig config) {
        mConfig = config;
    }

    public GeofenceCallback getGeofenceCallback() {
        return geofenceCallback;
    }

    public void setGeofenceCallback(final GeofenceCallback geofenceCallback) {
        this.geofenceCallback = geofenceCallback;
    }

    public CTInboxListener getInboxListener() {
        return inboxListener;
    }

    public void setInboxListener(final CTInboxListener inboxListener) {
        this.inboxListener = inboxListener;
    }

    public WeakReference<CTProductConfigListener> getProductConfigListener() {
        return productConfigListener;
    }

    public void setProductConfigListener(
            final CTProductConfigListener productConfigListener) {
        if (productConfigListener != null) {
            this.productConfigListener = new WeakReference<>(productConfigListener);
        }
    }

    public CTPushAmpListener getPushAmpListener() {
        return pushAmpListener;
    }

    public void setPushAmpListener(final CTPushAmpListener pushAmpListener) {
        this.pushAmpListener = pushAmpListener;
    }

    public CTPushNotificationListener getPushNotificationListener() {
        return pushNotificationListener;
    }

    public void setPushNotificationListener(
            final CTPushNotificationListener pushNotificationListener) {
        this.pushNotificationListener = pushNotificationListener;
    }

    public void setDisplayUnitListener(DisplayUnitListener listener) {
        if (listener != null) {
            displayUnitListenerWeakReference = new WeakReference<>(listener);
        } else {
            mConfig.getLogger().verbose(mConfig.getAccountId(),
                    Constants.FEATURE_DISPLAY_UNIT + "Failed to set - DisplayUnitListener can't be null");
        }
    }

    void _notifyInboxInitialized() {
        if (this.inboxListener != null) {
            this.inboxListener.inboxDidInitialize();
        }
    }

    void _notifyInboxMessagesDidUpdate() {
        if (this.inboxListener != null) {
            Utils.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if (inboxListener != null) {
                        inboxListener.inboxMessagesDidUpdate();
                    }
                }
            });
        }
    }

    /**
     * Notify the registered Display Unit listener about the running Display Unit campaigns
     *
     * @param displayUnits - Array of Display Units {@link CleverTapDisplayUnit}
     */
    void notifyDisplayUnitsLoaded(final ArrayList<CleverTapDisplayUnit> displayUnits) {
        if (displayUnits != null && !displayUnits.isEmpty()) {
            if (displayUnitListenerWeakReference != null && displayUnitListenerWeakReference.get() != null) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        //double check to ensure null safety
                        if (displayUnitListenerWeakReference != null
                                && displayUnitListenerWeakReference.get() != null) {
                            displayUnitListenerWeakReference.get().onDisplayUnitsLoaded(displayUnits);
                        }
                    }
                });
            } else {
                mConfig.getLogger().verbose(mConfig.getAccountId(),
                        Constants.FEATURE_DISPLAY_UNIT + "No registered listener, failed to notify");
            }
        } else {
            mConfig.getLogger()
                    .verbose(mConfig.getAccountId(), Constants.FEATURE_DISPLAY_UNIT + "No Display Units found");
        }
    }

}