package tk.nickp0is0n.MultiPawn;

import net.lingala.zip4j.exception.ZipException;
import org.apache.commons.io.FileUtils;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class OneFileProfileTest {

    @Test
    public void createArchive() throws IOException, ZipException {
        var oneProfile = new OneFileProfile("Test1");
        Files.createDirectory(new File("Test1").toPath());
        Files.createFile(new File("Test1/testfile.pwn").toPath());
        oneProfile.createArchive();
        var dir = new File("Test1.mp");
        if(dir.exists()) System.out.println("Тест createArchive() пройден");
        //noinspection ResultOfMethodCallIgnored
        dir.delete();
        FileUtils.deleteDirectory(new File("Test1"));
    }

    @Test
    public void removeArchive() throws IOException, ZipException {
        var oneProfile = new OneFileProfile("Test1");
        Files.createDirectory(new File("Test1").toPath());
        Files.createFile(new File("Test1/testfile.pwn").toPath());
        oneProfile.createArchive();
        oneProfile.removeArchive();
        if(!new File("Test1.mp").exists()) System.out.println("Тест removeArchive() пройден");
        FileUtils.deleteDirectory(new File("Test1"));
    }

    @Test
    public void unZip() throws IOException, ZipException {
        var oneProfile = new OneFileProfile("Test1");
        Files.createDirectory(new File("Test1").toPath());
        Files.createFile(new File("Test1/testfile.pwn").toPath());
        oneProfile.createArchive();
        FileUtils.deleteDirectory(new File("Test1"));
        oneProfile.unZip();
        if(new File("Test1").exists()) System.out.println("Тест unZip() пройден [1/2]");
        if(new File("Test1/testfile.pwn").exists()) System.out.println("Тест unZip() пройден [2/2]");
        FileUtils.deleteDirectory(new File("Test1"));
        Files.delete(new File("Test1.mp").toPath());
    }
}
