package com.ucam.flexicoche.service;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.GeneralSecurityException;
import java.util.Collections;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.google.api.client.http.FileContent;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.DriveScopes;
import com.google.api.services.drive.model.File;
import com.google.auth.http.HttpCredentialsAdapter;
import com.google.auth.oauth2.GoogleCredentials;

@Service
public class GoogleDriveService {
    //private static final String CREDENTIALS_FILE_PATH = "piis-flexicoche-304a9ebb3cba.json"; // debe estar en src/main/resources
    private static final String FOLDER_ID = "14KeGmKLUw68F7lcWNDn8IIOMCgxBURQD";

    private Drive getDriveService() throws IOException {
        //InputStream credentialsStream = getClass().getClassLoader().getResourceAsStream(CREDENTIALS_FILE_PATH);

    	String CREDENTIALS_FILE_PATH = System.getenv("GOOGLE_CREDENTIALS_JSON");

        //InputStream credentialsStream = getClass().getClassLoader().getResourceAsStream(CREDENTIALS_FILE_PATH);
    	
        InputStream credentialsStream = Files.newInputStream(Paths.get(CREDENTIALS_FILE_PATH));

        if (credentialsStream == null) {
            throw new FileNotFoundException("No se encontró el archivo de credenciales en resources.");
        }

        GoogleCredentials credentials = GoogleCredentials.fromStream(credentialsStream)
                .createScoped(Collections.singleton(DriveScopes.DRIVE_FILE));

        return new Drive.Builder(
                new NetHttpTransport(),
                GsonFactory.getDefaultInstance(),
                new HttpCredentialsAdapter(credentials)
        ).setApplicationName("flexicoche-drive-uploader").build();
    }

    public String uploadFile(MultipartFile file) throws IOException {
        File fileMetadata = new File();
        fileMetadata.setName(file.getOriginalFilename());
        fileMetadata.setParents(Collections.singletonList(FOLDER_ID));

        java.io.File tempFile = java.io.File.createTempFile("temp", file.getOriginalFilename());
        file.transferTo(tempFile);

        FileContent mediaContent = new FileContent(file.getContentType(), tempFile);
        File uploadedFile = getDriveService().files().create(fileMetadata, mediaContent)
                .setFields("id, webContentLink")
                .execute();

        return "https://drive.google.com/uc?export=view&id=" + uploadedFile.getId();
    }
}
