# Add project specific ProGuard rules here.
# You can control the set of applied configuration files using the
# proguardFiles setting in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}

# Uncomment this to preserve the line number information for
# debugging stack traces.
#-keepattributes SourceFile,LineNumberTable

# If you keep the line number information, uncomment this to
# hide the original source file name.
#-renamesourcefileattribute SourceFile
-assumenosideeffects class android.util.Log {
    public static boolean isLoggable(java.lang.String, int);
    public static int v(...);
    public static int i(...);
    public static int w(...);
    public static int d(...);
    public static int e(...);
}

-keepnames public class * extends androidx.fragment.app.Fragment
-keepnames public class * extends com.google.android.material.appbar.AppBarLayout.*
-keepnames abstract class com.google.android.material.appbar.HeaderBehavior
-keepclassmembers class com.google.android.material.appbar.HeaderBehavior {
    private java.lang.Runnable flingRunnable;
    android.widget.OverScroller scroller;
}
-keep class androidx.navigation** { *; }

-keep class * extends java.util.ListResourceBundle { *; }

-keepnames class * implements android.os.Parcelable {
    public static final ** CREATOR;
}
-keepclassmembers class * implements android.os.Parcelable {
    static ** CREATOR;
}

-keep class okhttp3.** { *; }
-dontwarn okhttp3.**

-keep class retrofit2.** { *; }
-dontwarn retrofit2.**

-keep class com.google.android.gms.** { *; }

-keepattributes LineNumberTable,SourceFile
-renamesourcefileattribute SourceFile
-keep public class * extends java.lang.Exception

-keepattributes InnerClasses -keep class **.R -keep class **.R$* { <fields>; }
-keepnames @dagger.hilt.android.lifecycle.HiltViewModel class * extends androidx.lifecycle.ViewModel
-keep,allowobfuscation,allowshrinking class kotlin.coroutines.Continuation

-keepclassmembers class com.example.weatherapp.network.responseClass.** {
    @com.google.gson.annotations.SerializedName <fields>;
}

-keepattributes *Annotation*

-keepclassmembers class * {
    public static final java.lang.reflect.Type[] $VALUES;
}

-keepnames class com.example.weatherapp.network.responseClass.** { *; }

-keep class com.google.gson.** { *; }
-dontwarn com.google.gson.**

-keep class retrofit2.converter.gson.GsonConverterFactory { *; }