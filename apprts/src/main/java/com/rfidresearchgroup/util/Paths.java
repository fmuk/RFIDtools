package com.rfidresearchgroup.util;

import android.content.Context;

import com.termux.app.TermuxService;

import java.io.File;

public class Paths {

    public static String EXTERNAL_STORAGE_DIRECTORY;
    public static String TOOLS_PATH = "NfcTools";
    public static String SETTINGS_PATH = "Settings";
    public static String HARDNESTED_PATH = "hardnested";
    public static String LOG_PATH = "log";
    public static String PM3_PATH = "proxmark3";
    public static String COMMON_PATH = "common";
    public static String PN53X_PATH = "pn53x";
    public static String DRIVER_PATH = "driver";
    public static String KEY_PATH = "keyFile";
    public static String DUMP_PATH = "dumpFile";
    public static String DEFAULT_KEYS_NAME = "default_keys.txt";
    public static String DEFAULT_DUMP_NAME = "BLANK(空白).dump";
    public static String DEFAULT_CMD_NAME = "cmd.json";

    public static String TOOLS_DIRECTORY;
    public static String KEY_DIRECTORY;
    public static String LOG_DIRECTORY;

    public static String COMMON_DIRECTORY;
    public static String DUMP_DIRECTORY;

    public static String PM3_DIRECTORY;
    public static String PN53X_DIRRECTORY;

    public static String SETTINGS_DIRECTORY;
    public static String SETTINGS_FILE;

    public static String PM3_BOOT_FILE_NAME = "bootrom.elf";
    public static String PM3_OS_FILE_NAME = "fullimage.elf";
    public static String PM3_FORWARD_O;
    public static String PM3_FORWARD_E;
    public static String PM3_CMD_FILE;
    public static String PM3_IMAGE_BOOT_FILE;
    public static String PM3_IMAGE_OS_FILE;
    public static String PM3_CWD;
    public static String PM3_CWD_FINAL;

    public static String PN53X_FORWARD_O;
    public static String PN53X_FORWARD_E;
    public static String PN53X_FORWARD_MF_O;
    public static String PN53X_FORWARD_MF_E;
    public static String COMMON_FORWARD_O;
    public static String COMMON_FORWARD_E;
    public static String COMMON_FORWARD_I;

    public static String DEFAULT_KEYS_FILE;
    public static String DEFAULT_DUMP_FILE;
    public static String DRIVER_DIRECTORY;

    public static void init(Context context) {
        File externalFiles = context.getExternalFilesDir(null);
        EXTERNAL_STORAGE_DIRECTORY = externalFiles != null ? externalFiles.getPath() : context.getFilesDir().getPath();

        TOOLS_DIRECTORY = EXTERNAL_STORAGE_DIRECTORY + "/" + TOOLS_PATH;
        KEY_DIRECTORY = TOOLS_DIRECTORY + "/" + KEY_PATH;
        LOG_DIRECTORY = TOOLS_DIRECTORY + "/" + LOG_PATH;

        COMMON_DIRECTORY = TOOLS_DIRECTORY + "/" + COMMON_PATH;
        DUMP_DIRECTORY = TOOLS_DIRECTORY + "/" + DUMP_PATH;

        PM3_DIRECTORY = TOOLS_DIRECTORY + "/" + PM3_PATH;
        PN53X_DIRRECTORY = TOOLS_DIRECTORY + "/" + PN53X_PATH;

        SETTINGS_DIRECTORY = TOOLS_DIRECTORY + "/" + SETTINGS_PATH;
        SETTINGS_FILE = SETTINGS_DIRECTORY + "/" + "set.dat";

        PM3_FORWARD_O = PM3_DIRECTORY + "/" + "pm3_forward_o.txt";
        PM3_FORWARD_E = PM3_DIRECTORY + "/" + "pm3_forward_e.txt";
        PM3_CMD_FILE = PM3_DIRECTORY + "/" + DEFAULT_CMD_NAME;
        PM3_IMAGE_BOOT_FILE = TermuxService.HOME_PATH + File.separator + PM3_PATH + File.separator + PM3_BOOT_FILE_NAME;
        PM3_IMAGE_OS_FILE = TermuxService.HOME_PATH + File.separator + PM3_PATH + File.separator + PM3_OS_FILE_NAME;
        PM3_CWD = PM3_DIRECTORY + File.separator + "home";
        PM3_CWD_FINAL = PM3_DIRECTORY + File.separator + "home";

        PN53X_FORWARD_O = PN53X_DIRRECTORY + "/" + "pn53x_forward_o.txt";
        PN53X_FORWARD_E = PN53X_DIRRECTORY + "/" + "pn53x_forward_e.txt";
        PN53X_FORWARD_MF_O = PN53X_DIRRECTORY + "/" + "pn53x_forward_mf_o.txt";
        PN53X_FORWARD_MF_E = PN53X_DIRRECTORY + "/" + "pn53x_forward_mf_e.txt";
        COMMON_FORWARD_O = COMMON_DIRECTORY + "/" + "common_forward_o.txt";
        COMMON_FORWARD_E = COMMON_DIRECTORY + "/" + "common_forward_e.txt";
        COMMON_FORWARD_I = COMMON_DIRECTORY + "/" + "common_forward_i.txt";

        DEFAULT_KEYS_FILE = KEY_DIRECTORY + "/" + DEFAULT_KEYS_NAME;
        DEFAULT_DUMP_FILE = DUMP_DIRECTORY + "/" + DEFAULT_DUMP_NAME;
        DRIVER_DIRECTORY = TOOLS_DIRECTORY + "/" + DRIVER_PATH;
    }
}
