import React, { useState } from 'react';
import { TextField, Button, Grid } from '@mui/material';
import { WGDto } from '../types/wgTypes';

interface Props {
    onSubmit: (wg: WGDto) => void;
    wgId: number;
    wg?: WGDto;
}

/**
 * `WGForm` is a React component that provides a form for creating or editing a WG (Wohngemeinschaft).
 *
 * @component
 * @param {Object} props - The properties passed to the component.
 * @param {(wg: WGDto) => void} props.onSubmit - The function called when the form is submitted. It passes the data of the created or edited WG.
 * @param {WGDto} [props.wg] - The WG object if it's being edited. Not present when creating a new WG.
 *
 * @returns {React.ReactElement} The `WGForm` React element.
 */
const WGForm = ({ onSubmit, wg }: Props) => {
    // ...Component logic and JSX...
    const [name, setName] = useState(wg?.name || '');

    const handleSubmit = (event: React.FormEvent<HTMLFormElement>) => { 
        event.preventDefault();
        const wgData = {...wg, name};
        onSubmit({ ...wgData, id: wgData.id || 0 });
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
                <Grid item xs={12} >
                    <Button variant="contained" color="primary" type="submit">Speichern</Button>
                </Grid>
            </Grid>
        </form>
    );

}

export default WGForm;