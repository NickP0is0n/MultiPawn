package tk.nickp0is0n.MultiPawn;

import net.lingala.zip4j.exception.ZipException;
import org.apache.commons.io.FileUtils;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

import static org.junit.Assert.*;

public class OneFileProfileTest {

    @Test
    public void createArchive() throws IOException, ZipException {
        var oneProfile = new OneFileProfile("Test");
        Files.createDirectory(new File("Test").toPath());
        Files.createFile(new File("Test/testfile.pwn").toPath());
        oneProfile.createArchive();
        var dir = new File("Test.mp");
        if(dir.exists()) System.out.println("Тест createArchive() пройден");
        //noinspection ResultOfMethodCallIgnored
        dir.delete();
        FileUtils.deleteDirectory(new File("Test"));
    }

    @Test
    public void removeArchive() {
    }

    @Test
    public void unZip() {
    }
}