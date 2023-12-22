import React, { useEffect, useState } from 'react';
import { useParams } from 'react-router-dom';
import PersonIcon from '@mui/icons-material/Person';
import { AppBar, Grid, Divider, Toolbar, Card, CardHeader, CardContent, Typography, List, ListItem, ListItemText, Button, ThemeProvider } from '@mui/material';
import { useTheme } from '@mui/material/styles';
import { getMitgliederByWGId, createMitglied, updateMitglied } from '../services/MitgliedService';
import { getInventarByWGId } from '../services/InventarService';
import { MitgliedDto, CreateMitgliedDto } from '../types/mitgliedTypes';
import { InventarDto } from '../types/inventarTypes';
import ModalForm from '../components/ModalForm';
import MitgliedForm from '../components/MitgliedForm';
import { useMediaQuery } from '@mui/material';

/**
 * `ManageMitglieder` is a React component for managing members (Mitglieder) in a WG (Wohngemeinschaft).
 * It allows users to view, add, and edit members and displays them in a list format.
 *
 * @component
 * @returns {React.ReactElement} The `ManageMitglieder` React element.
 *
 * @description This component fetches and displays members associated with a given WG ID. Users can add new members
 * or edit existing ones through a form presented in a modal. The component uses various Material-UI components 
 * to structure the layout and present the data in a user-friendly manner. It also handles the responsive layout
 * using Material-UI's useMediaQuery hook.
 */
const ManageMitglieder = () => {
     // ...Component logic and JSX...
    const { wgId } = useParams<{ wgId?: string }>();
    const [mitglieder, setMitglieder] = useState<MitgliedDto[]>([]);
    const [inventar, setInventar] = useState<InventarDto[]>([]);
    const [isModalOpen, setIsModalOpen] = useState(false);
    const [selectedMitglied, setSelectedMitglied] = useState<MitgliedDto | undefined>(undefined);
    const [errorMessage, setErrorMessage] = useState<string | null>(null);
    const theme = useTheme();
    const isMobile = useMediaQuery(theme.breakpoints.down('sm'));

    useEffect(() => {
        const fetchData = async () => {
            try {
                const wgIdNum = Number(wgId);
                const mitgliederData = await getMitgliederByWGId(wgIdNum);
                const inventarData = await getInventarByWGId(wgIdNum);
                setMitglieder(mitgliederData);
                setInventar(inventarData);
            } catch (error) {
                console.error('Error fetching data:', error);
            }
        }

        if (wgId) {
            fetchData();
        }
    }, [wgId]);

    const handleAddClick = () => {
        setIsModalOpen(true);
    };

    const handleEditClick = (mitglied: MitgliedDto) => {
        setSelectedMitglied(mitglied);
        setIsModalOpen(true);
    };

    const handleSubmitMitglied = async (mitgliedData: CreateMitgliedDto | MitgliedDto) => {
        try {
            if ('id' in mitgliedData && mitgliedData.id) {
                // Update existing member
                const updatedMitglied = await updateMitglied(mitgliedData.id, mitgliedData as MitgliedDto);
                setMitglieder(mitglieder.map(mitglied => mitglied.id === updatedMitglied.id ? updatedMitglied : mitglied));
            } else {
                // Create new member
                const neuesMitglied = await createMitglied(mitgliedData as CreateMitgliedDto);
                setMitglieder([...mitglieder, neuesMitglied]);
            }
            setIsModalOpen(false);
            setSelectedMitglied(undefined);
            setErrorMessage(null);
        } catch (error: any) {
            console.error('Error creating task:', error);
            setErrorMessage("Fehler beim Speichern: " + error.message || "Unbekannter Fehler");
        }
    };

    return (
        <div>
            <AppBar position="absolute" sx={{ bgcolor: '#ffffff', marginLeft: 250, width: isMobile ? '100%' : `calc(100% - 250px)` }} >
                <Toolbar>
                    <Typography variant="h5" component="h1" gutterBottom color={theme.palette.text.primary} sx={{ mt: 2 }}>
                        Mitglieder verwalten
                    </Typography>
                </Toolbar>
            </AppBar>
            <Grid container maxWidth="lg" spacing={3} p={3} m="auto" justifyContent="center">
                <Grid item xs={11} sm={6} >
                    <Card sx={{ backgroundColor: theme.palette.secondary.main }}>
                        <CardHeader title={"Mitglieder"}>
                        </CardHeader>
                        <CardContent>
                            <List>
                                {mitglieder.map((item, index) => (
                                    <div>
                                        <ListItem key={item.id}>
                                            <PersonIcon />
                                            <ListItemText primary={item.vorname + ' ' + item.nachname} sx={{ mx: 1.5 }} />
                                            <Button variant="outlined" onClick={() => handleEditClick(item)}>Bearbeiten</Button>
                                        </ListItem>
                                        <Divider />
                                    </div>

                                ))}
                            </List>
                            <Button variant="contained" onClick={() => handleAddClick()} disabled={inventar.length > 0}>
                                Neues Mitglied
                            </Button>
                        </CardContent>
                    </Card>
                </Grid>
            </Grid>
            <ModalForm
                open={isModalOpen}
                onClose={() => {
                    setIsModalOpen(false)
                    setSelectedMitglied(undefined);
                    setErrorMessage(null);
                }}
                title={selectedMitglied ? "Mitglied bearbeiten" : "Neues Mitglied erstellen"}
                errorMessage={errorMessage}
            >
                <MitgliedForm
                    mitglied={selectedMitglied}
                    onSubmit={handleSubmitMitglied}
                    wgId={Number(wgId)}
                    isEdit={selectedMitglied ? false : true}
                />
            </ModalForm>
        </div>
    );
}

export default ManageMitglieder;