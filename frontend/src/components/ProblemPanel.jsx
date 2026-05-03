export default function ProblemPanel({ prompt }) {
  return (
    <section className="panel problem-panel">
      <h3>Problem</h3>
      <div className="panel-content">
        <p>{prompt || 'Select a challenge to see the full requirements and starter context.'}</p>
      </div>
    </section>
  )
}
