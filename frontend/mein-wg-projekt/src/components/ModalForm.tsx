// ModalForm.tsx
import React from 'react';
import { Dialog, DialogTitle, DialogContent, IconButton, Typography } from '@mui/material';
import CloseIcon from '@mui/icons-material/Close';

interface Props{
    open: boolean;
    onClose: () => void;
    children: React.ReactNode;
    title: string;
    errorMessage?: string | null;
}

/**
 * `ModalForm` is a React component that provides a generic modal dialog with a customizable title and content.
 *
 * @component
 * @param {Object} props - The properties passed to the component.
 * @param {boolean} props.open - A flag indicating whether the modal is open.
 * @param {() => void} props.onClose - The function called when the modal is requested to be closed.
 * @param {React.ReactNode} props.children - The content to be displayed inside the modal.
 * @param {string} props.title - The title of the modal dialog.
 * @param {string | null} [props.errorMessage] - An optional error message to display in the modal.
 *
 * @returns {React.ReactElement} The `ModalForm` React element.
 */
const ModalForm = ({ open, onClose, children, title, errorMessage }: Props) => {
  // ...Component logic and JSX...
  return (
    <Dialog open={open} onClose={onClose}>
      <DialogTitle>
        {title}
        <IconButton onClick={onClose} style={{ position: 'absolute', right: 8, top: 8 }}>
          <CloseIcon />
        </IconButton>
      </DialogTitle>
      <DialogContent>
        {children}
        {errorMessage && (
            <Typography color="error" style={{ margin: '10px' }}>
                {errorMessage}
            </Typography>
        )}
      </DialogContent>
    </Dialog>
  );
};

export default ModalForm;