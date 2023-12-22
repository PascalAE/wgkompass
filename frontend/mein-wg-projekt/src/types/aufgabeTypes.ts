// aufgabeTypes.ts

/**
 * The `aufgabeTypes` module defines the TypeScript types and interfaces for Aufgabe (task) objects.
 * It specifies the structure for both retrieved tasks and new tasks to be created.
 *
 * @module aufgabeTypes
 * @description This module contains the type definitions for Aufgabe objects. These types are used throughout the application to ensure consistency and provide a clear structure for task-related data.
 */

export interface AufgabeDto {
    id: number;
    titel: string;
    beschreibung: string;
    wgId: number;
    verantwortlichesMitgliedId: number | null;
}

export interface CreateAufgabeDto {
    titel: string;
    beschreibung: string;
    wgId: number;
    verantwortlichesMitgliedId: number | null;
}
