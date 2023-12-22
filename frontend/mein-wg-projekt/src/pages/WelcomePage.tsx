import React, { useState, useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import { useTheme } from '@mui/material/styles';
import { getAllWGs, createWG } from '../services/WGService';
import { WGDto } from '../types/wgTypes';
import { Container, Divider, Card, CardHeader, CardContent, Typography, TextField, Button, List, ListItem, ListItemText, ListItemSecondaryAction, Alert } from '@mui/material';

/**
 * `WelcomePage` is a React component that serves as the landing page for the WG Kompass application.
 * It allows users to view existing WGs and create new ones.
 *
 * @component
 * @returns {React.ReactElement} The `WelcomePage` React element.
 *
 * @description This component fetches and displays all WGs the user is associated with. It provides an interface 
 * to create a new WG and navigate to the overview of an existing WG. The page is styled with Material-UI components 
 * and provides feedback through alerts for errors or actions.
 */
export const WelcomePage = () => {
    // ...Component logic and JSX...
    const [wgs, setWGs] = useState<WGDto[]>([]);
    const [newWGName, setNewWGName] = useState('');
    const [errorMessage, setErrorMessage] = useState<string | null>(null); // Zustandsvariable für Fehlermeldungen
    const navigate = useNavigate();
    const theme = useTheme();

    useEffect(() => {
        const fetchWGs = async () => {
            try {
                const fetchedWGs = await getAllWGs();
                setWGs(fetchedWGs);
            } catch (error: any) {
                console.error(error);
                setErrorMessage("Fehler beim Laden der WGs: " + error.message);
            }
        };

        fetchWGs();
    }, []);

    const handleCreateWG = async () => {
        if (!newWGName.trim()) return; // handle empty string

        try {
            const newWG = await createWG({ name: newWGName });
            setWGs([...wgs, newWG]);
            setNewWGName(''); // Reset input field after creation
            setErrorMessage(null); // Reset error message on successful creation
        } catch (error: any) {
            console.error(error);
            setErrorMessage("Fehler beim Erstellen einer neuen WG: " + error.message);
        }
    };

    return (
        <div>
            <Container maxWidth="sm" sx={{ padding: '20px' }}>
                <Typography variant="h4" component="h1" gutterBottom>
                    Willkommen bei WG Kompass
                </Typography>
                {errorMessage && <Alert severity="error">{errorMessage}</Alert>} {/* Fehlermeldung anzeigen */}
                <TextField
                    label="Neue WG erstellen"
                    variant="outlined"
                    value={newWGName}
                    onChange={(e) => setNewWGName(e.target.value)}
                    fullWidth
                    margin="normal"
                />
                <Button variant="contained" color="primary" onClick={handleCreateWG}>
                    Erstellen
                </Button>
            </Container>
            <Container maxWidth="sm" sx={{ padding: '20px' }}>
                {wgs.length > 0 ? (
                    <Card sx={{ backgroundColor: theme.palette.secondary.main }}>
                        <CardHeader title={"Deine WG's"}>
                        </CardHeader>
                        <CardContent>
                            <List>
                                {wgs.map((wg) => (
                                    <div key={wg.id}>
                                        <ListItem>
                                            <ListItemText primary={wg.name} sx={{ mx: 1.5 }} />
                                            <ListItemSecondaryAction>
                                                <Button variant="contained" color="primary" onClick={() => navigate(`/wg-overview/${wg.id}`)}>
                                                    Öffnen
                                                </Button>
                                            </ListItemSecondaryAction>
                                        </ListItem>
                                        <Divider />
                                    </div>
                                ))}
                            </List>
                        </CardContent>
                    </Card>
                ) : (
                    <Typography variant="subtitle1">
                        Du hast noch keine aktiven WG's.
                    </Typography>
                )}
            </Container>
        </div>
    );
};

export default WelcomePage;