package org.uas.util;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;

public class SessionManager implements Serializable {
    private static final String SESSION_FILE = "session.ser";

    private static SessionManager instance;
    private boolean isLoggedIn = false;

    // Static method to get the singleton instance
    public static SessionManager getInstance() {
        if (instance == null) {
            instance = new SessionManager();
            instance.createSessionFile();
        }
        return instance;
    }

    // Method to check if the session file doesn't exist
    public void createSessionFile() {
        File fileSession = new File(SESSION_FILE);
        if (!fileSession.exists()) {
            saveSession();
        } else {
            loadSession();
        }
    }

    private void loadSession() {
        try(ObjectInputStream ois = new ObjectInputStream(new FileInputStream(SESSION_FILE))) {
            SessionManager sm = (SessionManager) ois.readObject();
            this.isLoggedIn = sm.isLoggedIn;
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void saveSession() {
        try(ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(SESSION_FILE))) {
            oos.writeObject(this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Method to check if user is logged in
    public boolean isLoggedIn() {
        return isLoggedIn;
    }

    // Method to simulate login
    public void login() {
        isLoggedIn = true;
        saveSession();
    }

    // Method to simulate logout
    public void logout() {
        isLoggedIn = false;
        saveSession();
    }
}
