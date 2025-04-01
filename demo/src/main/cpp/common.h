#pragma once

#include <android/log.h>

#define TAG "demo"

#define ALOGI(...) (__android_log_print(ANDROID_LOG_INFO, TAG, __VA_ARGS__))
#define ALOGE(...) (__android_log_print(ANDROID_LOG_ERROR, TAG, __VA_ARGS__))
