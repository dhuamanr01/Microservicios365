package com.ms365.middleware.proyectos.common.constants;

public class Constants {
  final public static String CRLF     = "\r\n";
  final public static String QUOTES   = Character.toString((char) 34);

  final public static class Locale{
    final private static String LANGUAGE_SPANISH = "es";
    final private static String COUNTRY_MEXICO   = "MX";
    final public static java.util.Locale SPANISH = new java.util.Locale(LANGUAGE_SPANISH, COUNTRY_MEXICO);
  }

  final public static class Logger{
    final public static class Class{
      final public static String INITIALIZE  = "Class_Initialize";
      final public static String FINALIZE    = "Class_Finalize";
    }

    final public static class Method{
      final public static String INITIALIZE = "Method_Initialize";
      final public static String FINALIZE   = "Method_Finalize";
    }
  }

  final public static class Format{
    final public static class Date{
      final public static String YEAR                 = "yyyy";
      final public static String MONTH                = "MM";
      final public static String MONTH4               = "MMMM";
      final public static String DAY                  = "dd";
      final public static String YEAR_MONTH_DAY       = "yyyyMMdd";
      final public static String YEAR_MONTH_DAY_SLASH = "yyyy/MM/dd";
      final public static String YEAR_MONTH           = "yyyyMM";
      final public static String DAY_MONTH_YEAR       = "dd/MM/yyyy";
      final public static String MONTH_DAY_YEAR       = "MM/dd/yyyy";
    }

    final public static class Time{
      final public static String HOURS                 = "HH";
      final public static String MINUTES               = "mm";
      final public static String HOURS_MINUTES         = "HH:mm";
      final public static String HOURS_MINUTES_SECONDS = "HH:mm:ss";
      final public static String TIME                  = "HH:mm:ss,SSS";
    }

    final public static class DateTime{
      final public static String DATE_TIME  = "dd/MM/yyyy HH:mm:ss";
      final public static String ISO_8601   = "yyyy-MM-dd'T'HH:mm:ss";
      final public static String TIME_STAMP = "dd/MM/yyyy HH:mm:ss,SSS";
      final public static String STAMP      = "yyyyMMddHHmmssSSS";
    }
  }
}
