import React from 'react';
import { useNavigate, useLocation } from 'react-router-dom';
import { Box, CssBaseline, Toolbar, AppBar, Divider, List, ListItem, ListItemButton, ListItemIcon, ListItemText, Typography } from '@mui/material';
import Drawer from '@mui/material/Drawer';
import HomeIcon from '@mui/icons-material/Home';
import ChairIcon from '@mui/icons-material/Chair';
import FormatListBulletedIcon from '@mui/icons-material/FormatListBulleted';
import PeopleAltIcon from '@mui/icons-material/PeopleAlt';
import SettingsIcon from '@mui/icons-material/Settings';
import { useTheme } from '@mui/material/styles';
import useMediaQuery from '@mui/material/useMediaQuery';


interface Props {
    children: React.ReactNode;
}

const drawerWidth = 250;

/**
 * `Layout` is a React component that provides a common layout structure including a navigation drawer and main content area.
 *
 * @component
 * @param {Object} props - The properties passed to the component.
 * @param {React.ReactNode} props.children - The content to be displayed in the main content area.
 *
 * @returns {React.ReactElement} The `Layout` React element.
 *
 * @description This component handles the navigation and layout structure for a WG management application. It provides a drawer for navigation on desktop devices and adjusts the layout based on the screen size.
 */
const Layout = ({ children }: Props) => {
    // ...Component logic and JSX...
    const theme = useTheme();
    const location = useLocation();
    const navigate = useNavigate();
    const isDesktop = useMediaQuery(theme.breakpoints.up('sm'));

    // get wg id from URL
    const pathSegments = location.pathname.split('/');
    const wgId = pathSegments[2];

    const isWgId = !isNaN(Number(wgId));
    const showNavigation = isWgId;
    const showEditButton = isWgId;

    const handleNavigation = (text: string) => {
        switch (text) {
            case 'Home':
                navigate('/');
                break;
            case 'Inventar':
                if (isWgId) navigate(`/manage-inventar/${wgId}`);
                break;
            case 'Aufgaben':
                if (isWgId) navigate(`/manage-aufgaben/${wgId}`);
                break;
            case 'Mitglieder':
                if (isWgId) navigate(`/manage-mitglieder/${wgId}`);
                break;
            default:
                break;
        }
    };

    const handleWGEdit = () => {
        if (location.pathname === `/wg-overview/${wgId}`) {
            navigate(`/wg-overview/${wgId}?openModal=true`);
            window.location.reload();
        }

        navigate(`/wg-overview/${wgId}?openModal=true`);
    };

    return (
        <Box sx={{ display: 'flex' }}>
            <CssBaseline />
            {isDesktop && showNavigation && (
                <Drawer
                    sx={{
                        backgroundColor: theme.palette.secondary.main,
                        width: drawerWidth,
                        flexShrink: 0,
                        '& .MuiDrawer-paper': {
                            backgroundColor: theme.palette.secondary.main,
                            width: drawerWidth,
                            boxSizing: 'border-box',
                        },

                    }}
                    variant="permanent"
                    anchor="left"
                >
                    <Toolbar />                    
                    <Divider />
                    <List sx={{ flexGrow: 1 }}>
                        {['Home', 'Inventar', 'Aufgaben', 'Mitglieder'].map((text, index) => (
                            <ListItem key={text} disablePadding>
                                <ListItemButton onClick={() => handleNavigation(text)}>
                                    <ListItemIcon >
                                        {text === 'Home' ? <HomeIcon sx={{ fill: theme.palette.text.primary }} />
                                            : text === 'Inventar' ? <ChairIcon sx={{ fill: theme.palette.text.primary }} />
                                                : text === 'Aufgaben' ? <FormatListBulletedIcon sx={{ fill: theme.palette.text.primary }} />
                                                    : text === 'Mitglieder' ? <PeopleAltIcon sx={{ fill: theme.palette.text.primary }} />
                                                        : null}
                                    </ListItemIcon>
                                    <ListItemText primary={text} />
                                </ListItemButton>
                            </ListItem>
                        ))}
                    </List>
                    {showEditButton && (
                        <div>
                            <Divider />
                            <List>
                                <ListItem key={"WG Bearbeiten"} disablePadding>
                                    <ListItemButton onClick={handleWGEdit}>
                                        <ListItemIcon>
                                            <SettingsIcon sx={{ fill: theme.palette.text.primary }} />
                                        </ListItemIcon>
                                        <ListItemText primary={"WG bearbeiten"} />
                                    </ListItemButton>
                                </ListItem>
                            </List>
                        </div>
                    )}

                </Drawer>
            )}

            <Box component="main" sx={{ flexGrow: 1, bgcolor: 'background.default', mt: 6 }}>
                {children}
            </Box >
        </Box>
    );
}

export default Layout;