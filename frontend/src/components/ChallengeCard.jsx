export default function ChallengeCard({ challenge, onClick }) {
  const status = challenge.status || 'open';

  return (
    <button className={`challenge-card status-${status}`} onClick={onClick}>
      <div className="card-header">
        <div>
          <h2>{challenge.title}</h2>
          <p>{challenge.description || 'Complete the challenge by implementing required Spring Boot logic.'}</p>
        </div>
        <span className="difficulty-tag">{challenge.difficulty || 'unknown'}</span>
      </div>
      <div className="card-footer">
        <span className="status-pill">{status.toUpperCase()}</span>
      </div>
    </button>
  )
}
