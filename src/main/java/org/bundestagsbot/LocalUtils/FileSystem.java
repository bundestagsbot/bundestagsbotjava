package org.bundestagsbot.LocalUtils;

import org.jetbrains.annotations.NotNull;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;


public class FileSystem {

    /**
     * @param path has to be an existing path
     * @param content
     * @return boolean whether the file was saved, false in case of IOException
     */
    public static boolean saveJson(@NotNull String path, @NotNull JSONObject content){
        createFolderStructure(path);

        try(FileWriter file = new FileWriter((path))) {
            file.write(content.toJSONString());
            file.flush();
            return true;
        }
        catch(IOException e) {
            e.printStackTrace();
            System.out.println(path);
            return false;
        }


    }


    /**
     * @param path The path to the JSON you want to get
     * @return JSONObject of the JSON, if it exists, otherwise returns null
     **/
    public static JSONObject loadJson(String path, boolean print){
        JSONParser parser = new JSONParser();
        try{
            Object obj = parser.parse(new FileReader(path));

            if(print) System.out.println(obj);
            return (JSONObject) obj;
        } catch(IOException | ParseException e){
            e.printStackTrace();
            return null;
        }

    }

    public static boolean writeFile(String path, String contents) {
        createFolderStructure(path);


        try {
            Files.writeString(Paths.get(path), contents, StandardCharsets.UTF_8);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    private static void createFolderStructure(String path) {
        String lastPath = path.substring(path.lastIndexOf("/"));
        String dirPath = path.replaceFirst(lastPath, "");
        Path paths = Paths.get(dirPath);
        try{
            Files.createDirectories(paths);
        }
        catch(IOException e){
            e.printStackTrace();
        }
    }

    public static String loadFile(String path, boolean print) {


        try {
            return Files.readString(Paths.get(path), StandardCharsets.UTF_8);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;


    }

    public static JSONObject loadJson(String path){
        return loadJson(path, true);
    }


}
