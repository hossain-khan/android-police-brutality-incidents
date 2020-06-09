> **NOTE:** _This repository was created on June 6th, 2020 after discovering the [police-brutality](https://github.com/2020PB/police-brutality) repository that contains the incidents data. The hope is to quickly build an Android app that is easily accessible for people to get updates on new incidents or browse existing incidents._

# Police Brutality Incidents - Android App

Android client app for https://github.com/2020PB/police-brutality (Repository containing evidence of police brutality during the 2020 George Floyd protests)

<p align="center">
  <img src="https://raw.githubusercontent.com/amardeshbd/android-police-brutality-incidents/develop/resources/poster/github-repository-social-preview.png" width="50%">
</p>

### Early BETA Testing
If you want to try the app as it is being developed, you can get the latest Android APK in 2 different ways:
* GitHub Releases - See [current release](https://github.com/amardeshbd/android-police-brutality-incidents/releases) with APK bundled with it.
* Firebase App Distribution - Subscribe to new updates via email. Use this [open-beta testing](https://appdistribution.firebase.dev/i/5d2cb8359305f7e7) process. The invite email will be sent on next release, not immediately.

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

Here is a snapshot of the app in early stages _(taken on June 8th)_

<img src="https://user-images.githubusercontent.com/99822/84097521-dc272280-a9d2-11ea-9022-3f57b45f7096.gif" width="50%">
