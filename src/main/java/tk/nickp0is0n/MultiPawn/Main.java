package tk.nickp0is0n.MultiPawn;

import org.ini4j.Wini;

import java.io.IOException;
import java.util.Scanner;

public class Main {

    private static Scanner in = new Scanner(System.in);
    private static Scanner inStr = new Scanner(System.in);

    private static int count = 0;

    public static void main(String[] args) throws IOException {
        System.out.println("MultiPawn 2.0");
        System.out.println("by NickP0is0n (nickp0is0n.me)");
        Wini ini = null;
        int count = 0;
        while (true)
        {
            ini = PawnProfile.getWini();
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
            System.out.println("(" + (count + 3) + ") Переименовать профиль");
            System.out.println("(" + (count + 4) + ") Выйти");
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
                System.out.println("\nВведите номер профиля, который вы хотите удалить:");
                int number = in.nextInt();
                var newProfile = new PawnProfile(number);
                newProfile.delete();
            }
            else if (choose == (count + 3))
            {
                System.out.println("\nВведите номер профиля, который вы хотите переименовать:");
                int number = in.nextInt();
                System.out.println("Введите новое имя профиля:");
                String newName = inStr.nextLine();
                var workProfile = new PawnProfile(number);
                workProfile.rename(newName);
            }
            else
            {
                PawnProfile newProfile = new PawnProfile(choose);
                newProfile.load();
                System.out.println("Профиль " + newProfile.getName() + " успешно загружен!");
                System.out.println("Нажмите любую клавишу для продолжения...");
                System.in.read();
            }
        }
    }

    private static boolean newProfile(int count) throws IOException // возвращает false (ошибка) либо true (успех)
    {
        System.out.println("\nВведите название нового профиля:");
        var newProfile = new PawnProfile(inStr.nextLine(), count+1);
        newProfile.create();
        System.out.println("Профиль успешно создан!");
        System.out.println("Теперь поместите в созданную папку все include");
        System.in.read();
        return true;
    }
}
