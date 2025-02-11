# Add project specific ProGuard rules here.
# By default, the flags in this file are appended to flags specified
# in /Library/android-sdk-macosx/tools/proguard/proguard-android.txt
# You can edit the include path and order by changing the ProGuard
# include property in project.properties.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# Add any project specific keep options here:

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}


# okhttp3 with glide
-keep class com.bumptech.glide.integration.okhttp3.OkHttpGlideModule

-dontwarn com.squareup.okhttp.**


# custom glide module
-keep public class * implements com.bumptech.glide.module.GlideModule
# for DexGuard only
-keepresourcexmlelements manifest/application/meta-data@value=GlideModule