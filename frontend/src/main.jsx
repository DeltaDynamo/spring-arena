import React from 'react';
import ReactDOM from 'react-dom/client';
import { BrowserRouter } from 'react-router-dom';
import App from './App';
import { ChallengeProvider } from './store/challengeStore';
import './styles/app.css';

ReactDOM.createRoot(document.getElementById('root')).render(
  <React.StrictMode>
    <BrowserRouter>
      <ChallengeProvider>
        <App />
      </ChallengeProvider>
    </BrowserRouter>
  </React.StrictMode>
)
