// services/InventarService.ts
import { CreateInventarDto, InventarDto } from "../types/inventarTypes";

const BASE_URL = process.env.REACT_APP_API_URL + '/inventar';

/**
 * The `InventarService` module provides a set of functions to manage inventory items within a WG.
 * It facilitates operations such as retrieving, creating, and updating inventory items.
 *
 * @module InventarService
 * @description This module contains functions to interact with the `/inventar` endpoint of the backend API.
 * Each function is designed to perform specific operations on inventory items, like fetching all items, adding a new item, or updating an existing one.
 */

/**
 * Retrieves all inventory items.
 * @returns A promise resolving to an array of InventarDto.
 * @throws Will throw an error if fetching fails.
 */
export const getAllInventar = async (): Promise<InventarDto[]> => {
    try {
        const response = await fetch(`${BASE_URL}/all`);
        if (!response.ok) {
            const errorText = await response.text();   
            throw new Error(errorText || 'Something went wrong');
        }
        return await response.json() as InventarDto[];
    } catch (error) {
        console.error('Error fetching data:', error);
        throw error;
    }
};

/**
 * Retrieves an inventory item by its ID.
 * @param id - The ID of the inventory item.
 * @returns A promise resolving to an InventarDto.
 * @throws Will throw an error if fetching fails.
 */
export const getInventarById = async (id: number): Promise<InventarDto> => {
    try {
        const response = await fetch(`${BASE_URL}/${id}`);
        if (!response.ok) {
            const errorText = await response.text();   
            throw new Error(errorText || 'Something went wrong');
        }
        return await response.json() as InventarDto;
    } catch (error) {
        console.error('Error fetching data:', error);
        throw error;
    }
};

/**
 * Retrieves all inventory items for a specific WG.
 * @param wgId - The ID of the WG.
 * @returns A promise resolving to an array of InventarDto.
 * @throws Will throw an error if fetching fails.
 */
export const getInventarByWGId = async (wgId: number): Promise<InventarDto[]> => {
    try {
        const response = await fetch(`${BASE_URL}/wg/${wgId}`);
        if (!response.ok) {
            const errorText = await response.text();   
            throw new Error(errorText || 'Something went wrong');
        }
        return await response.json() as InventarDto[];
    } catch (error) {
        console.error('Error fetching data:', error);
        throw error;
    }
};

/**
 * Creates a new inventory item.
 * @param createInventarDto - The data transfer object containing new inventory item details.
 * @returns A promise resolving to the created InventarDto.
 * @throws Will throw an error if creation fails.
 */
export const createInventar = async (createInventarDto: CreateInventarDto): Promise<InventarDto> => {
    try {
        const response = await fetch(`${BASE_URL}/create`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify(createInventarDto),
        });
        if (!response.ok) {
            const errorText = await response.text();   
            throw new Error(errorText || 'Something went wrong');
        }
        return await response.json() as InventarDto;
    } catch (error) {
        console.error('Error creating data:', error);
        throw error;
    }
};

/**
 * Updates an existing inventory item.
 * @param id - The ID of the inventory item to update.
 * @param inventarDto - The updated inventory item data.
 * @returns A promise resolving to the updated InventarDto.
 * @throws Will throw an error if updating fails.
 */
export const updateInventar = async (id: number, inventarDto: InventarDto): Promise<InventarDto> => {
    try {
        const response = await fetch(`${BASE_URL}/update/${id}`, {
            method: 'PUT',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify(inventarDto),
        });
        if (!response.ok) {
            const errorText = await response.text();   
            throw new Error(errorText || 'Something went wrong');
        }
        return await response.json() as InventarDto;
    } catch (error) {
        console.error('Error updating data:', error);
        throw error;
    }
};

