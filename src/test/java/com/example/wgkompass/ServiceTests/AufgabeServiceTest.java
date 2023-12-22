package com.example.wgkompass.ServiceTests;

import com.example.wgkompass.models.Aufgabe;
import com.example.wgkompass.models.Mitglied;
import com.example.wgkompass.models.WG;
import com.example.wgkompass.repositories.AufgabeRepository;
import com.example.wgkompass.repositories.InventarRepository;
import com.example.wgkompass.repositories.MitgliedRepository;
import com.example.wgkompass.repositories.WGRepository;
import com.example.wgkompass.services.AufgabeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for AufgabeService.
 * This class includes tests for the CRU operations on Aufgaben within a shared living community (WG).
 */
@SpringBootTest
@ActiveProfiles("test")
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class AufgabeServiceTest {
    @Autowired
    private AufgabeService aufgabeService;

    @Autowired
    private AufgabeRepository aufgabeRepository;

    @Autowired
    private MitgliedRepository mitgliedRepository;

    @Autowired
    private WGRepository wgRepository;

    @Autowired
    private InventarRepository inventarRepository;

    private WG exampleWG;
    private Mitglied exampleMitglied;

    /**
     * Set up method to clear the database before each test.
     * and create needed wg and mitglied
     */
    @BeforeEach
    public void setUp() {
        //delete everything from test db
        aufgabeRepository.deleteAll();
        mitgliedRepository.deleteAll();
        inventarRepository.deleteAll();
        wgRepository.deleteAll();

        // create WG
        WG neueWG = new WG();
        neueWG.setName("Beispiel WG");
        exampleWG = wgRepository.save(neueWG);

        // create Mitglied
        exampleMitglied = new Mitglied();
        exampleMitglied.setVorname("Max");
        exampleMitglied.setNachname("Mustermann");
        exampleMitglied.setWg(exampleWG);
        mitgliedRepository.save(exampleMitglied);
    }

    /**
     * Test for adding a new Aufgabe.
     */
    @Test
    public void testAddNewAufgabe() {
        Aufgabe newAufgabe = new Aufgabe();
        newAufgabe.setTitel("Test Aufgabe");
        newAufgabe.setBeschreibung("This is a test Aufgabe");
        newAufgabe.setWg(exampleWG);
        newAufgabe.setVerantwortlichesMitglied(exampleMitglied);
        Aufgabe savedAufgabe = aufgabeService.save(newAufgabe);
        Aufgabe dbAufgabe = aufgabeRepository.findById(savedAufgabe.getId()).get();
        assertNotNull(dbAufgabe, "The saved Aufgabe should not be null");
        assertNotNull(dbAufgabe.getId(), "The ID of the saved Aufgabe should not be null");
        assertEquals("Test Aufgabe", dbAufgabe.getTitel(), "The title of the saved Aufgabe should be 'Test Aufgabe'");
        assertEquals("This is a test Aufgabe", dbAufgabe.getBeschreibung(), "The description of the saved Aufgabe should be 'This is a test task'");
    }

    /**
     * Test for updating an existing Aufgabe.
     */
    @Test
    public void testUpdateAufgabe() {
        Aufgabe existingAufgabe = new Aufgabe();
        existingAufgabe.setTitel("Old Aufgabe Title");
        existingAufgabe.setBeschreibung("Old Description");
        existingAufgabe.setWg(exampleWG);
        existingAufgabe.setVerantwortlichesMitglied(exampleMitglied);
        Aufgabe savedAufgabe = aufgabeService.save(existingAufgabe);
        assertNotNull(savedAufgabe, "The created Aufgabe should not be null");

        Aufgabe updatedAufgabe = existingAufgabe;
        updatedAufgabe.setTitel("New Aufgabe Title");
        updatedAufgabe.setBeschreibung("New Description");

        Aufgabe updatedSavedAufgabe = aufgabeService.save(updatedAufgabe);
        Aufgabe dbAufgabe = aufgabeRepository.findById(savedAufgabe.getId()).get();
        assertNotNull(dbAufgabe, "The updated Aufgabe should not be null");
        assertEquals(savedAufgabe.getId(), dbAufgabe.getId(), "The ID of the updated Aufgabe should remain the same");
        assertEquals("New Aufgabe Title", dbAufgabe.getTitel(), "The title of the updated Aufgabe should be changed");
        assertEquals("New Description", dbAufgabe.getBeschreibung(), "The description of the updated Aufgabe should be changed");
    }

    /**
     * Test for retrieving all Aufgaben.
     */
    @Test
    public void testGetAllAufgaben() {
        aufgabeRepository.deleteAll();
        Aufgabe aufgabe1 = new Aufgabe();
        aufgabe1.setTitel("Aufgabe 1");
        aufgabe1.setBeschreibung("Description 1");
        aufgabe1.setWg(exampleWG);
        aufgabe1.setVerantwortlichesMitglied(exampleMitglied);
        Aufgabe aufgabe2 = new Aufgabe();
        aufgabe2.setTitel("Aufgabe 2");
        aufgabe2.setBeschreibung("Description 2");
        aufgabe2.setWg(exampleWG);
        aufgabe2.setVerantwortlichesMitglied(exampleMitglied);
        aufgabeRepository.save(aufgabe1);
        aufgabeRepository.save(aufgabe2);
        List<Aufgabe> returnedAufgabeList = (List<Aufgabe>) aufgabeService.getAll();
        assertNotNull(returnedAufgabeList, "The returned Aufgaben list should not be null");
        assertEquals(2, returnedAufgabeList.size(), "The size of the returned Aufgaben list should be 2");
    }

}