-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Servidor: 127.0.0.1:3306
-- Tiempo de generación: 28-03-2024 a las 15:59:32
-- Versión del servidor: 8.2.0
-- Versión de PHP: 8.2.13

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de datos: `turnero`
--

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `ciudadanos`
--

DROP TABLE IF EXISTS `ciudadanos`;
CREATE TABLE IF NOT EXISTS `ciudadanos` (
  `ID` bigint NOT NULL AUTO_INCREMENT,
  `dni_nif` char(9) COLLATE utf8mb4_spanish_ci NOT NULL,
  PRIMARY KEY (`ID`),
  UNIQUE KEY `UQ_Ciudadanos_dninif` (`dni_nif`)
) ENGINE=MyISAM AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_spanish_ci;

--
-- Volcado de datos para la tabla `ciudadanos`
--

INSERT INTO `ciudadanos` (`ID`, `dni_nif`) VALUES
(1, '12345678A'),
(2, '98765432B'),
(3, '56789012C'),
(4, '32165498D'),
(5, '45612378E'),
(6, '65498732F');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `tramites`
--

DROP TABLE IF EXISTS `tramites`;
CREATE TABLE IF NOT EXISTS `tramites` (
  `ID` bigint NOT NULL AUTO_INCREMENT,
  `descripcion` varchar(255) COLLATE utf8mb4_spanish_ci DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=MyISAM AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_spanish_ci;

--
-- Volcado de datos para la tabla `tramites`
--

INSERT INTO `tramites` (`ID`, `descripcion`) VALUES
(1, 'Renovación de Pasaporte'),
(2, 'Trámite de Licencia de Conducir'),
(3, 'Registro Civil de Nacimiento'),
(4, 'Solicitud de Certificado de Antecedentes Penales'),
(5, 'Inscripción en el Registro Electoral'),
(6, 'Solicitud de Subsidio Familiar'),
(7, 'Trámite de Permiso de Construcción'),
(8, 'Inscripción en el Registro de Contribuyentes');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `turnos`
--

DROP TABLE IF EXISTS `turnos`;
CREATE TABLE IF NOT EXISTS `turnos` (
  `ID` bigint NOT NULL AUTO_INCREMENT,
  `fecha_hora` datetime(3) DEFAULT NULL,
  `pendiente` tinyint(1) DEFAULT '0',
  `ciudadanos_id` bigint DEFAULT NULL,
  `tramites_id` bigint DEFAULT NULL,
  PRIMARY KEY (`ID`),
  KEY `FK_TURNOS_tramites_id` (`tramites_id`),
  KEY `FK_TURNOS_ciudadanos_id` (`ciudadanos_id`)
) ENGINE=MyISAM AUTO_INCREMENT=21 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_spanish_ci;

--
-- Volcado de datos para la tabla `turnos`
--

INSERT INTO `turnos` (`ID`, `fecha_hora`, `pendiente`, `ciudadanos_id`, `tramites_id`) VALUES
(1, '2024-03-29 16:50:06.000', 1, 1, 1),
(2, '2024-03-29 16:50:06.000', 1, 2, 2),
(3, '2024-03-29 16:50:06.000', 0, 3, 3),
(4, '2024-03-30 16:50:06.000', 1, 4, 4),
(5, '2024-03-30 16:50:06.000', 1, 5, 5),
(6, '2024-03-30 16:50:06.000', 1, 6, 6),
(7, '2024-03-31 16:50:06.000', 1, 1, 7),
(8, '2024-03-31 16:50:06.000', 0, 2, 8),
(9, '2024-03-31 16:50:06.000', 1, 3, 1),
(10, '2024-04-01 16:50:06.000', 1, 4, 2),
(11, '2024-04-01 16:50:06.000', 1, 5, 3),
(12, '2024-04-01 16:50:06.000', 1, 6, 4),
(13, '2024-04-02 16:50:06.000', 1, 1, 5),
(14, '2024-04-02 16:50:06.000', 1, 2, 6),
(15, '2024-04-02 16:50:06.000', 1, 3, 7),
(16, '2024-04-03 16:50:06.000', 1, 4, 8),
(17, '2024-04-03 16:50:06.000', 1, 5, 1),
(18, '2024-04-03 16:50:06.000', 1, 6, 2),
(19, '2024-04-04 16:50:06.000', 1, 1, 3),
(20, '2024-04-04 16:50:06.000', 1, 2, 4);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
