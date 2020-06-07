package org.bundestagsbot.LocalUtils;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
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

import static org.bundestagsbot.Commands.ICommandExecutor.LOGGER;


public class FileSystem {



    public static void saveJson(@NotNull String path, @NotNull JSONObject content) throws IOException {
        createFolderStructure(path);

        try(FileWriter file = new FileWriter((path))) {
            file.write(content.toJSONString());
            file.flush();
        }
        catch(IOException e) {
            LOGGER.log(Level.WARNING, "Error writing JSON to File ", e);
            throw e;
        }


    }


    /**
     * @param path The path to the JSON you want to get
     * @return JSONObject of the JSON, if it exists, otherwise returns null
     **/
    @Nullable
    public static JSONObject loadJson(String path, boolean print){
        JSONParser parser = new JSONParser();
        try{
            Object obj = parser.parse(new FileReader(path));

            if(print) System.out.println(obj);
            return (JSONObject) obj;
        }
        catch(IOException e) {
            LOGGER.log(Level.WARNING, "Error reading file ", e);
            return null;

        }
        catch(ParseException e){
            LOGGER.log(Level.WARNING, "Error parsing file ", e);
            return null;
        }

    }

    public static boolean writeFile(String path, String contents) throws IOException{

        createFolderStructure(path);

        try {
            Files.writeString(Paths.get(path), contents, StandardCharsets.UTF_8);
            return true;
        } catch (IOException e) {
            LOGGER.log(Level.WARNING, "Error writing File ", e);
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
            LOGGER.log(Level.WARNING, "Error creating folder structure ", e);
        }
    }

    public static String loadFile(String path, boolean print) throws IOException{

        return Files.readString(Paths.get(path), StandardCharsets.UTF_8);

    }

    public static Optional<JSONObject> loadJson(String path){

        return Optional.ofNullable(loadJson(path, true));
    }


}
