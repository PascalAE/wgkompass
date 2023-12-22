// types/mitgliedTypes.ts

/**
 * The `mitgliedTypes` module provides TypeScript interfaces for representing members (Mitglieder) within the WG (residential community).
 * It ensures that member-related data are structured and interacted with consistently throughout the application.
 *
 * @module mitgliedTypes
 * @description This module contains TypeScript interfaces for members. Each interface specifies the structure for
 * member data and new member creation, ensuring consistency and type safety across the application.
 */

export interface MitgliedDto {
    id: number;
    vorname: string;
    nachname: string;
    wgId: number;
}

export interface CreateMitgliedDto {
    vorname: string;
    nachname: string;
    wgId: number;
}
