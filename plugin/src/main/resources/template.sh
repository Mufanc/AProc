#!/system/bin/sh

export APROC_TMP=$(mktemp)

dd if="$0" of="$APROC_TMP" bs=4k skip=1 >/dev/null 2>&1
exec app_process -Djava.class.path="$APROC_TMP" / xyz.mufanc.aproc.runtime.Main "$@"
