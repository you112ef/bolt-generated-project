# Keep models and Room entities by default
-keep class androidx.room.** { *; }
-keep @androidx.room.* class * { *; }
-dontwarn javax.annotation.**