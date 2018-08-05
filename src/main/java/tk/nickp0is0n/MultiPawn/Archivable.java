package tk.nickp0is0n.MultiPawn;

public interface Archivable {
    /*
    Этот интерфейс задает методы работы с архивированными профилями
    в MultiPawn. Также, интерфейс может использоваться для
    архивированных файлов в будущем.
     */
    void createArchive();
    void removeArchive();
    void unZip();
}
