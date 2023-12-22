// types/inventarTypes.ts

/**
 * The `inventarTypes` module provides TypeScript interfaces for representing inventory items (Inventar) within the application.
 * It allows strict typing for inventory-related data, ensuring that inventory items have consistent structures throughout the application.
 *
 * @module inventarTypes
 * @description This module contains TypeScript interfaces for inventory items. Each interface outlines the structure for
 * inventory items, including their creation and their standard data format, ensuring consistency and providing type safety.
 */

export interface InventarDto {
    id: number;
    name: string;
    preis: number;
    kaufdatum: string;  // Falls Datum im ISO-Format übertragen wird
    abschreibungssatz: number;
    wgId: number;
}

export interface CreateInventarDto {
    name: string;
    preis: number;
    kaufdatum: string;  // Falls Datum im ISO-Format übertragen wird
    abschreibungssatz: number;
    wgId: number;
}
