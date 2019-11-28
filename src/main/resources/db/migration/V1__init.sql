
CREATE TABLE `equipos` (
                           `id` bigint(20) NOT NULL,
                           `aforo` int(11) NOT NULL,
                           `estadio` varchar(255) DEFAULT NULL,
                           `nombre` varchar(255) DEFAULT NULL,
                           `presupuesto` float NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4;

ALTER TABLE `equipos`
    ADD PRIMARY KEY (`id`);

ALTER TABLE `equipos`
    MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;
COMMIT;