import { useEffect, useState } from 'react';
import { useParams } from 'react-router-dom';
import { AppBar, Toolbar, Typography, Grid, Divider, Card, CardHeader, CardContent, List, ListItem, ListItemText, Button } from '@mui/material';
import RadioButtonCheckedIcon from '@mui/icons-material/RadioButtonChecked';
import { useTheme } from '@mui/material/styles';
import { getAufgabenByWGId, createAufgabe, updateAufgabe } from '../services/AufgabeService';
import { getMitgliederByWGId } from '../services/MitgliedService';
import { AufgabeDto, CreateAufgabeDto } from '../types/aufgabeTypes';
import { MitgliedDto } from '../types/mitgliedTypes';
import ModalForm from '../components/ModalForm';
import AufgabeForm from '../components/AufgabeForm';
import { useMediaQuery } from '@mui/material';

/**
 * `ManageAufgaben` is a React component for managing tasks (Aufgaben) in a WG (Wohngemeinschaft).
 * It allows users to view, add, and edit tasks and displays them in a list format.
 *
 * @component
 * @returns {React.ReactElement} The `ManageAufgaben` React element.
 *
 * @description This component fetches and displays tasks associated with a given WG ID. Users can add new tasks
 * or edit existing ones through a form presented in a modal. The component uses various Material-UI components 
 * to present the tasks in a user-friendly manner and handle the responsive layout.
 */
const ManageAufgaben = () => {
     // ...Component logic and JSX...

    const { wgId } = useParams<{ wgId?: string }>();
    const [aufgaben, setAufgaben] = useState<AufgabeDto[]>([]);
    const [mitglieder, setMitglieder] = useState<MitgliedDto[]>([]);
    const [isModalOpen, setIsModalOpen] = useState(false);
    const [selectedAufgabe, setSelectedAufgabe] = useState<AufgabeDto | undefined>(undefined);
    const [errorMessage, setErrorMessage] = useState<string | null>(null);
    const theme = useTheme();
    const isMobile = useMediaQuery(theme.breakpoints.down('sm'));

    useEffect(() => {
        const fetchData = async () => {
            try {
                const wgIdNum = Number(wgId);
                const aufgabenData = await getAufgabenByWGId(wgIdNum);
                const mitgliederData = await getMitgliederByWGId(wgIdNum);
                setAufgaben(aufgabenData);
                setMitglieder(mitgliederData);
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

    const handleEditClick = (aufgabe: AufgabeDto) => {
        setSelectedAufgabe(aufgabe);
        setIsModalOpen(true);
    };

    const handleSubmitAufgabe = async (aufgabeData: CreateAufgabeDto | AufgabeDto) => {
        try {
            if ('id' in aufgabeData && aufgabeData.id) {
                // Update existing task
                const updatedAufgabe = await updateAufgabe(aufgabeData.id, aufgabeData as AufgabeDto);
                setAufgaben(aufgaben.map(aufgabe => aufgabe.id === updatedAufgabe.id ? updatedAufgabe : aufgabe));
            } else {
                // Create new task
                const neueAufgabe = await createAufgabe(aufgabeData as CreateAufgabeDto);
                setAufgaben([...aufgaben, neueAufgabe]);
            }
            setIsModalOpen(false);
            setSelectedAufgabe(undefined);
            setErrorMessage(null);
        } catch (error: any) {
            console.error('Error handling task:', error);
            setErrorMessage("Fehler beim Speichern: " + error.message || "Unbekannter Fehler");
        }
    };

    return (
        <div>
            <AppBar position="absolute" sx={{ bgcolor: '#ffffff', marginLeft: 250,width: isMobile ? '100%' : `calc(100% - 250px)` }} >
                <Toolbar>
                    <Typography variant="h5" component="h1" gutterBottom color={theme.palette.text.primary} sx={{ mt: 2 }}>
                        Aufgaben
                    </Typography>
                </Toolbar>
            </AppBar>
            <Grid container maxWidth="lg" spacing={3} p={3} m="auto" justifyContent="center">
                <Grid item xs={11} sm={6} >
                    <Card sx={{ backgroundColor: theme.palette.secondary.main }} >
                        <CardHeader title={"Aufgaben"}>
                        </CardHeader>
                        <CardContent>
                            <List>
                                {aufgaben.map((item) => (
                                    <div>
                                        <ListItem key={item.id}>
                                            <RadioButtonCheckedIcon color={"primary"} />
                                            <ListItemText primary={item.titel} sx={{ mx: 1.5 }} />
                                            <Button variant="outlined" onClick={() => handleEditClick(item)}>Bearbeiten</Button>
                                        </ListItem>
                                        <Divider />
                                    </div>
                                ))}
                            </List>
                            <Button variant="contained" onClick={() => handleAddClick()}>
                                Neue Aufgabe
                            </Button>
                        </CardContent>
                    </Card>
                </Grid>
            </Grid>
            <ModalForm
                open={isModalOpen}
                onClose={() => {
                    setIsModalOpen(false)
                    setSelectedAufgabe(undefined);
                    setErrorMessage(null);
                }}
                title={selectedAufgabe ? "Aufgabe bearbeiten" : "Neue Aufgabe erstellen"}
                errorMessage={errorMessage}
            >
                <AufgabeForm
                    aufgabe={selectedAufgabe}
                    onSubmit={handleSubmitAufgabe}
                    wgId={Number(wgId)}
                    mitglieder={mitglieder}
                    isEdit={selectedAufgabe ? false : true}
                />
            </ModalForm>
        </div>
    );
};

export default ManageAufgaben;