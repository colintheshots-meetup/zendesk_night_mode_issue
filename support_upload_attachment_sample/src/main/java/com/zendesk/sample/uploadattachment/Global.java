package com.zendesk.sample.uploadattachment;

import android.app.Application;

import com.zendesk.logger.Logger;
import com.zendesk.util.StringUtils;

import zendesk.core.AnonymousIdentity;
import zendesk.core.JwtIdentity;
import zendesk.core.Zendesk;
import zendesk.support.Support;

public class Global extends Application {


    private static final String SUBDOMAIN_URL = "https://z3nadvocate84.zendesk.com";
    private static final String APPLICATION_ID = "daf8af259b13b56f4a8d0594dafa9d8b7fcbe9f3934a9940";
    private static final String OAUTH_CLIENT_ID = "mobile_sdk_client_96ccbde88735e328e182";

    private static boolean missingCredentials = false;

    @Override
    public void onCreate() {
        super.onCreate();

        // Enable logging
        Logger.setLoggable(true);

        if (StringUtils.isEmpty(SUBDOMAIN_URL)
                || StringUtils.isEmpty(APPLICATION_ID)
                || StringUtils.isEmpty(OAUTH_CLIENT_ID)) {
            missingCredentials = true;
            return;
        }

        /**
         * Initialize the SDK with your Zendesk subdomain, mobile SDK app ID, and client ID.
         *
         * Get these details from your Zendesk dashboard: Admin -> Channels -> MobileSDK.
         */
        Zendesk.INSTANCE.init(this,
                SUBDOMAIN_URL,
                APPLICATION_ID,
                OAUTH_CLIENT_ID);

        /**
         * Set an identity (authentication).
         *
         * Set either Anonymous or JWT identity, as below:
         */

        // a). Anonymous (All fields are optional)
        Zendesk.INSTANCE.setIdentity(
                new AnonymousIdentity.Builder()
                        .withNameIdentifier("{optional name}")
                        .withEmailIdentifier("{optional email}")
                        .build()
        );

        // b). JWT (Must be initialized with your JWT identifier)
        Zendesk.INSTANCE.setIdentity(new JwtIdentity("{JWT User Identifier}"));

        Support.INSTANCE.init(Zendesk.INSTANCE);
    }

    static boolean isMissingCredentials() {
        return missingCredentials;
    }
}
