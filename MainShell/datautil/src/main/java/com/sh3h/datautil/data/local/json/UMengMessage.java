package com.sh3h.datautil.data.local.json;


import android.util.JsonReader;
import android.util.JsonToken;

import com.sh3h.datautil.data.local.config.ConfigHelper;
import com.sh3h.mobileutil.util.LogUtil;
import com.sh3h.mobileutil.util.TextUtil;


import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class UMengMessage {
    private static final String TAG = "UMengMessage";
    private static final String FILE_NAME = "message.json";
    private static final String DEFAULT_INFO = "您有新消息!";
    private ConfigHelper configHelper;
    private Map<Integer, String> data;
    private boolean isRead;

    @Inject
    public UMengMessage(ConfigHelper configHelper) {
        this.configHelper = configHelper;
        this.data = new HashMap<>();
        this.isRead = false;
    }

    public void read() {
        File file = new File(this.configHelper.getConfigFolderPath(), FILE_NAME);
        if (!file.exists()) {
            LogUtil.e(TAG, "file isn't exist: " + FILE_NAME);
            return;
        }

        InputStream is = null;
        JsonReader reader = null;
        try {
            is = new FileInputStream(file);
            reader = new JsonReader(new InputStreamReader(is));
            reader.beginArray();
            while (reader.hasNext()) {
                int type = 0;
                String info = null;
                reader.beginObject();
                while (reader.hasNext()) {
                    String name = reader.nextName();
                    if (name.equals("type")) {
                        type = reader.nextInt();
                    } else if (name.equals("info") || reader.peek() != JsonToken.NULL) { // 当前获取的字段是否为：null
                        info = reader.nextString();
                    }
                }
                reader.endObject();
                if (type != 0 && !TextUtil.isNullOrEmpty(info)) {
                    this.data.put(type, info);
                }
            }
            reader.endArray();
            this.isRead = true;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (reader != null) {
                    reader.close();
                    reader = null;
                }

                if (is != null) {
                    is.close();
                    is = null;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public boolean isRead() {
        return isRead;
    }

    public String getInfo(int type) {
        String info = this.data.get(type);
        return TextUtil.isNullOrEmpty(info) ? DEFAULT_INFO : info;
    }
}
