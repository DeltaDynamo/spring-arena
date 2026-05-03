import { useNavigate } from 'react-router-dom';
import { useChallengeStore } from '../store/challengeStore';
import ChallengeCard from '../components/ChallengeCard';

export default function DashboardPage() {
  const navigate = useNavigate();
  const { challenges, loading, error } = useChallengeStore();

  return (
    <div className="app-shell">
      <header className="page-header">
        <div>
          <p className="eyebrow">Spring Arena</p>
          <h1>Spring Boot Coding Challenges</h1>
          <p className="subtitle">Browse backend tasks, edit code in the browser, and run tests to verify</p>
        </div>
      </header>

      {loading && <div className="status-message">Loading challenges...</div>}
      {error && <div className="status-message error">{error}</div>}

      {!loading && challenges.length > 0 && (
        <div className="dashboard-grid">
          {challenges.map((challenge) => (
            <ChallengeCard
              key={challenge.id}
              challenge={challenge}
              onClick={() => navigate(`/challenge/${challenge.id}`)}
            />
          ))}
        </div>
      )}
    </div>
  )
}
