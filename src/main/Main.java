/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main;

import GUI.Login.loginFrame;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;


public class Main {
    public static void main(String[] args) {
        try {
            ProcessBuilder installLibs = new ProcessBuilder("python", "-m", "pip", "install", "-r", "src/main/requirement.txt");
            installLibs.redirectErrorStream(true);
            Process installProcess = installLibs.start();

            BufferedReader installReader = new BufferedReader(new InputStreamReader(installProcess.getInputStream()));
            String installLine;
            while ((installLine = installReader.readLine()) != null) {
                System.out.println("[INSTALL] " + installLine);
            }

            // Chạy Flask API
            String pythonScriptPath = "src/main/apriori.py";
            ProcessBuilder pb = new ProcessBuilder("python", pythonScriptPath);
            pb.redirectErrorStream(true);

            Process process = pb.start();

            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println("[PYTHON] " + line);
                if (line.contains("Running on")) {
                    break;
                }
            }

            // Mở giao diện Java
            loginFrame lg = new loginFrame();
            lg.setVisible(true);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}


