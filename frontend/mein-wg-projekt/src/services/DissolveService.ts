// DissolveService.ts
import { DissolveInventoryDto, DissolveResultDto } from '../types/dissolveTypes';

const BASE_URL = process.env.REACT_APP_API_URL + '/dissolve';

/**
 * The `DissolveService` module provides functionality to handle the dissolution of inventory items among WG members.
 * It offers a method to initiate the dissolution process and distribute inventory items based on specified criteria.
 *
 * @module DissolveService
 * @description This module contains a function to interact with the `/dissolve` endpoint of the backend API.
 * It handles the HTTP request to dissolve the inventory and assign items to members as per the dissolution agreement.
 */

/**
 * Dissolves the inventory for a given WG, distributing items among members.
 * @param dissolveInventoryDto - The data transfer object containing WG ID and inventory mappings.
 * @returns A promise resolving to the result of the dissolution process.
 * @throws Will throw an error if the dissolution process fails.
 */
export const dissolveInventory = async (dissolveInventoryDto: DissolveInventoryDto): Promise<DissolveResultDto> => {
    try {
        const response = await fetch(`${BASE_URL}/inventory`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify(dissolveInventoryDto),
        });

        if (!response.ok) {
            const errorText = await response.text();
            throw new Error(errorText || 'Something went wrong during the dissolution process');
        }

        return await response.json() as DissolveResultDto;
    } catch (error) {
        console.error('Error in dissolveInventory:', error);
        throw error;
    }
};
