package com.gazetinc.shopninja.Utility;

public class UtilityClass
{
    public static boolean isValidEmail(CharSequence target) {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
    }
}
