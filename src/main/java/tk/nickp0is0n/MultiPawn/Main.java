package tk.nickp0is0n.MultiPawn;

import net.lingala.zip4j.exception.ZipException;
import org.ini4j.Wini;
import org.jetbrains.annotations.Contract;

import java.io.IOException;
import java.util.Scanner;

public class Main {

    private static Scanner in = new Scanner(System.in);
    private static Scanner inStr = new Scanner(System.in);

    private static boolean directoryCustom = false;
    private static String customDir = "";

    @SuppressWarnings("InfiniteLoopStatement")
    public static void main(String[] args) throws IOException {
        System.out.println("MultiPawn Alpha 2.2.0.2509.1 with Java 11 support");
        System.out.println("by NickP0is0n (nickp0is0n.me)");
        System.out.println("Версия не предназначена для постоянного пользования!");
        System.out.println("Использование тестовых версий программы производится исключительно на ваш страх и риск!");
        Wini ini;
        int count;
        while (true)
        {
            ini = PawnProfile.getWini();
            count = ini.get("Base Config", "Count", int.class);
            directoryCustom = ini.get("Base Config", "IsDirCustom", boolean.class);
            customDir = ini.get("Base Config", "Custom Dir", String.class);
            System.out.println(" ");
            System.out.println("Выберите профиль, на который хотите переключится:");
            for (int i = 1; i <= count; i++)
            {
                String section = ini.get(String.valueOf(i), "Name", String.class);
                System.out.println("(" + i + ") " + section);
            }
            System.out.println("(" + (count + 1) + ") Добавить новый профиль");
            System.out.println("(" + (count + 2) + ") Удалить профиль");
            System.out.println("(" + (count + 3) + ") Переименовать профиль");
            System.out.println("(" + (count + 4) + ") Дополнительные опции");
            System.out.println("(" + (count + 5) + ") Выйти");
            int choose = in.nextInt();
            mainMenuExec(count, choose);
        }
    }

    @Contract(pure = true)
    static boolean isDirectoryCustom() {
        return directoryCustom;
    }

    @Contract(pure = true)
    static String getCustomDir() {
        return customDir;
    }

    private static void mainMenuExec(int count, int choose) throws IOException {
        var ini = PawnProfile.getWini();
        if (choose == (count + 1)) newProfile(count);
        else if (choose >= (count + 5)) System.exit(0);
        else if (choose == (count + 2)) deleteProfileShow();
        else if (choose == (count + 3)) renameProfileShow();
        else if (choose == (count + 4)) showAdditionalSettings(ini);
        else loadProfileShow(choose);
    }

    @SuppressWarnings("ResultOfMethodCallIgnored")
    private static void showAdditionalSettings(Wini ini) throws IOException {
        boolean inAdvOptions = true;
        while (inAdvOptions)
        {
            System.out.println("(1) Изменить папку для профилей");
            System.out.println("(2) Вернуть стандартную папку");
            System.out.println("(3) Импортировать профиль");
            System.out.println("(4) Экспортировать профиль");
            System.out.println("(5) Вернутся в главное меню");
            int advChoose = in.nextInt();
            switch (advChoose)
            {
                default:
                    System.out.println("Неправильный ввод\n");
                    break;
                case 1:
                    System.out.println("Введите путь к папке, где будут хранится профили:");
                    customDir = inStr.nextLine();
                    directoryCustom = true;
                    System.out.println("Кастомная папка установлена! Не забудьте перенести в неё существующие профили!\n");
                    ini.put("Base Config", "IsDirCustom", true);
                    ini.put("Base Config", "Custom Dir", customDir);
                    ini.store();
                    break;
                case 2:
                    directoryCustom = false;
                    customDir = "";
                    ini.put("Base Config", "IsDirCustom", false);
                    ini.put("Base Config", "Custom Dir", customDir);
                    ini.store();
                    System.out.println("Папка сброшена.");
                    break;
                case 3:
                    System.out.println("Введите название файла профиля (с учетом реестра и с расширением):");
                    var profileName = inStr.nextLine();
                    var profileFile = new OneFileProfile(profileName);
                    try {
                        profileFile.unZip();
                    } catch (ZipException e) {
                        System.out.println("Архив отсутствует или произошла ошибка при распаковке.");
                        pressAny();
                        break;
                    }
                    System.out.println("Профиль успешно импортирован!");
                    break;
                case 5:
                    inAdvOptions = false;
                    break;
            }
        }
    }

    //Методы дальше отвечают за обработку выбора в главном меню программы

    private static void newProfile(int count) throws IOException // возвращает false (ошибка) либо true (успех)
    {
        System.out.println("\nВведите название нового профиля:");
        var newProfile = new PawnProfile(inStr.nextLine(), count+1);
        newProfile.create();
        System.out.println("Профиль успешно создан!");
        System.out.println("Теперь поместите в созданную папку все include");
        //noinspection ResultOfMethodCallIgnored
        pressAny();
    }

    private static void deleteProfileShow() throws IOException {
        System.out.println("\nВведите номер профиля, который вы хотите удалить:");
        int number = in.nextInt();
        var newProfile = new PawnProfile(number);
        newProfile.delete();
    }

    private static void renameProfileShow() throws IOException {
        System.out.println("\nВведите номер профиля, который вы хотите переименовать:");
        int number = in.nextInt();
        System.out.println("Введите новое имя профиля:");
        String newName = inStr.nextLine();
        var workProfile = new PawnProfile(number);
        workProfile.rename(newName);
    }

    private static void loadProfileShow(int choose) throws IOException {
        PawnProfile newProfile = new PawnProfile(choose);
        newProfile.load();
        System.out.println("Профиль " + newProfile.getName() + " успешно загружен!");
        System.out.println("Нажмите любую клавишу для продолжения...");
        //noinspection ResultOfMethodCallIgnored
        pressAny();
    }

    @SuppressWarnings("ResultOfMethodCallIgnored")
    private static void pressAny() throws IOException {
        System.out.println("Нажмите Enter для продолжения");
        System.in.read();
    }
}
