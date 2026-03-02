set -eux

export GRADLE_OPTS="--enable-native-access=ALL-UNNAMED"

./gradlew publishToMavenLocal
./gradlew :demo:assemble -Pdemo=1

adb push demo/build/outputs/apk/release/demo-release-unsigned.ash /data/local/tmp/aproc
adb shell chmod +x /data/local/tmp/aproc

adb shell /data/local/tmp/aproc this is some args
