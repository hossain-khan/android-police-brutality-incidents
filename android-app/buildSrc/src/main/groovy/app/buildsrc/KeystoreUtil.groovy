package app.buildsrc

import org.gradle.api.Project

class KeystoreUtil {
    static def buildProperties(Project rootProject) {
        // Creates a variable called keystorePropertiesFile, and initializes it to the keystore.properties file.
        // https://developer.android.com/studio/build/gradle-tips#remove-private-signing-information-from-your-project
        def keystorePropertiesFile = rootProject.file("keystore.properties")

        // Initializes a new Properties() object called keystoreProperties.
        def keystoreProperties = new Properties()

        // Loads the keystore.properties file into the keystoreProperties object.
        if (keystorePropertiesFile.exists()) {
            keystoreProperties.load(new FileInputStream(keystorePropertiesFile))
        } else {
            // For CI builds, use debug cert to pass it.
            // https://developer.android.com/studio/publish/app-signing#debug-mode
            // https://coderwall.com/p/r09hoq/android-generate-release-debug-keystores
            keystoreProperties['keyAlias'] = 'androiddebugkey'
            keystoreProperties['keyPassword'] = 'android'
            keystoreProperties['storeFile'] = '../keystore/debug.keystore'
            keystoreProperties['storePassword'] = 'android'
        }

        return keystoreProperties
    }
}