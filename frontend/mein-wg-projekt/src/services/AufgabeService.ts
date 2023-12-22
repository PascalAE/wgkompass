// services/AufgabeService.ts
import { AufgabeDto, CreateAufgabeDto } from "../types/aufgabeTypes";

const BASE_URL = process.env.REACT_APP_API_URL + '/aufgabe';

/**
 * The `AufgabeService` module provides a collection of functions to interact with the backend API regarding Aufgabe (tasks) related operations.
 * It allows fetching, creating, and updating tasks associated with a specific WG.
 *
 * @module AufgabeService
 * @description This module contains functions to interact with the `/aufgabe` endpoint of the backend API. 
 * Each function handles the HTTP request to perform operations such as retrieving all tasks, creating a new task, or updating an existing one.
 */

/**
 * Fetches all tasks from the backend.
 * @async
 * @function getAllAufgaben
 * @returns {Promise<AufgabeDto[]>} A promise resolving to an array of AufgabeDto objects.
 * @throws Error if the request fails.
 */
export const getAllAufgaben = async (): Promise<AufgabeDto[]> => {
    try {
        const response = await fetch(`${BASE_URL}/all`);
        if (!response.ok) {
            const errorText = await response.text();   
            throw new Error(errorText || 'Something went wrong');
        }
        return await response.json() as AufgabeDto[];
    } catch (error) {
        console.error('Error fetching data:', error);
        throw error;
    }
};

/**
 * Fetches a specific task by its ID.
 * @async
 * @function getAufgabeById
 * @param {number} id - The ID of the task to fetch.
 * @returns {Promise<AufgabeDto>} A promise resolving to an AufgabeDto object.
 * @throws Error if the request fails.
 */
export const getAufgabeById = async (id: number): Promise<AufgabeDto> => {
    try {
        const response = await fetch(`${BASE_URL}/${id}`);
        if (!response.ok) {
            const errorText = await response.text();   
            throw new Error(errorText || 'Something went wrong');
        }
        return await response.json() as AufgabeDto;
    } catch (error) {
        console.error('Error fetching data:', error);
        throw error;
    }
};

/**
 * Fetches tasks associated with a specific WG ID.
 * @async
 * @function getAufgabenByWGId
 * @param {number} wgId - The ID of the WG to fetch tasks for.
 * @returns {Promise<AufgabeDto[]>} A promise resolving to an array of AufgabeDto objects.
 * @throws Error if the request fails.
 */
export const getAufgabenByWGId = async (wgId: number): Promise<AufgabeDto[]> => {
    try {
        const response = await fetch(`${BASE_URL}/wg/${wgId}`);
        if (!response.ok) {
            const errorText = await response.text();   
            throw new Error(errorText || 'Something went wrong');
        }
        return await response.json() as AufgabeDto[];
    } catch (error) {
        console.error('Error fetching data:', error);
        throw error;
    }
};


/**
 * Creates a new task.
 * @async
 * @function createAufgabe
 * @param {CreateAufgabeDto} createAufgabeDto - The data for the new task.
 * @returns {Promise<AufgabeDto>} A promise resolving to the newly created AufgabeDto object.
 * @throws Error if the request fails.
 */
export const createAufgabe = async (createAufgabeDto: CreateAufgabeDto): Promise<AufgabeDto> => {
    try {
        const response = await fetch(`${BASE_URL}/create`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify(createAufgabeDto),
        });
        if (!response.ok) {
            const errorText = await response.text();   
            throw new Error(errorText || 'Something went wrong');
        }
        return await response.json() as AufgabeDto;
    } catch (error) {
        console.error('Error creating data:', error);
        throw error;
    }
};

/**
 * Updates an existing task.
 * @async
 * @function updateAufgabe
 * @param {number} id - The ID of the task to update.
 * @param {AufgabeDto} aufgabeDto - The updated task data.
 * @returns {Promise<AufgabeDto>} A promise resolving to the updated AufgabeDto object.
 * @throws Error if the request fails.
 */
export const updateAufgabe = async (id: number, aufgabeDto: AufgabeDto): Promise<AufgabeDto> => {
    try {
        const response = await fetch(`${BASE_URL}/update/${id}`, {
            method: 'PUT',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify(aufgabeDto),
        });
        if (!response.ok) {
            const errorText = await response.text();   
            throw new Error(errorText || 'Something went wrong');
        }
        return await response.json() as AufgabeDto;
    } catch (error) {
        console.error('Error updating data:', error);
        throw error;
    }
};
