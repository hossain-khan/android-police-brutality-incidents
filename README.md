[![Android CI](https://github.com/amardeshbd/android-police-brutality-incidents/workflows/Android%20CI/badge.svg)](https://github.com/amardeshbd/android-police-brutality-incidents/actions?query=workflow%3A%22Android+CI%22) [![Android Lint](https://github.com/amardeshbd/android-police-brutality-incidents/workflows/Android%20Lint/badge.svg)](https://github.com/amardeshbd/android-police-brutality-incidents/actions?query=workflow%3A%22Android+Lint%22) [![codecov](https://codecov.io/gh/amardeshbd/android-police-brutality-incidents/branch/develop/graph/badge.svg)](https://codecov.io/gh/amardeshbd/android-police-brutality-incidents)

# 2020PB - Android App

Android client app for https://github.com/2020PB/police-brutality (Repository containing evidence of police brutality during the 2020 George Floyd protests)

<p align="center">
  <img src="https://raw.githubusercontent.com/amardeshbd/android-police-brutality-incidents/develop/resources/poster/github-repository-social-preview.png" width="50%">
</p>

## Google Play Submission Update
[![rejected](https://user-images.githubusercontent.com/99822/89718591-c2cc3380-d98d-11ea-9c6c-643b3daa694d.png)](https://github.com/amardeshbd/android-police-brutality-incidents/releases)

After being under review for [over a month](https://github.com/amardeshbd/android-police-brutality-incidents/issues/204), unfortunately Google has rejected the applications due to following Google Play policy violation.

* Sensitive Events policy - Specifically, we don't allow apps that lack reasonable sensitivity towards or capitalize on a natural disaster, atrocity, conflict, death, or other tragic event.
* Bullying and Harassment policy - We don't allow apps that contain or facilitate threats, harassment, or bullying.

So, if you are still interested to try this app, then [**download the APK** from latest release](https://github.com/amardeshbd/android-police-brutality-incidents/releases) and sideload the application. Alternatively you can sign up for beta testing subscription and get latest updates via Firebase App Distribution.

### BETA Testing 🚧
If you want to try the app as it is being developed, you can get the latest Android buid via Firebase:
* Firebase App Distribution - Subscribe to new updates via email. Use this [open-beta testing](https://appdistribution.firebase.dev/i/5d2cb8359305f7e7) process.

## Objective

The objective of the app is to be front-end of the data that is collected and exposed by [police-brutality](https://github.com/2020PB/police-brutality) repository.
This allows people to easily access data on the go, and allow them to easily report new incident.

### Key Features

Some of the key features based on data available though API is

* :scroll: Browse all the reported incidents
  * By US State
  * By specific date
  * By recent incidents
* :calling: Sync latest incidents from server though API
* :arrows_counterclockwise: Auto update latest incidents if it hasn't been updated for a while
* :earth_americas: Visit external links of related to incidents via native app, or web browser
* :incoming_envelope: Allows the user to share a single incident with all the associated information 
* :writing_hand: Allows user to report incident by opening official incident report form 
* :eyes: Shows list of renown charitable organizations that work for `#BlackLivesMatter` or in related area 
* :pray: Allows the users to explore more about charities or donate to them 
* :fist_raised: Provides bit more information about the `/r/2020PoliceBrutality` reddit community with social links 
* :hash: Allows the user to copy popular hash tags 
* :heart: Provides app's information with option to share with friends and family 
* :waxing_crescent_moon: Full dark mode support for device battery and eye

## Technical Details

Technical details about the Android app can be found at [android-app/README](https://github.com/amardeshbd/android-police-brutality-incidents/blob/develop/android-app/README.md).


### Preview

Here is a snapshot of the app in early stages _(taken on June 17th)_

| ![](https://user-images.githubusercontent.com/99822/84964750-e932c880-b0da-11ea-84b5-d7fcfb106367.gif) | ![](https://user-images.githubusercontent.com/99822/84964741-e6d06e80-b0da-11ea-9467-d4ad68380841.gif)  | ![](https://user-images.githubusercontent.com/99822/84964742-e7690500-b0da-11ea-9083-2cbf20a5ce13.gif) | ![](https://user-images.githubusercontent.com/99822/84964745-e89a3200-b0da-11ea-87d4-fd84825ce4f2.gif) |
| -- | -- | -- | -- |
