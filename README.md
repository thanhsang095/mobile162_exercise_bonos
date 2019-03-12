# Config Bugsnag

Efficiently debug React Native applications. Add Bugsnag to your projects to automatically capture and report JavaScript and native crashes in your Android and iOS apps.

---

## Installation

```bash
npm install bugsnag-react-native -save
```

## Linking the Library

### Auto Link

```bash
react-native link bugsnag-react-native
```

Note: if you are using CocoaPods to manage dependencies in your React Native application, run `pod install` after `react-native link`

### Manual Link

#### Android configuration

1. Add the library to `android/app/build.gradle`:

```
dependencies {
compile project(':bugsnag-react-native')
}
```
2. Link the library in `android/settings.gradle`:

```
dependencies {
compile project(':bugsnag-react-native')
}
```
3.  Add the library package to native packages listed in 
    `android/{...}/MainApplication.java`:
    
```java
import com.bugsnag.BugsnagReactNative;
// ...

protected List<ReactPackage> getPackages() {
    return Arrays.<ReactPackage>asList(
        new MainReactPackage(), BugsnagReactNative.getPackage()
    );
}
```
4. Specify Google's maven repository in `build.gradle`:

```
allprojects {
    repositories {
        maven { url 'https://maven.google.com' }
    }
}
```

#### iOS configuration

1. Open `ios/[project].xcodeproj` in Xcode and, using Finder, drag `node_modules/bugsnag-react-native/cocoa/BugsnagReactNative.xcodeproj` into the `Libraries` group, alongside React Native and other dependencies.
2. In Xcode, select your project in the Project Navigator pane, select the Build Phases tab, and add `libz.tbd` and `libBugsnagReactNative.a` to Link Binary With Libraries.
3. In Xcode, select the Build Settings tab, and append the following to the "Header Search Paths" field: `$(inherited) $(SRCROOT)/../node_modules/bugsnag-react-native/cocoa/**`

## Usage

### Basic configuration

To identify your app in the Bugsnag dashboard, you'll need to configure your Bugsnag API key. You can find your API key when creating a project in your [Bugsnag dashboard](https://app.bugsnag.com/), or later from your **Project Settings** page.

Import and initialize Bugsnag within your app's entry point, typically `index.js` (or `index.ios.js` and `index.android.js`):

```js
import { Client } from 'bugsnag-react-native';
const bugsnag = new Client('YOUR-API-KEY-HERE');
```

At this point, Bugsnag should be installed and configured, and any unhandled errors and native exceptions will be automatically detected and should appear in your Bugsnag dashboard.

### Showing full stacktraces

Bugsnag supports unminifying and demangling stacktraces using ProGuard files, LLVM debug symbol maps (dSYMs), and source maps to show a full stacktrace with methods, file paths, and line numbers for JavaScript and native errors.

### 1. Uploading debug symbol maps (dSYMs) for iOS

#### Using CocoaPods plugin

If you are using CocoaPods, installing the [cocoapods-bugsnag](https://github.com/bugsnag/cocoapods-bugsnag) plugin will add a build phase to upload dSYM files when you run `pod install`. To install, run `gem install cocoapods-bugsnag`.

Once added, uploading your dSYM files to Bugsnag will occur automatically.

### 2. Uploading source maps

#### Generating source maps
**Debug variant**

iOS example:

```bash
$ curl "http://localhost:8081/index.bundle?platform=ios&dev=true&minify=false" > ios-debug.bundle
$ curl "http://localhost:8081/index.bundle.map?platform=ios&dev=true&minify=false" > ios-debug.bundle.map
```
Android example:

```bash
$ curl "http://localhost:8081/index.bundle?platform=android&dev=true&minify=false" > android-debug.bundle
$ curl "http://localhost:8081/index.bundle.map?platform=android&dev=true&minify=false" > android-debug.bundle.map
```

**Release variant**

iOS example:

```bash
$ react-native bundle \
    --platform ios \
    --dev false \
    --entry-file index.js \
    --bundle-output ios-release.bundle \
    --sourcemap-output ios-release.bundle.map
```
Android example:

```bash
$ react-native bundle \
    --platform android \
    --dev false \
    --entry-file index.js \
    --bundle-output android-release.bundle \
    --sourcemap-output android-release.bundle.map
```

#### Uploading source maps to Bugsnag

```bash
npm install -g bugsnag-sourcemaps
```
**Debug variant example**

on iOS:

```bash
$ bugsnag-sourcemaps upload \
    --api-key YOUR_API_KEY_HERE \
    --app-version 1.0.0 \
    --minified-file ios-debug.bundle \
    --source-map ios-debug.bundle.map \
    --minified-url "http://localhost:8081/index.bundle?platform=ios&dev=true&minify=false"
```

on Android:

```bash
$ bugsnag-sourcemaps upload \
    --api-key YOUR_API_KEY_HERE \
    --app-version 1.0.0 \
    --minified-file android-debug.bundle \
    --source-map android-debug.bundle.map \
    --minified-url "http://10.0.2.2:8081/index.bundle?platform=android&dev=true&minify=false"
```

**Release variant example**

iOS example:

```bash
$ bugsnag-sourcemaps upload \
    --api-key YOUR_API_KEY_HERE \
    --app-version 1.0.0 \
    --minified-file ios-release.bundle \
    --source-map ios-release.bundle.map \
    --minified-url main.jsbundle \
    --upload-sources
```
Android example:

```bash
$ bugsnag-sourcemaps upload \
    --api-key YOUR_API_KEY_HERE \
    --app-version 1.0.0 \
    --minified-file android-release.bundle \
    --source-map android-release.bundle.map \
    --minified-url index.android.bundle \
    --upload-sources
```

## Reference

- Website: https://docs.bugsnag.com/platforms/react-native
