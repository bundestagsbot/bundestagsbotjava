package org.bundestagsbot.Meta;

import java.util.ArrayList;
import java.util.List;

public class About {
    public static String getInfo() {
        return info;
    }

    private static final String info = "BundestagsBot by zaanposni for political discords.\n" +
            "Version: " + VersionInfo.getLongVersion() + "\n" +
            "GitHub: https://github.com/bundestagsbot/bundestagsbotjava/";
}
