export default function OutputPanel({ result }) {
  if (!result) {
    return (
      <section className="panel output-panel">
        <h3>Output</h3>
        <div className="panel-content">Run the challenge to see results here.</div>
      </section>
    )
  }

  return (
    <section className={`panel output-panel ${result.success ? 'success' : 'failure'}`}>
      <h3>Output</h3>
      <div className="panel-content">
        <p className="output-status">
          {result.success ? 'All tests passed ✅' : '❌ Test run failed'}
        </p>
        <pre>{result.message || JSON.stringify(result, null, 2)}</pre>
      </div>
    </section>
  )
}
