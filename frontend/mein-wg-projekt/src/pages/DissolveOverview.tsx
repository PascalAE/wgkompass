import React from 'react';
import { useLocation, useNavigate } from 'react-router-dom';
import { Container, Typography, Button, Table, TableBody, TableCell, TableHead, TableRow } from '@mui/material';
import { DissolveResultDto } from '../types/dissolveTypes';
import { MitgliedDto } from '../types/mitgliedTypes';
import { InventarDto } from '../types/inventarTypes';
import { WGDto } from '../types/wgTypes';

/**
 * `DissolveOverview` is a React component that displays the dissolution overview of a WG (Wohngemeinschaft).
 * It shows the calculated value of inventory items and the financial obligations among members upon dissolution.
 *
 * @component
 * @returns {React.ReactElement} The `DissolveOverview` React element.
 *
 * @description This component uses data from the `location.state` including results of the dissolution calculations,
 * list of members, inventory items, and the WG details. It provides tables to display the calculated current value
 * of each inventory item and the financial transactions required between members.
 */
export const DissolveOverview = () => {
    // ...Component logic and JSX...

    const location = useLocation();
    const navigate = useNavigate();
    const { result, mitglieder, inventar, wg } = location.state as { result: DissolveResultDto; mitglieder: MitgliedDto[]; inventar: InventarDto[], wg: WGDto };


    const getInventarNameById = (id: number) => {
        return inventar.find(inventarItem => inventarItem.id === id)?.name || 'Unbekanntes Inventar';
    };

    const getMitgliedNameById = (id: number) => {
        const mitglied = mitglieder.find(mitgliedItem => mitgliedItem.id === id);
        return mitglied ? `${mitglied.vorname} ${mitglied.nachname}` : 'Unbekanntes Mitglied';
    };

    return (
        // ...JSX rendering tables and navigation button...
        
        <div>
            <Typography variant="h4" component="h1" gutterBottom>
                Inventar Auflösungsübersicht
            </Typography>
            <Container maxWidth="sm" sx={{ backgroundColor: 'lightgrey', padding: '20px' }}>
                <Typography variant="h6" component="h2">
                    Inventar Wertkalkulation
                </Typography>
                <Table>
                    <TableHead>
                        <TableRow>
                            <TableCell>Inventar Name</TableCell>
                            <TableCell>Originalpreis</TableCell>
                            <TableCell>Aktueller Wert</TableCell>
                        </TableRow>
                    </TableHead>
                    <TableBody>
                        {result.inventoryValues.map((inventoryValue) => (
                            <TableRow key={inventoryValue.inventarId}>
                                <TableCell>{getInventarNameById(inventoryValue.inventarId)}</TableCell>
                                <TableCell>{inventoryValue.originalPrice}</TableCell>
                                <TableCell>{inventoryValue.currentValue}</TableCell>
                            </TableRow>
                        ))}
                    </TableBody>
                </Table>
            </Container>
            <Container maxWidth="sm" sx={{ backgroundColor: 'lightgrey', padding: '20px', marginTop: '20px' }}>
                <Typography variant="h6" component="h2">
                    Finanzielle Verpflichtungen
                </Typography>
                <Table>
                    <TableHead>
                        <TableRow>
                            <TableCell>Zahler</TableCell>
                            <TableCell>Empfänger</TableCell>
                            <TableCell>Betrag</TableCell>
                        </TableRow>
                    </TableHead>
                    <TableBody>
                        {result.obligations.map((obligation, index) => (
                            <TableRow key={index}>
                                <TableCell>{getMitgliedNameById(obligation.payerId)}</TableCell>
                                <TableCell>{getMitgliedNameById(obligation.recipientId)}</TableCell>
                                <TableCell>{obligation.amount}</TableCell>
                            </TableRow>
                        ))}
                    </TableBody>
                </Table>
            </Container>
            <Button onClick={() =>  navigate(`/wg-overview/${wg.id}`)}>Zurück zur WG Übersicht</Button>
        </div>
    );
};

export default DissolveOverview;