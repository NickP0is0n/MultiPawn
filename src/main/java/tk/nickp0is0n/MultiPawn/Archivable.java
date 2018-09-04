package tk.nickp0is0n.MultiPawn;

import net.lingala.zip4j.exception.ZipException;

/*
   Этот интерфейс задает методы работы с архивированными профилями
   в MultiPawn. Также, интерфейс может использоваться для
   архивированных файлов в будущем.
*/
public interface Archivable {
    void createArchive() throws ZipException;
    void removeArchive() throws ZipException;
    void unZip() throws ZipException;
}
