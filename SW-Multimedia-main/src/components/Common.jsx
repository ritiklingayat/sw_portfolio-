import {
  BookOpen, BriefcaseBusiness, CheckCircle2, Cloud, Code2,
  LayoutDashboard, ShieldCheck, Sparkles, Zap,
} from 'lucide-react';
import { useState } from 'react';
import { categories } from '../data/content';
import { submitInternship, submitContact } from '../services/api';

export function Title({ small, title }) {
  return <div className="title">{small && <span>{small}</span>}<h1>{title}</h1></div>;
}

export function FeatureIcon({ index }) {
  const icons = [Cloud, Zap, Code2, Sparkles, LayoutDashboard, ShieldCheck, BriefcaseBusiness, BookOpen];
  const Icon = icons[index % icons.length];
  return <div className="icon"><Icon size={24} /></div>;
}

// type: 'internship' | 'contact' (default: 'contact')
// resumeFile: File object passed from InternshipsPage when type='internship'
export function Form({ button, dark = false, type = 'contact', resumeFile = null }) {
  const [formData, setFormData] = useState({
    name: '',
    phone: '',
    email: '',
    course: '',
    message: '',
  });
  const [status, setStatus] = useState('idle');

  const updateField = (event) => {
    setFormData((current) => ({
      ...current,
      [event.target.name]: event.target.value,
    }));
  };

  const handleSubmit = async (event) => {
    event.preventDefault();
    setStatus('submitting');

    try {
      if (type === 'internship') {
        // POST /sw/internship — multipart/form-data (includes optional resume PDF)
        await submitInternship({ ...formData, file: resumeFile });
      } else {
        // POST /sw/contact — JSON body
        await submitContact(formData);
      }
      setStatus('success');
      setFormData({ name: '', phone: '', email: '', course: '', message: '' });
    } catch (error) {
      console.error(error);
      setStatus('error');
    }
  };

  return (
    <form className={dark ? 'form darkForm' : 'form'} onSubmit={handleSubmit}>
      <input name="name" value={formData.name} onChange={updateField} aria-label="Full name" placeholder="Enter your full name" required />
      <input name="phone" value={formData.phone} onChange={updateField} aria-label="Mobile number" placeholder="Enter your mobile number" required />
      <input name="email" type="email" value={formData.email} onChange={updateField} aria-label="Email address" placeholder="Enter your email address" required />
      <select name="course" value={formData.course} onChange={updateField} aria-label="Course target domain" required>
        <option value="" disabled>Select Course Target Domain</option>
        {categories.map((category) => <option key={category.title} value={category.title}>{category.title}</option>)}
      </select>
      <textarea name="message" value={formData.message} onChange={updateField} aria-label="Educational background or career goals" placeholder="Tell us about your educational background or career goals" />
      <button disabled={status === 'submitting'}>{status === 'submitting' ? 'Submitting...' : button}</button>
      {status === 'success' && <p className="formStatus success">Thank you. Your enquiry has been submitted successfully.</p>}
      {status === 'error' && <p className="formStatus error">Unable to submit right now. Please try again or contact us directly.</p>}
    </form>
  );
}

export function Stats() {
  const stats = [['5000+', 'Students Trained'], ['1000+', 'Placements Secured'], ['100+', 'Hiring Partners'], ['50+', 'Expert Tech Trainers']];
  return (
    <section className="stats">
      {stats.map(([value, label]) => <div key={label}><b>{value}</b><span>{label}</span></div>)}
    </section>
  );
}

export { CheckCircle2 };
