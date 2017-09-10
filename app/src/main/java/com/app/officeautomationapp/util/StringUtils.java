package com.app.officeautomationapp.util;

import android.text.Editable;

/**
 * Created by yu on 2017/9/10.
 */

public abstract class StringUtils {

    public static String isEmpty(Editable text)
    {
        try
        {
            if(text.toString() == null)
            {
                return "";
            }
            else
            {
                return text.toString();
            }

        }
        catch (Exception e)
        {
            e.printStackTrace();
            return "";
        }
    }
    public static String isEmpty(CharSequence text)
    {
        try
        {
            if(text.toString() == null)
            {
                return "";
            }
            else
            {
                return text.toString();
            }

        }
        catch (Exception e)
        {
            e.printStackTrace();
            return "";
        }
    }



    public static double parseDouble(String str)
    {
        try{
            if("".equals(str)||str=="")
            {
                return Double.parseDouble("0");
            }
            else {
                return Double.parseDouble(str);
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return Double.parseDouble("0");
        }

    }

}
