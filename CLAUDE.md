# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Project Overview

RFIDtools is an Android application for RFID/NFC device management. It supports Proxmark3, phone NFC reader, ACS ACR-122u, Chameleon Mini, PN532, and PN53X-derived devices. Runs on non-rooted phones using LocalSocket/abstract namespace communication.

## Build

- **IDE:** Android Studio
- **Build system:** Gradle (AGP 7.1.2) + CMake 3.10+ for native code
- **NDK:** 20.0.5594570+
- **Java:** 1.8 compatibility
- **Compile/Target SDK:** 33, Min SDK: 19-21 (varies by module)

```bash
./gradlew assembleDebug          # Build debug APK
./gradlew assembleRelease        # Build release APK
./gradlew :apprts:assembleDebug  # Build only the main app module
```

Native ABIs built: `x86`, `x86_64`, `armeabi-v7a`, `arm64-v8a`

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

Extensive C/C++ code compiled via CMake (or ndk-build for terminal-emulator). JNI entry points:
- `Proxmark3Flasher.java` — Native methods for PM3 flashing
- `JNI.java` (terminal-emulator) — Terminal emulation

CMake modules produce shared libraries: `crapto1.so`, `mfkey32.so`, `mfkey64.so`, `mfkey32v2.so`, `pm3_flasher.so`, plus libnfc-related libraries. Compiled with C99, `-O3`, hidden visibility, `c++_static` STL.

## Key Dependencies

- MultiType 3.5.0 — Polymorphic RecyclerView adapter
- FastJSON 1.2.59 — JSON parsing
- Glide 4.5.0 — Image loading
- UsbSerial 6.1.0 — USB serial communication
- BannerViewPager 2.7.0 — Carousel UI

## Assets

`apprts/src/main/assets/` contains:
- `cmd.json` — Command definitions
- `default_keys.txt` — MIFARE default keys
- `proxmark3.zip` — Bundled Proxmark3 executable
- HTML templates for tag info display

## Communication Model

Device communication goes through `libcom/` abstractions. Proxmark3 client runs as a Linux executable on the device, communicating via LocalSocket with abstract namespace (no root needed).
