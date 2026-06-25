# Docker Setup Guide

This project is configured to run with Docker and Docker Compose. It includes:
- **PostgreSQL** database for the Java backend
- **MongoDB** for the Node.js backend
- **Java Spring Boot** backend (port 8082)
- **Node.js Express** backend (port 5000)
- **React Frontend** with Vite (port 3000)

## Prerequisites

- Docker (v20.10+)
- Docker Compose (v1.29+)

## Quick Start

1. **Clone/Extract the project** and navigate to the project root:
   ```bash
   cd SW-Multimedia-updated
   ```

2. **Create environment file** from template:
   ```bash
   cp .env.example .env
   ```

3. **Update `.env`** with your actual configuration:
   ```env
   DB_USER=postgres
   DB_PASSWORD=your_secure_password
   MAIL_USERNAME=your_email@gmail.com
   MAIL_PASSWORD=your_app_password
   APP_ADMIN_EMAIL=admin@example.com
   ```

4. **Build and start all services**:
   ```bash
   docker-compose up -d
   ```

   Or with logs:
   ```bash
   docker-compose up
   ```

5. **Access the services**:
   - Frontend: http://localhost:3000
   - Node.js Backend: http://localhost:5000
   - Java Backend: http://localhost:8082
   - PostgreSQL: localhost:5432
   - MongoDB: localhost:27017

## Common Commands

### View logs
```bash
docker-compose logs -f
docker-compose logs -f java-backend
docker-compose logs -f node-backend
docker-compose logs -f frontend
```

### Stop services
```bash
docker-compose down
```

### Stop and remove volumes (clean slate)
```bash
docker-compose down -v
```

### Rebuild images
```bash
docker-compose build --no-cache
```

### Restart a specific service
```bash
docker-compose restart java-backend
```

## Project Structure

```
SW-Multimedia-updated/
├── docker-compose.yml           # Main orchestration file
├── .env.example                 # Environment variables template
├── .dockerignore                # Files to exclude from Docker
├── sw_backend/                  # Java Spring Boot backend
│   ├── Dockerfile              # Java backend Docker image
│   ├── pom.xml
│   └── src/
├── SW-Multimedia-main/          # Frontend and Node.js backend
│   ├── Dockerfile.backend       # Node.js backend Docker image
│   ├── Dockerfile.frontend      # Frontend Docker image
│   ├── Backend/
│   │   ├── server.js
│   │   └── package.json
│   ├── src/
│   ├── package.json
│   └── vite.config.js
└── DOCKER_SETUP.md             # This file
```

## Services Configuration

### Java Backend
- **Image**: Built from `sw_backend/Dockerfile`
- **Port**: 8082
- **Database**: PostgreSQL
- **Environment**: Spring Boot properties passed via environment variables
- **Startup**: Waits for PostgreSQL to be healthy

### Node.js Backend
- **Image**: Built from `SW-Multimedia-main/Dockerfile.backend`
- **Port**: 5000
- **Database**: MongoDB
- **Environment**: Node.js with Mongoose
- **Startup**: Waits for MongoDB to be healthy

### Frontend
- **Image**: Built from `SW-Multimedia-main/Dockerfile.frontend`
- **Port**: 3000
- **Build**: Vite build with serve for production
- **Startup**: Waits for Node.js backend

### PostgreSQL
- **Image**: postgres:15-alpine
- **Port**: 5432
- **Volume**: postgres_data

### MongoDB
- **Image**: mongo:6.0-alpine
- **Port**: 27017
- **Volume**: mongo_data

## Environment Variables

Key environment variables used by services:

```
# Database
SPRING_DATASOURCE_URL=jdbc:postgresql://postgres:5432/neondb
SPRING_DATASOURCE_USERNAME=postgres
SPRING_DATASOURCE_PASSWORD=postgres
MONGO_URI=mongodb://mongo:27017/sw_multimedia

# Mail (Java Backend)
SPRING_MAIL_HOST=smtp.gmail.com
SPRING_MAIL_USERNAME=your_email@gmail.com
SPRING_MAIL_PASSWORD=your_app_password

# Frontend
VITE_API_URL=http://localhost:5000
```

## Troubleshooting

### Services won't start
```bash
# Check logs
docker-compose logs

# Rebuild images
docker-compose down
docker-compose build --no-cache
docker-compose up
```

### Database connection issues
```bash
# Verify database is running
docker-compose ps

# Check database logs
docker-compose logs postgres
docker-compose logs mongo
```

### Port already in use
Edit `docker-compose.yml` and change the port mappings:
```yaml
ports:
  - "8082:8082"  # Change first number to a different port
```

### Memory issues
Increase Docker's allocated memory in Docker Desktop settings or use:
```bash
docker-compose down
# Restart Docker daemon
docker-compose up
```

## Development vs Production

### For Development
Use local machine with hot-reload:
```bash
# Terminal 1: Frontend
cd SW-Multimedia-main
npm install
npm run dev

# Terminal 2: Node Backend
cd SW-Multimedia-main/Backend
npm install
npm start

# Terminal 3: Java Backend
cd sw_backend
mvn spring-boot:run
```

### For Production
Use Docker:
```bash
docker-compose up -d
```

## Network Communication

Services communicate through the `sw-network` Docker network:
- Frontend connects to Node.js backend: `http://node-backend:5000`
- Node.js backend connects to MongoDB: `mongodb://mongo:27017/sw_multimedia`
- Java backend connects to PostgreSQL: `postgresql://postgres:5432/neondb`

## Building Individual Services

```bash
# Build Java backend only
docker build -f sw_backend/Dockerfile -t sw-java-backend .

# Build Node backend only
docker build -f SW-Multimedia-main/Dockerfile.backend -t sw-node-backend ./SW-Multimedia-main

# Build frontend only
docker build -f SW-Multimedia-main/Dockerfile.frontend -t sw-frontend ./SW-Multimedia-main
```

## Next Steps

1. Update `.env` with your actual credentials
2. Test locally with `docker-compose up`
3. Push images to Docker Hub or your registry
4. Deploy to your hosting platform (AWS ECS, Google Cloud Run, Kubernetes, etc.)
