package com.rfidresearchgroup.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;

import com.rfidresearchgroup.common.util.FileUtils;
import com.rfidresearchgroup.javabean.EasyCMDEntry;

/*
 * 动态按钮的操作工具!
 * */
public class EasyBtnUtil {
    private File cmdXmlFile = new File(Paths.PM3_CMD_FILE);
    private static final Gson gson = new GsonBuilder().setPrettyPrinting().create();

    private JsonObject readRoot() throws Exception {
        String content = new String(FileUtils.readBytes(cmdXmlFile));
        return JsonParser.parseString(content).getAsJsonObject();
    }

    private void writeRoot(JsonObject jsonObject) throws Exception {
        FileWriter writer = new FileWriter(cmdXmlFile);
        gson.toJson(jsonObject, writer);
        writer.flush();
        writer.close();
    }

    /*
     * 可以获得组元素个数
     * */
    public int getGroupCount() {
        try {
            JsonObject jsonObject = readRoot();
            JsonArray array = jsonObject.getAsJsonArray("group");
            return array.size();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return -1;
    }

    /*
     * 获得组对应的按钮元素个数
     * */
    public int getButtonCount(int group) {
        try {
            JsonObject jsonObject = readRoot();
            JsonArray array = jsonObject.getAsJsonArray("group");
            JsonArray innerArr = array.get(group).getAsJsonArray();
            return innerArr.size();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return -1;
    }

    /*
     * 得到某个按钮
     * */
    public EasyCMDEntry getButton(int group, int buttonPosition) {
        try {
            JsonObject jsonObject = readRoot();
            JsonArray array = jsonObject.getAsJsonArray("group");
            JsonArray innerArr = array.get(group).getAsJsonArray();
            EasyCMDEntry entry = new EasyCMDEntry();
            JsonObject jsonObjectInner = innerArr.get(buttonPosition).getAsJsonObject();
            entry.setCmdName(jsonObjectInner.get("name").getAsString());
            entry.setCommand(jsonObjectInner.get("cmd").getAsString());
            return entry;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /*
     * 得到某个组的所有按钮!
     * */
    public List<EasyCMDEntry> getButtons(int group) {
        List<EasyCMDEntry> ret = new ArrayList<>();
        for (int i = 1; i < getButtonCount(group); i++) {
            EasyCMDEntry entry = getButton(group, i);
            ret.add(entry);
        }
        return ret;
    }

    /*
     * 删除某个组
     * */
    public boolean deteleGroup(int group) {
        try {
            JsonObject jsonObject = readRoot();
            JsonArray array = jsonObject.getAsJsonArray("group");
            array.remove(group);
            writeRoot(jsonObject);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /*
     * 删除某个按钮
     * */
    public boolean deleteButton(int group, int button) {
        try {
            button += 1;
            JsonObject jsonObject = readRoot();
            JsonArray array = jsonObject.getAsJsonArray("group");
            JsonArray innerArr = array.get(group).getAsJsonArray();
            innerArr.remove(button);
            writeRoot(jsonObject);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /*
     * 插入某个组!
     * */
    public boolean inertGroup(String name) {
        try {
            JsonObject jsonObject = readRoot();
            JsonArray array = jsonObject.getAsJsonArray("group");
            JsonArray newGroup = new JsonArray();
            JsonObject newObj = new JsonObject();
            newObj.addProperty("name", name);
            newGroup.add(newObj);
            array.add(newGroup);
            writeRoot(jsonObject);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /*
     * 插入某个按钮!
     * */
    public boolean inertButton(int group, String name, String cmd) {
        try {
            JsonObject jsonObject = readRoot();
            JsonArray array = jsonObject.getAsJsonArray("group");
            JsonArray innerArr = array.get(group).getAsJsonArray();
            JsonObject newObj = new JsonObject();
            newObj.addProperty("name", name);
            newObj.addProperty("cmd", cmd);
            innerArr.add(newObj);
            writeRoot(jsonObject);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /*
     * 更新某个组的信息!
     * */
    public boolean updateGroup(int group, String name) {
        try {
            JsonObject jsonObject = readRoot();
            JsonArray array = jsonObject.getAsJsonArray("group");
            JsonArray innerArr = array.get(group).getAsJsonArray();
            JsonObject innerObj = innerArr.get(0).getAsJsonObject();
            innerObj.addProperty("name", name);
            writeRoot(jsonObject);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /*
     * 更新某个按钮的信息!
     * */
    public boolean updateButton(int group, int button, String name, String cmd) {
        try {
            button += 1;
            JsonObject jsonObject = readRoot();
            JsonArray array = jsonObject.getAsJsonArray("group");
            JsonArray innerArr = array.get(group).getAsJsonArray();
            JsonObject innerObj = innerArr.get(button).getAsJsonObject();
            innerObj.addProperty("name", name);
            innerObj.addProperty("cmd", cmd);
            writeRoot(jsonObject);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

}
