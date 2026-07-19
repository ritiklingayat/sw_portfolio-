import emailjs from '@emailjs/browser';


const publicKey = import.meta.env.VITE_PUBLIC_KEY;
emailjs.init(publicKey);
export async function sendContactEmail(templateParams) {
  const serviceID = import.meta.env.VITE_SERVICE_ID;
  const contactTemplateID = import.meta.env.VITE_CONTACT_TEMPLATE_ID;
  return emailjs.send(serviceID, contactTemplateID, templateParams, publicKey);
}

export async function sendInternshipEmail(templateParams) {
  return emailjs.send(SERVICE_ID, INTERNSHIP_TEMPLATE_ID, templateParams, PUBLIC_KEY);
}
