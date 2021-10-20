/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package netfile;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

/**
 *
 * @author vitya
 */
public class FileSender {

    String fileName;
    File file = new File ("fileName");
    

    public static void main(String[] args) {
        System.out.println("File sender started...");
        new FileSender().run();
        System.out.println("File sender finished.");
    }

    private void run() {
        try (Socket s = new Socket("127.0.0.1", FileReceiver.SERVER_PORT);
                DataOutputStream outFileName = new DataOutputStream(s.getOutputStream());
                FileInputStream in = new FileInputStream(file.getAbsolutePath());
                OutputStream out = s.getOutputStream()) {
            outFileName.writeUTF(fileName);

            byte[] buf = new byte[FileReceiver.BUFFER_SIZE];
            while ((in.read(buf)) > 0) {
                out.write(buf);
            }
            out.write("Sender complete.".getBytes());

        } catch (IOException ex) {
            System.out.println("Error#1: " + ex.getMessage());
        }
    }

}
