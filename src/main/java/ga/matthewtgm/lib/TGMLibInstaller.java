/*
    # Copyright (C) MatthewTGM
    # This file is part of TGMLib <https://github.com/TGMDevelopment/TGMLib>.
    #
    # TGMLib is free software: you can redistribute it and/or modify
    # it under the terms of the GNU General Public License as published by
    # the Free Software Foundation, either version 3 of the License, or
    # (at your option) any later version.
    #
    # TGMLib is distributed in the hope that it will be useful,
    # but WITHOUT ANY WARRANTY; without even the implied warranty of
    # MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    # GNU General Public License for more details.
    #
    # You should have received a copy of the GNU General Public License
    # along with dogtag.  If not, see <http://www.gnu.org/licenses/>.
*/

package ga.matthewtgm.lib;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import net.minecraft.launchwrapper.Launch;
import net.minecraft.launchwrapper.LaunchClassLoader;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class TGMLibInstaller {

    private static final Gson gson = new Gson();

    private static final String versions_url = "https://raw.githubusercontent.com/TGMDevelopment/TGMLib-Data/main/versions.json";
    private static final String className = "ga.matthewtgm.lib.TGMLib";
    private static File dataDir;

    public static boolean isInitialized() {
        try {
            Launch.classLoader.clearNegativeEntries(new HashSet<>(Collections.singletonList(className)));
            Field invalidClasses = LaunchClassLoader.class.getDeclaredField("invalidClasses");
            if (!invalidClasses.isAccessible())
                invalidClasses.setAccessible(true);
            System.out.println(invalidClasses);
            Object obj = invalidClasses.get(Launch.classLoader);
            ((Set<String>) obj).remove(className);
            return Class.forName("ga.matthewtgm.lib.TGMLib") != null;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public static ReturnValue initialize(File gameDir) {
        if (isInitialized())
            return ReturnValue.FAILED;

        dataDir = new File(gameDir, "TGMLib");
        if (!dataDir.exists() || !dataDir.isDirectory()) {
            if (!dataDir.mkdirs()) {
                return ReturnValue.ERRORED;
            }
        }

        JsonObject versionsJson = gson.fromJson(fetchJson(versions_url), JsonObject.class);

        File metaFile = new File(dataDir, "meta.json");

        if (!metaFile.exists()) {
            BufferedWriter writer = null;
            try {
                metaFile.createNewFile();
                writer = new BufferedWriter(new FileWriter(metaFile));
                writer.write("{\"current\": \"" + versionsJson.get("latest").getAsString() + "\"}");
            } catch (IOException e) {
                e.printStackTrace();
                try {
                    writer.flush();
                    writer.close();
                } catch (Exception e1) {
                    e1.printStackTrace();
                }
            } finally {
                try {
                    writer.flush();
                    writer.close();
                } catch (Exception e1) {
                    e1.printStackTrace();
                }
            }
        }

        JsonObject localMetadataJson = gson.fromJson(readJsonFromFile(new File(dataDir, "meta.json")), JsonObject.class);

        System.out.println(localMetadataJson);


        File tgmLibFile = new File(dataDir, "TGMLib-" + versionsJson.get("latest").getAsString() + ".jar");

        if (!tgmLibFile.exists()) {
            File old = new File(dataDir, "TGMLib-" + localMetadataJson.get("current").getAsString() + ".jar");
            if (old.exists())
                old.delete();

            System.out.println(versionsJson);

            if (!download("https://raw.githubusercontent.com/TGMDevelopment/TGMLib-Data/main/downloads/TGMLib-" + versionsJson.get("latest").getAsString() + ".jar", versionsJson.get("latest").getAsString(), tgmLibFile, versionsJson))
                return ReturnValue.FAILED;
        }

        addToClasspath(tgmLibFile);

        if (!isInitialized())
            return ReturnValue.FAILED;

        return ReturnValue.SUCCESSFUL;
    }

    private static boolean download(String url, String version, File file, JsonObject versionData) {
        url = url.replace(" ", "%20");

        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        JFrame frame = new JFrame("TGMLib Installer");
        JProgressBar progressBar = new JProgressBar();
        JLabel label = new JLabel("Downloading TGMLib " + version, SwingConstants.CENTER);
        label.setSize(600, 120);
        frame.getContentPane().add(label);
        frame.getContentPane().add(progressBar);
        GroupLayout layout = new GroupLayout(frame.getContentPane());
        frame.getContentPane().setLayout(layout);
        layout.setHorizontalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                .addComponent(label, GroupLayout.Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 376, Short.MAX_VALUE)
                                .addComponent(progressBar, GroupLayout.Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addContainerGap()));
        layout.setVerticalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(label, GroupLayout.PREFERRED_SIZE, 55, GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(progressBar, GroupLayout.PREFERRED_SIZE, 33, GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)));
        frame.setResizable(false);
        progressBar.setBorderPainted(true);
        progressBar.setMinimum(0);
        progressBar.setStringPainted(true);
        Font font = progressBar.getFont();
        progressBar.setFont(new Font(font.getName(), font.getStyle(), font.getSize() * 2));
        label.setFont(new Font(font.getName(), font.getStyle(), font.getSize() * 2));
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);



        HttpURLConnection connection = null;
        try (FileOutputStream fout = new FileOutputStream(file)) {
            URL theUrl = new URL(url);
            connection = (HttpURLConnection) theUrl.openConnection();
            connection.setRequestMethod("GET");
            connection.setUseCaches(true);
            connection.setReadTimeout(20000);
            connection.setConnectTimeout(15000);
            connection.setDoOutput(true);

            int contentLength = connection.getContentLength();
            byte[] buffer = new byte[1024];

            progressBar.setMaximum(contentLength);

            int read;
            progressBar.setValue(0);
            while ((read = connection.getInputStream().read(buffer)) > 0) {
                fout.write(buffer, 0, read);
                progressBar.setValue(progressBar.getValue() + 1024);
            }
            FileUtils.write(new File(dataDir, "meta.json"), versionData.getAsString());
        } catch (Exception e) {
            if (!(e instanceof UnsupportedOperationException))
                e.printStackTrace();
            frame.dispose();
            return false;
        } finally {
            if (connection != null)
                connection.disconnect();
        }

        frame.dispose();
        return true;
    }

    private static void addToClasspath(File file) {
        try {
            URL url = file.toURI().toURL();

            ClassLoader classLoader = TGMLibInstaller.class.getClassLoader();
            Method method = classLoader.getClass().getDeclaredMethod("addURL", URL.class);
            method.setAccessible(true);
            method.invoke(classLoader, url);
        } catch (Exception e) {
            throw new RuntimeException("Unexpected exception...", e);
        }
    }

    private static String readJsonFromFile(File file) {
        try {
            StringBuilder builder = new StringBuilder();
            BufferedReader reader = new BufferedReader(new FileReader(file));
            reader.lines().forEach(builder::append);
            return builder.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private static String fetchJson(String url) {
        url = url.replace(" ", "%20");
        HttpURLConnection connection = null;
        try {
            URL theUrl = new URL(url);
            connection = (HttpURLConnection) theUrl.openConnection();
            connection.setRequestMethod("GET");
            connection.setUseCaches(true);
            connection.setReadTimeout(20000);
            connection.setConnectTimeout(15000);
            connection.setDoOutput(true);
            return IOUtils.toString(connection.getInputStream(), Charset.defaultCharset());
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            if (connection != null)
                connection.disconnect();
        }
    }

    public enum ReturnValue {
        FAILED,
        ERRORED,
        SUCCESSFUL
    }

}