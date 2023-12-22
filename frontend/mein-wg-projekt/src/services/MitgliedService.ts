// services/MitgliedService.ts
import { CreateMitgliedDto, MitgliedDto } from "../types/mitgliedTypes";

const BASE_URL = process.env.REACT_APP_API_URL + '/mitglied';

/**
 * The `MitgliedService` module provides functionality for managing members within a WG.
 * It allows for operations such as retrieving member details, adding new members, and updating existing member information.
 *
 * @module MitgliedService
 * @description This module contains functions to interact with the `/mitglied` endpoint of the backend API.
 * It handles the HTTP requests for operations related to WG members, including fetching all members, retrieving a single member, adding a new member, and updating member details.
 */

/**
 * Retrieves all WG members.
 * @returns A promise resolving to an array of MitgliedDto.
 * @throws Will throw an error if fetching fails.
 */
export const getAllMitglieder = async (): Promise<MitgliedDto[]> => {
    try {
        const response = await fetch(`${BASE_URL}/all`);
        if (!response.ok) {
            const errorText = await response.text();   
            throw new Error(errorText || 'Something went wrong');
        }
        return await response.json() as MitgliedDto[];
    } catch (error) {
        console.error('Error fetching data:', error);
        throw error;
    }
};

/**
 * Retrieves a WG member by their ID.
 * @param id - The ID of the member.
 * @returns A promise resolving to a MitgliedDto.
 * @throws Will throw an error if fetching fails.
 */
export const getMitgliedById = async (id: number): Promise<MitgliedDto> => {
    try {
        const response = await fetch(`${BASE_URL}/${id}`);
        if (!response.ok) {
            const errorText = await response.text();   
            throw new Error(errorText || 'Something went wrong');
        }
        return await response.json() as MitgliedDto;
    } catch (error) {
        console.error('Error fetching data:', error);
        throw error;
    }
};

/**
 * Retrieves all members of a specific WG.
 * @param wgId - The ID of the WG.
 * @returns A promise resolving to an array of MitgliedDto.
 * @throws Will throw an error if fetching fails.
 */
export const getMitgliederByWGId = async (wgId: number): Promise<MitgliedDto[]> => {
    try {
        const response = await fetch(`${BASE_URL}/wg/${wgId}`);
        if (!response.ok) {
            const errorText = await response.text();   
            throw new Error(errorText || 'Something went wrong');
        }
        return await response.json() as MitgliedDto[];
    } catch (error) {
        console.error('Error fetching data:', error);
        throw error;
    }
};

/**
 * Creates a new WG member.
 * @param createMitgliedDto - The data transfer object containing new member details.
 * @returns A promise resolving to the created MitgliedDto.
 * @throws Will throw an error if creation fails.
 */
export const createMitglied = async (createMitgliedDto: CreateMitgliedDto): Promise<MitgliedDto> => {
    try {
        const response = await fetch(`${BASE_URL}/create`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify(createMitgliedDto),
        });
        if (!response.ok) {
            const errorText = await response.text();   
            throw new Error(errorText || 'Something went wrong');
        }
        return await response.json() as MitgliedDto;
    } catch (error) {
        console.error('Error creating data:', error);
        throw error;
    }
};

/**
 * Updates an existing WG member.
 * @param id - The ID of the member to update.
 * @param mitgliedDto - The updated member data.
 * @returns A promise resolving to the updated MitgliedDto.
 * @throws Will throw an error if updating fails.
 */
export const updateMitglied = async (id: number, mitgliedDto: MitgliedDto): Promise<MitgliedDto> => {
    try {
        const response = await fetch(`${BASE_URL}/update/${id}`, {
            method: 'PUT',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify(mitgliedDto),
        });
        if (!response.ok) {
            const errorText = await response.text();   
            throw new Error(errorText || 'Something went wrong');
        }
        return await response.json() as MitgliedDto;
    } catch (error) {
        console.error('Error updating data:', error);
        throw error;
    }
};

