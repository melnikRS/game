import java.io.*;
import java.util.Arrays;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class Main {
    // mac os
    private static final String USER_NAME = "admin";
    private static final String PATH = "/Users/" + USER_NAME + "/Games/";
    private static final StringBuilder log = new StringBuilder();

    public static void createDirectory(String name) {

        File directory = new File(PATH + name);
        if (directory.mkdir())
            log.append("Каталог " + name + " создан\n");
        else log.append("Каталог " + name + "  создан\n");

    }

    public static void createFiles(String name) {

        File file = new File(PATH + name);
        try {
            if (file.createNewFile()) {
                log.append("Файл " + name + " был создан\n");
            } else {
                log.append("Файл " + name + " не был создан\n");
            }
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }

    }

    public static void recordFiles(String name) {

        try (FileWriter writer = new FileWriter(PATH + name, false)) {
            writer.write(log.toString());
            writer.flush();
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }

    }

    public static void zipFiles(String path, List<String> pathString) {

        try (ZipOutputStream zout = new ZipOutputStream(new FileOutputStream(path))){
            pathString.forEach( i -> {
                try (FileInputStream fis = new FileInputStream(i)) {

                    ZipEntry entry = new ZipEntry(i);
                    zout.putNextEntry(entry);
                    byte[] buffer = new byte[fis.available()];
                    fis.read(buffer);
                    zout.write(buffer);
                    zout.closeEntry();

                } catch (Exception exception) {
                    System.out.println(exception.getMessage());
                }
            });
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    public static void deleteFiles(List<String> pathString) {

        pathString.forEach( i -> {
            File file = new File(i);
            file.delete();
        });

    }

    public static void main(String[] args) {

        createDirectory("src");
        createDirectory("src/main");
        createFiles("src/main//Main.java");
        createFiles("src/main//Utils.java");
        createDirectory("src/test");
        createDirectory("res");
        createDirectory("res/drawables");
        createDirectory("res/vectors");
        createDirectory("res/icons");
        createDirectory("savegames");
        createDirectory("temp");
        createFiles("temp//temp.txt");
        recordFiles("temp//temp.txt");

        GameProgress gameProgress1 = new GameProgress(1, 2, 3, 3.15);
        GameProgress gameProgress2 = new GameProgress(2, 3, 4, 4.15);
        GameProgress gameProgress3 = new GameProgress(3, 4, 5, 5.15);

        gameProgress1.saveGame(PATH + "savegames/save1.dat", gameProgress1);
        gameProgress2.saveGame(PATH + "savegames/save2.dat", gameProgress2);
        gameProgress3.saveGame(PATH + "savegames/save3.dat", gameProgress3);

        List<String> str = Arrays.asList(PATH + "savegames/save1.dat", PATH + "savegames/save2.dat", PATH + "savegames/save3.dat");
        zipFiles(PATH + "//zip.zip", str);
        deleteFiles(str);
    }
}