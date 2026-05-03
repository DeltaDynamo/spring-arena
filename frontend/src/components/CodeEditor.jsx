import Editor from '@monaco-editor/react';

export default function CodeEditor({ file, onChange }) {
  if (!file) {
    return <div className="editor-empty">Select a file to edit.</div>
  }

  return (
    <div className="editor-shell">
      <div className="editor-header">
        <div className="editor-title">{file.path}</div>
      </div>
      <div className="editor-container">
        <Editor
          height="520px"
          language="java"
          value={file.content}
          onChange={(value) => onChange(value || '')}
          theme="vs-dark"
          options={{
            minimap: { enabled: false },
            fontSize: 14,
            wordWrap: 'on',
            tabSize: 2,
            scrollBeyondLastLine: false,
            fontFamily: "'Monaco', 'Menlo', 'Ubuntu Mono', 'Consolas', 'source-code-pro', monospace",
            lineNumbers: 'on',
          }}
        />
      </div>
    </div>
  )
}
