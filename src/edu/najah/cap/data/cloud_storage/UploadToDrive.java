package edu.najah.cap.data.cloud_storage;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.batch.BatchRequest;
import com.google.api.client.googleapis.batch.json.JsonBatchCallback;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.googleapis.json.GoogleJsonError;
import com.google.api.client.http.FileContent;
import com.google.api.client.http.HttpHeaders;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.DriveScopes;
import com.google.api.services.drive.model.File;
import com.google.api.services.drive.model.FileList;
import com.google.api.services.drive.model.Permission;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class UploadToDrive {
    /**
     * Application name.
     */
    private static final String APPLICATION_NAME =
            "Drive API Java Sample";

    /**
     * Directory to store user credentials for this application.
     */
    private static final java.io.File DATA_STORE_DIR = new java.io.File(
            System.getProperty("user.home"), ".credentials/drive-java-quickstart");
    /**
     * Global instance of the JSON factory.
     */
    private static final JsonFactory JSON_FACTORY =
            JacksonFactory.getDefaultInstance();
    /**
     * Global instance of the scopes required by this quickstart.
     * If modifying these scopes, delete your previously saved credentials
     * at ~/.credentials/drive-java-quickstart
     */
    private static final List<String> SCOPES =
            List.of(DriveScopes.DRIVE_FILE);
    /**
     * Global instance of the {@link FileDataStoreFactory}.
     */
    private static FileDataStoreFactory DATA_STORE_FACTORY;
    /**
     * Global instance of the HTTP transport.
     */
    private static HttpTransport HTTP_TRANSPORT;

    static {
        try {
            HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
            DATA_STORE_FACTORY = new FileDataStoreFactory(DATA_STORE_DIR);
        } catch (Throwable t) {
            t.printStackTrace();
            System.exit(1);
        }
    }

    /**
     * Creates an authorized Credential object.
     *
     * @return an authorized Credential object.
     * @throws IOException if an I/O error occurs
     */
    public static Credential authorize() throws IOException {
        // Load client secrets.
        InputStream in =
                UploadToDrive.class.getResourceAsStream("2.json");
        assert in != null;
        GoogleClientSecrets clientSecrets =
                GoogleClientSecrets.load(JSON_FACTORY, new InputStreamReader(in));

        // Build flow and trigger user authorization request.
        GoogleAuthorizationCodeFlow flow =
                new GoogleAuthorizationCodeFlow.Builder(
                        HTTP_TRANSPORT, JSON_FACTORY, clientSecrets, SCOPES)
                        .setDataStoreFactory(DATA_STORE_FACTORY)
                        .setAccessType("offline")
                        .build();
        Credential credential = new AuthorizationCodeInstalledApp(
                flow, new LocalServerReceiver()).authorize("user");
        return credential;
    }

    /**
     * Build and return an authorized Drive client service.
     *
     * @return an authorized Drive client service
     * @throws IOException if an I/O error occurs
     */
    public static Drive getDriveService() throws IOException {
        Credential credential = authorize();
        return new Drive.Builder(
                HTTP_TRANSPORT, JSON_FACTORY, credential)
                .setApplicationName(APPLICATION_NAME)
                .build();
    }

    /**
     * Get the folder ID if it exists, or create a new folder and return its ID.
     *
     * @param service    an authorized Drive client service
     * @param folderName Name of folder to create or check
     * @return a folder Id
     * @throws IOException if an I/O error occurs
     */
    public static String getOrCreateFolderId(Drive service, String folderName) throws IOException {
        // Check if the folder already exists
        String folderId = getFolderId(service, folderName);
        if (folderId != null) {
            return folderId;
        }

        // If the folder doesn't exist, create a new one
        return createFolderAndGetID(service, folderName);
    }

    /**
     * Get the folder ID if it exists, or return null if it doesn't.
     *
     * @param service    an authorized Drive client service
     * @param folderName Name of folder to check
     * @return a folder ID or null if the folder doesn't exist
     * @throws IOException if an I/O error occurs
     */
    public static String getFolderId(Drive service, String folderName) throws IOException {
        String query = "mimeType='application/vnd.google-apps.folder' and name='" + folderName + "'";
        FileList result = service.files().list().setQ(query).execute();
        List<File> files = result.getFiles();
        return files.isEmpty() ? null : files.get(0).getId();
    }

    /**
     * Create a folder or get the existing folder and return its ID.
     *
     * @param folderName Name of folder to create or get
     * @param service    an authorized Drive client service
     * @return a folder Id
     * @throws IOException if an I/O error occurs
     */
    public static String createFolderAndGetID(Drive service, String folderName) throws IOException {
        File fileMetadata = new File();
        fileMetadata.setName(folderName);
        fileMetadata.setMimeType("application/vnd.google-apps.folder");

        File file = service.files().create(fileMetadata)
                .setFields("id")
                .execute();

        return file.getId();
    }


    /**
     * Set permissions to a file in Google Drive.
     *
     * @param fileId  ID of the file to modify
     * @param service an authorized Drive client service
     * @throws IOException if an I/O error occurs
     */
    public static void setPermissionsToFile(Drive service, String fileId) throws IOException {

        JsonBatchCallback<Permission> callback = new JsonBatchCallback<>() {
            @Override
            public void onFailure(GoogleJsonError e,
                                  HttpHeaders responseHeaders) {
                // Handle error
                System.err.println(e.getMessage());
            }

            @Override
            public void onSuccess(Permission permission,
                                  HttpHeaders responseHeaders) {

            }
        };
        BatchRequest batch = service.batch();
        Permission userPermission = new Permission()
                .setType("anyone")
                .setRole("reader")
                .setAllowFileDiscovery(false);
        service.permissions().create(fileId, userPermission)
                .setFields("id")
                .queue(batch, callback);

        batch.execute();
    }

    /**
     * Upload files to a Google Drive folder.
     *
     * @param filesPath Name of folder that contain the files to upload
     * @param service   an authorized Drive client service
     * @throws IOException if an I/O error occurs
     */
    public static void uploadFilesAndGetURIs(Drive service, String filesPath) throws IOException {

        /* Create a folder and get Folder Id */
        String folderId = getOrCreateFolderId(service, "TestCopyAndShareFiles");

        /* Read files names from filesystem */
        List<String> files = new ArrayList<>();

        java.io.File[] filesNames = new java.io.File(filesPath).listFiles(
                (dir, name) -> name.endsWith(".zip")
        );

        if (filesNames != null) {
            for (java.io.File file : filesNames) {
                if (file.isFile()) {
                    files.add(file.getName());
                }
            }
        }


        for (String fileName : files) {
            /* Upload files to Google Drive */
            File fileMetadata = new File();
            fileMetadata.setName(fileName);
            fileMetadata.setParents(Collections.singletonList(folderId));
            java.io.File filePath = new java.io.File(filesPath + "/" + fileName);
            FileContent mediaContent = new FileContent("application/pdf", filePath);
            File file = service.files().create(fileMetadata, mediaContent)
                    .setFields("id, name, webContentLink, webViewLink")
                    .execute();

            /* Set permissions to file */
            setPermissionsToFile(service, file.getId());

            /* Get file properties: Id, Name, Link */
            System.out.println("Download Link: " + file.getWebContentLink());

        }
    }
}
