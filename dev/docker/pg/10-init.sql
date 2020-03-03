CREATE DATABASE event_app_test;

CREATE USER event_app_test WITH SUPERUSER PASSWORD 'event_app_test';

GRANT ALL PRIVILEGES ON DATABASE event_app_test TO event_app_test;
