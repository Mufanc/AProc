#include <dlfcn.h>
#include "common.h"

[[gnu::constructor()]] void ctor() {
    ALOGI("ctor B");
}
