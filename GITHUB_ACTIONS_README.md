# 🚀 بناء تطبيق Android APK باستخدام GitHub Actions

هذا الدليل يوضح كيفية إعداد GitHub Actions لبناء تطبيق Android APK تلقائياً.

## 📋 المتطلبات

- مشروع Android صالح
- مستودع GitHub
- إعدادات Gradle صحيحة

## 🔧 الملفات المطلوبة

### 1. ملف Workflow الرئيسي
تم إنشاء ملف `.github/workflows/android-release.yml` الذي يقوم بـ:
- بناء Release APK
- رفع APK كـ artifact
- دعم التوقيع (اختياري)

### 2. إعدادات التوقيع في build.gradle.kts
تم تحديث ملف `app/build.gradle.kts` لدعم:
- توقيع Release APK
- استخدام متغيرات البيئة للتوقيع
- fallback إلى debug keystore

## 🚀 كيفية الاستخدام

### الخطوة 1: دفع الكود
```bash
git add .
git commit -m "إضافة GitHub Actions workflow"
git push origin main
```

### الخطوة 2: مراقبة البناء
1. انتقل إلى تبويب **Actions** في مستودع GitHub
2. ستجد workflow جديد باسم "Build Release APK"
3. انتظر حتى يكتمل البناء

### الخطوة 3: تحميل APK
بعد اكتمال البناء:
1. انقر على workflow المكتمل
2. في أسفل الصفحة، ستجد قسم **Artifacts**
3. انقر على **app-release** لتحميل APK

## 🔐 إعداد التوقيع (اختياري)

للتوقيع بـ keystore خاص:

### 1. إنشاء Keystore
```bash
keytool -genkey -v -keystore my-release-key.keystore -alias my-key-alias -keyalg RSA -keysize 2048 -validity 10000
```

### 2. إضافة Secrets في GitHub
انتقل إلى **Settings** → **Secrets and variables** → **Actions** وأضف:

| Secret Name | القيمة |
|-------------|---------|
| `ANDROID_KEYSTORE` | محتوى ملف keystore (base64) |
| `ANDROID_KEYSTORE_PASSWORD` | كلمة مرور keystore |
| `ANDROID_KEY_ALIAS` | اسم المفتاح |
| `ANDROID_KEY_PASSWORD` | كلمة مرور المفتاح |

### 3. تحويل Keystore إلى Base64
```bash
base64 -i my-release-key.keystore | tr -d '\n' > keystore-base64.txt
```

## 📱 أنواع البناء

### Debug APK
- يستخدم workflow موجود: `android-ci.yml`
- يبني debug version
- مناسب للاختبار

### Release APK
- يستخدم workflow جديد: `android-release.yml`
- يبني release version
- يدعم التوقيع
- مناسب للنشر

## 🔍 استكشاف الأخطاء

### مشاكل شائعة:
1. **فشل في تثبيت Android SDK**
   - تأكد من وجود مساحة كافية
   - تحقق من اتصال الإنترنت

2. **فشل في بناء Gradle**
   - تحقق من `build.gradle.kts`
   - تأكد من صحة التبعيات

3. **مشاكل في التوقيع**
   - تحقق من صحة Secrets
   - تأكد من صحة keystore

## 📚 موارد إضافية

- [GitHub Actions Documentation](https://docs.github.com/en/actions)
- [Android Gradle Plugin](https://developer.android.com/studio/build)
- [Android App Signing](https://developer.android.com/studio/publish/app-signing)

## 🎯 النتيجة النهائية

بعد الإعداد الصحيح، ستحصل على:
- ✅ بناء تلقائي عند كل push
- ✅ APK مُوقَّع (اختياري)
- ✅ رفع تلقائي كـ artifact
- ✅ تقارير مفصلة عن البناء
- ✅ إمكانية التحميل من GitHub

---

**ملاحظة**: تأكد من اختبار workflow على branch تجريبي قبل استخدامه على main branch.