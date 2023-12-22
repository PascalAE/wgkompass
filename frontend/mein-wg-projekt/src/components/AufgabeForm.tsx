import React, { useState } from 'react';
import { TextField, Button, MenuItem, Select, InputLabel, FormControl, Grid } from '@mui/material';
import { MitgliedDto } from '../types/mitgliedTypes';
import { AufgabeDto, CreateAufgabeDto } from '../types/aufgabeTypes';

interface Props {
    onSubmit: (aufgabe: CreateAufgabeDto | AufgabeDto) => void;
    wgId: number;
    mitglieder: MitgliedDto[];
    aufgabe?: AufgabeDto; 
    isEdit: boolean;
}

/**
 * `AufgabeForm` is a React component that provides a form for creating or editing a task.
 * 
 * @component
 * @param {Object} props - The properties passed to the component.
 * @param {(aufgabe: CreateAufgabeDto | AufgabeDto) => void} props.onSubmit - The function called when the form is submitted. It passes the data of the created or edited task.
 * @param {number} props.wgId - The ID of the WG (living group) to which the task belongs.
 * @param {MitgliedDto[]} props.mitglieder - An array of member objects present in the WG.
 * @param {AufgabeDto} [props.aufgabe] - The task object if it's an edit. Not present when creating a new task.
 * @param {boolean} props.isEdit - A flag determining whether the form is in edit mode (true) or in create mode (false).
 * 
 * @returns {React.ReactElement} The `AufgabeForm` React element.
 */
const AufgabeForm = ({ onSubmit, wgId, mitglieder, aufgabe, isEdit }: Props) => {
    // ...Component logic and JSX...
    const [titel, setTitel] = useState(aufgabe?.titel || '');
    const [beschreibung, setBeschreibung] = useState(aufgabe?.beschreibung || '');
    const [verantwortlichesMitgliedId, setVerantwortlichesMitgliedId] = useState<number | null>(aufgabe?.verantwortlichesMitgliedId || null);

    // Check if mitglieder exists
    const mitgliederVorhanden = mitglieder.length > 0;

    const handleSubmit = (event: React.FormEvent<HTMLFormElement>) => {
        event.preventDefault();
        const aufgabeData = aufgabe ? { 
            ...aufgabe, 
            titel, 
            beschreibung, 
            verantwortlichesMitgliedId 
        } : {
            titel,
            beschreibung,
            wgId,
            verantwortlichesMitgliedId
        };
        onSubmit(aufgabeData);
    };

    if (!mitgliederVorhanden) {
        return <p>Keine Mitglieder vorhanden. Bitte fügen Sie zuerst Mitglieder hinzu, bevor Sie eine Aufgabe erstellen.</p>;
    }

    return (
        <form onSubmit={handleSubmit}>
            <Grid container p={2} spacing={4}>
                <Grid item xs={12}>
                    <TextField
                        label="Titel"
                        value={titel}
                        onChange={(e) => setTitel(e.target.value)}
                        fullWidth                        
                    />
                </Grid>
                <Grid item xs={12}>
                    <TextField
                        label="Beschreibung"
                        value={beschreibung}
                        onChange={(e) => setBeschreibung(e.target.value)}
                        rows={4}
                        fullWidth
                        multiline
                    />
                </Grid>
                <Grid item xs={12}>
                    <FormControl fullWidth>
                        <InputLabel id="verantwortliches-mitglied-label">Verantwortliches Mitglied</InputLabel>
                        <Select
                            labelId="verantwortliches-mitglied-label"
                            id="verantwortliches-mitglied"
                            value={verantwortlichesMitgliedId === null ? '' : verantwortlichesMitgliedId}
                            label="Verantwortliches Mitglied"
                            onChange={(e) => setVerantwortlichesMitgliedId(e.target.value === '' ? null : Number(e.target.value))}
                        >
                            <MenuItem value=""> </MenuItem>
                            {mitglieder.map((mitglied) => (
                                <MenuItem key={mitglied.id} value={mitglied.id}>
                                    {mitglied.vorname} {mitglied.nachname}
                                </MenuItem>
                            ))}
                        </Select>
                    </FormControl>
                </Grid>
                <Grid item xs={12}>
                    <Button variant="contained" color="primary" type="submit">
                        {isEdit ? "Erstellen" : "Speichern"}
                    </Button>
                </Grid>
            </Grid>
        </form>
    );
};

export default AufgabeForm;
