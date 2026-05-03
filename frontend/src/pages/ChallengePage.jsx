import { useEffect, useState } from 'react';
import { useNavigate, useParams } from 'react-router-dom';
import useChallenge from '../hooks/useChallenge';
import client from '../api/client';
import ProblemPanel from '../components/ProblemPanel';
import FileTree from '../components/FileTree';
import CodeEditor from '../components/CodeEditor';
import RunButton from '../components/RunButton';
import OutputPanel from '../components/OutputPanel';

export default function ChallengePage() {
  const { id } = useParams();
  const navigate = useNavigate();
  const { challenge, loading, error } = useChallenge(id);
  const [files, setFiles] = useState([]);
  const [activePath, setActivePath] = useState('');
  const [runResult, setRunResult] = useState(null);
  const [running, setRunning] = useState(false);

  useEffect(() => {
    if (!challenge?.files) {
      return;
    }

    setFiles(
      challenge.files.map((file) => ({
        path: file.path,
        content: file.content,
      }))
    );
    setActivePath(challenge.files[0]?.path || '');
  }, [challenge]);

  const activeFile = files.find((file) => file.path === activePath) || files[0];

  const handleFileUpdate = (content) => {
    setFiles((current) =>
      current.map((file) =>
        file.path === activePath ? { ...file, content } : file
      )
    );
  };

  const handleRun = async () => {
    setRunning(true);
    setRunResult(null);

    try {
      const response = await client.post(`/challenges/${id}/run`, {
        files,
      });
      setRunResult(response.data);
    } catch (err) {
      console.error(err);
      setRunResult({
        success: false,
        message:
          err.response?.data?.message ||
          'Unable to run tests. Please check the backend server.',
      });
    } finally {
      setRunning(false);
    }
  };

  return (
    <div className="app-shell challenge-page">
      <div className="challenge-header">
        <button className="ghost-button" onClick={() => navigate('/')}>Back</button>
        <div>
          <p className="eyebrow">Challenge</p>
          <h1>{challenge?.title || 'Loading challenge...'}</h1>
          <p className="subtitle">{challenge?.difficulty ? `Difficulty: ${challenge.difficulty}` : 'Loading details...'}</p>
        </div>
      </div>

      {loading && <div className="status-message">Loading challenge details...</div>}
      {error && <div className="status-message error">{error}</div>}

      {!loading && challenge && (
        <div className="challenge-layout">
          <aside className="sidebar-panel">
            <ProblemPanel prompt={challenge.prompt} />
            <FileTree
              files={files}
              activePath={activePath}
              onSelect={setActivePath}
            />
          </aside>

          <div className="editor-panel">
            <CodeEditor
              file={activeFile}
              onChange={handleFileUpdate}
            />
            <div className="run-bar">
              <RunButton onRun={handleRun} running={running} />
            </div>
            <OutputPanel result={runResult} />
          </div>
        </div>
      )}
    </div>
  )
}
