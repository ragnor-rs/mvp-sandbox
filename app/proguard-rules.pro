# Add project specific ProGuard rules here.
# By default, the flags in this file are appended to flags specified
# in /Users/Reist/Library/Android/sdk/tools/proguard/proguard-android.txt
# You can edit the include path and order by changing the proguardFiles
# directive in build.gradle.
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
-dontnote android.net.http.**
-dontnote org.apache.http.**
-dontnote com.google.vending.licensing.**
-dontnote com.android.vending.licensing.**
-keepattributes InnerClasses
-keepattributes Signature
-keepattributes Exceptions
-keepattributes *Annotation*
-keepattributes EnclosingMethod
-keep class ** { int title; }
-keep class ** { int icon; }
-keep class ** { android.app.PendingIntent actionIntent; }
-dontnote android.os.Build$VERSION
-dontnote rx.internal.util.PlatformDependent

# Retrofit
-dontwarn retrofit.**
-keep class retrofit.** { *; }
-dontnote retrofit.Platform

# Butter Knife
-keep class butterknife.** { *; }
-dontwarn butterknife.internal.**
-keep class **$$ViewBinder { *; }
-keepclasseswithmembernames class * {
    @butterknife.* <fields>;
}
-keepclasseswithmembernames class * {
    @butterknife.* <methods>;
}

# Retrolambda
-dontwarn java.lang.invoke.*

# Okio
-keep class okio.**
-dontwarn okio.**

# RxJava
-dontwarn sun.misc.**
-keepclassmembers class rx.internal.util.unsafe.*ArrayQueue*Field* {
   long producerIndex;
   long consumerIndex;
}
-keepclassmembers class rx.internal.util.unsafe.BaseLinkedQueueProducerNodeRef {
    rx.internal.util.atomic.LinkedQueueNode producerNode;
}
-keepclassmembers class rx.internal.util.unsafe.BaseLinkedQueueConsumerNodeRef {
    rx.internal.util.atomic.LinkedQueueNode consumerNode;
}
-keep class rx.Subscriber

# OkHttp
-keep class com.squareup.okhttp.** { *; }
-keep interface com.squareup.okhttp.** { *; }
-dontwarn com.squareup.okhttp.**
-dontnote com.squareup.okhttp.internal.Platform

# Android Support
-keep class android.support.v4.**
-keep class android.support.v7.**
-keep class android.support.design.**
-dontnote android.support.v4.text.ICUCompat**
-dontnote android.support.v7.widget.DrawableUtils

# Gson
-keep class com.google.gson.**
-dontnote com.google.gson.internal.UnsafeAllocator
