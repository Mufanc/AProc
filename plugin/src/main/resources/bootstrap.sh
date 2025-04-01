_ >/dev/null 2>&1

APROC_APK="$(realpath "$0")"

export APROC_APK
exec /system/bin/app_process -Djava.class.path="$APROC_APK" / xyz.mufanc.aproc.runtime.Main "$@"
