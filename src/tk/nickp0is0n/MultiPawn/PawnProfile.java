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

    public PawnProfile(int number)
    {
        this.number = number;
    }

    public String getName()
    {
        return name;
    }

    public int getNumber()
    {
        return number;
    }

    public void getNameByNumber() throws IOException {
        Wini ini = null;
        ini = getWini();
        name = ini.get(String.valueOf(number), "Name", String.class);
    }

    public void create() throws IOException {
        var name = new File(this.name);
        try {
            Files.createDirectory(name.toPath());
        } catch (IOException e) {
            System.out.println("Ошибка при создании нового профиля!");
            System.out.println("Для выхода из программы нажмите любую клавишу.");
            System.in.read();
            System.exit(4);
        }
        Wini ini = null;
        try {
            ini = PawnProfile.getWini();
        } catch (IOException e) {
            System.out.println("Ошибка при создании нового профиля!");
            System.out.println("Для выхода из программы нажмите любую клавишу.");
            System.in.read();
            System.exit(4);
        }
        ini.put(String.valueOf(number),"Name", name.toString());
        ini.put("Base Config", "Count", number);
        try {
            ini.store();
        } catch (IOException e) {
            System.out.println("Ошибка при создании нового профиля!");
            System.out.println("Для выхода из программы нажмите любую клавишу.");
            System.in.read();
            System.exit(4);
        }
    }

    public void load() throws IOException {
        try {
            FileUtils.deleteDirectory(new File("include"));
        } catch (IOException e) {
            System.out.println("Ошибка при загрузке профиля!");
            System.out.println("Для выхода из программы нажмите любую клавишу.");
            System.in.read();
            System.exit(4);
        }
        try {
            Files.createDirectory(new File("include").toPath());
        } catch (IOException e) {
            System.out.println("Ошибка при загрузке профиля!");
            System.out.println("Для выхода из программы нажмите любую клавишу.");
            System.in.read();
            System.exit(4);
        }
        try {
            FileUtils.copyDirectory(new File(name), new File("include"));
        } catch (IOException e) {
            System.out.println("Ошибка при загрузке профиля!");
            System.out.println("Для выхода из программы нажмите любую клавишу.");
            System.in.read();
            System.exit(4);
        }
    }

    public static Wini getWini() throws IOException {
        try {
            var ini = new Wini(new File("mpconfig.ini"));
            return ini;
        } catch (IOException e) {
            System.out.println("Ошибка в файле конфигурации!");
            System.out.println("Для выхода из программы нажмите любую клавишу.");
            System.in.read();
            System.exit(4);
        }
        return null;
    }
}
