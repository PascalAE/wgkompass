// dissolveTypes.ts

/**
 * The `dissolveTypes` module defines the TypeScript types and interfaces for the dissolution process of inventories among WG members.
 * It includes types for specifying inventory assignments and the results of a dissolution operation.
 *
 * @module dissolveTypes
 * @description This module contains type definitions for the dissolution process, including the mapping of inventory items to members and the financial obligations resulting from the dissolution.
 */

export interface DissolveInventoryDto {
    wgId: number;
    inventoryMappings: InventoryMemberMapping[];
}

export interface InventoryMemberMapping {
    inventarId: number;
    mitgliedId: number;
}

export interface DissolveResultDto {
    obligations: MemberFinancialObligation[];
    inventoryValues: InventoryValue[];
}

export interface MemberFinancialObligation {
    payerId: number;
    recipientId: number;
    amount: number;
}

export interface InventoryValue {
    inventarId: number;
    originalPrice: number;
    currentValue: number;
}
