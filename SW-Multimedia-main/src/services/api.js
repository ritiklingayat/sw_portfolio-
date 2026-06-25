// Spring Boot backend base URL (port 8082)
const SPRING_BASE_URL = 'http://localhost:8082/sw';

/**
 * Submit internship application — multipart/form-data
 * POST /sw/internship
 */
export async function submitInternship({ name, phone, email, course, message, file }) {
  const formData = new FormData();
  formData.append('name', name);
  formData.append('email', email);
  formData.append('number', phone);
  formData.append('course', course);
  formData.append('education_background', message || '');
  if (file) {
    formData.append('file', file);
  }

  const response = await fetch(`${SPRING_BASE_URL}/internship`, {
    method: 'POST',
    body: formData,
    // Do NOT set Content-Type header — browser sets multipart boundary automatically
  });

  if (!response.ok) {
    throw new Error(`Internship submission failed: ${response.status}`);
  }

  return response.json();
}

/**
 * Submit contact / help request — JSON body
 * POST /sw/contact
 */
export async function submitContact({ name, phone, email, course, message }) {
  const payload = {
    name,
    email,
    number: phone,
    course,
    education_background: message || '',
  };

  const response = await fetch(`${SPRING_BASE_URL}/contact`, {
    method: 'POST',
    headers: { 'Content-Type': 'application/json' },
    body: JSON.stringify(payload),
  });

  if (!response.ok) {
    throw new Error(`Contact submission failed: ${response.status}`);
  }

  return response.json();
}
