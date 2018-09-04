package tk.nickp0is0n.MultiPawn;

import net.lingala.zip4j.core.ZipFile;
import net.lingala.zip4j.exception.ZipException;
import net.lingala.zip4j.model.*;

import java.io.File;

public class OneFileProfile implements Archivable {

    private String archiveName;

    OneFileProfile(String archiveName)
    {
        this.archiveName = archiveName;
    }

    @Override
    public void createArchive() throws ZipException {
        var zipParameters = new ZipParameters();
        zipParameters.setIncludeRootFolder(false);
        var zipFile = new ZipFile(new File(archiveName + ".mp"));
        zipFile.createZipFileFromFolder(archiveName, zipParameters, false, 1);
    }

    @Override
    public void removeArchive() {

    }

    @Override
    public void unZip() {

    }
}
