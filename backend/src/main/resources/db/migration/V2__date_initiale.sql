-- Date de test. Parola pentru toti utilizatorii: parola123 (hash BCrypt)
INSERT INTO utilizator (email, parola, rol) VALUES
    ('medic@test.ro',    '$2b$10$IjknqLpwvhRH/zW.VDN9zuCbfUgyK0m8Fc8HM5aEYvw/mS6h8s0U6', 'MEDIC'),
    ('pacient1@test.ro', '$2b$10$IjknqLpwvhRH/zW.VDN9zuCbfUgyK0m8Fc8HM5aEYvw/mS6h8s0U6', 'PACIENT'),
    ('pacient2@test.ro', '$2b$10$IjknqLpwvhRH/zW.VDN9zuCbfUgyK0m8Fc8HM5aEYvw/mS6h8s0U6', 'PACIENT');

INSERT INTO medic (utilizator_id, nume, prenume, specializare)
SELECT id, 'Popescu', 'Ana', 'Cardiologie' FROM utilizator WHERE email = 'medic@test.ro';

INSERT INTO pacient (utilizator_id, nume, prenume, data_nasterii)
SELECT id, 'Ionescu', 'Mihai', DATE '1980-05-12' FROM utilizator WHERE email = 'pacient1@test.ro';

INSERT INTO pacient (utilizator_id, nume, prenume, data_nasterii)
SELECT id, 'Georgescu', 'Elena', DATE '1992-11-03' FROM utilizator WHERE email = 'pacient2@test.ro';

-- medicul ii urmareste pe ambii pacienti (N-N)
INSERT INTO medic_pacient (medic_id, pacient_id)
SELECT m.id, p.id FROM medic m CROSS JOIN pacient p;

INSERT INTO masuratoare (pacient_id, puls, tensiune_sistolica, tensiune_diastolica, temperatura, glicemie, masurat_la)
SELECT p.id, v.puls, v.sis, v.dia, v.temp, v.gli, v.la
FROM pacient p
JOIN utilizator u ON u.id = p.utilizator_id
JOIN (VALUES
    ('pacient1@test.ro', 72, 120, 80, 36.6, 95,  TIMESTAMP '2026-11-01 08:00'),
    ('pacient1@test.ro', 78, 125, 82, 36.8, 110, TIMESTAMP '2026-11-02 08:00'),
    ('pacient1@test.ro', 70, 118, 79, 36.5, 90,  TIMESTAMP '2026-11-03 08:00'),
    ('pacient2@test.ro', 65, 110, 70, 36.7, 88,  TIMESTAMP '2026-11-03 09:00')
) AS v(email, puls, sis, dia, temp, gli, la) ON v.email = u.email;
