package tk.nickp0is0n.MultiPawn;

import org.apache.commons.io.FileUtils;
import org.ini4j.Wini;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class PawnProfile {
    private String name;
    private int number;

    public PawnProfile(String name, int number)
    {
        this.name = name;
        this.number = number;
    }

    public PawnProfile(int number) throws IOException {
        this.number = number;
        var ini = getWini();
        name = ini.get(String.valueOf(number), "Name", String.class);
    }

    public String getName()
    {
        return name;
    }

    public int getNumber()
    {
        return number;
    }

    public void create() throws IOException {
        var name = new File(this.name);
        Files.createDirectory(name.toPath());
        var ini = PawnProfile.getWini();
        ini.put(String.valueOf(number),"Name", name.toString());
        ini.put("Base Config", "Count", number);
        ini.store();
    }

    public void load() throws IOException {
            FileUtils.deleteDirectory(new File("include"));
            Files.createDirectory(new File("include").toPath());
            FileUtils.copyDirectory(new File(name), new File("include"));
    }

    public static Wini getWini() throws IOException {
        var ini = new Wini(new File("mpconfig.ini"));
        return ini;
    }
}
