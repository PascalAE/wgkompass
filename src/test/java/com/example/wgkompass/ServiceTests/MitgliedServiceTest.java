package com.example.wgkompass.ServiceTests;

import com.example.wgkompass.models.Inventar;
import com.example.wgkompass.models.Mitglied;
import com.example.wgkompass.models.WG;
import com.example.wgkompass.repositories.AufgabeRepository;
import com.example.wgkompass.repositories.InventarRepository;
import com.example.wgkompass.repositories.MitgliedRepository;
import com.example.wgkompass.repositories.WGRepository;
import com.example.wgkompass.services.InventarService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class MitgliedServiceTest {
    @Autowired
    private InventarService inventarService;

    @Autowired
    private InventarRepository inventarRepository;

    @Autowired
    private MitgliedRepository mitgliedRepository;

    @Autowired
    private WGRepository wgRepository;

    @Autowired
    private AufgabeRepository aufgabeRepository;

    private WG exampleWG;

    /**
     * Set up method to clear the database before each test.
     * and create needed wg and mitglied
     */
    @BeforeEach
    public void setUp() {
        //delete everything from test db
        aufgabeRepository.deleteAll();
        inventarRepository.deleteAll();
        mitgliedRepository.deleteAll();
        wgRepository.deleteAll();

        // create WG
        WG neueWG = new WG();
        neueWG.setName("Beispiel WG");
        exampleWG = wgRepository.save(neueWG);
    }

    /**
     * Test for creating a Mitglied
     */
    @Test
    public void testAddNewMitglied() {
        // create new Mitglied
        Mitglied mitglied = new Mitglied();
        mitglied.setVorname("Max");
        mitglied.setNachname("Mustermann");
        mitglied.setWg(exampleWG);

        // save Mitglied
        Mitglied savedMitglied = mitgliedRepository.save(mitglied);

        Mitglied dbMitglied = mitgliedRepository.findById(savedMitglied.getId()).get();
        assertNotNull(dbMitglied, "Mitglied should be saved in the database");
        //Check each property of the saved mitglied for easy debugging
        assertEquals(mitglied.getVorname(), dbMitglied.getVorname(), "Vorname should be saved correctly");
        assertEquals(mitglied.getNachname(), dbMitglied.getNachname(), "Nachname should be saved correctly");
        assertEquals(mitglied.getWg().getId(), dbMitglied.getWg().getId(), "WG should be saved correctly");
    }

    /**
     * Test for updating a Mitglied
     */
    @Test
    public void testUpdateMitglied() {
        // create new Mitglied
        Mitglied mitglied = new Mitglied();
        mitglied.setVorname("Max");
        mitglied.setNachname("Mustermann");
        mitglied.setWg(exampleWG);

        // save Mitglied
        Mitglied savedMitglied = mitgliedRepository.save(mitglied);
        //check if saved correctly
        assertNotNull(savedMitglied, "Mitglied should be saved in the database");
        assertEquals(mitglied.getVorname(), savedMitglied.getVorname(), "Vorname should be saved correctly");
        assertEquals(mitglied.getNachname(), savedMitglied.getNachname(), "Nachname should be saved correctly");
        assertEquals(mitglied.getWg(), savedMitglied.getWg(), "WG should be saved correctly");

        // update Mitglied
        savedMitglied.setVorname("Maximilian");
        savedMitglied.setNachname("Musterfrau");
        savedMitglied.setWg(exampleWG);
        mitgliedRepository.save(savedMitglied);

        Mitglied dbMitglied = mitgliedRepository.findById(savedMitglied.getId()).get();
        assertNotNull(dbMitglied, "Mitglied should be saved in the database");
        //Check each property of the saved inventar for easy debugging
        assertEquals(savedMitglied.getVorname(), dbMitglied.getVorname(), "Vorname should be saved correctly");
        assertEquals(savedMitglied.getNachname(), dbMitglied.getNachname(), "Nachname should be saved correctly");
        assertEquals(savedMitglied.getWg().getId(), dbMitglied.getWg().getId(), "WG should be saved correctly");
    }

    /**
     * Test for retrieving all Mitglieder
     */
    @Test
    public void testGetAllMitglieder() {
        // create new Mitglied
        Mitglied mitglied = new Mitglied();
        mitglied.setVorname("Max");
        mitglied.setNachname("Mustermann");
        mitglied.setWg(exampleWG);

        // save Mitglied
        Mitglied savedMitglied = mitgliedRepository.save(mitglied);


        // create new Mitglied
        Mitglied mitglied2 = new Mitglied();
        mitglied2.setVorname("Maximilian");
        mitglied2.setNachname("Musterfrau");
        mitglied2.setWg(exampleWG);

        // save Mitglied
        Mitglied savedMitglied2 = mitgliedRepository.save(mitglied2);

        // get all Mitglieder
        List<Mitglied> mitglieder = mitgliedRepository.findAll();
        assertEquals(2, mitglieder.size(), "There should be 2 Mitglieder in the database");
    }

}