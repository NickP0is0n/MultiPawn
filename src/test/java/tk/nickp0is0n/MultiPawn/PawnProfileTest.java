package tk.nickp0is0n.MultiPawn;

import org.ini4j.Wini;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class PawnProfileTest {

    private int count;

    @Before
    public void setUp() throws Exception {
        count = setCount();
    }

    @After
    public void tearDown() {
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
        var output = newProfile.getName();
        if(output.equalsIgnoreCase("Test")) System.out.println("Тест getName() пройден");
    }

    @Test
    public void getNumber() {
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

    private static int setCount() throws IOException {
        //noinspection MismatchedQueryAndUpdateOfCollection
        var ini = new Wini(new File("mpconfig.ini"));
        return ini.get("Base Config", "Count", int.class);
    }
}