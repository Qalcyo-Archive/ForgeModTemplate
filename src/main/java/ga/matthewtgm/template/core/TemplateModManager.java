package ga.matthewtgm.template.core;

import ga.matthewtgm.lib.other.StandardVersionChecker;
import lombok.Getter;

import java.awt.*;
import java.io.IOException;
import java.net.URI;

public class TemplateModManager {

    /* Version Checker. */
    @Getter private final StandardVersionChecker versionChecker = new StandardVersionChecker("https://example.com/versions.json");

    /* Utility Methods. */
    public boolean openDownloadPage() {
        try {
            Desktop.getDesktop().browse(URI.create("https://example.com/download"));
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

}