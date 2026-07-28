-- phpMyAdmin SQL Dump
-- version 5.2.3
-- https://www.phpmyadmin.net/
--
-- Servidor: mysql
-- Tiempo de generación: 28-07-2026 a las 08:14:14
-- Versión del servidor: 8.0.45
-- Versión de PHP: 8.3.26

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de datos: `bms`
--

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `bicicleta`
--

CREATE TABLE `bicicleta` (
  `id_ref` varchar(100) NOT NULL,
  `marca` varchar(100) DEFAULT NULL,
  `modelo` varchar(100) DEFAULT NULL,
  `frenos` varchar(100) DEFAULT NULL,
  `susp_delantera` varchar(100) DEFAULT NULL,
  `susp_trasera` varchar(100) DEFAULT NULL,
  `transmision` varchar(100) DEFAULT NULL,
  `ruedas` varchar(100) DEFAULT NULL,
  `id_cliente` varchar(50) DEFAULT NULL,
  `estado` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT 'Sin reparar'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Volcado de datos para la tabla `bicicleta`
--

INSERT INTO `bicicleta` (`id_ref`, `marca`, `modelo`, `frenos`, `susp_delantera`, `susp_trasera`, `transmision`, `ruedas`, `id_cliente`, `estado`) VALUES
('Bici002', 'Mondraker', 'F-Podium', 'Sram Level', 'Sram Sid 35 120mm', 'Sram Sid 100mm', 'Sram XXSL 12V', 'D-tswin 1200X', '34567890C', 'En reparacion'),
('Bici003', 'Treck', 'Pro-Caliber 8', 'Shimano XTR', 'Fox 32 Elite 110mmº', '', 'Shimano XT', 'Maxxis', '23456789B', 'Reparada'),
('Bici004', 'BH', 'LinckRace', 'Shimano XTR', 'Fox Kashima 120mm 34', 'Fox 120mm', 'Shimano XTR Di2 12V', 'Duke', '51215378G', 'Reparada'),
('Bici005', 'Specialized', 'Epic 8', 'shimano', 'Fox 32 100mm ', '', 'Sram', 'estas', '12345678A', 'Sin reparar');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `cliente`
--

CREATE TABLE `cliente` (
  `dni` varchar(50) NOT NULL,
  `nombre` varchar(150) NOT NULL,
  `apellido` varchar(150) DEFAULT NULL,
  `telefono` varchar(50) DEFAULT NULL,
  `direccion` text
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Volcado de datos para la tabla `cliente`
--

INSERT INTO `cliente` (`dni`, `nombre`, `apellido`, `telefono`, `direccion`) VALUES
('12345678A', 'Carlos', 'Gómez Martín', '600123456', 'Calle Mayor 12, Madrid'),
('23456789B', 'Laura', 'Pérez Sánchez', '611234567', 'Avenida de la Constitución 45, Sevilla'),
('34567890C', 'Miguel', 'Fernández Ruiz', '622345678', 'Calle Gran Vía 89, Valencia'),
('45678901D', 'Ana', 'López Morales', '633456789', 'Plaza España 3, Zaragoza'),
('51213459Y', 'Angel', 'Morillo Jimenez', '657849302', 'Calle ave del paraiso'),
('51215378G', 'Angel', 'Morillo Jimenez', '64593420', 'esta'),
('51215378T', 'Fernando ', 'Alonso Diaz', '767859043', 'Ave del paraiso'),
('53437241R', 'ivan', 'padilla', '6912326864', 'esta'),
('56789012E', 'Javier', 'Martínez Ortega', '644567890', 'Calle del Sol 22, Málaga'),
('67890123F', 'María', 'Rodríguez Gil', '655678901', 'Avenida Diagonal 120, Barcelona');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `factura`
--

CREATE TABLE `factura` (
  `id_factura` int NOT NULL,
  `fecha` date NOT NULL,
  `total` decimal(12,2) DEFAULT '0.00',
  `id_cliente` varchar(50) NOT NULL,
  `id_bicicleta` varchar(100) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Volcado de datos para la tabla `factura`
--

INSERT INTO `factura` (`id_factura`, `fecha`, `total`, `id_cliente`, `id_bicicleta`) VALUES
(3, '2026-05-07', 5400.00, '56789012E', NULL),
(8, '2025-04-04', 3.00, '34567890C', 'Bici002'),
(9, '2026-05-05', 300.00, '23456789B', NULL),
(10, '2026-05-15', 150.50, '12345678A', 'Bici002'),
(11, '2026-05-12', 44444.00, '56789012E', NULL),
(12, '2026-05-19', 4500.00, '12345678A', NULL),
(13, '2026-05-19', 3.00, '56789012E', NULL),
(14, '2026-05-19', 4000.00, '12345678A', 'Bici002');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `factura_linea`
--

CREATE TABLE `factura_linea` (
  `id_factura` int NOT NULL,
  `linea` int NOT NULL,
  `id_pieza` int DEFAULT NULL,
  `descripcion` text,
  `cantidad` int NOT NULL DEFAULT '1',
  `precio_unitario` decimal(10,2) DEFAULT '0.00'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `mantenimiento`
--

CREATE TABLE `mantenimiento` (
  `id_mantenimiento` bigint UNSIGNED NOT NULL,
  `id_mecanico` varchar(50) NOT NULL,
  `id_bicicleta` varchar(100) NOT NULL,
  `fecha` date NOT NULL,
  `observaciones` text,
  `horas_trabajadas` decimal(5,2) DEFAULT '0.00'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Volcado de datos para la tabla `mantenimiento`
--

INSERT INTO `mantenimiento` (`id_mantenimiento`, `id_mecanico`, `id_bicicleta`, `fecha`, `observaciones`, `horas_trabajadas`) VALUES
(3, 'Mecanico2', 'Bici002', '2026-04-15', '', 3.00),
(4, 'Mecanico3', 'Bici002', '2026-05-06', '', 30.00),
(5, 'Mecanico44', 'Bici004', '2026-05-18', 'Hemos cambiado la suspension delantera de la bicicleta, hemos engrasado la cadena y cambiado los rodamientos del nucleo', 6.00);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `mecanico`
--

CREATE TABLE `mecanico` (
  `dni` varchar(50) NOT NULL,
  `nombre` varchar(150) NOT NULL,
  `apellidos` varchar(255) DEFAULT NULL,
  `email` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  `usuario` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `contrasena` varchar(255) NOT NULL,
  `telefono` varchar(50) DEFAULT NULL,
  `direccion` text
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Volcado de datos para la tabla `mecanico`
--

INSERT INTO `mecanico` (`dni`, `nombre`, `apellidos`, `email`, `usuario`, `contrasena`, `telefono`, `direccion`) VALUES
('43567890Y', 'Yorch', 'Martin', '', 'yorch25', 'yorcj', '42423423', 'Esta'),
('45678907T', 'Ivan', 'Padilla Saldaña', '', 'Ivanps06', 'ivanps', '27458934', 'las palmeras'),
('46554354H', 'Olga', 'Fernandez Palacios', '', 'Olgafp', 'soyfea', '54325', 'ytrew'),
('51215374R', 'Curro', 'Curro', 'ivanpa06@gmail.com', 'curro', 'curro2345', '5757755', 'esta'),
('54632893P', 'Angel', 'Morillo Jimenez', 'ivanpadillas006@gmail.com', 'angelmj06', '691232686ipS', '691232686', 'esta'),
('dni', 'nombre', 'apellidos', 'ivanpadillas006@gmail.com', 'usuario', 'contra', 'telefono', 'direccon');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `pieza`
--

CREATE TABLE `pieza` (
  `id_pieza` bigint UNSIGNED NOT NULL,
  `modelo` varchar(150) DEFAULT NULL,
  `marca` varchar(100) DEFAULT NULL,
  `stock` int NOT NULL DEFAULT '0',
  `tipo` varchar(100) DEFAULT NULL,
  `precio` double(10,2) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Volcado de datos para la tabla `pieza`
--

INSERT INTO `pieza` (`id_pieza`, `modelo`, `marca`, `stock`, `tipo`, `precio`) VALUES
(1, 'Kashima 32 100mm', 'Fox', 11, 'Suspension Delantera', 1000.00),
(2, 'Shimano XTR', 'Shimano', 0, 'Cambio Electronico', 400.00),
(3, 'Oiz M10', 'Orbea', 2, 'Cuadro Carbono', 2500.00),
(4, 'Progess', 'progress', 4, 'manillar', 150.00);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `usa`
--

CREATE TABLE `usa` (
  `id_bicicleta` varchar(100) NOT NULL,
  `id_pieza` bigint UNSIGNED NOT NULL,
  `cantidad` int NOT NULL DEFAULT '1',
  `id_usa` int NOT NULL,
  `id_cliente` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Índices para tablas volcadas
--

--
-- Indices de la tabla `bicicleta`
--
ALTER TABLE `bicicleta`
  ADD PRIMARY KEY (`id_ref`),
  ADD UNIQUE KEY `id_ref` (`id_ref`),
  ADD UNIQUE KEY `id_ref_2` (`id_ref`),
  ADD KEY `FKp3kou5wci4p445f41qcer8ruo` (`id_cliente`);

--
-- Indices de la tabla `cliente`
--
ALTER TABLE `cliente`
  ADD PRIMARY KEY (`dni`);

--
-- Indices de la tabla `factura`
--
ALTER TABLE `factura`
  ADD PRIMARY KEY (`id_factura`),
  ADD KEY `fk_factura_cliente` (`id_cliente`),
  ADD KEY `fk_factura_bicicleta` (`id_bicicleta`);

--
-- Indices de la tabla `factura_linea`
--
ALTER TABLE `factura_linea`
  ADD PRIMARY KEY (`id_factura`,`linea`);

--
-- Indices de la tabla `mantenimiento`
--
ALTER TABLE `mantenimiento`
  ADD PRIMARY KEY (`id_mantenimiento`),
  ADD UNIQUE KEY `id_mantenimiento` (`id_mantenimiento`);

--
-- Indices de la tabla `mecanico`
--
ALTER TABLE `mecanico`
  ADD PRIMARY KEY (`dni`);

--
-- Indices de la tabla `pieza`
--
ALTER TABLE `pieza`
  ADD PRIMARY KEY (`id_pieza`),
  ADD UNIQUE KEY `id_pieza` (`id_pieza`);

--
-- Indices de la tabla `usa`
--
ALTER TABLE `usa`
  ADD PRIMARY KEY (`id_bicicleta`,`id_pieza`),
  ADD KEY `FKid_cliente` (`id_cliente`),
  ADD KEY `FKid_pieza` (`id_pieza`);

--
-- AUTO_INCREMENT de las tablas volcadas
--

--
-- AUTO_INCREMENT de la tabla `factura`
--
ALTER TABLE `factura`
  MODIFY `id_factura` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=15;

--
-- AUTO_INCREMENT de la tabla `mantenimiento`
--
ALTER TABLE `mantenimiento`
  MODIFY `id_mantenimiento` bigint UNSIGNED NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- AUTO_INCREMENT de la tabla `pieza`
--
ALTER TABLE `pieza`
  MODIFY `id_pieza` bigint UNSIGNED NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- Restricciones para tablas volcadas
--

--
-- Filtros para la tabla `bicicleta`
--
ALTER TABLE `bicicleta`
  ADD CONSTRAINT `FKp3kou5wci4p445f41qcer8ruo` FOREIGN KEY (`id_cliente`) REFERENCES `cliente` (`dni`);

--
-- Filtros para la tabla `factura`
--
ALTER TABLE `factura`
  ADD CONSTRAINT `fk_factura_bicicleta` FOREIGN KEY (`id_bicicleta`) REFERENCES `bicicleta` (`id_ref`) ON DELETE SET NULL,
  ADD CONSTRAINT `fk_factura_cliente` FOREIGN KEY (`id_cliente`) REFERENCES `cliente` (`dni`) ON DELETE RESTRICT;

--
-- Filtros para la tabla `usa`
--
ALTER TABLE `usa`
  ADD CONSTRAINT `FK3cab071503nhgu4vhao022fo` FOREIGN KEY (`id_bicicleta`) REFERENCES `bicicleta` (`id_ref`),
  ADD CONSTRAINT `FK6hpcaao2tgq5fbdq1kpsfwwyb` FOREIGN KEY (`id_pieza`) REFERENCES `pieza` (`id_pieza`),
  ADD CONSTRAINT `FKgr4ke8pgg5n01xu062hdrqqbr` FOREIGN KEY (`id_cliente`) REFERENCES `cliente` (`dni`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
