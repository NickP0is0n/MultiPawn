package tk.nickp0is0n.MultiPawn;

import org.ini4j.Wini;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class PawnProfileTest {

    Wini ini;
    int count;

    @Before
    public void setUp() throws Exception {
        ini = setWini();
        count = setCount();
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void create() throws Exception{
        var newProfile = new PawnProfile("Test", count+1);
        newProfile.create();
        File dir = new File("Test");
        if(dir.exists()) System.out.println("Тест create() пройден");
    }

    @Test
    public void getName() throws IOException {
        var newProfile = new PawnProfile(count);
        var output = ini.get(String.valueOf(count), "Name", String.class);
        if(output.equalsIgnoreCase("Test")) System.out.println("Тест getName() пройден");
    }

    @Test
    public void getNumber() throws IOException {
        var newProfile = new PawnProfile("Test", count);
        int number = newProfile.getNumber();
        if (number == count) System.out.println("Тест getNumber() пройден");
    }

    @Test
    public void load() throws IOException {
        var newProfile = new PawnProfile("Test", count);
        Files.createFile(new File("Test/testfile.txt").toPath());
        newProfile.load();
        var movedFile = new File("include/testfile.txt");
        if (movedFile.exists()) System.out.println("Тест load() пройден");
    }

    @Test
    public void delete() throws IOException {
        var newProfile = new PawnProfile("Test", count);
        newProfile.delete();
        File dir = new File("Test");
        if(!dir.exists()) System.out.println("Тест delete() пройден");
        newProfile = new PawnProfile("Test", count+1);
        newProfile.create();
    }

    @Test
    public void rename() throws IOException {
        var newProfile = new PawnProfile("Test", count);
        String testName = String.valueOf(Math.random()*99999);
        newProfile.rename(testName);
        if (newProfile.getName().equalsIgnoreCase(testName)) System.out.println("Тест rename() пройден");
        newProfile.rename("Test");
    }

    public static int setCount() throws IOException {
        var ini = new Wini(new File("mpconfig.ini"));
        var count = ini.get("Base Config", "Count", int.class);
        return count;
    }

    public static Wini setWini() throws IOException {
        var ini = new Wini(new File("mpconfig.ini"));
        return ini;
    }
}