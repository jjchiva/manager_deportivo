CREATE TABLE `equipos` (
                           `id` bigint(20) NOT NULL,
                           `aforo` int(11) NOT NULL,
                           `estadio` varchar(255) COLLATE utf8mb4_spanish_ci DEFAULT NULL,
                           `nombre` varchar(255) COLLATE utf8mb4_spanish_ci NOT NULL,
                           `presupuesto` float NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_spanish_ci;


CREATE TABLE `jugador` (
                           `id` bigint(20) NOT NULL,
                           `apellidos` varchar(255) COLLATE utf8mb4_spanish_ci DEFAULT NULL,
                           `edad` int(11) NOT NULL,
                           `nombre` varchar(255) COLLATE utf8mb4_spanish_ci DEFAULT NULL,
                           `equipo_id` bigint(20) DEFAULT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_spanish_ci;


ALTER TABLE `equipos`
    ADD PRIMARY KEY (`id`);

ALTER TABLE `jugador`
    ADD PRIMARY KEY (`id`),
    ADD KEY `FKi3trnecbt3w7hnm27g6jj4ncq` (`equipo_id`);

ALTER TABLE `equipos`
    MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

ALTER TABLE `jugador`
    MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;
COMMIT;