package tk.nickp0is0n.MultiPawn;

import org.ini4j.Wini;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Scanner;

public class Main {

    private static Scanner in = new Scanner(System.in);
    private static Scanner inStr = new Scanner(System.in);

    private static int count = 0;

    public static void main(String[] args) throws IOException {
        System.out.println("MultiPawn Alpha v1.1.0.0906.1");
        System.out.println("by NickP0is0n (nickp0is0n.tk)");
        Wini ini = null;
        int count = 0;
        while (true)
        {
            ini = getWini(ini);
            count = ini.get("Base Config", "Count", int.class);
            System.out.println(" ");
            System.out.println("Выберите профиль, на который хотите переключится:");
            for (int i = 1; i <= count; i++)
            {
                String section = ini.get(String.valueOf(i), "Name", String.class);
                System.out.println("(" + i + ") " + section);
            }
            System.out.println("(" + (count + 1) + ") Добавить новый профиль");
            System.out.println("(" + (count + 2) + ") Удалить профиль");
            System.out.println("(" + (count + 3) + ") Выйти");
            int choose = in.nextInt();
            if (choose == (count + 1))
            {
                if (!newProfile(count))
                {
                    System.out.println("Ошибка при создании нового профиля!");
                    System.out.println("Для выхода из программы нажмите любую клавишу.");
                    System.in.read();
                    System.exit(4);
                }

            }
            else if (choose == (count + 3))
            {
                System.exit(-1);
            }
            else if (choose == (count + 2))
            {
                if (!DeleteProfile())
                {
                    System.out.println("Ошибка при удалении профиля!");
                    System.out.println("Для выхода из программы нажмите любую клавишу.");
                    System.in.read();
                    System.exit(4);
                }
            }
            else
            {
                String name = ini.get(String.valueOf(choose), "Name", String.class);
                if (!loadProfile(name))
                {
                    System.out.println("Ошибка при загрузке профиля!");
                    System.out.println("Для выхода из программы нажмите любую клавишу.");
                    System.in.read();
                    System.exit(4);
                }
            }
        }
    }

    private static boolean DeleteProfile() {
        System.out.println("\nВведите номер профиля, который вы хотите удалить:");
        int number = in.nextInt();
        System.out.println("В разработке");
        return true;
    }

    private static boolean loadProfile(String name) throws IOException // возвращает false (ошибка) либо true (успех)
    {
        try {
            FileUtils.deleteDirectory(new File("include"));
        } catch (IOException e) {
            return false;
        }
        try {
            Files.createDirectory(new File("include").toPath());
        } catch (IOException e) {
            return false;
        }
        try {
            FileUtils.copyDirectory(new File(name), new File("include"));
        } catch (IOException e) {
            return false;
        }
        System.out.println("Профиль " + name + " успешно загружен!");
        System.out.println("Нажмите любую клавишу для продолжения...");
        System.in.read();
        return true;
    }

    private static boolean newProfile(int count) throws IOException // возвращает false (ошибка) либо true (успех)
    {
        System.out.println("\nВведите название нового профиля:");
        File name = new File(inStr.nextLine());
        try {
            Files.createDirectory(name.toPath());
        } catch (IOException e) {
            return false;
        }
        Wini ini = null;
        try {
            ini = getWini(ini);
        } catch (IOException e) {
            return false;
        }
        ini.put(String.valueOf(count + 1),"Name", name.toString());
        ini.put("Base Config", "Count", count + 1);
        try {
            ini.store();
        } catch (IOException e) {
            return false;
        }
        System.out.println("Профиль успешно создан!");
        System.out.println("Теперь поместите в созданную папку все include");
        System.in.read();
        return true;
    }

    private static Wini getWini(Wini ini) throws IOException {
        try {
            ini = new Wini(new File("mpconfig.ini"));
        } catch (IOException e) {
            System.out.println("Ошибка в файле конфигурации!");
            System.out.println("Для выхода из программы нажмите любую клавишу.");
            System.in.read();
            System.exit(4);
        }
        return ini;
    }
}
