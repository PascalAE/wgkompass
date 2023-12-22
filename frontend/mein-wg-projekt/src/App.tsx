import React from 'react';
import logo from './logo.svg';
import './App.css';
import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';
import { ThemeProvider, createTheme } from '@mui/material';
import { WelcomePage } from './pages/WelcomePage';
import { WGOverview } from './pages/WGOverview';
import ManageAufgaben from './pages/ManageAufgaben';
import ManageMitglieder from './pages/ManageMitglieder';
import ManageInventar from './pages/ManageInventar';
import DissolveOverview from './pages/DissolveOverview';
import Layout from './components/Layout';

/**
 * App.tsx
 * 
 * This is the main application file for a React application using Material-UI and React Router.
 * It defines the theme of the application, sets up routing for different pages, and applies a consistent layout across all pages.
 * 
 * @module App
 */

// Define the theme for Material-UI components.

const theme = createTheme({
  palette: {
    primary: {
      main: '#ff1744',
    },
    secondary: {
      main: '#f0f0f0',
    },
    text: {
      primary: '#080a0b'
    },

  }
});

/**
 * The App component serves as the root of the React application.
 * It sets up a theme provider, router, and layout to be used throughout the application.
 * Routes are defined for navigation between different pages.
 */

function App() {
  return (
    <div className="App">
      <main>
        <ThemeProvider theme={theme}>
          <Router>
            <Layout>
              <Routes>
                <Route path="/" element={<WelcomePage />} />
                <Route path="/wg-overview/:wgId" element={<WGOverview />} />
                <Route path="/manage-aufgaben/:wgId" element={<ManageAufgaben />} />
                <Route path="/manage-mitglieder/:wgId" element={<ManageMitglieder />} />
                <Route path="/manage-inventar/:wgId" element={<ManageInventar />} />
                <Route path="/dissolve-overview" element={<DissolveOverview />} />
                {/* Weitere Routen hier */}
              </Routes>
            </Layout>
          </Router>
        </ThemeProvider>
      </main>
    </div>
  );
}

export default App;
