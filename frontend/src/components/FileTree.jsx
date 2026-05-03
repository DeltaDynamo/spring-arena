export default function FileTree({ files, activePath, onSelect }) {
  return (
    <section className="panel file-tree-panel">
      <h3>Files</h3>
      <div className="panel-content file-tree">
        {files.length === 0 ? (
          <p>No editable files available.</p>
        ) : (
          files.map((file) => (
            <button
              key={file.path}
              className={`file-item ${file.path === activePath ? 'active' : ''}`}
              onClick={() => onSelect(file.path)}
            >
              {file.path}
            </button>
          ))
        )}
      </div>
    </section>
  )
}
