import emailjs from '@emailjs/browser';

const SERVICE_ID = 'service_bonx91t';
const CONTACT_TEMPLATE_ID = 'template_drqorul';
const INTERNSHIP_TEMPLATE_ID = 'template_iz8zpqb';
const PUBLIC_KEY = 'eZUUN-YkZXMludBNy';

emailjs.init(PUBLIC_KEY);

export async function sendContactEmail(templateParams) {
  return emailjs.send(SERVICE_ID, CONTACT_TEMPLATE_ID, templateParams, PUBLIC_KEY);
}

export async function sendInternshipEmail(templateParams) {
  return emailjs.send(SERVICE_ID, INTERNSHIP_TEMPLATE_ID, templateParams, PUBLIC_KEY);
}
