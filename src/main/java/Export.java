// package src.main.java;
// package Final;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import org.json.JSONObject;

import java.nio.file.Path;


public class Export {

    public static void main(String[] args) {
        HttpURLConnection exportStatusConnection = null;
        try {
            String export = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJhcHBJZCI6ImNzLWI2ZDY4Njk3LTA1ZmEtNTQwNC1iNzg4LTIxNWE3MWUwMjc0OSJ9.bRkzPwrHF2aWLhvS3e6iEI72XVsk6nuUVPWl-z0VaFQ";
            String exportStatusAuth = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJhcHBJZCI6ImNzLWI2ZDY4Njk3LTA1ZmEtNTQwNC1iNzg4LTIxNWE3MWUwMjc0OSJ9.bRkzPwrHF2aWLhvS3e6iEI72XVsk6nuUVPWl-z0VaFQ";
            String exportUrl = "https://bots.kore.ai/api/public/bot/st-fa3c2d6e-128d-5e18-a60a-eca34e4a9132/export";
            String exportBody = "{\"exportType\": \"published\",\"exportOptions\": {\"settings\": [\"botSettings\",\"botVariables\",\"ivrSettings\"],\"tasks\": [\"botTask\",\"knowledgeGraph\",\"smallTalk\"],\"nlpData\": [\"training_data\",\"bot_synonyms\",\"defaultDialog\",\"nlpSettings\",\"utterances\",\"patterns\",\"standardResponses\"]},\"subTasks\": {\"alerts\": [],\"actions\": [],\"dialogs\": []},\"allTasks\": true,\"customDashboards\": true,\"IncludeDependentTasks\": true}";

            // Export API Call
            URL exportUrlObj = new URL(exportUrl);
            HttpURLConnection exportConnection = (HttpURLConnection) exportUrlObj.openConnection();
            exportConnection.setRequestMethod("POST");
            exportConnection.setRequestProperty("auth", export);
            exportConnection.setRequestProperty("Content-Type", "application/json");
            exportConnection.setDoOutput(true);

            OutputStream exportOutputStream = exportConnection.getOutputStream();
            exportOutputStream.write(exportBody.getBytes());
            exportOutputStream.flush();
            exportOutputStream.close();
            System.out.println("Export  API Response Code :: " + exportConnection.getResponseCode());

            Thread.sleep(1500);

            // Export Status API call to get the download URL
            StringBuilder expStatusResp = new StringBuilder();
            String exportStatusUrl = "https://bots.kore.ai/api/public/bot/st-fa3c2d6e-128d-5e18-a60a-eca34e4a9132/export/status";
            exportStatusConnection = (HttpURLConnection) new URL(exportStatusUrl).openConnection();
            exportStatusConnection.setRequestMethod("GET");
            exportStatusConnection.setRequestProperty("auth", exportStatusAuth);
            System.out.println("Export Status API Response Code :: " + exportStatusConnection.getResponseCode());

            if (exportStatusConnection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                InputStream in = new BufferedInputStream(exportStatusConnection.getInputStream());
                BufferedReader reader = new BufferedReader(new InputStreamReader(in));

                String line;
                while ((line = reader.readLine()) != null) {
                    expStatusResp.append(line);
                }
                System.out.println("Export API Status Response :: " + expStatusResp);
                JSONObject jsonObject = new JSONObject(expStatusResp.toString());
                String downloadUrl = jsonObject.get("downloadURL").toString();
                System.out.println("downloadUrl:: " + downloadUrl);

                // Download the file using URL
                URL downloadUrlObj = new URL(downloadUrl);
                downloadFile(downloadUrlObj, "fullexport.zip");
                System.out.println("File Downloaded in current working directory");
                Thread.sleep(1500);

                // Unzip the files
                String zipFilePath = "fullexport.zip";
                String destDir = "ExportBot";
                unzip(zipFilePath, destDir);
                System.out.println("Files unzipped to " + destDir);

            } else {
                InputStream exportStatusInputStream = exportStatusConnection.getErrorStream();
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            exportStatusConnection.disconnect();
        }

    }

    public static void downloadFile(URL url, String fileName) throws Exception {
        try (InputStream in = url.openStream()) {
            // If the file needs to be copied to specific path, create custom path and
            // provide
            Path path = Paths.get("ExportBot");
            Files.copy(in, Paths.get(fileName), StandardCopyOption.REPLACE_EXISTING);
        }
    }

    private static void unzip(String zipFilePath, String destDir) {
        File dir = new File(destDir);
        // create output directory if it doesn't exist
        if (!dir.exists())
            dir.mkdirs();
        FileInputStream fis;
        // buffer for read and write data to file
        byte[] buffer = new byte[1024];
        try {
            fis = new FileInputStream(zipFilePath);
            ZipInputStream zis = new ZipInputStream(fis);
            ZipEntry ze = zis.getNextEntry();
            while (ze != null) {
                String fileName = ze.getName();
                File newFile = new File(destDir + File.separator + fileName);
                System.out.println("Unzipping to " + newFile.getAbsolutePath());
                // create directories for sub directories in zip
                new File(newFile.getParent()).mkdirs();
                FileOutputStream fos = new FileOutputStream(newFile);
                int len;
                while ((len = zis.read(buffer)) > 0) {
                    fos.write(buffer, 0, len);
                }
                fos.close();
                // close this ZipEntry
                zis.closeEntry();
                ze = zis.getNextEntry();
            }
            // close last ZipEntry
            zis.closeEntry();
            zis.close();
            fis.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            // Set user email
            ProcessBuilder setEmail = new ProcessBuilder("git", "config", "--global", "user.email", "darshana@gmail.com");
            Process emailProcess = setEmail.start();
            int emailExitCode = emailProcess.waitFor();
            if (emailExitCode == 0) {
                System.out.println("User email set successfully.");
            } else {
                System.out.println("Error setting user email.");
            }

            // Set user name
            ProcessBuilder setName = new ProcessBuilder("git", "config", "--global", "user.name", "darshana");
            Process nameProcess = setName.start();
            int nameExitCode = nameProcess.waitFor();
            if (nameExitCode == 0) {
                System.out.println("User name set successfully.");
            } else {
                System.out.println("Error setting user name.");
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
        String gitRepoPath = "."; // Replace this with the actual path to your Git repository
        // String commitMessage = "changes for gradle";

         // Git commands
        
        String gitAdd = "git add .";
        String gitCommit = "git commit -m 'Updated'";
        // String gitPush = "git push origin main";

        // Execute Git commands
        try {
            // executeCommand(gitRepoPath, tmp);
            executeCommand(gitRepoPath, gitAdd);
            System.out.println("Executing: " + gitCommit);
            executeCommand(gitRepoPath, gitCommit);
            // System.out.println("Executing: " + gitPush);
            // executeCommand(gitRepoPath, gitPush);
            System.out.println("Changes added, committed and push successfully.");
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            System.err.println("Failed to add, commit, and push changes." + e.getMessage());
        }
    }

    private static void executeCommand(String workingDir, String command) throws IOException, InterruptedException {
        ProcessBuilder processBuilder = new ProcessBuilder(command.split(" "));
        processBuilder.directory(new java.io.File(workingDir));
        Process process = processBuilder.start();
        int exitCode = process.waitFor();
        if (exitCode != 0) {
            throw new RuntimeException("Failed to execute command: " + command); 
        }
    }
 
    }

