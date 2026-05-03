export default function RunButton({ onRun, running }) {
  return (
    <button className="run-button" onClick={onRun} disabled={running}>
      {running ? 'Running tests…' : 'Run tests'}
    </button>
  )
}
