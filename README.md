# ARProject Mobile

A sample Augmented Reality (AR) mobile application built with Kotlin using Google ARCore for Android.

## Features

- Simple AR scene rendering (e.g., adding a 3D object to the camera view).
- Modern Android app structure in Kotlin.

## Requirements

- Android Studio (Arctic Fox or later)
- Android device compatible with ARCore
- Kotlin 1.5+
- Min SDK version: 24+

## Setup

1. **Clone the repository:**
    ```bash
    git clone https://github.com/Than-Zaw-Hein/ARProject.git
    cd ARProject
    ```

2. **Open in Android Studio:**
    - File > Open > Select project folder

3. **Configure Dependencies:**
    The project uses [SceneView AR](https://github.com/SceneView/sceneview-android) and [ARCore-Android SDK](https://developers.google.com/ar/develop/java/quickstart).
    Ensure you have the following in your `build.gradle` (Module: app):
    ```groovy
    dependencies {
        implementation "io.github.sceneview:arsceneview:2.3.0"
        implementation "com.google.ar:core:1.51.0"
    }
    ```

4. **Set Up Device Permissions:**
    Add the following permissions to your `AndroidManifest.xml`:
    ```xml
    <uses-permission android:name="android.permission.CAMERA" />
    <uses-feature android:name="android.hardware.camera.ar" />
    ```

5. **Run the App:**
    - Connect an ARCore-supported Android device.
    - Click “Run” in Android Studio.

## Usage

- Point your camera at a flat surface.
- Tap the screen to place a 3D model in your environment.

## Notes

- For custom AR models, add `.obj` or `.glb` to the assets directory, and update your rendering logic accordingly.
- Tested on Pixel devices running Android 12 and up.

## License

MIT License
