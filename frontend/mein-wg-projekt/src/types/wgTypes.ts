// wgTypes.ts

/**
 * The `wgTypes` module provides TypeScript interfaces for representing WG (Wohngemeinschaft or residential community) entities.
 * It ensures that WG-related data are structured and interacted with consistently throughout the application.
 *
 * @module wgTypes
 * @description This module contains TypeScript interfaces for WGs. Each interface specifies the structure for
 * WG data and new WG creation, ensuring consistency and type safety across the application.
 */

export interface WGDto {
    id: number;
    name: string;
}

export interface CreateWGDto {
    name: string;
  }