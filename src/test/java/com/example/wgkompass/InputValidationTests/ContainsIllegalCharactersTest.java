package com.example.wgkompass.InputValidationTests;

import com.example.wgkompass.exception.InvalidRequestException;
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
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * This class contains tests for validating the input for creating entities such as WG (Wohngemeinschaft), Mitglied (member),
 * Inventar (inventory), and Aufgabe (task). It checks if the input contains illegal characters.
 */
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class ContainsIllegalCharactersTest {

    @Autowired
    private MockMvc mockMvc;

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

    private Long mitgliedid;
    private Long wgid;

    /**
     * Sets up the test environment before each test. It clears the database and creates the necessary WG (Wohngemeinschaft) and Mitglied (member) entities.
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
        //get wg id
        wgid = exampleWG.getId();

        // create Mitglied
        Mitglied exampleMitglied = new Mitglied();
        exampleMitglied.setVorname("Max");
        exampleMitglied.setNachname("Mustermann");
        exampleMitglied.setWg(exampleWG);
        mitgliedRepository.save(exampleMitglied);
        mitgliedid = exampleMitglied.getId();

    }

    /**
     * Tests the creation of a WG with illegal characters in its name. It expects a BadRequest response and a specific exception.
     */
    @Test
    public void testCreateWGWithIllegalCharacters() throws Exception {
        String wgJson = "{\"name\": \"Ungültige Zeichen @\"}";

        mockMvc.perform(MockMvcRequestBuilders.post("/wg/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(wgJson))
                .andExpect(status().isBadRequest()) // 400 (Bad Request)
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof InvalidRequestException))
                .andExpect(result -> assertEquals("Illegal characters in name", result.getResolvedException().getMessage()));
    }

    /**
     * Tests the creation of a WG without illegal characters in its name. It expects an OK response.
     */
    @Test
    public void testCreateWGWithoutIllegalCharacters() throws Exception {
        String wgJson = "{\"name\": \"Gültiger Name\"}";

        mockMvc.perform(MockMvcRequestBuilders.post("/wg/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(wgJson))
                .andExpect(status().isOk());
    }

    /**
     * Tests the creation of a Mitglied with illegal characters in the name. It expects a BadRequest response and a specific exception.
     */
    @Test
    public void testCreateMitgliedWithIllegalCharacters() throws Exception {
        String mitgliedJson = "{\"vorname\": \"Max @\", \"nachname\": \"Mustermann\", \"wgId\": " + wgid + "}";

        mockMvc.perform(MockMvcRequestBuilders.post("/mitglied/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mitgliedJson))
                .andExpect(status().isBadRequest()) //  400 (Bad Request)
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof InvalidRequestException))
                .andExpect(result -> assertEquals("Illegal characters in name", result.getResolvedException().getMessage()));
    }

    /**
     * Tests the creation of a Mitglied without illegal characters in the name. It expects an OK response.
     */
    @Test
    public void testCreateMitgliedWithoutIllegalCharacters() throws Exception {
        String mitgliedJson = "{\"vorname\": \"Max\", \"nachname\": \"Mustermann\", \"wgId\": " + wgid + "}";

        mockMvc.perform(MockMvcRequestBuilders.post("/mitglied/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mitgliedJson))
                .andExpect(status().isOk()); //  200 OK
    }

    /**
     * Tests the creation of an Inventar item with illegal characters in the name. It expects a BadRequest response and a specific exception.
     */
    @Test
    public void testCreateInventarWithIllegalCharacters() throws Exception {
        String inventarJson = "{\"name\": \"Ungültiges Inventar @\", \"preis\": 100.0, \"kaufdatum\": \"2023-12-18\", \"abschreibungssatz\": 10.0, \"wgId\": " + wgid + "}";

        mockMvc.perform(MockMvcRequestBuilders.post("/inventar/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(inventarJson))
                .andExpect(status().isBadRequest()) // 400 (Bad Request)
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof InvalidRequestException))
                .andExpect(result -> assertEquals("Illegal characters in name", result.getResolvedException().getMessage()));
    }

    /**
     * Tests the creation of an Inventar item without illegal characters in the name. It expects an OK response.
     */
    @Test
    public void testCreateInventarWithoutIllegalCharacters() throws Exception {
        String inventarJson = "{\"name\": \"Gültiges Inventar\", \"preis\": 100.0, \"kaufdatum\": \"2023-12-18\", \"abschreibungssatz\": 10.0, \"wgId\": " + wgid + "}";

        mockMvc.perform(MockMvcRequestBuilders.post("/inventar/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(inventarJson))
                .andExpect(status().isOk()); //  200 OK
    }

    /**
     * Tests the creation of an Aufgabe with illegal characters in the title. It expects a BadRequest response and a specific exception.
     */
    @Test
    public void testCreateAufgabeWithIllegalCharacters() throws Exception {
        String aufgabeJson = "{\"titel\": \"Ungültige Aufgabe @\", \"beschreibung\": \"Ungültige Beschreibung\", \"wgId\": " + wgid + ", \"verantwortlichesMitgliedId\": " + mitgliedid + "}";

        mockMvc.perform(MockMvcRequestBuilders.post("/aufgabe/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(aufgabeJson))
                .andExpect(status().isBadRequest()) //  400 (Bad Request)
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof InvalidRequestException))
                .andExpect(result -> assertEquals("Illegal characters in name", result.getResolvedException().getMessage()));
    }

    /**
     * Tests the creation of an Aufgabe without illegal characters in the title. It expects an OK response.
     */
    @Test
    public void testCreateAufgabeWithoutIllegalCharacters() throws Exception {
        String aufgabeJson = "{\"titel\": \"Gültige Aufgabe\", \"beschreibung\": \"Gültige Beschreibung\", \"wgId\": " + wgid + ", \"verantwortlichesMitgliedId\": " + mitgliedid + "}";

        mockMvc.perform(MockMvcRequestBuilders.post("/aufgabe/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(aufgabeJson))
                .andExpect(status().isOk()); //  200 OK
    }

}
