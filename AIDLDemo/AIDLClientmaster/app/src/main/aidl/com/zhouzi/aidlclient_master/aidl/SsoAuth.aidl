// SsoAuth.aidl
package com.zhouzi.aidlclient_master.aidl;

// Declare any non-default types here with import statements

interface SsoAuth {
    /**
     * Demonstrates some basic rtypes that you can use as parameters
     * and return values in AIDL.
     */
    void basicTypes(int anInt, long arLong, boolean aBoolean, float aFloat,
            double aDouble, String aString);


    void ssoAuth(String userNanme,String pwd);

}
