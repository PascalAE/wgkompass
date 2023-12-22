import React, { useState } from 'react';
import { TextField, Button, Grid } from '@mui/material';
import { AdapterDateFns } from '@mui/x-date-pickers/AdapterDateFns';
import { LocalizationProvider } from '@mui/x-date-pickers/LocalizationProvider';
import { DatePicker } from '@mui/x-date-pickers/DatePicker';
import { de } from 'date-fns/locale';
import { InventarDto, CreateInventarDto } from '../types/inventarTypes';
import { MitgliedDto } from '../types/mitgliedTypes'; // Import für MitgliedDto

interface Props {
    onSubmit: (inventar: CreateInventarDto | InventarDto) => void;
    wgId: number;
    inventar?: InventarDto;
    isEdit: boolean;
    mitglieder: MitgliedDto[]; // Prop für Mitglieder hinzugefügt
}

/**
 * `InventarForm` is a React component that provides a form for creating or editing inventory items.
 *
 * @component
 * @param {Object} props - The properties passed to the component.
 * @param {(inventar: CreateInventarDto | InventarDto) => void} props.onSubmit - The function called when the form is submitted. It passes the data of the created or edited inventory item.
 * @param {number} props.wgId - The ID of the WG (living group) to which the inventory item belongs.
 * @param {InventarDto} [props.inventar] - The inventory object if it's an edit. Not present when creating a new inventory item.
 * @param {boolean} props.isEdit - A flag determining whether the form is in edit mode (true) or in create mode (false).
 * @param {MitgliedDto[]} props.mitglieder - An array of member objects present in the WG.
 *
 * @returns {React.ReactElement} The `InventarForm` React element.
 */
const InventarForm = ({ onSubmit, wgId, inventar, isEdit, mitglieder }: Props) => {
    // ...Component logic and JSX...
    const [name, setName] = useState(inventar?.name || '');
    const [preis, setPreis] = useState(inventar?.preis || 0);
    const [kaufdatum, setKaufdatum] = useState<Date | null>(inventar?.kaufdatum ? new Date(inventar.kaufdatum) : new Date());
    const [abschreibungssatz, setAbschreibungssatz] = useState(inventar?.abschreibungssatz || 0);

    const mitgliederVorhanden = mitglieder.length > 0; // Prüfung, ob Mitglieder vorhanden sind

    const handleSubmit = (event: React.FormEvent<HTMLFormElement>) => {
        event.preventDefault();
        const datumsString = kaufdatum ? kaufdatum.toISOString().split('T')[0] : new Date().toISOString().split('T')[0];
        const inventarData = inventar ? { ...inventar, name, preis, kaufdatum: datumsString, abschreibungssatz } : { name, preis, kaufdatum: datumsString, abschreibungssatz, wgId };
        onSubmit(inventarData);
    };

    if (!mitgliederVorhanden) {
        return <p>Es müssen Mitglieder vorhanden sein, um Inventar anzulegen.</p>;
    }

    return (
        <form onSubmit={handleSubmit}>
            <Grid container p={2} spacing={4}>
                <Grid item xs={12}>
                    <TextField
                        label="Name"
                        value={name}
                        onChange={(e) => setName(e.target.value)}
                        fullWidth
                    />
                </Grid>
                <Grid item xs={12}>
                    <TextField
                        label="Preis"
                        value={preis}
                        onChange={(e) => setPreis(Number(e.target.value))}
                        fullWidth
                    />
                </Grid>
                <Grid item xs={12}>
                    <LocalizationProvider dateAdapter={AdapterDateFns} adapterLocale={de}>
                        <DatePicker
                            label="Kaufdatum"
                            value={kaufdatum}
                            onChange={setKaufdatum}
                        />
                    </LocalizationProvider>
                </Grid>
                <Grid item xs={12}>
                    <TextField
                        label="Abschreibungssatz"
                        value={abschreibungssatz}
                        onChange={(e) => setAbschreibungssatz(Number(e.target.value))}
                        fullWidth
                    />
                </Grid>
                <Grid item xs={12} >
                    <Button variant="contained" color="primary" type="submit">
                        {isEdit ? "Erstellen" : "Speichern"}
                    </Button>
                </Grid>
            </Grid>
        </form>
    );
};

export default InventarForm;