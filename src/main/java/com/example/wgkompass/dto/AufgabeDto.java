package com.example.wgkompass.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * Data Transfer Object for Aufgabe (task). This class is used to transfer data related to tasks
 * within a shared living community (WG) between different layers of the application, especially for RESTful responses.
 * It encapsulates the data attributes of a task.
 */
@Getter
@Setter
@AllArgsConstructor
public class AufgabeDto {
    /**
     * The unique identifier for the task.
     */
    private Long id;

    /**
     * The title of the task.
     */
    private String titel;

    /**
     * The description of the task.
     */
    private String beschreibung;

    /**
     * The identifier of the WG to which this task belongs.
     */
    private Long wgId;
    /**
     * The identifier of the member responsible for this task.
     */
    private Long verantwortlichesMitgliedId;
}
