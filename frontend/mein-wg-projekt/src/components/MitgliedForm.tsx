import React, { useState } from 'react';
import { TextField, Button, Grid } from '@mui/material';
import { MitgliedDto, CreateMitgliedDto } from '../types/mitgliedTypes';

interface Props {
    onSubmit: (mitglied: CreateMitgliedDto | MitgliedDto) => void;
    wgId: number;
    mitglied?: MitgliedDto; 
    isEdit: boolean;
}

/**
 * `MitgliedForm` is a React component that provides a form for creating or editing a member (Mitglied).
 *
 * @component
 * @param {Object} props - The properties passed to the component.
 * @param {(mitglied: CreateMitgliedDto | MitgliedDto) => void} props.onSubmit - The function called when the form is submitted. It passes the data of the created or edited member.
 * @param {number} props.wgId - The ID of the WG (living group) to which the member belongs.
 * @param {MitgliedDto} [props.mitglied] - The member object if it's an edit. Not present when creating a new member.
 * @param {boolean} props.isEdit - A flag determining whether the form is in edit mode (true) or in create mode (false).
 *
 * @returns {React.ReactElement} The `MitgliedForm` React element.
 */
const MitgliedForm = ({ onSubmit, wgId, mitglied, isEdit }: Props) => {
    // ...Component logic and JSX...
    const [vorname, setVorname] = useState(mitglied?.vorname || '');
    const [nachname, setNachname] = useState(mitglied?.nachname || '');

    const handleSubmit = (event: React.FormEvent<HTMLFormElement>) => { 
        event.preventDefault();
        const mitgliedData = mitglied ? { ...mitglied, vorname, nachname } : { vorname, nachname, wgId };
        onSubmit(mitgliedData);
    }

    return (
        <form onSubmit={handleSubmit}>
            <Grid container p={2} spacing={4}>
                <Grid item xs={12}>
                    <TextField
                        label="Vorname"
                        value={vorname}
                        onChange={(e) => setVorname(e.target.value)}
                        fullWidth
                    />
                </Grid>
                <Grid item xs={12}>
                    <TextField
                        label="Nachname"
                        value={nachname}
                        onChange={(e) => setNachname(e.target.value)}
                        fullWidth                       
                    />
                </Grid>
                <Grid item xs={12} >
                    <Button variant="contained" color="primary" type="submit">{isEdit ? "Erstellen" : "Speichern"}</Button>
                </Grid>
            </Grid>
        </form>
    );

}

export default MitgliedForm;