import { useRef, useState } from 'react';
import { BriefcaseBusiness, Upload } from 'lucide-react';
import { Form, Title } from '../components/Common';

export default function InternshipsPage() {
  const fileInputRef = useRef(null);
  const [resume, setResume] = useState(null);
  const [uploadError, setUploadError] = useState('');

  const handleResume = (file) => {
    if (!file) return;

    const fileName = (file.name || '').toLowerCase();
    const allowedMimeTypes = [
      'application/pdf',
      'application/msword',
      'application/vnd.openxmlformats-officedocument.wordprocessingml.document',
    ];
    const isAllowedFile = allowedMimeTypes.includes(file.type) || fileName.endsWith('.pdf') || fileName.endsWith('.doc') || fileName.endsWith('.docx');

    if (!isAllowedFile) {
      setUploadError('Please upload a PDF or Word file only.');
      setResume(null);
      return;
    }
    if (file.size > 5 * 1024 * 1024) {
      setUploadError('File must be 5MB or smaller.');
      setResume(null);
      return;
    }
    setUploadError('');
    setResume(file);
  };

  return (
    <section className="page split">
      <div>
        <Title title="Gain Absolute Marketplace Authority Through Technical Internships" />
        <p>Apply to active development, cloud, design and analytics internship roles. Approved candidates join live production feature teams.</p>
        {['Web Development Intern', 'Cloud & DevOps Intern', 'AI/ML Intern', 'UI/UX Design Intern'].map((role) => (
          <div key={role} className="job"><BriefcaseBusiness /> <b>{role}</b><span>Apply Now</span></div>
        ))}
      </div>
      <div className="upload" onDragOver={(event) => event.preventDefault()} onDrop={(event) => { event.preventDefault(); handleResume(event.dataTransfer.files?.[0]); }}>
        <input ref={fileInputRef} type="file" accept=".pdf,.doc,.docx,application/pdf,application/msword,application/vnd.openxmlformats-officedocument.wordprocessingml.document" className="srOnly" onChange={(event) => handleResume(event.target.files?.[0])} />
        <h2>Submit Onboarding Request</h2>
        <p>Complete the details below and upload your resume before submitting.</p>
        <Form
          button="Submit Internship Application"
          type="internship"
          resumeFile={resume}
          beforeSubmit={(
            <div className="resumeUploadField">
              <p className="resumeUploadTitle">Upload the resume here</p>
              <p>Drag and drop your engineering resume here (PDF or Word only, max 5MB)</p>
              <button type="button" className="uploadBtn" onClick={() => fileInputRef.current?.click()} aria-label="Upload resume PDF">
                <Upload size={32} />
                <span>Upload Resume</span>
              </button>
              {resume && <p className="uploadFileName">Selected: {resume.name}</p>}
              {uploadError && <p className="uploadError">{uploadError}</p>}
            </div>
          )}
        />
      </div>
    </section>
  );
}
