package tk.nickp0is0n.MultiPawn;

import org.apache.commons.io.FileUtils;
import org.ini4j.Ini;
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
        this.name = ini.get(String.valueOf(number), "Name", String.class);
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
        if (Main.isDirectoryCustom()) name = new File(Main.getCustomDir() + "/" + this.name);
        Files.createDirectory(name.toPath());
        var ini = PawnProfile.getWini();
        ini.put(String.valueOf(number),"Name", this.name);
        ini.put("Base Config", "Count", number);
        ini.store();
    }

    public void load() throws IOException {
        FileUtils.deleteDirectory(new File("include"));
        Files.createDirectory(new File("include").toPath());
        String name = this.name;
        if (Main.isDirectoryCustom()) name = Main.getCustomDir() + "/" + this.name;
        FileUtils.copyDirectory(new File(name), new File("include"));
    }

    public void delete() throws IOException {
        var ini = getWini();
        ini.remove(ini.get(String.valueOf(number)));
        String name = this.name;
        if (Main.isDirectoryCustom()) name = Main.getCustomDir() + "/" + this.name;
        FileUtils.deleteDirectory(new File(name));
        var totalcount = ini.get("Base Config", "Count", int.class);
        for (int i = number + 1; i <= totalcount; i++)
        {
            name = ini.get(String.valueOf(i), "Name", String.class);
            ini.put(String.valueOf(i - 1), "Name", name);
        }
        ini.put("Base Config", "Count", totalcount-1);
        ini.store();
    }

    public void rename(String name) throws IOException {
        var ini = getWini();
        ini.put(String.valueOf(number), "Name", name);
        ini.store();
        String name1 = this.name;
        if (Main.isDirectoryCustom()) name1 = Main.getCustomDir() + "/" + this.name;
        var directory = new File(name1);
        if (Main.isDirectoryCustom()) directory.renameTo(new File(Main.getCustomDir() + "/" + name));
        else directory.renameTo(new File(name));
        this.name = name;
    }

    public static Wini getWini() throws IOException {
        var ini = new Wini(new File("mpconfig.ini"));
        return ini;
    }
}
