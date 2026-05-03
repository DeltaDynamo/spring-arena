import { useEffect, useState } from 'react';
import client from '../api/client';
import { findChallengeById } from '../data/mockChallenges';

export default function useChallenge(id) {
  const [challenge, setChallenge] = useState(null);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);

  useEffect(() => {
    if (!id) {
      setLoading(false);
      return;
    }

    async function loadChallenge() {
      setLoading(true);
      setError(null);
      try {
        const response = await client.get(`/challenges/${id}`);
        if (response?.data) {
          setChallenge(response.data);
        } else {
          const fallback = findChallengeById(id);
          setChallenge(fallback);
          setError(fallback ? 'Loaded challenge from offline mock data.' : 'Challenge not found.');
        }
      } catch (err) {
        console.error(err);
        const fallback = findChallengeById(id);
        if (fallback) {
          setChallenge(fallback);
          setError('Unable to load challenge details from backend. Showing mock content.');
        } else {
          setError('Unable to load challenge details. Check backend connectivity.');
        }
      } finally {
        setLoading(false);
      }
    }

    loadChallenge();
  }, [id]);

  return { challenge, loading, error };
}
