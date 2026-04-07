# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Project Overview

RFIDtools is an Android application for RFID/NFC device management. It supports Proxmark3, phone NFC reader, ACS ACR-122u, Chameleon Mini, PN532, and PN53X-derived devices. Runs on non-rooted phones using LocalSocket/abstract namespace communication.

This is a maintained fork of the original [RfidResearchGroup/RFIDtools](https://github.com/RfidResearchGroup/RFIDtools) (which is effectively abandoned). The upstream original developer (DXL) has been absent since 2021.

## Build

- **IDE:** Android Studio
- **Build system:** Gradle 8.11.1 (AGP 8.7.3) + CMake 3.22.1 for native code
- **NDK:** 28.0.13004108
- **Java:** 17+ (tested with JDK 21)
- **Compile/Target SDK:** 35, Min SDK: 26

```bash
./gradlew assembleDebug          # Build debug APK
./gradlew assembleRelease        # Build release APK
./gradlew :apprts:assembleDebug  # Build only the main app module
```

Install on connected device:
```bash
adb install -r apprts/build/outputs/apk/debug/apprts-debug.apk
```

Native ABIs built: `x86`, `x86_64`, `armeabi-v7a`, `arm64-v8a`

All native libraries use 16KB page alignment (`-Wl,-z,max-page-size=16384`) for Android 16 compatibility.

## Module Architecture

The project is a multi-module Gradle build. All modules are listed in `settings.gradle`.

- **`apprts/`** — Main application module (`com.rfidresearchgroup.rfidtools`)
- **`libcom/`** — Communication abstractions (USB, Bluetooth, Serial)
- **`libcml/`** — Chameleon Mini library
- **`libnfc/`** — NFC device support (libnfc, mfoc, mfclassic, pn53x, check tools) — heavy C/JNI
- **`libpm3/`** — Proxmark3 support; uses pre-compiled `libpm3rrg_cmd.so` in `jniLibs/`
- **`libflasher/`** — Proxmark3 firmware flasher (C/JNI via CMake)
- **`libcrapto1/`** — Crypto1 cipher implementation (C/JNI)
- **`libmfkey/`** — MIFARE key recovery: mfkey32, mfkey64, mfkey32v2 (C/JNI)
- **`libtag/`** — RFID tag utilities
- **`libutils/`** — General utilities
- **`terminal-emulator/`**, **`terminal-view/`**, **`termux-app/`** — Embedded terminal (forked TERMUX), NDK Build (Android.mk, not CMake)

## App Architecture (MVP)

The main app (`apprts/`) follows **MVP (Model-View-Presenter)** pattern:
- `activities/` — Activities organized by device/feature (tools, connect, main, proxmark3)
- `fragment/` — Fragments for UI screens
- `presenter/` — Presenters containing presentation logic
- `models/` — Business logic and data models
- `callback/` — Event callback interfaces
- `javabean/` — POJOs/data classes
- `adapter/` + `binder/` + `holder/` — RecyclerView with MultiType for polymorphic list rendering

## Native Code (JNI/NDK)

Extensive C/C++ code compiled via CMake 3.22.1 (or ndk-build for terminal-emulator) with NDK 28. JNI entry points:
- `Proxmark3Flasher.java` — Native methods for PM3 flashing
- `JNI.java` (terminal-emulator) — Terminal emulation

CMake modules produce shared libraries: `crapto1.so`, `mfkey32.so`, `mfkey64.so`, `mfkey32v2.so`, `pm3_flasher.so`, plus libnfc-related libraries. Compiled with C99, `-O3`, hidden visibility, `c++_static` STL.

Global variables in C headers (`tools.h`, `util.h`) use `extern` declarations with definitions in corresponding `.c` files. The libnfc module requires `-Wno-implicit-int -Wno-int-conversion -Wno-implicit-function-declaration` flags due to legacy C code.

## Key Dependencies

- Gson 2.11.0 — JSON parsing (replaced fastjson which had CVEs)
- MultiType 3.5.0 — Polymorphic RecyclerView adapter
- Glide 4.16.0 — Image loading
- UsbSerial 6.1.0 — USB serial communication
- BannerViewPager 2.7.0 — Carousel UI
- Material 1.12.0 — Material Design components
- AppCompat 1.7.0 — Backwards compatibility

## Assets

`apprts/src/main/assets/` contains:
- `cmd.json` — Command definitions
- `default_keys.txt` — MIFARE default keys
- `proxmark3.zip` — Bundled Proxmark3 executable
- HTML templates for tag info display

## Storage

The app uses **scoped storage** via `Context.getExternalFilesDir()`. All app data lives under `/sdcard/Android/data/com.rfidresearchgroup.rfidtools/files/NfcTools/`. No storage permissions are needed.

`Paths.init(Context)` must be called before any `Paths.*` fields are accessed (currently called in `LoginActivity.onCreate()`).

## Communication Model

Device communication goes through `libcom/` abstractions. Proxmark3 client runs as a Linux executable on the device, communicating via LocalSocket with abstract namespace (no root needed).
