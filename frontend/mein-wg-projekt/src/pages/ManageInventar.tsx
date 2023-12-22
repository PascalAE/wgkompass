import React, { useEffect, useState } from 'react';
import { useParams } from 'react-router-dom';
import { AppBar, Toolbar, Grid, Divider, Card, CardHeader, CardContent, Typography, List, ListItem, ListItemText, Button } from '@mui/material';
import { useTheme } from '@mui/material/styles';
import ChairIcon from '@mui/icons-material/Chair';
import { getInventarByWGId, createInventar, updateInventar } from '../services/InventarService';
import { getMitgliederByWGId } from '../services/MitgliedService'; // Fügen Sie Ihre Methode zum Laden der Mitglieder hier hinzu
import { InventarDto, CreateInventarDto } from '../types/inventarTypes';
import { MitgliedDto } from '../types/mitgliedTypes'; // Import für MitgliedDto
import ModalForm from '../components/ModalForm';
import InventarForm from '../components/InventarForm';
import { useMediaQuery } from '@mui/material';


/**
 * `ManageInventar` is a React component for managing inventory (Inventar) in a WG (Wohngemeinschaft).
 * It allows users to view, add, and edit inventory items and displays them in a list format.
 *
 * @component
 * @returns {React.ReactElement} The `ManageInventar` React element.
 *
 * @description This component fetches and displays inventory items from a given WG ID. Users can add new items
 * or edit existing ones through a form presented in a modal. The component uses various Material-UI components 
 * to structure the layout and present the data in a user-friendly manner. It also handles the responsive layout
 * using Material-UI's useMediaQuery hook.
 */
const ManageInventar = () => {
     // ...Component logic and JSX...

    const { wgId } = useParams<{ wgId?: string }>();
    const [inventar, setInventar] = useState<InventarDto[]>([]);
    const [mitglieder, setMitglieder] = useState<MitgliedDto[]>([]); // State für Mitglieder
    const [isModalOpen, setIsModalOpen] = useState(false);
    const [selectedInventar, setSelectedInventar] = useState<InventarDto | undefined>(undefined);
    const [errorMessage, setErrorMessage] = useState<string | null>(null);
    const theme = useTheme();
    const isMobile = useMediaQuery(theme.breakpoints.down('sm'));

    useEffect(() => {
        const fetchData = async () => {
            try {
                const wgIdNum = Number(wgId);
                const inventarData = await getInventarByWGId(wgIdNum);
                const mitgliederData = await getMitgliederByWGId(wgIdNum); // Laden der Mitglieder
                setInventar(inventarData);
                setMitglieder(mitgliederData); // Setzen der Mitglieder
            } catch (error) {
                console.error('Error fetching data:', error);
            }
        };

        if (wgId) {
            fetchData();
        }
    }, [wgId]);

    const handleAddClick = () => {
        setIsModalOpen(true);
    };

    const handleEditClick = (inventar: InventarDto) => {
        setSelectedInventar(inventar);
        setIsModalOpen(true);
    };

    const handleSubmitInventar = async (inventarData: CreateInventarDto | InventarDto) => {
        try {
            if ('id' in inventarData && inventarData.id) {
                // Update existing inventar
                const updatedInventar = await updateInventar(inventarData.id, inventarData as InventarDto);
                setInventar(inventar.map((item) => (item.id === updatedInventar.id ? updatedInventar : item)));
            } else {
                // Add new inventar
                const newInventar = await createInventar(inventarData as CreateInventarDto);
                setInventar([...inventar, newInventar]);
            }
            setIsModalOpen(false);
        } catch (error) {
            setErrorMessage('Fehler beim Speichern des Inventars');
        }
    };

    return (
        <div>
            <AppBar position="absolute" sx={{ bgcolor: '#ffffff', marginLeft: 250, width: isMobile ? '100%' : `calc(100% - 250px)` }} >
                <Toolbar>
                    <Typography variant="h5" component="h1" gutterBottom color={theme.palette.text.primary} sx={{ mt: 2 }}>
                        Inventar
                    </Typography>
                </Toolbar>
            </AppBar>
            <Grid container maxWidth="lg" spacing={3} p={3} m="auto" justifyContent="center">
                <Grid item xs={11} sm={6} >
                    <Card sx={{ backgroundColor: theme.palette.secondary.main }}>
                        <CardHeader title={"Inventar"}>
                        </CardHeader>
                        <CardContent>
                            <List>
                                {inventar.map((item) => (
                                    <div key={item.id}>
                                        <ListItem>
                                            <ChairIcon />
                                            <ListItemText primary={item.name} sx={{ mx: 1.5 }} />
                                            <Button variant="outlined" onClick={() => handleEditClick(item)}>Bearbeiten</Button>
                                        </ListItem>
                                        <Divider />
                                    </div>
                                ))}
                            </List>
                            <Button variant="contained" color="primary" onClick={handleAddClick}>Neues Inventar</Button>
                        </CardContent>
                    </Card>
                </Grid>
            </Grid>
            <ModalForm
                open={isModalOpen}
                onClose={() => {
                    setIsModalOpen(false);
                    setSelectedInventar(undefined);
                    setErrorMessage(null);
                }}
                title={selectedInventar ? "Inventar bearbeiten" : "Neues Inventar"}
                errorMessage={errorMessage}
            >
                <InventarForm
                    inventar={selectedInventar}
                    onSubmit={handleSubmitInventar}
                    wgId={Number(wgId)}
                    isEdit={!selectedInventar}
                    mitglieder={mitglieder}
                />
            </ModalForm>
        </div>
    );
};

export default ManageInventar;