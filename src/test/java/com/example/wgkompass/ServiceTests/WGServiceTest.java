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
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
@ActiveProfiles("test")
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class WGServiceTest {
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
    }

    /**
     * Test for creating a WG
     */
    @Test
    //Need for testing the update without the dto. This test tests only the service. Controller und dto test is done in the http test
    @Transactional
    public void testAddNewWG() {
        WG neueWG = new WG();
        neueWG.setName("Beispiel WG");
        WG exampleWG = wgRepository.save(neueWG);
        WG wg = wgRepository.getById(exampleWG.getId());
        assertEquals(exampleWG.getName(), wg.getName());
    }

    /**
     * Test for getting all WGs
     */
    @Test
    public void testGetAllWGs() {
        WG neueWG = new WG();
        neueWG.setName("Beispiel WG");
        WG exampleWG = wgRepository.save(neueWG);
        WG wg = new WG();
        wg.setName("Test WG");
        wgRepository.save(wg);
        List<WG> wgList = wgRepository.findAll();
        assertEquals(wgList.size(), 2);
    }

    /**
     * Test for updating a WG
     */
    @Test
    //Need for testing the update without the dto. This test tests only the service. Controller und dto test is done in the http test
    @Transactional
    public void testUpdateWG() {
        //Testing if update of wg name works
        WG neueWG = new WG();
        neueWG.setName("Beispiel WG");
        WG exampleWG = wgRepository.save(neueWG);
        WG wg = wgRepository.getById(exampleWG.getId());
        wg.setName("Test WG");
        wgRepository.save(wg);
        WG updatedWG = wgRepository.getById(exampleWG.getId());
        assertEquals(updatedWG.getName(), "Test WG");
    }
}
