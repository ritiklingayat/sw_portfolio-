import { useState } from 'react';
import { ArrowRight, ChevronRight, PlayCircle, Sparkles } from 'lucide-react';
import { Form, Stats, Title } from '../components/Common';
import CourseVisual from '../components/CourseVisual';
import GridlineAnimation from '../components/GridlineAnimation';
import PartnerLogo from '../components/PartnerLogo';
import { features, journeySteps, partners, popular } from '../data/content';
import heroVideo from '../../images/3d.mp4';

function Hero({ go }) {
  return (
    <section className="hero">
      <GridlineAnimation />
      <div className="heroText">
        <span className="badge"><Sparkles size={16} /> Premium Ed-Tech Platform</span>
        <h1>Transform Your Career With Industry-Oriented IT Training</h1>
        <p>Learn in-demand technologies through real-time projects, certified tech internships, and robust placement support. Move from concepts to production code under expert guidance.</p>
        <div className="actions">
          <button onClick={() => go('Courses')}>Explore Courses <ArrowRight size={18} /></button>
          <button className="secondary" onClick={() => go('Contact')}>Book Free Counselling</button>
        </div>
      </div>
      <div className="heroArt videoHero">
        <GridlineAnimation variant="dark" />
        <video
          src={heroVideo}
          className="heroVideo"
          autoPlay
          loop
          muted
          playsInline
          aria-label="SW Multimedia 3D course showcase"
        />
      </div>
    </section>
  );
}

function Popular({ openCourse }) {
  const descriptions = [
    'Master cloud architecture, secure infrastructure automation, and multi-region networks.',
    'Implement CI/CD lifecycles, Docker containers and Kubernetes clusters.',
    'Build scalable client-server apps using React, Node and Next.js.',
    'Develop ML, deep learning, generative AI and automation systems.',
  ];

  return (
    <section className="section soft">
      <Title small="Demanded Courses" title="Build job-ready skills with structured learning tracks" />
      <div className="grid cards">
        {popular.map((course, index) => (
          <article className="card course" onClick={() => openCourse(course)} key={course}>
            <CourseVisual name={course} variant="stack" />
            <h3>{course}</h3>
            <p>{descriptions[index % descriptions.length]}</p>
            <a>View Track <ChevronRight size={16} /></a>
          </article>
        ))}
      </div>
    </section>
  );
}

function Why() {
  const descriptions = [
    'Learn directly from experienced trainers and certified technology professionals.',
    'Build practical software frameworks inside live hosting and project environments.',
    'Bridge theory and corporate workflow through validated internship pathways.',
    'Access resume building, interview preparation and hiring partner support.',
    'Practice technical, behavioral and project explanation rounds confidently.',
    'Optimize LinkedIn, GitHub, resume and career direction with mentors.',
  ];

  return (
    <section className="section">
      <Title small="Why SW Multimedia" title="A premium training ecosystem, not a basic institute website" />
      <div className="grid featureGrid">
        {features.map((feature, index) => (
          <div className="feature" key={feature}>
            <CourseVisual name={feature} variant="feature" />
            <h3>{feature}</h3>
            <p>{descriptions[index]}</p>
          </div>
        ))}
      </div>
    </section>
  );
}

function Journey() {
  const [active, setActive] = useState(null);
  const [hovered, setHovered] = useState(null);
  const previewIndex = active ?? hovered;
  const activeStep = previewIndex !== null ? journeySteps[previewIndex] : null;
  const hubTitle = activeStep ? activeStep.title : 'Roadmap';
  const hubText = activeStep ? activeStep.text : 'Hover a step to pause and preview each phase.';

  return (
    <section className="section dark journeyShowcase">
      <GridlineAnimation variant="dark" />
      <Title small="Student Journey" title="From enrolment to placement readiness" />
      <div className="ferrisJourney" aria-label="Animated student journey ferris wheel">
        <div className="ferrisWheel" style={{ '--count': journeySteps.length }}>
          <div className="ferrisRing">
            {journeySteps.map((step, index) => (
              <button
                className="ferrisStep"
                key={step.title}
                style={{
                  '--angle': `${(360 / journeySteps.length) * index}deg`,
                  '--cabin-start': `${(-360 / journeySteps.length) * index}deg`,
                  '--cabin-end': `${(-360 / journeySteps.length) * index - 360}deg`,
                }}
                onMouseEnter={() => setHovered(index)}
                onMouseLeave={() => setHovered(null)}
                onFocus={() => setHovered(index)}
                onBlur={() => setHovered(null)}
                onClick={() => setActive(index)}
              >
                <span className="ferrisCabin">
                  <span
                    className="ferrisImage"
                    style={{ backgroundImage: `url(${step.image})`, backgroundPosition: step.imagePosition || 'center' }}
                  />
                  <span className="ferrisCopy">
                    <b>{String(index + 1).padStart(2, '0')}</b>
                    <span>{step.title}</span>
                    <small>{step.tag}</small>
                  </span>
                </span>
              </button>
            ))}
          </div>
          <div className="ferrisHub">
            <span>Carrer Map</span>
            <strong className={activeStep ? 'is-active' : ''}>{hubTitle}</strong>
            <p className={activeStep ? 'is-active' : ''}>{hubText}</p>
          </div>
        </div>
      </div>

      {active !== null && (
        <div className="journeyModal" role="dialog" onClick={() => setActive(null)}>
          <div className="journeyModalContent" onClick={(event) => event.stopPropagation()}>
            <div className="journeyModalIcon">
              <img src={journeySteps[active].image} alt={`${journeySteps[active].title} student journey`} className="journeyImageLarge" />
            </div>
            <h3>{journeySteps[active].title}</h3>
            <p>{journeySteps[active].text}</p>
            <button className="cta" onClick={() => setActive(null)}>Close</button>
          </div>
        </div>
      )}
    </section>
  );
}

function Partners() {
  return (
    <section className="partners" aria-label="Hiring and technology partners">
      <div className="partnerTrack">
        {[...partners, ...partners].map((partner, index) => (
          <PartnerLogo partner={partner} key={`${partner.name}-${index}`} />
        ))}
      </div>
    </section>
  );
}

function Testimonials() {
  return (
    <section className="section soft">
      <div className="testimonial">
        <video
          src={heroVideo}
          className="testimonialVideo"
          autoPlay
          loop
          muted
          playsInline
          aria-label="SW Multimedia video testimonial"
        />
        <div>
          <span className="badge">Student Success</span>
          <h2>"The production-level MERN stack training transformed my approach to building software."</h2>
          <p>The integrated internship portfolio helped me prepare for real interviews and build confidence within 3 months of completing my track.</p>
          <b>Mr. Sachin Wathore</b>
        </div>
      </div>
    </section>
  );
}

function LeadForm() {
  return (
    <section className="lead">
      <h2>Kickstart Your Transition Into Technical Excellence</h2>
      <p>An admissions analyst will contact you with a course path matching your profile goals.</p>
      <Form button="Submit Application & Book Consultation" dark />
    </section>
  );
}

function CareerCta({ go }) {
  return (
    <section className="footerCta homeCareerCta">
      <div>
        <h2>READY TO START YOUR IT CAREER?</h2>
        <p>Join SW Multimedia today and take the first step towards a successful technology career.</p>
      </div>
      <button onClick={() => go('Contact')}>ENQUIRE NOW</button>
    </section>
  );
}

export default function HomePage({ go, openCourse }) {
  return (
    <>
      <Hero go={go} />
      <Stats />
      <Popular openCourse={openCourse} />
      <Why />
      <Journey />
      <Partners />
      <Testimonials />
      <CareerCta go={go} />
      <LeadForm />
    </>
  );
}
