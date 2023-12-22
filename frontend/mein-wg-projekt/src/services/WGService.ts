// WGService.ts
import { WGDto, CreateWGDto } from "../types/wgTypes";

const BASE_URL = process.env.REACT_APP_API_URL + '/wg';

/**
 * The `WGService` module provides functionality for managing WGs (Wohngemeinschaften or shared living spaces).
 * It facilitates operations such as retrieving, creating, and updating WGs.
 *
 * @module WGService
 * @description This module contains functions to interact with the `/wg` endpoint of the backend API.
 * It handles the HTTP requests for operations related to WG management, including fetching all WGs, retrieving a specific WG, creating a new WG, and updating an existing WG's details.
 */

export const getAllWGs = async (): Promise<WGDto[]> => {
    try {
        const response = await fetch(`${BASE_URL}/all`);
        if (!response.ok) {
            const errorText = await response.text();
            throw new Error(errorText || 'Something went wrong');
        }
               
        return await response.json() as WGDto[];
    } catch (error) {
        console.error('Error fetching data:', error);
        throw error;
    }
};

export const getWGById = async (id: number): Promise<WGDto> => {
    try {
        const response = await fetch(`${BASE_URL}/${id}`);
        if (!response.ok) {
            const errorText = await response.text();   
            throw new Error(errorText || 'Something went wrong');
        }
        return await response.json() as WGDto;
    } catch (error) {
        console.error('Error fetching data:', error);
        throw error;
    }
};

export const createWG = async (createWGDto: CreateWGDto): Promise<WGDto> => {
    try {
        const response = await fetch(`${BASE_URL}/create`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify(createWGDto),
        });
        if (!response.ok) {
            const errorText = await response.text();   
            throw new Error(errorText || 'Something went wrong');
        }
        return await response.json() as WGDto;
    } catch (error) {
        console.error('Error creating data:', error);
        throw error;
    }
};

export const updateWG = async (id: number, WGDto: WGDto): Promise<WGDto> => {
    try {
        const response = await fetch(`${BASE_URL}/update/${id}`, {
            method: 'PUT',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify(WGDto),
        });
        if (!response.ok) {
            const errorText = await response.text();   
            throw new Error(errorText || 'Something went wrong');
        }
        return await response.json() as WGDto;
    } catch (error) {
        console.error('Error updating data:', error);
        throw error;
    }
};