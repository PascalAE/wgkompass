import React from 'react';
import ReactDOM from 'react-dom/client';
import './index.css';
import App from './App';
import reportWebVitals from './reportWebVitals';

/**
 * index.tsx
 * 
 * This file is the entry point for the React application.
 * It renders the main App component inside a root DOM element and sets up reportWebVitals for performance measuring.
 * 
 * @module Index
 */

const root = ReactDOM.createRoot(
  document.getElementById('root') as HTMLElement
);
root.render(
  <React.StrictMode>
    <App />
  </React.StrictMode>
);


reportWebVitals();
