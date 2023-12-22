// WGOverview.tsx
import React, { useState, useEffect } from 'react';
import { useParams, useNavigate, useSearchParams } from 'react-router-dom';
import { AppBar, Divider, Toolbar, Grid, Card, CardContent, CardHeader, Typography, List, ListItem, ListItemText, Button, Select, MenuItem, FormControl } from '@mui/material';
import ChairIcon from '@mui/icons-material/Chair';
import PersonIcon from '@mui/icons-material/Person';
import RadioButtonCheckedIcon from '@mui/icons-material/RadioButtonChecked';
import { useTheme } from '@mui/material/styles';
import { getAufgabenByWGId } from '../services/AufgabeService';
import { getInventarByWGId } from '../services/InventarService';
import { getMitgliederByWGId } from '../services/MitgliedService';
import { dissolveInventory } from '../services/DissolveService';
import { DissolveInventoryDto } from '../types/dissolveTypes';
import { AufgabeDto } from '../types/aufgabeTypes';
import { InventarDto } from '../types/inventarTypes';
import { MitgliedDto } from '../types/mitgliedTypes';
import { WGDto } from '../types/wgTypes';
import ModalForm from '../components/ModalForm';
import WgForm from '../components/WGForm';
import { getWGById, updateWG } from '../services/WGService';
import { useMediaQuery } from '@mui/material';

/**
 * `WGOverview` is a React component that provides a detailed overview of a specific WG (Wohngemeinschaft).
 * It displays WG members, inventory, and tasks and offers functionalities to manage and dissolve them.
 *
 * @component
 * @returns {React.ReactElement} The `WGOverview` React element.
 *
 * @description This component uses a combination of Material-UI components to create a user-friendly interface
 * where users can view and interact with different aspects of a WG such as members, tasks, and inventory.
 * It allows for the editing of WG details, managing individual sections, and dissolving inventory through a dissolve process.
 */
export const WGOverview = () => {
    // ...Component logic and JSX...
    
    const { wgId } = useParams<{ wgId?: string }>();
    const [searchParams] = useSearchParams();
    const navigate = useNavigate();
    const openModalOnLoad = searchParams.get('openModal') === 'true';
    const [wg, setWg] = useState<WGDto>();
    const [aufgaben, setAufgaben] = useState<AufgabeDto[]>([]);
    const [inventar, setInventar] = useState<InventarDto[]>([]);
    const [mitglieder, setMitglieder] = useState<MitgliedDto[]>([]);
    const [isWGEditModalOpen, setIsWGEditModalOpen] = useState(false);
    const [isDissolveModalOpen, setIsDissolveModalOpen] = useState(false);
    const [inventoryAssignments, setInventoryAssignments] = useState<Map<number, number>>(new Map());
    const [errorMessage, setErrorMessage] = useState<string | null>(null);
    const theme = useTheme();
    const isMobile = useMediaQuery(theme.breakpoints.down('sm'));

    useEffect(() => {
        const fetchData = async () => {
            try {
                const aufgabenData = await getAufgabenByWGId(Number(wgId));
                const inventarData = await getInventarByWGId(Number(wgId));
                const mitgliederData = await getMitgliederByWGId(Number(wgId));
                const wgData = await getWGById(Number(wgId));
                setAufgaben(aufgabenData);
                setInventar(inventarData);
                setMitglieder(mitgliederData);
                setWg(wgData);

                if (openModalOnLoad) {
                    setIsWGEditModalOpen(true);
                }

            } catch (error) {
                console.error('Error fetching data:', error);
            }
        };

        if (wgId) {
            fetchData();
        }

    }, [wgId]);

    const handleVerwaltenClick = (section: 'aufgaben' | 'inventar' | 'mitglieder') => {
        // Navigate to the respective management page
        navigate(`/manage-${section}/${wgId}`);
    };

    const hanldeSubmitWG = async (wgData: WGDto) => {
        try {
            const updatedWg = await updateWG(wgData.id, wgData);
            setWg(updatedWg);
            setIsWGEditModalOpen(false);
            setErrorMessage(null);
        } catch (error: any) {
            console.error('Error creating task:', error);
            setErrorMessage("Fehler beim Speichern: " + error.message || "Unbekannter Fehler");
        }
    };

    const handleInventoryMemberChange = (inventarId: number, mitgliedId: number) => {
        setInventoryAssignments(new Map(inventoryAssignments.set(inventarId, mitgliedId)));
    };

    const handleDissolveInventory = async () => {
        const dissolveInventoryDto: DissolveInventoryDto = {
            wgId: Number(wgId),
            inventoryMappings: Array.from(inventoryAssignments, ([inventarId, mitgliedId]) => ({ inventarId, mitgliedId }))
        };
        const result = await dissolveInventory(dissolveInventoryDto);
        navigate('/dissolve-overview', { state: { result, mitglieder, inventar, wg } });
    };

    return (
        <div>
            <AppBar position="absolute" sx={{ bgcolor: '#ffffff', marginLeft: 250, width: isMobile ? '100%' : `calc(100% - 250px)`, }} >
                <Toolbar>
                    <Typography variant="h5" component="h1" gutterBottom color={theme.palette.text.primary} sx={{ mt: 2 }}>
                        {wg?.name}
                    </Typography>
                </Toolbar>
            </AppBar>
            <Grid container maxWidth="lg" spacing={3} p={3} m="auto">
                <Grid item xs={11} sm={6}  >
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
                                        </ListItem>
                                        <Divider />
                                    </div>

                                ))}
                            </List>
                            <Button
                                variant="contained"
                                onClick={() => handleVerwaltenClick('mitglieder')}
                                disabled={inventar.length > 0}
                                sx={{ m: 2 }}
                            >                            Mitglieder verwalten
                            </Button>
                        </CardContent>
                    </Card>
                </Grid>
                <Grid item xs={11} sm={6}  >
                    <Card sx={{ backgroundColor: theme.palette.secondary.main }}>
                        <CardHeader title={"Inventar"}>
                        </CardHeader>
                        <CardContent>
                            <List>
                                {inventar.map((item, index) => (
                                    <div>
                                        <ListItem key={item.id} >
                                            <ChairIcon />
                                            <ListItemText primary={item.name} sx={{ mx: 1.5 }} />
                                        </ListItem>
                                        <Divider />
                                    </div>
                                ))}
                            </List>
                            <Button
                                variant="contained"
                                onClick={() => handleVerwaltenClick('inventar')}
                                disabled={mitglieder.length === 0}
                                sx={{ m: 2 }}
                            >
                                Inventar verwalten
                            </Button>
                            <Button
                                variant="contained"
                                onClick={() => setIsDissolveModalOpen(true)}
                                disabled={inventar.length === 0}
                            >
                                Inventar auflösen
                            </Button>
                        </CardContent>
                    </Card>
                </Grid>
                <Grid item xs={11} sm={6}  >
                    <Card sx={{ backgroundColor: theme.palette.secondary.main }} >
                        <CardHeader title={"Aufgaben"}>
                        </CardHeader>
                        <CardContent>
                            <List>
                                {aufgaben.map((item, index) => (
                                    <div>
                                        <ListItem key={item.id}>
                                            <RadioButtonCheckedIcon color={"primary"} />
                                            <ListItemText primary={item.titel} sx={{ mx: 1.5 }} />

                                        </ListItem>
                                        <Divider />
                                    </div>
                                ))}
                            </List>
                            <Button
                                variant="contained"
                                onClick={() => handleVerwaltenClick('aufgaben')}
                                disabled={mitglieder.length === 0}
                                sx={{ m: 2 }}
                            >
                                Aufgaben verwalten
                            </Button>
                        </CardContent>
                    </Card>
                </Grid>
            </Grid>

            <ModalForm
                open={isWGEditModalOpen}
                onClose={() => {
                    setIsWGEditModalOpen(false)
                    setErrorMessage(null);
                }}
                title={"WG bearbeiten"}
                errorMessage={errorMessage}
            >
                <WgForm
                    wgId={Number(wgId)}
                    wg={wg}
                    onSubmit={hanldeSubmitWG}
                />
            </ModalForm>

            <ModalForm
                open={isDissolveModalOpen}
                onClose={() => setIsDissolveModalOpen(false)}
                title="Inventar auflösen"
            >
                <Grid container p={2} spacing={4}>
                    <Grid item xs={12}>
                        {inventar.map((inv) => (
                            <div key={inv.id}>
                                <Typography>{inv.name}</Typography>
                                <FormControl fullWidth>
                                    <Select
                                        value={inventoryAssignments.get(inv.id) || ''}
                                        onChange={(e) => handleInventoryMemberChange(inv.id, e.target.value as number)}
                                    >
                                        {mitglieder.map((mitglied) => (
                                            <MenuItem key={mitglied.id} value={mitglied.id}>
                                                {mitglied.vorname} {mitglied.nachname}
                                            </MenuItem>
                                        ))}
                                    </Select>
                                </FormControl>
                            </div>
                        ))}
                    </Grid>
                    <Grid item xs={12} >
                        <Button variant="contained" onClick={handleDissolveInventory}>Inventar auflösen</Button>
                    </Grid>
                </Grid>
            </ModalForm>
        </div>
    );
};
