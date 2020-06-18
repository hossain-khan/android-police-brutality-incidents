# Police Brutality Incidents - Android App

Android client app for https://github.com/2020PB/police-brutality (Repository containing evidence of police brutality during the 2020 George Floyd protests)

<p align="center">
  <img src="https://raw.githubusercontent.com/amardeshbd/android-police-brutality-incidents/develop/resources/poster/github-repository-social-preview.png" width="50%">
</p>

### Early BETA Testing 🚧
If you want to try the app as it is being developed, you can get the latest Android APK in 2 different ways:
* GitHub Releases - See [current release](https://github.com/amardeshbd/android-police-brutality-incidents/releases) with APK bundled with it.
* Firebase App Distribution - Subscribe to new updates via email. Use this [open-beta testing](https://appdistribution.firebase.dev/i/5d2cb8359305f7e7) process. The invite email will be sent on next release, not immediately.

> NOTE: App will be available in Google Play in few days after it's reviewed and approved.

## Objective

The objective of the app is to be front-end of the data that is collected and exposed by [police-brutality](https://github.com/2020PB/police-brutality) repository.
This allows people to easily access data on the go, and allow them to easily report new incident.


## Technical Details

Here are some technical details about the app.


### Features

Some of the key features based on data available though API is

* Browse content by area (eg. State, City)
* Browse content by date
* Show embeded content in app
* Cache content locally

Advanced feature
* Cache content on central server to avoid loading data from 3rd pary services


### Architecture

Essentially, the app should follow standard guidelines from Google. 
Currently following key features are being used.

* MVVM, Dagger2, Room, Architecture Components and other JetPack libraries.

### Preview

Here is a snapshot of the app in early stages _(taken on June 17th)_

| ![](https://user-images.githubusercontent.com/99822/84964750-e932c880-b0da-11ea-84b5-d7fcfb106367.gif) | ![](https://user-images.githubusercontent.com/99822/84964741-e6d06e80-b0da-11ea-9467-d4ad68380841.gif)  | ![](https://user-images.githubusercontent.com/99822/84964742-e7690500-b0da-11ea-9083-2cbf20a5ce13.gif) | ![](https://user-images.githubusercontent.com/99822/84964745-e89a3200-b0da-11ea-87d4-fd84825ce4f2.gif) |
| -- | -- | -- | -- |
