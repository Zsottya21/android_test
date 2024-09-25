# android_test
Tesztfeladat

# Budapest13 Smart City Mobil Alkalmazás - Teszt Automatizálás

## Projekt leírása
Ez a projekt a Budapest13 Smart City mobil alkalmazás regisztrációs folyamatának automatizált tesztelését tartalmazza. A tesztek Java és TestNG keretrendszerrel készültek, és az Appium segítségével szimulálnak felhasználói interakciókat a regisztráció és az email megerősítés során.
Továbbá az email API használatára eleinte Gmail API-t, később pedig jobbnak láttam Mailosaur-t alkalmazni.

## Követelmények
- **Java** (verzió: 22.0.1)
- **Android Studio**
- **Gradle**
- **Node.js** (Az Appium szerver futtatásához)
- **Appium inspector** (verzió: 8.6.0 (Esetemben))
- **TestNG** (A legfrissebb verzió)
- **Mailosaur** (Email API)

## Beállítások

### 1. Appium környezet beállítása:
1. Telepítsük a Node.js-t majd az Appium szervert Node.js-en keresztül:

    npm install -g appium   VAGY    npm i --location=global appium
    (Érdemes ellenőrizni is: appium -v)

2. Futtasd az Appium szervert: (cmd -> futtatás rendszergazdaként)

    appium

3. Készülék adatainak kinyerése, beállítása
   Appium szerver alapvető elérési útvonala: http://127.0.0.1:4723/ vagy http://localhost:4723/
   Json:
   {
   "platformName": "Android",
   "appium:deviceName": "BS98317AA12B1602079",                          // adb devices
   "appium:platformVersion": "13.0",                                    //
   "appium:automationName": "UiAutomator2",                             //
   "appium:appPackage": "com.interticket.budapest13",                   // adb shell dumpsys window | find "mCurrentFocus"
   "appium:appActivity": "com.interticket.smartcity.ui.SplashActivity"  // adb shell dumpsys package com.interticket.budapest13 | findstr "Activity"
   }

### 2. UIAutomotor telepítése:
1. UIAutomotor telepítése, ellenőrzése:

   UIAutomotor telepítése: appium driver install uiautomator2
   ellenőrzés: appium driver list


### 3. Teszt környezet beállítása:
1. Klónozd le a projektet vagy töltsd le a forráskódot.
2. Nyisd meg a projektet Android Studio-ban.
3. Győződj meg róla, hogy a szükséges függőségek (TestNG, Appium Java Client stb.) telepítve vannak a `build.gradle` fájlban. (én azt használtam)
4. APKTools telepítése és a PATH file elérési útvonalának beállítása.

   ANDROID_HOME HELYES BEÁLLÍTÁSA. (sajátgépen belül a "semmibe" jobb klikk tulajdonságok,speciális rendszerbeállítások, környezeti változók... felső ablakba: ANDROID_HOME hozzáadása azon belül ANDROID_HOME, C:\Users\Felhasznalo\AppData\Local\Android\Sdk.
   alsó ablakban pedig: path -> szerkesztés -> új -> C:\Users\Felhasznalo\AppData\Local\Android\Sdk és aztán mégegy hozzáadása -> %ANDROID_HOME%\platform-tools.

   cmd-ben ellenőrizzük hogy jó e: echo %ANDROID_HOME% (meg kell mutassa a folder elérési útvonalát.

### 4. Mailosaur konfigurálása
1. Mailosaur függőség Gradle-be:
   dependencies {
   implementation 'com.mailosaur:mailosaur-java:1.0.0'
   }
2. Android stúdió console-ba: npm create mailosaur@latest (Innentől felteszi a kérdéseket a varázsló illetve a Mailosaur Dashboard-ró lbegyüjthetőek a server ID és az API key)

### 5. Teszt futtatása:
1. Futtasd az AppTest.java file-t (com/example/tesztfeladat/AppTest.java):
    
2. Az Appium el fogja indítani az Android emulátort vagy a fizikai eszközt, és végrehajtja a regisztrációs folyamat automatizált tesztjeit.

## Teszt részletei
A projektben több automatizált teszt található, amelyek a regisztrációs folyamat különböző lépéseit ellenőrzik:

- **testOpeningApplication**: Az alkalmazás megnyitása és az engedélyezési kérés kezelése.
- **testStepToRegisterForm**: A regisztrációs űrlap elérése.
- **testRegister**: A regisztrációs adatok megadása és beküldése.
- **testEmailConfirmation**: A regisztráció megerősítése az emailben kapott linken keresztül.
- **toLogIn**: Sikeres regisztráció után belépés az alkalmazásba.

## Teszt eredmények
A teszt eredmények a `build/test-results/testDebugUnitTest` mappában találhatók. Az eredmények XML fájlokban vannak mentve, amelyek tartalmazzák az egyes tesztek sikerességét, futási idejét és a konzolra írt üzeneteket.

Példa teszt eredmény:

```xml
<testsuite name="com.example.tesztfeladat.AppTest" tests="6" skipped="0" failures="0" errors="0">
  <testcase name="testRegister" classname="com.example.tesztfeladat.AppTest" time="27.377"/>
  <testcase name="testEmailConfirmation" classname="com.example.tesztfeladat.AppTest" time="10.82"/>
</testsuite>
