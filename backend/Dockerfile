FROM python:3.9-slim
WORKDIR /app
RUN pip install fastapi uvicorn
COPY . .
CMD ["uvicorn", "main:app", "--host", "0.0.0.0", "--port", "5000"]