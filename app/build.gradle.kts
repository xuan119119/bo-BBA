plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
}

android {
    namespace = "com.bba.youbo_bba"
    compileSdk = 34

    buildFeatures {
        viewBinding = true
    }

    defaultConfig {
        applicationId = "com.bba.youbo_bba"
        minSdk = 27
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        ndk {
            //选择要添加的对应 cpu 类型的 .so 库。
            abiFilters += listOf("armeabi","armeabi-v7a", "arm64-v8a")
            // 还可以添加 'x86', 'x86_64', 'mips', 'mips64'
        }

        manifestPlaceholders["JPUSH_PKGNAME"] =  "com.bba.youbo_bba"
        manifestPlaceholders["JPUSH_APPKEY"] = "76e99f5c3ea2321320ccff83" // 替换为实际 AppKey
        manifestPlaceholders["JPUSH_CHANNEL"] = "developer-default" // 极光默认渠道，可自定义


    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }

}

dependencies {
    implementation ("cn.jiguang.sdk:jpush:5.5.3")
    implementation("cn.jiguang.sdk:jcore:2.8.0")
    // Network
// OkHttp 核心库


    implementation("com.squareup.okhttp3:okhttp:4.12.0")
    // OkHttp 日志拦截器（用于打印请求日志）
    implementation("com.squareup.okhttp3:logging-interceptor:4.12.0")
    // Retrofit 核心库
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    // Retrofit Gson 转换器
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")
    implementation("com.github.bumptech.glide:glide:4.16.0")
    //kapt("com.github.bumptech.glide:compiler:4.16.0") // Kotlin 需要 kapt
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.activity.ktx)
    implementation(libs.fragment.ktx)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.lifecycle.viewmodel.ktx)
    implementation(libs.lifecycle.livedata.ktx)
    implementation(libs.lifecycle.runtime.ktx)
    implementation(libs.kotlinx.coroutines.android)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}