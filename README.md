<!-- def -->
[img_pm3]: /githubsrc/rdv4x173.png
[img_nfc]: /githubsrc/phone_nfcx173.png
[img_122]: /githubsrc/acr122ux173.png
[img_cml]: /githubsrc/chameleonx173.png
[img_532]: /githubsrc/PN532x173.png

<p align="center" background="#000000">
    <img align="center" src="/githubsrc/rfidx100.png" alt="RFID Tools" width="100" height="100">
</p>

<h1 align="center">RFID Tools android app</h1>

> **Note:** This is a maintained fork of [RfidResearchGroup/RFIDtools](https://github.com/RfidResearchGroup/RFIDtools), modernized for current Android versions.

## Supported devices

|Proxmark3   |NFC Reader  |ACS ACR-122u  |Chameleon Mini  |PN532       |  
|----------- |----------- |------------- |--------------- |----------- |
| ![img_pm3] | ![img_nfc] |  ![img_122]  |   ![img_cml]   | ![img_532] |

|                                           PN53X Derived(Testing)                                      |
|-------------------------------------------------------------------------------------------------------|
| NXP_PN533    .   NXP_PN531    .   SONY_PN531    .   SCM_SCL3711    .   SCM_SCL3712    .   SONY_RCS360 |

## Features

- Runs on non-rooted phones
- Supports five device types (Proxmark3, NFC, ACR-122u, Chameleon Mini, PN532)
- Simple UI for tag read/write operations

## Requirements

- Android 8.0+ (API 26)
- Tested on Android 16 (API 36)

## Development tools

- IDE: Android Studio
- JDK: 17+ (tested with 21)
- Gradle: 8.11.1 (AGP 8.7.3)
- CMake: 3.22.1
- NDK: 28.0.13004108

## How to build

```bash
git clone https://github.com/fmuk/RFIDtools.git
cd RFIDtools
./gradlew assembleDebug
```

Install on a connected device:

```bash
adb install -r apprts/build/outputs/apk/debug/apprts-debug.apk
```

## Architecture

- Communication: LocalSocket & LocalServerSocket (abstract namespace, no root)
- Build: Gradle + CMake + ndk-build
- Framework: MVP
- Native: All libraries compiled to `.so` via JNI/NDK with 16KB page alignment

## Compatibility list

Tested on:

- Google Pixel (Android 16 / API 36)
- Redmi K20 Pro (MIUI 10 / Android 9)
- Redmi K20 (MIUI 10 / Android 9)
- OnePlus 5T (H2OS 5.1.2 / Android 8.1.0)

## Dependencies

- [Gson](https://github.com/google/gson) — JSON parsing
- [Glide](https://github.com/bumptech/glide) — Image loading
- [MultiType](https://github.com/drakeet/MultiType) — Polymorphic RecyclerView adapter
- [TERMUX](https://github.com/termux) — Embedded terminal
- [UsbSerial](https://github.com/felHR85/UsbSerial) — USB serial communication
- [Material Components](https://github.com/material-components/material-components-android) — UI

## Maintainer

- Fork maintained by [@fmuk](https://github.com/fmuk)
- Original app by DXL

## License

GPL
