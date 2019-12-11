package org.o7planning.googledrive.quickstart;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

class GoogleDriveTest {
    GoogleDrive googleDriveEx;

    {
        try {
            googleDriveEx = new GoogleDrive();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (GeneralSecurityException e) {
            e.printStackTrace();
        }
    }

    @Test
    void getFileId() {
        var actual = googleDriveEx.getFileId("Проверка");
        var expected = "1AZEXSBvmyMDwqyugIvD_oqMMpe0aH3BPsLjkxqutpQg";
        assertEquals(expected, actual);
    }

    @Test
    void getParagraphsList() throws IOException {
        var service = googleDriveEx.getDrive();
        var text = googleDriveEx.getTextByGoogleDisk(service, "Проверка");
        var expected = googleDriveEx.getParagraphsList(text);
        var actual = new ArrayList<String>();
        actual.add("(0) dfghjk\n");
        actual.add("(1) vbn\n");
        actual.add("(2) bnm");
        assertEquals(expected, actual);
    }
}