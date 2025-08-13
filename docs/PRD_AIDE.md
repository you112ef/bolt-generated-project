# PRD مفصل: AIDE — محرّر أكواد أندرويد مع تصدير APK ودمج نماذج ذكاء اصطناعي (على غرار bolt.diy)

> هدف المستند: تقديم مواصفات تنفيذية دقيقة لبناء تطبيق محرّر أكواد على أندرويد يدعم تصدير APK محليًا/سحابيًا، مع منظومة إضافات للذكاء الاصطناعي (توليد/إكمال/إصلاح/تشغيل أوامر)، مع قابلية العمل دون اتصال.

---

## 1) المشكلة والهدف

- **المشكلة**: مطوّرو الهواتف/الميدانيون يحتاجون إلى IDE على أندرويد خفيف وسريع مع إمكانيات ذكاء اصطناعي متقدمة وتصدير APK مباشرة.
- **الهدف**: تطبيق AIDE يمنح:
  1. تحرير أكواد متقدّم (تظليل/طيات/بحث/استبدال/تنقّل رمزي)
  2. تشغيل مهام بناء وتوقيع وتصدير APK
  3. تكامل AI مشابه لفلسفة bolt.diy (مساعدات توليد مشاريع، شرح، تحويل أطر عمل، إصلاح أخطاء)
  4. استخدام دون اتصال عبر نماذج محلية، وأداء قوي عند الاتصال عبر نماذج سحابية

---

## 2) المستخدم المستهدف

- الطلبة، الهواة، مطوّرو سكربتات ومكتبات، فرق ميدانية تحتاج أدوات سريعة.
- **السيناريوهات**: إنشاء مشروع صغير، إصلاح مشروع موجود، توليد نماذج أولية بسرعة، التعديل على كود أثناء التنقّل.

---

## 3) المنصة والتقنيات

- **المنصة**: أندرويد (SDK 26+)
- **اللغة**: Kotlin (واجهة المستخدم/الخدمات)، + C++/NDK للتكامل مع محركات LLM محلية
- **UI**: Jetpack Compose
- **المحرّر**:
  - محرك تحرير نصوص يدعم TextMate/Tree-sitter لقواعد التمييز
  - دعم مئات اللغات (JSON/YAML/Markdown/HTML/CSS/JS/TS/Kotlin/Java/Dart/Python/Go/Rust/…)
- **التخزين**: Room + ملفات المشروع على التخزين المحلي + EncryptedSharedPreferences للإعدادات الحساسة
- **الربط الخارجي**: WorkManager للمهام الطويلة (بناء، تنزيل نماذج)
- **التصاريح**: READ/WRITE (Scoped Storage)، INTERNET، POST_NOTIFICATIONS (اختياري)، FOREGROUND_SERVICE للمهام

---

## 4) معمارية عامة

Presentation (Compose + ViewModel)

Domain (UseCases)

Data (Repositories: Projects, Builds, AI, Plugins, Models)

Core (المحرّر + LSP client + Parser + FS Abstraction)

Engines

AI Engine: واجهة موحّدة لمزوّدي النماذج (محلي/سحابي)

Build Engine: بناء محلي (Gradle/CLI) أو سحابي (Webhook + Queue)

LSP Engine: عملاء LSP جاهزون للغات الشائعة


App (Compose)

 ├─ EditorCore (Text Engine, Tree-sitter, LSP client)

 ├─ AI Engine (Local JNI + Cloud REST gRPC)

 ├─ Build Engine (Local Gradle Wrapper + Cloud Builds)

 ├─ Plugins SDK (Actions, Panels, Sidebars, Commands)

 └─ Data (Room, Repos, Settings, KeyStore)

---

## 5) الميزات الأساسية

### 5.1 محرّر أكواد

- تظليل نحوي، طيّ أكواد، ميني ماب، أسطر أرقام، تلقّي أخطاء من LSP
- بحث/استبدال Regex، قفز للتعريف، قائمة "الرموز" في الملف
- إدارة ملفات ومجلدات (إنشاء/حذف/إعادة تسمية/سحب وإفلات)

### 5.2 إدارة مشاريع

- قوالب سريعة: Kotlin Android App، Library، Console؛ Flutter (قالب)، Node/React؛ Python scripts
- استيراد ZIP/Git (اختياري: git-lite داخلي أو عبر Termux)
- بيئات تشغيل (Run Configurations): run/debug للسكريبتات والأدوات

### 5.3 التكميل الذكي (Code Intelligence)

- LSP للغات المدعومة
- AI Completion في السطر (inline) + نافذة اقتراحات، مع سياق الملف/المشروع

### 5.4 الذكاء الاصطناعي (على غرار bolt.diy)

- مساعد مشاريع: أمر “New from Prompt” يولّد مشروعًا كاملًا (قوالب + ملفات + README + مهام بناء)
- شرح وتحويل: تحديد كود → "اشرح"، "حوّل إلى Kotlin/Flutter"، "بسّط"
- إصلاح الأخطاء: لصق سجل بناء/Stacktrace → "اقترح إصلاح"
- سطر أوامر ذكي: Chat داخل IDE بقدرات تنفيذ أوامر آمنة (Sandboxed Tasks)
- وصفات (Recipes): أتمتة متسلسلة (توليد شاشة → إضافة Route → تحديث Tests → تشغيل بناء)

### 5.5 البناء وتصدير APK

- محلي (أفضلية للمشاريع Kotlin/Java خفيفة):
  - تضمين Gradle Wrapper، d8, aapt2, zipalign, apksigner (باينريات ARM64 مضغوطة) + اتفاقية ترخيص المستخدم
  - تثبيت Android Command-line Tools وقت الطلب (تنزيل مُدار)
  - إدارة SDK/Build-tools بنسخ مصغّرة
- سحابي:
  - خدمة بناء عبر API: رفع المشروع أو ربط Git، اختيار target، إصدار APK/AAB موقّع
  - إعداد مفاتيح التوقيع في Keystore مشفّر على الجهاز أو تخزين سحابي مُدار
- خيوط الأمان: التشغيل داخل ForegroundService + إشعارات تقدّم + حدود موارد

### 5.6 التوقيع والشهادات

- إنشاء keystore محليًا (AndroidKeyStore) + تصدير آمن (محمي كلمة مرور)
- أو استخدام مفتاح سحابي للمؤسسات

### 5.7 العمل دون اتصال

- فتح/تحرير/بحث محلي
- نماذج LLM محلية (صغيرة) للإكمال/التعديل
- البناء المحلي إن توفرت الأدوات

### 5.8 قابلية التوسّع (Plugins)

- SDK إضافات بلغة Kotlin/JS (WebAssembly مستقبلًا) مع صلاحيات محدّدة
- واجهات برمجية: Editor, FS, Terminal, AI, Build, UI Panels
- متجر إضافات داخلي (سجل حزم) مع توقيع وتحقق

---

## 6) تكامل الذكاء الاصطناعي

### 6.1 مزوّدون محليون (On‑device)

- llama.cpp عبر JNI (GGUF): تشغيل نماذج 3–7B للإكمال والشرح
- MLC LLM لتسريع Vulkan/GPU حيث متاح
- TFLite لنماذج متخصّصة (تصنيف، تلخيص قصير)

- قابلية التحميل:
  - مدير نماذج: تنزيل/تحديث/حذف، فحص مساحة التخزين، FP16/INT4
  - كاش للسياقات واستدعاء الدوال (Function calling) محليًا عند الإمكان

### 6.2 مزوّدون سحابيّون

- موحّد API (Strategy): OpenAI/Anthropic/Google/DeepSeek/…
- إعدادات مفاتيح آمنة + قيود معدل + تتبّع تكلفة تقريبية

### 6.3 واجهة AI موحّدة (Core)

- AiProvider (generate, complete, chat, tools, embeddings)
- AiContext (ملفات مفتوحة + مسارات + مقتطف مُحدد + سجل بناء)
- Function Tools: تعريف أوامر يمكن للنموذج استدعاؤها (create_file, patch_file, run_build, search_symbols…)

### 6.4 أوضاع الاستخدام

- Inline Completion: يقترح أثناء الكتابة
- Quick Actions: تحديد نص → قائمة ذكاء اصطناعي
- Project Agent: نافذة محادثة بذاكرة على مستوى المشروع
- Bolt-like Flows: قوالب Prompt لولادة تطبيقات (Android/Flutter) من وصف

---

## 7) محرك البناء (Build Engine)

### 7.1 محلي

- تضمين:
  - Gradle Wrapper + مغلّفات لبناء دون JDK كامل عبر jre-min (temurin-min) أو D8 مباشرة
  - أدوات: aapt2, d8, zipalign, apksigner (بنايات arm64)
- قيود: المشاريع الكبيرة قد تتطلب ذاكرة/زمن كبيرين → اقتراح التحويل للسحابة

### 7.2 سحابي

- واجهة REST/gRPC:
  - POST /builds (source: zip/git, target: apk/aab, sdkVer, buildTools)
  - GET /builds/{id} (status/logs/artifacts)
  - POST /sign (upload unsigned.apk, return signed.apk)
- الأمان: مصادقة JWT، حدود حجم، حذف آلي للمخرجات

---

## 8) الأمان والخصوصية

- مفاتيح API تُخزّن مشفّرة (AndroidKeyStore)
- نماذج محلية تعمل دون رفع الكود سحابيًا
- عند البناء السحابي: تحذير واضح + موافقة + حذف تلقائي بعد X أيام
- سياسات أذونات صارمة، تسجيل أخطاء مجهولة الهوية

---

## 9) الأداء

- تحرير ملفات ضخمة عبر paging/rope buffers
- Threading: تحليل نحوي/LSP في Dispatcher.IO، الإكمال في Default
- دفتر سياق AI محدود الحجم مع ترميز فعال (BPE/WordPiece) + ضغط سياق للمشروعات الكبيرة

---

## 10) تجربة المستخدم (UX)

- شاشة رئيسية: مشاريعي + أنشئ من Prompt + افتح مجلد/ZIP
- المحرّر: ترويسة ملف، شريط أوامر سريع (⌘K/CTRL+K)، ميني ماب، لوحة طرفية
- لوحة AI جانبية: جلسة مشروع + أوامر جاهزة (Explain/Refactor/Test/Generate Screen)
- لوحة بناء: مهام، سجلات، حالة حيّة، زر تصدير APK
- متجر إضافات: بطاقات + تقييمات + أذونات الإضافة

---

## 11) مخطط بيانات مبسّط

```
classDiagram
  class Project { id; name; path; type; lastOpened }
  class FileIndex { id; projectId; path; lang; symbols }
  class BuildJob { id; projectId; mode; status; logsPath; artifactPath }
  class AiModel { id; name; type; size; location; provider }
  class AiSession { id; projectId; historyPath; pinnedFiles[] }
  Project --> FileIndex
  Project --> BuildJob
  Project --> AiSession
  AiModel --> AiSession
```

---

## 12) هيكل المجلدات (Android + Core)

```
app/
  src/main/kotlin/com/aide/app/
    ui/ (Compose screens, components)
    editor/ (Text engine, Tree-sitter bindings)
    ai/
      core/ (AiProvider, AiContext, Tools API)
      local/ (llama.cpp JNI, loaders)
      cloud/ (clients)
    buildsystem/
      local/ (wrappers for d8, aapt2, zipalign, apksigner)
      cloud/ (API client)
    plugins/
      sdk/ (interfaces)
      runtime/ (sandbox, permissions)
    data/ (Room, entities, repos)
    lsp/ (clients per language)
  src/main/jni/ (C++ glue for llama.cpp)
  assets/models/ (optional tiny models)
  assets/grammars/ (tree-sitter grammars)
```

---

## 13) واجهات برمجية أساسية (Pseudo/Kotlin)

### 13.1 AI Provider

```kotlin
interface AiProvider {
  suspend fun chat(prompt: String, ctx: AiContext, tools: List<AiTool> = emptyList()): AiResponse
  suspend fun complete(prefix: String, suffix: String?, lang: String?, ctx: AiContext): String
  suspend fun embeddings(texts: List<String>): FloatArray
}

data class AiContext(
  val projectPath: String,
  val openFiles: List<OpenFile>,
  val selection: CodeSelection?,
  val buildLog: String?
)
```

### 13.2 أدوات قابلة للاستدعاء من النموذج

```kotlin
data class AiTool(
  val name: String,
  val description: String,
  val parametersJsonSchema: String,
  val handler: suspend (JsonObject) -> ToolResult
)
```

### 13.3 محرك بناء محلي

```kotlin
sealed interface BuildMode { object Local: BuildMode; object Cloud: BuildMode }

interface BuildEngine {
  suspend fun build(project: Project, mode: BuildMode): BuildResult
  suspend fun sign(apkPath: String, keystore: KeystoreConfig): String
}
```

---

## 14) عينات تدفّقات استخدام AI

1. **توليد مشروع من Prompt**
   - المستخدم يصف: "تطبيق ملاحظات ومصاريف لفرق المبيعات" → النموذج يولّد قالب Kotlin/Flutter + شاشات + Storage + README + مهام

2. **إصلاح خطأ بناء**
   - لصق Stacktrace → AI يقترح تغييرات محدّدة في Gradle/الكود + زر "تطبيق التصحيحات"

3. **تحويل تقنية**
   - تحديد ملف Kotlin → "حوّله إلى Flutter/Dart" → إنشاء ملفات Dart + pubspec + تعليمات بناء

---

## 15) شاشة تصدير APK

- اختيار نوع الخرج (APK/AAB)، مستوى minSdk/targetSdk، وضع Debug/Release
- اختيار keystore أو إنشاء جديد
- زر ابدأ البناء مع عدّاد وتتبّع حالة، ثم مشاركة artifact

---

## 16) القياسات والتتبّع

- زمن فتح الملف، زمن الإكمال، نجاح البناء
- عدم إرسال محتوى الكود بشكل افتراضي (Opt‑in فقط للإحصاءات المجمّعة)

---

## 17) التسعير والتراخيص

- مجاني: محرّر + بناء محلي محدود + نموذج محلي صغير + 1 مشروع سحابي/يوم
- Pro: نماذج سحابية متعددة + بناء سحابي غير محدود + متجر إضافات + مزايا تعاون
- تراخيص مكونات طرف ثالث (Tree-sitter, llama.cpp, LSP servers) وفق رخصها (MIT/Apache/…)

---

## 18) خارطة طريق (MVP → v1.2)

- **MVP (8 أسابيع)**
  - محرّر + ملفات + قوالب Kotlin/Console
  - AI محلي صغير (3B) للإكمال والشرح
  - بناء محلي Debug APK لمشاريع Kotlin بسيطة
  - شاشة تصدير + توقيع Debug

- **v1.0**
  - دعم سحابي للبناء (APK/AAB + توقيع Release)
  - LSP للغات أساسية + متجر إضافات
  - مساعد المشاريع (New from Prompt) مع قوالب Android/Flutter

- **v1.1**
  - تحسين الأداء، إدارة نماذج متعددة، أدوات AI قابلة للاستدعاء (Function Calling)

- **v1.2**
  - تعاون لحظي عبر P2P/Relay، جلسات AI مشتركة، مزامنة إعدادات

---

## 19) مخاطر وحلول

- قيود الذاكرة: نماذج أصغر + تجزئة السياق + توصية سحابة
- قيود رُخص أدوات البناء: اعتماد CLI رسمي مع اتفاقيات واضحة للمستخدم
- أمن المفاتيح: AndroidKeyStore + عدم التخزين بنص صريح
- تعقيد Flutter محليًا: تحويله لبناء سحابي افتراضيًا، مع SDK محلي اختياري متقدّم

---

## 20) ملحقات

### 20.1 Prompt جاهز (على نمط bolt.diy) لتوليد مشروع “ملاحظات + مصاريف للمبيعات”

> System: أنت مساعد برمجي داخل IDE أندرويد. اكتب كودًا صحيحًا وملفات كاملة.
>
> User: أنشئ مشروع Android Kotlin بسيط يُسجّل ملاحظات واجتماعات ومصاريف يومية محليًا، يعمل دون اتصال، بواجهات نظيفة، مع Room وViewModel وقائمة قابلة للتمرير وتاريخ/وقت لكل عنصر.
>
> Tools (مسموحة): create_file, patch_file, add_dependency, run_build, explain.
>
> Output: هيكل مجلدات + ملفات Kotlin/Layouts + Room Entities/DAO + ViewModels + شاشة إدخال + RecyclerLists + زر تصدير APK.

### 20.2 متطلبات الأجهزة للنماذج المحلية

- ذاكرة ≥ 4GB لنموذج 3–4B INT4، و≥ 8GB لـ 7B INT4
- يُنصح بتفعيل تسريع GPU عبر Vulkan إن توفّر (MLC LLM)

---

انتهى المستند