# Flutter Docker

Build apps for any screen

## Install
### Windows install
```sh
git clone https://github.com/flutter/flutter.git -b stable
# Path append C:\path-to-flutter-sdk\bin
flutter --version
```

### Run flutter doctor
```sh
flutter doctor
```

## Flutter Examples
```sh
export PUB_HOSTED_URL=https://pub.flutter-io.cn
export FLUTTER_STORAGE_BASE_URL=https://storage.flutter-io.cn
flutter config --enable-web
```

### Hello, world
The hello world app is a minimal Flutter app that shows the text "hello, world."
```sh
flutter run -d chrome
flutter run lib/arabic.dart -d chrome
```

### Flutter gallery
Flutter Gallery is a resource to help developers evaluate and use Flutter

#### Demo
https://gallery.flutter.dev/

```sh
git clone https://github.com/flutter/gallery.git
flutter pub get
flutter run
```

### Layers
The layers vignettes show how to use the various layers in the Flutter framework.
```sh
flutter run raw/spinning_square.dart -d chrome
flutter run rendering/spinning_square.dart -d chrome
flutter run widgets/spinning_square.dart -d chrome
```

### Platform Channel
The platform channel app demonstrates how to connect a Flutter app to platform-specific APIs.
```sh
flutter run -d chrome
```

## Docker
```sh
docker run --rm -it -v ${PWD}:/build --workdir /build cirrusci/flutter:stable flutter test
```

## Screenshots
![](https://raw.githubusercontent.com/flutter/website/main/src/assets/images/docs/homepage/reflectly-hero-600px.png)

![](https://raw.githubusercontent.com/flutter/website/main/src/assets/images/docs/tools/android-studio/hot-reload.gif)

## References
- [Flutter](https://flutter.dev/)
- [Flutter GitHub](https://github.com/flutter/flutter)
- [cirrusci/flutter Docker](https://github.com/cirruslabs/docker-images-flutter)
- [Flutter Gallery GitHub](https://github.com/flutter/gallery)
- [在中国网络环境下使用 Flutter](https://flutter.cn/community/china)
- [构建 Flutter Web 应用](https://flutter.cn/docs/get-started/web)