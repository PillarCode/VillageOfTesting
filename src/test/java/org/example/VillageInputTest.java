package org.example;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;

class VillageInputTest {

    private DatabaseConnection databaseConnection;
    private VillageInput villageInput;

    @BeforeEach
    void setUp() {
        databaseConnection = mock(DatabaseConnection.class);
        Village village = mock(Village.class);

        villageInput = new VillageInput(village, databaseConnection);
    }

    @Test
    void testSave() {
        // Set up mock behavior
        ArrayList<String> villages = new ArrayList<>();
        villages.add("existing_village");
        when(databaseConnection.GetTownNames()).thenReturn(villages);
        when(databaseConnection.SaveVillage(any(Village.class), anyString())).thenReturn(true);

        // Set up input
        String input = """
                new_village
                y
                """;
        InputStream in = new ByteArrayInputStream(input.getBytes());

        // Call method
        villageInput.Save(in);

        // Verify interactions
        verify(databaseConnection, times(1)).GetTownNames();
        verify(databaseConnection, times(1)).SaveVillage(any(Village.class), eq("new_village"));
    }

    @Test
    void testLoad() {
        // Set up mock behavior
        ArrayList<String> villages = new ArrayList<>();
        villages.add("existing_village");
        when(databaseConnection.GetTownNames()).thenReturn(villages);
        when(databaseConnection.LoadVillage(anyString())).thenReturn(new Village());

        // Set up input
        String input = "existing_village" + "\n";
        InputStream in = new ByteArrayInputStream(input.getBytes());

        // Call method
        villageInput.Load(in);

        // Verify interactions
        verify(databaseConnection, times(1)).GetTownNames();
        verify(databaseConnection, times(1)).LoadVillage(eq("existing_village"));
    }
}