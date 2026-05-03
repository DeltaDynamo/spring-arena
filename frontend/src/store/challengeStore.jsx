import React, { createContext, useContext, useEffect, useState } from 'react';
import client from '../api/client';
import { mockChallenges } from '../data/mockChallenges';

const ChallengeContext = createContext(null);

export function ChallengeProvider({ children }) {
  const [challenges, setChallenges] = useState([]);
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState(null);

  useEffect(() => {
    async function loadChallenges() {
      setLoading(true);
      setError(null);
      try {
        const response = await client.get('/challenges');
        const data = Array.isArray(response.data) && response.data.length ? response.data : mockChallenges;
        setChallenges(data);
        if (!Array.isArray(response.data) || response.data.length === 0) {
          setError('No backend challenge data found. Showing mock challenge data instead.');
        }
      } catch (err) {
        console.error(err);
        setChallenges(mockChallenges);
        setError('Unable to load challenges from backend. Showing mock challenge data.');
      } finally {
        setLoading(false);
      }
    }

    loadChallenges();
  }, []);

  return (
    <ChallengeContext.Provider value={{ challenges, loading, error }}>
      {children}
    </ChallengeContext.Provider>
  )
}

export function useChallengeStore() {
  return useContext(ChallengeContext);
}
