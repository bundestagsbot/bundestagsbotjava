package org.bundestagsbot.LocalUtils;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
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
import java.util.Optional;
import java.util.logging.Level;

public class FileSystem {
    private static final Logger logger = LogManager.getLogger(FileSystem.class.getName());

    public static void saveJson(@NotNull String path, @NotNull JSONObject content) throws IOException {
        createFolderStructure(path);

        try(FileWriter file = new FileWriter((path))) {
            file.write(content.toJSONString());
            file.flush();
        }
        catch(IOException e) {
            logger.warn("Error writing JSON to File ", e);
            throw e;
        }
    }

    /**
     * @param path The path to the JSON you want to get
     * @return JSONObject of the JSON, if it exists, otherwise returns null
     **/
    public static Optional<JSONObject> loadJson(String path){
        JSONParser parser = new JSONParser();
        try{
            Object obj = parser.parse(new FileReader(path));
            return Optional.of((JSONObject) obj);
        }
        catch(IOException e) {
            logger.warn("Error reading file ", e);
            return Optional.empty();
        }
        catch(ParseException e){
           logger.warn("Error parsing file ", e);
            return Optional.empty();
        }
    }

    public static boolean writeFile(String path, String contents) throws IOException{
        createFolderStructure(path);

        try {
            Files.writeString(Paths.get(path), contents, StandardCharsets.UTF_8);
            return true;
        } catch (IOException e) {
            logger.warn("Error writing File ", e);
            return false;
        }
    }

    private static void createFolderStructure(String path) {
        String lastPath = path.substring(path.lastIndexOf("/"));
        String dirPath = path.replaceFirst(lastPath, "");
        Path paths = Paths.get(dirPath);
        try{
            Files.createDirectories(paths);
        }
        catch(IOException e){
            logger.warn("Error creating folder structure ", e);
        }
    }

    public static String loadFile(String path) throws IOException{
        return Files.readString(Paths.get(path), StandardCharsets.UTF_8);
    }
}