package com.example.wgkompass.ServiceTests;

import com.example.wgkompass.models.Inventar;
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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class InventarServiceTest {
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
     * Test for creating an inventory item.
     */
    @Test
    public void testAddNewInventar() throws ParseException {
        Inventar inventar = new Inventar();
        inventar.setName("TestInventar");
        inventar.setPreis(100.0);
        String input = "Thu Jun 18 20:56:02 EDT 2009";
        SimpleDateFormat parser = new SimpleDateFormat("EEE MMM d HH:mm:ss zzz yyyy");
        inventar.setKaufdatum(parser.parse(input));
        inventar.setAbschreibungssatz(1.0);
        inventar.setWg(exampleWG);
        Inventar savedInventar = inventarService.save(inventar);
        Inventar dbIventar = inventarRepository.findById(savedInventar.getId()).get();
        assertNotNull(dbIventar, "The saved Inventar should not be null");
        //Check each property of the saved inventar for easy debugging
        assertNotNull(dbIventar.getId(), "The ID of the saved Inventar should not be null");
        assertEquals("TestInventar", dbIventar.getName(), "The name of the saved Inventar should be 'TestInventar'");
        assertEquals(100.0, dbIventar.getPreis(), "The price of the saved Inventar should be '100.0'");
        assertEquals(parser.parse(input), dbIventar.getKaufdatum(), "The purchase date of the saved Inventar should be 'Thu Jun 18 20:56:02 EDT 2009'");
        assertEquals(1.0, dbIventar.getAbschreibungssatz(), "The depreciation rate of the saved Inventar should be '1.0'");
    }

    /**
     * Test for updating an existing inventory item.
     */
    @Test
    public void testUpdateInventar() throws ParseException {
        Inventar inventar = new Inventar();
        inventar.setName("TestInventar");
        inventar.setPreis(100.0);
        String input = "Thu Jun 18 20:56:02 EDT 2009";
        SimpleDateFormat parser = new SimpleDateFormat("EEE MMM d HH:mm:ss zzz yyyy");
        inventar.setKaufdatum(parser.parse(input));
        inventar.setAbschreibungssatz(1.0);
        inventar.setWg(exampleWG);
        Inventar savedInventar = inventarService.save(inventar);
        Inventar dbIventar = inventarRepository.findById(savedInventar.getId()).get();
        //check if inventar is saved correctly
        assertNotNull(dbIventar, "The saved Inventar should not be null");
        assertNotNull(dbIventar.getId(), "The ID of the saved Inventar should not be null");
        assertEquals("TestInventar", dbIventar.getName(), "The name of the saved Inventar should be 'TestInventar'");
        assertEquals(100.0, dbIventar.getPreis(), "The price of the saved Inventar should be '100.0'");
        assertEquals(parser.parse(input), dbIventar.getKaufdatum(), "The purchase date of the saved Inventar should be 'Thu Jun 18 20:56:02 EDT 2009'");
        assertEquals(1.0, dbIventar.getAbschreibungssatz(), "The depreciation rate of the saved Inventar should be '1.0'");

        //update inventar
        dbIventar.setName("TestInventar2");
        dbIventar.setPreis(200.0);
        String input2 = "Thu Jun 18 20:56:02 EDT 2010";
        dbIventar.setKaufdatum(parser.parse(input2));
        dbIventar.setAbschreibungssatz(2.0);
        //save updated inventar
        inventarService.save(dbIventar);
        //get saved inventar from db by id of the first saved inventar
        Inventar dbIventar2 = inventarRepository.findById(savedInventar.getId()).get();
        assertNotNull(dbIventar2, "The saved Inventar should not be null");
        assertNotNull(dbIventar2.getId(), "The ID of the saved Inventar should not be null");
        assertEquals("TestInventar2", dbIventar2.getName(), "The name of the saved Inventar should be 'TestInventar2'");
        assertEquals(200.0, dbIventar2.getPreis(), "The price of the saved Inventar should be '200.0'");
        assertEquals(parser.parse(input2), dbIventar2.getKaufdatum(), "The purchase date of the saved Inventar should be 'Thu Jun 18 20:56:02 EDT 2010'");
        assertEquals(2.0, dbIventar2.getAbschreibungssatz(), "The depreciation rate of the saved Inventar should be '2.0'");
    }

    /**
     * Test for retrieving all inventory items.
     */
    @Test
    public void testGetAllInventar() throws ParseException {
        inventarRepository.deleteAll();
        Inventar inventar1 = new Inventar();
        inventar1.setName("Inventar 1");
        inventar1.setPreis(100.0);
        String input = "Thu Jun 18 20:56:02 EDT 2009";
        SimpleDateFormat parser = new SimpleDateFormat("EEE MMM d HH:mm:ss zzz yyyy");
        inventar1.setKaufdatum(parser.parse(input));
        inventar1.setAbschreibungssatz(1.0);
        inventar1.setWg(exampleWG);
        Inventar inventar2 = new Inventar();
        inventar2.setName("Inventar 2");
        inventar2.setPreis(200.0);
        String input2 = "Thu Jun 18 20:56:02 EDT 2010";
        inventar2.setKaufdatum(parser.parse(input2));
        inventar2.setAbschreibungssatz(2.0);
        inventar2.setWg(exampleWG);
        inventarService.save(inventar1);
        inventarService.save(inventar2);
        List<Inventar> returnedInventarList = (List<Inventar>) inventarService.getAll();
        assertEquals(2, returnedInventarList.size(), "The number of retrieved inventory items should be 2");
    }
}