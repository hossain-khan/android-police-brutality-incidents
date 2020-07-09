## Android App 

Android client app for https://github.com/2020PB/police-brutality (Repository containing evidence of police brutality during the 2020 George Floyd protests)

### Architecture

Essentially, the app should follow standard guidelines from Google.

Currently following key features are being used.

* MVVM, Dagger Hilt, Room, Navigation, WorkManager, Retrofit2, Architecture Components and other JetPack libraries.

See [app/build.gradle](https://github.com/amardeshbd/android-police-brutality-incidents/blob/develop/android-app/app/build.gradle#L87) for library dependency.

### Builds

#### Local Build
You can just run following gradle command to make local build

```
./gradlew clean assembleDebug
```

#### Firebase App Distribution

Any merge to `main` branch builds release build that is available in Firebase App Distribution [self-serve signup page](https://appdistribution.firebase.dev/i/5d2cb8359305f7e7).


#### Release Builds

Release build is manually built and posted to Google Play store. (pending approval)


## Background

Here is some background and social posts about building this app:

* Early Preview - I have made an Android app to access 2020PoliceBrutality data ✊ - https://www.reddit.com/r/2020PoliceBrutality/comments/gzcvt0/early_preview_i_have_made_an_android_app_to/
* 2020PB Android App - Stable and Ready - https://www.reddit.com/r/2020PoliceBrutality/comments/hbj5z4/2020pb_android_app_stable_and_ready/
