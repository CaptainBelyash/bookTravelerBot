package org.o7planning.googledrive.quickstart;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.DriveScopes;
import com.google.api.services.drive.model.File;
import com.google.api.services.drive.model.FileList;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.io.InputStreamReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class GoogleDrive {

    private Drive Service;
    private HashMap<String, String> Files = new HashMap<>(); // ключ - имя файла, значение - его ID

    private static final String APPLICATION_NAME = "Google Drive API Java Quickstart";
    private static final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();
    private static final java.io.File CREDENTIALS_FOLDER //
            = new java.io.File(System.getProperty("user.home"), "credentials");

    private static final String CLIENT_SECRET_FILE_NAME = "client_secret.json";
    private static final List<String> SCOPES = Collections.singletonList(DriveScopes.DRIVE);

    public Drive getDrive() {
        return Service;
    }

    public GoogleDrive() throws IOException, GeneralSecurityException {
        if (!CREDENTIALS_FOLDER.exists()) {
            System.out.println("Created Folder: " + CREDENTIALS_FOLDER.getAbsolutePath());
            System.out.println("Copy file " + CLIENT_SECRET_FILE_NAME + " into folder above.. and rerun this class!!");
            return;
        }
        final NetHttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();

        Credential credential = getCredentials(HTTP_TRANSPORT);

        Service = new Drive.Builder(HTTP_TRANSPORT, JSON_FACTORY, credential) //
                .setApplicationName(APPLICATION_NAME).build();

        FileList files = Service.files().list().setFields("nextPageToken, files(id, name)").execute();
        for (File file : files.getFiles()) {
            Files.put(file.getName(), file.getId());
        }
    }

    private static Credential getCredentials(final NetHttpTransport HTTP_TRANSPORT) throws IOException {

        java.io.File clientSecretFilePath = new java.io.File(CREDENTIALS_FOLDER, CLIENT_SECRET_FILE_NAME);

        if (!clientSecretFilePath.exists()) {
            throw new FileNotFoundException("Please copy " + CLIENT_SECRET_FILE_NAME //
                    + " to folder: " + CREDENTIALS_FOLDER.getAbsolutePath());
        }
        // Load client secrets.
        InputStream in = new FileInputStream(clientSecretFilePath);

        GoogleClientSecrets clientSecrets = GoogleClientSecrets.load(JSON_FACTORY, new InputStreamReader(in));

        // Build flow and trigger user authorization request.
        GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(HTTP_TRANSPORT, JSON_FACTORY,
                clientSecrets, SCOPES).setDataStoreFactory(new FileDataStoreFactory(CREDENTIALS_FOLDER))
                .setAccessType("offline").build();

        return new AuthorizationCodeInstalledApp(flow, new LocalServerReceiver()).authorize("user");
    }

    String getFileId(String name) {
        if (!Files.containsKey(name)) {
            System.out.println("извините, текст данной книги добавится позже");
        }
        if (!Files.isEmpty())
            return Files.get(name);
        return null;
    }

    public String getTextByGoogleDisk(Drive service, String fileName) throws IOException {
        OutputStream outputStream = new ByteArrayOutputStream();
        var fileId = getFileId(fileName); //
        service.files().export(fileId, "text/plain")
                .executeMediaAndDownloadTo(outputStream);
        return "a"+outputStream.toString();
    }

    public ArrayList<String> getParagraphsList(String text) {
        String[] strArray = text.split("\\n");
        ArrayList<String> result = new ArrayList<>();
        var currentNumber = 0;

        for (String s : strArray) {
            if (s.length() > 1) {
                StringBuilder line = new StringBuilder(s);
                line.insert(0, "(" + currentNumber + ") ");
                currentNumber++;
                result.add(line.toString());
            }
        }
        return result;
    }

    public static void main(String... args) {
        System.out.println("CREDENTIALS_FOLDER: " + CREDENTIALS_FOLDER.getAbsolutePath());
    }
}
