#include <dlfcn.h>
#include "common.h"

[[gnu::constructor()]] void ctor() {
    ALOGI("ctor A");

    void *handle = dlopen("libdemo_b.so", RTLD_NOW);

    if (handle == nullptr) {
        ALOGE("dlopen failed: %s", dlerror());
    }
}